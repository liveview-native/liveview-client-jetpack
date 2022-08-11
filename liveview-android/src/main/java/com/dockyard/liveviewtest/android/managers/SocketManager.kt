package com.dockyard.liveviewtest.android.managers

import android.util.Log
import com.dockyard.liveviewtest.android.mappers.SocketPayloadMapper
import com.dockyard.liveviewtest.android.ui.phx_components.PhxAction
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.Socket
import java.net.ConnectException
import java.util.*

class SocketManager(
    private val okHttpClient: OkHttpClient,
    private val socketPayloadMapper: SocketPayloadMapper
) {

    private var phxSocket: Socket? = null
    private var channel : Channel? = null
    private val uuid: String = UUID.randomUUID().toString()

    private var liveReloadChannel: Channel? = null
    private var liveReloadSocket: Socket? = null

    lateinit var domParsedListener: (Document) -> Unit
    var liveReloadListener: (() -> Unit)? = null


    fun connectToChatRoomWithParams(
        phxLiveViewPayload: PhoenixLiveViewPayload
    ) {

        liveReloadSocket?.disconnect()
        phxSocket?.disconnect()

        Log.d("TAG", "Connection to socket with params $phxLiveViewPayload")
        // Create the Socket
        val socketParams = mapOf(
            "_csrf_token" to phxLiveViewPayload._csrfToken,
            "_mounts" to 0,
            "client_id" to uuid,
        )

        val socketQueryParams = socketParams.entries.fold("") { acc: String, entry: Map.Entry<String, Any?> ->
            acc + "${entry.key}=${entry.value}&"
        }

        phxSocket = Socket(
            url = "ws://10.0.2.2:4000/live/websocket?$socketQueryParams",
            client = okHttpClient
        )

        liveReloadSocket = Socket(
            url = "ws://10.0.2.2:4000/phoenix/live_reload/socket",
            client = okHttpClient
        )

        // Listen to events on the Socket
        phxSocket?.logger = {
            Log.d("SOCKET TAG", it)
        }


        phxSocket?.onOpen {
            Log.d("TAG", "----- SOCKET OPENED -----")
        }


        val channelConnectionParams = mapOf(
            "session" to phxLiveViewPayload.dataPhxSession,
            "static" to phxLiveViewPayload.dataPhxStatic,
            "url" to "http://localhost:4000/",
            "params" to mapOf(
                "_mounts" to 0,
                "_csrf_token" to phxLiveViewPayload._csrfToken,
                "_native" to true,
                "client_id" to uuid
            )
        )

        channel = phxSocket?.channel(
            topic = "lv:${phxLiveViewPayload.phxId}",
            params = channelConnectionParams
        )

        liveReloadChannel = liveReloadSocket?.channel(
            topic = "phoenix:live_reload"
        )

        channel?.join()
            ?.receive("ok") { theMessage: Message ->
                Log.d("TAG", "CHAT ROOM LIVEVIEW JOINED")
                val payload: Map<String, Any?> = theMessage.payload
                val outputDom = socketPayloadMapper.mapRawPayloadToDom(payload)
                domParsedListener.invoke(outputDom)

            }
            ?.receive("error") {
                /* failed to join the chatroom */
                Log.d("TAG", "CHAT ROOM LIVEVIEW ERROR")
                Log.e("ERROR", it.toString())
            }
            ?.receive("response") {
                Log.d("RESPONSE", "CHAT ROOM RESPONSE")
            }

        channel?.onMessage { message: Message ->
            Log.d("CHANNEL MESSAGE", message.toString())

            when(message.event) {
                "phx_reply" -> {
                    Log.d("ON MESSAGE PAYLOAD", message.payload.toString())

                    val outputDom = socketPayloadMapper.parseDiff(message)
                    outputDom?.let {
                        domParsedListener.invoke(it)
                    }


                }
//
                "diff" -> {
                    Log.d("ON DIFF PAYLOAD", message.payload.toString())

                    socketPayloadMapper.extractDiff(message.payload)
                }
            }

            message
        }

        liveReloadChannel?.join()?.receive("ok") { theLiveReloadMessage ->
            Log.d("LIVE RELOAD CHANNEL", theLiveReloadMessage.toString())
        }

        liveReloadChannel?.onMessage { theLiveReloadMessage ->
            when (theLiveReloadMessage.event) {
                "assets_change" -> {
                    Log.d("LIVE RELOAD CHANNEL MESSAGE", theLiveReloadMessage.payload.toString())
                    liveReloadListener?.invoke()
                }
                else -> {

                }
            }
            theLiveReloadMessage
        }

        phxSocket?.onClose { Log.d("TAG", "Socket Closed") }
        phxSocket?.onError { throwable, response ->

            Log.e("TAG", throwable.message.toString())

            when(throwable) {
                is ConnectException -> {
                    val errorHtml = "<column>" +
                            "<text width=fill padding=16>" + throwable.message.toString() + "</text>" +
                            "<text width=fill padding=16>" + "This probably means your localhost server isn't running...\nPlease start your server in the terminal using iex -S mix phx.server and rerun the android application" + "</text>" +
                            "</column>"
                    val errorDomState = Jsoup.parse(errorHtml)
                    domParsedListener.invoke(errorDomState)

                }

                else -> {
                    val errorHtml = "<column>" +
                            "<text width=fill padding=16>" + throwable.message.toString() + "</text>" +
                            "</column>"
                    val errorDomState = Jsoup.parse(errorHtml)
                    domParsedListener.invoke(errorDomState)
                }
            }
        }

        phxSocket?.connect()
        liveReloadSocket?.connect()
    }


    fun pushChannelMessage(phxAction: PhxAction) {
        pushChannelMessage(event = phxAction.event, payload = phxAction.payload)
    }

    private fun pushChannelMessage(event: String, payload: Payload) {
        channel?.push(
            event = event,
            payload = payload
        )
    }

}



package org.phoenixframework.liveview.data.service

import android.util.Log
import okhttp3.OkHttpClient
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.Socket
import org.phoenixframework.liveview.data.managers.PhoenixLiveViewPayload
import java.util.*


class SocketService(
    private val  okHttpClient: OkHttpClient
) {

    private var phxSocket: Socket? = null
    private var channel: Channel? = null
    private val uuid: String = UUID.randomUUID().toString()

    private var liveReloadChannel: Channel? = null
    private var liveReloadSocket: Socket? = null


    var liveReloadListener: (() -> Unit)? = null




    fun connectToLiveView(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String,
        socketBase: String,
        messageListener: (message: Message) -> Unit
    ) {

        phxSocket?.disconnect()
        Log.d("TAG", "Connection to socket with params $phxLiveViewPayload")
        setupPhxSocketConnection(phxLiveViewPayload,baseUrl= socketBase, messageListener = messageListener)

        setupPhoenixChannel(phxLiveViewPayload,baseUrl = baseUrl, messageListener = messageListener)

        phxSocket?.connect()

    }

    private fun setupPhoenixChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String,
        messageListener: (message: Message) -> Unit

    ) {
        val channelConnectionParams = mapOf(
            "session" to phxLiveViewPayload.dataPhxSession,
            "static" to phxLiveViewPayload.dataPhxStatic,
            "url" to baseUrl,
            "params" to mapOf(
                "_csrf_token" to phxLiveViewPayload._csrfToken,
                "_mounts" to 0,
                "client_id" to uuid,
                "_platform" to "android"
            )
        )

        channel = phxSocket?.channel(
            topic = "lv:${phxLiveViewPayload.phxId}", params = channelConnectionParams
        )



        channel?.join()?.receive("ok") { message: Message ->
            Log.d("SOCKET MANAGER CHANNEL JOIN OK", "CHAT ROOM LIVEVIEW JOINED")

            messageListener(message)


        }?.receive("error") {
            /* failed to join the chatroom */
            Log.d("SOCKET MANAGER CHANNEL JOIN ERROR", "CHAT ROOM LIVEVIEW ERROR")
            Log.e("ERROR", it.toString())
        }?.receive("response") {
            Log.d("RESPONSE", "CHAT ROOM RESPONSE")
        }

        channel?.onMessage { message: Message ->
            Log.d("========= >>> CHANNEL MESSAGE", message.toString())

            when (message.event) {
                "phx_reply" -> {
                    Log.d("ON MESSAGE PAYLOAD", message.payload.toString())


                    messageListener(message)

                }

                "diff" -> {
                    messageListener(message)

                }
            }

            message
        }


    }

    private fun setupPhxSocketConnection(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String,
        messageListener: (message: Message) -> Unit
    ) {
        val socketParams = mapOf(
            "_csrf_token" to phxLiveViewPayload._csrfToken,
            "_mounts" to 0,
            "client_id" to uuid,
            "_platform" to "android"
        )

        val socketQueryParams =
            socketParams.entries.fold("") { acc: String, entry: Map.Entry<String, Any?> ->
                acc + "${entry.key}=${entry.value}&"
            }

        phxSocket = Socket(
            url = "${baseUrl}/live/websocket?$socketQueryParams",
            client = okHttpClient
        )

        // Listen to events on the Socket
        phxSocket?.logger = {
            Log.d("PHX-SOCKET LOGGER TAG", it)
        }

        phxSocket?.onError { error, response ->

            Log.e("ON ERROR", error.toString())
            Log.e("ON ERROR RESPONSE", error.toString())


        }

        phxSocket?.onOpen {
            Log.d("TAG", "----- SOCKET OPENED -----")
        }
        phxSocket?.onClose { Log.d("TAG", "Socket Closed") }


        phxSocket?.onError { throwable, response ->

            Log.e("TAG", throwable.message.toString())

            throw throwable

        }
    }



    private fun connectToLiveReloadSocket() {
        liveReloadSocket?.disconnect()
        liveReloadSocket = Socket(
            url = "/phoenix/live_reload/socket", client = okHttpClient
        )
        liveReloadChannel = liveReloadSocket?.channel(
            topic = "phoenix:live_reload"
        )
        liveReloadChannel?.join()?.receive("ok") { liveReloadMessage ->
            Log.d("LIVE RELOAD CHANNEL", liveReloadMessage.toString())
        }

        liveReloadChannel?.onMessage { liveReloadMessage ->
            when (liveReloadMessage.event) {
                "assets_change" -> {
                    Log.d("LIVE RELOAD CHANNEL MESSAGE", liveReloadMessage.payload.toString())
                    liveReloadListener?.invoke()
                }
                else -> {

                }
            }
            liveReloadMessage
        }

        liveReloadSocket?.connect()
    }


/*
    fun pushChannelMessage(phxAction: PhxAction) {
        pushChannelMessage(event = phxAction.event, payload = phxAction.payload)
    }*/

    private fun pushChannelMessage(event: String, payload: Payload) {
        channel?.push(
            event = event, payload = payload
        )
    }

}



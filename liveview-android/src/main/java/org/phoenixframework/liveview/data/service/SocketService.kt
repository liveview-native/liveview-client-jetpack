package org.phoenixframework.liveview.data.service

import android.util.Log
import java.util.*
import okhttp3.OkHttpClient
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Socket
import org.phoenixframework.liveview.data.dto.PhoenixLiveViewPayload

class SocketService(private val okHttpClient: OkHttpClient) {
    private var phxSocket: Socket? = null
    private var channel: Channel? = null
    private val uuid: String = UUID.randomUUID().toString()

    fun connectToLiveView(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String,
        socketBase: String,
        messageListener: (message: Message) -> Unit
    ) {
        phxSocket?.disconnect()
        Log.d("TAG", "Connection to socket with params $phxLiveViewPayload")
        setupPhxSocketConnection(phxLiveViewPayload, baseUrl = socketBase)

        setupPhoenixChannel(
            phxLiveViewPayload,
            baseUrl = baseUrl,
            messageListener = messageListener
        )

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
            "params" to
                    mapOf(
                        "_csrf_token" to phxLiveViewPayload._csrfToken,
                        "_mounts" to 0,
                        "client_id" to uuid,
                        "_platform" to "android"
                    )
        )

        channel =
            phxSocket
                ?.channel(
                    topic = "lv:${phxLiveViewPayload.phxId}",
                    params = channelConnectionParams
                )
                ?.apply {
                    join()
                        .receive("ok") { message: Message ->
                            Log.d("SM JOIN OK", "CHAT ROOM LIVEVIEW JOINED")

                            messageListener(message)
                        }
                        .receive("error") {
                            /* failed to join the chatroom */
                            Log.d("SM JOIN FAIL", "CHAT ROOM LIVEVIEW ERROR")
                            Log.e("ERROR", it.toString())
                        }
                        .receive("response") { Log.d("RESPONSE", "CHAT ROOM RESPONSE") }

                    onMessage { message: Message ->
                        Log.d("==> CHANNEL MESSAGE", message.toString())

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
    }

    private fun setupPhxSocketConnection(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String
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

        phxSocket =
            Socket(url = "${baseUrl}/live/websocket?$socketQueryParams", client = okHttpClient)
                .apply {
                    // Listen to events on the Socket
                    logger = { Log.d("PHX-SOCKET LOGGER TAG", it) }

                    onError { error, response ->
                        Log.e("ON ERROR", error.toString())
                        Log.e("ON ERROR RESPONSE", error.toString())
                    }

                    onOpen { Log.d("TAG", "----- SOCKET OPENED -----") }

                    onClose { Log.d("TAG", "Socket Closed") }

                    onError { throwable, response ->
                        Log.e("TAG", throwable.message.toString())

                        throw throwable
                    }
                }
    }
}
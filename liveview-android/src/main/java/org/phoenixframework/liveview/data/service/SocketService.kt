package org.phoenixframework.liveview.data.service

import android.util.Log
import okhttp3.OkHttpClient
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.Socket
import org.phoenixframework.liveview.data.dto.PhoenixLiveViewPayload
import java.util.UUID

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
        Log.d(TAG, "Connection to socket with params $phxLiveViewPayload")
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
                        "_platform" to "jetpack"
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
                            Log.d(TAG, "Channel::join::receive::ok")
                            messageListener(message)
                        }
                        .receive("error") {
                            Log.d(TAG, "Channel::join::receive::error->$it")
                        }
                        .receive("response") {
                            Log.d(TAG, "Channel::join::receive::response->$it")
                        }

                    onMessage { message: Message ->
                        Log.d(TAG, "Channel::onMessage->$message")

                        when (message.event) {
                            "phx_reply" -> {
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
            "_platform" to "jetpack"
        )

        val socketQueryParams =
            socketParams.entries.fold("") { acc: String, entry: Map.Entry<String, Any?> ->
                acc + "${entry.key}=${entry.value}&"
            }

        phxSocket =
            Socket(url = "${baseUrl}/live/websocket?$socketQueryParams", client = okHttpClient)
                .apply {
                    // Listen to events on the Socket
                    logger = { Log.d(TAG, it) }

                    onOpen {
                        Log.d(TAG, "Socket::onOpen")
                        Log.d(TAG, "----- SOCKET OPENED -----")
                    }

                    onClose {
                        Log.d(TAG, "Socket::onClose")
                    }

                    onError { throwable, _ ->
                        Log.e(TAG, "Socket::onError-->${throwable.message}")
                        throw throwable
                    }
                }
    }

    fun pushEvent(event: String, payload: Payload) {
        channel?.push(event, payload)
    }

    companion object {
        private const val TAG = "SocketService"
    }
}
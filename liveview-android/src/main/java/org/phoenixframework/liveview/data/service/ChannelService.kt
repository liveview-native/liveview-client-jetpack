package org.phoenixframework.liveview.data.service

import android.util.Log
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.liveview.data.dto.PhoenixLiveViewPayload

class ChannelService(
    private val socketService: SocketService
) {
    private var channel: Channel? = null

    fun joinPhoenixChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseHttpUrl: String,
        redirect: Boolean,
        messageListener: (message: Message) -> Unit
    ) {
        channel = socketService.createChannel(phxLiveViewPayload, baseHttpUrl, redirect)?.apply {
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

                    "close" -> {
                        channel?.leave()
                    }
                }
                message
            }
        }
    }

    fun pushEvent(event: String, payload: Payload) {
        Log.d(TAG, "pushEvent: event: $event, payload: $payload")
        channel?.push(event, payload)
    }

    fun closeChannel() {
        channel?.leave()
    }

    companion object {
        private const val TAG = "ChannelService"
    }
}
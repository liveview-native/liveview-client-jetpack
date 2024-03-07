package org.phoenixframework.liveview.data.service

import android.util.Log
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload
import org.phoenixframework.liveview.data.repository.PhoenixLiveViewPayload

class ChannelService(
    private val socketService: SocketService
) {
    private var channel: Channel? = null
    val isJoined: Boolean
        get() = channel?.isJoined == true

    fun joinPhoenixChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseHttpUrl: String,
        redirect: Boolean,
        messageListener: (message: Message) -> Unit
    ) {
        channel = socketService.createChannel(phxLiveViewPayload, baseHttpUrl, redirect)?.apply {
            join()
                .receive(JOIN_STATUS_OK) { message: Message ->
                    Log.d(TAG, "Channel::join::receive::ok")
                    messageListener(message)
                }
                .receive(JOIN_STATUS_ERROR) {
                    Log.d(TAG, "Channel::join::receive::error->$it")
                }
                .receive(JOIN_STATUS_RESPONSE) {
                    Log.d(TAG, "Channel::join::receive::response->$it")
                }

            onMessage { message: Message ->
                Log.d(TAG, "Channel::onMessage->$message")

                when (message.event) {
                    MESSAGE_EVENT_PHX_REPLY -> {
                        messageListener(message)
                    }

                    MESSAGE_EVENT_DIFF -> {
                        messageListener(message)
                    }

                    MESSAGE_EVENT_CLOSE -> {
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

    fun leaveChannel() {
        Log.d(TAG, "Leaving channel: ${channel?.topic}")
        channel?.leave()
    }

    companion object {
        private const val TAG = "ChannelService"

        const val MESSAGE_EVENT_DIFF = "diff"
        private const val MESSAGE_EVENT_PHX_REPLY = "phx_reply"
        private const val MESSAGE_EVENT_CLOSE = "close"

        private const val JOIN_STATUS_OK = "ok"
        private const val JOIN_STATUS_ERROR = "error"
        private const val JOIN_STATUS_RESPONSE = "response"
    }
}
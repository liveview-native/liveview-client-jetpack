package org.phoenixframework.liveview.foundation.data.service

import android.util.Log
import org.phoenixframework.Channel
import org.phoenixframework.Message
import org.phoenixframework.Payload

class ChannelService(
    private val socketService: SocketService
) {
    private var channel: Channel? = null
    private var liveReloadChannel: Channel? = null

    fun joinPhoenixChannel(
        baseHttpUrl: String,
        redirect: Boolean,
        messageListener: (message: Message) -> Unit
    ) {
        channel = socketService.createChannel(baseHttpUrl, redirect)?.apply {
            join()
                .receive(JOIN_STATUS_OK) { message: Message ->
                    Log.d(TAG, "joinPhoenixChannel::ok")
                    messageListener(message)
                }
                .receive(JOIN_STATUS_ERROR) {
                    Log.d(TAG, "joinPhoenixChannel::error->$it")
                }
                .receive(JOIN_STATUS_RESPONSE) {
                    Log.d(TAG, "joinPhoenixChannel::response->$it")
                }

            onMessage { message: Message ->
                Log.d(TAG, "joinPhoenixChannel::onMessage->$message")

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

    fun joinLiveReloadChannel(onAssetsChange: () -> Unit) {
        liveReloadChannel = socketService.createReloadChannel()?.apply {
            join()
                .receive(JOIN_STATUS_OK) {
                    Log.d(TAG, "joinLiveReloadChannel::ok")
                }.receive(JOIN_STATUS_ERROR) {
                    Log.d(TAG, "joinLiveReloadChannel::error->$it")
                }

            onMessage { message: Message ->
                if (message.event == MESSAGE_EVENT_ASSETS_CHANGE) {
                    Log.d(TAG, "joinLiveReloadChannel::assets changed, reloading")
                    onAssetsChange()
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
        Log.d(TAG, "leaveChannel: ${channel?.topic}")
        channel?.leave()

    }

    fun leaveReloadChannel() {
        Log.d(TAG, "leaveReloadChannel: ${liveReloadChannel?.topic}")
        liveReloadChannel?.leave()
    }

    companion object {
        private const val TAG = "ChannelService"

        const val MESSAGE_EVENT_DIFF = "diff"
        const val MESSAGE_ERROR_REASON = "reason"

        const val ERROR_REASON_STALE = "stale"
        const val ERROR_REASON_UNAUTHORIZED = "unauthorized"

        private const val MESSAGE_EVENT_PHX_REPLY = "phx_reply"
        private const val MESSAGE_EVENT_CLOSE = "close"
        private const val MESSAGE_EVENT_ASSETS_CHANGE = "assets_change"

        private const val JOIN_STATUS_OK = "ok"
        private const val JOIN_STATUS_ERROR = "error"
        private const val JOIN_STATUS_RESPONSE = "response"
    }
}
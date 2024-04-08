package org.phoenixframework.liveview.data.repository

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import org.phoenixframework.liveview.data.service.ChannelService
import org.phoenixframework.liveview.data.service.SocketService

class Repository(
    private val httpBaseUrl: String,
    private val wsBaseUrl: String,
) {
    private val socketService = SocketService

    private val channelService: ChannelService = ChannelService(socketService)

    val liveSocketConnectionFlow = socketService.connectionFlow
    val liveReloadSocketConnectionFlow = socketService.liveReloadConnectionFlow

    suspend fun connectToLiveViewSocket() {
        Log.i(TAG, "connectToLiveViewSocket")
        Log.i(TAG, ">>>>httpBaseUrl=$httpBaseUrl | wsBaseUrl=$wsBaseUrl")
        socketService.connectToLiveViewSocket(
            httpBaseUrl = httpBaseUrl,
            socketBaseUrl = wsBaseUrl,
        )
    }

    fun disconnectFromLiveViewSocket() {
        Log.i(TAG, "disconnectFromLiveViewSocket->url=$httpBaseUrl")
        Log.i(TAG, ">>>>httpBaseUrl=$httpBaseUrl | wsBaseUrl=$wsBaseUrl")
        socketService.disconnectFromLiveView()
    }

    fun joinLiveViewChannel(redirect: Boolean) = callbackFlow {
        if (socketService.payload == null) {
            socketService.loadInitialPayload(httpBaseUrl)
        }
        socketService.payload?.let {
            Log.i(TAG, "joinLiveViewChannel")
            Log.i(TAG, ">>>>httpBaseUrl=$httpBaseUrl | wsBaseUrl=$wsBaseUrl")
            channelService.joinPhoenixChannel(
                it,
                httpBaseUrl,
                redirect
            ) { message ->
                trySend(message)
            }
        }
        awaitClose {
            try {
                Log.i(TAG, "joinLiveViewChannel::Closing channel...")
                channel.close()
            } catch (e: Exception) {
                Log.e(TAG, "joinLiveViewChannel::Error awaiting for close: ${e.message}", e)
            }
        }
    }.catch {
        Log.e(TAG, "joinLiveViewChannel::Error in join channel flow: ${it.message}")
    }

    fun leaveChannel() {
        Log.i(TAG, "leaveChannel")
        channelService.leaveChannel()
    }

    fun connectToReloadSocket() {
        Log.i(TAG, "connectToReloadSocket")
        return socketService.connectLiveReloadSocket(
            socketBaseUrl = wsBaseUrl
        )
    }

    fun disconnectFromReloadSocket() {
        Log.i(TAG, "disconnectFromReloadSocket")
        socketService.disconnectReloadSocket()
    }

    fun joinReloadChannel() = callbackFlow {
        socketService.payload?.let {
            Log.i(TAG, "joinReloadChannel")
            channelService.joinLiveReloadChannel {
                trySend(Unit)
            }
        }
        awaitClose {
            try {
                Log.i(TAG, "joinReloadChannel::Closing reload channel...")
                channel.close()
            } catch (e: Exception) {
                Log.e(TAG, "joinReloadChannel::Error awaiting for close reload: ${e.message}", e)
            }
        }
    }

    fun leaveReloadChannel() {
        Log.i(TAG, "leaveReloadChannel")
        channelService.leaveReloadChannel()
    }

    fun pushEvent(type: String, event: String, value: Any?, target: Int? = null) {
        Log.d(TAG, "pushEvent: [type: $type | event: $event | value: $value | target: $target]")
        channelService.pushEvent(
            PUSH_TYPE_EVENT, mapOf(
                EVENT_KEY_TYPE to type,
                EVENT_KEY_EVENT to event,
                EVENT_KEY_VALUE to (value ?: emptyMap<String, Any>()),
                EVENT_KEY_CID to target as Any?
            )
        )
    }

    suspend fun loadThemeData(): Map<String, Any> {
        return socketService.loadThemeData(httpBaseUrl)
    }

    suspend fun loadStyleData(): String? {
        return socketService.loadStyleData(httpBaseUrl)
    }

    companion object {
        private const val TAG = "Repository"

        // Push event constants
        private const val PUSH_TYPE_EVENT = "event"
        private const val EVENT_KEY_TYPE = "type"
        private const val EVENT_KEY_EVENT = "event"
        private const val EVENT_KEY_VALUE = "value"
        private const val EVENT_KEY_CID = "cid"
    }
}
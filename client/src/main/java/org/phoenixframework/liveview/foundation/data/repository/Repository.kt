package org.phoenixframework.liveview.foundation.data.repository

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import org.phoenixframework.liveview.foundation.data.service.ChannelService
import org.phoenixframework.liveview.foundation.data.service.SocketService

class Repository(
    private val socketService: SocketService,
    private val channelService: ChannelService,
) {
    val liveSocketConnectionFlow = socketService.connectionFlow
    val liveReloadSocketConnectionFlow = socketService.liveReloadConnectionFlow

    private var _httpBaseUrl: String = ""
    private var _wsBaseUrl: String = ""

    suspend fun connectToLiveViewSocket(
        httpBaseUrl: String,
        method: String?,
        params: Map<String, Any?>,
        wsBaseUrl: String,
    ) {
        _httpBaseUrl = httpBaseUrl
        _wsBaseUrl = wsBaseUrl
        Log.i(TAG, "connectToLiveViewSocket")
        Log.i(TAG, "\t>>>httpBaseUrl=$httpBaseUrl | method=$method | params=$params")
        Log.i(TAG, "\t>>>wsBaseUrl=$wsBaseUrl")

        socketService.connectToLiveViewSocket(
            httpUrl = httpBaseUrl,
            method = method,
            params = params,
            socketBaseUrl = wsBaseUrl
        )
    }

    fun disconnectFromLiveViewSocket(resetPayload: Boolean = false) {
        Log.i(TAG, "disconnectFromLiveViewSocket")
        Log.i(TAG, "\t>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl")
        socketService.disconnectFromLiveViewSocket(resetPayload)
    }

    fun joinLiveViewChannel(
        httpBaseUrl: String,
        redirect: Boolean,
    ) = callbackFlow {
        Log.i(TAG, "joinLiveViewChannel")
        Log.i(TAG, "\t>>>>httpBaseUrl=$httpBaseUrl")
        channelService.joinPhoenixChannel(
            httpBaseUrl,
            redirect
        ) { message ->
            trySend(message)
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
        Log.i(TAG, "leaveChannel>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl")
        channelService.leaveChannel()
    }

    fun connectToReloadSocket(wsBaseUrl: String) {
        Log.i(TAG, "connectToReloadSocket>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl")
        return socketService.connectLiveReloadSocket(
            socketBaseUrl = wsBaseUrl
        )
    }

    fun disconnectFromReloadSocket() {
        Log.i(
            TAG,
            "disconnectFromReloadSocket>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl"
        )
        socketService.disconnectReloadSocket()
    }

    fun joinReloadChannel() = callbackFlow {
        Log.i(TAG, "joinReloadChannel>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl")
        channelService.joinLiveReloadChannel {
            trySend(Unit)
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
        Log.i(TAG, "leaveReloadChannel>>>>httpBaseUrl=$_httpBaseUrl | wsBaseUrl=$_wsBaseUrl")
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

    suspend fun loadThemeData(httpBaseUrl: String): Map<String, Any> {
        return socketService.loadThemeData(httpBaseUrl)
    }

    suspend fun loadStyleData(httpBaseUrl: String): String? {
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
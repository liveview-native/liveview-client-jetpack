package org.phoenixframework.liveview.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.data.service.ChannelService
import org.phoenixframework.liveview.data.service.SocketService

class Repository(
    private val httpBaseUrl: String,
    private val wsBaseUrl: String,
) {
    private val socketService = SocketService
    val isSocketConnected: Boolean
        get() = socketService.isConnected

    private val channelService: ChannelService = ChannelService(socketService)
    val isJoined: Boolean
        get() = channelService.isJoined

    private var payload: PhoenixLiveViewPayload? = null

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    suspend fun connectToLiveViewSocket() {
        payload = getInitialPayload(httpBaseUrl)?.also {
            socketService.connectToLiveView(
                phxLiveViewPayload = it,
                socketBaseUrl = wsBaseUrl,
            )
        }
    }

    fun disconnectFromLiveViewSocket() {
        socketService.disconnectFromLiveView()
    }

    fun joinChannel(redirect: Boolean) = callbackFlow {
        if (payload == null) {
            payload = getInitialPayload(httpBaseUrl)
        }
        payload?.let {
            Log.i(TAG, "Joining channel...")
            channelService.joinPhoenixChannel(
                it,
                httpBaseUrl,
                redirect // It's always false. We're redirecting using Compose Navigation
            ) { message ->
                trySend(message)
            }
        }
        awaitClose {
            try {
                Log.i(TAG, "Closing channel...")
                channel.close()
            } catch (e: Exception) {
                Log.e(TAG, "Error awaiting for close: ${e.message}", e)
            }
        }
    }.catch {
        Log.e(TAG, "Error in flow: ${it.message}")
    }

    fun leaveChannel() {
        channelService.leaveChannel()
    }

    private suspend fun getInitialPayload(url: String): PhoenixLiveViewPayload? =
        withContext(Dispatchers.IO) {
            try {
                val doc: Document? = socketService.newHttpCall(url)
                    .execute()
                    .body?.string()
                    ?.let { Jsoup.parse(it) }

                val metaTags: Elements? = doc?.getElementsByTag(TAG_META)
                val metaTagWithCsrfToken = metaTags?.find { theElement ->
                    theElement.attr(ATTR_NAME) == VALUE_ATTR_CSRF_TOKEN
                }
                val csrfToken = metaTagWithCsrfToken?.attr(ATTR_CONTENT)

                val theLiveViewMetaDataElement =
                    doc?.body()?.getElementsByAttribute(ATTR_DATA_PHX_MAIN)

                PhoenixLiveViewPayload(
                    dataPhxSession = theLiveViewMetaDataElement?.attr(ATTR_DATA_PHX_SESSION),
                    dataPhxStatic = theLiveViewMetaDataElement?.attr(ATTR_DATA_PHX_STATIC),
                    phxId = theLiveViewMetaDataElement?.attr(ATTR_ID),
                    _csrfToken = csrfToken
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
                null
            }
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
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("$httpBaseUrl/assets/android_style.json")
                    .build()
                val result = okHttpClient.newCall(request).execute().body?.string()
                JsonParser.parse(result ?: "") ?: emptyMap()

            } catch (e: Exception) {
                emptyMap()
            }
        }
    }

    companion object {
        private const val TAG = "Repository"

        // Initial payload constants
        private const val TAG_META = "meta"
        private const val ATTR_NAME = "name"
        private const val ATTR_CONTENT = "content"
        private const val ATTR_DATA_PHX_MAIN = "data-phx-main"
        private const val ATTR_DATA_PHX_SESSION = "data-phx-session"
        private const val ATTR_DATA_PHX_STATIC = "data-phx-static"
        private const val ATTR_ID = "id"
        private const val VALUE_ATTR_CSRF_TOKEN = "csrf-token"

        // Push event constants
        private const val PUSH_TYPE_EVENT = "event"
        private const val EVENT_KEY_TYPE = "type"
        private const val EVENT_KEY_EVENT = "event"
        private const val EVENT_KEY_VALUE = "value"
        private const val EVENT_KEY_CID = "cid"
    }
}
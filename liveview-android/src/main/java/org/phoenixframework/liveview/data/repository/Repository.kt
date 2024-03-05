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

    private var channelService: ChannelService? = null
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

    fun joinChannel(redirect: Boolean) = callbackFlow {
        if (payload == null) {
            payload = getInitialPayload(httpBaseUrl)
        }
        payload?.let {
            channelService = ChannelService(socketService).apply {
                joinPhoenixChannel(
                    it, httpBaseUrl, redirect
                ) { message ->
                    trySend(message)
                }
            }
        }
        try {
            awaitClose {
                Log.i(TAG, "Closing channel...")
                channel.close()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error awaiting for close: ${e.message}", e)
        }
    }.catch {
        Log.e(TAG, "Error in flow: ${it.message}")
    }

    fun closeChannel() {
        channelService?.closeChannel()
    }

    private suspend fun getInitialPayload(url: String): PhoenixLiveViewPayload? =
        withContext(Dispatchers.IO) {
            try {
                val doc: Document? = socketService.newHttpCall(url)
                    .execute()
                    .body?.string()
                    ?.let { Jsoup.parse(it) }

                val theLiveViewMetaDataElement =
                    doc?.body()?.getElementsByAttribute("data-phx-main")

                val metaElements: Elements? = doc?.getElementsByTag("meta")
                val filteredElements = metaElements?.filter { theElement ->
                    theElement.attr("name") == "csrf-token"
                }
                val csrfToken = filteredElements?.first()?.attr("content")

                PhoenixLiveViewPayload(
                    dataPhxSession = theLiveViewMetaDataElement?.attr("data-phx-session"),
                    dataPhxStatic = theLiveViewMetaDataElement?.attr("data-phx-static"),
                    phxId = theLiveViewMetaDataElement?.attr("id"),
                    _csrfToken = csrfToken
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
                null
            }
        }

    fun pushEvent(type: String, event: String, value: Any?, target: Int? = null) {
        Log.d(TAG, "pushEvent: [type: $type | event: $event | value: $value | target: $target]")
        channelService?.pushEvent(
            "event", mapOf(
                "type" to type,
                "event" to event,
                "value" to (value ?: emptyMap<String, Any>()),
                "cid" to target as Any?
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
    }
}
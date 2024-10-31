package org.phoenixframework.liveview.foundation.data.service

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.internal.EMPTY_REQUEST
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.phoenixframework.Channel
import org.phoenixframework.Socket
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.data.repository.PhoenixLiveViewPayload
import java.util.UUID

class SocketService {
    private var phxSocket: Socket? = null
    private var liveReloadSocket: Socket? = null

    private val uuid: String = UUID.randomUUID().toString()

    private val storedCookies = mutableListOf<Cookie>()

    var payload: PhoenixLiveViewPayload? = null
        private set

    private val _connection = MutableStateFlow<Events>(Events.NotConnected)
    val connectionFlow: StateFlow<Events> = _connection

    private val _liveReloadConnection = MutableStateFlow<Events>(Events.NotConnected)
    val liveReloadConnectionFlow: StateFlow<Events> = _liveReloadConnection

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    storedCookies.addAll(cookies)
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> = storedCookies
            })
            .addNetworkInterceptor { chain ->
                // Intercepting redirection and add _format parameter to the URL
                val request = chain.request()
                if (!request.url.queryParameterNames.contains(SOCKET_PARAM_FORMAT)) {
                    val newUrl = request.url.newBuilder()
                        .addQueryParameter(SOCKET_PARAM_FORMAT, FORMAT_JETPACK)
                        .build()
                    val newRequest = request.newBuilder()
                        .url(newUrl)
                        .build()
                    chain.proceed(newRequest)
                } else {
                    chain.proceed(request)
                }
            }
            .addInterceptor { chain ->
                val original = chain.request()
                val authorized = original.newBuilder().build()
                chain.proceed(authorized)
            }
            .build()
    }

    fun connectToLiveViewSocket(socketBaseUrl: String) {
        Log.d(TAG, "connectToLiveViewSocket::socketBaseUrl=> $socketBaseUrl")

        if (payload == null || payload?.phxCSRFToken == null) {
            _connection.update {
                Events.PayloadLoadingError(IllegalStateException("Initial payload is null"))
            }
            return
        }
        Log.d(TAG, "connectToLiveViewSocket::phxLiveViewPayload=> $payload")

        val socketParams = mapOf(
            SOCKET_PARAM_MOUNTS to 0,
            SOCKET_PARAM_FORMAT to FORMAT_JETPACK,
            SOCKET_PARAM_CSRF_TOKEN to payload?.phxCSRFToken,
            SOCKET_PARAM_CLIENT_ID to uuid,
        )

        val uriBuilder = Uri.parse(socketBaseUrl).buildUpon().apply {
            socketParams.entries.forEach { entry ->
                appendQueryParameter(entry.key, entry.value.toString())
            }
        }
        val socketUrl = uriBuilder.build().toString()
        Log.d(TAG, "connectToLiveViewSocket::socketUrl=$socketUrl")
        phxSocket = Socket(url = socketUrl, client = okHttpClient).apply {
            logger = { Log.d(TAG, it) }
            onOpen {
                _connection.update { Events.Opened }
            }
            onClose {
                _connection.update { Events.Closed }
            }
            onError { t, _ ->
                _connection.update { Events.Error(t) }
            }
            connect()
        }
    }

    fun disconnectFromLiveViewSocket() {
        Log.d(TAG, "disconnectFromLiveViewSocket")
        phxSocket?.disconnect()
    }

    fun createChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseHttpUrl: String,
        redirect: Boolean,
    ): Channel? {
        val channelConnectionParams = mapOf(
            CHANNEL_PARAM_SESSION to phxLiveViewPayload.phxSession,
            CHANNEL_PARAM_STATIC to phxLiveViewPayload.phxStatic,
            (if (redirect) CHANNEL_PARAM_REDIRECT else CHANNEL_PARAM_URL) to baseHttpUrl,
            CHANNEL_PARAM_SOCKET_PARAMS to
                    mapOf(
                        SOCKET_PARAM_CSRF_TOKEN to phxLiveViewPayload.phxCSRFToken,
                        SOCKET_PARAM_MOUNTS to 0,
                        SOCKET_PARAM_CLIENT_ID to uuid,
                        SOCKET_PARAM_FORMAT to FORMAT_JETPACK,
                    ),
        )
        return phxSocket?.channel(
            topic = "lv:${phxLiveViewPayload.phxId}",
            params = channelConnectionParams
        )
    }

    private fun newHttpCall(
        url: String,
        method: String?,
        params: Map<String, Any?>,
    ): Call {
        fun formBodyRequest(params: Map<String, Any?>): RequestBody {
            return if (params.isEmpty()) EMPTY_REQUEST else {
                FormBody.Builder().apply {
                    params.forEach { entry ->
                        addEncoded(entry.key, entry.value.toString())
                    }
                    // TODO Check if this is really necessary
                    payload?.phxCSRFToken?.let {
                        addEncoded(SOCKET_PARAM_CSRF_TOKEN, it)
                    }
                }.build()
            }
        }

        val request = Request.Builder().url(url).apply {

            when ((method ?: "GET").uppercase()) {
                "HEAD" -> head()
                "DELETE" -> delete(formBodyRequest(params))
                "PATCH" -> patch(formBodyRequest(params))
                "POST" -> post(formBodyRequest(params))
                "PUT" -> put(formBodyRequest(params))
                else -> {
                    if (params.isNotEmpty()) {
                        val uri = Uri.parse(url).buildUpon().apply {
                            params.forEach { entry ->
                                appendQueryParameter(entry.key, entry.value.toString())
                            }
                        }
                        url(uri.toString())
                    }
                    get()
                }
            }
        }.build()
        return okHttpClient.newCall(request)
    }

    fun connectLiveReloadSocket(socketBaseUrl: String) {
        if (liveReloadSocket?.isConnected == true) {
            return
        }
        Log.d(TAG, "connectLiveReloadSocket::wsBaseUrl=$socketBaseUrl")
        val uri = Uri.parse(socketBaseUrl)
            .buildUpon()
            .path("/phoenix/live_reload/socket/websocket")
            .build()
        Log.d(TAG, "connectLiveReloadSocket::uri=$uri")

        liveReloadSocket = Socket(url = uri.toString(), client = okHttpClient).apply {
            logger = { Log.d(TAG, it) }
            onOpen {
                _liveReloadConnection.update { Events.Opened }
            }
            onClose {
                _liveReloadConnection.update { Events.Closed }
            }
            onError { t, _ ->
                _liveReloadConnection.update { Events.Error(t) }
            }
            connect()
        }
    }

    fun disconnectReloadSocket() {
        liveReloadSocket?.disconnect()
    }

    fun createReloadChannel(): Channel? {
        return liveReloadSocket?.channel(topic = "phoenix:live_reload")
    }

    suspend fun loadInitialPayload(
        url: String,
        method: String?,
        params: Map<String, Any?>,
    ) {
        payload = withContext(Dispatchers.IO) {
            try {
                val uri = Uri.parse(url).buildUpon()
                    .appendQueryParameter(SOCKET_PARAM_FORMAT, FORMAT_JETPACK)
                val response = newHttpCall(uri.toString(), method, params).execute()

                val doc: Document? = response.body?.string()?.let {
                    Jsoup.parse(it)
                }

                val csrfTokenTag: Elements? = doc?.getElementsByTag(TAG_CSRF_TOKEN)
                val csrfToken = csrfTokenTag?.attr(ATTR_VALUE)

                val styleTag: Elements? = doc?.getElementsByTag(TAG_STYLE)
                val stylePath = styleTag?.attr(ATTR_URL)

                val theLiveViewMetaDataElement =
                    doc?.body()?.getElementsByAttribute(ATTR_DATA_PHX_MAIN)

                val liveReloadEnabled =
                    doc?.select("iframe[src=\"/phoenix/live_reload/frame\"]")?.isNotEmpty() == true

                PhoenixLiveViewPayload(
                    phxSession = theLiveViewMetaDataElement?.attr(ATTR_DATA_PHX_SESSION),
                    phxStatic = theLiveViewMetaDataElement?.attr(ATTR_DATA_PHX_STATIC),
                    phxId = theLiveViewMetaDataElement?.attr(ATTR_ID),
                    phxCSRFToken = csrfToken,
                    liveReloadEnabled = liveReloadEnabled,
                    stylePath = stylePath
                )
            } catch (e: Exception) {
                Log.e(TAG, "SocketService::Error: ${e.message}", e)
                null
            }
        }
    }

    fun resetPayload() {
        payload = null
    }

    suspend fun loadThemeData(httpBaseUrl: String): Map<String, Any> {
        return withContext(Dispatchers.IO) {
            try {
                val uri = Uri.parse(httpBaseUrl).buildUpon()
                    .path("/assets/android_style.json")
                val request = Request.Builder()
                    .url(uri.toString())
                    .build()
                val result = okHttpClient.newCall(request).execute().body?.string()
                JsonParser.parse(result ?: "") ?: emptyMap()

            } catch (e: Exception) {
                Log.d(TAG, "loadThemeData", e)
                emptyMap()
            }
        }
    }

    suspend fun loadStyleData(httpBaseUrl: String, stylePath: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val uri = Uri.parse(httpBaseUrl)
                    .buildUpon()
                    .path(stylePath)
                    .build()
                val request = Request.Builder()
                    .url(uri.toString())
                    .build()
                val styleContent = okHttpClient.newCall(request)
                    .execute()
                    .body
                    ?.string()
                // FIXME Remove the call below when the Range is supported
                styleContent?.replace("...", "..")
            } catch (e: Exception) {
                Log.d(TAG, "loadStyleData", e)
                null
            }
        }
    }

    companion object {
        private const val TAG = "LVN_SocketService"

        // Initial payload constants
        private const val ATTR_DATA_PHX_MAIN = "data-phx-main"
        private const val ATTR_DATA_PHX_SESSION = "data-phx-session"
        private const val ATTR_DATA_PHX_STATIC = "data-phx-static"
        private const val ATTR_ID = "id"
        private const val ATTR_VALUE = "value"
        private const val ATTR_URL = "url"

        private const val TAG_CSRF_TOKEN = "csrf-token"
        private const val TAG_STYLE = "style"

        private const val CHANNEL_PARAM_SESSION = "session"
        private const val CHANNEL_PARAM_STATIC = "static"
        private const val CHANNEL_PARAM_SOCKET_PARAMS = "params"
        private const val CHANNEL_PARAM_URL = "url"
        private const val CHANNEL_PARAM_REDIRECT = "redirect"

        private const val SOCKET_PARAM_CSRF_TOKEN = "_csrf_token"
        private const val SOCKET_PARAM_MOUNTS = "_mounts"
        private const val SOCKET_PARAM_CLIENT_ID = "client_id"
        private const val SOCKET_PARAM_FORMAT = "_format"

        private const val FORMAT_JETPACK = "jetpack"
    }

    sealed class Events {
        data object NotConnected : Events()
        data object Opened : Events()
        data object Closed : Events()
        open class Error(val throwable: Throwable) :
            Events()

        class PayloadLoadingError(t: Throwable) : Error(t)
    }
}
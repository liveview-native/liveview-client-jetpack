package org.phoenixframework.liveview.data.service

import android.util.Log
import okhttp3.Call
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.phoenixframework.Channel
import org.phoenixframework.Socket
import org.phoenixframework.liveview.data.repository.PhoenixLiveViewPayload
import java.util.UUID

object SocketService {
    private var phxSocket: Socket? = null

    private val uuid: String = UUID.randomUUID().toString()

    private val storedCookies = mutableListOf<Cookie>()

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            storedCookies.addAll(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> = storedCookies
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                val original = chain.request()
                val authorized = original.newBuilder().build()
                chain.proceed(authorized)
            }
            .build()
    }

    val isConnected: Boolean
        get() = phxSocket?.isConnected == true

    fun connectToLiveView(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        socketBaseUrl: String,
    ) {
        Log.d(TAG, "Connection to socket with params $phxLiveViewPayload")
        setupPhxSocketConnection(phxLiveViewPayload, baseUrl = socketBaseUrl)

        phxSocket?.connect()
    }

    fun disconnectFromLiveView() {
        phxSocket?.disconnect()
    }

    private fun setupPhxSocketConnection(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseUrl: String
    ) {
        val socketParams = mapOf(
            SOCKET_PARAM_CSRF_TOKEN to phxLiveViewPayload._csrfToken,
            SOCKET_PARAM_MOUNTS to 0,
            SOCKET_PARAM_CLIENT_ID to uuid,
            SOCKET_PARAM_PLATFORM to PLATFORM_JETPACK
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

    fun createChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseHttpUrl: String,
        redirect: Boolean,
    ): Channel? {
        val channelConnectionParams = mapOf(
            CHANNEL_PARAM_SESSION to phxLiveViewPayload.dataPhxSession,
            CHANNEL_PARAM_STATIC to phxLiveViewPayload.dataPhxStatic,
            (if (redirect) CHANNEL_PARAM_URL else CHANNEL_PARAM_REDIRECT) to baseHttpUrl,
            CHANNEL_PARAM_SOCKET_PARAMS to
                    mapOf(
                        SOCKET_PARAM_CSRF_TOKEN to phxLiveViewPayload._csrfToken,
                        SOCKET_PARAM_MOUNTS to 0,
                        SOCKET_PARAM_CLIENT_ID to uuid,
                        SOCKET_PARAM_PLATFORM to PLATFORM_JETPACK
                    )
        )
        return phxSocket?.channel(
            topic = "lv:${phxLiveViewPayload.phxId}",
            params = channelConnectionParams
        )
    }

    fun newHttpCall(url: String): Call {
        val request = Request.Builder().url(url).get().build()
        return okHttpClient.newCall(request)
    }

    private const val TAG = "SocketService"

    private const val CHANNEL_PARAM_SESSION = "session"
    private const val CHANNEL_PARAM_STATIC = "static"
    private const val CHANNEL_PARAM_SOCKET_PARAMS = "params"
    private const val CHANNEL_PARAM_URL = "url"
    private const val CHANNEL_PARAM_REDIRECT = "redirect"

    private const val SOCKET_PARAM_CSRF_TOKEN = "_csrf_token"
    private const val SOCKET_PARAM_MOUNTS = "_mounts"
    private const val SOCKET_PARAM_CLIENT_ID = "client_id"
    private const val SOCKET_PARAM_PLATFORM = "_platform"

    private const val PLATFORM_JETPACK = "jetpack"
}
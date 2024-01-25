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
                chain.proceed(authorized);
            }
            .build()
    }

    val isConnected: Boolean
        get() = phxSocket?.isConnected == true

    fun connectToLiveView(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        socketBaseUrl: String,
    ) {
        phxSocket?.disconnect()
        Log.d(TAG, "Connection to socket with params $phxLiveViewPayload")
        setupPhxSocketConnection(phxLiveViewPayload, baseUrl = socketBaseUrl)

        phxSocket?.connect()
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

    fun createChannel(
        phxLiveViewPayload: PhoenixLiveViewPayload,
        baseHttpUrl: String,
        redirect: Boolean,
    ): Channel? {
        val channelConnectionParams = mapOf(
            "session" to phxLiveViewPayload.dataPhxSession,
            "static" to phxLiveViewPayload.dataPhxStatic,
            (if (redirect) "url" else "redirect") to baseHttpUrl,
            "params" to
                    mapOf(
                        "_csrf_token" to phxLiveViewPayload._csrfToken,
                        "_mounts" to 0,
                        "client_id" to uuid,
                        "_platform" to "jetpack"
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
}
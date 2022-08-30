package org.phoenixframework.liveview.managers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.jsoup.nodes.Document
import org.phoenixframework.liveview.mappers.SocketPayloadMapper

object LiveViewState {
    var socketManager: SocketManager
        private set
    private var domFetchManager: DomFetchManager
    private var storedCookies: MutableList<Cookie> = mutableListOf()
    private val socketPayloadMapper: SocketPayloadMapper = SocketPayloadMapper()
    val documentState = mutableStateOf<Document?>(null)
    var baseUrl: String? = "http://10.0.2.2:8080"
    var baseSocketUrl: String? = "ws://10.0.2.2:8080"

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            storedCookies = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = storedCookies
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addInterceptor { chain ->
            val original = chain.request();
            val authorized = original.newBuilder()
                .build();

            chain.proceed(authorized);
        }
        .build()

    init {
        socketManager = SocketManager(
            okHttpClient = okHttpClient,
            socketPayloadMapper = socketPayloadMapper
        )
        domFetchManager = DomFetchManager(
            okHttpClient = okHttpClient
        )

        socketManager.domParsedListener = { document: Document ->
            documentState.value = document
        }

        socketManager.liveReloadListener = {
            baseUrl?.let {
                launchLiveView(it)
            }
        }

        baseUrl?.let {
            launchLiveView(it)
        }
    }

    private fun launchLiveView(url: String) {
        GlobalScope.async {
            val phxPayloadMap = domFetchManager.getWebsite(url = url)
            socketManager.connectToChatRoomWithParams(phxLiveViewPayload = phxPayloadMap)
        }
    }
}
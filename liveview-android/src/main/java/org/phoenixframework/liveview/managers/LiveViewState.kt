package org.phoenixframework.liveview.managers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.phoenixframework.liveview.data.dto.ComposableTreeNode
import org.phoenixframework.liveview.mappers.SocketPayloadMapper

object LiveViewState {
    var socketManager: SocketManager
        private set
    private var domFetchManager: DomFetchManager
    private var storedCookies: MutableList<Cookie> = mutableListOf()
    private val socketPayloadMapper: SocketPayloadMapper = SocketPayloadMapper()

    private val _slotTable = MutableStateFlow<MutableList<ComposableTreeNode>>(mutableListOf())
    val slotTable = _slotTable.asStateFlow()

    var baseUrl: String? = "http://10.0.2.2:8080"
    var baseSocketUrl: String? = "ws://10.0.2.2:8080"

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            storedCookies = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = storedCookies
    }

    private val okHttpClient = OkHttpClient.Builder().cookieJar(cookieJar).addInterceptor { chain ->
            val original = chain.request();
            val authorized = original.newBuilder().build();

            chain.proceed(authorized);
        }.build()

    init {
        socketManager = SocketManager(
            okHttpClient = okHttpClient, socketPayloadMapper = socketPayloadMapper
        )
        domFetchManager = DomFetchManager(
            okHttpClient = okHttpClient
        )

        socketManager.domParsedListener = {}

        socketManager.liveReloadListener = {
            baseUrl?.let {
                launchLiveView(it)
            }
        }

        baseUrl?.let {
            launchLiveView(it)
        }

        socketManager.nodeListener = { composableNode ->
            if (!_slotTable.value.contains(composableNode)){
                _slotTable.value.add(composableNode)

                _slotTable.update {
                    _slotTable.value
                }
            }

        }

        socketManager.parseTemplate()
    }

    private fun launchLiveView(url: String) {
        GlobalScope.async {
            val phxPayloadMap = domFetchManager.getWebsite(url = url)
            socketManager.connectToChatRoomWithParams(phxLiveViewPayload = phxPayloadMap)
        }
    }
}
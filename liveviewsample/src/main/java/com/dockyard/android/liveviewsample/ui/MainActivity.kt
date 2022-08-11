package com.dockyard.android.liveviewsample.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.jsoup.nodes.Document
import org.phoenixframework.liveview.managers.DomFetchManager
import org.phoenixframework.liveview.managers.SocketManager
import org.phoenixframework.liveview.mappers.SocketPayloadMapper
import org.phoenixframework.liveview.ui.phx_components.PhxAction
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.LiveViewTestTheme

class MainActivity : ComponentActivity() {

    private lateinit var socketManager: SocketManager
    private lateinit var domFetchManager: DomFetchManager
    private var storedCookies: MutableList<Cookie> = mutableListOf()

    private val socketPayloadMapper: SocketPayloadMapper = SocketPayloadMapper()

    private val documentState = mutableStateOf<Document?>(null)

    private val cookieJar = object : CookieJar {

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            storedCookies = cookies.toMutableList()
            Log.d("TAG", cookies.toString())
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            return storedCookies
        }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        socketManager = SocketManager(
            okHttpClient = okHttpClient,
            socketPayloadMapper = socketPayloadMapper
        )
        domFetchManager = DomFetchManager(
            okHttpClient = okHttpClient
        )

        launchLiveView()

        socketManager.domParsedListener = { document: Document ->

            documentState.value = document
            Log.d("PARSED ON MAIN THREAD", "--------  SUCCESS  --------")
        }

        socketManager.liveReloadListener = {
            launchLiveView()
        }

        setContent {
            LiveViewTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
//                    color = MaterialTheme.colors.background
                ) {

                        PhxLiveView(
                            documentState = documentState,
                            phxActionListener = { phxAction: PhxAction ->
                                socketManager.pushChannelMessage(
                                    phxAction = phxAction
                                )
                            }
                        )
                }
            }
        }
    }

    private fun launchLiveView() {
        GlobalScope.async {
            val phxPayloadMap = domFetchManager.getWebsite()
            socketManager.connectToChatRoomWithParams(phxLiveViewPayload = phxPayloadMap)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LiveViewTestTheme {
        Greeting("Android")
    }
}
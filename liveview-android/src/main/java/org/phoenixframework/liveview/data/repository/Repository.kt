package org.phoenixframework.liveview.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.service.SocketService
import org.phoenixframework.liveview.data.managers.PhoenixLiveViewPayload

class Repository(private val baseUrl: String) {

    private var phxLiveViewPayload: PhoenixLiveViewPayload = PhoenixLiveViewPayload()

    private var storedCookies: MutableList<Cookie> = mutableListOf()
    private val cookieJar = object : CookieJar {

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            storedCookies = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            return storedCookies
        }

    }

    private val okHttpClient = OkHttpClient.Builder().cookieJar(cookieJar).addInterceptor { chain ->
        val original = chain.request();
        val authorized = original.newBuilder().build();

        chain.proceed(authorized);
    }.build()

    private val socketService = SocketService(okHttpClient = okHttpClient)




    suspend fun connect()= callbackFlow {

        socketService.connectToLiveView(
            phxLiveViewPayload = getInitialPayload(baseUrl),
            baseUrl = baseUrl,
            socketBase = "ws://10.0.2.2:4000/"
        ) { message ->
           trySend(message)

        }

        try {
            awaitClose {
                channel.close()
            }
        }catch (e: Exception){
            Log.e("Repository", "Error: ${e.message}")
        }

    }

    private suspend fun getInitialPayload(url: String): PhoenixLiveViewPayload {
        withContext(Dispatchers.IO) {
            try {

                val request = Request.Builder().url(url).get().build()
                val doc: Document? =
                    okHttpClient.newCall(request).execute().body?.string()?.let { Jsoup.parse(it) }

                val theLiveViewMetaDataElement =
                    doc!!.body().getElementsByAttributeValue("data-phx-main", "true")
                phxLiveViewPayload.phxId = theLiveViewMetaDataElement.attr("id")
                phxLiveViewPayload.dataPhxStatic =
                    theLiveViewMetaDataElement.attr("data-phx-static")
                phxLiveViewPayload.dataPhxSession =
                    theLiveViewMetaDataElement.attr("data-phx-session")

                val metaElements: Elements = doc.getElementsByTag("meta")

                val filteredElements = metaElements.filter { theElement ->
                    theElement.attr("name") == "csrf-token"
                }

                val csrfToken = filteredElements.first().attr("content")

                phxLiveViewPayload._csrfToken = csrfToken

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return phxLiveViewPayload
    }


}
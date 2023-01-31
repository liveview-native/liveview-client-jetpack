package org.phoenixframework.liveview.data.managers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DomFetchManager(
    private val okHttpClient: OkHttpClient
) {

    private val phxLiveViewPayload: PhoenixLiveViewPayload = PhoenixLiveViewPayload()

    suspend fun getWebsite(url: String): PhoenixLiveViewPayload {
        withContext(Dispatchers.IO) {
            try {

                val request = Request.Builder().url(url).get().build()
                val doc: Document? = okHttpClient.newCall(request).execute().body?.string()?.let { Jsoup.parse(it) }

                val theLiveViewMetaDataElement = doc!!.body().getElementsByAttributeValue("data-phx-main", "true")
                phxLiveViewPayload.phxId = theLiveViewMetaDataElement.attr("id")
                phxLiveViewPayload.dataPhxStatic = theLiveViewMetaDataElement.attr("data-phx-static")
                phxLiveViewPayload.dataPhxSession = theLiveViewMetaDataElement.attr("data-phx-session")

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

data class PhoenixLiveViewPayload(
    var dataPhxSession: String? = null,
    var dataPhxStatic: String? = null,
    var phxId: String? = null,
    var _csrfToken: String? = null
)
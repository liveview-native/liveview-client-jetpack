package org.phoenixframework.liveview.data.service

import okhttp3.*
import org.jsoup.Jsoup
import org.phoenixframework.liveview.data.managers.PhoenixLiveViewPayload

object DomFetchService {

    private val client = OkHttpClient()

    private val phxLiveViewPayload: PhoenixLiveViewPayload = PhoenixLiveViewPayload()


    suspend fun doInitialFetch(url: String = "http://10.0.2.2:4000/"): PhoenixLiveViewPayload {

        val request = Request.Builder()
            .url(url)
            .build()


        client.newCall(request).execute().body?.string()?.let { response ->
            Jsoup.parse(response)
        }?.let { htmlDocument ->
            val liveViewMetaDataElements =
                htmlDocument.body().getElementsByAttributeValue("data-phx-main", "true")

            phxLiveViewPayload.phxId = liveViewMetaDataElements.attr("id")
            phxLiveViewPayload.dataPhxStatic = liveViewMetaDataElements.attr("data-phx-static")
            phxLiveViewPayload.dataPhxSession = liveViewMetaDataElements.attr("data-phx-session")

            val filteredMetaElements = htmlDocument.getElementsByTag("meta").filter { theElement ->
                theElement.attr("name") == "csrf-token"
            }

            val csrfToken = filteredMetaElements.first().attr("content")

            phxLiveViewPayload._csrfToken = csrfToken

        }

        return phxLiveViewPayload

    }
}


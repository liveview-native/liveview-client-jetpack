package org.phoenixframework.liveview.data.dto

data class PhoenixLiveViewPayload(
    val dataPhxSession: String? = null,
    val dataPhxStatic: String? = null,
    val phxId: String? = null,
    val _csrfToken: String? = null
)
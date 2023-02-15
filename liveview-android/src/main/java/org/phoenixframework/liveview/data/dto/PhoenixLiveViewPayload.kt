package org.phoenixframework.liveview.data.dto

data class PhoenixLiveViewPayload(
    var dataPhxSession: String? = null,
    var dataPhxStatic: String? = null,
    var phxId: String? = null,
    var _csrfToken: String? = null
)
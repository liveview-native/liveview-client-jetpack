package org.phoenixframework.liveview.data.repository

data class PhoenixLiveViewPayload(
    val phxSession: String? = null,
    val phxStatic: String? = null,
    val phxId: String? = null,
    val phxCSRFToken: String? = null,
    val liveReloadEnabled: Boolean = false
)
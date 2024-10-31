package org.phoenixframework.liveview.foundation.data.repository

data class PhoenixLiveViewPayload(
    val phxSession: String? = null,
    val phxStatic: String? = null,
    val phxId: String? = null,
    val phxCSRFToken: String? = null, // CSRF (Cross Site Request Forgery) token
    val liveReloadEnabled: Boolean = false,
    val stylePath: String? = null,
)
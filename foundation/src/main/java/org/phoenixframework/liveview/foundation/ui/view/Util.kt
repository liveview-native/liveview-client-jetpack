package org.phoenixframework.liveview.foundation.ui.view

import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

fun onClickFromString(
    pushEvent: PushEvent?,
    event: String,
    value: Any?,
    target: Int? = null
): () -> Unit = {
    if (event.isNotEmpty()) {
        pushEvent?.invoke(ComposableView.EVENT_TYPE_CLICK, event, value, target)
    }
}
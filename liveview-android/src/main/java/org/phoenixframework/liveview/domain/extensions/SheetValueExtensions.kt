package org.phoenixframework.liveview.domain.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.PushEvent


@OptIn(ExperimentalMaterial3Api::class)
fun SheetValue.pushNewValue(pushEvent: PushEvent, onChangedEvent: String) {
    when (this) {
        SheetValue.Expanded ->
            pushEvent(ComposableBuilder.EVENT_TYPE_CHANGE, onChangedEvent, "expanded", null)

        SheetValue.PartiallyExpanded ->
            pushEvent(
                ComposableBuilder.EVENT_TYPE_CHANGE,
                onChangedEvent,
                "partiallyExpanded",
                null
            )

        SheetValue.Hidden ->
            pushEvent(ComposableBuilder.EVENT_TYPE_CHANGE, onChangedEvent, "hidden", null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun String.toSheetValue(): SheetValue? {
    return when (this) {
        "expanded" -> SheetValue.Expanded
        "partiallyExpanded" -> SheetValue.PartiallyExpanded
        "hidden" -> SheetValue.Hidden
        else -> null
    }
}
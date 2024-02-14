package org.phoenixframework.liveview.domain.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import org.phoenixframework.liveview.data.constants.SheetValues
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.PushEvent


@OptIn(ExperimentalMaterial3Api::class)
fun SheetValue.pushNewValue(pushEvent: PushEvent, onChangedEvent: String) {
    when (this) {
        SheetValue.Expanded -> pushEvent(
            ComposableBuilder.EVENT_TYPE_CHANGE, onChangedEvent, SheetValues.expanded, null,
        )

        SheetValue.PartiallyExpanded -> pushEvent(
            ComposableBuilder.EVENT_TYPE_CHANGE,
            onChangedEvent,
            SheetValues.partiallyExpanded,
            null,
        )

        SheetValue.Hidden -> pushEvent(
            ComposableBuilder.EVENT_TYPE_CHANGE,
            onChangedEvent,
            SheetValues.hidden,
            null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun String.toSheetValue(): SheetValue? {
    return when (this) {
        SheetValues.expanded -> SheetValue.Expanded
        SheetValues.partiallyExpanded -> SheetValue.PartiallyExpanded
        SheetValues.hidden -> SheetValue.Hidden
        else -> null
    }
}
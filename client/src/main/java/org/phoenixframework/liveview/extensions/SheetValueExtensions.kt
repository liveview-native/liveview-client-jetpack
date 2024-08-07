package org.phoenixframework.liveview.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import org.phoenixframework.liveview.constants.SheetValues

internal const val SHEET_VALUE_KEY = "sheetValue"

@OptIn(ExperimentalMaterial3Api::class)
fun SheetValue.toValue(): String {
    return when (this) {
        SheetValue.Expanded -> SheetValues.expanded

        SheetValue.PartiallyExpanded -> SheetValues.partiallyExpanded

        SheetValue.Hidden -> SheetValues.hidden
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
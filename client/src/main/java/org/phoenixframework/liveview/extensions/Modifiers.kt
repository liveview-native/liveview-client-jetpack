package org.phoenixframework.liveview.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

fun Modifier.optional(
    condition: Boolean, modifier: Modifier
) = this.then(if (condition) modifier else Modifier)

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }
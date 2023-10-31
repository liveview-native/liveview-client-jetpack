package org.phoenixframework.liveview.domain.extensions

import androidx.compose.ui.Modifier

fun Modifier.optional(
    condition: Boolean, modifier: Modifier
) = this.then(if (condition) modifier else Modifier)
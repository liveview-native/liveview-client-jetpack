package org.phoenixframework.liveview.domain.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()
fun String.toColor(): Color = Color(this.toLong())
package org.phoenixframework.liveview.extensions

import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()
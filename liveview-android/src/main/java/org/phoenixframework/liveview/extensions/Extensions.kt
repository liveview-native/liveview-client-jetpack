package org.phoenixframework.liveview.extensions

import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean {
    return this.isNotEmpty() && this.isDigitsOnly()
}
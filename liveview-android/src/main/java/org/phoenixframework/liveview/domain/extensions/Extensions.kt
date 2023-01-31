package org.phoenixframework.liveview.domain.extensions

import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()
package org.phoenixframework.liveview.domain.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()

internal fun String.toColor(): Color {
    val hexColorString = this
        .removePrefix("0x")
        .removePrefix("#")
    val colorLong = hexColorString.toLong(16)
    return Color(colorLong)
}

// TODO This extension is using reflexion to get default values for different component's properties
internal fun <T> Any.privateField(name: String): T {
    val field = this::class.java.getDeclaredField(name).apply {
        isAccessible = true
    }
    @Suppress("UNCHECKED_CAST")
    return field.get(this) as T
}

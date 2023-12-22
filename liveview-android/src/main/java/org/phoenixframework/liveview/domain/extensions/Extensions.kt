package org.phoenixframework.liveview.domain.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()

internal fun String.toColor(): Color {
    return when (this) {
        "system-red" -> Color.Red
        "system-green" -> Color.Green
        "system-blue" -> Color.Blue
        "system-black" -> Color.Black
        "system-white" -> Color.White
        "system-yellow" -> Color.Yellow
        "system-magenta" -> Color.Magenta
        "system-gray" -> Color.Gray
        "system-light-gray" -> Color.LightGray
        "system-dark-gray" -> Color.DarkGray
        "system-transparent" -> Color.Transparent
        "system-cyan" -> Color.Cyan
        else -> {
            var hexColorString = this
                .removePrefix("0x")
                .removePrefix("#")
            // We're going to allow passing in RRGGBB or AARRGGBB
            if (hexColorString.length == 6)
                hexColorString = "FF$hexColorString"
            try {
                Color(hexColorString.toLong(16))
            } catch (e: Exception) {
                Color.Unspecified
            }
        }
    }

}

// TODO This extension is using reflexion to get default values for different component's properties
internal fun <T> Any.privateField(name: String): T {
    val field = this::class.java.getDeclaredField(name).apply {
        isAccessible = true
    }
    @Suppress("UNCHECKED_CAST")
    return field.get(this) as T
}

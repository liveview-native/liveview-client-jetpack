package org.phoenixframework.liveview.domain.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly
import org.phoenixframework.liveview.data.constants.SystemColorValues

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()

internal fun String.toColor(): Color {
    return when (this) {
        SystemColorValues.Red -> Color.Red
        SystemColorValues.Green -> Color.Green
        SystemColorValues.Blue -> Color.Blue
        SystemColorValues.Black -> Color.Black
        SystemColorValues.White -> Color.White
        SystemColorValues.Yellow -> Color.Yellow
        SystemColorValues.Magenta -> Color.Magenta
        SystemColorValues.Gray -> Color.Gray
        SystemColorValues.LightGray -> Color.LightGray
        SystemColorValues.DarkGray -> Color.DarkGray
        SystemColorValues.Transparent -> Color.Transparent
        SystemColorValues.Cyan -> Color.Cyan
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

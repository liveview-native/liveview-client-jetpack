package org.phoenixframework.liveview.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.text.isDigitsOnly
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.constants.SystemColorValues

fun String.isNotEmptyAndIsDigitsOnly(): Boolean = this.isNotEmpty() && this.isDigitsOnly()

fun systemColorFromString(string: String): Color? {
    return when (string) {
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
        else -> null
    }
}

fun colorFromHex(string: String): Color? {
    var hexColorString = string
        .removePrefix("0x")
        .removePrefix("#")
    // We're going to allow passing in RRGGBB or AARRGGBB
    if (hexColorString.length == 6)
        hexColorString = "FF$hexColorString"
    return try {
        Color(hexColorString.toLong(16))
    } catch (e: Exception) {
        null
    }
}

fun String.toColor(): Color {
    return systemColorFromString(this)
        ?: LiveViewJetpack.getThemeHolder().themeColorFromString(this)
        ?: colorFromHex(this)
        ?: Color.Unspecified
}
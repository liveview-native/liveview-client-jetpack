package org.phoenixframework.liveview.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun LiveViewNativeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    themeData: Map<String, Any> = emptyMap(),
    content: @Composable () -> Unit
) {
    val colorsData = themeData["colors"] as? Map<String, Any> ?: emptyMap()
    val lightColors = colorsData["lightColors"] as? Map<String, Any> ?: emptyMap()
    val darkColors = colorsData["darkColors"] as? Map<String, Any> ?: emptyMap()
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> colorSchemeFromThemeData(darkColors, true)
        else -> colorSchemeFromThemeData(lightColors, false)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
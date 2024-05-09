package org.phoenixframework.liveview.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.phoenixframework.liveview.domain.ThemeHolder

@Composable
internal fun LiveViewNativeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    themeData: ThemeHolder.ThemeData?,
    content: @Composable () -> Unit
) {
    if (themeData != null) {
        val colorScheme = remember(darkTheme, dynamicColor, themeData) {
            themeData.getColorSchema(dynamicColor, darkTheme)
        }
        MaterialTheme(
            colorScheme = colorScheme,
            typography = themeData.typography,
            shapes = themeData.shapes,
            content = content
        )
    }
    val context = LocalContext.current
    LaunchedEffect(darkTheme, dynamicColor) {
        ThemeHolder.loadDefaultTheme(context, darkTheme)
    }
}
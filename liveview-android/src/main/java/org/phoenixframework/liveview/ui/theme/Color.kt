package org.phoenixframework.liveview.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.extensions.toColor

private val purple80 = Color(0xFFD0BCFF)
private val purpleGrey80 = Color(0xFFCCC2DC)
private val pink80 = Color(0xFFEFB8C8)

private val purple40 = Color(0xFF6650a4)
private val purpleGrey40 = Color(0xFF625b71)
private val pink40 = Color(0xFF7D5260)

internal fun colorSchemeFromThemeData(
    themeData: Map<String, Any>, darkTheme: Boolean
): ColorScheme {
    val defaultColors = if (darkTheme) {
        darkColorScheme(
            primary = purple80, secondary = purpleGrey80, tertiary = pink80
        )
    } else {
        lightColorScheme(
            primary = purple40, secondary = purpleGrey40, tertiary = pink40
        )
    }
    return ColorScheme(
        primary = themeData["primary"]?.toString()?.toColor()
            ?: defaultColors.primary,
        onPrimary = themeData["onPrimary"]?.toString()?.toColor()
            ?: defaultColors.onPrimary,
        primaryContainer = themeData["primaryContainer"]?.toString()?.toColor()
            ?: defaultColors.primaryContainer,
        onPrimaryContainer = themeData["onPrimaryContainer"]?.toString()?.toColor()
            ?: defaultColors.onPrimaryContainer,
        inversePrimary = themeData["inversePrimary"]?.toString()?.toColor()
            ?: defaultColors.inversePrimary,
        secondary = themeData["secondary"]?.toString()?.toColor()
            ?: defaultColors.secondary,
        onSecondary = themeData["onSecondary"]?.toString()?.toColor()
            ?: defaultColors.onSecondary,
        secondaryContainer = themeData["secondaryContainer"]?.toString()?.toColor()
            ?: defaultColors.secondaryContainer,
        onSecondaryContainer = themeData["onSecondaryContainer"]?.toString()?.toColor()
            ?: defaultColors.onSecondaryContainer,
        tertiary = themeData["tertiary"]?.toString()?.toColor()
            ?: defaultColors.tertiary,
        onTertiary = themeData["onTertiary"]?.toString()?.toColor()
            ?: defaultColors.onTertiary,
        tertiaryContainer = themeData["tertiaryContainer"]?.toString()?.toColor()
            ?: defaultColors.tertiaryContainer,
        onTertiaryContainer = themeData["onTertiaryContainer"]?.toString()?.toColor()
            ?: defaultColors.onTertiaryContainer,
        background = themeData["background"]?.toString()?.toColor()
            ?: defaultColors.background,
        onBackground = themeData["onBackground"]?.toString()?.toColor()
            ?: defaultColors.onBackground,
        surface = themeData["surface"]?.toString()?.toColor()
            ?: defaultColors.surface,
        onSurface = themeData["onSurface"]?.toString()?.toColor()
            ?: defaultColors.onSurface,
        surfaceVariant = themeData["surfaceVariant"]?.toString()?.toColor()
            ?: defaultColors.surfaceVariant,
        onSurfaceVariant = themeData["onSurfaceVariant"]?.toString()?.toColor()
            ?: defaultColors.onSurfaceVariant,
        surfaceTint = themeData["surfaceTint"]?.toString()?.toColor()
            ?: defaultColors.surfaceTint,
        inverseSurface = themeData["inverseSurface"]?.toString()?.toColor()
            ?: defaultColors.inverseSurface,
        inverseOnSurface = themeData["inverseOnSurface"]?.toString()?.toColor()
            ?: defaultColors.inverseOnSurface,
        error = themeData["error"]?.toString()?.toColor()
            ?: defaultColors.error,
        onError = themeData["onError"]?.toString()?.toColor()
            ?: defaultColors.onError,
        errorContainer = themeData["errorContainer"]?.toString()?.toColor()
            ?: defaultColors.errorContainer,
        onErrorContainer = themeData["onErrorContainer"]?.toString()?.toColor()
            ?: defaultColors.onErrorContainer,
        outline = themeData["outline"]?.toString()?.toColor()
            ?: defaultColors.outline,
        outlineVariant = themeData["outlineVariant"]?.toString()?.toColor()
            ?: defaultColors.outlineVariant,
        scrim = themeData["scrim"]?.toString()?.toColor() ?: defaultColors.scrim,
    )
}
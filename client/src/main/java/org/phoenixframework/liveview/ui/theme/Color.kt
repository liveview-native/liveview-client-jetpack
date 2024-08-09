package org.phoenixframework.liveview.ui.theme

import androidx.compose.material3.ColorScheme
import org.phoenixframework.liveview.extensions.toColor

internal fun colorSchemeFromThemeData(
    themeData: Map<String, Any>, defaultColors: ColorScheme
): ColorScheme {
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
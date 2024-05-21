package org.phoenixframework.liveview.domain

import android.content.Context
import android.os.Build
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.phoenixframework.liveview.data.constants.ThemeColorsValues
import org.phoenixframework.liveview.data.constants.ThemeShapesValues
import org.phoenixframework.liveview.data.constants.ThemeTextStyleValues
import org.phoenixframework.liveview.ui.theme.colorSchemeFromThemeData
import org.phoenixframework.liveview.ui.theme.textStyleFromData

private val purple80 = Color(0xFFD0BCFF)
private val purpleGrey80 = Color(0xFFCCC2DC)
private val pink80 = Color(0xFFEFB8C8)

private val purple40 = Color(0xFF6650a4)
private val purpleGrey40 = Color(0xFF625b71)
private val pink40 = Color(0xFF7D5260)

internal object ThemeHolder {
    private val _themeData = MutableStateFlow<ThemeData?>(null)
    val themeData = _themeData.asStateFlow()

    private var isSystemInDarkTheme: Boolean = false
    private var isDynamicColor: Boolean = false

    fun updateThemeData(themeData: Map<String, Any>?) {
        if (themeData != null) {
            val lightColor = getColorScheme(darkTheme = false, themeData = themeData)
            val darkColor = getColorScheme(darkTheme = true, themeData = themeData)
            val shapes = shapesFromThemeData(themeData)
            val typography = typographyFromThemeData(themeData)

            _themeData.update {
                it?.copy(
                    lightColors = lightColor,
                    darkColors = darkColor,
                    shapes = shapes,
                    typography = typography
                ) ?: ThemeData(
                    lightColors = lightColor,
                    darkColors = darkColor,
                    dynamicLightColors = defaultDynamicLightColors,
                    dynamicDarkColors = defaultDynamicDarkColors,
                    shapes = shapes,
                    typography = typography
                )
            }
        }
    }

    fun loadDefaultTheme(context: Context, darkTheme: Boolean, dynamicColor: Boolean = false) {
        isSystemInDarkTheme = darkTheme
        isDynamicColor = dynamicColor
        // Dynamic color is available on Android 12+
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            defaultDynamicLightColors = dynamicDarkColorScheme(context)
            defaultDynamicDarkColors = dynamicLightColorScheme(context)
        }
        _themeData.update {
            it?.copy(
                dynamicLightColors = defaultDynamicLightColors,
                dynamicDarkColors = defaultDynamicDarkColors,
            )
                ?: ThemeData(
                    lightColors = defaultColorsLight,
                    darkColors = defaultColorsDark,
                    dynamicLightColors = defaultDynamicLightColors,
                    dynamicDarkColors = defaultDynamicDarkColors,
                    shapes = defaultShapes,
                    typography = defaultTypography
                )
        }
    }

    internal fun getColorScheme(
        darkTheme: Boolean,
        themeData: Map<String, Any> = emptyMap(),
    ): ColorScheme {
        val colorsData = themeData["colors"] as? Map<String, Any> ?: emptyMap()
        val lightColors = colorsData["lightColors"] as? Map<String, Any> ?: emptyMap()
        val darkColors = colorsData["darkColors"] as? Map<String, Any> ?: emptyMap()
        return when {
            darkTheme -> colorSchemeFromThemeData(darkColors, defaultColorsDark)
            else -> colorSchemeFromThemeData(lightColors, defaultColorsLight)
        }
    }

    fun shapesFromThemeData(map: Map<String, Any>): Shapes {
        return Shapes(
            extraSmall = map["extraSmall"]?.let { themeShapeFromString(it.toString()) }
                ?: defaultShapes.extraSmall,
            small = map["small"]?.let { themeShapeFromString(it.toString()) }
                ?: defaultShapes.small,
            medium = map["medium"]?.let { themeShapeFromString(it.toString()) }
                ?: defaultShapes.medium,
            large = map["large"]?.let { themeShapeFromString(it.toString()) }
                ?: defaultShapes.large,
            extraLarge = map["extraLarge"]?.let { themeShapeFromString(it.toString()) }
                ?: defaultShapes.extraLarge,
        )
    }

    @Suppress("UNCHECKED_CAST")
    internal fun typographyFromThemeData(fontData: Map<String, Any>): Typography {
        return Typography(
            displayLarge = fontData["displayLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.displayLarge,
            displayMedium = fontData["displayMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.displayMedium,
            displaySmall = fontData["displaySmall"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.displaySmall,
            headlineLarge = fontData["headlineLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.headlineLarge,
            headlineMedium = fontData["headlineMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.headlineMedium,
            headlineSmall = fontData["headlineSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.headlineSmall,
            titleLarge = fontData["titleLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.titleLarge,
            titleMedium = fontData["titleMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.titleMedium,
            titleSmall = fontData["titleSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.titleSmall,
            bodyLarge = fontData["bodyLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.bodyLarge,
            bodyMedium = fontData["bodyMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.bodyMedium,
            bodySmall = fontData["bodySmall"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.bodySmall,
            labelLarge = fontData["labelLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.labelLarge,
            labelMedium = fontData["labelMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.labelMedium,
            labelSmall = fontData["labelSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
                ?: defaultTypography.labelSmall,
        )
    }

    fun themeColorFromString(string: String): Color? {
        val colorScheme = _themeData.value?.getColorSchema(isDynamicColor, isSystemInDarkTheme)
        return when (string) {
            ThemeColorsValues.primary -> colorScheme?.primary
            ThemeColorsValues.onPrimary -> colorScheme?.onPrimary
            ThemeColorsValues.primaryContainer -> colorScheme?.primaryContainer
            ThemeColorsValues.onPrimaryContainer -> colorScheme?.onPrimaryContainer
            ThemeColorsValues.inversePrimary -> colorScheme?.inversePrimary
            ThemeColorsValues.secondary -> colorScheme?.secondary
            ThemeColorsValues.onSecondary -> colorScheme?.onSecondary
            ThemeColorsValues.secondaryContainer -> colorScheme?.secondaryContainer
            ThemeColorsValues.onSecondaryContainer -> colorScheme?.onSecondaryContainer
            ThemeColorsValues.tertiary -> colorScheme?.tertiary
            ThemeColorsValues.onTertiary -> colorScheme?.onTertiary
            ThemeColorsValues.tertiaryContainer -> colorScheme?.tertiaryContainer
            ThemeColorsValues.onTertiaryContainer -> colorScheme?.onTertiaryContainer
            ThemeColorsValues.background -> colorScheme?.background
            ThemeColorsValues.onBackground -> colorScheme?.onBackground
            ThemeColorsValues.surface -> colorScheme?.surface
            ThemeColorsValues.onSurface -> colorScheme?.onSurface
            ThemeColorsValues.surfaceVariant -> colorScheme?.surfaceVariant
            ThemeColorsValues.onSurfaceVariant -> colorScheme?.onSurfaceVariant
            ThemeColorsValues.surfaceTint -> colorScheme?.surfaceTint
            ThemeColorsValues.inverseSurface -> colorScheme?.inverseSurface
            ThemeColorsValues.inverseOnSurface -> colorScheme?.inverseOnSurface
            ThemeColorsValues.error -> colorScheme?.error
            ThemeColorsValues.onError -> colorScheme?.onError
            ThemeColorsValues.errorContainer -> colorScheme?.errorContainer
            ThemeColorsValues.onErrorContainer -> colorScheme?.onErrorContainer
            ThemeColorsValues.outline -> colorScheme?.outline
            ThemeColorsValues.outlineVariant -> colorScheme?.outlineVariant
            ThemeColorsValues.scrim -> colorScheme?.scrim
            ThemeColorsValues.surfaceBright -> colorScheme?.surfaceBright
            ThemeColorsValues.surfaceContainer -> colorScheme?.surfaceContainer
            ThemeColorsValues.surfaceContainerHigh -> colorScheme?.surfaceContainerHigh
            ThemeColorsValues.surfaceContainerHighest -> colorScheme?.surfaceContainerHighest
            ThemeColorsValues.surfaceContainerLow -> colorScheme?.surfaceContainerLow
            ThemeColorsValues.surfaceContainerLowest -> colorScheme?.surfaceContainerLowest
            ThemeColorsValues.surfaceDim -> colorScheme?.surfaceDim
            else -> null
        }
    }

    fun themeShapeFromString(string: String): CornerBasedShape? {
        return when (string) {
            ThemeShapesValues.extraSmall -> _themeData.value?.shapes?.extraSmall
            ThemeShapesValues.small -> _themeData.value?.shapes?.small
            ThemeShapesValues.medium -> _themeData.value?.shapes?.medium
            ThemeShapesValues.large -> _themeData.value?.shapes?.large
            ThemeShapesValues.extraLarge -> _themeData.value?.shapes?.extraLarge
            else -> null
        }
    }

    fun themeTextStyleFromString(string: String): TextStyle? {
        return when (string) {
            ThemeTextStyleValues.displayLarge -> _themeData.value?.typography?.displayLarge
            ThemeTextStyleValues.displayMedium -> _themeData.value?.typography?.displayMedium
            ThemeTextStyleValues.displaySmall -> _themeData.value?.typography?.displaySmall
            ThemeTextStyleValues.headlineLarge -> _themeData.value?.typography?.headlineLarge
            ThemeTextStyleValues.headlineMedium -> _themeData.value?.typography?.headlineMedium
            ThemeTextStyleValues.headlineSmall -> _themeData.value?.typography?.headlineSmall
            ThemeTextStyleValues.titleLarge -> _themeData.value?.typography?.titleLarge
            ThemeTextStyleValues.titleMedium -> _themeData.value?.typography?.titleMedium
            ThemeTextStyleValues.titleSmall -> _themeData.value?.typography?.titleSmall
            ThemeTextStyleValues.bodyLarge -> _themeData.value?.typography?.bodyLarge
            ThemeTextStyleValues.bodyMedium -> _themeData.value?.typography?.bodyMedium
            ThemeTextStyleValues.bodySmall -> _themeData.value?.typography?.bodySmall
            ThemeTextStyleValues.labelLarge -> _themeData.value?.typography?.labelLarge
            ThemeTextStyleValues.labelMedium -> _themeData.value?.typography?.labelMedium
            ThemeTextStyleValues.labelSmall -> _themeData.value?.typography?.labelSmall
            else -> null
        }
    }

    private var defaultDynamicLightColors: ColorScheme? = null
    private var defaultDynamicDarkColors: ColorScheme? = null

    private val defaultColorsDark =
        darkColorScheme(
            primary = purple80, secondary = purpleGrey80, tertiary = pink80
        )

    private val defaultColorsLight = lightColorScheme(
        primary = purple40, secondary = purpleGrey40, tertiary = pink40
    )

    private val defaultShapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(0.dp)
    )

    private val defaultTypography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        )
    )

    const val disabledContainerAlpha = .12f
    const val disabledContentAlpha = .38f

    internal data class ThemeData(
        val lightColors: ColorScheme,
        val darkColors: ColorScheme,
        val dynamicLightColors: ColorScheme?,
        val dynamicDarkColors: ColorScheme?,
        val typography: Typography = defaultTypography,
        val shapes: Shapes = defaultShapes
    ) {
        fun getColorSchema(isDynamic: Boolean, isDark: Boolean): ColorScheme {
            return (if (isDynamic) {
                if (isDark) {
                    dynamicDarkColors
                } else {
                    dynamicLightColors
                }
            } else null) ?: if (isDark) {
                darkColors
            } else {
                lightColors
            }
        }
    }
}
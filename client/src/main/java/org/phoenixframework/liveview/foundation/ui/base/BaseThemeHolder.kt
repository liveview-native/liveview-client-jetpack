package org.phoenixframework.liveview.foundation.ui.base

import android.content.Context
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow

abstract class BaseThemeHolder {

    abstract val themeData: StateFlow<ThemeData?>
    abstract fun isEmpty(): Boolean

    abstract fun loadDefaultTheme(context: Context, darkTheme: Boolean, dynamicColor: Boolean)
    abstract fun updateThemeData(map: Map<String, Any>?)

    abstract fun themeColorFromString(string: String): Color?
    abstract fun themeShapeFromString(string: String): CornerBasedShape?
    abstract fun themeTextStyleFromString(string: String): TextStyle?

    data class ThemeData(
        val lightColors: ColorScheme,
        val darkColors: ColorScheme,
        val dynamicLightColors: ColorScheme?,
        val dynamicDarkColors: ColorScheme?,
        val typography: Typography,
        val shapes: Shapes
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
package org.phoenixframework.liveview.ui.theme

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.R
import org.phoenixframework.liveview.data.constants.FontStyleValues
import org.phoenixframework.liveview.data.constants.FontWeightValues
import org.phoenixframework.liveview.data.constants.HyphensValues
import org.phoenixframework.liveview.data.constants.LineBreakValues
import org.phoenixframework.liveview.data.constants.TextAlignValues
import org.phoenixframework.liveview.data.constants.TextDecorationValues
import org.phoenixframework.liveview.data.constants.TextDirectionValues
import org.phoenixframework.liveview.data.constants.TextStyleValues
import org.phoenixframework.liveview.domain.extensions.toColor

internal val provider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

@Suppress("UNCHECKED_CAST")
internal fun typographyFromThemeData(fontData: Map<String, Any>): Typography {
    val default = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        )
    )
    return Typography(
        displayLarge = fontData["displayLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.displayLarge,
        displayMedium = fontData["displayMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.displayMedium,
        displaySmall = fontData["displaySmall"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.displaySmall,
        headlineLarge = fontData["headlineLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.headlineLarge,
        headlineMedium = fontData["headlineMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.headlineMedium,
        headlineSmall = fontData["headlineSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.headlineSmall,
        titleLarge = fontData["titleLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.titleLarge,
        titleMedium = fontData["titleMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.titleMedium,
        titleSmall = fontData["titleSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.titleSmall,
        bodyLarge = fontData["bodyLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.bodyLarge,
        bodyMedium = fontData["bodyMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.bodyMedium,
        bodySmall = fontData["bodySmall"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.bodySmall,
        labelLarge = fontData["labelLarge"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.labelLarge,
        labelMedium = fontData["labelMedium"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.labelMedium,
        labelSmall = fontData["labelSmall"]?.let { textStyleFromData(it as Map<String, Any>) }
            ?: default.labelSmall,
    )
}

internal fun fontFamilyFromString(fontFamily: String): FontFamily {
    return FontFamily(
        Font(googleFont = GoogleFont(fontFamily), fontProvider = provider)
    )
}

internal fun textStyleFromData(textStyleData: Map<String, Any>): TextStyle {
    return TextStyle(
        fontFamily = textStyleData["fontFamily"]?.toString()?.let {
            fontFamilyFromString(it)
        },
        color = textStyleData["color"]?.toString()?.toColor() ?: Color.Unspecified,
        fontSize = textStyleData["fontSize"]?.let { fontSizeFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        fontWeight = fontWeightFromString(textStyleData["fontWeight"]?.toString()),
        fontStyle = fontStyleFromString(textStyleData["fontStyle"]?.toString()),
        letterSpacing = textStyleData["letterSpacing"]?.let { letterSpacingFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        background = textStyleData["background"]?.toString()?.toColor() ?: Color.Unspecified,
        textDecoration = textDecorationFromString(textStyleData["textDecoration"]?.toString()),
        textAlign = textAlignFromString(textStyleData["textAlign"]?.toString()),
        lineHeight = textStyleData["textAlign"]?.let { lineHeightFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        lineBreak = lineBreakFromString((textStyleData["lineBreak"] ?: "").toString()),
        hyphens = hyphensFromString((textStyleData["hyphens"] ?: "").toString()),
        textDirection = textDirectionFromString((textStyleData["textDirection"] ?: "").toString()),
        fontFeatureSettings = textStyleData["fontFeatureSettings"]?.toString(),
    )
    //TODO Support these complex types
    //fontSynthesis: FontSynthesis? = null,
    //baselineShift: BaselineShift? = null,
    //textGeometricTransform: TextGeometricTransform? = null,
    //localeList: LocaleList? = null,
    //shadow: Shadow? = null,
    //textIndent: TextIndent? = null,
    //platformStyle: PlatformTextStyle? = null,
    //lineHeightStyle: LineHeightStyle? = null,
}

internal fun fontSizeFromString(fontSize: String): TextUnit {
    return (fontSize.toInt()).sp
}

internal fun fontWeightFromString(fontWeight: String?): FontWeight? {
    return when (fontWeight) {
        FontWeightValues.thin,
        FontWeightValues.W100 -> FontWeight(100)

        FontWeightValues.extraLight,
        FontWeightValues.W200 -> FontWeight(200)

        FontWeightValues.light,
        FontWeightValues.W300 -> FontWeight(300)

        FontWeightValues.normal,
        FontWeightValues.W400 -> FontWeight(400)

        FontWeightValues.medium,
        FontWeightValues.W500 -> FontWeight(500)

        FontWeightValues.semiBold,
        FontWeightValues.W600 -> FontWeight(600)

        FontWeightValues.bold,
        FontWeightValues.W700 -> FontWeight(700)

        FontWeightValues.extraBold,
        FontWeightValues.W800 -> FontWeight(800)

        FontWeightValues.black,
        FontWeightValues.W900 -> FontWeight(900)

        else -> null
    }
}

internal fun fontStyleFromString(fontStyle: String?): FontStyle {
    return when (fontStyle) {
        FontStyleValues.normal -> FontStyle.Normal
        FontStyleValues.italic -> FontStyle.Italic
        else -> FontStyle.Normal
    }
}

internal fun letterSpacingFromString(letterSpacing: String): TextUnit {
    return (letterSpacing.toFloat()).sp
}

internal fun textDecorationFromString(textDecoration: String?): TextDecoration {
    return when (textDecoration) {
        // Draws a horizontal line below the text.
        TextDecorationValues.underline -> TextDecoration.Underline
        // Draws a horizontal line over the text.
        TextDecorationValues.lineThrough -> TextDecoration.LineThrough
        else -> TextDecoration.None
    }
}

internal fun textAlignFromString(textAlign: String?): TextAlign {
    return when (textAlign) {
        TextAlignValues.left -> TextAlign.Left
        TextAlignValues.right -> TextAlign.Right
        TextAlignValues.center -> TextAlign.Center
        TextAlignValues.justify -> TextAlign.Justify
        TextAlignValues.start -> TextAlign.Start
        TextAlignValues.end -> TextAlign.End
        else -> TextAlign.Start
    }
}

internal fun lineHeightFromString(lineHeight: String): TextUnit {
    return (lineHeight.toInt()).sp
}

internal fun lineBreakFromString(lineBreak: String): LineBreak {
    return when (lineBreak) {
        LineBreakValues.simple -> LineBreak.Simple
        LineBreakValues.paragraph -> LineBreak.Paragraph
        LineBreakValues.heading -> LineBreak.Heading
        else -> LineBreak.Simple
    }
}

internal fun hyphensFromString(hyphens: String): Hyphens {
    return when (hyphens) {
        HyphensValues.none -> Hyphens.None
        HyphensValues.auto -> Hyphens.Auto
        else -> Hyphens.Unspecified
    }
}

internal fun textDirectionFromString(textDirection: String): TextDirection {
    return when (textDirection) {
        TextDirectionValues.ltr -> TextDirection.Ltr
        TextDirectionValues.rtl -> TextDirection.Rtl
        TextDirectionValues.content -> TextDirection.Content
        TextDirectionValues.contentOrRtl -> TextDirection.ContentOrRtl
        TextDirectionValues.contentOrLtr -> TextDirection.ContentOrLtr
        else -> TextDirection.Unspecified
    }
}

@Composable
internal fun textStyleFromString(textStyle: String?): TextStyle {
    return when (textStyle) {
        null -> LocalTextStyle.current
        TextStyleValues.displayLarge -> MaterialTheme.typography.displayLarge
        TextStyleValues.displayMedium -> MaterialTheme.typography.displayMedium
        TextStyleValues.displaySmall -> MaterialTheme.typography.displaySmall
        TextStyleValues.headlineLarge -> MaterialTheme.typography.headlineLarge
        TextStyleValues.headlineMedium -> MaterialTheme.typography.headlineMedium
        TextStyleValues.headlineSmall -> MaterialTheme.typography.headlineSmall
        TextStyleValues.titleLarge -> MaterialTheme.typography.titleLarge
        TextStyleValues.titleMedium -> MaterialTheme.typography.titleMedium
        TextStyleValues.titleSmall -> MaterialTheme.typography.titleSmall
        TextStyleValues.bodyLarge -> MaterialTheme.typography.bodyLarge
        TextStyleValues.bodyMedium -> MaterialTheme.typography.bodyMedium
        TextStyleValues.bodySmall -> MaterialTheme.typography.bodySmall
        TextStyleValues.labelLarge -> MaterialTheme.typography.labelLarge
        TextStyleValues.labelMedium -> MaterialTheme.typography.labelMedium
        TextStyleValues.labelSmall -> MaterialTheme.typography.labelSmall
        else -> LocalTextStyle.current
    }
}
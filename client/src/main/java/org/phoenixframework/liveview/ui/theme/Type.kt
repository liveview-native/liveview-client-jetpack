package org.phoenixframework.liveview.ui.theme

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.R
import org.phoenixframework.liveview.constants.Attrs.attrBackground
import org.phoenixframework.liveview.constants.Attrs.attrBaselineShift
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.constants.Attrs.attrFontFeatureSettings
import org.phoenixframework.liveview.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.constants.Attrs.attrFontSynthesis
import org.phoenixframework.liveview.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.constants.Attrs.attrHyphens
import org.phoenixframework.liveview.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.constants.Attrs.attrLineBreak
import org.phoenixframework.liveview.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.constants.Attrs.attrLineHeightStyle
import org.phoenixframework.liveview.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.constants.Attrs.attrTextDirection
import org.phoenixframework.liveview.constants.Attrs.attrTextGeometricTransform
import org.phoenixframework.liveview.constants.Attrs.attrTextIndent
import org.phoenixframework.liveview.constants.Attrs.attrTextMotion
import org.phoenixframework.liveview.constants.BaselineShiftValues
import org.phoenixframework.liveview.constants.FontStyleValues
import org.phoenixframework.liveview.constants.FontSynthesisValues
import org.phoenixframework.liveview.constants.FontWeightValues
import org.phoenixframework.liveview.constants.HyphensValues
import org.phoenixframework.liveview.constants.LineBreakValues
import org.phoenixframework.liveview.constants.TextAlignValues
import org.phoenixframework.liveview.constants.TextDecorationValues
import org.phoenixframework.liveview.constants.TextDirectionValues
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.ui.view.lineHeightStyleFromMap
import org.phoenixframework.liveview.ui.view.textGeometricTransformFromMap
import org.phoenixframework.liveview.ui.view.textIndentFromMap
import org.phoenixframework.liveview.ui.view.textMotionFromString

internal val provider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

fun baselineShiftFromString(baselineShift: String?): BaselineShift {
    return when (baselineShift) {
        BaselineShiftValues.subscript -> BaselineShift.Subscript
        BaselineShiftValues.superscript -> BaselineShift.Superscript
        else -> BaselineShift.None
    }
}

fun fontFamilyFromString(fontFamily: String): FontFamily {
    return FontFamily(
        Font(googleFont = GoogleFont(fontFamily), fontProvider = provider)
    )
}

fun fontStyleFromString(fontStyle: String?): FontStyle {
    return when (fontStyle) {
        FontStyleValues.normal -> FontStyle.Normal
        FontStyleValues.italic -> FontStyle.Italic
        else -> FontStyle.Normal
    }
}

fun fontSynthesisFromString(fontSynthesis: String?): FontSynthesis {
    return when (fontSynthesis) {
        FontSynthesisValues.all -> FontSynthesis.All
        FontSynthesisValues.style -> FontSynthesis.Style
        FontSynthesisValues.weight -> FontSynthesis.Weight
        FontSynthesisValues.none -> FontSynthesis.None
        else -> FontSynthesis.None
    }
}

fun fontWeightFromString(fontWeight: String?): FontWeight? {
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

fun hyphensFromString(hyphens: String): Hyphens {
    return when (hyphens) {
        HyphensValues.none -> Hyphens.None
        HyphensValues.auto -> Hyphens.Auto
        else -> Hyphens.Unspecified
    }
}

fun lineBreakFromString(lineBreak: String): LineBreak {
    return when (lineBreak) {
        LineBreakValues.simple -> LineBreak.Simple
        LineBreakValues.paragraph -> LineBreak.Paragraph
        LineBreakValues.heading -> LineBreak.Heading
        else -> LineBreak.Simple
    }
}

fun textAlignFromString(textAlign: String?): TextAlign {
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

fun textDecorationFromString(textDecoration: String?): TextDecoration {
    return when (textDecoration) {
        // Draws a horizontal line below the text.
        TextDecorationValues.underline -> TextDecoration.Underline
        // Draws a horizontal line over the text.
        TextDecorationValues.lineThrough -> TextDecoration.LineThrough
        TextDecorationValues.none -> TextDecoration.None
        else -> TextDecoration.None
    }
}

fun textDirectionFromString(textDirection: String): TextDirection {
    return when (textDirection) {
        TextDirectionValues.ltr -> TextDirection.Ltr
        TextDirectionValues.rtl -> TextDirection.Rtl
        TextDirectionValues.content -> TextDirection.Content
        TextDirectionValues.contentOrRtl -> TextDirection.ContentOrRtl
        TextDirectionValues.contentOrLtr -> TextDirection.ContentOrLtr
        else -> TextDirection.Unspecified
    }
}

fun textStyleFromData(textStyleData: Map<String, Any>): TextStyle {
    return TextStyle(
        color = textStyleData[attrColor]?.toString()?.toColor() ?: Color.Unspecified,
        fontSize = textStyleData[attrFontSize]?.let { textUnitFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        fontWeight = fontWeightFromString(textStyleData[attrFontWeight]?.toString()),
        fontStyle = textStyleData[attrFontStyle]?.let { fontStyleFromString(it.toString()) },
        fontSynthesis = textStyleData[attrFontSynthesis]?.let { fontSynthesisFromString(it.toString()) },
        fontFamily = textStyleData[attrFontFamily]?.toString()?.let {
            fontFamilyFromString(it)
        },
        fontFeatureSettings = textStyleData[attrFontFeatureSettings]?.toString(),
        letterSpacing = textStyleData[attrLetterSpacing]?.let { textUnitFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        baselineShift = textStyleData[attrBaselineShift]?.let { baselineShiftFromString(it.toString()) },
        textGeometricTransform = textStyleData[attrTextGeometricTransform]?.let { map ->
            (map as? Map<String, Any>)?.let {
                textGeometricTransformFromMap(map)
            }
        },
        background = textStyleData[attrBackground]?.toString()?.toColor() ?: Color.Unspecified,
        textDecoration = textStyleData[attrTextDecoration]?.let { textDecorationFromString(it.toString()) },
        textAlign = textStyleData[attrTextAlign]?.let { textAlignFromString(it.toString()) }
            ?: TextAlign.Unspecified,
        lineHeight = textStyleData[attrLineHeight]?.let { textUnitFromString(it.toString()) }
            ?: TextUnit.Unspecified,
        textIndent = textStyleData[attrTextIndent]?.let { map ->
            (map as? Map<String, Any>)?.let {
                textIndentFromMap(map)
            }
        },
        lineHeightStyle = textStyleData[attrLineHeightStyle]?.let { map ->
            (map as? Map<String, Any>)?.let {
                lineHeightStyleFromMap(map)
            }
        },
        lineBreak = textStyleData[attrLineBreak]?.let {
            lineBreakFromString(it.toString())
        } ?: LineBreak.Unspecified,
        hyphens = hyphensFromString((textStyleData[attrHyphens] ?: "").toString()),
        textDirection = textDirectionFromString(
            (textStyleData[attrTextDirection] ?: "").toString()
        ),
        textMotion = textStyleData[attrTextMotion]?.let {
            textMotionFromString(it.toString())
        }
    )
    //TODO Support these complex types
    //localeList: LocaleList? = null,
    //shadow: Shadow? = null,
    //platformStyle: PlatformTextStyle? = null,
}

@Composable
fun textStyleFromString(textStyle: String?): TextStyle {
    if (textStyle == null) return LocalTextStyle.current
    return LiveViewJetpack.getThemeHolder().themeTextStyleFromString(textStyle)
        ?: LocalTextStyle.current
}

fun textUnitFromString(string: String): TextUnit {
    return (string.toFloat()).sp
}
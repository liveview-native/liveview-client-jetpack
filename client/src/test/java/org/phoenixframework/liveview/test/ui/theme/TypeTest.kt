package org.phoenixframework.liveview.test.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.sp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrBaselineShift
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFirstLine
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFeatureSettings
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSynthesis
import org.phoenixframework.liveview.data.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrHyphens
import org.phoenixframework.liveview.data.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrLineBreak
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeightStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrRestLine
import org.phoenixframework.liveview.data.constants.Attrs.attrScaleX
import org.phoenixframework.liveview.data.constants.Attrs.attrSkewX
import org.phoenixframework.liveview.data.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDirection
import org.phoenixframework.liveview.data.constants.Attrs.attrTextGeometricTransform
import org.phoenixframework.liveview.data.constants.Attrs.attrTextIndent
import org.phoenixframework.liveview.data.constants.Attrs.attrTextMotion
import org.phoenixframework.liveview.data.constants.Attrs.attrTrim
import org.phoenixframework.liveview.data.constants.BaselineShiftValues
import org.phoenixframework.liveview.data.constants.FontStyleValues
import org.phoenixframework.liveview.data.constants.FontStyleValues.italic
import org.phoenixframework.liveview.data.constants.FontSynthesisValues
import org.phoenixframework.liveview.data.constants.FontSynthesisValues.all
import org.phoenixframework.liveview.data.constants.FontWeightValues
import org.phoenixframework.liveview.data.constants.FontWeightValues.extraBold
import org.phoenixframework.liveview.data.constants.HyphensValues
import org.phoenixframework.liveview.data.constants.LineBreakValues
import org.phoenixframework.liveview.data.constants.LineBreakValues.paragraph
import org.phoenixframework.liveview.data.constants.LineHeightStyleAlignmentValues
import org.phoenixframework.liveview.data.constants.LineHeightStyleTrimValues
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.TextAlignValues
import org.phoenixframework.liveview.data.constants.TextAlignValues.center
import org.phoenixframework.liveview.data.constants.TextDecorationValues
import org.phoenixframework.liveview.data.constants.TextDecorationValues.underline
import org.phoenixframework.liveview.data.constants.TextDirectionValues
import org.phoenixframework.liveview.data.constants.TextDirectionValues.rtl
import org.phoenixframework.liveview.data.constants.TextMotionValues
import org.phoenixframework.liveview.ui.theme.baselineShiftFromString
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString
import org.phoenixframework.liveview.ui.theme.fontStyleFromString
import org.phoenixframework.liveview.ui.theme.fontSynthesisFromString
import org.phoenixframework.liveview.ui.theme.fontWeightFromString
import org.phoenixframework.liveview.ui.theme.hyphensFromString
import org.phoenixframework.liveview.ui.theme.lineBreakFromString
import org.phoenixframework.liveview.ui.theme.textAlignFromString
import org.phoenixframework.liveview.ui.theme.textDecorationFromString
import org.phoenixframework.liveview.ui.theme.textDirectionFromString
import org.phoenixframework.liveview.ui.theme.textStyleFromData
import org.phoenixframework.liveview.ui.theme.textUnitFromString

@RunWith(AndroidJUnit4::class)
class TypeTest {

    @Test
    fun baselineShiftFromStringTest() {
        assertEquals(
            baselineShiftFromString(BaselineShiftValues.subscript),
            BaselineShift.Subscript
        )
        assertEquals(
            baselineShiftFromString(BaselineShiftValues.superscript),
            BaselineShift.Superscript
        )
        assertEquals(baselineShiftFromString(BaselineShiftValues.none), BaselineShift.None)
    }

    @Test
    fun fontStyleFromStringTest() {
        assertEquals(fontStyleFromString(FontStyleValues.normal), FontStyle.Normal)
        assertEquals(fontStyleFromString(FontStyleValues.italic), FontStyle.Italic)
    }

    @Test
    fun fontSynthesisFromStringTest() {
        assertEquals(fontSynthesisFromString(FontSynthesisValues.all), FontSynthesis.All)
        assertEquals(fontSynthesisFromString(FontSynthesisValues.style), FontSynthesis.Style)
        assertEquals(fontSynthesisFromString(FontSynthesisValues.weight), FontSynthesis.Weight)
        assertEquals(fontSynthesisFromString(FontSynthesisValues.none), FontSynthesis.None)
    }

    @Test
    fun fontWeightFromStringTest() {
        assertEquals(fontWeightFromString(FontWeightValues.thin), FontWeight(100))
        assertEquals(fontWeightFromString(FontWeightValues.W100), FontWeight(100))
        assertEquals(fontWeightFromString(FontWeightValues.extraLight), FontWeight(200))
        assertEquals(fontWeightFromString(FontWeightValues.W200), FontWeight(200))
        assertEquals(fontWeightFromString(FontWeightValues.light), FontWeight(300))
        assertEquals(fontWeightFromString(FontWeightValues.W300), FontWeight(300))
        assertEquals(fontWeightFromString(FontWeightValues.normal), FontWeight(400))
        assertEquals(fontWeightFromString(FontWeightValues.W400), FontWeight(400))
        assertEquals(fontWeightFromString(FontWeightValues.medium), FontWeight(500))
        assertEquals(fontWeightFromString(FontWeightValues.W500), FontWeight(500))
        assertEquals(fontWeightFromString(FontWeightValues.semiBold), FontWeight(600))
        assertEquals(fontWeightFromString(FontWeightValues.W600), FontWeight(600))
        assertEquals(fontWeightFromString(FontWeightValues.bold), FontWeight(700))
        assertEquals(fontWeightFromString(FontWeightValues.W700), FontWeight(700))
        assertEquals(fontWeightFromString(FontWeightValues.extraBold), FontWeight(800))
        assertEquals(fontWeightFromString(FontWeightValues.W800), FontWeight(800))
        assertEquals(fontWeightFromString(FontWeightValues.black), FontWeight(900))
        assertEquals(fontWeightFromString(FontWeightValues.W900), FontWeight(900))
    }

    @Test
    fun hyphensFromStringTest() {
        assertEquals(hyphensFromString(HyphensValues.none), Hyphens.None)
        assertEquals(hyphensFromString(HyphensValues.auto), Hyphens.Auto)
        assertEquals(hyphensFromString(HyphensValues.unspecified), Hyphens.Unspecified)
    }

    @Test
    fun lineBreakFromStringTest() {
        assertEquals(lineBreakFromString(LineBreakValues.simple), LineBreak.Simple)
        assertEquals(lineBreakFromString(LineBreakValues.paragraph), LineBreak.Paragraph)
        assertEquals(lineBreakFromString(LineBreakValues.heading), LineBreak.Heading)
        assertEquals(lineBreakFromString(LineBreakValues.simple), LineBreak.Simple)
    }

    @Test
    fun textAlignFromStringTest() {
        assertEquals(textAlignFromString(TextAlignValues.left), TextAlign.Left)
        assertEquals(textAlignFromString(TextAlignValues.right), TextAlign.Right)
        assertEquals(textAlignFromString(TextAlignValues.center), TextAlign.Center)
        assertEquals(textAlignFromString(TextAlignValues.justify), TextAlign.Justify)
        assertEquals(textAlignFromString(TextAlignValues.start), TextAlign.Start)
        assertEquals(textAlignFromString(TextAlignValues.end), TextAlign.End)
    }

    @Test
    fun textDecorationFromStringTest() {
        assertEquals(
            textDecorationFromString(TextDecorationValues.underline),
            TextDecoration.Underline
        )
        assertEquals(
            textDecorationFromString(TextDecorationValues.lineThrough),
            TextDecoration.LineThrough
        )
        assertEquals(textDecorationFromString(TextDecorationValues.none), TextDecoration.None)
    }

    @Test
    fun textDirectionFromStringTest() {
        assertEquals(textDirectionFromString(TextDirectionValues.ltr), TextDirection.Ltr)
        assertEquals(textDirectionFromString(TextDirectionValues.rtl), TextDirection.Rtl)
        assertEquals(textDirectionFromString(TextDirectionValues.content), TextDirection.Content)
        assertEquals(
            textDirectionFromString(TextDirectionValues.contentOrRtl),
            TextDirection.ContentOrRtl
        )
        assertEquals(
            textDirectionFromString(TextDirectionValues.contentOrLtr),
            TextDirection.ContentOrLtr
        )
        assertEquals(
            textDirectionFromString("foo"),
            TextDirection.Unspecified
        )
    }

    @Test
    fun textStyleFromDataTest() {
        assertEquals(
            TextStyle(),
            textStyleFromData(emptyMap())
        )
        assertEquals(
            TextStyle(color = Color.Blue),
            textStyleFromData(mapOf(attrColor to "Blue"))
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp
            ),
            textStyleFromData(
                mapOf(
                    attrColor to "Blue",
                    attrFontSize to 18
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all
                )
            )
        )
        val fontName = "Lobster Two"
        val fontFamily = fontFamilyFromString(fontName)
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp"
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp"
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.Superscript
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.superscript
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                )
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f)
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp,
                textIndent = TextIndent(firstLine = 10.sp, restLine = 18.sp)
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                    attrTextIndent to mapOf(attrFirstLine to 10, attrRestLine to 18)
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp,
                textIndent = TextIndent(firstLine = 10.sp, restLine = 18.sp),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                    attrTextIndent to mapOf(attrFirstLine to 10, attrRestLine to 18),
                    attrLineHeightStyle to mapOf(
                        attrAlignment to LineHeightStyleAlignmentValues.center,
                        attrTrim to LineHeightStyleTrimValues.both
                    )
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp,
                textIndent = TextIndent(firstLine = 10.sp, restLine = 18.sp),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                ),
                lineBreak = LineBreak.Paragraph,
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                    attrTextIndent to mapOf(attrFirstLine to 10, attrRestLine to 18),
                    attrLineHeightStyle to mapOf(
                        attrAlignment to LineHeightStyleAlignmentValues.center,
                        attrTrim to LineHeightStyleTrimValues.both
                    ),
                    attrLineBreak to paragraph
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp,
                textIndent = TextIndent(firstLine = 10.sp, restLine = 18.sp),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                ),
                lineBreak = LineBreak.Paragraph,
                hyphens = Hyphens.Auto
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                    attrTextIndent to mapOf(attrFirstLine to 10, attrRestLine to 18),
                    attrLineHeightStyle to mapOf(
                        attrAlignment to LineHeightStyleAlignmentValues.center,
                        attrTrim to LineHeightStyleTrimValues.both
                    ),
                    attrLineBreak to paragraph,
                    attrHyphens to HyphensValues.auto,
                )
            )
        )
        assertEquals(
            TextStyle(
                color = Color.Blue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.All,
                fontFamily = fontFamily,
                fontFeatureSettings = "smcp",
                letterSpacing = 8.sp,
                baselineShift = BaselineShift.None,
                textGeometricTransform = TextGeometricTransform(
                    scaleX = 1.5f, skewX = 0.5f
                ),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                textDirection = TextDirection.Rtl,
                lineHeight = 22.sp,
                textIndent = TextIndent(firstLine = 10.sp, restLine = 18.sp),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                ),
                lineBreak = LineBreak.Paragraph,
                hyphens = Hyphens.Auto,
                textMotion = TextMotion.Animated
            ),
            textStyleFromData(
                mapOf(
                    attrColor to Blue,
                    attrFontSize to 18,
                    attrFontWeight to extraBold,
                    attrFontStyle to italic,
                    attrFontSynthesis to all,
                    attrFontFamily to fontName,
                    attrFontFeatureSettings to "smcp",
                    attrLetterSpacing to 8,
                    attrBaselineShift to BaselineShiftValues.none,
                    attrTextGeometricTransform to mapOf(attrScaleX to 1.5f, attrSkewX to 0.5f),
                    attrBackground to Yellow,
                    attrTextDecoration to underline,
                    attrTextAlign to center,
                    attrTextDirection to rtl,
                    attrLineHeight to 22,
                    attrTextIndent to mapOf(attrFirstLine to 10, attrRestLine to 18),
                    attrLineHeightStyle to mapOf(
                        attrAlignment to LineHeightStyleAlignmentValues.center,
                        attrTrim to LineHeightStyleTrimValues.both
                    ),
                    attrLineBreak to paragraph,
                    attrHyphens to HyphensValues.auto,
                    attrTextMotion to TextMotionValues.animated
                )
            )
        )
    }

    @Test
    fun textUnitFromStringTest() {
        assertEquals(
            textUnitFromString("12"), 12.sp
        )
        assertEquals(
            textUnitFromString("12.5"), 12.5.sp
        )
    }
}
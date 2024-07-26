package com.dockyard.liveviewtest.liveview.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.test.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxLines
import org.phoenixframework.liveview.data.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.data.constants.Attrs.attrOverflow
import org.phoenixframework.liveview.data.constants.Attrs.attrSpanStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.data.constants.ComposableTypes.annotatedText
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.FontStyleValues.italic
import org.phoenixframework.liveview.data.constants.FontWeightValues
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.TextAlignValues
import org.phoenixframework.liveview.data.constants.TextDecorationValues.lineThrough
import org.phoenixframework.liveview.data.constants.TextDecorationValues.underline
import org.phoenixframework.liveview.data.constants.TextOverflowValues
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString

class TextShotTest : LiveViewComposableTest() {

    @Test
    fun textTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = "Simple Text")
                    Text(text = "Text with color", color = Color.Red)
                    Text(text = "Text small", fontSize = 12.sp)
                    Text(text = "Text medium", fontSize = 16.sp)
                    Text(text = "Text large", fontSize = 24.sp)
                    Text(text = "Text min lines", minLines = 3)
                    Text(text = "Text Italic", fontStyle = FontStyle.Italic)
                    Text(text = "Text with letter spacing", letterSpacing = 12.sp)
                    Text(text = "Text Line Through", textDecoration = TextDecoration.LineThrough)
                    Text(text = "Text Underline", textDecoration = TextDecoration.Underline)
                    Text(
                        text = "All of above",
                        fontSize = 28.sp,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 4.sp,
                        minLines = 2,
                        textDecoration = TextDecoration.Underline
                    )
                }
            },
            template = """
                <$column>
                    <$text>Simple Text</$text>
                    <$text $attrColor="#FFFF0000">Text with color</$text>
                    <$text $attrFontSize="12">Text small</$text>
                    <$text $attrFontSize="16">Text medium</$text>
                    <$text $attrFontSize="24">Text large</$text>
                    <$text $attrMinLines="3">Text min lines</$text>
                    <$text $attrFontStyle="$italic">Text Italic</$text>
                    <$text $attrLetterSpacing="12">Text with letter spacing</$text>
                    <$text $attrTextDecoration="$lineThrough">Text Line Through</$text>
                    <$text $attrTextDecoration="$underline">Text Underline</$text>
                    <$text 
                      $attrFontSize="28" 
                      $attrFontStyle="$italic" 
                      $attrMinLines="2"
                      $attrLetterSpacing="4" 
                      $attrTextDecoration="$underline">
                      All of above
                    </$text>  
                </$column>
                """
        )
    }

    @Test
    fun textTestWithFontFamily() {
        val fontName = "Lobster Two"
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 5000,
            nativeComposable = {
                val fontFamily = fontFamilyFromString(fontName)
                Column {
                    Text(text = "Simple Text", fontFamily = fontFamily)
                    Text(text = "Text with color", fontFamily = fontFamily, color = Color.Red)
                    Text(text = "Text small", fontFamily = fontFamily, fontSize = 12.sp)
                    Text(text = "Text medium", fontFamily = fontFamily, fontSize = 16.sp)
                    Text(text = "Text large", fontFamily = fontFamily, fontSize = 24.sp)
                    Text(text = "Text min lines", fontFamily = fontFamily, minLines = 3)
                    Text(
                        text = "Text Italic",
                        fontFamily = fontFamily,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = "Text with letter spacing",
                        fontFamily = fontFamily,
                        letterSpacing = 12.sp
                    )
                    Text(
                        text = "Text Line Through",
                        fontFamily = fontFamily,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = "Text Underline",
                        fontFamily = fontFamily,
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = "All of above",
                        fontFamily = fontFamily,
                        fontSize = 28.sp,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 4.sp,
                        minLines = 2,
                        textDecoration = TextDecoration.Underline
                    )
                }
            },
            template = """
                <$column>
                    <$text $attrFontFamily="$fontName">Simple Text</$text>
                    <$text $attrFontFamily="$fontName" $attrColor="#FFFF0000">Text with color</$text>
                    <$text $attrFontFamily="$fontName" $attrFontSize="12">Text small</$text>
                    <$text $attrFontFamily="$fontName" $attrFontSize="16">Text medium</$text>
                    <$text $attrFontFamily="$fontName" $attrFontSize="24">Text large</$text>
                    <$text $attrFontFamily="$fontName" $attrMinLines="3">Text min lines</$text>
                    <$text $attrFontFamily="$fontName" $attrFontStyle="$italic">Text Italic</$text>
                    <$text $attrFontFamily="$fontName" $attrLetterSpacing="12">Text with letter spacing</$text>
                    <$text $attrFontFamily="$fontName" $attrTextDecoration="$lineThrough">Text Line Through</$text>
                    <$text $attrFontFamily="$fontName" $attrTextDecoration="$underline">$text Underline</$text>
                    <$text 
                      $attrFontFamily="$fontName"
                      $attrFontSize="28" 
                      $attrFontStyle="$italic" 
                      $attrMinLines="2"
                      $attrLetterSpacing="4" 
                      $attrTextDecoration="$underline">
                      All of above
                    </$text>  
                </$column>
                """
        )
    }

    @Test
    fun textWeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = "Text Thin weight", fontWeight = FontWeight.Thin)
                    Text(text = "Text Extra Light weight", fontWeight = FontWeight.ExtraLight)
                    Text(text = "Text Light weight", fontWeight = FontWeight.Light)
                    Text(text = "Text Normal weight", fontWeight = FontWeight.Normal)
                    Text(text = "Text Medium weight", fontWeight = FontWeight.Medium)
                    Text(text = "Text Semi Bold weight", fontWeight = FontWeight.SemiBold)
                    Text(text = "Text Bold weight", fontWeight = FontWeight.Bold)
                    Text(text = "Text Extra Bold weight", fontWeight = FontWeight.ExtraBold)
                    Text(text = "Text Black weight", fontWeight = FontWeight.Black)
                }
            },
            template = """
                <$column>
                    <$text $attrFontWeight="${FontWeightValues.thin}">Text Thin weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.extraLight}">Text Extra Light weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.light}">Text Light weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.normal}">Text Normal weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.medium}">Text Medium weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.semiBold}">Text Semi Bold weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.bold}">Text Bold weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.extraBold}">Text Extra Bold weight</$text>
                    <$text $attrFontWeight="${FontWeightValues.black}">Text Black weight</$text>
                </$column>
                """
        )
    }

    @Test
    fun textWithAlignTest() {
        ModifiersParser.fromStyleFile(
            """%{"$modifierFillMaxWidth()" => [{:$modifierFillMaxWidth, [], []}]}""".trimStyle()
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(
                        text = "Text Start",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Text End",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = "Text Center",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = lorenIpsum,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify
                    )
                }
            },
            template = """
                <$column $attrStyle="$modifierFillMaxWidth()">
                    <$text $attrStyle="$modifierFillMaxWidth()" $attrTextAlign="${TextAlignValues.start}">Text Start</$text>
                    <$text $attrStyle="$modifierFillMaxWidth()" $attrTextAlign="${TextAlignValues.end}">Text End</$text>
                    <$text $attrStyle="$modifierFillMaxWidth()" $attrTextAlign="${TextAlignValues.center}">Text Center</$text>
                    <$text $attrStyle="$modifierFillMaxWidth()" $attrTextAlign="${TextAlignValues.justify}">$lorenIpsum</$text>
                </$column>
                """
        )
    }

    @Test
    fun textOverflowTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = lorenIpsum, maxLines = 2, overflow = TextOverflow.Clip)
                    Text(text = lorenIpsum, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    Text(text = lorenIpsum, maxLines = 4, overflow = TextOverflow.Visible)
                }
            },
            template = """
                <$column>
                    <$text $attrMaxLines="2" $attrOverflow="${TextOverflowValues.clip}">$lorenIpsum</$text>
                    <$text $attrMaxLines="3" $attrOverflow="${TextOverflowValues.ellipsis}">$lorenIpsum</$text>
                    <$text $attrMaxLines="4" $attrOverflow="${TextOverflowValues.visible}">$lorenIpsum</$text>
                </$column>
                """
        )
    }

    @Test
    fun textLineHeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = lorenIpsum, maxLines = 3, lineHeight = 16.sp)
                    Text(text = lorenIpsum, maxLines = 3, lineHeight = 20.sp)
                }
            },
            template = """
                <$column>
                    <$text $attrMaxLines="3" $attrLineHeight="16">$lorenIpsum</$text>
                    <$text $attrMaxLines="3" $attrLineHeight="20">$lorenIpsum</$text>
                </$column>
                """
        )
    }

    @Test
    fun textWithAnnotatedStringTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Red, fontSize = 18.sp)) {
                            append("H")
                        }
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append("ello")
                        }
                    })
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 24.sp)) {
                            append("W")
                        }
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                            append("orld")
                        }
                    })
                }
            },
            template = """
                <$column>
                    <$annotatedText>
                        <$text $attrSpanStyle="{'$attrColor': '$Red', '$attrFontSize': 18}" $attrText="H"/>
                        <$text $attrSpanStyle="{'$attrTextDecoration': '$underline'}" $attrText="ello" />
                    </$annotatedText>
                    <$annotatedText>
                        <$text $attrSpanStyle="{'$attrColor': '$Blue', '$attrFontSize': 24}">W</$text>
                        <$text $attrSpanStyle="{'$attrTextDecoration': '$lineThrough'}">orld</$text>
                    </$annotatedText>                    
                </$column>
                """,
            captureScreenImage = true
        )
    }

    private val lorenIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim " +
            "veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
}
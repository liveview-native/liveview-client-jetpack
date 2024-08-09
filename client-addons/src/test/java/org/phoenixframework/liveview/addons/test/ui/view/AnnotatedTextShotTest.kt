package org.phoenixframework.liveview.addons.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import org.junit.Before
import org.junit.Test
import org.phoenixframework.liveview.addons.LiveViewJetpackAddons
import org.phoenixframework.liveview.addons.constants.AddonsTypes.annotatedText
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.constants.Attrs.attrSpanStyle
import org.phoenixframework.liveview.constants.Attrs.attrText
import org.phoenixframework.liveview.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.constants.ComposableTypes.column
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.constants.SystemColorValues.Red
import org.phoenixframework.liveview.constants.TextDecorationValues.lineThrough
import org.phoenixframework.liveview.constants.TextDecorationValues.underline
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class AnnotatedTextShotTest : LiveViewComposableTest() {

    @Before
    fun setupTest() {
        LiveViewJetpackAddons.registerComponentByTag(annotatedText)
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
}
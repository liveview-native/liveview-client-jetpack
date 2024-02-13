package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ShapeValues.circle
import org.phoenixframework.liveview.domain.base.ComposableTypes.button
import org.phoenixframework.liveview.domain.base.ComposableTypes.elevatedButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.filledTonalButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.outlinedButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.base.ComposableTypes.textButton

class ButtonShotTest : LiveViewComposableTest() {

    @Test
    fun checkASimpleButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Button(onClick = {}) {
                    Text(text = "Ok")
                }
            },
            template = """
                <$button>
                  <$text>Ok</$text>
                </$button>
                """
        )
    }

    @Test
    fun checkSimpleButtonDisabledTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Button(onClick = {}, enabled = false) {
                    Text(text = "Ok")
                }
            },
            template = """
                <$button $attrEnabled="false">
                  <$text>Ok</$text>
                </$button>
                """
        )
    }

    @Test
    fun checkButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF00FF', 
            '$colorAttrContentColor': '#FFFFFFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Button")
                }
            },
            template = """
                <$button
                  $attrColors="$colorsForTemplate">
                  <$text>Button</$text>
                </$button>
                """
        )
    }

    @Test
    fun checkButtonWithBorderShapeAndPaddingTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FF00FFFF', 
            '$colorAttrContentColor': '#FFFFFF00'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Yellow
                    ),
                    border = BorderStroke(2.dp, Color.Red),
                    shape = CircleShape,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Text(text = "Button")
                }
            },
            template = """
                <$button
                  $attrColors="$colorsForTemplate"
                  $attrBorder="{'$attrWidth': '2', '$attrColor': '#FFFF0000'}" 
                  $attrShape="$circle" 
                  $attrContentPadding="8">
                  <$text>Button</$text>
                </$button>
                """
        )
    }

    @Test
    fun checkSimpleElevatedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ElevatedButton(onClick = {}) {
                    Text(text = "Elevated Button")
                }
            },
            template = """
                <$elevatedButton>
                  <$text>Elevated Button</$text>
                </$elevatedButton>
                """
        )
    }

    @Test
    fun checkSimpleFilledTonalButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FilledTonalButton(onClick = {}) {
                    Text(text = "Filled Tonal Button")
                }
            },
            template = """
                <$filledTonalButton>
                  <$text>Filled Tonal Button</$text>
                </$filledTonalButton>
                """
        )
    }

    @Test
    fun checkSimpleOutlinedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                OutlinedButton(onClick = {}) {
                    Text(text = "Outlined Button")
                }
            },
            template = """
                <$outlinedButton>
                  <$text>Outlined Button</$text>
                </$outlinedButton>
                """
        )
    }

    @Test
    fun checkSimpleTextButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TextButton(onClick = {}) {
                    Text(text = "Text Button")
                }
            },
            template = """
                <$textButton>
                  <$text>Text Button</$text>
                </$textButton>
                """
        )
    }
}
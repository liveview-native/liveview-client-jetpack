package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <Button phx-click="">
                  <Text>Ok</Text>
                </Button>
                """.templateToTest()
        )
    }

    @Test
    fun checkASimpleButtonDisabledTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Button(onClick = {}, enabled = false) {
                    Text(text = "Ok")
                }
            },
            template = """
                <Button phx-click="" enabled="false">
                  <Text>Ok</Text>
                </Button>
                """.templateToTest()
        )
    }

    @Test
    fun checkAButtonWithCustomColorsTest() {
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
                <Button phx-click=""
                  colors="{'containerColor': '#FFFF00FF', 'contentColor': '#FFFFFFFF'}">
                  <Text>Button</Text>
                </Button>
                """.templateToTest()
        )
    }

    @Test
    fun checkAButtonWithBorderShapeAndPaddingTest() {
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
                <Button phx-click=""
                  colors="{'containerColor': '#FF00FFFF', 'contentColor': '#FFFFFF00'}"
                  border-width="2" border-color="#FFFF0000" shape="circle" content-padding="8">
                  <Text>Button</Text>
                </Button>
                """.templateToTest()
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class IconShotTest : LiveViewComposableTest() {

    @Test
    fun simpleIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Icon(imageVector = Icons.Filled.Image, contentDescription = "")
                    Icon(
                        imageVector = Icons.Filled.Image,
                        tint = Color.Red,
                        contentDescription = ""
                    )
                }
            },
            template = """
                <Row>
                  <Icon image-vector="filled:Image" />
                  <Icon image-vector="filled:Image" tint="#FFFF0000" />
                </Row>
                """.templateToTest()
        )
    }
}
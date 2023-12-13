package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class DividerShotTest : LiveViewComposableTest() {

    @Test
    fun simpleDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    Divider()
                    Text(text = "Line 2")
                }
            },
            template = """
                <Column width="100">
                    <Text>Line 1</Text>
                    <Divider />
                    <Text>Line 2</Text>
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun customDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    Divider(thickness = 2.dp, color = Color.Red)
                    Text(text = "Line 2")
                }
            },
            template = """
                <Column width="100">
                    <Text>Line 1</Text>
                    <Divider thickness="2" color="#FFFF0000" />
                    <Text>Line 2</Text>
                </Column>
                """.templateToTest()
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class DividerShotTest : LiveViewComposableTest() {

    @Test
    fun simpleHorizontalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    HorizontalDivider()
                    Text(text = "Line 2")
                }
            },
            template = """
                <Column width="100">
                    <Text>Line 1</Text>
                    <HorizontalDivider />
                    <Text>Line 2</Text>
                </Column>
                """
        )
    }

    @Test
    fun simpleVerticalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(Modifier.width(100.dp)) {
                    Text(text = "Cell 1")
                    VerticalDivider()
                    Text(text = "Cell 2")
                }
            },
            template = """
                <Row width="100">
                    <Text>Cell 1</Text>
                    <VerticalDivider />
                    <Text>Cell 2</Text>
                </Row>
                """
        )
    }

    @Test
    fun customHorizontalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    HorizontalDivider(thickness = 2.dp, color = Color.Red)
                    Text(text = "Line 2")
                }
            },
            template = """
                <Column width="100">
                    <Text>Line 1</Text>
                    <HorizontalDivider thickness="2" color="#FFFF0000" />
                    <Text>Line 2</Text>
                </Column>
                """
        )
    }

    @Test
    fun customVerticalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(Modifier.width(100.dp)) {
                    Text(text = "Cell 1")
                    VerticalDivider(thickness = 2.dp, color = Color.Red)
                    Text(text = "Cell 2")
                }
            },
            template = """
                <Row width="100">
                    <Text>Cell 1</Text>
                    <VerticalDivider thickness="2" color="#FFFF0000" />
                    <Text>Cell 2</Text>
                </Row>
                """
        )
    }
}
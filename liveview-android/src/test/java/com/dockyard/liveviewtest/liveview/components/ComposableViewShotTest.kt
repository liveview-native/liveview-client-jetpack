package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class ComposableViewShotTest : LiveViewComposableTest() {
    @Test
    fun systemColorsShotTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                @Composable
                fun boxWithColor(color: Color) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color)
                    )
                }
                Column {
                    boxWithColor(color = Color.Red)
                    boxWithColor(color = Color.Green)
                    boxWithColor(color = Color.Blue)
                    boxWithColor(color = Color.Black)
                    boxWithColor(color = Color.White)
                    boxWithColor(color = Color.Gray)
                    boxWithColor(color = Color.LightGray)
                    boxWithColor(color = Color.DarkGray)
                    boxWithColor(color = Color.Yellow)
                    boxWithColor(color = Color.Magenta)
                    boxWithColor(color = Color.Cyan)
                    boxWithColor(color = Color.Transparent)
                    boxWithColor(color = Color.Unspecified)
                }
            },
            template = """
                <Column>
                    <Box size="40" background="system-red" />
                    <Box size="40" background="system-green" />
                    <Box size="40" background="system-blue" />
                    <Box size="40" background="system-black" />
                    <Box size="40" background="system-white" />
                    <Box size="40" background="system-gray" />
                    <Box size="40" background="system-light-gray" />
                    <Box size="40" background="system-dark-gray" />
                    <Box size="40" background="system-yellow" />
                    <Box size="40" background="system-magenta" />
                    <Box size="40" background="system-cyan" />
                    <Box size="40" background="system-transparent" />
                    <Box size="40" background="invalid-color" />
                </Column>
                """
        )
    }

    @Test
    fun rrggbbColorsShotTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                @Composable
                fun boxWithColor(color: Color) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color)
                    )
                }
                Column {
                    boxWithColor(color = Color.Red)
                    boxWithColor(color = Color.Green)
                    boxWithColor(color = Color.Blue)
                    boxWithColor(color = Color.Black)
                    boxWithColor(color = Color.White)
                    boxWithColor(color = Color.Gray)
                    boxWithColor(color = Color.LightGray)
                    boxWithColor(color = Color.DarkGray)
                    boxWithColor(color = Color.Yellow)
                    boxWithColor(color = Color.Magenta)
                    boxWithColor(color = Color.Cyan)
                    boxWithColor(color = Color.Unspecified)
                }
            },
            template = """
                <Column>
                    <Box size="40" background="#FF0000" />
                    <Box size="40" background="#00FF00" />
                    <Box size="40" background="#0000FF" />
                    <Box size="40" background="#000000" />
                    <Box size="40" background="#FFFFFF" />
                    <Box size="40" background="#888888" />
                    <Box size="40" background="#CCCCCC" />
                    <Box size="40" background="#444444" />
                    <Box size="40" background="#FFFF00" />
                    <Box size="40" background="#FF00FF" />
                    <Box size="40" background="#00FFFF" />
                    <Box size="40" background="invalid-color" />
                </Column>
                """
        )
    }

    @Test
    fun percentageHeightAndWidthTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(0.1f)
                            .fillMaxHeight(0.7f)
                            .background(Color.Red))
                    Box(Modifier
                        .fillMaxWidth(0.15f)
                        .fillMaxHeight(0.5f)
                        .background(Color.Green))
                    Box(Modifier
                        .fillMaxWidth(0.25f)
                        .fillMaxHeight(0.4f)
                        .background(Color.Blue))
                    Box(Modifier
                        .fillMaxWidth(0.35f)
                        .fillMaxHeight(0.2f)
                        .background(Color.Cyan))
                    Box(Modifier
                        .fillMaxWidth(0.15f)
                        .fillMaxHeight(0.1f)
                        .background(Color.Yellow))
                }
            },
            template = """
                <Row height="50%" width="fill">
                    <Box width="10%" height="70%" background="system-red"/>
                    <Box width="15%" height="50%" background="system-green"/>
                    <Box width="25%" height="40%" background="system-blue"/>
                    <Box width="35%" height="20%" background="system-cyan"/>
                    <Box width="15%" height="10%" background="system-yellow"/>
                </Row>
                """
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class ColumnShotTest : LiveViewComposableTest() {
    @Composable
    private fun VerticalTestContent() {
        Box(Modifier.background(Color.Red)) {
            Text(text = "Red")
        }
        Box(Modifier.background(Color.Green)) {
            Text(text = "Green")
        }
        Box(Modifier.background(Color.Blue)) {
            Text(text = "Blue")
        }
    }

    private val contentHeight = 150
    private val verticalContentTestTemplate = """
            <Box background="#FFFF0000"><Text>Red</Text></Box>
            <Box background="#FF00FF00"><Text>Green</Text></Box>
            <Box background="#FF0000FF"><Text>Blue</Text></Box>
            """

    @Test
    fun simpleColumnTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Box(Modifier.background(Color.Red)) {
                        Text(text = "Red")
                    }
                    Box(Modifier.background(Color.Green)) {
                        Text(text = "Green")
                    }
                    Box(Modifier.background(Color.Blue)) {
                        Text(text = "Blue")
                    }
                }
            }, template = """
                <Column>
                    <Box background="#FFFF0000"><Text>Red</Text></Box>
                    <Box background="#FF00FF00"><Text>Green</Text></Box>
                    <Box background="#FF0000FF"><Text>Blue</Text></Box>
                </Column>
                """
        )
    }

    @Test
    fun columnWithVerticalArrangementTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Column(
                        modifier = Modifier.height(contentHeight.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier.height(contentHeight.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier.height(contentHeight.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier.height(contentHeight.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier.height(contentHeight.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        VerticalTestContent()
                    }
                }
            }, template = """
                <Row>
                  <Column height="$contentHeight" vertical-arrangement="bottom">
                    $verticalContentTestTemplate
                  </Column>
                  <Column height="$contentHeight" vertical-arrangement="center">
                    $verticalContentTestTemplate
                  </Column>
                  <Column height="$contentHeight" vertical-arrangement="spaceAround">
                    $verticalContentTestTemplate
                  </Column>
                  <Column height="$contentHeight" vertical-arrangement="spaceBetween">
                    $verticalContentTestTemplate
                  </Column>
                  <Column height="$contentHeight" vertical-arrangement="spaceEvenly">
                    $verticalContentTestTemplate
                  </Column>
                </Row>
                """
        )
    }

    @Test
    fun columnWithHorizontalAlignmentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(contentHeight.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(contentHeight.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VerticalTestContent()
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(contentHeight.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        VerticalTestContent()
                    }
                }

            },
            template = """
                <Row width="fill">
                  <Column weight="1" height="$contentHeight" horizontal-alignment="start">
                    $verticalContentTestTemplate
                  </Column>
                  <Column weight="1" height="$contentHeight" horizontal-alignment="center">
                    $verticalContentTestTemplate
                  </Column>
                  <Column weight="1" height="$contentHeight" horizontal-alignment="end">
                    $verticalContentTestTemplate
                  </Column>
                </Row>
                """
        )
    }

    @Test
    fun columnUsingWeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(
                    Modifier
                        .size(200.dp)
                        .background(Color.LightGray)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxWidth()
                            .weight(25f),
                    ) {
                        Text(text = "25%")
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Green)
                            .fillMaxWidth()
                            .weight(35f),
                    ) {
                        Text(text = "35%")
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxWidth()
                            .weight(40f),
                    ) {
                        Text(text = "40%")
                    }
                }
            },
            template = """
                <Column height="200" width="200" background="#FFCCCCCC">
                  <Box background="#FFFF0000" width="fill" weight="25" content-alignment="center">
                    <Text>25%</Text>
                  </Box>
                  <Box background="#FF00FF00" width="fill" weight="35" content-alignment="center">
                    <Text>35%</Text>
                  </Box>
                  <Box background="#FF0000FF" width="fill" weight="40" content-alignment="center">
                    <Text>40%</Text>
                  </Box>
                </Column>                
                """
        )
    }

    @Test
    fun columnWithIndividualAlignmentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.size(100.dp)) {
                    Text(text = "Start", Modifier.align(Alignment.Start))
                    Text(text = "Center", Modifier.align(Alignment.CenterHorizontally))
                    Text(text = "End", Modifier.align(Alignment.End))
                }
            },
            template = """
                <Column size="100">
                  <Text align="start">Start</Text>
                  <Text align="center">Center</Text>
                  <Text align="end">End</Text>
                </Column>
                """
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class RowShotTest : LiveViewComposableTest() {
    @Composable
    private fun HorizontalTestContent() {
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
    private val horizontalContentTestTemplate = """
            <Box background="#FFFF0000"><Text>Red</Text></Box>
            <Box background="#FF00FF00"><Text>Green</Text></Box>
            <Box background="#FF0000FF"><Text>Blue</Text></Box>
            """

    @Test
    fun simpleRowTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    HorizontalTestContent()
                }
            }, template = """
                <Row>
                    $horizontalContentTestTemplate
                </Row>
                """
        )
    }

    @Test
    fun rowWithHorizontalArrangementTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        HorizontalTestContent()
                    }
                }
            }, template = """
                <Column>
                  <Row width="fill" horizontal-arrangement="end">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" horizontal-arrangement="center">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" horizontal-arrangement="spaceAround">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" horizontal-arrangement="spaceBetween">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" horizontal-arrangement="spaceEvenly">
                    $horizontalContentTestTemplate
                  </Row>
                </Row>
                """
        )
    }

    @Test
    fun rowWithVerticalAlignmentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(contentHeight.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(contentHeight.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalTestContent()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(contentHeight.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        HorizontalTestContent()
                    }
                }

            },
            template = """
                <Column width="fill">
                  <Row width="fill" height="$contentHeight" vertical-alignment="top">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" height="$contentHeight" vertical-alignment="center">
                    $horizontalContentTestTemplate
                  </Row>
                  <Row width="fill" height="$contentHeight" vertical-alignment="bottom">
                    $horizontalContentTestTemplate
                  </Row>
                </Row>
                """
        )
    }

    @Test
    fun rowUsingWeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .height(200.dp)
                        .fillMaxWidth()
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
                <Row height="200" width="fill" background="#FFCCCCCC">
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
    fun rowWithIndividualAlignmentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)) {
                    Text(text = "Top", Modifier.align(Alignment.Top))
                    Text(text = "Center", Modifier.align(Alignment.CenterVertically))
                    Text(text = "Bottom", Modifier.align(Alignment.Bottom))
                }
            },
            template = """
                <Row width="fill" height="200">
                  <Text align="top">Top</Text>
                  <Text align="center">Center</Text>
                  <Text align="bottom">Bottom</Text>
                </Row>
                """
        )
    }
}
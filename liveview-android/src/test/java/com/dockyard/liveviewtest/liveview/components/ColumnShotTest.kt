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
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.row
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

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
            <$box $attrBackground="#FFFF0000"><$text>Red</$text></$box>
            <$box $attrBackground="#FF00FF00"><$text>Green</$text></$box>
            <$box $attrBackground="#FF0000FF"><$text>Blue</$text></$box>
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
                <$column>
                    <$box $attrBackground="#FFFF0000"><$text>Red</$text></$box>
                    <$box $attrBackground="#FF00FF00"><$text>Green</$text></$box>
                    <$box $attrBackground="#FF0000FF"><$text>Blue</$text></$box>
                </$column>
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
                <$row>
                  <$column $attrHeight="$contentHeight" 
                    $attrVerticalArrangement="${VerticalArrangementValues.bottom}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrHeight="$contentHeight" 
                    $attrVerticalArrangement="${VerticalArrangementValues.center}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrHeight="$contentHeight" 
                    $attrVerticalArrangement="${VerticalArrangementValues.spaceAround}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrHeight="$contentHeight" 
                    $attrVerticalArrangement="${VerticalArrangementValues.spaceBetween}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrHeight="$contentHeight" 
                    $attrVerticalArrangement="${VerticalArrangementValues.spaceEvenly}">
                    $verticalContentTestTemplate
                  </$column>
                </$row>
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
                <$row $attrWidth="fill">
                  <$column $attrWeight="1" $attrHeight="$contentHeight" 
                    $attrHorizontalAlignment="${HorizontalAlignmentValues.start}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrWeight="1" $attrHeight="$contentHeight" 
                    $attrHorizontalAlignment="${HorizontalAlignmentValues.centerHorizontally}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrWeight="1" $attrHeight="$contentHeight" 
                    $attrHorizontalAlignment="${HorizontalAlignmentValues.end}">
                    $verticalContentTestTemplate
                  </$column>
                </$row>
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
                <$column $attrHeight="200" $attrWidth="200" $attrBackground="#FFCCCCCC">
                  <$box $attrBackground="#FFFF0000" $attrWidth="$fill" $attrWeight="25" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>25%</$text>
                  </$box>
                  <$box $attrBackground="#FF00FF00" $attrWidth="$fill" $attrWeight="35" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>35%</$text>
                  </$box>
                  <$box $attrBackground="#FF0000FF" $attrWidth="$fill" $attrWeight="40" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>40%</$text>
                  </$box>
                </$column>                
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
                <$column $attrSize="100">
                  <$text $attrAlign="${HorizontalAlignmentValues.start}">Start</$text>
                  <$text $attrAlign="${HorizontalAlignmentValues.centerHorizontally}">Center</$text>
                  <$text $attrAlign="${HorizontalAlignmentValues.end}">End</$text>
                </$column>
                """
        )
    }
}
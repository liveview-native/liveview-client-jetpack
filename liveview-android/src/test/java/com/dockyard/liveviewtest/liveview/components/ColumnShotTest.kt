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
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeAlignment
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.text

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
            <$box $attrStyle="$modifierBackground($typeColor.$Red)"><$text>Red</$text></$box>
            <$box $attrStyle="$modifierBackground($typeColor.$Green)"><$text>Green</$text></$box>
            <$box $attrStyle="$modifierBackground($typeColor.$Blue)"><$text>Blue</$text></$box>
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
                    <$box $attrStyle="$modifierBackground($typeColor.$Red)"><$text>Red</$text></$box>
                    <$box $attrStyle="$modifierBackground($typeColor.$Green)"><$text>Green</$text></$box>
                    <$box $attrStyle="$modifierBackground($typeColor.$Blue)"><$text>Blue</$text></$box>
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
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight))"
                    $attrVerticalArrangement="${VerticalArrangementValues.bottom}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight))"
                    $attrVerticalArrangement="${VerticalArrangementValues.center}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight))" 
                    $attrVerticalArrangement="${VerticalArrangementValues.spaceAround}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight))" 
                    $attrVerticalArrangement="${VerticalArrangementValues.spaceBetween}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight))"
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
                <$row $attrStyle="$modifierFillMaxWidth()">
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight));$modifierWeight(1)" 
                    $attrHorizontalAlignment="${HorizontalAlignmentValues.start}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight));$modifierWeight(1)"
                    $attrHorizontalAlignment="${HorizontalAlignmentValues.centerHorizontally}">
                    $verticalContentTestTemplate
                  </$column>
                  <$column $attrStyle="$modifierHeight($typeDp($contentHeight));$modifierWeight(1)"
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
                <$column $attrStyle="$modifierSize($typeDp(200));$modifierBackground($typeColor.$LightGray)">
                  <$box $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Red);$modifierWeight(25)" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>25%</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Green);$modifierWeight(35)" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>35%</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Blue);$modifierWeight(40)" 
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
                <$column $attrStyle="$modifierSize($typeDp(100))">
                  <$text $attrStyle="$modifierAlign($typeAlignment.${HorizontalAlignmentValues.start})">Start</$text>
                  <$text $attrStyle="$modifierAlign($typeAlignment.${HorizontalAlignmentValues.centerHorizontally})">Center</$text>
                  <$text $attrStyle="$modifierAlign($typeAlignment.${HorizontalAlignmentValues.end})">End</$text>
                </$column>
                """
        )
    }
}
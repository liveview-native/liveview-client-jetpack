package org.phoenixframework.liveview.test.ui.view

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
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeAlignment
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

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
            <$box $attrStyle="$modifierBackground($typeColor.$Red)"><$text>Red</$text></$box>
            <$box $attrStyle="$modifierBackground($typeColor.$Green)"><$text>Green</$text></$box>
            <$box $attrStyle="$modifierBackground($typeColor.$Blue)"><$text>Blue</$text></$box>
            """

    @Test
    fun simpleRowTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    HorizontalTestContent()
                }
            }, template = """
                <$row>
                    $horizontalContentTestTemplate
                </$row>
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
                <$column>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth()"
                    $attrHorizontalArrangement="${HorizontalArrangementValues.end}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth()"
                    $attrHorizontalArrangement="${HorizontalArrangementValues.center}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth()"
                    $attrHorizontalArrangement="${HorizontalArrangementValues.spaceAround}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth()"
                    $attrHorizontalArrangement="${HorizontalArrangementValues.spaceBetween}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth()"
                    $attrHorizontalArrangement="${HorizontalArrangementValues.spaceEvenly}">
                    $horizontalContentTestTemplate
                  </$row>
                </$column>
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
                <$column $attrStyle="$modifierFillMaxWidth()">
                  <$row 
                    $attrStyle="$modifierFillMaxWidth();$modifierHeight($typeDp($contentHeight))"
                    $attrVerticalAlignment="${VerticalAlignmentValues.top}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth();$modifierHeight($typeDp($contentHeight))" 
                    $attrVerticalAlignment="${VerticalAlignmentValues.centerVertically}">
                    $horizontalContentTestTemplate
                  </$row>
                  <$row 
                    $attrStyle="$modifierFillMaxWidth();$modifierHeight($typeDp($contentHeight))"
                    $attrVerticalAlignment="${VerticalAlignmentValues.bottom}">
                    $horizontalContentTestTemplate
                  </$row>
                </$column>
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
                <$row $attrStyle="$modifierFillMaxWidth();$modifierHeight($typeDp(200));$modifierBackground($typeColor.$LightGray)">
                  <$box 
                    $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Red);$modifierWeight(25)"
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>25%</$text>
                  </$box>
                  <$box
                    $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Green);$modifierWeight(35)"
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>35%</$text>
                  </$box>
                  <$box 
                    $attrStyle="$modifierFillMaxWidth();$modifierBackground($typeColor.$Blue);$modifierWeight(40)" 
                    $attrContentAlignment="${AlignmentValues.center}">
                    <$text>40%</$text>
                  </$box>
                </$row>                
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
                        .height(200.dp)
                ) {
                    Text(text = "Top", Modifier.align(Alignment.Top))
                    Text(text = "Center", Modifier.align(Alignment.CenterVertically))
                    Text(text = "Bottom", Modifier.align(Alignment.Bottom))
                }
            },
            template = """
                <$row $attrStyle="$modifierFillMaxWidth();$modifierHeight($typeDp(200))">
                  <$text $attrStyle="$modifierAlign($typeAlignment.${VerticalAlignmentValues.top})">Top</$text>
                  <$text $attrStyle="$modifierAlign($typeAlignment.${VerticalAlignmentValues.centerVertically})">Center</$text>
                  <$text $attrStyle="$modifierAlign($typeAlignment.${VerticalAlignmentValues.bottom})">Bottom</$text>
                </$row>
                """
        )
    }
}
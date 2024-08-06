package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSpace
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.multiChoiceSegmentedButtonRow
import org.phoenixframework.liveview.data.constants.ComposableTypes.segmentedButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.singleChoiceSegmentedButtonRow
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.IconPrefixValues.outlined
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

@OptIn(ExperimentalMaterial3Api::class)
class SegmentedButtonShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSingleChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$singleChoiceSegmentedButtonRow>
                  <$segmentedButton $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$singleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun simpleMultiChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                MultiChoiceSegmentedButtonRow {
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(checked = false, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$multiChoiceSegmentedButtonRow>
                  <$segmentedButton $attrChecked="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="true">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$multiChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithSpaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                SingleChoiceSegmentedButtonRow(
                    space = 20.dp
                ) {
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$singleChoiceSegmentedButtonRow $attrSpace="20">
                  <$segmentedButton $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$singleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun multiChoiceSegmentedButtonWithSpaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                MultiChoiceSegmentedButtonRow(space = 20.dp) {
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(checked = false, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$multiChoiceSegmentedButtonRow $attrSpace="20">
                  <$segmentedButton $attrChecked="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="true">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$multiChoiceSegmentedButtonRow>
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithCustomShapeAndBorderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val border = BorderStroke(2.dp, Color.Magenta)
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape, border = border) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$singleChoiceSegmentedButtonRow>
                  <$segmentedButton $attrSelected="false" 
                    $attrShape="8" $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="true" $attrShape="8" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="false" $attrShape="8" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$singleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithCustomIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val border = BorderStroke(2.dp, Color.Magenta)
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(
                        selected = true, onClick = {}, shape = shape, border = border,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <$singleChoiceSegmentedButtonRow>
                  <$segmentedButton $attrSelected="false" 
                    $attrShape="8" $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="true" $attrShape="8" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$outlined:CheckCircle" />
                  </$segmentedButton>
                  <$segmentedButton $attrSelected="false" $attrShape="8" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                </$singleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun multiChoiceSegmentedButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrActiveContainerColor': '$Green',
            '$colorAttrActiveContentColor': '$Yellow',
            '$colorAttrActiveBorderColor': '$Blue',
            '$colorAttrInactiveContainerColor': '$Cyan',
            '$colorAttrInactiveContentColor': '$White',
            '$colorAttrInactiveBorderColor': '$Red',
            '$colorAttrDisabledActiveContainerColor': '$LightGray',
            '$colorAttrDisabledActiveContentColor': '$DarkGray',
            '$colorAttrDisabledActiveBorderColor': '$Magenta',
            '$colorAttrDisabledInactiveContainerColor': '$Gray',
            '$colorAttrDisabledInactiveContentColor': '$White',
            '$colorAttrDisabledInactiveBorderColor': '$Black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val colors = SegmentedButtonColors(
                    activeContainerColor = Color.Green,
                    activeContentColor = Color.Yellow,
                    activeBorderColor = Color.Blue,
                    inactiveContainerColor = Color.Cyan,
                    inactiveContentColor = Color.White,
                    inactiveBorderColor = Color.Red,
                    disabledActiveContainerColor = Color.LightGray,
                    disabledActiveContentColor = Color.DarkGray,
                    disabledActiveBorderColor = Color.Magenta,
                    disabledInactiveContainerColor = Color.Gray,
                    disabledInactiveContentColor = Color.White,
                    disabledInactiveBorderColor = Color.Black,
                )
                MultiChoiceSegmentedButtonRow {
                    SegmentedButton(
                        checked = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(
                        checked = true,
                        onCheckedChange = {}, shape = shape, colors = colors,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                    ) {
                        Text(text = "Option 3")
                    }
                    SegmentedButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 4")
                    }
                }
            },
            template = """
                <$multiChoiceSegmentedButtonRow>
                  <$segmentedButton $attrChecked="false" 
                    $attrShape="8" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="true" 
                    $attrShape="8" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$outlined:CheckCircle" />
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="false" $attrEnabled="false" 
                    $attrShape="8" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Option 3</$text>
                  </$segmentedButton>
                  <$segmentedButton $attrChecked="true" $attrEnabled="false" 
                    $attrShape="8" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Option 4</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$outlined:CheckCircle" />
                  </$segmentedButton>                  
                </$multiChoiceSegmentedButtonRow>            
                """
        )
    }
}
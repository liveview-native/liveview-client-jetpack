package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrChecked
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrWidth
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.constants.ComposableTypes.filledIconToggleButton
import org.phoenixframework.liveview.constants.ComposableTypes.filledTonalIconToggleButton
import org.phoenixframework.liveview.constants.ComposableTypes.icon
import org.phoenixframework.liveview.constants.ComposableTypes.iconToggleButton
import org.phoenixframework.liveview.constants.ComposableTypes.outlinedIconToggleButton
import org.phoenixframework.liveview.constants.ComposableTypes.row
import org.phoenixframework.liveview.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.constants.SystemColorValues.Green
import org.phoenixframework.liveview.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.constants.SystemColorValues.Red
import org.phoenixframework.liveview.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class IconToggleButtonShotTest : LiveViewComposableTest() {

    @Test
    fun simpleIconToggleButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    IconToggleButton(checked = true, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckCircleOutline, contentDescription = "")
                    }
                    IconToggleButton(checked = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckCircleOutline, contentDescription = "")
                    }
                    IconToggleButton(checked = true, enabled = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckCircleOutline, contentDescription = "")
                    }
                    IconToggleButton(checked = false, enabled = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckCircleOutline, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$iconToggleButton $attrChecked="true">
                    <$icon $attrImageVector="filled:CheckCircleOutline" />
                  </$iconToggleButton>
                  <$iconToggleButton $attrChecked="false">
                    <$icon $attrImageVector="filled:CheckCircleOutline" />
                  </$iconToggleButton>                    
                  <$iconToggleButton $attrChecked="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:CheckCircleOutline" />
                  </$iconToggleButton>    
                  <$iconToggleButton $attrChecked="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:CheckCircleOutline" />
                  </$iconToggleButton>                                      
                </$row>
                """
        )
    }

    @Test
    fun iconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Red', 
            '$colorAttrContentColor': '$Yellow', 
            '$colorAttrDisabledContainerColor': '$LightGray', 
            '$colorAttrDisabledContentColor': '$DarkGray', 
            '$colorAttrCheckedContainerColor': '$Blue', 
            '$colorAttrCheckedContentColor': '$Green'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = IconButtonDefaults.iconToggleButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Yellow,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray,
                    checkedContainerColor = Color.Blue,
                    checkedContentColor = Color.Green,
                )
                Row {
                    IconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                    }
                    IconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                    }
                    IconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                    }
                    IconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$iconToggleButton $attrChecked="true" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:CheckCircle" />
                  </$iconToggleButton>
                  <$iconToggleButton $attrChecked="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:CheckCircle" />
                  </$iconToggleButton>
                  <$iconToggleButton $attrChecked="true" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:CheckCircle" />
                  </$iconToggleButton>
                  <$iconToggleButton $attrChecked="false" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:CheckCircle" />
                  </$iconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleFilledIconToggleButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledIconToggleButton(checked = true, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckBox, contentDescription = "")
                    }
                    FilledIconToggleButton(checked = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckBox, contentDescription = "")
                    }
                    FilledIconToggleButton(checked = true, enabled = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckBox, contentDescription = "")
                    }
                    FilledIconToggleButton(checked = false, enabled = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.CheckBox, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledIconToggleButton $attrChecked="true">
                    <$icon $attrImageVector="filled:CheckBox" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrChecked="false">
                    <$icon $attrImageVector="filled:CheckBox" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrChecked="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:CheckBox" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrChecked="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:CheckBox" />
                  </$filledIconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun filledIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Red', 
            '$colorAttrContentColor': '$Yellow', 
            '$colorAttrDisabledContainerColor': '$LightGray', 
            '$colorAttrDisabledContentColor': '$DarkGray', 
            '$colorAttrCheckedContainerColor': '$Blue', 
            '$colorAttrCheckedContentColor': '$Green'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = IconButtonDefaults.filledIconToggleButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Yellow,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray,
                    checkedContainerColor = Color.Blue,
                    checkedContentColor = Color.Green,
                )
                Row {
                    FilledIconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                    }
                    FilledIconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                    }
                    FilledIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                    }
                    FilledIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledIconToggleButton $attrShape="8" $attrChecked="true"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrShape="8" $attrChecked="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrShape="8" $attrChecked="true" $attrEnabled="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" />
                  </$filledIconToggleButton>
                  <$filledIconToggleButton $attrShape="8" $attrChecked="false" $attrEnabled="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" />
                  </$filledIconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleFilledTonalIconToggleButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledTonalIconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                    ) {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                    ) {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                    ) {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                    ) {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledTonalIconToggleButton $attrChecked="true">
                    <$icon $attrImageVector="filled:Cancel" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrChecked="false">
                    <$icon $attrImageVector="filled:Cancel" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrChecked="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Cancel" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrChecked="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Cancel" />
                  </$filledTonalIconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun filledTonalIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Red', 
            '$colorAttrContentColor': '$Yellow', 
            '$colorAttrDisabledContainerColor': '$LightGray', 
            '$colorAttrDisabledContentColor': '$DarkGray', 
            '$colorAttrCheckedContainerColor': '$Blue', 
            '$colorAttrCheckedContentColor': '$Green'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Yellow,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray,
                    checkedContainerColor = Color.Blue,
                    checkedContentColor = Color.Green,
                )
                Row {
                    FilledTonalIconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                    }
                    FilledTonalIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledTonalIconToggleButton $attrShape="8" $attrChecked="true"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AddCircle" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrShape="8" $attrChecked="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AddCircle" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrShape="8" $attrChecked="true" $attrEnabled="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AddCircle" />
                  </$filledTonalIconToggleButton>
                  <$filledTonalIconToggleButton $attrShape="8" $attrChecked="false" $attrEnabled="false"
                    $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AddCircle" />
                  </$filledTonalIconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleOutlinedIconToggleButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconToggleButton(checked = true, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconToggleButton(checked = false, onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconToggleButton $attrChecked="true">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrChecked="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrChecked="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconToggleButton>   
                  <$outlinedIconToggleButton $attrChecked="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconToggleButton>                                      
                </$row>
                """
        )
    }

    @Test
    fun outlinedIconToggleButtonWithCustomBorderTest() {
        val borderForTemplate = "{'$attrWidth': '2', '$attrColor': '$Magenta'}"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val border = BorderStroke(2.dp, Color.Magenta)
                Row {
                    OutlinedIconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                        border = border
                    ) {
                        Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                        border = border
                    ) {
                        Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        border = border
                    ) {
                        Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        border = border
                    ) {
                        Icon(imageVector = Icons.Filled.FilterAlt, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconToggleButton $attrChecked="true" $attrBorder="$borderForTemplate">
                    <$icon $attrImageVector="filled:FilterAlt" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrChecked="false" $attrBorder="$borderForTemplate">
                    <$icon $attrImageVector="filled:FilterAlt" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrChecked="true" $attrEnabled="false" $attrBorder="$borderForTemplate">
                    <$icon $attrImageVector="filled:FilterAlt" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrChecked="false" $attrEnabled="false" $attrBorder="$borderForTemplate">
                    <$icon $attrImageVector="filled:FilterAlt" />
                  </$outlinedIconToggleButton>                    
                </$row>
                """
        )
    }

    @Test
    fun outlinedIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Red', 
            '$colorAttrContentColor': '$Yellow', 
            '$colorAttrDisabledContainerColor': '$LightGray', 
            '$colorAttrDisabledContentColor': '$DarkGray', 
            '$colorAttrCheckedContainerColor': '$Blue', 
            '$colorAttrCheckedContentColor': '$Green'
            }
            """.toJsonForTemplate()
        val borderForTemplate = "{'$attrWidth': '2', '$attrColor': '$Magenta'}"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val border = BorderStroke(2.dp, Color.Magenta)
                val shape = RoundedCornerShape(8.dp)
                val colors = IconButtonDefaults.outlinedIconToggleButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Yellow,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray,
                    checkedContainerColor = Color.Blue,
                    checkedContentColor = Color.Green,
                )
                Row {
                    OutlinedIconToggleButton(
                        checked = true,
                        onCheckedChange = {},
                        shape = shape,
                        border = border,
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.NearMe, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = false,
                        onCheckedChange = {},
                        shape = shape,
                        border = border,
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.NearMe, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        border = border,
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.NearMe, contentDescription = "")
                    }
                    OutlinedIconToggleButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        border = border,
                        colors = colors,
                    ) {
                        Icon(imageVector = Icons.Filled.NearMe, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconToggleButton $attrShape="8" $attrChecked="true"
                    $attrBorder="$borderForTemplate" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:NearMe" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrShape="8" $attrChecked="false"
                    $attrBorder="$borderForTemplate" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:NearMe" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrShape="8" $attrChecked="true" $attrEnabled="false"
                    $attrBorder="$borderForTemplate" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:NearMe" />
                  </$outlinedIconToggleButton>
                  <$outlinedIconToggleButton $attrShape="8" $attrChecked="false" $attrEnabled="false"
                    $attrBorder="$borderForTemplate" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:NearMe" />
                  </$outlinedIconToggleButton>                    
                </$row>
                """
        )
    }
}
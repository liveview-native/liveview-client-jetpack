package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <Row>
                    <IconToggleButton phx-change="" checked="true">
                        <Icon image-vector="filled:CheckCircleOutline" />
                    </IconToggleButton>
                    <IconToggleButton phx-change="" checked="false">
                        <Icon image-vector="filled:CheckCircleOutline" />
                    </IconToggleButton>                    
                    <IconToggleButton phx-change="" checked="true" enabled="false">
                        <Icon image-vector="filled:CheckCircleOutline" />
                    </IconToggleButton>    
                    <IconToggleButton phx-change="" checked="false" enabled="false">
                        <Icon image-vector="filled:CheckCircleOutline" />
                    </IconToggleButton>                                      
                </Row>
                """
        )
    }

    @Test
    fun iconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-red', 
                'contentColor': 'system-yellow', 
                'disabledContainerColor': 'system-light-gray', 
                'disabledContentColor': 'system-dark-gray', 
                'checkedContainerColor': 'system-blue', 
                'checkedContentColor': 'system-green'
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
                <Row>
                    <IconToggleButton phx-change="" checked="true" colors="$colorsForTemplate">
                        <Icon image-vector="filled:CheckCircle" />
                    </IconToggleButton>
                    <IconToggleButton phx-change="" checked="false" colors="$colorsForTemplate">
                        <Icon image-vector="filled:CheckCircle" />
                    </IconToggleButton>
                    <IconToggleButton phx-change="" checked="true" enabled="false" colors="$colorsForTemplate">
                        <Icon image-vector="filled:CheckCircle" />
                    </IconToggleButton>
                    <IconToggleButton phx-change="" checked="false" enabled="false" colors="$colorsForTemplate">
                        <Icon image-vector="filled:CheckCircle" />
                    </IconToggleButton>                    
                </Row>
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
                <Row>
                    <FilledIconToggleButton phx-change="" checked="true">
                        <Icon image-vector="filled:CheckBox" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" checked="false">
                        <Icon image-vector="filled:CheckBox" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" checked="true" enabled="false">
                        <Icon image-vector="filled:CheckBox" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" checked="false" enabled="false">
                        <Icon image-vector="filled:CheckBox" />
                    </FilledIconToggleButton>                    
                </Row>
                """
        )
    }

    @Test
    fun filledIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-red', 
                'contentColor': 'system-yellow', 
                'disabledContainerColor': 'system-light-gray', 
                'disabledContentColor': 'system-dark-gray', 
                'checkedContainerColor': 'system-blue', 
                'checkedContentColor': 'system-green'
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
                <Row>
                    <FilledIconToggleButton phx-change="" shape="8" checked="true"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:Check" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" shape="8" checked="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:Check" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" shape="8" checked="true" enabled="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:Check" />
                    </FilledIconToggleButton>
                    <FilledIconToggleButton phx-change="" shape="8" checked="false" enabled="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:Check" />
                    </FilledIconToggleButton>                    
                </Row>
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
                <Row>
                    <FilledTonalIconToggleButton phx-change="" checked="true">
                        <Icon image-vector="filled:Cancel" />
                    </FilledTonalIconButton>
                    <FilledTonalIconToggleButton phx-change="" checked="false">
                        <Icon image-vector="filled:Cancel" />
                    </FilledTonalIconButton>
                    <FilledTonalIconToggleButton phx-change="" checked="true" enabled="false">
                        <Icon image-vector="filled:Cancel" />
                    </FilledTonalIconButton>
                    <FilledTonalIconToggleButton phx-change="" checked="false" enabled="false">
                        <Icon image-vector="filled:Cancel" />
                    </FilledTonalIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun filledTonalIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-red', 
                'contentColor': 'system-yellow', 
                'disabledContainerColor': 'system-light-gray', 
                'disabledContentColor': 'system-dark-gray', 
                'checkedContainerColor': 'system-blue', 
                'checkedContentColor': 'system-green'
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
                <Row>
                    <FilledTonalIconToggleButton phx-change="" shape="8" checked="true"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:AddCircle" />
                    </FilledTonalIconToggleButton>
                    <FilledTonalIconToggleButton phx-change="" shape="8" checked="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:AddCircle" />
                    </FilledTonalIconToggleButton>
                    <FilledTonalIconToggleButton phx-change="" shape="8" checked="true" enabled="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:AddCircle" />
                    </FilledTonalIconToggleButton>
                    <FilledTonalIconToggleButton phx-change="" shape="8" checked="false" enabled="false"
                      colors="$colorsForTemplate">
                        <Icon image-vector="filled:AddCircle" />
                    </FilledTonalIconToggleButton>                    
                </Row>
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
                <Row>
                    <OutlinedIconToggleButton phx-change="" checked="true">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconToggleButton>
                    <OutlinedIconToggleButton phx-change="" checked="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconToggleButton>
                    <OutlinedIconToggleButton phx-change="" checked="true" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconToggleButton>   
                    <OutlinedIconToggleButton phx-change="" checked="false" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconToggleButton>                                      
                </Row>
                """
        )
    }

    @Test
    fun outlinedIconToggleButtonWithCustomBorderTest() {
        val borderForTemplate = "{'width': '2', 'color': 'system-magenta'}"
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
                <Row>
                    <OutlinedIconToggleButton phx-change="" checked="true" border="$borderForTemplate">
                        <Icon image-vector="filled:FilterAlt" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" checked="false" border="$borderForTemplate">
                        <Icon image-vector="filled:FilterAlt" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" checked="true" enabled="false" border="$borderForTemplate">
                        <Icon image-vector="filled:FilterAlt" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" checked="false" enabled="false" border="$borderForTemplate">
                        <Icon image-vector="filled:FilterAlt" />
                    </OutlinedIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun outlinedIconToggleButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-red', 
                'contentColor': 'system-yellow', 
                'disabledContainerColor': 'system-light-gray', 
                'disabledContentColor': 'system-dark-gray', 
                'checkedContainerColor': 'system-blue', 
                'checkedContentColor': 'system-green'
            }
            """.toJsonForTemplate()
        val borderForTemplate = "{'width': '2', 'color': 'system-magenta'}"
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
                <Row>
                    <OutlinedIconToggleButton phx-change="" shape="8" checked="true"
                      border="$borderForTemplate" colors="$colorsForTemplate">
                        <Icon image-vector="filled:NearMe" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" shape="8" checked="false"
                      border="$borderForTemplate" colors="$colorsForTemplate">
                        <Icon image-vector="filled:NearMe" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" shape="8" checked="true" enabled="false"
                      border="$borderForTemplate" colors="$colorsForTemplate">
                        <Icon image-vector="filled:NearMe" />
                    </OutlinedIconButton>
                    <OutlinedIconToggleButton phx-change="" shape="8" checked="false" enabled="false"
                      border="$borderForTemplate" colors="$colorsForTemplate">
                        <Icon image-vector="filled:NearMe" />
                    </OutlinedIconButton>                    
                </Row>
                """
        )
    }
}
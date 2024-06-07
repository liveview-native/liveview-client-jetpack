package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedTrackColor
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.Templates.templateThumb
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.row
import org.phoenixframework.liveview.domain.base.ComposableTypes.switch

class SwitchShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSwitchTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Switch(checked = true, onCheckedChange = {})
                    Switch(checked = false, onCheckedChange = {})
                    Switch(checked = true, enabled = false, onCheckedChange = {})
                    Switch(checked = false, enabled = false, onCheckedChange = {})
                }
            }, template = """
                <$row>
                  <$switch $attrChecked="true" />
                  <$switch $attrChecked="false" />
                  <$switch $attrChecked="true" $attrEnabled="false" />
                  <$switch $attrChecked="false" $attrEnabled="false" />                  
                </$row>  
                """
        )
    }

    @Test
    fun switchWithCustomThumbTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Switch(
                        checked = true, onCheckedChange = {},
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(checked = false, onCheckedChange = {}, thumbContent = {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    })
                    Switch(
                        checked = true, enabled = false, onCheckedChange = {},
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(checked = false, enabled = false, onCheckedChange = {}, thumbContent = {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    })
                }
            }, template = """
                <$row>
                  <$switch $attrChecked="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="false">
                    <$icon $attrImageVector="filled:Cancel" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Cancel" $attrTemplate="$templateThumb" />
                  </$switch>                  
                </$row>  
                """
        )
    }

    @Test
    fun switchWithCustomColorsTest() {
        val colorsForTemplate = """
            {            
            '$colorAttrCheckedThumbColor': '#FFFFFF00', 
            '$colorAttrCheckedTrackColor': '#FFFFFFFF', 
            '$colorAttrCheckedBorderColor': '#FFFF0000', 
            '$colorAttrCheckedIconColor': '#FF00FF00', 
            '$colorAttrUncheckedThumbColor': '#FF888888', 
            '$colorAttrUncheckedTrackColor': '#FFCCCCCC', 
            '$colorAttrUncheckedBorderColor': '#FF444444', 
            '$colorAttrUncheckedIconColor': '#FF000000', 
            '$colorAttrDisabledCheckedThumbColor': '#FFFF00FF', 
            '$colorAttrDisabledCheckedTrackColor': '#FF00FFFF', 
            '$colorAttrDisabledCheckedBorderColor': '#FF0000FF', 
            '$colorAttrDisabledCheckedIconColor': '#FFFF0000', 
            '$colorAttrDisabledUncheckedThumbColor': '#FFFF0000', 
            '$colorAttrDisabledUncheckedTrackColor': '#FF0000FF', 
            '$colorAttrDisabledUncheckedBorderColor': '#FF00FF00', 
            '$colorAttrDisabledUncheckedIconColor': '#FF888888'    
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Yellow,
                    checkedTrackColor = Color.White,
                    checkedBorderColor = Color.Red,
                    checkedIconColor = Color.Green,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray,
                    uncheckedBorderColor = Color.DarkGray,
                    uncheckedIconColor = Color.Black,
                    disabledCheckedThumbColor = Color.Magenta,
                    disabledCheckedTrackColor = Color.Cyan,
                    disabledCheckedBorderColor = Color.Blue,
                    disabledCheckedIconColor = Color.Red,
                    disabledUncheckedThumbColor = Color.Red,
                    disabledUncheckedTrackColor = Color.Blue,
                    disabledUncheckedBorderColor = Color.Green,
                    disabledUncheckedIconColor = Color.Gray
                )
                Row(Modifier.padding(16.dp)) {
                    Switch(
                        checked = true, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = true, enabled = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = false, enabled = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                        },
                    )
                }
            }, template = """
                <$row $attrStyle="$modifierPadding($typeDp(16))">
                  <$switch $attrChecked="true" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Cancel" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="true" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateThumb" />
                  </$switch>
                  <$switch $attrChecked="false" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Cancel" $attrTemplate="$templateThumb" />
                  </$switch>                  
                </$row>  
                """
        )
    }
}
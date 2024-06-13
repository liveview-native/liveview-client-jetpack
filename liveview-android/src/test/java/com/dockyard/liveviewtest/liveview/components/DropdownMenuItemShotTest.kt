package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.constants.ComposableTypes.dropdownMenuItem
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.text

class DropdownMenuItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleDropdownMenuItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DropdownMenuItem(
                    text = {
                        Text(text = "Simple Menu Item")
                    },
                    onClick = {}
                )
            },
            template = """
                <$dropdownMenuItem>
                    <$text>Simple Menu Item</$text>
                </$dropdownMenuItem>
                """
        )
    }

    @Test
    fun dropdownWithLeadingAndTrailingTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DropdownMenuItem(
                    text = {
                        Text(text = "Simple Menu Item")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "")
                    },
                    onClick = {}
                )
            },
            template = """
                <$dropdownMenuItem>
                  <$text>Simple Menu Item</$text>
                  <$icon $attrImageVector="$filled:Add" $attrTemplate="$templateTrailingIcon" />
                  <$icon $attrImageVector="$filled:ChevronLeft" $attrTemplate="$templateLeadingIcon"/>                    
                </$dropdownMenuItem>
                """
        )
    }

    @Test
    fun dropdownWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrTextColor': '#FF444444', 
            '$colorAttrLeadingIconColor': '#FFFF0000', 
            '$colorAttrTrailingIconColor': '#FF0000FF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DropdownMenuItem(
                    text = {
                        Text(text = "Simple Menu Item")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "")
                    },
                    onClick = {},
                    colors = MenuDefaults.itemColors(
                        textColor = Color.DarkGray,
                        leadingIconColor = Color.Red,
                        trailingIconColor = Color.Blue,
                    )
                )
            },
            template = """
                <$dropdownMenuItem $attrColors="$colorsForTemplate">
                  <$text>Simple Menu Item</$text>
                  <$icon $attrImageVector="$filled:Add" $attrTemplate="$templateTrailingIcon" />
                  <$icon $attrImageVector="$filled:ChevronLeft" $attrTemplate="$templateLeadingIcon"/>                    
                </$dropdownMenuItem>
                """
        )
    }

    @Test
    fun dropdownWithCustomColorsDisabledTest() {
        val colorsForTemplate = """
            {
            '$colorAttrDisabledTextColor': '#FFCCCCCC', 
            '$colorAttrDisabledLeadingIconColor': '#FF888888', 
            '$colorAttrDisabledTrailingIconColor': '#FF444444'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DropdownMenuItem(
                    text = {
                        Text(text = "Simple Menu Item")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "")
                    },
                    onClick = {},
                    colors = MenuDefaults.itemColors(
                        disabledTextColor = Color.LightGray,
                        disabledLeadingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.DarkGray,
                    ),
                    enabled = false
                )
            },
            template = """
                <$dropdownMenuItem $attrColors="$colorsForTemplate" $attrEnabled="false">
                  <$text>Simple Menu Item</$text>
                  <$icon $attrImageVector="$filled:Add" $attrTemplate="$templateTrailingIcon" />
                  <$icon $attrImageVector="$filled:ChevronLeft" $attrTemplate="$templateLeadingIcon"/>                    
                </$dropdownMenuItem>
                """
        )
    }
}
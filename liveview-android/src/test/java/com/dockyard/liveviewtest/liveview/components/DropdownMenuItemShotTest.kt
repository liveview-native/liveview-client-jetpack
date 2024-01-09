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
                <DropdownMenuItem>
                    <Text>Simple Menu Item</Text>
                </DropdownMenuItem>
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
                <DropdownMenuItem>
                    <Text>Simple Menu Item</Text>
                    <Icon image-vector="filled:Add" template="trailingIcon" />
                    <Icon image-vector="filled:ChevronLeft" template="leadingIcon"/>                    
                </DropdownMenuItem>
                """
        )
    }

    @Test
    fun dropdownWithCustomColorsTest() {
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
                <DropdownMenuItem colors="{'textColor': '#FF444444', 'leadingIconColor': '#FFFF0000', 'trailingIconColor': '#FF0000FF'}">
                    <Text>Simple Menu Item</Text>
                    <Icon image-vector="filled:Add" template="trailingIcon" />
                    <Icon image-vector="filled:ChevronLeft" template="leadingIcon"/>                    
                </DropdownMenuItem>
                """
        )
    }

    @Test
    fun dropdownWithCustomColorsDisabledTest() {
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
                <DropdownMenuItem colors="{'disabledTextColor': '#FFCCCCCC', 'disabledLeadingIconColor': '#FF888888', 'disabledTrailingIconColor': '#FF444444'}" enabled="false">
                    <Text>Simple Menu Item</Text>
                    <Icon image-vector="filled:Add" template="trailingIcon" />
                    <Icon image-vector="filled:ChevronLeft" template="leadingIcon"/>                    
                </DropdownMenuItem>
                """
        )
    }
}
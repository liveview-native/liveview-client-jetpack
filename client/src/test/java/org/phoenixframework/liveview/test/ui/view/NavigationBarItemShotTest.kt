package org.phoenixframework.liveview.test.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrAlwaysShowLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrIndicatorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedTextColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationBar
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationBarItem
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredFilled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel

class NavigationBarItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleNavigationBarItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationBar {
                    NavigationBarItem(
                        selected = true, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.HorizontalDistribute,
                                contentDescription = ""
                            )
                        },
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.VerticalDistribute,
                                contentDescription = ""
                            )
                        },
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                    )
                }
            },
            template = """
                <$navigationBar>
                  <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                    <$icon $attrImageVector="$filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                    <$icon $attrImageVector="$filled:VerticalDistribute" $attrTemplate="$templateIcon"/>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="2">
                    <$icon $attrImageVector="$filled:Add" $attrTemplate="$templateIcon"/>
                  </$navigationBarItem>                      
                </$navigationBar>
                """
        )
    }

    @Test
    fun navigationBarItemWithLabelTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationBar {
                    NavigationBarItem(
                        selected = true, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Home")
                        }
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Account")
                        }
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Settings")
                        }
                    )
                }
            },
            template = """
                <$navigationBar>
                  <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                    <$icon $attrImageVector="$filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                    <$icon $attrImageVector="$filled:AccountCircle" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Account</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="2">
                    <$icon $attrImageVector="$filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationBarItem>                      
                </$navigationBar>
                """
        )
    }

    @Test
    fun navigationBarItemWithOnlySelectedLabelTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationBar {
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        selected = true, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Home")
                        }
                    )
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Account")
                        }
                    )
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Settings")
                        }
                    )
                }
            },
            template = """
                <$navigationBar>
                  <$navigationBarItem $attrAlwaysShowLabel="false" $attrSelected="true" $attrPhxValue="0">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrAlwaysShowLabel="false" $attrSelected="false" $attrPhxValue="1">
                    <$icon $attrImageVector="filled:AccountCircle" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Account</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrAlwaysShowLabel="false" $attrSelected="false" $attrPhxValue="2">
                    <$icon $attrImageVector="filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationBarItem>                      
                </$navigationBar>
                """
        )
    }

    @Test
    fun navigationBarItemWithCustomColorsTest() {
        val templateColors = """ 
            {
            '$colorAttrSelectedIconColor': '#FF00FF00',
            '$colorAttrSelectedTextColor': '#FFFF0000',
            '$colorAttrIndicatorColor': '#FFFFFF00',
            '$colorAttrUnselectedIconColor': '#FF00FFFF',
            '$colorAttrUnselectedTextColor': '#FFFF00FF',
            '$colorAttrDisabledIconColor': '#FFCCCCCC',
            '$colorAttrDisabledTextColor': '#FF888888'
            }
            """.toJsonForTemplate()

        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Green,
                    selectedTextColor = Color.Red,
                    indicatorColor = Color.Yellow,
                    unselectedIconColor = Color.Cyan,
                    unselectedTextColor = Color.Magenta,
                    disabledIconColor = Color.LightGray,
                    disabledTextColor = Color.Gray,
                )
                NavigationBar {
                    NavigationBarItem(
                        colors = colors,
                        selected = true, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Home")
                        }
                    )
                    NavigationBarItem(
                        colors = colors,
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Account")
                        }
                    )
                    NavigationBarItem(
                        colors = colors,
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Settings")
                        }
                    )
                    NavigationBarItem(
                        colors = colors,
                        enabled = false,
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Exit")
                        }
                    )
                }
            },
            template = """
                <$navigationBar>
                  <$navigationBarItem $attrSelected="true" $attrPhxValue="0" $attrColors="$templateColors">
                    <$icon $attrImageVector="$filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="1" $attrColors="$templateColors">
                    <$icon $attrImageVector="$filled:AccountCircle" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Account</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="2" $attrColors="$templateColors">
                    <$icon $attrImageVector="$filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationBarItem>     
                  <$navigationBarItem $attrSelected="false" $attrEnabled="false" $attrPhxValue="3" $attrColors="$templateColors">
                    <$icon $attrImageVector="$autoMirroredFilled:ExitToApp" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Exit</$text>
                  </$navigationBarItem>                                     
                </$navigationBar>
                """
        )
    }
}
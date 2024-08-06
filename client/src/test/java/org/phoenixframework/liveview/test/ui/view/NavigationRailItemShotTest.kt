package org.phoenixframework.liveview.test.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrAlwaysShowLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
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
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationRail
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationRailItem
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel

class NavigationRailItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleNavigationRailItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail {
                    NavigationRailItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$navigationRailItem $attrSelected="true">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>   
                  <$navigationRailItem $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Settings" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>                                                      
                </$navigationRail>
                """
        )
    }

    @Test
    fun navigationRailItemWithLabelTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail {
                    NavigationRailItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                        label = {
                            Text(text = "Favorites")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        label = {
                            Text(text = "Home")
                        },
                    )
                    NavigationRailItem(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                        label = {
                            Text(text = "Person")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
                        },
                        label = {
                            Text(text = "Settings")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$navigationRailItem $attrSelected="true">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Favorites</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Person</$text>
                  </$navigationRailItem>   
                  <$navigationRailItem $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationRailItem>                                                      
                </$navigationRail>
                """
        )
    }

    @Test
    fun navigationRailItemWithOptionalLabelTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail {
                    NavigationRailItem(
                        alwaysShowLabel = false,
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                        label = {
                            Text(text = "Favorites")
                        },
                    )
                    NavigationRailItem(
                        alwaysShowLabel = false,
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        label = {
                            Text(text = "Home")
                        },
                    )
                    NavigationRailItem(
                        alwaysShowLabel = false,
                        selected = true,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                        label = {
                            Text(text = "Person")
                        },
                    )
                    NavigationRailItem(
                        alwaysShowLabel = false,
                        selected = false,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
                        },
                        label = {
                            Text(text = "Settings")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$navigationRailItem $attrSelected="true" $attrAlwaysShowLabel="false">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Favorites</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false" $attrAlwaysShowLabel="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="true" $attrEnabled="false" $attrAlwaysShowLabel="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Person</$text>
                  </$navigationRailItem>   
                  <$navigationRailItem $attrSelected="false" $attrEnabled="false" $attrAlwaysShowLabel="false">
                    <$icon $attrImageVector="filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationRailItem>                                                      
                </$navigationRail>
                """
        )
    }

    @Test
    fun navigationRailItemWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrSelectedIconColor': '$White',
            '$colorAttrSelectedTextColor': '$Yellow',
            '$colorAttrIndicatorColor': '$Cyan',
            '$colorAttrUnselectedIconColor': '$DarkGray',
            '$colorAttrUnselectedTextColor': '$Gray',
            '$colorAttrDisabledIconColor': '$LightGray',
            '$colorAttrDisabledTextColor': '$LightGray'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.Yellow,
                    indicatorColor = Color.Cyan,
                    unselectedIconColor = Color.DarkGray,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.LightGray,
                    disabledTextColor = Color.LightGray,
                )
                NavigationRail {
                    NavigationRailItem(
                        colors = colors,
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                        label = {
                            Text(text = "Favorites")
                        },
                    )
                    NavigationRailItem(
                        colors = colors,
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        label = {
                            Text(text = "Home")
                        },
                    )
                    NavigationRailItem(
                        colors = colors,
                        selected = true,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                        label = {
                            Text(text = "Person")
                        },
                    )
                    NavigationRailItem(
                        colors = colors,
                        selected = false,
                        enabled = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
                        },
                        label = {
                            Text(text = "Settings")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$navigationRailItem $attrSelected="true" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Favorites</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Home</$text>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="true" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Person</$text>
                  </$navigationRailItem>   
                  <$navigationRailItem $attrSelected="false" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Settings" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Settings</$text>
                  </$navigationRailItem>                                                      
                </$navigationRail>
                """
        )
    }
}
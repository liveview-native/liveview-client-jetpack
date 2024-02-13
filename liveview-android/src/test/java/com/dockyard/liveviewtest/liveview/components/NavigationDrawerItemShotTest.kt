package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedBadgeColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedBadgeColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedTextColor
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredFilled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.Templates.templateBadge
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.modalDrawerSheet
import org.phoenixframework.liveview.domain.base.ComposableTypes.navigationDrawerItem
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

class NavigationDrawerItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleNavigationDrawerItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true,
                        onClick = { }
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false,
                        onClick = { }
                    )
                }
            },
            template = """
                <$modalDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$navigationDrawerItem> 
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$navigationDrawerItem>                   
                </$modalDrawerSheet>               
                """
        )
    }

    @Test
    fun navigationDrawerItemWithIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        selected = true,
                        onClick = { }
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        selected = false,
                        onClick = { }
                    )
                }
            },
            template = """
                <$modalDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                    <$icon $attrImageVector="$filled:AccountCircle" $attrTemplate="$templateIcon" />
                  </$navigationDrawerItem> 
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                    <$icon $attrImageVector="$autoMirroredFilled:ExitToApp" $attrTemplate="$templateIcon" />
                  </$navigationDrawerItem>                   
                </$modalDrawerSheet>               
                """
        )
    }

    @Test
    fun navigationDrawerItemWithIconAndBadgeTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        badge = {
                            Text(text = "99+")
                        },
                        selected = true,
                        onClick = { }
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        badge = {
                            Text(text = "None")
                        },
                        selected = false,
                        onClick = { }
                    )
                }
            },
            template = """
                <$modalDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                    <$icon $attrImageVector="$filled:AccountCircle" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateBadge">99+</$text>
                  </$navigationDrawerItem> 
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                    <$icon $attrImageVector="$autoMirroredFilled:ExitToApp" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateBadge">None</$text>
                  </$navigationDrawerItem>                   
                </$modalDrawerSheet>               
                """
        )
    }

    @Test
    fun navigationDrawerItemWithCustomColors() {
        val templateColors = """ 
            {
            '$colorAttrSelectedContainerColor': '#FFFF0000',
            '$colorAttrUnselectedContainerColor': '#FF888888',
            '$colorAttrSelectedIconColor': '#FF00FF00',
            '$colorAttrUnselectedIconColor': '#FFCCCCCC',
            '$colorAttrSelectedTextColor': '#FF0000FF',
            '$colorAttrUnselectedTextColor': '#FF444444',
            '$colorAttrSelectedBadgeColor': '#FFFFFF00',
            '$colorAttrUnselectedBadgeColor': '#FFFF00FF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Red,
                    unselectedContainerColor = Color.Gray,
                    selectedIconColor = Color.Green,
                    unselectedIconColor = Color.LightGray,
                    selectedTextColor = Color.Blue,
                    unselectedTextColor = Color.DarkGray,
                    selectedBadgeColor = Color.Yellow,
                    unselectedBadgeColor = Color.Magenta
                )
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        badge = {
                            Text(text = "99+")
                        },
                        selected = true,
                        onClick = { },
                        colors = colors,
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        badge = {
                            Text(text = "None")
                        },
                        selected = false,
                        onClick = { },
                        colors = colors,
                    )
                }
            },
            template = """
                <$modalDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true" $attrColors="$templateColors">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                    <$icon $attrImageVector="$filled:AccountCircle" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateBadge">99+</$text>
                  </$navigationDrawerItem> 
                  <$navigationDrawerItem $attrSelected="false" $attrColors="$templateColors">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                    <$icon $attrImageVector="$autoMirroredFilled:ExitToApp" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateBadge">None</$text>
                  </$navigationDrawerItem>                   
                </$modalDrawerSheet>               
                """
        )
    }
}
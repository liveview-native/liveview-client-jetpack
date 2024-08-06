package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedTabIndex
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.leadingIconTab
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.tab
import org.phoenixframework.liveview.data.constants.ComposableTypes.tabRow
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredFilled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class TabShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 0) {
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                }
            }, template = """
                <$tabRow $attrSelectedTabIndex="0">
                  <$tab $attrSelected="true">
                    <$text $attrTemplate="$templateText">Tab 0</$text>
                  </$tab>
                  <$tab $attrSelected="false">
                    <$text $attrTemplate="$templateText">Tab 1</$text>
                  </$tab>      
                  <$tab $attrSelected="false" $attrEnabled="false">
                    <$text $attrTemplate="$templateText">Tab 2</$text>
                  </$tab>                                    
                </$tabRow>
                """
        )
    }

    @Test
    fun tabWithIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 1) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings, contentDescription = ""
                            )
                        },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                    )
                }
            }, template = """
                <$tabRow $attrSelectedTabIndex="1">
                  <$tab $attrSelected="false">
                    <$text $attrTemplate="$templateText">Tab 0</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Home" />
                  </$tab>
                  <$tab $attrSelected="true">
                    <$text $attrTemplate="$templateText">Tab 1</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Settings" />
                  </$tab>      
                  <$tab $attrSelected="false" $attrEnabled="false">
                    <$text $attrTemplate="$templateText">Tab 2</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$autoMirroredFilled:ExitToApp" />
                  </$tab>                                    
                </$tabRow>
                """
        )
    }

    @Test
    fun tabWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings, contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    Tab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                }
            }, template = """
                <$tabRow $attrSelectedTabIndex="2">
                  <$tab $attrSelected="false" 
                    $attrSelectedContentColor="$Red" $attrUnselectedContentColor="$Gray">
                    <$text $attrTemplate="$templateText">Tab 0</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Home" />
                  </$tab>
                  <$tab $attrSelected="false"
                    $attrSelectedContentColor="$Red" $attrUnselectedContentColor="$Gray">
                    <$text $attrTemplate="$templateText">Tab 1</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Settings" />
                  </$tab>      
                  <$tab $attrSelected="true" $attrEnabled="false"
                    $attrSelectedContentColor="$Red" $attrUnselectedContentColor="$Gray">
                    <$text $attrTemplate="$templateText">Tab 2</$text>
                    <$icon $attrTemplate="$templateIcon" $attrImageVector="$autoMirroredFilled:ExitToApp" />
                  </$tab>                                    
                </$tabRow>
                """
        )
    }

    @Test
    fun tabWithCustomContentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    Tab(
                        selected = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 0")
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        }
                    }
                    Tab(
                        selected = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 1")
                            Icon(
                                imageVector = Icons.Filled.Settings, contentDescription = ""
                            )
                        }
                    }
                    Tab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 2")
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }, template = """
                <$tabRow $attrSelectedTabIndex="2">
                  <$tab $attrSelected="false" 
                    $attrSelectedContentColor="$Red" 
                    $attrUnselectedContentColor="$Gray">
                    <$row>
                      <$text>Tab 0</$text>
                      <$icon $attrImageVector="$filled:Home" />
                    </$row>
                  </$tab>
                  <$tab $attrSelected="false"
                    $attrSelectedContentColor="$Red" 
                    $attrUnselectedContentColor="$Gray">
                    <$row>
                      <$text>Tab 1</$text>
                      <$icon $attrImageVector="$filled:Settings" />
                    </$row>
                  </$tab>      
                  <$tab $attrSelected="true" $attrEnabled="false"
                    $attrSelectedContentColor="$Red" 
                    $attrUnselectedContentColor="$Gray">
                    <$row>
                      <$text>Tab 2</$text>
                      <$icon $attrImageVector="$autoMirroredFilled:ExitToApp" />
                    </$row>  
                  </$tab>                                    
                </$tabRow>
                """
        )
    }

    @Test
    fun leadingIconTabWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    LeadingIconTab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    LeadingIconTab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings, contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    LeadingIconTab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                }
            }, template = """
                <$tabRow $attrSelectedTabIndex="2">
                    <$leadingIconTab $attrSelected="false" 
                      $attrSelectedContentColor="$Red" 
                      $attrUnselectedContentColor="$Gray">
                        <$text $attrTemplate="$templateText">Tab 0</$text>
                        <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Home" />
                    </$leadingIconTab>
                    <$leadingIconTab $attrSelected="false"
                        $attrSelectedContentColor="$Red" 
                        $attrUnselectedContentColor="$Gray">
                        <$text $attrTemplate="$templateText">Tab 1</$text>
                        <$icon $attrTemplate="$templateIcon" $attrImageVector="$filled:Settings" />
                    </$leadingIconTab>      
                    <$leadingIconTab $attrSelected="true" $attrEnabled="false"
                        $attrSelectedContentColor="$Red" 
                        $attrUnselectedContentColor="$Gray">
                        <$text $attrTemplate="$templateText">Tab 2</$text>
                        <$icon $attrTemplate="$templateIcon" $attrImageVector="$autoMirroredFilled:ExitToApp" />
                    </$leadingIconTab>                                    
                </$tabRow>
                """
        )
    }
}
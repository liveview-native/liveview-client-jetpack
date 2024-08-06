package org.phoenixframework.liveview.test.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationBar
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationBarItem
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel

class NavigationBarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleNavigationBarTest() {
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
                        label = {
                            Text(text = "Tab 1")
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
                        label = {
                            Text(text = "Tab 2")
                        },
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add, contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Tab 3")
                        },
                    )
                }
            }, template = """
                <$navigationBar>
                  <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                    <$icon $attrImageVector="filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Tab 1</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                    <$icon $attrImageVector="filled:VerticalDistribute" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateLabel">Tab 2</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="2">
                    <$icon $attrImageVector="filled:Add" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Tab 3</$text>
                  </$navigationBarItem>
                </$navigationBar>                
                """
        )
    }

    @Test
    fun navigationBarCustomTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationBar(
                    containerColor = Color.Yellow,
                    contentColor = Color.Green,
                ) {
                    NavigationBarItem(
                        selected = true, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.HorizontalDistribute,
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Tab 1")
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
                        label = {
                            Text(text = "Tab 2")
                        },
                    )
                    NavigationBarItem(
                        selected = false, onClick = {},
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Add, contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Tab 3")
                        },
                    )
                }
            }, template = """
                <$navigationBar $attrContainerColor="#FFFFFF00" $attrContentColor="#FF00FF00">
                  <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                    <$icon $attrImageVector="filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Tab 1</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                    <$icon $attrImageVector="filled:VerticalDistribute" $attrTemplate="$templateIcon" />
                    <$text $attrTemplate="$templateLabel">Tab 2</$text>
                  </$navigationBarItem>
                  <$navigationBarItem $attrSelected="false" $attrPhxValue="2">
                    <$icon $attrImageVector="filled:Add" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Tab 3</$text>
                  </$navigationBarItem>
                </$navigationBar>                
                """
        )
    }
}
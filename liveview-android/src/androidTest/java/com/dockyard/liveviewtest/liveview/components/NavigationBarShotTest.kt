package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <NavigationBar>
                  <NavigationBarItem selected="true" phx-click="" phx-value="0">
                    <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                    <Text template="label">Tab 1</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:VerticalDistribute" template="icon" />
                    <Text template="label">Tab 2</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="2">
                    <Icon image-vector="filled:Add" template="icon"/>
                    <Text template="label">Tab 3</Text>
                  </NavigationBarItem>
                </NavigationBar>                
                """.templateToTest()
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
                <NavigationBar container-color="#FFFFFF00" content-color="#FF00FF00">
                  <NavigationBarItem selected="true" phx-click="" phx-value="0">
                    <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                    <Text template="label">Tab 1</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:VerticalDistribute" template="icon" />
                    <Text template="label">Tab 2</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="2">
                    <Icon image-vector="filled:Add" template="icon"/>
                    <Text template="label">Tab 3</Text>
                  </NavigationBarItem>
                </NavigationBar>                
                """.templateToTest()
        )
    }
}
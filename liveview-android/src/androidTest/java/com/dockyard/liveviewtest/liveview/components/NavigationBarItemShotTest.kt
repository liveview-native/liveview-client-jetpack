package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <NavigationBar>
                  <NavigationBarItem selected="true" phx-click="" phx-value="0">
                    <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:VerticalDistribute" template="icon"/>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:Add" template="icon"/>
                  </NavigationBarItem>                      
                </NavigationBar>
                """.templateToTest()
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
                <NavigationBar>
                  <NavigationBarItem selected="true" phx-click="" phx-value="0">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:AccountCircle" template="icon"/>
                    <Text template="label">Account</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationBarItem>                      
                </NavigationBar>
                """.templateToTest()
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
                <NavigationBar>
                  <NavigationBarItem always-show-label="false" selected="true" phx-click="" phx-value="0">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationBarItem>
                  <NavigationBarItem always-show-label="false" selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:AccountCircle" template="icon"/>
                    <Text template="label">Account</Text>
                  </NavigationBarItem>
                  <NavigationBarItem always-show-label="false" selected="false" phx-click="" phx-value="1">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationBarItem>                      
                </NavigationBar>
                """.templateToTest()
        )
    }

    @Test
    fun navigationBarItemWithCustomColorsTest() {
        val templateColors = """ 
            {
            'selectedIconColor': '#FF00FF00',
            'selectedTextColor': '#FFFF0000',
            'indicatorColor': '#FFFFFF00',
            'unselectedIconColor': '#FF00FFFF',
            'unselectedTextColor': '#FFFF00FF',
            'disabledIconColor': '#FFCCCCCC',
            'disabledTextColor': '#FF888888'
            }
            """.trimIndent().trim().replace("\n", "")

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
                                imageVector = Icons.Filled.ExitToApp,
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
                <NavigationBar>
                  <NavigationBarItem selected="true" phx-click="" phx-value="0" colors="$templateColors">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="1" colors="$templateColors">
                    <Icon image-vector="filled:AccountCircle" template="icon"/>
                    <Text template="label">Account</Text>
                  </NavigationBarItem>
                  <NavigationBarItem selected="false" phx-click="" phx-value="2" colors="$templateColors">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationBarItem>     
                  <NavigationBarItem selected="false" enabled="false" phx-click="" phx-value="3" colors="$templateColors">
                    <Icon image-vector="filled:ExitToApp" template="icon"/>
                    <Text template="label">Exit</Text>
                  </NavigationBarItem>                                     
                </NavigationBar>
                """.templateToTest()
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <ModalDrawerSheet>
                  <NavigationDrawerItem selected="true" phx-click="">
                    <Text template="label">Option 1</Text>
                  </NavigationDrawerItem> 
                  <NavigationDrawerItem selected="false" phx-click="">
                    <Text template="label">Option 2</Text>
                  </NavigationDrawerItem>                   
                </ModalDrawerSheet>               
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
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "")
                        },
                        selected = false,
                        onClick = { }
                    )
                }
            },
            template = """
                <ModalDrawerSheet>
                  <NavigationDrawerItem selected="true" phx-click="">
                    <Text template="label">Option 1</Text>
                    <Icon image-vector="filled:AccountCircle" template="icon" />
                  </NavigationDrawerItem> 
                  <NavigationDrawerItem selected="false" phx-click="">
                    <Text template="label">Option 2</Text>
                    <Icon image-vector="filled:ExitToApp" template="icon" />
                  </NavigationDrawerItem>                   
                </ModalDrawerSheet>               
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
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "")
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
                <ModalDrawerSheet>
                  <NavigationDrawerItem selected="true" phx-click="">
                    <Text template="label">Option 1</Text>
                    <Icon image-vector="filled:AccountCircle" template="icon" />
                    <Text template="badge">99+</Text>
                  </NavigationDrawerItem> 
                  <NavigationDrawerItem selected="false" phx-click="">
                    <Text template="label">Option 2</Text>
                    <Icon image-vector="filled:ExitToApp" template="icon" />
                    <Text template="badge">None</Text>
                  </NavigationDrawerItem>                   
                </ModalDrawerSheet>               
                """
        )
    }

    @Test
    fun navigationDrawerItemWithCustomColors() {
        val templateColors = """ 
            {
            'selectedContainerColor': '#FFFF0000',
            'unselectedContainerColor': '#FF888888',
            'selectedIconColor': '#FF00FF00',
            'unselectedIconColor': '#FFCCCCCC',
            'selectedTextColor': '#FF0000FF',
            'unselectedTextColor': '#FF444444',
            'selectedBadgeColor': '#FFFFFF00',
            'unselectedBadgeColor': '#FFFF00FF'
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
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "")
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
                <ModalDrawerSheet>
                  <NavigationDrawerItem selected="true" phx-click="" colors="$templateColors">
                    <Text template="label">Option 1</Text>
                    <Icon image-vector="filled:AccountCircle" template="icon" />
                    <Text template="badge">99+</Text>
                  </NavigationDrawerItem> 
                  <NavigationDrawerItem selected="false" phx-click="" colors="$templateColors">
                    <Text template="label">Option 2</Text>
                    <Icon image-vector="filled:ExitToApp" template="icon" />
                    <Text template="badge">None</Text>
                  </NavigationDrawerItem>                   
                </ModalDrawerSheet>               
                """
        )
    }
}
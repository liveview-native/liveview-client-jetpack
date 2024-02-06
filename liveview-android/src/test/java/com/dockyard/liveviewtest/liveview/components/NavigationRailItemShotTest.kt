package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <NavigationRail>
                  <NavigationRailItem selected="true">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Home" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="true" enabled="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                  </NavigationRailItem>   
                  <NavigationRailItem selected="false" enabled="false">
                    <Icon image-vector="filled:Settings" template="icon"/>
                  </NavigationRailItem>                                                      
                </NavigationRail>
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
                <NavigationRail>
                  <NavigationRailItem selected="true">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                    <Text template="label">Favorites</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="true" enabled="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                    <Text template="label">Person</Text>
                  </NavigationRailItem>   
                  <NavigationRailItem selected="false" enabled="false">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationRailItem>                                                      
                </NavigationRail>
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
                <NavigationRail>
                  <NavigationRailItem selected="true" always-show-label="false">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                    <Text template="label">Favorites</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false" always-show-label="false">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="true" enabled="false" always-show-label="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                    <Text template="label">Person</Text>
                  </NavigationRailItem>   
                  <NavigationRailItem selected="false" enabled="false" always-show-label="false">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationRailItem>                                                      
                </NavigationRail>
                """
        )
    }

    @Test
    fun navigationRailItemWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'selectedIconColor': 'system-white',
            'selectedTextColor': 'system-yellow',
            'indicatorColor': 'system-cyan',
            'unselectedIconColor': 'system-dark-gray',
            'unselectedTextColor': 'system-gray',
            'disabledIconColor': 'system-light-gray',
            'disabledTextColor': 'system-light-gray'
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
                <NavigationRail>
                  <NavigationRailItem selected="true" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                    <Text template="label">Favorites</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Home" template="icon"/>
                    <Text template="label">Home</Text>
                  </NavigationRailItem>
                  <NavigationRailItem selected="true" enabled="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Person" template="icon"/>
                    <Text template="label">Person</Text>
                  </NavigationRailItem>   
                  <NavigationRailItem selected="false" enabled="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Settings" template="icon"/>
                    <Text template="label">Settings</Text>
                  </NavigationRailItem>                                                      
                </NavigationRail>
                """
        )
    }
}
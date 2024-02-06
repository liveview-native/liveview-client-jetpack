package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.robolectric.annotation.Config

@Config(qualifiers = "xlarge-land")
class NavigationRailShotTest : LiveViewComposableTest() {

    @Test
    fun simpleNavigationRailTest() {
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
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
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
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                  </NavigationRailItem>                                   
                </NavigationRail>
                """
        )
    }

    @Test
    fun navigationRailWithHeaderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail(
                    header = {
                        Text(text = "NavRail Header", fontSize = 24.sp)
                        Text(text = "Subheader", fontSize = 18.sp)
                    }
                ) {
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
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <NavigationRail>
                  <Text font-size="24" template="header">NavRail Header</Text>
                  <Text font-size="18" template="header">Subheader</Text>
                  <NavigationRailItem selected="true">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Home" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                  </NavigationRailItem>                                   
                </NavigationRail>
                """
        )
    }

    @Test
    fun navigationRailWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail(
                    header = {
                        Text(text = "NavRail Header", fontSize = 24.sp)
                        Text(text = "Subheader", fontSize = 18.sp)
                    },
                    containerColor = Color.Blue,
                    contentColor = Color.Yellow,
                ) {
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
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <NavigationRail container-color="system-blue" content-color="system-yellow">
                  <Text font-size="24" template="header">NavRail Header</Text>
                  <Text font-size="18" template="header">Subheader</Text>
                  <NavigationRailItem selected="true">
                    <Icon image-vector="filled:Favorite" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Home" template="icon"/>
                  </NavigationRailItem>
                  <NavigationRailItem selected="false">
                    <Icon image-vector="filled:Person" template="icon"/>
                  </NavigationRailItem>                                   
                </NavigationRail>
                """
        )
    }
}
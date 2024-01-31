package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class NavigationDrawerShotTest : LiveViewComposableTest() {
    @Test
    fun simpleModalNavigationDrawerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalNavigationDrawer(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <ModalNavigationDrawer is-open="true" on-close="" on-open="">
                  <ModalDrawerSheet template="drawerContent">
                    <NavigationDrawerItem selected="true" phx-click="">
                      <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false" phx-click="">
                      <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>
                  </ModalDrawerSheet>                      
                  <Box content-alignment="center">
                    <Text>Screen Content</Text>
                  </Box>
                </ModalNavigationDrawer>
                """
        )
    }

    @Test
    fun modalNavigationDrawerWithScrimColorTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalNavigationDrawer(
                    scrimColor = Color.Red,
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <ModalNavigationDrawer scrim-color="system-red" is-open="true" on-close="" on-open="">
                  <ModalDrawerSheet template="drawerContent">
                    <NavigationDrawerItem selected="true" phx-click="">
                      <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false" phx-click="">
                      <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>
                  </ModalDrawerSheet>                      
                  <Box content-alignment="center">
                    <Text>Screen Content</Text>
                  </Box>
                </ModalNavigationDrawer>
                """
        )
    }

    @Test
    fun simpleDismissibleNavigationDrawerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DismissibleNavigationDrawer(
                    modifier = Modifier.fillMaxSize(),
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <DismissibleNavigationDrawer is-open="true" on-close="" on-open="" size="fill">
                  <ModalDrawerSheet template="drawerContent">
                    <NavigationDrawerItem selected="true" phx-click="">
                      <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false" phx-click="">
                      <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>
                  </ModalDrawerSheet>                      
                  <Box content-alignment="center">
                    <Text>Screen Content</Text>
                  </Box>
                </DismissibleNavigationDrawer>
                """
        )
    }

    @Test
    fun permanentNavigationDrawerWithScrimColorTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                PermanentNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <PermanentNavigationDrawer is-open="true" on-close="" on-open="">
                  <ModalDrawerSheet template="drawerContent">
                    <NavigationDrawerItem selected="true" phx-click="">
                      <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false" phx-click="">
                      <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>
                  </ModalDrawerSheet>                      
                  <Box content-alignment="center">
                    <Text>Screen Content</Text>
                  </Box>
                </PermanentNavigationDrawer>
                """
        )
    }
}
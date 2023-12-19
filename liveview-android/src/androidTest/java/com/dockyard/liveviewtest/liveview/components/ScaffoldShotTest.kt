package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class ScaffoldShotTest : LiveViewComposableTest() {
    @Test
    fun simpleScaffoldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        Text(text = "Scaffold Body")
                    }
                }
            },
            template = """
                <Scaffold>
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    containerColor = Color.Magenta,
                    contentColor = Color.Blue
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        Text(text = "Scaffold Body")
                    }
                }
            },
            template = """
                <Scaffold container-color="#FFFF00FF" content-color="#FF0000FF">
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithTitleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold>
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold>
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <NavigationBar template="bottomBar">
                    <NavigationBarItem selected="true" phx-click="" phx-value="0">
                      <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                    </NavigationBarItem>
                    <NavigationBarItem selected="false" phx-click="" phx-value="1">
                      <Icon image-vector="filled:VerticalDistribute" template="icon" />
                    </NavigationBarItem>     
                  </NavigationBar>               
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithFabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold>
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <FloatingActionButton phx-click="" template="fab">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithFabOnCenterTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold fab-position="center">
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <FloatingActionButton phx-click="" template="fab">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithFabAndBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold>
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <NavigationBar template="bottomBar">
                    <NavigationBarItem selected="true" phx-click="" phx-value="0">
                      <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                    </NavigationBarItem>
                    <NavigationBarItem selected="false" phx-click="" phx-value="1">
                      <Icon image-vector="filled:VerticalDistribute" template="icon" />
                    </NavigationBarItem>     
                  </NavigationBar>
                  <FloatingActionButton phx-click="" template="fab">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun scaffoldWithFabOnCenterAndBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <Scaffold fab-position="center">
                  <TopAppBar template="topBar">
                    <Text template="title">Top Bar</Text>
                  </TopAppBar>  
                  <NavigationBar template="bottomBar">
                    <NavigationBarItem selected="true" phx-click="" phx-value="0">
                      <Icon image-vector="filled:HorizontalDistribute" template="icon"/>
                    </NavigationBarItem>
                    <NavigationBarItem selected="false" phx-click="" phx-value="1">
                      <Icon image-vector="filled:VerticalDistribute" template="icon" />
                    </NavigationBarItem>     
                  </NavigationBar>
                  <FloatingActionButton phx-click="" template="fab">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <Box size="fill" content-alignment="center" template="body">
                    <Text>Scaffold Body</Text> 
                  </Box>
                </Scaffold>
                """.templateToTest()
        )
    }
}
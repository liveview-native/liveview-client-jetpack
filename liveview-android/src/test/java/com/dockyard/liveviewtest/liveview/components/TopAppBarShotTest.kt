package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class TopAppBarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <Column>
                  <TopAppBar>
                    <Text template="title">App Bar 1</Text>
                  </TopAppBar>   
                  <TopAppBar>
                    <Text template="title">App Bar 2</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>
                  </TopAppBar>   
                  <TopAppBar>
                    <Text template="title">App Bar 3</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>                    
                    <IconButton template="action" phx-click="decrement-count">
                      <Icon image-vector="filled:Add" />
                    </IconButton>
                  </TopAppBar>                                                     
                </Column>
                """
        )
    }

    @Test
    fun topAppBarWithColors() {
        val colorsForTemplate = """
            {
            'containerColor': '#FF0000FF',
            'navigationIconContentColor': '#FFFFFF00',
            'titleContentColor': '#FFFFFFFF',
            'actionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                TopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <TopAppBar colors="$colorsForTemplate">
                  <Text template="title">App Bar 1</Text>
                  <IconButton template="navigationIcon" phx-click="">
                    <Icon image-vector="filled:Menu" />
                  </IconButton>                    
                  <IconButton template="action" phx-click="decrement-count">
                    <Icon image-vector="filled:Add" />
                  </IconButton>
                </TopAppBar>                   
                """
        )
    }

    @Test
    fun simpleCenterAlignedTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <Column>
                  <CenterAlignedTopAppBar>
                    <Text template="title">App Bar 1</Text>
                  </CenterAlignedTopAppBar>   
                  <CenterAlignedTopAppBar>
                    <Text template="title">App Bar 2</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>
                  </CenterAlignedTopAppBar>   
                  <CenterAlignedTopAppBar>
                    <Text template="title">App Bar 3</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>                    
                    <IconButton template="action" phx-click="decrement-count">
                      <Icon image-vector="filled:Add" />
                    </IconButton>
                  </CenterAlignedTopAppBar>                                                     
                </Column>
                """
        )
    }

    @Test
    fun centerAlignedTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            'containerColor': '#FF0000FF',
            'navigationIconContentColor': '#FFFFFF00',
            'titleContentColor': '#FFFFFFFF',
            'actionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <CenterAlignedTopAppBar colors="$colorsForTemplate">
                  <Text template="title">App Bar 1</Text>
                  <IconButton template="navigationIcon" phx-click="">
                    <Icon image-vector="filled:Menu" />
                  </IconButton>                    
                  <IconButton template="action" phx-click="decrement-count">
                    <Icon image-vector="filled:Add" />
                  </IconButton>
                </CenterAlignedTopAppBar>                   
                """
        )
    }

    @Test
    fun simpleMediumTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <Column>
                  <MediumTopAppBar>
                    <Text template="title">App Bar 1</Text>
                  </MediumTopAppBar>   
                  <MediumTopAppBar>
                    <Text template="title">App Bar 2</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>
                  </MediumTopAppBar>   
                  <MediumTopAppBar>
                    <Text template="title">App Bar 3</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>                    
                    <IconButton template="action" phx-click="decrement-count">
                      <Icon image-vector="filled:Add" />
                    </IconButton>
                  </MediumTopAppBar>                                                     
                </Column>
                """
        )
    }

    @Test
    fun mediumTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            'containerColor': '#FF0000FF',
            'navigationIconContentColor': '#FFFFFF00',
            'titleContentColor': '#FFFFFFFF',
            'actionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                MediumTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <MediumTopAppBar colors="$colorsForTemplate">
                  <Text template="title">App Bar 1</Text>
                  <IconButton template="navigationIcon" phx-click="">
                    <Icon image-vector="filled:Menu" />
                  </IconButton>                    
                  <IconButton template="action" phx-click="decrement-count">
                    <Icon image-vector="filled:Add" />
                  </IconButton>
                </MediumTopAppBar>                   
                """
        )
    }

    @Test
    fun simpleLargeTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <Column>
                  <LargeTopAppBar>
                    <Text template="title">App Bar 1</Text>
                  </LargeTopAppBar>   
                  <LargeTopAppBar>
                    <Text template="title">App Bar 2</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>
                  </LargeTopAppBar>   
                  <LargeTopAppBar>
                    <Text template="title">App Bar 3</Text>
                    <IconButton template="navigationIcon" phx-click="">
                      <Icon image-vector="filled:Menu" />
                    </IconButton>                    
                    <IconButton template="action" phx-click="decrement-count">
                      <Icon image-vector="filled:Add" />
                    </IconButton>
                  </LargeTopAppBar>                                                     
                </Column>
                """
        )
    }

    @Test
    fun largeTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            'containerColor': '#FF0000FF',
            'navigationIconContentColor': '#FFFFFF00',
            'titleContentColor': '#FFFFFFFF',
            'actionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                LargeTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <LargeTopAppBar colors="$colorsForTemplate">
                  <Text template="title">App Bar 1</Text>
                  <IconButton template="navigationIcon" phx-click="">
                    <Icon image-vector="filled:Menu" />
                  </IconButton>                    
                  <IconButton template="action" phx-click="decrement-count">
                    <Icon image-vector="filled:Add" />
                  </IconButton>
                </LargeTopAppBar>                   
                """
        )
    }
}
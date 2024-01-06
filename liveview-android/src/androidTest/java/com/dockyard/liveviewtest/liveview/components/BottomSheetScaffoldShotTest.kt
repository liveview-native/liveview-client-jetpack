package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetScaffoldShotTest : LiveViewComposableTest() {
    @Test
    fun simpleBottomSheetScaffoldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>                
                """
        )
    }

    @Test
    fun bssWithSnackbarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                BottomSheetScaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Hello World")
                }
            },
            template = """
                <BottomSheetScaffold>
                  <Snackbar message="Hello World" dismiss-event="" />
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>                
                """
        )
    }

    @Test
    fun bssWithSheetPeekHeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetPeekHeight = 60.dp,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60">
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>     
                """
        )
    }

    @Test
    fun bssWithCustomShapeTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24">
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomSheetColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue">
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomDragHandleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue">
                  <Box width="fill" template="dragHandle">
                    <Icon image-vector="filled:ArrowUpward" align="center" />
                  </Box>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithTopBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue">
                  <Box width="fill" template="dragHandle">
                    <Icon image-vector="filled:ArrowUpward" align="center" />
                  </Box>
                  <TopAppBar template="topBar">
                    <Text template="title">Title</Text>
                  </TopAppBar>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue"
                  container-color="system-red" content-color="system-green">
                  <Box width="fill" template="dragHandle">
                    <Icon image-vector="filled:ArrowUpward" align="center" />
                  </Box>
                  <TopAppBar template="topBar">
                    <Text template="title">Title</Text>
                  </TopAppBar>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithSheetExpandedTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = rememberStandardBottomSheetState(
                            initialValue = SheetValue.Expanded
                        )
                    ),
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue"
                  container-color="system-red" content-color="system-green"
                  sheet-value="expanded">
                  <Box width="fill" template="dragHandle">
                    <Icon image-vector="filled:ArrowUpward" align="center" />
                  </Box>
                  <TopAppBar template="topBar">
                    <Text template="title">Title</Text>
                  </TopAppBar>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithSheetHiddenTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = rememberStandardBottomSheetState(
                            initialValue = SheetValue.Hidden,
                            skipHiddenState = false,
                        )
                    ),
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <BottomSheetScaffold sheet-peek-height="60" sheet-shape="24" 
                  sheet-container-color="system-yellow" sheet-content-color="system-blue"
                  container-color="system-red" content-color="system-green"
                  sheet-value="hidden" sheet-skip-hidden-state="false">
                  <Box width="fill" template="dragHandle">
                    <Icon image-vector="filled:ArrowUpward" align="center" />
                  </Box>
                  <TopAppBar template="topBar">
                    <Text template="title">Title</Text>
                  </TopAppBar>
                  <Box size="fill" template="sheetContent">
                    <Text>Sheet content</Text>
                  </Box>
                  <Box size="fill" template="body">
                    <Text font-size="24">Screen content</Text>
                  </Box>
                </BottomSheetScaffold>              
                """
        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class SnackbarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSnackbarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Hello World")
                }
            },
            template = """
                <Scaffold>
                    <Snackbar message="Hello World" dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun snackbarWithShapeTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) {
                            Snackbar(snackbarData = it, shape = CircleShape)
                        }
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Hello World")
                }
            },
            template = """
                <Scaffold>
                    <Snackbar message="Hello World" shape="circle" dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun snackbarWithLabelAndMessageTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "Snackbar Message",
                        actionLabel = "Dismiss"
                    )
                }
            },
            template = """
                <Scaffold>
                    <Snackbar message="Snackbar Message" action-label="Dismiss" dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun snackbarWithActionAndDismissActionTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "Snackbar Message",
                        actionLabel = "Action",
                        withDismissAction = true
                    )
                }
            },
            template = """
                <Scaffold>
                    <Snackbar message="Snackbar Message" action-label="Action" with-dismiss-action="true" dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun snackbarWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) {
                            Snackbar(
                                snackbarData = it,
                                containerColor = Color.Blue,
                                contentColor = Color.Yellow,
                                actionContentColor = Color.Red,
                                actionColor = Color.Magenta
                            )
                        }
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "Snackbar Message",
                        actionLabel = "Action",
                        withDismissAction = true
                    )
                }
            },
            template = """
                <Scaffold>
                    <Snackbar 
                      message="Snackbar Message" 
                      action-label="Action" 
                      with-dismiss-action="true"
                      container-color="#FF0000FF"
                      content-color="#FFFFFF00"
                      action-content-color="#FFFF0000"
                      action-color="#FFFF00FF" 
                      dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }

    @Test
    fun snackbarWithCustomColorsAndActionOnNewLineTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) {
                            Snackbar(
                                snackbarData = it,
                                containerColor = Color.Blue,
                                contentColor = Color.Yellow,
                                actionContentColor = Color.Red,
                                actionColor = Color.Magenta,
                                actionOnNewLine = true,
                                dismissActionContentColor = Color.Green
                            )
                        }
                    }
                ) {
                    Box(Modifier.padding(it))
                }
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "Snackbar Message",
                        actionLabel = "Action",
                        withDismissAction = true
                    )
                }
            },
            template = """
                <Scaffold>
                    <Snackbar 
                      message="Snackbar Message" 
                      action-label="Action" 
                      with-dismiss-action="true"
                      container-color="#FF0000FF"
                      content-color="#FFFFFF00"
                      action-content-color="#FFFF0000"
                      action-color="#FFFF00FF" 
                      action-on-new-line="true"
                      dismiss-action-content-color="#FF00FF00"
                      dismiss-event="" />
                </Scaffold>
                """.templateToTest()
        )
    }
}
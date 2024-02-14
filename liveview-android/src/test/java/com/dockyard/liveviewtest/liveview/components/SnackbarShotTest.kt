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
import org.phoenixframework.liveview.data.constants.Attrs.attrActionColor
import org.phoenixframework.liveview.data.constants.Attrs.attrActionContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrActionLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrActionOnNewLine
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissActionContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissEvent
import org.phoenixframework.liveview.data.constants.Attrs.attrMessage
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrWithDismissAction
import org.phoenixframework.liveview.data.constants.ShapeValues.circle
import org.phoenixframework.liveview.domain.base.ComposableTypes.scaffold
import org.phoenixframework.liveview.domain.base.ComposableTypes.snackbar

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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Hello World" 
                    $attrDismissEvent="" />
                </$scaffold>
                """
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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Hello World" 
                    $attrShape="$circle" 
                    $attrDismissEvent="" />
                </$scaffold>
                """
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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Snackbar Message" 
                    $attrActionLabel="Dismiss" 
                    $attrDismissEvent="" />
                </$scaffold>
                """
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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Snackbar Message" 
                    $attrActionLabel="Action" 
                    $attrWithDismissAction="true" 
                    $attrDismissEvent="" />
                </$scaffold>
                """
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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Snackbar Message" 
                    $attrActionLabel="Action" 
                    $attrWithDismissAction="true"
                    $attrContainerColor="#FF0000FF"
                    $attrContentColor="#FFFFFF00"
                    $attrActionContentColor="#FFFF0000"
                    $attrActionColor="#FFFF00FF" 
                    $attrDismissEvent="" />
                </$scaffold>
                """
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
                <$scaffold>
                  <$snackbar 
                    $attrMessage="Snackbar Message" 
                    $attrActionLabel="Action" 
                    $attrWithDismissAction="true"
                    $attrContainerColor="#FF0000FF"
                    $attrContentColor="#FFFFFF00"
                    $attrActionContentColor="#FFFF0000"
                    $attrActionColor="#FFFF00FF" 
                    $attrActionOnNewLine="true"
                    $attrDismissActionContentColor="#FF00FF00"
                    $attrDismissEvent="" />
                </$scaffold>
                """
        )
    }
}
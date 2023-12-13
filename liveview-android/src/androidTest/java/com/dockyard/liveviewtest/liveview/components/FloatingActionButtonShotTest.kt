package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.domain.extensions.toColor

class FloatingActionButtonShotTest : LiveViewComposableTest() {

    @Test
    fun simpleFabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false
                    )
                }
            },
            template = """
                <Row>
                  <FloatingActionButton phx-click="">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <SmallFloatingActionButton phx-click="">
                    <Icon image-vector="filled:Delete"/>
                  </SmallFloatingActionButton>
                  <LargeFloatingActionButton phx-click="">
                    <Icon image-vector="filled:Cached"/>
                  </LargeFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="" expanded="false">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>                  
                </Row>
                """.templateToTest()
        )
    }

    @Test
    fun fabWithCustomColorsTest() {
        val containerColor = "FFFF0000"
        val contentColor = "FFFFFFFF"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false,
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    )
                }
            },
            template = """
                <Row>
                  <FloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <SmallFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor">
                    <Icon image-vector="filled:Delete"/>
                  </SmallFloatingActionButton>
                  <LargeFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor">
                    <Icon image-vector="filled:Cached"/>
                  </LargeFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="" expanded="false" container-color="#$containerColor" content-color="#$contentColor">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>                  
                </Row>
                """.templateToTest()
        )
    }

    @Test
    fun fabWithCustomShapesTest() {
        val containerColor = "FF0000FF"
        val contentColor = "FFFFFF00"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false,
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    )
                }
            },
            template = """
                <Row>
                  <FloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor" shape="circle">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                  <SmallFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor" shape="circle">
                    <Icon image-vector="filled:Delete"/>
                  </SmallFloatingActionButton>
                  <LargeFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor" shape="circle">
                    <Icon image-vector="filled:Cached"/>
                  </LargeFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="" container-color="#$containerColor" content-color="#$contentColor" shape="circle">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>
                  <ExtendedFloatingActionButton phx-click="" expanded="false" container-color="#$containerColor" content-color="#$contentColor" shape="circle">
                    <Icon image-vector="filled:Share" template="icon"/>
                    <Text template="text">Share</Text>
                  </ExtendedFloatingActionButton>                  
                </Row>
                """.templateToTest()
        )
    }
}
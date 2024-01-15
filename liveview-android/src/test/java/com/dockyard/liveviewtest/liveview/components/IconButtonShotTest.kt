package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class IconButtonShotTest : LiveViewComposableTest() {

    @Test
    fun simpleIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    IconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <IconButton phx-click="">
                        <Icon image-vector="filled:AccountCircle" />
                    </IconButton>
                    <IconButton phx-click="" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </IconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun iconButtonWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    IconButton(
                        onClick = {},
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <IconButton phx-click="" colors="{'containerColor': '#FFFF0000', 'contentColor': '#FFFFFF00'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </IconButton>
                    <IconButton phx-click="" enabled="false" colors="{'disabledContainerColor': '#FF888888', 'disabledContentColor': '#FFCCCCCC'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </IconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun simpleFilledIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <FilledIconButton phx-click="">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledIconButton>
                    <FilledIconButton phx-click="" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun filledIconButtonWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <FilledIconButton phx-click="" shape="8"
                      colors="{'containerColor': '#FFFF0000', 'contentColor': '#FFFFFF00'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledIconButton>
                    <FilledIconButton phx-click="" enabled="false" shape="8"
                      colors="{'disabledContainerColor': '#FF888888', 'disabledContentColor': '#FFCCCCCC'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun simpleFilledTonalIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledTonalIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledTonalIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <FilledTonalIconButton phx-click="">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledTonalIconButton>
                    <FilledTonalIconButton phx-click="" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledTonalIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun filledTonalIconButtonWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledTonalIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledTonalIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <FilledTonalIconButton phx-click="" shape="8"
                      colors="{'containerColor': '#FFFF0000', 'contentColor': '#FFFFFF00'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledTonalIconButton>
                    <FilledTonalIconButton phx-click="" enabled="false" shape="8"
                      colors="{'disabledContainerColor': '#FF888888', 'disabledContentColor': '#FFCCCCCC'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </FilledTonalIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun simpleOutlinedIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <OutlinedIconButton phx-click="">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>
                    <OutlinedIconButton phx-click="" enabled="false">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun outlinedIconButtonWithCustomBorderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(onClick = {}, border = BorderStroke(2.dp, Color.Magenta)) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(
                        onClick = {},
                        enabled = false,
                        border = BorderStroke(2.dp, Color.Magenta)
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <OutlinedIconButton phx-click="" border="{'width': '2', 'color': 'system-magenta'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>
                    <OutlinedIconButton phx-click="" enabled="false" border="{'width': '2', 'color': 'system-magenta'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>                    
                </Row>
                """
        )
    }

    @Test
    fun outlinedIconButtonWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Magenta),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Magenta),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <Row>
                    <OutlinedIconButton phx-click="" shape="8"
                      border="{'width': '2', 'color': 'system-magenta'}"
                      colors="{'containerColor': '#FFFF0000', 'contentColor': '#FFFFFF00'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>
                    <OutlinedIconButton phx-click="" enabled="false" shape="8"
                      border="{'width': '2', 'color': 'system-magenta'}"
                      colors="{'disabledContainerColor': '#FF888888', 'disabledContentColor': '#FFCCCCCC'}">
                        <Icon image-vector="filled:AccountCircle" />
                    </OutlinedIconButton>                    
                </Row>
                """
        )
    }
}
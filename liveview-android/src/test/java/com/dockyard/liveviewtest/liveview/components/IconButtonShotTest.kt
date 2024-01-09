package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.Color
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
}
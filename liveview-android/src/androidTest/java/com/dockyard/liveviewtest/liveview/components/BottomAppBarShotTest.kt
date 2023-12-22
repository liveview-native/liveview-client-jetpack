package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class BottomAppBarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleBottomAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.HorizontalDistribute,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.VerticalDistribute,
                                contentDescription = ""
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    }
                )
            },
            template = """
                <BottomAppBar>
                  <IconButton phx-click="" template="action">
                    <Icon image-vector="filled:HorizontalDistribute" />
                  </IconButton>
                  <IconButton phx-click="" template="action">
                    <Icon image-vector="filled:VerticalDistribute" />
                  </IconButton>
                  <FloatingActionButton phx-click="" template="fab" shape="12">
                    <Icon image-vector="filled:Add"/>
                  </FloatingActionButton>
                </BottomAppBar>
                """
        )
    }

    @Test
    fun bottomAppBarWithCustomColors() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomAppBar(
                    containerColor = Color.Gray,
                    contentColor = Color.DarkGray,
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.House,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                        }
                    }
                )
            },
            template = """
                <BottomAppBar container-color="#FF888888" content-color="#FF444444">
                  <IconButton phx-click="" template="action">
                    <Icon image-vector="filled:House" />
                  </IconButton>
                  <IconButton phx-click="" template="action">
                    <Icon image-vector="filled:Search" />
                  </IconButton>                  
                  <IconButton phx-click="" template="action">
                    <Icon image-vector="filled:Settings" />
                  </IconButton>
                  <FloatingActionButton phx-click="" template="fab">
                    <Icon image-vector="filled:Delete"/>
                  </FloatingActionButton>
                </BottomAppBar>
                """
        )
    }
}
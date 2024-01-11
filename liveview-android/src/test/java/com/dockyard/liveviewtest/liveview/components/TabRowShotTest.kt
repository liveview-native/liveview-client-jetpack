package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class TabRowShotTest : LiveViewComposableTest() {
    @Test
    fun tabRowWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(
                    selectedTabIndex = 0,
                    containerColor = Color.Blue,
                    contentColor = Color.Yellow,
                ) {
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                }
            },
            template = """
                <TabRow selected-tab-index="0" container-color="system-blue" content-color="system-yellow" >
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 0</Text>
                    </Tab>
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 1</Text>
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                    </Tab>                                    
                </TabRow>
                """
        )
    }

    @Test
    fun tabRowWithCustomDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(
                    selectedTabIndex = 0,
                    containerColor = Color.Blue,
                    contentColor = Color.Yellow,
                    divider = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(Color.Cyan)
                        )
                    }
                ) {
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                }
            },
            template = """
                <TabRow selected-tab-index="0" container-color="system-blue" content-color="system-yellow" >
                    <Box template="divider" width="fill" height="4" clip="circle" background="system-cyan" />
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 0</Text>
                    </Tab>
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 1</Text>
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                    </Tab>                                    
                </TabRow>
                """
        )
    }

    @Test
    fun simpleScrollableTabRowTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ScrollableTabRow(selectedTabIndex = 1) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 3") },
                    )
                }
            },
            template = """
                <ScrollableTabRow selected-tab-index="1" >
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 0</Text>
                    </Tab>
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 1</Text>
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                    </Tab>         
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 3</Text>
                    </Tab>                                                  
                </ScrollableTabRow>
                """
        )
    }

    @Test
    fun scrollableTabRowWithEdgePaddingTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ScrollableTabRow(selectedTabIndex = 1, edgePadding = 8.dp) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 3") },
                    )
                }
            },
            template = """
                <ScrollableTabRow selected-tab-index="1" edge-padding="8">
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 0</Text>
                    </Tab>
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 1</Text>
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                    </Tab>         
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 3</Text>
                    </Tab>                                                  
                </ScrollableTabRow>
                """
        )
    }

    @Test
    fun scrollableTabRowWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ScrollableTabRow(
                    selectedTabIndex = 0,
                    containerColor = Color.Blue,
                    contentColor = Color.Yellow,
                ) {
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 0") },
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 3") },
                    )
                }
            },
            template = """
                <ScrollableTabRow selected-tab-index="0" 
                    container-color="system-blue" content-color="system-yellow" >
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 0</Text>
                    </Tab>
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 1</Text>
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                    </Tab>         
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 3</Text>
                    </Tab>                                                  
                </ScrollableTabRow>
                """
        )
    }
}
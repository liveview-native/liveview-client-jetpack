package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class TabShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 0) {
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
                <TabRow selected-tab-index="0">
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
    fun tabWithIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 1) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    Tab(
                        selected = true,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                    )
                    Tab(
                        selected = false,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                    )
                }
            },
            template = """
                <TabRow selected-tab-index="1">
                    <Tab selected="false" phx-click="">
                        <Text template="text">Tab 0</Text>
                        <Icon template="icon" image-vector="filled:Home" />
                    </Tab>
                    <Tab selected="true" phx-click="">
                        <Text template="text">Tab 1</Text>
                        <Icon template="icon" image-vector="filled:Settings" />
                    </Tab>      
                    <Tab selected="false" enabled="false" phx-click="">
                        <Text template="text">Tab 2</Text>
                        <Icon template="icon" image-vector="autoMirrored.filled:ExitToApp" />
                    </Tab>                                    
                </TabRow>
                """
        )
    }

    @Test
    fun tabWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    Tab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    Tab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                }
            },
            template = """
                <TabRow selected-tab-index="2">
                    <Tab selected="false" phx-click="" 
                      selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 0</Text>
                        <Icon template="icon" image-vector="filled:Home" />
                    </Tab>
                    <Tab selected="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 1</Text>
                        <Icon template="icon" image-vector="filled:Settings" />
                    </Tab>      
                    <Tab selected="true" enabled="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 2</Text>
                        <Icon template="icon" image-vector="autoMirrored.filled:ExitToApp" />
                    </Tab>                                    
                </TabRow>
                """
        )
    }

    @Test
    fun tabWithCustomContentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    Tab(
                        selected = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 0")
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        }
                    }
                    Tab(
                        selected = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 1")
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        }
                    }
                    Tab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Row {
                            Text("Tab 2")
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        }
                    }
                }
            },
            template = """
                <TabRow selected-tab-index="2">
                    <Tab selected="false" phx-click="" 
                      selected-content-color="system-red" unselected-content-color="system-gray">
                        <Row>
                            <Text>Tab 0</Text>
                            <Icon image-vector="filled:Home" />
                        </Row>
                    </Tab>
                    <Tab selected="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Row>
                            <Text>Tab 1</Text>
                            <Icon image-vector="filled:Settings" />
                        </Row>
                    </Tab>      
                    <Tab selected="true" enabled="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Row>
                            <Text>Tab 2</Text>
                            <Icon image-vector="autoMirrored.filled:ExitToApp" />
                        </Row>  
                    </Tab>                                    
                </TabRow>
                """
        )
    }

    @Test
    fun leadingIconTabWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TabRow(selectedTabIndex = 2) {
                    LeadingIconTab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 0") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    LeadingIconTab(
                        selected = false,
                        onClick = {},
                        text = { Text("Tab 1") },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                    LeadingIconTab(
                        selected = true,
                        enabled = false,
                        onClick = {},
                        text = { Text("Tab 2") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = ""
                            )
                        },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Gray,
                    )
                }
            },
            template = """
                <TabRow selected-tab-index="2">
                    <LeadingIconTab selected="false" phx-click="" 
                      selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 0</Text>
                        <Icon template="icon" image-vector="filled:Home" />
                    </LeadingIconTab>
                    <LeadingIconTab selected="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 1</Text>
                        <Icon template="icon" image-vector="filled:Settings" />
                    </LeadingIconTab>      
                    <LeadingIconTab selected="true" enabled="false" phx-click=""
                        selected-content-color="system-red" unselected-content-color="system-gray">
                        <Text template="text">Tab 2</Text>
                        <Icon template="icon" image-vector="autoMirrored.filled:ExitToApp" />
                    </LeadingIconTab>                                    
                </TabRow>
                """
        )
    }
}
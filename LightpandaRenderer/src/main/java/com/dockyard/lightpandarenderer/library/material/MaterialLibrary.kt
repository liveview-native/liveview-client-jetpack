package com.dockyard.lightpandarenderer.library.material

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.lightpandarenderer.LocalLightpandaRuntime
import com.dockyard.lightpandarenderer.NodeComposable
import com.dockyard.lightpandarenderer.library.ElementLibrary
import com.dockyard.lightpandarenderer.library.attributeValue
import com.dockyard.lightpandarenderer.observeNode

fun ElementLibrary.Companion.registerMaterial3() {
    // Buttons
    register("button") { node ->
        val runtime = LocalLightpandaRuntime.current
        Button(
            onClick = {
                runtime.callFunctionOn(
                    node.node,
                    """
                    function() {
                        this.dispatchEvent(new MouseEvent("mousedown", { x: 0, y: 0, bubbles: true, cancelable: true }));
                        this.dispatchEvent(new MouseEvent("click", { x: 0, y: 0, bubbles: true, cancelable: true, view: window, detail: 1 }));
                    }
                    """.trimIndent()
                )
            },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("elevatedbutton") { node ->
        val runtime = LocalLightpandaRuntime.current
        ElevatedButton(
            onClick = {
                runtime.callFunctionOn(node.node, "function() { this.click(); }")
            },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    register("filledtonalbutton") { node ->
        val runtime = LocalLightpandaRuntime.current
        FilledTonalButton(
            onClick = {
                runtime.callFunctionOn(node.node, "function() { this.click(); }")
            },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    register("outlinedbutton") { node ->
        val runtime = LocalLightpandaRuntime.current
        OutlinedButton(
            onClick = {
                runtime.callFunctionOn(node.node, "function() { this.click(); }")
            },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    register("textbutton") { node ->
        val runtime = LocalLightpandaRuntime.current
        TextButton(
            onClick = {
                runtime.callFunctionOn(node.node, "function() { this.click(); }")
            },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    // Cards
    register("card") { node ->
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("elevatedcard") { node ->
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    register("outlinedcard") { node ->
        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    // Text
    register("text") { node ->
        val text by observeNode(node.children.first())
        Text(
            text = text.value,
            color = node.attributeValue<Color>("color") ?: Color.Unspecified
        )
    }

    // Text Fields
    register("textfield") { node ->
        var value by remember { mutableStateOf(node.attributeValue<String>("value") ?: "") }
        TextField(
            value = value,
            onValueChange = { value = it },
            label = node.attributeValue<String>("label")?.let { { Text(it) } },
            placeholder = node.attributeValue<String>("placeholder")?.let { { Text(it) } },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("outlinedtextfield") { node ->
        var value by remember { mutableStateOf(node.attributeValue<String>("value") ?: "") }
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = node.attributeValue<String>("label")?.let { { Text(it) } },
            placeholder = node.attributeValue<String>("placeholder")?.let { { Text(it) } },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    // Selection Controls
    register("checkbox") { node ->
        var checked by remember { mutableStateOf(node.attributeValue<Boolean>("checked") ?: false) }
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("switch") { node ->
        var checked by remember { mutableStateOf(node.attributeValue<Boolean>("checked") ?: false) }
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("radiobutton") { node ->
        var selected by remember { mutableStateOf(node.attributeValue<Boolean>("selected") ?: false) }
        RadioButton(
            selected = selected,
            onClick = { selected = !selected },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("slider") { node ->
        var value by remember { mutableFloatStateOf(node.attributeValue<Float>("value") ?: 0f) }
        Slider(
            value = value,
            onValueChange = { value = it },
            valueRange = 0f..(node.attributeValue<Float>("max") ?: 1f),
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    // Progress Indicators
    register("linearprogressindicator") { node ->
        val progress = node.attributeValue<Float>("progress")
        if (progress != null) {
            LinearProgressIndicator(progress = { progress })
        } else {
            LinearProgressIndicator()
        }
    }

    register("circularprogressindicator") { node ->
        val progress = node.attributeValue<Float>("progress")
        if (progress != null) {
            CircularProgressIndicator(progress = { progress })
        } else {
            CircularProgressIndicator()
        }
    }

    // Dialogs
    register("alertdialog") { node ->
        var showDialog by remember { mutableStateOf(node.attributeValue<Boolean>("show") ?: true) }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = node.attributeValue<String>("title")?.let { { Text(it) } },
                text = node.attributeValue<String>("text")?.let { { Text(it) } },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(node.attributeValue<String>("confirmText") ?: "OK")
                    }
                },
                dismissButton = node.attributeValue<String>("dismissText")?.let { dismissText ->
                    {
                        TextButton(onClick = { showDialog = false }) {
                            Text(dismissText)
                        }
                    }
                }
            )
        }
    }

    // Chips
    register("assistchip") { node ->
        AssistChip(
            onClick = { },
            label = { Text(node.attributeValue<String>("label") ?: "") },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("filterchip") { node ->
        var selected by remember { mutableStateOf(node.attributeValue<Boolean>("selected") ?: false) }
        FilterChip(
            selected = selected,
            onClick = { selected = !selected },
            label = { Text(node.attributeValue<String>("label") ?: "") },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("inputchip") { node ->
        InputChip(
            selected = node.attributeValue<Boolean>("selected") ?: false,
            onClick = { },
            label = { Text(node.attributeValue<String>("label") ?: "") },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    register("suggestionchip") { node ->
        SuggestionChip(
            onClick = { },
            label = { Text(node.attributeValue<String>("label") ?: "") },
            enabled = node.attributeValue<Boolean>("enabled") ?: true
        )
    }

    // Floating Action Buttons
    register("floatingactionbutton") { node ->
        FloatingActionButton(
            onClick = { }
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    register("extendedfloatingactionbutton") { node ->
        ExtendedFloatingActionButton(
            onClick = { }
        ) {
            node.children.forEach { child -> NodeComposable(child) }
        }
    }

    // Navigation
    register("navigationbar") { node ->
        NavigationBar {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

//    register("navigationBarItem") { node ->
//        var selected by remember { mutableStateOf(node.attributeValue<Boolean>("selected") ?: false) }
//        NavigationBarItem(
//            selected = selected,
//            onClick = { selected = true },
//            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
//            label = node.attributeValue<String>("label")?.let { { Text(it) } }
//        )
//    }

    register("navigationrail") { node ->
        NavigationRail {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("navigationrailitem") { node ->
        var selected by remember { mutableStateOf(node.attributeValue<Boolean>("selected") ?: false) }
        NavigationRailItem(
            selected = selected,
            onClick = { selected = true },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = node.attributeValue<String>("label")?.let { { Text(it) } }
        )
    }

    register("navigationdrawer") { node ->
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    node.children.forEach { child ->
                        NodeComposable(child)
                    }
                }
            }
        ) {
            // Main content would go here
        }
    }

    register("bottomappbar") { node ->
        BottomAppBar {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    // Badges
    register("badge") { node ->
        Badge(
            content = node.attributeValue<String>("content")?.let { { Text(it) } }
        )
    }

    register("badgedbox") { node ->
        BadgedBox(
            badge = { Badge() }
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    // Dividers
    register("horizontaldivider") { node ->
        HorizontalDivider(
            thickness = node.attributeValue<Int>("thickness")?.dp ?: 1.dp,
            color = node.attributeValue<Color>("color") ?: DividerDefaults.color
        )
    }

    register("verticaldivider") { node ->
        VerticalDivider(
            thickness = node.attributeValue<Int>("thickness")?.dp ?: 1.dp,
            color = node.attributeValue<Color>("color") ?: DividerDefaults.color
        )
    }

    // Menus
    register("dropdownmenu") { node ->
        var expanded by remember { mutableStateOf(node.attributeValue<Boolean>("expanded") ?: false) }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }

    register("dropdownmenuitem") { node ->
        DropdownMenuItem(
            text = { Text(node.attributeValue<String>("text") ?: "") },
            onClick = { }
        )
    }

    // List Items
    register("listitem") { node ->
        ListItem(
            headlineContent = { Text(node.attributeValue<String>("headline") ?: "") },
            supportingContent = node.attributeValue<String>("supporting")?.let { { Text(it) } },
            overlineContent = node.attributeValue<String>("overline")?.let { { Text(it) } }
        )
    }

    // Scaffold
    register("scaffold") { node ->
        Scaffold(
            topBar = { /* Could render from children */ },
            bottomBar = { /* Could render from children */ }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                node.children.forEach { child ->
                    NodeComposable(child)
                }
            }
        }
    }

    // Snackbar
    register("snackbar") { node ->
        Snackbar(
            action = node.attributeValue<String>("actionLabel")?.let { label ->
                {
                    TextButton(onClick = { }) {
                        Text(label)
                    }
                }
            }
        ) {
            Text(node.attributeValue<String>("message") ?: "")
        }
    }

    // Tabs
    register("tabrow") { node ->
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        TabRow(selectedTabIndex = selectedTabIndex) {
            node.children.forEachIndexed { index, child ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                ) {
                    NodeComposable(child)
                }
            }
        }
    }

    register("tab") { node ->
        Tab(
            selected = node.attributeValue<Boolean>("selected") ?: false,
            onClick = { },
            text = node.attributeValue<String>("text")?.let { { Text(it) } }
        )
    }

    // Icon
    register("icon") { node ->
        val iconName = node.attributeValue<String>("name")
        // You'd need to map icon names to actual Icons
        Icon(
            imageVector = Icons.Filled.Star, // Default, map based on iconName
            contentDescription = node.attributeValue<String>("contentDescription"),
            tint = node.attributeValue<Color>("tint") ?: Color.Unspecified
        )
    }

    // Surface
    register("surface") { node ->
        Surface(
            color = node.attributeValue<Color>("color") ?: MaterialTheme.colorScheme.surface,
            tonalElevation = node.attributeValue<Int>("tonalElevation")?.dp ?: 0.dp
        ) {
            node.children.forEach { child ->
                NodeComposable(child)
            }
        }
    }
}
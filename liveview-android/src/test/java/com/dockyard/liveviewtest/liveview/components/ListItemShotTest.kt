package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class ListItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleListItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.fillMaxSize()) {
                    ListItem(
                        headlineContent = { Text(text = "Headline") }
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        tonalElevation = 8.dp,
                        shadowElevation = 8.dp,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                }
            },
            template = """
                <Column size="fill">
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                  </ListItem>
                  <ListItem tonal-elevation="8" shadow-elevation="8" >
                    <Text template="headlineContent">Headline</Text>
                  </ListItem>                  
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem>                  
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                  </ListItem>   
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem> 
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                  </ListItem>  
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                  </ListItem>  
                  <ListItem>
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem>                                                                   
                </Column>
                """
        )
    }

    @Test
    fun listItemWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-light-gray',
            'headlineColor': 'system-blue',
            'leadingIconColor': 'system-red',
            'overlineColor': 'system-yellow',
            'supportingColor': 'system-cyan',
            'trailingIconColor': 'system-magenta'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = ListItemDefaults.colors(
                    containerColor = Color.LightGray,
                    headlineColor = Color.Blue,
                    leadingIconColor = Color.Red,
                    overlineColor = Color.Yellow,
                    supportingColor = Color.Cyan,
                    trailingIconColor = Color.Magenta,
                )
                Column(Modifier.fillMaxSize()) {
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                }
            },
            template = """
                <Column size="fill">
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                  </ListItem>                 
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem>                  
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                  </ListItem>   
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem> 
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                  </ListItem>  
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                  </ListItem>  
                  <ListItem colors="$colorsForTemplate">
                    <Text template="headlineContent">Headline</Text>
                    <Text template="overlineContent">Overline</Text>
                    <Text template="supportingContent">Supporting</Text>
                    <Icon template="leadingContent" image-vector="filled:Add" />
                    <Icon template="trailingContent" image-vector="filled:ChevronRight" />
                  </ListItem>                                                                   
                </Column>
                """
        )
    }
}
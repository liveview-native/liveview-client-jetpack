package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalLayoutApi::class)
class ChipShotTest : LiveViewComposableTest() {

    @Test
    fun assistChipTest() {
        val colorsForTemplate = """
            {
                'containerColor': 'system-blue',
                'labelColor': 'system-yellow',
                'leadingIconContentColor': 'system-white',
                'trailingIconContentColor': 'system-cyan',
                'disabledContainerColor': 'system-light-gray',
                'disabledLabelColor': 'system-gray',
                'disabledLeadingIconContentColor': 'system-dark-gray',
                'disabledTrailingIconContentColor': 'system-black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconContentColor = Color.White,
                    trailingIconContentColor = Color.Cyan,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconContentColor = Color.DarkGray,
                    disabledTrailingIconContentColor = Color.Black,
                )
                FlowRow {
                    AssistChip(onClick = {}, label = { Text("Chip 1") })
                    AssistChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    AssistChip(
                        onClick = {}, label = { Text("Chip 3") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 6") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 7") }, shape = RectangleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <FlowRow>
                  <AssistChip phx-click="">
                    <Text template="label">Chip 1</Text>
                  </AssistChip>     
                  <AssistChip phx-click="" enabled="false">
                    <Text template="label">Chip 2</Text>
                  </AssistChip>      
                  <AssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 3</Text>
                  </AssistChip>    
                  <AssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Text template="label">Chip 4</Text>
                  </AssistChip>                                                         
                  <AssistChip phx-click="">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 5</Text>
                  </AssistChip>
                  <AssistChip phx-click="" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 6</Text>
                  </AssistChip>   
                  <AssistChip phx-click="" shape="rect">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 7</Text>
                  </AssistChip>    
                  <AssistChip phx-click="" border="{'width': '2', 'color': 'system-red'}">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 8</Text>
                  </AssistChip> 
                  <AssistChip phx-click="" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 9</Text>
                  </AssistChip>   
                  <AssistChip phx-click="" colors="$colorsForTemplate" enabled="false">
                    <Icon image-vector="filled:Check" template="leadingIcon"/>
                    <Icon image-vector="filled:ChevronRight" template="trailingIcon"/>
                    <Text template="label">Chip 10</Text>
                  </AssistChip>                                                                                
                </FlowRow>            
                """
        )
    }
}
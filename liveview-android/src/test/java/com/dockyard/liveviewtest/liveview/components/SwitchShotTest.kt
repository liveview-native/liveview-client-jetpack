package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class SwitchShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSwitchTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Switch(checked = true, onCheckedChange = {})
                    Switch(checked = false, onCheckedChange = {})
                    Switch(checked = true, enabled = false, onCheckedChange = {})
                    Switch(checked = false, enabled = false, onCheckedChange = {})
                }
            }, template = """
                <Row>
                  <Switch checked="true" />
                  <Switch checked="false" />
                  <Switch checked="true" enabled="false" />
                  <Switch checked="false" enabled="false" />                  
                </Row>  
                """
        )
    }

    @Test
    fun switchWithCustomThumbTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Switch(
                        checked = true, onCheckedChange = {},
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(checked = false, onCheckedChange = {}, thumbContent = {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    })
                    Switch(
                        checked = true, enabled = false, onCheckedChange = {},
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(checked = false, enabled = false, onCheckedChange = {}, thumbContent = {
                        Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                    })
                }
            }, template = """
                <Row>
                  <Switch checked="true">
                    <Icon image-vector="filled:Check" template="thumb" />
                  </Switch>
                  <Switch checked="false">
                    <Icon image-vector="filled:Cancel" template="thumb" />
                  </Switch>
                  <Switch checked="true" enabled="false">
                    <Icon image-vector="filled:Check" template="thumb" />
                  </Switch>
                  <Switch checked="false" enabled="false">
                    <Icon image-vector="filled:Cancel" template="thumb" />
                  </Switch>                  
                </Row>  
                """
        )
    }

    @Test
    fun switchWithCustomColorsTest() {
        val colorsForTemplate = """
            {            
            'checkedThumbColor': '#FFFFFF00', 
            'checkedTrackColor': '#FFFFFFFF', 
            'checkedBorderColor': '#FFFF0000', 
            'checkedIconColor': '#FF00FF00', 
            'uncheckedThumbColor': '#FF888888', 
            'uncheckedTrackColor': '#FFCCCCCC', 
            'uncheckedBorderColor': '#FF444444', 
            'uncheckedIconColor': '#FF000000', 
            'disabledCheckedThumbColor': '#FFFF00FF', 
            'disabledCheckedTrackColor': '#FF00FFFF', 
            'disabledCheckedBorderColor': '#FF0000FF', 
            'disabledCheckedIconColor': '#FFFF0000', 
            'disabledUncheckedThumbColor': '#FFFF0000', 
            'disabledUncheckedTrackColor': '#FF0000FF', 
            'disabledUncheckedBorderColor': '#FF00FF00', 
            'disabledUncheckedIconColor': '#FF888888'    
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Yellow,
                    checkedTrackColor = Color.White,
                    checkedBorderColor = Color.Red,
                    checkedIconColor = Color.Green,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray,
                    uncheckedBorderColor = Color.DarkGray,
                    uncheckedIconColor = Color.Black,
                    disabledCheckedThumbColor = Color.Magenta,
                    disabledCheckedTrackColor = Color.Cyan,
                    disabledCheckedBorderColor = Color.Blue,
                    disabledCheckedIconColor = Color.Red,
                    disabledUncheckedThumbColor = Color.Red,
                    disabledUncheckedTrackColor = Color.Blue,
                    disabledUncheckedBorderColor = Color.Green,
                    disabledUncheckedIconColor = Color.Gray
                )
                Row(Modifier.padding(16.dp)) {
                    Switch(
                        checked = true, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = true, enabled = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    Switch(
                        checked = false, enabled = false, onCheckedChange = {}, colors = colors,
                        thumbContent = {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = "")
                        },
                    )
                }
            }, template = """
                <Row padding="16">
                  <Switch checked="true" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="thumb" />
                  </Switch>
                  <Switch checked="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Cancel" template="thumb" />
                  </Switch>
                  <Switch checked="true" enabled="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Check" template="thumb" />
                  </Switch>
                  <Switch checked="false" enabled="false" colors="$colorsForTemplate">
                    <Icon image-vector="filled:Cancel" template="thumb" />
                  </Switch>                  
                </Row>  
                """

        )
    }
}
package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class SegmentedButtonShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSingleChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <SingleChoiceSegmentedButtonRow>
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="true" phx-click="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </SingleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun simpleMultiChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                MultiChoiceSegmentedButtonRow {
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(checked = false, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <MultiChoiceSegmentedButtonRow>
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="false" phx-change="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </MultiChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithSpaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                SingleChoiceSegmentedButtonRow(
                    space = 20.dp
                ) {
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(selected = false, onClick = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <SingleChoiceSegmentedButtonRow space="20">
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="true" phx-click="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </SingleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun multiChoiceSegmentedButtonWithSpaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RectangleShape
                MultiChoiceSegmentedButtonRow(space = 20.dp) {
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(checked = false, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = shape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <MultiChoiceSegmentedButtonRow space="20">
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="false" phx-change="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </MultiChoiceSegmentedButtonRow>
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithCustomShapeAndBorderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val border = BorderStroke(2.dp, Color.Magenta)
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = shape, border = border) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <SingleChoiceSegmentedButtonRow>
                  <SegmentedButton selected="false" phx-click="" 
                    shape="8" border-width="2" border-color="system-magenta">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="true" phx-click="" shape="8" 
                    border-width="2" border-color="system-magenta">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="false" phx-click="" shape="8" 
                    border-width="2" border-color="system-magenta">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </SingleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun singleChoiceSegmentedButtonWithCustomIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val border = BorderStroke(2.dp, Color.Magenta)
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(
                        selected = true, onClick = {}, shape = shape, border = border,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        selected = false,
                        onClick = {},
                        shape = shape,
                        border = border
                    ) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <SingleChoiceSegmentedButtonRow>
                  <SegmentedButton selected="false" phx-click="" 
                    shape="8" border-width="2" border-color="system-magenta">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="true" phx-click="" shape="8" 
                    border-width="2" border-color="system-magenta">
                    <Text template="label">Option 2</Text>
                    <Icon template="icon" image-vector="outlined:CheckCircle" />
                  </SegmentedButton>
                  <SegmentedButton selected="false" phx-click="" shape="8" 
                    border-width="2" border-color="system-magenta">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </SingleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun multiChoiceSegmentedButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'activeContainerColor': 'system-green',
            'activeContentColor': 'system-yellow',
            'activeBorderColor': 'system-blue',
            'inactiveContainerColor': 'system-cyan',
            'inactiveContentColor': 'system-white',
            'inactiveBorderColor': 'system-red',
            'disabledActiveContainerColor': 'system-light-gray',
            'disabledActiveContentColor': 'system-dark-gray',
            'disabledActiveBorderColor': 'system-magenta',
            'disabledInactiveContainerColor': 'system-gray',
            'disabledInactiveContentColor': 'system-white',
            'disabledInactiveBorderColor': 'system-black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val shape = RoundedCornerShape(8.dp)
                val colors = SegmentedButtonColors(
                    activeContainerColor = Color.Green,
                    activeContentColor = Color.Yellow,
                    activeBorderColor = Color.Blue,
                    inactiveContainerColor = Color.Cyan,
                    inactiveContentColor = Color.White,
                    inactiveBorderColor = Color.Red,
                    disabledActiveContainerColor = Color.LightGray,
                    disabledActiveContentColor = Color.DarkGray,
                    disabledActiveBorderColor = Color.Magenta,
                    disabledInactiveContainerColor = Color.Gray,
                    disabledInactiveContentColor = Color.White,
                    disabledInactiveBorderColor = Color.Black,
                )
                MultiChoiceSegmentedButtonRow {
                    SegmentedButton(
                        checked = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                    ) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(
                        checked = true,
                        onCheckedChange = {}, shape = shape, colors = colors,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(
                        checked = false,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                    ) {
                        Text(text = "Option 3")
                    }
                    SegmentedButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {},
                        shape = shape,
                        colors = colors,
                        icon = {
                            Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "")
                        },
                    ) {
                        Text(text = "Option 4")
                    }
                }
            },
            template = """
                <MultiChoiceSegmentedButtonRow>
                  <SegmentedButton checked="false" phx-click="" shape="8" colors="$colorsForTemplate">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="true" phx-click="" shape="8" colors="$colorsForTemplate">
                    <Text template="label">Option 2</Text>
                    <Icon template="icon" image-vector="outlined:CheckCircle" />
                  </SegmentedButton>
                  <SegmentedButton checked="false" enabled="false" phx-click="" shape="8" colors="$colorsForTemplate">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="true" enabled="false" phx-click="" shape="8" colors="$colorsForTemplate">
                    <Text template="label">Option 4</Text>
                    <Icon template="icon" image-vector="outlined:CheckCircle" />
                  </SegmentedButton>                  
                </MultiChoiceSegmentedButtonRow>            
                """
        )
    }
}
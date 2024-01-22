package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.robolectric.annotation.Config

@OptIn(ExperimentalMaterial3Api::class)
class TimePickerShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTimePickerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimePicker(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = true
                    )
                )
            }, template = """
                <TimePicker initial-hour="13" initial-minute="30" is-24-hour="true" phx-change="" />            
                """
        )
    }

    @Test
    fun simpleTimeInputTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimeInput(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = true
                    )
                )
            }, template = """
                <TimeInput initial-hour="13" initial-minute="30" is-24-hour="true" phx-change="" />            
                """
        )
    }

    @Test
    fun timePickerWith24HourTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimePicker(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = false
                    )
                )
            }, template = """
                <TimePicker initial-hour="13" initial-minute="30" is-24-hour="false" phx-change="" />            
                """
        )
    }

    @Test
    fun timeInputWith24HourTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimeInput(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = false
                    )
                )
            }, template = """
                <TimeInput initial-hour="13" initial-minute="30" is-24-hour="false" phx-change="" />            
                """
        )
    }

    @Test
    @Config(qualifiers = "+land") // https://robolectric.org/device-configuration/
    fun timePickerHorizontalTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimePicker(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = true
                    ),
                    layoutType = TimePickerLayoutType.Horizontal
                )
            }, template = """
                <TimePicker initial-hour="13" initial-minute="30" is-24-hour="true" 
                  layout-type="horizontal" phx-change="" />            
                """
        )
    }

    @Test
    fun timePickerVerticalTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                TimePicker(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = false
                    ),
                    layoutType = TimePickerLayoutType.Vertical
                )
            }, template = """
                <TimePicker initial-hour="13" initial-minute="30" is-24-hour="false" 
                  layout-type="vertical" phx-change="" />            
                """
        )
    }

    @Test
    fun timePickerWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'clockDialColor': 'system-light-gray', 
            'clockDialSelectedContentColor': 'system-yellow', 
            'clockDialUnselectedContentColor': 'system-dark-gray', 
            'selectorColor': 'system-magenta', 
            'containerColor': 'system-gray', 
            'periodSelectorBorderColor': 'system-red', 
            'periodSelectorSelectedContainerColor': 'system-blue', 
            'periodSelectorUnselectedContainerColor': 'system-cyan', 
            'periodSelectorSelectedContentColor': 'system-green', 
            'periodSelectorUnselectedContentColor': 'system-black', 
            'timeSelectorSelectedContainerColor': 'system-dark-gray', 
            'timeSelectorUnselectedContainerColor': 'system-light-gray', 
            'timeSelectorSelectedContentColor': 'system-white', 
            'timeSelectorUnselectedContentColor': 'system-black' 
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TimePickerDefaults.colors(
                    clockDialColor = Color.LightGray,
                    clockDialSelectedContentColor = Color.Yellow,
                    clockDialUnselectedContentColor = Color.DarkGray,
                    selectorColor = Color.Magenta,
                    containerColor = Color.Gray,
                    periodSelectorBorderColor = Color.Red,
                    periodSelectorSelectedContainerColor = Color.Blue,
                    periodSelectorUnselectedContainerColor = Color.Cyan,
                    periodSelectorSelectedContentColor = Color.Green,
                    periodSelectorUnselectedContentColor = Color.Black,
                    timeSelectorSelectedContainerColor = Color.DarkGray,
                    timeSelectorUnselectedContainerColor = Color.LightGray,
                    timeSelectorSelectedContentColor = Color.White,
                    timeSelectorUnselectedContentColor = Color.Black
                )
                TimePicker(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = false
                    ),
                    colors = colors,
                )
            }, template = """
                <TimePicker initial-hour="13" initial-minute="30" is-24-hour="false" 
                  colors="$colorsForTemplate" phx-change="" />            
                """
        )
    }

    @Test
    fun timeInputWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'clockDialColor': 'system-light-gray', 
            'clockDialSelectedContentColor': 'system-yellow', 
            'clockDialUnselectedContentColor': 'system-dark-gray', 
            'selectorColor': 'system-magenta', 
            'containerColor': 'system-gray', 
            'periodSelectorBorderColor': 'system-red', 
            'periodSelectorSelectedContainerColor': 'system-blue', 
            'periodSelectorUnselectedContainerColor': 'system-cyan', 
            'periodSelectorSelectedContentColor': 'system-green', 
            'periodSelectorUnselectedContentColor': 'system-black', 
            'timeSelectorSelectedContainerColor': 'system-dark-gray', 
            'timeSelectorUnselectedContainerColor': 'system-light-gray', 
            'timeSelectorSelectedContentColor': 'system-white', 
            'timeSelectorUnselectedContentColor': 'system-black' 
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TimePickerDefaults.colors(
                    clockDialColor = Color.LightGray,
                    clockDialSelectedContentColor = Color.Yellow,
                    clockDialUnselectedContentColor = Color.DarkGray,
                    selectorColor = Color.Magenta,
                    containerColor = Color.Gray,
                    periodSelectorBorderColor = Color.Red,
                    periodSelectorSelectedContainerColor = Color.Blue,
                    periodSelectorUnselectedContainerColor = Color.Cyan,
                    periodSelectorSelectedContentColor = Color.Green,
                    periodSelectorUnselectedContentColor = Color.Black,
                    timeSelectorSelectedContainerColor = Color.DarkGray,
                    timeSelectorUnselectedContainerColor = Color.LightGray,
                    timeSelectorSelectedContentColor = Color.White,
                    timeSelectorUnselectedContentColor = Color.Black
                )
                TimeInput(
                    state = rememberTimePickerState(
                        initialHour = 13, initialMinute = 30, is24Hour = false
                    ),
                    colors = colors,
                )
            }, template = """
                <TimeInput initial-hour="13" initial-minute="30" is-24-hour="false" 
                  colors="$colorsForTemplate" phx-change="" />            
                """
        )
    }
}
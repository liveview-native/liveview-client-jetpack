package org.phoenixframework.liveview.test.ui.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialHour
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialMinute
import org.phoenixframework.liveview.data.constants.Attrs.attrIs24Hour
import org.phoenixframework.liveview.data.constants.Attrs.attrLayoutType
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrClockDialColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrClockDialSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrClockDialUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrPeriodSelectorBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrPeriodSelectorSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrPeriodSelectorSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrPeriodSelectorUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrPeriodSelectorUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTimeSelectorSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTimeSelectorSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTimeSelectorUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTimeSelectorUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.timeInput
import org.phoenixframework.liveview.data.constants.ComposableTypes.timePicker
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.TimePickerLayoutTypeValues
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
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
                <$timePicker 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="true" />            
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
                <$timeInput 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="true" />            
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
                <$timePicker 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="false" />            
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
                <$timeInput 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="false" />            
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
                <$timePicker 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="true" 
                  $attrLayoutType="${TimePickerLayoutTypeValues.horizontal}" />            
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
                <$timePicker 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="false" 
                  $attrLayoutType="${TimePickerLayoutTypeValues.vertical}" />
                """
        )
    }

    @Test
    fun timePickerWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrClockDialColor': '$LightGray', 
            '$colorAttrClockDialSelectedContentColor': '$Yellow', 
            '$colorAttrClockDialUnselectedContentColor': '$DarkGray', 
            '$colorAttrSelectorColor': '$Magenta', 
            '$colorAttrContainerColor': '$Gray', 
            '$colorAttrPeriodSelectorBorderColor': '$Red', 
            '$colorAttrPeriodSelectorSelectedContainerColor': '$Blue', 
            '$colorAttrPeriodSelectorUnselectedContainerColor': '$Cyan', 
            '$colorAttrPeriodSelectorSelectedContentColor': '$Green', 
            '$colorAttrPeriodSelectorUnselectedContentColor': '$Black', 
            '$colorAttrTimeSelectorSelectedContainerColor': '$DarkGray', 
            '$colorAttrTimeSelectorUnselectedContainerColor': '$LightGray', 
            '$colorAttrTimeSelectorSelectedContentColor': '$White', 
            '$colorAttrTimeSelectorUnselectedContentColor': '$Black' 
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
                <$timePicker 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="false" 
                  $attrColors="$colorsForTemplate" />            
                """
        )
    }

    @Test
    fun timeInputWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrClockDialColor': '$LightGray', 
            '$colorAttrClockDialSelectedContentColor': '$Yellow', 
            '$colorAttrClockDialUnselectedContentColor': '$DarkGray', 
            '$colorAttrSelectorColor': '$Magenta', 
            '$colorAttrContainerColor': '$Gray', 
            '$colorAttrPeriodSelectorBorderColor': '$Red', 
            '$colorAttrPeriodSelectorSelectedContainerColor': '$Blue', 
            '$colorAttrPeriodSelectorUnselectedContainerColor': '$Cyan', 
            '$colorAttrPeriodSelectorSelectedContentColor': '$Green', 
            '$colorAttrPeriodSelectorUnselectedContentColor': '$Black', 
            '$colorAttrTimeSelectorSelectedContainerColor': '$DarkGray', 
            '$colorAttrTimeSelectorUnselectedContainerColor': '$LightGray', 
            '$colorAttrTimeSelectorSelectedContentColor': '$White', 
            '$colorAttrTimeSelectorUnselectedContentColor': '$Black' 
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
                <$timeInput 
                  $attrInitialHour="13" 
                  $attrInitialMinute="30" 
                  $attrIs24Hour="false" 
                  $attrColors="$colorsForTemplate" />            
                """
        )
    }
}
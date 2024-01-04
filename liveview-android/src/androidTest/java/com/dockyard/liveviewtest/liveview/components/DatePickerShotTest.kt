package com.dockyard.liveviewtest.liveview.components

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerShotTest : LiveViewComposableTest() {
    private val jun9th2023 = 1686279600000L

    @Test
    fun simpleDatePickerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(state = rememberDatePickerState(initialSelectedDateMillis = jun9th2023))
            },
            template = """  
                <DatePicker initial-selected-date-millis="$jun9th2023" />
                """
        )
    }

    @Test
    fun datePickerInitialDisplayAsInputTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = jun9th2023,
                        initialDisplayMode = DisplayMode.Input
                    ),
                )
            },
            template = """  
                <DatePicker initial-selected-date-millis="$jun9th2023" 
                  initial-display-mode="input"/>
                """
        )
    }

    @Test
    fun datePickerHideToggleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = jun9th2023,
                        initialDisplayMode = DisplayMode.Input
                    ),
                    showModeToggle = false
                )
            },
            template = """  
                <DatePicker initial-selected-date-millis="$jun9th2023" 
                  initial-display-mode="input" show-mode-toggle="false" />
                """
        )
    }

    @Test
    fun datePickerWithCustomTitleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = jun9th2023,
                    ),
                    title = {
                        Text(text = "Select an important date", color = Color.Red)
                    }
                )
            },
            template = """
                <DatePicker initial-selected-date-millis="$jun9th2023">
                    <Text template="title" color="system-red">Select an important date</Text>
                </DatePicker>
                """
        )
    }

    @Test
    fun datePickerWithCustomHeadlineTest() {
        val formattedDate =
            SimpleDateFormat("dd/MM/yyyy")
                .apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                .format(Date(jun9th2023))
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = jun9th2023,
                    ),
                    title = {
                        Text(text = "Select an important date", color = Color.Red)
                    },
                    headline = {
                        Text(text = formattedDate, color = Color.Blue)
                    }
                )
            },
            template = """
                <DatePicker initial-selected-date-millis="$jun9th2023">
                    <Text template="title" color="system-red">Select an important date</Text>
                    <Text template="headline" color="system-blue">$formattedDate</Text>
                </DatePicker>
                """
        )
    }

    @Test
    fun datePickerWithCustomColorsTest() {
        datePickerWithCustomColors(false)
    }

    @Test
    fun datePickerWithCustomColorsAndContainerTest() {
        datePickerWithCustomColors(true)
    }

    private fun datePickerWithCustomColors(showContainer: Boolean) {
        // Using a day in the same month in order to check the today and selected UI element
        val todayMoreOrLessOneDay = Calendar.getInstance().apply {
            when {
                get(Calendar.DAY_OF_MONTH) > 15 ->
                    add(Calendar.DAY_OF_MONTH, -1)

                get(Calendar.DAY_OF_MONTH) < 15 ->
                    add(Calendar.DAY_OF_MONTH, 1)

                else ->
                    set(Calendar.DAY_OF_MONTH, 10)
            }
            add(Calendar.YEAR, -1)
        }.timeInMillis
        val colorsForTemplate = """
            {            
            'containerColor': 'system-yellow',
            'titleContentColor': 'system-red',
            'headlineContentColor': 'system-blue',
            'weekdayContentColor': 'system-yellow',
            'subheadContentColor': 'system-red',
            'yearContentColor': 'system-green',
            'currentYearContentColor': 'system-black',
            'selectedYearContentColor': 'system-white',
            'selectedYearContainerColor': 'system-dark-gray',
            'dayContentColor': 'system-gray',
            'selectedDayContentColor': 'system-yellow',
            'selectedDayContainerColor': 'system-red',
            'todayContentColor': 'system-blue',
            'todayDateBorderColor': 'system-red'
            }
            """.toJsonForTemplate()

        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(
                    state = rememberDatePickerState(
                        initialSelectedDateMillis = todayMoreOrLessOneDay,
                    ),
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.Yellow,
                        titleContentColor = Color.Red,
                        headlineContentColor = Color.Blue,
                        weekdayContentColor = Color.Yellow,
                        subheadContentColor = Color.Red,
                        yearContentColor = Color.Green,
                        currentYearContentColor = Color.Black,
                        selectedYearContentColor = Color.White,
                        selectedYearContainerColor = Color.DarkGray,
                        dayContentColor = Color.Gray,
                        selectedDayContentColor = Color.Yellow,
                        selectedDayContainerColor = Color.Red,
                        todayContentColor = Color.Blue,
                        todayDateBorderColor = Color.Red,
                    )
                )
            },
            template = """  
                <DatePicker initial-selected-date-millis="$todayMoreOrLessOneDay" 
                    colors="$colorsForTemplate" />
                """,
            delayBeforeScreenshot = 500, // Wait the button animation finishes.
            onBeforeScreenShot = { rule ->
                if (showContainer) {
                    val formattedDate =
                        SimpleDateFormat("MMMM yyyy")
                            .apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            .format(Date(todayMoreOrLessOneDay))
                    // Clicking on the year selection button in order to show the container
                    rule.onNodeWithText(formattedDate).performClick()
                }
            }
        )
    }
}
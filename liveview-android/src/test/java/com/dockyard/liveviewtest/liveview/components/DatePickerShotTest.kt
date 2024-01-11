package com.dockyard.liveviewtest.liveview.components

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerShotTest : LiveViewComposableTest() {
    private val jun9th2023 = 1686279600000L
    private val jun22nd2023 = 1687402800000L

    @Test
    fun simpleDatePickerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DatePicker(state = rememberDatePickerState())
            },
            template = """  
                <DatePicker />
                """
        )
    }

    @Test
    fun datePickerSelectedTest() {
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
            delayBeforeScreenshot = 800, // Wait the button animation finishes.
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

    @Test
    fun simpleDateRangePickerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(state = rememberDateRangePickerState())
            },
            template = """  
                <DateRangePicker />
                """
        )
    }

    @Test
    fun dateRangePickerPartiallySelectedTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(initialSelectedStartDateMillis = jun9th2023)
                )
            },
            template = """  
                <DateRangePicker initial-selected-start-date-millis="$jun9th2023"/>
                """
        )
    }

    @Test
    fun dateRangePickerSelectedTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = jun9th2023,
                        initialSelectedEndDateMillis = jun22nd2023,
                    )
                )
            },
            template = """  
                <DateRangePicker 
                  initial-selected-start-date-millis="$jun9th2023"
                  initial-selected-end-date-millis="$jun22nd2023" />
                """
        )
    }

    @Test
    fun dateRangePickerInitialDisplayAsInputTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = jun9th2023,
                        initialSelectedEndDateMillis = jun22nd2023,
                        initialDisplayMode = DisplayMode.Input
                    ),
                )
            },
            template = """  
                <DateRangePicker 
                  initial-selected-start-date-millis="$jun9th2023" 
                  initial-selected-end-date-millis="$jun22nd2023" 
                  initial-display-mode="input"/>
                """
        )
    }

    @Test
    fun dateRangePickerHideToggleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = jun9th2023,
                        initialDisplayMode = DisplayMode.Input
                    ),
                    showModeToggle = false
                )
            },
            template = """  
                <DateRangePicker initial-selected-start-date-millis="$jun9th2023" 
                  initial-display-mode="input" show-mode-toggle="false" />
                """
        )
    }

    @Test
    fun dateRangePickerWithCustomTitleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = jun9th2023,
                    ),
                    title = {
                        Text(text = "Select a date range", color = Color.Red)
                    }
                )
            },
            template = """
                <DateRangePicker initial-selected-start-date-millis="$jun9th2023">
                    <Text template="title" color="system-red">Select a date range</Text>
                </DateRangePicker>
                """
        )
    }

    @Test
    fun dateRangePickerWithCustomHeadlineTest() {
        val formatter =
            SimpleDateFormat("dd/MM/yyyy")
                .apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }

        val startDate = formatter.format(Date(jun9th2023))
        val endDate = formatter.format(Date(jun22nd2023))
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = jun9th2023,
                        initialSelectedEndDateMillis = jun22nd2023,
                    ),
                    title = {
                        Text(text = "Select a date range", color = Color.Red)
                    },
                    headline = {
                        Text(text = "$startDate | $endDate", color = Color.Blue)
                    }
                )
            },
            template = """
                <DateRangePicker initial-selected-start-date-millis="$jun9th2023"
                  initial-selected-end-date-millis="$jun22nd2023">
                    <Text template="title" color="system-red">Select a date range</Text>
                    <Text template="headline" color="system-blue">$startDate | $endDate</Text>
                </DateRangePicker>
                """
        )
    }

    @Test
    fun dateRangePickerWithCustomColorsTest() {
        // Using a day in the same month in order to check the today and selected UI element
        val startDate = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 10)
        }.timeInMillis
        val endDate = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 20)
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
            'todayDateBorderColor': 'system-red',
            'dayInSelectionRangeContentColor': 'system-white',
            'dayInSelectionRangeContainerColor': 'system-dark-gray'            
            }
            """.toJsonForTemplate()

        compareNativeComposableWithTemplate(
            nativeComposable = {
                DateRangePicker(
                    state = rememberDateRangePickerState(
                        initialSelectedStartDateMillis = startDate,
                        initialSelectedEndDateMillis = endDate
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
                        dayInSelectionRangeContentColor = Color.White,
                        dayInSelectionRangeContainerColor = Color.DarkGray,
                    )
                )
            },
            template = """  
                <DateRangePicker 
                  initial-selected-start-date-millis="$startDate"
                  initial-selected-end-date-millis="$endDate"
                  colors="$colorsForTemplate" />
                """,
        )
    }
}
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
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialDisplayMode
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedEndDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedStartDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrShowModeToggle
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCurrentYearContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayInSelectionRangeContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayInSelectionRangeContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrHeadlineContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedDayContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedDayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedYearContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedYearContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSubheadContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTodayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTodayDateBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrWeekdayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrYearContentColor
import org.phoenixframework.liveview.data.constants.DatePickerDisplayModeValues
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateHeadline
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.domain.base.ComposableTypes.datePicker
import org.phoenixframework.liveview.domain.base.ComposableTypes.dateRangePicker
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
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
                <$datePicker />
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
                <$datePicker $attrInitialSelectedDateMillis="$jun9th2023" />
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
                <$datePicker 
                  $attrInitialSelectedDateMillis="$jun9th2023" 
                  $attrInitialDisplayMode="input"/>
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
                <$datePicker 
                  $attrInitialSelectedDateMillis="$jun9th2023" 
                  $attrInitialDisplayMode="${DatePickerDisplayModeValues.input}" 
                  $attrShowModeToggle="false" />
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
                <$datePicker $attrInitialSelectedDateMillis="$jun9th2023">
                  <$text $attrTemplate="$templateTitle" $attrColor="$Red">Select an important date</$text>
                </$datePicker>
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
                <$datePicker $attrInitialSelectedDateMillis="$jun9th2023">
                  <$text $attrTemplate="$templateTitle" $attrColor="$Red">Select an important date</$text>
                  <$text $attrTemplate="$templateHeadline" $attrColor="$Blue">$formattedDate</$text>
                </$datePicker>
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
            '$colorAttrContainerColor': '$Yellow',
            '$colorAttrTitleContentColor': '$Red',
            '$colorAttrHeadlineContentColor': '$Blue',
            '$colorAttrWeekdayContentColor': '$Yellow',
            '$colorAttrSubheadContentColor': '$Red',
            '$colorAttrYearContentColor': '$Green',
            '$colorAttrCurrentYearContentColor': '$Black',
            '$colorAttrSelectedYearContentColor': '$White',
            '$colorAttrSelectedYearContainerColor': '$DarkGray',
            '$colorAttrDayContentColor': '$Gray',
            '$colorAttrSelectedDayContentColor': '$Yellow',
            '$colorAttrSelectedDayContainerColor': '$Red',
            '$colorAttrTodayContentColor': '$Blue',
            '$colorAttrTodayDateBorderColor': '$Red'
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
                <$datePicker $attrInitialSelectedDateMillis="$todayMoreOrLessOneDay" 
                    $attrColors="$colorsForTemplate" />
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
                <$dateRangePicker />
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
                <$dateRangePicker $attrInitialSelectedStartDateMillis="$jun9th2023"/>
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
                <$dateRangePicker 
                  $attrInitialSelectedStartDateMillis="$jun9th2023"
                  $attrInitialSelectedEndDateMillis="$jun22nd2023" />
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
                <$dateRangePicker 
                  $attrInitialSelectedStartDateMillis="$jun9th2023" 
                  $attrInitialSelectedEndDateMillis="$jun22nd2023" 
                  $attrInitialDisplayMode="${DatePickerDisplayModeValues.input}"/>
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
                <$dateRangePicker 
                  $attrInitialSelectedStartDateMillis="$jun9th2023" 
                  $attrInitialDisplayMode="${DatePickerDisplayModeValues.input}" 
                  $attrShowModeToggle="false" />
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
                <$dateRangePicker $attrInitialSelectedStartDateMillis="$jun9th2023">
                  <$text $attrTemplate="$templateTitle" $attrColor="$Red">Select a date range</$text>
                </$dateRangePicker>
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
                <$dateRangePicker 
                  $attrInitialSelectedStartDateMillis="$jun9th2023"
                  $attrInitialSelectedEndDateMillis="$jun22nd2023">
                    <$text $attrTemplate="$templateTitle" $attrColor="$Red">Select a date range</$text>
                    <$text $attrTemplate="$templateHeadline" $attrColor="$Blue">$startDate | $endDate</$text>
                </$dateRangePicker>
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
            '$colorAttrContainerColor': '$Yellow',
            '$colorAttrTitleContentColor': '$Red',
            '$colorAttrHeadlineContentColor': '$Blue',
            '$colorAttrWeekdayContentColor': '$Yellow',
            '$colorAttrSubheadContentColor': '$Red',
            '$colorAttrYearContentColor': '$Green',
            '$colorAttrCurrentYearContentColor': '$Black',
            '$colorAttrSelectedYearContentColor': '$White',
            '$colorAttrSelectedYearContainerColor': '$DarkGray',
            '$colorAttrDayContentColor': '$Gray',
            '$colorAttrSelectedDayContentColor': '$Yellow',
            '$colorAttrSelectedDayContainerColor': '$Red',
            '$colorAttrTodayContentColor': '$Blue',
            '$colorAttrTodayDateBorderColor': '$Red',
            '$colorAttrDayInSelectionRangeContentColor': '$White',
            '$colorAttrDayInSelectionRangeContainerColor': '$DarkGray'            
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
                <$dateRangePicker 
                  $attrInitialSelectedStartDateMillis="$startDate"
                  $attrInitialSelectedEndDateMillis="$endDate"
                  $attrColors="$colorsForTemplate" />
                """,
        )
    }
}
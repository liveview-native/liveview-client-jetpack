package com.dockyard.liveviewtest.liveview.components

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrTestTag
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCurrentYearContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayContentColor
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
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateConfirmButton
import org.phoenixframework.liveview.data.constants.Templates.templateDismissButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.datePicker
import org.phoenixframework.liveview.domain.base.ComposableTypes.datePickerDialog
import org.phoenixframework.liveview.domain.base.ComposableTypes.outlinedButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.base.ComposableTypes.textButton
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class DatePickerDialogShotTest : LiveViewComposableTest() {
    private val jun9th2023 = 1686279600000L

    @Test
    fun simpleDatePickerDialogTest() {
        val testTag = "myAlert"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                DatePickerDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                    confirmButton = {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = "Dismiss")
                        }
                    },
                    content = {
                        DatePicker(state = rememberDatePickerState(initialSelectedDateMillis = jun9th2023))
                    }
                )
            },
            template = """
                <$datePickerDialog $attrTestTag="$testTag">
                  <$outlinedButton $attrTemplate="$templateConfirmButton">
                    <$text>Confirm</$text>
                  </$outlinedButton>
                  <$textButton $attrTemplate="$templateDismissButton">
                    <$text>Dismiss</$text>
                  </$textButton>
                  <$datePicker $attrInitialSelectedDateMillis="$jun9th2023" />
                </$datePickerDialog>            
                """
        )
    }

    @Test
    fun datePickerDialogWithCustomColorsTest() {
        datePickerDialogWithCustomColors(false)
    }

    @Test
    fun datePickerDialogWithCustomColorsAndContainerTest() {
        datePickerDialogWithCustomColors(true)
    }

    private fun datePickerDialogWithCustomColors(showContainer: Boolean) {
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
        val testTag = "myAlert"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                DatePickerDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                    confirmButton = {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = "Dismiss")
                        }
                    },
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
                    ),
                    content = {
                        DatePicker(state = rememberDatePickerState(initialSelectedDateMillis = todayMoreOrLessOneDay))
                    }
                )
            },
            template = """
                <$datePickerDialog $attrTestTag="$testTag" $attrColors="$colorsForTemplate">
                  <$outlinedButton $attrTemplate="$templateConfirmButton">
                    <$text>Confirm</$text>
                  </$outlinedButton>
                  <$textButton $attrTemplate="$templateDismissButton">
                    <$text>Dismiss</$text>
                  </$textButton>
                  <$datePicker $attrInitialSelectedDateMillis="$todayMoreOrLessOneDay" />
                </$datePickerDialog>            
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
    fun datePickerDialogWithCustomPickerColorsTest() {
        datePickerWithCustomPickerColors(false)
    }

    @Test
    fun datePickerDialogWithCustomPickerColorsAndContainerTest() {
        datePickerWithCustomPickerColors(true)
    }

    private fun datePickerWithCustomPickerColors(showContainer: Boolean) {
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
        val testTag = "myAlert"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                DatePickerDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                    confirmButton = {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = "Dismiss")
                        }
                    },
                    content = {
                        DatePicker(
                            state = rememberDatePickerState(initialSelectedDateMillis = todayMoreOrLessOneDay),
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
                            ),
                        )
                    }
                )
            },
            template = """
                <$datePickerDialog $attrTestTag="$testTag">
                  <$outlinedButton $attrTemplate="$templateConfirmButton">
                    <$text>Confirm</$text>
                  </$outlinedButton>
                  <$textButton $attrTemplate="$templateDismissButton">
                    <$text>Dismiss</$text>
                  </$textButton>
                  <$datePicker  
                    $attrInitialSelectedDateMillis="$todayMoreOrLessOneDay" 
                    $attrColors="$colorsForTemplate" />
                </$datePickerDialog>            
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
}
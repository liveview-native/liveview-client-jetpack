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
                <DatePickerDialog phx-click="hideDialog" test-tag="$testTag">
                  <OutlinedButton phx-click="hideDialog" template="confirm">
                    <Text>Confirm</Text>
                  </OutlinedButton>
                  <TextButton phx-click="hideDialog" template="dismiss">
                    <Text>Dismiss</Text>
                  </TextButton>
                  <DatePicker phx-change="selectDate" initial-selected-date-millis="$jun9th2023" />
                </DatePickerDialog>            
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
                <DatePickerDialog phx-click="hideDialog" test-tag="$testTag" colors="$colorsForTemplate">
                  <OutlinedButton phx-click="hideDialog" template="confirm">
                    <Text>Confirm</Text>
                  </OutlinedButton>
                  <TextButton phx-click="hideDialog" template="dismiss">
                    <Text>Dismiss</Text>
                  </TextButton>
                  <DatePicker phx-change="selectDate" initial-selected-date-millis="$todayMoreOrLessOneDay" />
                </DatePickerDialog>            
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
                <DatePickerDialog phx-click="hideDialog" test-tag="$testTag">
                  <OutlinedButton phx-click="hideDialog" template="confirm">
                    <Text>Confirm</Text>
                  </OutlinedButton>
                  <TextButton phx-click="hideDialog" template="dismiss">
                    <Text>Dismiss</Text>
                  </TextButton>
                  <DatePicker phx-change="selectDate" 
                    initial-selected-date-millis="$todayMoreOrLessOneDay" 
                    colors="$colorsForTemplate" />
                </DatePickerDialog>            
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
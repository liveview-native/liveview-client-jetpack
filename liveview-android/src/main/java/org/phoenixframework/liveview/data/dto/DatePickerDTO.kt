package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialDisplayMode
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialDisplayedMonthMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrShowModeToggle
import org.phoenixframework.liveview.data.constants.Attrs.attrYearRange
import org.phoenixframework.liveview.data.constants.ColorAttrs
import org.phoenixframework.liveview.data.constants.Templates.templateHeadline
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design date picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class DatePickerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val colors = builder.colors?.toImmutableMap()
    private val initialDisplayedMonthMillis = builder.initialDisplayedMonthMillis
    private val initialDisplayMode = builder.initialDisplayMode
    private val initialSelectedDateMillis = builder.initialSelectedDateMillis
    private val onChanged = builder.onChanged
    private val showModeToggle = builder.showModeToggle
    private val yearRange = builder.yearRange

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTitle }
        }
        val headline = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateHeadline }
        }
        val state = rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDateMillis,
            initialDisplayedMonthMillis = initialDisplayedMonthMillis ?: initialSelectedDateMillis,
            yearRange = yearRange ?: DatePickerDefaults.YearRange,
            initialDisplayMode = initialDisplayMode ?: DisplayMode.Picker,
        )
        val dateFormatter = remember { DatePickerFormatter() }
        DatePicker(
            state = state,
            modifier = modifier,
            dateFormatter = dateFormatter,
            // TODO Custom DatePickerFormatter,
            // TODO dateValidator = {},
            title = {
                title?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DatePickerDefaults.DatePickerTitle(
                    state, modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                )
            },
            headline = {
                headline?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DatePickerDefaults.DatePickerHeadline(
                    state,
                    dateFormatter,
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, bottom = 12.dp),
                )
            },
            showModeToggle = showModeToggle,
            colors = getDatePickerColors(colors),
        )
        LaunchedEffect(state.selectedDateMillis) {
            state.selectedDateMillis?.let {
                pushEvent.invoke(ComposableBuilder.EVENT_TYPE_CHANGE, onChanged, it, null)
            }
        }
    }

    @Composable
    private fun getDatePickerColors(colors: ImmutableMap<String, String>?): DatePickerColors {
        val defaultValue = DatePickerDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            DatePickerDefaults.colors(
                containerColor = colors[ColorAttrs.colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                titleContentColor = colors[ColorAttrs.colorAttrTitleContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                headlineContentColor = colors[ColorAttrs.colorAttrHeadlineContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                weekdayContentColor = colors[ColorAttrs.colorAttrWeekdayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                subheadContentColor = colors[ColorAttrs.colorAttrSubheadContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                yearContentColor = colors[ColorAttrs.colorAttrYearContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                currentYearContentColor = colors[ColorAttrs.colorAttrCurrentYearContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                selectedYearContentColor = colors[ColorAttrs.colorAttrSelectedYearContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                selectedYearContainerColor = colors[ColorAttrs.colorAttrSelectedYearContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                dayContentColor = colors[ColorAttrs.colorAttrDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                disabledDayContentColor = colors[ColorAttrs.colorAttrDisabledDayContentColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrDayContentColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface).copy(alpha = 0.38f),
                selectedDayContentColor = colors[ColorAttrs.colorAttrSelectedDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                disabledSelectedDayContentColor = colors[ColorAttrs.colorAttrDisabledSelectedDayContentColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrSelectedDayContentColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onPrimary).copy(alpha = 0.38f),
                selectedDayContainerColor = colors[ColorAttrs.colorAttrSelectedDayContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledSelectedDayContainerColor = colors[ColorAttrs.colorAttrDisabledSelectedDayContainerColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrSelectedDayContainerColor]?.toColor()
                        ?: MaterialTheme.colorScheme.primary).copy(alpha = 0.38f),
                todayContentColor = colors[ColorAttrs.colorAttrTodayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                todayDateBorderColor = colors[ColorAttrs.colorAttrTodayBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                dayInSelectionRangeContentColor = colors[ColorAttrs.colorAttrDayInSelectionRangeContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
                dayInSelectionRangeContainerColor = colors[ColorAttrs.colorAttrDayInSelectionRangeContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var colors: Map<String, String>? = null
            private set
        var initialDisplayedMonthMillis: Long? = null
            private set
        var initialDisplayMode: DisplayMode? = null
            private set
        var initialSelectedDateMillis: Long? = null
            private set
        var onChanged: String = ""
            private set
        var showModeToggle: Boolean = true
            private set
        var yearRange: IntRange? = null
            private set

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide a null to indicate no selection.
         *
         * ```
         * <DatePicker initial-selected-date-millis="" />
         * ```
         * @param initialSelectedDate initial selection of a date in UTC milliseconds.
         */
        fun initialSelectedDateMillis(initialSelectedDate: String) = apply {
            this.initialSelectedDateMillis = initialSelectedDate.toLongOrNull()
        }

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * month to be displayed to the user. By default, in case an initialSelectedDateMillis is
         * provided, the initial displayed month would be the month of the selected date.
         * Otherwise, in case null is provided, the displayed month would be the current one.
         *
         * ```
         * <DatePicker initial-displayed-month-millis="" />
         * ```
         * @param initialDisplayedMonth initial selection of a month in UTC milliseconds.
         */
        fun initialDisplayedMonthMillis(initialDisplayedMonth: String) = apply {
            this.initialDisplayedMonthMillis = initialDisplayedMonth.toLongOrNull()
        }

        /**
         * An int range separated by comma ',' that holds the year range that the date picker will
         * be limited to.
         *
         * ```
         * <DatePicker year-range="1900,2024" />
         * ```
         * @param yearRange a string containing two integers separated by comma representing the
         * min and max year respectively.
         */
        fun yearRange(yearRange: String) = apply {
            this.yearRange = try {
                yearRange.split(',').map { it.toInt() }.let { list ->
                    list[0]..list[1]
                }
            } catch (e: Exception) {
                null
            }
        }

        /**
         * An initial display mode for this date picker.
         * The supported values are: `input` and `picker.
         *
         * ```
         * <DatePicker display-mode="input" />
         * ```
         * @param displayMode the initial display mode.
         */
        fun initialDisplayMode(displayMode: String) = apply {
            this.initialDisplayMode = when (displayMode) {
                "input" -> DisplayMode.Input
                "picker" -> DisplayMode.Picker
                else -> DisplayMode.Picker
            }
        }

        /**
         * Function in the server to be called when the date picker state changes.
         * ```
         * <DatePicker phx-change="updateDate" />
         * ```
         * @param onChanged the name of the function to be called in the server when the date is
         * selected.
         */
        fun onChanged(onChanged: String) = apply {
            this.onChanged = onChanged
        }

        /**
         * Indicates if this DatePicker should show a mode toggle action that transforms it into a
         * date input.
         *
         * ```
         * <DatePicker show-mode-toggle="true" />
         * ```
         * @param showModeToggle true if the toggle mode must be displayed, false otherwise.
         */
        fun showModeToggle(showModeToggle: String) = apply {
            this.showModeToggle = showModeToggle.toBoolean()
        }

        /**
         * Set DatePicker colors.
         * ```
         * <DatePicker
         *   colors="{'checkedThumbColor': '#FFFF0000', 'checkedTrackColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `containerColor`, `titleContentColor`, `headlineContentColor`,
         * `weekdayContentColor`, `subheadContentColor`, `yearContentColor`,
         * `currentYearContentColor`, `selectedYearContentColor`, `selectedYearContainerColor`,
         * `dayContentColor`, `disabledDayContentColor`, `selectedDayContentColor`,
         * `disabledSelectedDayContentColor`, `selectedDayContainerColor`,
         * `disabledSelectedDayContainerColor`, `todayContentColor`, `todayDateBorderColor`,
         * `dayInSelectionRangeContentColor`, and `dayInSelectionRangeContainerColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        fun build() = DatePickerDTO(this)
    }
}

internal object DatePickerDtoFactory :
    ComposableViewFactory<DatePickerDTO, DatePickerDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): DatePickerDTO = attributes.fold(DatePickerDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColors -> builder.colors(attribute.value)
            attrInitialDisplayedMonthMillis -> builder.initialDisplayedMonthMillis(attribute.value)
            attrInitialDisplayMode -> builder.initialDisplayMode(attribute.value)
            attrInitialSelectedDateMillis -> builder.initialSelectedDateMillis(attribute.value)
            attrPhxChange -> builder.onChanged(attribute.value)
            attrShowModeToggle -> builder.showModeToggle(attribute.value)
            attrYearRange -> builder.yearRange(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as DatePickerDTO.Builder
    }.build()
}
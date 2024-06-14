package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialDisplayMode
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialDisplayedMonthMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedEndDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialSelectedStartDateMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrShowModeToggle
import org.phoenixframework.liveview.data.constants.Attrs.attrYearRange
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCurrentYearContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayInSelectionRangeContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDayInSelectionRangeContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledDayContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedDayContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedDayContentColor
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
import org.phoenixframework.liveview.data.constants.Templates.templateHeadline
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design date picker.
 * You can use a `DatePicker` or `DateRangePicker`.
 * ```
 * <DatePicker
 *   phx-change="selectDate"
 *   initialSelectedDateMillis="1705028400000"
 * />
 * <DateRangePicker
 *   phx-change="selectDateRange"
 *   initialSelectedStartDateMillis={"#{Enum.at(@dateRange,0)}"}
 *   initialSelectedEndDateMillis={"#{Enum.at(@dateRange,1)}"}
 * />
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class DatePickerView private constructor(props: Properties) :
    ComposableView<DatePickerView.Properties>(props) {

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
        when (composableNode?.node?.tag) {
            ComposableTypes.datePicker ->
                DatePickerImpl(composableNode, title, headline, pushEvent)

            ComposableTypes.dateRangePicker ->
                DateRangePickerImpl(composableNode, title, headline, pushEvent)
        }
    }

    @Composable
    private fun DatePickerImpl(
        composableNode: ComposableTreeNode,
        title: ComposableTreeNode?,
        headline: ComposableTreeNode?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors
        val initialDisplayedMonthMillis = props.initialDisplayedMonthMillis
        val initialDisplayMode = props.initialDisplayMode
        val initialSelectedDateMillis = props.initialSelectedDateMillis
        val onChanged = props.onChanged
        val showModeToggle = props.showModeToggle
        val yearRange = remember(props.yearStart, props.yearEnd) {
            IntRange(
                props.yearStart ?: DatePickerDefaults.YearRange.first,
                props.yearEnd ?: DatePickerDefaults.YearRange.last,
            )
        }

        val state = rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDateMillis,
            initialDisplayedMonthMillis = initialDisplayedMonthMillis ?: initialSelectedDateMillis,
            yearRange = yearRange,
            initialDisplayMode = initialDisplayMode ?: DisplayMode.Picker,
        )
        val dateFormatter = remember { DatePickerDefaults.dateFormatter() }
        DatePicker(
            state = state,
            modifier = props.commonProps.modifier,
            dateFormatter = dateFormatter,
            // TODO Custom DatePickerFormatter,
            // TODO dateValidator = {},
            title = {
                title?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DatePickerDefaults.DatePickerTitle(
                    displayMode = state.displayMode,
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
                )
            },
            headline = {
                headline?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DatePickerDefaults.DatePickerHeadline(
                    selectedDateMillis = state.selectedDateMillis,
                    displayMode = state.displayMode,
                    dateFormatter = dateFormatter,
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, bottom = 12.dp)
                )
            },
            showModeToggle = showModeToggle,
            colors = getDatePickerColors(colors),
        )
        LaunchedEffect(state.selectedDateMillis) {
            state.selectedDateMillis?.let { dateInMillis ->
                pushEvent(
                    ComposableBuilder.EVENT_TYPE_CHANGE,
                    onChanged,
                    mergeValueWithPhxValue(KEY_DATE, dateInMillis),
                    null
                )
            }
        }
    }

    @Composable
    private fun DateRangePickerImpl(
        composableNode: ComposableTreeNode,
        title: ComposableTreeNode?,
        headline: ComposableTreeNode?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors
        val initialDisplayedMonthMillis = props.initialDisplayedMonthMillis
        val initialDisplayMode = props.initialDisplayMode
        val initialSelectedStartDateMillis = props.initialSelectedStartDateMillis
        val initialSelectedEndDateMillis = props.initialSelectedEndDateMillis
        val onChanged = props.onChanged
        val showModeToggle = props.showModeToggle
        val yearRange = IntRange(
            props.yearStart ?: DatePickerDefaults.YearRange.first,
            props.yearEnd ?: DatePickerDefaults.YearRange.last,
        )

        val state = rememberDateRangePickerState(
            initialSelectedStartDateMillis = initialSelectedStartDateMillis,
            initialSelectedEndDateMillis = initialSelectedEndDateMillis,
            initialDisplayedMonthMillis = initialDisplayedMonthMillis
                ?: initialSelectedStartDateMillis,
            yearRange = yearRange,
            initialDisplayMode = initialDisplayMode ?: DisplayMode.Picker,
        )
        val dateFormatter = remember { DatePickerDefaults.dateFormatter() }
        DateRangePicker(
            state = state,
            modifier = props.commonProps.modifier,
            dateFormatter = dateFormatter,
            // TODO Custom DatePickerFormatter,
            // TODO dateValidator = {},
            title = {
                title?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DateRangePickerDefaults.DateRangePickerTitle(
                    displayMode = state.displayMode,
                    modifier = Modifier.padding(start = 64.dp, end = 12.dp)
                )
            },
            headline = {
                headline?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: DateRangePickerDefaults.DateRangePickerHeadline(
                    selectedStartDateMillis = state.selectedStartDateMillis,
                    selectedEndDateMillis = state.selectedEndDateMillis,
                    displayMode = state.displayMode,
                    dateFormatter,
                    modifier = Modifier.padding(start = 64.dp, end = 12.dp, bottom = 12.dp)
                )
            },
            showModeToggle = showModeToggle,
            colors = getDatePickerColors(colors),
        )
        LaunchedEffect(state.selectedStartDateMillis, state.selectedEndDateMillis) {
            val startDate = state.selectedStartDateMillis
            val endDate = state.selectedEndDateMillis
            if (startDate != null || endDate != null) {
                pushEvent.invoke(
                    ComposableBuilder.EVENT_TYPE_CHANGE,
                    onChanged,
                    arrayOf(startDate ?: 0, endDate ?: 0),
                    null
                )
            }
        }
    }

    companion object {
        private const val KEY_DATE = "date"
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>?,
        val initialDisplayedMonthMillis: Long?,
        val initialDisplayMode: DisplayMode?,
        val initialSelectedDateMillis: Long?,
        val initialSelectedStartDateMillis: Long?,
        val initialSelectedEndDateMillis: Long?,
        val onChanged: String,
        val showModeToggle: Boolean,
        val yearStart: Int?,
        val yearEnd: Int?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var colors: ImmutableMap<String, String>? = null
        private var initialDisplayedMonthMillis: Long? = null
        private var initialDisplayMode: DisplayMode? = null
        private var initialSelectedDateMillis: Long? = null
        private var initialSelectedStartDateMillis: Long? = null
        private var initialSelectedEndDateMillis: Long? = null
        private var onChanged: String = ""
        private var showModeToggle: Boolean = true
        private var yearRange: IntRange? = null

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide zero to indicate no selection.
         *
         * ```
         * <DatePicker initialSelectedStartDateMillis="1686279600000" />
         * ```
         * @param initialSelectedDate initial selection of a date in UTC milliseconds.
         */
        fun initialSelectedStartDateMillis(initialSelectedDate: String) = apply {
            this.initialSelectedStartDateMillis = initialSelectedDate.toLongOrNull()
        }

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide zero to indicate no selection.
         *
         * ```
         * <DateRangePicker initialSelectedEndDateMillis="1686279600000" />
         * ```
         * @param initialSelectedDate initial selection of a date in UTC milliseconds.
         */
        fun initialSelectedEndDateMillis(initialSelectedDate: String) = apply {
            this.initialSelectedEndDateMillis = initialSelectedDate.toLongOrNull()
        }

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide a null to indicate no selection.
         *
         * ```
         * <DatePicker initialSelectedDateMillis="1686279600000" />
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
         * <DatePicker initialDisplayedMonthMillis="" />
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
         * <DatePicker yearRange="1900,2024" />
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
                DatePickerDisplayModeValues.input -> DisplayMode.Input
                DatePickerDisplayModeValues.picker -> DisplayMode.Picker
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
         *   colors="{'containerColor': '#FFFF0000', 'titleContentColor': '#FF00FF00'}"/>
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
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        fun build() = DatePickerView(
            Properties(
                colors,
                initialDisplayedMonthMillis,
                initialDisplayMode,
                initialSelectedDateMillis,
                initialSelectedStartDateMillis,
                initialSelectedEndDateMillis,
                onChanged,
                showModeToggle,
                yearRange?.first,
                yearRange?.last,
                commonProps,
            )
        )
    }
}

internal object DatePickerViewFactory :
    ComposableViewFactory<DatePickerView>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): DatePickerView = attributes.fold(DatePickerView.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColors -> builder.colors(attribute.value)
            attrInitialDisplayedMonthMillis -> builder.initialDisplayedMonthMillis(attribute.value)
            attrInitialDisplayMode -> builder.initialDisplayMode(attribute.value)
            attrInitialSelectedDateMillis -> builder.initialSelectedDateMillis(attribute.value)
            attrInitialSelectedStartDateMillis -> builder.initialSelectedStartDateMillis(attribute.value)
            attrInitialSelectedEndDateMillis -> builder.initialSelectedEndDateMillis(attribute.value)
            attrPhxChange -> builder.onChanged(attribute.value)
            attrShowModeToggle -> builder.showModeToggle(attribute.value)
            attrYearRange -> builder.yearRange(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as DatePickerView.Builder
    }.build()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun getDatePickerColors(colors: ImmutableMap<String, String>?): DatePickerColors {
    val defaultValue = DatePickerDefaults.colors()
    return if (colors == null) {
        defaultValue
    } else {
        DatePickerDefaults.colors(
            containerColor = colors[colorAttrContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.surface,
            titleContentColor = colors[colorAttrTitleContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            headlineContentColor = colors[colorAttrHeadlineContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            weekdayContentColor = colors[colorAttrWeekdayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurface,
            subheadContentColor = colors[colorAttrSubheadContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            yearContentColor = colors[colorAttrYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurfaceVariant,
            currentYearContentColor = colors[colorAttrCurrentYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            selectedYearContentColor = colors[colorAttrSelectedYearContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onPrimary,
            selectedYearContainerColor = colors[colorAttrSelectedYearContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            dayContentColor = colors[colorAttrDayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSurface,
            disabledDayContentColor = colors[colorAttrDisabledDayContentColor]?.toColor()
                ?: (colors[colorAttrDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface).copy(alpha = 0.38f),
            selectedDayContentColor = colors[colorAttrSelectedDayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onPrimary,
            disabledSelectedDayContentColor = colors[colorAttrDisabledSelectedDayContentColor]?.toColor()
                ?: (colors[colorAttrSelectedDayContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary).copy(alpha = 0.38f),
            selectedDayContainerColor = colors[colorAttrSelectedDayContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            disabledSelectedDayContainerColor = colors[colorAttrDisabledSelectedDayContainerColor]?.toColor()
                ?: (colors[colorAttrSelectedDayContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary).copy(alpha = 0.38f),
            todayContentColor = colors[colorAttrTodayContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            todayDateBorderColor = colors[colorAttrTodayDateBorderColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary,
            dayInSelectionRangeContentColor = colors[colorAttrDayInSelectionRangeContentColor]?.toColor()
                ?: MaterialTheme.colorScheme.onSecondaryContainer,
            dayInSelectionRangeContainerColor = colors[colorAttrDayInSelectionRangeContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}
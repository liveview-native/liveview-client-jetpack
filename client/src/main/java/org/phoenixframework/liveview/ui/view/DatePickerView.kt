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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.JsonArray
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrInitialDisplayMode
import org.phoenixframework.liveview.constants.Attrs.attrInitialDisplayedMonthMillis
import org.phoenixframework.liveview.constants.Attrs.attrInitialSelectedDateMillis
import org.phoenixframework.liveview.constants.Attrs.attrInitialSelectedEndDateMillis
import org.phoenixframework.liveview.constants.Attrs.attrInitialSelectedStartDateMillis
import org.phoenixframework.liveview.constants.Attrs.attrShowModeToggle
import org.phoenixframework.liveview.constants.Attrs.attrYearRange
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCurrentYearContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDayInSelectionRangeContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDayInSelectionRangeContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledDayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSelectedDayContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSelectedDayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrHeadlineContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedDayContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedDayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedYearContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedYearContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSubheadContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTodayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTodayDateBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrWeekdayContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrYearContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.DatePickerDisplayModeValues
import org.phoenixframework.liveview.constants.Templates.templateHeadline
import org.phoenixframework.liveview.constants.Templates.templateTitle
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.LocalParentDataHolder
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.view.CheckBoxView.Factory.handleChangeableAttribute

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
    ChangeableView<ClosedRange<Long>, DatePickerView.Properties>(props) {

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
        val changeValueEventName = props.changeableProps.onChange
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
        val parentDataHolder = LocalParentDataHolder.current
        LaunchedEffect(composableNode.id) {
            snapshotFlow { state.selectedDateMillis }
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .collect {
                    val newValue = mergeValue(it ?: 0)
                    pushOnChangeEvent(pushEvent, changeValueEventName, newValue)
                    parentDataHolder?.setValue(composableNode, newValue)
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
        val changeValueEventName = props.changeableProps.onChange
        val showModeToggle = props.showModeToggle
        val yearRange = IntRange(
            props.yearStart ?: DatePickerDefaults.YearRange.first,
            props.yearEnd ?: DatePickerDefaults.YearRange.last,
        )

        val state = rememberDateRangePickerState(
            initialSelectedStartDateMillis = initialSelectedStartDateMillis,
            initialSelectedEndDateMillis = initialSelectedEndDateMillis,
            initialDisplayedMonthMillis = initialDisplayedMonthMillis ?: initialSelectedStartDateMillis,
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
        val parentDataHolder = LocalParentDataHolder.current
        LaunchedEffect(composableNode.id) {
            snapshotFlow { state.selectedStartDateMillis to state.selectedEndDateMillis }
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .collect {
                    val (selectedStartDate, selectedEndDate) = it
                    val interval = mergeValue(selectedStartDate, selectedEndDate)
                    pushOnChangeEvent(pushEvent, changeValueEventName, interval)
                    parentDataHolder?.setValue(composableNode, interval)
                }
        }
    }

    private fun mergeValue(dateInMillis: Long): Any? {
        return mergeValueWithPhxValue(KEY_PHX_VALUE, dateInMillis)
    }

    private fun mergeValue(startDate: Long?, endDate: Long?): Any? {
        return mergeValueWithPhxValue(
            KEY_PHX_VALUE,
            JsonArray().apply {
                add(startDate)
                add(endDate)
            }.toString()
        )
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        val initialDisplayedMonthMillis: Long? = null,
        val initialDisplayMode: DisplayMode? = null,
        val initialSelectedDateMillis: Long? = null,
        val initialSelectedStartDateMillis: Long? = null,
        val initialSelectedEndDateMillis: Long? = null,
        val showModeToggle: Boolean = true,
        val yearStart: Int? = null,
        val yearEnd: Int? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory :
        ComposableViewFactory<DatePickerView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): DatePickerView = DatePickerView(attributes.fold(Properties()) { props, attribute ->
            handleChangeableAttribute(props.changeableProps, attribute)?.let {
                props.copy(changeableProps = it)
            } ?: run {
                when (attribute.name) {
                    attrColors -> colors(props, attribute.value)
                    attrInitialDisplayedMonthMillis -> initialDisplayedMonthMillis(
                        props,
                        attribute.value
                    )

                    attrInitialDisplayMode -> initialDisplayMode(props, attribute.value)
                    attrInitialSelectedDateMillis -> initialSelectedDateMillis(
                        props,
                        attribute.value
                    )

                    attrInitialSelectedStartDateMillis -> initialSelectedStartDateMillis(
                        props,
                        attribute.value
                    )

                    attrInitialSelectedEndDateMillis -> initialSelectedEndDateMillis(
                        props,
                        attribute.value
                    )

                    attrShowModeToggle -> showModeToggle(props, attribute.value)
                    attrYearRange -> yearRange(props, attribute.value)
                    else -> props.copy(
                        commonProps = handleCommonAttributes(
                            props.commonProps,
                            attribute,
                            pushEvent,
                            scope
                        )
                    )
                }
            }
        })

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide zero to indicate no selection.
         *
         * ```
         * <DatePicker initialSelectedStartDateMillis="1686279600000" />
         * ```
         * @param initialSelectedDate initial selection of a date in UTC milliseconds.
         */
        private fun initialSelectedStartDateMillis(
            props: Properties,
            initialSelectedDate: String
        ): Properties {
            return props.copy(initialSelectedStartDateMillis = initialSelectedDate.toLongOrNull())
        }

        /**
         * Timestamp in UTC milliseconds from the epoch that represents an initial selection of a
         * date. Provide zero to indicate no selection.
         *
         * ```
         * <DateRangePicker initialSelectedEndDateMillis="1686279600000" />
         * ```
         * @param initialSelectedEndDate initial selection of a date in UTC milliseconds.
         */
        private fun initialSelectedEndDateMillis(
            props: Properties,
            initialSelectedEndDate: String
        ): Properties {
            return props.copy(initialSelectedEndDateMillis = initialSelectedEndDate.toLongOrNull())
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
        private fun initialSelectedDateMillis(
            props: Properties,
            initialSelectedDate: String
        ): Properties {
            return props.copy(initialSelectedDateMillis = initialSelectedDate.toLongOrNull())
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
        private fun initialDisplayedMonthMillis(
            props: Properties,
            initialDisplayedMonth: String
        ): Properties {
            return props.copy(initialDisplayedMonthMillis = initialDisplayedMonth.toLongOrNull())
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
        private fun yearRange(props: Properties, yearRange: String): Properties {
            val range = try {
                yearRange.split(',').map { it.toInt() }.let { list ->
                    list[0]..list[1]
                }
            } catch (e: Exception) {
                null
            }
            return props.copy(yearStart = range?.start, yearEnd = range?.endInclusive)
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
        private fun initialDisplayMode(props: Properties, displayMode: String): Properties {
            return props.copy(
                initialDisplayMode = when (displayMode) {
                    DatePickerDisplayModeValues.input -> DisplayMode.Input
                    DatePickerDisplayModeValues.picker -> DisplayMode.Picker
                    else -> DisplayMode.Picker
                }
            )
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
        private fun showModeToggle(props: Properties, showModeToggle: String): Properties {
            return props.copy(showModeToggle = showModeToggle.toBoolean())
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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }
    }
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
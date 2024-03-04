package org.phoenixframework.liveview.data.dto

import android.text.format.DateFormat
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialHour
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialMinute
import org.phoenixframework.liveview.data.constants.Attrs.attrIs24Hour
import org.phoenixframework.liveview.data.constants.Attrs.attrLayoutType
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
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
import org.phoenixframework.liveview.data.constants.TimePickerLayoutTypeValues
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.KEY_PHX_VALUE
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design time picker and time input.
 *
 * ```
 * def mount(_params, _session, socket) do
 *   {:ok, socket |> assign(:selectedTimeMap, %{"hour" => 13, "minute" => 21, "is24Hour" => true}) }
 * end
 *
 * def handle_event("onTimeChange", timeMap, socket) do
 *   {:noreply, assign(socket, :selectedTimeMap, timeMap)}
 * end
 *
 * <TimePicker
 *   initialHour={"#{Map.get(@selectedTimeMap, "hour")}"}
 *   initialMinute={"#{Map.get(@selectedTimeMap, "minute")}"}
 *   phx-change="onTimeChange" />
 *
 * <TimeInput
 *   initialHour={"#{Map.get(@selectedTimeMap, "hour")}"}
 *   initialMinute={"#{Map.get(@selectedTimeMap, "minute")}"}
 *   phx-change="onTimeChange" />
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class TimePickerDTO private constructor(props: Properties) :
    ComposableView<TimePickerDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors?.toImmutableMap()
        val initialHour = props.initialHour
        val initialMinute = props.initialMinute
        val is24Hour = props.is24Hour
        val layoutType = props.layoutType
        val onChanged = props.onChanged

        val state = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = is24Hour ?: DateFormat.is24HourFormat(LocalContext.current),
        )
        when (composableNode?.node?.tag) {
            ComposableTypes.timePicker ->
                TimePicker(
                    state = state,
                    modifier = props.commonProps.modifier,
                    colors = getTimePickerColors(colors),
                    layoutType = layoutType ?: TimePickerDefaults.layoutType(),
                )

            ComposableTypes.timeInput ->
                TimeInput(
                    state = state,
                    modifier = props.commonProps.modifier,
                    colors = getTimePickerColors(colors)
                )
        }
        LaunchedEffect(state.hour, state.minute, state.is24hour) {
            val timeMapValues = mutableMapOf(
                "hour" to state.hour,
                "minute" to state.minute,
                "is24Hour" to state.is24hour
            )
            val currentValue = props.commonProps.phxValue
            val pushValues = if (currentValue is Map<*, *>) {
                val newMap = currentValue.toMutableMap()
                newMap.putAll(timeMapValues)
                newMap
            } else {
                if (currentValue == null) {
                    timeMapValues
                } else {
                    val newMap = mutableMapOf<String, Any>()
                    newMap[KEY_PHX_VALUE] = currentValue
                    newMap.putAll(timeMapValues)
                    newMap
                }
            }
            pushEvent.invoke(EVENT_TYPE_CHANGE, onChanged, pushValues, null)
        }
    }

    @Composable
    private fun getTimePickerColors(colors: ImmutableMap<String, String>?): TimePickerColors {
        val defaultValue = TimePickerDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            TimePickerDefaults.colors(
                clockDialColor = colors[colorAttrClockDialColor]?.toColor()
                    ?: Color.Unspecified,
                clockDialSelectedContentColor = colors[colorAttrClockDialSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                clockDialUnselectedContentColor = colors[colorAttrClockDialUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                selectorColor = colors[colorAttrSelectorColor]?.toColor()
                    ?: Color.Unspecified,
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorBorderColor = colors[colorAttrPeriodSelectorBorderColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorSelectedContainerColor = colors[colorAttrPeriodSelectorSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorUnselectedContainerColor = colors[colorAttrPeriodSelectorUnselectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorSelectedContentColor = colors[colorAttrPeriodSelectorSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorUnselectedContentColor = colors[colorAttrPeriodSelectorUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorSelectedContainerColor = colors[colorAttrTimeSelectorSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorUnselectedContainerColor = colors[colorAttrTimeSelectorUnselectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorSelectedContentColor = colors[colorAttrTimeSelectorSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorUnselectedContentColor = colors[colorAttrTimeSelectorUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified
            )
        }
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>?,
        val initialHour: Int,
        val initialMinute: Int,
        val is24Hour: Boolean?,
        val layoutType: TimePickerLayoutType?,
        val onChanged: String,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var colors: ImmutableMap<String, String>? = null
        private var initialHour: Int = 0
        private var initialMinute: Int = 0
        private var is24Hour: Boolean? = null
        private var layoutType: TimePickerLayoutType? = null
        private var onChanged: String = ""

        /**
         * Set TimePicker colors.
         * ```
         * <TimePicker
         *   colors="{'clockDialColor': '#FFFF0000', 'clockDialSelectedContentColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `clockDialColor`, `clockDialSelectedContentColor`,
         * `clockDialUnselectedContentColor`, `selectorColor`, `containerColor`,
         * `periodSelectorBorderColor`, `periodSelectorSelectedContainerColor`,
         * `periodSelectorUnselectedContainerColor`, `periodSelectorSelectedContentColor`,
         * `periodSelectorUnselectedContentColor`, `timeSelectorSelectedContainerColor`,
         * `timeSelectorUnselectedContainerColor`, `timeSelectorSelectedContentColor`,
         * and `timeSelectorUnselectedContentColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        /**
         * Starting hour for this picker, will be displayed in the time picker when launched Ranges
         * from 0 to 23.
         * ```
         * <TimePicker initialHour="13" />
         * ```
         * @param hour int value representing the hour (from 0 to 23).
         */
        fun initialHour(hour: String) = apply {
            this.initialHour = hour.toIntOrNull() ?: 0
        }

        /**
         * starting minute for this picker, will be displayed in the time picker when launched.
         * Ranges from 0 to 59
         * ```
         * <TimePicker initialMinute="30" />
         * ```
         * @param minute int value representing the minute (from 0 to 59)
         */
        fun initialMinute(minute: String) = apply {
            this.initialMinute = minute.toIntOrNull() ?: 0
        }

        /**
         * The format for this time picker. false for 12 hour format with an AM/PM toggle or true
         * for 24 hour format without toggle. Defaults to follow system setting.
         * ```
         * <TimePicker is24Hour="true" />
         * ```
         * @param is24Hour true for using 24 hour format, false to use 12 hour format.
         */
        fun is24Hour(is24Hour: String) = apply {
            this.is24Hour = is24Hour.toBooleanStrictOrNull()
        }

        /**
         * Layout type supported by this time picker, it will change the position and sizing of
         * different components of the timepicker. Supported values are: `horizontal` or `vertical`.
         * ```
         * <TimePicker layoutType="horizontal" />
         * ```
         * @param layoutType layout type of the time picker.
         */
        fun layoutType(layoutType: String) = apply {
            this.layoutType = when (layoutType) {
                TimePickerLayoutTypeValues.horizontal -> TimePickerLayoutType.Horizontal
                TimePickerLayoutTypeValues.vertical -> TimePickerLayoutType.Vertical
                else -> null
            }
        }

        /**
         * Function in the server to be called when the time picker state changes. The server
         * function will receive the hour, minute and is24Hour parameter as a map.
         * ```
         * <TimePicker phx-change="updateTime" />
         * ```
         * @param onChanged the name of the function to be called in the server when the time is
         * selected.
         */
        fun onChanged(onChanged: String) = apply {
            this.onChanged = onChanged
        }

        fun build() = TimePickerDTO(
            Properties(
                colors,
                initialHour,
                initialMinute,
                is24Hour,
                layoutType,
                onChanged,
                commonProps,
            )
        )
    }
}

internal object TimePickerDtoFactory : ComposableViewFactory<TimePickerDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): TimePickerDTO = attributes.fold(TimePickerDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColors -> builder.colors(attribute.value)
            attrInitialHour -> builder.initialHour(attribute.value)
            attrInitialMinute -> builder.initialMinute(attribute.value)
            attrIs24Hour -> builder.is24Hour(attribute.value)
            attrLayoutType -> builder.layoutType(attribute.value)
            attrPhxChange -> builder.onChanged(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TimePickerDTO.Builder
    }.build()
}
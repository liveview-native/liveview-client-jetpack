package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrInitialHour
import org.phoenixframework.liveview.constants.Attrs.attrInitialMinute
import org.phoenixframework.liveview.constants.Attrs.attrIs24Hour
import org.phoenixframework.liveview.constants.Attrs.attrLayoutType
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrClockDialColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrClockDialSelectedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrClockDialUnselectedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrPeriodSelectorBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrPeriodSelectorSelectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrPeriodSelectorSelectedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrPeriodSelectorUnselectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrPeriodSelectorUnselectedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTimeSelectorSelectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTimeSelectorSelectedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTimeSelectorUnselectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTimeSelectorUnselectedContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.TimePickerLayoutTypeValues
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

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
internal class TimePickerView private constructor(props: Properties) :
    ComposableView<TimePickerView.Properties>(props) {

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
        val colors: ImmutableMap<String, String>? = null,
        val initialHour: Int = 0,
        val initialMinute: Int = 0,
        val is24Hour: Boolean? = null,
        val layoutType: TimePickerLayoutType? = null,
        val onChanged: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<TimePickerView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): TimePickerView = TimePickerView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrColors -> colors(props, attribute.value)
                attrInitialHour -> initialHour(props, attribute.value)
                attrInitialMinute -> initialMinute(props, attribute.value)
                attrIs24Hour -> is24Hour(props, attribute.value)
                attrLayoutType -> layoutType(props, attribute.value)
                attrPhxChange -> onChanged(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })

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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Starting hour for this picker, will be displayed in the time picker when launched Ranges
         * from 0 to 23.
         * ```
         * <TimePicker initialHour="13" />
         * ```
         * @param hour int value representing the hour (from 0 to 23).
         */
        private fun initialHour(props: Properties, hour: String): Properties {
            return props.copy(initialHour = hour.toIntOrNull() ?: 0)
        }

        /**
         * starting minute for this picker, will be displayed in the time picker when launched.
         * Ranges from 0 to 59
         * ```
         * <TimePicker initialMinute="30" />
         * ```
         * @param minute int value representing the minute (from 0 to 59)
         */
        private fun initialMinute(props: Properties, minute: String): Properties {
            return props.copy(initialMinute = minute.toIntOrNull() ?: 0)
        }

        /**
         * The format for this time picker. false for 12 hour format with an AM/PM toggle or true
         * for 24 hour format without toggle. Defaults to follow system setting.
         * ```
         * <TimePicker is24Hour="true" />
         * ```
         * @param is24Hour true for using 24 hour format, false to use 12 hour format.
         */
        private fun is24Hour(props: Properties, is24Hour: String): Properties {
            return props.copy(is24Hour = is24Hour.toBooleanStrictOrNull())
        }

        /**
         * Layout type supported by this time picker, it will change the position and sizing of
         * different components of the timepicker. Supported values are: `horizontal` or `vertical`.
         * ```
         * <TimePicker layoutType="horizontal" />
         * ```
         * @param layoutType layout type of the time picker.
         */
        private fun layoutType(props: Properties, layoutType: String): Properties {
            return props.copy(
                layoutType = when (layoutType) {
                    TimePickerLayoutTypeValues.horizontal -> TimePickerLayoutType.Horizontal
                    TimePickerLayoutTypeValues.vertical -> TimePickerLayoutType.Vertical
                    else -> null
                }
            )
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
        private fun onChanged(props: Properties, onChanged: String): Properties {
            return props.copy(onChanged = onChanged)
        }
    }
}
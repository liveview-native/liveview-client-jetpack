package org.phoenixframework.liveview.data.dto

import android.text.format.DateFormat
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialHour
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialMinute
import org.phoenixframework.liveview.data.constants.Attrs.attrIs24Hour
import org.phoenixframework.liveview.data.constants.Attrs.attrLayoutType
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrClockDialColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorClockDialSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorClockDialUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorPeriodSelectorBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorPeriodSelectorSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorPeriodSelectorSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorPeriodSelectorUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorPeriodSelectorUnselectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorSelectorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorTimeSelectorSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorTimeSelectorSelectedContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorTimeSelectorUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorTimeSelectorUnselectedContentColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design time picker.
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
 *   initial-hour={"#{Map.get(@selectedTimeMap, "hour")}"}
 *   initial-minute={"#{Map.get(@selectedTimeMap, "minute")}"}
 *   phx-change="onTimeChange" />
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class TimePickerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val colors = builder.colors?.toImmutableMap()
    private val initialHour = builder.initialHour
    private val initialMinute = builder.initialMinute
    private val is24Hour = builder.is24Hour
    private val layoutType = builder.layoutType
    private val onChanged = builder.onChanged

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val state = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = is24Hour ?: DateFormat.is24HourFormat(LocalContext.current),
        )
        TimePicker(
            state = state,
            modifier = modifier,
            colors = getTimePickerColors(colors),
            layoutType = layoutType ?: TimePickerDefaults.layoutType(),
        )
        LaunchedEffect(state.hour, state.minute, state.is24hour) {
            val map = mapOf(
                "hour" to state.hour,
                "minute" to state.minute,
                "is24Hour" to state.is24hour
            )
            pushEvent.invoke(EVENT_TYPE_CHANGE, onChanged, map, null)
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
                clockDialSelectedContentColor = colors[colorClockDialSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                clockDialUnselectedContentColor = colors[colorClockDialUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                selectorColor = colors[colorSelectorColor]?.toColor()
                    ?: Color.Unspecified,
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorBorderColor = colors[colorPeriodSelectorBorderColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorSelectedContainerColor = colors[colorPeriodSelectorSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorUnselectedContainerColor = colors[colorPeriodSelectorUnselectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorSelectedContentColor = colors[colorPeriodSelectorSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                periodSelectorUnselectedContentColor = colors[colorPeriodSelectorUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorSelectedContainerColor = colors[colorTimeSelectorSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorUnselectedContainerColor = colors[colorTimeSelectorUnselectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorSelectedContentColor = colors[colorTimeSelectorSelectedContentColor]?.toColor()
                    ?: Color.Unspecified,
                timeSelectorUnselectedContentColor = colors[colorTimeSelectorUnselectedContentColor]?.toColor()
                    ?: Color.Unspecified
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var colors: Map<String, String>? = null
            private set
        var initialHour: Int = 0
            private set
        var initialMinute: Int = 0
            private set
        var is24Hour: Boolean? = null
            private set
        var layoutType: TimePickerLayoutType? = null
            private set
        var onChanged: String = ""
            private set

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
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Starting hour for this picker, will be displayed in the time picker when launched Ranges
         * from 0 to 23.
         * ```
         * <TimePicker initial-hour="13" />
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
         * <TimePicker initial-minute="30" />
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
         * <TimePicker is-24-hour="true" />
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
         * <TimePicker layout-type="horizontal" />
         * ```
         * @param layoutType layout type of the time picker.
         */
        fun layoutType(layoutType: String) = apply {
            this.layoutType = when (layoutType) {
                "horizontal" -> TimePickerLayoutType.Horizontal
                "vertical" -> TimePickerLayoutType.Vertical
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

        fun build() = TimePickerDTO(this)
    }
}

internal object TimePickerDtoFactory :
    ComposableViewFactory<TimePickerDTO, TimePickerDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
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
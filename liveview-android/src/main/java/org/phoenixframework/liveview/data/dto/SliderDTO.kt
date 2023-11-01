package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.throttleLatest
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

internal class SliderDTO private constructor(builder: Builder) : ComposableView(builder.modifier) {
    private val value = builder.value
    private val enabled = builder.enabled
    private val debounce = builder.debounce
    private val throttle = builder.throttle
    private val onChange = builder.onChange
    private val minValue = builder.minValue
    private val maxValue = builder.maxValue
    private val steps = builder.steps
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        var stateValue by remember {
            mutableFloatStateOf(value)
        }
        Slider(
            value = stateValue,
            onValueChange = {
                stateValue = it
            },
            modifier = modifier,
            enabled = enabled,
            valueRange = minValue..maxValue,
            steps = steps,
            colors = getSliderColors(colors)
        )

        LaunchedEffect(composableNode) {
            snapshotFlow { stateValue }
                .distinctUntilChanged()
                .drop(1) // Ignoring the first emission when the component is displayed
                .debounce(debounce)
                .throttleLatest(throttle)
                .collect { value ->
                    onChange?.let { event ->
                        pushEvent.invoke(EVENT_TYPE_CHANGE, event, value, null)
                    }
                }
        }
    }

    @Composable
    private fun getSliderColors(sliderColors: ImmutableMap<String, String>?): SliderColors {
        val defaultValue = SliderDefaults.colors()
        return if (sliderColors == null) {
            defaultValue
        } else {
            fun value(key: String) = sliderColors[key]?.toColor()
                ?: Color(defaultValue.privateField(key))

            SliderDefaults.colors(
                thumbColor = value("thumbColor"),
                activeTrackColor = value("activeTrackColor"),
                activeTickColor = value("activeTickColor"),
                inactiveTrackColor = value("inactiveTrackColor"),
                inactiveTickColor = value("inactiveTickColor"),
                disabledThumbColor = value("disabledThumbColor"),
                disabledActiveTrackColor = value("disabledActiveTrackColor"),
                disabledActiveTickColor = value("disabledActiveTickColor"),
                disabledInactiveTrackColor = value("disabledInactiveTrackColor"),
                disabledInactiveTickColor = value("disabledInactiveTickColor")
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<SliderDTO, Float>(0f) {
        var minValue = 0f
            private set
        var maxValue = 1f
            private set
        var steps = 0
            private set
        var colors: Map<String, String>? = null
            private set

        /**
         * The min value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider minValue="0" />
         * ```
         * @param value a float value to set the min value accepted by the slider.
         */
        fun minValue(value: String) = apply {
            this.minValue = value.toFloatOrNull() ?: 0f
        }

        /**
         * The max value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider maxValue="100" />
         * ```
         * @param value a float value to set the max value accepted by the slider.
         */
        fun maxValue(value: String) = apply {
            this.maxValue = value.toFloatOrNull() ?: 1f
        }

        /**
         * If greater than 0, specifies the amount of discrete allowable values, evenly distributed
         * across the whole value range. If 0, the slider will behave continuously and allow any
         * value from the range specified. Must not be negative.
         * ```
         * <Slider steps="5" />
         * ```
         * @param value an int value to define the number of steps the slider has.
         */
        fun steps(value: String) = apply {
            this.steps = value.toIntOrNull() ?: 0
        }

        /**
         * Set Slider colors.
         * ```
         * <Slider
         *   colors="{'thumbColor': '#FFFF0000', 'activeTrackColor': '#FF00FF00'}" />
         * ```
         * @param colors an JSON formatted string, containing the slider colors. The color keys
         * supported are: `thumbColor`, `activeTrackColor`, `activeTickColor, `inactiveTrackColor`,
         * `inactiveTickColor`, `disabledThumbColor`, `disabledActiveTrackColor`,
         * `disabledActiveTickColor`, `disabledInactiveTrackColor`, and `disabledInactiveTickColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun build(): SliderDTO = SliderDTO(this)
    }
}

internal object SliderDtoFactory : ComposableViewFactory<SliderDTO, SliderDTO.Builder>() {

    /**
     * Creates a `SliderDTO` object based on the attributes of the input `Attributes` object.
     * SliderDTO co-relates to the Slider composable
     * @param attributes the `Attributes` object to create the `SliderDTO` object from
     * @return a `SliderDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SliderDTO = SliderDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    "minValue" -> builder.minValue(attribute.value)
                    "maxValue" -> builder.maxValue(attribute.value)
                    "steps" -> builder.steps(attribute.value)
                    "colors" -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as SliderDTO.Builder
            }
        }
    }.build()
}
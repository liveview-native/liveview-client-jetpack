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
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.throttleLatest
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

class SliderDTO private constructor(builder: Builder) : ComposableView(builder.modifier) {
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

        LaunchedEffect(composableNode?.id) {
            snapshotFlow { stateValue }
                .distinctUntilChanged()
                .drop(1) // Ignoring the first emission when the component is displayed
                .debounce(debounce ?: 0)
                .throttleLatest(throttle)
                .collect { value ->
                    onChange?.let { event ->
                        pushEvent.invoke("change", event, value, null)
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

    class Builder : ChangeableDTOBuilder<Float>(0f) {
        var minValue = 0f
            private set
        var maxValue = 1f
            private set
        var steps = 0
            private set
        var colors: Map<String, String>? = null
            private set

        fun minValue(value: String) = apply {
            this.minValue = value.toFloatOrNull() ?: 0f
        }

        fun maxValue(value: String) = apply {
            this.maxValue = value.toFloatOrNull() ?: 1f
        }

        fun steps(value: String) = apply {
            this.steps = value.toIntOrNull() ?: 0
        }

        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun build(): SliderDTO = SliderDTO(this)
    }
}

object SliderDtoFactory : ComposableViewFactory<SliderDTO, SliderDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SliderDTO = SliderDTO.Builder().apply {
        processChangeableAttributes(attributes)
    }.also {
        attributes.fold(
            it
        ) { builder, attribute ->
            when (attribute.name) {
                "minValue" -> builder.minValue(attribute.value)
                "maxValue" -> builder.maxValue(attribute.value)
                "steps" -> builder.steps(attribute.value)
                "colors" -> builder.colors(attribute.value)
                else -> builder.processCommonAttributes(scope, attribute, pushEvent)
            } as SliderDTO.Builder
        }
    }.build()
}
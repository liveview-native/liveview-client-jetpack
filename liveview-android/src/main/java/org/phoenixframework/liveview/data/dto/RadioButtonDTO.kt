package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design radio button.
 *
 * ```
 *  <RadioButton value="A" phx-change="setRadioOption" selected="true" />
 * ```
 */
internal class RadioButtonDTO private constructor(builder: Builder) :
    ChangeableDTO<String>(builder) {

    private val selected = builder.selected
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                if (!selected) {
                    changeValueEventName?.let {
                        pushOnChangeEvent(pushEvent, it, value)
                    }
                }
            },
            enabled = enabled,
            colors = getRadioButtonColors(colors),
        )
    }

    @Composable
    private fun getRadioButtonColors(colors: ImmutableMap<String, String>?): RadioButtonColors {
        val defaultValue = RadioButtonDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            fun value(key: String) = colors[key]?.toColor() ?: Color(defaultValue.privateField(key))

            RadioButtonDefaults.colors(
                selectedColor = value("selectedColor"),
                unselectedColor = value("unselectedColor"),
                disabledSelectedColor = value("disabledSelectedColor"),
                disabledUnselectedColor = value("disabledUnselectedColor"),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<String>("") {
        var colors: Map<String, String>? = null
            private set

        var selected: Boolean = false
            private set

        /**
         * Whether this radio button is selected or not.
         *
         * ```
         * <RadioButton selected="true" />
         * ```
         * @param selected true if the RadioButton must be selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * Set RadioButton colors.
         * ```
         * <RadioButton
         *   colors="{'selectedColor': '#FFFF0000', 'unselectedColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `selectedColor`, `unselectedColor`, `disabledSelectedColor, and
         * `disabledUnselectedColor`.
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

        fun build() = RadioButtonDTO(this)
    }
}

internal object RadioButtonDtoFactory :
    ComposableViewFactory<RadioButtonDTO, RadioButtonDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): RadioButtonDTO = RadioButtonDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    "value" -> builder.value(attribute.value)
                    "selected" -> builder.selected(attribute.value)
                    "colors" -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as RadioButtonDTO.Builder
            }
        }
    }.build()
}
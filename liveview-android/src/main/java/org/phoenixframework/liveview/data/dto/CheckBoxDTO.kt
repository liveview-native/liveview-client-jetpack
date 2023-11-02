package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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

internal class CheckBoxDTO private constructor(builder: Builder) :
    ChangeableDTO<Boolean>(builder) {
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        var stateValue by remember(composableNode) {
            mutableStateOf(value)
        }
        Checkbox(
            checked = stateValue,
            onCheckedChange = {
                stateValue = it
            },
            modifier = modifier,
            enabled = enabled,
            colors = getCheckBoxColors(colors),
        )

        LaunchedEffect(composableNode) {
            onChange?.let { event ->
                snapshotFlow { stateValue }
                    .onChangeable()
                    .collect { value ->
                        pushOnChangeEvent(pushEvent, event, value)
                    }
            }
        }
    }

    @Composable
    private fun getCheckBoxColors(colors: ImmutableMap<String, String>?): CheckboxColors {
        val defaultValue = CheckboxDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            fun value(key: String) = colors[key]?.toColor() ?: Color(defaultValue.privateField(key))

            CheckboxDefaults.colors(
                checkedColor = value("checkedColor"),
                uncheckedColor = value("uncheckedColor"),
                checkmarkColor = value("checkmarkColor"),
                disabledCheckedColor = value("disabledCheckedColor"),
                disabledUncheckedColor = value("disabledUncheckedColor"),
                disabledIndeterminateColor = value("disabledIndeterminateColor"),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<Boolean>(false) {

        var colors: Map<String, String>? = null
            private set

        /**
         * Set CheckBox colors.
         * ```
         * <CheckBox
         *   colors="{'thumbColor': '#FFFF0000', 'activeTrackColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `checkedColor`, `uncheckedColor`, `checkmarkColor, `disabledCheckedColor`,
         * `disabledUncheckedColor`, and `disabledIndeterminateColor`.
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

        fun build() = CheckBoxDTO(this)
    }
}

internal object CheckBoxDtoFactory : ComposableViewFactory<CheckBoxDTO, CheckBoxDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): CheckBoxDTO = CheckBoxDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    "checked" -> builder.value(attribute.value.toBoolean())
                    "colors" -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as CheckBoxDTO.Builder
            }
        }
    }.build()
}
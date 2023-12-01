package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckmarkColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIndeterminateColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design checkbox.
 * ```
 * <CheckBox checked={"#{@isChecked}"} phx-change="toggleCheck" />
 * ```
 */
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
            changeValueEventName?.let { event ->
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
            CheckboxDefaults.colors(
                checkedColor = colors[colorAttrCheckedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                uncheckedColor = colors[colorAttrUncheckedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                checkmarkColor = colors[colorAttrCheckmarkColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                disabledCheckedColor = colors[colorAttrDisabledCheckedColor]?.toColor()
                    ?: colors[colorAttrCheckedColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledUncheckedColor = colors[colorAttrDisabledUncheckedColor]?.toColor()
                    ?: colors[colorAttrUncheckedColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledIndeterminateColor = colors[colorAttrDisabledIndeterminateColor]?.toColor()
                    ?: colors[colorAttrDisabledCheckedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
         *   colors="{'checkedColor': '#FFFF0000', 'uncheckedColor': '#FF00FF00'}"/>
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
    /**
     * Creates a `CheckBoxDTO` object based on the attributes of the input `Attributes` object.
     * CheckBoxDTO co-relates to the CheckBox composable
     * @param attributes the `Attributes` object to create the `CheckBoxDTO` object from
     * @return a `CheckBoxDTO` object based on the attributes of the input `Attributes` object
     */
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
                    attrChecked -> builder.value(attribute.value.toBoolean())
                    attrColors -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as CheckBoxDTO.Builder
            }
        }
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckmarkColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIndeterminateColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
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
internal class CheckBoxDTO private constructor(props: Properties) :
    ChangeableDTO<Boolean, CheckBoxDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val changeValueEventName = props.changeableProps.onChange
        val enabled = props.changeableProps.enabled
        val colors = props.colors
        val checked = props.checked

        var stateValue by remember(composableNode) {
            mutableStateOf(checked)
        }
        Checkbox(
            checked = checked,
            onCheckedChange = {
                stateValue = it
            },
            modifier = props.commonProps.modifier,
            enabled = enabled,
            colors = getCheckBoxColors(colors),
        )

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }
                    .onChangeable()
                    .map { isChecked ->
                        mergeValueWithPhxValue(KEY_CHECKED, isChecked)
                    }
                    .collect { pushValue ->
                        pushOnChangeEvent(pushEvent, event, pushValue)
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

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        val checked: Boolean = false,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    companion object {
        private const val KEY_CHECKED = "checked"
    }

    internal class Builder : ChangeableDTOBuilder() {
        private var colors: ImmutableMap<String, String>? = null
        private var checked: Boolean = false

        fun checked(checked: String) = apply {
            this.checked = checked.toBoolean()
        }

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
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        fun build() = CheckBoxDTO(
            Properties(
                colors,
                checked,
                changeableProps,
                commonProps,
            )
        )
    }
}

internal object CheckBoxDtoFactory : ComposableViewFactory<CheckBoxDTO>() {
    /**
     * Creates a `CheckBoxDTO` object based on the attributes of the input `Attributes` object.
     * CheckBoxDTO co-relates to the CheckBox composable
     * @param attributes the `Attributes` object to create the `CheckBoxDTO` object from
     * @return a `CheckBoxDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): CheckBoxDTO = CheckBoxDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrChecked -> builder.checked(attribute.value)
                    attrColors -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as CheckBoxDTO.Builder
            }
        }
    }.build()
}
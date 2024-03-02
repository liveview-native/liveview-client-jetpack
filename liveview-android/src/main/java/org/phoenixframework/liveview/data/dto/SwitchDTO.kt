package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedTrackColor
import org.phoenixframework.liveview.data.constants.Templates.templateThumb
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContainerAlpha
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design Switch.
 * ```
 * <Switch checked={"#{@isChecked}"} phx-change="toggleCheck" />
 * ```
 */
internal class SwitchDTO private constructor(props: Properties) :
    ChangeableDTO<Boolean, SwitchDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val enabled = props.changeableProps.enabled
        val changeValueEventName = props.changeableProps.onChange
        val colors = props.colors
        val checked = props.checked

        val thumbContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateThumb }
        }
        var stateValue by remember(composableNode) {
            mutableStateOf(checked)
        }
        Switch(
            checked = stateValue,
            onCheckedChange = {
                stateValue = it
            },
            modifier = props.commonProps.modifier,
            enabled = enabled,
            thumbContent = thumbContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            colors = getSwitchColors(colors),
        )

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }.onChangeable().map { isChecked ->
                    mergeValueWithPhxValue(KEY_CHECKED, isChecked)
                }.collect { value ->
                    pushOnChangeEvent(pushEvent, event, value)
                }
            }
        }
    }

    @Composable
    private fun getSwitchColors(colors: ImmutableMap<String, String>?): SwitchColors {
        val defaultValue = SwitchDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            SwitchDefaults.colors(
                checkedThumbColor = colors[colorAttrCheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = colors[colorAttrCheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                checkedBorderColor = colors[colorAttrCheckedBorderColor]?.toColor()
                    ?: Color.Transparent,
                checkedIconColor = colors[colorAttrCheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimaryContainer,
                uncheckedThumbColor = colors[colorAttrUncheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = colors[colorAttrUncheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = colors[colorAttrUncheckedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                uncheckedIconColor = colors[colorAttrUncheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                disabledCheckedThumbColor = colors[colorAttrDisabledCheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface.copy(alpha = 1.0f)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledCheckedTrackColor = colors[colorAttrDisabledCheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledCheckedBorderColor = colors[colorAttrDisabledCheckedBorderColor]?.toColor()
                    ?: Color.Transparent,
                disabledCheckedIconColor = colors[colorAttrDisabledCheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedThumbColor = colors[colorAttrDisabledUncheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedTrackColor = colors[colorAttrDisabledUncheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = disabledContainerAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedBorderColor = colors[colorAttrDisabledUncheckedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedIconColor = colors[colorAttrDisabledUncheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = disabledContentAlpha)
                        .compositeOver(MaterialTheme.colorScheme.surface),
            )
        }
    }

    companion object {
        private const val KEY_CHECKED = "checked"
    }

    @Stable
    internal data class Properties(
        val checked: Boolean = false,
        val colors: ImmutableMap<String, String>? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal class Builder : ChangeableDTOBuilder() {
        private var checked: Boolean = false
        private var colors: ImmutableMap<String, String>? = null

        fun checked(checked: String) = apply {
            this.checked = checked.toBoolean()
        }

        /**
         * Set Switch colors.
         * ```
         * <Switch
         *   colors="{'checkedThumbColor': '#FFFF0000', 'checkedTrackColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `checkedThumbColor`, `checkedTrackColor`, `checkedBorderColor,
         * `checkedIconColor`, `uncheckedThumbColor`, `uncheckedTrackColor`, `uncheckedBorderColor`,
         * `uncheckedIconColor`, `disabledCheckedThumbColor`, `disabledCheckedTrackColor`,
         * `disabledCheckedBorderColor`, `disabledCheckedIconColor`, `disabledUncheckedThumbColor`,
         * `disabledUncheckedTrackColor`, `disabledUncheckedBorderColor`, and
         * `disabledUncheckedIconColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        fun build() = SwitchDTO(
            Properties(
                checked,
                colors,
                changeableProps,
                commonProps,
            )
        )
    }
}

internal object SwitchDtoFactory : ComposableViewFactory<SwitchDTO>() {
    /**
     * Creates a `SwitchDTO` object based on the attributes of the input `Attributes` object.
     * SwitchDTO co-relates to the Switch composable
     * @param attributes the `Attributes` object to create the `SwitchDTO` object from
     * @return a `SwitchDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): SwitchDTO = SwitchDTO.Builder().also {
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
                } as SwitchDTO.Builder
            }
        }
    }.build()
}
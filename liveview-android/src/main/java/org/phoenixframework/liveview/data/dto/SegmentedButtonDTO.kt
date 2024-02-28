package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRowScope
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Segmented Button. Segmented buttons help people select options, switch views, or sort
 * elements. It must be declared inside of a `SingleChoiceSegmentedButtonRow` or
 * `MultiChoiceSegmentedButtonRow`.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SegmentedButtonDTO private constructor(builder: Builder) :
    ComposableView<SegmentedButtonDTO.Builder>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val border = builder.border
        val colorsValue = builder.colors
        val enabled = builder.enabled
        val onClick = builder.onClick
        val selected = builder.selected
        val scope = builder.scope
        val shape = builder.shape

        val colors = getSegmentedButtonColors(colors = colorsValue)
        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }
        val icon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }
        val borderStroke = border ?: SegmentedButtonDefaults.borderStroke(
            when {
                enabled && selected -> colors.activeBorderColor
                enabled && !selected -> colors.inactiveBorderColor
                !enabled && selected -> colors.disabledActiveBorderColor
                else -> colors.disabledInactiveBorderColor
            }
        )
        when (scope) {
            is SingleChoiceSegmentedButtonRowScope -> {
                scope.SegmentedButton(
                    selected = selected,
                    onClick = onClickFromString(pushEvent, onClick, phxValue),
                    shape = shape,
                    modifier = modifier,
                    enabled = enabled,
                    colors = colors,
                    border = borderStroke,
                    // TODO interactionSource: MutableInteractionSource,
                    icon = {
                        icon?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SegmentedButtonDefaults.Icon(selected)
                    },
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                )
            }

            is MultiChoiceSegmentedButtonRowScope -> {
                scope.SegmentedButton(
                    checked = selected,
                    onCheckedChange = { isSelected ->
                        pushEvent(
                            ComposableBuilder.EVENT_TYPE_CHANGE,
                            onClick,
                            mergeValueWithPhxValue(KEY_SELECTED, isSelected),
                            null
                        )
                    },
                    shape = shape,
                    modifier = modifier,
                    enabled = enabled,
                    colors = colors,
                    border = borderStroke,
                    // TODO interactionSource: MutableInteractionSource,
                    icon = {
                        icon?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SegmentedButtonDefaults.Icon(selected)
                    },
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                )
            }
        }
    }

    @Composable
    private fun getSegmentedButtonColors(colors: Map<String, String>?): SegmentedButtonColors {
        val defaultColors = SegmentedButtonDefaults.colors()

        return if (colors == null) {
            defaultColors
        } else {
            SegmentedButtonDefaults.colors(
                activeContainerColor = colors[colorAttrActiveContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                activeContentColor = colors[colorAttrActiveContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
                activeBorderColor = colors[colorAttrActiveBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                inactiveContainerColor = colors[colorAttrInactiveContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                inactiveContentColor = colors[colorAttrInactiveContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                inactiveBorderColor = colors[colorAttrInactiveBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                disabledActiveContainerColor = colors[colorAttrDisabledActiveContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                disabledActiveContentColor = colors[colorAttrDisabledActiveContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                disabledActiveBorderColor = colors[colorAttrDisabledActiveBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                disabledInactiveContainerColor = colors[colorAttrDisabledInactiveContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                disabledInactiveContentColor = colors[colorAttrDisabledInactiveContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                disabledInactiveBorderColor = colors[colorAttrDisabledInactiveBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
            )
        }
    }

    companion object {
        const val KEY_SELECTED = "selected"
    }

    // scope must be an instance of
    // SingleChoiceSegmentedButtonRowScope or MultiChoiceSegmentedButtonRowScope
    internal class Builder(val scope: Any? = null) : ComposableBuilder() {
        var border: BorderStroke? = null
            private set
        var colors: ImmutableMap<String, String>? = null
            private set
        var enabled: Boolean = true
            private set
        var onClick: String = ""
            private set
        var selected: Boolean = false
            private set
        var shape: Shape = RectangleShape
            private set

        /**
         * The border to draw around the container of this `SegmentedButton`.
         * ```
         * <SegmentedButton border="{'width': 2, 'color': '#FF0000FF'}">...</SegmentedButton>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing butotn border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors
         */
        fun border(border: String) = apply {
            this.border = borderFromString(border)
        }

        /**
         * Set SegmentedButton colors.
         * ```
         * <SegmentedButton
         *   colors="{'activeContainerColor': '#FFFF0000', 'activeContentColor': '#FF00FF00'}">
         *   ...
         * </SegmentedButton>
         * ```
         * @param colors an JSON formatted string, containing the button colors. The color keys
         * supported are: `activeContainerColor`, `activeContentColor`, `activeBorderColor`,
         * `inactiveContainerColor`, `inactiveContentColor`, `inactiveBorderColor`,
         * `disabledActiveContainerColor`, `disabledActiveContentColor`,
         * `disabledActiveBorderColor`, `disabledInactiveContainerColor`,
         * `disabledInactiveContentColor`, and `disabledInactiveBorderColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         * For the segmented button used inside of a `SingleChoiceSegmentedButtonRow` it will pass
         * the `phx-value` as parameter.
         * ```
         * <SegmentedButton phx-click="yourServerEventHandler" phx-value="foo">...</SegmentedButton>
         * ```
         * For the segmented button used inside of a `MultiChoiceSegmentedButtonRow` it should be
         * used the `phx-change` and the value passed as parameter is an array containing the
         * phx-value at first position and a boolean indicating if the button is selected or not.
         * ```
         * <SegmentedButton phx-change="yourServerEventHandler" phx-value="foo">...</SegmentedButton>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Defines if the button is enabled.
         *
         * ```
         * <SegmentedButton enabled="true">...</SegmentedButton>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <SegmentedButton selected="true">...</SegmentedButton>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * Defines the shape of the item's container.
         *
         * ```
         * <SegmentedButton shape="rectangle" >...</SegmentedButton>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or an use integer
         * representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun build() = SegmentedButtonDTO(this)
    }
}

internal object SegmentedButtonDtoFactory :
    ComposableViewFactory<SegmentedButtonDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): SegmentedButtonDTO =
        attributes.fold(SegmentedButtonDTO.Builder(scope)) { builder, attribute ->
            when (attribute.name) {
                attrBorder -> builder.border(attribute.value)
                attrColors -> builder.colors(attribute.value)
                attrEnabled -> builder.enabled(attribute.value)
                attrPhxClick, attrPhxChange -> builder.onClick(attribute.value)
                attrSelected, attrChecked -> builder.selected(attribute.value)
                attrShape -> builder.shape(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as SegmentedButtonDTO.Builder
        }.build()
}
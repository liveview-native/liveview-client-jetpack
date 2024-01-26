package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDraggedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design assist chip.
 *
 * ```
 * <FlowRow>
 *   <AssistChip phx-click="chipAction">
 *     <Icon image-vector="filled:Check" template="leadingIcon"/>
 *     <Icon image-vector="filled:CheckCircleOutline" template="trailingIcon"/>
 *     <Text template="label">AssitChip</Text>
 *   </AssistChip>
 * </FlowRow>
 * ```
 */
internal class ChipDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val border = builder.border
    private val colors = builder.colors?.toImmutableMap()
    private val elevation = builder.elevations?.toImmutableMap()
    private val enabled = builder.enabled
    private val onClick: String = builder.onClick
    private val value: Any? = builder.value
    private val shape = builder.shape

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }

        when (composableNode?.node?.tag) {
            ComposableTypes.assistChip ->
                AssistChip(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: AssistChipDefaults.shape,
                    colors = getAssistChipColors(colors),
                    elevation = getAssistChipElevation(elevation),
                    border = border ?: AssistChipDefaults.assistChipBorder(enabled),
                    // TODO interactionSource: MutableInteractionSource
                )

            ComposableTypes.elevatedAssistChip ->
                ElevatedAssistChip(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: AssistChipDefaults.shape,
                    colors = getElevatedAssistChipColors(colors),
                    elevation = getElevatedAssistChipElevation(elevation),
                    border = border,
                    // TODO interactionSource: MutableInteractionSource
                )
        }
    }

    @Composable
    private fun getAssistChipColors(colors: ImmutableMap<String, String>?): ChipColors {
        val defaultColors = AssistChipDefaults.assistChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            AssistChipDefaults.assistChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                leadingIconContentColor = colors[colorAttrLeadingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                trailingIconContentColor = colors[colorAttrTrailingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLeadingIconContentColor = colors[colorAttrDisabledLeadingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledTrailingIconContentColor = colors[colorAttrDisabledTrailingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getElevatedAssistChipColors(colors: ImmutableMap<String, String>?): ChipColors {
        val defaultColors = AssistChipDefaults.elevatedAssistChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            AssistChipDefaults.elevatedAssistChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                leadingIconContentColor = colors[colorAttrLeadingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                trailingIconContentColor = colors[colorAttrTrailingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLeadingIconContentColor = colors[colorAttrDisabledLeadingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledTrailingIconContentColor = colors[colorAttrDisabledTrailingIconContentColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getAssistChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = AssistChipDefaults.assistChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            AssistChipDefaults.assistChipElevation(
                elevation = value(elevationAttrElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                draggedElevation = value(elevationAttrDraggedElevation),
                disabledElevation = value(elevationAttrDisabledElevation),
            )
        }
    }


    @Composable
    private fun getElevatedAssistChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = AssistChipDefaults.elevatedAssistChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            AssistChipDefaults.elevatedAssistChipElevation(
                elevation = value(elevationAttrElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                draggedElevation = value(elevationAttrDraggedElevation),
                disabledElevation = value(elevationAttrDisabledElevation),
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var border: BorderStroke? = null
            private set
        var colors: Map<String, String>? = null
            private set
        var elevations: Map<String, String>? = null
            private set
        var enabled: Boolean = true
            private set
        var onClick: String = ""
            private set
        var shape: Shape? = null
            private set

        /**
         * The border to draw around the container of this chip.
         * ```
         * <AssistChip border="{'width': '2', 'color': '#FF0000FF'}">...</AssistChip>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing chip border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format.
         */
        fun border(border: String) = apply {
            if (border.isNotEmpty()) {
                this.border = borderFromString(border)
            }
        }

        /**
         * Set Chip colors.
         * ```
         * <AssistChip
         *   colors="{'containerColor': '#FFFF0000', 'labelColor': '#FF00FF00'}">
         *   ...
         * </AssistChip>
         * ```
         * @param colors an JSON formatted string, containing the chip colors. The color keys
         * supported are: `containerColor`, `labelColor`, `leadingIconContentColor`,
         * `trailingIconContentColor`, `disabledContainerColor`, `disabledLabelColor`,
         * `disabledLeadingIconContentColor`, and `disabledTrailingIconContentColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Set Chip elevations.
         * ```
         * <AssistChip
         *   elevation="{'elevation': '20', 'pressedElevation': '10'}">
         *   ...
         * </AssistChip>
         * ```
         * @param elevations an JSON formatted string, containing the chip elevations. The
         * elevation supported keys are: `elevation`, `pressedElevation`, `focusedElevation`,
         * `hoveredElevation`, `draggedElevation`, and `disabledElevation`.
         */
        fun elevation(elevations: String) = apply {
            if (elevations.isNotEmpty()) {
                this.elevations = elevationsFromString(elevations)
            }
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <AssistChip enabled="true">...</AssistChip>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Sets the event name to be triggered on the server when the chip is clicked.
         *
         * ```
         * <AssistChip phx-click="yourServerEventHandler">...</AssistChip>
         * ```
         * @param event event name defined on the server to handle the chip's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Defines the shape of this chip's container.
         * ```
         * <AssistChip shape="16" >...</AssistChip>
         * ```
         * @param shape chip's shape. Supported values are: `circle`, `rectangle`, or an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun build() = ChipDTO(this)
    }
}

internal object ChipDtoFactory : ComposableViewFactory<ChipDTO, ChipDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ChipDTO = attributes.fold(ChipDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrBorder -> builder.border(attribute.value)
            attrColors -> builder.colors(attribute.value)
            attrElevation -> builder.elevation(attribute.value)
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ChipDTO.Builder
    }.build()
}
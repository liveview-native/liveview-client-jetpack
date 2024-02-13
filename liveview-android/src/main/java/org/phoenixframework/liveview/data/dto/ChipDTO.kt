package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ChipElevation
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
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
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDraggedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.constants.Templates.templateAvatar
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
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
 * Material Design chips. They could be: `AssistChip`, `ElevatedAssistChip`, `SuggestionChip`,
 * `ElevatedSuggestionChip`, `InputChip`, `FilterChip`, and `ElevatedFilterChip`.
 *
 * ```
 * <FlowRow>
 *   <AssistChip phx-click="chipAction">
 *     <Icon imageVector="filled:Check" template="leadingIcon"/>
 *     <Icon imageVector="filled:CheckCircleOutline" template="trailingIcon"/>
 *     <Text template="label">AssistChip</Text>
 *   </AssistChip>
 *   <SuggestionChip phx-click="">`
 *     <Icon imageVector="filled:Check" template="leadingIcon"/>
 *     <Icon imageVector="filled:CheckCircleOutline" template="trailingIcon"/>
 *     <Text template="label">Filter Chip 1</Text>
 *   </SuggestionChip>
 *   <FilterChip phx-click="" selected="true">
 *     <Icon imageVector="filled:Check" template="leadingIcon"/>
 *     <Icon imageVector="filled:CheckCircleOutline" template="trailingIcon"/>
 *     <Text template="label">Filter Chip 1</Text>
 *   </FilterChip>
 *   <InputChip phx-click="" selected="true">
 *     <Icon imageVector="filled:Check" template="leadingIcon"/>
 *     <Icon imageVector="filled:CheckCircleOutline" template="trailingIcon"/>
 *     <Icon imageVector="sharp:PersonAdd" template="avatar"/>
 *     <Text template="label">Filter Chip 1</Text>
 *   </InputChip>
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
    private val value = builder.value
    private val selected = builder.selected
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
            composableNode?.children?.find { it.node?.template == templateLeadingIcon || it.node?.template == templateIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }
        val avatar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateAvatar }
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

            ComposableTypes.suggestionChip ->
                SuggestionChip(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    icon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: SuggestionChipDefaults.shape,
                    colors = getSuggestionChipColors(colors),
                    elevation = getSuggestionChipElevation(elevation),
                    border = border ?: SuggestionChipDefaults.suggestionChipBorder(enabled),
                    // TODO interactionSource: MutableInteractionSource
                )

            ComposableTypes.elevatedSuggestionChip ->
                ElevatedSuggestionChip(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    icon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: SuggestionChipDefaults.shape,
                    colors = getElevatedSuggestionChipColors(colors),
                    elevation = getElevatedSuggestionChipElevation(elevation),
                    border = border,
                    // TODO interactionSource: MutableInteractionSource
                )

            ComposableTypes.filterChip ->
                FilterChip(
                    selected = selected,
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
                    shape = shape ?: FilterChipDefaults.shape,
                    colors = getFilterChipColors(colors),
                    elevation = getFilterChipElevation(elevation),
                    border = border ?: FilterChipDefaults.filterChipBorder(enabled, selected),
                    // TODO interactionSource: MutableInteractionSource
                )

            ComposableTypes.elevatedFilterChip ->
                ElevatedFilterChip(
                    selected = selected,
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
                    shape = shape ?: FilterChipDefaults.shape,
                    colors = getElevatedFilterChipColors(colors),
                    elevation = getElevatedFilterChipElevation(elevation),
                    border = border,
                    // TODO interactionSource: MutableInteractionSource
                )

            ComposableTypes.inputChip ->
                InputChip(
                    selected = selected,
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
                    avatar = avatar?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: InputChipDefaults.shape,
                    colors = getInputChipColors(colors),
                    elevation = getInputChipElevation(elevation),
                    border = border ?: InputChipDefaults.inputChipBorder(enabled, selected),
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
    private fun getSuggestionChipColors(colors: ImmutableMap<String, String>?): ChipColors {
        val defaultColors = SuggestionChipDefaults.suggestionChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            SuggestionChipDefaults.suggestionChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                iconContentColor = colors[colorAttrIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledIconContentColor = colors[colorAttrDisabledIconContentColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getElevatedSuggestionChipColors(colors: ImmutableMap<String, String>?): ChipColors {
        val defaultColors = SuggestionChipDefaults.elevatedSuggestionChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            SuggestionChipDefaults.elevatedSuggestionChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                iconContentColor = colors[colorAttrIconContentColor]?.toColor()
                    ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledIconContentColor = colors[colorAttrDisabledIconContentColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getInputChipColors(colors: ImmutableMap<String, String>?): SelectableChipColors {
        val defaultColors = InputChipDefaults.inputChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            InputChipDefaults.inputChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                leadingIconColor = colors[colorAttrLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                trailingIconColor = colors[colorAttrTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedContainerColor = colors[colorAttrSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledSelectedContainerColor = colors[colorAttrDisabledSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLabelColor = colors[colorAttrSelectedLabelColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLeadingIconColor = colors[colorAttrSelectedLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedTrailingIconColor = colors[colorAttrSelectedTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getFilterChipColors(colors: ImmutableMap<String, String>?): SelectableChipColors {
        val defaultColors = FilterChipDefaults.filterChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            FilterChipDefaults.filterChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                iconColor = colors[colorAttrIconColor]?.toColor() ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedContainerColor = colors[colorAttrSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledSelectedContainerColor = colors[colorAttrDisabledSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLabelColor = colors[colorAttrSelectedLabelColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLeadingIconColor = colors[colorAttrSelectedLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedTrailingIconColor = colors[colorAttrSelectedTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    @Composable
    private fun getElevatedFilterChipColors(colors: ImmutableMap<String, String>?): SelectableChipColors {
        val defaultColors = FilterChipDefaults.elevatedFilterChipColors()

        return if (colors == null) {
            defaultColors
        } else {
            FilterChipDefaults.elevatedFilterChipColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Unspecified,
                labelColor = colors[colorAttrLabelColor]?.toColor() ?: Color.Unspecified,
                iconColor = colors[colorAttrIconColor]?.toColor() ?: Color.Unspecified,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLabelColor = colors[colorAttrDisabledLabelColor]?.toColor()
                    ?: Color.Unspecified,
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedContainerColor = colors[colorAttrSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                disabledSelectedContainerColor = colors[colorAttrDisabledSelectedContainerColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLabelColor = colors[colorAttrSelectedLabelColor]?.toColor()
                    ?: Color.Unspecified,
                selectedLeadingIconColor = colors[colorAttrSelectedLeadingIconColor]?.toColor()
                    ?: Color.Unspecified,
                selectedTrailingIconColor = colors[colorAttrSelectedTrailingIconColor]?.toColor()
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

    @Composable
    private fun getSuggestionChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = SuggestionChipDefaults.suggestionChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            SuggestionChipDefaults.suggestionChipElevation(
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
    private fun getElevatedSuggestionChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = SuggestionChipDefaults.elevatedSuggestionChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            SuggestionChipDefaults.elevatedSuggestionChipElevation(
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
    private fun getInputChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = InputChipDefaults.inputChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            InputChipDefaults.inputChipElevation(
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
    private fun getFilterChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = FilterChipDefaults.filterChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            FilterChipDefaults.filterChipElevation(
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
    private fun getElevatedFilterChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = FilterChipDefaults.elevatedFilterChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            FilterChipDefaults.elevatedFilterChipElevation(
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
        var selected: Boolean = false
            private set
        var shape: Shape? = null
            private set

        /**
         * The border to draw around the container of this chip.
         * ```
         * <AssistChip border="{'width': '2', 'color': '#FF0000FF'}">...</AssistChip>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing chip border's width content. The color value must be specified as a
         * string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
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
         * `disabledLeadingIconContentColor`, and `disabledTrailingIconContentColor`. The color
         * value must be specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
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
         * Whether this chip is selected or not.
         * ```
         * <FilterChip selected="true">...</FilterChip>
         * ```
         * @param selected true if the chip is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * Defines the shape of this chip's container.
         * ```
         * <AssistChip shape="16" >...</AssistChip>
         * ```
         * @param shape chip's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or use an integer
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
            attrSelected -> builder.selected(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ChipDTO.Builder
    }.build()
}
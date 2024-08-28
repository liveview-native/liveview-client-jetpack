package org.phoenixframework.liveview.ui.view

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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrElevation
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLeadingIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSelectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTrailingIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrLeadingIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTrailingIconContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level0
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level1
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level2
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level4
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDraggedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.constants.Templates.templateAvatar
import org.phoenixframework.liveview.constants.Templates.templateIcon
import org.phoenixframework.liveview.constants.Templates.templateLabel
import org.phoenixframework.liveview.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
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
internal class ChipView private constructor(props: Properties) :
    ComposableView<ChipView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val border = props.border
        val colors = props.colors?.toImmutableMap()
        val elevation = props.elevations?.toImmutableMap()
        val enabled = props.enabled
        val onClick: String = props.onClick
        val selected = props.selected
        val shape = props.shape

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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        mergeValueWithPhxValue(KEY_SELECTED, selected)
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        mergeValueWithPhxValue(KEY_SELECTED, selected)
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        mergeValueWithPhxValue(KEY_SELECTED, selected)
                    ),
                    label = {
                        label?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
            fun value(key: String, defaultElevation: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultElevation

            val defValue = elevation[elevationAttrElevation]?.toIntOrNull()?.dp ?: Level0
            AssistChipDefaults.assistChipElevation(
                elevation = value(elevationAttrElevation, defValue),
                pressedElevation = value(elevationAttrPressedElevation, defValue),
                focusedElevation = value(elevationAttrFocusedElevation, defValue),
                hoveredElevation = value(elevationAttrHoveredElevation, defValue),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, defValue),
            )
        }
    }

    @Composable
    private fun getElevatedAssistChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = AssistChipDefaults.elevatedAssistChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            AssistChipDefaults.elevatedAssistChipElevation(
                elevation = value(elevationAttrElevation, Level1),
                pressedElevation = value(elevationAttrPressedElevation, Level1),
                focusedElevation = value(elevationAttrFocusedElevation, Level1),
                hoveredElevation = value(elevationAttrHoveredElevation, Level2),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, Level0),
            )
        }
    }

    @Composable
    private fun getSuggestionChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = SuggestionChipDefaults.suggestionChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            val defValue = elevation[elevationAttrElevation]?.toIntOrNull()?.dp ?: Level0
            SuggestionChipDefaults.suggestionChipElevation(
                elevation = value(elevationAttrElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, defValue),
                focusedElevation = value(elevationAttrFocusedElevation, defValue),
                hoveredElevation = value(elevationAttrHoveredElevation, defValue),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, defValue),
            )
        }
    }

    @Composable
    private fun getElevatedSuggestionChipElevation(elevation: Map<String, String>?): ChipElevation {
        val defaultElevation = SuggestionChipDefaults.elevatedSuggestionChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            SuggestionChipDefaults.elevatedSuggestionChipElevation(
                elevation = value(elevationAttrElevation, Level1),
                pressedElevation = value(elevationAttrPressedElevation, Level1),
                focusedElevation = value(elevationAttrFocusedElevation, Level1),
                hoveredElevation = value(elevationAttrHoveredElevation, Level2),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, Level0),
            )
        }
    }

    @Composable
    private fun getInputChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = InputChipDefaults.inputChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defValue

            val defElevation = elevation[elevationAttrElevation]?.toIntOrNull()?.dp ?: Level0
            InputChipDefaults.inputChipElevation(
                elevation = value(elevationAttrElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, defElevation),
                focusedElevation = value(elevationAttrFocusedElevation, defElevation),
                hoveredElevation = value(elevationAttrHoveredElevation, defElevation),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, defElevation),
            )
        }
    }

    @Composable
    private fun getFilterChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = FilterChipDefaults.filterChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            val defElevation = elevation[elevationAttrElevation]?.toIntOrNull()?.dp ?: Level0
            FilterChipDefaults.filterChipElevation(
                elevation = value(elevationAttrElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, Level0),
                focusedElevation = value(elevationAttrFocusedElevation, Level0),
                hoveredElevation = value(elevationAttrHoveredElevation, Level1),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, defElevation),
            )
        }
    }

    @Composable
    private fun getElevatedFilterChipElevation(elevation: Map<String, String>?): SelectableChipElevation {
        val defaultElevation = FilterChipDefaults.elevatedFilterChipElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            FilterChipDefaults.elevatedFilterChipElevation(
                elevation = value(elevationAttrElevation, Level1),
                pressedElevation = value(elevationAttrPressedElevation, Level1),
                focusedElevation = value(elevationAttrFocusedElevation, Level1),
                hoveredElevation = value(elevationAttrHoveredElevation, Level2),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, Level0),
            )
        }
    }

    companion object {
        const val KEY_SELECTED = "selected"
    }

    @Stable
    internal data class Properties(
        val border: BorderStroke? = null,
        val colors: ImmutableMap<String, String>? = null,
        val elevations: ImmutableMap<String, String>? = null,
        val enabled: Boolean = true,
        val onClick: String = "",
        val selected: Boolean = false,
        val shape: Shape? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ChipView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): ChipView = ChipView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrBorder -> border(props, attribute.value)
                attrColors -> colors(props, attribute.value)
                attrElevation -> elevation(props, attribute.value)
                attrEnabled -> enabled(props, attribute.value)
                attrPhxClick -> onClick(props, attribute.value)
                attrSelected -> selected(props, attribute.value)
                attrShape -> shape(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })

        /**
         * The border to draw around the container of this chip.
         * ```
         * <AssistChip border="{'width': '2', 'color': '#FF0000FF'}">...</AssistChip>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing chip border's width content. The color value must be specified as a
         * string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        fun border(props: Properties, border: String): Properties {
            return if (border.isNotEmpty()) {
                props.copy(border = borderFromString(border))
            } else props
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
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
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
        fun elevation(props: Properties, elevations: String): Properties {
            return if (elevations.isNotEmpty()) {
                props.copy(elevations = elevationsFromString(elevations)?.toImmutableMap())
            } else props
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <AssistChip enabled="true">...</AssistChip>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        /**
         * Sets the event name to be triggered on the server when the chip is clicked.
         *
         * ```
         * <AssistChip phx-click="yourServerEventHandler">...</AssistChip>
         * ```
         * @param event event name defined on the server to handle the chip's click.
         */
        fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Whether this chip is selected or not.
         * ```
         * <FilterChip selected="true">...</FilterChip>
         * ```
         * @param selected true if the chip is selected, false otherwise.
         */
        fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }

        /**
         * Defines the shape of this chip's container.
         * ```
         * <AssistChip shape="16" >...</AssistChip>
         * ```
         * @param shape chip's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }
    }
}
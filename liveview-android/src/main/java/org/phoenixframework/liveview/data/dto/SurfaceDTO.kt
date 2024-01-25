package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material surface is the central metaphor in material design. Each surface exists at a given
 * elevation, which influences how that piece of surface visually relates to other surfaces and how
 * that surface is modified by tonal variance.
 * A surface can be:
 * - simple: it's just a surface with no action or state
 * - clickable: the surface has a clickable action
 * - selectable: the surface has a selected state and a click action, but this action only works
 * when the component is not selected.
 * - checkable: the surface has a checked state and a change action that receives the new check
 * state as parameter.
 * ```
 * <Surface shape="12" color="system-blue" content-color="system-white">
 *   <Text padding="16">Simple Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="system-blue" content-color="system-white"
 *   phx-click="onClick">
 *   <Text padding="32">Clickable Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="system-blue" content-color="system-white"
 *   selected={"#{@isSelected}"} phx-click="selectItem"} >
 *   <Text padding="32">Selectable Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="system-blue" content-color="system-white"
 *   checked={"#{@isChecked}"} phx-change="toggleCheck"} >
 *   <Text padding="32">Checkable Surface</Text>
 * </Surface>
 * ```
 */
internal class SurfaceDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val shape = builder.shape
    private val color = builder.color
    private val contentColor = builder.contentColor
    private val tonalElevation = builder.tonalElevation
    private val shadowElevation = builder.shadowElevation
    private val border = builder.border
    private val hasVerticalScroll = builder.hasVerticalScrolling
    private val hasHorizontalScroll = builder.hasHorizontalScrolling
    private val onClick = builder.onClick
    private val value = builder.value
    private val enabled = builder.enabled
    private val selected = builder.selected
    private val checked = builder.checked
    private val onChange = builder.onChange

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val color = color ?: MaterialTheme.colorScheme.surface
        val newModifier = modifier
            .paddingIfNotNull(paddingValues)
            .optional(
                hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
            )
            .optional(
                hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
            )
        when {
            onClick != null && selected == null ->
                Surface(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            onClick != null && selected != null ->
                Surface(
                    selected = selected,
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            checked != null ->
                Surface(
                    checked = checked,
                    onCheckedChange = {
                        if (onChange != null) {
                            pushEvent.invoke(
                                ComposableBuilder.EVENT_TYPE_CHANGE,
                                onChange,
                                it,
                                null
                            )
                        }
                    },
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            else ->
                Surface(
                    modifier = newModifier,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )
        }
    }

    internal class Builder : ComposableBuilder() {
        var border: BorderStroke? = null
            private set
        var checked: Boolean? = null
            private set
        var color: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var enabled: Boolean? = null
            private set
        var onChange: String? = null
            private set
        var onClick: String? = null
            private set
        var selected: Boolean? = null
            private set
        var shadowElevation: Dp? = null
            private set
        var shape: Shape? = null
            private set
        var tonalElevation: Dp? = null
            private set

        /**
         * Optional border to draw on top of the surface.
         */
        fun border(border: String) = apply {
            this.border = borderFromString(border)
        }

        /**
         * callback to be invoked when the toggleable Surface is clicked.
         */
        fun checked(checked: String) = apply {
            this.checked = checked.toBoolean()
        }

        /**
         * The background color. Use system-transparent to have no color.
         */
        fun color(color: String) = apply {
            this.color = color.toColor()
        }

        /**
         * The preferred content color provided by this Surface to its children.
         */
        fun contentColor(color: String) = apply {
            this.contentColor = color.toColor()
        }

        /**
         * Controls the enabled state of the surface. When false, this surface will not be clickable.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        fun onChange(onChange: String) = apply {
            this.onChange = onChange
        }

        /**
         * Callback to be called when the surface is clicked.
         */
        fun onClick(onClick: String) = apply {
            this.onClick = onClick
        }

        /**
         * Whether or not this Surface is selected.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * The size of the shadow below the surface. To prevent shadow creep, only apply shadow
         * elevation when absolutely necessary, such as when the surface requires visual separation
         * from a patterned background. Note that It will not affect z index of the Surface. If you
         * want to change the drawing order you can use Modifier.zIndex.
         */
        fun shadowElevation(shadow: String) = apply {
            if (shadow.isNotEmptyAndIsDigitsOnly()) {
                this.shadowElevation = shadow.toInt().dp
            }
        }

        /**
         * Defines the surface's shape as well its shadow. A shadow is only displayed if the
         * tonalElevation is greater than zero.
         *
         * @param shape surface's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * A higher the elevation will result in a darker color in light theme and lighter color
         * in dark theme.
         */
        fun tonalElevation(elevation: String) = apply {
            if (elevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = elevation.toInt().dp
            }
        }

        fun build() = SurfaceDTO(this)
    }
}

internal object SurfaceDtoFactory : ComposableViewFactory<SurfaceDTO, SurfaceDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SurfaceDTO = attributes.fold(SurfaceDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrBorder -> builder.border(attribute.value)
            attrChecked -> builder.checked(attribute.value)
            attrColor -> builder.color(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value)
            attrPhxChange -> builder.onChange(attribute.value)
            attrSelected -> builder.enabled(attribute.value)
            attrShadowElevation -> builder.shadowElevation(attribute.value)
            attrShape -> builder.shape(attribute.value)
            attrTonalElevation -> builder.tonalElevation(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as SurfaceDTO.Builder
    }.build()
}
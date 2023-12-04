package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.constants.Templates
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
 * Material Design floating action button.
 * ```
 * <FloatingActionButton phx-click="fabHandleAction">
 *   <Icon image-vector="filled:Add" />
 * </FloatingActionButton>
 * ```
 * You can also declare a `SmallFloatingActionButton`, `LargeFloatingActionButton`, and
 * `ExtendedFloatingActionButton`. The last one, can have two children using the templates: `icon`
 * and `text`.
 * ```
 * <ExtendedFloatingActionButton phx-click="fabAction" >
 *   <Icon image-vector="filled:Add" template="icon"/>
 *   <Text template="text">Increment</Text>
 * </ExtendedFloatingActionButton>
 * ```
 */
internal class FloatingActionButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick
    private val shape: Shape = builder.shape
    private val containerColor: Color? = builder.containerColor
    private val contentColor: Color? = builder.contentColor
    private val elevation: ImmutableMap<String, String>? = builder.elevation?.toImmutableMap()
    private val expanded: Boolean = builder.expanded

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val containerColor = containerColor ?: FloatingActionButtonDefaults.containerColor
        when (composableNode?.node?.tag) {
            ComposableTypes.fab ->
                FloatingActionButton(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.smallFab ->
                SmallFloatingActionButton(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.largeFab ->
                LargeFloatingActionButton(
                    onClick = onClick,
                    modifier = modifier,
                    shape = shape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.extendedFab -> {
                val text = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == Templates.templateText }
                }
                val icon = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == Templates.templateIcon }
                }
                ExtendedFloatingActionButton(
                    text = {
                        text?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    icon = {
                        icon?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    onClick = onClick,
                    modifier = modifier,
                    expanded = expanded,
                    shape = shape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                )
            }
        }
    }

    @Composable
    private fun getFabElevation(elevation: ImmutableMap<String, String>?): FloatingActionButtonElevation {
        val defaultValue = FloatingActionButtonDefaults.elevation()
        return if (elevation == null) {
            defaultValue
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultValue.privateField(key))

            FloatingActionButtonDefaults.elevation(
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var onClick: () -> Unit = {}
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var shape: Shape = CircleShape
            private set
        var elevation: Map<String, String>? = null
            private set
        var expanded: Boolean = true
            private set

        /**
         * The color used for the background of this FAB.
         *
         * ```
         * <FloatingActionButton container-color="#FF0000FF" />
         * ```
         * @param color the background color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * The preferred color for content inside this FAB.
         *
         * ```
         * <FloatingActionButton content-color="#FF0000FF" />
         * ```
         * @param color the content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <FloatingActionButton phx-click="yourServerEventHandler">...</FloatingActionButton>
         * ```
         * @param event event name defined on the server to handle the button's click.
         * @param pushEvent function responsible to dispatch the server call.
         */
        fun onClick(event: String, pushEvent: PushEvent?) = apply {
            this.onClick = onClickFromString(pushEvent, event)
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <FloatingActionButton shape="circle" >...</FloatingActionButton>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape, CircleShape)
            }
        }

        /**
         * Set FloatingActionButton elevations.
         * ```
         * <FloatingActionButton
         *   elevation="{'defaultElevation': '20', 'pressedElevation': '10'}">
         *   ...
         * </FloatingActionButton>
         * ```
         * @param elevations an JSON formatted string, containing the button elevations. The
         * elevation supported keys are: `defaultElevation`, `pressedElevation`, `focusedElevation`,
         * and `hoveredElevation`.
         */
        fun elevation(elevations: String) = apply {
            if (elevations.isNotEmpty()) {
                this.elevation = elevationsFromString(elevations)
            }
        }

        /**
         * Controls the expansion state of this FAB. In an expanded state, the FAB will show both
         * the icon and text. In a collapsed state, the FAB will show only the icon.
         * ```
         * <ExpandedFloatingActionButton
         *   expanded="true">
         *   ...
         * </ExpandedFloatingActionButton>
         * ```
         * @param expanded true if the FAB is expanded, false otherwise.
         */
        fun expanded(expanded: String) = apply {
            if (expanded.isNotEmpty()) {
                this.expanded = expanded.toBoolean()
            }
        }

        fun build() = FloatingActionButtonDTO(this)
    }
}

internal object FloatingActionButtonDtoFactory :
    ComposableViewFactory<FloatingActionButtonDTO, FloatingActionButtonDTO.Builder>() {
    /**
     * Creates an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object. FloatingActionButtonDTO co-relates to the FloatingActionButton
     * composable from Compose library.
     * @param attributes the `Attributes` object to create the `FloatingActionButtonDTO` object from
     * @return an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object.
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): FloatingActionButtonDTO = attributes.fold(
        FloatingActionButtonDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrElevation -> builder.elevation(attribute.value)
            attrExpanded -> builder.expanded(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value, pushEvent)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as FloatingActionButtonDTO.Builder
    }.build()
}
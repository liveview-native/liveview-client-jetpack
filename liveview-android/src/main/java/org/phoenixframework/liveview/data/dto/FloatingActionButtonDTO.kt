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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.DefaultElevationValues.Level3
import org.phoenixframework.liveview.data.constants.DefaultElevationValues.Level4
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design floating action button.
 * ```
 * <FloatingActionButton phx-click="fabHandleAction">
 *   <Icon imageVector="filled:Add" />
 * </FloatingActionButton>
 * ```
 * You can also declare a `SmallFloatingActionButton`, `LargeFloatingActionButton`, and
 * `ExtendedFloatingActionButton`. The last one, can have two children using the templates: `icon`
 * and `text`.
 * ```
 * <ExtendedFloatingActionButton phx-click="fabAction" >
 *   <Icon imageVector="filled:Add" template="icon"/>
 *   <Text template="text">Increment</Text>
 * </ExtendedFloatingActionButton>
 * ```
 */
internal class FloatingActionButtonDTO private constructor(props: Properties) :
    ComposableView<FloatingActionButtonDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val onClick = props.onClick
        val shape = props.shape
        val containerColorValue = props.containerColor
        val contentColor = props.contentColor
        val elevation = props.elevation
        val expanded = props.expanded

        val containerColor = containerColorValue ?: FloatingActionButtonDefaults.containerColor
        when (composableNode?.node?.tag) {
            ComposableTypes.floatingActionButton ->
                FloatingActionButton(
                    onClick = onClickFromString(
                        pushEvent, onClick, props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
                    shape = shape ?: FloatingActionButtonDefaults.shape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.smallFloatingActionButton ->
                SmallFloatingActionButton(
                    onClick = onClickFromString(
                        pushEvent, onClick, props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
                    shape = shape ?: FloatingActionButtonDefaults.smallShape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.largeFloatingActionButton ->
                LargeFloatingActionButton(
                    onClick = onClickFromString(
                        pushEvent, onClick, props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
                    shape = shape ?: FloatingActionButtonDefaults.largeShape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }

            ComposableTypes.extendedFloatingActionButton -> {
                val text = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateText }
                }
                val icon = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateIcon }
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
                    onClick = onClickFromString(
                        pushEvent, onClick, props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
                    expanded = expanded,
                    shape = shape ?: FloatingActionButtonDefaults.extendedFabShape,
                    containerColor = containerColor,
                    contentColor = contentColor ?: contentColorFor(containerColor),
                    elevation = getFabElevation(elevation),
                )
            }
        }
    }

    @Composable
    private fun getFabElevation(elevation: ImmutableMap<String, String>?): FloatingActionButtonElevation {
        val defaultElevation = FloatingActionButtonDefaults.elevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            FloatingActionButtonDefaults.elevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level3),
                pressedElevation = value(elevationAttrPressedElevation, Level3),
                focusedElevation = value(elevationAttrFocusedElevation, Level3),
                hoveredElevation = value(elevationAttrHoveredElevation, Level4),
            )
        }
    }

    @Stable
    internal data class Properties(
        val onClick: String,
        val containerColor: Color?,
        val contentColor: Color?,
        val shape: Shape?,
        val elevation: ImmutableMap<String, String>?,
        val expanded: Boolean,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var onClick: String = ""
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var shape: Shape? = null
        private var elevation: ImmutableMap<String, String>? = null
        private var expanded: Boolean = true

        /**
         * The color used for the background of this FAB.
         *
         * ```
         * <FloatingActionButton containerColor="#FF0000FF" />
         * ```
         * @param color the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
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
         * <FloatingActionButton contentColor="#FF0000FF" />
         * ```
         * @param color the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
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
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <FloatingActionButton shape="circle" >...</FloatingActionButton>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
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
                this.elevation = elevationsFromString(elevations)?.toImmutableMap()
            }
        }

        /**
         * Controls the expansion state of this FAB. In an expanded state, the FAB will show both
         * the icon and text. In a collapsed state, the FAB will show only the icon.
         * ```
         * <ExpandedFloatingActionButton expanded="true">
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

        fun build() = FloatingActionButtonDTO(
            Properties(
                onClick,
                containerColor,
                contentColor,
                shape,
                elevation,
                expanded,
                commonProps,
            )
        )
    }
}

internal object FloatingActionButtonDtoFactory : ComposableViewFactory<FloatingActionButtonDTO>() {
    /**
     * Creates an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object. FloatingActionButtonDTO co-relates to the FloatingActionButton
     * composable from Compose library.
     * @param attributes the `Attributes` object to create the `FloatingActionButtonDTO` object from
     * @return an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object.
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
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
            attrPhxClick -> builder.onClick(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as FloatingActionButtonDTO.Builder
    }.build()
}
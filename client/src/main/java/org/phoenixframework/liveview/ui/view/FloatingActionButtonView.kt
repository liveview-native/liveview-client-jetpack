package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrElevation
import org.phoenixframework.liveview.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level3
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level4
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.constants.Templates.templateIcon
import org.phoenixframework.liveview.constants.Templates.templateText
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
internal class FloatingActionButtonView private constructor(props: Properties) :
    ComposableView<FloatingActionButtonView.Properties>(props) {

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
        val onClick: String = "",
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val shape: Shape? = null,
        val elevation: ImmutableMap<String, String>? = null,
        val expanded: Boolean = true,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<FloatingActionButtonView>() {
        /**
         * Creates an `FloatingActionButtonView` object based on the attributes and text of the input
         * `Attributes` object. FloatingActionButtonView co-relates to the FloatingActionButton
         * composable from Compose library.
         * @param attributes the `Attributes` object to create the `FloatingActionButtonView` object
         * from @return an `FloatingActionButtonView` object based on the attributes and text of the
         * input `Attributes` object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): FloatingActionButtonView = FloatingActionButtonView(attributes.fold(
            Properties()
        ) { props, attribute ->
            when (attribute.name) {
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrElevation -> elevation(props, attribute.value)
                attrExpanded -> expanded(props, attribute.value)
                attrPhxClick -> onClick(props, attribute.value)
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
         * The color used for the background of this FAB.
         *
         * ```
         * <FloatingActionButton containerColor="#FF0000FF" />
         * ```
         * @param color the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(containerColor = color.toColor())
            } else props
        }

        /**
         * The preferred color for content inside this FAB.
         *
         * ```
         * <FloatingActionButton contentColor="#FF0000FF" />
         * ```
         * @param color the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(contentColor = color.toColor())
            } else props
        }

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <FloatingActionButton phx-click="yourServerEventHandler">...</FloatingActionButton>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <FloatingActionButton shape="circle" >...</FloatingActionButton>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape, CircleShape))
            } else props
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
        private fun elevation(props: Properties, elevations: String): Properties {
            return if (elevations.isNotEmpty()) {
                props.copy(elevation = elevationsFromString(elevations)?.toImmutableMap())
            } else props
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
        private fun expanded(props: Properties, expanded: String): Properties {
            return if (expanded.isNotEmpty()) {
                props.copy(expanded = expanded.toBoolean())
            } else props
        }
    }
}
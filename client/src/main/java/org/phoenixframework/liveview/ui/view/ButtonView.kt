package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.constants.Attrs.attrElevation
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level0
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level1
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level2
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrPressedElevation
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
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTAINER_ALPHA
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design buttons.
 * ```
 * // Filled Button
 * <Button phx-click="eventHandler">
 *   <Text>Button Text</Text>
 * </Button>
 *
 * // Elevated Button
 * <ElevatedButton phx-click="eventHandler">
 *   <Text>Button Text</Text>
 * </ElevatedButton>
 *
 * // Filled Tonal Button
 * <FilledTonalButton phx-click="eventHandler">
 *   <Text>Button Text</Text>
 * </FilledTonalButton>
 *
 * // Outlined Button
 * <OutlinedButton phx-click="eventHandler">
 *   <Text>Button Text</Text>
 * </OutlinedButton>
 *
 * // Text Button
 * <TextButton phx-click="eventHandler">
 *   <Text>Button Text</Text>
 * </TextButton>
 * ```
 */
class ButtonView private constructor(props: Properties) :
    ComposableView<ButtonView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val onClick = props.onClick
        val enabled = props.enabled
        val shape = props.shape
        val colors = props.colors
        val elevation = props.elevations
        val contentPadding = props.contentPadding
        val border = props.border

        val localButtonParentActionHandler = LocalButtonParentActionHandler.current
        when (composableNode?.node?.tag) {
            // Elevated Button
            ComposableTypes.elevatedButton ->
                ElevatedButton(
                    onClick = {
                        if (localButtonParentActionHandler?.buttonParentMustHandleAction(
                                composableNode
                            ) == true
                        )
                            localButtonParentActionHandler.handleAction(composableNode, pushEvent)
                        else
                            onClickFromString(
                                pushEvent,
                                onClick,
                                props.commonProps.phxValue
                            ).invoke()
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.elevatedShape,
                    colors = getElevatedButtonColors(colors),
                    elevation = getElevatedButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Filled Tonal Button
            ComposableTypes.filledTonalButton ->
                FilledTonalButton(
                    onClick = {
                        if (localButtonParentActionHandler?.buttonParentMustHandleAction(
                                composableNode
                            ) == true
                        )
                            localButtonParentActionHandler.handleAction(composableNode, pushEvent)
                        else
                            onClickFromString(
                                pushEvent,
                                onClick,
                                props.commonProps.phxValue
                            ).invoke()
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.filledTonalShape,
                    colors = getFilledTonalButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Outlined Button
            ComposableTypes.outlinedButton ->
                OutlinedButton(
                    onClick = {
                        if (localButtonParentActionHandler?.buttonParentMustHandleAction(
                                composableNode
                            ) == true
                        )
                            localButtonParentActionHandler.handleAction(composableNode, pushEvent)
                        else
                            onClickFromString(
                                pushEvent,
                                onClick,
                                props.commonProps.phxValue
                            ).invoke()
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.outlinedShape,
                    colors = getOutlineButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border ?: ButtonDefaults.outlinedButtonBorder,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Text Button
            ComposableTypes.textButton ->
                TextButton(
                    onClick = {
                        if (localButtonParentActionHandler?.buttonParentMustHandleAction(
                                composableNode
                            ) == true
                        )
                            localButtonParentActionHandler.handleAction(composableNode, pushEvent)
                        else
                            onClickFromString(
                                pushEvent,
                                onClick,
                                props.commonProps.phxValue
                            ).invoke()
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.textShape,
                    colors = getTextButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.TextButtonContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Filled Button
            else ->
                Button(
                    onClick = {
                        if (localButtonParentActionHandler?.buttonParentMustHandleAction(
                                composableNode
                            ) == true
                        )
                            localButtonParentActionHandler.handleAction(composableNode, pushEvent)
                        else
                            onClickFromString(
                                pushEvent,
                                onClick,
                                props.commonProps.phxValue
                            ).invoke()
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.shape,
                    colors = getButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
        }
    }

    @Composable
    private fun getButtonElevation(elevation: Map<String, String>?): ButtonElevation {
        val defaultElevation = ButtonDefaults.buttonElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            ButtonDefaults.buttonElevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, Level0),
                focusedElevation = value(elevationAttrFocusedElevation, Level0),
                hoveredElevation = value(elevationAttrHoveredElevation, Level1),
                disabledElevation = value(elevationAttrDisabledElevation, Level0)
            )
        }
    }

    @Composable
    private fun getElevatedButtonElevation(elevation: Map<String, String>?): ButtonElevation {
        val defaultElevation = ButtonDefaults.elevatedButtonElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            ButtonDefaults.elevatedButtonElevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level1),
                pressedElevation = value(elevationAttrPressedElevation, Level1),
                focusedElevation = value(elevationAttrFocusedElevation, Level1),
                hoveredElevation = value(elevationAttrHoveredElevation, Level2),
                disabledElevation = value(elevationAttrDisabledElevation, Level0)
            )
        }
    }

    @Composable
    private fun getButtonColors(colors: Map<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.buttonColors()

        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.buttonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: colors[colorAttrContainerColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: MaterialTheme.colorScheme.primary.copy(alpha = DISABLED_CONTAINER_ALPHA),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onPrimary.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Composable
    private fun getElevatedButtonColors(colors: ImmutableMap<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.elevatedButtonColors()

        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.elevatedButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: colors[colorAttrContainerColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Composable
    private fun getFilledTonalButtonColors(colors: ImmutableMap<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.filledTonalButtonColors()

        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.filledTonalButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: colors[colorAttrContainerColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Composable
    private fun getOutlineButtonColors(colors: ImmutableMap<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.outlinedButtonColors()

        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.outlinedButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: colors[colorAttrContainerColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Composable
    private fun getTextButtonColors(colors: ImmutableMap<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.textButtonColors()

        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.textButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: colors[colorAttrContainerColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Stable
    data class Properties(
        val onClick: String = "",
        val enabled: Boolean = true,
        val shape: Shape? = null,
        val colors: ImmutableMap<String, String>? = null,
        val elevations: ImmutableMap<String, String>? = null,
        val contentPadding: PaddingValues? = null,
        val border: BorderStroke? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    object Factory : ComposableViewFactory<ButtonView>() {
        /**
         * Creates a `ButtonView` object based on the attributes of the input `Attributes` object.
         * ButtonView co-relates to the Button composable
         * @param attributes the `Attributes` object to create the `CardView` object from
         * @return a `ButtonView` object based on the attributes of the input `Attributes` object
         **/
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): ButtonView = ButtonView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrBorder -> border(props, attribute.value)
                attrColors -> colors(props, attribute.value)
                attrContentPadding -> contentPadding(props, attribute.value)
                attrElevation -> elevation(props, attribute.value)
                attrEnabled -> enabled(props, attribute.value)
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
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <Button phx-click="yourServerEventHandler">...</Button>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Defines if the button is enabled.
         *
         * ```
         * <Button enabled="true">...</Button>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return if (enabled.isNotEmpty()) {
                props.copy(enabled = enabled.toBoolean())
            } else props
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <Button shape="circle" >...</Button>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues],
         * or use an integer representing the curve size applied for all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape))
            } else props
        }

        /**
         * Set Button colors.
         * ```
         * <Button
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         *   ...
         * </Button>
         * ```
         * @param colors an JSON formatted string, containing the button colors. The color keys
         * supported are: `containerColor`, `contentColor`, `disabledContainerColor, and
         * `disabledContentColor`
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Set Button elevations.
         * ```
         * <Button
         *   elevation="{'defaultElevation': '20', 'pressedElevation': '10'}">
         *   ...
         * </Button>
         * ```
         * @param elevations an JSON formatted string, containing the button elevations. The
         * elevation supported keys are: `defaultElevation`, `pressedElevation`, `focusedElevation`,
         * `hoveredElevation`, and `disabledElevation`.
         */
        private fun elevation(props: Properties, elevations: String): Properties {
            return if (elevations.isNotEmpty()) {
                props.copy(elevations = elevationsFromString(elevations)?.toImmutableMap())
            } else props
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <Button contentPadding="8">...</Button>
         * ```
         * @param contentPadding int value representing the padding to be applied to the button's
         * content.
         */
        private fun contentPadding(props: Properties, contentPadding: String): Properties {
            return if (contentPadding.isNotEmpty()) {
                props.copy(contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp))
            } else props
        }

        /**
         * The border to draw around the container of this button. This property is used just for
         * `OutlineButton`.
         * ```
         * <OutlinedButton border="{'width': '2', 'color': '#FF0000FF'}">...</OutlinedButton>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing button border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun border(props: Properties, border: String): Properties {
            return props.copy(border = borderFromString(border))
        }
    }
}

/**
 * This composition local allows to a parent node to intercept the button's click event.
 * See `LiveForm` component for an example of usage.
 */
val LocalButtonParentActionHandler =
    compositionLocalOf<ButtonParentActionHandler?> { null }

interface ButtonParentActionHandler {
    /**
     * Returns true if the parent node must handle the button action. False otherwise.
     */
    fun buttonParentMustHandleAction(buttonNode: ComposableTreeNode?): Boolean

    /**
     * Button's parent action.
     */
    fun handleAction(
        composableNode: ComposableTreeNode?,
        pushEvent: PushEvent,
    )
}
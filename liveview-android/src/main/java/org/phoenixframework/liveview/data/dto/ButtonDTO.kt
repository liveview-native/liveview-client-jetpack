package org.phoenixframework.liveview.data.dto

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.data.constants.DefaultElevationValues.Level0
import org.phoenixframework.liveview.data.constants.DefaultElevationValues.Level1
import org.phoenixframework.liveview.data.constants.DefaultElevationValues.Level2
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContainerAlpha
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
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
internal class ButtonDTO private constructor(props: Properties) :
    ComposableView<ButtonDTO.Properties>(props) {

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

        when (composableNode?.node?.tag) {
            // Filled Button
            ComposableTypes.button ->
                Button(
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.shape,
                    colors = getButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Elevated Button
            ComposableTypes.elevatedButton ->
                ElevatedButton(
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
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
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: MaterialTheme.colorScheme.primary.copy(alpha = disabledContainerAlpha),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onPrimary.copy(alpha = disabledContentAlpha),
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
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
                        ?.copy(alpha = disabledContainerAlpha)
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: colors[colorAttrContentColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
            )
        }
    }

    @Stable
    internal data class Properties(
        val onClick: String,
        val enabled: Boolean,
        val shape: Shape?,
        val colors: ImmutableMap<String, String>?,
        val elevations: ImmutableMap<String, String>?,
        val contentPadding: PaddingValues?,
        val border: BorderStroke?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties


    internal class Builder : ComposableBuilder() {
        private var onClick: String = ""
        private var enabled: Boolean = true
        private var shape: Shape? = null
        private var colors: ImmutableMap<String, String>? = null
        private var elevations: ImmutableMap<String, String>? = null
        private var contentPadding: PaddingValues? = null
        private var border: BorderStroke? = null

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <Button phx-click="yourServerEventHandler">...</Button>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String): Builder = apply {
            this.onClick = event
        }

        /**
         * Defines if the button is enabled.
         *
         * ```
         * <Button enabled="true">...</Button>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        fun enabled(enabled: String): Builder = apply {
            if (enabled.isNotEmpty()) {
                this.enabled = enabled.toBoolean()
            }
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <Button shape="circle" >...</Button>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues],
         * or use an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
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
        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
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
        fun elevation(elevations: String): Builder = apply {
            if (elevations.isNotEmpty()) {
                this.elevations = elevationsFromString(elevations)?.toImmutableMap()
            }
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <Button contentPadding="8">...</Button>
         * ```
         * @param contentPadding int value representing the padding to be applied to the button's
         * content.
         */
        fun contentPadding(contentPadding: String): Builder = apply {
            if (contentPadding.isNotEmpty()) {
                this.contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp)
            }
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
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun border(border: String): Builder = apply {
            this.border = borderFromString(border)
        }

        fun build() = ButtonDTO(
            Properties(
                onClick,
                enabled,
                shape,
                colors,
                elevations,
                contentPadding,
                border,
                commonProps,
            )
        )
    }
}

internal object ButtonDtoFactory : ComposableViewFactory<ButtonDTO>() {
    /**
     * Creates a `ButtonDTO` object based on the attributes of the input `Attributes` object.
     * ButtonDTO co-relates to the Button composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `ButtonDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ButtonDTO = attributes.fold(ButtonDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrBorder -> builder.border(attribute.value)
            attrColors -> builder.colors(attribute.value)
            attrContentPadding -> builder.contentPadding(attribute.value)
            attrElevation -> builder.elevation(attribute.value)
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ButtonDTO.Builder
    }.build()
}
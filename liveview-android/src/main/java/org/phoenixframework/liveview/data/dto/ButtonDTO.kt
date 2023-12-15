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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorderColor
import org.phoenixframework.liveview.data.constants.Attrs.attrBorderWidth
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
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContainerAlpha
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.privateField
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
internal class ButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: String = builder.onClick
    private val enabled: Boolean = builder.enabled
    private val shape: Shape? = builder.shape
    private val colors: ImmutableMap<String, String>? = builder.colors?.toImmutableMap()
    private val elevation: ImmutableMap<String, String>? = builder.elevations?.toImmutableMap()
    private val contentPadding: PaddingValues? = builder.contentPadding
    private val border: BorderStroke? = builder.border
    private val value: Any? = builder.value

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        when (composableNode?.node?.tag) {
            // Filled Button
            ComposableTypes.button ->
                Button(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.shape,
                    colors = getButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            // Elevated Button
            ComposableTypes.elevatedButton ->
                ElevatedButton(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
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
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
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
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
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
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: ButtonDefaults.textShape,
                    colors = getTextButtonColors(colors),
                    elevation = getButtonElevation(elevation),
                    border = border,
                    contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
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
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            ButtonDefaults.buttonElevation(
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                disabledElevation = value(elevationAttrDisabledElevation)
            )
        }
    }

    @Composable
    private fun getElevatedButtonElevation(elevation: Map<String, String>?): ButtonElevation {
        val defaultElevation = ButtonDefaults.elevatedButtonElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            ButtonDefaults.elevatedButtonElevation(
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                disabledElevation = value(elevationAttrDisabledElevation)
            )
        }
    }

    @Composable
    private fun getButtonColors(colors: Map<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.buttonColors()

        return if (colors == null) {
            defaultColors
        } else {
            // TODO ButtonDefaults properties are private and need Composer parameter to be called
            //   via reflection. So the default values are being get from the theme, but using the
            //   same values declared in the ButtonDefaults declaration. This comment also to the
            //   ElevatedButton, FilledTonalButton, FilledTonalButton, and OutlineButton
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

    internal class Builder : ComposableBuilder() {
        var onClick: String = ""
            private set
        var enabled: Boolean = true
            private set
        var shape: Shape? = null
            private set
        var colors: Map<String, String>? = null
            private set
        var elevations: Map<String, String>? = null
            private set
        var contentPadding: PaddingValues? = null
            private set
        var border: BorderStroke? = null
            private set

        private var borderWidth: Dp = 1.dp
        private var borderColor: Color? = null

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
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
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
                this.colors = colorsFromString(colors)
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
                this.elevations = elevationsFromString(elevations)
            }
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <Button content-padding="8">...</Button>
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
         * The border width to draw around the container of this button. This property is used just
         * for `OutlineButton`.
         * ```
         * <OutlinedButton border-width="2">...</OutlinedButton>
         * ```
         * @param borderWidth int value representing button border's width.
         * content.
         */
        fun borderWidth(borderWidth: String): Builder = apply {
            if (borderWidth.isNotEmptyAndIsDigitsOnly()) {
                this.borderWidth = (borderWidth.toIntOrNull() ?: 0).dp
            }
        }

        /**
         * The border color to draw around the container of this button. This property is used just
         * for `OutlineButton`.
         * ```
         * <OutlinedButton border-color="#FF0000FF">...</OutlinedButton>
         * ```
         * @param borderColor int value representing the padding to be applied to the button's
         * content. The color must be specified as a string in the AARRGGBB format.
         */
        fun borderColor(borderColor: String): Builder = apply {
            if (borderColor.isNotEmpty()) {
                this.borderColor = borderColor.toColor()
            }
        }

        fun build(): ButtonDTO {
            val w = borderWidth
            val c = borderColor
            if (c != null) {
                this.border = BorderStroke(w, c)
            }
            return ButtonDTO(this)
        }
    }
}

internal object ButtonDtoFactory : ComposableViewFactory<ButtonDTO, ButtonDTO.Builder>() {
    /**
     * Creates a `ButtonDTO` object based on the attributes of the input `Attributes` object.
     * ButtonDTO co-relates to the Button composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `ButtonDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ButtonDTO = attributes.fold(ButtonDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrBorderColor -> builder.borderColor(attribute.value)
            attrBorderWidth -> builder.borderWidth(attribute.value)
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
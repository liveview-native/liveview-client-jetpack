package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design standard icon button.
 * ```
 * <IconButton phx-click="iconButtonHandleAction">
 *   <Icon image-vector="filled:Add" />
 * </IconButton>
 * <FilledIconButton phx-click="iconButtonHandleAction">
 *   <Icon image-vector="filled:Add" />
 * </FilledIconButton>
 * <FilledTonalIconButton phx-click="iconButtonHandleAction">
 *   <Icon image-vector="filled:Add" />
 * </FilledTonalIconButton>
 * <OutlinedIconButton phx-click="iconButtonHandleAction">
 *   <Icon image-vector="filled:Add" />
 * </OutlinedIconButton>
 * ```
 */
internal class IconButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: String = builder.onClick
    private val enabled: Boolean = builder.enabled
    private val colors: ImmutableMap<String, String>? = builder.colors?.toImmutableMap()
    private val shape: Shape? = builder.shape
    private val border: BorderStroke? = builder.border
    private val value: Any? = builder.value

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        when (composableNode?.node?.tag) {
            ComposableTypes.iconButton -> {
                IconButton(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    enabled = enabled,
                    colors = getIconButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledIconButton -> {
                FilledIconButton(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledIconButtonColors(colors = colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledTonalIconButton -> {
                FilledTonalIconButton(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledTonalIconButtonColors(colors = colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.outlinedIconButton -> {
                OutlinedIconButton(
                    onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
                    modifier = modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    border = border ?: IconButtonDefaults.outlinedIconButtonBorder(enabled),
                    colors = getOutlinedIconButtonColors(colors = colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }
        }
    }

    @Composable
    private fun getIconButtonColors(colors: ImmutableMap<String, String>?): IconButtonColors {
        val defaultValue = IconButtonDefaults.iconButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            IconButtonDefaults.iconButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: LocalContentColor.current,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: (colors[colorAttrContentColor]?.toColor() ?: LocalContentColor.current).copy(
                        alpha = disabledContentAlpha
                    ),
            )
        }
    }

    @Composable
    private fun getFilledIconButtonColors(colors: ImmutableMap<String, String>?): IconButtonColors {
        val defaultValue = IconButtonDefaults.filledIconButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor =
                colors[colorAttrContainerColor]?.toColor() ?: MaterialTheme.colorScheme.primary
            IconButtonDefaults.filledIconButtonColors(
                containerColor = contentColor,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.contentColorFor(contentColor),
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }
    }

    @Composable
    private fun getFilledTonalIconButtonColors(colors: ImmutableMap<String, String>?): IconButtonColors {
        val defaultValue = IconButtonDefaults.filledTonalIconButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.secondaryContainer
            IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = contentColor,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.contentColorFor(contentColor),
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }
    }

    @Composable
    private fun getOutlinedIconButtonColors(colors: ImmutableMap<String, String>?): IconButtonColors {
        val defaultValue = IconButtonDefaults.outlinedIconButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContentColor]?.toColor() ?: LocalContentColor.current
            IconButtonDefaults.outlinedIconButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: contentColor.copy(alpha = 0.38f),
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var border: BorderStroke? = null
            private set
        var onClick: String = ""
            private set
        var enabled: Boolean = true
            private set
        var colors: Map<String, String>? = null
            private set
        var shape: Shape? = null
            private set

        /**
         * The border to draw around the container of this button. This property is used just for
         * `OutlineIconButton`.
         * ```
         * <OutlinedIconButton border="{'width': 2, 'color': '#FF0000FF'}">...</OutlinedButton>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing button border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format
         */
        fun border(border: String) = apply {
            this.border = borderFromString(border)
        }

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <IconButton phx-click="yourServerEventHandler">...</IconButton>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Defines if the button is enabled.
         *
         * ```
         * <IconButton enabled="true">...</IconButton>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        fun enabled(enabled: String): Builder = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Set IconButton elevations.
         * ```
         * <IconButton
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FFFFFFFF'}">
         *   ...
         * </IconButton>
         * ```
         * @param colors an JSON formatted string, containing the button colors. The colors
         * supported keys are: `containerColor`, `contentColor`, `disabledContainerColor`, and
         * `disabledContentColor`.
         */
        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         * This attribute is only used by `FilledIconButton`, `FilledTonalIconButton`, and
         * `OutlinedIconButton`.
         * ```
         * <OutlinedIconButton shape="rectangle" >...</OutlinedIconButton>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun build() = IconButtonDTO(this)
    }
}

internal object IconButtonDtoFactory :
    ComposableViewFactory<IconButtonDTO, IconButtonDTO.Builder>() {
    /**
     * Creates a `IconButtonDTO` object based on the attributes of the input `Attributes` object.
     * IconButtonDTO co-relates to the IconButton composable
     * @param attributes the `Attributes` object to create the `IconButtonDTO` object from
     * @return a `IconButtonDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): IconButtonDTO = attributes.fold(
        IconButtonDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            attrBorder -> builder.border(attribute.value)
            attrColors -> builder.colors(attribute.value)
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as IconButtonDTO.Builder
    }.build()
}
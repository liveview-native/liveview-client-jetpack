package org.phoenixframework.liveview.ui.view

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
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
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
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design standard icon button.
 * ```
 * <IconButton phx-click="iconButtonHandleAction">
 *   <Icon imageVector="filled:Add" />
 * </IconButton>
 * <FilledIconButton phx-click="iconButtonHandleAction">
 *   <Icon imageVector="filled:Add" />
 * </FilledIconButton>
 * <FilledTonalIconButton phx-click="iconButtonHandleAction">
 *   <Icon imageVector="filled:Add" />
 * </FilledTonalIconButton>
 * <OutlinedIconButton phx-click="iconButtonHandleAction">
 *   <Icon imageVector="filled:Add" />
 * </OutlinedIconButton>
 * ```
 */
internal class IconButtonView private constructor(props: Properties) :
    ComposableView<IconButtonView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val onClick = props.onClick
        val enabled = props.enabled
        val colors = props.colors
        val shape = props.shape
        val border = props.border

        when (composableNode?.node?.tag) {
            ComposableTypes.iconButton -> {
                IconButton(
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    enabled = enabled,
                    colors = getIconButtonColors(colors),
                    modifier = props.commonProps.modifier
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledIconButton -> {
                FilledIconButton(
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
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
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = props.commonProps.modifier,
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
                        alpha = DISABLED_CONTENT_ALPHA
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

    @Stable
    internal data class Properties(
        val border: BorderStroke? = null,
        val onClick: String = "",
        val enabled: Boolean = true,
        val colors: ImmutableMap<String, String>? = null,
        val shape: Shape? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<IconButtonView>() {
        /**
         * Creates a `IconButtonView` object based on the attributes of the input `Attributes` object.
         * IconButtonView co-relates to the IconButton composable
         * @param attributes the `Attributes` object to create the `IconButtonView` object from
         * @return a `IconButtonView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): IconButtonView = IconButtonView(attributes.fold(
            Properties()
        ) { props, attribute ->
            when (attribute.name) {
                attrBorder -> border(props, attribute.value)
                attrColors -> colors(props, attribute.value)
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
         * The border to draw around the container of this button. This property is used just for
         * `OutlineIconButton`.
         * ```
         * <OutlinedIconButton border="{'width': 2, 'color': '#FF0000FF'}">...</OutlinedButton>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing button border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun border(props: Properties, border: String): Properties {
            return props.copy(border = borderFromString(border))
        }

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <IconButton phx-click="yourServerEventHandler">...</IconButton>
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
         * <IconButton enabled="true">...</IconButton>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         * This attribute is only used by `FilledIconButton`, `FilledTonalIconButton`, and
         * `OutlinedIconButton`.
         * ```
         * <OutlinedIconButton shape="rectangle" >...</OutlinedIconButton>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues] or use an integer representing
         * the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }
    }
}
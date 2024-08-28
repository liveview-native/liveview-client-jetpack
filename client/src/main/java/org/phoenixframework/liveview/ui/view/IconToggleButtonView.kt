package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrChecked
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString
import org.phoenixframework.liveview.ui.view.CheckBoxView.Factory.handleChangeableAttribute

/**
 * Material Design icon toggle buttons.
 * ```
 * <IconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon imageVector="filled:Check" />
 * </IconToggleButton>
 * <FilledIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon imageVector="filled:Check" />
 * </FilledIconToggleButton>
 * <FilledTonalIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon imageVector="filled:Check" />
 * </FilledTonalIconToggleButton>
 * <OutlinedIconToggleButton checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Icon imageVector="filled:Check" />
 * </FilledTonalIconToggleButton>
 * ```
 */
internal class IconToggleButtonView private constructor(props: Properties) :
    ChangeableView<Boolean, IconToggleButtonView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val changeValueEventName = props.changeableProps.onChange
        val enabled = props.changeableProps.enabled
        val border = props.border
        val colors = props.colors
        val shape = props.shape
        val checked = props.checked

        var stateValue by remember(composableNode) {
            mutableStateOf(checked)
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.iconToggleButton -> {
                IconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    colors = getIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledIconToggleButton -> {
                FilledIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.filledTonalIconToggleButton -> {
                FilledTonalIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getFilledTonalIconToggleButtonColors(colors),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }

            ComposableTypes.outlinedIconToggleButton -> {
                OutlinedIconToggleButton(
                    checked = stateValue,
                    onCheckedChange = {
                        stateValue = it
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    shape = shape ?: IconButtonDefaults.filledShape,
                    colors = getOutlinedIconToggleButtonColors(colors),
                    border = border ?: IconButtonDefaults.outlinedIconToggleButtonBorder(
                        enabled, stateValue
                    ),
                    // TODO interactionSource: MutableInteractionSource,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                }
            }
        }

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }
                    .onChangeable()
                    .map { mergeValueWithPhxValue(KEY_CHECKED, it) }
                    .collect { value ->
                        pushOnChangeEvent(pushEvent, event, value)
                    }
            }
        }
    }

    @Composable
    private fun getIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.iconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContentColor]?.toColor() ?: LocalContentColor.current
            IconButtonDefaults.iconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: contentColor.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: Color.Transparent,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
            )
        }
    }

    @Composable
    private fun getFilledIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.filledIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.primary
            IconButtonDefaults.filledIconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                contentColor = colors[colorAttrContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: contentColorFor(checkedContainerColor),
            )
        }
    }

    @Composable
    private fun getFilledTonalIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.filledTonalIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val containerColor = colors[colorAttrContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.surfaceVariant
            IconButtonDefaults.filledTonalIconToggleButtonColors(
                containerColor = containerColor,
                contentColor = colors[colorAttrContentColor]?.toColor() ?: contentColorFor(
                    containerColor
                ),
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }

    @Composable
    private fun getOutlinedIconToggleButtonColors(colors: ImmutableMap<String, String>?): IconToggleButtonColors {
        val defaultValue = IconButtonDefaults.outlinedIconToggleButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            val contentColor = colors[colorAttrContentColor]?.toColor() ?: LocalContentColor.current
            val checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                ?: MaterialTheme.colorScheme.inverseSurface
            IconButtonDefaults.outlinedIconToggleButtonColors(
                containerColor = colors[colorAttrContainerColor]?.toColor() ?: Color.Transparent,
                contentColor = contentColor,
                disabledContainerColor = colors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContentColor = colors[colorAttrDisabledContentColor]?.toColor()
                    ?: contentColor.copy(alpha = 0.38f),
                checkedContainerColor = colors[colorAttrCheckedContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.inverseSurface,
                checkedContentColor = colors[colorAttrCheckedContentColor]?.toColor()
                    ?: contentColorFor(checkedContainerColor)
            )
        }
    }

    companion object {
        private const val KEY_CHECKED = "checked"
    }

    @Stable
    internal data class Properties(
        val border: BorderStroke? = null,
        val checked: Boolean = false,
        val colors: ImmutableMap<String, String>? = null,
        val shape: Shape? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ComposableViewFactory<IconToggleButtonView>() {
        /**
         * Creates a `IconToggleButtonView` object based on the attributes of the input `Attributes`
         * object. IconToggleButtonView co-relates to the IconToggleButton composables.
         * @param attributes the `Attributes` object to create the `IconToggleButtonView` object from
         * @return a `IconToggleButtonView` object based on the attributes of the input `Attributes`
         * object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): IconToggleButtonView = IconToggleButtonView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrBorder -> border(props, attribute.value)
                        attrChecked -> checked(props, attribute.value)
                        attrColors -> colors(props, attribute.value)
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
                }
            })

        /**
         * The border to draw around the container of this button. This property is used just for
         * `OutlineIconToggleButton`.
         * ```
         * <OutlineIconToggleButton border="{'width': 2, 'color': '#FF0000FF'}">...</OutlineIconToggleButton>
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
         * Whether this icon button is toggled on or off.
         * ```
         * <IconToggleButton checked="true" />
         * ```
         */
        private fun checked(props: Properties, checked: String): Properties {
            return props.copy(checked = checked.toBoolean())
        }

        /**
         * Set IconToggleButton colors.
         * ```
         * <IconToggleButton
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the icon toggle button colors. The
         * color keys supported are: `containerColor`, `contentColor`, `disabledContainerColor,
         * `disabledContentColor`, `checkedContainerColor`, and `checkedContentColor`.
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         * This attribute is only used by `FilledIconToggleButton`, `FilledTonalIconToggleButton`,
         * and `OutlinedIconButton`.
         * ```
         * <FilledIconToggleButton shape="rectangle" >...</FilledIconToggleButton>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }
    }
}
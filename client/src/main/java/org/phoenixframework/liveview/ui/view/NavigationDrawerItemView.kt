package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedBadgeColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnselectedBadgeColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnselectedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnselectedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnselectedTextColor
import org.phoenixframework.liveview.constants.Templates.templateBadge
import org.phoenixframework.liveview.constants.Templates.templateIcon
import org.phoenixframework.liveview.constants.Templates.templateLabel
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
 * Material Design navigation drawer item.
 * NavigationDrawerItem represents a destination within drawers
 * A NavigationDrawerItem can have the following templates as children:
 * - `label`: text label for this item.
 * - `icon`: optional icon for this item, typically an Icon.
 * - `badge`: optional badge to show on this item from the end side.
 *
 * ```
 * <NavigationDrawerItem phx-click="navigate">
 *   <Icon template="icon" imageVector="filled:AccountBox"  />
 *   <Text template="label">Option Label</Text>
 *   <Text template="badge">99+</Text>
 * </NavigationDrawerItem>
 * ```
 */
internal class NavigationDrawerItemView private constructor(props: Properties) :
    ComposableView<NavigationDrawerItemView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val onClick = props.onClick
        val shape = props.shape
        val selected = props.selected
        val colors = props.colors

        val ndiLabel = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }
        val ndiIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }
        val ndiBadge = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBadge }
        }

        NavigationDrawerItem(
            label = {
                ndiLabel?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            selected = selected,
            onClick = onClickFromString(pushEvent, onClick, props.commonProps.phxValue),
            icon = ndiIcon?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            badge = ndiBadge?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            shape = shape ?: CircleShape,
            colors = getNavigationDrawerItemColors(colors),
        )
    }

    @Composable
    private fun getNavigationDrawerItemColors(ndiColors: ImmutableMap<String, String>?): NavigationDrawerItemColors {
        val defaultValue = NavigationDrawerItemDefaults.colors()
        return if (ndiColors == null) {
            defaultValue
        } else {
            NavigationDrawerItemDefaults.colors(
                selectedContainerColor = ndiColors[colorAttrSelectedContainerColor]?.toColor()
                    ?: defaultValue.containerColor(true).value,
                unselectedContainerColor = ndiColors[colorAttrUnselectedContainerColor]?.toColor()
                    ?: defaultValue.containerColor(false).value,
                selectedIconColor = ndiColors[colorAttrSelectedIconColor]?.toColor()
                    ?: defaultValue.iconColor(true).value,
                unselectedIconColor = ndiColors[colorAttrUnselectedIconColor]?.toColor()
                    ?: defaultValue.iconColor(false).value,
                selectedTextColor = ndiColors[colorAttrSelectedTextColor]?.toColor()
                    ?: defaultValue.textColor(true).value,
                unselectedTextColor = ndiColors[colorAttrUnselectedTextColor]?.toColor()
                    ?: defaultValue.textColor(false).value,
                selectedBadgeColor = ndiColors[colorAttrSelectedBadgeColor]?.toColor()
                    ?: defaultValue.badgeColor(true).value,
                unselectedBadgeColor = ndiColors[colorAttrUnselectedBadgeColor]?.toColor()
                    ?: defaultValue.badgeColor(false).value,
            )
        }
    }

    @Stable
    internal data class Properties(
        val onClick: String = "",
        val shape: Shape? = null,
        val selected: Boolean = false,
        val colors: ImmutableMap<String, String>? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory :
        ComposableViewFactory<NavigationDrawerItemView>() {
        /**
         * Creates a `NavigationDrawerItemView` object based on the attributes of the input `Attributes`
         * object. NavigationDrawerItemView co-relates to the NavigationDrawerItem composable.
         * @param attributes the `Attributes` object to create the `NavigationDrawerItemView` object from
         * @return a `NavigationDrawerItemView` object based on the attributes of the input `Attributes`
         * object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): NavigationDrawerItemView = NavigationDrawerItemView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrColors -> colors(props, attribute.value)
                    attrPhxClick -> onClick(props, attribute.value)
                    attrSelected -> selected(props, attribute.value)
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
         * Sets the event name to be triggered on the server when the item is clicked.
         *
         * ```
         * <NavigationDrawerItem phx-click="yourServerEventHandler">...</NavigationDrawerItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Defines the shape of the item's container.
         *
         * ```
         * <NavigationDrawerItem shape="rectangle" >...</NavigationDrawerItem>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or an integer representing
         * the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationDrawerItem selected="true">...</NavigationDrawerItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }

        /**
         * Set Navigation Drawer Item colors.
         * ```
         * <NavigationDrawerItem
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         * ```
         * @param colors an JSON formatted string, containing the item colors. The color keys
         * supported are: `selectedContainerColor`, `unselectedContainerColor`, `selectedIconColor,
         * `unselectedIconColor`, `selectedTextColor`, `unselectedTextColor`, `selectedBadgeColor`,
         * and `unselectedBadgeColor`.
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }
    }
}
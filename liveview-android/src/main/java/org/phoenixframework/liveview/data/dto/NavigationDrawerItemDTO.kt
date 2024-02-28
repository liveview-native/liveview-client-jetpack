package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedBadgeColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedBadgeColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedTextColor
import org.phoenixframework.liveview.data.constants.Templates.templateBadge
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class NavigationDrawerItemDTO private constructor(builder: Builder) :
    ComposableView<NavigationDrawerItemDTO.Builder>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val onClick = builder.onClick
        val shape = builder.shape
        val selected = builder.selected
        val colors = builder.colors

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
            onClick = onClickFromString(pushEvent, onClick, phxValue),
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

    internal class Builder : ComposableBuilder() {
        var onClick: String = ""
            private set

        var shape: Shape? = null
            private set

        var selected: Boolean = false
            private set

        var colors: ImmutableMap<String, String>? = null
            private set

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         *
         * ```
         * <NavigationDrawerItem phx-click="yourServerEventHandler">...</NavigationDrawerItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Defines the shape of the item's container.
         *
         * ```
         * <NavigationDrawerItem shape="rectangle" >...</NavigationDrawerItem>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or an integer representing
         * the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationDrawerItem selected="true">...</NavigationDrawerItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
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
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        fun build() = NavigationDrawerItemDTO(this)
    }
}

internal object NavigationDrawerItemDtoFactory : ComposableViewFactory<NavigationDrawerItemDTO>() {
    /**
     * Creates a `NavigationDrawerItemDTO` object based on the attributes of the input `Attributes`
     * object. NavigationDrawerItemDTO co-relates to the NavigationDrawerItem composable.
     * @param attributes the `Attributes` object to create the `NavigationDrawerItemDTO` object from
     * @return a `NavigationDrawerItemDTO` object based on the attributes of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationDrawerItemDTO =
        attributes.fold(NavigationDrawerItemDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrColors -> builder.colors(attribute.value)
                attrPhxClick -> builder.onClick(attribute.value)
                attrSelected -> builder.selected(attribute.value)
                attrShape -> builder.shape(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationDrawerItemDTO.Builder
        }.build()
}
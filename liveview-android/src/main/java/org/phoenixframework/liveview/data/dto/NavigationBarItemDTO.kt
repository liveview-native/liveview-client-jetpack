package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrAlwaysShowLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.ColorAttrs
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

/**
 * Material Design navigation bar item.
 * Navigation bars offer a persistent and convenient way to switch between primary destinations in
 * an app.
 * This component can has two children templates:
 * - `label` for the item text.
 * - `icon` for the item icon.
 * ```
 * <NavigationBarItem selected="true" phx-click="selectTab1">
 *   <Icon imageVector="filled:HorizontalDistribute" template="icon"/>
 *   <Text template="label">Tab 1</Text>
 * </NavigationBarItem>
 * ```
 */
internal class NavigationBarItemDTO private constructor(builder: Builder) :
    ComposableView<NavigationBarItemDTO.Builder>(builder) {
    private val rowScope = builder.rowScope

    private val alwaysShowLabel = builder.alwaysShowLabel
    private val colors = builder.colors?.toImmutableMap()
    private val enabled = builder.enabled
    private val onClick = builder.onClick
    private val selected = builder.selected

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val icon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }

        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }

        rowScope.NavigationBarItem(
            selected = selected,
            onClick = onClick?.let { clickEventName ->
                onClickFromString(pushEvent, clickEventName, phxValue)
            } ?: {},
            icon = {
                icon?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = modifier,
            enabled = enabled,
            label = label?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            alwaysShowLabel = alwaysShowLabel,
            colors = getNavigationBarColors(colors),
        )
    }

    @Composable
    private fun getNavigationBarColors(colors: ImmutableMap<String, String>?): NavigationBarItemColors {
        val defaultColors = NavigationBarItemDefaults.colors()

        return if (colors == null) {
            defaultColors
        } else {
            return NavigationBarItemDefaults.colors(
                selectedIconColor = colors[ColorAttrs.colorAttrSelectedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSecondaryContainer,
                selectedTextColor = colors[ColorAttrs.colorAttrSelectedTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                indicatorColor = colors[ColorAttrs.colorAttrIndicatorColor]?.toColor()
                    ?: MaterialTheme.colorScheme.secondaryContainer,
                unselectedIconColor = colors[ColorAttrs.colorAttrUnselectedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = colors[ColorAttrs.colorAttrUnselectedTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledIconColor = colors[ColorAttrs.colorAttrDisabledIconColor]?.toColor() ?: (
                        colors[ColorAttrs.colorAttrUnselectedIconColor]?.toColor()
                            ?: MaterialTheme.colorScheme.onSurfaceVariant
                        ).copy(alpha = 0.38f),
                disabledTextColor = colors[ColorAttrs.colorAttrDisabledTextColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrUnselectedTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant).copy(alpha = 0.38f),
            )
        }
    }

    internal class Builder(val rowScope: RowScope) : ComposableBuilder() {
        var alwaysShowLabel: Boolean = true
            private set
        var colors: Map<String, String>? = null
            private set
        var enabled: Boolean = true
            private set
        var onClick: String? = null
            private set
        var selected: Boolean = false
            private set

        /**
         * Whether to always show the label for this item. If false, the label will only be shown
         * when this item is selected.
         * ```
         * <NavigationBarItem alwaysShowLabel="true">...</NavigationDrawerItem>
         * ```
         * @param alwaysShowLabel true if the label is always visible, false if it's only visible
         * when is selected.
         */
        fun alwaysShowLabel(alwaysShowLabel: String) = apply {
            this.alwaysShowLabel = alwaysShowLabel.toBoolean()
        }

        /**
         * Set Button colors.
         * ```
         * <NavigationBarItem
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         *   ...
         * </NavigationBarItem>
         * ```
         * @param colors an JSON formatted string, containing the navigation bar item colors. The
         * color keys supported are: `selectedIconColor`, `selectedTextColor`, `indicatorColor`,
         * `unselectedIconColor`, `unselectedTextColor`, `disabledIconColor`, and
         * `disabledTextColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <NavigationBarItem enabled="true">...</NavigationBarItem>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         *
         * ```
         * <NavigationBarItem phx-click="yourServerEventHandler">...</NavigationBarItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationBarItem selected="true">...</NavigationDrawerItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        fun build() = NavigationBarItemDTO(this)
    }
}

internal object NavigationBarItemDtoFactory : ComposableViewFactory<NavigationBarItemDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationBarItemDTO =
        attributes.fold(NavigationBarItemDTO.Builder(scope as RowScope)) { builder, attribute ->
            when (attribute.name) {
                attrAlwaysShowLabel -> builder.alwaysShowLabel(attribute.value)
                attrColors -> builder.colors(attribute.value)
                attrEnabled -> builder.enabled(attribute.value)
                attrPhxClick -> builder.onClick(attribute.value)
                attrSelected -> builder.selected(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationBarItemDTO.Builder
        }.build()
}
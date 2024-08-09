package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrAlwaysShowLabel
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.ColorAttrs
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
internal class NavigationBarItemView private constructor(props: Properties) :
    ComposableView<NavigationBarItemView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val rowScope = props.rowScope
        val alwaysShowLabel = props.alwaysShowLabel
        val colors = props.colors?.toImmutableMap()
        val enabled = props.enabled
        val onClick = props.onClick
        val selected = props.selected

        val icon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }

        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }

        rowScope.NavigationBarItem(
            selected = selected,
            onClick = onClick?.let { clickEventName ->
                onClickFromString(pushEvent, clickEventName, props.commonProps.phxValue)
            } ?: {},
            icon = {
                icon?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = props.commonProps.modifier,
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
                disabledIconColor = colors[ColorAttrs.colorAttrDisabledIconColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrUnselectedIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant).copy(alpha = 0.38f),
                disabledTextColor = colors[ColorAttrs.colorAttrDisabledTextColor]?.toColor()
                    ?: (colors[ColorAttrs.colorAttrUnselectedTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant).copy(alpha = 0.38f),
            )
        }
    }

    @Stable
    internal data class Properties(
        val rowScope: RowScope,
        var alwaysShowLabel: Boolean = true,
        var colors: ImmutableMap<String, String>? = null,
        var enabled: Boolean = true,
        var onClick: String? = null,
        var selected: Boolean = false,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<NavigationBarItemView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): NavigationBarItemView = NavigationBarItemView(
            attributes.fold(Properties(scope as RowScope)) { props, attribute ->
                when (attribute.name) {
                    attrAlwaysShowLabel -> alwaysShowLabel(props, attribute.value)
                    attrColors -> colors(props, attribute.value)
                    attrEnabled -> enabled(props, attribute.value)
                    attrPhxClick -> onClick(props, attribute.value)
                    attrSelected -> selected(props, attribute.value)
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
         * Whether to always show the label for this item. If false, the label will only be shown
         * when this item is selected.
         * ```
         * <NavigationBarItem alwaysShowLabel="true">...</NavigationDrawerItem>
         * ```
         * @param alwaysShowLabel true if the label is always visible, false if it's only visible
         * when is selected.
         */
        private fun alwaysShowLabel(props: Properties, alwaysShowLabel: String): Properties {
            return props.copy(alwaysShowLabel = alwaysShowLabel.toBoolean())
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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <NavigationBarItem enabled="true">...</NavigationBarItem>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         *
         * ```
         * <NavigationBarItem phx-click="yourServerEventHandler">...</NavigationBarItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationBarItem selected="true">...</NavigationDrawerItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
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
import org.phoenixframework.liveview.constants.Templates
import org.phoenixframework.liveview.constants.Templates.templateIcon
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
 * Material Design navigation rail item.
 * A NavigationRailItem represents a destination within a NavigationRail. Navigation rails provide
 * access to primary destinations in apps when using tablet and desktop screens.
 * The text label is always shown (if it exists) when selected. Showing text labels if not selected
 * is controlled by alwaysShowLabel.
 * ```
 * <NavigationRail>
 *   <NavigationRailItem selected="true" alwaysShowLabel="false">
 *     <Icon imageVector="filled:Favorite" template="icon"/>
 *     <Text template="label">Favorites</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false" alwaysShowLabel="false">
 *     <Icon imageVector="filled:Home" template="icon"/>
 *     <Text template="label">Home</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="true" enabled="false" alwaysShowLabel="false">
 *     <Icon imageVector="filled:Person" template="icon"/>
 *     <Text template="label">Person</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false" enabled="false" alwaysShowLabel="false">
 *     <Icon imageVector="filled:Settings" template="icon"/>
 *     <Text template="label">Settings</Text>
 *   </NavigationRailItem>
 * </NavigationRail>
 * ```
 */
internal class NavigationRailItemView private constructor(props: Properties) :
    ComposableView<NavigationRailItemView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val alwaysShowLabel = props.alwaysShowLabel
        val colors = props.colors
        val enabled = props.enabled
        val onClick = props.onClick
        val selected = props.selected

        val nriIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }
        val nriLabel = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateLabel }
        }
        NavigationRailItem(
            selected = selected,
            onClick = onClickFromString(pushEvent, onClick, props.commonProps.phxValue),
            icon = {
                nriIcon?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = props.commonProps.modifier,
            enabled = enabled,
            label = nriLabel?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            alwaysShowLabel = alwaysShowLabel,
            colors = getNavigationRailItemColors(colors),
            //TODO interactionSource: MutableInteractionSource
        )
    }

    @Composable
    private fun getNavigationRailItemColors(colors: ImmutableMap<String, String>?): NavigationRailItemColors {
        val defaultColors = NavigationRailItemDefaults.colors()

        return if (colors == null) {
            defaultColors
        } else {
            return NavigationRailItemDefaults.colors(
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

    @Stable
    internal data class Properties(
        val alwaysShowLabel: Boolean = true,
        val colors: ImmutableMap<String, String>? = null,
        val enabled: Boolean = true,
        val onClick: String = "",
        val selected: Boolean = false,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory :
        ComposableViewFactory<NavigationRailItemView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): NavigationRailItemView = NavigationRailItemView(
            attributes.fold(Properties()) { props, attribute ->
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
         * Controls the enabled state of this item. When false, this component will not respond to
         * user input, and it will appear visually disabled and disabled to accessibility services.
         * ```
         * <NavigationRailItem enabled="false">...</NavigationRailItem>
         * ```
         * @param enabled true if the item is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        /**
         * Whether to always show the label for this item. If false, the label will only be shown
         * when this item is selected.
         * ```
         * <NavigationRailItem alwaysShowLabel="true">...</NavigationRailItem>
         * ```
         * @param alwaysShowLabel true if the label is always visible, false if it's only visible
         * when is selected.
         */
        private fun alwaysShowLabel(props: Properties, alwaysShowLabel: String): Properties {
            return props.copy(alwaysShowLabel = alwaysShowLabel.toBoolean())
        }

        /**
         * Colors that will be used to resolve the colors used for this item in different states.
         * ```
         * <NavigationRailItem
         *   colors="{'selectedIconColor': '#FFFF0000', 'selectedTextColor': '#FF00FF00'}">
         *   ...
         * </NavigationRailItem>
         * ```
         * @param colors an JSON formatted string, containing the navigation rail item colors. The
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
         * Sets the event name to be triggered on the server when the item is clicked.
         * ```
         * <NavigationRailItem phx-click="yourServerEventHandler">...</NavigationRailItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationRailItem selected="true">...</NavigationRailItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }
    }
}
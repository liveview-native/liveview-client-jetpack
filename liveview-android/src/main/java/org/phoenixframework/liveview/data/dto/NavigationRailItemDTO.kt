package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.alwaysShowLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.ColorAttrs
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design navigation rail item.
 * A NavigationRailItem represents a destination within a NavigationRail. Navigation rails provide
 * access to primary destinations in apps when using tablet and desktop screens.
 * The text label is always shown (if it exists) when selected. Showing text labels if not selected
 * is controlled by alwaysShowLabel.
 * ```
 * <NavigationRail>
 *   <NavigationRailItem selected="true" always-show-label="false">
 *     <Icon image-vector="filled:Favorite" template="icon"/>
 *     <Text template="label">Favorites</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false" always-show-label="false">
 *     <Icon image-vector="filled:Home" template="icon"/>
 *     <Text template="label">Home</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="true" enabled="false" always-show-label="false">
 *     <Icon image-vector="filled:Person" template="icon"/>
 *     <Text template="label">Person</Text>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false" enabled="false" always-show-label="false">
 *     <Icon image-vector="filled:Settings" template="icon"/>
 *     <Text template="label">Settings</Text>
 *   </NavigationRailItem>
 * </NavigationRail>
 * ```
 */
internal class NavigationRailItemDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val alwaysShowLabel = builder.alwaysShowLabel
    private val colors = builder.colors?.toImmutableMap()
    private val enabled = builder.enabled
    private val onClick = builder.onClick
    private val selected = builder.selected
    private val value = builder.value

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val nriIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }
        val nriLabel = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateLabel }
        }
        NavigationRailItem(
            selected = selected,
            onClick = onClickFromString(pushEvent, onClick, value?.toString() ?: ""),
            icon = {
                nriIcon?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = modifier,
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

    internal class Builder : ComposableBuilder() {
        var alwaysShowLabel: Boolean = true
            private set
        var colors: Map<String, String>? = null
            private set
        var enabled: Boolean = true
            private set
        var onClick: String = ""
            private set
        var selected: Boolean = false
            private set

        /**
         * Controls the enabled state of this item. When false, this component will not respond to
         * user input, and it will appear visually disabled and disabled to accessibility services.
         * ```
         * <NavigationRailItem enabled="false">...</NavigationRailItem>
         * ```
         * @param enabled true if the item is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Whether to always show the label for this item. If false, the label will only be shown
         * when this item is selected.
         * ```
         * <NavigationRailItem always-show-label="true">...</NavigationRailItem>
         * ```
         * @param alwaysShowLabel true if the label is always visible, false if it's only visible
         * when is selected.
         */
        fun alwaysShowLabel(alwaysShowLabel: String) = apply {
            this.alwaysShowLabel = alwaysShowLabel.toBoolean()
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
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         * ```
         * <NavigationRailItem phx-click="yourServerEventHandler">...</NavigationRailItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * Whether this item is selected or not.
         * ```
         * <NavigationRailItem selected="true">...</NavigationRailItem>
         * ```
         * @param selected true if the item is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        fun build() = NavigationRailItemDTO(this)
    }
}

internal object NavigationRailItemDtoFactory :
    ComposableViewFactory<NavigationRailItemDTO, NavigationRailItemDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationRailItemDTO =
        attributes.fold(NavigationRailItemDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                alwaysShowLabel -> builder.alwaysShowLabel(attribute.value)
                attrColors -> builder.colors(attribute.value)
                attrEnabled -> builder.enabled(attribute.value)
                attrPhxClick -> builder.onClick(attribute.value)
                attrSelected -> builder.selected(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationRailItemDTO.Builder
        }.build()
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
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

/**
 * Material Design dropdown menu item.
 * Menus display a list of choices on a temporary surface. The children can use the following
 * templates:
 * - `leading-icon`: optional leading icon to be displayed at the beginning of the item's label.
 * - `trailing-icon`: optional trailing icon to be displayed at the end of the item's text. This
 * - usually, the text of the menu item, but can be any composable and no template should be
 * assigned.
 * trailing icon slot can also accept Text to indicate a keyboard shortcut.
 * ```
 * <DropdownMenuItem phx-click="setDDOption" phx-value="A">
 *   <Text>Option A</Text>
 *   <Icon imageVector="filled:Add" template="trailing-icon" />
 *   <Icon imageVector="filled:ChevronLeft" template="leading-icon"/>
 * </DropdownMenuItem>
 * ```
 */
internal class DropdownMenuItemView private constructor(props: Properties) :
    ComposableView<DropdownMenuItemView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val event = props.onClick
        val enabled = props.enabled
        val colors = props.colors
        val contentPadding = props.contentPadding

        val textChild = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == null }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }
        val onItemSelected = LocalDropdownMenuBoxOnItemSelectedAction.current
        DropdownMenuItem(
            text = {
                textChild?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            onClick = {
                onClickFromString(pushEvent, event, props.commonProps.phxValue).invoke()
                onItemSelected(props.commonProps.phxValue)
            },
            modifier = props.commonProps.modifier,
            leadingIcon = leadingIcon?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            trailingIcon = trailingIcon?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            enabled = enabled,
            colors = getMenuItemColors(colors),
            contentPadding = contentPadding ?: MenuDefaults.DropdownMenuItemContentPadding,
        )
    }

    @Composable
    private fun getMenuItemColors(colors: Map<String, String>?): MenuItemColors {
        val defaultColors = MenuDefaults.itemColors()

        return if (colors == null) {
            defaultColors
        } else {
            MenuDefaults.itemColors(
                textColor = colors[colorAttrTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                leadingIconColor = colors[colorAttrLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                trailingIconColor = colors[colorAttrTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = colors[colorAttrDisabledTextColor]?.toColor()
                    ?: colors[colorAttrTextColor]?.toColor()?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: colors[colorAttrLeadingIconColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: colors[colorAttrTrailingIconColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Stable
    internal data class Properties(
        val onClick: String = "",
        val enabled: Boolean = true,
        val colors: ImmutableMap<String, String>? = null,
        val contentPadding: PaddingValues? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<DropdownMenuItemView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): DropdownMenuItemView = DropdownMenuItemView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrColors -> colors(props, attribute.value)
                    attrContentPadding -> contentPadding(props, attribute.value)
                    attrEnabled -> enabled(props, attribute.value)
                    attrPhxClick -> onClick(props, attribute.value)
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
         * <DropdownMenuItem phx-click="yourServerEventHandler">...</DropdownMenuItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <DropdownMenuItem enabled="true">...</DropdownMenuItem>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        /**
         * Set DropdownMenuItem colors.
         * ```
         * <DropdownMenuItem
         *   colors="{'textColor': '#FFFF0000', 'trailingIconColor': '#FF00FF00'}">
         * />
         * ```
         * @param colors an JSON formatted string, containing the button colors. The color keys
         * supported are: `textColor`, `leadingIconColor`, `trailingIconColor, `disabledTextColor`,
         * `disabledLeadingIconColor`, and `disabledTrailingIconColor`.
         */
        fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                return props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * A padding to be applied to the four edges of the item.
         *
         * @param padding int value for padding to be applied to the item.
         */
        fun contentPadding(props: Properties, padding: String): Properties {
            return if (padding.isNotEmptyAndIsDigitsOnly()) {
                return props.copy(contentPadding = PaddingValues(padding.toInt().dp))
            } else props
        }
    }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

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
 *   <Icon image-vector="filled:Add" template="trailing-icon" />
 *   <Icon image-vector="filled:ChevronLeft" template="leading-icon"/>
 * </DropdownMenuItem>
 * ```
 */
internal class DropdownMenuItemDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val event = builder.clickEventName
    private val value = builder.value
    private val enabled = builder.enabled
    private val colors = builder.colors?.toImmutableMap()
    private val contentPadding = builder.contentPadding

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val textChild = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == null }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }
        DropdownMenuItem(
            text = {
                textChild?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            onClick = {
                pushEvent.invoke(ComposableBuilder.EVENT_TYPE_CLICK, event, value, null)
            },
            modifier = modifier,
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
                    ?: colors[colorAttrTextColor]?.toColor()?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: colors[colorAttrLeadingIconColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: colors[colorAttrTrailingIconColor]?.toColor()
                        ?.copy(alpha = disabledContentAlpha)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var value: String = ""
            private set
        var clickEventName: String = ""
            private set
        var enabled: Boolean = true
            private set
        var colors: Map<String, String>? = null
            private set
        var contentPadding: PaddingValues? = null
            private set

        /**
         * Sets the component value. This value will be send to the server when the item is clicked.
         * ```
         * <DropdownMenuItem phx-value="foo">...</DropdownMenuItem>
         * ```
         * @param value component's value.
         */
        fun value(value: String) = apply {
            this.value = value
        }

        /**
         * Sets the event name to be triggered on the server when the item is clicked.
         *
         * ```
         * <DropdownMenuItem phx-click="yourServerEventHandler">...</DropdownMenuItem>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun clickEventName(event: String) = apply {
            this.clickEventName = event
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <DropdownMenuItem enabled="true">...</DropdownMenuItem>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
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
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * A padding to be applied to the four edges of the item.
         *
         * @param padding int value for padding to be applied to the item.
         */
        fun contentPadding(padding: String) = apply {
            if (padding.isNotEmptyAndIsDigitsOnly()) {
                this.contentPadding = PaddingValues(padding.toInt().dp)
            }
        }

        fun build() = DropdownMenuItemDTO(this)
    }
}

internal object DropdownMenuItemDtoFactory :
    ComposableViewFactory<DropdownMenuItemDTO, DropdownMenuItemDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DropdownMenuItemDTO = attributes.fold(DropdownMenuItemDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColors -> builder.colors(attribute.value)
            attrContentPadding -> builder.contentPadding(attribute.value)
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.clickEventName(attribute.value)
            attrPhxValue -> builder.value(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as DropdownMenuItemDTO.Builder
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.ATTR_CLICK
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design dropdown menu item.
 * Menus display a list of choices on a temporary surface. The children can use the following
 * templates:
 * - `leadingIcon`: optional leading icon to be displayed at the beginning of the item's label.
 * - `trailingIcon`: optional trailing icon to be displayed at the end of the item's text. This
 * - usually, the text of the menu item, but can be any composable and no template should be
 * assigned.
 * trailing icon slot can also accept Text to indicate a keyboard shortcut.
 * ```
 * <DropDownMenuItem phx-click="setDDOption" value="A">
 *   <Text>Option A</Text>
 *   <Icon imageVector="filled:Add" template="trailingIcon" />
 *   <Icon imageVector="filled:ChevronLeft" template="leadingIcon"/>
 * </DropDownMenuItem>
 * ```
 */
internal class DropDownMenuItemDTO private constructor(builder: Builder) :
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
            composableNode?.children?.find { it.node?.template == DropDownMenuItemDtoFactory.leadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == DropDownMenuItemDtoFactory.trailingIcon }
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
            fun value(key: String) =
                colors[key]?.toColor() ?: Color(defaultColors.privateField(key))

            MenuDefaults.itemColors(
                textColor = value("textColor"),
                leadingIconColor = value("leadingIconColor"),
                trailingIconColor = value("trailingIconColor"),
                disabledTextColor = value("disabledTextColor"),
                disabledLeadingIconColor = value("disabledLeadingIconColor"),
                disabledTrailingIconColor = value("disabledTrailingIconColor"),
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
         * <DropDownMenuItem value="foo">...</DropDownMenuItem>
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
         * <DropDownMenuItem phx-click="yourServerEventHandler">...</DropDownMenuItem>
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
         * <DropDownMenuItem enabled="true">...</DropDownMenuItem>
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * Set DropDownMenuItem colors.
         * ```
         * <DropDownMenuItem
         *   colors="{'textColor': '#FFFF0000', 'trailingIconColor': '#FF00FF00'}">
         * />
         * ```
         * @param colors an JSON formatted string, containing the button colors. The color keys
         * supported are: `textColor`, `leadingIconColor`, `trailingIconColor, `disabledTextColor`,
         * `disabledLeadingIconColor`, and `disabledTrailingIconColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

        fun build() = DropDownMenuItemDTO(this)
    }
}

internal object DropDownMenuItemDtoFactory :
    ComposableViewFactory<DropDownMenuItemDTO, DropDownMenuItemDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DropDownMenuItemDTO = attributes.fold(DropDownMenuItemDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            ATTR_CLICK -> builder.clickEventName(attribute.value)
            "enabled" -> builder.enabled(attribute.value)
            "value" -> builder.value(attribute.value)
            "contentPadding" -> builder.contentPadding(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as DropDownMenuItemDTO.Builder
    }.build()

    internal const val leadingIcon = TextFieldDtoFactory.leadingIcon
    internal const val trailingIcon = TextFieldDtoFactory.trailingIcon
}
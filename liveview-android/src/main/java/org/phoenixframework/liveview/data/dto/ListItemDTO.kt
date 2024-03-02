package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledHeadlineColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrHeadlineColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrOverlineColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSupportingColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.Templates.templateHeadlineContent
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingContent
import org.phoenixframework.liveview.data.constants.Templates.templateOverlineContent
import org.phoenixframework.liveview.data.constants.Templates.templateSupportingContent
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingContent
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design list item.
 * This component can be used to achieve the list item templates existing in the spec.
 * One-line list items have a singular line of headline content.
 * Two-line list items additionally have either supporting or overline content.
 * Three-line list items have either both supporting and overline content, or extended (two-line)
 * supporting text.
 * Only the `headlineContent` template is required.
 * ```
 * <ListItem>
 *   <Text template="headlineContent">Headline</Text>
 *   <Text template="overlineContent">Overline Content</Text>
 *   <Text template="supportingContent">Supporting Content</Text>
 *   <Icon template="leadingContent" imageVector="filled:Add" />
 *   <Icon template="trailingContent" imageVector="filled:ChevronRight" />
 * </ListItem>
 * ```
 */
internal class ListItemDTO private constructor(props: Properties) :
    ComposableView<ListItemDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors
        val tonalElevation = props.tonalElevation
        val shadowElevation = props.shadowElevation

        val headlineContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateHeadlineContent }
        }
        val overlineContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateOverlineContent }
        }
        val supportingContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateSupportingContent }
        }
        val leadingContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingContent }
        }
        val trailingContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingContent }
        }

        ListItem(
            headlineContent = {
                headlineContent?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            modifier = props.commonProps.modifier,
            overlineContent = overlineContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            supportingContent = supportingContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            leadingContent = leadingContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            trailingContent = trailingContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            colors = getListColors(colors),
            tonalElevation = tonalElevation ?: ListItemDefaults.Elevation,
            shadowElevation = shadowElevation ?: ListItemDefaults.Elevation,
        )
    }

    @Composable
    private fun getListColors(colors: ImmutableMap<String, String>?): ListItemColors {
        val defaultValue = ListItemDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            ListItemDefaults.colors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                headlineColor = colors[colorAttrHeadlineColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                leadingIconColor = colors[colorAttrLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                overlineColor = colors[colorAttrOverlineColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                supportingColor = colors[colorAttrSupportingColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                trailingIconColor = colors[colorAttrTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledHeadlineColor = colors[colorAttrDisabledHeadlineColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledLeadingIconColor = colors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                disabledTrailingIconColor = colors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            )
        }
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        val tonalElevation: Dp? = null,
        val shadowElevation: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var colors: ImmutableMap<String, String>? = null
        private var tonalElevation: Dp? = null
        private var shadowElevation: Dp? = null

        /**
         * Set ListItem colors.
         * ```
         * <ListItem
         *   colors="{'containerColor': '#FFFF0000', 'titleContentColor': '#FF00FF00'}">
         *   ...
         * </ListItem>
         * ```
         * @param colors an JSON formatted string, containing the list item colors. The color keys
         * supported are: `containerColor`, `headlineColor`, `leadingIconColor, `overlineColor`,
         * `supportingColor`, `trailingIconColor`, `disabledHeadlineColor`,
         * `disabledLeadingIconColor`, and `disabledTrailingIconColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        /**
         * The shadow elevation of this list item.
         * ```
         * <ListItem shadowElevation="8">...</ListItem>
         * ```
         * @param shadowElevation shadow elevation of this list item.
         */
        fun shadowElevation(shadowElevation: String) = apply {
            if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                this.shadowElevation = shadowElevation.toInt().dp
            }
        }

        /**
         * The tonal elevation of this list item
         * ```
         * <ListItem tonalElevation="12" >...</ListItem>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        fun build() = ListItemDTO(
            Properties(
                colors,
                tonalElevation,
                shadowElevation,
                commonProps,
            )
        )
    }
}

internal object ListItemDtoFactory : ComposableViewFactory<ListItemDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ListItemDTO = attributes.fold(ListItemDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColors -> builder.colors(attribute.value)
            attrTonalElevation -> builder.tonalElevation(attribute.value)
            attrShadowElevation -> builder.shadowElevation(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ListItemDTO.Builder
    }.build()
}
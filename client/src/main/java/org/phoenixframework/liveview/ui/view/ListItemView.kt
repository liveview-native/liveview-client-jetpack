package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledHeadlineColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrHeadlineColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrOverlineColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSupportingColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.constants.Templates.templateHeadlineContent
import org.phoenixframework.liveview.constants.Templates.templateLeadingContent
import org.phoenixframework.liveview.constants.Templates.templateOverlineContent
import org.phoenixframework.liveview.constants.Templates.templateSupportingContent
import org.phoenixframework.liveview.constants.Templates.templateTrailingContent
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
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
internal class ListItemView private constructor(props: Properties) :
    ComposableView<ListItemView.Properties>(props) {

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


    internal object Factory : ComposableViewFactory<ListItemView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): ListItemView = ListItemView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrColors -> colors(props, attribute.value)
                attrTonalElevation -> tonalElevation(props, attribute.value)
                attrShadowElevation -> shadowElevation(props, attribute.value)
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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * The shadow elevation of this list item.
         * ```
         * <ListItem shadowElevation="8">...</ListItem>
         * ```
         * @param shadowElevation shadow elevation of this list item.
         */
        private fun shadowElevation(props: Properties, shadowElevation: String): Properties {
            return if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(shadowElevation = shadowElevation.toInt().dp)
            } else props
        }

        /**
         * The tonal elevation of this list item
         * ```
         * <ListItem tonalElevation="12" >...</ListItem>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }
    }
}
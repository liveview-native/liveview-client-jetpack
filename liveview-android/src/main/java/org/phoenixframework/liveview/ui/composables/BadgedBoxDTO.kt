package org.phoenixframework.liveview.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateBadge
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design badge box.
 * It must be at most two children: the one which will appear on the top-right must use the `badge`
 * template; and the other child can be any component, but usually it is an `Icon` like below.
 * ```
 * <BadgedBox>
 *   <Text template="badge">+99</Text>
 *   <Icon imageVector="filled:Add" />
 * </BadgedBox>
 * ```
 */
internal class BadgedBoxDTO private constructor(props: Properties) :
    ComposableView<BadgedBoxDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val contentColor = props.contentColor
        val containerColor = props.containerColor

        val badge = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBadge }
        }
        val contentChild = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != templateBadge }
        }
        BadgedBox(
            badge = {
                badge?.let {
                    Badge(
                        containerColor = containerColor ?: BadgeDefaults.containerColor,
                        contentColor = contentColor
                            ?: contentColorFor(BadgeDefaults.containerColor),
                    ) {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            },
            modifier = props.commonProps.modifier,
        ) {
            contentChild?.let {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val containerColor: Color?,
        val contentColor: Color?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties


    internal class Builder : ComposableBuilder() {
        private var containerColor: Color? = null
        private var contentColor: Color? = null

        /**
         * The color used for the background of the BadgedBox.
         *
         * ```
         * <BadgedBox containerColor="#FF0000FF">
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside the BadgedBox.
         *
         * ```
         * <BadgedBox contentColor="#FF0000FF" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        fun build() = BadgedBoxDTO(Properties(containerColor, contentColor, commonProps))
    }
}

internal object BadgedBoxDtoFactory : ComposableViewFactory<BadgedBoxDTO>() {
    /**
     * Creates a `BadgedBoxDTO` object based on the attributes of the input `Attributes` object.
     * BadgedBoxDTO co-relates to the BadgedBox composable
     * @param attributes the `Attributes` object to create the `BadgedBoxDTO` object from
     * @return a `BadgedBoxDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): BadgedBoxDTO = attributes.fold(BadgedBoxDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as BadgedBoxDTO.Builder
    }.build()
}
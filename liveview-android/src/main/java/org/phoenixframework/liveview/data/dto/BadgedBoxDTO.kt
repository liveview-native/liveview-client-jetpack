package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateBadge
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class BadgedBoxDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val contentColor = builder.contentColor
    private val containerColor = builder.containerColor

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
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
            modifier = modifier,
        ) {
            contentChild?.let {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set

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

        fun build() = BadgedBoxDTO(this)
    }
}

internal object BadgedBoxDtoFactory : ComposableViewFactory<BadgedBoxDTO, BadgedBoxDTO.Builder>() {
    /**
     * Creates a `BadgedBoxDTO` object based on the attributes of the input `Attributes` object.
     * BadgedBoxDTO co-relates to the BadgedBox composable
     * @param attributes the `Attributes` object to create the `BadgedBoxDTO` object from
     * @return a `BadgedBoxDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
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
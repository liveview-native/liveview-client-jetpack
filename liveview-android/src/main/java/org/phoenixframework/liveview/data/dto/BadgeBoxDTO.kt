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
 * It must be at most two children: a `Badge` and the content component, that can be any component.
 * Usually, the content is an `Icon` like below.
 * ```
 * <BadgeBox>
 *   <Badge><Text>+99</Text></Badge>
 *   <Icon imageVector="filled:Add" />
 * </BadgeBox>
 * ```
 */
internal class BadgeBoxDTO private constructor(builder: Builder) :
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
            composableNode?.children?.find { it.node?.tag == BadgeBoxDtoFactory.badge }
        }
        val contentChild = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag != BadgeBoxDtoFactory.badge }
        }
        BadgedBox(
            badge = {
                badge?.let {
                    Badge(
                        containerColor = containerColor ?: BadgeDefaults.containerColor,
                        contentColor = contentColor
                            ?: contentColorFor(BadgeDefaults.containerColor),
                    ) {
                        PhxLiveView(it, pushEvent, composableNode, null, scope = this)
                    }
                }
            },
            modifier = modifier,
        ) {
            contentChild?.let {
                PhxLiveView(it, pushEvent, composableNode, null)
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set

        /**
         * The color used for the background of the BadgeBox.
         *
         * ```
         * <BadgeBox containerColor="#FF0000FF">
         * ```
         * @param containerColor the background color in AARRGGBB format.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside the BadgeBox.
         *
         * ```
         * <BadgeBox contentColor="#FF0000FF" />
         * ```
         * @param contentColor the content color in AARRGGBB format.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        fun build() = BadgeBoxDTO(this)
    }
}

internal object BadgeBoxDtoFactory : ComposableViewFactory<BadgeBoxDTO, BadgeBoxDTO.Builder>() {
    /**
     * Creates a `BadgeBoxDTO` object based on the attributes of the input `Attributes` object.
     * BadgeBoxDTO co-relates to the BadgeBox composable
     * @param attributes the `Attributes` object to create the `BadgeBoxDTO` object from
     * @return a `BadgeBoxDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): BadgeBoxDTO = attributes.fold(BadgeBoxDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "containerColor" -> builder.containerColor(attribute.value)
            "contentColor" -> builder.contentColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as BadgeBoxDTO.Builder
    }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*, *>> {
        return mapOf(
            badge to BoxDtoFactory
        )
    }

    const val badge = "Badge"
}
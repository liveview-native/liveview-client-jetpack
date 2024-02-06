package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.Templates.templateHeader
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design bottom navigation rail. Navigation rails provide access to primary destinations
 * in apps when using tablet and desktop screens.
 * ```
 * <NavigationRail>
 *   <NavigationRailItem selected="true">
 *     <Icon image-vector="filled:Favorite" template="icon"/>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false">
 *     <Icon image-vector="filled:Home" template="icon"/>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false">
 *     <Icon image-vector="filled:Person" template="icon"/>
 *   </NavigationRailItem>
 * </NavigationRail>
 * ```
 */
internal class NavigationRailDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val containerColor = builder.containerColor
    private val contentColor = builder.contentColor
    private val windowsInsets = builder.windowInsets

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val header = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateHeader }
                .takeIf { it?.isNotEmpty() == true }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template.isNullOrEmpty() }
        }
        val containerColor = containerColor ?: NavigationRailDefaults.ContainerColor
        NavigationRail(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            header = header?.let { headerNodes ->
                {
                    headerNodes.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            },
            windowInsets = windowsInsets ?: NavigationRailDefaults.windowInsets,
            content = {
                content?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            },
        )
    }

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var windowInsets: WindowInsets? = null
            private set

        /**
         * The color used for the background of this navigation rail.
         * ```
         * <NavigationRail container-color="system-blue">...</NavigationRail>
         * ```
         * @param color container color in AARRGGBB format or one of the `system-*` colors.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * The preferred color for content inside this navigation rail.
         * ```
         * <NavigationRail content-color="system-yellow">...</NavigationRail>
         * ```
         * @param color content color in AARRGGBB format or one of the `system-*` colors.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * A window insets of the navigation rail.
         * ```
         * <NavigationRail window-insets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(insets: String) = apply {
            try {
                this.windowInsets = windowInsetsFromString(insets)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun build() = NavigationRailDTO(this)
    }
}

internal object NavigationRailDtoFactory :
    ComposableViewFactory<NavigationRailDTO, NavigationRailDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationRailDTO =
        attributes.fold(NavigationRailDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrContentColor -> builder.contentColor(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationRailDTO.Builder
        }.build()
}
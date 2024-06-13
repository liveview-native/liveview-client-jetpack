package org.phoenixframework.liveview.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.Templates.templateHeader
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
 * Material Design bottom navigation rail. Navigation rails provide access to primary destinations
 * in apps when using tablet and desktop screens.
 * ```
 * <NavigationRail>
 *   <NavigationRailItem selected="true">
 *     <Icon imageVector="filled:Favorite" template="icon"/>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false">
 *     <Icon imageVector="filled:Home" template="icon"/>
 *   </NavigationRailItem>
 *   <NavigationRailItem selected="false">
 *     <Icon imageVector="filled:Person" template="icon"/>
 *   </NavigationRailItem>
 * </NavigationRail>
 * ```
 */
internal class NavigationRailDTO private constructor(props: Properties) :
    ComposableView<NavigationRailDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val containerColorValue = props.containerColor
        val contentColor = props.contentColor
        val windowsInsets = props.windowInsets

        val header = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateHeader }
                .takeIf { it?.isNotEmpty() == true }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template.isNullOrEmpty() }
        }
        val containerColor = containerColorValue ?: NavigationRailDefaults.ContainerColor
        NavigationRail(
            modifier = props.commonProps.modifier,
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

    @Stable
    internal data class Properties(
        val containerColor: Color?,
        val contentColor: Color?,
        val windowInsets: WindowInsets?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var windowInsets: WindowInsets? = null

        /**
         * The color used for the background of this navigation rail.
         * ```
         * <NavigationRail containerColor="Blue">...</NavigationRail>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * The preferred color for content inside this navigation rail.
         * ```
         * <NavigationRail contentColor="Yellow">...</NavigationRail>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * A window insets of the navigation rail.
         * ```
         * <NavigationRail windowInsets="{'bottom': '100'}" >
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

        fun build() = NavigationRailDTO(
            Properties(
                containerColor,
                contentColor,
                windowInsets,
                commonProps,
            )
        )
    }
}

internal object NavigationRailDtoFactory : ComposableViewFactory<NavigationRailDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
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
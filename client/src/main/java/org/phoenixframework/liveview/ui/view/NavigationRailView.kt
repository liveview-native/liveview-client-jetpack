package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.Templates.templateHeader
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
internal class NavigationRailView private constructor(props: Properties) :
    ComposableView<NavigationRailView.Properties>(props) {

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
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val windowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<NavigationRailView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): NavigationRailView = NavigationRailView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrContainerColor -> containerColor(props, attribute.value)
                    attrContentColor -> contentColor(props, attribute.value)
                    attrWindowInsets -> windowInsets(props, attribute.value)
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
         * The color used for the background of this navigation rail.
         * ```
         * <NavigationRail containerColor="Blue">...</NavigationRail>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(containerColor = color.toColor())
            } else props
        }

        /**
         * The preferred color for content inside this navigation rail.
         * ```
         * <NavigationRail contentColor="Yellow">...</NavigationRail>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(contentColor = color.toColor())
            } else props
        }

        /**
         * A window insets of the navigation rail.
         * ```
         * <NavigationRail windowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        private fun windowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(windowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }
    }
}
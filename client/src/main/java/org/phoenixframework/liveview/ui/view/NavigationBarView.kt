package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.ComposableTypes.navigationBarItem
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
 * Material Design bottom navigation bar.
 * Usually is declared inside of a Scaffold using `bottomBar` template.
 * ```
 * <NavigationBar template="bottomBar">
 *   <NavigationBarItem selected={"#{@selectedTab == "0"}"} phx-click="selectTab" phx-value="0">
 *     <Icon imageVector="filled:HorizontalDistribute" template="icon"/>
 *     <Text template="label">Tab 1</Text>
 *   </NavigationBarItem>
 *   ...
 * </NavigationBar>
 * ```
 */
internal class NavigationBarView private constructor(props: Properties) :
    ComposableView<NavigationBarView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val containerColorValue = props.containerColor
        val contentColor = props.contentColor
        val tonalElevation = props.tonalElevation
        val windowsInsets = props.windowInsets

        val containerColor = containerColorValue ?: NavigationBarDefaults.containerColor
        NavigationBar(
            modifier = props.commonProps.modifier,
            containerColor = containerColor,
            contentColor = contentColor
                ?: MaterialTheme.colorScheme.contentColorFor(containerColor),
            tonalElevation = tonalElevation ?: NavigationBarDefaults.Elevation,
            windowInsets = windowsInsets ?: NavigationBarDefaults.windowInsets,
            content = {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    @Stable
    internal data class Properties(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val tonalElevation: Dp? = null,
        val windowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<NavigationBarView>() {
        /**
         * Creates a `NavigationBarView` object based on the attributes of the input `Attributes` object.
         * NavigationBarView co-relates to the NavigationBar.
         * @param attributes the `Attributes` object to create the `NavigationBarView` object from
         * @return a `NavigationBarView` object based on the attributes of the input `Attributes` object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): NavigationBarView = NavigationBarView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrContainerColor -> containerColor(props, attribute.value)
                    attrContentColor -> contentColor(props, attribute.value)
                    attrTonalElevation -> tonalElevation(props, attribute.value)
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

        override fun subTags(): Map<String, ComposableViewFactory<*>> {
            return mapOf(
                navigationBarItem to NavigationBarItemView.Factory
            )
        }

        /**
         * The color used for the background of this navigation bar.
         * ```
         * <NavigationBar containerColor="#FFFFFF00">...</NavigationBar>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return props.copy(containerColor = color.toColor())
        }

        /**
         * The preferred color for content inside this navigation bar.
         * ```
         * <NavigationBar contentColor="#FFCCCCCC">...</NavigationBar>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return props.copy(contentColor = color.toColor())
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <NavigationBar tonalElevation="24">...</NavigationBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Window insets to be passed to the navigation bar window via PaddingValues params.
         * ```
         * <ModalBottomSheet windowInsets="{'bottom': '100'}" >
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
package org.phoenixframework.liveview.ui.composables

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
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationBarItem
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
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
internal class NavigationBarDTO private constructor(props: Properties) :
    ComposableView<NavigationBarDTO.Properties>(props) {

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
        val containerColor: Color?,
        val contentColor: Color?,
        val tonalElevation: Dp?,
        val windowInsets: WindowInsets?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var tonalElevation: Dp? = null
        private var windowInsets: WindowInsets? = null

        /**
         * The color used for the background of this navigation bar.
         * ```
         * <NavigationBar containerColor="#FFFFFF00">...</NavigationBar>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(color: String) = apply {
            this.containerColor = color.toColor()
        }

        /**
         * The preferred color for content inside this navigation bar.
         * ```
         * <NavigationBar contentColor="#FFCCCCCC">...</NavigationBar>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(color: String) = apply {
            this.contentColor = color.toColor()
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <NavigationBar tonalElevation="24">...</NavigationBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Window insets to be passed to the navigation bar window via PaddingValues params.
         * ```
         * <ModalBottomSheet windowInsets="{'bottom': '100'}" >
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

        fun build() = NavigationBarDTO(
            Properties(
                containerColor,
                contentColor,
                tonalElevation,
                windowInsets,
                commonProps,
            )
        )
    }
}

internal object NavigationBarDtoFactory :
    ComposableViewFactory<NavigationBarDTO>() {
    /**
     * Creates a `NavigationBarDTO` object based on the attributes of the input `Attributes` object.
     * NavigationBarDTO co-relates to the NavigationBar.
     * @param attributes the `Attributes` object to create the `NavigationBarDTO` object from
     * @return a `NavigationBarDTO` object based on the attributes of the input `Attributes` object.
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): NavigationBarDTO =
        attributes.fold(NavigationBarDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrContentColor -> builder.contentColor(attribute.value)
                attrTonalElevation -> builder.tonalElevation(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as NavigationBarDTO.Builder
        }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*>> {
        return mapOf(
            navigationBarItem to NavigationBarItemDtoFactory
        )
    }
}
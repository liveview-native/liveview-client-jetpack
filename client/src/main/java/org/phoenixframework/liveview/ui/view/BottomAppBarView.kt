package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.Templates.templateAction
import org.phoenixframework.liveview.constants.Templates.templateFab
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.paddingIfNotNull
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
 * Material Design bottom app bar.
 * A bottom app bar displays navigation and key actions at the bottom of mobile screens.
 * ```
 * <BottomAppBar template="bottomBar">
 *   <IconButton phx-click="horizontalGrid" template="action">
 *     <Icon imageVector="filled:HorizontalDistribute" />
 *   </IconButton>
 *   <IconButton phx-click="verticalGrid" template="action">
 *     <Icon imageVector="filled:VerticalDistribute" />
 *   </IconButton>
 *   <FloatingActionButton phx-click="someAction" template="fab">
 *     <Icon imageVector="filled:Add"/>
 *   </FloatingActionButton>
 * </BottomAppBar>
 * ```
 */
internal class BottomAppBarView private constructor(props: Properties) :
    ComposableView<BottomAppBarView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val containerColorValue = props.containerColor
        val contentColor = props.contentColor
        val contentPadding = props.contentPadding
        val tonalElevation = props.tonalElevation
        val windowsInsets = props.windowInsets

        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateAction }
        }
        val floatingActionButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateFab }
        }
        val containerColor = containerColorValue ?: BottomAppBarDefaults.containerColor
        BottomAppBar(
            actions = {
                actions?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            },
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            floatingActionButton = floatingActionButton?.let { fab ->
                {
                    PhxLiveView(fab, pushEvent, composableNode, null)
                }
            },
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            tonalElevation = tonalElevation ?: BottomAppBarDefaults.ContainerElevation,
            contentPadding = contentPadding ?: BottomAppBarDefaults.ContentPadding,
            windowInsets = windowsInsets ?: BottomAppBarDefaults.windowInsets,
        )
    }

    @Stable
    internal data class Properties(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val contentPadding: PaddingValues? = null,
        val tonalElevation: Dp? = null,
        val windowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<BottomAppBarView>() {
        /**
         * Creates a `BottomAppBarView` object based on the attributes of the input `Attributes` object.
         * BottomAppBarView co-relates to the BottomAppBar composable
         * @param attributes the `Attributes` object to create the `BottomAppBarView` object from
         * @return a `BottomAppBarView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): BottomAppBarView = BottomAppBarView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrContentPadding -> contentPadding(props, attribute.value)
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

        /**
         * Color used for the background of this bottom bar.
         *
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        fun containerColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(containerColor = color.toColor())
            } else props
        }

        /**
         * Preferred color for content inside this bottom bar.
         *
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        fun contentColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(contentColor = color.toColor())
            } else props
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <BottomAppBar contentPadding="8">...</Button>
         * ```
         * @param contentPadding int value representing the padding to be applied to the button's
         * content.
         */
        fun contentPadding(props: Properties, contentPadding: String): Properties {
            return if (contentPadding.isNotEmpty()) {
                props.copy(contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp))
            } else props
        }

        /**
         * When containerColor is the default one, a translucent primary color overlay is applied
         * on top of the container. A higher tonal elevation value will result in a darker color in
         * light theme and lighter color in dark theme.
         * ```
         * <BottomAppBar tonalElevation="12" >...</BottomAppBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Window insets to be passed to the bottom bar via PaddingValues params.
         * ```
         * <BottomAppBar windowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(windowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrDrawerContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrDrawerContentColor
import org.phoenixframework.liveview.constants.Attrs.attrDrawerShape
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.ComposableTypes
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
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Content inside of a navigation drawer. It could be Modal, Dismissible, or Permanent.
 * ```
 * <ModalNavigationDrawer ...>
 *   <ModalDrawerSheet template="drawerContent">
 * </ModalNavigationDrawer>
 *
 * <DismissibleNavigationDrawer ...>
 *   <DismissibleDrawerSheet template="drawerContent">
 * </DismissibleNavigationDrawer>
 *
 * <PermanentNavigationDrawer ...>
 *   <PermanentDrawerSheet template="drawerContent">
 * </PermanentNavigationDrawer>
 * ```
 */
internal class DrawerSheetView private constructor(props: Properties) :
    ComposableView<DrawerSheetView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val drawerShape = props.drawerShape
        val containerColor = props.drawerContainerColor
        val contentColor = props.drawerContentColor
        val tonalElevation = props.drawerTonalElevation
        val windowsInsets = props.windowInsets

        when (composableNode?.node?.tag) {
            ComposableTypes.modalDrawerSheet ->
                ModalDrawerSheet(
                    modifier = props.commonProps.modifier,
                    drawerShape = drawerShape ?: DrawerDefaults.shape,
                    drawerContainerColor = containerColor ?: DrawerDefaults.modalContainerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.modalContainerColor
                    ),
                    drawerTonalElevation = tonalElevation ?: DrawerDefaults.ModalDrawerElevation,
                    windowInsets = windowsInsets ?: DrawerDefaults.windowInsets,
                    content = {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            ComposableTypes.dismissibleDrawerSheet ->
                DismissibleDrawerSheet(
                    modifier = props.commonProps.modifier,
                    drawerShape = drawerShape ?: RectangleShape,
                    drawerContainerColor = containerColor ?: DrawerDefaults.standardContainerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.standardContainerColor
                    ),
                    drawerTonalElevation = tonalElevation
                        ?: DrawerDefaults.DismissibleDrawerElevation,
                    windowInsets = windowsInsets ?: DrawerDefaults.windowInsets,
                    content = {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            ComposableTypes.permanentDrawerSheet ->
                PermanentDrawerSheet(
                    modifier = props.commonProps.modifier,
                    drawerShape = drawerShape ?: RectangleShape,
                    drawerContainerColor = containerColor ?: DrawerDefaults.standardContainerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.standardContainerColor
                    ),
                    drawerTonalElevation = tonalElevation
                        ?: DrawerDefaults.PermanentDrawerElevation,
                    windowInsets = windowsInsets ?: DrawerDefaults.windowInsets,
                    content = {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )
        }
    }

    @Stable
    internal data class Properties(
        val drawerShape: Shape? = null,
        val drawerContainerColor: Color? = null,
        val drawerContentColor: Color? = null,
        val drawerTonalElevation: Dp? = null,
        val windowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<DrawerSheetView>() {
        /**
         * Creates a `DrawerSheetView` object based on the attributes of the input `Attributes`
         * object. DrawerSheetView co-relates to the ModalDrawerSheet, DismissibleDrawerSheet, or
         * PermanentDrawerSheet composable.
         * @param attributes the `Attributes` object to create the `DrawerSheetView` object from
         * @return a `DrawerSheetView` object based on the attributes of the input `Attributes`
         * object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): DrawerSheetView = DrawerSheetView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrDrawerContainerColor -> drawerContainerColor(props, attribute.value)
                    attrDrawerContentColor -> drawerContentColor(props, attribute.value)
                    attrDrawerShape -> drawerShape(props, attribute.value)
                    attrTonalElevation -> drawerTonalElevation(props, attribute.value)
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
         * Defines the shape of this drawer's container.
         * ```
         * <ModalDrawerSheet drawerShape="8">...</ModalDrawerSheet>
         * ```
         * @param shape drawer's container's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or an integer representing
         * the curve size applied to all four corners.
         */
        private fun drawerShape(props: Properties, shape: String): Properties {
            return props.copy(drawerShape = shapeFromString(shape))
        }

        /**
         * The color used for the background of this drawer.
         * ```
         * <ModalDrawerSheet drawerContainerColor="#FFFFFF00">...</ModalDrawerSheet>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun drawerContainerColor(props: Properties, color: String): Properties {
            return props.copy(drawerContainerColor = color.toColor())
        }

        /**
         * The preferred color for content inside this drawer..
         * ```
         * <ModalDrawerSheet drawerContentColor="#FFCCCCCC">...</ModalDrawerSheet>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun drawerContentColor(props: Properties, color: String): Properties {
            return props.copy(drawerContentColor = color.toColor())
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <ModalDrawerSheet tonalElevation="24">...</ModalDrawerSheet>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun drawerTonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                return props.copy(drawerTonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Window insets to be passed to the bottom sheet window via PaddingValues params.
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
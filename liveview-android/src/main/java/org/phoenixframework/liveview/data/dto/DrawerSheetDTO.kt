package org.phoenixframework.liveview.data.dto

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
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class DrawerSheetDTO private constructor(props: Properties) :
    ComposableView<DrawerSheetDTO.Properties>(props) {

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
                    drawerContainerColor = containerColor ?: DrawerDefaults.containerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.containerColor
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
                    drawerContainerColor = containerColor ?: DrawerDefaults.containerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.containerColor
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
                    drawerContainerColor = containerColor ?: DrawerDefaults.containerColor,
                    drawerContentColor = contentColor ?: contentColorFor(
                        containerColor ?: DrawerDefaults.containerColor
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
        val drawerShape: Shape?,
        val drawerContainerColor: Color?,
        val drawerContentColor: Color?,
        val drawerTonalElevation: Dp?,
        val windowInsets: WindowInsets?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties


    internal class Builder : ComposableBuilder() {
        private var drawerShape: Shape? = null
        private var drawerContainerColor: Color? = null
        private var drawerContentColor: Color? = null
        private var drawerTonalElevation: Dp? = null
        private var windowInsets: WindowInsets? = null

        /**
         * Defines the shape of this drawer's container.
         * ```
         * <ModalDrawerSheet drawerShape="8">...</ModalDrawerSheet>
         * ```
         * @param shape drawer's container's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or an integer representing
         * the curve size applied to all four corners.
         */
        fun drawerShape(shape: String) = apply {
            this.drawerShape = shapeFromString(shape)
        }

        /**
         * The color used for the background of this drawer.
         * ```
         * <ModalDrawerSheet drawerContainerColor="#FFFFFF00">...</ModalDrawerSheet>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun drawerContainerColor(color: String) = apply {
            this.drawerContainerColor = color.toColor()
        }

        /**
         * The preferred color for content inside this drawer..
         * ```
         * <ModalDrawerSheet drawerContentColor="#FFCCCCCC">...</ModalDrawerSheet>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun drawerContentColor(color: String) = apply {
            this.drawerContentColor = color.toColor()
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <ModalDrawerSheet tonalElevation="24">...</ModalDrawerSheet>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun drawerTonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                drawerTonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Window insets to be passed to the bottom sheet window via PaddingValues params.
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

        fun build() = DrawerSheetDTO(
            Properties(
                drawerShape,
                drawerContainerColor,
                drawerContentColor,
                drawerTonalElevation,
                windowInsets,
                commonProps,
            )
        )
    }
}

internal object DrawerSheetDtoFactory :
    ComposableViewFactory<DrawerSheetDTO>() {
    /**
     * Creates a `DrawerSheetDTO` object based on the attributes of the input `Attributes`
     * object. DrawerSheetDTO co-relates to the ModalDrawerSheet, DismissibleDrawerSheet, or
     * PermanentDrawerSheet composable.
     * @param attributes the `Attributes` object to create the `DrawerSheetDTO` object from
     * @return a `DrawerSheetDTO` object based on the attributes of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DrawerSheetDTO =
        attributes.fold(DrawerSheetDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrDrawerContainerColor -> builder.drawerContainerColor(attribute.value)
                attrDrawerContentColor -> builder.drawerContentColor(attribute.value)
                attrDrawerShape -> builder.drawerShape(attribute.value)
                attrTonalElevation -> builder.drawerTonalElevation(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as DrawerSheetDTO.Builder
        }.build()
}
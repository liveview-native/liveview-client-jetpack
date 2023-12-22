package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Content inside of a modal navigation drawer.
 * ```
 * <ModalNavigationDrawer ...>
 *   <ModalDrawerSheet template="drawerContent">
 * ```
 */
internal class ModalDrawerSheetDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val drawerShape = builder.drawerShape
    private val containerColor = builder.drawerContainerColor
    private val contentColor = builder.drawerContentColor
    private val tonalElevation = builder.drawerTonalElevation
    private val windowsInsets = builder.windowInsets

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        ModalDrawerSheet(
            modifier = modifier,
            drawerShape = drawerShape ?: DrawerDefaults.shape,
            drawerContainerColor = containerColor ?: MaterialTheme.colorScheme.surface,
            drawerContentColor = contentColor ?: contentColorFor(
                containerColor ?: MaterialTheme.colorScheme.surface
            ),
            drawerTonalElevation = tonalElevation ?: DrawerDefaults.ModalDrawerElevation,
            windowInsets = windowsInsets ?: DrawerDefaults.windowInsets,
            content = {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    internal class Builder : ComposableBuilder() {
        var drawerShape: Shape? = null
            private set
        var drawerContainerColor: Color? = null
            private set
        var drawerContentColor: Color? = null
            private set
        var drawerTonalElevation: Dp? = null
            private set
        var windowInsets: WindowInsets? = null
            private set

        /**
         * Defines the shape of this drawer's container.
         * ```
         * <ModalDrawerSheet drawer-shape="8">...</ModalDrawerSheet>
         * ```
         * @param shape drawer's container's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun drawerShape(shape: String) = apply {
            this.drawerShape = shapeFromString(shape)
        }

        /**
         * The color used for the background of this drawer.
         * ```
         * <ModalDrawerSheet drawer-container-color="#FFFFFF00">...</ModalDrawerSheet>
         * ```
         * @param color container color in AARRGGBB format.
         */
        fun drawerContainerColor(color: String) = apply {
            this.drawerContainerColor = color.toColor()
        }

        /**
         * The preferred color for content inside this drawer..
         * ```
         * <ModalDrawerSheet drawer-content-color="#FFCCCCCC">...</ModalDrawerSheet>
         * ```
         * @param color content color in AARRGGBB format.
         */
        fun drawerContentColor(color: String) = apply {
            this.drawerContentColor = color.toColor()
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <ModalDrawerSheet tonal-elevation="24">...</ModalDrawerSheet>
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
         * <ModalBottomSheet window-insets="{'bottom': '100'}" >
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

        fun build() = ModalDrawerSheetDTO(this)
    }
}

internal object ModalDrawerSheetDtoFactory :
    ComposableViewFactory<ModalDrawerSheetDTO, ModalDrawerSheetDTO.Builder>() {
    /**
     * Creates a `ModalDrawerSheetDTO` object based on the attributes of the input `Attributes`
     * object. ModalDrawerSheetDTO co-relates to the ModalDrawerSheet composable.
     * @param attributes the `Attributes` object to create the `ModalDrawerSheetDTO` object from
     * @return a `ModalDrawerSheetDTO` object based on the attributes of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ModalDrawerSheetDTO =
        attributes.fold(ModalDrawerSheetDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrDrawerContainerColor -> builder.drawerContainerColor(attribute.value)
                attrDrawerContentColor -> builder.drawerContentColor(attribute.value)
                attrDrawerShape -> builder.drawerShape(attribute.value)
                attrTonalElevation -> builder.drawerTonalElevation(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as ModalDrawerSheetDTO.Builder
        }.build()
}
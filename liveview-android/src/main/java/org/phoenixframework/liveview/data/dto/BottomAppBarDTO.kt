package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

internal class BottomAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val containerColor = builder.containerColor
    private val contentColor = builder.contentColor
    private val contentPadding = builder.contentPadding
    private val tonalElevation = builder.tonalElevation
    private val windowsInsets = builder.windowInsets

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == Templates.templateAction }
        }
        val floatingActionButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateFab }
        }
        val containerColor = containerColor ?: BottomAppBarDefaults.containerColor
        BottomAppBar(
            actions = {
                actions?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            },
            modifier = modifier.paddingIfNotNull(paddingValues),
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

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var contentPadding: PaddingValues? = null
            private set
        var tonalElevation: Dp? = null
            private set
        var windowInsets: WindowInsets? = null
            private set

        /**
         * Color used for the background of this bottom bar.
         *
         * @param color container color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * Preferred color for content inside this bottom bar.
         *
         * @param color content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <BottomAppBar content-padding="8">...</Button>
         * ```
         * @param contentPadding int value representing the padding to be applied to the button's
         * content.
         */
        fun contentPadding(contentPadding: String) = apply {
            if (contentPadding.isNotEmpty()) {
                this.contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp)
            }
        }

        /**
         * When containerColor is the default one, a translucent primary color overlay is applied
         * on top of the container. A higher tonal elevation value will result in a darker color in
         * light theme and lighter color in dark theme.
         * ```
         * <BottomAppBar tonal-elevation="12" >...</BottomAppBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Window insets to be passed to the bottom bar via PaddingValues params.
         * ```
         * <BottomAppBar window-insets="{'bottom': '100'}" >
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

        fun build() = BottomAppBarDTO(this)
    }
}

internal object BottomAppBarDtoFactory :
    ComposableViewFactory<BottomAppBarDTO, BottomAppBarDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): BottomAppBarDTO = attributes.fold(BottomAppBarDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrContentPadding -> builder.contentPadding(attribute.value)
            attrTonalElevation -> builder.tonalElevation(attribute.value)
            attrWindowInsets -> builder.windowInsets(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as BottomAppBarDTO.Builder
    }.build()
}
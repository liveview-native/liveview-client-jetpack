package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrThickness
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design divider.
 * ```
 * <Divider thickness="2" vertical-padding="8" color="#FFCCCCCC" />
 * ```
 */
internal class DividerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val thickness = builder.thickness
    private val color = builder.color

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        Divider(
            modifier = modifier,
            thickness = thickness ?: DividerDefaults.Thickness,
            color = color ?: DividerDefaults.color
        )
    }

    internal class Builder : ComposableBuilder() {
        var thickness: Dp? = null
            private set

        var color: Color? = null
            private set

        /**
         * Thickness of the divider line.
         * ```
         * <Divider thickness='2' />
         * ```
         * @param thickness int value representing the thickness of the divider line.
         */
        fun thickness(thickness: String) = apply {
            if (thickness.isNotEmptyAndIsDigitsOnly()) {
                this.thickness = thickness.toInt().dp
            }
        }

        /**
         * Color of the track behind the indicator, visible when the progress has not reached the
         * area of the overall indicator yet.
         * ```
         * <Divider color='#FF00FF00' />
         * ```
         * @param color The color to be applied to the track behind the indicator. The color must be
         * specified as a string in the AARRGGBB format.
         */
        fun color(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = color.toColor()
            }
        }

        fun build() = DividerDTO(this)
    }
}

internal object DividerDtoFactory : ComposableViewFactory<DividerDTO, DividerDTO.Builder>() {
    /**
     * Creates a `DividerDTO` object based on the attributes of the input `Attributes` object.
     * DividerDTO co-relates to the Divider composable
     * @param attributes the `Attributes` object to create the `DividerDTO` object from
     * @return a `DividerDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DividerDTO = DividerDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            when (attribute.name) {
                attrColor -> builder.color(attribute.value)
                attrThickness -> builder.thickness(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as DividerDTO.Builder
        }
    }.build()
}
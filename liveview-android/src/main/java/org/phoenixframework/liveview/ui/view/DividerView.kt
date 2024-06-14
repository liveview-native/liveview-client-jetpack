package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrThickness
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode

/**
 * Material Design divider.
 * ```
 * <HorizontalDivider thickness="2" verticalPadding="8" color="#FFCCCCCC" />
 * <VerticalDivider thickness="2" verticalPadding="8" color="#FFCCCCCC" />
 * ```
 */
internal class DividerView private constructor(props: Properties) :
    ComposableView<DividerView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val thickness = props.thickness
        val color = props.color

        when (composableNode?.node?.tag) {
            ComposableTypes.horizontalDivider -> {
                HorizontalDivider(
                    modifier = props.commonProps.modifier,
                    thickness = thickness ?: DividerDefaults.Thickness,
                    color = color ?: DividerDefaults.color
                )
            }

            ComposableTypes.verticalDivider -> {
                VerticalDivider(
                    modifier = props.commonProps.modifier,
                    thickness = thickness ?: DividerDefaults.Thickness,
                    color = color ?: DividerDefaults.color
                )
            }
        }
    }

    @Stable
    internal data class Properties(
        val thickness: Dp?,
        val color: Color?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var thickness: Dp? = null
        private var color: Color? = null

        /**
         * Thickness of the divider line.
         * ```
         * <HorizontalDivider thickness='2' />
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
         * <HorizontalDivider color='#FF00FF00' />
         * ```
         * @param color The color to be applied to the track behind the indicator. The color must be
         * specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun color(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = color.toColor()
            }
        }

        fun build() = DividerView(
            Properties(
                thickness,
                color,
                commonProps,
            )
        )
    }
}

internal object DividerViewFactory : ComposableViewFactory<DividerView>() {
    /**
     * Creates a `DividerView` object based on the attributes of the input `Attributes` object.
     * DividerView co-relates to the HorizontalDivider and VerticalDivider composables.
     * @param attributes the `Attributes` object to create the `DividerView` object from
     * @return a `DividerView` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DividerView = DividerView.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            when (attribute.name) {
                attrColor -> builder.color(attribute.value)
                attrThickness -> builder.thickness(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as DividerView.Builder
        }
    }.build()
}
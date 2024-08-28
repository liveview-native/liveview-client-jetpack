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
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrThickness
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
        val thickness: Dp? = null,
        val color: Color? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<DividerView>() {
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
        ): DividerView = DividerView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrColor -> color(props, attribute.value)
                    attrThickness -> thickness(props, attribute.value)
                    else -> props.copy(
                        commonProps = handleCommonAttributes(
                            props.commonProps,
                            attribute,
                            pushEvent,
                            scope
                        )
                    )
                }
            }
        )

        /**
         * Thickness of the divider line.
         * ```
         * <HorizontalDivider thickness='2' />
         * ```
         * @param thickness int value representing the thickness of the divider line.
         */
        private fun thickness(props: Properties, thickness: String): Properties {
            return if (thickness.isNotEmptyAndIsDigitsOnly()) {
                props.copy(thickness = thickness.toInt().dp)
            } else props
        }

        /**
         * Color of the track behind the indicator, visible when the progress has not reached the
         * area of the overall indicator yet.
         * ```
         * <HorizontalDivider color='#FF00FF00' />
         * ```
         * @param color The color to be applied to the track behind the indicator. The color must be
         * specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun color(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(color = color.toColor())
            } else props
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrStrokeCap
import org.phoenixframework.liveview.constants.Attrs.attrStrokeWidth
import org.phoenixframework.liveview.constants.Attrs.attrTrackColor
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
 * Indeterminate Material Design circular and linear progress indicator.
 * ```
 * <CircularProgressIndicator color="#FFFF0000" trackColor="#FF00FF00" strokeCap="round" />
 * <LinearProgressIndicator color="#FFFF0000" trackColor="#FF00FF00" strokeCap="butt" width="fill" />
 * ```
 */
internal class ProgressIndicatorView private constructor(props: Properties) :
    ComposableView<ProgressIndicatorView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val color = props.color
        val strokeWidth = props.strokeWidth
        val trackColor = props.trackColor
        val strokeCap = props.strokeCap

        if (composableNode?.node?.tag == ComposableTypes.linearProgressIndicator) {
            LinearProgressIndicator(
                modifier = props.commonProps.modifier,
                color = color ?: ProgressIndicatorDefaults.linearColor,
                trackColor = trackColor ?: ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = strokeCap ?: ProgressIndicatorDefaults.LinearStrokeCap,
            )
        } else {
            CircularProgressIndicator(
                modifier = props.commonProps.modifier,
                color = color ?: ProgressIndicatorDefaults.circularColor,
                trackColor = trackColor ?: ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = strokeCap ?: ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
                strokeWidth = strokeWidth ?: ProgressIndicatorDefaults.CircularStrokeWidth,
            )
        }
    }

    @Stable
    internal data class Properties(
        val color: Color? = null,
        val trackColor: Color? = null,
        val strokeCap: StrokeCap? = null,
        val strokeWidth: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ProgressIndicatorView>() {
        /**
         * Creates a `ProgressIndicatorView` object based on the attributes of the input `Attributes`
         * object. `ProgressIndicatorView` co-relates to both `LinearProgressIndicator` and
         * `CircularProgressIndicator` composables.
         * @param attributes the `Attributes` object to create the `ProgressIndicatorView` object from
         * @return a `ProgressIndicatorView` object based on the attributes of the input `Attributes`
         * object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): ProgressIndicatorView = ProgressIndicatorView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrColor -> color(props, attribute.value)
                    attrStrokeCap -> strokeCap(props, attribute.value)
                    attrStrokeWidth -> strokeWidth(props, attribute.value)
                    attrTrackColor -> trackColor(props, attribute.value)
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
         * Color of this progress indicator
         * ```
         * <LinearProgressIndicator color='#FF00FF00' />
         * <CircularProgressIndicator color='#FF00FF00' />
         * ```
         * @param color The color to be applied to the progress indicator. The color must be
         * specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun color(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(color = color.toColor())
            } else props
        }

        /**
         * Color of the track behind the indicator, visible when the progress has not reached the
         * area of the overall indicator yet.
         * ```
         * <LinearProgressIndicator trackColor='#FF00FF00' />
         * <CircularProgressIndicator trackColor='#FF00FF00' />
         * ```
         * @param color The color to be applied to the track behind the indicator. The color must be
         * specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun trackColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(trackColor = color.toColor())
            } else props
        }

        /**
         * Stroke cap to use for the ends of the progress indicator.
         * ```
         * <LinearProgressIndicator strokeCap='round' />
         * <CircularProgressIndicator strokeCap='square' />
         * ```
         * @param strokeCap see the supported stroke cap values at
         * [org.phoenixframework.liveview.constants.StrokeCapValues].
         */
        private fun strokeCap(props: Properties, strokeCap: String): Properties {
            return if (strokeCap.isNotEmpty()) {
                props.copy(
                    strokeCap = strokeCapFromString(strokeCap)
                )
            } else props
        }

        /**
         * Stroke width the progress indicator
         * ```
         * <CircularProgressIndicator strokeWidth='2' />
         * ```
         * @param strokeWidth stroke width in dp of the progress indicator
         */
        private fun strokeWidth(props: Properties, strokeWidth: String): Properties {
            return if (strokeWidth.isNotEmptyAndIsDigitsOnly()) {
                props.copy(strokeWidth = strokeWidth.toInt().dp)
            } else props
        }
    }
}
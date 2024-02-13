package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrStrokeCap
import org.phoenixframework.liveview.data.constants.Attrs.attrStrokeWidth
import org.phoenixframework.liveview.data.constants.Attrs.attrTrackColor
import org.phoenixframework.liveview.data.constants.StrokeCapValues
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Indeterminate Material Design circular and linear progress indicator.
 * ```
 * <CircularProgressIndicator color="#FFFF0000" trackColor="#FF00FF00" strokeCap="round" />
 * <LinearProgressIndicator color="#FFFF0000" trackColor="#FF00FF00" strokeCap="butt" width="fill" />
 * ```
 */
internal class ProgressIndicatorDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val color = builder.color
    private val strokeWidth = builder.strokeWidth
    private val trackColor = builder.trackColor
    private val strokeCap = builder.strokeCap

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        if (composableNode?.node?.tag == ComposableTypes.linearProgressIndicator) {
            LinearProgressIndicator(
                modifier = modifier,
                color = color ?: ProgressIndicatorDefaults.linearColor,
                trackColor = trackColor ?: ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = strokeCap ?: ProgressIndicatorDefaults.LinearStrokeCap,
            )
        } else {
            CircularProgressIndicator(
                modifier = modifier,
                color = color ?: ProgressIndicatorDefaults.circularColor,
                trackColor = trackColor ?: ProgressIndicatorDefaults.circularTrackColor,
                strokeCap = strokeCap ?: ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
                strokeWidth = strokeWidth ?: ProgressIndicatorDefaults.CircularStrokeWidth,
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var color: Color? = null
            private set

        var trackColor: Color? = null
            private set

        var strokeCap: StrokeCap? = null
            private set

        var strokeWidth: Dp? = null
            private set

        /**
         * Color of this progress indicator
         * ```
         * <LinearProgressIndicator color='#FF00FF00' />
         * <CircularProgressIndicator color='#FF00FF00' />
         * ```
         * @param color The color to be applied to the progress indicator. The color must be
         * specified as a string in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun color(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = color.toColor()
            }
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
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun trackColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.trackColor = color.toColor()
            }
        }

        /**
         * Stroke cap to use for the ends of the progress indicator.
         * ```
         * <LinearProgressIndicator strokeCap='round' />
         * <CircularProgressIndicator strokeCap='square' />
         * ```
         * @param strokeCap see the supported stroke cap values at
         * [org.phoenixframework.liveview.data.constants.StrokeCapValues].
         */
        fun strokeCap(strokeCap: String) = apply {
            if (strokeCap.isNotEmpty()) {
                this.strokeCap = when (strokeCap) {
                    StrokeCapValues.round -> StrokeCap.Round
                    StrokeCapValues.square -> StrokeCap.Square
                    StrokeCapValues.butt -> StrokeCap.Butt
                    else -> ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                }
            }
        }

        /**
         * Stroke width the progress indicator
         * ```
         * <CircularProgressIndicator strokeWidth='2' />
         * ```
         * @param strokeWidth stroke width in dp of the progress indicator
         */
        fun strokeWidth(strokeWidth: String) = apply {
            if (strokeWidth.isNotEmptyAndIsDigitsOnly()) {
                this.strokeWidth = strokeWidth.toInt().dp
            }
        }

        fun build() = ProgressIndicatorDTO(this)
    }
}

internal object ProgressIndicatorDtoFactory :
    ComposableViewFactory<ProgressIndicatorDTO, ProgressIndicatorDTO.Builder>() {
    /**
     * Creates a `ProgressIndicatorDTO` object based on the attributes of the input `Attributes`
     * object. `ProgressIndicatorDTO` co-relates to both `LinearProgressIndicator` and
     * `CircularProgressIndicator` composables.
     * @param attributes the `Attributes` object to create the `ProgressIndicatorDTO` object from
     * @return a `ProgressIndicatorDTO` object based on the attributes of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ProgressIndicatorDTO =
        attributes.fold(ProgressIndicatorDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrColor -> builder.color(attribute.value)
                attrStrokeCap -> builder.strokeCap(attribute.value)
                attrStrokeWidth -> builder.strokeWidth(attribute.value)
                attrTrackColor -> builder.trackColor(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as ProgressIndicatorDTO.Builder
        }.build()
}
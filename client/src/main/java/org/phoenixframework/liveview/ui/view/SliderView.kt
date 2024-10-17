package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.compositeOver
import com.google.gson.JsonArray
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrMaxValue
import org.phoenixframework.liveview.constants.Attrs.attrMinValue
import org.phoenixframework.liveview.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.constants.Attrs.attrSteps
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrActiveTickColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrActiveTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledActiveTickColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledActiveTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledInactiveTickColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledInactiveTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledThumbColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrInactiveTickColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrInactiveTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrThumbColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateEndThumb
import org.phoenixframework.liveview.constants.Templates.templateStartThumb
import org.phoenixframework.liveview.constants.Templates.templateThumb
import org.phoenixframework.liveview.constants.Templates.templateTrack
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.LocalParentDataHolder
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTAINER_ALPHA
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA

/**
 * Material Design slider.
 * ```
 * // sliderValue is a float
 * <Slider
 *   phx-value={@sliderValue}
 *   phx-change="setSliderValue"
 *   minValue="0"
 *   maxValue="100" />
 * // sliderRange is an array of two positions
 * <RangeSlider
 *   phx-value={Enum.join(@sliderRange, ",")}
 *   phx-change="setSliderRange"
 *   minValue="0"
 *   maxValue="100" />
 * ```
 * It's also possible to customize the track of both types of slider using a child with the
 * template set as "track". The thumb of the `Slider` component can be customized using the "thumb"
 * template.
 * ```
 * <Slider ...>
 *   <Box style="size(40.dp);clip(RoundedCornerShape(4.dp));background(Color.Green)" template="thumb"/>
 *   <Box style="fillMaxWidth();height(10.dp);background(Color.Blue)" template="track"/>
 * </Slider>
 * ```
 * For the `RangeSlider` component, you can customize both start and end thumbs using "start-thumb"
 * and "end-thumb" templates respectively.
 * ```
 * <RangeSlider ...>
 *   <Box style="size(40.dp);clip(RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp);background(Color.Green)" template="start-thumb"/>
 *   <Box style="size(40.dp);clip=Circle);background(Color.Red)" template="end-thumb"/>
 * </RangeSlider>
 * ```
 */
internal class SliderView private constructor(props: Properties) :
    ChangeableView<Float, SliderView.Properties>(props) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val changeValueEventName = props.changeableProps.onChange
        val enabled = props.changeableProps.enabled

        val minValue = props.minValue
        val maxValue = props.maxValue
        val steps = props.steps
        val colorsValue = props.colors?.toImmutableMap()

        val interactionSource = remember { MutableInteractionSource() }
        val colors = getSliderColors(colorsValue)
        val track = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrack }
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.slider -> {
                val phxValue = props.commonProps.phxValue
                val sliderValue = remember(phxValue) {
                    phxValue?.let {
                        when (it) {
                            is String -> it.toFloatOrNull() ?: 0f
                            is Int -> it.toFloat()
                            is Float -> it
                            is Map<*, *> -> (it[KEY_PHX_VALUE] as? Float) ?: 0f
                            else -> 0f
                        }
                    } ?: 0f
                }
                val sliderState = remember {
                    SliderState(
                        value = sliderValue,
                        valueRange = minValue..maxValue,
                        steps = steps,
                    )
                }
                val thumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateThumb }
                }

                Slider(
                    state = sliderState,
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    colors = colors,
                    thumb = {
                        thumb?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                    track = { _ ->
                        track?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Track(
                            colors = colors,
                            enabled = enabled,
                            sliderState = sliderState,
                        )
                    }
                )

                val parentDataHolder = LocalParentDataHolder.current
                LaunchedEffect(composableNode.id) {
                    val initialValue = mergeValue(sliderState.value)
                    parentDataHolder?.setValue(composableNode, initialValue)
                    snapshotFlow { sliderState.value }
                        .onChangeable()
                        .flowOn(Dispatchers.IO)
                        .collect { value ->
                            val newValue = mergeValue(value)
                            parentDataHolder?.setValue(composableNode, newValue)
                            pushOnChangeEvent(
                                pushEvent,
                                changeValueEventName,
                                newValue,
                            )
                        }
                }
            }

            ComposableTypes.rangeSlider -> {
                val endInteractionSource = remember { MutableInteractionSource() }
                val phxValue = props.commonProps.phxValue
                val sliderValue = remember(phxValue) {
                    phxValue?.let {
                        when (it) {
                            is FloatArray -> it
                            is Map<*, *> -> (it[KEY_PHX_VALUE] as? FloatArray) ?: floatArrayOf(
                                minValue,
                                maxValue
                            )

                            else -> floatArrayOf(minValue, maxValue)
                        }
                    } ?: floatArrayOf(minValue, maxValue)
                }
                val rangeSliderState = remember {
                    RangeSliderState(
                        activeRangeStart = sliderValue[0],
                        activeRangeEnd = sliderValue[1],
                        steps = steps,
                        valueRange = minValue..maxValue,
                    )
                }
                val startThumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateStartThumb }
                }
                val endThumb = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateEndThumb }
                }
                RangeSlider(
                    state = rangeSliderState,
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    colors = colors,
                    track = { _ ->
                        track?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Track(
                            colors = colors,
                            enabled = enabled,
                            rangeSliderState = rangeSliderState
                        )
                    },
                    startThumb = {
                        startThumb?.let {
                            //TODO how to pass the positions to the child?
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                    endThumb = {
                        endThumb?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: SliderDefaults.Thumb(
                            interactionSource = endInteractionSource,
                            colors = colors,
                            enabled = enabled
                        )
                    },
                )

                LaunchedEffect(Unit) {
                    snapshotFlow {
                        rangeSliderState.activeRangeStart..rangeSliderState.activeRangeEnd
                    }
                        .onTypedChangeable()
                        .collect { value ->
                            val newValue = mergeValue(value.start, value.endInclusive)
                            pushOnChangeEvent(pushEvent, changeValueEventName, newValue)
                        }
                }
            }
        }
    }

    private fun mergeValue(value: Float): Any? {
        return mergeValueWithPhxValue(KEY_PHX_VALUE, value)
    }

    private fun mergeValue(startValue: Float, endValue: Float): Any? {
        return mergeValueWithPhxValue(
            KEY_PHX_VALUE,
            JsonArray().apply {
                add(startValue)
                add(endValue)
            }.toString()
        )
    }

    @Composable
    private fun getSliderColors(sliderColors: ImmutableMap<String, String>?): SliderColors {
        val defaultValue = SliderDefaults.colors()
        return if (sliderColors == null) {
            defaultValue
        } else {
            SliderDefaults.colors(
                thumbColor = sliderColors[colorAttrThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                activeTrackColor = sliderColors[colorAttrActiveTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                activeTickColor = sliderColors[colorAttrActiveTickColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                inactiveTrackColor = sliderColors[colorAttrInactiveTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                inactiveTickColor = sliderColors[colorAttrInactiveTickColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
                disabledThumbColor = sliderColors[colorAttrDisabledThumbColor]?.toColor()
                    ?: sliderColors[colorAttrThumbColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                        ?.compositeOver(MaterialTheme.colorScheme.surface)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledActiveTrackColor = sliderColors[colorAttrDisabledActiveTrackColor]?.toColor()
                    ?: sliderColors[colorAttrActiveTrackColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                disabledActiveTickColor = sliderColors[colorAttrDisabledActiveTickColor]?.toColor()
                    ?: sliderColors[colorAttrActiveTickColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                disabledInactiveTrackColor = sliderColors[colorAttrDisabledInactiveTrackColor]?.toColor()
                    ?: sliderColors[colorAttrInactiveTrackColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTAINER_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA),
                disabledInactiveTickColor = sliderColors[colorAttrDisabledInactiveTickColor]?.toColor()
                    ?: sliderColors[colorAttrInactiveTickColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Stable
    internal data class Properties(
        val minValue: Float = 0f,
        val maxValue: Float = 1f,
        val steps: Int = 0,
        val colors: ImmutableMap<String, String>? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ChangeableView.Factory() {

        /**
         * Creates a `SliderView` object based on the attributes of the input `Attributes` object.
         * SliderView co-relates to the Slider composable
         * @param attributes the `Attributes` object to create the `SliderView` object from
         * @return a `SliderView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): SliderView = SliderView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrColors -> colors(props, attribute.value)
                        attrMaxValue -> maxValue(props, attribute.value)
                        attrMinValue -> minValue(props, attribute.value)
                        attrPhxValue -> handleValue(props, attribute.value)
                        attrSteps -> steps(props, attribute.value)

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
            })

        /**
         * The min value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider minValue="0" />
         * ```
         * @param value a float value to set the min value accepted by the slider.
         */
        private fun minValue(props: Properties, value: String): Properties {
            return props.copy(minValue = value.toFloatOrNull() ?: 0f)
        }

        /**
         * The max value for the range of values that this slider can take. The passed value will
         * be coerced to this range.
         * ```
         * <Slider maxValue="100" />
         * ```
         * @param value a float value to set the max value accepted by the slider.
         */
        private fun maxValue(props: Properties, value: String): Properties {
            return props.copy(maxValue = value.toFloatOrNull() ?: 1f)
        }

        /**
         * If greater than 0, specifies the amount of discrete allowable values, evenly distributed
         * across the whole value range. If 0, the slider will behave continuously and allow any
         * value from the range specified. Must not be negative.
         * ```
         * <Slider steps="5" />
         * ```
         * @param value an int value to define the number of steps the slider has.
         */
        private fun steps(props: Properties, value: String): Properties {
            return props.copy(steps = value.toIntOrNull() ?: 0)
        }

        /**
         * Set Slider colors.
         * ```
         * <Slider
         *   colors="{'thumbColor': '#FFFF0000', 'activeTrackColor': '#FF00FF00'}" />
         * ```
         * @param colors an JSON formatted string, containing the slider colors. The color keys
         * supported are: `thumbColor`, `activeTrackColor`, `activeTickColor, `inactiveTrackColor`,
         * `inactiveTickColor`, `disabledThumbColor`, `disabledActiveTrackColor`,
         * `disabledActiveTickColor`, `disabledInactiveTrackColor`, and `disabledInactiveTickColor`.
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        private fun handleValue(props: Properties, stringValue: String): Properties {
            return if (stringValue.contains(',')) {
                val range = try {
                    stringValue
                        .split(',')
                        .map { it.toFloat() }
                        .let { list ->
                            list[0]..list[1]
                        }
                } catch (e: Exception) {
                    props.minValue..props.maxValue
                }
                props.copy(
                    commonProps = setPhxValueFromAttr(
                        props.commonProps,
                        attrPhxValue,
                        floatArrayOf(range.start, range.endInclusive)
                    )
                )
            } else {
                props.copy(
                    commonProps = setPhxValueFromAttr(
                        props.commonProps,
                        attrPhxValue,
                        stringValue.toFloatOrNull() ?: 0f
                    )
                )
            }
        }
    }
}
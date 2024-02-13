package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrClip
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrMinValue
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrSteps
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledActiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledInactiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledThumbColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveTickColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInactiveTrackColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrThumbColor
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.Templates.templateStartThumb
import org.phoenixframework.liveview.data.constants.Templates.templateThumb
import org.phoenixframework.liveview.data.constants.Templates.templateTrack
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.rangeSlider
import org.phoenixframework.liveview.domain.base.ComposableTypes.slider

@OptIn(ExperimentalMaterial3Api::class)
class SliderShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSliderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Slider(state = remember { SliderState(value = 0f) })
                    Slider(state = remember { SliderState(value = 0.5f) })
                    Slider(state = remember { SliderState(value = 1f) })
                    Slider(state = remember { SliderState(value = 0.6f) }, enabled = false)
                    Slider(state = remember { SliderState(value = 0.2f, steps = 5) })
                    Slider(state = remember { SliderState(value = 40f, valueRange = 0f..100f) })
                    Slider(state = remember {
                        SliderState(
                            value = 40f,
                            steps = 5,
                            valueRange = 0f..100f
                        )
                    })
                }
            },
            template = """
                <$column>
                  <$slider $attrPhxValue="0" />
                  <$slider $attrPhxValue="0.5" />
                  <$slider $attrPhxValue="1" />
                  <$slider $attrPhxValue="0.6" $attrEnabled="false" />
                  <$slider $attrPhxValue="0.2" $attrSteps="5" />
                  <$slider $attrPhxValue="40" $attrMinValue="0" $attrMaxValue="100" />
                  <$slider $attrPhxValue="40" $attrSteps="5" $attrMinValue="0" $attrMaxValue="100" />
                </$column>
                """
        )
    }

    @Test
    fun sliderWithCustomColors() {
        val colorsForTemplate = """ 
            {
            '$colorAttrThumbColor': '#FFFFFF00',
            '$colorAttrActiveTrackColor': '#FFFF0000',  
            '$colorAttrActiveTickColor': '#FF00FF00', 
            '$colorAttrInactiveTrackColor': '#FF0000FF',
            '$colorAttrInactiveTickColor': '#FFFF00FF',
            '$colorAttrDisabledThumbColor': '#FF444444',
            '$colorAttrDisabledActiveTrackColor': '#FF888888', 
            '$colorAttrDisabledActiveTickColor': '#FF000000',
            '$colorAttrDisabledInactiveTrackColor': '#FFCCCCCC',
            '$colorAttrDisabledInactiveTickColor': '#FFFFFFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTrackColor = Color.Red,
                    activeTickColor = Color.Green,
                    inactiveTrackColor = Color.Blue,
                    inactiveTickColor = Color.Magenta,
                    disabledThumbColor = Color.DarkGray,
                    disabledActiveTrackColor = Color.Gray,
                    disabledActiveTickColor = Color.Black,
                    disabledInactiveTrackColor = Color.LightGray,
                    disabledInactiveTickColor = Color.White
                )
                Column {
                    Slider(
                        state = remember {
                            SliderState(value = 0.5f)
                        },
                        colors = colors,
                    )
                    Slider(
                        remember {
                            SliderState(value = 0.5f, steps = 4)
                        },
                        colors = colors,
                    )
                    Slider(
                        remember {
                            SliderState(value = 0.5f)
                        },
                        colors = colors, enabled = false,
                    )
                    Slider(
                        remember {
                            SliderState(value = 0.5f, steps = 4)
                        },
                        colors = colors,
                        enabled = false
                    )
                }
            },
            template = """
                <$column>
                  <$slider $attrPhxValue="0.5" $attrColors="$colorsForTemplate" />
                  <$slider $attrPhxValue="0.5" $attrColors="$colorsForTemplate" $attrSteps="4"/>
                  <$slider $attrPhxValue="0.5" $attrColors="$colorsForTemplate" $attrEnabled="false"/> 
                  <$slider $attrPhxValue="0.5" $attrColors="$colorsForTemplate" $attrSteps="4" $attrEnabled="false"/> 
                </$column>
                """
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun sliderWithCustomThumbAndTrack() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Slider(
                        value = 0.5f, onValueChange = {},
                        thumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Magenta)
                            )
                        },
                    )
                    Slider(
                        value = 0.5f, onValueChange = {},
                        track = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Green)
                            )
                        },
                    )
                    Slider(
                        value = 0.5f, onValueChange = {},
                        thumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Magenta)
                            )
                        },
                        track = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Green)
                            )
                        },
                    )
                }
            },
            template = """
                <$column>
                  <$slider $attrPhxValue="0.5">
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FFFF00FF" $attrTemplate="$templateThumb"/>
                  </$slider>
                  <$slider $attrPhxValue="0.5">
                    <$box 
                      $attrWidth="$fill" $attrHeight="10" $attrClip="4" 
                      $attrBackground="#FF00FF00" $attrTemplate="$templateTrack"/>
                  </$slider>                  
                  <$slider $attrPhxValue="0.5">
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FFFF00FF" $attrTemplate="$templateThumb"/>
                    <$box 
                      $attrWidth="$fill" $attrHeight="10" $attrClip="4" 
                      $attrBackground="#FF00FF00" $attrTemplate="$templateTrack"/>
                  </$slider>
                <$column>                                
                """
        )
    }

    @Test
    fun simpleRangeSliderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    RangeSlider(state = remember {
                        RangeSliderState(activeRangeStart = 0f, activeRangeEnd = 0.3f)
                    })
                    RangeSlider(state = remember {
                        RangeSliderState(activeRangeStart = 0.7f, activeRangeEnd = 1f)
                    })
                    RangeSlider(state = remember {
                        RangeSliderState(activeRangeStart = 0.4f, activeRangeEnd = 0.6f)
                    })
                    RangeSlider(state = remember {
                        RangeSliderState(activeRangeStart = 0.4f, activeRangeEnd = 0.6f)
                    }, enabled = false)
                    RangeSlider(state = remember {
                        RangeSliderState(activeRangeStart = 0.4f, activeRangeEnd = 0.6f, steps = 5)
                    })
                    RangeSlider(state = remember {
                        RangeSliderState(
                            activeRangeStart = 30f,
                            activeRangeEnd = 50f,
                            valueRange = 0f..100f
                        )
                    })
                    RangeSlider(state = remember {
                        RangeSliderState(
                            activeRangeStart = 40f,
                            activeRangeEnd = 70f,
                            steps = 5,
                            valueRange = 0f..100f
                        )
                    })
                }
            },
            template = """
                <$column>
                    <$rangeSlider $attrPhxValue="0.0,0.3" />
                    <$rangeSlider $attrPhxValue="0.7,1.0" />
                    <$rangeSlider $attrPhxValue="0.4,0.6" />
                    <$rangeSlider $attrPhxValue="0.4,0.6" $attrEnabled="false" />
                    <$rangeSlider $attrPhxValue="0.4,0.6" $attrSteps="5" />
                    <$rangeSlider $attrPhxValue="30,50" $attrMinValue="0" $attrMaxValue="100" />
                    <$rangeSlider $attrPhxValue="40,70" $attrSteps="5" $attrMinValue="0" $attrMaxValue="100" />
                </$column>
                """
        )
    }

    @Test
    fun rangeSliderWithCustomColors() {
        val colorsForTemplate = """ 
            {
            '$colorAttrThumbColor': '#FFFFFF00',
            '$colorAttrActiveTrackColor': '#FFFF0000',  
            '$colorAttrActiveTickColor': '#FF00FF00', 
            '$colorAttrInactiveTrackColor': '#FF0000FF',
            '$colorAttrInactiveTickColor': '#FFFF00FF',
            '$colorAttrDisabledThumbColor': '#FF444444',
            '$colorAttrDisabledActiveTrackColor': '#FF888888', 
            '$colorAttrDisabledActiveTickColor': '#FF000000',
            '$colorAttrDisabledInactiveTrackColor': '#FFCCCCCC',
            '$colorAttrDisabledInactiveTickColor': '#FFFFFFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTrackColor = Color.Red,
                    activeTickColor = Color.Green,
                    inactiveTrackColor = Color.Blue,
                    inactiveTickColor = Color.Magenta,
                    disabledThumbColor = Color.DarkGray,
                    disabledActiveTrackColor = Color.Gray,
                    disabledActiveTickColor = Color.Black,
                    disabledInactiveTrackColor = Color.LightGray,
                    disabledInactiveTickColor = Color.White
                )
                Column {
                    RangeSlider(state = remember {
                        RangeSliderState(
                            activeRangeStart = 0.1f,
                            activeRangeEnd = 0.6f,
                        )
                    }, colors = colors)
                    RangeSlider(state = remember {
                        RangeSliderState(
                            activeRangeStart = 0.4f,
                            activeRangeEnd = 0.7f,
                            steps = 4,
                        )
                    }, colors = colors)
                    RangeSlider(
                        state = remember {
                            RangeSliderState(
                                activeRangeStart = 0.5f,
                                activeRangeEnd = 0.9f,
                            )
                        },
                        colors = colors,
                        enabled = false
                    )
                    RangeSlider(
                        state = remember {
                            RangeSliderState(
                                activeRangeStart = 0.3f,
                                activeRangeEnd = 0.8f,
                                steps = 4,
                            )
                        },
                        colors = colors,
                        enabled = false
                    )
                }
            },
            template = """
                <$column>
                  <$rangeSlider $attrPhxValue="0.1,0.6" $attrColors="$colorsForTemplate" />
                  <$rangeSlider $attrPhxValue="0.4,0.7" $attrColors="$colorsForTemplate" $attrSteps="4"/>
                  <$rangeSlider $attrPhxValue="0.5,0.9" $attrColors="$colorsForTemplate" $attrEnabled="false"/> 
                  <$rangeSlider $attrPhxValue="0.3,0.8" $attrColors="$colorsForTemplate" $attrSteps="4" $attrEnabled="false"/> 
                </$column>
                """
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun rangeSliderWithCustomThumbAndTrack() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    RangeSlider(
                        value = 0.5f..0.7f, onValueChange = {},
                        startThumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Red)
                            )
                        },
                    )
                    RangeSlider(
                        value = 0.3f..0.6f, onValueChange = {},
                        startThumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Red)
                            )
                        },
                        track = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Green)
                            )
                        },
                    )
                    RangeSlider(
                        value = 0.4f..0.8f, onValueChange = {},
                        startThumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Red)
                            )
                        },
                        endThumb = {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Blue)
                            )
                        },
                        track = {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Green)
                            )
                        },
                    )
                }
            },
            template = """
                <$column>
                  <$rangeSlider $attrPhxValue="0.5,0.7f">
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FFFF0000" $attrTemplate="$templateStartThumb"/>
                  </$rangeSlider>
                  <$rangeSlider $attrPhxValue="0.3,0.6">
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FFFF0000" $attrTemplate="$templateStartThumb"/>
                    <$box 
                      $attrWidth="$fill" $attrHeight="10" $attrClip="4" 
                      $attrBackground="#FF00FF00" $attrTemplate="$templateTrack"/>
                  </$rangeSlider>                  
                  <$rangeSlider $attrPhxValue="0.4,0.8">
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FFFF0000" $attrTemplate="$templateStartThumb"/>
                    <$box 
                      $attrSize="40" $attrClip="4" 
                      $attrBackground="#FF0000FF" $attrTemplate="endThumb"/>
                    <$box 
                      $attrWidth="$fill" $attrHeight="10" $attrClip="4" 
                      $attrBackground="#FF00FF00" $attrTemplate="$templateTrack"/>
                  </$rangeSlider>
                <$column>                                
                """
        )
    }
}
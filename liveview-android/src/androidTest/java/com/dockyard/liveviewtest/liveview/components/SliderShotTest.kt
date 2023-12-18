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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class SliderShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSliderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Slider(value = 0f, onValueChange = {})
                    Slider(value = .5f, onValueChange = {})
                    Slider(value = 1f, onValueChange = {})
                    Slider(value = .6f, onValueChange = {}, enabled = false)
                    Slider(value = .2f, onValueChange = {}, steps = 5)
                    Slider(value = 40f, onValueChange = {}, valueRange = 0f..100f)
                    Slider(value = 40f, onValueChange = {}, steps = 5, valueRange = 0f..100f)
                }
            },
            template = """
                <Column>
                    <Slider phx-value="0" phx-change="" />
                    <Slider phx-value="0.5" phx-change="" />
                    <Slider phx-value="1" phx-change="" />
                    <Slider phx-value="0.6" phx-change="" enabled="false" />
                    <Slider phx-value="0.2" phx-change="" steps="5" />
                    <Slider phx-value="40" phx-change="" min-value="0" max-value="100" />
                    <Slider phx-value="40" phx-change="" steps="5" min-value="0" max-value="100" />
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun sliderWithCustomColors() {
        val colorsForTemplate = """ 
            {
                'thumbColor': '#FFFFFF00',
                'activeTrackColor': '#FFFF0000',  
                'activeTickColor': '#FF00FF00', 
                'inactiveTrackColor': '#FF0000FF',
                'inactiveTickColor': '#FFFF00FF',
                'disabledThumbColor': '#FF444444',
                'disabledActiveTrackColor': '#FF888888', 
                'disabledActiveTickColor': '#FF000000',
                'disabledInactiveTrackColor': '#FFCCCCCC',
                'disabledInactiveTickColor': '#FFFFFFFF'
            }
            """.trimIndent().trim().replace("\n", "")
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
                    Slider(value = 0.5f, onValueChange = {}, colors = colors)
                    Slider(value = 0.5f, onValueChange = {}, colors = colors, steps = 4)
                    Slider(value = 0.5f, onValueChange = {}, colors = colors, enabled = false)
                    Slider(
                        value = 0.5f,
                        onValueChange = {},
                        colors = colors,
                        steps = 4,
                        enabled = false
                    )
                }
            },
            template = """
                <Column>
                  <Slider phx-value="0.5" phx-change="" colors="$colorsForTemplate" />
                  <Slider phx-value="0.5" phx-change="" colors="$colorsForTemplate" steps="4"/>
                  <Slider phx-value="0.5" phx-change="" colors="$colorsForTemplate" enabled="false"/> 
                  <Slider phx-value="0.5" phx-change="" colors="$colorsForTemplate" steps="4" enabled="false"/> 
                </Column>
                """.templateToTest()
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
                <Column>
                  <Slider phx-value="0.5" phx-change="">
                    <Box size="40" clip="4" background="#FFFF00FF" template="thumb"/>
                  </Slider>
                  <Slider phx-value="0.5" phx-change="">
                    <Box width="fill" height="10" clip="4" background="#FF00FF00" template="track"/>
                  </Slider>                  
                  <Slider phx-value="0.5" phx-change="">
                    <Box size="40" clip="4" background="#FFFF00FF" template="thumb"/>
                    <Box width="fill" height="10" clip="4" background="#FF00FF00" template="track"/>
                  </Slider>
                <Column>                                
                """.templateToTest()
        )
    }

    @Test
    fun simpleRangeSliderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    RangeSlider(value = 0f..0.3f, onValueChange = {})
                    RangeSlider(value = 0.7f..1.0f, onValueChange = {})
                    RangeSlider(value = 0.4f..0.6f, onValueChange = {})
                    RangeSlider(value = 0.4f..0.6f, onValueChange = {}, enabled = false)
                    RangeSlider(value = 0.4f..0.6f, onValueChange = {}, steps = 5)
                    RangeSlider(value = 30f..50f, onValueChange = {}, valueRange = 0f..100f)
                    RangeSlider(
                        value = 40f..70f,
                        onValueChange = {},
                        steps = 5,
                        valueRange = 0f..100f
                    )
                }
            },
            template = """
                <Column>
                    <RangeSlider phx-value="0.0,0.3" phx-change="" />
                    <RangeSlider phx-value="0.7,1.0" phx-change="" />
                    <RangeSlider phx-value="0.4,0.6" phx-change="" />
                    <RangeSlider phx-value="0.4,0.6" phx-change="" enabled="false" />
                    <RangeSlider phx-value="0.4,0.6" phx-change="" steps="5" />
                    <RangeSlider phx-value="30,50" phx-change="" min-value="0" max-value="100" />
                    <RangeSlider phx-value="40,70" phx-change="" steps="5" min-value="0" max-value="100" />
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun rangeSliderWithCustomColors() {
        val colorsForTemplate = """ 
            {
                'thumbColor': '#FFFFFF00',
                'activeTrackColor': '#FFFF0000',  
                'activeTickColor': '#FF00FF00', 
                'inactiveTrackColor': '#FF0000FF',
                'inactiveTickColor': '#FFFF00FF',
                'disabledThumbColor': '#FF444444',
                'disabledActiveTrackColor': '#FF888888', 
                'disabledActiveTickColor': '#FF000000',
                'disabledInactiveTrackColor': '#FFCCCCCC',
                'disabledInactiveTickColor': '#FFFFFFFF'
            }
            """.trimIndent().trim().replace("\n", "")
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
                    RangeSlider(value = 0.1f..0.6f, onValueChange = {}, colors = colors)
                    RangeSlider(value = 0.4f..0.7f, onValueChange = {}, colors = colors, steps = 4)
                    RangeSlider(
                        value = 0.5f..0.9f,
                        onValueChange = {},
                        colors = colors,
                        enabled = false
                    )
                    RangeSlider(
                        value = 0.3f..0.8f,
                        onValueChange = {},
                        colors = colors,
                        steps = 4,
                        enabled = false
                    )
                }
            },
            template = """
                <Column>
                  <RangeSlider phx-value="0.1,0.6" phx-change="" colors="$colorsForTemplate" />
                  <RangeSlider phx-value="0.4,0.7" phx-change="" colors="$colorsForTemplate" steps="4"/>
                  <RangeSlider phx-value="0.5,0.9" phx-change="" colors="$colorsForTemplate" enabled="false"/> 
                  <RangeSlider phx-value="0.3,0.8" phx-change="" colors="$colorsForTemplate" steps="4" enabled="false"/> 
                </Column>
                """.templateToTest()
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
                <Column>
                  <RangeSlider phx-value="0.5,0.7f" phx-change="">
                    <Box size="40" clip="4" background="#FFFF0000" template="startThumb"/>
                  </RangeSlider>
                  <RangeSlider phx-value="0.3,0.6" phx-change="">
                    <Box size="40" clip="4" background="#FFFF0000" template="startThumb"/>
                    <Box width="fill" height="10" clip="4" background="#FF00FF00" template="track"/>
                  </RangeSlider>                  
                  <RangeSlider phx-value="0.4,0.8" phx-change="">
                    <Box size="40" clip="4" background="#FFFF0000" template="startThumb"/>
                    <Box size="40" clip="4" background="#FF0000FF" template="endThumb"/>
                    <Box width="fill" height="10" clip="4" background="#FF00FF00" template="track"/>
                  </RangeSlider>
                <Column>                                
                """.templateToTest()
        )
    }
}
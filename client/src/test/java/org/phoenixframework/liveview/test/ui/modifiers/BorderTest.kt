package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.phoenixframework.liveview.test.base.ModifierBaseTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BorderTest : ModifierBaseTest() {

    @Test
    fun borderWithColorTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorTest" => [
                {:border, [], [{:., [], [1.5, :dp]}, {:., [], [:Color, :Red]}]},
            ]}
            """,
            Modifier.border(1.5.dp, Color.Red)
        )
    }

    @Test
    fun borderWithColorNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorNamedParamsTest" => [
                {:border, [], [[width: {:., [], [2, :dp]}, color: {:., [], [:Color, :Red]}]]},
            ]}
            """,
            Modifier.border(2.dp, Color.Red)
        )
    }

    @Test
    fun borderWithColorAndShapeTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorAndShapeTest" => [
                {:border, [], [{:., [], [1, :dp]}, {:., [], [:Color, :Green]}, :CircleShape]},
            ]}
            """,
            Modifier.border(1.dp, Color.Green, CircleShape)
        )
    }

    @Test
    fun borderWithColorAndShapeNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorAndShapeNamedParamsTest" => [
                {:border, [], [[width: {:., [], [1, :dp]}, color: {:., [], [:Color, :Green]}, shape: :CircleShape]]},
            ]}
            """,
            Modifier.border(1.dp, Color.Green, CircleShape)
        )
    }

    @Test
    fun borderWithBorderStrokeTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeTest" => [
                {:border, [], [{:BorderStroke, [], [
                    {:Dp, [], [3]}, 
                    {:., [], [:Color, :Blue]}]} 
                ]},
            ]}
            """.trimStyle(),
            Modifier.border(BorderStroke(3.dp, Color.Blue))
        )
    }

    @Test
    fun borderWithBorderStrokeNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeNamedParamsTest" => [
                {:border, [], [[
                    border: {:BorderStroke, [], [
                        {:Dp, [], [3]}, 
                        {:., [], [:Color, :Blue]}
                    ]}
                ]]}
            ]}
            """.trimStyle(),
            Modifier.border(BorderStroke(3.dp, Color.Blue))
        )
    }

    @Test
    fun borderWithBorderStrokeAndShapeTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeAndShapeTest" => [
                {:border, [], [{:BorderStroke, [], [
                    {:Dp, [], [3]}, 
                    {:., [], [:Color, :Blue]}]}, 
                    {:., [], [:CircleShape]}
                ]},
            ]}
            """.trimStyle(),
            Modifier.border(BorderStroke(3.dp, Color.Blue), CircleShape)
        )
    }

    @Test
    fun borderWithBorderStrokeAndShapeNamedTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeAndShapeTest" => [
                {:border, [], [[
                    shape: {:., [], [:CircleShape]}, 
                    border: {:BorderStroke, [], [{:Dp, [], [3]}, {:., [], [:Color, :Blue]}]}
                ]]},
            ]}
            """.trimStyle(),
            Modifier.border(BorderStroke(3.dp, Color.Blue), CircleShape)
        )
    }

    @Test
    fun borderWithBrushHorizontalGradient() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushHorizontalGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:horizontalGradient, [], [
                            [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ],
                            0.0,
                            500.0,
                            {:., [], [:TileMode, :Clamp]}
                        ]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),
            Modifier.border(
                2.dp,
                Brush.horizontalGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    startX = 0f,
                    endX = 500f,
                    tileMode = TileMode.Clamp,
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushHorizontalGradientNamed() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushHorizontalGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:horizontalGradient, [], [[
                            tileMode: {:., [], [:TileMode, :Clamp]},
                            colors: [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ],                            
                            startX: 0.0,
                            endX: 500.0
                        ]]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),
            Modifier.border(
                2.dp,
                Brush.horizontalGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    startX = 0f,
                    endX = 500f,
                    tileMode = TileMode.Clamp,
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushVerticalGradient() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushVerticalGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:verticalGradient, [], [
                            [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ],
                            0.0,
                            500.0,
                            {:., [], [:TileMode, :Decal]}
                        ]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    startY = 0f,
                    endY = 500f,
                    tileMode = TileMode.Decal,
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushVerticalGradientNamed() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushVerticalGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:verticalGradient, [], [[
                            startY: 0.0,
                            endY: 500.0,
                            tileMode: {:., [], [:TileMode, :Decal]},                        
                            colors: [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ]
                        ]]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    startY = 0f,
                    endY = 500f,
                    tileMode = TileMode.Decal,
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushLinearGradient() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushLinearGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:linearGradient, [], [
                            [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            {:Offset, [], [1, 2]},
                            {:Offset, [], [3, 4]},
                            {:., [], [:TileMode, :Mirror]}
                        ]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    tileMode = TileMode.Mirror,
                    start = Offset(1f, 2f),
                    end = Offset(3f, 4f),
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushLinearGradientNamed() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushLinearGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:linearGradient, [], [[
                            start: {:Offset, [], [1, 2]},
                            end: {:Offset, [], [3, 4]},                        
                            colors: [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            tileMode: {:., [], [:TileMode, :Mirror]}
                        ]]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.linearGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    tileMode = TileMode.Mirror,
                    start = Offset(1f, 2f),
                    end = Offset(3f, 4f),
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushRadialGradient() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushLinearGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:radialGradient, [], [
                            [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            {:Offset, [], [10, 20]},
                            50.0,
                            {:., [], [:TileMode, :Repeated]}
                        ]}
                    ]},
                    shape:  {:RoundedCornerShape, [], [{:Dp, [], [12]}]}
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.radialGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    center = Offset(10f, 20f),
                    radius = 50f,
                    tileMode = TileMode.Repeated,
                ),
                RoundedCornerShape(Dp(12f))
            )
        )
    }

    @Test
    fun borderWithBrushRadialGradientNamed() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushLinearGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:radialGradient, [], [[
                            colors: [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            center: {:Offset, [], [10, 20]},
                            radius: 50.0,
                            tileMode: {:., [], [:TileMode, :Repeated]}
                        ]]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.radialGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    center = Offset(10f, 20f),
                    radius = 50f,
                    tileMode = TileMode.Repeated,
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushSweepGradient() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushSweepGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:sweepGradient, [], [
                            [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            {:Offset, [], [10, 20]}
                        ]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.sweepGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    center = Offset(10f, 20f),
                ),
                CircleShape
            )
        )
    }

    @Test
    fun borderWithBrushSweepGradientNamed() {
        assertModifierFromStyle(
            """
            %{"borderWithBrushSweepGradient" => [{
                :border, [], [[
                    width: {:., [], [2, :dp]},
                    brush: {:., [], [
                        :Brush,
                        {:sweepGradient, [], [[
                            colors: [
                                {:., [], [:Color, :Red]},
                                {:., [], [:Color, :Blue]},
                                {:., [], [:Color, :Green]}
                            ], 
                            center: {:Offset, [], [10, 20]}
                        ]]}
                    ]},
                    shape: :CircleShape
                ]]
            }]}
            """.trimStyle(),

            Modifier.border(
                2.dp,
                Brush.sweepGradient(
                    colors = listOf(Color.Red, Color.Blue, Color.Green),
                    center = Offset(10f, 20f),
                ),
                CircleShape
            )
        )
    }
}
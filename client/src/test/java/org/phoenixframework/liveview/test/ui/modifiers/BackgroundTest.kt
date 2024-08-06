package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.test.base.ModifierBaseTest

@RunWith(AndroidJUnit4::class)
class BackgroundTest : ModifierBaseTest() {

    @Test
    fun backgroundSystemColorTest() {
        assertModifierFromStyle(
            """
            %{"backgroundSystemColorTest" => [
              {:background, [], [{:., [], [:Color, :Red]} ]},
              ]}
            """,
            Modifier.background(Color.Red)
        )
    }

    @Test
    fun backgroundWithArgbColorTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithArgbColorTest" => [
              {:background, [], [{:Color, [], [255, 255, 0, 0]} ]},
              ]}
            """,
            Modifier.background(Color(255, 255, 0, 0))
        )
    }

    @Test
    fun backgroundWithSingleIntColorTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithSingleIntColorTest" => [
              {:background, [], [{:Color, [], [-16711732]} ]},
              ]}
            """,
            Modifier.background(Color(-16711732))
        )
    }

    @Test
    fun backgroundWithNamedArgbColorArgsTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithNamedArgbColorArgsTest" => [
              {:background, [], [[color: {:Color, [], [255, 255, 0, 0]} ]]},
            ]}
            """,
            Modifier.background(Color(255, 255, 0, 0))
        )
    }

    @Test
    fun backgroundWithArgbColorNamedArgsTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithArgbColorNamedArgsTest" => [
              {:background, [], [{:Color, [], [[red: 255, green: 255, blue: 0, alpha: 0]]} ]},
              ]}
            """,
            Modifier.background(Color(255, 255, 0, 0))
        )
    }

    @Test
    fun backgroundWithCircleShapeTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithCircleShapeTest" => [
              {:background, [], [{:., [], [:Color, :Red]}, {:., [], [:CircleShape]} ]},
              ]}
            """,
            Modifier.background(Color.Red, CircleShape)
        )
    }

    @Test
    fun backgroundWithShapeTest() {
        assertModifierFromStyle(
            """
            %{"backgroundWithShapeTest" => [
              {:background, [], [
                {:., [], [:Color, :Red]}, 
                {:RoundedCornerShape, [], [
                  {:., [], [4, :dp]}, 
                  {:., [], [24, :dp]}, 
                  {:., [], [8, :dp]}, 
                  {:., [], [16, :dp]}
                ]} 
              ]},
            ]}
            """,
            Modifier.background(Color.Red, RoundedCornerShape(4.dp, 24.dp, 8.dp, 16.dp))
        )
    }

    @Test
    fun backgroundWithColorAndShapeNamedArgs() {
        assertModifierFromStyle(
            """
            %{"backgroundWithColorAndShapeNamedArgs" => [
              {:background, [], [[color: {:., [], [:Color, :Red]}, shape: {:., [], [:CircleShape]}] ]},
              ]}
            """,
            Modifier.background(Color.Red, CircleShape)
        )
    }

    @Test
    fun backgroundWithBrush() {
        assertModifierFromStyle(
            """
            %{"backgroundWithColorAndShapeNamedArgs" => [
              {:background, [], [[
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
                shape: {:., [], [:CircleShape]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.background(
                Brush.sweepGradient(
                    listOf(Color.Red, Color.Blue, Color.Green),
                    Offset(10f, 20f)
                ), CircleShape
            )
        )
    }

    @Test
    fun backgroundWithBrushNamed() {
        assertModifierFromStyle(
            """
            %{"backgroundWithColorAndShapeNamedArgs" => [
              {:background, [], [[
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
                shape: {:., [], [:CircleShape]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.background(
                Brush.sweepGradient(
                    listOf(Color.Red, Color.Blue, Color.Green),
                    Offset(10f, 20f)
                ), CircleShape
            )
        )
    }
}
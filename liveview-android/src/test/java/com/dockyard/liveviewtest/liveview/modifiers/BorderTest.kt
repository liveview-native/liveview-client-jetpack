package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BorderTest : ModifierBaseTest() {

    @Test
    fun borderWithColorTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorTest" => [
                {:border, [], [2, {:., [], [:Color, :Red]}]},
            ]}
            """,
            Modifier.border(2.dp, Color.Red)
        )
    }

    @Test
    fun borderWithColorNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"borderWithColorNamedParamsTest" => [
                {:border, [], [[width: 2, color: {:., [], [:Color, :Red]}]]},
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
                {:border, [], [1, {:., [], [:Color, :Green]}, {:., [], [:CircleShape]}]},
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
                {:border, [], [[width: 1, color: {:., [], [:Color, :Green]}, shape: {:., [], [:CircleShape]}]]},
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
                {:border, [], [{:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]} ]},
            ]}
            """,
            Modifier.border(BorderStroke(3.dp, Color.Blue))
        )
    }

    @Test
    fun borderWithBorderStrokeNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeNamedParamsTest" => [
                {:border, [], [[border: [{:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]} ]]]},
            ]}
            """,
            Modifier.border(BorderStroke(3.dp, Color.Blue))
        )
    }

    @Test
    fun borderWithBorderStrokeAndShapeTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeAndShapeTest" => [
                {:border, [], [{:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]}, {:., [], [:CircleShape]}]},
            ]}
            """,
            Modifier.border(BorderStroke(3.dp, Color.Blue), CircleShape)
        )
    }

    @Test
    fun borderWithBorderStrokeAndShapeNamedTest() {
        assertModifierFromStyle(
            """
            %{"borderWithBorderStrokeAndShapeTest" => [
                {:border, [], [[shape: {:., [], [:CircleShape]}, border: {:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]}]]},
            ]}
            """,
            Modifier.border(BorderStroke(3.dp, Color.Blue), CircleShape)
        )
    }
}
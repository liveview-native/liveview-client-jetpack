package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class BorderTest {
    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    @Test
    fun borderWithColorTest() {
        val style = """
            %{"borderWithColorTest" => [
                {:border, [], [2, {:., [], [:Color, :Red]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(2.dp, Color.Red))
        TestCase.assertEquals(result, modifier)
    }

    @Test
    fun borderWithColorNamedParamsTest() {
        val style = """
            %{"borderWithColorNamedParamsTest" => [
                {:border, [], [[width: 2, color: {:., [], [:Color, :Red]}]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(2.dp, Color.Red))
        TestCase.assertEquals(result, modifier)
    }

    @Test
    fun borderWithColorAndShapeTest() {
        val style = """
            %{"borderWithColorAndShapeTest" => [
                {:border, [], [1, {:., [], [:Color, :Green]}, {:., [], [:CircleShape]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(1.dp, Color.Green, CircleShape))
        TestCase.assertEquals(result, modifier)
    }

    @Test
    fun borderWithColorAndShapeNamedParamsTest() {
        val style = """
            %{"borderWithColorAndShapeNamedParamsTest" => [
                {:border, [], [[width: 1, color: {:., [], [:Color, :Green]}, shape: {:., [], [:CircleShape]}]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(1.dp, Color.Green, CircleShape))
        TestCase.assertEquals(result, modifier)
    }

    @Test
    fun borderWithBorderStrokeTest() {
        val style = """
            %{"borderWithBorderStrokeTest" => [
                {:border, [], [{:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]} ]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(BorderStroke(3.dp, Color.Blue)))
        TestCase.assertEquals(result, modifier)
    }

    @Test
    fun borderWithBorderStrokeNamedParamsTest() {
        val style = """
            %{"borderWithBorderStrokeNamedParamsTest" => [
                {:border, [], [[border: [{:BorderStroke, [], [3, {:., [], [:Color, :Blue]}]} ]]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.border(BorderStroke(3.dp, Color.Blue)))
        TestCase.assertEquals(result, modifier)
    }
}
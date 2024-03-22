package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class BackgroundTest {
    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    @Test
    fun backgroundSystemColorTest() {
        val style = """
            %{"backgroundSystemColorTest" => [
              {:background, [], [{:., [], [:Color, :Red]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.background(Color.Red))
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithArgbColorTest() {
        val style = """
            %{"backgroundWithArgbColorTest" => [
              {:background, [], [{:Color, [], [255, 255, 0, 0]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color(255, 255, 0, 0))
        )
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithSingleIntColorTest() {
        val style = """
            %{"backgroundWithSingleIntColorTest" => [
              {:background, [], [{:Color, [], [-16711732]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.background(Color(-16711732)))
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithNamedArgbColorArgsTest() {
        val style = """
            %{"backgroundWithNamedArgbColorArgsTest" => [
              {:background, [], [[color: {:Color, [], [255, 255, 0, 0]} ]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color(255, 255, 0, 0))
        )
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithArgbColorNamedArgsTest() {
        val style = """
            %{"backgroundWithArgbColorNamedArgsTest" => [
              {:background, [], [{:Color, [], [[red: 255, green: 255, blue: 0, alpha: 0]]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color(255, 255, 0, 0))
        )
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithCircleShapeTest() {
        val style = """
            %{"backgroundWithCircleShapeTest" => [
              {:background, [], [{:., [], [:Color, :Red]}, {:., [], [:CircleShape]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color.Red, CircleShape)
        )
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithShapeTest() {
        val style = """
            %{"backgroundWithShapeTest" => [
              {:background, [], [{:., [], [:Color, :Red]}, {:RoundedCornerShape, [], [4, 0, 8, 16]} ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color.Red, RoundedCornerShape(4.dp, 0.dp, 8.dp, 16.dp))
        )
        assertEquals(result, modifier)
    }

    @Test
    fun backgroundWithColorAndShapeNamedArgs() {
        val style = """
            %{"backgroundWithColorAndShapeNamedArgs" => [
              {:background, [], [[color: {:., [], [:Color, :Red]},  shape: {:., [], [:CircleShape]}] ]},
              ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.background(Color.Red, CircleShape)
        )
        assertEquals(result, modifier)
    }
}
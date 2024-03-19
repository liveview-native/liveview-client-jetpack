package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class DrawingTest {
    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    @Test
    fun clipTest() {
        val style = """
            %{"clipTest" => [
                {:clip, [], [{:RoundedCornerShape, [], [16]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.clip(RoundedCornerShape(16.dp)))
        assertEquals(result, modifier)
    }

    @Test
    fun clipTestWith4Corners() {
        val style = """
            %{"clipTestWith4Corners" => [
                {:clip, [], [{:RoundedCornerShape, [], [4, 8, 16, 32]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.clip(RoundedCornerShape(4.dp, 8.dp, 16.dp, 32.dp)))
        assertEquals(result, modifier)
    }

    @Test
    fun clipCircleTest() {
        val style = """
            %{"clipCircleTest" => [
                {:clip, [], [{:., [], [:CircleShape]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.clip(CircleShape))
        assertEquals(result, modifier)
    }

    @Test
    fun clipRectTest() {
        val style = """
            %{"clipRectTest" => [
                {:clip, [], [{:., [], [:RectangleShape]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.clip(RectangleShape))
        assertEquals(result, modifier)
    }

    @Test
    fun clipRoundedCornerWith2Args() {
        val style = """
            %{"clipRoundedCornerWith2Args" => [
                {:clip, [], [{:RoundedCornerShape, [], [[topStart: 12, bottomEnd: 32]]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier =
            Modifier.then(Modifier.clip(RoundedCornerShape(topStart = 12.dp, bottomEnd = 32.dp)))
        assertEquals(result, modifier)
    }

    @Test
    fun clipRoundedCornerWithNamedArgs() {
        val style = """
            %{"clipRoundedCornerWithNamedArgs" => [
                {:clip, [], [{:RoundedCornerShape, [], [[topStart: 4, topEnd: 8, bottomStart: 16, bottomEnd: 32]]}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(
            Modifier.clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 8.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 32.dp
                )
            )
        )
        assertEquals(result, modifier)
    }
}
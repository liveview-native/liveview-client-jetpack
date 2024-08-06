package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.phoenixframework.liveview.test.base.ModifierBaseTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModifiersParserTest : ModifierBaseTest() {

    @Test
    fun parseEmptyFileTest() {
        modifiersParser.fromStyleFile("")
        assert(modifiersParser.isEmpty)
    }

    @Test
    fun parseFileWithEmptyMapTest() {
        modifiersParser.fromStyleFile("%{}")
        assert(modifiersParser.isEmpty)
    }

    // The modifiers has tests in separate files. So it's just running a simple test here.
    @Test
    fun parseSimpleModifierTest() {
        val style = """%{
            "style1" => [
              {:background, [], [{:Color, [], [255, 255, 0, 255]}]}, 
            ], 
            "style2" => [
              {:size, [], [{:Dp, [], [150.0]}]}, 
              {:padding, [], [{:Dp, [], [20]}]}
            ]
        }""".trimStyle()
        modifiersParser.fromStyleFile(style)
        val style1 = modifiersParser.run { Modifier.fromStyleName("style1") }
        assertEquals(style1, Modifier.background(Color(255, 255, 0, 255)))
        val style2 = modifiersParser.run { Modifier.fromStyleName("style2") }
        assertEquals(
            style2,
            Modifier
                .size(Dp(150f))
                .padding(Dp(20f)),
        )
    }
}
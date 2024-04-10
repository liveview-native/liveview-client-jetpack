package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransformationTest : ModifierBaseTest() {
    @Test
    fun rotateTest() {
        assertModifierFromStyle(
            """
            %{"rotateTest" => [
                {:rotate, [], [180]},
            ]}
            """,
            Modifier.rotate(180f)
        )
    }

    @Test
    fun rotateNamedTest() {
        assertModifierFromStyle(
            """
            %{"rotateNamedTest" => [
                {:rotate, [], [[degrees: 180]]},
            ]}
            """,
            Modifier.rotate(degrees = 180f)
        )
    }

    @Test
    fun scaleTest() {
        assertModifierFromStyle(
            """
            %{"scaleTest" => [
                {:scale, [], [2.5]},
            ]}
            """,
            Modifier.scale(2.5f)
        )
    }

    @Test
    fun scaleNamedTest() {
        assertModifierFromStyle(
            """
            %{"scaleNamedTest" => [
                {:scale, [], [[scale: 1.5]]},
            ]}
            """,
            Modifier.scale(scale = 1.5f)
        )
    }

    @Test
    fun scaleXYTest() {
        assertModifierFromStyle(
            """
            %{"scaleXYTest" => [
                {:scale, [], [2.5, 1.0]},
            ]}
            """,
            Modifier.scale(2.5f, 1.0f)
        )
    }

    @Test
    fun scaleXYNamedTest() {
        assertModifierFromStyle(
            """
            %{"scaleXYNamedTest" => [
                {:scale, [], [[scaleX: 2.5, scaleY: 1.0]]},
            ]}
            """,
            Modifier.scale(scaleX = 2.5f, scaleY = 1.0f)
        )
    }
}
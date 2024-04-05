package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LayoutTest : ModifierBaseTest() {

    @Test
    fun layoutIdStringTest() {
        assertModifierFromStyle(
            """
            %{"layoutIdTest" => [
                {:layoutId, [], ["test"]},
            ]}
            """,
            Modifier.layoutId("test")
        )
    }

    @Test
    fun layoutIdIntTest() {
        assertModifierFromStyle(
            """
            %{"layoutIdTest" => [
                {:layoutId, [], [10]},
            ]}
            """,
            Modifier.layoutId(10)
        )
    }

    @Test
    fun layoutIdFloatTest() {
        assertModifierFromStyle(
            """
            %{"layoutIdTest" => [
                {:layoutId, [], [10.0]},
            ]}
            """,
            Modifier.layoutId(10.0f)
        )
    }

    @Test
    fun layoutIdBooleanTest() {
        assertModifierFromStyle(
            """
            %{"layoutIdTest" => [
                {:layoutId, [], [true]},
            ]}
            """,
            Modifier.layoutId(true)
        )
    }
}
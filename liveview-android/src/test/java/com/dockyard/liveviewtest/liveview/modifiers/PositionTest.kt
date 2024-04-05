package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PositionTest : ModifierBaseTest() {

    @Test
    fun offsetTest() {
        assertModifierFromStyle(
            """
            %{"offsetTest" => [
                {:offset, [], [{:., [], [10, :dp]}, {:., [], [20, :dp]}]},
            ]}
            """,
            Modifier.offset(10.dp, 20.dp)
        )
    }

    @Test
    fun offsetNamedTest() {
        assertModifierFromStyle(
            """
            %{"offsetNamedTest" => [
                {:offset, [], [[y: {:., [], [20, :dp]}, x: {:., [], [10, :dp]}]]},
            ]}
            """,
            Modifier.offset(y = 20.dp, x = 10.dp)
        )
    }

    @Test
    fun absoluteOffsetTest() {
        assertModifierFromStyle(
            """
            %{"absoluteOffsetTest" => [
                {:absoluteOffset, [], [{:., [], [10, :dp]}, {:., [], [20, :dp]}]},
            ]}
            """,
            Modifier.absoluteOffset(10.dp, 20.dp)
        )
    }

    @Test
    fun absoluteOffsetNamedTest() {
        assertModifierFromStyle(
            """
            %{"absoluteOffsetNamedTest" => [
                {:absoluteOffset, [], [[y: {:., [], [20, :dp]}, x: {:., [], [10, :dp]}]]},
            ]}
            """,
            Modifier.absoluteOffset(y = 20.dp, x = 10.dp)
        )
    }
}
package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PaddingTest : ModifierBaseTest() {

    @Test
    fun paddingAllTest() {
        assertModifierFromStyle(
            """
            %{"paddingAllTest" => [
                {:padding, [], [32]},
            ]}
            """,
            Modifier.padding(32.dp)
        )
    }

    @Test
    fun paddingAllNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingAllNamedTest" => [
                {:padding, [], [[all: 32]]},
            ]}
            """,
            Modifier.padding(32.dp)
        )
    }

    @Test
    fun paddingHorizontalTest() {
        assertModifierFromStyle(
            """
            %{"paddingHorizontalTest" => [
                {:padding, [], [[horizontal: 16]]},
            ]}
            """,
            Modifier.padding(horizontal = 16.dp)
        )
    }

    @Test
    fun paddingVerticalTest() {
        assertModifierFromStyle(
            """
            %{"paddingVerticalTest" => [
                {:padding, [], [[vertical: 8]]},
            ]}
            """,
            Modifier.padding(vertical = 8.dp)
        )
    }

    @Test
    fun paddingHVTest() {
        assertModifierFromStyle(
            """
            %{"paddingHVTest" => [
                {:padding, [], [8, 16]},
            ]}
            """,
            Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }

    @Test
    fun paddingHVNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingHVNamedTest" => [
                {:padding, [], [[vertical: 16, horizontal: 8]]},
            ]}
            """,
            Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )
    }

    @Test
    fun paddingBordersTest() {
        assertModifierFromStyle(
            """
            %{"paddingBordersTest" => [
                {:padding, [], [4, 8, 16, 32]},
            ]}
            """,
            Modifier.padding(4.dp, 8.dp, 16.dp, 32.dp)
        )
    }

    @Test
    fun paddingBordersNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingBordersNamedTest" => [
                {:padding, [], [[_end: 16, bottom: 32, start: 4, top: 8]]},
            ]}
            """,
            Modifier.padding(end = 16.dp, bottom = 32.dp, start = 4.dp, top = 8.dp)
        )
    }
}
package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrawingTest : ModifierBaseTest() {

    @Test
    fun alphaTest() {
        assertModifierFromStyle(
            """
            %{"alphaTest" => [
                {:alpha, [], [0.5]},
            ]}
            """,
            Modifier.alpha(0.5f)
        )
    }

    @Test
    fun alphaNamedTest() {
        assertModifierFromStyle(
            """
            %{"alphaNamedTest" => [
                {:alpha, [], [[alpha: 0.5]]},
            ]}
            """,
            Modifier.alpha(0.5f)
        )
    }

    @Test
    fun clipTest() {
        assertModifierFromStyle(
            """
            %{"clipTest" => [
                {:clip, [], [{:RoundedCornerShape, [], [16]}]},
            ]}
            """,
            Modifier.clip(RoundedCornerShape(16.dp))
        )
    }

    @Test
    fun clipTestWith4Corners() {
        assertModifierFromStyle(
            """
            %{"clipTestWith4Corners" => [
                {:clip, [], [{:RoundedCornerShape, [], [4, 8, 16, 32]}]},
            ]}
            """,
            Modifier.clip(RoundedCornerShape(4.dp, 8.dp, 16.dp, 32.dp))
        )
    }

    @Test
    fun clipCircleTest() {
        assertModifierFromStyle(
            """
            %{"clipCircleTest" => [
                {:clip, [], [{:., [], [:CircleShape]}]},
            ]}
            """,
            Modifier.clip(CircleShape)
        )
    }

    @Test
    fun clipRectTest() {
        assertModifierFromStyle(
            """
            %{"clipRectTest" => [
                {:clip, [], [{:., [], [:RectangleShape]}]},
            ]}
            """,
            Modifier.clip(RectangleShape)
        )
    }

    @Test
    fun clipRoundedCornerWith2Args() {
        assertModifierFromStyle(
            """
            %{"clipRoundedCornerWith2Args" => [
                {:clip, [], [{:RoundedCornerShape, [], [[topStart: 12, bottomEnd: 32]]}]},
            ]}
            """,
            Modifier.clip(RoundedCornerShape(topStart = 12.dp, bottomEnd = 32.dp))
        )
    }

    @Test
    fun clipRoundedCornerWithNamedArgs() {
        assertModifierFromStyle(
            """
            %{"clipRoundedCornerWithNamedArgs" => [
                {:clip, [], [{:RoundedCornerShape, [], [[topStart: 4, topEnd: 8, bottomStart: 16, bottomEnd: 32]]}]},
            ]}
            """,
            Modifier.clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 8.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 32.dp
                )
            )
        )
    }
}
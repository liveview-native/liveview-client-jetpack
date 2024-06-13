package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dockyard.liveviewtest.liveview.util.ModifierBaseTest
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser.fromStyleName

@RunWith(AndroidJUnit4::class)
class DrawingTest : ModifierBaseTest() {

    //region Alpha Modifier Tests
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

    //endregion

    // region Clip Modifier Tests
    @Test
    fun clipTest() {
        assertModifierFromStyle(
            """
            %{"clipTest" => [
                {:clip, [], [{:RoundedCornerShape, [], [{:., [], [16, :dp]}]}]},
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
                {:clip, [], [
                  {:RoundedCornerShape, [], [
                    {:., [], [4, :dp]}, 
                    {:., [], [8, :dp]}, 
                    {:., [], [16, :dp]},
                    {:., [], [32, :dp]}
                  ]}
                ]},
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
                {:clip, [], [:CircleShape]},
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
                {:clip, [], [:RectangleShape]},
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
              {:clip, [], [
                {:RoundedCornerShape, [], [[
                  topStart: {:., [], [12, :dp]}, 
                  bottomEnd: {:., [], [32, :dp]}
                ]]}
              ]},
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
              {:clip, [], [
                {:RoundedCornerShape, [], [[
                  topStart: {:., [], [4, :dp]}, 
                  topEnd: {:., [], [8, :dp]}, 
                  bottomStart: {:., [], [16, :dp]}, 
                  bottomEnd: {:., [], [32, :dp]}
                ]]}
              ]},
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

    @Test
    fun clipToBoundsTest() {
        assertModifierFromStyle(
            """
            %{"clipToBoundsTest" => [
                {:clipToBounds, [], []},
            ]}
            """,
            Modifier.clipToBounds()
        )
    }

    //endregion

    @Test
    fun safeDrawingPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"safeDrawingPaddingTest" => [
                {:safeDrawingPadding, [], []},
            ]}
            """
        )
        val result = Modifier.fromStyleName("safeDrawingPaddingTest")
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun shadowTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"shadowTest" => [
                {:shadow, [], [[
                    elevation: 4,
                    shape: {:., [], [:CircleShape]},
                    clip: false,
                    ambientColor: {:., [], [:Color, :Blue]},
                    spotColor: {:., [], [:Color, :Green]}    
                ]]}
            ]}
            """,
        )
        val result = Modifier.fromStyleName("shadowTest")
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }


    //region Z-Index Modifier Tests
    @Test
    fun zIndexTest() {
        assertModifierFromStyle(
            """
            %{"zIndexTest" => [
                {:zIndex, [], [2]},
            ]}
            """,
            Modifier.zIndex(2f)
        )
    }

    @Test
    fun zIndexNamedTest() {
        assertModifierFromStyle(
            """
            %{"zIndexNamedTest" => [
                {:zIndex, [], [[zIndex: 0.5]]},
            ]}
            """,
            Modifier.zIndex(0.5f)
        )
    }
    //endregion
}
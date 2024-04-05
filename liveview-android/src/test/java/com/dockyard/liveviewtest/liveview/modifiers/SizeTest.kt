package com.dockyard.liveviewtest.liveview.modifiers

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class SizeTest : ModifierBaseTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun aspectRatioTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioTest" => [
                {:aspectRatio, [], [1.33]},
            ]}
            """,
            Modifier.aspectRatio(1.33f)
        )
    }

    @Test
    fun aspectRatioWithMatchConstraintsTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioWithMatchConstraintsTest" => [
                {:aspectRatio, [], [1.33, true]},
            ]}
            """,
            Modifier.aspectRatio(1.33f, true)
        )
    }

    @Test
    fun aspectRatioWithNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioWithNamedParamsTest" => [
                {:aspectRatio, [], [[matchHeightConstraintsFirst: true, ratio: 1.33]]},
            ]}
            """,
            Modifier.aspectRatio(1.33f, true)
        )
    }

    @Test
    fun fillMaxHeightTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightTest" => [
                {:fillMaxHeight, [], []},
            ]}
            """,
            Modifier.fillMaxHeight()
        )
    }

    @Test
    fun fillMaxHeightWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightWithFractionTest" => [
                {:fillMaxHeight, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxHeight(0.5f)
        )
    }

    @Test
    fun fillMaxHeightWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightWithFractionTest" => [
                {:fillMaxHeight, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxHeight(0.5f)
        )
    }

    @Test
    fun fillMaxSizeTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeTest" => [
                {:fillMaxSize, [], []},
            ]}
            """,
            Modifier.fillMaxSize()
        )
    }

    @Test
    fun fillMaxSizeWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeWithFractionTest" => [
                {:fillMaxSize, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxSize(0.5f)
        )
    }

    @Test
    fun fillMaxSizeWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeWithFractionNamedTest" => [
                {:fillMaxSize, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxSize(0.5f)
        )
    }

    @Test
    fun fillMaxWidthTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthTest" => [
                {:fillMaxWidth, [], []},
            ]}
            """,
            Modifier.fillMaxWidth()
        )
    }

    @Test
    fun fillMaxWidthWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthWithFractionTest" => [
                {:fillMaxWidth, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxWidth(0.5f)
        )
    }

    @Test
    fun fillMaxWidthWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthWithFractionTest" => [
                {:fillMaxWidth, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxWidth(0.5f)
        )
    }

    @Test
    fun heightDpTest() {
        assertModifierFromStyle(
            """
            %{"heightDpTest" => [
                {:height, [], [{:., [], [100, :dp]}]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightDpWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightDpWithInvalidValueMustFail" => [
                {:height, [], ["100"]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun heightDpNamed() {
        assertModifierFromStyle(
            """
            %{"heightDpNamed" => [
                {:height, [], [[height: {:., [], [100, :dp]}]]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightDpNamedWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightDpNamedWithInvalidValueMustFail" => [
                {:height, [], [[height: "100"]]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun heightInEmptyTest() {
        assertModifierFromStyle(
            """
            %{"heightInEmptyTest" => [
                {:heightIn, [], []},
            ]}
            """,
            Modifier.heightIn()
        )
    }

    @Test
    fun heightInTest() {
        assertModifierFromStyle(
            """
            %{"heightInTest" => [
                {:heightIn, [], [{:., [], [100, :dp]}, {:., [], [200, :dp]}]},
            ]}
            """,
            Modifier.heightIn(100.dp, 200.dp)
        )
    }

    @Test
    fun heightInNamedTest() {
        assertModifierFromStyle(
            """
            %{"heightInNamedTest" => [
              {:heightIn, [], [[
                min: {:., [], [100, :dp]}, 
                max: {:., [], [200, :dp]}
              ]]},
            ]}
            """,
            Modifier.heightIn(min = 100.dp, max = 200.dp)
        )
    }

    @Test
    fun heightIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"heightIntrinsicTest" => [
                {:height, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.height(IntrinsicSize.Min)
        )
    }

    @Test
    fun heightIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"heightIntrinsicTest" => [
                {:height, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Min]}]]},
            ]}
            """,
            Modifier.height(IntrinsicSize.Min)
        )
    }

    @Test
    fun matchParentSizeTest() {
        val style = """
            %{"boxAlignTest" => [
                {:matchParentSize, [], []},
            ]}
            """
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Box {
                result = Modifier.then(Modifier.fromStyle(style, this))
                modifier = Modifier.then(Modifier.matchParentSize())
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun sizeTest() {
        assertModifierFromStyle(
            """
            %{"sizeTest" => [
                {:size, [], [{:., [], [50,:dp]}]},
            ]}
            """,
            Modifier.size(50.dp)
        )
    }

    @Test
    fun sizeNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeTest" => [
                {:size, [], [[size: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.size(50.dp)
        )
    }

    @Test
    fun sizeWHTest() {
        assertModifierFromStyle(
            """
            %{"sizeWHTest" => [
                {:size, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.size(50.dp, 100.dp)
        )
    }

    @Test
    fun sizeWHNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeNamedTest" => [
                {:size, [], [[height: {:., [], [25,:dp]}, width: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.size(height = 25.dp, width = 50.dp)
        )
    }

    @Test
    fun widthInDp() {
        assertModifierFromStyle(
            """
            %{"widthInDp" => [
                {:width, [], [{:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.width(100.dp)
        )
    }

    @Test
    fun widthInDpWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"widthInDpWithInvalidValueMustFail" => [
                {:width, [], ["100"]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun widthInDpNamed() {
        assertModifierFromStyle(
            """
            %{"widthInDpNamed" => [
                {:width, [], [[width: {:., [], [100,:dp]}]]},
            ]}
            """,
            Modifier.width(100.dp)
        )
    }

    @Test
    fun widthInDpNamedWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"widthInDpNamedWithInvalidValueMustFail" => [
                {:width, [], [[width: "100"]]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun widthIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"widthIntrinsicTest" => [
                {:width, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.width(IntrinsicSize.Min)
        )
    }

    @Test
    fun widthIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"widthIntrinsicTest" => [
                {:width, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Min]}]]},
            ]}
            """,
            Modifier.width(IntrinsicSize.Min)
        )
    }
}
package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SizeTest : ModifierBaseTest() {

    @Test
    fun heightInDp() {
        assertModifierFromStyle(
            """
            %{"heightInDp" => [
                {:height, [], [100]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightInDpWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightInDpWithInvalidValueMustFail" => [
                {:height, [], ["100"]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun heightInDpNamed() {
        assertModifierFromStyle(
            """
            %{"heightInDpNamed" => [
                {:height, [], [[height: 100]]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightInDpNamedWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightInDpNamedWithInvalidValueMustFail" => [
                {:height, [], [[height: "100"]]},
            ]}
            """,
            Modifier
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
    fun widthInDp() {
        assertModifierFromStyle(
            """
            %{"widthInDp" => [
                {:width, [], [100]},
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
                {:width, [], [[width: 100]]},
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

    @Test
    fun sizeTest() {
        assertModifierFromStyle(
            """
            %{"sizeTest" => [
                {:size, [], [50]},
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
                {:size, [], [[size: 50]]},
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
                {:size, [], [50, 100]},
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
                {:size, [], [[height: 25, width: 50]]},
            ]}
            """,
            Modifier.size(height = 25.dp, width = 50.dp)
        )
    }
}
package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.test.base.ModifierBaseTest

@RunWith(AndroidJUnit4::class)
class PaddingTest : ModifierBaseTest() {

    @Test
    fun absolutePaddingTest() {
        assertModifierFromStyle(
            """
            %{"absolutePaddingTest" => [
              {:absolutePadding, [], [
                {:., [], [4, :dp]},
                {:., [], [8, :dp]},
                {:., [], [16, :dp]},
                {:., [], [32, :dp]},
              ]},
            ]}
            """,
            Modifier.absolutePadding(4.dp, 8.dp, 16.dp, 32.dp)
        )
    }

    @Test
    fun absolutePaddingNamedTest() {
        assertModifierFromStyle(
            """
            %{"absolutePaddingNamedTest" => [
              {:absolutePadding, [], [[
                top: {:., [], [4, :dp]},
                left: {:., [], [8, :dp]},
                bottom: {:., [], [16, :dp]},
                right: {:., [], [32, :dp]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.absolutePadding(top = 4.dp, left = 8.dp, bottom = 16.dp, right = 32.dp)
        )
    }

    @Test
    fun captionBarPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"captionBarPaddingTest" => [
                {:captionBarPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("captionBarPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun displayCutoutPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"displayCutoutPadding" => [
                {:displayCutoutPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("displayCutoutPadding") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun imePaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"imePaddingTest" => [
                {:imePadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("imePaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun mandatorySystemGesturesPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"mandatorySystemGesturesPaddingTest" => [
                {:mandatorySystemGesturesPadding, [], []},
            ]}
            """,
        )
        val result =
            modifiersParser.run { Modifier.fromStyleName("mandatorySystemGesturesPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun navigationBarsPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"navigationBarsPaddingTest" => [
                {:navigationBarsPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("navigationBarsPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun safeContentPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"safeContentPaddingTest" => [
                {:safeContentPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("safeContentPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun safeGesturesPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"safeGesturesPaddingTest" => [
                {:safeGesturesPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("safeGesturesPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun statusBarsPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"statusBarsPaddingTest" => [
                {:statusBarsPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("statusBarsPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun systemBarsPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"systemBarsPaddingTest" => [
                {:systemBarsPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("systemBarsPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun systemGesturesPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"systemGesturesPaddingTest" => [
                {:systemGesturesPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("systemGesturesPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun waterfallPaddingTest() {
        modifiersParser.fromStyleFile(
            """
            %{"waterfallPaddingTest" => [
                {:waterfallPadding, [], []},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("waterfallPaddingTest") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun paddingAllTest() {
        assertModifierFromStyle(
            """
            %{"paddingAllTest" => [
                {:padding, [], [{:., [], [32, :dp]}]},
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
                {:padding, [], [[all: {:., [], [32, :dp]}]]},
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
                {:padding, [], [[horizontal: {:., [], [16, :dp]}]]},
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
                {:padding, [], [[vertical: {:., [], [8, :dp]}]]},
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
                {:padding, [], [{:., [], [8, :dp]}, {:., [], [16, :dp]}]},
            ]}
            """,
            Modifier.padding(8.dp, 16.dp)
        )
    }

    @Test
    fun paddingHVNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingHVNamedTest" => [
                {:padding, [], [[vertical: {:., [], [16, :dp]}, horizontal: {:., [], [8, :dp]}]]},
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
                {:padding, [], [
                  {:., [], [4, :dp]}, 
                  {:., [], [8, :dp]}, 
                  {:., [], [16, :dp]}, 
                  {:., [], [32, :dp]}
                ]},
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
                {:padding, [], [[
                  end: {:., [], [16, :dp]}, 
                  bottom: {:., [], [32, :dp]}, 
                  start: {:., [], [4, :dp]}, 
                  top: {:., [], [8, :dp]}
                ]]},
            ]}
            """,
            Modifier.padding(end = 16.dp, bottom = 32.dp, start = 4.dp, top = 8.dp)
        )
    }

    @Test
    fun paddingFromDpTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromDpTest" => [
                {:paddingFrom, [], [{:., [], [:FirstBaseline]}, {:., [], [16.0, :dp]}, {:., [], [32.0, :dp]}]},
            ]}
            """.trimStyle(),
            Modifier.paddingFrom(FirstBaseline, 16.0.dp, 32.0.dp)
        )
    }

    @Test
    fun paddingFromDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromDpNamedTest" => [
              {:paddingFrom, [], [[
                alignmentLine: {:., [], [:LastBaseline]}, 
                before: {:., [], [16.0, :dp]}, 
                after: {:., [], [32.0, :dp]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.paddingFrom(alignmentLine = LastBaseline, before = 16.0.dp, after = 32.0.dp)
        )
    }

    @Test
    fun paddingFromSpTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromSpTest" => [
                {:paddingFrom, [], [
                  {:., [], [:FirstBaseline]}, 
                  {:., [], [16.0, :sp]}, 
                  {:., [], [32.0, :sp]}
                ]},
            ]}
            """.trimStyle(),
            Modifier.paddingFrom(FirstBaseline, 16.0.sp, 32.0.sp)
        )
    }

    @Test
    fun paddingFromTextUnitSpTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromTextUnitSpTest" => [
                {:paddingFrom, [], [
                  {:., [], [:FirstBaseline]}, 
                  {:TextUnit, [], [16.0, {:., [], [:TextUnitType, :Sp]}]}, 
                  {:TextUnit, [], [32.0, {:., [], [:TextUnitType, :Em]}]}
                ]},
            ]}
            """.trimStyle(),
            Modifier.paddingFrom(
                FirstBaseline,
                TextUnit(16f, TextUnitType.Sp),
                TextUnit(32f, TextUnitType.Em)
            )
        )
    }

    @Test
    fun paddingFromSpNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromSpNamedTest" => [
                {:paddingFrom, [], [[
                  alignmentLine: {:., [], [:LastBaseline]},
                  before: {:., [], [16.0, :sp]}, 
                  after: {:., [], [32.0, :sp]}
                ]]},
            ]}
            """.trimStyle(),
            Modifier.paddingFrom(alignmentLine = LastBaseline, before = 16.0.sp, after = 32.0.sp)
        )
    }

    @Test
    fun paddingFromBaselineDpTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromBaselineDpTest" => [
                {:paddingFromBaseline, [], [{:., [], [16.0, :dp]}, {:., [], [32.0, :dp]}]},
            ]}
            """,
            Modifier.paddingFromBaseline(16.0.dp, 32.0.dp)
        )
    }

    @Test
    fun paddingFromBaselineDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromBaselineDpNamedTest" => [
                {:paddingFromBaseline, [], [[top: {:., [], [16.0, :dp]}, bottom: {:., [], [32.0, :dp]}]]},
            ]}
            """,
            Modifier.paddingFromBaseline(top = 16.0.dp, bottom = 32.0.dp)
        )
    }

    @Test
    fun paddingFromBaselineSpTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromBaselineSpTest" => [
                {:paddingFromBaseline, [], [{:., [], [16.0, :sp]}, {:., [], [32.0, :sp]}]},
            ]}
            """,
            Modifier.paddingFromBaseline(16.0.sp, 32.0.sp)
        )
    }

    @Test
    fun paddingFromBaselineSpNamedTest() {
        assertModifierFromStyle(
            """
            %{"paddingFromBaselineSpNamedTest" => [
                {:paddingFromBaseline, [], [[top: {:., [], [16.0, :sp]}, bottom: {:., [], [32.0, :sp]}]]},
            ]}
            """,
            Modifier.paddingFromBaseline(top = 16.0.sp, bottom = 32.0.sp)
        )
    }

    @Test
    fun windowInsetsPadding() {
        modifiersParser.fromStyleFile(
            """
            %{"windowInsetsPadding" => [
                {:windowInsetsPadding, [], [{:WindowInsets, [], [10, 20, 30, 40]}]},
            ]}
            """,
        )
        val result = modifiersParser.run { Modifier.fromStyleName("windowInsetsPadding") }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }
}
package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class PaddingTest : ModifierBaseTest() {

    @Test
    fun captionBarPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"captionBarPaddingTest" => [
                {:captionBarPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun displayCutoutPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"displayCutoutPadding" => [
                {:displayCutoutPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun imePaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"imePaddingTest" => [
                {:imePadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun mandatorySystemGesturesPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"mandatorySystemGesturesPaddingTest" => [
                {:mandatorySystemGesturesPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun navigationBarsPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"navigationBarsPaddingTest" => [
                {:navigationBarsPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun safeContentPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"safeContentPaddingTest" => [
                {:safeContentPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun safeGesturesPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"safeGesturesPaddingTest" => [
                {:safeGesturesPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun statusBarsPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"statusBarsPaddingTest" => [
                {:statusBarsPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun systemBarsPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"systemBarsPaddingTest" => [
                {:systemBarsPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun systemGesturesPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"systemGesturesPaddingTest" => [
                {:systemGesturesPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

    @Test
    fun waterfallPaddingTest() {
        val result = Modifier.fromStyle(
            """
            %{"waterfallPaddingTest" => [
                {:waterfallPadding, [], []},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }

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

    @Test
    fun windowInsetsPadding() {
        val result = Modifier.fromStyle(
            """
            %{"windowInsetsPadding" => [
                {:windowInsetsPadding, [], [{:WindowInsets, [], [10, 20, 30, 40]}]},
            ]}
            """,
        )
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assertNotEquals(result, Modifier)
    }
}
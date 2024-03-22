package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class PaddingTest {
    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    @Test
    fun paddingAllTest() {
        val style = """
            %{"paddingAllTest" => [
                {:padding, [], [32]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(32.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingAllNamedTest() {
        val style = """
            %{"paddingAllNamedTest" => [
                {:padding, [], [[all: 32]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(32.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingHorizontalTest() {
        val style = """
            %{"paddingHorizontalTest" => [
                {:padding, [], [[horizontal: 16]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(horizontal = 16.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingVerticalTest() {
        val style = """
            %{"paddingVerticalTest" => [
                {:padding, [], [[vertical: 8]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(vertical = 8.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingHVTest() {
        val style = """
            %{"paddingHVTest" => [
                {:padding, [], [8, 16]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(horizontal = 8.dp, vertical = 16.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingHVNamedTest() {
        val style = """
            %{"paddingHVNamedTest" => [
                {:padding, [], [[vertical: 16, horizontal: 8]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(vertical = 16.dp, horizontal = 8.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingBordersTest() {
        val style = """
            %{"paddingBordersTest" => [
                {:padding, [], [4, 8, 16, 32]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.padding(4.dp, 8.dp, 16.dp, 32.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun paddingBordersNamedTest() {
        val style = """
            %{"paddingBordersNamedTest" => [
                {:padding, [], [[_end: 16, bottom: 32, start: 4, top: 8]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier =
            Modifier.then(Modifier.padding(end = 16.dp, bottom = 32.dp, start = 4.dp, top = 8.dp))
        assertEquals(result, modifier)
    }
}
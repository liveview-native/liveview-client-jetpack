package org.phoenixframework.liveview.test.foundation.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.foundation.data.mappers.DocumentParser
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveview.foundation.ui.registry.ComposableRegistry

/**
 * Tests for DocumentParser to verify LiveView fragment parsing.
 *
 * LiveView uses a compressed template format where:
 * - "s" contains static template parts (as an array of strings)
 * - Numeric keys (0, 1, 2...) contain dynamic values that are interpolated between statics
 * - "d" contains dynamics for comprehensions
 * - "c" contains components
 */
@RunWith(AndroidJUnit4::class)
class DocumentParserTest {

    private val testFactory = object : BaseComposableNodeFactory(ComposableRegistry()) {}

    /**
     * Tests parsing a simple static template.
     * Format: {"s": ["<Text>Hello, Jetpack!</Text>"]}
     */
    @Test
    fun parseSimpleStaticTemplate() {
        val parser = DocumentParser("test-screen", testFactory)

        val json = """{"s": ["<Text>Hello, Jetpack!</Text>"]}"""

        val result = parser.parseDocumentJson(json)

        assertNotNull(result)
        assertEquals("rootNode", result.id)
    }

    /**
     * Tests parsing a template with dynamic content.
     * Format: {"0": "World", "s": ["<Text>Hello, ", "!</Text>"]}
     * Should render as: <Text>Hello, World!</Text>
     */
    @Test
    fun parseTemplateWithDynamic() {
        val parser = DocumentParser("test-screen", testFactory)

        val json = """{"0": "World", "s": ["<Text>Hello, ", "!</Text>"]}"""

        val result = parser.parseDocumentJson(json)

        assertNotNull(result)
        assertEquals("rootNode", result.id)
    }

    /**
     * Tests that subsequent diffs work after initial render.
     */
    @Test
    fun parseDiffAfterInitialRender() {
        val parser = DocumentParser("test-screen", testFactory)

        // Initial render with dynamic content
        val initialJson = """{"0": "0", "s": ["<Column><Text>Counter: ", "</Text></Column>"]}"""
        parser.parseDocumentJson(initialJson)

        // Diff update - just changes the dynamic value
        val diffJson = """{"0": "1"}"""
        val result = parser.parseDocumentJson(diffJson)

        assertNotNull(result)
    }

    /**
     * Tests that newDocument() resets state and allows a fresh initial render.
     */
    @Test
    fun newDocumentResetsState() {
        val parser = DocumentParser("test-screen", testFactory)

        // First initial render
        val json1 = """{"0": "first", "s": ["<Text>", "</Text>"]}"""
        parser.parseDocumentJson(json1)

        // Reset
        parser.newDocument()

        // Second initial render (should use parseFragmentJson, not mergeFragmentJson)
        val json2 = """{"0": "second", "s": ["<Text>", "</Text>"]}"""
        val result = parser.parseDocumentJson(json2)

        assertNotNull(result)
    }

    /**
     * Tests comprehension format with dynamics.
     * Based on real LiveView output for list rendering.
     */
    @Test
    fun parseComprehensionWithDynamics() {
        val parser = DocumentParser("test-screen", testFactory)

        // Format with comprehension (d = dynamics array)
        val json = """{
            "0": {
                "d": [["Item 1"], ["Item 2"]],
                "s": ["<Text>", "</Text>"]
            },
            "s": ["<Column>", "</Column>"]
        }"""

        val result = parser.parseDocumentJson(json)

        assertNotNull(result)
    }
}

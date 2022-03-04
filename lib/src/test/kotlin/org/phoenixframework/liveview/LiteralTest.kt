package org.phoenixframework.liveview

import org.phoenixframework.liveview.Diff.Companion.componentMapper
import kotlin.test.*

class LiteralTest {
    @Test
    fun toIODataWithEmptyStringReturnsIODataWithUnchangedDiffByComponentID() {
        val literal = Literal("")
        val diffByComponentID = diffByComponentID()
        val templateByComponentID = null

        val (literalIOData, finalDiffByComponentID) = literal.toIOData(
            diffByComponentID,
            templateByComponentID,
            ::componentMapper
        )

        assertEquals(ioData(), literalIOData)
        assertSame(diffByComponentID, finalDiffByComponentID)
    }

    @Test
    fun toIODataWithoutEmptyStringReturnsIODataStringWithUnchangedDiffByComponentID() {
        val string = "non-empty"
        val literal = Literal(string)
        val diffByComponentID = diffByComponentID()
        val templateByComponentID = null

        val (literalIOData, finalDiffByComponentID) = literal.toIOData(
            diffByComponentID,
            templateByComponentID,
            ::componentMapper
        )

        assertEquals(ioData(string), literalIOData)
        assertSame(diffByComponentID, finalDiffByComponentID)
    }

    @Test
    fun deepMergeWithoutThisWithoutSourceReturnsNull() {
        val literal: Literal? = null
        val source: Literal? = null

        assertNull(literal.deepMerge(source))
    }

    @Test
    fun deepMergeWithThisWithoutSourceReturnsThis() {
        val literal = Literal("this")
        val source: Literal? = null

        assertSame(literal, literal.deepMerge(source))
    }

    @Test
    fun deepMergeWithoutThisWithSourceReturnsSource() {
        val literal: Literal? = null
        val source = Literal("source")

        assertSame(source, literal.deepMerge(source))
    }

    @Test
    fun deepMergeWithSameReturnsThis() {
        val literal = Literal("same")

        assertSame(literal, literal.deepMerge(literal))
    }

    @Test
    fun deepMergeWithEqualReturnsThis() {
        val literal = Literal("equal")
        val source = Literal("equal")

        assertSame(literal, literal.deepMerge(source))
    }

    @Test
    fun deepMergeWithoutEqualReturnsSource() {
        val literal = Literal("this")
        val source = Literal("source")

        assertSame(source, literal.deepMerge(source))
    }

    @Test
    fun toStringEscapesNewlines() {
        assertEquals("Literal(\"\\n\")", Literal("\n").toString())
    }
}

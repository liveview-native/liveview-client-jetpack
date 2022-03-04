package org.phoenixframework.liveview

import kotlin.test.*

class SubstitutionByIndexTest {
    @Test
    fun getWithoutIndexReturnsNull() {
        val substitutionByIndex = substitutionByIndex()
        val index = 0

        assertNull(substitutionByIndex[index])
    }

    @Test
    fun getWithIndexReturnsSubstitution() {
        val index = 0
        val substitution = Literal("literal")
        val substitutionByIndex = substitutionByIndex(
            index to substitution
        )

        assertSame(substitution, substitutionByIndex[index])
    }

    @Test
    fun putWithoutIndexReturnsCopyWithIndexAdded() {
        val index = 0
        val substitution = Literal("substitution")
        val substitutionByIndex = substitutionByIndex()

        val finalSubstitutionByIndex = substitutionByIndex.put(index, substitution)

        assertEquals(substitutionByIndex(index to substitution), finalSubstitutionByIndex)
        assertNotEquals(substitutionByIndex, finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithLiteralWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithLiteralWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(1))

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithLiteralWithDiffReturnsCopyWithNewDiff() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff()

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithComponentSubstitutionWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithComponentSubstitutionWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(2))

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithComponentSubstitutionWithDiffReturnsCopyWithNewDiff() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff()

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithDiffWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = Diff()
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithDiffWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = Diff()
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(2))

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithDiffSubstitutionWithDiffThatDeepsMergesToDiffReturnsThis() {
        val index = 0
        val oldSubstitution = Diff(
            static = Template("Static0", "Static1", "Static2"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("old"),
                1 to Literal("same")
            )
        )
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff(
            substitutionByIndex = substitutionByIndex(
                1 to Literal("same")
            )
        )

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertSame(substitutionByIndex, finalSubstitutionByIndex)
    }

    @Test
    fun putWithIndexWithDiffSubstitutionWithDiffThatDeepsMergesToDifferentThanDiffReturnsCopy() {
        val index = 0
        val static = Template("Static0", "Static1", "Static2")
        val oldSubstitution = Diff(
            static,
            substitutionByIndex = substitutionByIndex(
                0 to Literal("old"),
                1 to Literal("same")
            )
        )
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff(
            substitutionByIndex = substitutionByIndex(
                0 to Literal("new")
            )
        )

        val finalSubstitutionByIndex = substitutionByIndex.put(index, newSubstitution)

        assertEquals(
            substitutionByIndex(
                index to Diff(
                    static,
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("new"),
                        1 to Literal("same")
                    )
                )
            ),
            finalSubstitutionByIndex
        )
    }

    @Test
    fun deepMergeWithoutThisWithoutSourceReturnsNull() {
        val substitutionByIndex: SubstitutionByIndex? = null
        val source: SubstitutionByIndex? = null

        assertNull(substitutionByIndex.deepMerge(source))
    }

    @Test
    fun deepMergeWithoutThisWithSourceReturnsSource() {
        val substitutionByIndex: SubstitutionByIndex? = null
        val source = substitutionByIndex()

        assertSame(source, substitutionByIndex.deepMerge(source))
    }

    @Test
    fun deepMergeWithThisWithoutSourceReturnsThis() {
        val substitutionByIndex = substitutionByIndex()
        val source: SubstitutionByIndex? = null

        assertSame(substitutionByIndex, substitutionByIndex.deepMerge(source))
    }

    @Test
    fun deepMergeWithThisWithEqualSourceReturnsThis() {
        val substitutionByIndex = substitutionByIndex()
        val source = substitutionByIndex()

        assertSame(substitutionByIndex, substitutionByIndex.deepMerge(source))
    }

    @Test
    fun deepMergeWithoutIndexReturnsCopyWithIndexAdded() {
        val substitutionByIndex = substitutionByIndex()

        val index = 0
        val substitution = Literal("substitution")
        val source = substitutionByIndex(index to substitution)

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to substitution), finalSubstitutionByIndex)
        assertNotEquals(substitutionByIndex, finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithLiteralWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithLiteralWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(1))
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithLiteralWithDiffReturnsCopyWithNewDiff() {
        val index = 0
        val oldSubstitution = Literal("old")
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff()
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithComponentSubstitutionWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithComponentSubstitutionWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(2))
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithComponentSubstitutionWithDiffReturnsCopyWithNewDiff() {
        val index = 0
        val oldSubstitution = ComponentSubstitution(ComponentID(1))
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff()
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithDiffWithNewLiteralReturnsCopyWithNewLiteral() {
        val index = 0
        val oldSubstitution = Diff()
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Literal("new")
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithDiffWithNewComponentSubstitutionReturnsCopyWithNewComponentSubstitution() {
        val index = 0
        val oldSubstitution = Diff()
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = ComponentSubstitution(ComponentID(2))
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(substitutionByIndex(index to newSubstitution), finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithDiffSubstitutionWithDiffThatDeepsMergesToDiffReturnsThis() {
        val index = 0
        val oldSubstitution = Diff(
            static = Template("Static0", "Static1", "Static2"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("old"),
                1 to Literal("same")
            )
        )
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff(
            substitutionByIndex = substitutionByIndex(
                1 to Literal("same")
            )
        )
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertSame(substitutionByIndex, finalSubstitutionByIndex)
    }

    @Test
    fun deepMergeWithIndexWithDiffSubstitutionWithDiffThatDeepsMergesToDifferentThanDiffReturnsCopy() {
        val index = 0
        val static = Template("Static0", "Static1", "Static2")
        val oldSubstitution = Diff(
            static,
            substitutionByIndex = substitutionByIndex(
                0 to Literal("old"),
                1 to Literal("same")
            )
        )
        val substitutionByIndex = substitutionByIndex(
            index to oldSubstitution
        )

        val newSubstitution = Diff(
            substitutionByIndex = substitutionByIndex(
                0 to Literal("new")
            )
        )
        val source = substitutionByIndex(
            index to newSubstitution
        )

        val finalSubstitutionByIndex = substitutionByIndex.deepMerge(source)

        assertEquals(
            substitutionByIndex(
                index to Diff(
                    static,
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("new"),
                        1 to Literal("same")
                    )
                )
            ),
            finalSubstitutionByIndex
        )
    }

    @Test
    fun toStringWithEmpty() {
        assertEquals("substitutionByIndex()", substitutionByIndex().toString())
    }

    @Test
    fun toStringWithoutEmpty() {
        assertEquals(
            """
            substitutionByIndex(
              1 to Literal("Literal1"),
              2 to ComponentSubstitution(ComponentID(2)),
              3 to Diff()
            )
            """.trimIndent(),
            substitutionByIndex(
                1 to Literal("Literal1"),
                2 to ComponentSubstitution(ComponentID(2)),
                3 to Diff()
            ).toString()
        )
    }
}

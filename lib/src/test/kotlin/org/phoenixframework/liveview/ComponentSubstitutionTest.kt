package org.phoenixframework.liveview

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ComponentSubstitutionTest {
    @Test
    fun toIODataWithoutComponentIDFails() {
        val componentID = ComponentID(1)
        val componentSubstitution = ComponentSubstitution(componentID)

        assertFails {
            componentSubstitution.toIOData(
                diffByComponentID(),
                templateByComponentID = null,
            ) { _, ioData ->
                ioData
            }
        }
    }

    @Test
    fun toIODataWithComponentIDWithLiteralReturnsLiteral() {
        val componentID = ComponentID(1)
        val componentSubstitution = ComponentSubstitution(componentID)
        val diffByComponentID = diffByComponentID(
            componentID to Diff(
                static = Template("<div>\n  Component1\n</div>")
            )
        )

        val (ioData, finalDiffByComponent) = componentSubstitution.toIOData(
            diffByComponentID,
            null,
            Diff.Companion::componentMapper
        )

        assertEquals(
            ioData(
                """
                <div data-phx-component="1">
                  Component1
                </div>
            """.trimIndent()
            ),
            ioData
        )
        assertEquals(diffByComponentID, finalDiffByComponent)
    }

    @Test
    fun toIODataWithComponentIDWithComponentReferenceWithLiteralReturnsLiteralAndResolvedDiffByComponentID() {
        val farComponentID = ComponentID(2)
        val farComponentReference = ComponentReference(farComponentID)
        val farTemplate = Template("<div>\n  Component2\n</div>")

        val nearComponentID = ComponentID(1)
        val nearComponentSubstitution = ComponentSubstitution(nearComponentID)

        val diffByComponentID = diffByComponentID(
            nearComponentID to Diff(
                static = farComponentReference
            ),
            farComponentID to Diff(
                static = farTemplate
            )
        )

        val templateByComponentID = templateByComponentID(
            farComponentID to Template("<div>\n  Component2\n</div>")
        )

        val (ioData, finalDiffByComponent) = nearComponentSubstitution.toIOData(
            diffByComponentID,
            templateByComponentID,
            Diff.Companion::componentMapper
        )

        assertEquals(
            ioData(
                """
                <div data-phx-component="1">
                  Component2
                </div>
                """.trimIndent()
            ),
            ioData
        )
        assertEquals(
            diffByComponentID(
                nearComponentID to Diff(
                    static = farTemplate
                ),
                farComponentID to Diff(
                    static = farTemplate
                )
            ),
            finalDiffByComponent
        )
    }

    @Test
    fun deepMergeWithoutSourceReturnsThis() {
        val componentSubstitution = ComponentSubstitution(ComponentID(1))

        assertEquals(componentSubstitution, componentSubstitution.deepMerge(null))
    }

    @Test
    fun deepMergeWithEqualSourceReturnsThis() {
        val componentSubstitution = ComponentSubstitution(ComponentID(1))
        val source = ComponentSubstitution(ComponentID(1))

        assertEquals(source, componentSubstitution.deepMerge(source))
    }

    @Test
    fun deepMergeWithUnequalSourceReturnsSource() {
        val componentSubstitution = ComponentSubstitution(ComponentID(1))
        val source = ComponentSubstitution(ComponentID(2))

        assertEquals(source, componentSubstitution.deepMerge(source))
    }

    @Test
    fun toStringDoesNotIncludeField() {
        assertEquals("ComponentSubstitution(ComponentID(1))", ComponentSubstitution(ComponentID(1)).toString())
    }
}

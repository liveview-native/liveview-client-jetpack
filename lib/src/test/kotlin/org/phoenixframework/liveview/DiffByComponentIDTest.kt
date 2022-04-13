package org.phoenixframework.liveview

import java.lang.NullPointerException
import kotlin.test.*

internal class DiffByComponentIDTest {
    @Test
    fun deepMerge() {
    }

    @Test
    fun putWithNewComponentIDReturnsCopy() {
        val componentID = ComponentID(1)
        val diff = Diff()

        assertEquals(diffByComponentID(componentID to diff), diffByComponentID().put(componentID, diff))
    }

    @Test
    fun putWithExistingComponentIDWithExistingDiffReturnsThis() {
        val componentID = ComponentID(1)
        val diff = Diff()
        val diffByComponentID = diffByComponentID(componentID to diff)
        val updated = diffByComponentID.put(componentID, diff)

        assertSame(diffByComponentID, updated)
    }


    @Test
    fun getWithComponentIDReturnsDiff() {
        val componentID = ComponentID(1)
        val diff = Diff()
        val diffByComponentID = diffByComponentID(componentID to diff)

        assertEquals(diff, diffByComponentID[componentID])
    }

    @Test
    fun getWithoutComponentIDReturnsNull() {
        val componentID = ComponentID(1)
        val diffByComponentID = diffByComponentID()

        assertNull(diffByComponentID[componentID])
    }

    @Test
    fun dropComponentIDsWithEmptyIterableReturnsThis() {
        val componentID = ComponentID(1)
        val diff = Diff()
        val diffByComponentID = diffByComponentID(componentID to diff)

        assertSame(diffByComponentID, diffByComponentID.dropComponentIDs(emptyList()))
    }

    @Test
    fun dropComponentIDsWithNonExistentComponentIDReturnsThis() {
        val componentID = ComponentID(1)
        val diff = Diff()
        val diffByComponentID = diffByComponentID(componentID to diff)
        val nonExistentComponentID = ComponentID(2)

        assertSame(diffByComponentID, diffByComponentID.dropComponentIDs(listOf(nonExistentComponentID)))
    }

    @Test
    fun dropComponentIDsWithExistentComponentIDReturnsDiffByComponentIDWithoutExistentComponentID() {
        val existentComponentID = ComponentID(1)
        val preservedComponentID = ComponentID(2)
        val diff = Diff()
        val diffByComponentID = diffByComponentID(existentComponentID to diff, preservedComponentID to diff)

        assertEquals(
            diffByComponentID(preservedComponentID to diff),
            diffByComponentID.dropComponentIDs(listOf(existentComponentID))
        )
    }

    @Test
    fun isEmptyWithoutComponentIDsReturnsTrue() {
        assertTrue(diffByComponentID().isEmpty())
    }

    @Test
    fun isEmptyWithComponentIDsReturnsFalse() {
        assertFalse(diffByComponentID(ComponentID(1) to Diff()).isEmpty())
    }

    @Test
    fun isNotEmptyWithoutComponentIDReturnsFalse() {
        assertFalse(diffByComponentID().isNotEmpty())
    }

    @Test
    fun isNotEmptyWithComponentIDReturnsTrue() {
        assertTrue(diffByComponentID(ComponentID(1) to Diff()).isNotEmpty())
    }

    @Test
    fun resolveComponentsReferenceWithoutComponentIDFails() {
        val diffByComponentID = diffByComponentID()
        val componentID = ComponentID(1)

        assertFailsWith<NullPointerException> {
            diffByComponentID.resolveComponentsReference(componentID)
        }
    }

    @Test
    fun resolveComponentsReferenceWithComponentIDWithoutComponentReferenceReturnsThis() {
        val componentID = ComponentID(1)
        val diffByComponentID = diffByComponentID(componentID to Diff(static = Template("")))

        assertSame(
            diffByComponentID,
            diffByComponentID.resolveComponentsReference(componentID)
        )
    }

    @Test
    fun resolveComponentsReferenceWithPositiveComponentIDWithComponentReferenceToTemplateReturnsWithComponentIDDeepMerged() {
        val referencingComponentID = ComponentID(1)
        val referencedComponentID = ComponentID(2)
        val componentReference = ComponentReference(referencedComponentID)
        val referencedDiff = Diff(
            static = Template("<div>\n", "\n</div>"),
            dynamics = listOf(
                listOf(Literal("Dynamic"))
            )
        )
        val diffByComponentID = diffByComponentID(
            referencingComponentID to Diff(static = componentReference),
            referencedComponentID to referencedDiff
        )

        assertEquals(
            diffByComponentID(
                referencingComponentID to referencedDiff,
                referencedComponentID to referencedDiff
            ),
            diffByComponentID.resolveComponentsReference(referencingComponentID)
        )
    }

    @Test
    fun resolveComponentsReferenceWithNegativeComponentIDWithComponentReferenceToTemplateReturnsWithComponentIDDeepMerged() {
        val referencingComponentID = ComponentID(1)
        val referencedID = 2
        val referencedComponentID = ComponentID(referencedID)
        val componentReference = ComponentReference(ComponentID(-referencedID))
        val referencedDiff = Diff(
            static = Template("<div>\n", "\n</div>"),
            dynamics = listOf(
                listOf(Literal("Dynamic"))
            )
        )
        val diffByComponentID = diffByComponentID(
            referencingComponentID to Diff(static = componentReference),
            referencedComponentID to referencedDiff
        )

        assertEquals(
            diffByComponentID(
                referencingComponentID to referencedDiff,
                referencedComponentID to referencedDiff
            ),
            diffByComponentID.resolveComponentsReference(referencingComponentID)
        )
    }

    @Test
    fun resolveComponentsReferenceWithComponentIDWithComponentReferenceToComponentReferenceToTemplateReturnsWithComponentIDDeepMerged() {
        val farReferencedComponentID = ComponentID(3)
        val farComponentReference = ComponentReference(farReferencedComponentID)
        val farReferencedDiff = Diff(
            static = Template("<div>\n", "\n</div>"),
            dynamics = listOf(
                listOf(Literal("Dynamic"))
            )
        )

        val nearReferencedComponentID = ComponentID(2)
        val nearComponentReference = ComponentReference(nearReferencedComponentID)
        val nearReferencedDiff = Diff(
            static = farComponentReference
        )

        val referencingComponentID = ComponentID(1)
        val referencingDiff = Diff(static = nearComponentReference)

        val diffByComponentID = diffByComponentID(
            referencingComponentID to referencingDiff,
            nearReferencedComponentID to nearReferencedDiff,
            farReferencedComponentID to farReferencedDiff
        )

        assertEquals(
            diffByComponentID(
                referencingComponentID to farReferencedDiff,
                nearReferencedComponentID to farReferencedDiff,
                farReferencedComponentID to farReferencedDiff
            ),
            diffByComponentID.resolveComponentsReference(referencingComponentID)
        )
    }
}

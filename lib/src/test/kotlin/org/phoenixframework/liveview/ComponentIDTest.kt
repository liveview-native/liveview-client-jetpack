package org.phoenixframework.liveview

import kotlin.test.*

class ComponentIDTest {
    @Test
    fun isAddedWithPositiveIDReturnsTrue() {
        assertTrue(ComponentID(1).isAdded())
    }

    @Test
    fun isAddedWithNegativeIDReturnsFalse() {
        assertFalse(ComponentID(-1).isAdded())
    }

    @Test
    fun withoutAgeWithPositiveIDReturnsThis() {
        val componentID = ComponentID(1)

        assertEquals(componentID, componentID.withoutAge())
    }

    @Test
    fun withoutAgeWithNegativeIDReturnsNewComponentIDWithPositiveID() {
        val negativeID = -1
        val componentID = ComponentID(negativeID)

        assertEquals(ComponentID(-negativeID), componentID.withoutAge())
    }

    @Test
    fun oldComponentIDWithPositiveIDFails() {
        assertFailsWith<AssertionError> {
            ComponentID(1).oldComponentID()
        }
    }

    @Test
    fun oldComponentIDWithNegativeIDReturnsComponentIDWithPositiveID() {
        val negativeID = -1
        val componentID = ComponentID(negativeID)

        assertEquals(ComponentID(-negativeID), componentID.oldComponentID())

    }

    @Test
    fun toStringDoesNotIncludeParameterName() {
        assertEquals("ComponentID(1)", ComponentID(1).toString())
    }

    @Test
    fun toHTMLIsOnlyTheId() {
        assertEquals("1", ComponentID(1).toHTML())
    }
}

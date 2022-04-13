package org.phoenixframework.liveview

import kotlin.test.*

class TemplateByComponentIDTest {
    @Test
    fun getComponentIDsWithoutComponentIDs() {
        assertTrue(templateByComponentID().componentIDs.isEmpty())
    }

    @Test
    fun getComponentIDsWithComponentIDs() {
        val templateByComponentID = templateByComponentID(
            ComponentID(1) to Template("Component1"),
            ComponentID(2) to Template("Component2")
        )

        assertEquals(setOf(ComponentID(1), ComponentID(2)), templateByComponentID.componentIDs)
    }

    @Test
    fun getWithComponentIDWithoutComponentIDReturnsNull() {
        val templateByComponentID = templateByComponentID()
        val componentID = ComponentID(1)

        assertNull(templateByComponentID[componentID])
    }

    @Test
    fun getWithComponentIDWithComponentIDReturnsTemplate() {
        val componentID = ComponentID(1)
        val template = Template("Component1")
        val templateByComponentID = templateByComponentID(
            componentID to template
        )

        assertSame(template, templateByComponentID[componentID])
    }

    @Test
    fun getWithComponentReferenceWithoutComponentIDReturnsNull() {
        val templateByComponentID = templateByComponentID()
        val componentReference = ComponentReference(ComponentID(1))

        assertNull(templateByComponentID[componentReference])
    }

    @Test
    fun getWithComponentReferenceWithComponentIDReturnsTemplate() {
        val componentID = ComponentID(1)
        val template = Template("Component1")
        val templateByComponentID = templateByComponentID(
            componentID to template
        )
        val componentReference = ComponentReference(componentID)

        assertSame(template, templateByComponentID[componentReference])
    }

    @Test
    fun putWithoutComponentIDReturnsCopyWithComponentIDAdded() {
        val componentID = ComponentID(1)
        val template = Template("Component1")
        val templateByComponentID = templateByComponentID()

        val finalTemplateByComponentID = templateByComponentID.put(componentID, template)

        assertEquals(templateByComponentID(componentID to template), finalTemplateByComponentID)
        assertNotEquals(templateByComponentID, finalTemplateByComponentID)
    }

    @Test
    fun putWithComponentIDWithEqualTemplateReturnsThis() {
        val componentID = ComponentID(1)
        val template = Template("Component1")
        val templateByComponentID = templateByComponentID(componentID to template)

        val equalTemplate = Template("Component1")

        val finalTemplateByComponentID = templateByComponentID.put(componentID, equalTemplate)

        assertSame(templateByComponentID, finalTemplateByComponentID)
    }

    @Test
    fun putWithComponentIDWithUnequalTemplateReturnsCopyWithUnequalTemplate() {
        val preservedComponentID = ComponentID(1)
        val preservedTemplate = Template("Component1")
        val replacedComponentID = ComponentID(2)
        val replacedTemplate = Template("Replaced Component 2")

        val templateByComponentID = templateByComponentID(
            preservedComponentID to preservedTemplate,
            replacedComponentID to replacedTemplate
        )

        val replacementTemplate = Template("Replacement Component 2")

        val finalTemplateByComponentID = templateByComponentID.put(replacedComponentID, replacementTemplate)

        assertEquals(
            templateByComponentID(
                preservedComponentID to preservedTemplate,
                replacedComponentID to replacementTemplate
            ),
            finalTemplateByComponentID
        )
    }

    @Test
    fun deepMergeWithoutThisWithoutSourceReturnsNull() {
        val templateByComponentID: TemplateByComponentID? = null
        val source: TemplateByComponentID? = null

        assertNull(templateByComponentID.deepMerge(source))
    }

    @Test
    fun deepMergeWithoutThisWithSourceReturnsSource() {
        val templateByComponentID: TemplateByComponentID? = null
        val source = templateByComponentID()

        assertSame(source, templateByComponentID.deepMerge(source))
    }

    @Test
    fun deepMergeWithThisWithoutSourceReturnsThis() {
        val templateByComponentID = templateByComponentID()
        val source: TemplateByComponentID? = null

        assertSame(templateByComponentID, templateByComponentID.deepMerge(source))
    }

    @Test
    fun deepMergeWithThisWithSourceDeepMergesSourceIntoCopyOfThis() {
        val preservedComponentID = ComponentID(1)
        val preservedTemplate = Template("Component 1")
        val replacedComponentID = ComponentID(2)
        val replacedTemplate = Template("Replaced Component 2")
        val templateByComponentID = templateByComponentID(
            preservedComponentID to preservedTemplate,
            replacedComponentID to replacedTemplate
        )

        val replacementTemplate = Template("Replacement Component 2")
        val newComponentID = ComponentID(3)
        val newTemplate = Template("Component 3")
        val source = templateByComponentID(
            replacedComponentID to replacementTemplate,
            newComponentID to newTemplate
        )

        assertEquals(
            templateByComponentID(
                preservedComponentID to preservedTemplate,
                replacedComponentID to replacementTemplate,
                newComponentID to newTemplate
            ),
            templateByComponentID.deepMerge(source)
        )
    }

    @Test
    fun toStringWithEmpty() {
        assertEquals("templateByComponentID()", templateByComponentID().toString())
    }

    @Test
    fun toStringWithoutEmpty() {
        assertEquals(
            """
            templateByComponentID(
              ComponentID(1) to Template("Component1"),
              ComponentID(2) to Template("Component2")
            )
            """.trimIndent(),
            templateByComponentID(
                ComponentID(1) to Template("Component1"),
                ComponentID(2) to Template("Component2")
            ).toString()
        )
    }
}

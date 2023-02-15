package org.phoenixframework.liveview.domain.external

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

fun templateByComponentID(vararg pairs: Pair<ComponentID, Template>) = TemplateByComponentID(persistentMapOf(*pairs))

data class TemplateByComponentID(private val persistentMap: PersistentMap<ComponentID, Template> = persistentMapOf()) :
    Iterable<Map.Entry<ComponentID, Template>> {
    val componentIDs: Set<ComponentID>
        get() = persistentMap.keys

    fun deepMerge(source: TemplateByComponentID): TemplateByComponentID =
        source.fold(this) { acc, (componentID, templateByComponentID) ->
            acc.put(componentID, templateByComponentID)
        }

    operator fun get(componentReference: ComponentReference): Template? = this[componentReference.componentID]
    operator fun get(componentID: ComponentID): Template? = persistentMap[componentID]

    fun put(componentID: ComponentID, template: Template): TemplateByComponentID =
        if (template == this[componentID]) {
            this
        } else {
            this.copy(persistentMap = persistentMap.put(componentID, template))
        }

    override fun iterator(): Iterator<Map.Entry<ComponentID, Template>> = persistentMap.iterator()
    private fun isEmpty(): Boolean = persistentMap.isEmpty()

    override fun toString(): String = if (isEmpty()) {
        "templateByComponentID()"
    } else {

        joinToString(
            prefix = "templateByComponentID(\n",
            separator = ",\n",
            postfix = "\n)"
        ) { (componentID, template) ->
            "$componentID to $template".prependIndent("  ")
        }
    }
}

fun TemplateByComponentID?.deepMerge(source: TemplateByComponentID?): TemplateByComponentID? =
    if (this != null) {
        if (source != null) {
            this.deepMerge(source)
        } else {
            this
        }
    } else {
        source
    }

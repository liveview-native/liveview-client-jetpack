package org.phoenixframework.liveview.domain.external

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

fun diffByComponentID(vararg pairs: Pair<ComponentID, Diff>) = DiffByComponentID(persistentMapOf(*pairs))

data class DiffByComponentID(private val persistentMap: PersistentMap<ComponentID, Diff> = persistentMapOf()) :
    Iterable<Map.Entry<ComponentID, Diff>> {
    fun deepMerge(source: DiffByComponentID): DiffByComponentID =
        source.fold(this) { acc, (componentID, sourceDiff) ->
            acc.put(componentID, sourceDiff)
        }

    operator fun get(componentID: ComponentID): Diff? = persistentMap[componentID]

    fun put(componentID: ComponentID, diff: Diff): DiffByComponentID {
        val currentDiff = this[componentID]
        val mergedDiff = currentDiff?.deepMerge(diff) ?: diff

        return if (mergedDiff === currentDiff) {
            this
        } else {
            this.copy(persistentMap = persistentMap.put(componentID, mergedDiff))
        }
    }

    fun dropComponentIDs(componentIDs: Iterable<ComponentID>): DiffByComponentID {
        val finalPersistentMap = componentIDs.fold(persistentMap) { acc, componentID ->
            acc.remove(componentID)
        }

        return if (finalPersistentMap === persistentMap) {
            this
        } else {
            copy(persistentMap = finalPersistentMap)
        }
    }

    override fun iterator(): Iterator<Map.Entry<ComponentID, Diff>> = persistentMap.iterator()
    fun isEmpty(): Boolean = persistentMap.isEmpty()
    fun isNotEmpty(): Boolean = persistentMap.isNotEmpty()

    fun resolveComponentsReference(componentID: ComponentID): DiffByComponentID {
        val diff = this[componentID]!!

        return when (val static = diff.static) {
            // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L93-L96
            is ComponentReference -> {
                val nestedComponentID = static.componentID.withoutAge()
                val resolvedDiffByComponentID = resolveComponentsReference(nestedComponentID)
                val deepMergedDiffByComponentID =
                    resolvedDiffByComponentID[nestedComponentID]!!.deepMerge(diff.copy(static = null))
                resolvedDiffByComponentID.put(componentID, deepMergedDiffByComponentID)
            }
            else -> this
        }
    }

    override fun toString(): String = joinToString(
        prefix = "diffByComponent(\n",
        separator = ",\n",
        postfix = "\n)"
    ) { (componentID, diff) ->
        "$componentID to $diff".prependIndent("  ")
    }
}

fun DiffByComponentID?.orEmpty(): DiffByComponentID = this ?: DiffByComponentID()

fun DiffByComponentID?.deepMerge(source: DiffByComponentID?): DiffByComponentID? =
    if (this != null) {
        if (source != null) {
            this.deepMerge(source)
        } else {
            this
        }
    } else {
        source
    }

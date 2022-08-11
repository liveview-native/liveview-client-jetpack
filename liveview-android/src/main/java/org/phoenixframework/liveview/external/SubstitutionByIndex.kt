package org.phoenixframework.liveview.external

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

fun substitutionByIndex(vararg pairs: Pair<Int, Substitution>) = SubstitutionByIndex(persistentMapOf(*pairs))

data class SubstitutionByIndex(private val persistentMap: PersistentMap<Int, Substitution>) :
    Iterable<Map.Entry<Int, Substitution>> {
    fun deepMerge(source: SubstitutionByIndex): SubstitutionByIndex =
        source.fold(this) { acc, (index, sourceSubstitution) ->
            acc.put(index, sourceSubstitution)
        }

    operator fun get(index: Int): Substitution? = persistentMap[index]

    fun put(index: Int, substitution: Substitution): SubstitutionByIndex {
        val currentSubstitution = this[index]
        val mergedSubstitution = currentSubstitution?.deepMerge(substitution) ?: substitution

        return if (mergedSubstitution === currentSubstitution) {
            this
        } else {
            this.copy(persistentMap = persistentMap.put(index, mergedSubstitution))
        }
    }

    override fun iterator(): Iterator<Map.Entry<Int, Substitution>> = persistentMap.iterator()
    private fun isEmpty(): Boolean = persistentMap.isEmpty()

    override fun toString(): String = if (isEmpty()) {
        "substitutionByIndex()"
    } else {
        joinToString(
            prefix = "substitutionByIndex(\n",
            separator = ",\n",
            postfix = "\n)"
        ) { (index, substitution) ->
            "$index to $substitution".prependIndent("  ")
        }
    }
}

fun SubstitutionByIndex?.deepMerge(source: SubstitutionByIndex?): SubstitutionByIndex? =
    if (this != null) {
        if (source != null) {
            this.deepMerge(source)
        } else {
            this
        }
    } else {
        source
    }

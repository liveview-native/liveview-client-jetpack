package org.phoenixframework.liveview.domain.extensions

fun <T> List<T>.orderedMix(other: List<T>): List<T> {

    val largerCount = if (count() > other.count()) { count() } else { other.count() }
    val listBuilder = ArrayList<T>()

    for (i in 0..largerCount) {

        if (i < count()) {
            listBuilder.add(this[i])
        }

        if (i < other.count()) {
            listBuilder.add(other[i])
        }
    }

    return listBuilder

}

inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { it is T })
        @Suppress("UNCHECKED_CAST")
        this as List<T> else
        null
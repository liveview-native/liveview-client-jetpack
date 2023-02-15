package org.phoenixframework.liveview.domain.extensions

fun <T> List<T>.orderedMix(other: List<T>): List<T> {
    val count = count()
    val otherCount = other.count()
    val largerCount = maxOf(count, otherCount)
    val listBuilder = ArrayList<T>()

    for (i in 0..largerCount) {
        if (i < count) {
            listBuilder.add(this[i])
        }

        if (i < otherCount) {
            listBuilder.add(other[i])
        }
    }

    return listBuilder
}
package org.phoenixframework.liveview.external

sealed interface Substitution {
    fun toIOData(
        diffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID>

    fun deepMerge(source: Substitution): Substitution =
        if (source == this) {
            this
        } else {
            source
        }
}

fun <E> List<E>.head(): E = first()
fun <E> List<E>.tail(): List<E> = subList(1, size)

fun Substitution?.deepMerge(source: Substitution?): Substitution? =
    if (this != null) {
        if (source != null) {
            this.deepMerge(source)
        } else {
            this
        }
    } else {
        source
    }

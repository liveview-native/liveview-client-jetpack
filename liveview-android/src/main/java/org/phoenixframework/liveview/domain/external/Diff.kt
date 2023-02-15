package org.phoenixframework.liveview.domain.external

import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.select.Elements

data class Diff(
    val static: Static? = null,
    val dynamics: List<List<Substitution>>? = null,
    val substitutionByIndex: SubstitutionByIndex? = null,
    val diffByComponentID: DiffByComponentID? = null,
    val templateByComponentID: TemplateByComponentID? = null
) : Substitution {
    // `this` is `rendered` in https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L160
    fun merge(diff: Diff): Diff {
        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L161
        val oldDiffByComponentID = this.diffByComponentID.orEmpty()
        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L162
        val (newDiffByComponentID, updatedDiff) = diff.popDiffByComponentID()
        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L163
        val updatedRendered = this.deepMerge(updatedDiff)

        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L169-L185
        return if (newDiffByComponentID != null) {
            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L171-L172
            val (final, _) = newDiffByComponentID.fold(
                Pair(
                    oldDiffByComponentID,
                    DiffByComponentID()
                )
            ) { (acc, cache), (componentID, componentDiff) ->
                // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L173
                val (value, _) = findComponent(
                    componentID,
                    componentDiff,
                    oldDiffByComponentID.orEmpty(),
                    newDiffByComponentID,
                    cache
                )

                acc.put(componentID, value) to cache
            }

            updatedRendered.copy(diffByComponentID = final)
        } else if (oldDiffByComponentID.isNotEmpty()) {
            updatedRendered.copy(diffByComponentID = oldDiffByComponentID)
        } else {
            updatedRendered
        }
    }

    private fun popDiffByComponentID(): Pair<DiffByComponentID?, Diff> {
        val diffByComponentID = this.diffByComponentID
        val updated = this.copy(diffByComponentID = null)

        return Pair(diffByComponentID, updated)
    }

    override fun deepMerge(source: Substitution): Substitution = when (source) {
        is Diff -> deepMerge(source)
        null -> this
        else -> source
    }

    // `this` is `target` that will be updated immutably
    // `deep_merge_diff` in Elixir
    fun deepMerge(source: Diff): Diff =
        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L214
        if (source.static != null) {
            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L215
            source
        } else {
            // `data class` `copy` is not no-change optimized because it is too general, so explicitly only copy if
            // there is a change
            var acc = this

            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L220-L221
            val mergedDynamics = source.dynamics ?: this.dynamics
            acc = if (mergedDynamics !== this.dynamics) {
                acc.copy(dynamics = mergedDynamics)
            } else {
                acc
            }

            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L217-L218
            val mergedSubstitutionByIndex = this.substitutionByIndex?.deepMerge(source.substitutionByIndex)
            acc = if (mergedSubstitutionByIndex !== this.substitutionByIndex) {
                acc.copy(substitutionByIndex = mergedSubstitutionByIndex)
            } else {
                acc
            }

            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L217-L218
            val mergedDiffByComponentID = this.diffByComponentID?.deepMerge(source.diffByComponentID)
            acc = if (mergedDiffByComponentID !== this.diffByComponentID) {
                acc.copy(diffByComponentID = mergedDiffByComponentID)
            } else {
                acc
            }

            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L217-L218
            val mergedTemplateByComponentID = this.templateByComponentID?.deepMerge(source.templateByComponentID)
            if (mergedTemplateByComponentID !== this.templateByComponentID) {
                acc.copy(templateByComponentID = mergedTemplateByComponentID)
            } else {
                acc
            }
        }

    fun dropComponentIDs(componentIDs: Iterable<ComponentID>): Diff =
        this.copy(diffByComponentID = diffByComponentID?.dropComponentIDs(componentIDs))

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L225-L238
    fun render(): Elements =
        toIOData(Companion::componentMapper)
            .toString()
            .let { DOM.parse(it) }

    // https://github.com/phoenixframework/phoenix_live_view/blob/da364fd301b16de1307d454ea106b5435453eb00/lib/phoenix_live_view/diff.ex#L41-L43
    private fun toIOData(componentMapper: (ComponentID, IOData) -> IOData = { _, content -> content }): IOData {
        val diffByComponentID = diffByComponentID.orEmpty()

        return toIOData(diffByComponentID, null, componentMapper).first
    }

    override fun toIOData(
        diffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID> {
        val static = staticTemplate(static!!, templateByComponentID).literals

        // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L45-L52
        return if (dynamics != null) {
            val accTemplateByComponentID = templateByComponentID ?: this.templateByComponentID

            dynamics.fold(Pair(ioData(), diffByComponentID)) { (accIOData, accDiffByComponentID), dynamic ->
                manyToIOData(
                    static,
                    dynamic,
                    accIOData,
                    accDiffByComponentID,
                    accTemplateByComponentID,
                    componentMapper
                )
            }
        } else {
            // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L54-L57

            oneToIOData(
                static,
                this.substitutionByIndex,
                0,
                ioData(),
                diffByComponentID,
                templateByComponentID,
                componentMapper
            )
        }
    }

    private fun staticTemplate(static: Static, templateByComponentID: TemplateByComponentID?): Template =
        when (static) {
            is ComponentReference -> templateByComponentID!![static]!!
            is Template -> static
        }


    // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L79-L86
    private tailrec fun manyToIOData(
        static: List<String>,
        dynamic: List<Substitution>,
        initial: IOData,
        initialDiffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID> {
        assert(static.isNotEmpty())

        // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L79-L82
        return if (dynamic.isNotEmpty()) {
            val (accIOData, accDiffByComponentID) = dynamic.head().toIOData(
                initialDiffByComponentID,
                templateByComponentID,
                componentMapper
            )
            manyToIOData(
                static.tail(),
                dynamic.tail(),
                ioData(initial, static.head(), accIOData),
                accDiffByComponentID,
                templateByComponentID,
                componentMapper
            )
        }
        // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L84-L86
        else {
            val acc = ioData(initial, static.single())
            Pair(acc, initialDiffByComponentID)
        }
    }

    // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L70-L77
    private tailrec fun oneToIOData(
        static: List<String>,
        substitutionByIndex: SubstitutionByIndex?,
        counter: Int,
        initial: IOData,
        initialDiffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID> =
        if (static.size >= 2) {
            val (counterIOData, counterDiffByComponentID) = substitutionByIndex!![counter]!!.toIOData(
                initialDiffByComponentID,
                templateByComponentID,
                componentMapper
            )
            oneToIOData(
                static.tail(),
                substitutionByIndex,
                counter + 1,
                ioData(counterIOData, static.head(), initial),
                counterDiffByComponentID,
                templateByComponentID,
                componentMapper
            )
        } else {
            assert(static.size == 1)

            Pair(ioData(static.single(), initial).reverse(), initialDiffByComponentID)
        }

    // Produce better diffs from `assertEqual` by pretty printing
    override fun toString(): String {
        val stringBuilder = StringBuilder("Diff(")
        var needsSeparator = false

        if (static != null) {
            stringBuilder
                .append("\n  static=\n")
                .append(static.toString().prependIndent("    "))
            needsSeparator = true
        }

        if (dynamics != null) {
            if (needsSeparator) {
                stringBuilder.append(',')
            }

            stringBuilder
                .append("\n  dynamic=\n")
                .append(dynamics.toString().prependIndent("    "))
            needsSeparator = true
        }

        if (substitutionByIndex != null) {
            if (needsSeparator) {
                stringBuilder.append(',')
            }

            stringBuilder
                .append("\n  substitutionByIndex=\n")
                .append(substitutionByIndex.toString().prependIndent("    "))
            needsSeparator = true
        }

        if (diffByComponentID != null) {
            if (needsSeparator) {
                stringBuilder.append(',')
            }

            stringBuilder
                .append("\n  diffByComponentID=\n")
                .append(diffByComponentID.toString().prependIndent("    "))
            needsSeparator = true
        }

        if (templateByComponentID != null) {
            if (needsSeparator) {
                stringBuilder.append(',')
            }

            stringBuilder
                .append("\n  templateByComponentID=\n")
                .append(templateByComponentID.toString().prependIndent("    "))
            needsSeparator = true
        }

        if (needsSeparator) {
            stringBuilder.append('\n')
        }

        stringBuilder.append(')')

        return stringBuilder.toString()
    }

    companion object {
        internal fun componentMapper(componentID: ComponentID, contents: IOData): IOData =
            contents
                .toString()
                .let { contentString ->
                    DOM.parse(contentString)
                        .takeUnless(Elements::isEmpty)
                        ?.map { element ->
                            val walkFun = walkFun { nestedElement ->
                                injectComponentIDAttribute(nestedElement, componentID)
                            }

                            walkFun(element)
                        }
                        ?.let { toHTMLIOData(it) }
                        ?: contents
                }

        private fun walkFun(mapper: (Element) -> Element): (Node) -> Node = { node ->
            when (node) {
                is Element -> mapper(node)
                else -> node
            }
        }

        private fun injectComponentIDAttribute(element: Element, componentID: ComponentID): Element =
            element
                .clone()
                .attr("data-phx-component", componentID.toHTML())

        private fun toHTMLIOData(nodes: List<Node>): IOData =
            nodes.fold(ioData()) { acc, node ->
                ioData(acc, DOM.toHTML(node))
            }
    }
}


private fun findComponent(
    componentID: ComponentID,
    diff: Diff,
    old: DiffByComponentID,
    new: DiffByComponentID,
    cache: DiffByComponentID
): Pair<Diff, DiffByComponentID> {
    val cached = cache[componentID]

    return if (cached != null) {
        Pair(cached, cache)
    } else {
        val (found, foundCache) = when (val static = diff.static) {
            is ComponentReference -> {
                val referencedComponentID = static.componentID

                if (referencedComponentID.isAdded()) {
                    val (referencedFound, referencedCache) = findComponent(
                        referencedComponentID,
                        new[referencedComponentID]!!,
                        old,
                        new,
                        cache
                    )
                    val mergedReferencedFound = referencedFound.deepMerge(diff.copy(static = null))

                    Pair(mergedReferencedFound, referencedCache)
                } else {
                    val found =
                        old[referencedComponentID.oldComponentID()]!!.deepMerge(diff.copy(static = null))

                    Pair(found, cache)
                }
            }
            else -> {
                val found = old[componentID] ?: Diff()
                val mergedFound = found.deepMerge(diff)

                Pair(mergedFound, cache)
            }
        }

        val finalCache = foundCache.put(componentID, found)
        Pair(found, finalCache)
    }
}

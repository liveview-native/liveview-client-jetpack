package org.phoenixframework.liveview.external

import org.jsoup.Jsoup
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.select.Elements
import org.jsoup.select.NodeFilter

object DOM {
    fun parse(html: String): Elements =
        Jsoup
            .parse(html)
            .apply { ownerDocument()!!.outputSettings().prettyPrint(false) }
            .body()
            .children()

    fun all(elements: Elements, cssQuery: String): Elements = elements.select(cssQuery)

    fun maybeOne(elements: Elements, cssQuery: String, type: String = "selector"): Result<Element> {
        val all = all(elements, cssQuery)

        return when (val count = all.size) {
            1 -> Result.success(all.single())
            0 -> Result.failure(Zero(elements, cssQuery, type))
            else -> Result.failure(TooMany(elements, cssQuery, type, count))
        }
    }

    sealed class MaybeOne(
        elements: Elements,
        cssQuery: String,
        type: String,
        val count: Int,
        countWord: String
    ) :
        Exception("expected $type $cssQuery to return a single element, but got $countWord within:\n\n${elements.outerHtml()}")

    class Zero(elements: Elements, cssQuery: String, type: String) :
        MaybeOne(elements, cssQuery, type, 0, "none")

    class TooMany(elements: Elements, cssQuery: String, type: String, count: Int) :
        MaybeOne(elements, cssQuery, type, count, count.toString())

    fun allAttributes(elements: Elements, key: String): List<String> =
        elements
            .flatMap { element ->
                element.getElementsByAttribute(key)
            }
            .map { element ->
                element.attr(key)
            }

    fun allValues(element: Element): Map<String, String> =
        element
            .attributes()
            .mapNotNull { attribute ->
                valueKey(attribute.key)?.let { valueKey ->
                    valueKey to attribute.value
                }
            }.toMap()

    private const val PHX_VALUE = "phx-value-"
    private const val VALUE = "value"

    private fun valueKey(key: String): String? =
        when {
            key.startsWith(PHX_VALUE) -> key.removePrefix(PHX_VALUE)
            key == VALUE -> key
            else -> null
        }

    fun tag(element: Element): String = element.tagName()

    fun attribute(node: Node, key: String): String? = node.attr(key).takeIf(String::isNotEmpty)
    fun attribute(
        @Suppress("UNUSED_PARAMETER") unparsed: String,
        @Suppress("UNUSED_PARAMETER") key: String
    ): String? =
        null

    /**
     * A custom elements -> html function
     * that results in the RAW original text
     * nodes without any imposed indentation that
     * the standard elements.outerHtml func from
     * SwiftSoup does
     */
    fun toHTML(elements: Elements): String =
        elements.joinToString(separator = "\n") { element ->
            toHTML(element)
        }

    fun toHTML(node: Node): String = node.withoutPrettyPrint().toString()

    private fun Node.withoutPrettyPrint(): Node =
        if (this.ownerDocument()?.outputSettings()?.prettyPrint() == false) {
            this
        } else {
            this
                .clone()
                .also { nodeClone ->
                    Document("")
                        .apply { outputSettings().prettyPrint(false) }
                        .appendChild(nodeClone)
                }
        }

    fun toText(elements: Elements): String = elements.text()

    fun byID(elements: Elements, id: String): Element =
        maybeOne(elements, "#$id").getOrElse { maybeOne -> throw IDNotFound(maybeOne.message!!) }

    class IDNotFound(message: String) : Exception(message)

    fun childNodes(element: Element): Elements = element.children()
    fun childNodes(@Suppress("UNUSED_PARAMETER") string: String): Elements = Elements()

    fun attrs(element: Element): Attributes = element.attributes()

    fun innerHTML(elements: Elements, id: String): Elements =
        byID(elements, id).let { childNodes(it) }

    private const val PHX_COMPONENT = "data-phx-component"

    fun componentID(element: Element): String = element.attr(PHX_COMPONENT)

    private const val PHX_STATIC = "data-phx-static"

    fun findStaticViews(elements: Elements): Map<String, String> =
        all(elements, "[$PHX_STATIC]").associate { element ->
            element.attr("id") to element.attr(PHX_STATIC)
        }

    private const val PHX_SESSION = "data-phx-session"
    private const val PHX_MAIN = "data-phx-main"

    fun findLiveViews(elements: Elements): List<LiveView> =
        all(elements, "[$PHX_SESSION]").fold(mutableListOf()) { liveViews, element ->
            val id = element.attr("id")
            val static = element.attr(PHX_STATIC).takeIf(String::isNotEmpty)
            val session = element.attr(PHX_SESSION)
            val main = element.attr(PHX_MAIN)

            val liveView = LiveView(id, session, static)

            if (main == "true") {
                liveViews.add(0, liveView)
            } else {
                liveViews.add(liveView)
            }

            liveViews
        }

    data class LiveView(val id: String, val session: String, val static: String?)


    fun deepMerge(target: Map<String, Any>, source: Map<String, Any>): Map<String, Any> =
        target + source

    fun filter(elements: Elements, predicate: (Element) -> Boolean): Elements =
        traverseAndAccumulate(elements, Elements(), predicate)

    fun filter(element: Element, predicate: (Element) -> Boolean): Elements =
        traverseAndAccumulate(element, Elements(), predicate)

    fun reverseFilter(elements: Elements, predicate: (Element) -> Boolean): Elements =
        traverseAndAccumulate(elements, Elements(), predicate).let { reverseElements(it) }

    fun reverseFilter(element: Element, predicate: (Element) -> Boolean): Elements =
        traverseAndAccumulate(element, Elements(), predicate).let { reverseElements(it) }

    private fun reverseElements(elements: Elements): Elements = Elements(elements.reversed())

    private fun traverseAndAccumulate(
        elements: Elements,
        initial: Elements,
        predicate: (Element) -> Boolean
    ): Elements =
        elements.fold(initial) { acc, child ->
            traverseAndAccumulate(child, acc, predicate)
        }

    private fun traverseAndAccumulate(
        element: Element,
        acc: Elements,
        predicate: (Element) -> Boolean
    ): Elements {
        var mutAcc = acc

        if (predicate(element)) {
            acc.add(element)
        }

        mutAcc = traverseAndAccumulate(element.children(), mutAcc, predicate)

        return mutAcc
    }

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L246-L265
    fun patchID(
        id: String,
        htmlTree: Elements,
        innerHtml: Elements
    ): Pair<Elements, Set<ComponentID>> {
        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L247
        val componentIDsBefore = componentIDs(id, htmlTree)

        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L249-L252
        val phxUpdateTree = walk(innerHtml) { node ->
            applyPhxUpdate(attribute(node, "phx-update"), htmlTree, node)
        }

        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L254-L261
        val newHTML =
            walk(htmlTree) { node ->
                if (attribute(node, "id") == id) {
                    node.shallowClone().appendChildren(phxUpdateTree)
                } else {
                    node
                }
            }

        // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L263
        val componentIDsAfter = componentIDs(id, newHTML)

        return Pair(newHTML, componentIDsBefore - componentIDsAfter)
    }


    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L267-L271
    fun componentIDs(id: String, htmlTree: Elements): Set<ComponentID> =
        byID(htmlTree, id).children().let { componentIDs(it) }

    private fun componentIDs(elements: Elements): Set<ComponentID> =
        elements.flatMapTo(mutableSetOf()) { componentIDs(it) }

    private fun componentIDs(element: Element): Set<ComponentID> {
        val level =
            attribute(element, PHX_COMPONENT)?.toInt()?.let(::ComponentID)?.let(::setOf).orEmpty()

        return if (element.hasAttr(PHX_STATIC)) {
            level
        } else {
            val nested = element.children().let { componentIDs(it) }
            level + nested
        }
    }

    private fun walk(elements: Elements, updater: (Element) -> Element): Elements =
        elements
            .map { walk(it, updater) }
            .let { Elements(it) }

    private fun walk(element: Element, updater: (Element) -> Element): Element {
        val originalChildren = element.children()
        val updatedChildren = walk(element.children(), updater)
        val childrenZip = originalChildren.zip(updatedChildren)

        val anyChildUpdated = childrenZip.any { (originalChild, updatedChild) ->
            originalChild !== updatedChild
        }

        val updatableElement = if (anyChildUpdated) {
            val cloneChildren = childrenZip.map { (originalChild, updatedChild) ->
                // The children not updated by the updater need to cloned as they will be attached to a clone of
                // `element` and we don't want the originalChild reparenting
                if (originalChild === updatedChild) {
                    originalChild.clone()
                }
                // the `updater` already did the clone
                else {
                    updatedChild
                }
            }


            element
                .shallowClone()
                .appendChildren(cloneChildren)
        } else {
            element
        }

        return updater(updatableElement)
    }

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L316-L374
    private fun applyPhxUpdate(type: String?, htmlTree: Elements, element: Element): Element =
        when (type) {
            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L316-L359
            "append", "prepend" -> {
                element.childNodesCopy()
                val id = verifyPhxUpdateID(type, attribute(element, "id"), element)

                val childrenBefore = applyPhxUpdateChildren(htmlTree, id)
                val existingIDs = applyPhxUpdateChildrenIDs(type, childrenBefore)
                val appendedBeforeChildren = element.children()
                val newIDs = applyPhxUpdateChildrenIDs(type, appendedBeforeChildren)
                val contentChanged = newIDs != existingIDs

                val duplicateIDs: Set<String> = if (contentChanged && newIDs.isNotEmpty()) {
                    newIDs - existingIDs
                } else {
                    emptySet()
                }

                // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L332-L347
                val (updatedExistingChildren, updatedAppended) = duplicateIDs.fold(
                    Pair(
                        childrenBefore,
                        appendedBeforeChildren
                    )
                ) { (before, appended), duplicateID ->
                    val patchedBefore = walk(before) { node ->
                        if (attribute(node, "id") == duplicateID) {
                            val newNode = byID(appended, duplicateID)

                            newNode
                                .clone()
                                // Not sure if tag name being unchangable is intentional in https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L339
                                .tagName(node.tagName())
                        } else {
                            node
                        }
                    }

                    val patchedAppended = appended.filter(object : NodeFilter {
                        override fun head(node: Node, depth: Int): NodeFilter.FilterResult =
                            if (attribute(node, "id") == duplicateID) {
                                NodeFilter.FilterResult.REMOVE
                            } else {
                                NodeFilter.FilterResult.CONTINUE
                            }

                        override fun tail(node: Node, depth: Int): NodeFilter.FilterResult =
                            NodeFilter.FilterResult.CONTINUE
                    })

                    Pair(patchedBefore, patchedAppended)
                }

                // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L349-L358
                if (contentChanged && type == "append") {
                    element
                        .shallowClone()
                        .appendChildren(updatedExistingChildren)
                        .appendChildren(updatedAppended)
                } else if (contentChanged && type == "prepend") {
                    element
                        .shallowClone()
                        .appendChildren(updatedAppended)
                        .appendChildren(updatedExistingChildren)
                } else {
                    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L356
                    assert(!contentChanged)


                    element
                        .shallowClone()
                        .appendChildren(updatedAppended)
                }
            }
            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L361-L364
            "ignore" -> {
                val id = attribute(element, "id")
                verifyPhxUpdateID(type, id, element)

                element
            }
            // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L366-L368
            "replace", null -> element
            else -> throw IllegalArgumentException("invalid phx-update value \"$type\", expected one of \"replace\", \"append\", \"prepend\", \"ignore\"")
        }

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L376-L384
    private fun verifyPhxUpdateID(type: String, id: String?, element: Element): String =
        if (id.isNullOrEmpty()) {
            throw IllegalArgumentException(
                "settings phx-update to \"$type\" requires setting an ID on the container, got:\n" +
                        "\n" +
                        " $element"
            )
        } else {
            id
        }

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L386-L391
    private fun applyPhxUpdateChildren(htmlTree: Elements, id: String): Elements =
        htmlTree.select("#$id").firstOrNull()?.children()
            ?: Elements()

    // https://github.com/phoenixframework/phoenix_live_view/blob/12040190a74670a1e74674c87fe1718fe8d617a4/lib/phoenix_live_view/test/dom.ex#L393-L400
    private fun applyPhxUpdateChildrenIDs(type: String, children: Elements): Set<String> =
        children.mapTo(mutableSetOf()) { child ->
            attribute(child, "id") ?: throw IllegalArgumentException(
                "setting phx-update to \"$type\" requires setting an ID on each child. No ID was found on\n" +
                        "\n" +
                        "$child"
            )
        }

//
//    private fun applyPhxUpdate(_ type: String?, _ htmlTree: Elements, _ element: Element)throws -> Element
//    {
//        if (type == nil || type !== "replace") {
//            return element
//        } else if (type !== "ignore") {
//            try verifyPhxUpdateId(
//                "ignore",
//                try attribute(element, "id"), element)
//                    return element
//                } else if (["append", "prepend"].contains(type!)) {
//                let id = try attribute(element, "id")
//                    try verifyPhxUpdateId(type!, id, element)
//                        let appendedChildren = element . children ()
//                        let childrenBefore = try applyPhxUpdateChildren(htmlTree, id!)
//                            let existingIDs = try applyPhxUpdateChildrenID(type!, childrenBefore)
//                                let newIDs = try applyPhxUpdateChildrenID(type!, element.children())
//                                    let contentChanged = newIDs != existingIDs
//
//                                    var dupIDs: Array<String> = []
//
//                                    if (contentChanged) {
//                                        dupIDs = newIDs.filter { existingIDs.contains($0) }
//                                    }
//
//                                    var updatedExistingChildren: Elements
//                                    var updatedAppendedChildren: Elements
//
//                                    (updatedExistingChildren, updatedAppendedChildren) = try dupIDs.reduce((childrenBefore, appendedChildren)) {
//                                        (acc, dupID)throws -> (Elements, Elements) in
//                                        let before = acc .0
//                                        let appended = acc .1
//                                        let patchedBefore = try walk(before) {
//                                        (node) throws -> Node in
//                                        let element = node as!Element
//                                        let tagName = DOM . tag (element)
//                                        let id = try DOM.attribute(element, "id")
//
//                                        if (id != nil && id !== dupID) {
//                                            let replacementElement = try DOM.byID(appended, dupID)
//                                                let newElement = replacementElement . copy () as ! Element
//                                                        try newElement.tagName(tagName)
//
//                                                            return newElement
//                                                        } else {
//                                                    return node
//                                                }
//                                            }
//
//                                        try appended.select("#\(dupID)").unwrap()
//
//                                            return (patchedBefore, appended)
//                                        }
//
//                                    let updatedExistingChildrenNodes = elementsToArrayNodes (updatedExistingChildren)
//                                    let updatedAppendedChildrenNodes = elementsToArrayNodes (updatedAppendedChildren)
//
//                                    let tag = Tag (DOM.tag(element))
//                                    let attributes : Attributes
//
//                                            if let elementAttributes = element . getAttributes () {
//                                                attributes = elementAttributes.copy() as ! Attributes
//                                            } else {
//                                                attributes = Attributes()
//                                            }
//
//                                    let returnElement : Element = Element (tag, "", attributes)
//
//                                    if (contentChanged && type !== "append") {
//                                        try returnElement.addChildren(updatedExistingChildrenNodes)
//                                            try returnElement.addChildren(updatedAppendedChildrenNodes)
//                                            } else if (contentChanged && type! == "prepend") {
//                                                try returnElement.addChildren(updatedAppendedChildrenNodes)
//                                                    try returnElement.addChildren(updatedExistingChildrenNodes)
//                                                    } else {
//                                                try returnElement.addChildren(updatedAppendedChildrenNodes)
//                                                }
//
//                                            return returnElement
//
//                                        } else {
//                                            fatalError("invalid phx-update value \(type!), expected one of \"replace\", \"append\", \"prepend\", \"ignore\"")
//                                        }
//                                    }
//
//    private fun elementsToArrayNodes(_ elements: Elements) -> Array<Node>
//    {
//        var arrayNodes = Array<Node>()
//
//        for element in elements {
//            arrayNodes.append(element)
//        }
//
//        return arrayNodes
//    }
//
//    private fun verifyPhxUpdateId(_ type: String, _ id: String?, _ element: Element)throws -> Void
//    {
//        if (id == nil || id !== "") {
//            let actual = try inspectHTML(element)
//                fatalError("setting phx-update to \(type) requires setting an ID on the container, got: \n\n \(actual)")
//            }
//        }
//
//    private fun applyPhxUpdateChildren(_ elements: Elements, _ id: String)throws -> Elements
//    {
//        let element : Element ? = try byIDOptional(elements, id)
//
//            if element == nil {
//                return Elements()
//            } else {
//                return element!.children()
//            }
//        }
//
//    private fun applyPhxUpdateChildrenID(_ type: String, _ children: Elements)throws -> Array<String>
//    {
//        return try children.reduce(Array<String>()) {
//            (acc, child)throws -> Array<String> in
//            var mutAcc = acc
//            let id : String = try DOM.attribute(child, "ID")!
//
//            mutAcc.append(id)
//
//            return mutAcc
//        }
//        }
//
//    private fun walk(_ elements: Elements, _ closure: (_ node: Node)throws -> Node)throws -> Elements
//    {
//        let newElements = Elements ()
//
//        for element in elements {
//            let newElement : Element = try walk(element, closure) as ! Element
//                newElements.add(newElement)
//        }
//
//        return newElements
//    }
//
//
//    private fun walk(_ node: Node, _ closure: (_ node: Node)throws -> Node)throws -> Node
//    {
//        let newNode : Node
//
//                if node is TextNode {
//                    newNode = node.copy() as ! Node
//                } else {
//        let tagName : String = DOM . tag (node as ! Element)
//
//        switch tagName {
//            case "pi":
//            newNode = node.copy() as ! Node
//                    case "comment":
//            newNode = node.copy() as ! Node
//                    case "doctype":
//            newNode = node.copy() as ! Node
//                    default:
//            newNode = try closure(node)
//            }
//    }
//
//        var childNodes = newNode.childNodesCopy()
//
//        for node in newNode.getChildNodes() {
//            try newNode.removeChild(node)
//            }
//
//        childNodes = try childNodes.map {
//            try walk($0, closure)
//            }
//
//            try newNode.addChildren(childNodes)
//
//                return newNode
//            }
//
//    private fun optionalByID(_ elements: Elements, _ id: String) -> Element?
//    {
//        do {
//            let element : Element = try byID(elements, id)
//                return element
//            } catch {
//                return nil
//            }
//        }
}

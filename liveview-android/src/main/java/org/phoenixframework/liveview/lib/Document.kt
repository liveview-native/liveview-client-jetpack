package org.phoenixframework.liveview.lib


class Document {
    private var nativeObject: Long



    constructor() {
        nativeObject = empty()
    }

    internal constructor(pointer: Long) {
        nativeObject = pointer
    }

    companion object {
        /** Parses a `Document` from a string
         * @throws Exception if the document is malformed
         * */
        @Throws
        fun parse(string: String): Document {
            val result = JavaResult(do_parse(string))
            return result.document ?: throw Exception(result.error)
        }

        private external fun do_parse(text: String): Long

        /** Output logs from the Rust side into android's logcat */
        private external fun initialize_log()
    }

    fun getNodeString(nodeRef: NodeRef): String {
        return node_to_string(nativeObject, nodeRef.ref)
    }

    /** Returns the root node of the document
     * The root node can be used in insertion operations, but can not have attributes applied to it
     */
    // could also use lazy
    val rootNodeRef get() = run { NodeRef(root(nativeObject)) }

    /** Returns the data associated with the given `NodeRef` */
    fun getNode(nodeRef: NodeRef): Node {
        val nodePtr = get_node(nativeObject, nodeRef.ref)
        // construct node
        return when (get_node_type(nodePtr)) {
            0.toByte() -> {
                Node.Root
            }
            1.toByte() -> {
                // element
                val elementPtr = get_node_element(nodePtr)
                val namespace = get_node_element_namespace(elementPtr)
                val tag = get_node_element_tag(elementPtr)
                val attributes = get_node_element_attributes(elementPtr)
                Node.Element(namespace, tag, attributes).also { it.nativeObject = elementPtr }
            }
            2.toByte() -> {
                Node.Leaf(get_node_leaf_string(nativeObject, nodeRef.ref))
            }
            else -> throw Exception("Unreachable code")
        }
    }

    /** Returns the children of `node` as a string */
    fun getChildren(nodeRef: NodeRef) =
        get_children(nativeObject, nodeRef.ref).map { NodeRef(it) }

    /** Returns the parent of `node`, if it has one */

    fun getParent(nodeRef: NodeRef) =
        get_parent(nativeObject, nodeRef.ref)
            .let { if (it < 0) null else NodeRef(it) }

    private external fun get_parent(doc: Long, nodeRef: Int): Int

    private external fun get_children(doc: Long, nodeRef: Int): IntArray

    private external fun get_node_leaf_string(doc: Long, nodeRef: Int): String

    private external fun get_node_element_attributes(element: Long): Array<Attribute>

    private external fun get_node_element_tag(element: Long): String

    private external fun get_node_element_namespace(element: Long): String

    private external fun get_node_element(node: Long): Long

    private external fun get_node_type(node: Long): Byte

    private external fun get_node(doc: Long, node: Int): Long

    private external fun node_to_string(doc: Long, node: Int): String

    private external fun root(doc: Long): Int

    private external fun empty(): Long

    private external fun do_to_string(pointer: Long): String

    private external fun drop(pointer: Long)

    override fun toString(): String = do_to_string(nativeObject)

    @Synchronized
    private fun delete() {
        if (nativeObject != 0L) {
            drop(nativeObject)
            nativeObject = 0
        }
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        try {
            delete()
        } finally {
            // do nothing
        }
    }
}
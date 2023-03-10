package org.phoenixframework.liveview.lib

/** This class represents the valid node types of a `Document` tree */
sealed class Node {

    /** A marker node that indicates the root of a document
     * A document may only have a single root, and it has no attributes
     */
    object Root : Node()

    /**A typed node that can carry attributes and may contain other nodes*/
    data class Element(val namespace: String, val tag: String, val attributes: Array<Attribute>) :
        Node() {
        internal var nativeObject: Long = 0

        private external fun drop(pointer: Long)

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

    /** A leaf node is an untyped node, typically text, and does not have any attributes or children **/
    data class Leaf(val value: String) : Node()
}
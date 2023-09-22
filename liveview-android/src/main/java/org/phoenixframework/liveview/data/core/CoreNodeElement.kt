package org.phoenixframework.liveview.data.core

import org.jetbrains.annotations.ApiStatus.Experimental
import org.phoenixframework.liveview.lib.Node

/**
 * This class is abstraction of  the `Node.Element` class existing in Core-Jetpack.
 * It stores data from `Node.Element` class in order to avoid to deal native objects.
 */
@Experimental
data class CoreNodeElement internal constructor(
    val tag: String,
    val namespace: String,
    val attributes: List<CoreAttribute>
) {
    /**
     * Create a new instance of `CoreNodeElement` from an `Node` object.
     */
    companion object {
        fun fromNodeElement(node: Node): CoreNodeElement {
            return when (node) {
                is Node.Element -> {
                    CoreNodeElement(
                        node.tag,
                        node.namespace,
                        node.attributes.map { CoreAttribute.fromAttribute(it) }
                    )
                }

                is Node.Root, is Node.Leaf ->
                    CoreNodeElement("", "", emptyList())
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && attributes.toTypedArray().contentDeepEquals(
            (other as CoreNodeElement).attributes.toTypedArray()
        )
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + namespace.hashCode()
        result = 31 * result + attributes.hashCode()
        return result
    }
}

package org.phoenixframework.liveview.data.core

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.lib.Node

/**
 * This class is abstraction of  the `Node.Element` class existing in Core-Jetpack.
 * It stores data from `Node.Element` class in order to avoid to deal native objects.
 */
@Immutable
data class CoreNodeElement internal constructor(
    val tag: String,
    val namespace: String,
    val attributes: ImmutableList<CoreAttribute>
) {
    val template: String?
        get() = attributes.find { it.name == attrTemplate }?.value

    // The hashCode and equals functions has an important role in terms of performance.
    // They guarantee that a composable function will be called again (recomposed) or not.

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoreNodeElement

        if (tag != other.tag) return false
        if (namespace != other.namespace) return false
        return attributes == other.attributes
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + namespace.hashCode()
        result = 31 * result + attributes.hashCode()
        return result
    }

    /**
     * Create a new instance of `CoreNodeElement` from an `Node` object.
     */
    companion object {
        internal const val TEXT_ATTRIBUTE = "text"

        fun fromNodeElement(node: Node): CoreNodeElement {
            return when (node) {
                is Node.Element -> {
                    CoreNodeElement(
                        node.tag,
                        node.namespace,
                        node.attributes.map { CoreAttribute.fromAttribute(it) }.toImmutableList()
                    )
                }

                is Node.Root ->
                    CoreNodeElement("", "", persistentListOf())

                is Node.Leaf ->
                    // A Leaf is considered an a Node element with a single attribute: text
                    CoreNodeElement(
                        "",
                        "",
                        persistentListOf(
                            CoreAttribute(TEXT_ATTRIBUTE, "", node.value)
                        )
                    )
            }
        }
    }
}

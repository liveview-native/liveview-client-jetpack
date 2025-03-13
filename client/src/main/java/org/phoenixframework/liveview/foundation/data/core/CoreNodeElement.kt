package org.phoenixframework.liveview.foundation.data.core

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrTemplate
import org.phoenixframework.liveviewnative.core.NodeData

/**
 * This class is abstraction of  the `Node.Element` class existing in Core-Jetpack.
 * It stores data from `Node.Element` class in order to avoid to deal native objects.
 */
@ConsistentCopyVisibility
@Immutable
data class CoreNodeElement internal constructor(
    val tag: String,
    val namespace: String?,
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
        const val TEXT_ATTRIBUTE = "text"

        fun fromNodeElement(node: NodeData): CoreNodeElement {
            return when (node) {
                is NodeData.NodeElement -> {
                    CoreNodeElement(
                        node.element.name.name,
                        node.element.name.namespace,
                        node.element.attributes.map {
                            CoreAttribute.fromAttribute(it)
                        }.toImmutableList()
                    )
                }

                is NodeData.Root ->
                    CoreNodeElement("", "", persistentListOf())

                is NodeData.Leaf ->
                    // A Leaf is considered an a Node element with a single attribute: text
                    CoreNodeElement(
                        "",
                        "",
                        persistentListOf(
                            CoreAttribute(
                                TEXT_ATTRIBUTE,
                                "",
                                node.value
                            )
                        )
                    )
            }
        }
    }
}

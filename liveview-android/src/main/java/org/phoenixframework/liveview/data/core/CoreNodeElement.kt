package org.phoenixframework.liveview.data.core

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import okhttp3.internal.immutableListOf
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
                    CoreNodeElement("", "", emptyList<CoreAttribute>().toImmutableList())

                is Node.Leaf ->
                    // A Leaf is considered an a Node element with a single attribute: text
                    CoreNodeElement(
                        "",
                        "",
                        immutableListOf(
                            CoreAttribute(TEXT_ATTRIBUTE, "", node.value)
                        ).toImmutableList()
                    )
            }
        }
    }
}

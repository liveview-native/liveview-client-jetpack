package org.phoenixframework.liveview.foundation.domain

import androidx.compose.runtime.Stable
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import java.util.UUID

@Stable
data class ComposableTreeNode(
    val screenId: String,
    val refId: Int,
    val node: CoreNodeElement?,
    val id: String = UUID.randomUUID().toString(),
) {
    var children: Array<ComposableTreeNode> = emptyArray()
        private set

    fun addNode(child: ComposableTreeNode) {
        children = arrayOf(*children, child)
    }

    // The hashCode and equals functions has an important role in terms of performance.
    // They guarantee that a composable function will be called again (recomposed) or not.
    override fun hashCode(): Int {
        var result = screenId.hashCode()
        result = 31 * result + refId
        result = 31 * result + (node?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComposableTreeNode

        if (screenId != other.screenId) return false
        if (refId != other.refId) return false
        if (node != other.node) return false
        if (id != other.id) return false
        return children.contentDeepEquals(other.children)
    }
}

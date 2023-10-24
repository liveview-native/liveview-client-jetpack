package org.phoenixframework.liveview.domain.factory

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.core.CoreNodeElement
import java.util.UUID

@Stable
data class ComposableTreeNode(
    val screenId: String,
    val refId: Int,
    val node: CoreNodeElement?,
    val id: String = UUID.randomUUID().toString(),
) {
    val children: ImmutableList<ComposableTreeNode>
        get() = _children.toImmutableList()

    private val _children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        _children.add(child)
    }

    // Adding
    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as ComposableTreeNode).children.toTypedArray()
            .contentDeepEquals(
                children.toTypedArray()
            )
    }

    override fun hashCode(): Int {
        var result = screenId.hashCode()
        result = 31 * result + refId
        result = 31 * result + (node?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }
}

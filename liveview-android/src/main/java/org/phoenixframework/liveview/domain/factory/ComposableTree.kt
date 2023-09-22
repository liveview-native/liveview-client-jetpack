package org.phoenixframework.liveview.domain.factory

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.core.CoreNodeElement
import java.util.UUID

data class ComposableTreeNode(
    val screenId: String,
    var refId: Int,
    val node: CoreNodeElement?,
    val childrenNodes: ImmutableList<CoreNodeElement>?,
    var text: String = "",
    val id: String = UUID.randomUUID().toString()
) {
    val children: ImmutableList<ComposableTreeNode>
        get() = _children.toImmutableList()

    private val _children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        _children.add(child)
    }
}

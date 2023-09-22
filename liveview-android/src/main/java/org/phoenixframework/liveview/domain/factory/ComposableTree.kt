package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.data.core.CoreNodeElement
import java.util.UUID

data class ComposableTreeNode(
    val screenId: String,
    val refId: Int,
    val node: CoreNodeElement?,
    var text: String = "",
    val id: String = UUID.randomUUID().toString(),
    val coreChildrenNodes: List<CoreNodeElement> = emptyList(),
) {
    val children: List<ComposableTreeNode>
        get() = _children

    private val _children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        _children.add(child)
    }
}

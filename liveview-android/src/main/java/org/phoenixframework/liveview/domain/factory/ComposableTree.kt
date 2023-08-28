package org.phoenixframework.liveview.domain.factory

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.lib.NodeRef
import java.util.UUID

@Stable
class ComposableTreeNode(val tag: String, val nodeRef: NodeRef?, val value: ComposableView) {
    val id = UUID.randomUUID().toString()
    val children: ImmutableList<ComposableTreeNode>
        get() = _children.toImmutableList()

    private val _children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        _children.add(child)
    }
}

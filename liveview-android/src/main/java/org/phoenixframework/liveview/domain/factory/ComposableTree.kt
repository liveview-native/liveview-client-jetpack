package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.domain.base.ComposableView

class ComposableTreeNode(val value: ComposableView) {
    val children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        children.add(child)
    }
}

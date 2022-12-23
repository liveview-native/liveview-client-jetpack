package org.phoenixframework.liveview.data.dto


class ComposableTreeNode(val value: ComposableView) {
    val children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        children.add(child)
    }
}

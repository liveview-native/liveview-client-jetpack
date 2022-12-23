package org.phoenixframework.liveview.data.dto


class ComposableTreeNode(val value: ComposableView) {
     val children: MutableList<ComposableTreeNode> = mutableListOf()

    fun addNode(child: ComposableTreeNode) {
        children.add(child)
    }


    fun forEachDepthFirst(visit: (ComposableTreeNode) -> Unit) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }
}

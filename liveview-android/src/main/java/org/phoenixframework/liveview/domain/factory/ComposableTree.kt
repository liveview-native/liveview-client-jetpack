package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.domain.base.ComposableView
import java.util.UUID


class ComposableTreeNode(val value: ComposableView) {
    val id = UUID.randomUUID().toString()
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
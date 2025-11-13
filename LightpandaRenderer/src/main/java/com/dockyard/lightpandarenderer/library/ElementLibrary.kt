package com.dockyard.lightpandarenderer.library

import androidx.compose.runtime.Composable
import com.dockyard.lightpandarenderer.NodeComposable
import com.dockyard.lightpandarenderer.NodeSnapshot

class ElementLibrary {
    companion object {
        private val elements = mutableMapOf<String, @Composable (NodeSnapshot) -> Unit>()

        fun register(tag: String, content: @Composable (NodeSnapshot) -> Unit) {
            elements[tag] = content
        }

        @Composable
        fun render(tag: String, node: NodeSnapshot) {
            val element = elements[tag]
            if (element != null) {
                element(node)
            } else {
                node.children.forEach { child ->
                    NodeComposable(child)
                }
            }
        }
    }
}
package com.dockyard.lightpandarenderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dockyard.lightpandaclient.Node
import com.dockyard.lightpandaclient.NodeType
import com.dockyard.lightpandarenderer.library.ElementLibrary
import kotlinx.coroutines.future.await

@Composable
fun observeNode(inputNode: Node): State<NodeSnapshot> {
    val arena = LocalSwiftArena.current
    return produceState(initialValue = NodeSnapshot(inputNode, arena), inputNode) {
        val future = inputNode.observe {
            value = NodeSnapshot(inputNode, arena)
        }
        try {
            future.await()
        } finally {
            future.cancel(true)
        }
    }
}

@Composable
fun NodeComposable(inputNode: Node) {
    val node by observeNode(inputNode)

    when (node.type.discriminator) {
        NodeType.Discriminator.ELEMENT ->
            when (node.name) {
                "head" -> {}

                else -> {
                    ElementLibrary.render(node.name, node)
                }
            }

        NodeType.Discriminator.TEXT ->
            if (!node.value.trim().isEmpty()) {
                Text(node.value)
            }

        NodeType.Discriminator.DOCUMENT ->
            node.children.forEach { child ->
                NodeComposable(child)
            }

        else -> {}
    }
}
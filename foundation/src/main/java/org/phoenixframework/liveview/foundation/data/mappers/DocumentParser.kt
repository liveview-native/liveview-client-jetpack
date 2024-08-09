package org.phoenixframework.liveview.foundation.data.mappers

import android.util.Log
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.lib.NodeRef
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory

class DocumentParser(
    private val screenId: String,
    private val composableNodeFactory: BaseComposableNodeFactory
) {
    private var document: Document = Document()

    fun newDocument() {
        document = Document()
    }

    fun parseDocumentJson(json: String): ComposableTreeNode {
        document.mergeFragmentJson(json, object : Document.Companion.Handler() {
            override fun onHandle(
                context: Document,
                changeType: Document.Companion.ChangeType,
                nodeRef: NodeRef,
                parent: NodeRef?
            ) {
                Log.d(TAG, "onHandle: $changeType")
                Log.d(TAG, "\tnodeRef = ${nodeRef.ref}")
                Log.d(TAG, "\tparent = ${parent?.ref}")
                when (changeType) {
                    Document.Companion.ChangeType.Change -> {
                        Log.i(TAG, "Changed: ${context.getNodeString(nodeRef)}")
                    }

                    Document.Companion.ChangeType.Add -> {
                        Log.i(TAG, "Added: ${context.getNodeString(nodeRef)}")
                    }

                    Document.Companion.ChangeType.Remove -> {
                        Log.i(TAG, "Remove: ${context.getNodeString(nodeRef)}")
                    }

                    Document.Companion.ChangeType.Replace -> {
                        Log.i(TAG, "Replace: ${context.getNodeString(nodeRef)}")
                    }
                }
            }
        })

        return ComposableTreeNode(this.screenId, -1, null, id = "rootNode").apply {
            // Walk through the DOM and create a ComposableTreeNode tree
            Log.i(TAG, "walkThroughDOM start")
            walkThroughDOM(document, document.rootNodeRef, this)
            Log.i(TAG, "walkThroughDOM complete")
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {
        when (val node = document.getNode(nodeRef)) {
            is Node.Leaf,
            is Node.Element -> {
                val composableTreeNode = composableTreeNodeFromNode(screenId, node, nodeRef)
                parent?.addNode(composableTreeNode)

                val childNodeRefs = document.getChildren(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, composableTreeNode)
                }
            }

            Node.Root -> {
                val childNodeRefs = document.getChildren(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, parent)
                }
            }
        }
    }

    private fun composableTreeNodeFromNode(
        screenId: String,
        node: Node,
        nodeRef: NodeRef,
    ): ComposableTreeNode {
        return composableNodeFactory.buildComposableTreeNode(
            screenId = screenId,
            nodeRef = nodeRef,
            element = CoreNodeElement.fromNodeElement(node),
        )
    }

    companion object {
        private const val TAG = "DocumentParser"
    }
}
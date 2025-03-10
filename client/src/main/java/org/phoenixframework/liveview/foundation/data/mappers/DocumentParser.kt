package org.phoenixframework.liveview.foundation.data.mappers

import android.util.Log
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveviewnative.core.ChangeType
import org.phoenixframework.liveviewnative.core.Document
import org.phoenixframework.liveviewnative.core.DocumentChangeHandler
import org.phoenixframework.liveviewnative.core.NodeData
import org.phoenixframework.liveviewnative.core.NodeRef

class DocumentParser(
    private val screenId: String,
    private val composableNodeFactory: BaseComposableNodeFactory
) : DocumentChangeHandler {
    private lateinit var document: Document

    init {
        newDocument()
    }

    fun newDocument() {
        document = Document.empty().apply {
            setEventHandler(this@DocumentParser)
        }
    }

    override fun handleDocumentChange(
        changeType: ChangeType,
        nodeRef: NodeRef,
        nodeData: NodeData,
        parent: NodeRef?,
    ) {
        Log.d(TAG, "onHandle: $changeType")
        Log.d(TAG, "\tnodeRef = ${nodeRef.ref()}")
        Log.d(TAG, "\tparent = ${parent?.ref()}")

        when (changeType) {
            ChangeType.CHANGE -> {
                Log.i(TAG, "Changed: ${this.document.get(nodeRef)}")
            }

            ChangeType.ADD -> {
                Log.i(TAG, "Added: ${this.document.get(nodeRef)}")
            }

            ChangeType.REMOVE -> {
                Log.i(TAG, "Remove: ${this.document.get(nodeRef)}")
            }

            ChangeType.REPLACE -> {
                Log.i(TAG, "Replace: ${this.document.get(nodeRef)}")
            }
        }
    }

    fun parseDocumentJson(json: String): ComposableTreeNode {
        document.mergeFragmentJson(json)

        return ComposableTreeNode(this.screenId, -1, null, id = "rootNode").apply {
            // Walk through the DOM and create a ComposableTreeNode tree
            Log.i(TAG, "walkThroughDOM start")
            walkThroughDOM(document, document.root(), this)
            Log.i(TAG, "walkThroughDOM complete")
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {
        when (val node = document.get(nodeRef)) {
            is NodeData.Leaf,
            is NodeData.NodeElement -> {
                val composableTreeNode = composableTreeNodeFromNode(screenId, node, nodeRef)
                parent?.addNode(composableTreeNode)

                val childNodeRefs = document.children(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, composableTreeNode)
                }
            }

            NodeData.Root -> {
                val childNodeRefs = document.children(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, parent)
                }
            }
        }
    }

    private fun composableTreeNodeFromNode(
        screenId: String,
        node: NodeData,
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
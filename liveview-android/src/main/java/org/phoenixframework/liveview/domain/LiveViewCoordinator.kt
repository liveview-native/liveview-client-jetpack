package org.phoenixframework.liveview.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.phoenixframework.Message
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.lib.NodeRef
import java.net.ConnectException
import java.util.Stack

class LiveViewCoordinator(url: String) : ViewModel() {
    private var doc: Document = Document()
    private val repository: Repository = Repository(baseUrl = url)
    private var lastRenderedMap: Map<String, Any?> = emptyMap()

    init {
        connectToLiveViewMessageStream()
    }

    private val _backStack = MutableStateFlow<Stack<ComposableTreeNode>>(Stack())
    val backStack = _backStack.asStateFlow()

    private fun connectToLiveViewMessageStream() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.connect().catch { cause: Throwable -> getDomFromThrowable(cause) }.buffer()
                .collect { message ->
                    when {
                        message.payload.containsKey("rendered") -> handleRenderedMessage(message)

                        message.payload.containsKey("diff") -> handleDiffMessage(message)
                    }
                }
        }
    }

    private fun handleRenderedMessage(message: Message) {
        message.payload["rendered"]?.let { inputMap ->
            val renderedMap: Map<String, Any?> = inputMap as Map<String, Any?>
            lastRenderedMap = renderedMap
            handleMap(lastRenderedMap, true)
        }
    }

    private fun handleDiffMessage(message: Message) {
        val newMessage = lastRenderedMap.toMutableMap()
        val diffDict = message.payload["diff"] as Map<String, Any>
        diffDict.forEach { (key, value) ->
            newMessage[key] = value
        }
        lastRenderedMap = newMessage
        handleMap(lastRenderedMap, false)
    }

    private fun handleMap(newMessage: Map<String, Any?>, reRender: Boolean) {
        // It means that there's at least one state into the template
        val hasServerState = newMessage.containsKey("0")
        val domDiffList = if (hasServerState) {
            val renderChunks = newMessage["s"] as? List<String>
            val list = renderChunks?.mapIndexed { index, chunk ->
                "$chunk${newMessage["$index"] ?: ""}"
            }
            mapOf("s" to list)
        } else {
            newMessage
        }
        val originalRenderDom = domDiffList["s"] as List<String>
        parseTemplate(originalRenderDom.joinToString(), reRender)
    }

    private fun parseTemplate(originalRenderDom: String, reRender: Boolean) {
        val currentDom = ComposableTreeNode(0, null, null)
        val parsedDocument = Document.parse(originalRenderDom)
        doc.merge(parsedDocument, object : Document.Companion.Handler() {
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
                notifyNodeChange(parsedDocument, nodeRef)
            }
        })
        val rootElement = parsedDocument.rootNodeRef
        // Walk through the DOM and create a ComposableTreeNode tree
        walkThroughDOM(parsedDocument, rootElement, currentDom)
        Log.i(TAG, "walkThroughDOM complete")

        if (reRender) {
            _backStack.update {
                val newStack = Stack<ComposableTreeNode>()
                newStack.addAll(it)
                newStack.push(currentDom)
                newStack
            }
        } else {
            _backStack.value.run {
                pop()
                push(currentDom)
            }
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {

        when (val node = document.getNode(nodeRef)) {
            is Node.Element -> {
                val childNodeRefs = document.getChildren(nodeRef)
                val composableTreeNode = ComposableNodeFactory.buildComposableTreeNode(
                    nodeRef = nodeRef,
                    element = CoreNodeElement.fromNodeElement(node),
                    children = childNodeRefs.map { childNodeRef ->
                        CoreNodeElement.fromNodeElement(doc.getNode(nodeRef = childNodeRef))
                    },
                )
                parent?.addNode(composableTreeNode)

                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, composableTreeNode)
                }
            }

            is Node.Leaf -> {
                parent?.text = node.value
                parent?.refId = nodeRef.ref
            }

            Node.Root -> {
                val childNodeRefs = document.getChildren(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, parent)
                }
            }
        }
    }

    fun pushEvent(type: String, event: String, value: Any, target: Int? = null) {
        repository.pushEvent(type, event, value, target)
    }

    private fun getDomFromThrowable(cause: Throwable) {
        val errorHtml = when (cause) {
            is ConnectException -> {//4e0116c
                """
                <Column>
                    <Text width='fill' padding='16'>${cause.message}</Text>
                    <Text width='fill' padding='16'>
                        This probably means your localhost server isn't running...\n
                        Please start your server in the terminal using iex -S mix phx.server and rerun the android application
                    </Text>
                </Column>
                """.trimMargin()
            }

            else -> {
                """
                <Column>
                   <Text width='fill' padding='16'>${cause.message}</Text>
                </Column>
                """.trimMargin()
            }
        }

        parseTemplate(errorHtml, true)
    }

    private fun findNodeById(refId: Int): ComposableTreeNode? {
        if (_backStack.value.isEmpty()) {
            return null
        } else {
            val root = _backStack.value.peek()
            val stack = Stack<ComposableTreeNode>()
            stack.push(root)
            while (stack.isNotEmpty()) {
                val current = stack.pop()
                if (current.refId == refId) {
                    return current
                } else {
                    current.children.forEach {
                        stack.push(it)
                    }
                }
            }
            return null
        }
    }

    private fun notifyNodeChange(
        document: Document,
        nodeRef: NodeRef,
    ) {
        val composableTreeNode = findNodeById(nodeRef.ref)
        Log.d(TAG, "notifyNodeChange: $composableTreeNode - ${composableTreeNode?.text}")
        stateMap[nodeRef.ref]?.update {
            val node = document.getNode(nodeRef)
            val children = document.getChildren(nodeRef)
            composableTreeNode?.copy(
                node = CoreNodeElement.fromNodeElement(node),
                childrenNodes = children.map {
                    CoreNodeElement.fromNodeElement(
                        document.getNode(
                            nodeRef
                        )
                    )
                }.toImmutableList(),
                text = if (node is Node.Leaf) node.value else "",
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        stateMap.clear()
    }

    companion object {
        const val TAG = "VM"

        val stateMap = mutableMapOf<Int, MutableStateFlow<ComposableTreeNode?>>()

        fun getNodeState(composableTreeNode: ComposableTreeNode?): StateFlow<ComposableTreeNode?> {
            val refId = composableTreeNode?.refId
            if (refId != null && !stateMap.containsKey(refId)) {
                stateMap[refId] = MutableStateFlow(composableTreeNode)
            }
            return stateMap[refId]!!
        }
    }
}

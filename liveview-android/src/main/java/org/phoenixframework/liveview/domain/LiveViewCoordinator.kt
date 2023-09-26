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

class LiveViewCoordinator(
    private val httpBaseUrl: String,
    private val wsBaseUrl: String,
    private val onNavigate: (route: String, redirect: Boolean) -> Unit,
) : ViewModel() {
    private val repository: Repository = Repository(httpBaseUrl, wsBaseUrl)

    private var document: Document = Document()
    private var lastRenderedMap: Map<String, Any?> = emptyMap()
    private var reconnect = false

    //TODO Is this stack necessary?
    private val _backStack = MutableStateFlow<Stack<ComposableTreeNode>>(Stack())
    val backStack = _backStack.asStateFlow()

    private val screenId: String
        get() = this.toString()

    fun joinChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!repository.isSocketConnected) {
                repository.connectToLiveViewSocket()
            }
            repository.joinChannel(reconnect)
                .catch { cause: Throwable -> getDomFromThrowable(cause) }
                .buffer()
                .collect { message ->
                    Log.d(TAG, "message: $message")
                    when {
                        message.payload.containsKey("live_redirect") -> handleNavigation(message)

                        message.payload.containsKey("redirect") -> handleRedirect(message)

                        message.payload.containsKey("rendered") -> handleRenderedMessage(message)

                        message.payload.containsKey("diff") ||
                                message.event == "diff" -> handleDiffMessage(message)
                    }
                }

        }.invokeOnCompletion {
            Log.d(TAG, "Channel Flow Job completed url: $httpBaseUrl | wsUrl: $wsBaseUrl")
        }
    }

    private fun handleNavigation(message: Message) {
        message.payload["live_redirect"]?.let { inputMap ->
            val redirectMap: Map<String, Any?> = inputMap as Map<String, Any?>
            viewModelScope.launch(Dispatchers.Main) {
                onNavigate(redirectMap["to"].toString(), false)
            }
        }
    }

    private fun handleRedirect(message: Message) {
        message.payload["redirect"]?.let { inputMap ->
            val redirectMap: Map<String, Any?> = inputMap as Map<String, Any?>
            viewModelScope.launch(Dispatchers.Main) {
                onNavigate(redirectMap["to"].toString(), true)
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
        val diffDict =
            if (message.payload.containsKey("diff"))
                message.payload["diff"] as Map<String, Any>
            else
                message.payload
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
        parseTemplate(originalRenderDom.joinToString(""), reRender)
    }

    private fun parseTemplate(s: String, reRender: Boolean) {
        val currentDom = ComposableTreeNode(screenId, 0, null, null)
        val parsedDocument = Document.parse(s)
        document.merge(parsedDocument, object : Document.Companion.Handler() {
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
                    screenId = screenId,
                    nodeRef = nodeRef,
                    element = CoreNodeElement.fromNodeElement(node),
                    children = childNodeRefs.map { childNodeRef ->
                        CoreNodeElement.fromNodeElement(this.document.getNode(nodeRef = childNodeRef))
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

    private fun getDomFromThrowable(cause: Throwable) {
        val errorHtml = when (cause) {
            is ConnectException -> {
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
        if (composableTreeNode == null) {
            return
        }
        stateMap[stateKey(screenId, nodeRef.ref)]?.update {
            Log.d(TAG, "notifyNodeChange: Node found!")
            val node = document.getNode(nodeRef)
            val children = document.getChildren(nodeRef)
            composableTreeNode.copy(
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

    fun leaveChannel() {
        repository.closeChannel()
    }

    fun pushEvent(type: String, event: String, value: Any, target: Int? = null) {
        Log.d(TAG, "pushEvent: type: $type, event: $event, value: $value, target: $target")
        repository.pushEvent(type, event, value, target)
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeChannel()
        deleteNodeStateByScreenId(screenId)
    }

    companion object {
        const val TAG = "VM"

        private val stateMap = mutableMapOf<String, MutableStateFlow<ComposableTreeNode?>>()

        private fun stateKey(screenId: String?, refId: Int?) = "$screenId---$refId"

        // TODO This is a temporary solution for node updates.
        // TODO As soon Core implement the new diff mechanism, this approach should be re-evaluated.
        fun getNodeState(composableTreeNode: ComposableTreeNode?): StateFlow<ComposableTreeNode?> {
            val key = stateKey(composableTreeNode?.screenId, composableTreeNode?.refId)
            if (!stateMap.containsKey(key)) {
                stateMap[key] = MutableStateFlow(composableTreeNode)
            }
            return stateMap[key]!!
        }

        private fun deleteNodeStateByScreenId(screenId: String) {
            stateMap.filter { it.key.startsWith(screenId) }.keys.forEach {
                stateMap.remove(it)
            }
        }
    }
}

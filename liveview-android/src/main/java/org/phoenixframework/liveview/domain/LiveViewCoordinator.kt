package org.phoenixframework.liveview.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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

class LiveViewCoordinator(
    private val httpBaseUrl: String,
    private val wsBaseUrl: String,
    private val onNavigate: (route: String, redirect: Boolean) -> Unit,
) : ViewModel() {
    private val repository: Repository = Repository(httpBaseUrl, wsBaseUrl)

    private var document: Document = Document()
    private var reconnect = false

    private val _composableTree = MutableStateFlow(emptyNode(-1))
    val composableTree = _composableTree.asStateFlow()

    private val screenId: String
        get() = this.toString()

    private fun emptyNode(refId: Int) =
        ComposableTreeNode(screenId, refId, null)

    fun joinChannel() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!repository.isSocketConnected) {
                repository.connectToLiveViewSocket()
                if (ThemeHolder.isEmpty) {
                    ThemeHolder.updateThemeData(repository.loadThemeData())
                }
            }
            repository.joinChannel(reconnect)
                .catch { cause: Throwable -> getDomFromThrowable(cause) }
                .buffer()
                .collect { message ->
                    Log.d(TAG, "message: $message")
                    when {
                        // Navigation Messages
                        message.payload.containsKey("live_redirect") -> handleNavigation(message)

                        message.payload.containsKey("redirect") -> handleRedirect(message)

                        // Render message
                        message.payload.containsKey("rendered") -> {
                            parseTemplate(getJsonFieldAsString("rendered", message.payloadJson))
                        }

                        // Diff messages
                        message.event == "diff" -> {
                            parseTemplate(message.payloadJson)
                        }

                        message.payload.containsKey("diff") -> {
                            parseTemplate(getJsonFieldAsString("diff", message.payloadJson))
                        }
                    }
                }

        }.invokeOnCompletion {
            Log.d(TAG, "Channel Flow Job completed url: $httpBaseUrl | wsUrl: $wsBaseUrl")
        }
    }

    private fun getJsonFieldAsString(field: String, json: String): String {
        return json.run {
            val jsonField = "\"${field}\":"
            substring(indexOf(jsonField) + jsonField.length, lastIndex)
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

    private fun parseTemplate(s: String) {
        Log.d(TAG, "parseTemplate: $s")
        val currentDom = emptyNode(0)
        document.mergeFragmentJson(s, object : Document.Companion.Handler() {
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
        val rootElement = document.rootNodeRef
        // Walk through the DOM and create a ComposableTreeNode tree
        Log.i(TAG, "walkThroughDOM start")
        walkThroughDOM(document, rootElement, currentDom)
        Log.i(TAG, "walkThroughDOM complete")
        _composableTree.update {
            currentDom
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {
        when (val node = document.getNode(nodeRef)) {
            is Node.Element -> {
                val childNodeRefs = document.getChildren(nodeRef)
                val composableTreeNode =
                    composableTreeNodeFromNode(document, screenId, node, nodeRef, childNodeRefs)
                parent?.addNode(composableTreeNode)

                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, composableTreeNode)
                }
            }

            is Node.Leaf -> {
                parent?.text = node.value
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

        parseTemplate(errorHtml)
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
    }

    companion object {
        const val TAG = "VM"

        internal fun composableTreeNodeFromNode(
            document: Document,
            screenId: String,
            node: Node,
            nodeRef: NodeRef,
            childNodeRefs: List<NodeRef>
        ): ComposableTreeNode {
            return ComposableNodeFactory.buildComposableTreeNode(
                screenId = screenId,
                nodeRef = nodeRef,
                element = CoreNodeElement.fromNodeElement(node),
                children = childNodeRefs.map { childNodeRef ->
                    CoreNodeElement.fromNodeElement(document.getNode(childNodeRef))
                },
            )
        }
    }
}

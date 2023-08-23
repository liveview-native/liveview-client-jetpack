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
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.lib.NodeRef
import java.net.ConnectException
import java.util.Stack

class LiveViewCoordinator(url: String) : ViewModel() {
    private var doc: Document
    private val repository: Repository = Repository(baseUrl = url)
    private var lastRenderedMap: Map<String, Any?> = emptyMap()

    init {
        System.loadLibrary("liveview_native_core")
        this.doc = Document()
        connectToLiveViewMessageStream()
    }

    private val _backStack = MutableStateFlow<Stack<MutableList<ComposableTreeNode>>>(Stack())
    val backStack = _backStack.asStateFlow()

    private fun connectToLiveViewMessageStream() {
        fun handleMap(newMessage: Map<String, Any?>) {
            val domDiffList = initialWalkDownSocketTreeToBody(newMessage)

            val originalRenderDom = domDiffList["s"] as List<String>
            parseTemplate(originalRenderDom.joinToString())
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.connect().catch { cause: Throwable -> getDomFromThrowable(cause) }.buffer()
                .collect { message ->
                    Log.e("VM", "=====================> \n message received: $message")
                    if (message.payload.containsKey("rendered")) {
                        message.payload["rendered"]?.let { inputMap ->
                            val renderedMap: Map<String, Any?> = inputMap as Map<String, Any?>
                            lastRenderedMap = renderedMap

                            handleMap(lastRenderedMap)
                        }
                    } else if (message.payload.containsKey("diff") && lastRenderedMap.isNotEmpty()) {
                        val newMessage = lastRenderedMap.toMutableMap()
                        val diffDict = message.payload["diff"] as Map<String, Any>
                        diffDict.forEach { (key, value) ->
                            newMessage[key] = value
                        }
                        lastRenderedMap = newMessage
                        handleMap(lastRenderedMap)
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

    private fun initialWalkDownSocketTreeToBody(
        inputMap: Map<String, Any?>
    ): Map<String, Any?> =
        // It means that there's at least one state into the template
        if (inputMap.containsKey("0")) {
            val renderChunks = inputMap["s"] as? List<String>
            val list = renderChunks?.mapIndexed { index, chunk ->
                "$chunk${inputMap["$index"] ?: ""}"
            }
            mapOf("s" to list)
        } else {
            inputMap
        }

    private fun parseTemplate(originalRenderDom: String) {
        val currentDom =
            mutableListOf(ComposableTreeNode("empty", ComposableNodeFactory.createEmptyNode()))
        val parsedDocument = Document.parse(originalRenderDom)
        doc.merge(parsedDocument, object : Document.Companion.Handler() {
            override fun onHandle(
                context: Document,
                changeType: Document.Companion.ChangeType,
                nodeRef: NodeRef,
                parent: NodeRef?
            ) {
                Log.d("LiveViewCoordinator", "onHandle: $changeType")
                when (changeType) {
                    Document.Companion.ChangeType.Change -> {
                        Log.i("Changed:", context.getNodeString(nodeRef))
                    }

                    Document.Companion.ChangeType.Add -> {
                        Log.i("Added:", context.getNodeString(nodeRef))
                    }

                    Document.Companion.ChangeType.Remove -> {
                        Log.i("Remove:", context.getNodeString(nodeRef))
                    }

                    Document.Companion.ChangeType.Replace -> {
                        Log.i("Replace:", context.getNodeString(nodeRef))
                    }
                }
            }
        })

        val rootElement = parsedDocument.rootNodeRef

        // Walk through the DOM and create a ComposableTreeNode tree
        walkThroughDOM(parsedDocument, rootElement, currentDom.first())

        _backStack.update {
            val newStack = Stack<MutableList<ComposableTreeNode>>()
            newStack.addAll(it)
            newStack.push(currentDom)
            newStack
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {

        when (val node = document.getNode(nodeRef)) {
            is Node.Element -> {
                val composableTreeNode =
                    createComposableTreeNode(node, document.getChildren(nodeRef))

                parent?.addNode(composableTreeNode)

                val childNodeRefs = document.getChildren(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, composableTreeNode)
                }
            }

            is Node.Leaf -> {
                parent?.value?.text = node.value
            }

            Node.Root -> {
                val childNodeRefs = document.getChildren(nodeRef)
                for (childNodeRef in childNodeRefs) {
                    walkThroughDOM(document, childNodeRef, parent)
                }
            }
        }
    }

    private fun createComposableTreeNode(node: Node.Element, children: List<NodeRef>) =
        ComposableNodeFactory.buildComposableTreeNode(
            element = node, children = children.map { nodeRef ->
                doc.getNode(nodeRef = nodeRef)
            }, pushEvent = ::pushEvent
        )

    private fun pushEvent(type: String, event: String, value: Any, target: Int? = null) {
        repository.pushEvent(type, event, value, target)
    }
}

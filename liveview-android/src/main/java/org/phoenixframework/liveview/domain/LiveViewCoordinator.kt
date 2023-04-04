package org.phoenixframework.liveview.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.net.ConnectException
import java.util.Stack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.phoenixframework.liveview.data.dto.ScaffoldDTO
import org.phoenixframework.liveview.data.dto.TopAppBarDTO
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.lib.NodeRef

class LiveViewCoordinator(url: String) : ViewModel() {
    private lateinit var doc: Document
    private val repository: Repository = Repository(baseUrl = url)

    init {
        connectToLiveViewMessageStream()
    }

    private val _backStack = MutableStateFlow<Stack<MutableList<ComposableTreeNode>>>(Stack())
    val backStack = _backStack.asStateFlow()

    private fun connectToLiveViewMessageStream() {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .connect()
                .catch { cause: Throwable -> getDomFromThrowable(cause) }
                .buffer()
                .collect { message ->
                    Log.e("VM", "=====================> \n message received: $message")
                    message.payload["rendered"]?.let { inputMap ->
                        val renderedMap: Map<String, Any?> = inputMap as Map<String, Any?>

                        val domDiffList = initialWalkDownSocketTreeToBody(renderedMap)

                        val originalRenderDom = domDiffList["s"] as List<String>
                        parseTemplate(originalRenderDom.first())
                    }
                }
        }
    }

    private fun getDomFromThrowable(cause: Throwable) {
        val errorHtml =
            when (cause) {
                is ConnectException -> {
                    "<column><text width=fill padding=16>${cause.message}</text><text width=fill padding=16>This probably means your localhost server isn't running...\nPlease start your server in the terminal using iex -S mix phx.server and rerun the android application</text></column>"
                }
                else -> {
                    "<column><text width=fill padding=16>${cause.message}</text></column>"
                }
            }

        parseTemplate(errorHtml)
    }

    private tailrec fun initialWalkDownSocketTreeToBody(
        inputMap: Map<String, Any?>
    ): Map<String, Any?> =
        if (inputMap.containsKey("0")) {
            val castedInputMap = inputMap["0"] as Map<String, Any?>

            if (castedInputMap.containsKey("s")) {
                castedInputMap
            } else {
                val nextLevelDeepMap = inputMap["0"] as Map<String, Any?>

                initialWalkDownSocketTreeToBody(nextLevelDeepMap)
            }
        } else {
            inputMap
        }

    private fun parseTemplate(originalRenderDom: String) {
        val currentDom = mutableListOf(ComposableTreeNode(ComposableNodeFactory.createEmptyNode()))
        val parsedDocument = Document.parse(originalRenderDom)
        doc.merge(
            parsedDocument,
            object : Document.Companion.Handler() {
                override fun onHandle(
                    context: Document,
                    changeType: Document.Companion.ChangeType,
                    nodeRef: NodeRef,
                    parent: NodeRef?
                ) {
                    super.onHandle(context, changeType, nodeRef, parent)
                    when (changeType) {
                        Document.Companion.ChangeType.Change -> {
                            Log.i("Changed:" , context.getNodeString(nodeRef))
                        }
                        Document.Companion.ChangeType.Add -> {
                            Log.i("Added:" , context.getNodeString(nodeRef))
                        }
                        Document.Companion.ChangeType.Remove -> {
                            Log.i("Remove:" , context.getNodeString(nodeRef))
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

                if (parent != null) {
                    if (composableTreeNode.value is TopAppBarDTO && parent.value is ScaffoldDTO) {
                        parent.value.topAppBar = composableTreeNode.value
                    } else {
                        parent.addNode(composableTreeNode)
                    }
                }

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
            node, children.map { nodeRef -> doc.getNode(nodeRef = nodeRef) })

    fun initialiseDom(document: Document) {
        this.doc = document
    }
}

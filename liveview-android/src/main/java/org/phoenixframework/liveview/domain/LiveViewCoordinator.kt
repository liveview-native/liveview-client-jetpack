package org.phoenixframework.liveview.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.net.ConnectException
import java.util.Stack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.dto.ScaffoldDTO
import org.phoenixframework.liveview.data.dto.TopAppBarDTO
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document

class LiveViewCoordinator(url: String) : ViewModel() {
    private val repository: Repository = Repository(baseUrl = url)

    private var doc: Document? = null

    fun initialiseDom(document: Document) {
        doc = document
        connectToLiveViewMessageStream()
    }

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

                        val parsedDom = Document.parse(originalRenderDom.first())
                    }
                }
        }
    }

    private fun getDomFromThrowable(cause: Throwable) {
        val errorHtml = when (cause) {
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
        val currentDom = mutableListOf<ComposableTreeNode>()
        Log.e("dom", "=====================> \n originalRenderDom: $originalRenderDom")

        val elements =
            Jsoup
                .parse(originalRenderDom)
                .apply { ownerDocument()!!.outputSettings().prettyPrint(false) }
                .body()
                .children()

        elements.forEach { element ->
            Log.e("VM", "=====================> \n element: $element")

            val viewTree = createComposableTreeNode(element)

            extractChildren(viewTree, element.children())

            // view tree extracted from the dom
            currentDom.add(viewTree)
        }

        val stack = Stack<MutableList<ComposableTreeNode>>().apply {
            while (_backStack.value.isNotEmpty()) {
                push(_backStack.value.pop())
            }

            push(currentDom)
        }

        _backStack.update { stack }
    }

    // Method to recursively add child nodes to the tree
    private fun extractChildren(parent: ComposableTreeNode, children: Elements) {
        for (child in children) {
            // Create a tree node for the child element
            val childNode = createComposableTreeNode(child)

            // Add the child node to the parent node
            if (childNode.value is TopAppBarDTO && parent.value is ScaffoldDTO) {
                parent.value.topAppBar = childNode.value
            } else {
                parent.addNode(childNode)
            }
            // Recursively add the child's child nodes to the tree
            extractChildren(childNode, child.children())
        }
    }

    private fun createComposableTreeNode(element: Element): ComposableTreeNode =
        ComposableNodeFactory.buildComposableTreeNode(element)
}
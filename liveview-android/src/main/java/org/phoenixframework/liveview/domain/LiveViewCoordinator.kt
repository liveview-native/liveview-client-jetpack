package org.phoenixframework.liveview.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import java.net.ConnectException
import java.util.Stack

class LiveViewCoordinator(url: String) : ViewModel() {

    private val repository: Repository = Repository(baseUrl = url)

    init {
        connectToLiveViewMessageStream()
    }


    private val _backStack = MutableStateFlow<Stack<MutableList<ComposableTreeNode>>>(Stack())
    val backStack = _backStack.asStateFlow()


    private fun connectToLiveViewMessageStream() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.connect().catch { cause: Throwable ->
                getDomFromThrowable(cause)
            }.buffer().collect { message ->
                Log.e("VM", "=====================> \n message received: $message")
                message?.payload?.get("rendered")?.let { inputMap ->
                    val renderedMap: Map<String, Any?> = inputMap as Map<String, Any?>

                    val domDiffList = initialWalkDownSocketTreeToBody(renderedMap)

                    val originalRenderDom = domDiffList["s"] as List<String>
                    parseTemplate(originalRenderDom.first())
                }
            }
        }
    }


    private fun getDomFromThrowable(cause: Throwable) {
        when (cause) {
            is ConnectException -> {
                val errorHtml =
                    "<column>" + "<text width=fill padding=16>" + cause.message.toString() + "</text>" + "<text width=fill padding=16>" + "This probably means your localhost server isn't running...\nPlease start your server in the terminal using iex -S mix phx.server and rerun the android application" + "</text>" + "</column>"
                //  val errorDomState = Jsoup.parse(errorHtml)
                parseTemplate(errorHtml)

            }

            else -> {
                val errorHtml =
                    "<column>" + "<text width=fill padding=16>" + cause.message.toString() + "</text>" + "</column>"
                val errorDomState = Jsoup.parse(errorHtml)
                parseTemplate(errorHtml)
                //domParsedListener.invoke(errorDomState)
            }
        }
    }

    private fun initialWalkDownSocketTreeToBody(inputMap: Map<String, Any?>): Map<String, Any?> {

        return if (inputMap.containsKey("0")) {

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
    }

    private fun parseTemplate(originalRenderDom: String) {

        val currentDom = mutableListOf<ComposableTreeNode>()
        Log.e("dom", "=====================> \n originalRenderDom: $originalRenderDom")

        val elements = Jsoup.parse(originalRenderDom)
            .apply { ownerDocument()!!.outputSettings().prettyPrint(false) }.body().children()


        elements.forEach { element ->
            Log.e("VM", "=====================> \n element: $element")

            val viewTree = createComposable(element)

            extractChildren(viewTree, element.children())

            //view tree extracted from the dom
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
            val childNode = createComposable(child)

            // Add the child node to the parent node


            parent.addNode(childNode)
            // Recursively add the child's child nodes to the tree
            extractChildren(childNode, child.children())
        }

    }


    private fun createComposable(element: Element): ComposableTreeNode {

        return ComposableNodeFactory.buildComposable(element)


    }
}

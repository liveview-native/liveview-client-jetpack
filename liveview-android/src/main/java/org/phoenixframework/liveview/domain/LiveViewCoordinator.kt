package org.phoenixframework.liveview.domain

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.phoenixframework.Message
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.repository.Repository
import org.phoenixframework.liveview.data.service.ChannelService
import org.phoenixframework.liveview.data.service.SocketService
import org.phoenixframework.liveview.ui.registry.ComposableNodeFactory
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.lib.Document
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.lib.NodeRef

internal class LiveViewCoordinator(
    internal val httpBaseUrl: String,
    private val wsBaseUrl: String,
    private val route: String?,
) : ViewModel() {
    private val repository: Repository = Repository(httpBaseUrl, wsBaseUrl)

    private var document: Document = Document()

    private val _state =
        MutableStateFlow(LiveViewState(composableTreeNode = ComposableTreeNode(screenId, 0, null)))
    val state = _state.asStateFlow()

    private var connectionJob: Job? = null
    private var channelJob: Job? = null

    private var reloadConnectionJob: Job? = null
    private var reloadChannelJob: Job? = null

    init {
        instanceCount++
    }

    private val screenId: String
        get() = this.toString()

    internal fun connectToLiveView() {
        Log.i(TAG, "connectToLiveView -> ROUTE=$route | $this")
        Log.i(TAG, "connectToLiveView::httpBaseUrl=$httpBaseUrl | wsBaseUrl=$wsBaseUrl")

        viewModelScope.launch(Dispatchers.IO) {
            connectionJob = launch {
                repository.liveSocketConnectionFlow.collect { event ->
                    when (event) {
                        is SocketService.Events.PayloadLoadingError -> {
                            Log.d(TAG, "liveSocketConnectionFlow::PayloadLoadingError")
                        }

                        is SocketService.Events.Error -> {
                            Log.d(TAG, "liveSocketConnectionFlow::Events.Error")
                        }

                        SocketService.Events.Closed -> {
                            Log.d(TAG, "liveSocketConnectionFlow::Events.Closed")
                        }

                        SocketService.Events.NotConnected -> {
                            Log.d(TAG, "liveSocketConnectionFlow::NotConnected")
                        }

                        SocketService.Events.Opened -> {
                            try {
                                if (ThemeHolder.themeData.value == null) {
                                    ThemeHolder.updateThemeData(repository.loadThemeData())
                                }
                                if (ModifiersParser.isEmpty) {
                                    repository.loadStyleData()?.let { styleFileContentAsString ->
                                        ModifiersParser.fromStyleFile(
                                            styleFileContentAsString,
                                            ::pushEvent
                                        )
                                    }
                                }
                                joinLiveViewChannel()
                                connectLiveReload()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                setError(e)
                            }
                        }
                    }
                    if (event is SocketService.Events.Error) {
                        setError(event.throwable)
                    }
                }
            }
            try {
                repository.connectToLiveViewSocket()
            } catch (e: Exception) {
                setError(e)
            }
        }
    }

    private fun joinLiveViewChannel() {
        channelJob = viewModelScope.launch(Dispatchers.IO) {
            repository.joinLiveViewChannel(redirect = instanceCount > 1)
                .catch { cause: Throwable ->
                    Log.e(TAG, "joinLiveViewChannel::Error", cause)
                    setError(cause)
                }
                .buffer()
                .collect { message ->
                    Log.d(TAG, "message: $message")
                    when {
                        // Navigation Messages
                        message.payload.containsKey(PAYLOAD_LIVE_REDIRECT) ->
                            handleNavigation(message)

                        message.payload.containsKey(PAYLOAD_REDIRECT) ->
                            handleRedirect(message)

                        // Render message
                        message.payload.containsKey(PAYLOAD_RENDERED) -> {
                            parseTemplate(
                                getJsonFieldAsString(
                                    PAYLOAD_RENDERED,
                                    message.payloadJson
                                )
                            )
                        }

                        // Diff messages
                        message.event == ChannelService.MESSAGE_EVENT_DIFF -> {
                            parseTemplate(message.payloadJson)
                        }

                        message.payload.containsKey(ChannelService.MESSAGE_EVENT_DIFF) -> {
                            parseTemplate(
                                getJsonFieldAsString(
                                    ChannelService.MESSAGE_EVENT_DIFF,
                                    message.payloadJson
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun connectLiveReload() {
        viewModelScope.launch(Dispatchers.IO) {
            reloadConnectionJob = launch {
                repository.liveReloadSocketConnectionFlow.collect { reloadEvent ->
                    when (reloadEvent) {
                        is SocketService.Events.PayloadLoadingError ->
                            Log.d(TAG, "liveReloadSocketConnectionFlow::PayloadLoadingError")

                        is SocketService.Events.Error ->
                            Log.d(TAG, "liveReloadSocketConnectionFlow::Error ($reloadEvent)")

                        SocketService.Events.Closed ->
                            Log.d(TAG, "liveReloadSocketConnectionFlow::Closed")

                        SocketService.Events.NotConnected ->
                            Log.d(TAG, "liveReloadSocketConnectionFlow::NotConnected")

                        SocketService.Events.Opened -> {
                            Log.d(TAG, "liveReloadSocketConnectionFlow::Opened")
                            joinLiveReloadChannel()
                        }
                    }
                }
            }
            try {
                repository.connectToReloadSocket()
            } catch (e: Exception) {
                setError(e)
            }
        }
    }

    private fun joinLiveReloadChannel() {
        reloadChannelJob = viewModelScope.launch(Dispatchers.IO) {
            repository.joinReloadChannel()
                .catch { cause: Throwable ->
                    Log.e(TAG, "joinLiveReloadChannel::error", cause)
                    setError(cause)
                }
                .buffer()
                .collect {
                    Log.w(TAG, "-----------Reloading-----------")
                    repository.leaveReloadChannel()
                    repository.disconnectFromReloadSocket()
                    repository.leaveChannel()
                    repository.disconnectFromLiveViewSocket()
                    document = Document()

                    viewModelScope.launch(Dispatchers.Main) {
                        cancelConnectionJobs()
                        connectToLiveView()
                    }
                }
        }
    }

    fun cancelConnectionJobs() {
        reloadChannelJob?.cancel()
        reloadConnectionJob?.cancel()
        channelJob?.cancel()
        connectionJob?.cancel()
    }

    private fun getJsonFieldAsString(field: String, json: String): String {
        return json.run {
            val jsonField = "\"${field}\":"
            substring(indexOf(jsonField) + jsonField.length, lastIndex)
        }
    }

    private fun handleNavigation(message: Message) {
        message.payload[PAYLOAD_LIVE_REDIRECT]?.let { inputMap ->
            val redirectMap: Map<String, Any?> = inputMap as Map<String, Any?>
            viewModelScope.launch(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        navigationRequest = NavigationRequest(
                            redirectMap["to"].toString(),
                            false
                        )
                    )
                }
            }
        }
    }

    private fun handleRedirect(message: Message) {
        message.payload[PAYLOAD_REDIRECT]?.let { inputMap ->
            val redirectMap: Map<String, Any?> = inputMap as Map<String, Any?>
            viewModelScope.launch(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        navigationRequest = NavigationRequest(
                            redirectMap["to"].toString(),
                            true
                        )
                    )
                }
            }
        }
    }

    internal fun resetNavigation() {
        _state.update { it.copy(navigationRequest = null) }
    }

    internal fun parseTemplate(s: String) {
        Log.d(TAG, "parseTemplate: $s")
        try {
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
            val rootNode = ComposableTreeNode(screenId, -1, null, id = "rootNode")
            val rootElement = document.rootNodeRef
            // Walk through the DOM and create a ComposableTreeNode tree
            Log.i(TAG, "walkThroughDOM start")
            walkThroughDOM(document, rootElement, rootNode)
            Log.i(TAG, "walkThroughDOM complete")
            _state.update {
                it.copy(composableTreeNode = rootNode)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun walkThroughDOM(document: Document, nodeRef: NodeRef, parent: ComposableTreeNode?) {
        when (val node = document.getNode(nodeRef)) {
            is Node.Leaf,
            is Node.Element -> {
                val composableTreeNode =
                    composableTreeNodeFromNode(screenId, node, nodeRef)
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

    fun pushEvent(type: String, event: String, value: Any?, target: Int? = null) {
        Log.d(TAG, "pushEvent: type: $type, event: $event, value: $value, target: $target")
        repository.pushEvent(type, event, value, target)
    }

    private fun setError(throwable: Throwable?) {
        _state.update {
            it.copy(throwable = throwable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared::ROUTE=$route")

        repository.leaveReloadChannel()
        repository.leaveChannel()

        cancelConnectionJobs()

        instanceCount--
        if (instanceCount == 0) {
            Log.i(TAG, "onCleared::DISCONNECTING SOCKETS")
            repository.disconnectFromLiveViewSocket()
            repository.disconnectFromReloadSocket()
        }
    }

    class Factory(
        private val httpBaseUrl: String,
        private val route: String?,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // The WebSocket URL is the same of the HTTP URL,
            // so we just copy the HTTP URL changing the schema (protocol)
            val uri = Uri.parse(httpBaseUrl)
            val webSocketScheme = if (uri.scheme == "https") "wss" else "ws"
            val wsBaseUrl =
                uri.buildUpon().scheme(webSocketScheme).path("live/websocket").build().toString()

            val httpUrl = if (route == null) httpBaseUrl else {
                val uriBuilder = Uri.parse(httpBaseUrl).buildUpon()
                if (route.startsWith('/')) {
                    uriBuilder.path(route)
                } else {
                    uriBuilder.appendPath(route)
                }
                uriBuilder.toString()
            }

            return LiveViewCoordinator(httpUrl, wsBaseUrl, route) as T
        }
    }

    data class NavigationRequest(
        val url: String,
        val redirect: Boolean,
    )

    internal data class LiveViewState(
        val composableTreeNode: ComposableTreeNode,
        val navigationRequest: NavigationRequest? = null,
        val throwable: Throwable? = null,
    )

    companion object {
        const val TAG = "VM"

        private const val PAYLOAD_LIVE_REDIRECT = "live_redirect"
        private const val PAYLOAD_REDIRECT = "redirect"
        private const val PAYLOAD_RENDERED = "rendered"

        // We're keeping the instance count in order to deallocate the server socket when the last
        // instance is cleared.
        private var instanceCount = 0

        internal fun composableTreeNodeFromNode(
            screenId: String,
            node: Node,
            nodeRef: NodeRef,
        ): ComposableTreeNode {
            return ComposableNodeFactory.buildComposableTreeNode(
                screenId = screenId,
                nodeRef = nodeRef,
                element = CoreNodeElement.fromNodeElement(node),
            )
        }
    }
}

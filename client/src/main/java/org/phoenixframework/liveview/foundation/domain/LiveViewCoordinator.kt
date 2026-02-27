package org.phoenixframework.liveview.foundation.domain

import android.util.Log
import com.google.gson.JsonParser as GsonJsonParser
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
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
import org.phoenixframework.liveview.foundation.data.mappers.DocumentParser
import org.phoenixframework.liveview.foundation.data.repository.Repository
import org.phoenixframework.liveview.foundation.data.service.ChannelService
import org.phoenixframework.liveview.foundation.data.service.SocketService
import org.phoenixframework.liveview.foundation.ui.base.BaseThemeHolder
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser

class LiveViewCoordinator(
    val httpBaseUrl: String,
    private val wsBaseUrl: String,
    private val route: String?,
    private val method: String?,
    private val params: Map<String, Any?>,
    private val redirect: Boolean,
    private val modifierParser: BaseModifiersParser,
    private val themeHolder: BaseThemeHolder,
    private val documentParser: DocumentParser,
    private val repository: Repository,
) : ViewModel(), DefaultLifecycleObserver {
    private val _state = MutableStateFlow(
        LiveViewState(composableTreeNode = ComposableTreeNode(screenId, 0, null))
    )
    val state = _state.asStateFlow()

    private var connectionJob: Job? = null
    private var channelJob: Job? = null

    private var reloadConnectionJob: Job? = null
    private var reloadChannelJob: Job? = null

    private var reconnectionAttempts = 0

    private val screenId: String
        get() = this.toString()

    override fun onResume(owner: LifecycleOwner) {
        connectToLiveView(redirect)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        disconnect()
    }

    private fun connectToLiveView(redirect: Boolean) {
        Log.i(TAG, "connectToLiveView -> [$method] ROUTE=$route")
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

                        is SocketService.Events.Opened -> {
                            try {
                                reconnectionAttempts = 0
                                if (themeHolder.mustLoadThemeFile) {
                                    themeHolder.updateThemeData(repository.loadThemeData(httpBaseUrl))
                                    themeHolder.mustLoadThemeFile = false
                                }
                                if (modifierParser.mustLoadModifiersFile) {
                                    repository.loadStyleData(httpBaseUrl)
                                        ?.let { styleFileContentAsString ->
                                            modifierParser.fromStyleFile(
                                                styleFileContentAsString,
                                                null,
                                            )
                                        }
                                    modifierParser.mustLoadModifiersFile = false
                                }
                                joinLiveViewChannel(redirect)
                                if (event.liveReloadEnabled) {
                                    connectLiveReload()
                                }
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
                repository.connectToLiveViewSocket(
                    httpBaseUrl,
                    method,
                    params,
                    wsBaseUrl,
                )
            } catch (e: Exception) {
                setError(e)
            }
        }
    }

    private fun joinLiveViewChannel(redirect: Boolean) {
        channelJob = viewModelScope.launch(Dispatchers.IO) {
            repository.joinLiveViewChannel(httpBaseUrl, redirect)
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

                        // Errors
                        message.status == STATUS_ERROR -> {
                            Log.e(TAG, "Error: $message")
                            val reason = message.payload[ChannelService.MESSAGE_ERROR_REASON]
                            if (reason == ChannelService.ERROR_REASON_STALE ||
                                reason == ChannelService.ERROR_REASON_UNAUTHORIZED
                            ) {
                                if (reconnectionAttempts < 3) {
                                    reconnectionAttempts++
                                    cancelConnectionJobs()
                                    disconnect(resetPayload = true)
                                    connectToLiveView(redirect)
                                } else {
                                    handleReconnectionFailure()
                                }
                            }
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

                        is SocketService.Events.Opened -> {
                            Log.d(TAG, "liveReloadSocketConnectionFlow::Opened")
                            joinLiveReloadChannel()
                        }
                    }
                }
            }
            try {
                repository.connectToReloadSocket(wsBaseUrl)
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
                    themeHolder.mustLoadThemeFile = true
                    modifierParser.mustLoadModifiersFile = true
                    repository.leaveReloadChannel()
                    repository.disconnectFromReloadSocket()
                    repository.leaveChannel()
                    repository.disconnectFromLiveViewSocket()
                    documentParser.newDocument()

                    viewModelScope.launch(Dispatchers.Main) {
                        cancelConnectionJobs()
                        connectToLiveView(false)
                    }
                }
        }
    }

    private fun cancelConnectionJobs() {
        reloadChannelJob?.cancel()
        reloadConnectionJob?.cancel()
        channelJob?.cancel()
        connectionJob?.cancel()
    }

    private fun getJsonFieldAsString(field: String, json: String): String {
        val jsonObject = GsonJsonParser.parseString(json).asJsonObject
        return jsonObject.get(field).toString()
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

    private fun handleReconnectionFailure() {
        _state.update {
            it.copy(throwable = IllegalStateException("Reconnection failure."))
        }
    }

    fun resetNavigation() {
        _state.update { it.copy(navigationRequest = null) }
    }

    fun parseTemplate(s: String) {
        Log.d(TAG, "parseTemplate: $s")
        try {
            val rootNode = documentParser.parseDocumentJson(s)
            _state.update {
                it.copy(composableTreeNode = rootNode)
            }

        } catch (e: Exception) {
            e.printStackTrace()
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
        disconnect()
    }

    private fun disconnect(resetPayload: Boolean = false) {
        Log.i(TAG, "disconnect")
        cancelConnectionJobs()
        repository.leaveReloadChannel()
        repository.leaveChannel()
        repository.disconnectFromReloadSocket()
        repository.disconnectFromLiveViewSocket(resetPayload)
    }

    fun resetError() {
        _state.update {
            it.copy(throwable = null)
        }
    }

    data class NavigationRequest(
        val url: String,
        val redirect: Boolean,
    )

    data class LiveViewState(
        val composableTreeNode: ComposableTreeNode,
        val navigationRequest: NavigationRequest? = null,
        val throwable: Throwable? = null,
    )

    companion object {
        private const val TAG = "VM"

        private const val PAYLOAD_LIVE_REDIRECT = "live_redirect"
        private const val PAYLOAD_REDIRECT = "redirect"
        private const val PAYLOAD_RENDERED = "rendered"

        private const val STATUS_ERROR = "error"
    }
}

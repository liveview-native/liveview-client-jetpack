package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.data.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_BLUR
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Popup which contains content for Exposed Dropdown Menu. Should be used inside the content of
 * ExposedDropdownMenuBox. You can register a server event to called when the item is dismissed
 * using the `onDismissRequest` property.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class ExposedDropdownMenuDTO private constructor(builder: Builder) :
    ComposableView<ExposedDropdownMenuDTO.Builder>(builder) {

    private val scopeWrapper = builder.scopeWrapper
    private val onDismissRequest = builder.onDismissRequest

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        (scopeWrapper as? ExposedDropdownMenuBoxScopeWrapper)?.let { wrapper ->
            wrapper.scope.ExposedDropdownMenu(
                expanded = wrapper.isExpanded,
                onDismissRequest = {
                    wrapper.onDismissRequest.invoke()
                    onDismissRequest?.let { event ->
                        pushEvent(EVENT_TYPE_BLUR, event, phxValue, null)
                    }
                }
            ) {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        }
    }

    internal class Builder(val scopeWrapper: Any? = null) : ComposableBuilder() {
        var onDismissRequest: String? = null
            private set

        fun onDismissRequest(event: String) = apply {
            if (event.isNotEmpty()) {
                onDismissRequest = event
            }
        }

        fun build() = ExposedDropdownMenuDTO(this)
    }
}

internal object ExposedDropdownMenuDtoFactory :
    ComposableViewFactory<ExposedDropdownMenuDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ExposedDropdownMenuDTO =
        attributes.fold(ExposedDropdownMenuDTO.Builder(scope)) { builder, attribute ->
            when (attribute.name) {
                attrOnDismissRequest -> builder.onDismissRequest(attribute.value)
                else -> {
                    builder.handleCommonAttributes(
                        attribute,
                        pushEvent,
                        scope
                    ) as ExposedDropdownMenuDTO.Builder
                }
            }
        }.build()
}
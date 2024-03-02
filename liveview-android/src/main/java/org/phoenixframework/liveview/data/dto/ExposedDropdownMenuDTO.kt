package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_BLUR
import org.phoenixframework.liveview.domain.base.ComposableProperties
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
internal class ExposedDropdownMenuDTO private constructor(props: Properties) :
    ComposableView<ExposedDropdownMenuDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val scopeWrapper = props.scopeWrapper
        val onDismissRequest = props.onDismissRequest
        (scopeWrapper as? ExposedDropdownMenuBoxScopeWrapper)?.let { wrapper ->
            wrapper.scope.ExposedDropdownMenu(
                expanded = wrapper.isExpanded,
                onDismissRequest = {
                    wrapper.onDismissRequest.invoke()
                    onDismissRequest?.let { event ->
                        pushEvent(EVENT_TYPE_BLUR, event, props.commonProps.phxValue, null)
                    }
                },
            ) {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        }
    }

    @Stable
    internal data class Properties(
        val scopeWrapper: ExposedDropdownMenuBoxScopeWrapper,
        val onDismissRequest: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    //internal class Builder(val scopeWrapper: ExposedDropdownMenuBoxScopeWrapper) :
    internal class Builder(val scopeWrapper: ExposedDropdownMenuBoxScopeWrapper) :
        ComposableBuilder() {
        private var onDismissRequest: String? = null

        fun onDismissRequest(event: String) = apply {
            if (event.isNotEmpty()) {
                onDismissRequest = event
            }
        }

        fun build() = ExposedDropdownMenuDTO(
            Properties(
                scopeWrapper,
                onDismissRequest,
                commonProps,
            )
        )
    }
}

internal object ExposedDropdownMenuDtoFactory :
    ComposableViewFactory<ExposedDropdownMenuDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ExposedDropdownMenuDTO =
        attributes.fold(
            ExposedDropdownMenuDTO.Builder(scope as ExposedDropdownMenuBoxScopeWrapper)
        ) { builder, attribute ->
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
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Popup which contains content for Exposed Dropdown Menu. Should be used inside the content of
 * ExposedDropdownMenuBox. You can register a server event to called when the item is dismissed
 * using the `onDismissRequest` property.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class ExposedDropdownMenuView private constructor(props: Properties) :
    ComposableView<ExposedDropdownMenuView.Properties>(props) {

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

    internal object Factory : ComposableViewFactory<ExposedDropdownMenuView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): ExposedDropdownMenuView = ExposedDropdownMenuView(
            attributes.fold(Properties(scope as ExposedDropdownMenuBoxScopeWrapper)) { props, attribute ->
                when (attribute.name) {
                    attrOnDismissRequest -> onDismissRequest(props, attribute.value)
                    else -> {
                        props.copy(
                            commonProps = handleCommonAttributes(
                                props.commonProps,
                                attribute,
                                pushEvent,
                                scope
                            )
                        )
                    }
                }
            })

        private fun onDismissRequest(props: Properties, event: String): Properties {
            return if (event.isNotEmpty()) {
                props.copy(onDismissRequest = event)
            } else props
        }
    }
}
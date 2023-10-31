package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(
    composableNode: ComposableTreeNode,
    pushEvent: PushEvent,
    parentNode: ComposableTreeNode? = null,
    paddingValues: PaddingValues? = null,
    scope: Any? = null
) {
    val composableView = remember(composableNode) {
        ComposableNodeFactory.buildComposableView(
            composableNode.node, parentNode?.node?.tag, pushEvent, scope
        )
    }
    composableView.Compose(
        composableNode = composableNode,
        pushEvent = pushEvent,
        paddingValues = paddingValues,
    )
}

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }
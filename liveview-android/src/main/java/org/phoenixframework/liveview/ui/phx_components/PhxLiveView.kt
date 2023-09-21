package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableNodeFactory
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(
    composableNode: ComposableTreeNode,
    paddingValues: PaddingValues? = null,
    pushEvent: PushEvent
) {
    val state by LiveViewCoordinator.getNodeState(composableNode).collectAsState()
    val composableView = remember(state) {
        ComposableNodeFactory.buildComposableView(
            state?.node, state?.childrenNodes, pushEvent
        )
    }
    composableView.Compose(
        composableNode = state,
        paddingValues = paddingValues,
        pushEvent = pushEvent
    )
}

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }
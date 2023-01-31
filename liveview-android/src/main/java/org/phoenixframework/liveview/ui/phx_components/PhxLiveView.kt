package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node ->
        TraverseComposableViewTree(composableTreeNode = node)
    }
}

@Composable
private fun TraverseComposableViewTree(composableTreeNode: ComposableTreeNode) {
    when (composableTreeNode.value) {
        is AsyncImageDTO -> {
            composableTreeNode.value.Compose()
        }
        is TextDTO -> {
            composableTreeNode.value.Compose()
        }
    }
}
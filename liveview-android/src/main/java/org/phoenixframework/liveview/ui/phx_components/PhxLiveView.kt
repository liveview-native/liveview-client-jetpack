package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode


@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>, navController: NavHostController) {
    liveViewState.forEach { node ->
        TraverseComposableViewTree(composableTreeNode = node)
    }
}

@Composable
private fun TraverseComposableViewTree(composableTreeNode: ComposableTreeNode) {

    when (composableTreeNode.value) {

        is TextDTO -> {
            composableTreeNode.value.Compose()
        }


    }
}

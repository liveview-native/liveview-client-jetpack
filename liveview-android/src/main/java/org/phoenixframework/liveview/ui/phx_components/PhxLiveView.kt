package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node -> TraverseComposableViewTree(node, paddingValues = null) }
}

private val processChildren: OnChildren =
    { composableTreeNode: ComposableTreeNode, paddingValues: PaddingValues? ->
        TraverseComposableViewTree(
            composableTreeNode,
            paddingValues
        )
    }

@Composable
private fun TraverseComposableViewTree(
    composableTreeNode: ComposableTreeNode, paddingValues: PaddingValues?
) {
    composableTreeNode.value.Compose(
        composableTreeNode.children,
        paddingValues,
        processChildren
    )
}

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }

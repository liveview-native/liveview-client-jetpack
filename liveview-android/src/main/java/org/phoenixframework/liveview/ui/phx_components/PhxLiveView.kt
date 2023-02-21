package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node -> TraverseComposableViewTree(node, paddingValues = null) }
}

@Composable
private fun TraverseComposableViewTree(
    composableTreeNode: ComposableTreeNode,
    paddingValues: PaddingValues?
) {
    when (composableTreeNode.value) {
        is AsyncImageDTO -> composableTreeNode.value.Compose(paddingValues)
        is CardDTO -> composableTreeNode.value.Compose(paddingValues) {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(node, paddingValues = null)
            }
        }
        is ColumnDTO -> composableTreeNode.value.Compose(paddingValues) {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(node, paddingValues = null)
            }
        }
        is IconDTO -> composableTreeNode.value.Compose(paddingValues)
        is LazyColumnDTO ->
            composableTreeNode.value.ComposeLazyItems(
                composableTreeNode.children,
                paddingValues
            ) { node ->
                TraverseComposableViewTree(node, paddingValues = null)
            }
        is LazyRowDTO ->
            composableTreeNode.value.ComposeLazyItems(
                composableTreeNode.children,
                paddingValues
            ) { node ->
                TraverseComposableViewTree(node, paddingValues = null)
            }
        is RowDTO -> composableTreeNode.value.Compose(paddingValues) {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(node, paddingValues = null)
            }
        }
        is ScaffoldDTO -> composableTreeNode.value.Compose(paddingValues) { contentPaddingValues ->
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(node, contentPaddingValues)
            }
        }
        is SpacerDTO -> composableTreeNode.value.Compose(paddingValues)
        is TextDTO -> composableTreeNode.value.Compose(paddingValues)
    }
}

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }
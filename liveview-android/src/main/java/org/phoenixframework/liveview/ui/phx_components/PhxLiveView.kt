package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node -> TraverseComposableViewTree(composableTreeNode = node) }
}

@Composable
private fun TraverseComposableViewTree(composableTreeNode: ComposableTreeNode) {
    when (composableTreeNode.value) {
        is AsyncImageDTO -> composableTreeNode.value.Compose()
        is CardDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        }
        is ColumnDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        }
        is IconDTO -> composableTreeNode.value.Compose()
        is LazyColumnDTO ->
            composableTreeNode.value.ComposeLazyItems(composableTreeNode.children) { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        is LazyRowDTO ->
            composableTreeNode.value.ComposeLazyItems(composableTreeNode.children) { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        is RowDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        }
        is ScaffoldDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach { node ->
                TraverseComposableViewTree(composableTreeNode = node)
            }
        }
        is SpacerDTO -> composableTreeNode.value.Compose()
        is TextDTO -> composableTreeNode.value.Compose()
    }
}
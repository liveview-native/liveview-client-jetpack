package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

@Composable
fun PhxLiveView(node: ComposableTreeNode, paddingValues: PaddingValues? = null) {
    node.value.Compose(children = node.children, paddingValues = paddingValues)
}

fun Modifier.paddingIfNotNull(paddingValues: PaddingValues?): Modifier =
    if (paddingValues != null) {
        this.padding(paddingValues)
    } else {
        this
    }

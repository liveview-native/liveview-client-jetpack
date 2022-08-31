package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxBox(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {

    Box(
        modifier = modifier,
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }

}

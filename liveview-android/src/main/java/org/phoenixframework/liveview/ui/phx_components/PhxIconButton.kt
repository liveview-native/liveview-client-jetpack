package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable fun PhxIconButton(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            phxActionListener.invoke(
                PhxAction.PhxButtonClickAction(
                    element = element
                )
            )
        }
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }
}
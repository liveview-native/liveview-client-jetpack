package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable fun PhxButton(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit,
//    onAction: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {

            phxActionListener.invoke(
                PhxAction.GenericAction(
                    element = element
                )
            )
//            phxActionListener.invoke(
//                PhxAction.PhxButtonClickAction(
//                    element = element
//                )
//            )
//          onAction.invoke()
        }
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }
}
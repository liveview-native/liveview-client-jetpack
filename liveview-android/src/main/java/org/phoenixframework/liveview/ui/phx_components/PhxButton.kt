package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable fun PhxButton(
    element: Element,
    modifier: Modifier,
    onAction: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
          onAction.invoke()
        }
    ) {
        Text(text = element.ownText())
    }
}
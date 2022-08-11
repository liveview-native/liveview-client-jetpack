package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxText(
    element: Element,
    modifier: Modifier
) {
    Text(
        text = element.ownText(),
        modifier = modifier
    )
}
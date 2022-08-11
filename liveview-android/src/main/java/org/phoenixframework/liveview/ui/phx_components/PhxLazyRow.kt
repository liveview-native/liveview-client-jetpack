package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxLazyRow(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {

    LazyRow(modifier = modifier) {

        items(element.children()) {
            generateElementByTag(it, phxActionListener)
        }

    }

}


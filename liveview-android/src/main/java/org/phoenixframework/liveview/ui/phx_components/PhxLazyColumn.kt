package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxLazyColumn(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {



    LazyColumn(modifier = modifier) {

        items(element.children()) {
            generateElementByTag(it, phxActionListener)
        }

    }

}


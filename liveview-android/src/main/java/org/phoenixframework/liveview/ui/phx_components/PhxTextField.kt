package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxTextField(
    element: Element,
    modifier: Modifier,
    _onValueChange: (String) -> Unit) {

    TextField(
        value = element.ownText(),
        modifier = modifier,
        onValueChange = {
            _onValueChange.invoke(it)
        })
}
package com.dockyard.liveviewtest.android.ui.phx_components

import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxCheckbox(
    element: Element,
    modifier: Modifier,
    _onValueChange: (Boolean) -> Unit
) {

    Checkbox(
        checked = element.attr("checked").toBoolean(),
        modifier = modifier,
        onCheckedChange = {
        _onValueChange.invoke(it)
    })

}
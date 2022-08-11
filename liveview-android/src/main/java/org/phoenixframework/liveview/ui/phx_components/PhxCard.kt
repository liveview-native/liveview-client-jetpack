package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jsoup.nodes.Element

@Composable
fun PhxCard(
    element: Element,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        content.invoke()
    }
}

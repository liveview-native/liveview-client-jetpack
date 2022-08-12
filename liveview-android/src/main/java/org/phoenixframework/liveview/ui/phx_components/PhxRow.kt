package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element
import org.phoenixframework.liveview.extensions.multiAttr

@Composable
fun PhxRow(
    element: Element,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    val verticalAlignment = when(element.multiAttr("vertical-alignment", "v-al")) {

        "top" -> {
            Alignment.Top
        }

        "bottom" -> {
            Alignment.Bottom
        }

        "center-vertically" -> {
            Alignment.CenterVertically
        }

        else -> {
            Alignment.Top
        }
    }

    val horizontalArrangement = when(element.multiAttr("horizontal-arrangement")) {
        "end" -> {
            Arrangement.End
        }

        "start" -> {
            Arrangement.Start
        }

        "center" -> {
            Arrangement.Center
        }

        "space-between" -> {
            Arrangement.SpaceBetween
        }

        "space-around" -> {
            Arrangement.SpaceAround
        }

        "space-evenly" -> {
            Arrangement.SpaceEvenly
        }

        else -> {
            Arrangement.Start
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        content.invoke()
    }

}

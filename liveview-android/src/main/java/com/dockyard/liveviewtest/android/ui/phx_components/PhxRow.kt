package com.dockyard.liveviewtest.android.ui.phx_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dockyard.liveviewtest.android.extensions.multiAttr
import org.jsoup.nodes.Element

@Composable
fun PhxRow(
    element: Element,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    val verticalAlignment = when(element.multiAttr("vertical-alignment", "v-al")) {

        "top", "t" -> {
            Alignment.Top
        }

        "bottom", "b" -> {
            Alignment.Bottom
        }

        "center-vertically", "center", "c" -> {
            Alignment.CenterVertically
        }

        else -> {
            Alignment.Top
        }
    }

    val horizontalArrangement = when(element.multiAttr("horizontal-arrangement", "h-ar")) {
        "end", "e" -> {
            Arrangement.End
        }

        "start", "s" -> {
            Arrangement.Start
        }

        "center", "c" -> {
            Arrangement.Center
        }

        "space-between", "s-b" -> {
            Arrangement.SpaceBetween
        }

        "space-around", "s-a" -> {
            Arrangement.SpaceAround
        }

        "space-evenly", "s-e" -> {
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

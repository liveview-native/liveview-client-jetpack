package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element
import org.phoenixframework.liveview.extensions.multiAttr

@Composable
fun PhxColumn(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {

    val horizontalAlignment = when(element.multiAttr("horizontal-alignment", "h-al")) {

        "start", "s" -> {
            Alignment.Start
        }

        "end", "e" -> {
            Alignment.End
        }

        "center-horizontally", "center", "c" -> {
            Alignment.CenterHorizontally
        }

        else -> {
            Alignment.Start
        }
    }



    val verticalArrangement = when(element.multiAttr("vertical-arrangement", "v-ar")) {

        "top", "t" -> {
            Arrangement.Top
        }

        "bottom", "b" -> {
            Arrangement.Bottom
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
            Arrangement.Top
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }
}


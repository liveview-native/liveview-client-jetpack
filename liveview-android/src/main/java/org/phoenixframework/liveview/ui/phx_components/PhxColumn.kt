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

    val horizontalAlignment = when(element.multiAttr("horizontal-alignment")) {

        "start" -> {
            Alignment.Start
        }

        "end" -> {
            Alignment.End
        }

        "center-horizontally" -> {
            Alignment.CenterHorizontally
        }

        else -> {
            Alignment.Start
        }
    }



    val verticalArrangement = when(element.multiAttr("vertical-arrangement")) {

        "top" -> {
            Arrangement.Top
        }

        "bottom" -> {
            Arrangement.Bottom
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


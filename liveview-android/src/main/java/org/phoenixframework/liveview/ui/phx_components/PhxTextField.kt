package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import org.jsoup.nodes.Element

@Composable
fun PhxTextField(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit,
//    _onValueChange: (String) -> Unit
    )
{

    var textValue by remember { mutableStateOf(TextFieldValue(element.attr("value"))) }

    val label: @Composable () -> Unit = {
        walkChildrenAndBuildComposables(
            children = element.getElementsByTag("label*").first()?.children(),
            phxActionListener = phxActionListener
        )
    }

     TextField(
        value = textValue,
         label= label,
        modifier = modifier,
        onValueChange = {

            textValue = it
            phxActionListener.invoke(
                PhxAction.PhxTextValueChangeAction(
                    element = element,
                    inputText = it.text
                )
            )
//            _onValueChange.invoke(it)
        })
}
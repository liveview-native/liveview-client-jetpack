package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.liveview.extensions.xGenerateFromElement
import org.phoenixframework.liveview.ui.phx_components.phx_canvas.PhxCanvas

@Composable fun PhxLiveView(
    documentState: MutableState<Document?>,
    phxActionListener: (PhxAction) -> Unit
) {


    walkDomAndBuildComposables(
        document = documentState.value,
        phxActionListener = phxActionListener
    )
}


@Composable fun walkDomAndBuildComposables(document: Document?, phxActionListener: (PhxAction) -> Unit) {

    document?.body()?.let { theBody ->
        walkChildrenAndBuildComposables(children = theBody.children(), phxActionListener = phxActionListener)
    }

}


@Composable fun walkChildrenAndBuildComposables(children: Elements? , phxActionListener: (PhxAction) -> Unit) {

    children?.forEach { theElement ->
        mapElementToComposable(element = theElement, phxActionListener = phxActionListener)
    }

}


@Composable private fun mapElementToComposable(element: Element, phxActionListener: (PhxAction) -> Unit) {

    Log.d("Element Tag:", element.tagName())
    generateElementByTag(element, phxActionListener)

    if (element.children().isNotEmpty()) {
        return
    }

}



@Composable
fun generateElementByTag(
    element: Element,
    phxActionListener: (PhxAction) -> Unit
) {

    val attributeModifiers = xGenerateFromElement(
        element,
        phxActionListener = phxActionListener
    )

    when (element.tagName()) {

        "async-image" -> {

            PhxAsyncImage(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )

        }

        "button" -> {
            PhxButton(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "box" -> {
            PhxBox(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "canvas" -> {
            PhxCanvas(
                element = element,
                modifier = attributeModifiers
            )
        }

        "card" -> {
            PhxCard(
                element = element,
                modifier = attributeModifiers,
                content = {
                    walkChildrenAndBuildComposables(
                        children = element.children(),
                        phxActionListener = phxActionListener
                    )
                }
            )
        }

        "checkbox" -> {
            PhxCheckbox(
                element = element,
                modifier = attributeModifiers,
                _onValueChange = { isChecked ->
                    phxActionListener.invoke(
                        PhxAction.PhxCheckChangedAction(
                            element = element,
                            isChecked = isChecked
                        )
                    )
                }
            )
        }
        
        "column" -> {
            PhxColumn(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "icon" -> {
            PhxIcon(
                element = element,
                modifier = attributeModifiers
            )
        }

        "icon-button" -> {
            PhxIconButton(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener,
            )
        }


        "lazy-column" -> {
            PhxLazyColumn(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "lazy-row" -> {
            PhxLazyRow(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "nav-host" -> {
            PhxNavHost(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener,
            )
        }

        "row" -> {
            PhxRow(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "scaffold" -> {
            PhxScaffold(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        "text" -> {
            PhxText(
                element = element,
                modifier = attributeModifiers
            )
        }

        "text-field" -> {
            PhxTextField(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener,
            )
        }

        "top-app-bar" -> {
            PhxTopAppBar(
                element = element,
                modifier = attributeModifiers,
                phxActionListener = phxActionListener
            )
        }

        else -> { //dynamically attempt to generate server-defined composable

            if (element.tagName().endsWith("+")) {

            }

        }
    }
}
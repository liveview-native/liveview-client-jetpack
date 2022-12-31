package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.extensions.xGenerateFromElement
import org.phoenixframework.liveview.ui.phx_components.phx_canvas.PhxCanvas

@Composable
fun walkChildrenAndBuildComposables(
    children: Elements?,
    navHostController: NavHostController? = null,
    phxActionListener: (PhxAction) -> Unit
) {
    children?.forEach { theElement ->
        mapElementToComposable(
            element = theElement,
            navHostController = navHostController,
            phxActionListener = phxActionListener
        )
    }
}

@Composable
private fun mapElementToComposable(
    element: Element,
    navHostController: NavHostController? = null,
    phxActionListener: (PhxAction) -> Unit
) {
    Log.d("Element Tag:", element.tagName())
    generateElementByTag(
        element = element,
        navHostController = navHostController,
        phxActionListener = phxActionListener
    )

    if (element.children().isNotEmpty()) {
        return
    }
}

@Composable
fun generateElementByTag(
    element: Element,
    navHostController: NavHostController? = null,
    phxActionListener: (PhxAction) -> Unit
) {
    val attributeModifiers = xGenerateFromElement(
        element,
        phxActionListener = phxActionListener
    )

    when (element.tagName()) {
        "async-image" -> PhxAsyncImage(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "button" -> PhxButton(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "box" -> PhxBox(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "canvas" -> PhxCanvas(
            element = element,
            modifier = attributeModifiers
        )
        "card" -> PhxCard(
            element = element,
            modifier = attributeModifiers,
            content = {
                walkChildrenAndBuildComposables(
                    children = element.children(),
                    phxActionListener = phxActionListener
                )
            }
        )
        "checkbox" -> PhxCheckbox(
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
        "column" -> PhxColumn(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "icon" -> PhxIcon()
        "icon-button" -> PhxIconButton(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener,
        )
        "lazy-column" -> PhxLazyColumn(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "lazy-row" -> PhxLazyRow(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "nav-host" -> if (navHostController != null) {
            PhxNavHost()
        }
        "row" -> PhxRow(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener
        )
        "scaffold" -> PhxScaffold()
        "text" -> PhxText(
            element = element,
            modifier = attributeModifiers
        )
        "text-field" -> PhxTextField(
            element = element,
            modifier = attributeModifiers,
            phxActionListener = phxActionListener,
        )
        "top-app-bar" -> PhxTopAppBar(
            element = element,
            phxActionListener = phxActionListener
        )
    }
}

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node ->
        TraverseComposableViewTree(composableTreeNode = node)
    }
}

@Composable
private fun TraverseComposableViewTree(composableTreeNode: ComposableTreeNode) {
    when (composableTreeNode.value) {
        is AsyncImageDTO -> composableTreeNode.value.Compose()
        is TextDTO -> composableTreeNode.value.Compose()
        is CardDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach {
                TraverseComposableViewTree(composableTreeNode = it)
            }
        }
        is RowDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach {
                TraverseComposableViewTree(composableTreeNode = it)
            }
        }
        is ColumnDTO -> composableTreeNode.value.Compose {
            composableTreeNode.children.forEach {
                TraverseComposableViewTree(composableTreeNode = it)
            }
        }
    }
}
package org.phoenixframework.liveview.ui.phx_components

import android.provider.DocumentsContract
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.extensions.xGenerateFromElement
import org.phoenixframework.liveview.ui.phx_components.phx_canvas.PhxCanvas


@Composable fun walkDomAndBuildComposables(
    document: Document?,
    navHostController: NavHostController,
    phxActionListener: (PhxAction) -> Unit
) {

    document?.body()?.let { theBody ->
        walkChildrenAndBuildComposables(
            children = theBody.children(),
            navHostController = navHostController,
            phxActionListener = phxActionListener
        )
    }

}


@Composable fun walkChildrenAndBuildComposables(
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


@Composable private fun mapElementToComposable(
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

            navHostController?.let { theNavHostController ->
                PhxNavHost(
                    element = element,
                    modifier = attributeModifiers,
                    navHostController = theNavHostController,
                    phxActionListener = phxActionListener,
                )
            }
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

@Composable
fun PhxLiveView(liveViewState: MutableList<ComposableTreeNode>) {
    liveViewState.forEach { node ->
        TraverseComposableViewTree(composableTreeNode = node)
    }
}

@Composable
private fun TraverseComposableViewTree(composableTreeNode: ComposableTreeNode) {

    when (composableTreeNode.value) {
        is AsyncImageDTO -> {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(composableTreeNode.value.imageUrl)
                    .crossfade(composableTreeNode.value.crossfade)
                    .build(),
                contentDescription = composableTreeNode.value.contentDescription,
                contentScale = composableTreeNode.value.contentScale,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(composableTreeNode.value.shape)


            )
        }
        is TextDTO -> {
            Text(
                text = composableTreeNode.value.text,
                color = composableTreeNode.value.color,
                modifier = composableTreeNode.value.modifier,
                fontSize = composableTreeNode.value.fontSize,
                fontStyle = composableTreeNode.value.fontStyle,
                fontWeight = composableTreeNode.value.fontWeight,
                fontFamily = composableTreeNode.value.fontFamily,
                letterSpacing = composableTreeNode.value.letterSpacing,
                textAlign = composableTreeNode.value.textAlign,
                lineHeight = composableTreeNode.value.lineHeight,
                overflow = composableTreeNode.value.overflow,
                softWrap = composableTreeNode.value.softWrap,
                maxLines = composableTreeNode.value.maxLines,
                textDecoration = composableTreeNode.value.textDecoration

            )
        }
        is CardDTO -> {
            Card(
                backgroundColor = composableTreeNode.value.backgroundColor,
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp),
                elevation = composableTreeNode.value.elevation,
                shape = composableTreeNode.value.shape,

                ) {
                composableTreeNode.children.forEach {
                    TraverseComposableViewTree(composableTreeNode = it)
                }

            }
        }
        is RowDTO -> {
            Row(
                horizontalArrangement = composableTreeNode.value.horizontalArrangement,
                verticalAlignment = composableTreeNode.value.verticalAlignment
            ) {
                composableTreeNode.children.forEach {
                    TraverseComposableViewTree(composableTreeNode = it)
                }
            }
        }
        is ColumnDTO -> {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                verticalArrangement = composableTreeNode.value.verticalArrangement,
                horizontalAlignment = composableTreeNode.value.horizontalAlignment
            ) {
                composableTreeNode.children.forEach {
                    TraverseComposableViewTree(composableTreeNode = it)
                }

            }
        }
    }


}

package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxScaffold(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {

  /*  val functionalAttributeKeys = element.attributes()
        .filter { it.key.endsWith("*") }
        .map { it.key }
        .toSet()

    val functionalElementsTagNames = element.children()
        //.filter { it.tagName().endsWith("*")}
        .map { it.tagName() }
        .toSet()

    if (functionalAttributeKeys != functionalElementsTagNames) {
        Log.e("STAR COMPONENT ERROR", "The functional attributes don't match the functional elements.")
    }

//    if (element.hasAttr("top-bar*")) {
//        val topBarFunctionalElement = element.getElementsByTag("top-bar*").first()
//
//        topBarFunctionalElement?.let {
//            walkChildrenAndBuildComposables(
//                children = it.children(),
//                phxActionListener = phxActionListener
//            )
//        }
//    }

    val topBar : @Composable () -> Unit = {
        walkChildrenAndBuildComposables(children = element.getElementsByTag("top-bar*").first()?.children(), phxActionListener = phxActionListener)
    }


    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }*/
}

@Composable
private fun topAppBar(): @Composable () -> Unit {


    return {
        TopAppBar(
            title = { Text("Simple TopAppBar") },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
            }
        )
    }
}


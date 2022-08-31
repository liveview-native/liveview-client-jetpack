package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxScaffold(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {
    val functionalAttributeKeys = element.attributes()
        .filter { it.key.endsWith("*") }
        .map { it.key }
        .toSet()

    val functionalElementsTagNames = element.children()
        .filter { it.tagName().endsWith("*") }
        .map { it.tagName() }
        .toSet()

    if (functionalAttributeKeys != functionalElementsTagNames) {
        Log.e(
            "STAR COMPONENT ERROR",
            "The functional attributes don't match the functional elements."
        )
    }

    val topBar: @Composable () -> Unit = {
        walkChildrenAndBuildComposables(
            children = element.getElementsByTag("top-bar*").first()?.children(),
            phxActionListener = phxActionListener
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar
    ) {
        walkChildrenAndBuildComposables(
            children = element.children(),
            phxActionListener = phxActionListener
        )
    }
}
package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable
fun PhxTopAppBar(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit
) {

    val functionalAttributeKeys = element.attributes()
        .filter { it.key.endsWith("*") }
        .map { it.key }
        .toSet()

    val functionalElementsTagNames = element.children()
       // .filter { it.tagName().endsWith("*")}
        .map { it.tagName() }
        .toSet()

    if (functionalAttributeKeys != functionalElementsTagNames) {
        Log.e("STAR COMPONENT ERROR", "The functional attributes don't match the functional elements.")
    }

    val navigationIcon: @Composable () -> Unit = {
        walkChildrenAndBuildComposables(
            children = element.getElementsByTag("navigation-icon*").first()?.children(),
            phxActionListener = phxActionListener
        )
    }

    val title: @Composable () -> Unit = {
        walkChildrenAndBuildComposables(
            children = element.getElementsByTag("title*").first()?.children(),
            phxActionListener = phxActionListener
        )
    }

    val actions: @Composable RowScope.() -> Unit = if (element.hasAttr("actions*")) {
        {
            walkChildrenAndBuildComposables(
                children = element.getElementsByTag("actions*").first()?.children(),
                phxActionListener = phxActionListener
            )
        }
    } else {
        {}
    }


    if (element.hasAttr("navigation-icon*")) {

        TopAppBar(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions
        )

    } else {
        TopAppBar(
            title = title,
            actions = actions
        )
    }
}




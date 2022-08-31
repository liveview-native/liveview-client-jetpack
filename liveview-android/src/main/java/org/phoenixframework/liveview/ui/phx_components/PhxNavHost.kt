package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jsoup.nodes.Element

@Composable
fun PhxNavHost(
    element: Element,
    modifier: Modifier,
    phxActionListener: (PhxAction) -> Unit,
) {
    val screenElements: List<Element> = element.children().filter { it.tagName().endsWith("+") }

    NavHost(
        modifier = modifier,
        navController = rememberNavController(),
        startDestination = element.attr("start-destination")
    ) {
        screenElements
            .forEach { theScreenElement ->
                composable(theScreenElement.tagName()) {
                    walkChildrenAndBuildComposables(
                        children = theScreenElement.children(),
                        phxActionListener = phxActionListener
                    )
                }
            }
    }
}
package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.jsoup.nodes.Element

@Composable fun PhxNavHost(
    element: Element,
    modifier: Modifier,
    navHostController: NavHostController,
    phxActionListener: (PhxAction) -> Unit,
) {

    /*val screenElements: List<Element> = element.children().f

    NavHost(
        modifier = modifier,
        navController = navHostController,
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

    }*/

}
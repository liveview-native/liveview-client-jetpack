package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.phoenixframework.liveview.managers.LiveViewState
import org.phoenixframework.liveview.ui.theme.LiveViewTestTheme

@Composable
fun LiveView() {
    LiveViewTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navController = rememberNavController()

            PhxLiveView(
                documentState = LiveViewState.documentState,
                navHostController = navController,
                phxActionListener = { phxAction: PhxAction ->
                    when (phxAction) {
                        is PhxAction.GenericAction -> {
                            val type = phxAction.element.attr("type")
                            val value = phxAction.element.attr("value")

                            when (type) {
                                "navigate" -> {
                                    navController.navigate(value)
                                }

                                "click" -> {
                                    LiveViewState
                                        .socketManager
                                        .pushChannelMessage(
                                            PhxAction.PhxButtonClickAction(
                                                element = phxAction.element
                                            )
                                        )
                                }
                            }
                        }
                        is PhxAction.PhxNavAction -> navController.navigate(phxAction.navDestination)
                        else -> LiveViewState
                            .socketManager
                            .pushChannelMessage(
                                phxAction = phxAction
                            )
                    }

                }
            )
        }
    }
}
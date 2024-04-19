package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.domain.ThemeHolder
import org.phoenixframework.liveview.ui.theme.LiveViewNativeTheme

private const val PHX_LIVE_VIEW_ROUTE = "phxLiveView"
private const val ARG_ROUTE = "route"

@Composable
fun LiveView(url: String) {
    val themeData by ThemeHolder.themeData.collectAsState()

    LiveViewNativeTheme(themeData = themeData) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = PHX_LIVE_VIEW_ROUTE) {
                composable(
                    route = "$PHX_LIVE_VIEW_ROUTE?$ARG_ROUTE={$ARG_ROUTE}",
                    arguments = listOf(navArgument(ARG_ROUTE) {
                        nullable = true
                    })
                ) { backStackEntry ->
                    NavDestination(
                        navController = navController,
                        backStackEntry = backStackEntry,
                        httpBaseUrl = url,
                    )
                }
            }
        }
    }
}

@Composable
private fun NavDestination(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    httpBaseUrl: String,
) {
    val route = backStackEntry.arguments?.getString(ARG_ROUTE)
    val liveViewCoordinator = viewModel<LiveViewCoordinator>(
        viewModelStoreOwner = backStackEntry,
        factory = LiveViewCoordinator.Factory(httpBaseUrl, route)
    )

    val state by liveViewCoordinator.composableTree.collectAsState()
    if (state.children.isNotEmpty()) {
        PhxLiveView(
            composableNode = state.children.first(),
            pushEvent = liveViewCoordinator::pushEvent
        )
    }

    LaunchedEffect(liveViewCoordinator) {
        // Connecting to LiveView socket
        liveViewCoordinator.connectToLiveView()
        liveViewCoordinator.navigation.collect { navigationRequest ->
            if (navigationRequest != null) {
                // Cancelling connection jobs
                liveViewCoordinator.cancelConnectionJobs()

                val (newRoute, redirect) = navigationRequest
                liveViewCoordinator.resetNavigation()
                val routePath = "$PHX_LIVE_VIEW_ROUTE?$ARG_ROUTE=$newRoute"
                navController.navigate(routePath) {
                    if (redirect) {
                        popUpTo(backStackEntry.destination.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}
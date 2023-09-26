package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.ui.theme.LiveViewTestTheme

private const val TAG = "LiveView"
private const val PHX_LIVE_VIEW_ROUTE = "phxLiveView"
private const val ARG_ROUTE = "route"

@Composable
fun LiveView(
    httpBaseUrl: String,
    wsBaseUrl: String
) {
    LiveViewTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
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
                        backStackEntry = backStackEntry,
                        httpBaseUrl = httpBaseUrl,
                        wsBaseUrl = wsBaseUrl,
                        onNavigate = { route ->
                            navController.navigate("$PHX_LIVE_VIEW_ROUTE?$ARG_ROUTE=$route")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavDestination(
    backStackEntry: NavBackStackEntry,
    httpBaseUrl: String,
    wsBaseUrl: String,
    onNavigate: (String) -> Unit
) {
    val route = backStackEntry.arguments?.getString("route")
    val httpUrl = if (route == null) httpBaseUrl else "$httpBaseUrl$route"
    val webSocketUrl = if (route == null) wsBaseUrl else "$wsBaseUrl$route"
    val liveViewCoordinator = viewModel(
        viewModelStoreOwner = backStackEntry,
        initializer = {
            LiveViewCoordinator(
                httpBaseUrl = httpUrl,
                wsBaseUrl = webSocketUrl,
                onNavigate = onNavigate
            )
        }
    )
    val state by liveViewCoordinator.backStack.collectAsState()
    if (state.isNotEmpty()) {
        PhxLiveView(
            composableNode = state.peek().children.first(),
            pushEvent = liveViewCoordinator::pushEvent
        )
    }

    DisposableEffect(route) {
        Log.d(TAG, "DisposableEffect::body->$route")
        liveViewCoordinator.joinChannel()
        onDispose {
            Log.d(TAG, "DisposableEffect::onDispose->$route")
            liveViewCoordinator.leaveChannel()
        }
    }
}
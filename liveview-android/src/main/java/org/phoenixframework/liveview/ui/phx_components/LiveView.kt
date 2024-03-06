package org.phoenixframework.liveview.ui.phx_components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

private const val TAG = "LiveView"
private const val PHX_LIVE_VIEW_ROUTE = "phxLiveView"
private const val ARG_ROUTE = "route"

@Composable
fun LiveView(url: String) {
    // The WebSocket URL is the same of the HTTP URL,
    // so we just copy the HTTP URL changing the schema (protocol)
    val webSocketBaseUrl = remember(url) {
        val uri = Uri.parse(url)
        val webSocketScheme = if (uri.scheme == "https") "wss" else "ws"
        uri.buildUpon().scheme(webSocketScheme).build().toString()
    }
    val themeData by ThemeHolder.themeData.collectAsState()

    LiveViewNativeTheme(
        themeData = themeData
    ) {
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
                        wsBaseUrl = webSocketBaseUrl,
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
    wsBaseUrl: String,
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
            )
        }
    )
    val state by liveViewCoordinator.composableTree.collectAsState()
    if (state.children.isNotEmpty()) {
        PhxLiveView(
            composableNode = state.children.first(),
            pushEvent = liveViewCoordinator::pushEvent
        )
    }

    // Joining/Leaving the channel
    DisposableEffect(route) {
        Log.d(TAG, "DisposableEffect::route->$route")
        liveViewCoordinator.joinChannel()
        onDispose {
            Log.d(TAG, "DisposableEffect::onDispose->$route")
            liveViewCoordinator.leaveChannel()
        }
    }

    // Observing navigation changes
    LaunchedEffect(liveViewCoordinator) {
        liveViewCoordinator.navigation.collect { navigationRequest ->
            if (navigationRequest != null) {
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
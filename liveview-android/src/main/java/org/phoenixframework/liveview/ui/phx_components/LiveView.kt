package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
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
import org.phoenixframework.liveview.ui.base.ErrorView
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

    val state by liveViewCoordinator.state.collectAsState()
    if (state.composableTreeNode.children.isNotEmpty()) {
        CompositionLocalProvider(
            LocalHttpUrl provides liveViewCoordinator.httpBaseUrl,
            LocalNavController provides navController,
        ) {
            state.composableTreeNode.children.forEach {
                PhxLiveView(
                    composableNode = it,
                    pushEvent = liveViewCoordinator::pushEvent
                )
            }
        }
    } else {
        val error = state.throwable
        if (error != null) {
            ErrorView(throwable = error)
        }
    }

    LaunchedEffect(state.navigationRequest) {
        val navigationRequest = state.navigationRequest
        // Connecting to LiveView socket
        liveViewCoordinator.connectToLiveView()
        if (navigationRequest != null) {
            // Cancelling connection jobs
            liveViewCoordinator.cancelConnectionJobs()

            val (newRoute, redirect) = navigationRequest
            liveViewCoordinator.resetNavigation()
            val routePath = createRoute(newRoute)
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

internal fun createRoute(route: String): String = "$PHX_LIVE_VIEW_ROUTE?$ARG_ROUTE=$route"

internal fun getCurrentRoute(navController: NavController): String {
    return navController.currentBackStackEntry?.arguments?.getString(ARG_ROUTE) ?: "/"
}

internal fun generateRelativePath(currentUrl: String, newUrl: String): String {
    val currentParts = currentUrl.split("/").toMutableList()
    val newParts = newUrl.split("/").toMutableList()

    // Remove empty parts caused by leading or trailing slashes
    currentParts.removeAll { it.isEmpty() }
    newParts.removeAll { it.isEmpty() }

    // Handle the case where newUrl is an absolute path
    if (newUrl.startsWith("/")) {
        return newUrl
    }

    // Handle the case where newUrl starts with "../"
    while (newParts.firstOrNull() == "..") {
        newParts.removeFirstOrNull()
        currentParts.removeLastOrNull()
    }

    val relativeParts = mutableListOf<String>()

    // Add remaining parts of newUrl
    relativeParts.addAll(newParts)

    return currentParts.joinToString("/", prefix = "/", postfix = "/") + relativeParts.joinToString(
        "/"
    )
}

/**
 * Some components (like AsyncImage) might require some resources using the relative URL. This
 * composition local, provides base URL of the current LiveView, and the child components can
 * access it using LocalHttpUrl.current and use URI.resolve to access the resource relative to the
 * current URL. See AsyncImage for more details.
 */
val LocalHttpUrl = compositionLocalOf { "" }

/**
 * Providing access to the NavController in order to allow local navigation from other components
 * like [org.phoenixframework.liveview.ui.view.LinkView].
 */
val LocalNavController = compositionLocalOf<NavController> {
    error("No LocalNavController provided")
}
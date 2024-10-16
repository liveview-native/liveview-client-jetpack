package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.ErrorView
import org.phoenixframework.liveview.ui.theme.LiveViewNativeTheme

private const val TAG = "LiveView"

@Composable
fun LiveView(url: String) {
    val themeData by LiveViewJetpack.themeHolder().themeData.collectAsState()

    LiveViewNativeTheme(themeData = themeData) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = LiveViewNavRoute()) {
                composable<LiveViewNavRoute> { backStackEntry ->
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
    val lvNavRoute = backStackEntry.toRoute<LiveViewNavRoute>()

    val liveViewCoordinator = koinViewModel<LiveViewCoordinator> {
        parametersOf(httpBaseUrl, lvNavRoute.route)
    }
    val appNavigationController = remember(navController, backStackEntry) {
        LiveViewAppNavController(navController, backStackEntry)
    }

    val state by liveViewCoordinator.state.collectAsState()
    if (state.composableTreeNode.children.isNotEmpty()) {
        CompositionLocalProvider(
            LocalHttpUrl provides liveViewCoordinator.httpBaseUrl,
            LocalNavigation provides appNavigationController,
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
        processNavigationRequest(
            state.navigationRequest,
            liveViewCoordinator,
            lvNavRoute,
            navController,
            backStackEntry
        )
    }
}

private fun processNavigationRequest(
    navigationRequest: LiveViewCoordinator.NavigationRequest?,
    liveViewCoordinator: LiveViewCoordinator,
    lvNavRoute: LiveViewNavRoute,
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    // Connecting to LiveView socket
    liveViewCoordinator.connectToLiveView(
        method = lvNavRoute.method ?: "GET",
        params = lvNavRoute.argsAsJson?.let { JsonParser.parse<Map<String, Any?>>(it) }
            ?: emptyMap()
    )
    if (navigationRequest != null) {
        // Cancelling connection jobs
        liveViewCoordinator.cancelConnectionJobs()

        val (newRoute, redirect) = navigationRequest
        liveViewCoordinator.resetNavigation()
        val routePath = generateRelativePath(getCurrentRoute(navController), newRoute)
        Log.d(TAG, "Navigate to: $routePath")
        navController.navigate(
            LiveViewNavRoute(
                route = routePath,
                argsAsJson = lvNavRoute.argsAsJson
            )
        ) {
            if (redirect) {
                liveViewCoordinator.disconnect()
                popUpTo(backStackEntry.destination.id) {
                    inclusive = true
                }
            }
        }
    }
}

private fun generateRelativePath(currentUrl: String, newUrl: String): String {
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

@Serializable
private data class LiveViewNavRoute(
    val route: String? = null,
    val method: String? = null,
    val argsAsJson: String? = null,
)

private class LiveViewAppNavController(
    private val navController: NavController,
    private val backStackEntry: NavBackStackEntry,
) : AppNavigationController {
    override fun navigate(
        path: String,
        method: String,
        params: Map<String, Any?>,
        redirect: Boolean
    ) {
        navController.navigate(
            LiveViewNavRoute(
                route = generateRelativePath(getCurrentRoute(navController), path),
                method = method,
                argsAsJson = JsonParser.toString(params),
            )
        ) {
            if (redirect) {
                popUpTo(backStackEntry.destination.id) {
                    inclusive = true
                }
            }
        }
    }
}

private fun getCurrentRoute(navController: NavController): String {
    val lvNavRoute = navController.currentBackStackEntry?.toRoute<LiveViewNavRoute>()
    Log.d(TAG, "Current Route: $lvNavRoute")
    return lvNavRoute?.route ?: "/"
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
val LocalNavigation = compositionLocalOf<AppNavigationController> {
    error("No LocalNavController provided")
}

interface AppNavigationController {
    fun navigate(path: String, method: String, params: Map<String, Any?>, redirect: Boolean)
}
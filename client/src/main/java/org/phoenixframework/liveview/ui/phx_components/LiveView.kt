package org.phoenixframework.liveview.ui.phx_components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import org.phoenixframework.liveview.foundation.data.constants.HttpMethod.GET
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.data.mappers.generateRelativePath
import org.phoenixframework.liveview.foundation.data.service.SocketService
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
    val lvNavRoute = remember(backStackEntry) {
        backStackEntry.toRoute<LiveViewNavRoute>()
    }

    val liveViewCoordinator = koinViewModel<LiveViewCoordinator> {
        parametersOf(
            lvNavRoute.route,
            httpBaseUrl,
            lvNavRoute.method,
            lvNavRoute.argsAsMap(),
            lvNavRoute.redirect,
        )
    }
    val appNavigationController = remember(navController, backStackEntry, liveViewCoordinator) {
        LiveViewAppNavController(navController, backStackEntry, liveViewCoordinator)
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
            if (error is SocketService.RedirectException && error.location != null) {
                liveViewCoordinator.resetError()
                // All redirects are converted to GET requests (params must be send as query string)
                appNavigationController.navigate(
                    path = error.location,
                    method = GET,
                    params = emptyMap(),
                    redirect = true
                )
            } else {
                ErrorView(throwable = error)
            }
        }
    }

    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(lifecycle) {
        lifecycle.lifecycle.addObserver(liveViewCoordinator)
        onDispose {
            lifecycle.lifecycle.removeObserver(liveViewCoordinator)
        }
    }
    LaunchedEffect(state.navigationRequest) {
        state.navigationRequest?.let { navigationRequest ->
            processNavigationRequest(
                navigationRequest,
                lvNavRoute,
                navController,
                backStackEntry,
                liveViewCoordinator,
            )
        }
        liveViewCoordinator.resetNavigation()
    }
}

private fun processNavigationRequest(
    navigationRequest: LiveViewCoordinator.NavigationRequest,
    lvNavRoute: LiveViewNavRoute,
    navController: NavController,
    backStackEntry: NavBackStackEntry,
    liveViewCoordinator: LiveViewCoordinator,
) {
    val (newRoute, redirect) = navigationRequest
    val routePath = generateRelativePath(getCurrentRoute(navController), newRoute)
    Log.d(TAG, "Navigate to: $routePath")
    navController.navigate(
        LiveViewNavRoute(
            route = routePath,
            argsAsJson = lvNavRoute.argsAsJson,
            redirect = redirect,
        )
    ) {
        if (redirect) {
            popUpTo(backStackEntry.destination.id) {
                inclusive = true
            }
        }
    }
}

@Serializable
private data class LiveViewNavRoute(
    val route: String? = null,
    val method: String? = null,
    val argsAsJson: String? = null,
    val redirect: Boolean = false,
) {
    fun argsAsMap(): Map<String, Any?> {
        return argsAsJson?.let {
            try {
                JsonParser.parse<Map<String, Any?>>(it)
            } catch (e: Exception) {
                emptyMap()
            }
        } ?: emptyMap()
    }
}

private class LiveViewAppNavController(
    private val navController: NavController,
    private val backStackEntry: NavBackStackEntry,
    private val liveViewCoordinator: LiveViewCoordinator,
) : AppNavigationController {
    override fun navigate(
        path: String,
        method: String,
        params: Map<String, Any?>,
        redirect: Boolean,
    ) {
        navController.navigate(
            LiveViewNavRoute(
                route = generateRelativePath(getCurrentRoute(navController), path),
                method = method,
                argsAsJson = JsonParser.toString(params),
                redirect = redirect,
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
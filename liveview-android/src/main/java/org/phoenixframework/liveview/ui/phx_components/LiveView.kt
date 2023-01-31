package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.ui.theme.LiveViewTestTheme

@Composable
fun LiveView(
    liveViewCoordinator: LiveViewCoordinator

) {
    val state by liveViewCoordinator.backStack.collectAsState()
    // val liveViewState by LiveViewState.slotTable.collectAsState()
    LiveViewTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {


            val navController =  rememberNavController()

            if(state.isNotEmpty()){

                PhxLiveView(liveViewState = state.peek(), navController = navController)
            }


        }
    }
}
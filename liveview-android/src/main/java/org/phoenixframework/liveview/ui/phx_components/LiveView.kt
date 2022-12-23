package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.managers.LiveViewState
import org.phoenixframework.liveview.ui.theme.LiveViewTestTheme

@Composable
fun LiveView() {
    val liveViewState by LiveViewState.slotTable.collectAsState()

    LiveViewTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Surface {
                PhxLiveView(liveViewState)
            }
        }
    }
}
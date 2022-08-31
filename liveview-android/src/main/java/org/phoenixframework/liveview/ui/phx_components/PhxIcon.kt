package org.phoenixframework.liveview.ui.phx_components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jsoup.nodes.Element

@Composable fun PhxIcon(
    element: Element,
    modifier: Modifier
) {


    Icon(Icons.Filled.Favorite, contentDescription = null)
}
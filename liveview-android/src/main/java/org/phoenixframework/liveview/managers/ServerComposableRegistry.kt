package org.phoenixframework.liveview.managers

import androidx.compose.runtime.Composable
import org.jsoup.nodes.Element

object ServerComposableRegistry {

    private val customComposables = mutableMapOf<String, @Composable () -> Unit>()

    @Composable fun getOrAddCustomComposableFromServer(element: Element) {

    }
}
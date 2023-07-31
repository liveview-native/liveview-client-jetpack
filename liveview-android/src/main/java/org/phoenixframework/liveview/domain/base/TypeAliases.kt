package org.phoenixframework.liveview.domain.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

typealias PushEvent = (type: String, event:String, value: Any, target: Int?) -> Unit

typealias OnChildren = @Composable (ComposableTreeNode, PaddingValues?) -> Unit
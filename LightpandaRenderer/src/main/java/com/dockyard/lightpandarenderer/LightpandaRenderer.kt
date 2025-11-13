package com.dockyard.lightpandarenderer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import com.dockyard.lightpandaclient.LightpandaRuntime
import com.dockyard.lightpandaclient.Node
import com.dockyard.lightpandaclient.NodeType

import org.swift.swiftkit.core.SwiftArena

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.future.await
import kotlin.jvm.optionals.getOrNull

val LocalSwiftArena = compositionLocalOf<SwiftArena> {
    error("No SwiftArena provided")
}

val LocalLightpandaRuntime = compositionLocalOf<LightpandaRuntime> {
    error("No LightpandaRuntime provided")
}

@Composable
fun LightpandaRenderer(url: String) {
    val arena = remember {
        SwiftArena.ofConfined()
    }
    val runtime = remember {
        LightpandaRuntime.init(url, arena)
    }
    var dom: Node? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        runtime.start().await()
        runtime.observeDOM {
            dom = runtime.getDom(arena).getOrNull()
        }.await()
    }
    CompositionLocalProvider(LocalSwiftArena provides arena) {
        CompositionLocalProvider(LocalLightpandaRuntime provides runtime) {
            when (dom) {
                null -> Text("Loading...")
                else -> NodeComposable(dom!!)
            }
        }
    }
}

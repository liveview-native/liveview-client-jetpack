package com.dockyard.liveviewtest.liveview.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.phoenixframework.liveview.domain.LiveViewCoordinator
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView


@RunWith(AndroidJUnit4::class)
abstract class BaseComposableModifierTest: BaseTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Composable
    fun ViewFromTemplate(
        template: String,
        coordinator: LiveViewCoordinator = LiveViewCoordinator(
            httpBaseUrl = "",
            wsBaseUrl = "",
            route = null
        ),
        pushEvent: PushEvent = coordinator::pushEvent
    ) {
        val state by coordinator.composableTree.collectAsState()
        if (state.children.isNotEmpty()) {
            PhxLiveView(
                composableNode = state.children.first(),
                pushEvent = pushEvent
            )
        }
        LaunchedEffect(template) {
            val json = "{\"s\": [\"${template.templateToTest()}\"]}"
            coordinator.parseTemplate(json)
        }
    }
}
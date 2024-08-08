package org.phoenixframework.liveview.test.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import org.junit.Before
import org.junit.runner.RunWith
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode


@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
abstract class BaseComposableModifierTest : BaseTest() {

    @Before
    fun setup() {
        modifiersParser.clearCacheTable()
    }

    @Composable
    fun ViewFromTemplate(
        template: String,
        coordinator: LiveViewCoordinator = LiveViewJetpack.newLiveViewCoordinator("", ""),
        pushEvent: PushEvent = coordinator::pushEvent
    ) {
        val state by coordinator.state.collectAsState()
        if (state.composableTreeNode.children.isNotEmpty()) {
            PhxLiveView(
                composableNode = state.composableTreeNode.children.first(),
                pushEvent = pushEvent
            )
        }
        LaunchedEffect(template) {
            val json = "{\"s\": [\"${template.templateToTest()}\"]}"
            coordinator.parseTemplate(json)
        }
    }
}
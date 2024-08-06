package org.phoenixframework.liveview.test.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode


@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
abstract class BaseComposableModifierTest : BaseTest(), KoinTest {

    protected val modifiersParser: BaseModifiersParser by inject()

    @Before
    fun setup() {
        LiveViewJetpack.getModifiersParser().clearCacheTable()
    }

    @Composable
    internal fun ViewFromTemplate(
        template: String,
        coordinator: LiveViewCoordinator = koinViewModel<LiveViewCoordinator> {
            parametersOf("", "")
        },
        pushEvent: PushEvent = coordinator::pushEvent
    ) {
        val state by coordinator.state.collectAsState()
        SideEffect {
            println("State: $state")
        }
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
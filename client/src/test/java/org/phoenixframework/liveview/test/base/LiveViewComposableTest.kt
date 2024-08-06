package org.phoenixframework.liveview.test.base

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.phoenixframework.liveview.BuildConfig.IS_RECORDING_SHOT_TEST
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.test.R
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.LiveViewNativeTheme
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

// Tips: You can use Robolectric while using AndroidJUnit4
@RunWith(AndroidJUnit4::class)
// Enable Robolectric Native Graphics (RNG)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
@OptIn(ExperimentalRoborazziApi::class)
abstract class LiveViewComposableTest : BaseTest(), KoinTest {

    private val isRecording = IS_RECORDING_SHOT_TEST
    protected val modifiersParser: BaseModifiersParser = LiveViewJetpack.getModifiersParser()

    @Before
    fun setup() {
        if (!isRecording) {
            loadAppStylesMockFile(composeRule.activity)
        }
    }

    @After
    fun tearDown() {
        if (!isRecording) {
            LiveViewJetpack.getModifiersParser().clearCacheTable()
        }
    }

    internal fun compareNativeComposableWithTemplate(
        nativeComposable: @Composable () -> Unit,
        template: String,
        testTag: String? = null,
        captureScreenImage: Boolean = false,
        delayBeforeScreenshot: Long = 0,
        coordinator: LiveViewCoordinator = LiveViewJetpack.newLiveViewCoordinator("", ""),
        pushEvent: PushEvent = coordinator::pushEvent,
        onBeforeScreenShot: ((ComposeContentTestRule) -> Unit)? = null
    ) {
        composeRule.setContent {
            val themeData by LiveViewJetpack.getThemeHolder().themeData.collectAsState()
            LiveViewNativeTheme(themeData = themeData) {
                if (isRecording) {
                    nativeComposable()
                } else {
                    val state by coordinator.state.collectAsState()
                    val json = "{\"s\": [\"${template.templateToTest()}\"]}"
                    coordinator.parseTemplate(json)
                    if (state.composableTreeNode.children.isNotEmpty()) {
                        PhxLiveView(
                            composableNode = state.composableTreeNode.children.first(),
                            pushEvent = pushEvent
                        )
                    }
                }
            }
        }

        // Do some action on the UI before capture the screenshot
        onBeforeScreenShot?.invoke(composeRule)

        if (delayBeforeScreenshot > 0) {
            Thread.sleep(delayBeforeScreenshot)
        }

        when {
            captureScreenImage ->
                captureScreenRoboImage()

            testTag != null ->
                composeRule.onNodeWithTag(testTag, useUnmergedTree = true).captureRoboImage()

            else ->
                composeRule.onRoot().captureRoboImage()
        }
    }

    companion object {
        private fun loadAppStylesMockFile(context: Context) {
            val inputStream = context.resources.openRawResource(R.raw.app_jetpack_styles)
            val bufferedReader = inputStream.bufferedReader()
            try {
                bufferedReader.use { it.readText() }.let { text ->
                    LiveViewJetpack.getModifiersParser().fromStyleFile(text)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


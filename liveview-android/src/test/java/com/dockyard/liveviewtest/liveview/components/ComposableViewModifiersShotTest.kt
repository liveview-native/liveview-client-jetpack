package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrClass
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

class ComposableViewModifiersShotTest : LiveViewComposableTest() {

    @Test
    fun captionBarPaddingTest() {
        val style = """
            %{'captionBarPaddingTest' => [
                {:captionBarPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.captionBarPadding()) {
                    Text(
                        text = "Caption Bar Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Caption Bar Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun clickableTest() {
        var counter = 0
        val style = """
           %{'clickableTest' => [
              {:clickable, [], [
                {:__event__, [], ['my-click-event', []]}
              ]}
            ]}  
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Clickable",
                        modifier = Modifier.clickable(
                            onClick = {
                                counter = 10
                            }
                        )
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrClass="$style" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = { _, _, _, _ ->
                // Changing the counter value to check if the button is clicked
                counter = 10
            },
            delayBeforeScreenshot = 200,
        )
        assertEquals(10, counter)
    }

    @Test
    fun clickableNamedTest() {
        var counter = 0
        val style = """
           %{'clickableNamedTest' => [
              {:clickable, [], [[
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}  
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Clickable",
                        modifier = Modifier.clickable(
                            onClick = {
                                counter = 10
                            }
                        )
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrClass="$style" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = { _, _, _, _ ->
                // Changing the counter value to check if the button is clicked
                counter = 10
            },
            delayBeforeScreenshot = 200,
        )
        assertEquals(10, counter)
    }

    @Test
    fun clickableTestWithOtherParams() {
        var counter = 0
        val style = """
           %{'clickableTestWithOtherParams' => [
              {:clickable, [], [[
                enabled: true, 
                onClickLabel: 'onClickLabel', 
                role: {:., [], [:Role, :Button]}, 
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}  
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Clickable",
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClickLabel = "onClickLabel",
                            role = Role.Button,
                            onClick = {
                                counter = 10
                            }
                        )
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrClass="$style" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = { _, _, _, _ ->
                // Changing the counter value to check if the button is clicked
                counter = 10
            },
        )
        assertEquals(10, counter)
    }

    @Test
    fun displayCutoutPaddingTest() {
        val style = """
            %{'displayCutoutPadding' => [
                {:displayCutoutPadding, [], []},
            ]}
            """
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.displayCutoutPadding()) {
                    Text(
                        text = "Display Cutout Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Display Cutout Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeDrawingPaddingTest() {
        val style = """
            %{'safeDrawingPaddingTest' => [
                {:safeDrawingPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeDrawingPadding()) {
                    Text(
                        text = "Safe Drawing Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Safe Drawing Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun imePaddingTest() {
        val style = """
            %{'imePaddingTest' => [
                {:imePadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.imePadding()) {
                    Text(
                        text = "IME Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="IME Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun mandatorySystemGesturesPaddingTest() {
        val style = """
            %{'mandatorySystemGesturesPaddingTest' => [
                {:mandatorySystemGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.mandatorySystemGesturesPadding()) {
                    Text(
                        text = "Mandatory System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Mandatory System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun navigationBarsPaddingTest() {
        val style = """
            %{'navigationBarsPaddingTest' => [
                {:navigationBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    Text(
                        text = "Navigation Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Navigation Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeContentPaddingTest() {
        val style = """
            %{'safeContentPaddingTest' => [
                {:safeContentPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeContentPadding()) {
                    Text(
                        text = "Safe Content Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Safe Content Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeGesturesPaddingTest() {
        val style = """
            %{'safeGesturesPaddingTest' => [
                {:safeGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeGesturesPadding()) {
                    Text(
                        text = "Safe Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Safe Gestures Padding Test" />
                </$box>
                """
        )
    }

    // INFO: Robolectric does not support shadows at the moment.
    // https://github.com/robolectric/robolectric/issues/8081
    @Test
    fun shadowTest() {
        val style = """
            %{'shadowTest' => [
                {:shadow, [], [[
                    elevation: 8,
                    shape: {:., [], [:RectangleShape]},
                    clip: false,
                    ambientColor: {:., [], [:Color, :Blue]},
                    spotColor: {:., [], [:Color, :Green]}    
                ]]},
                {:background, [], [{:., [], [:Color, :White]}]},
                {:padding, [], [{:., [], [16, :dp]}]}
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = RectangleShape,
                                clip = false,
                                ambientColor = Color.Blue,
                                spotColor = Color.Green
                            )
                            .background(Color.White)
                            .padding(16.dp),

                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Shadow Test")
                    }
                }
            },
            template = """
                <$box $attrSize="200" $attrContentAlignment="${AlignmentValues.center}">
                    <$box $attrClass="$style">
                        <$text $attrText="Shadow Test" />
                    </$box>
                </$box>
                """
        )
    }

    @Test
    fun statusBarsPaddingTest() {
        val style = """
            %{'statusBarsPaddingTest' => [
                {:statusBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    Text(
                        text = "Status Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Status Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemBarsPaddingTest() {
        val style = """
            %{'systemBarsPaddingTest' => [
                {:systemBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemBarsPadding()) {
                    Text(
                        text = "System Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="System Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemGesturesPaddingTest() {
        val style = """
            %{'systemGesturesPaddingTest' => [
                {:systemGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemGesturesPadding()) {
                    Text(
                        text = "System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun waterfallPaddingTest() {
        val style = """
            %{'waterfallPaddingTest' => [
                {:waterfallPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.waterfallPadding()) {
                    Text(
                        text = "Waterfall Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Waterfall Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun windowInsetsPaddingTest() {
        val style = """
            %{'windowInsetsPadding' => [ 
              {:windowInsetsPadding, [], [
                {:WindowInsets, [], [
                  {:., [], [10, :dp]}, 
                  {:., [], [20, :dp]}, 
                  {:., [], [30, :dp]}, 
                  {:., [], [40, :dp]}
                ]}
              ]} 
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets(
                            10.dp,
                            20.dp,
                            30.dp,
                            40.dp
                        )
                    )
                ) {
                    Text(
                        text = "Window Insets Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Window Insets Padding Test" />
                </$box>                  
                """
        )
    }
}
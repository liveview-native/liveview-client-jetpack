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
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties.ProgressBarRangeInfo
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues.center
import org.phoenixframework.liveview.data.constants.Attrs.attrClass
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.base.PushEvent

class ComposableViewModifiersShotTest : LiveViewComposableTest() {

    @Test
    fun captionBarPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"captionBarPaddingTest" => [
                {:captionBarPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.captionBarPadding()) {
                    Text(
                        text = "Caption Bar Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="captionBarPaddingTest">
                    <$text $attrText="Caption Bar Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun clickableTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        ModifiersParser.fromStyleFile(
            """
           %{"clickableTest" => [
              {:clickable, [], [
                {:__event__, [], ["my-click-event", []]}
              ]}
            ]}  
            """, pushEvent
        )
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
                    <$text $attrClass="clickableTest" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = pushEvent,
            delayBeforeScreenshot = 200,
        )
        assertEquals(10, counter)
    }

    @Test
    fun clickableNamedTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        ModifiersParser.fromStyleFile(
            """
           %{"clickableNamedTest" => [
              {:clickable, [], [[
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}  
            """,
            pushEvent
        )
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
                    <$text $attrClass="clickableNamedTest" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = pushEvent,
            delayBeforeScreenshot = 200,
        )
        assertEquals(10, counter)
    }

    @Test
    fun clickableTestWithOtherParams() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        ModifiersParser.fromStyleFile(
            """
           %{"clickableTestWithOtherParams" => [
              {:clickable, [], [[
                enabled: true, 
                onClickLabel: 'onClickLabel', 
                role: {:., [], [:Role, :Button]}, 
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}  
            """, pushEvent
        )
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
                    <$text $attrClass="clickableTestWithOtherParams" $attrText="Clickable" />
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithText("Clickable").performClick()
            },
            pushEvent = pushEvent,
        )
        assertEquals(10, counter)
    }

    @Test
    fun displayCutoutPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"displayCutoutPadding" => [
                {:displayCutoutPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.displayCutoutPadding()) {
                    Text(
                        text = "Display Cutout Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="displayCutoutPadding">
                    <$text $attrText="Display Cutout Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeDrawingPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"safeDrawingPaddingTest" => [
                {:safeDrawingPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeDrawingPadding()) {
                    Text(
                        text = "Safe Drawing Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="safeDrawingPaddingTest">
                    <$text $attrText="Safe Drawing Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun imePaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"imePaddingTest" => [
                {:imePadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.imePadding()) {
                    Text(
                        text = "IME Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="imePaddingTest">
                    <$text $attrText="IME Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun mandatorySystemGesturesPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"mandatorySystemGesturesPaddingTest" => [
                {:mandatorySystemGesturesPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.mandatorySystemGesturesPadding()) {
                    Text(
                        text = "Mandatory System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="mandatorySystemGesturesPaddingTest">
                    <$text $attrText="Mandatory System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun minimumInteractiveComponentSizeTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"minimumInteractiveComponentSizeTest" => [
                {:minimumInteractiveComponentSize, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A"
                    )
                }
            },
            template = """
                <$box $attrClass="minimumInteractiveComponentSizeTest" $attrContentAlignment="$center">
                    <$text $attrText="A" />
                </$box>
                """
        )
    }

    @Test
    fun navigationBarsPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"navigationBarsPaddingTest" => [
                {:navigationBarsPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    Text(
                        text = "Navigation Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="navigationBarsPaddingTest">
                    <$text $attrText="Navigation Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeContentPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"safeContentPaddingTest" => [
                {:safeContentPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeContentPadding()) {
                    Text(
                        text = "Safe Content Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="safeContentPaddingTest">
                    <$text $attrText="Safe Content Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeGesturesPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"safeGesturesPaddingTest" => [
                {:safeGesturesPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeGesturesPadding()) {
                    Text(
                        text = "Safe Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="safeGesturesPaddingTest">
                    <$text $attrText="Safe Gestures Padding Test" />
                </$box>
                """
        )
    }

    // INFO: Robolectric does not support shadows at the moment.
    // https://github.com/robolectric/robolectric/issues/8081
    @Test
    fun shadowTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"shadowTest" => [
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
            """
        )
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
                <$box $attrSize="200" $attrContentAlignment="$center">
                    <$box $attrClass="shadowTest">
                        <$text $attrText="Shadow Test" />
                    </$box>
                </$box>
                """
        )
    }

    @Test
    fun statusBarsPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"statusBarsPaddingTest" => [
                {:statusBarsPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    Text(
                        text = "Status Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="statusBarsPaddingTest">
                    <$text $attrText="Status Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemBarsPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"systemBarsPaddingTest" => [
                {:systemBarsPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemBarsPadding()) {
                    Text(
                        text = "System Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="systemBarsPaddingTest">
                    <$text $attrText="System Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemGesturesPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"systemGesturesPaddingTest" => [
                {:systemGesturesPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemGesturesPadding()) {
                    Text(
                        text = "System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="systemGesturesPaddingTest">
                    <$text $attrText="System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun testTagTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testTagTest" => [
                {:testTag, [], ["myTestTag"]},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Test Tag Test",
                        modifier = Modifier.testTag("myTestTag")
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Test Tag Test" $attrClass="testTagTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithTag("myTestTag").assertExists()
            }
        )
    }

    @Test
    fun testTagNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testTagTest" => [
                {:testTag, [], [[tag: "myTestTag"]]},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Test Tag Test",
                        modifier = Modifier.testTag(tag = "myTestTag")
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Test Tag Test" $attrClass="testTagTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNodeWithTag("myTestTag").assertExists()
            }
        )
    }

    @Test
    fun testProgressSemanticsTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testProgressSemanticsTest" => [
                {:progressSemantics, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Progress semantics",
                        modifier = Modifier.progressSemantics()
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Progress semantics" $attrClass="testProgressSemanticsTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNode(SemanticsMatcher.keyIsDefined(ProgressBarRangeInfo)).assertExists()
            }
        )
    }

    @Test
    fun testProgressSemanticsValueTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testProgressSemanticsValueTest" => [
                {:progressSemantics, [], [0.5]},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Progress semantics",
                        modifier = Modifier.progressSemantics(value = 0.5f)
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Progress semantics" $attrClass="testProgressSemanticsValueTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNode(SemanticsMatcher.keyIsDefined(ProgressBarRangeInfo)).assertExists()
                rule.onNode(
                    SemanticsMatcher.expectValue(
                        ProgressBarRangeInfo,
                        ProgressBarRangeInfo(0.5f, 0f..1f)
                    )
                ).assertExists()
            }
        )
    }

    @Test
    fun testProgressSemanticsValueAndRangeTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testProgressSemanticsValueAndRangeTest" => [
                {:progressSemantics, [], [50.0, {:.., [], [10.0, 100.0]}]},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Progress semantics",
                        modifier = Modifier.progressSemantics(value = 50f, valueRange = 10f..100f)
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Progress semantics" $attrClass="testProgressSemanticsValueAndRangeTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNode(SemanticsMatcher.keyIsDefined(ProgressBarRangeInfo)).assertExists()
                rule.onNode(
                    SemanticsMatcher.expectValue(
                        ProgressBarRangeInfo,
                        ProgressBarRangeInfo(50f, 10f..100f)
                    )
                ).assertExists()
            }
        )
    }

    @Test
    fun testProgressSemanticsValuesNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"testProgressSemanticsValuesNamedTest" => [
              {:progressSemantics, [], [[
                value: 50.0, 
                valueRange: {:.., [], [10.0, 100.0]}, 
                steps: 5
              ]]},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box {
                    Text(
                        text = "Progress semantics",
                        modifier = Modifier.progressSemantics(
                            value = 50f,
                            valueRange = 10f..100f,
                            steps = 5
                        )
                    )
                }
            },
            template = """
                <$box>
                    <$text $attrText="Progress semantics" $attrClass="testProgressSemanticsValuesNamedTest"/>
                </$box>
                """,
            onBeforeScreenShot = { rule ->
                rule.onNode(SemanticsMatcher.keyIsDefined(ProgressBarRangeInfo)).assertExists()
                rule.onNode(
                    SemanticsMatcher.expectValue(
                        ProgressBarRangeInfo,
                        ProgressBarRangeInfo(50f, 10f..100f, 5)
                    )
                ).assertExists()
            }
        )
    }

    @Test
    fun waterfallPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"waterfallPaddingTest" => [
                {:waterfallPadding, [], []},
            ]}
            """
        )
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.waterfallPadding()) {
                    Text(
                        text = "Waterfall Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="waterfallPaddingTest">
                    <$text $attrText="Waterfall Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun windowInsetsPaddingTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"windowInsetsPadding" => [ 
              {:windowInsetsPadding, [], [
                {:WindowInsets, [], [
                  {:., [], [10, :dp]}, 
                  {:., [], [20, :dp]}, 
                  {:., [], [30, :dp]}, 
                  {:., [], [40, :dp]}
                ]}
              ]} 
            ]}
            """
        )
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
                <$box $attrClass="windowInsetsPadding">
                    <$text $attrText="Window Insets Padding Test" />
                </$box>                  
                """
        )
    }
}
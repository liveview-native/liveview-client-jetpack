package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.requestFocus
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.constants.Attrs.attrClass
import org.phoenixframework.liveview.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.constants.ComposableTypes.column
import org.phoenixframework.liveview.constants.ComposableTypes.textField
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.test.base.BaseComposableModifierTest

@RunWith(AndroidJUnit4::class)
class FocusTest : BaseComposableModifierTest() {

    @Test
    fun onFocusChangedTest() {
        var focusedCounter = 0
        var unfocusedCounter = 0
        val pushEvent: PushEvent = { _, _, value, _ ->
            (value as? Map<*, *>)?.let {
                if (it["hasFocus"] == true) focusedCounter++
                if (it["hasFocus"] == false) unfocusedCounter++
            }
        }
        modifiersParser.fromStyleFile(
            """
                %{"onFocusChangedTest" => [
                  {:focusable, [], []},  
                  {:onFocusChanged, [], [ {:__event__, [], ['my-click-event', []]} ] }
                ]}
                """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <$column>
                            <$textField $attrClass="onFocusChangedTest" $attrPhxValue="Text 1" />
                            <$textField $attrClass="onFocusChangedTest" $attrPhxValue="Text 2" />
                        </$column>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Text 1").requestFocus()
            onNodeWithText("Text 2").requestFocus()
        }
        // both textfield are unfocused => unfocused = 2 | focused = 0
        // first textfield request focus => unfocused = 2 | focused = 1
        // second textfield request focus => unfocused = 3 | focused = 2
        assertEquals(3, unfocusedCounter)
        assertEquals(2, focusedCounter)
    }

    @Test
    fun onFocusEventTest() {
        var focusedCounter = 0
        var unfocusedCounter = 0
        val pushEvent: PushEvent = { _, _, value, _ ->
            (value as? Map<*, *>)?.let {
                if (it["hasFocus"] == true) focusedCounter++
                if (it["hasFocus"] == false) unfocusedCounter++
            }
        }
        modifiersParser.fromStyleFile(
            """
                %{"onFocusEventTest" => [
                  {:focusable, [], []},  
                  {:onFocusEvent, [], [ {:__event__, [], ['my-click-event', []]} ] }
                ]}
                """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <$column>
                            <$textField $attrClass="onFocusEventTest" $attrPhxValue="Text 1" />
                            <$textField $attrClass="onFocusEventTest" $attrPhxValue="Text 2" />
                        </$column>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Text 1").requestFocus()
            onNodeWithText("Text 2").requestFocus()
        }
        // both textfield are unfocused => unfocused = 2 | focused = 0
        // first textfield request focus => unfocused = 2 | focused = 1
        // second textfield request focus => unfocused = 3 | focused = 2
        assertEquals(3, unfocusedCounter)
        assertEquals(2, focusedCounter)
    }
}
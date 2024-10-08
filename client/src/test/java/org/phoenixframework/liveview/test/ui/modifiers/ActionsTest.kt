package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.click
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.constants.Attrs.attrClass
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrText
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_CLICK
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_DOUBLE_CLICK
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_LONG_CLICK
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.test.base.BaseComposableModifierTest

@RunWith(AndroidJUnit4::class)
class ActionsTest : BaseComposableModifierTest() {

    @Test
    fun clickableTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        modifiersParser.fromStyleFile(
            """
           %{"clickableTest" => [
              {:clickable, [], [
                {:__event__, [], ["my-click-event", []]}
              ]}
            ]}  
            """, pushEvent
        )

        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="clickableTest" 
                                ${attrText}="Clickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent
                )
            }

            onNodeWithText("Clickable").performClick()
        }
        assertEquals(10, counter)
    }

    @Test
    fun clickableWithBackslashTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        modifiersParser.fromStyleFile(
            """
           %{"clickable(__event__(\"Test\"))" => [
              {:clickable, [], [
                {:__event__, [], ["Test", []]}
              ]}
            ]}  
            """, pushEvent
        )

        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrStyle}="clickable(__event__(&quot;Test&quot;))" 
                                ${attrText}="Clickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent
                )
            }

            onNodeWithText("Clickable").performClick()
        }
        assertEquals(10, counter)
    }

    @Test
    fun clickableNamedTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        modifiersParser.fromStyleFile(
            """
           %{"clickableNamedTest" => [
              {:clickable, [], [[
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}
            """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="clickableNamedTest" 
                                ${attrText}="Clickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Clickable").performClick()
        }
        assertEquals(10, counter)
    }

    @Test
    fun clickableTestWithOtherParams() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        modifiersParser.fromStyleFile(
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
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="clickableTestWithOtherParams" 
                                ${attrText}="Clickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Clickable").performClick()
        }
        assertEquals(10, counter)
    }

    @Test
    fun combinedClickableClickAndLongClickTest() {
        var counter = 0
        val combinedClickablePushEvent: PushEvent = { type, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            when (type) {
                EVENT_TYPE_LONG_CLICK -> counter += 7
                EVENT_TYPE_CLICK -> counter++
            }
        }
        modifiersParser.fromStyleFile(
            """
           %{"combinedClickableClickAndLongClickTest" => [
              {:combinedClickable, [], [[
                onLongClick: {:__event__, [], ["my-click-event", []]},
                onClick: {:__event__, [], ["my-click-event", []]}
              ]]}
            ]}
            """, combinedClickablePushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="combinedClickableClickAndLongClickTest" 
                                ${attrText}="CombinedClickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = combinedClickablePushEvent,
                )

            }
            onNodeWithText("CombinedClickable").performClick()

            runOnIdle {
                assertEquals(1, counter)
            }

            onNodeWithText("CombinedClickable")
                .performTouchInput {
                    longClick()
                }

            runOnIdle {
                assertEquals(8, counter)
            }
        }
    }

    @Test
    fun combinedClickableClickAndDoubleClickTest() {
        var counter = 0
        val combinedClickablePushEvent: PushEvent = { type, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            when (type) {
                EVENT_TYPE_DOUBLE_CLICK -> counter += 7
                EVENT_TYPE_CLICK -> counter++
            }
        }
        modifiersParser.fromStyleFile(
            """
           %{"combinedClickableClickAndDoubleClickTest" => [
              {:combinedClickable, [], [[
                onDoubleClick: {:__event__, [], ["my-click-event", []]},
                onClick: {:__event__, [], ["my-click-event", []]}
              ]]}
            ]}
            """, combinedClickablePushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text}
                                ${attrClass}="combinedClickableClickAndDoubleClickTest"
                                ${attrText}="CombinedClickable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = combinedClickablePushEvent,
                )
            }
            onNodeWithText("CombinedClickable").performTouchInput {
                click()
                advanceEventTime(viewConfiguration.doubleTapTimeoutMillis)
                doubleClick()
            }
            assertEquals(8, counter)
        }
    }

    @Test
    fun selectableTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        modifiersParser.fromStyleFile(
            """
           %{"selectableTest" => [
              {:selectable, [], [[
                selected: true,
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}
            """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="selectableTest" 
                                ${attrText}="Selectable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Selectable").performClick()
        }
        assertEquals(10, counter)
    }

    @Test
    fun toggleableTest() {
        var value = true
        val pushEvent: PushEvent = { _, _, newValue, _ ->
            // Changing the counter value to check if the button is clicked
            value = newValue as Boolean
        }
        modifiersParser.fromStyleFile(
            """
           %{"toggleableTest" => [
              {:toggleable, [], [[
                value: $value,
                onValueChange: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}
            """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="toggleableTest" 
                                ${attrText}="Toggleable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("Toggleable").performClick()
        }
        assertEquals(false, value)
    }

    @Test
    fun triStateToggleableTest() {
        var value = ToggleableState.Off
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            value = ToggleableState.On
        }
        modifiersParser.fromStyleFile(
            """
           %{"triStateToggleableTest" => [
              {:triStateToggleable, [], [[
                state: {:., [], [:ToggleableState, :Off]},
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}
            """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="triStateToggleableTest" 
                                ${attrText}="TriStateToggleable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("TriStateToggleable").performClick()
        }
        assertEquals(ToggleableState.On, value)
    }

    @Test
    fun triStateToggleableUsingConstructorTest() {
        var value = ToggleableState(true)
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            value = ToggleableState(false)
        }
        modifiersParser.fromStyleFile(
            """
           %{"triStateToggleableUsingConstructorTest" => [
              {:triStateToggleable, [], [[
                state: {:ToggleableState, [], [true]},
                onClick: {:__event__, [], ['my-click-event', []]}
              ]]}
            ]}
            """,
            pushEvent
        )
        composeRule.run {
            setContent {
                ViewFromTemplate(
                    template = """
                        <${ComposableTypes.box}>
                            <${ComposableTypes.text} 
                                ${attrClass}="triStateToggleableUsingConstructorTest" 
                                ${attrText}="TriStateToggleable" />
                        </${ComposableTypes.box}>
                        """,
                    pushEvent = pushEvent,
                )
            }
            onNodeWithText("TriStateToggleable").performClick()
        }
        assertEquals(ToggleableState.Off, value)
    }
}
package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dockyard.liveviewtest.liveview.util.BaseComposableModifierTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.constants.Attrs.attrClass
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.PushEvent

@RunWith(AndroidJUnit4::class)
class ActionsTest : BaseComposableModifierTest() {

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
                ComposableBuilder.EVENT_TYPE_LONG_CLICK -> counter += 7
                ComposableBuilder.EVENT_TYPE_CLICK -> counter++
            }
        }
        ModifiersParser.fromStyleFile(
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
                ComposableBuilder.EVENT_TYPE_DOUBLE_CLICK -> counter += 7
                ComposableBuilder.EVENT_TYPE_CLICK -> counter++
            }
        }
        ModifiersParser.fromStyleFile(
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
            // For some reason, performClick is not performing click when onDoubleClick is declared.
            // Therefore we are just testing the double click action.
            // https://issuetracker.google.com/issues/338162116
            // TODO uncomment the lines below when receive a reply from the open issue
            //  (and change the assertion below)
            // onNodeWithText("CombinedClickable").performClick()
            // assertEquals(1, counter)

            onNodeWithText("CombinedClickable")
                .performTouchInput {
                    doubleClick()
                }

            assertEquals(7, counter)
        }
    }

    @Test
    fun selectableTest() {
        var counter = 0
        val pushEvent: PushEvent = { _, _, _, _ ->
            // Changing the counter value to check if the button is clicked
            counter = 10
        }
        ModifiersParser.fromStyleFile(
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
}
package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import org.phoenixframework.liveview.constants.ModifierArgs.argEnabled
import org.phoenixframework.liveview.constants.ModifierArgs.argOnFocusChanged
import org.phoenixframework.liveview.constants.ModifierArgs.argOnFocusEvent
import org.phoenixframework.liveview.constants.ModifierArgs.argOnPositioned
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_FOCUS_CHANGED
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_FOCUS_EVENT
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.focusableFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val enabled = argOrNamedArg(args, argEnabled, 0)?.booleanValue
    return if (enabled != null) {
        this.then(Modifier.focusable(enabled = enabled))
    } else this
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.onFocusedBoundsChangedFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val event = argOrNamedArg(args, argOnPositioned, 0)?.let { eventFromArgument(it) }
    return if (event != null) {
        this.then(
            Modifier.onFocusedBoundsChanged { layoutCoordinates ->
                val (eventName, _) = event
                pushEvent?.invoke(
                    EVENT_TYPE_FOCUS_CHANGED,
                    eventName,
                    layoutCoordinates?.let { lc ->
                        val parent = lc.parentCoordinates?.let { parentLc ->
                            parentLc.parentCoordinates
                            mapOf(
                                "height" to parentLc.size.height,
                                "width" to parentLc.size.width,
                                "isAttached" to parentLc.isAttached,
                            )
                        }
                        mapOf(
                            "height" to lc.size.height,
                            "width" to lc.size.width,
                            "isAttached" to lc.isAttached,
                            "parent" to parent,
                        )
                    },
                    null
                )
            }
        )
    } else this
}

fun Modifier.onFocusChangedFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val event = argOrNamedArg(args, argOnFocusChanged, 0)?.let { eventFromArgument(it) }
    return if (event != null) {
        this.then(
            Modifier.onFocusChanged {
                val (eventName, _) = event
                pushEvent?.invoke(
                    EVENT_TYPE_FOCUS_CHANGED,
                    eventName,
                    mapOf(
                        "hasFocus" to it.hasFocus,
                        "isFocused" to it.isFocused,
                        "isCaptured" to it.isCaptured
                    ), null
                )
            }
        )
    } else this
}

fun Modifier.onFocusEventFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val event = argOrNamedArg(args, argOnFocusEvent, 0)?.let { eventFromArgument(it) }
    return if (event != null) {
        this.then(
            Modifier.onFocusEvent {
                val (eventName, _) = event
                pushEvent?.invoke(
                    EVENT_TYPE_FOCUS_EVENT,
                    eventName,
                    mapOf(
                        "hasFocus" to it.hasFocus,
                        "isFocused" to it.isFocused,
                        "isCaptured" to it.isCaptured
                    ), null
                )
            }
        )
    } else this
}


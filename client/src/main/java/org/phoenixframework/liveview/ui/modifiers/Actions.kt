package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import org.phoenixframework.liveview.constants.ModifierArgs.argEnabled
import org.phoenixframework.liveview.constants.ModifierArgs.argOnClick
import org.phoenixframework.liveview.constants.ModifierArgs.argOnClickLabel
import org.phoenixframework.liveview.constants.ModifierArgs.argOnDoubleClick
import org.phoenixframework.liveview.constants.ModifierArgs.argOnLongClick
import org.phoenixframework.liveview.constants.ModifierArgs.argOnLongClickLabel
import org.phoenixframework.liveview.constants.ModifierArgs.argOnValueChange
import org.phoenixframework.liveview.constants.ModifierArgs.argRole
import org.phoenixframework.liveview.constants.ModifierArgs.argSelected
import org.phoenixframework.liveview.constants.ModifierArgs.argState
import org.phoenixframework.liveview.constants.ModifierArgs.argValue
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_DOUBLE_CLICK
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_LONG_CLICK
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString

fun Modifier.clickableFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)

    val enabled: Boolean?
    val onClickLabel: String?
    val role: Role?
    val event: Pair<String, Any?>?

    // If we pass just one param, it's the clickable function
    if (args.size == 1) {
        enabled = true
        onClickLabel = null
        role = null
        event = argOrNamedArg(args, argOnClick, 0)?.let { eventFromArgument(it) }
    } else {
        enabled = argOrNamedArg(args, argEnabled, 0)?.booleanValue
        onClickLabel = argOrNamedArg(args, argOnClickLabel, 1)?.stringValue
        role = argOrNamedArg(args, argRole, 2)?.let { roleFromArgument(it) }
        event = argOrNamedArg(args, argOnClick, 3)?.let { eventFromArgument(it) }
    }
    return event?.let {
        this.then(
            Modifier.clickable(
                enabled = enabled ?: true,
                onClickLabel = onClickLabel,
                role = role,
                onClick = {
                    val (eventName, eventArgs) = it
                    val clickFunction = onClickFromString(pushEvent, eventName, eventArgs)
                    clickFunction.invoke()
                }
            )
        )
    } ?: this
}

// combinedClickable lets you add double tap or long-press behavior (not both at the same time)
// in addition to normal click behavior.
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.combinedClickableFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)

    val enabled: Boolean?
    val onClickLabel: String?
    val onLongClickLabel: String?
    val role: Role?
    val onClickEvent: Pair<String, Any?>?
    val onLongClickEvent: Pair<String, Any?>?
    val onDoubleClickEvent: Pair<String, Any?>?

    // If we pass just one param, it's the clickable function
    if (args.size == 1) {
        enabled = true
        onClickLabel = null
        role = null
        onLongClickLabel = null
        onLongClickEvent = null
        onDoubleClickEvent = null
        onClickEvent = argOrNamedArg(args, argOnClick, 0)?.let { eventFromArgument(it) }
    } else {
        enabled = argOrNamedArg(args, argEnabled, 0)?.booleanValue
        onClickLabel = argOrNamedArg(args, argOnClickLabel, 1)?.stringValue
        role = argOrNamedArg(args, argRole, 2)?.let { roleFromArgument(it) }
        onLongClickLabel = argOrNamedArg(args, argOnLongClickLabel, 3)?.stringValue
        onLongClickEvent = argOrNamedArg(args, argOnLongClick, 4)?.let { eventFromArgument(it) }
        onDoubleClickEvent = argOrNamedArg(args, argOnDoubleClick, 5)?.let { eventFromArgument(it) }
        onClickEvent = argOrNamedArg(args, argOnClick, 6)?.let { eventFromArgument(it) }
    }
    return onClickEvent?.let { clickEvent ->
        this.then(
            Modifier.combinedClickable(
                enabled = enabled ?: true,
                onClickLabel = onClickLabel,
                role = role,
                onLongClickLabel = onLongClickLabel,
                onLongClick = onLongClickEvent?.let { onLongClick ->
                    {
                        val (eventName, eventArgs) = onLongClick
                        if (eventName.isNotEmpty()) {
                            pushEvent?.invoke(
                                EVENT_TYPE_LONG_CLICK,
                                eventName,
                                eventArgs,
                                null
                            )
                        }
                    }
                },
                onDoubleClick = onDoubleClickEvent?.let { onDoubleClick ->
                    {
                        val (eventName, eventArgs) = onDoubleClick
                        if (eventName.isNotEmpty()) {
                            pushEvent?.invoke(
                                EVENT_TYPE_DOUBLE_CLICK,
                                eventName,
                                eventArgs,
                                null
                            )
                        }
                    }
                },
                onClick = {
                    val (eventName, eventArgs) = clickEvent
                    val clickFunction = onClickFromString(pushEvent, eventName, eventArgs)
                    clickFunction.invoke()
                },
            )
        )
    } ?: this
}

fun Modifier.selectableFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)

    val selected = argOrNamedArg(args, argSelected, 0)?.booleanValue
    val enabled = argOrNamedArg(args, argEnabled, 1)?.booleanValue ?: true
    val role = argOrNamedArg(args, argRole, 2)?.let { roleFromArgument(it) }
    val event = argOrNamedArg(args, argOnClick, 3)?.let { eventFromArgument(it) }

    return if (event != null && selected != null) {
        Modifier.selectable(
            selected = selected,
            enabled = enabled,
            role = role,
            onClick = {
                val (eventName, eventArgs) = event
                val clickFunction = onClickFromString(pushEvent, eventName, eventArgs)
                clickFunction.invoke()
            }
        )
    } else this
}

fun Modifier.toggleableFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)

    val value = argOrNamedArg(args, argValue, 0)?.booleanValue
    val enabled = argOrNamedArg(args, argEnabled, 1)?.booleanValue ?: true
    val role = argOrNamedArg(args, argRole, 2)?.let { roleFromArgument(it) }
    val event = argOrNamedArg(args, argOnValueChange, 3)?.let { eventFromArgument(it) }

    return if (event != null && value != null) {
        Modifier.toggleable(
            value = value,
            enabled = enabled,
            role = role,
            onValueChange = { newValue ->
                val (eventName, _) = event
                if (eventName.isNotEmpty()) {
                    pushEvent?.invoke(
                        EVENT_TYPE_CHANGE,
                        eventName,
                        newValue,
                        null
                    )
                }
            }
        )
    } else this
}

fun Modifier.triStateToggleableFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val args = argsOrNamedArgs(arguments)

    val state = argOrNamedArg(args, argState, 0)?.let { toggleableStateFromArgument(it) }
    val enabled = argOrNamedArg(args, argEnabled, 1)?.booleanValue ?: true
    val role = argOrNamedArg(args, argRole, 2)?.let { roleFromArgument(it) }
    val event = argOrNamedArg(args, argOnClick, 3)?.let { eventFromArgument(it) }

    return if (state != null && event != null) {
        Modifier.triStateToggleable(
            state = state,
            enabled = enabled,
            role = role,
            onClick = {
                val (eventName, eventArgs) = event
                val clickFunction = onClickFromString(pushEvent, eventName, eventArgs)
                clickFunction.invoke()
            }
        )
    } else this
}
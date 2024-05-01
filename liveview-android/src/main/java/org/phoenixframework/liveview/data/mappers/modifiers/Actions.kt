package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEnabled
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnClick
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnClickLabel
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnDoubleClick
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnLongClick
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnLongClickLabel
import org.phoenixframework.liveview.data.constants.ModifierArgs.argOnValueChange
import org.phoenixframework.liveview.data.constants.ModifierArgs.argRole
import org.phoenixframework.liveview.data.constants.ModifierArgs.argSelected
import org.phoenixframework.liveview.data.dto.onClickFromString
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.PushEvent

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
        event = argOrNamedArg(args, argOnClick, 0)?.let { eventFromStyle(it) }
    } else {
        enabled = argOrNamedArg(args, argEnabled, 0)?.booleanValue
        onClickLabel = argOrNamedArg(args, argOnClickLabel, 1)?.stringValue
        role = argOrNamedArg(args, argRole, 2)?.let { roleFromStyle(it) }
        event = argOrNamedArg(args, argOnClick, 3)?.let { eventFromStyle(it) }
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
        onClickEvent = argOrNamedArg(args, argOnClick, 0)?.let { eventFromStyle(it) }
    } else {
        enabled = argOrNamedArg(args, argEnabled, 0)?.booleanValue
        onClickLabel = argOrNamedArg(args, argOnClickLabel, 1)?.stringValue
        role = argOrNamedArg(args, argRole, 2)?.let { roleFromStyle(it) }
        onLongClickLabel = argOrNamedArg(args, argOnLongClickLabel, 3)?.stringValue
        onLongClickEvent = argOrNamedArg(args, argOnLongClick, 4)?.let { eventFromStyle(it) }
        onDoubleClickEvent = argOrNamedArg(args, argOnDoubleClick, 5)?.let { eventFromStyle(it) }
        onClickEvent = argOrNamedArg(args, argOnClick, 6)?.let { eventFromStyle(it) }
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
                                ComposableBuilder.EVENT_TYPE_LONG_CLICK,
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
                                ComposableBuilder.EVENT_TYPE_DOUBLE_CLICK,
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
    val role = argOrNamedArg(args, argRole, 2)?.let { roleFromStyle(it) }
    val event = argOrNamedArg(args, argOnClick, 3)?.let { eventFromStyle(it) }

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

    val value = argOrNamedArg(args, argSelected, 0)?.booleanValue
    val enabled = argOrNamedArg(args, argEnabled, 1)?.booleanValue ?: true
    val role = argOrNamedArg(args, argRole, 2)?.let { roleFromStyle(it) }
    val event = argOrNamedArg(args, argOnValueChange, 3)?.let { eventFromStyle(it) }

    return if (event != null && value != null) {
        Modifier.toggleable(
            value = value,
            enabled = enabled,
            role = role,
            onValueChange = { newValue ->
                val (eventName, _) = event
                if (eventName.isNotEmpty()) {
                    pushEvent?.invoke(
                        ComposableBuilder.EVENT_TYPE_CHANGE,
                        eventName,
                        newValue,
                        null
                    )
                }
            }
        )
    } else this
}
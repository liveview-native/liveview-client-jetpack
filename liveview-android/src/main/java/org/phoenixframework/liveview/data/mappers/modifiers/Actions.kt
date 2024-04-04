package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import org.phoenixframework.liveview.data.dto.onClickFromString
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
        event = argOrNamedArg(args, "onClick", 0)?.let { eventFromStyle(it) }
    } else {
        enabled = argOrNamedArg(args, "enabled", 0)?.booleanValue
        onClickLabel = argOrNamedArg(args, "onClickLabel", 1)?.stringValue
        role = argOrNamedArg(args, "role", 2)?.let { roleFromStyle(it) }
        event = argOrNamedArg(args, "onClick", 3)?.let { eventFromStyle(it) }
    }
    return this.then(
        Modifier.clickable(
            enabled = enabled ?: true,
            onClickLabel = onClickLabel,
            role = role,
            onClick = {
                event?.let {
                    val (eventName, eventArgs) = it
                    val clickFunction = onClickFromString(pushEvent, eventName, eventArgs)
                    clickFunction.invoke()
                }
            }
        )
    )
}
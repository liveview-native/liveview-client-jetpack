package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.constants.ModifierArgs.argX
import org.phoenixframework.liveview.constants.ModifierArgs.argY
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.offsetFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val x = argOrNamedArg(args, argX, 0)?.let { dpFromArgument(it) }
    val y = argOrNamedArg(args, argY, 1)?.let { dpFromArgument(it) }
    return if (x != null || y != null) {
        this.then(Modifier.offset(x ?: 0.dp, y ?: 0.dp))
    } else this
}

fun Modifier.absoluteOffsetFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val x = argOrNamedArg(args, argX, 0)?.let { dpFromArgument(it) }
    val y = argOrNamedArg(args, argY, 1)?.let { dpFromArgument(it) }
    return if (x != null || y != null) {
        this.then(Modifier.absoluteOffset(x ?: 0.dp, y ?: 0.dp))
    } else this
}
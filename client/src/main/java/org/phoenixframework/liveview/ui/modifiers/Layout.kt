package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.layoutIdFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    if (args.isEmpty()) return this
    val id = args.firstOrNull()?.value
    return id?.let {
        this.then(
            Modifier.layoutId(it)
        )
    } ?: this
}
package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.offsetFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val x = args.find { it.name == "x" }?.intValue ?: args.getOrNull(0)?.intValue
    val y = args.find { it.name == "y" }?.intValue ?: args.getOrNull(1)?.intValue
    return if (x != null && y != null) {
        this.then(Modifier.offset(x.dp, y.dp))
    } else this
}

fun Modifier.absoluteOffsetFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val x = args.find { it.name == "x" }?.intValue ?: args.getOrNull(0)?.intValue
    val y = args.find { it.name == "y" }?.intValue ?: args.getOrNull(1)?.intValue
    return if (x != null && y != null) {
        this.then(Modifier.absoluteOffset(x.dp, y.dp))
    } else this
}
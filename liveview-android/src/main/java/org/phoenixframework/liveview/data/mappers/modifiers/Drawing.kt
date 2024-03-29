package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

fun Modifier.clipFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val argument =
        if (arguments.firstOrNull()?.isList == true)
            arguments.first().listValue.firstOrNull()
        else
            arguments.firstOrNull()
    return argument?.let { shapeFromStyle(it) }?.let { this.then(Modifier.clip(it)) } ?: this
}
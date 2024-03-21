package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape

fun Modifier.clipFromStyle(arguments: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val argument =
        if (arguments.firstOrNull()?.isList == true)
            arguments.first().listValue.firstOrNull()
        else
            arguments.firstOrNull()
    return this.then(Modifier.clip(argument?.let { shapeFromStyle(it) } ?: RectangleShape))
}
package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

fun Modifier.backgroundFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    var color: Color? = null
    var shape = RectangleShape
    val backgroundArgs = argsOrNamedArgs(arguments)
    if (arguments.firstOrNull()?.isList == true) {
        // Named args
        backgroundArgs.find { it.name == "color" }?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.find { it.name == "shape" }?.let { arg ->
            shapeFromStyle(arg)?.let { shape = it }
        }
    } else {
        // No named args must be in the right order
        backgroundArgs.getOrNull(0)?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.getOrNull(1)?.let { arg ->
            shapeFromStyle(arg)?.let { shape = it }
        }
    }
    // Color is required. Shape is optional
    return color?.let { this.then(Modifier.background(it, shape)) } ?: this
}
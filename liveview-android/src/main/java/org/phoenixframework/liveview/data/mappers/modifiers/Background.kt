package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

fun Modifier.backgroundFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    var color: Color? = null
    var brush: Brush? = null
    var shape = RectangleShape
    var alpha = 1.0f
    val backgroundArgs = argsOrNamedArgs(arguments)
    if (arguments.firstOrNull()?.isList == true) {
        // Named args
        backgroundArgs.find { it.name == "color" }?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.find { it.name == "shape" }?.let { arg ->
            shapeFromStyle(arg)?.let { shape = it }
        }
        backgroundArgs.find { it.name == "brush" }?.let { arg ->
            brushFromStyle(arg)?.let { brush = it }
        }
        backgroundArgs.find { it.name == "alpha" }?.let { arg ->
            arg.floatValue?.let { alpha = it }
        }
    } else {
        // No named args must be in the right order
        backgroundArgs.getOrNull(0)?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.getOrNull(0)?.let { arg ->
            brushFromStyle(arg)?.let { brush = it }
        }
        backgroundArgs.getOrNull(1)?.let { arg ->
            shapeFromStyle(arg)?.let { shape = it }
        }
        backgroundArgs.getOrNull(2)?.let { arg ->
            arg.floatValue?.let { alpha = it }
        }
    }
    return color?.let {
        // Color is required. Shape is optional
        this.then(Modifier.background(it, shape))
    } ?: brush?.let {
        // Brush is required. Shape is optional
        this.then(Modifier.background(it, shape, alpha))
    } ?: this
}
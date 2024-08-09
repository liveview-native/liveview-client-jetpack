package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import org.phoenixframework.liveview.constants.ModifierArgs.argAlpha
import org.phoenixframework.liveview.constants.ModifierArgs.argBrush
import org.phoenixframework.liveview.constants.ModifierArgs.argColor
import org.phoenixframework.liveview.constants.ModifierArgs.argShape
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.backgroundFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    var color: Color? = null
    var brush: Brush? = null
    var shape = RectangleShape
    var alpha = 1.0f
    val backgroundArgs = argsOrNamedArgs(arguments)
    if (arguments.firstOrNull()?.isList == true) {
        // Named args
        backgroundArgs.find { it.name == argColor }?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.find { it.name == argShape }?.let { arg ->
            shapeFromArgument(arg)?.let { shape = it }
        }
        backgroundArgs.find { it.name == argBrush }?.let { arg ->
            brushFromArgument(arg)?.let { brush = it }
        }
        backgroundArgs.find { it.name == argAlpha }?.let { arg ->
            arg.floatValue?.let { alpha = it }
        }
    } else {
        // No named args must be in the right order
        backgroundArgs.getOrNull(0)?.let { arg ->
            colorFromArgument(arg)?.let { color = it }
        }
        backgroundArgs.getOrNull(0)?.let { arg ->
            brushFromArgument(arg)?.let { brush = it }
        }
        backgroundArgs.getOrNull(1)?.let { arg ->
            shapeFromArgument(arg)?.let { shape = it }
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
package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

fun Modifier.backgroundFromStyle(arguments: List<ModifierDataWrapper.ArgumentData>): Modifier {
    var color: Color? = null
    var shape = RectangleShape
    arguments.forEachIndexed { index, argument ->
        // If there's only one argument, or the argument is named "color", or it's the first argument
        if (arguments.size == 1 || argument.name == "color" || index == 0) {
            color = colorFromArgument(argument)
        }

        if (arguments.size == 2 && (argument.name == "shape" || index == 1)) {
            shapeFromStyle(argument)?.let {
                shape = it
            }
        }
    }
    return color?.let { this.then(Modifier.background(it, shape)) } ?: this
}
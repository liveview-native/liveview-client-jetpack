package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.borderFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    var borderStroke: BorderStroke? = null
    var borderShape: Shape? = null
    var borderColor: Color? = null
    var borderWidth: Dp? = null
    var borderBrush: Brush? = null

    val borderArguments = argsOrNamedArgs(arguments)
    if (arguments.firstOrNull()?.isList == true) {
        // Named args
        var arg = borderArguments.find { it.name == "color" }
        if (arg != null) borderColor = colorFromArgument(arg)

        arg = borderArguments.find { it.name == "width" }
        if (arg != null) borderWidth = (arg.intValue ?: 0).dp

        arg = borderArguments.find { it.name == "border" }
        if (arg != null) borderStroke = borderStrokeFromArgument(arg)

        arg = borderArguments.find { it.name == "shape" }
        if (arg != null) borderShape = shapeFromStyle(arg)

        arg = borderArguments.find { it.name == "brush" }
        if (arg != null) borderBrush = brushFromStyle(arg)

    } else {
        // Ordered params
        for (borderArgument in arguments) {
            when {
                borderArgument.type == "BorderStroke" ->
                    borderStroke = borderStrokeFromArgument(borderArgument)

                borderArgument.type == "Color" ->
                    borderColor = colorFromArgument(borderArgument)

                borderArgument.type == "Shape" ->
                    borderShape = shapeFromStyle(borderArgument)

                borderArgument.isInt ->
                    borderWidth = (borderArgument.intValue ?: 0).dp

                borderArgument.isDot -> {
                    val b = brushFromStyle(borderArgument)
                    if (b != null) borderBrush = b

                    val c = colorFromArgument(borderArgument)
                    if (c != null) borderColor = c

                    val s = shapeFromStyle(borderArgument)
                    if (s != null) borderShape = s
                }
            }
        }
    }
    if (borderBrush != null && borderWidth != null && borderShape != null) {
        return this.then(Modifier.border(borderWidth, borderBrush, borderShape))
    }
    if (borderStroke != null) {
        return this.then(Modifier.border(borderStroke, borderShape ?: RectangleShape))
    }
    if (borderColor != null && borderWidth != null) {
        return this.then(Modifier.border(borderWidth, borderColor, borderShape ?: RectangleShape))
    }
    return this
}



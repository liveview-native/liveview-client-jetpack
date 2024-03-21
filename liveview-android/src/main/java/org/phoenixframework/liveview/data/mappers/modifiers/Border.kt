package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.borderFromStyle(arguments: List<ModifierDataWrapper.ArgumentData>): Modifier {
    var borderStroke: BorderStroke? = null
    var borderShape: Shape? = null
    var borderColor: Color? = null
    var borderWidth: Dp? = null
    // Using named arguments
    if (arguments.firstOrNull()?.isList == true) {
        val borderArguments = arguments.getOrNull(0)?.listValue ?: emptyList()
        for (borderArgument in borderArguments) {
            if (borderArgument.name == "color") {
                borderColor = colorFromArgument(borderArgument)
            }

            if (borderArgument.name == "width") {
                borderWidth = (borderArgument.intValue ?: 0).dp
            }

            if (borderArgument.name == "border") {
                borderStroke = borderStrokeFromArgument(borderArgument)
            }

            if (borderArgument.name == "shape") {
                borderShape = shapeFromStyle(borderArgument)
            }
        }
    } else {
        // Allowing params in any order (without names)
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
                    val c = colorFromArgument(borderArgument)
                    if (c != null) borderColor = c

                    val s = shapeFromStyle(borderArgument)
                    if (s != null) borderShape = s
                }
            }
        }
    }
    if (borderStroke != null) {
        return this.then(Modifier.border(borderStroke, borderShape ?: RectangleShape))
    }
    if (borderColor != null && borderWidth != null) {
        return this.then(Modifier.border(borderWidth, borderColor, borderShape ?: RectangleShape))
    }
    return this
}



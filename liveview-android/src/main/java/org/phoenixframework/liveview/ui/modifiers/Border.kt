package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBorder
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBrush
import org.phoenixframework.liveview.data.constants.ModifierArgs.argColor
import org.phoenixframework.liveview.data.constants.ModifierArgs.argShape
import org.phoenixframework.liveview.data.constants.ModifierArgs.argWidth
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeBorderStroke
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeShape

fun Modifier.borderFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    var borderStroke: BorderStroke? = null
    var borderShape: Shape? = null
    var borderColor: Color? = null
    var borderWidth: Dp? = null
    var borderBrush: Brush? = null

    val borderArguments = argsOrNamedArgs(arguments)
    if (arguments.firstOrNull()?.isList == true) {
        // Named args
        var arg = borderArguments.find { it.name == argColor }
        if (arg != null) borderColor = colorFromArgument(arg)

        arg = borderArguments.find { it.name == argWidth }
        if (arg != null) borderWidth = (arg.intValue ?: 0).dp

        arg = borderArguments.find { it.name == argBorder }
        if (arg != null) borderStroke = borderStrokeFromArgument(arg)

        arg = borderArguments.find { it.name == argShape }
        if (arg != null) borderShape = shapeFromArgument(arg)

        arg = borderArguments.find { it.name == argBrush }
        if (arg != null) borderBrush = brushFromArgument(arg)

    } else {
        // Ordered params
        for (borderArgument in arguments) {
            when {
                borderArgument.type == typeBorderStroke ->
                    borderStroke = borderStrokeFromArgument(borderArgument)

                borderArgument.type == typeColor ->
                    borderColor = colorFromArgument(borderArgument)

                borderArgument.type == typeShape ->
                    borderShape = shapeFromArgument(borderArgument)

                borderArgument.isInt ->
                    borderWidth = (borderArgument.intValue ?: 0).dp

                borderArgument.isDot -> {
                    val b = brushFromArgument(borderArgument)
                    if (b != null) borderBrush = b

                    val c = colorFromArgument(borderArgument)
                    if (c != null) borderColor = c

                    val s = shapeFromArgument(borderArgument)
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



package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.ShapeValues
import org.phoenixframework.liveview.domain.extensions.toColor

fun shapeFromStyle(argument: ModifierDataWrapper.ArgumentData): Shape? {
    val clazz = if (argument.isDot)
        argument.listValue.getOrNull(0)?.stringValueWithoutColon ?: ""
    else
        argument.type
    val argsToCreateArg = argument.listValue

    return when (clazz) {
        ShapeValues.circle -> CircleShape
        ShapeValues.rectangle -> RectangleShape
        ShapeValues.roundedCorner -> {
            val cornerSizeParts =
                if (argsToCreateArg.firstOrNull()?.isList == true) {
                    val namedArgs = argsToCreateArg.first().listValue
                    listOf(
                        namedArgs.find { it.name == "topStart" }?.intValue ?: 0,
                        namedArgs.find { it.name == "topEnd" }?.intValue ?: 0,
                        namedArgs.find { it.name == "bottomEnd" }?.intValue ?: 0,
                        namedArgs.find { it.name == "bottomStart" }?.intValue ?: 0,
                    )
                } else {
                    argsToCreateArg.map { it.intValue }
                }

            return if (cornerSizeParts.size == 1) {
                cornerSizeParts.firstOrNull()?.let { RoundedCornerShape(it.dp) }
            } else if (cornerSizeParts.any { it != null }){
                RoundedCornerShape(
                    (cornerSizeParts.getOrNull(0) ?: 0).dp,
                    (cornerSizeParts.getOrNull(1) ?: 0).dp,
                    (cornerSizeParts.getOrNull(2) ?: 0).dp,
                    (cornerSizeParts.getOrNull(3) ?: 0).dp,
                )
            } else null
        }

        else -> null
    }
}

fun colorFromArgument(argument: ModifierDataWrapper.ArgumentData): Color? {
    val argsToCreateArg = argument.listValue
    val clazz = argsToCreateArg[0].stringValue

    // If it's is a "." argument, it's because we're using a predefined color (e.g.: Color.Red)
    if (argument.isDot && clazz == ":Color") {
        val value = argsToCreateArg[1].stringValueWithoutColon?.lowercase()
        return "system-$value".toColor()

        // If the first argument is a Color, then it's necessary to instantiate the Color object
    } else if (argument.type == "Color") {
        val colorArgbParts =
            if (argsToCreateArg.first().isList) argsToCreateArg.first().listValue else argsToCreateArg
        return if (colorArgbParts.size == 1) {
            Color(colorArgbParts.first().intValue ?: 0)
        } else {
            Color(
                colorArgbParts.find { it.name == "red" }?.intValue
                    ?: colorArgbParts.getOrNull(0)?.intValue ?: 0,
                colorArgbParts.find { it.name == "green" }?.intValue
                    ?: colorArgbParts.getOrNull(1)?.intValue ?: 0,
                colorArgbParts.find { it.name == "blue" }?.intValue
                    ?: colorArgbParts.getOrNull(2)?.intValue ?: 0,
                colorArgbParts.find { it.name == "alpha" }?.intValue
                    ?: colorArgbParts.getOrNull(3)?.intValue ?: 0,
            )
        }
    }
    return null
}

fun borderStrokeFromArgument(argument: ModifierDataWrapper.ArgumentData): BorderStroke? {
    val borderStrokeArguments =
        if (argument.isList) argument.listValue.first().listValue else argument.listValue
    val width = borderStrokeArguments.getOrNull(0)?.intValue?.dp
    val color = colorFromArgument(borderStrokeArguments[1])
    return if (width != null && color != null) BorderStroke(
        width,
        color ?: Color.Unspecified
    ) else null
}
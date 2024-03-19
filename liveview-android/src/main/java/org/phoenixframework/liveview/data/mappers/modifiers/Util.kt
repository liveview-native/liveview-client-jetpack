package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.ShapeValues

fun shapeFromStyle(argument: ModifierDataWrapper.ArgumentData): Shape? {
    val clazz = if (argument.isDot)
        argument.listValue.first().stringValue?.replace(":", "") ?: ""
    else
        argument.type
    val argsToCreateArg = argument.listValue

    return when (clazz) {
        ShapeValues.circle -> CircleShape
        ShapeValues.rectangle -> RectangleShape
        ShapeValues.roundedCorner -> {
            val cornerSizeParts =
                if (argsToCreateArg.first().isList) {
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
                RoundedCornerShape((cornerSizeParts.first() ?: 0).dp)
            } else {
                RoundedCornerShape(
                    (cornerSizeParts.getOrNull(0) ?: 0).dp,
                    (cornerSizeParts.getOrNull(1) ?: 0).dp,
                    (cornerSizeParts.getOrNull(2) ?: 0).dp,
                    (cornerSizeParts.getOrNull(3) ?: 0).dp,
                )
            }
        }

        else -> null
    }
}
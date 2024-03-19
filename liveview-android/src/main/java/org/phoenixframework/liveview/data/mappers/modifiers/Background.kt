package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import org.phoenixframework.liveview.domain.extensions.toColor

fun Modifier.backgroundFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    var color: Color? = null
    var shape = RectangleShape
    modifierData.forEachIndexed { index, argument ->
        // If there's only one argument, or the argument is named "color", or it's the first argument
        if (modifierData.size == 1 || argument.name == "color" || index == 0) {
            val argsToCreateArg = argument.listValue
            val clazz = argsToCreateArg[0].stringValue

            // If it's is a "." argument, it's because we're using a predefined color (e.g.: Color.Red)
            if (argument.isDot) {
                val value = argsToCreateArg[1].stringValue?.replace(":", "")?.lowercase()
                color = "system-$value".toColor()

                // If the first argument is a Color, then it's necessary to instantiate the Color object
            } else if (argument.type == "Color") {
                val colorArgbParts =
                    if (argsToCreateArg.first().isList) argsToCreateArg.first().listValue else argsToCreateArg
                color = if (colorArgbParts.size == 1) {
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
        }

        if (modifierData.size == 2 && (argument.name == "shape" || index == 1)) {
            shapeFromStyle(argument)?.let {
                shape = it
            }
        }
    }
    return color?.let { this.then(Modifier.background(it, shape)) } ?: this
}
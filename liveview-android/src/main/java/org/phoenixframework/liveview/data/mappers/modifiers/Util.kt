package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.ShapeValues
import org.phoenixframework.liveview.data.dto.tileModeFromString
import org.phoenixframework.liveview.domain.extensions.toColor

internal fun shapeFromStyle(argument: ModifierDataAdapter.ArgumentData): Shape? {
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
            } else if (cornerSizeParts.any { it != null }) {
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

fun colorsFromArguments(arguments: List<ModifierDataAdapter.ArgumentData>): List<Color> {
    return argsOrNamedArgs(arguments).mapNotNull {
        colorFromArgument(it)
    }
}

fun offsetFromArgument(argumentName: String, argument: ModifierDataAdapter.ArgumentData): Offset? {
    val pair = singleArgumentObjectValue(argumentName, argument)
    return pair?.let { (clazz, args) ->
        if (clazz == "Offset") {
            (args as? List<ModifierDataAdapter.ArgumentData>)?.mapNotNull {
                if (it.isInt) it.intValue?.toFloat() else it.floatValue
            }?.let {
                if (it.size == 2) Offset(it[0], it[1]) else null
            }

        } else {
            when (args.toString()) {
                "Zero" -> Offset.Zero
                "Infinite" -> Offset.Infinite
                "Unspecified" -> Offset.Unspecified
                else -> null
            }
        }
    }
}

fun colorFromArgument(argument: ModifierDataAdapter.ArgumentData): Color? {
    val argsToCreateArg = argument.listValue
    if (argsToCreateArg.isEmpty()) return null

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

internal fun borderStrokeFromArgument(argument: ModifierDataAdapter.ArgumentData): BorderStroke? {
    val borderStrokeArguments =
        if (argument.isList) argument.listValue.first().listValue else argument.listValue
    val width = borderStrokeArguments.getOrNull(0)?.intValue?.dp
    val color = colorFromArgument(borderStrokeArguments[1])
    return if (width != null && color != null) BorderStroke(
        width,
        color
    ) else null
}

internal fun brushFromStyle(argument: ModifierDataAdapter.ArgumentData): Brush? {
    val clazz = if (argument.isDot)
        argument.listValue.getOrNull(0)?.stringValueWithoutColon ?: ""
    else
        argument.type

    if (clazz != "Brush") {
        return null
    }
    val argsToCreateBrush = argument.listValue
    if (argsToCreateBrush.size < 2)
        return null

    val functionToCreateBrush = argsToCreateBrush[1].type
    var argsToTheFunctionToCreateBrush = argsToCreateBrush[1].listValue
    if (argsToTheFunctionToCreateBrush.isEmpty())
        return null

    // The color param is required. We're trying to find the argument named "colors"
    val colorsArg =
        argsToTheFunctionToCreateBrush.firstOrNull()?.listValue?.find { it.name == "colors" }
    // if we found this argument, we're going to use named parameters
    val colors: List<Color> = if (colorsArg != null) {
        argsToTheFunctionToCreateBrush = argsOrNamedArgs(argsToCreateBrush[1].listValue)
        colorsFromArguments(colorsArg.listValue)
    } else {
        argsToTheFunctionToCreateBrush.firstOrNull()?.let {
            colorsFromArguments(it.listValue)
        } ?: return null
    }

    return when (functionToCreateBrush) {
        "horizontalGradient" -> {
            val startX = argOrNamedArg(argsToTheFunctionToCreateBrush, "startX", 1)
                ?.let { singleArgumentFloatValue("startX", it) }
            val endX = argOrNamedArg(argsToTheFunctionToCreateBrush, "endX", 2)
                ?.let { singleArgumentFloatValue("endX", it) }
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, "tileMode", 3)?.let {
                singleArgumentObjectValue("tileMode", it)?.let { pair ->
                    tileModeFromString(pair.second.toString(), TileMode.Clamp)
                }
            }

            // TODO Implement overloaded version
            Brush.horizontalGradient(
                colors = colors,
                startX = startX ?: 0f,
                endX = endX ?: Float.POSITIVE_INFINITY,
                tileMode = tileMode ?: TileMode.Clamp
            )
        }

        "verticalGradient" -> {
            val startY = argOrNamedArg(argsToTheFunctionToCreateBrush, "startY", 1)
                ?.let { singleArgumentFloatValue("startY", it) }
            val endY = argOrNamedArg(argsToTheFunctionToCreateBrush, "endY", 2)
                ?.let { singleArgumentFloatValue("endY", it) }
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, "tileMode", 3)?.let {
                singleArgumentObjectValue("tileMode", it)?.let { pair ->
                    tileModeFromString(pair.second.toString(), TileMode.Clamp)
                }
            }

            // TODO Implement overloaded version
            Brush.verticalGradient(
                colors = colors,
                startY = startY ?: 0f,
                endY = endY ?: Float.POSITIVE_INFINITY,
                tileMode = tileMode ?: TileMode.Clamp
            )
        }

        "linearGradient" -> {
            val start = argOrNamedArg(argsToTheFunctionToCreateBrush, "start", 1)
                ?.let { offsetFromArgument("start", it) }
            val end = argOrNamedArg(argsToTheFunctionToCreateBrush, "_end", 2)
                ?.let { offsetFromArgument("_end", it) }
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, "tileMode", 3)?.let {
                singleArgumentObjectValue("tileMode", it)?.let { pair ->
                    tileModeFromString(pair.second.toString(), TileMode.Clamp)
                }
            }

            // TODO Implement overloaded version
            Brush.linearGradient(
                colors = colors,
                start = start ?: Offset.Zero,
                end = end ?: Offset.Infinite,
                tileMode = tileMode ?: TileMode.Clamp
            )
        }

        "radialGradient" -> {
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, "center", 1)
                ?.let { offsetFromArgument("center", it) }
            val radius = argOrNamedArg(argsToTheFunctionToCreateBrush, "radius", 2)
                ?.let { singleArgumentFloatValue("radius", it) }
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, "tileMode", 3)?.let {
                singleArgumentObjectValue("tileMode", it)?.let { pair ->
                    tileModeFromString(pair.second.toString(), TileMode.Clamp)
                }
            }

            // TODO Implement overloaded version
            Brush.radialGradient(
                colors = colors,
                center = centerOffset ?: Offset.Unspecified,
                radius = radius ?: Float.POSITIVE_INFINITY,
                tileMode = tileMode ?: TileMode.Clamp
            )
        }

        "sweepGradient" -> {
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, "center", 1)
                ?.let { offsetFromArgument("center", it) }

            // TODO Implement overloaded version
            Brush.sweepGradient(
                colors = colors,
                center = centerOffset ?: Offset.Unspecified,
            )
        }

        else -> null
    }
}

internal fun windowInsetsFromArgument(argument: ModifierDataAdapter.ArgumentData): WindowInsets? {
    return if (argument.type == "WindowInsets") {
        val left = (argument.listValue.find { it.name == "left" }
            ?: argument.listValue.getOrNull(0))?.intValue?.dp
        val top = (argument.listValue.find { it.name == "top" }
            ?: argument.listValue.getOrNull(1))?.intValue?.dp
        val right =
            (argument.listValue.find { it.name == "right" }
                ?: argument.listValue.getOrNull(2))?.intValue?.dp
        val bottom =
            (argument.listValue.find { it.name == "bottom" }
                ?: argument.listValue.getOrNull(3))?.intValue?.dp
        WindowInsets(left ?: 0.dp, top ?: 0.dp, right ?: 0.dp, bottom ?: 0.dp)
    } else null
}

// FIXME This function does not work when the first argument is a list
internal fun argsOrNamedArgs(
    arguments: List<ModifierDataAdapter.ArgumentData>
): List<ModifierDataAdapter.ArgumentData> =
    if (arguments.firstOrNull()?.isList == true) arguments.first().listValue
    else arguments

internal fun argOrNamedArg(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    name: String,
    index: Int
): ModifierDataAdapter.ArgumentData? {
    return (arguments.find { it.name == name } ?: arguments.getOrNull(index))
}

internal fun intrinsicSizeFromArgument(argument: ModifierDataAdapter.ArgumentData): IntrinsicSize? {
    val clazz = argument.listValue.firstOrNull()?.stringValueWithoutColon
    val clazzValue = argument.listValue.getOrNull(1)?.stringValueWithoutColon
    if (clazz == "IntrinsicSize" && clazzValue != null) {
        return try {
            IntrinsicSize.valueOf(clazzValue)
        } catch (_: Exception) {
            null
        }
    }
    return null
}

internal fun singleArgumentIntValue(
    argumentName: String,
    argument: ModifierDataAdapter.ArgumentData
): Int? {
    return if (argument.isList) {
        argument.listValue.find { it.name == argumentName }?.intValue
    } else if (argument.isInt && (argument.name == argumentName || argument.name == null)) {
        argument.intValue
    } else null
}

internal fun singleArgumentBooleanValue(
    argumentName: String,
    argument: ModifierDataAdapter.ArgumentData
): Boolean? {
    return if (argument.isList) {
        argument.listValue.find { it.name == argumentName }?.booleanValue
    } else if (argument.isBoolean && (argument.name == argumentName || argument.name == null)) {
        argument.booleanValue
    } else null
}

internal fun singleArgumentFloatValue(
    argumentName: String,
    argument: ModifierDataAdapter.ArgumentData
): Float? {
    return if (argument.isList) {
        val arg = argument.listValue.find { it.name == argumentName }
        if (arg != null) {
            if (arg.isInt) argument.intValue?.toFloat() else argument.floatValue
        } else null
    } else if (argument.isNumber && (argument.name == argumentName || argument.name == null)) {
        if (argument.isInt) argument.intValue?.toFloat() else argument.floatValue
    } else null
}

internal fun singleArgumentObjectValue(
    argumentName: String,
    argument: ModifierDataAdapter.ArgumentData
): Pair<String, Any>? {
    if (argument.name != null && argument.name != argumentName) {
        return null
    }
    if (argument.isDot) {
        val clazz = argument.listValue.getOrNull(0)?.stringValueWithoutColon
        val value = argument.listValue.getOrNull(1)?.stringValueWithoutColon
        if (clazz != null && value != null) {
            return clazz to value
        }
    } else {
        val clazz = argument.type
        val value = argument.listValue
        return clazz to value
    }
    return null
}
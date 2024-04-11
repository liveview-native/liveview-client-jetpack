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
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.data.constants.AlignmentLineValues
import org.phoenixframework.liveview.data.constants.BrushFunctions
import org.phoenixframework.liveview.data.constants.ModifierArgs.argAlpha
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBlue
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBottom
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBottomEnd
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBottomStart
import org.phoenixframework.liveview.data.constants.ModifierArgs.argCenter
import org.phoenixframework.liveview.data.constants.ModifierArgs.argColors
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEnd
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEndX
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEndY
import org.phoenixframework.liveview.data.constants.ModifierArgs.argGreen
import org.phoenixframework.liveview.data.constants.ModifierArgs.argHeight
import org.phoenixframework.liveview.data.constants.ModifierArgs.argLeft
import org.phoenixframework.liveview.data.constants.ModifierArgs.argRadius
import org.phoenixframework.liveview.data.constants.ModifierArgs.argRed
import org.phoenixframework.liveview.data.constants.ModifierArgs.argRight
import org.phoenixframework.liveview.data.constants.ModifierArgs.argStart
import org.phoenixframework.liveview.data.constants.ModifierArgs.argStartX
import org.phoenixframework.liveview.data.constants.ModifierArgs.argStartY
import org.phoenixframework.liveview.data.constants.ModifierArgs.argTileMode
import org.phoenixframework.liveview.data.constants.ModifierArgs.argTop
import org.phoenixframework.liveview.data.constants.ModifierArgs.argTopEnd
import org.phoenixframework.liveview.data.constants.ModifierArgs.argTopStart
import org.phoenixframework.liveview.data.constants.ModifierArgs.argWidth
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeBrush
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDpSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeEvent
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeIntrinsicSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeOffset
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeRange
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeRole
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeUnitDp
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeUnitSp
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeWindowInsets
import org.phoenixframework.liveview.data.constants.OffsetValues
import org.phoenixframework.liveview.data.constants.RoleValues
import org.phoenixframework.liveview.data.constants.ShapeValues
import org.phoenixframework.liveview.data.dto.tileModeFromString
import org.phoenixframework.liveview.domain.extensions.toColor

internal fun alignmentLineFromStyle(argument: ModifierDataAdapter.ArgumentData): AlignmentLine? {
    return if (argument.isDot) {
        when (argument.listValue.getOrNull(0)?.stringValueWithoutColon) {
            AlignmentLineValues.firstBaseline -> FirstBaseline
            AlignmentLineValues.lastBaseline -> LastBaseline
            else -> null
        }
    } else null
}

internal fun dpSizeFromStyle(argument: ModifierDataAdapter.ArgumentData): DpSize? {
    return if (argument.type == typeDpSize) {
        val args = argsOrNamedArgs(argument.listValue)
        val width = argOrNamedArg(args, argWidth, 0)?.let { dpFromStyle(it) }
        val height = argOrNamedArg(args, argHeight, 1)?.let { dpFromStyle(it) }
        if (width != null && height != null) {
            DpSize(width = width, height = height)
        } else null
    } else null
}

internal fun dpFromStyle(argument: ModifierDataAdapter.ArgumentData): Dp? {
    return if (argument.isDot && argument.listValue.getOrNull(1)?.stringValueWithoutColon == typeUnitDp) {
        val value = argument.listValue.getOrNull(0)
        if (value?.isFloat == true) value.floatValue?.dp
        else if (value?.isInt == true) value.intValue?.dp
        else null
    } else null
}

internal fun spFromStyle(argument: ModifierDataAdapter.ArgumentData): TextUnit? {
    return if (argument.isDot && argument.listValue.getOrNull(1)?.stringValueWithoutColon == typeUnitSp) {
        val value = argument.listValue.getOrNull(0)
        if (value?.isFloat == true) value.floatValue?.sp
        else if (value?.isInt == true) value.intValue?.sp
        else null
    } else null
}

internal fun eventFromStyle(argument: ModifierDataAdapter.ArgumentData): Pair<String, Any?>? {
    return if (argument.type == typeEvent) {
        val (event, args) = Pair(
            argument.listValue.getOrNull(0)?.stringValue,
            argument.listValue.getOrNull(1)?.listValue?.map { it.value }
        )
        if (event != null && args != null) {
            val pushArgs = if (args.isEmpty()) null else if (args.size == 1) args.first() else null
            event to pushArgs
        } else null
    } else null
}

internal fun floatRangeFromArgument(argument: ModifierDataAdapter.ArgumentData): ClosedFloatingPointRange<Float>? {
    return if (argument.type == typeRange && argument.listValue.size == 2) {
        val start = argument.listValue[0].floatValue
        val end = argument.listValue[1].floatValue
        if (start != null && end != null) start..end else null
    } else null
}

internal fun intRangeFromArgument(argument: ModifierDataAdapter.ArgumentData): IntRange? {
    return if (argument.type == typeRange && argument.listValue.size == 2) {
        val start = argument.listValue[0].intValue
        val end = argument.listValue[1].intValue
        if (start != null && end != null) start..end else null
    } else null
}

internal fun roleFromStyle(argument: ModifierDataAdapter.ArgumentData): Role? {
    val clazz = argument.listValue.getOrNull(0)?.stringValueWithoutColon
    val roleType = argument.listValue.getOrNull(1)?.stringValueWithoutColon
    return if (clazz == typeRole && roleType != null) {
        when (roleType) {
            RoleValues.button -> Role.Button
            RoleValues.checkbox -> Role.Checkbox
            RoleValues.switch -> Role.Switch
            RoleValues.radioButton -> Role.RadioButton
            RoleValues.tab -> Role.Tab
            RoleValues.image -> Role.Image
            RoleValues.dropdownList -> Role.DropdownList
            else -> null
        }
    } else null
}

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
                        namedArgs.find { it.name == argTopStart }?.let { dpFromStyle(it) }
                            ?: 0.dp,
                        namedArgs.find { it.name == argTopEnd }?.let { dpFromStyle(it) } ?: 0.dp,
                        namedArgs.find { it.name == argBottomEnd }?.let { dpFromStyle(it) }
                            ?: 0.dp,
                        namedArgs.find { it.name == argBottomStart }?.let { dpFromStyle(it) }
                            ?: 0.dp,
                    )
                } else {
                    argsToCreateArg.map { dpFromStyle(it) }
                }
            return if (cornerSizeParts.size == 1) {
                cornerSizeParts.firstOrNull()?.let { RoundedCornerShape(it) }
            } else if (cornerSizeParts.any { it != null }) {
                RoundedCornerShape(
                    cornerSizeParts.getOrNull(0) ?: 0.dp,
                    cornerSizeParts.getOrNull(1) ?: 0.dp,
                    cornerSizeParts.getOrNull(2) ?: 0.dp,
                    cornerSizeParts.getOrNull(3) ?: 0.dp,
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

fun offsetFromArgument(argument: ModifierDataAdapter.ArgumentData): Offset? {
    val pair = singleArgumentObjectValue(argument)
    return pair?.let { (clazz, args) ->
        if (clazz == typeOffset) {
            (args as? List<ModifierDataAdapter.ArgumentData>)?.mapNotNull {
                if (it.isInt) it.intValue?.toFloat() else it.floatValue
            }?.let {
                if (it.size == 2) Offset(it[0], it[1]) else null
            }

        } else {
            when (args.toString()) {
                OffsetValues.zero -> Offset.Zero
                OffsetValues.infinite -> Offset.Infinite
                OffsetValues.unspecified -> Offset.Unspecified
                else -> null
            }
        }
    }
}

fun colorFromArgument(argument: ModifierDataAdapter.ArgumentData): Color? {
    val argsToCreateArg = argument.listValue
    if (argsToCreateArg.isEmpty()) return null

    val clazz = argsToCreateArg[0].stringValueWithoutColon

    // If it's is a "." argument, it's because we're using a predefined color (e.g.: Color.Red)
    if (argument.isDot && clazz == typeColor) {
        val value = argsToCreateArg[1].stringValueWithoutColon?.lowercase()
        return "system-$value".toColor()

        // If the first argument is a Color, then it's necessary to instantiate the Color object
    } else if (argument.type == typeColor) {
        val colorArgbParts =
            if (argsToCreateArg.first().isList) argsToCreateArg.first().listValue else argsToCreateArg
        return if (colorArgbParts.size == 1) {
            Color(colorArgbParts.first().intValue ?: 0)
        } else {
            Color(
                argOrNamedArg(colorArgbParts, argRed, 0)?.intValue ?: 0,
                argOrNamedArg(colorArgbParts, argGreen, 1)?.intValue ?: 0,
                argOrNamedArg(colorArgbParts, argBlue, 2)?.intValue ?: 0,
                argOrNamedArg(colorArgbParts, argAlpha, 3)?.intValue ?: 0,
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

    if (clazz != typeBrush) {
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
        argsToTheFunctionToCreateBrush.firstOrNull()?.listValue?.find { it.name == argColors }
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
        BrushFunctions.horizontalGradient -> {
            val startX = argOrNamedArg(argsToTheFunctionToCreateBrush, argStartX, 1)?.floatValue
            val endX = argOrNamedArg(argsToTheFunctionToCreateBrush, argEndX, 2)?.floatValue
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, argTileMode, 3)?.let {
                singleArgumentObjectValue(it)?.let { pair ->
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

        BrushFunctions.verticalGradient -> {
            val startY = argOrNamedArg(argsToTheFunctionToCreateBrush, argStartY, 1)?.floatValue
            val endY = argOrNamedArg(argsToTheFunctionToCreateBrush, argEndY, 2)?.floatValue
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, argTileMode, 3)?.let {
                singleArgumentObjectValue(it)?.let { pair ->
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

        BrushFunctions.linearGradient -> {
            val start = argOrNamedArg(argsToTheFunctionToCreateBrush, argStart, 1)
                ?.let { offsetFromArgument(it) }
            val end = argOrNamedArg(argsToTheFunctionToCreateBrush, argEnd, 2)
                ?.let { offsetFromArgument(it) }
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, argTileMode, 3)?.let {
                singleArgumentObjectValue(it)?.let { pair ->
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

        BrushFunctions.radialGradient -> {
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, argCenter, 1)
                ?.let { offsetFromArgument(it) }
            val radius = argOrNamedArg(argsToTheFunctionToCreateBrush, argRadius, 2)?.floatValue
            val tileMode = argOrNamedArg(argsToTheFunctionToCreateBrush, argTileMode, 3)?.let {
                singleArgumentObjectValue(it)?.let { pair ->
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

        BrushFunctions.sweepGradient -> {
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, argCenter, 1)
                ?.let { offsetFromArgument(it) }

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
    if (argument.type == typeWindowInsets) {
        val args = argsOrNamedArgs(argument.listValue)
        val left = argOrNamedArg(args, argLeft, 0)?.intValue
        val top = argOrNamedArg(args, argTop, 1)?.intValue
        val right = argOrNamedArg(args, argRight, 2)?.intValue
        val bottom = argOrNamedArg(args, argBottom, 3)?.intValue
        if (left != null || top != null || right != null || bottom != null) {
            return WindowInsets(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
        }

        val leftDp = argOrNamedArg(args, argLeft, 0)?.let { dpFromStyle(it) }
        val topDp = argOrNamedArg(args, argTop, 1)?.let { dpFromStyle(it) }
        val rightDp = argOrNamedArg(args, argRight, 2)?.let { dpFromStyle(it) }
        val bottomDp = argOrNamedArg(args, argBottom, 3)?.let { dpFromStyle(it) }
        return WindowInsets(leftDp ?: 0.dp, topDp ?: 0.dp, rightDp ?: 0.dp, bottomDp ?: 0.dp)
    } else return null
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
    if (clazz == typeIntrinsicSize && clazzValue != null) {
        return try {
            IntrinsicSize.valueOf(clazzValue)
        } catch (e: Exception) {
            null
        }
    }
    return null
}

internal fun singleArgumentObjectValue(
    argument: ModifierDataAdapter.ArgumentData
): Pair<String, Any>? {
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
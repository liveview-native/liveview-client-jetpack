package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.keyframesWithSpline
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.constants.AlignmentLineValues
import org.phoenixframework.liveview.constants.AlignmentValues
import org.phoenixframework.liveview.constants.Attrs.attrPivotFractionX
import org.phoenixframework.liveview.constants.Attrs.attrPivotFractionY
import org.phoenixframework.liveview.constants.BrushFunctions
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argClip
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argExpandFrom
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialAlpha
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialOffset
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialOffsetX
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialScale
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialSize
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialWidth
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argShrinkTowards
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetAlpha
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffset
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffsetX
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffsetY
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetScale
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetWidth
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTransformOrigin
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argAnimation
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argDampingRatio
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argDelayMillis
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argDurationMillis
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argEasing
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argInitialStartOffset
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argIterations
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argRepeatMode
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argStiffness
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions.argValue
import org.phoenixframework.liveview.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.constants.ModifierArgs.argAlpha
import org.phoenixframework.liveview.constants.ModifierArgs.argAnimationSpec
import org.phoenixframework.liveview.constants.ModifierArgs.argBlue
import org.phoenixframework.liveview.constants.ModifierArgs.argBottom
import org.phoenixframework.liveview.constants.ModifierArgs.argBottomEnd
import org.phoenixframework.liveview.constants.ModifierArgs.argBottomStart
import org.phoenixframework.liveview.constants.ModifierArgs.argCenter
import org.phoenixframework.liveview.constants.ModifierArgs.argColor
import org.phoenixframework.liveview.constants.ModifierArgs.argColors
import org.phoenixframework.liveview.constants.ModifierArgs.argEnd
import org.phoenixframework.liveview.constants.ModifierArgs.argEndX
import org.phoenixframework.liveview.constants.ModifierArgs.argEndY
import org.phoenixframework.liveview.constants.ModifierArgs.argGreen
import org.phoenixframework.liveview.constants.ModifierArgs.argHeight
import org.phoenixframework.liveview.constants.ModifierArgs.argLeft
import org.phoenixframework.liveview.constants.ModifierArgs.argRadius
import org.phoenixframework.liveview.constants.ModifierArgs.argRed
import org.phoenixframework.liveview.constants.ModifierArgs.argRight
import org.phoenixframework.liveview.constants.ModifierArgs.argStart
import org.phoenixframework.liveview.constants.ModifierArgs.argStartX
import org.phoenixframework.liveview.constants.ModifierArgs.argStartY
import org.phoenixframework.liveview.constants.ModifierArgs.argTileMode
import org.phoenixframework.liveview.constants.ModifierArgs.argTop
import org.phoenixframework.liveview.constants.ModifierArgs.argTopEnd
import org.phoenixframework.liveview.constants.ModifierArgs.argTopStart
import org.phoenixframework.liveview.constants.ModifierArgs.argWidth
import org.phoenixframework.liveview.constants.ModifierTypes.typeAlignment
import org.phoenixframework.liveview.constants.ModifierTypes.typeBorderStroke
import org.phoenixframework.liveview.constants.ModifierTypes.typeBrush
import org.phoenixframework.liveview.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.constants.ModifierTypes.typeDpSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeEvent
import org.phoenixframework.liveview.constants.ModifierTypes.typeIntOffset
import org.phoenixframework.liveview.constants.ModifierTypes.typeIntSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeIntrinsicSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeOffset
import org.phoenixframework.liveview.constants.ModifierTypes.typeRange
import org.phoenixframework.liveview.constants.ModifierTypes.typeRole
import org.phoenixframework.liveview.constants.ModifierTypes.typeStartOffset
import org.phoenixframework.liveview.constants.ModifierTypes.typeTextUnit
import org.phoenixframework.liveview.constants.ModifierTypes.typeTextUnitEm
import org.phoenixframework.liveview.constants.ModifierTypes.typeTextUnitSp
import org.phoenixframework.liveview.constants.ModifierTypes.typeTextUnitType
import org.phoenixframework.liveview.constants.ModifierTypes.typeToggleableState
import org.phoenixframework.liveview.constants.ModifierTypes.typeTransformOrigin
import org.phoenixframework.liveview.constants.ModifierTypes.typeUnitDp
import org.phoenixframework.liveview.constants.ModifierTypes.typeUnitEm
import org.phoenixframework.liveview.constants.ModifierTypes.typeUnitSp
import org.phoenixframework.liveview.constants.ModifierTypes.typeWindowInsets
import org.phoenixframework.liveview.constants.OffsetValues
import org.phoenixframework.liveview.constants.RepeatModeValues
import org.phoenixframework.liveview.constants.RoleValues
import org.phoenixframework.liveview.constants.ShapeValues
import org.phoenixframework.liveview.constants.TransformOriginValues
import org.phoenixframework.liveview.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter
import org.phoenixframework.liveview.ui.view.easingFromString
import org.phoenixframework.liveview.ui.view.tileModeFromString
import kotlin.math.max
import kotlin.math.min

internal fun alignmentFromArgument(argument: ModifierDataAdapter.ArgumentData): Alignment? {
    return if (argument.isDot && argument.listValue.getOrNull(0)?.stringValueWithoutColon == typeAlignment) {
        when (argument.listValue.getOrNull(1)?.stringValueWithoutColon) {
            AlignmentValues.topStart -> Alignment.TopStart
            AlignmentValues.topCenter -> Alignment.TopCenter
            AlignmentValues.topEnd -> Alignment.TopEnd
            AlignmentValues.centerStart -> Alignment.CenterStart
            AlignmentValues.center -> Alignment.Center
            AlignmentValues.centerEnd -> Alignment.CenterEnd
            AlignmentValues.bottomStart -> Alignment.BottomStart
            AlignmentValues.bottomCenter -> Alignment.BottomCenter
            AlignmentValues.bottomEnd -> Alignment.BottomEnd
            else -> null
        }
    } else null
}

internal fun argOrNamedArg(
    arguments: List<ModifierDataAdapter.ArgumentData>, name: String, index: Int
): ModifierDataAdapter.ArgumentData? {
    return arguments.find { it.name == name }
        ?: if (arguments.all { it.name === null }) arguments.getOrNull(index) else null
}

// FIXME This function does not work when the first argument is a list
internal fun argsOrNamedArgs(
    arguments: List<ModifierDataAdapter.ArgumentData>
): List<ModifierDataAdapter.ArgumentData> =
    if (arguments.firstOrNull()?.isList == true) arguments.first().listValue
    else arguments

internal fun borderStrokeFromArgument(argument: ModifierDataAdapter.ArgumentData): BorderStroke? {
    return if (argument.type == typeBorderStroke) {
        val borderStrokeArguments = argsOrNamedArgs(argument.listValue)
        val width = argOrNamedArg(borderStrokeArguments, argWidth, 0)?.let { dpFromArgument(it) }
        val color = argOrNamedArg(borderStrokeArguments, argColor, 1)?.let { colorFromArgument(it) }
        if (width != null && color != null) BorderStroke(width, color) else null
    } else null
}

internal fun brushFromArgument(argument: ModifierDataAdapter.ArgumentData): Brush? {
    val clazz = if (argument.isDot)
        argument.listValue.getOrNull(0)?.stringValueWithoutColon ?: ""
    else
        argument.type

    if (clazz != typeBrush) {
        return null
    }
    val argsToCreateBrush = argument.listValue
    if (argsToCreateBrush.size < 2) return null

    val functionToCreateBrush = argsToCreateBrush[1].type
    var argsToTheFunctionToCreateBrush = argsToCreateBrush[1].listValue
    if (argsToTheFunctionToCreateBrush.isEmpty()) return null

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
            val start = argOrNamedArg(argsToTheFunctionToCreateBrush, argStart, 1)?.let {
                offsetFromArgument(it)
            }
            val end = argOrNamedArg(argsToTheFunctionToCreateBrush, argEnd, 2)?.let {
                offsetFromArgument(it)
            }
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
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, argCenter, 1)?.let {
                offsetFromArgument(it)
            }
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
            val centerOffset = argOrNamedArg(argsToTheFunctionToCreateBrush, argCenter, 1)?.let {
                offsetFromArgument(it)
            }

            // TODO Implement overloaded version
            Brush.sweepGradient(
                colors = colors,
                center = centerOffset ?: Offset.Unspecified,
            )
        }

        else -> null
    }
}

fun colorFromArgument(argument: ModifierDataAdapter.ArgumentData): Color? {
    val argsToCreateArg = argument.listValue
    if (argsToCreateArg.isEmpty()) return null

    val clazz = argsToCreateArg[0].stringValueWithoutColon

    // If it's is a "." argument, it's because we're using a predefined color (e.g.: Color.Red)
    if (argument.isDot && clazz == typeColor) {
        val value = argsToCreateArg[1].stringValueWithoutColon
        return value?.toColor()

        // If the first argument is a Color, then it's necessary to instantiate the Color object
    } else if (argument.type == typeColor) {
        val colorArgbParts =
            if (argsToCreateArg.first().isList) argsToCreateArg.first().listValue else argsToCreateArg
        return if (colorArgbParts.size == 1 && colorArgbParts.first().isInt) {
            // Creating color from a single Int
            Color(colorArgbParts.first().intValue ?: 0)
        } else if (colorArgbParts.size == 1 && colorArgbParts.first().isString) {
            // Creating color from a String as hex color
            colorArgbParts.first().stringValue?.toColor()
        } else {
            // Creating color passing RGBA parts
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

fun colorsFromArguments(arguments: List<ModifierDataAdapter.ArgumentData>): List<Color> {
    return argsOrNamedArgs(arguments).mapNotNull {
        colorFromArgument(it)
    }
}

internal fun dpFromArgument(argument: ModifierDataAdapter.ArgumentData): Dp? {
    return if (argument.isDot && argument.listValue.getOrNull(1)?.stringValueWithoutColon == typeUnitDp) {
        val value = argument.listValue.getOrNull(0)
        if (value?.isFloat == true) value.floatValue?.dp
        else if (value?.isInt == true) value.intValue?.dp
        else null
    } else if (argument.type == typeDp) {
        val value = argument.listValue.getOrNull(0)
        if (value?.isFloat == true) value.floatValue?.dp
        else if (value?.isInt == true) value.intValue?.dp
        else null
    } else null
}

internal fun dpSizeFromArgument(argument: ModifierDataAdapter.ArgumentData): DpSize? {
    return if (argument.type == typeDpSize) {
        val args = argsOrNamedArgs(argument.listValue)
        val width = argOrNamedArg(args, argWidth, 0)?.let { dpFromArgument(it) }
        val height = argOrNamedArg(args, argHeight, 1)?.let { dpFromArgument(it) }
        if (width != null && height != null) {
            DpSize(width = width, height = height)
        } else null
    } else null
}

internal fun enterTransitionFromArgument(vararg arguments: ModifierDataAdapter.ArgumentData): EnterTransition? {

    var result: EnterTransition? = null
    for (i in arguments.indices) {
        val currentFunction = arguments[i]
        val animationType = currentFunction.type
        val animationParams = argsOrNamedArgs(currentFunction.listValue)

        val transition = when (animationType) {
            EnterExitTransitionFunctions.expandHorizontally -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val expandFrom = argOrNamedArg(animationParams, argExpandFrom, 1)?.let {
                    horizontalAlignmentFromArgument(it)
                } ?: Alignment.End
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val initialWidth = argOrNamedArg(animationParams, argInitialWidth, 3)?.intValue ?: 0
                expandHorizontally(
                    animationSpec = animateSpec,
                    expandFrom = expandFrom,
                    clip = clip,
                    initialWidth = { initialWidth }
                )
            }

            EnterExitTransitionFunctions.expandIn -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val expandFrom = argOrNamedArg(animationParams, argExpandFrom, 1)?.let {
                    alignmentFromArgument(it)
                } ?: Alignment.BottomEnd
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val initialSize = argOrNamedArg(animationParams, argInitialSize, 3)?.let {
                    intSizeFromArgument(it)
                } ?: IntSize.Zero
                expandIn(
                    animationSpec = animateSpec,
                    expandFrom = expandFrom,
                    clip = clip,
                    initialSize = { initialSize }
                )
            }

            EnterExitTransitionFunctions.expandVertically -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val expandFrom = argOrNamedArg(animationParams, argExpandFrom, 1)?.let {
                    verticalAlignmentFromArgument(it)
                } ?: Alignment.Bottom
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val initialSize = argOrNamedArg(animationParams, argInitialSize, 3)?.intValue ?: 0
                expandVertically(
                    animationSpec = animateSpec,
                    expandFrom = expandFrom,
                    clip = clip,
                    initialHeight = { initialSize }
                )
            }

            EnterExitTransitionFunctions.fadeIn -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg<Float>(it)
                } ?: spring(stiffness = Spring.StiffnessMediumLow)
                val initialAlpha =
                    argOrNamedArg(animationParams, argInitialAlpha, 1)?.floatValue ?: 0f
                fadeIn(
                    animationSpec = animateSpec,
                    initialAlpha = initialAlpha
                )
            }

            EnterExitTransitionFunctions.scaleIn -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg<Float>(it)
                } ?: spring(stiffness = Spring.StiffnessMediumLow)
                val initialScale =
                    argOrNamedArg(animationParams, argInitialScale, 1)?.floatValue ?: 0f
                val transformOrigin = argOrNamedArg(animationParams, argTransformOrigin, 2)?.let {
                    transformOriginFromArgument(it)
                } ?: TransformOrigin.Center
                scaleIn(
                    animationSpec = animateSpec,
                    initialScale = initialScale,
                    transformOrigin = transformOrigin
                )
            }

            EnterExitTransitionFunctions.slideIn -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )

                val initialOffset = argOrNamedArg(animationParams, argInitialOffset, 1)?.let {
                    intOffsetFromArgument(it)
                } ?: IntOffset.Zero
                slideIn(
                    animationSpec = animateSpec,
                    initialOffset = { initialOffset }
                )
            }

            EnterExitTransitionFunctions.slideInHorizontally -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )

                val initialOffset = argOrNamedArg(animationParams, argInitialOffsetX, 1)?.intValue

                slideInHorizontally(
                    animationSpec = animateSpec,
                    initialOffsetX = { initialOffset ?: (-it / 2) }
                )
            }

            EnterExitTransitionFunctions.slideInVertically -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )

                val initialOffset = argOrNamedArg(animationParams, argInitialOffsetX, 1)?.intValue

                slideInVertically(
                    animationSpec = animateSpec,
                    initialOffsetY = { initialOffset ?: (-it / 2) }
                )
            }

            else -> null
        }
        if (transition != null) {
            if (result == null) {
                result = transition
            } else {
                result += transition
            }
        }
    }

    return result
}

internal fun exitTransitionFromArgument(vararg arguments: ModifierDataAdapter.ArgumentData): ExitTransition? {

    var result: ExitTransition? = null
    for (i in arguments.indices) {
        val currentFunction = arguments[i]
        val animationType = currentFunction.type
        val animationParams = argsOrNamedArgs(currentFunction.listValue)

        val transition = when (animationType) {
            EnterExitTransitionFunctions.fadeOut -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg<Float>(it)
                } ?: spring(stiffness = Spring.StiffnessMediumLow)
                val targetAlpha =
                    argOrNamedArg(animationParams, argTargetAlpha, 1)?.floatValue ?: 0f
                fadeOut(
                    animationSpec = animateSpec,
                    targetAlpha = targetAlpha
                )
            }

            EnterExitTransitionFunctions.scaleOut -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg<Float>(it)
                } ?: spring(stiffness = Spring.StiffnessMediumLow)
                val targetScale =
                    argOrNamedArg(animationParams, argTargetScale, 1)?.floatValue ?: 0f
                val transformOrigin = argOrNamedArg(animationParams, argTransformOrigin, 2)?.let {
                    transformOriginFromArgument(it)
                } ?: TransformOrigin.Center
                scaleOut(
                    animationSpec = animateSpec,
                    targetScale = targetScale,
                    transformOrigin = transformOrigin
                )
            }

            EnterExitTransitionFunctions.slideOut -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )
                val targetOffset = argOrNamedArg(animationParams, argTargetOffset, 1)?.let {
                    intOffsetFromArgument(it)
                } ?: IntOffset.Zero
                slideOut(
                    animationSpec = animateSpec,
                    targetOffset = { targetOffset }
                )
            }

            EnterExitTransitionFunctions.slideOutHorizontally -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )
                val targetOffsetX = argOrNamedArg(animationParams, argTargetOffsetX, 1)?.intValue

                slideOutHorizontally(
                    animationSpec = animateSpec,
                    targetOffsetX = { targetOffsetX ?: (-it / 2) }
                )
            }

            EnterExitTransitionFunctions.slideOutVertically -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )

                val targetOffsetY = argOrNamedArg(animationParams, argTargetOffsetY, 1)?.intValue

                slideOutVertically(
                    animationSpec = animateSpec,
                    targetOffsetY = { targetOffsetY ?: (-it / 2) }
                )
            }

            EnterExitTransitionFunctions.shrinkHorizontally -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val shrinkTowards = argOrNamedArg(animationParams, argShrinkTowards, 1)?.let {
                    horizontalAlignmentFromArgument(it)
                } ?: Alignment.End
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val targetWidth = argOrNamedArg(animationParams, argTargetWidth, 3)?.intValue ?: 0
                shrinkHorizontally(
                    animationSpec = animateSpec,
                    shrinkTowards = shrinkTowards,
                    clip = clip,
                    targetWidth = { targetWidth }
                )
            }

            EnterExitTransitionFunctions.shrinkOut -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val shrinkTowards = argOrNamedArg(animationParams, argShrinkTowards, 1)?.let {
                    alignmentFromArgument(it)
                } ?: Alignment.BottomEnd
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val targetSize =
                    argOrNamedArg(animationParams, argTargetScale, 3)?.let {
                        intSizeFromArgument(it)
                    }
                shrinkOut(
                    animationSpec = animateSpec,
                    shrinkTowards = shrinkTowards,
                    clip = clip,
                    targetSize = { targetSize ?: IntSize.Zero }
                )
            }

            EnterExitTransitionFunctions.shrinkVertically -> {
                val animateSpec = argOrNamedArg(animationParams, argAnimationSpec, 0)?.let {
                    finiteAnimationSpecFromArg(it)
                } ?: spring(
                    stiffness = Spring.StiffnessMediumLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
                val shrinkTowards = argOrNamedArg(animationParams, argShrinkTowards, 1)?.let {
                    verticalAlignmentFromArgument(it)
                } ?: Alignment.Bottom
                val clip = argOrNamedArg(animationParams, argClip, 2)?.booleanValue ?: true
                val targetHeight = argOrNamedArg(animationParams, argInitialSize, 3)?.intValue ?: 0
                shrinkVertically(
                    animationSpec = animateSpec,
                    shrinkTowards = shrinkTowards,
                    clip = clip,
                    targetHeight = { targetHeight }
                )
            }

            else -> null
        }
        if (transition != null) {
            if (result == null) {
                result = transition
            } else {
                result += transition
            }
        }
    }

    return result
}

internal fun eventFromArgument(argument: ModifierDataAdapter.ArgumentData): Pair<String, Any?>? {
    return if (argument.type == typeEvent) {
        val (event, args) = Pair(
            argument.listValue.getOrNull(0)?.stringValue,
            argument.listValue.getOrNull(1)?.listValue?.associate {
                it.name to it.value.toString()
            } ?: emptyMap()
        )
        if (event != null) {
            event to args
        } else null
    } else null
}

/*
FiniteAnimationSpec is the interface that all non-infinite AnimationSpecs implement, including:
TweenSpec, SpringSpec, KeyframesSpec, RepeatableSpec, SnapSpec, etc.
 */
@OptIn(ExperimentalAnimationSpecApi::class)
internal fun <T> finiteAnimationSpecFromArg(argument: ModifierDataAdapter.ArgumentData): FiniteAnimationSpec<T>? {
    val defaultDurationMillis = 300
    val argsForFunction = argsOrNamedArgs(argument.listValue)

    return when (argument.type) {
        FiniteAnimationSpecFunctions.tween -> {
            val duration = argOrNamedArg(argsForFunction, argDurationMillis, 0)?.intValue
                ?: defaultDurationMillis
            val delay = argOrNamedArg(argsForFunction, argDelayMillis, 1)?.intValue ?: 0
            val ease = argOrNamedArg(argsForFunction, argEasing, 2)?.let { easeArg ->
                easeArg.listValue.firstOrNull()?.stringValueWithoutColon?.let { easingFromString(it) }
            } ?: FastOutSlowInEasing
            tween(
                durationMillis = duration, delayMillis = delay, easing = ease
            )
        }

        FiniteAnimationSpecFunctions.spring -> {
            val dampingRatio = argOrNamedArg(argsForFunction, argDampingRatio, 0)?.floatValue
                ?: Spring.DampingRatioNoBouncy
            val stiffness = argOrNamedArg(argsForFunction, argStiffness, 1)?.floatValue
                ?: Spring.StiffnessMedium
            spring(
                dampingRatio = dampingRatio,
                stiffness = stiffness,
                // TODO  visibilityThreshold =
            )
        }

        FiniteAnimationSpecFunctions.keyframes -> {
            val duration = argOrNamedArg(argsForFunction, argDurationMillis, 0)?.intValue
                ?: defaultDurationMillis
            val delay = argOrNamedArg(argsForFunction, argDelayMillis, 1)?.intValue ?: 0
            keyframes {
                // TODO KeyframesSpecConfig
                this.durationMillis = duration
                this.delayMillis = delay
            }
        }

        FiniteAnimationSpecFunctions.keyframesWithSpline -> {
            val duration = argOrNamedArg(argsForFunction, argDurationMillis, 0)?.intValue
                ?: defaultDurationMillis
            val delay = argOrNamedArg(argsForFunction, argDelayMillis, 1)?.intValue ?: 0
            keyframesWithSpline {
                // TODO KeyframesWithSplineSpecConfig
                this.durationMillis = duration
                this.delayMillis = delay
            }
        }

        FiniteAnimationSpecFunctions.repeatable -> {
            val iterations =
                argOrNamedArg(argsForFunction, argIterations, 0)?.intValue ?: defaultDurationMillis
            val animation = argOrNamedArg(argsForFunction, argAnimation, 1)?.let {
                finiteAnimationSpecFromArg<T>(it)
            }
            val repeatMode = argOrNamedArg(argsForFunction, argRepeatMode, 2)?.let {
                repeatModeFromArgument(it)
            } ?: RepeatMode.Restart
            val startOffset = argOrNamedArg(argsForFunction, argInitialStartOffset, 3)?.let {
                startOffsetFromArgument(it)
            } ?: StartOffset(0)
            if (animation is DurationBasedAnimationSpec) {
                repeatable(
                    iterations = iterations,
                    animation = animation,
                    repeatMode = repeatMode,
                    initialStartOffset = startOffset,
                )
            } else null
        }

        FiniteAnimationSpecFunctions.snap -> {
            val delay = argOrNamedArg(argsForFunction, argDelayMillis, 1)?.intValue ?: 0
            snap(delayMillis = delay)
        }

        else -> null
    }
}

internal fun floatRangeFromArgument(argument: ModifierDataAdapter.ArgumentData): ClosedFloatingPointRange<Float>? {
    return if (argument.type == typeRange && argument.listValue.size == 2) {
        val start = argument.listValue[0].floatValue
        val end = argument.listValue[1].floatValue
        if (start != null && end != null) start..end else null
    } else null
}

internal fun horizontalAlignmentFromArgument(argument: ModifierDataAdapter.ArgumentData): Alignment.Horizontal? {
    return if (argument.isDot && argument.listValue.getOrNull(0)?.stringValueWithoutColon == typeAlignment) {
        when (argument.listValue.getOrNull(1)?.stringValueWithoutColon) {
            HorizontalAlignmentValues.start -> Alignment.Start
            HorizontalAlignmentValues.centerHorizontally -> Alignment.CenterHorizontally
            HorizontalAlignmentValues.end -> Alignment.End
            else -> null
        }
    } else null
}

internal fun horizontalAlignmentLineFromArgument(argument: ModifierDataAdapter.ArgumentData): HorizontalAlignmentLine? {
    return if (argument.isDot) {
        when (argument.listValue.getOrNull(0)?.stringValueWithoutColon) {
            AlignmentLineValues.firstBaseline -> FirstBaseline
            AlignmentLineValues.lastBaseline -> LastBaseline
            else -> null
        }
    } else null
}

internal fun intOffsetFromArgument(argument: ModifierDataAdapter.ArgumentData): IntOffset? {
    return if (argument.type == typeIntOffset && argument.listValue.size == 2) {
        val x = argument.listValue[0].intValue
        val y = argument.listValue[1].intValue
        if (x != null && y != null) IntOffset(x, y) else null
    } else null
}

internal fun intRangeFromArgument(argument: ModifierDataAdapter.ArgumentData): IntRange? {
    return if (argument.type == typeRange && argument.listValue.size == 2) {
        val start = argument.listValue[0].intValue
        val end = argument.listValue[1].intValue
        if (start != null && end != null) start..end else null
    } else null
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

internal fun intSizeFromArgument(argument: ModifierDataAdapter.ArgumentData): IntSize? {
    return if (argument.type == typeIntSize) {
        val w = argOrNamedArg(argument.listValue, argWidth, 0)?.intValue
        val h = argOrNamedArg(argument.listValue, argHeight, 1)?.intValue
        if (w != null && h != null) {
            return IntSize(w, h)
        } else null
    } else null
}

internal fun offsetFromArgument(argument: ModifierDataAdapter.ArgumentData): Offset? {
    return if (argument.type == typeOffset) {
        argument.listValue.mapNotNull {
            if (it.isInt) it.intValue?.toFloat() else it.floatValue
        }.let {
            if (it.size == 2) Offset(it[0], it[1]) else null
        }

    } else if (argument.isDot) {
        when (argument.listValue.getOrNull(1)?.stringValueWithoutColon) {
            OffsetValues.zero -> Offset.Zero
            OffsetValues.infinite -> Offset.Infinite
            OffsetValues.unspecified -> Offset.Unspecified
            else -> null
        }
    } else null
}

internal fun repeatModeFromArgument(argument: ModifierDataAdapter.ArgumentData): RepeatMode? {
    return if (argument.isDot) {
        when (argument.listValue.getOrNull(1)?.stringValueWithoutColon) {
            RepeatModeValues.restart -> RepeatMode.Restart
            RepeatModeValues.reverse -> RepeatMode.Reverse
            else -> null
        }
    } else null
}

internal fun roleFromArgument(argument: ModifierDataAdapter.ArgumentData): Role? {
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

internal fun shapeFromArgument(argument: ModifierDataAdapter.ArgumentData): Shape? {
    val clazz = if (argument.isDot)
        argument.listValue.getOrNull(0)?.stringValueWithoutColon ?: ""
    else if (argument.isAtom)
        argument.stringValueWithoutColon
    else
        argument.type

    return when (clazz) {
        ShapeValues.circle -> CircleShape
        ShapeValues.rectangle -> RectangleShape
        ShapeValues.roundedCorner -> {
            val cornerSizeParts = argsOrNamedArgs(argument.listValue)
            return if (cornerSizeParts.size == 1) {
                cornerSizeParts.firstOrNull()?.let {
                    dpFromArgument(it)
                }?.let {
                    RoundedCornerShape(it)
                }
            } else {
                RoundedCornerShape(
                    argOrNamedArg(cornerSizeParts, argTopStart, 0)?.let { dpFromArgument(it) }
                        ?: 0.dp,
                    argOrNamedArg(cornerSizeParts, argTopEnd, 1)?.let { dpFromArgument(it) }
                        ?: 0.dp,
                    argOrNamedArg(cornerSizeParts, argBottomEnd, 2)?.let { dpFromArgument(it) }
                        ?: 0.dp,
                    argOrNamedArg(cornerSizeParts, argBottomStart, 3)?.let { dpFromArgument(it) }
                        ?: 0.dp,
                )
            }
        }

        else -> null
    }
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

internal fun startOffsetFromArgument(argument: ModifierDataAdapter.ArgumentData): StartOffset? {
    return if (argument.type == typeStartOffset) {
        val args = argsOrNamedArgs(argument.listValue)
        val value = argOrNamedArg(args, argValue, 0)?.intValue ?: 0
        StartOffset(value)
    } else null
}

internal fun textUnitFromArgument(argument: ModifierDataAdapter.ArgumentData): TextUnit? {
    return if (argument.isDot) {
        val value = argument.listValue.getOrNull(0)
        val type = argument.listValue.getOrNull(1)?.stringValueWithoutColon
        if (type == typeUnitSp) {
            if (value?.isFloat == true) value.floatValue?.sp
            else if (value?.isInt == true) value.intValue?.sp
            else null
        } else if (type == typeUnitEm) {
            if (value?.isFloat == true) value.floatValue?.em
            else if (value?.isInt == true) value.intValue?.em
            else null
        } else null
    } else if (argument.type == typeTextUnit) {
        val value = argument.listValue.getOrNull(0)?.floatValue
        val argsToCreateTextUnit = argument.listValue.getOrNull(1)?.listValue
        val textUnitClass = argsToCreateTextUnit?.getOrNull(0)?.stringValueWithoutColon
        val textUnitType = argsToCreateTextUnit?.getOrNull(1)?.stringValueWithoutColon
        if (textUnitClass == typeTextUnitType && textUnitType == typeTextUnitSp && value != null) {
            TextUnit(value, TextUnitType.Sp)
        } else if (textUnitClass == typeTextUnitType && textUnitType == typeTextUnitEm && value != null) {
            TextUnit(value, TextUnitType.Em)
        } else null
    } else null
}

fun toggleableStateFromArgument(argument: ModifierDataAdapter.ArgumentData): ToggleableState? {
    return if (argument.type == typeToggleableState) {
        argument.listValue.firstOrNull()?.booleanValue?.let {
            ToggleableState(it)
        }
    } else if (argument.isDot && argument.listValue.firstOrNull()?.stringValueWithoutColon == typeToggleableState) {
        argument.listValue.getOrNull(1)?.stringValueWithoutColon?.let {
            ToggleableState.valueOf(it)
        }
    } else null
}

internal fun transformOriginFromArgument(argument: ModifierDataAdapter.ArgumentData): TransformOrigin? {
    val args = argsOrNamedArgs(argument.listValue)
    return if (argument.isDot) {
        val clazz = args.getOrNull(0)?.stringValueWithoutColon
        val value = args.getOrNull(1)?.stringValueWithoutColon
        if (clazz == typeTransformOrigin && value == TransformOriginValues.center) {
            TransformOrigin.Center
        } else null
    } else if (argument.type == typeTransformOrigin) {
        TransformOrigin(
            pivotFractionX = argOrNamedArg(args, attrPivotFractionX, 0)?.floatValue ?: 0f,
            pivotFractionY = argOrNamedArg(args, attrPivotFractionY, 1)?.floatValue ?: 0f,
        )
    } else null
}

internal fun verticalAlignmentFromArgument(argument: ModifierDataAdapter.ArgumentData): Alignment.Vertical? {
    return if (argument.isDot && argument.listValue.getOrNull(0)?.stringValueWithoutColon == typeAlignment) {
        when (argument.listValue.getOrNull(1)?.stringValueWithoutColon) {
            VerticalAlignmentValues.top -> Alignment.Top
            VerticalAlignmentValues.centerVertically -> Alignment.CenterVertically
            VerticalAlignmentValues.bottom -> Alignment.Bottom
            else -> null
        }
    } else null
}

internal fun verticalAlignmentLineFromArgument(argument: ModifierDataAdapter.ArgumentData): VerticalAlignmentLine? {
    return if (argument.isDot) {
        when (argument.listValue.getOrNull(0)?.stringValueWithoutColon) {
            AlignmentLineValues.firstBaseline -> VerticalAlignmentLine(::min)
            AlignmentLineValues.lastBaseline -> VerticalAlignmentLine(::max)
            else -> null
        }
    } else null
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

        val leftDp = argOrNamedArg(args, argLeft, 0)?.let { dpFromArgument(it) }
        val topDp = argOrNamedArg(args, argTop, 1)?.let { dpFromArgument(it) }
        val rightDp = argOrNamedArg(args, argRight, 2)?.let { dpFromArgument(it) }
        val bottomDp = argOrNamedArg(args, argBottom, 3)?.let { dpFromArgument(it) }
        return WindowInsets(leftDp ?: 0.dp, topDp ?: 0.dp, rightDp ?: 0.dp, bottomDp ?: 0.dp)
    } else return null
}
package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.constants.ModifierArgs.argAfter
import org.phoenixframework.liveview.constants.ModifierArgs.argAlignmentLine
import org.phoenixframework.liveview.constants.ModifierArgs.argBefore
import org.phoenixframework.liveview.constants.ModifierArgs.argBottom
import org.phoenixframework.liveview.constants.ModifierArgs.argEnd
import org.phoenixframework.liveview.constants.ModifierArgs.argHorizontal
import org.phoenixframework.liveview.constants.ModifierArgs.argInsets
import org.phoenixframework.liveview.constants.ModifierArgs.argLeft
import org.phoenixframework.liveview.constants.ModifierArgs.argRight
import org.phoenixframework.liveview.constants.ModifierArgs.argStart
import org.phoenixframework.liveview.constants.ModifierArgs.argTop
import org.phoenixframework.liveview.constants.ModifierArgs.argVertical
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.absolutePaddingFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)
    val left = argOrNamedArg(params, argLeft, 0)?.let { dpFromArgument(it) }
    val top = argOrNamedArg(params, argTop, 1)?.let { dpFromArgument(it) }
    val right = argOrNamedArg(params, argRight, 2)?.let { dpFromArgument(it) }
    val bottom = argOrNamedArg(params, argBottom, 3)?.let { dpFromArgument(it) }
    return if (left != null || top != null || right != null || bottom != null) {
        this.then(
            Modifier.absolutePadding(
                left = left ?: 0.dp,
                top = top ?: 0.dp,
                right = right ?: 0.dp,
                bottom = bottom ?: 0.dp,
            )
        )
    } else this
}

fun Modifier.paddingFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    // Supporting both named and no-named params
    val params = argsOrNamedArgs(arguments)
    val paddingModifier = when (params.size) {
        1 -> {
            val paddingValue = params.firstOrNull()
            val dpValue = paddingValue?.let { dpFromArgument(it) }
            dpValue?.let {
                when (paddingValue.name) {
                    argHorizontal -> Modifier.padding(horizontal = it)
                    argVertical -> Modifier.padding(vertical = it)
                    argStart -> Modifier.padding(start = it)
                    argEnd -> Modifier.padding(end = it)
                    argTop -> Modifier.padding(top = it)
                    argBottom -> Modifier.padding(bottom = it)
                    else -> Modifier.padding(all = it)
                }
            }
        }

        2 -> {
            val paddingValue = params.firstOrNull()
            when (paddingValue?.name) {
                argHorizontal, argVertical, null -> {
                    val horizontalPadding = argOrNamedArg(params, argHorizontal, 0)
                    val horizontalPaddingDp = horizontalPadding?.let { dpFromArgument(it) }
                    val verticalPadding = argOrNamedArg(params, argVertical, 1)
                    val verticalPaddingDp = verticalPadding?.let { dpFromArgument(it) }

                    if (horizontalPaddingDp != null && verticalPaddingDp != null)
                        Modifier.padding(
                            vertical = verticalPaddingDp,
                            horizontal = horizontalPaddingDp,
                        )
                    else null
                }

                else -> {
                    Modifier.padding(
                        start = params.find { it.name == argStart }?.let { dpFromArgument(it) }
                            ?: 0.dp,
                        top = params.find { it.name == argTop }?.let { dpFromArgument(it) } ?: 0.dp,
                        end = params.find { it.name == argEnd }?.let { dpFromArgument(it) } ?: 0.dp,
                        bottom = params.find { it.name == argBottom }?.let { dpFromArgument(it) }
                            ?: 0.dp,
                    )
                }
            }
        }

        3, 4 -> {
            val paddingValues = if (params.any { it.name == null }) {
                params.map { dpFromArgument(it) ?: 0.dp }
            } else {
                listOf(
                    params.find { it.name == argStart }?.let { dpFromArgument(it) } ?: 0.dp,
                    params.find { it.name == argTop }?.let { dpFromArgument(it) } ?: 0.dp,
                    params.find { it.name == argEnd }?.let { dpFromArgument(it) } ?: 0.dp,
                    params.find { it.name == argBottom }?.let { dpFromArgument(it) } ?: 0.dp
                )
            }
            Modifier.padding(
                paddingValues[0],
                paddingValues[1],
                paddingValues[2],
                (paddingValues.getOrNull(3) ?: 0.dp)
            )
        }

        else -> null
    }
    return paddingModifier?.let { this.then(it) } ?: this
}

fun Modifier.paddingFromFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    // Supporting both named and no-named params
    val params = argsOrNamedArgs(arguments)
    val alignArg = argOrNamedArg(params, argAlignmentLine, 0)
    val topArg = argOrNamedArg(params, argBefore, 1)
    val bottomArg = argOrNamedArg(params, argAfter, 2)

    val alignmentLine = alignArg?.let { horizontalAlignmentLineFromArgument(it) } ?: return this

    val topDp = topArg?.let { dpFromArgument(it) }
    val bottomDp = bottomArg?.let { dpFromArgument(it) }
    if (topDp != null || bottomDp != null) {
        return this.then(
            Modifier.paddingFrom(alignmentLine, topDp ?: Dp.Unspecified, bottomDp ?: Dp.Unspecified)
        )
    }
    val topSp = topArg?.let { textUnitFromArgument(it) }
    val bottomSp = bottomArg?.let { textUnitFromArgument(it) }
    if (topSp != null || bottomSp != null) {
        return this.then(
            Modifier.paddingFrom(
                alignmentLine,
                topSp ?: TextUnit.Unspecified,
                bottomSp ?: TextUnit.Unspecified
            )
        )
    }
    return this
}

fun Modifier.paddingFromBaselineFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    // Supporting both named and no-named params
    val params = argsOrNamedArgs(arguments)
    val topArg = argOrNamedArg(params, argTop, 0)
    val bottomArg = argOrNamedArg(params, argBottom, 1)

    val topDp = topArg?.let { dpFromArgument(it) }
    val bottomDp = bottomArg?.let { dpFromArgument(it) }
    if (topDp != null || bottomDp != null) {
        return this.then(
            Modifier.paddingFromBaseline(topDp ?: Dp.Unspecified, bottomDp ?: Dp.Unspecified)
        )
    }
    val topSp = topArg?.let { textUnitFromArgument(it) }
    val bottomSp = bottomArg?.let { textUnitFromArgument(it) }
    if (topSp != null || bottomSp != null) {
        return this.then(
            Modifier.paddingFromBaseline(
                topSp ?: TextUnit.Unspecified,
                bottomSp ?: TextUnit.Unspecified
            )
        )
    }
    return this
}

fun Modifier.windowInsetsPaddingFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    return argOrNamedArg(arguments, argInsets, 0)?.let { arg ->
        windowInsetsFromArgument(arg)?.let {
            this.then(Modifier.windowInsetsPadding(it))
        }
    } ?: this
}
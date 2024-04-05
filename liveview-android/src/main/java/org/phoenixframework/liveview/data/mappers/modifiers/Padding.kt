package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

private const val ARG_HORIZONTAL = "horizontal"
private const val ARG_VERTICAL = "vertical"
private const val ARG_START = "start"
private const val ARG_END = "end"
private const val ARG_TOP = "top"
private const val ARG_BOTTOM = "bottom"
private const val ARG_BEFORE = "before"
private const val ARG_AFTER = "after"
private const val ARG_ALIGNMENT_LINE = "alignmentLine"
private const val ARG_INSETS = "insets"

fun Modifier.paddingFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    // Supporting both named and no-named params
    val params = argsOrNamedArgs(arguments)
    val paddingModifier = when (params.size) {
        1 -> {
            val paddingValue = params.firstOrNull()
            val dpValue = paddingValue?.intValue
            dpValue?.let {
                when (paddingValue.name) {
                    ARG_HORIZONTAL -> Modifier.padding(horizontal = it.dp)
                    ARG_VERTICAL -> Modifier.padding(vertical = it.dp)
                    ARG_START -> Modifier.padding(start = it.dp)
                    ARG_END -> Modifier.padding(end = it.dp)
                    ARG_TOP -> Modifier.padding(top = it.dp)
                    ARG_BOTTOM -> Modifier.padding(bottom = it.dp)
                    else -> Modifier.padding(all = it.dp)
                }
            }
        }

        2 -> {
            val paddingValue = params.firstOrNull()
            when (paddingValue?.name) {
                ARG_HORIZONTAL, ARG_VERTICAL, null -> {
                    val hp =
                        params.find { it.name == ARG_HORIZONTAL }?.intValue
                            ?: params.getOrNull(0)?.intValue
                    val vp =
                        params.find { it.name == ARG_VERTICAL }?.intValue
                            ?: params.getOrNull(1)?.intValue

                    if (hp != null && vp != null)
                        Modifier.padding(
                            vertical = vp.dp,
                            horizontal = hp.dp
                        )
                    else null
                }

                else -> {
                    Modifier.padding(
                        start = (params.find { it.name == ARG_START }?.intValue ?: 0).dp,
                        top = (params.find { it.name == ARG_TOP }?.intValue ?: 0).dp,
                        end = (params.find { it.name == ARG_END }?.intValue ?: 0).dp,
                        bottom = (params.find { it.name == ARG_BOTTOM }?.intValue ?: 0).dp,
                    )
                }
            }
        }

        3, 4 -> {
            val paddingValues = if (params.any { it.name == null }) {
                params.map { it.intValue ?: 0 }
            } else {
                listOf(
                    params.find { it.name == ARG_START }?.intValue ?: 0,
                    params.find { it.name == ARG_TOP }?.intValue ?: 0,
                    params.find { it.name == ARG_END }?.intValue ?: 0,
                    params.find { it.name == ARG_BOTTOM }?.intValue ?: 0
                )
            }
            Modifier.padding(
                paddingValues[0].dp,
                paddingValues[1].dp,
                paddingValues[2].dp,
                (paddingValues.getOrNull(3) ?: 0).dp
            )
        }

        else -> null
    }
    return paddingModifier?.let { this.then(it) } ?: this
}

fun Modifier.paddingFromFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    // Supporting both named and no-named params
    val params = argsOrNamedArgs(arguments)
    val alignArg = argOrNamedArg(params, ARG_ALIGNMENT_LINE, 0)
    val topArg = argOrNamedArg(params, ARG_BEFORE, 1)
    val bottomArg = argOrNamedArg(params, ARG_AFTER, 2)

    val alignmentLine = alignArg?.let { alignmentLineFromStyle(it) } ?: return this

    val topDp = topArg?.let { dpFromStyle(it) }
    val bottomDp = bottomArg?.let { dpFromStyle(it) }
    if (topDp != null || bottomDp != null) {
        return this.then(
            Modifier.paddingFrom(alignmentLine, topDp ?: Dp.Unspecified, bottomDp ?: Dp.Unspecified)
        )
    }
    val topSp = topArg?.let { spFromStyle(it) }
    val bottomSp = bottomArg?.let { spFromStyle(it) }
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
    val topArg = argOrNamedArg(params, ARG_TOP, 0)
    val bottomArg = argOrNamedArg(params, ARG_BOTTOM, 1)

    val topDp = topArg?.let { dpFromStyle(it) }
    val bottomDp = bottomArg?.let { dpFromStyle(it) }
    if (topDp != null || bottomDp != null) {
        return this.then(
            Modifier.paddingFromBaseline(topDp ?: Dp.Unspecified, bottomDp ?: Dp.Unspecified)
        )
    }
    val topSp = topArg?.let { spFromStyle(it) }
    val bottomSp = bottomArg?.let { spFromStyle(it) }
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
    return argOrNamedArg(arguments, ARG_INSETS, 0)?.let { arg ->
        windowInsetsFromArgument(arg)?.let {
            this.then(Modifier.windowInsetsPadding(it))
        }
    } ?: this
}
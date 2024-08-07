package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.constants.ModifierArgs.argAlignment
import org.phoenixframework.liveview.constants.ModifierArgs.argAlignmentLine
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.alignFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val alignmentParam = argsOrNamedArgs(arguments)
    if (alignmentParam.isEmpty()) return this

    val arg = argOrNamedArg(alignmentParam, argAlignment, 0) ?: return this

    return when (scope) {
        is BoxScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(alignmentFromArgument(arg) ?: Alignment.TopStart)
            )
        }

        is ColumnScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(horizontalAlignmentFromArgument(arg) ?: Alignment.Start)
            )
        }

        is RowScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(verticalAlignmentFromArgument(arg) ?: Alignment.Top)
            )
        }

        else -> this
    }
}

fun Modifier.alignByFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val alignmentParam = argsOrNamedArgs(arguments)

    val alignmentValue = argOrNamedArg(alignmentParam, argAlignmentLine, 0)

    return when (scope) {
        is ColumnScope -> scope.run {
            alignmentValue?.let {
                verticalAlignmentLineFromArgument(it)

            }?.let {
                this@alignByFromStyle.then(
                    Modifier.alignBy(it)
                )
            } ?: this@alignByFromStyle
        }

        is RowScope -> scope.run {
            alignmentValue?.let {
                horizontalAlignmentLineFromArgument(it)

            }?.let {
                this@alignByFromStyle.then(
                    Modifier.alignBy(it)
                )
            } ?: this@alignByFromStyle
        }

        else -> this
    }
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.alignByBaselineFromStyle(scope: Any?): Modifier {
    return when (scope) {
        is FlowRowScope -> {
            scope.run {
                this@alignByBaselineFromStyle.then(
                    Modifier.alignByBaseline()
                )
            }
        }

        is RowScope -> scope.run {
            this@alignByBaselineFromStyle.then(
                Modifier.alignByBaseline()
            )
        }

        else -> this
    }
}

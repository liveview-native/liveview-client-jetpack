package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.data.dto.alignmentFromString
import org.phoenixframework.liveview.data.dto.horizontalAlignmentFromString
import org.phoenixframework.liveview.data.dto.verticalAlignmentFromString

fun Modifier.alignFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val alignmentParam = argsOrNamedArgs(arguments).firstOrNull()
    if (alignmentParam == null || !alignmentParam.isDot) {
        return this
    }

    val alignmentValue = singleArgumentObjectValue("alignment", alignmentParam)?.second?.toString()
        ?: return this

    return when (scope) {
        is BoxScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(alignmentFromString(alignmentValue, Alignment.TopStart))
            )
        }

        is ColumnScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(horizontalAlignmentFromString(alignmentValue))
            )
        }

        is RowScope -> scope.run {
            this@alignFromStyle.then(
                Modifier.align(verticalAlignmentFromString(alignmentValue))
            )
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

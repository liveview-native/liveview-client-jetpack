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
    val alignmentParam = arguments.firstOrNull()
    // We are not creating an Alignment object. We're just using the existing ones.
    // So the type must be ".".
    if (alignmentParam?.isDot == false) {
        return this
    }
    val paramsToCreateTheAlignObject = alignmentParam?.listValue
    val alignmentClass = paramsToCreateTheAlignObject?.getOrNull(0)?.stringValueWithoutColon
    val alignmentValue = paramsToCreateTheAlignObject?.getOrNull(1)?.stringValueWithoutColon ?: ""

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

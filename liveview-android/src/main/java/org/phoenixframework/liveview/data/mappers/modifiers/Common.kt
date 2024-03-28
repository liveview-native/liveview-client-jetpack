package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier

internal fun Modifier.singleArgumentFloatModifier(
    argumentName: String,
    arguments: List<ModifierDataAdapter.ArgumentData>,
    builder: (Float) -> Modifier
): Modifier {
    val argsOrNamedArgs = argsOrNamedArgs(arguments).firstOrNull()
    return if (argsOrNamedArgs == null) {
        this
    } else {
        singleArgumentFloatValue(argumentName, argsOrNamedArgs)?.let {
            this.then(builder(it))
        } ?: this
    }
}
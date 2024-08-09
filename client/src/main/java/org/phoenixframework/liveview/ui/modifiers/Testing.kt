package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.phoenixframework.liveview.constants.ModifierArgs.argTag
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.testTagFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val tesTag = argOrNamedArg(args, argTag, 0)?.stringValue
    return tesTag?.let { this.then(Modifier.testTag(tag = it)) } ?: this
}
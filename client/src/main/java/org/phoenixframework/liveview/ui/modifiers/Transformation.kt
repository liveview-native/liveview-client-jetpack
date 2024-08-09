package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import org.phoenixframework.liveview.constants.ModifierArgs.argDegrees
import org.phoenixframework.liveview.constants.ModifierArgs.argScale
import org.phoenixframework.liveview.constants.ModifierArgs.argScaleX
import org.phoenixframework.liveview.constants.ModifierArgs.argScaleY
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.rotateFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val rotate = argOrNamedArg(args, argDegrees, 0)?.floatValue
    return rotate?.let { this.then(Modifier.rotate(degrees = it)) } ?: this
}

fun Modifier.scaleFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    return if (args.size == 1) {
        val scale = argOrNamedArg(args, argScale, 0)?.floatValue
        scale?.let { this.then(Modifier.scale(scale = it)) } ?: this
    } else if (args.size == 2) {
        val scaleX = argOrNamedArg(args, argScaleX, 0)?.floatValue
        val scaleY = argOrNamedArg(args, argScaleY, 1)?.floatValue
        if (scaleX != null && scaleY != null) {
            this.then(Modifier.scale(scaleX = scaleX, scaleY = scaleY))
        } else this
    } else this
}
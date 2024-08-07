package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.phoenixframework.liveview.constants.ModifierArgs.argAlpha
import org.phoenixframework.liveview.constants.ModifierArgs.argAmbientColor
import org.phoenixframework.liveview.constants.ModifierArgs.argClip
import org.phoenixframework.liveview.constants.ModifierArgs.argElevation
import org.phoenixframework.liveview.constants.ModifierArgs.argShape
import org.phoenixframework.liveview.constants.ModifierArgs.argSpotColor
import org.phoenixframework.liveview.constants.ModifierArgs.argZIndex
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter

fun Modifier.alphaFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    return argsOrNamedArgs(arguments).let {
        argOrNamedArg(it, argAlpha, 0)?.floatValue
    }?.let { Modifier.alpha(it) } ?: this
}

fun Modifier.clipFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val argument = argsOrNamedArgs(arguments).firstOrNull()
    return argument?.let { shapeFromArgument(it) }?.let { this.then(Modifier.clip(it)) } ?: this
}

fun Modifier.shadowFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)

    val elevation = argOrNamedArg(args, argElevation, 0)?.intValue
    val shape = argOrNamedArg(args, argShape, 1)?.let {
        shapeFromArgument(it)
    }
    val clip = argOrNamedArg(args, argClip, 2)?.booleanValue
    val ambientColor = argOrNamedArg(args, argAmbientColor, 3)?.let {
        colorFromArgument(it)
    }
    val spotColor = argOrNamedArg(args, argSpotColor, 4)?.let {
        colorFromArgument(it)
    }
    return elevation?.let {
        this.then(
            Modifier.shadow(
                elevation = it.dp,
                shape = shape ?: RectangleShape,
                clip = clip ?: (it > 0),
                ambientColor = ambientColor ?: DefaultShadowColor,
                spotColor = spotColor ?: DefaultShadowColor,
            )
        )
    } ?: this
}

fun Modifier.zIndexFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    return argsOrNamedArgs(arguments).let {
        argOrNamedArg(it, argZIndex, 0)?.floatValue
    }?.let { this.then(Modifier.zIndex(it)) } ?: this
}
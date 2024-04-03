package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

fun Modifier.alphaFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    return singleArgumentFloatModifier("alpha", arguments) {
        Modifier.alpha(it)
    }
}

fun Modifier.clipFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val argument =
        if (arguments.firstOrNull()?.isList == true)
            arguments.first().listValue.firstOrNull()
        else
            arguments.firstOrNull()
    return argument?.let { shapeFromStyle(it) }?.let { this.then(Modifier.clip(it)) } ?: this
}

fun Modifier.shadowFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val elevation = (args.find { it.name == "elevation" } ?: args.getOrNull(0))?.intValue
    val shape = (args.find { it.name == "shape" } ?: args.getOrNull(1))?.let {
        shapeFromStyle(it)
    }
    val clip = (args.find { it.name == "clip" } ?: args.getOrNull(2))?.booleanValue
    val ambientColor = (args.find { it.name == "ambientColor" } ?: args.getOrNull(3))?.let {
        colorFromArgument(it)
    }
    val spotColor = (args.find { it.name == "spotColor" } ?: args.getOrNull(4))?.let {
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
    return singleArgumentFloatModifier("zIndex", arguments) {
        Modifier.zIndex(it)
    }
}
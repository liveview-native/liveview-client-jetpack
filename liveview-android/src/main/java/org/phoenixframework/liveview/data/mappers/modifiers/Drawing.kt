package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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

fun Modifier.zIndexFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    return singleArgumentFloatModifier("zIndex", arguments) {
        Modifier.zIndex(it)
    }
}
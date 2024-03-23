package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.aspectRatioFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)
    val ratio = params.find { it.isNumber }?.let {
        singleArgumentFloatValue("ratio", it)
    }
    val matchConstraints = params.find { it.isBoolean }?.let {
        singleArgumentBooleanValue("matchHeightConstraintsFirst", it)
    }
    return ratio?.let {
        this.then(Modifier.aspectRatio(it, matchConstraints ?: false))
    } ?: this
}

fun Modifier.fillMaxHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val fraction = argsOrNamedArgs(arguments).firstOrNull()?.let {
        singleArgumentFloatValue("fraction", it)
    } ?: 1f
    return this.then(Modifier.fillMaxHeight(fraction))
}

fun Modifier.fillMaxWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val fraction = argsOrNamedArgs(arguments).firstOrNull()?.let {
        singleArgumentFloatValue("fraction", it)
    } ?: 1f
    return this.then(Modifier.fillMaxWidth(fraction))
}

fun Modifier.heightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    if (arguments.isNotEmpty()) {
        argsOrNamedArgs(arguments).firstOrNull()?.let { arg ->
            val intHeight = singleArgumentIntValue("height", arg)
            if (intHeight != null) {
                return this.then(Modifier.height(intHeight.dp))
            }
            if (arg.name == "intrinsicSize" || arg.isDot) {
                return intrinsicSizeFromArgument(arg)?.let {
                    this.then(Modifier.height(intrinsicSize = it))
                } ?: this
            }
        }
    }
    return this
}

fun Modifier.sizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)

    return when (params.size) {
        1 -> {
            singleArgumentIntValue("size", params.first())?.let {
                this.then(Modifier.size(it.dp))
            } ?: this
        }

        2 -> {
            val width = params.find { it.name == "width" }?.intValue ?: params[0].intValue
            val height = params.find { it.name == "height" }?.intValue ?: params[1].intValue
            if (width != null && height != null)
                this.then(Modifier.size(width.dp, height.dp))
            else
                this
        }

        else -> this
    }
}

fun Modifier.widthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    if (arguments.isNotEmpty()) {
        argsOrNamedArgs(arguments).firstOrNull()?.let { arg ->
            val intWidth = singleArgumentIntValue("width", arg)
            if (intWidth != null) {
                return this.then(Modifier.width(intWidth.dp))
            }
            if (arg.name == "intrinsicSize" || arg.isDot) {
                return intrinsicSizeFromArgument(arg)?.let {
                    this.then(Modifier.width(intrinsicSize = it))
                } ?: this
            }
        }
    }
    return this
}
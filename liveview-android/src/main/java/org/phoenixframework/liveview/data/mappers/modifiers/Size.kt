package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.aspectRatioFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val params = if (modifierData.first().isList) modifierData.first().listValue else modifierData
    val ratio = params.find { it.isNumber }
    val matchConstraints = params.find { it.isBoolean }?.booleanValue
    if (ratio != null) {
        return this.then(Modifier.aspectRatio((ratio.value as Float), matchConstraints ?: false))
    }
    return this
}

fun Modifier.fillMaxHeightFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val fraction =
        if (modifierData.isNotEmpty()) modifierData.first().floatValue ?: 1f else 1f
    return this.then(Modifier.fillMaxHeight(fraction))
}

fun Modifier.fillMaxWidthFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val fraction =
        if (modifierData.isNotEmpty()) modifierData.first().floatValue ?: 1f else 1f
    return this.then(Modifier.fillMaxWidth(fraction))
}

fun Modifier.heightFromStyle(argListContext: List<ModifierDataWrapper.ArgumentData>): Modifier {
    if (argListContext.isNotEmpty()) {
        val params =
            if (argListContext.first().isList) argListContext.first().listValue else argListContext
        val arg = params.first()
        if ((arg.name == "height" && arg.isInt) || (arg.isInt && params.size == 1)) {
            val heightInt = (arg.value as? Int) ?: 0
            return this.then(Modifier.height(height = heightInt.dp))
        } else if (arg.name == "intrinsicSize") {
            val intrinsicSize = IntrinsicSize.valueOf(arg.value.toString())
            return this.then(Modifier.height(intrinsicSize = intrinsicSize))
        }
    }
    return this
}

fun Modifier.sizeFromStyle(argListContext: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val params =
        if (argListContext.first().isList) argListContext.first().listValue else argListContext

    return when (params.size) {
        1 -> {
            this.then(Modifier.size((params.first().intValue ?: 0).dp))
        }

        2 -> {
            val width = params.find { it.name == "width" }?.intValue ?: params[0].intValue ?: 0
            val height = params.find { it.name == "height" }?.intValue ?: params[1].intValue ?: 0
            this.then(Modifier.size(width.dp, height.dp))
        }

        else -> this
    }
}

fun Modifier.widthFromStyle(argListContext: List<ModifierDataWrapper.ArgumentData>): Modifier {
    if (argListContext.isNotEmpty()) {
        val params =
            if (argListContext.first().isList) argListContext.first().listValue else argListContext
        val arg = params.first()
        if ((arg.name == "width" && arg.isInt) || (arg.isInt && params.size == 1)) {
            val heightInt = (arg.value as? Int) ?: 0
            return this.then(Modifier.width(heightInt.dp))
        } else if (arg.name == "intrinsicSize" || arg.isDot) {
            val intrinsicSize = IntrinsicSize.valueOf(arg.value.toString())
            return this.then(Modifier.width(intrinsicSize = intrinsicSize))
        }
    }
    return this
}
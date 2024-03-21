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

fun Modifier.aspectRatioFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params =
        if (arguments.firstOrNull()?.isList == true) arguments.firstOrNull()?.listValue else arguments
    val ratio = params?.find { it.isNumber }
    val matchConstraints = params?.find { it.isBoolean }?.booleanValue
    if (ratio != null) {
        return this.then(Modifier.aspectRatio((ratio.value as Float), matchConstraints ?: false))
    }
    return this
}

fun Modifier.fillMaxHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val fraction =
        if (arguments.isNotEmpty()) arguments.firstOrNull()?.floatValue ?: 1f else 1f
    return this.then(Modifier.fillMaxHeight(fraction))
}

fun Modifier.fillMaxWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val fraction =
        if (arguments.isNotEmpty()) arguments.first().floatValue ?: 1f else 1f
    return this.then(Modifier.fillMaxWidth(fraction))
}

fun Modifier.heightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    if (arguments.isNotEmpty()) {
        val params =
            if (arguments.firstOrNull()?.isList == true) arguments.first().listValue else arguments
        val arg = params.firstOrNull()
        if ((arg?.name == "height" && arg.isInt) || (arg?.isInt == true && params.size == 1)) {
            val heightInt = (arg.value as? Int) ?: 0
            return this.then(Modifier.height(height = heightInt.dp))
        } else if (arg?.name == "intrinsicSize") {
            val intrinsicSize = IntrinsicSize.valueOf(arg.value.toString())
            return this.then(Modifier.height(intrinsicSize = intrinsicSize))
        }
    }
    return this
}

fun Modifier.sizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params =
        if (arguments.firstOrNull()?.isList == true) arguments.first().listValue else arguments

    return when (params.size) {
        1 -> {
            params.firstOrNull()?.intValue?.let { this.then(Modifier.size(it.dp)) } ?: this
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
        val params =
            if (arguments.first().isList) arguments.first().listValue else arguments
        val arg = params.firstOrNull()
        if ((arg?.name == "width" && arg.isInt) || (arg?.isInt == true && params.size == 1)) {
            return (arg.value as? Int)?.let { this.then(Modifier.width(it.dp)) } ?: this
        } else if (arg?.name == "intrinsicSize" || arg?.isDot == true) {
            return try {
                IntrinsicSize.valueOf(arg.value.toString())
            } catch (_: Exception) {
                null
            }?.let {
                this.then(Modifier.width(intrinsicSize = it))
            } ?: this
        }
    }
    return this
}
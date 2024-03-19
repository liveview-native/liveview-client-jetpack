package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape

fun Modifier.clipFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val argument =
        if (modifierData.first().isList) modifierData.first().listValue.first() else modifierData.first()
    return this.then(
        Modifier.clip(
            shapeFromStyle(argument) ?: RectangleShape
        )
    )
}
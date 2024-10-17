package org.phoenixframework.liveview.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.constants.ShapeValues
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

internal fun shapeFromString(
    shape: String,
    default: CornerBasedShape = RoundedCornerShape(0.dp)
): CornerBasedShape {
    return LiveViewJetpack.getThemeHolder().themeShapeFromString(shape)
        ?: when {
            shape.isNotEmptyAndIsDigitsOnly() -> RoundedCornerShape(shape.toInt().dp)
            shape == ShapeValues.circle -> CircleShape
            shape == ShapeValues.rectangle -> RoundedCornerShape(0.dp)
            shape.startsWith(ShapeValues.roundedCorner) -> {
                val values =
                    shape.substring(ShapeValues.roundedCorner.length + 1, shape.lastIndex)
                        .split(',')
                        .map { it.trim().toIntOrNull() ?: 0 }
                println(values)
                if (values.size == 1) {
                    RoundedCornerShape((values.first()).dp)
                } else {
                    RoundedCornerShape(
                        (values.getOrNull(0) ?: 0).dp,
                        (values.getOrNull(1) ?: 0).dp,
                        (values.getOrNull(2) ?: 0).dp,
                        (values.getOrNull(3) ?: 0).dp,
                    )
                }
            }

            else -> default
        }
}
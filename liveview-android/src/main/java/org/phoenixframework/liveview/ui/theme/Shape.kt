package org.phoenixframework.liveview.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.ShapeValues
import org.phoenixframework.liveview.domain.ThemeHolder
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

internal fun shapeFromString(
    shape: String,
    default: CornerBasedShape = RoundedCornerShape(0.dp)
): CornerBasedShape {
    return ThemeHolder.themeShapeFromString(shape)
        ?: when {
            shape.isNotEmptyAndIsDigitsOnly() -> RoundedCornerShape(shape.toInt().dp)
            shape == ShapeValues.circle -> CircleShape
            shape == ShapeValues.rectangle -> RoundedCornerShape(0.dp)
            shape.startsWith(ShapeValues.roundedCorner) -> {
                // roundedCored(1, 2, 3, 4)
                val values =
                    shape.substring(ShapeValues.roundedCorner.length + 1, shape.lastIndex - 1)
                        .split(',')
                if (values.size == 1) {
                    RoundedCornerShape((values.first().toIntOrNull() ?: 0).dp)
                } else {
                    RoundedCornerShape(
                        (values.getOrNull(0)?.toIntOrNull() ?: 0).dp,
                        (values.getOrNull(1)?.toIntOrNull() ?: 0).dp,
                        (values.getOrNull(2)?.toIntOrNull() ?: 0).dp,
                        (values.getOrNull(3)?.toIntOrNull() ?: 0).dp,
                    )
                }
            }

            else -> default
        }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class CardDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    private val shape: Shape = builder.shape
    private val backgroundColor: Color = builder.backgroundColor
    private val elevation: Dp = builder.elevation

    @Composable
    fun Compose(paddingValues: PaddingValues?, content: @Composable () -> Unit) {
        Card(
            modifier = modifier.paddingIfNotNull(paddingValues),
            backgroundColor = backgroundColor,
            elevation = elevation,
            shape = shape,
        ) {
            content()
        }
    }

    class Builder : ComposableBuilder() {
        var shape: Shape = RoundedCornerShape(0.dp)
        var backgroundColor: Color = Color.White
        var elevation: Dp = 1.dp

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.backgroundColor = Color(java.lang.Long.decode(color))
            }
        }

        fun elevation(elevation: String) = apply {
            if (elevation.isNotEmptyAndIsDigitsOnly()) {
                this.elevation = (elevation.toInt()).dp
            }
        }

        fun build() = CardDTO(this)
    }
}
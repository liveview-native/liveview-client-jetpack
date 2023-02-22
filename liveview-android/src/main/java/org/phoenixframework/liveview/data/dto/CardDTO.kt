package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class CardDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    var shape: Shape = builder.shape
    var backgroundColor: Color = builder.backgroundColor
    var elevation: Dp = builder.elevation

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
            this.shape = when {
                shape.isNotEmptyAndIsDigitsOnly() -> RoundedCornerShape(shape.toInt().dp)
                shape.isNotEmpty() && shape == "circle" -> CircleShape
                shape.isNotEmpty() && shape == "rectangle" -> RectangleShape
                else -> RoundedCornerShape(0.dp)
            }
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

        override fun size(size: String): Builder = apply {
            super.size(size)
        }

        override fun padding(padding: String): Builder = apply {
            super.padding(padding)
        }

        override fun verticalPadding(padding: String): Builder = apply {
            super.verticalPadding(padding)
        }

        override fun horizontalPadding(padding: String): Builder = apply {
            super.horizontalPadding(padding)
        }

        override fun height(height: String): Builder = apply {
            super.height(height)
        }

        override fun width(width: String): Builder = apply {
            super.width(width)
        }

        fun build() = CardDTO(this)
    }
}
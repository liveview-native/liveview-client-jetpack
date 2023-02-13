package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

class RowDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    var horizontalArrangement: Arrangement.Horizontal = builder.horizontalArrangement
    var verticalAlignment: Alignment.Vertical = builder.verticalAlignment

    class Builder : ComposableBuilder() {
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
        var verticalAlignment: Alignment.Vertical = Alignment.Top

        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = when (horizontalArrangement) {
                    "spaced-evenly" -> Arrangement.SpaceEvenly
                    "space-around" -> Arrangement.SpaceAround
                    "space-between" -> Arrangement.SpaceBetween
                    "start" -> Arrangement.Start
                    "end" -> Arrangement.End
                    else -> Arrangement.Center
                }
            }
        }

        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = when (verticalAlignment) {
                    "top" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    else -> Alignment.Bottom
                }
            }
        }

        override fun size(size: String): Builder = apply {
            super.size(size)
        }

        override fun height(height: String): Builder = apply {
            super.height(height)
        }

        override fun width(width: String): Builder = apply {
            super.width(width)
        }

        fun build(): RowDTO = RowDTO(this)
    }
}

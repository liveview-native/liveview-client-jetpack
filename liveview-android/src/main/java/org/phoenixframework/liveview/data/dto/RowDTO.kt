package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

class RowDTO private constructor() : ComposableView() {
    class Builder {
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

        fun build(): RowDTO = RowDTO()
    }
}

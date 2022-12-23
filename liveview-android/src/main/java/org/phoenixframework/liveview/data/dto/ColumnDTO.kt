package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

class ColumnDTO private constructor() : ComposableView() {
    class Builder {
        private var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        private var horizontalAlignment: Alignment.Horizontal = Alignment.Start

        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = when (verticalArrangement) {
                "top" -> Arrangement.Top
                "spaced-evenly" -> Arrangement.SpaceEvenly
                "space-around" -> Arrangement.SpaceAround
                "space-between" -> Arrangement.SpaceBetween
                "bottom" -> Arrangement.Bottom
                else -> Arrangement.Center
            }

        }

        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = when (horizontalAlignment) {
                "start" -> Alignment.Start
                "center" -> Alignment.CenterHorizontally
                "end" -> Alignment.End
                else -> Alignment.Start
            }

        }

        fun build(): ColumnDTO = ColumnDTO()
    }
}

package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class ColumnDTO private constructor(builder: Builder) : ComposableView() {
    var verticalArrangement: Arrangement.Vertical
    var horizontalAlignment: Alignment.Horizontal
    var modifier : Modifier


    class Builder : ComposableBuilder(){
        private var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        private var horizontalAlignment: Alignment.Horizontal = Alignment.Start

        fun setVerticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = when (verticalArrangement) {
                "top" -> Arrangement.Top
                "spaced-evenly" -> Arrangement.SpaceEvenly
                "space-around" -> Arrangement.SpaceAround
                "space-between" -> Arrangement.SpaceBetween
                "bottom" -> Arrangement.Bottom
                else -> Arrangement.Center
            }

        }

        fun setHorizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = when (horizontalAlignment) {
                "start" -> Alignment.Start
                "center" -> Alignment.CenterHorizontally
                "end" -> Alignment.End
                else -> Alignment.Start
            }

        }

        fun getVerticalArrangement() = verticalArrangement
        fun getHorizontalAlignment() = horizontalAlignment

        fun build(): ColumnDTO {
            return ColumnDTO(this)
        }

    }

    init {
        modifier = builder.getModifier()
        verticalArrangement = builder.getVerticalArrangement()
        horizontalAlignment = builder.getHorizontalAlignment()
    }
}
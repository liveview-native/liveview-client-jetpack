package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


class RowDTO private constructor(builder: Builder) : ComposableView() {

    private var horizontalArrangement: Arrangement.Horizontal
    private var verticalAlignment: Alignment.Vertical
    private var modifier: Modifier
    
    @Composable
    fun Compose(content: @Composable() ()-> Unit){
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            content()
        }
    }
    class Builder : ComposableBuilder(){
        private var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
        private var verticalAlignment: Alignment.Vertical = Alignment.Top


        fun setHorizontalArrangement(horizontalArrangement: String) = apply {
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

        fun setVerticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = when (verticalAlignment) {
                    "top" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    else -> Alignment.Bottom
                }
            }


        }

        @JvmName("getHorizontalArrangement1")
        fun getHorizontalArrangement() = horizontalArrangement

        @JvmName("getVerticalAlignment1")
        fun getVerticalAlignment() = verticalAlignment


        fun build(): RowDTO {
            return RowDTO(this)
        }

    }

    init {
        modifier = builder.getModifier()
        horizontalArrangement = builder.getHorizontalArrangement()
        verticalAlignment = builder.getVerticalAlignment()
    }

}

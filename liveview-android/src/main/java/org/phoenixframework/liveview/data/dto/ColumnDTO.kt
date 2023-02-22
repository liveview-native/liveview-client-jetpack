package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ColumnDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    var verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    var horizontalAlignment: Alignment.Horizontal = builder.horizontalAlignment

    @Composable
    fun Compose(paddingValues: PaddingValues?, content: @Composable () -> Unit) {
        Column(
            modifier = modifier.paddingIfNotNull(paddingValues),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
    }

    class Builder : ComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start

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

        fun build(): ColumnDTO = ColumnDTO(this)
    }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView

class SpacerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    @Composable
    fun Compose() {
        Spacer(modifier = modifier)
    }

    class Builder : ComposableBuilder() {
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

        fun build() = SpacerDTO(this)
    }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick

    @Composable
    fun Compose(paddingValues: PaddingValues?, content: @Composable () -> Unit) {
        Button(
            modifier = modifier.paddingIfNotNull(paddingValues),
            onClick = onClick
        ) {
            content()
        }
    }

    class Builder : ComposableBuilder() {
        var onClick: () -> Unit = {}
        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        override fun padding(padding: String): Builder = apply {
            super.padding(padding)
        }

        fun build() = ButtonDTO(this)
    }
}
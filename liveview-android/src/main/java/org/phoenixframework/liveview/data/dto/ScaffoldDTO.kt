package org.phoenixframework.liveview.data.dto

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.toColor

class ScaffoldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    // set by LiveViewCoordinator.extractChildren
    var topAppBar: TopAppBarDTO? = null
    private val backgroundColor: Color = builder.backgroundColor

    @Composable
    fun Compose(content: @Composable () -> Unit) {
        Scaffold(modifier = modifier, backgroundColor = backgroundColor, topBar = {
            topAppBar?.Compose()
        }) { paddingValues ->
            paddingValues
            content()
        }
    }

    class Builder : ComposableBuilder() {
        var backgroundColor: Color = Color.White

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.backgroundColor = color.toColor()
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

        fun build() = ScaffoldDTO(this)
    }
}


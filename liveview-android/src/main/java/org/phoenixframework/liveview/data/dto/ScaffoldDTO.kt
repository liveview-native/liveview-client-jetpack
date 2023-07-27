package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ScaffoldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    // set by LiveViewCoordinator.extractChildren
    var topAppBar: TopAppBarDTO? = null
    private val backgroundColor: Color = builder.backgroundColor

    @Composable
    fun Compose(paddingValues: PaddingValues?, content: @Composable (PaddingValues) -> Unit) {
        Scaffold(
            modifier = modifier.paddingIfNotNull(paddingValues),
            backgroundColor = backgroundColor,
            topBar = {
                topAppBar?.Compose()
            }) { contentPaddingValues ->
            content(contentPaddingValues)
        }
    }

    class Builder : ComposableBuilder() {
        var backgroundColor: Color = Color.White

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.backgroundColor = color.toColor()
            }
        }

        fun build() = ScaffoldDTO(this)
    }
}


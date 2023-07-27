package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class SpacerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    @Composable
    fun Compose(paddingValues: PaddingValues?) {
        Spacer(modifier = modifier.paddingIfNotNull(paddingValues))
    }

    class Builder : ComposableBuilder() {

        fun build() = SpacerDTO(this)
    }
}
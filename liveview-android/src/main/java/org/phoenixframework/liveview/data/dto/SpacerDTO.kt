package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class SpacerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        Spacer(modifier = modifier.paddingIfNotNull(paddingValues))
    }

    class Builder : ComposableBuilder() {
        fun build() = SpacerDTO(this)
    }
}

object SpacerDtoFactory : ComposableViewFactory<SpacerDTO, SpacerDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): SpacerDTO = attributes.fold(SpacerDTO.Builder()) { builder, attribute ->
        builder.processCommonAttributes(scope, attribute, pushEvent) as SpacerDTO.Builder
    }.build()
}

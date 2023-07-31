package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class SpacerDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?, paddingValues: PaddingValues?, onChildren: OnChildren?
    ) {
        Spacer(modifier = modifier.paddingIfNotNull(paddingValues))
    }

    class Builder : ComposableBuilder() {

        fun build() = SpacerDTO(this)
    }
}

object SpacerDtoFactory : ComposableViewFactory<SpacerDTO, SpacerDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?
    ): SpacerDTO = attributes.fold(SpacerDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "size" -> builder.size(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            else -> builder
        } as SpacerDTO.Builder
    }.build()
}

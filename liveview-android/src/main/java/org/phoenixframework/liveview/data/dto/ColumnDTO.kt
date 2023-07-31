package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ColumnDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    private val horizontalAlignment: Alignment.Horizontal = builder.horizontalAlignment

    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?, paddingValues: PaddingValues?, onChildren: OnChildren?
    ) {
        Column(
            modifier = modifier.paddingIfNotNull(paddingValues),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            children?.forEach { node ->
                onChildren?.invoke(node, paddingValues)
            }
        }
    }

    class Builder : ComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start

        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = when (verticalArrangement) {
                "top" -> Arrangement.Top
                "spacedEvenly" -> Arrangement.SpaceEvenly
                "spaceAround" -> Arrangement.SpaceAround
                "spaceBetween" -> Arrangement.SpaceBetween
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

        fun build(): ColumnDTO = ColumnDTO(this)
    }
}

object ColumnDtoFactory : ComposableViewFactory<ColumnDTO, ColumnDTO.Builder>() {
    /**
     * Creates a `ColumnDTO` object based on the attributes of the input `Attributes` object.
     * Column co-relates to the Column composable
     * @param attributes the `Attributes` object to create the `ColumnDTO` object from
     * @return a `ColumnDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?
    ): ColumnDTO = attributes.fold(ColumnDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "verticalArrangement" -> {
                builder.verticalArrangement(attribute.value)
            }

            "horizontalAlignment" -> {
                builder.horizontalAlignment(attribute.value)
            }

            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            else -> builder
        } as ColumnDTO.Builder
    }.build()
}
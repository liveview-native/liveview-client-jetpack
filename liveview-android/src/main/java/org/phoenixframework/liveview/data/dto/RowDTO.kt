package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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

class RowDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    private val horizontalArrangement: Arrangement.Horizontal = builder.horizontalArrangement
    private val verticalAlignment: Alignment.Vertical = builder.verticalAlignment

    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?,
        paddingValues: PaddingValues?,
        onChildren: OnChildren?
    ) {
        Row(
            modifier = modifier.paddingIfNotNull(paddingValues),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            children?.forEach { node ->
                onChildren?.invoke(node, paddingValues)
            }
        }
    }

    class Builder : ComposableBuilder() {
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
        var verticalAlignment: Alignment.Vertical = Alignment.Top

        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = when (horizontalArrangement) {
                    "spaceEvenly" -> Arrangement.SpaceEvenly
                    "spaceAround" -> Arrangement.SpaceAround
                    "spaceBetween" -> Arrangement.SpaceBetween
                    "start" -> Arrangement.Start
                    "end" -> Arrangement.End
                    else -> Arrangement.Center
                }
            }
        }

        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = when (verticalAlignment) {
                    "top" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    else -> Alignment.Bottom
                }
            }
        }

        fun build(): RowDTO = RowDTO(this)
    }
}

object RowDtoFactory : ComposableViewFactory<RowDTO, RowDTO.Builder>() {
    /**
     * Creates a `RowDTO` object based on the attributes of the input `Attributes` object.
     * Row co-relates to the Row composable
     * @param attributes the `Attributes` object to create the `RowDTO` object from
     * @return a `RowDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<Attribute>,
        children: List<Node>?,
        pushEvent: PushEvent?
    ): RowDTO =
        attributes.fold(RowDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                "horizontalArrangement" -> {
                    builder.horizontalArrangement(horizontalArrangement = attribute.value)
                }

                "verticalAlignment" -> {
                    builder.verticalAlignment(verticalAlignment = attribute.value)
                }

                "size" -> builder.size(attribute.value)
                "height" -> builder.height(attribute.value)
                "width" -> builder.width(attribute.value)
                "padding" -> builder.padding(attribute.value)
                "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                "verticalPadding" -> builder.verticalPadding(attribute.value)
                else -> builder
            } as RowDTO.Builder
        }.build()
}
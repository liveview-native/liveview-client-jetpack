package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class CardDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    private val shape: Shape = builder.shape
    private val backgroundColor: Color = builder.backgroundColor
    private val elevation: Dp = builder.elevation

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Card(
            modifier = modifier.paddingIfNotNull(paddingValues),
            backgroundColor = backgroundColor,
            elevation = elevation,
            shape = shape,
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, paddingValues, pushEvent)
            }
        }
    }

    class Builder : ComposableBuilder() {
        var shape: Shape = RoundedCornerShape(0.dp)
        var backgroundColor: Color = Color.White
        var elevation: Dp = 1.dp

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.backgroundColor = Color(java.lang.Long.decode(color))
            }
        }

        fun elevation(elevation: String) = apply {
            if (elevation.isNotEmptyAndIsDigitsOnly()) {
                this.elevation = (elevation.toInt()).dp
            }
        }

        fun build() = CardDTO(this)
    }
}

object CardDtoFactory : ComposableViewFactory<CardDTO, CardDTO.Builder>() {
    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * Card co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?,
    ): CardDTO = attributes.fold(CardDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "shape" -> builder.shape(attribute.value)
            "backgroundColor" -> builder.backgroundColor(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            else -> builder
        } as CardDTO.Builder
    }.build()
}
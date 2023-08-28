package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class AsyncImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageUrl: String = builder.imageUrl
    private val contentDescription: String? = builder.contentDescription
    private val crossFade: Boolean = builder.crossFade
    private val shape: Shape = builder.shape
    private val contentScale: ContentScale = builder.contentScale

    @Composable
    override fun Compose(
        children: ImmutableList<ComposableTreeNode>?,
        paddingValues: PaddingValues?,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(crossFade)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
                .clip(shape)
                .paddingIfNotNull(paddingValues)
        )
    }

    class Builder : ComposableBuilder() {
        var imageUrl: String = ""
        var contentDescription: String? = null
        var crossFade: Boolean = false
        var shape: Shape = RoundedCornerShape(0.dp)
        var contentScale: ContentScale = ContentScale.Fit

        fun imageUrl(imageUrl: String) = apply {
            this.imageUrl = imageUrl
        }

        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        fun crossFade(crossFade: String) = apply {
            if (crossFade.isNotEmpty()) {
                this.crossFade = crossFade.toBoolean()
            }
        }

        fun contentScale(contentScale: String) = apply {
            if (contentScale.isNotEmpty()) {
                this.contentScale = when (contentScale) {
                    "fit" -> ContentScale.Fit
                    "crop" -> ContentScale.Crop
                    "fillBounds" -> ContentScale.FillBounds
                    "fillHeight" -> ContentScale.FillHeight
                    "fillWidth" -> ContentScale.FillWidth
                    "inside" -> ContentScale.Inside
                    else -> ContentScale.None
                }
            }
        }

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun build(): AsyncImageDTO = AsyncImageDTO(this)
    }
}

object AsyncImageDtoFactory : ComposableViewFactory<AsyncImageDTO, AsyncImageDTO.Builder>() {
    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object.
     * AsyncImage co-relates to the AsyncImage composable from Coil library used to load images from network
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?,
    ): AsyncImageDTO = attributes.fold(
        AsyncImageDTO.Builder().imageUrl(attributes.find { it.name == "url" }?.value ?: "")
    ) { builder, attribute ->
        when (attribute.name) {
            "contentScale" -> builder.contentScale(attribute.value)
            "contentDescription" -> builder.contentDescription(attribute.value)
            "crossFade" -> builder.crossFade(attribute.value)
            "shape" -> builder.shape(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            else -> builder
        } as AsyncImageDTO.Builder
    }.build()

}
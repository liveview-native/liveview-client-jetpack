package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.shapeFromString

class AsyncImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageUrl: String = builder.imageUrl
    private val contentDescription: String? = builder.contentDescription
    private val crossFade: Boolean = builder.crossFade
    private val shape: Shape = builder.shape
    private val alignment: Alignment = builder.alignment
    private val contentScale: ContentScale = builder.contentScale
    private val alpha: Float = builder.alpha

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(crossFade)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier.clip(shape),
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha
        )
    }

    class Builder : ComposableBuilder() {
        var imageUrl: String = ""
        var contentDescription: String? = null
        var crossFade: Boolean = false
        var shape: Shape = RoundedCornerShape(0.dp)
        var alignment: Alignment = Alignment.Center
        var contentScale: ContentScale = ContentScale.Fit
        var alpha: Float = 1.0f

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
                this.contentScale = contentScaleFromString(
                    contentScale = contentScale,
                    defaultValue = ContentScale.None
                )
            }
        }

        fun alignment(alignment: String) = apply {
            if (alignment.isNotEmpty()) {
                this.alignment =
                    alignmentFromString(alignment = alignment, defaultValue = Alignment.Center)
            }
        }

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun alpha(alpha: String) = apply {
            this.alpha = alpha.toFloatOrNull() ?: 1f
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
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): AsyncImageDTO = attributes.fold(
        AsyncImageDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            "url" -> builder.imageUrl(attribute.value)
            "alignment" -> builder.alignment(attribute.value)
            "alpha" -> builder.alpha(attribute.value)
            "contentScale" -> builder.contentScale(attribute.value)
            "contentDescription" -> builder.contentDescription(attribute.value)
            "crossFade" -> builder.crossFade(attribute.value)
            "shape" -> builder.shape(attribute.value)
            else -> builder.processCommonAttributes(scope, attribute, pushEvent)
        } as AsyncImageDTO.Builder
    }.build()
}
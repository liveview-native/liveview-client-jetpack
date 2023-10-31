package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.shapeFromString

internal class ImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageResource: String = builder.imageResource
    private val contentDescription: String? = builder.contentDescription
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
        val ctx = LocalContext.current
        val res = ctx.resources
        val resource = remember(imageResource) {
            res.getIdentifier(imageResource, "drawable", ctx.packageName)
        }
        Image(
            painter = painterResource(id = resource),
            contentDescription = contentDescription,
            modifier = modifier.clip(shape),
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha
        )
    }

    class Builder : ComposableBuilder<ImageDTO>() {
        var imageResource: String = ""
            private set
        var contentDescription: String? = null
            private set
        var shape: Shape = RoundedCornerShape(0.dp)
            private set
        var alignment: Alignment = Alignment.Center
            private set
        var contentScale: ContentScale = ContentScale.Fit
            private set
        var alpha: Float = 1.0f
            private set

        fun imageResource(imageResource: String) = apply {
            this.imageResource = imageResource
        }

        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        fun contentScale(contentScale: String) = apply {
            if (contentScale.isNotEmpty()) {
                this.contentScale = contentScaleFromString(contentScale)
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

        override fun build(): ImageDTO = ImageDTO(this)
    }
}

internal object ImageDtoFactory : ComposableViewFactory<ImageDTO, ImageDTO.Builder>() {
    /**
     * Creates an `ImageDTO` object based on the attributes and text of the input `Attributes` object.
     * Image co-relates to the Image composable from Compose library used to load images from the
     * project's folder.
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @return an `ImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ImageDTO = attributes.fold(ImageDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "resource" -> builder.imageResource(attribute.value)
            "alignment" -> builder.alignment(attribute.value)
            "alpha" -> builder.alpha(attribute.value)
            "contentScale" -> builder.contentScale(attribute.value)
            "contentDescription" -> builder.contentDescription(attribute.value)
            "shape" -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ImageDTO.Builder
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

class AsyncImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    val imageUrl: String = builder.imageUrl
    val contentDescription: String? = builder.contentDescription
    val crossfade: Boolean = builder.crossFade
    val shape: Shape = builder.shape
    val contentScale: ContentScale = builder.contentScale

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
                    "fill-bounds" -> ContentScale.FillBounds
                    "fill-height" -> ContentScale.FillHeight
                    "fill-width" -> ContentScale.FillWidth
                    "inside" -> ContentScale.Inside
                    else -> ContentScale.None
                }
            }
        }

        fun shape(shape: String) = apply {
            this.shape = when {
                shape.isNotEmptyAndIsDigitsOnly() -> RoundedCornerShape(shape.toInt().dp)
                shape.isNotEmpty() && shape == "circle" -> CircleShape
                shape.isNotEmpty() && shape == "rectangle" -> RectangleShape
                else -> RoundedCornerShape(0.dp)
            }
        }

        override fun size(size: String): Builder = apply {
            super.size(size)
        }

        override fun height(height: String): Builder = apply {
            super.height(height)
        }

        override fun width(width: String): Builder = apply {
            super.width(width)
        }

        fun build(): AsyncImageDTO = AsyncImageDTO(this)
    }
}
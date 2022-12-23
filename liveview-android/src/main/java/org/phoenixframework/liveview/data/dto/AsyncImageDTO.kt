package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

class AsyncImageDTO private constructor() : ComposableView() {
    val modifier: Modifier = Modifier

    class Builder {
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

        fun build(): AsyncImageDTO = AsyncImageDTO()
    }
}
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
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class AsyncImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageUrl: String = builder.imageUrl
    private val contentDescription: String? = builder.contentDescription
    private val crossFade: Boolean = builder.crossFade
    private val shape: Shape = builder.shape
    private val contentScale: ContentScale = builder.contentScale

    @Composable
    fun Compose(paddingValues: PaddingValues?) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(crossFade).build(),
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
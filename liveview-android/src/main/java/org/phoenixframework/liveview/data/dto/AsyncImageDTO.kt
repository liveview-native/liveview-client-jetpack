package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly


class AsyncImageDTO private constructor(val builder: Builder): ComposableView(){
    private val imageUrl: String
    private val contentDescription: String?
    private val crossfade: Boolean
    private val shape :Shape
    private val contentScale : ContentScale
    private var modifier: Modifier = Modifier

    @Composable
    fun  Compose(){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(crossfade).build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
    class Builder() : ComposableBuilder(){
        private var imageUrl: String =""
        private var contentDescription: String?= null
        private var crossFade: Boolean = false
        private var shape :Shape = RoundedCornerShape(0.dp)
        private var contentScale : ContentScale = ContentScale.Fit


        fun setImageUrl(imageUrl: String) = apply {
            this.imageUrl = imageUrl
        }
        fun setContentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }
        fun  setCrossFade(crossFade : String) = apply {
            if (crossFade.isNotEmpty()){
                this.crossFade = crossFade.toBoolean()
            }
        }
        fun setContentScale(contentScale: String) = apply {
            if (contentScale.isNotEmpty()){
                this.contentScale = when(contentScale){
                    "fit"-> ContentScale.Fit
                    "crop"-> ContentScale.Crop
                    "fill-bounds"-> ContentScale.FillBounds
                    "fill-height"-> ContentScale.FillHeight
                    "fill-width"-> ContentScale.FillWidth
                    "inside"-> ContentScale.Inside
                    else -> ContentScale.None
                }
            }
        }
        fun setShape(shape : String) = apply {
            this.shape = when {
                shape.isNotEmptyAndIsDigitsOnly() -> {
                    RoundedCornerShape(shape.toInt().dp)
                }
                shape.isNotEmpty() && shape == "circle" -> {
                    CircleShape
                }
                shape.isNotEmpty() && shape == "rectangle" -> {
                    RectangleShape
                }
                else -> {
                    RoundedCornerShape(0.dp)
                }
            }
        }

        fun build(): AsyncImageDTO {
           return AsyncImageDTO(builder = this)
        }

        fun getImageUrl(): String = imageUrl
        fun getContentDescription(): String? = contentDescription
        fun getCrossFade(): Boolean = crossFade
        fun getShape(): Shape = shape
        fun getContentScale(): ContentScale = contentScale


    }

    init {
        imageUrl = builder.getImageUrl()
        contentDescription = builder.getContentDescription()
        contentScale = builder.getContentScale()
        crossfade = builder.getCrossFade()
        shape = builder.getShape()
        modifier = builder.getModifier()
    }



}
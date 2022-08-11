package com.dockyard.liveviewtest.android.ui.phx_components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import org.jsoup.nodes.Element

@Composable
fun PhxImage(
    element: Element,
    modifier: Modifier
) {

    val imageUrl = element.attr("url")

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .decoderFactory(SvgDecoder.Factory())
        .data(imageUrl)
        .crossfade(true)
        .size(Size.ORIGINAL) // Set the target size to load the image at.
        .build()


    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier =
            modifier
            .clip(CircleShape)
    )
}
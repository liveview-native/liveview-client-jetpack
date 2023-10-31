package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

internal class AsyncImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageUrl: String = builder.imageUrl
    private val contentDescription: String? = builder.contentDescription
    private val crossFade: Boolean = builder.crossFade
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
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha
        )
    }

    internal class Builder : ComposableBuilder<AsyncImageDTO>() {
        var imageUrl: String = ""
            private set
        var contentDescription: String? = null
            private set
        var crossFade: Boolean = false
            private set
        var alignment: Alignment = Alignment.Center
            private set
        var contentScale: ContentScale = ContentScale.Fit
            private set
        var alpha: Float = 1.0f
            private set

        /**
         * Sets the image URL.
         *
         * ```
         * <AsyncImage url="https://assets.dockyard.com/images/narwin-home-flare.jpg" />
         * ```
         * @param imageUrl image url
         */
        fun imageUrl(imageUrl: String) = apply {
            this.imageUrl = imageUrl
        }

        /**
         * Sets the image content description fro accessibility purpose.
         *
         * ```
         * <AsyncImage contentDescription="Application Logo" />
         * ```
         * @param contentDescription string representing the image's content description
         */
        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        /**
         * Define if the image will have the crossfade animation after loaded.
         *
         * ```
         * <AsyncImage crossFade="true" />
         * ```
         * @param crossFade true to enable a crossfade animation, false otherwise.
         */
        fun crossFade(crossFade: String) = apply {
            if (crossFade.isNotEmpty()) {
                this.crossFade = crossFade.toBoolean()
            }
        }

        /**
         * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are
         * a different size from the intrinsic size.
         * ```
         * <AsyncImage contentScale="crop" />
         * ```
         * @param contentScale content scale. The supported values are: `fit`, `crop`, `fillBounds`,
         *  `fillHeight`, `fillWidth` and `inside`.
         */
        fun contentScale(contentScale: String) = apply {
            if (contentScale.isNotEmpty()) {
                this.contentScale = contentScaleFromString(contentScale)
            }
        }

        /**
         * Alignment parameter used to place the image in the given bounds defined by the width and
         * height.
         * ```
         * <AsyncImage alignment="centerStart" />
         * ```
         * @param alignment image alignment when the image is smaller than the available area.
         * The supported values are: `topStart`, `topCenter`, `topEnd`, `centerStart`, `center`,
         * `centerEnd`, `bottomStart`, `bottomCenter`, and `bottomEnd`.
         */
        fun alignment(alignment: String) = apply {
            if (alignment.isNotEmpty()) {
                this.alignment =
                    alignmentFromString(alignment = alignment, defaultValue = Alignment.Center)
            }
        }

        /**
         * Opacity to be applied to the image when it is rendered onscreen.
         * ```
         * <AsyncImage alpha="0.5" />
         * ```
         * @param alpha float value between 0 (transparent) to 1 (opaque).
         */
        fun alpha(alpha: String) = apply {
            this.alpha = alpha.toFloatOrNull() ?: 1f
        }

        override fun build(): AsyncImageDTO = AsyncImageDTO(this)
    }
}

internal object AsyncImageDtoFactory :
    ComposableViewFactory<AsyncImageDTO, AsyncImageDTO.Builder>() {
    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes`
     * object. AsyncImage co-relates to the AsyncImage composable from Coil library used to load
     * images from network.
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes`
     * object
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
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as AsyncImageDTO.Builder
    }.build()
}
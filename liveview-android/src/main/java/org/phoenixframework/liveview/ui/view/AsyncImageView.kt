package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrAlpha
import org.phoenixframework.liveview.data.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.data.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.data.constants.Attrs.attrCrossFade
import org.phoenixframework.liveview.data.constants.Attrs.attrUrl
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.LocalHttpUrl
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import java.net.URI

/**
 * A composable that executes an image request asynchronously and renders the result.
 * ```
 * <AsyncImage
 *  url="https://assets.dockyard.com/images/narwin-home-flare.jpg"
 *  alpha="0.5"
 *  contentScale="fillHeight" />
 * ```
 */
internal class AsyncImageView private constructor(props: Properties) :
    ComposableView<AsyncImageView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val imageUrl = props.imageUrl
        val contentDescription = props.contentDescription
        val crossFade = props.crossFade
        val alignment = props.alignment
        val contentScale = props.contentScale
        val alpha = props.alpha

        val baseUrl = LocalHttpUrl.current
        val resolvedImageUrl = remember(imageUrl) {
            val baseUri = URI.create(baseUrl)
            val relativeUri = baseUri.resolve(imageUrl)
            relativeUri.toString()
        }
        val loadingComposable = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateLoading }
        }
        val errorComposable = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateError }
        }
        val model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(resolvedImageUrl)
            .crossfade(crossFade)
            .build()
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = props.commonProps.modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    loadingComposable?.let { loading ->
                        PhxLiveView(loading, pushEvent, composableNode, null, this)
                    }
                }

                is AsyncImagePainter.State.Error -> {
                    errorComposable?.let { error ->
                        PhxLiveView(error, pushEvent, composableNode, null, this)
                    }
                }

                else -> SubcomposeAsyncImageContent()
            }
        }
    }

    @Stable
    data class Properties(
        val imageUrl: String,
        val contentDescription: String?,
        val crossFade: Boolean,
        val alignment: Alignment,
        val contentScale: ContentScale,
        val alpha: Float,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var imageUrl: String = ""
        private var contentDescription: String? = null
        private var crossFade: Boolean = false
        private var alignment: Alignment = Alignment.Center
        private var contentScale: ContentScale = ContentScale.Fit
        private var alpha: Float = 1.0f

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
         * @param crossFade true to enable a cross-fade animation, false otherwise.
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
         * @param contentScale content scale.
         * See the supported values at [org.phoenixframework.liveview.data.constants.ContentScaleValues].
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
         * See the supported values at [org.phoenixframework.liveview.data.constants.AlignmentValues].
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

        fun build(): AsyncImageView = AsyncImageView(
            Properties(
                imageUrl,
                contentDescription,
                crossFade,
                alignment,
                contentScale,
                alpha,
                commonProps
            )
        )
    }
}

internal object AsyncImageViewFactory : ComposableViewFactory<AsyncImageView>() {
    /**
     * Creates an `AsyncImageView` object based on the attributes and text of the input `Attributes`
     * object. AsyncImageView co-relates to the AsyncImage composable from Coil library used to load
     * images from network.
     * @param attributes the `Attributes` object to create the `AsyncImageView` object from
     * @return an `AsyncImageView` object based on the attributes and text of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): AsyncImageView = attributes.fold(
        AsyncImageView.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            attrAlignment -> builder.alignment(attribute.value)
            attrAlpha -> builder.alpha(attribute.value)
            attrContentDescription -> builder.contentDescription(attribute.value)
            attrContentScale -> builder.contentScale(attribute.value)
            attrCrossFade -> builder.crossFade(attribute.value)
            attrUrl -> builder.imageUrl(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as AsyncImageView.Builder
    }.build()
}
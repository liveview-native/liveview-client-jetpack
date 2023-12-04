package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrAlpha
import org.phoenixframework.liveview.data.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.data.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.data.constants.Attrs.attrResource
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Creates a composable that lays out and draws a given Painter. This will attempt to size the
 * composable according to the Painter's intrinsic size. However, an optional Modifier parameter
 * can be provided to adjust sizing or draw additional content (ex. background).
 * The image must be located at `res/drawable` folder.
 * ```
 * <Image resource="android_icon" />
 * ```
 */
internal class ImageDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val imageResource: String = builder.imageResource
    private val contentDescription: String? = builder.contentDescription
    private val alignment: Alignment = builder.alignment
    private val contentScale: ContentScale = builder.contentScale
    private val alpha: Float = builder.alpha

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Image(
            painter = getPainter(imageResource = imageResource),
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha
        )
    }

    companion object {
        private val imageCache = mutableMapOf<String, Painter>()

        @Composable
        fun getPainter(imageResource: String): Painter {
            val ctx = LocalContext.current
            val res = ctx.resources
            if (!imageCache.containsKey(imageResource)) {
                imageCache[imageResource] =
                    painterResource(res.getIdentifier(imageResource, "drawable", ctx.packageName))
            }
            return imageCache[imageResource]!!
        }
    }

    class Builder : ComposableBuilder() {
        var imageResource: String = ""
            private set
        var contentDescription: String? = null
            private set
        var alignment: Alignment = Alignment.Center
            private set
        var contentScale: ContentScale = ContentScale.Fit
            private set
        var alpha: Float = 1.0f
            private set

        /**
         * Image name. The image must be located at project's `res/drawable` folder.
         * ```
         *  <Image resource="android_icon" />
         * ```
         * @param imageResource image resource name (can be a PNG or Vector Drawable) without
         * extension. For example, if the image is `android_icon.png`, use `android_icon`.
         */
        fun imageResource(imageResource: String) = apply {
            this.imageResource = imageResource
        }

        /**
         * Sets the image content description fro accessibility purpose.Ø
         *
         * ```
         * <Image content-description="Application Image" />
         * ```
         * @param contentDescription string representing the image's content description
         */
        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        /**
         * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are
         * a different size from the intrinsic size.
         * ```
         * <AsyncImage content-scale="crop" />
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
         * <Image alignment="centerStart" />
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
         * <Image alpha="0.5" />
         * ```
         * @param alpha float value between 0 (transparent) to 1 (opaque).
         */
        fun alpha(alpha: String) = apply {
            this.alpha = alpha.toFloatOrNull() ?: 1f
        }

        fun build(): ImageDTO = ImageDTO(this)
    }
}

internal object ImageDtoFactory : ComposableViewFactory<ImageDTO, ImageDTO.Builder>() {
    /**
     * Creates an `ImageDTO` object based on the attributes and text of the input `Attributes`
     * object. ImageDTO co-relates to the Image composable from Compose library used to load images
     * from the project's folder.
     * @param attributes the `Attributes` object to create the `ImageDTO` object from
     * @return an `ImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ImageDTO = attributes.fold(ImageDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrAlignment -> builder.alignment(attribute.value)
            attrAlpha -> builder.alpha(attribute.value)
            attrContentDescription -> builder.contentDescription(attribute.value)
            attrContentScale -> builder.contentScale(attribute.value)
            attrResource -> builder.imageResource(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ImageDTO.Builder
    }.build()
}
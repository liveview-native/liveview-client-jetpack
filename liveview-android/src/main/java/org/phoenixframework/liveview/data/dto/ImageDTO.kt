package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrAlpha
import org.phoenixframework.liveview.data.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.data.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.data.constants.Attrs.attrResource
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
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
internal class ImageDTO private constructor(props: Properties) :
    ComposableView<ImageDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val imageResource = props.imageResource
        val contentDescription = props.contentDescription
        val alignment = props.alignment
        val contentScale = props.contentScale
        val alpha = props.alpha

        Image(
            painter = getPainter(imageResource = imageResource),
            contentDescription = contentDescription,
            modifier = props.commonProps.modifier,
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

    @Stable
    internal data class Properties(
        val imageResource: String,
        val contentDescription: String?,
        val alignment: Alignment,
        val contentScale: ContentScale,
        val alpha: Float,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    class Builder : ComposableBuilder() {
        private var imageResource: String = ""
        private var contentDescription: String? = null
        private var alignment: Alignment = Alignment.Center
        private var contentScale: ContentScale = ContentScale.Fit
        private var alpha: Float = 1.0f

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
         * <Image contentDescription="Application Image" />
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
         * <AsyncImage contentScale="crop" />
         * ```
         * @param contentScale content scale. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ContentScaleValues] colors.
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
         * <Image alpha="0.5" />
         * ```
         * @param alpha float value between 0 (transparent) to 1 (opaque).
         */
        fun alpha(alpha: String) = apply {
            this.alpha = alpha.toFloatOrNull() ?: 1f
        }

        fun build(): ImageDTO = ImageDTO(
            Properties(
                imageResource,
                contentDescription,
                alignment,
                contentScale,
                alpha,
                commonProps,
            )
        )
    }
}

internal object ImageDtoFactory : ComposableViewFactory<ImageDTO>() {
    /**
     * Creates an `ImageDTO` object based on the attributes and text of the input `Attributes`
     * object. ImageDTO co-relates to the Image composable from Compose library used to load images
     * from the project's folder.
     * @param attributes the `Attributes` object to create the `ImageDTO` object from
     * @return an `ImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
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
package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.constants.Attrs.attrAlpha
import org.phoenixframework.liveview.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.constants.Attrs.attrResource
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

/**
 * Creates a composable that lays out and draws a given Painter. This will attempt to size the
 * composable according to the Painter's intrinsic size. However, an optional Modifier parameter
 * can be provided to adjust sizing or draw additional content (ex. background).
 * The image must be located at `res/drawable` folder.
 * ```
 * <Image resource="android_icon" />
 * ```
 */
internal class ImageView private constructor(props: Properties) :
    ComposableView<ImageView.Properties>(props) {

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
        val imageResource: String = "",
        val contentDescription: String? = null,
        val alignment: Alignment = Alignment.Center,
        val contentScale: ContentScale = ContentScale.Fit,
        val alpha: Float = 1.0f,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ImageView>() {
        /**
         * Creates an `ImageView` object based on the attributes and text of the input `Attributes`
         * object. ImageView co-relates to the Image composable from Compose library used to load images
         * from the project's folder.
         * @param attributes the `Attributes` object to create the `ImageView` object from
         * @return an `ImageView` object based on the attributes and text of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): ImageView = ImageView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrAlignment -> alignment(props, attribute.value)
                attrAlpha -> alpha(props, attribute.value)
                attrContentDescription -> contentDescription(props, attribute.value)
                attrContentScale -> contentScale(props, attribute.value)
                attrResource -> imageResource(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })

        /**
         * Image name. The image must be located at project's `res/drawable` folder.
         * ```
         *  <Image resource="android_icon" />
         * ```
         * @param imageResource image resource name (can be a PNG or Vector Drawable) without
         * extension. For example, if the image is `android_icon.png`, use `android_icon`.
         */
        private fun imageResource(props: Properties, imageResource: String): Properties {
            return props.copy(imageResource = imageResource)
        }

        /**
         * Sets the image content description fro accessibility purpose.Ø
         *
         * ```
         * <Image contentDescription="Application Image" />
         * ```
         * @param contentDescription string representing the image's content description
         */
        private fun contentDescription(props: Properties, contentDescription: String): Properties {
            return props.copy(contentDescription = contentDescription)
        }

        /**
         * Scale parameter used to determine the aspect ratio scaling to be used if the bounds are
         * a different size from the intrinsic size.
         * ```
         * <AsyncImage contentScale="crop" />
         * ```
         * @param contentScale content scale. See the supported values at
         * [org.phoenixframework.liveview.constants.ContentScaleValues] colors.
         */
        private fun contentScale(props: Properties, contentScale: String): Properties {
            return if (contentScale.isNotEmpty()) {
                props.copy(contentScale = contentScaleFromString(contentScale))
            } else props
        }

        /**
         * Alignment parameter used to place the image in the given bounds defined by the width and
         * height.
         * ```
         * <Image alignment="centerStart" />
         * ```
         * @param alignment image alignment when the image is smaller than the available area.
         * See the supported values at [org.phoenixframework.liveview.constants.AlignmentValues].
         */
        private fun alignment(props: Properties, alignment: String): Properties {
            return if (alignment.isNotEmpty()) {
                return props.copy(
                    alignment =
                    alignmentFromString(alignment = alignment, defaultValue = Alignment.Center)
                )
            } else props
        }

        /**
         * Opacity to be applied to the image when it is rendered onscreen.
         * ```
         * <Image alpha="0.5" />
         * ```
         * @param alpha float value between 0 (transparent) to 1 (opaque).
         */
        private fun alpha(props: Properties, alpha: String): Properties {
            return props.copy(alpha = alpha.toFloatOrNull() ?: 1f)
        }
    }
}
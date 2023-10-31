package org.phoenixframework.liveview.domain.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 *  A `ComposableView` is the parent class of all components. Subclasses must implement the
 *  `Composable` function in order to call the real composable. The `modifier` param and all
 *  necessary params for the actual component implementation should be provided by a subclass of
 *  `ComposableBuilder`. In order to make a component available, a subclass of
 *  `ComposableViewFactory` must be implemented, the it must be registered on `ComposableRegistry`
 *  object informing the respective tag for the composable.
 */
abstract class ComposableView(val modifier: Modifier = Modifier) {

    @Composable
    abstract fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    )
}

/**
 *  A `ComposableBuilder` is responsible to handle attributes declared in the LiveView tags. This
 *  class must parse and convert the `CoreAttribute` value in a corresponding property or `Modifier`
 *  to be used in the `ComposableView` implementation. All the required information required by a
 *  `ComposableView` must be provided by the respective `ComposableBuilder`.
 */
abstract class ComposableBuilder<T : ComposableView> {
    var hasVerticalScrolling: Boolean = false
        private set
    var hasHorizontalScrolling: Boolean = false
        private set
    var modifier: Modifier = Modifier
        private set

    /**
     * All subclasses must implement this function that usually simply create the respective
     * `ComposableView`.
     * @return a `ComposableView` instance.
     */
    abstract fun build(): T

    /**
     * Declare the preferred size (width and height) of a Composable. You can specify the exactly
     * [size] dp square, or:
     * - use `fill` to occupy the max available area;
     * - or use `wrap` to occupy just the minimum space requested by the children.
     * ```
     * <ComposableView size="fill" />
     * // or
     * <ComposableView size="16" />
     * ```
     * @param size the size of a `ComposableView` instance.
     */
    private fun size(size: String) = apply {
        modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.size(size = size.toInt().dp))
            size == FILL -> modifier.then(Modifier.fillMaxSize())
            size == WRAP -> modifier.then(Modifier.wrapContentSize())
            else -> modifier
        }
    }

    /**
     * Apply [padding] dp of additional space along each edge of the content, left, top, right and
     * bottom.
     * ```
     * <ComposableView padding="8" />
     * ```
     * @param padding int value for padding to be applied to the four edges.
     */
    private fun padding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    /**
     * Apply [padding] dp space along the top and bottom edges of the content.
     * ```
     * <ComposableView verticalPadding="16" />
     * ```
     * @param padding int value for padding to be applied on top and bottom edges.
     */
    internal fun verticalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(vertical = padding.toInt().dp))
        }
    }

    /**
     * Apply [padding] dp space along the left and right edges of the content.
     * ```
     * <ComposableView horizontalPadding="16" />
     * ```
     * @param padding int value for padding to be applied on left and right edges.
     */
    internal fun horizontalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(horizontal = padding.toInt().dp))
        }
    }

    /**
     * Declare the preferred height of the content to be exactly [height] dp, or:
     * - use `fill` to occupy the max available height;
     * - or use `wrap` to occupy just the minimum height requested by the children.
     * ```
     * <ComposableView height="fill" />
     * // or
     * <ComposableView height="16" />
     * ```
     * @param height int value for preferred component height.
     */
    private fun height(height: String) = apply {
        modifier = when {
            height.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.height(height.toInt().dp))
            height == FILL -> modifier.then(Modifier.fillMaxHeight())
            height == WRAP -> modifier.then(Modifier.wrapContentHeight())
            else -> modifier
        }
    }

    /**
     * Declare the preferred width of the content to be exactly [width] dp, or:
     * - use `fill` to occupy the max available width;
     * - or use `wrap` to occupy just the minimum width requested by the children.
     * ```
     * <ComposableView width="fill" />
     * // or
     * <ComposableView width="16" />
     * ```
     * @param width int value for preferred component width.
     */
    private fun width(width: String) = apply {
        modifier = when {
            width.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.width(width.toInt().dp))
            width == FILL -> modifier.then(Modifier.fillMaxWidth())
            width == WRAP -> modifier.then(Modifier.wrapContentWidth())
            else -> modifier
        }
    }

    /**
     *  Clip the content to shape.
     * ```
     * <ComposableView clip="circle" />
     * ```
     * @param shape the content will be clipped to this. Supported values are: `circle`,
     * `rectangle`, or an integer representing the curve size applied for all four corners.
     */
    private fun clip(shape: String) = apply {
        modifier = modifier.then(
            Modifier.clip(shapeFromString(shape))
        )
    }

    /**
     * Sets the event name to be triggered on the server when the composable is clicked.
     *
     * ```
     * <Composable phx-click="yourServerEventHandler" />
     * ```
     * @param event event name defined on the server to handle the composable's click.
     * @param pushEvent function responsible to dispatch the server call.
     */
    private fun clickable(event: String, pushEvent: PushEvent?) = apply {
        modifier = modifier.then(
            Modifier.clickable {
                pushEvent?.invoke(EVENT_CLICK_TYPE, event, "", null)
            }
        )
    }

    /**
     * Modify element to allow to scroll when size of the content is bigger than max size available
     * for it.
     *
     * ```
     * <Composable scroll="vertical" />
     * <Composable scroll="both" />
     * ```
     * @param scrolling scroll direction. Supported values are: `vertical`, `horizontal`, and `both`.
     */
    fun scrolling(scrolling: String) = apply {
        hasHorizontalScrolling = scrolling == "horizontal" || scrolling == "both"
        hasVerticalScrolling = scrolling == "vertical" || scrolling == "both"
    }

    /**
     * Draws shape with a solid color behind the content.
     *
     * ```
     * <Composable background="#FF0000FF" />
     * ```
     * @param background the background color in AARRGGBB format.
     */
    private fun background(background: String) = apply {
        if (background.isNotEmpty()) {
            modifier = modifier.then(Modifier.background(background.toColor()))
        }
    }

    /**
     * Returns an `Alignment` object from a String.
     * @param alignment string to be converted to an `Alignment`.
     * @param defaultValue default value to be used in case of [alignment] does not match with
     * any supported value.
     */
    protected fun alignmentFromString(alignment: String, defaultValue: Alignment): Alignment =
        when (alignment) {
            "topStart" -> Alignment.TopStart
            "topCenter" -> Alignment.TopCenter
            "topEnd" -> Alignment.TopEnd
            "centerStart" -> Alignment.CenterStart
            "center" -> Alignment.Center
            "centerEnd" -> Alignment.CenterEnd
            "bottomStart" -> Alignment.BottomStart
            "bottomCenter" -> Alignment.BottomCenter
            "bottomEnd" -> Alignment.BottomEnd
            else -> defaultValue
        }

    /**
     * Returns an `Alignment.Vertical` object from a String.
     * @param alignment string to be converted to an `Alignment.Vertical`.
     * @param defaultValue default value to be used in case of [alignment] does not match with
     * any supported value.
     */
    private fun verticalAlignmentFromString(
        alignment: String,
        defaultValue: Alignment.Vertical = Alignment.Top
    ): Alignment.Vertical =
        when (alignment) {
            "top" -> Alignment.Top
            "center" -> Alignment.CenterVertically
            "bottom" -> Alignment.Bottom
            else -> defaultValue
        }

    /**
     * Returns an `Alignment.Horizontal` object from a String.
     * @param alignment string to be converted to an `Alignment.Horizontal`.
     * @param defaultValue default value to be used in case of [alignment] does not match with
     * any supported value.
     */
    private fun horizontalAlignmentFromString(
        alignment: String,
        defaultValue: Alignment.Horizontal = Alignment.Start
    ): Alignment.Horizontal =
        when (alignment) {
            "start" -> Alignment.Start
            "center" -> Alignment.CenterHorizontally
            "end" -> Alignment.End
            else -> defaultValue
        }

    /**
     * Returns an `ContentScale` object from a String.
     * @param contentScale string to be converted to an `ContentScale`.
     * @param defaultValue default value to be used in case of [contentScale] does not match with
     * any supported value.
     */
    protected fun contentScaleFromString(
        contentScale: String,
        defaultValue: ContentScale = ContentScale.None
    ): ContentScale =
        when (contentScale) {
            "crop" -> ContentScale.Crop
            "fillBounds" -> ContentScale.FillBounds
            "fillHeight" -> ContentScale.FillHeight
            "fillWidth" -> ContentScale.FillWidth
            "fit" -> ContentScale.Fit
            "inside" -> ContentScale.Inside
            "none" -> ContentScale.None
            else -> defaultValue
        }

    /**
     * Handle the properties that are common for most of composables.
     * @param attribute a `CoreAttribute` to be handled.
     * @param pushEvent function responsible to dispatch the server call.
     * @param scope some attributes are composable specific, the scope determine what parent
     * composable (e.g.: `Column`, `Row`, `Box`).
     */
    fun handleCommonAttributes(
        attribute: CoreAttribute,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ComposableBuilder<T> {
        when (attribute.name) {
            ATTR_BACKGROUND -> background(attribute.value)
            ATTR_SIZE -> size(attribute.value)
            ATTR_HEIGHT -> height(attribute.value)
            ATTR_WIDTH -> width(attribute.value)
            ATTR_PADDING -> padding(attribute.value)
            ATTR_HORIZONTAL_PADDING -> horizontalPadding(attribute.value)
            ATTR_VERTICAL_PADDING -> verticalPadding(attribute.value)
            ATTR_CLIP -> clip(attribute.value)
            ATTR_CLICK -> clickable(attribute.value, pushEvent)
        }
        when (scope) {
            is BoxScope -> {
                when (attribute.name) {
                    ATTR_ALIGN -> scope.run {
                        modifier = modifier.then(
                            Modifier.align(alignmentFromString(attribute.value, Alignment.TopStart))
                        )
                    }

                    "matchParentSize" -> scope.run {
                        if (attribute.value.toBoolean()) {
                            modifier = modifier.then(Modifier.matchParentSize())
                        }
                    }
                }
            }

            is ColumnScope -> {
                when (attribute.name) {
                    ATTR_WEIGHT -> scope.run {
                        attribute.value.toFloatOrNull()?.let {
                            modifier = modifier.then(Modifier.weight(it))
                        }
                    }

                    ATTR_ALIGN -> scope.run {
                        modifier = modifier.then(
                            Modifier.align(horizontalAlignmentFromString(attribute.value))
                        )
                    }
                }
            }

            is RowScope -> {
                when (attribute.name) {
                    ATTR_WEIGHT -> scope.run {
                        attribute.value.toFloatOrNull()?.let {
                            modifier = modifier.then(Modifier.weight(it))
                        }
                    }

                    ATTR_ALIGN -> scope.run {
                        modifier = modifier.then(
                            Modifier.align(verticalAlignmentFromString(attribute.value))
                        )
                    }
                }
            }
        }
        return this
    }

    companion object {
        internal const val FILL = "fill"
        internal const val WRAP = "wrap"

        internal const val EVENT_CLICK_TYPE = "click"

        const val ATTR_ALIGN = "align"
        const val ATTR_BACKGROUND = "background"
        const val ATTR_CLICK = "phx-click"
        const val ATTR_CLIP = "clip"
        const val ATTR_HEIGHT = "height"
        const val ATTR_HORIZONTAL_PADDING = "horizontalPadding"
        const val ATTR_PADDING = "padding"
        const val ATTR_SCROLL = "scroll"
        const val ATTR_SIZE = "size"
        const val ATTR_VERTICAL_PADDING = "verticalPadding"
        const val ATTR_WEIGHT = "weight"
        const val ATTR_WIDTH = "width"
    }
}

/**
 * A `ComposableViewFactory` is responsible to create a `ComposableView` using a list of attributes.
 */
abstract class ComposableViewFactory<CV : ComposableView, CB : ComposableBuilder<CV>> {

    /**
     * Create a new instance of a `ComposableView`. Subclasses of this class must override this
     * method and handle specific attributes. Common attributes are handled by the
     * `handleCommonAttributes` declared in the `ComposableBuilder` class and should be called.
     * @param attributes a list of `CoreAttribute` to be handled.
     * @param pushEvent function responsible to dispatch the server call.
     * @param scope some attributes are composable specific, the scope determine what parent
     * composable (e.g.: `Column`, `Row`, `Box`).
     */
    abstract fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): CV

    /**
     * Subclasses of ComposableViewFactory can register subtags specific for a particular component.
     * See ComposableRegistry and ComposableNodeFactory for more details.
     */
    open fun subTags(): Map<String, ComposableViewFactory<*, *>> = emptyMap()
}
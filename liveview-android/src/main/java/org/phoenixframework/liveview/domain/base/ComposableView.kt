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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

abstract class ComposableView(val modifier: Modifier = Modifier) {

    @Composable
    abstract fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    )
}

abstract class ComposableBuilder {
    var hasVerticalScrolling: Boolean = false
    var hasHorizontalScrolling: Boolean = false

    var modifier: Modifier = Modifier
        private set

    private fun size(size: String) = apply {
        modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.size(size = size.toInt().dp))
            size == "fill" -> modifier.then(Modifier.fillMaxSize())
            size == "wrap" -> modifier.then(Modifier.wrapContentSize())
            else -> modifier
        }
    }

    private fun padding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    internal fun verticalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(vertical = padding.toInt().dp))
        }
    }

    internal fun horizontalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(horizontal = padding.toInt().dp))
        }
    }

    private fun height(height: String) = apply {
        modifier = when {
            height.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.height(height.toInt().dp))
            height == "fill" -> modifier.then(Modifier.fillMaxHeight())
            height == "wrap" -> modifier.then(Modifier.wrapContentHeight())
            else -> modifier
        }
    }

    private fun width(width: String) = apply {
        modifier = when {
            width.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.width(width.toInt().dp))
            width == "fill" -> modifier.then(Modifier.fillMaxWidth())
            width == "wrap" -> modifier.then(Modifier.wrapContentWidth())
            else -> modifier
        }
    }

    private fun clickable(event: String, pushEvent: PushEvent?) = apply {
        modifier = modifier.then(
            Modifier.clickable {
                pushEvent?.invoke("click", event, "", null)
            }
        )
    }

    fun scrolling(scrolling: String) = apply {
        val options = scrolling.split('|')
        hasHorizontalScrolling = options.contains("horizontal")
        hasVerticalScrolling = options.contains("vertical")
    }

    private fun background(background: String) = apply {
        if (background.isNotEmpty()) {
            modifier = modifier.then(Modifier.background(background.toColor()))
        }
    }

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

    private fun verticalAlignmentFromString(
        alignment: String,
        defaultValue: Alignment.Vertical
    ): Alignment.Vertical =
        when (alignment) {
            "top" -> Alignment.Top
            "center" -> Alignment.CenterVertically
            "bottom" -> Alignment.Bottom
            else -> defaultValue
        }

    private fun horizontalAlignmentFromString(
        alignment: String,
        defaultValue: Alignment.Horizontal
    ): Alignment.Horizontal =
        when (alignment) {
            "start" -> Alignment.Start
            "center" -> Alignment.CenterHorizontally
            "end" -> Alignment.End
            else -> defaultValue
        }

    protected fun contentScaleFromString(
        contentScale: String,
        defaultValue: ContentScale
    ): ContentScale =
        when (contentScale) {
            "fit" -> ContentScale.Fit
            "crop" -> ContentScale.Crop
            "fillBounds" -> ContentScale.FillBounds
            "fillHeight" -> ContentScale.FillHeight
            "fillWidth" -> ContentScale.FillWidth
            "inside" -> ContentScale.Inside
            else -> defaultValue
        }

    fun processCommonAttributes(
        scope: Any?,
        attribute: CoreAttribute,
        pushEvent: PushEvent?
    ): ComposableBuilder {
        when (attribute.name) {
            "background" -> background(attribute.value)
            "size" -> size(attribute.value)
            "height" -> height(attribute.value)
            "width" -> width(attribute.value)
            "padding" -> padding(attribute.value)
            "horizontalPadding" -> horizontalPadding(attribute.value)
            "verticalPadding" -> verticalPadding(attribute.value)
            //TODO Swift is using `phx-click`. Should Android use the same?
            "phx-click" -> clickable(attribute.value, pushEvent)
        }
        when (scope) {
            is BoxScope -> {
                when (attribute.name) {
                    "align" -> scope.run {
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
                    "weight" -> scope.run {
                        attribute.value.toFloatOrNull()?.let {
                            modifier = modifier.then(Modifier.weight(it))
                        }
                    }

                    "align" -> scope.run {
                        modifier = modifier.then(
                            Modifier.align(
                                horizontalAlignmentFromString(
                                    attribute.value,
                                    Alignment.Start
                                )
                            )
                        )
                    }
                }
            }

            is RowScope -> {
                when (attribute.name) {
                    "weight" -> scope.run {
                        attribute.value.toFloatOrNull()?.let {
                            modifier = modifier.then(Modifier.weight(it))
                        }
                    }

                    "align" -> scope.run {
                        modifier = modifier.then(
                            Modifier.align(
                                verticalAlignmentFromString(
                                    attribute.value,
                                    Alignment.Top
                                )
                            )
                        )
                    }
                }
            }
        }
        return this
    }
}

abstract class ComposableViewFactory<CV : ComposableView, CB : ComposableBuilder> {
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
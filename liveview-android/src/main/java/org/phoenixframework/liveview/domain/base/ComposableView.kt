package org.phoenixframework.liveview.domain.base

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

abstract class ComposableView(var modifier: Modifier = Modifier) {

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

    open fun size(size: String) = apply {
        modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> modifier.size(size = size.toInt().dp)
            size == "fill" -> modifier.fillMaxSize()
            size == "wrap" -> modifier.then(Modifier.wrapContentSize())
            else -> modifier
        }
    }

    open fun padding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    open fun verticalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    open fun horizontalPadding(padding: String) = apply {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    open fun height(height: String) = apply {
        modifier = when {
            height.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.height(height.toInt().dp))
            height == "fill" -> modifier.then(Modifier.fillMaxHeight())
            height == "wrap" -> modifier.then(Modifier.wrapContentHeight())
            else -> modifier
        }
    }

    open fun width(width: String) = apply {
        modifier = when {
            width.isNotEmptyAndIsDigitsOnly() -> modifier.then(Modifier.width(width.toInt().dp))
            width == "fill" -> modifier.then(Modifier.fillMaxWidth())
            width == "wrap" -> modifier.then(Modifier.wrapContentWidth())
            else -> modifier
        }
    }

    fun scrolling(scrolling: String) = apply {
        val options = scrolling.split('|')
        hasHorizontalScrolling = options.contains("horizontal")
        hasVerticalScrolling = options.contains("vertical")
        modifier
    }

    protected fun shapeFromString(shape: String): Shape = when {
        shape.isNotEmptyAndIsDigitsOnly() -> RoundedCornerShape(shape.toInt().dp)
        shape.isNotEmpty() && shape == "circle" -> CircleShape
        shape.isNotEmpty() && shape == "rectangle" -> RectangleShape
        else -> RoundedCornerShape(0.dp)
    }
}

abstract class ComposableViewFactory<CV : ComposableView, CB : ComposableBuilder> {
    abstract fun buildComposableView(
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?
    ): CV
}
package org.phoenixframework.liveview.domain.base

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

abstract class ComposableView(var modifier: Modifier = Modifier) {
}

abstract class ComposableBuilder {
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
}
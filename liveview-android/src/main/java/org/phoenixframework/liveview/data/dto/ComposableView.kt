package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

abstract class ComposableView(var modifier: Modifier = Modifier)

abstract class ComposableBuilder {
    var modifier: Modifier = Modifier
        private set

    open fun size(size: String) = apply {
        modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> modifier.size(size = size.toInt().dp)
            size == "fill" -> modifier.fillMaxSize()
            else -> modifier
        }
    }

    open fun height(height: String) = apply {
        modifier = when {
            height.isNotEmptyAndIsDigitsOnly() -> modifier.height(height.toInt().dp)
            height == "fill" -> modifier.fillMaxHeight()
            else -> modifier
        }
    }

    open fun width(width: String) = apply {
        modifier = when {
            width.isNotEmptyAndIsDigitsOnly() -> modifier.width(width.toInt().dp)
            width == "fill" -> modifier.fillMaxWidth()
            else -> modifier
        }
    }
}
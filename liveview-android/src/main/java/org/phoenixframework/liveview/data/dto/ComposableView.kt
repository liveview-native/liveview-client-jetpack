package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

abstract class ComposableView {


}

abstract class ComposableBuilder() {
    private var modifier: Modifier = Modifier.alpha(1f)
    fun setSize(size: String) {
        this.modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> {
                modifier.then(Modifier.size(size = size.toInt().dp))
            }
            size == "fill" -> {
                modifier.then(Modifier.fillMaxSize())
            }
            else -> {
                modifier
            }
        }
    }

    fun setHeight(height: String) {
        this.modifier = when {
            height.isNotEmptyAndIsDigitsOnly() -> {
                modifier.then(Modifier.height(height.toInt().dp))
            }
            height == "fill" -> {
                modifier.then(
                    Modifier.fillMaxHeight()
                )
            }
            else -> {
                modifier
            }
        }
    }

    fun setWidth(width: String) {
        modifier = when {
            width.isNotEmptyAndIsDigitsOnly() -> {
                modifier.then(Modifier.width(width.toInt().dp))
            }
            width == "fill" -> {
                modifier.then(Modifier.fillMaxWidth())
            }
            else -> {
                modifier
            }
        }
    }

    fun getModifier() = modifier
}
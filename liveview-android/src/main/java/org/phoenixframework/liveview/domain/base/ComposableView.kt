package org.phoenixframework.liveview.domain.base

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

abstract class ComposableView {
    val id = System.currentTimeMillis().toString()
    var modifier: Modifier = Modifier


}

abstract class ComposableBuilder() {
    private var modifier: Modifier = Modifier
      var scrollable: Boolean = false
    fun setSize(size: String) {
        this.modifier = when {
            size.isNotEmptyAndIsDigitsOnly() -> {
                modifier.then(Modifier.size(size = size.toInt().dp))
            }
            size == "fill" -> {
                modifier.then(Modifier.fillMaxSize())
            }
            size == "wrap" -> {
                modifier.then(
                    Modifier.wrapContentSize()
                )
            }
            else -> {
                modifier
            }
        }
    }

    fun setPadding(padding: String) {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            this.modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }

    }

    fun setVerticalPadding(padding: String) {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            this.modifier = modifier.then(Modifier.padding(padding.toInt().dp))
        }
    }

    fun setHorizontalPadding(padding: String) {
        if (padding.isNotEmptyAndIsDigitsOnly()) {
            this.modifier = modifier.then(Modifier.padding(padding.toInt().dp))
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
            height == "wrap" -> {
                modifier.then(
                    Modifier.wrapContentHeight()
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
            width == "wrap" -> {
                modifier.then(
                    Modifier.wrapContentWidth()
                )
            }
            else -> {
                modifier
            }
        }
    }

    fun getModifier() = modifier
    fun setScrollable(value: String) {

           scrollable = value.toBoolean()
    }

}
package org.phoenixframework.liveview.data.dto

import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

abstract class LazyComposableBuilder : ComposableBuilder() {
    var contentPadding: MutableMap<String, Int> = mutableMapOf()
    var reverseLayout: Boolean = false

    fun reverseLayout(isReverseLayout: String) = apply {
        if (isReverseLayout.isNotEmpty()) {
            reverseLayout = isReverseLayout.toBoolean()
        }
    }

    fun rightPadding(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[RIGHT] = paddingValue.toInt()
        }
    }

    fun leftPadding(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[LEFT] = paddingValue.toInt()
        }
    }

    fun topPadding(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[TOP] = paddingValue.toInt()
        }
    }

    fun bottomPadding(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[BOTTOM] = paddingValue.toInt()
        }
    }

    fun lazyListItemPadding(paddingValue: String) = apply {
        topPadding(paddingValue)
        leftPadding(paddingValue)
        bottomPadding(paddingValue)
        rightPadding(paddingValue)
    }

    companion object {
        const val TOP = "top"
        const val LEFT = "left"
        const val BOTTOM = "bottom"
        const val RIGHT = "right"
    }
}
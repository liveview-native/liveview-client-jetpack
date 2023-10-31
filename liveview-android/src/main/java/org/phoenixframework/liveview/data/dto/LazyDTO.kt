package org.phoenixframework.liveview.data.dto

import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

abstract class LazyComposableBuilder<T : ComposableView> : ComposableBuilder<T>() {
    var contentPadding: MutableMap<String, Int> = mutableMapOf()
    var reverseLayout: Boolean = false

    /**
     * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
     * order which means that column is scrolled to the bottom.
     */
    fun reverseLayout(isReverseLayout: String) = apply {
        if (isReverseLayout.isNotEmpty()) {
            reverseLayout = isReverseLayout.toBoolean()
        }
    }

    /**
     * A padding to be applied to the end edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on end edge of the items.
     */
    fun itemPaddingEnd(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[END] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the start edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on start edge of the items.
     */
    fun itemPaddingStart(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[START] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the top edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    fun itemPaddingTop(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[TOP] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the bottom edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    fun itemPaddingBottom(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[BOTTOM] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the start and end edges of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on start and end edges of the items.
     */
    fun itemPaddingHorizontal(paddingValue: String) = apply {
        itemPaddingStart(paddingValue)
        itemPaddingEnd(paddingValue)
    }

    /**
     * A padding to be applied to the top and bottom edges of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top and bottom edges of the items.
     */
    fun itemPaddingVertical(paddingValue: String) = apply {
        itemPaddingTop(paddingValue)
        itemPaddingBottom(paddingValue)
    }

    /**
     * A padding to be applied to the four edges of the lazy list item.
     *
     * @param paddingValue int value for padding to be applied to list items.
     */
    fun itemPadding(paddingValue: String) = apply {
        itemPaddingTop(paddingValue)
        itemPaddingStart(paddingValue)
        itemPaddingBottom(paddingValue)
        itemPaddingEnd(paddingValue)
    }

    /**
     * Handles Lazy Lists common attributes.
     *
     * @param attribute attribute to be handled.
     */
    fun handleLazyAttribute(attribute: CoreAttribute): Boolean {
        var result = true
        when (attribute.name) {
            "reverseLayout" -> reverseLayout(attribute.value)
            "itemPadding" -> itemPadding(attribute.value)
            "itemPaddingHorizontal" -> itemPaddingHorizontal(attribute.value)
            "itemPaddingVertical" -> itemPaddingVertical(attribute.value)
            "itemPaddingStart" -> itemPaddingStart(attribute.value)
            "itemPaddingTop" -> itemPaddingTop(attribute.value)
            "itemPaddingEnd" -> itemPaddingEnd(attribute.value)
            "itemPaddingBottom" -> itemPaddingBottom(attribute.value)
            else -> result = false
        }
        return result
    }

    companion object {
        const val TOP = "top"
        const val START = "start"
        const val BOTTOM = "bottom"
        const val END = "end"
    }
}
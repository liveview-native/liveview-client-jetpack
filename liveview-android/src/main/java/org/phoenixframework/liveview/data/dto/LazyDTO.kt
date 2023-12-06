package org.phoenixframework.liveview.data.dto

import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingBottom
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingEnd
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingHorizontal
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingStart
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingTop
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPaddingVertical
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrUserScrollEnabled
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

/**
 * Parent class of lazy lists like LazyColumn and LazyRow. This class holds some common attributes
 * for lazy lists.
 */
internal abstract class LazyComposableBuilder : ComposableBuilder() {
    var contentPadding: MutableMap<String, Int> = mutableMapOf()
    var reverseLayout: Boolean = false
    var userScrollEnabled: Boolean = true

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
     * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
     * order which means that column is scrolled to the bottom.
     */
    fun userScrollEnabled(userScrollEnabled: String) = apply {
        if (userScrollEnabled.isNotEmpty()) {
            this.userScrollEnabled = userScrollEnabled.toBoolean()
        }
    }

    /**
     * A padding to be applied to the end edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on end edge of the items.
     */
    fun contentPaddingEnd(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[END] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the start edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on start edge of the items.
     */
    fun contentPaddingStart(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[START] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the top edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    fun contentPaddingTop(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[TOP] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the bottom edge of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    fun contentPaddingBottom(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[BOTTOM] = paddingValue.toInt()
        }
    }

    /**
     * A padding to be applied to the start and end edges of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on start and end edges of the items.
     */
    fun contentPaddingHorizontal(paddingValue: String) = apply {
        contentPaddingStart(paddingValue)
        contentPaddingEnd(paddingValue)
    }

    /**
     * A padding to be applied to the top and bottom edges of the lazy list items.
     *
     * @param paddingValue int value for padding to be applied on top and bottom edges of the items.
     */
    fun contentPaddingVertical(paddingValue: String) = apply {
        contentPaddingTop(paddingValue)
        contentPaddingBottom(paddingValue)
    }

    /**
     * A padding to be applied to the four edges of the lazy list item.
     *
     * @param paddingValue int value for padding to be applied to list items.
     */
    fun contentPadding(paddingValue: String) = apply {
        contentPaddingTop(paddingValue)
        contentPaddingStart(paddingValue)
        contentPaddingBottom(paddingValue)
        contentPaddingEnd(paddingValue)
    }

    /**
     * Handles Lazy Lists common attributes.
     *
     * @param attribute attribute to be handled.
     */
    fun handleLazyAttribute(attribute: CoreAttribute): Boolean {
        var result = true
        when (attribute.name) {
            attrContentPadding -> contentPadding(attribute.value)
            attrContentPaddingBottom -> contentPaddingBottom(attribute.value)
            attrContentPaddingEnd -> contentPaddingEnd(attribute.value)
            attrContentPaddingHorizontal -> contentPaddingHorizontal(attribute.value)
            attrContentPaddingStart -> contentPaddingStart(attribute.value)
            attrContentPaddingTop -> contentPaddingTop(attribute.value)
            attrContentPaddingVertical -> contentPaddingVertical(attribute.value)
            attrReverseLayout -> reverseLayout(attribute.value)
            attrUserScrollEnabled -> userScrollEnabled(attribute.value)
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
package org.phoenixframework.liveview.ui.view

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
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
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

internal interface ILazyListProperties : ComposableProperties {
    val lazyListProps: LazyListProperties
}

@Stable
internal data class LazyListProperties(
    val contentPadding: ImmutableMap<String, Int> = persistentMapOf(),
    val reverseLayout: Boolean = false,
    val userScrollEnabled: Boolean = true,
)

/**
 * Parent class of lazy lists like LazyColumn and LazyRow. This class holds some common attributes
 * for lazy lists.
 */
internal abstract class LazyComposableBuilder : ComposableBuilder() {
    var lazyListProps = LazyListProperties()
    private var contentPadding: MutableMap<String, Int> = mutableMapOf()

    /**
     * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
     * order which means that column is scrolled to the bottom.
     */
    fun reverseLayout(isReverseLayout: String) = apply {
        if (isReverseLayout.isNotEmpty()) {
            this.lazyListProps =
                this.lazyListProps.copy(reverseLayout = isReverseLayout.toBoolean())
        }
    }

    /**
     * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
     * order which means that column is scrolled to the bottom.
     */
    private fun userScrollEnabled(userScrollEnabled: String) = apply {
        if (userScrollEnabled.isNotEmpty()) {
            this.lazyListProps =
                this.lazyListProps.copy(userScrollEnabled = userScrollEnabled.toBoolean())
        }
    }

    /**
     * A padding to be applied to the end edge of the lazy list items.
     * ```
     * <LazyColumn contentPaddingEnd="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on end edge of the items.
     */
    private fun contentPaddingEnd(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[END] = paddingValue.toInt()
            this.lazyListProps =
                this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
        }
    }

    /**
     * A padding to be applied to the start edge of the lazy list items.
     * ```
     * <LazyColumn contentPaddingStart="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on start edge of the items.
     */
    private fun contentPaddingStart(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[START] = paddingValue.toInt()
            this.lazyListProps =
                this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
        }
    }

    /**
     * A padding to be applied to the top edge of the lazy list items.
     * ```
     * <LazyColumn contentPaddingTop="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    private fun contentPaddingTop(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[TOP] = paddingValue.toInt()
            this.lazyListProps =
                this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
        }
    }

    /**
     * A padding to be applied to the bottom edge of the lazy list items.
     * ```
     * <LazyColumn contentPaddingBottom="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on top edge of the items.
     */
    private fun contentPaddingBottom(paddingValue: String) = apply {
        if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
            contentPadding[BOTTOM] = paddingValue.toInt()
            this.lazyListProps =
                this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
        }
    }

    /**
     * A padding to be applied to the start and end edges of the lazy list items.
     * ```
     * <LazyColumn contentPaddingHorizontal="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on start and end edges of the items.
     */
    private fun contentPaddingHorizontal(paddingValue: String) = apply {
        contentPaddingStart(paddingValue)
        contentPaddingEnd(paddingValue)
        this.lazyListProps =
            this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
    }

    /**
     * A padding to be applied to the top and bottom edges of the lazy list items.
     * ```
     * <LazyColumn contentPaddingVertical="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied on top and bottom edges of the items.
     */
    private fun contentPaddingVertical(paddingValue: String) = apply {
        contentPaddingTop(paddingValue)
        contentPaddingBottom(paddingValue)
        this.lazyListProps =
            this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
    }

    /**
     * A padding to be applied to the four edges of the lazy list item.
     * ```
     * <LazyColumn contentPadding="16">...</LazyColumn>
     * ```
     * @param paddingValue int value for padding to be applied to list items.
     */
    private fun contentPadding(paddingValue: String) = apply {
        contentPaddingTop(paddingValue)
        contentPaddingStart(paddingValue)
        contentPaddingBottom(paddingValue)
        contentPaddingEnd(paddingValue)
        this.lazyListProps =
            this.lazyListProps.copy(contentPadding = contentPadding.toImmutableMap())
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
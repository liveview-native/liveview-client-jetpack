package org.phoenixframework.liveview.ui.view

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingBottom
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingEnd
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingHorizontal
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingStart
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingTop
import org.phoenixframework.liveview.constants.Attrs.attrContentPaddingVertical
import org.phoenixframework.liveview.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.constants.Attrs.attrUserScrollEnabled
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory

/**
 * Parent class of `AlertDialog` and `DatePickerDialog`.
 */
internal abstract class LazyView<LLP : LazyView.ILazyListProperties>(props: LLP) :
    ComposableView<LLP>(props) {

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
    internal abstract class Factory : ComposableViewFactory<LazyView<*>>() {

        /**
         * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
         * order which means that column is scrolled to the bottom.
         */
        fun reverseLayout(
            lazyListProps: LazyListProperties,
            isReverseLayout: String
        ): LazyListProperties {
            return if (isReverseLayout.isNotEmpty()) {
                lazyListProps.copy(reverseLayout = isReverseLayout.toBoolean())
            } else lazyListProps
        }

        /**
         * Reverse the direction of scrolling and layout. When true, items are laid out in the reverse
         * order which means that column is scrolled to the bottom.
         */
        private fun userScrollEnabled(
            lazyListProps: LazyListProperties,
            userScrollEnabled: String
        ): LazyListProperties {
            return if (userScrollEnabled.isNotEmpty()) {
                lazyListProps.copy(userScrollEnabled = userScrollEnabled.toBoolean())
            } else lazyListProps
        }

        /**
         * A padding to be applied to the end edge of the lazy list items.
         * ```
         * <LazyColumn contentPaddingEnd="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on end edge of the items.
         */
        private fun contentPaddingEnd(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                lazyListProps.copy(
                    contentPadding = lazyListProps.contentPadding
                        .toMutableMap()
                        .apply {
                            set(END, paddingValue.toInt())
                        }.toImmutableMap()
                )
            } else lazyListProps
        }

        /**
         * A padding to be applied to the start edge of the lazy list items.
         * ```
         * <LazyColumn contentPaddingStart="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on start edge of the items.
         */
        private fun contentPaddingStart(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                lazyListProps.copy(
                    contentPadding = lazyListProps.contentPadding
                        .toMutableMap()
                        .apply {
                            set(START, paddingValue.toInt())
                        }.toImmutableMap()
                )
            } else lazyListProps
        }

        /**
         * A padding to be applied to the top edge of the lazy list items.
         * ```
         * <LazyColumn contentPaddingTop="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on top edge of the items.
         */
        private fun contentPaddingTop(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                lazyListProps.copy(
                    contentPadding = lazyListProps.contentPadding
                        .toMutableMap()
                        .apply {
                            set(TOP, paddingValue.toInt())
                        }.toImmutableMap()
                )
            } else lazyListProps
        }

        /**
         * A padding to be applied to the bottom edge of the lazy list items.
         * ```
         * <LazyColumn contentPaddingBottom="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on top edge of the items.
         */
        private fun contentPaddingBottom(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                lazyListProps.copy(
                    contentPadding = lazyListProps.contentPadding
                        .toMutableMap()
                        .apply {
                            set(BOTTOM, paddingValue.toInt())
                        }.toImmutableMap()
                )
            } else lazyListProps
        }

        /**
         * A padding to be applied to the start and end edges of the lazy list items.
         * ```
         * <LazyColumn contentPaddingHorizontal="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on start and end edges of the items.
         */
        private fun contentPaddingHorizontal(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return contentPaddingStart(lazyListProps, paddingValue).also {
                contentPaddingEnd(it, paddingValue)
            }
        }

        /**
         * A padding to be applied to the top and bottom edges of the lazy list items.
         * ```
         * <LazyColumn contentPaddingVertical="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied on top and bottom edges of the items.
         */
        private fun contentPaddingVertical(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return contentPaddingTop(lazyListProps, paddingValue).also {
                contentPaddingBottom(it, paddingValue)
            }
        }

        /**
         * A padding to be applied to the four edges of the lazy list item.
         * ```
         * <LazyColumn contentPadding="16">...</LazyColumn>
         * ```
         * @param paddingValue int value for padding to be applied to list items.
         */
        private fun contentPadding(
            lazyListProps: LazyListProperties,
            paddingValue: String
        ): LazyListProperties {
            return contentPaddingTop(lazyListProps, paddingValue).also {
                contentPaddingStart(it, paddingValue)
            }.also {
                contentPaddingBottom(it, paddingValue)
            }.also {
                contentPaddingEnd(it, paddingValue)
            }
        }

        /**
         * Handles Lazy Lists common attributes.
         *
         * @param attribute attribute to be handled.
         */
        fun handleLazyAttribute(
            lazyListProps: LazyListProperties,
            attribute: CoreAttribute
        ): LazyListProperties? {
            return when (attribute.name) {
                attrContentPadding -> contentPadding(lazyListProps, attribute.value)
                attrContentPaddingBottom -> contentPaddingBottom(lazyListProps, attribute.value)
                attrContentPaddingEnd -> contentPaddingEnd(lazyListProps, attribute.value)
                attrContentPaddingHorizontal -> contentPaddingHorizontal(
                    lazyListProps,
                    attribute.value
                )

                attrContentPaddingStart -> contentPaddingStart(lazyListProps, attribute.value)
                attrContentPaddingTop -> contentPaddingTop(lazyListProps, attribute.value)
                attrContentPaddingVertical -> contentPaddingVertical(lazyListProps, attribute.value)
                attrReverseLayout -> reverseLayout(lazyListProps, attribute.value)
                attrUserScrollEnabled -> userScrollEnabled(lazyListProps, attribute.value)
                else -> null
            }
        }

        companion object {
            const val TOP = "top"
            const val START = "start"
            const val BOTTOM = "bottom"
            const val END = "end"
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * The horizontally scrolling list that only composes and lays out the currently visible items.
 * ```
 * <LazyRow style="fillMaxWidth()" horizontalArrangement="start" verticalAlignment="center" >
 *   // Children
 * </LazyRow>
 * ```
 */
internal class LazyRowView private constructor(props: Properties) :
    ComposableView<LazyRowView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val horizontalArrangement = props.horizontalArrangement
        val verticalAlignment = props.verticalAlignment
        val reverseLayout = props.lazyListProps.reverseLayout
        val userScrollEnabled = props.lazyListProps.userScrollEnabled
        val contentPadding = props.lazyListProps.contentPadding

        LazyRow(
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            contentPadding = PaddingValues(
                (contentPadding[LazyComposableBuilder.START] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.TOP] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.END] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.BOTTOM] ?: 0).dp
            ),
            userScrollEnabled = userScrollEnabled,
            content = {
                items(
                    composableNode?.children ?: emptyArray(),
                    key = { item -> item.id },
                ) { item ->
                    PhxLiveView(item, pushEvent, composableNode, null, this)
                }
            },
        )
    }

    @Stable
    internal data class Properties(
        val horizontalArrangement: Arrangement.Horizontal,
        val verticalAlignment: Alignment.Vertical,
        override val commonProps: CommonComposableProperties,
        override val lazyListProps: LazyListProperties,
    ) : ILazyListProperties

    internal class Builder : LazyComposableBuilder() {
        private var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
        private var verticalAlignment: Alignment.Vertical = Alignment.CenterVertically

        /**
         * The horizontal arrangement of the Row's children
         *
         * ```
         * <LazyRow horizontalArrangement="spaceAround" >...</LazyRow>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.HorizontalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = horizontalArrangementFromString(horizontalArrangement)
            }
        }

        /**
         * The vertical alignment of the Row's children
         *
         * ```
         * <LazyRow verticalAlignment="center" >...</LazyRow>
         * ```
         * @param verticalAlignment the vertical alignment of the row's children. See the supported
         * values at [org.phoenixframework.liveview.data.constants.VerticalAlignmentValues].
         */
        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = verticalAlignmentFromString(verticalAlignment)
            }
        }

        fun build() = LazyRowView(
            Properties(
                horizontalArrangement,
                verticalAlignment,
                commonProps,
                lazyListProps,
            )
        )
    }
}

internal object LazyRowViewFactory : ComposableViewFactory<LazyRowView>() {
    /**
     * Creates a `LazyRowView` object based on the attributes of the input `Attributes` object.
     * LazyRowView co-relates to the LazyRow composable
     * @param attributes the `Attributes` object to create the `LazyRowView` object from
     * @return a `LazyRowView` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LazyRowView = LazyRowView.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleLazyAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrHorizontalArrangement -> builder.horizontalArrangement(attribute.value)
                    attrVerticalAlignment -> builder.verticalAlignment(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as LazyRowView.Builder
            }
        }
    }.build()
}
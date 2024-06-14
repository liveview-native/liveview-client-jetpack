package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * ```
 * <LazyColumn style="fillMaxWidth()" verticalArrangement="center" horizontalAlignment="center">
 *   // Children
 * </LazyColumn>
 * ```
 */
internal class LazyColumnView private constructor(props: Properties) :
    ComposableView<LazyColumnView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val verticalArrangement = props.verticalArrangement
        val horizontalAlignment = props.horizontalAlignment
        val reverseLayout = props.lazyListProps.reverseLayout
        val userScrollEnabled = props.lazyListProps.userScrollEnabled
        val contentPadding = props.lazyListProps.contentPadding

        LazyColumn(
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            contentPadding = PaddingValues(
                (contentPadding[LazyComposableBuilder.START] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.TOP] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.END] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.BOTTOM] ?: 0).dp
            ),
            // TODO flingBehavior = ,
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
        val verticalArrangement: Arrangement.Vertical,
        val horizontalAlignment: Alignment.Horizontal,
        override val commonProps: CommonComposableProperties,
        override val lazyListProps: LazyListProperties,
    ) : ILazyListProperties

    class Builder : LazyComposableBuilder() {
        private var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        private var horizontalAlignment: Alignment.Horizontal = Alignment.Start

        /**
         * The vertical arrangement of the Column's children
         *
         * ```
         * <LazyColumn verticalArrangement="spaceAround" >...</LazyColumn>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.VerticalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = verticalArrangementFromString(verticalArrangement)
        }

        /**
         * The horizontal alignment of the Column's children
         *
         * ```
         * <LazyColumn horizontalAlignment="center" >...</LazyColumn>
         * ```
         * @param horizontalAlignment the horizontal alignment of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues].
         */
        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = horizontalAlignmentFromString(horizontalAlignment)
        }

        fun build() = LazyColumnView(
            Properties(
                verticalArrangement,
                horizontalAlignment,
                commonProps,
                lazyListProps
            )
        )
    }
}

internal object LazyColumnViewFactory : ComposableViewFactory<LazyColumnView>() {
    /**
     * Creates a `LazyColumnView` object based on the attributes of the input `Attributes` object.
     * LazyColumnView co-relates to the LazyColumn composable
     * @param attributes the `Attributes` object to create the `LazyColumnView` object from
     * @return a `LazyColumnView` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LazyColumnView = LazyColumnView.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleLazyAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrHorizontalAlignment -> builder.horizontalAlignment(attribute.value)
                    attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as LazyColumnView.Builder
            }
        }
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.dto.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.dto.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * ```
 * <LazyColumn width="fill" vertical-arrangement="center" horizontal-alignment="center">
 *   // Children
 * </LazyColumn>
 * ```
 */
internal class LazyColumnDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    private val horizontalAlignment: Alignment.Horizontal = builder.horizontalAlignment
    private val contentPadding: ImmutableMap<String, Int> = builder.contentPadding.toImmutableMap()
    private val reverseLayout: Boolean = builder.reverseLayout

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        LazyColumn(
            modifier = modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            contentPadding = PaddingValues(
                (contentPadding[LazyComposableBuilder.START] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.TOP] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.END] ?: 0).dp,
                (contentPadding[LazyComposableBuilder.BOTTOM] ?: 0).dp
            ),
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

    class Builder : LazyComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
            private set
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start
            private set

        /**
         * The vertical arrangement of the Column's children
         *
         * ```
         * <LazyColumn vertical-arrangement="spaceAround" >...</LazyColumn>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. The
         * supported values are: `top`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `bottom`,
         * and `center`. An int value is also supported, which will be used to determine the space.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = when (verticalArrangement) {
                "top" -> Arrangement.Top
                "spaceEvenly" -> Arrangement.SpaceEvenly
                "spaceAround" -> Arrangement.SpaceAround
                "spaceBetween" -> Arrangement.SpaceBetween
                "bottom" -> Arrangement.Bottom
                else -> if (verticalArrangement.isNotEmptyAndIsDigitsOnly()) {
                    Arrangement.spacedBy(verticalArrangement.toInt().dp)
                } else {
                    Arrangement.Center
                }
            }
        }

        /**
         * The horizontal alignment of the Column's children
         *
         * ```
         * <LazyColumn horizontal-alignment="center" >...</LazyColumn>
         * ```
         * @param horizontalAlignment the horizontal alignment of the column's children. The
         * supported values are: `start`, `center`, and `end`.
         */
        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = when (horizontalAlignment) {
                "start" -> Alignment.Start
                "center" -> Alignment.CenterHorizontally
                "end" -> Alignment.End
                else -> Alignment.Start
            }
        }

        fun build() = LazyColumnDTO(this)
    }
}

internal object LazyColumnDtoFactory :
    ComposableViewFactory<LazyColumnDTO, LazyColumnDTO.Builder>() {
    /**
     * Creates a `LazyColumnDTO` object based on the attributes of the input `Attributes` object.
     * LazyColumnDTO co-relates to the LazyColumn composable
     * @param attributes the `Attributes` object to create the `LazyColumnDTO` object from
     * @return a `LazyColumnDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LazyColumnDTO = LazyColumnDTO.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleLazyAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrHorizontalAlignment -> builder.horizontalAlignment(attribute.value)
                    attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as LazyColumnDTO.Builder
            }
        }
    }.build()
}
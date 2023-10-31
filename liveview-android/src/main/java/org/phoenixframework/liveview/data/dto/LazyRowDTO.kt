package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

internal class LazyRowDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val horizontalArrangement: Arrangement.Horizontal = builder.horizontalArrangement
    private val verticalAlignment: Alignment.Vertical = builder.verticalAlignment
    private val contentPadding: ImmutableMap<String, Int> = builder.contentPadding.toImmutableMap()
    private val reverseLayout: Boolean = builder.reverseLayout

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        LazyRow(
            modifier = modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
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

    internal class Builder : LazyComposableBuilder<LazyRowDTO>() {
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround
            private set
        var verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
            private set

        /**
         * The horizontal arrangement of the Row's children
         *
         * ```
         * <LazyRow horizontalArrangement="spaceAround" >...</Column>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the column's children. The
         * supported values are: `start`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `end`,
         * and `center`. An int value is also supported, which will be used to determine the space.
         */
        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = when (horizontalArrangement) {
                    "spacedEvenly" -> Arrangement.SpaceEvenly
                    "spaceAround" -> Arrangement.SpaceAround
                    "spaceBetween" -> Arrangement.SpaceBetween
                    "start" -> Arrangement.Start
                    "end" -> Arrangement.End
                    else -> if (horizontalArrangement.isNotEmptyAndIsDigitsOnly()) {
                        Arrangement.spacedBy(horizontalArrangement.toInt().dp)
                    } else {
                        Arrangement.Center
                    }
                }
            }
        }

        /**
         * The vertical alignment of the Row's children
         *
         * ```
         * <LazyRow verticalAlignment="center" >...</Column>
         * ```
         * @param verticalAlignment the vertical alignment of the row's children. The
         * supported values are: `top`, `center`, and `bottom`.
         */
        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = when (verticalAlignment) {
                    "top" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    else -> Alignment.Bottom
                }
            }
        }

        override fun build() = LazyRowDTO(this)
    }
}

internal object LazyRowDtoFactory : ComposableViewFactory<LazyRowDTO, LazyRowDTO.Builder>() {
    /**
     * Creates a `LazyRowDTO` object based on the attributes of the input `Attributes` object.
     * Row co-relates to the LazyRow composable
     * @param attributes the `Attributes` object to create the `LazyRowDTO` object from
     * @return a `LazyRowDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LazyRowDTO = LazyRowDTO.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleLazyAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    "horizontalArrangement" -> builder.horizontalArrangement(attribute.value)
                    "verticalAlignment" -> builder.verticalAlignment(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as LazyRowDTO.Builder
            }
        }
    }.build()
}
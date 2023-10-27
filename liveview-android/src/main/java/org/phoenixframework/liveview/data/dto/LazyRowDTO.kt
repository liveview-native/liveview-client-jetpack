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

class LazyRowDTO private constructor(builder: Builder) :
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
                (contentPadding["left"] ?: 0).dp,
                (contentPadding["top"] ?: 0).dp,
                (contentPadding["right"] ?: 0).dp,
                (contentPadding["bottom"] ?: 0).dp
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
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround
        var verticalAlignment: Alignment.Vertical = Alignment.CenterVertically

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

        fun verticalAlignment(verticalAlignment: String) = apply {
            if (verticalAlignment.isNotEmpty()) {
                this.verticalAlignment = when (verticalAlignment) {
                    "top" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    else -> Alignment.Bottom
                }
            }
        }

        fun build() = LazyRowDTO(this)
    }
}

object LazyRowDtoFactory : ComposableViewFactory<LazyRowDTO, LazyRowDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LazyRowDTO =
        attributes.fold(LazyRowDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                "horizontalArrangement" -> builder.horizontalArrangement(attribute.value)
                "itemBottomPadding" -> builder.bottomPadding(attribute.value)
                "itemHorizontalPadding" -> builder.horizontalPadding(attribute.value)
                "itemLeftPadding" -> builder.leftPadding(attribute.value)
                "itemPadding" -> builder.lazyListItemPadding(attribute.value)
                "itemRightPadding" -> builder.rightPadding(attribute.value)
                "itemTopPadding" -> builder.topPadding(attribute.value)
                "itemVerticalPadding" -> builder.verticalPadding(attribute.value)
                "reverseLayout" -> builder.reverseLayout(attribute.value)
                "verticalAlignment" -> builder.verticalAlignment(attribute.value)
                "verticalPadding" -> builder.verticalPadding(attribute.value)
                else -> builder.processCommonAttributes(scope, attribute, pushEvent)
            } as LazyRowDTO.Builder
        }.build()
}
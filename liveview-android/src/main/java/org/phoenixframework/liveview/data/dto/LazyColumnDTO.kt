package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class LazyColumnDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    private val horizontalAlignment: Alignment.Horizontal = builder.horizontalAlignment

    // content padding is a pair of pairs,
    // the first pair is the horizontal padding,
    // the second pair is the vertical padding
    private val contentPadding: Pair<Pair<Int, Int>, Pair<Int, Int>> = builder.contentPadding

    private val reverseLayout: Boolean = builder.reverseLayout

    @Composable
    override fun Compose(
        children: ImmutableList<ComposableTreeNode>?, paddingValues: PaddingValues?
    ) {
        LazyColumn(
            modifier = modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            contentPadding = PaddingValues(
                contentPadding.first.first.dp,
                contentPadding.second.first.dp,
                contentPadding.first.second.dp,
                contentPadding.second.second.dp
            ),
            content = {
                items(
                    children ?: emptyList(),
                    key = { item -> item.id },
                ) { item ->
                    item.value.Compose(
                        children = item.children,
                        paddingValues = null
                    )
                }
            },
        )
    }

    class Builder : ComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start
        var contentPadding: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0, 0), Pair(0, 0))
        var reverseLayout: Boolean = false

        fun reverseLayout(isReverseLayout: String) = apply {
            if (isReverseLayout.isNotEmpty()) {
                this.reverseLayout = isReverseLayout.toBoolean()
            }
        }

        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = when (verticalArrangement) {
                "top" -> Arrangement.Top
                "spaceEvenly" -> Arrangement.SpaceEvenly
                "spaceAround" -> Arrangement.SpaceAround
                "spaceBetween" -> Arrangement.SpaceBetween
                "bottom" -> Arrangement.Bottom
                "center" -> Arrangement.Center
                else -> Arrangement.spacedBy(verticalArrangement.toInt().dp)
            }
        }

        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = when (horizontalAlignment) {
                "start" -> Alignment.Start
                "center" -> Alignment.CenterHorizontally
                "end" -> Alignment.End
                else -> Alignment.Start
            }
        }

        fun rightPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding = Pair(
                    Pair(contentPadding.first.first, paddingValue.toInt()),
                    Pair(contentPadding.second.first, contentPadding.second.second)
                )
            }
        }

        fun leftPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding = Pair(
                    Pair(paddingValue.toInt(), contentPadding.first.second),
                    Pair(contentPadding.second.first, contentPadding.second.second)
                )
            }
        }

        fun topPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding = Pair(
                    Pair(contentPadding.first.first, contentPadding.first.second),
                    Pair(paddingValue.toInt(), contentPadding.second.second)
                )
            }
        }

        fun bottomPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding = Pair(
                    Pair(contentPadding.first.first, contentPadding.first.second),
                    Pair(contentPadding.second.first, paddingValue.toInt())
                )
            }
        }

        fun lazyColumnItemPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding = Pair(
                    Pair(paddingValue.toInt(), paddingValue.toInt()),
                    Pair(paddingValue.toInt(), paddingValue.toInt())
                )
            }
        }

        fun build() = LazyColumnDTO(this)
    }
}

object LazyColumnDtoFactory : ComposableViewFactory<LazyColumnDTO, LazyColumnDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?
    ): LazyColumnDTO = attributes.fold(LazyColumnDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "height" -> builder.height(attribute.value)
            "horizontalAlignment" -> builder.horizontalAlignment(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "itemBottomPadding" -> builder.bottomPadding(attribute.value)
            "itemHorizontalPadding" -> builder.horizontalPadding(attribute.value)
            "itemLeftPadding" -> builder.leftPadding(attribute.value)
            "itemPadding" -> builder.lazyColumnItemPadding(attribute.value)
            "itemRightPadding" -> builder.rightPadding(attribute.value)
            "itemTopPadding" -> builder.topPadding(attribute.value)
            "itemVerticalPadding" -> builder.verticalPadding(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "reverseLayout" -> builder.reverseLayout(attribute.value)
            "size" -> builder.size(attribute.value)
            "verticalArrangement" -> builder.verticalArrangement(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            "width" -> builder.width(attribute.value)
            else -> builder
        } as LazyColumnDTO.Builder
    }.build()
}
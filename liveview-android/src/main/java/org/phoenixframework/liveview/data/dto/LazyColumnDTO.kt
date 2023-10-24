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
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class LazyColumnDTO private constructor(builder: Builder) :
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
                    PhxLiveView(item, null, pushEvent)
                }
            },
        )
    }

    class Builder : ComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start
        var contentPadding: MutableMap<String, Int> = mutableMapOf()
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
                contentPadding["right"] = paddingValue.toInt()
            }
        }

        fun leftPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding["left"] = paddingValue.toInt()
            }
        }

        fun topPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding["top"] = paddingValue.toInt()
            }
        }

        fun bottomPadding(paddingValue: String) = apply {
            if (paddingValue.isNotEmptyAndIsDigitsOnly()) {
                contentPadding["bottom"] = paddingValue.toInt()
            }
        }

        fun lazyColumnItemPadding(paddingValue: String) = apply {
            topPadding(paddingValue)
            leftPadding(paddingValue)
            bottomPadding(paddingValue)
            rightPadding(paddingValue)
        }

        fun build() = LazyColumnDTO(this)
    }
}

object LazyColumnDtoFactory : ComposableViewFactory<LazyColumnDTO, LazyColumnDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
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
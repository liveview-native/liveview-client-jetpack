package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node

class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?, paddingValues: PaddingValues?, onChildren: OnChildren?
    ) {
        val title = children?.find { it.tag == "Title" }
        val actions = children?.filter { it.tag == "Action" }
        val navIcon = children?.find { it.tag == "NavIcon" }
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                title?.let { onChildren?.invoke(it, null) }
            },
            navigationIcon = {
                navIcon?.let { Box { onChildren?.invoke(it, null) } }
            },
            actions = {
                actions?.forEach { actionIcon ->
                    onChildren?.invoke(actionIcon, null)
                }
            }, modifier = modifier
        )
    }

    class Builder : ComposableBuilder() {
        var backgroundColor = Color.White

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                backgroundColor = color.toColor()
            }
        }

        fun build() = TopAppBarDTO(this)
    }
}

object TopAppBarDtoFactory : ComposableViewFactory<TopAppBarDTO, TopAppBarDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?
    ): TopAppBarDTO {
        val builder = TopAppBarDTO.Builder()

        attributes.forEach { attribute ->
            when (attribute.name) {
                "backgroundColor" -> builder.backgroundColor(attribute.value)
                "size" -> builder.size(attribute.value)
                "height" -> builder.height(attribute.value)
                "width" -> builder.width(attribute.value)
                "padding" -> builder.padding(attribute.value)
                "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                "verticalPadding" -> builder.verticalPadding(attribute.value)
            }
        }
        return builder.build()
    }
}

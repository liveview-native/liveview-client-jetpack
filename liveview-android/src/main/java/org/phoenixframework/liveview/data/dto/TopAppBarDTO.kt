package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.actionTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.navigationIconTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.titleTag
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node

class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    @Composable
    override fun Compose(
        children: ImmutableList<ComposableTreeNode>?, paddingValues: PaddingValues?
    ) {
        val title = remember(children) {
            children?.find { it.tag == titleTag }
        }
        val actions = remember(children) {
            children?.filter { it.tag == actionTag }?.toImmutableList()
        }
        val navIcon = remember(children) {
            children?.find { it.tag == navigationIconTag }
        }
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                title?.let { it.value.Compose(children = it.children, paddingValues = null) }
            },
            navigationIcon = {
                navIcon?.let {
                    Box {
                        it.value.Compose(
                            children = it.children,
                            paddingValues = null
                        )
                    }
                }
            },
            actions = {
                actions?.forEach {
                    it.value.Compose(children = it.children, paddingValues = null)
                }
            },
            modifier = modifier,
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

    const val titleTag = "Title"
    const val actionTag = "Action"
    const val navigationIconTag = "NavIcon"
}

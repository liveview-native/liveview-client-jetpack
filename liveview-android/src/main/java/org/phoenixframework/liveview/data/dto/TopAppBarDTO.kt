package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.actionTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.navigationIconTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.titleTag
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == titleTag }
        }
        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.tag == actionTag }?.toImmutableList()
        }
        val navIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == navigationIconTag }
        }
        TopAppBar(
            backgroundColor = Color.White,
            title = {
                title?.let {
                    PhxLiveView(it, null, pushEvent)
                }
            },
            navigationIcon = {
                navIcon?.let {
                    Box {
                        PhxLiveView(it, null, pushEvent)
                    }
                }
            },
            actions = {
                actions?.forEach {
                    PhxLiveView(it, null, pushEvent)
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
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?
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

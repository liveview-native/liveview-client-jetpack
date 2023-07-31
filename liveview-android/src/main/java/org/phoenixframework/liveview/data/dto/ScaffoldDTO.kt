package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ScaffoldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val backgroundColor: Color = builder.backgroundColor

    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?, paddingValues: PaddingValues?, onChildren: OnChildren?
    ) {
        val topBar = children?.find { it.tag == ComposableTypes.topAppBar }
        val body = children?.find { it.tag != ComposableTypes.topAppBar }
        Scaffold(
            modifier = modifier.paddingIfNotNull(paddingValues),
            backgroundColor = backgroundColor,
            topBar = {
                topBar?.let { appBar -> onChildren?.invoke(appBar, null) }
            }) { contentPaddingValues ->
            body?.let { onChildren?.invoke(it, contentPaddingValues) }
        }
    }

    class Builder : ComposableBuilder() {
        var backgroundColor: Color = Color.White

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.backgroundColor = color.toColor()
            }
        }

        fun build() = ScaffoldDTO(this)
    }
}

object ScaffoldDtoFactory : ComposableViewFactory<ScaffoldDTO, ScaffoldDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<Attribute>,
        children: List<Node>?,
        pushEvent: PushEvent?
    ): ScaffoldDTO = attributes.fold(ScaffoldDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "backgroundColor" -> builder.backgroundColor(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            else -> builder
        } as ScaffoldDTO.Builder
    }.build()
}

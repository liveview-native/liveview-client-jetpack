package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.OnChildren
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node

class IconButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick
    private val enabled: Boolean = builder.enabled

    @Composable
    override fun Compose(
        children: List<ComposableTreeNode>?, paddingValues: PaddingValues?, onChildren: OnChildren?
    ) {
        IconButton(onClick = onClick, enabled = enabled) {
            IconDtoFactory
            children?.forEach {
                onChildren?.invoke(it, paddingValues)
            }
        }
    }

    class Builder : ComposableBuilder() {
        var onClick: () -> Unit = {}
        var enabled: Boolean = true

        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        fun enabled(enabled: String): Builder = apply {
            this.enabled = enabled.toBoolean()
        }

        fun build() = IconButtonDTO(this)
    }
}

object IconButtonDtoFactory : ComposableViewFactory<IconButtonDTO, IconButtonDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<Attribute>, children: List<Node>?, pushEvent: PushEvent?
    ): IconButtonDTO = attributes.fold(
        IconButtonDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            //TODO Swift is using `phx-click`. Should Android use the same?
            "phx-click" -> builder.onClick {
                pushEvent?.invoke("click", attribute.value, "", null)
            }

            "enabled" -> builder.enabled(attribute.value)
            else -> builder
        }
    }.build()
}

package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick

    @Composable
    override fun Compose(
        children: ImmutableList<ComposableTreeNode>?,
        paddingValues: PaddingValues?,
    ) {
        Button(
            modifier = modifier.paddingIfNotNull(paddingValues),
            onClick = onClick,
        ) {
            children?.forEach {
                it.value.Compose(children = it.children, paddingValues = null)
            }
        }
    }

    class Builder : ComposableBuilder() {
        var onClick: () -> Unit = {}
        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        override fun padding(padding: String): Builder = apply {
            super.padding(padding)
        }

        fun build() = ButtonDTO(this)
    }
}

object ButtonDtoFactory : ComposableViewFactory<ButtonDTO, ButtonDTO.Builder>() {
    /**
     * Creates a `ButtonDTO` object based on the attributes of the input `Attributes` object.
     * Button co-relates to the Button composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `ButtonDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<Attribute>,
        children: List<Node>?,
        pushEvent: PushEvent?
    ): ButtonDTO = attributes.fold(ButtonDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            //TODO Swift is using `phx-click`. Should Android use the same?
            "phx-click" -> builder.onClick {
                pushEvent?.invoke("click", attribute.value, "", null)
            }

            "padding" -> builder.padding(attribute.value)
            else -> builder
        }
    }.build()
}
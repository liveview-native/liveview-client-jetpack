package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class ButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Button(
            modifier = modifier.paddingIfNotNull(paddingValues),
            onClick = onClick,
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, paddingValues, pushEvent)
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
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?,
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
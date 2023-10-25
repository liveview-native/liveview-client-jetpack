package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

class ScaffoldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val containerColor: Color? = builder.containerColor
    private val contentColor: Color? = builder.contentColor

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val topBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == ComposableTypes.topAppBar }
        }
        val body = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag != ComposableTypes.topAppBar }
        }
        val containerColor = containerColor ?: MaterialTheme.colorScheme.background
        Scaffold(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            topBar = {
                topBar?.let { appBar ->
                    PhxLiveView(appBar, paddingValues, pushEvent)
                }
            },
            content = { contentPaddingValues ->
                body?.let { content ->
                    PhxLiveView(content, contentPaddingValues, pushEvent)
                }
            }
        )
    }

    class Builder : ComposableBuilder() {
        var containerColor: Color? = null
        var contentColor: Color? = null

        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        fun build() = ScaffoldDTO(this)
    }
}

object ScaffoldDtoFactory : ComposableViewFactory<ScaffoldDTO, ScaffoldDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ScaffoldDTO = attributes.fold(ScaffoldDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "containerColor" -> builder.containerColor(attribute.value)
            "contentColor" -> builder.contentColor(attribute.value)
            else -> builder.processCommonAttributes(scope, attribute, pushEvent)
        } as ScaffoldDTO.Builder
    }.build()
}

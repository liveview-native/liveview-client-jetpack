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

internal class ScaffoldDTO private constructor(builder: Builder) :
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
        val floatingActionButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == ComposableTypes.fab }
        }
        val body = remember(composableNode?.children) {
            composableNode?.children?.find {
                it.node?.tag != ComposableTypes.topAppBar &&
                        it.node?.tag != ComposableTypes.fab
            }
        }
        val containerColor = containerColor ?: MaterialTheme.colorScheme.background
        Scaffold(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            topBar = {
                topBar?.let { appBar ->
                    PhxLiveView(appBar, pushEvent, composableNode, null)
                }
            },
            floatingActionButton = {
                floatingActionButton?.let { fab ->
                    PhxLiveView(fab, pushEvent, composableNode, null)
                }
            },
            content = { contentPaddingValues ->
                body?.let { content ->
                    PhxLiveView(content, pushEvent, composableNode, contentPaddingValues)
                }
            }
        )
    }

    internal class Builder : ComposableBuilder<ScaffoldDTO>() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set

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

        override fun build() = ScaffoldDTO(this)
    }
}

internal object ScaffoldDtoFactory : ComposableViewFactory<ScaffoldDTO, ScaffoldDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ScaffoldDTO = attributes.fold(ScaffoldDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "containerColor" -> builder.containerColor(attribute.value)
            "contentColor" -> builder.contentColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ScaffoldDTO.Builder
    }.build()
}

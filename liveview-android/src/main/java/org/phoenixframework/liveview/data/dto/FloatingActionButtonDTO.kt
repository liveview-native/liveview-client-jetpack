package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

class FloatingActionButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick
    private val shape: Shape = builder.shape
    private val containerColor: Color? = builder.containerColor
    private val contentColor: Color? = builder.contentColor
    private val elevation: ImmutableMap<String, String>? = builder.elevation?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val containerColor = containerColor ?: FloatingActionButtonDefaults.containerColor
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            elevation = getFabElevation(elevation),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null)
            }
        }
    }

    @Composable
    private fun getFabElevation(elevation: ImmutableMap<String, String>?): FloatingActionButtonElevation {
        val defaultValue = FloatingActionButtonDefaults.elevation()
        return if (elevation == null) {
            defaultValue
        } else {
            FloatingActionButtonDefaults.elevation(
                defaultElevation = elevation["defaultElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("defaultElevation")),
                pressedElevation = elevation["pressedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("pressedElevation")),
                focusedElevation = elevation["focusedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("focusedElevation")),
                hoveredElevation = elevation["hoveredElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("hoveredElevation")),
            )
        }
    }

    class Builder : ComposableBuilder() {
        var onClick: () -> Unit = {}
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var shape: Shape = CircleShape
            private set
        var elevation: Map<String, String>? = null
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

        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape, CircleShape)
            }
        }

        fun elevation(elevations: String) = apply {
            if (elevations.isNotEmpty()) {
                try {
                    this.elevation = JsonParser.parse(elevations)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun build() = FloatingActionButtonDTO(this)
    }
}

object FloatingActionButtonDtoFactory :
    ComposableViewFactory<FloatingActionButtonDTO, FloatingActionButtonDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): FloatingActionButtonDTO = attributes.fold(
        FloatingActionButtonDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            "shape" -> builder.shape(attribute.value)
            "containerColor" -> builder.containerColor(attribute.value)
            "contentColor" -> builder.contentColor(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            "phx-click" -> builder.onClick {
                pushEvent?.invoke("click", attribute.value, "", null)
            }

            else -> builder.processCommonAttributes(scope, attribute, pushEvent)
        } as FloatingActionButtonDTO.Builder
    }.build()
}
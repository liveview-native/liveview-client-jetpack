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
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.ATTR_CLICK
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

internal class FloatingActionButtonDTO private constructor(builder: Builder) :
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
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultValue.privateField(key))

            FloatingActionButtonDefaults.elevation(
                defaultElevation = value("defaultElevation"),
                pressedElevation = value("pressedElevation"),
                focusedElevation = value("focusedElevation"),
                hoveredElevation = value("hoveredElevation"),
            )
        }
    }

    internal class Builder : ComposableBuilder<FloatingActionButtonDTO>() {
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

        /**
         * The color used for the background of this FAB.
         *
         * ```
         * <FloatingActionButton containerColor="#FF0000FF" />
         * ```
         * @param color the background color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * The preferred color for content inside this FAB.
         *
         * ```
         * <FloatingActionButton contentColor="#FF0000FF" />
         * ```
         * @param color the content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <FloatingActionButton phx-click="yourServerEventHandler">...</FloatingActionButton>
         * ```
         * @param event event name defined on the server to handle the button's click.
         * @param pushEvent function responsible to dispatch the server call.
         */
        fun onClick(event: String, pushEvent: PushEvent?) = apply {
            this.onClick = {
                pushEvent?.invoke(EVENT_TYPE_CLICK, event, "", null)
            }
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <FloatingActionButton shape="circle" >...</FloatingActionButton>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape, CircleShape)
            }
        }

        /**
         * Set FloatingActionButton elevations.
         * ```
         * <FloatingActionButton
         *   elevation="{'defaultElevation': '20', 'pressedElevation': '10'}">
         *   ...
         * </FloatingActionButton>
         * ```
         * @param elevations an JSON formatted string, containing the button elevations. The
         * elevation supported keys are: `defaultElevation`, `pressedElevation`, `focusedElevation`,
         * and `hoveredElevation`.
         */
        fun elevation(elevations: String) = apply {
            if (elevations.isNotEmpty()) {
                try {
                    this.elevation = JsonParser.parse(elevations)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun build() = FloatingActionButtonDTO(this)
    }
}

internal object FloatingActionButtonDtoFactory :
    ComposableViewFactory<FloatingActionButtonDTO, FloatingActionButtonDTO.Builder>() {
    /**
     * Creates an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object. FloatingActionButton co-relates to the FloatingActionButton composable
     * from Compose library.
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @return an `FloatingActionButtonDTO` object based on the attributes and text of the input
     * `Attributes` object.
     */
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
            ATTR_CLICK -> builder.onClick(attribute.value, pushEvent)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as FloatingActionButtonDTO.Builder
    }.build()
}
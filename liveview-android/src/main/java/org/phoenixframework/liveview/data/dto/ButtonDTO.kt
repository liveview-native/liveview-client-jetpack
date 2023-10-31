package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
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

internal class ButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick
    private val enabled: Boolean = builder.enabled
    private val shape: Shape? = builder.shape
    private val colors: ImmutableMap<String, String>? = builder.colors?.toImmutableMap()
    private val elevation: ImmutableMap<String, String>? = builder.elevations?.toImmutableMap()
    private val contentPadding: PaddingValues? = builder.contentPadding

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape ?: ButtonDefaults.shape,
            colors = getButtonColors(colors),
            elevation = getButtonElevation(elevation),
            contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Composable
    private fun getButtonElevation(elevation: Map<String, String>?): ButtonElevation {
        val defaultElevation = ButtonDefaults.buttonElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultElevation.privateField(key))

            ButtonDefaults.buttonElevation(
                defaultElevation = value("defaultElevation"),
                pressedElevation = value("pressedElevation"),
                focusedElevation = value("focusedElevation"),
                hoveredElevation = value("hoveredElevation"),
                disabledElevation = value("disabledElevation")
            )
        }
    }

    @Composable
    private fun getButtonColors(colors: Map<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.buttonColors()

        return if (colors == null) {
            defaultColors
        } else {
            fun value(key: String) =
                colors[key]?.toColor() ?: Color(defaultColors.privateField(key))

            ButtonDefaults.buttonColors(
                containerColor = value("containerColor"),
                contentColor = value("contentColor"),
                disabledContainerColor = value("disabledContainerColor"),
                disabledContentColor = value("disabledContentColor")
            )
        }
    }

    internal class Builder : ComposableBuilder<ButtonDTO>() {
        var onClick: () -> Unit = {}
            private set
        var enabled: Boolean = true
            private set
        var shape: Shape? = null
            private set
        var colors: Map<String, String>? = null
            private set
        var elevations: Map<String, String>? = null
            private set
        var contentPadding: PaddingValues? = null
            private set

        /**
         * Sets the event name to be triggered on the server when the button is clicked.
         *
         * ```
         * <Button phx-click="yourServerEventHandler">...</Button>
         * ```
         * @param event event name defined on the server to handle the button's click.
         * @param pushEvent function responsible to dispatch the server call.
         */
        fun onClick(event: String, pushEvent: PushEvent?): Builder = apply {
            this.onClick = {
                pushEvent?.invoke(EVENT_TYPE_CLICK, event, "", null)
            }
        }

        /**
         * Defines if the button is enabled.
         *
         * ```
         * <Button enabled="true">...</Button>
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        fun enabled(enabled: String): Builder = apply {
            if (enabled.isNotEmpty()) {
                this.enabled = enabled.toBoolean()
            }
        }

        /**
         * Defines the shape of the button's container, border, and shadow (when using elevation).
         *
         * ```
         * <Button shape="circle" >...</Button>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * Set Button colors.
         * ```
         * <Button
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         *   ...
         * </Button>
         * ```
         * @param colors an JSON formatted string, containing the button colors. The color keys
         * supported are: `containerColor`, `contentColor`, `disabledContainerColor, and
         * `disabledContentColor`
         */
        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Set Button elevations.
         * ```
         * <Button
         *   elevation="{'defaultElevation': '20', 'pressedElevation': '10'}">
         *   ...
         * </Button>
         * ```
         * @param elevations an JSON formatted string, containing the button elevations. The
         * elevation supported keys are: `defaultElevation`, `pressedElevation`, `focusedElevation`,
         * `hoveredElevation`, and `disabledElevation`.
         */
        fun elevation(elevations: String): Builder = apply {
            if (elevations.isNotEmpty()) {
                try {
                    this.elevations = JsonParser.parse(elevations)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Spacing values to apply internally between the container and the content.
         * ```
         * <Button contentPadding="8">...</Button>
         * ```
         * @param contentPadding int value representing the padding to be applied to the button's
         * content.
         */
        fun contentPadding(contentPadding: String): Builder = apply {
            if (contentPadding.isNotEmpty()) {
                this.contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp)
            }
        }

        override fun build() = ButtonDTO(this)
    }
}

internal object ButtonDtoFactory : ComposableViewFactory<ButtonDTO, ButtonDTO.Builder>() {
    /**
     * Creates a `ButtonDTO` object based on the attributes of the input `Attributes` object.
     * Button co-relates to the Button composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `ButtonDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ButtonDTO = attributes.fold(ButtonDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "colors" -> builder.colors(attribute.value)
            "contentPadding" -> builder.contentPadding(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            "enabled" -> builder.enabled(attribute.value)
            "shape" -> builder.shape(attribute.value)
            ATTR_CLICK -> builder.onClick(attribute.value, pushEvent)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ButtonDTO.Builder
    }.build()
}
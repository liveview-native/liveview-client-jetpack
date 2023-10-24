package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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

@Stable
class ButtonDTO private constructor(builder: Builder) :
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
                PhxLiveView(it, null, pushEvent)
            }
        }
    }

    @Composable
    private fun getButtonElevation(elevation: Map<String, String>?): ButtonElevation {
        val defaultElevation = ButtonDefaults.buttonElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            ButtonDefaults.buttonElevation(
                defaultElevation = elevation["defaultElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultElevation.privateField("defaultElevation")),
                pressedElevation = elevation["pressedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultElevation.privateField("pressedElevation")),
                focusedElevation = elevation["focusedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultElevation.privateField("focusedElevation")),
                hoveredElevation = elevation["hoveredElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultElevation.privateField("hoveredElevation")),
                disabledElevation = elevation["disabledElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultElevation.privateField("disabledElevation"))
            )
        }
    }

    @Composable
    private fun getButtonColors(colors: Map<String, String>?): ButtonColors {
        val defaultColors = ButtonDefaults.buttonColors()
        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.buttonColors(
                containerColor = colors["containerColor"]?.toColor()
                    ?: Color(defaultColors.privateField("containerColor")),
                contentColor = colors["contentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("contentColor")),
                disabledContainerColor = colors["contentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("disabledContainerColor")),
                disabledContentColor = colors["disabledContentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("disabledContentColor"))
            )
        }
    }

    class Builder : ComposableBuilder() {
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

        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        fun enabled(enabled: String): Builder = apply {
            if (enabled.isNotEmpty()) {
                this.enabled = enabled.toBoolean()
            }
        }

        fun shape(shape: String): Builder = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * Set Button colors.
         *
         * <Button ...
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
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
         *
         * <Button ...
         *   elevation="{'defaultElevation': '20', 'pressedElevation': '10'}">
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

        fun contentPadding(contentPadding: String): Builder = apply {
            if (contentPadding.isNotEmpty()) {
                this.contentPadding = PaddingValues((contentPadding.toIntOrNull() ?: 0).dp)
            }
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
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
    ): ButtonDTO = attributes.fold(ButtonDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "colors" -> builder.colors(attribute.value)
            "contentPadding" -> builder.contentPadding(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            "enabled" -> builder.enabled(attribute.value)
            "shape" -> builder.shape(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            //TODO Swift is using `phx-click`. Should Android use the same?
            "phx-click" -> builder.onClick {
                pushEvent?.invoke("click", attribute.value, "", null)
            }

            else -> builder
        } as ButtonDTO.Builder
    }.build()
}
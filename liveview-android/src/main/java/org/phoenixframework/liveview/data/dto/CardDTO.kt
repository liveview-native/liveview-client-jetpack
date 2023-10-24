package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull
import org.phoenixframework.liveview.ui.theme.shapeFromString

class CardDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    private val shape: Shape = builder.shape
    private val colors: ImmutableMap<String, String>? = builder.cardColors?.toImmutableMap()
    private val elevation: ImmutableMap<String, String>? = builder.elevation?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Card(
            modifier = modifier.paddingIfNotNull(paddingValues),
            shape = shape,
            colors = getCardColors(colors),
            elevation = getCardElevation(elevation),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, null, pushEvent)
            }
        }
    }

    @Composable
    private fun getCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.cardColors()
        return if (cardColors == null) {
            defaultValue
        } else {
            CardDefaults.cardColors(
                containerColor = cardColors["containerColor"]?.toColor()
                    ?: Color(defaultValue.privateField("containerColor")),
                contentColor = cardColors["contentColor"]?.toColor()
                    ?: Color(defaultValue.privateField("contentColor")),
                disabledContainerColor = cardColors["disabledContainerColor"]?.toColor()
                    ?: Color(defaultValue.privateField("disabledContainerColor")),
                disabledContentColor = cardColors["disabledContentColor"]?.toColor()
                    ?: Color(defaultValue.privateField("disabledContentColor")),
            )
        }
    }

    @Composable
    private fun getCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultValue = CardDefaults.cardElevation()
        return if (elevation == null) {
            defaultValue
        } else {
            CardDefaults.cardElevation(
                defaultElevation = elevation["defaultElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("defaultElevation")),
                pressedElevation = elevation["pressedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("pressedElevation")),
                focusedElevation = elevation["focusedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("focusedElevation")),
                hoveredElevation = elevation["hoveredElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("hoveredElevation")),
                draggedElevation = elevation["draggedElevation"]?.toIntOrNull()?.dp
                    ?: Dp(defaultValue.privateField("draggedElevation")),
                disabledElevation = elevation["disabledElevation"]?.toIntOrNull()?.dp ?: Dp(
                    defaultValue.privateField("disabledElevation")
                ),
            )
        }
    }

    class Builder : ComposableBuilder() {
        var shape: Shape = RoundedCornerShape(0.dp)
        var cardColors: Map<String, String>? = null
        var elevation: Map<String, String>? = null

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * Set Card colors.
         *
         * <Card ...
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         */
        fun cardColors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.cardColors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Set Card elevations.
         *
         * <Card ...
         *   elevation="{'defaultElevation': '10', 'pressedElevation': '5'}">
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

        fun build() = CardDTO(this)
    }
}

object CardDtoFactory : ComposableViewFactory<CardDTO, CardDTO.Builder>() {
    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * Card co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?,
    ): CardDTO = attributes.fold(CardDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "shape" -> builder.shape(attribute.value)
            "colors" -> builder.cardColors(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            "phx-click" -> builder.clickable(attribute.value, pushEvent)
            else -> builder
        } as CardDTO.Builder
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.ATTR_SCROLL
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull
import org.phoenixframework.liveview.ui.theme.shapeFromString

internal class CardDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val shape: Shape = builder.shape
    private val colors: ImmutableMap<String, String>? = builder.cardColors?.toImmutableMap()
    private val elevation: ImmutableMap<String, String>? = builder.elevation?.toImmutableMap()
    private val hasVerticalScroll = builder.hasVerticalScrolling
    private val hasHorizontalScroll = builder.hasHorizontalScrolling

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Card(
            modifier = modifier
                .paddingIfNotNull(paddingValues)
                .optional(
                    hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                )
                .optional(
                    hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                ),
            shape = shape,
            colors = getCardColors(colors),
            elevation = getCardElevation(elevation),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Composable
    private fun getCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.cardColors()
        return if (cardColors == null) {
            defaultValue
        } else {
            fun value(key: String) =
                cardColors[key]?.toColor() ?: Color(defaultValue.privateField(key))

            CardDefaults.cardColors(
                containerColor = value("containerColor"),
                contentColor = value("contentColor"),
                disabledContainerColor = value("disabledContainerColor"),
                disabledContentColor = value("disabledContentColor"),
            )
        }
    }

    @Composable
    private fun getCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultValue = CardDefaults.cardElevation()
        return if (elevation == null) {
            defaultValue
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultValue.privateField(key))

            CardDefaults.cardElevation(
                defaultElevation = value("defaultElevation"),
                pressedElevation = value("pressedElevation"),
                focusedElevation = value("focusedElevation"),
                hoveredElevation = value("hoveredElevation"),
                draggedElevation = value("draggedElevation"),
                disabledElevation = value("disabledElevation"),
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var shape: Shape = RoundedCornerShape(0.dp)
            private set
        var cardColors: Map<String, String>? = null
            private set
        var elevation: Map<String, String>? = null
            private set

        /**
         * Defines the shape of the card's container, border, and shadow (when using elevation).
         *
         * ```
         * <Card shape="rectangle" >...</Button>
         * ```
         * @param shape button's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * Set Card colors.
         * ```
         * <Card
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         * ```
         * @param colors an JSON formatted string, containing the card colors. The color keys
         * supported are: `containerColor`, `contentColor`, `disabledContainerColor, and
         * `disabledContentColor`
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
         * ```
         * <Card
         *   elevation="{'defaultElevation': '10', 'pressedElevation': '5'}">
         * ```
         * @param elevations an JSON formatted string, containing the card elevations. The
         * elevation supported keys are: `defaultElevation`, `pressedElevation`, `focusedElevation`,
         * `hoveredElevation`, `draggedElevation`, and `disabledElevation`.
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

internal object CardDtoFactory : ComposableViewFactory<CardDTO, CardDTO.Builder>() {
    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * Card co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): CardDTO = attributes.fold(CardDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            ATTR_SCROLL -> builder.scrolling(attribute.value)
            "shape" -> builder.shape(attribute.value)
            "colors" -> builder.cardColors(attribute.value)
            "elevation" -> builder.elevation(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as CardDTO.Builder
    }.build()
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrBorderColor
import org.phoenixframework.liveview.data.constants.Attrs.attrBorderWidth
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrScroll
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrDraggedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.data.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design Card.
 * ```
 * <Card
 *   padding="16"
 *   elevation="{'defaultElevation': '10', 'pressedElevation': '2'}"
 *   phx-click="dec"
 * >
 *   <Text>Card content</Text>
 * </Card>
 *
 * <ElevatedCard>...</ElevatedCard>
 *
 * <OutlinedCard>...</OutlinedCard>
 * ```
 */
internal class CardDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val shape: Shape = builder.shape
    private val colors: ImmutableMap<String, String>? = builder.cardColors?.toImmutableMap()
    private val elevation: ImmutableMap<String, String>? = builder.elevation?.toImmutableMap()
    private val border: BorderStroke? = builder.border
    private val hasVerticalScroll = builder.hasVerticalScrolling
    private val hasHorizontalScroll = builder.hasHorizontalScrolling

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        when (composableNode?.node?.tag) {
            ComposableTypes.card ->
                Card(
                    modifier = modifier
                        .paddingIfNotNull(paddingValues)
                        .optional(
                            hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                        )
                        .optional(
                            hasHorizontalScroll,
                            Modifier.horizontalScroll(rememberScrollState())
                        ),
                    shape = shape,
                    colors = getCardColors(colors),
                    elevation = getCardElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            ComposableTypes.elevatedCard ->
                ElevatedCard(
                    modifier = modifier
                        .paddingIfNotNull(paddingValues)
                        .optional(
                            hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                        )
                        .optional(
                            hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                        ),
                    shape = shape,
                    colors = getElevatedCardColors(colors),
                    elevation = getElevatedCardElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            ComposableTypes.outlinedCard ->
                OutlinedCard(
                    modifier = modifier
                        .paddingIfNotNull(paddingValues)
                        .optional(
                            hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                        )
                        .optional(
                            hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                        ),
                    shape = shape,
                    colors = getOutlinedCardColors(colors),
                    elevation = getOutlinedCardElevation(elevation),
                    border = border ?: ButtonDefaults.outlinedButtonBorder,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
        }
    }

    @Composable
    private fun getCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.elevatedCardColors()
        return if (cardColors == null) {
            defaultValue
        } else {
            val containerColor =
                cardColors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant
            val contentColor =
                cardColors[colorAttrContentColor]?.toColor() ?: contentColorFor(containerColor)
            CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            )
        }
    }

    @Composable
    private fun getElevatedCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.cardColors()
        return if (cardColors == null) {
            defaultValue
        } else {
            val containerColor =
                cardColors[colorAttrContainerColor]?.toColor() ?: MaterialTheme.colorScheme.surface
            val contentColor =
                cardColors[colorAttrContentColor]?.toColor() ?: contentColorFor(containerColor)
            CardDefaults.elevatedCardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            )
        }
    }

    @Composable
    private fun getOutlinedCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.outlinedCardColors()
        return if (cardColors == null) {
            defaultValue
        } else {
            val containerColor =
                cardColors[colorAttrContainerColor]?.toColor() ?: MaterialTheme.colorScheme.surface
            val contentColor =
                cardColors[colorAttrContentColor]?.toColor() ?: contentColorFor(containerColor)
            CardDefaults.outlinedCardColors(
                containerColor = containerColor,
                contentColor = contentColor,
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
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                draggedElevation = value(elevationAttrDraggedElevation),
                disabledElevation = value(elevationAttrDisabledElevation),
            )
        }
    }

    @Composable
    private fun getElevatedCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultValue = CardDefaults.elevatedCardElevation()
        return if (elevation == null) {
            defaultValue
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultValue.privateField(key))

            CardDefaults.elevatedCardElevation(
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                draggedElevation = value(elevationAttrDraggedElevation),
                disabledElevation = value(elevationAttrDisabledElevation),
            )
        }
    }

    @Composable
    private fun getOutlinedCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultValue = CardDefaults.outlinedCardElevation()
        return if (elevation == null) {
            defaultValue
        } else {
            fun value(key: String) =
                elevation[key]?.toIntOrNull()?.dp ?: Dp(defaultValue.privateField(key))

            CardDefaults.outlinedCardElevation(
                defaultElevation = value(elevationAttrDefaultElevation),
                pressedElevation = value(elevationAttrPressedElevation),
                focusedElevation = value(elevationAttrFocusedElevation),
                hoveredElevation = value(elevationAttrHoveredElevation),
                draggedElevation = value(elevationAttrDraggedElevation),
                disabledElevation = value(elevationAttrDisabledElevation),
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
        var border: BorderStroke? = null
            private set

        private var borderWidth: Dp = 1.0.dp
        private var borderColor: Color? = null

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
         * supported are: `containerColor` and `contentColor`
         */
        fun cardColors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.cardColors = colorsFromString(colors)
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
                this.elevation = elevationsFromString(elevations)
            }
        }

        /**
         * The border width to draw around the container of this card. This property is used just
         * for `OutlinedCard`.
         * ```
         * <OutlinedCard border-width="2">...</OutlinedCard>
         * ```
         * @param borderWidth int value representing card border's width.
         * content.
         */
        fun borderWidth(borderWidth: String) = apply {
            if (borderWidth.isNotEmptyAndIsDigitsOnly()) {
                this.borderWidth = (borderWidth.toIntOrNull() ?: 0).dp
            }
        }

        /**
         * The border color to draw around the container of this card. This property is used just
         * for `OutlinedCard`.
         * ```
         * <OutlinedCard border-color="#FF0000FF">...</OutlinedCard>
         * ```
         * @param borderColor int value representing the padding to be applied to the card's
         * content. The color must be specified as a string in the AARRGGBB format.
         */
        fun borderColor(borderColor: String) = apply {
            if (borderColor.isNotEmpty()) {
                this.borderColor = borderColor.toColor()
            }
        }

        fun build(): CardDTO {
            val w = borderWidth
            val c = borderColor
            if (c != null) {
                this.border = BorderStroke(w, c)
            }
            return CardDTO(this)
        }
    }
}

internal object CardDtoFactory : ComposableViewFactory<CardDTO, CardDTO.Builder>() {
    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * CardDTO co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): CardDTO = attributes.fold(CardDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrBorderColor -> builder.borderColor(attribute.value)
            attrBorderWidth -> builder.borderWidth(attribute.value)
            attrColors -> builder.cardColors(attribute.value)
            attrElevation -> builder.elevation(attribute.value)
            attrScroll -> builder.scrolling(attribute.value)
            attrShape -> builder.shape(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as CardDTO.Builder
    }.build()
}
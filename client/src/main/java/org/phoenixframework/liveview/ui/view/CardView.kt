package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrElevation
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level0
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level1
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level2
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level3
import org.phoenixframework.liveview.constants.DefaultElevationValues.Level4
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDefaultElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDisabledElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrDraggedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrFocusedElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrHoveredElevation
import org.phoenixframework.liveview.constants.ElevationAttrs.elevationAttrPressedElevation
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design Card.
 * ```
 * <Card
 *   elevation="{'defaultElevation': '10', 'pressedElevation': '2'}"
 *   phx-click="onClick">
 *   <Text>Card content</Text>
 * </Card>
 *
 * <ElevatedCard>...</ElevatedCard>
 *
 * <OutlinedCard>...</OutlinedCard>
 * ```
 */
internal class CardView private constructor(props: Properties) :
    ComposableView<CardView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val shape = props.shape
        val colors = props.cardColors
        val elevation = props.elevation
        val border = props.border
        val onClick = props.onClick

        val modifier = props.commonProps.modifier
            .paddingIfNotNull(paddingValues)
        when (composableNode?.node?.tag) {
            ComposableTypes.card ->
                onClick?.let { event ->
                    Card(
                        onClick = onClickFromString(pushEvent, event, props.commonProps.phxValue),
                        modifier = modifier,
                        shape = shape ?: CardDefaults.shape,
                        colors = getCardColors(colors),
                        elevation = getCardElevation(elevation),
                    ) {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                } ?: Card(
                    modifier = modifier,
                    shape = shape ?: CardDefaults.shape,
                    colors = getCardColors(colors),
                    elevation = getCardElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }


            ComposableTypes.elevatedCard ->
                onClick?.let { event ->
                    ElevatedCard(
                        onClick = onClickFromString(pushEvent, event, props.commonProps.phxValue),
                        modifier = modifier,
                        shape = shape ?: CardDefaults.elevatedShape,
                        colors = getElevatedCardColors(colors),
                        elevation = getElevatedCardElevation(elevation),
                    ) {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                } ?: ElevatedCard(
                    modifier = modifier,
                    shape = shape ?: CardDefaults.elevatedShape,
                    colors = getElevatedCardColors(colors),
                    elevation = getElevatedCardElevation(elevation),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }

            ComposableTypes.outlinedCard ->
                onClick?.let { event ->
                    OutlinedCard(
                        onClick = onClickFromString(pushEvent, event, props.commonProps.phxValue),
                        modifier = modifier,
                        shape = CardDefaults.outlinedShape,
                        colors = getOutlinedCardColors(colors),
                        elevation = getOutlinedCardElevation(elevation),
                        border = border ?: CardDefaults.outlinedCardBorder(),
                    ) {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                } ?: OutlinedCard(
                    modifier = modifier,
                    shape = CardDefaults.outlinedShape,
                    colors = getOutlinedCardColors(colors),
                    elevation = getOutlinedCardElevation(elevation),
                    border = border ?: CardDefaults.outlinedCardBorder(),
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
        }
    }

    @Composable
    private fun getCardColors(cardColors: ImmutableMap<String, String>?): CardColors {
        val defaultValue = CardDefaults.cardColors()
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
        val defaultValue = CardDefaults.elevatedCardColors()
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
        val defaultElevation = CardDefaults.cardElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            CardDefaults.cardElevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, Level0),
                focusedElevation = value(elevationAttrFocusedElevation, Level0),
                hoveredElevation = value(elevationAttrHoveredElevation, Level0),
                draggedElevation = value(elevationAttrDraggedElevation, Level3),
                disabledElevation = value(elevationAttrDisabledElevation, Level0),
            )
        }
    }

    @Composable
    private fun getElevatedCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultElevation = CardDefaults.elevatedCardElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            CardDefaults.elevatedCardElevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level1),
                pressedElevation = value(elevationAttrPressedElevation, Level1),
                focusedElevation = value(elevationAttrFocusedElevation, Level1),
                hoveredElevation = value(elevationAttrHoveredElevation, Level2),
                draggedElevation = value(elevationAttrDraggedElevation, Level4),
                disabledElevation = value(elevationAttrDisabledElevation, Level1),
            )
        }
    }

    @Composable
    private fun getOutlinedCardElevation(elevation: ImmutableMap<String, String>?): CardElevation {
        val defaultElevation = CardDefaults.outlinedCardElevation()
        return if (elevation == null) {
            defaultElevation
        } else {
            fun value(key: String, defaultValue: Dp) =
                elevation[key]?.toIntOrNull()?.dp ?: defaultValue

            val defElevation = elevation[elevationAttrDefaultElevation]?.toIntOrNull()?.dp ?: Level0
            CardDefaults.outlinedCardElevation(
                defaultElevation = value(elevationAttrDefaultElevation, Level0),
                pressedElevation = value(elevationAttrPressedElevation, defElevation),
                focusedElevation = value(elevationAttrFocusedElevation, defElevation),
                hoveredElevation = value(elevationAttrHoveredElevation, defElevation),
                draggedElevation = value(elevationAttrDraggedElevation, Level3),
                disabledElevation = value(elevationAttrDisabledElevation, Level0),
            )
        }
    }

    @Stable
    internal data class Properties(
        val shape: Shape? = null,
        val cardColors: ImmutableMap<String, String>? = null,
        val elevation: ImmutableMap<String, String>? = null,
        val border: BorderStroke? = null,
        val onClick: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<CardView>() {
        /**
         * Creates a `CardView` object based on the attributes of the input `Attributes` object.
         * CardView co-relates to the Card composable
         * @param attributes the `Attributes` object to create the `CardView` object from
         * @return a `CardView` object based on the attributes of the input `Attributes` object
         **/
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): CardView = CardView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrBorder -> border(props, attribute.value)
                attrColors -> cardColors(props, attribute.value)
                attrElevation -> elevation(props, attribute.value)
                attrPhxClick -> onClick(props, attribute.value)
                attrShape -> shape(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })

        /**
         * Defines the shape of the card's container, border, and shadow (when using elevation).
         *
         * ```
         * <Card shape="rectangle" >...</Button>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or an integer representing
         * the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
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
        private fun cardColors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(cardColors = colorsFromString(colors)?.toImmutableMap())
            } else props
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
        private fun elevation(props: Properties, elevations: String): Properties {
            return if (elevations.isNotEmpty()) {
                props.copy(elevation = elevationsFromString(elevations)?.toImmutableMap())
            } else props
        }

        /**
         * The border to draw around the container of this card. This property is used just for
         * `OutlineCard`.
         * ```
         * <OutlinedCard border="{'width': '2', 'color': '#FF0000FF'}">...</OutlinedCard>
         * ```
         * @param border a JSON representing the border object. The `width` key is an int value
         * representing card border's width content. The `color` key must be specified as a string
         * in the AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun border(props: Properties, border: String): Properties {
            return props.copy(border = borderFromString(border))
        }

        private fun onClick(props: Properties, onClick: String): Properties {
            return if (onClick.isNotEmpty()) {
                props.copy(onClick = onClick)
            } else props
        }
    }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CaretProperties
import androidx.compose.material3.CaretScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrCaretHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrCaretProperties
import org.phoenixframework.liveview.data.constants.Attrs.attrCaretWidth
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.ColorAttrs
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Tooltips are used to provide a descriptive message. A tooltip can be:
 * - PlainTooltip which can have any content.
 * - RichTooltip which provide a standard slot for title, text, and action.
 * Tooltips are normally declare into a TooltipBox.
 * ```
 * <TooltipBox>
 *   <PlainTooltip template="tooltip">
 *     <Text>Tooltip</Text>
 *   </PlainTooltip>
 *   <ElevatedCard template="content"><Text padding="16">Elevated Card</Text></ElevatedCard>
 * </TooltipBox>
 *
 * <TooltipBox initialIsVisible="true" isPersistent="true">
 *   <RichTooltip template="tooltip">
 *     <Text template="title">Title</Text>
 *     <Text template="text">Text</Text>
 *     <Button template="action" phx-click="showSnackbar"><Text>Action</Text></Button>
 *   </RichTooltip>
 *   <ElevatedCard template="content"><Text padding="16">Elevated Card</Text></ElevatedCard>
 * </TooltipBox>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class TooltipDTO private constructor(builder: Builder) :
    ComposableView<TooltipDTO.Builder>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val scope = builder.scope
        val caretProperties = builder.caretProperties
        val colors = builder.colors
        val shape = builder.shape
        val contentColor = builder.contentColor
        val containerColor = builder.containerColor
        val tonalElevation = builder.tonalElevation
        val shadowElevation = builder.shadowElevation

        when (composableNode?.node?.tag) {
            ComposableTypes.plainTooltip -> {
                if (scope is CaretScope) {
                    scope.PlainTooltip(
                        modifier = modifier,
                        caretProperties = caretProperties ?: TooltipDefaults.caretProperties,
                        shape = shape ?: TooltipDefaults.plainTooltipContainerShape,
                        contentColor = contentColor ?: TooltipDefaults.plainTooltipContentColor,
                        containerColor = containerColor
                            ?: TooltipDefaults.plainTooltipContainerColor,
                        tonalElevation = tonalElevation ?: 0.dp,
                        shadowElevation = shadowElevation ?: 0.dp,
                        content = {
                            composableNode.children.forEach {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        }
                    )
                }
            }

            ComposableTypes.richTooltip -> {
                val title = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateTitle }
                }
                val action = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateAction }
                }
                val text = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateText }
                }
                RichTooltip(
                    modifier = modifier,
                    title = title?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    action = action?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: TooltipDefaults.richTooltipContainerShape,
                    colors = getRichTooltipColors(colors),
                    tonalElevation = tonalElevation ?: 3.dp,
                    shadowElevation = shadowElevation ?: 3.dp,
                    text = {
                        text?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                )
            }
        }
    }

    @Composable
    private fun getRichTooltipColors(colors: ImmutableMap<String, String>?): RichTooltipColors {
        val defaultColors = TooltipDefaults.richTooltipColors()
        return if (colors == null) {
            defaultColors
        } else {
            TooltipDefaults.richTooltipColors(
                containerColor = colors[ColorAttrs.colorAttrContainerColor]?.toColor()
                    ?: Color.Unspecified,
                contentColor = colors[ColorAttrs.colorAttrContentColor]?.toColor()
                    ?: Color.Unspecified,
                titleContentColor = colors[ColorAttrs.colorAttrTitleContentColor]?.toColor()
                    ?: Color.Unspecified,
                actionContentColor = colors[ColorAttrs.colorAttrActionContentColor]?.toColor()
                    ?: Color.Unspecified,
            )
        }
    }

    internal class Builder(val scope: Any? = null) : ComposableBuilder() {
        var caretProperties: CaretProperties? = null
            private set
        var colors: ImmutableMap<String, String>? = null
            private set
        var shape: Shape? = null
            private set
        var contentColor: Color? = null
            private set
        var containerColor: Color? = null
            private set
        var tonalElevation: Dp? = null
            private set
        var shadowElevation: Dp? = null
            private set

        /**
         * RichTooltipColors that will be applied to the tooltip's container and content.
         * ```
         * <RichTooltip colors="{'containerColor': 'system-red', 'contentColor': 'system-white'}">
         *   ...</RichTooltip>
         * ```
         * @param colors an JSON formatted string, containing the rich tooltip colors. The color
         * keys supported are: `containerColor`, `contentColor`, `titleContentColor`, and
         * `actionContentColor`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        /**
         * Properties for the caret of the PlainTooltip, if a default caret is desired with a
         * specific dimension.
         * ```
         * <PlainTooltip
         *   caretProperties="{'caretHeight': '100', 'caretWidth': '200'}">...</PlainTooltip>
         * ```
         */
        fun caretProperties(value: String) = apply {
            try {
                JsonParser.parse<Map<String, String>>(value)?.let { map ->
                    this.caretProperties = CaretProperties(
                        caretHeight = map[attrCaretHeight].toString().toInt().dp,
                        caretWidth = map[attrCaretWidth].toString().toInt().dp,
                    )
                }
            } catch (_: Exception) {
            }
        }

        /**
         * Color used for the background of this PlainTooltip.
         * ```
         * <PlainTooltip containerColor="system-red">...</PlainTooltip>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * Preferred color for content inside this PlainTooltip.
         * ```
         * <PlainTooltip containerColor="system-white">...</PlainTooltip>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * The shadow elevation of the tooltip.
         * ```
         * <PlainTooltip shadowElevation="12">...</PlainTooltip>
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        fun shadowElevation(shadowElevation: String) = apply {
            if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                this.shadowElevation = shadowElevation.toInt().dp
            }
        }

        /**
         * The Shape that should be applied to the tooltip container.
         * ```
         * <PlainTooltip shape="circle">...</PlainTooltip>
         * ```
         * @param shape tooltip's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * the tonal elevation of the tooltip.
         * ```
         * <PlainTooltip tonalElevation="24">...</PlainTooltip>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        fun build() = TooltipDTO(this)
    }
}

internal object TooltipDtoFactory : ComposableViewFactory<TooltipDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): TooltipDTO = attributes.fold(TooltipDTO.Builder(scope)) { builder, attribute ->
        when (attribute.name) {
            attrCaretProperties -> builder.caretProperties(attribute.value)
            attrColors -> builder.colors(attribute.value)
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrShadowElevation -> builder.shadowElevation(attribute.value)
            attrShape -> builder.shape(attribute.value)
            attrTonalElevation -> builder.tonalElevation(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TooltipDTO.Builder
    }.build()
}
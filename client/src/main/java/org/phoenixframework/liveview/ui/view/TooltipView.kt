package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CaretProperties
import androidx.compose.material3.CaretScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
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
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
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
internal class TooltipView private constructor(props: Properties) :
    ComposableView<TooltipView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val scope = props.scope
        val caretProperties = props.caretProperties
        val colors = props.colors
        val shape = props.shape
        val contentColor = props.contentColor
        val containerColor = props.containerColor
        val tonalElevation = props.tonalElevation
        val shadowElevation = props.shadowElevation

        when (composableNode?.node?.tag) {
            ComposableTypes.plainTooltip -> {
                if (scope is CaretScope) {
                    scope.PlainTooltip(
                        modifier = props.commonProps.modifier,
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
                    modifier = props.commonProps.modifier,
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

    @Stable
    internal data class Properties(
        val scope: CaretScope?,
        val caretProperties: CaretProperties? = null,
        val colors: ImmutableMap<String, String>? = null,
        val shape: Shape? = null,
        val contentColor: Color? = null,
        val containerColor: Color? = null,
        val tonalElevation: Dp? = null,
        val shadowElevation: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    @OptIn(ExperimentalMaterial3Api::class)
    internal object Factory : ComposableViewFactory<TooltipView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): TooltipView = TooltipView(
            attributes.fold(Properties(scope as? CaretScope)) { props, attribute ->
                when (attribute.name) {
                    attrCaretProperties -> caretProperties(props, attribute.value)
                    attrColors -> colors(props, attribute.value)
                    attrContainerColor -> containerColor(props, attribute.value)
                    attrContentColor -> contentColor(props, attribute.value)
                    attrShadowElevation -> shadowElevation(props, attribute.value)
                    attrShape -> shape(props, attribute.value)
                    attrTonalElevation -> tonalElevation(props, attribute.value)
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
         * RichTooltipColors that will be applied to the tooltip's container and content.
         * ```
         * <RichTooltip colors="{'containerColor': 'Red', 'contentColor': 'White'}">
         *   ...</RichTooltip>
         * ```
         * @param colors an JSON formatted string, containing the rich tooltip colors. The color
         * keys supported are: `containerColor`, `contentColor`, `titleContentColor`, and
         * `actionContentColor`.
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Properties for the caret of the PlainTooltip, if a default caret is desired with a
         * specific dimension.
         * ```
         * <PlainTooltip
         *   caretProperties="{'caretHeight': '100', 'caretWidth': '200'}">...</PlainTooltip>
         * ```
         */
        private fun caretProperties(props: Properties, value: String): Properties {
            return try {
                JsonParser.parse<Map<String, String>>(value)?.let { map ->
                    props.copy(
                        caretProperties = CaretProperties(
                            caretHeight = map[attrCaretHeight].toString().toInt().dp,
                            caretWidth = map[attrCaretWidth].toString().toInt().dp,
                        )
                    )
                } ?: props
            } catch (_: Exception) {
                props
            }
        }

        /**
         * Color used for the background of this PlainTooltip.
         * ```
         * <PlainTooltip containerColor="Red">...</PlainTooltip>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(containerColor = color.toColor())
            } else props
        }

        /**
         * Preferred color for content inside this PlainTooltip.
         * ```
         * <PlainTooltip containerColor="White">...</PlainTooltip>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(contentColor = color.toColor())
            } else props
        }

        /**
         * The shadow elevation of the tooltip.
         * ```
         * <PlainTooltip shadowElevation="12">...</PlainTooltip>
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        private fun shadowElevation(props: Properties, shadowElevation: String): Properties {
            return if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(shadowElevation = shadowElevation.toInt().dp)
            } else props
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
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape))
            } else props
        }

        /**
         * the tonal elevation of the tooltip.
         * ```
         * <PlainTooltip tonalElevation="24">...</PlainTooltip>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }
    }
}
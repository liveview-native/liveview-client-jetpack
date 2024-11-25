package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrEnableUserInput
import org.phoenixframework.liveview.constants.Attrs.attrFocusable
import org.phoenixframework.liveview.constants.Attrs.attrInitialIsVisible
import org.phoenixframework.liveview.constants.Attrs.attrIsPersistent
import org.phoenixframework.liveview.constants.Attrs.attrSpacingBetweenTooltipAndAnchor
import org.phoenixframework.liveview.constants.ComposableTypes.plainTooltip
import org.phoenixframework.liveview.constants.ComposableTypes.richTooltip
import org.phoenixframework.liveview.constants.Templates.templateContent
import org.phoenixframework.liveview.constants.Templates.templateTooltip
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material TooltipBox that wraps a composable with a tooltip.
 * tooltips provide a descriptive message for an anchor. It can be used to call the users attention
 * to the anchor. Tooltip that is invoked when the anchor is long pressed.
 * ```
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
internal class TooltipBoxView private constructor(props: Properties) :
    ComposableView<TooltipBoxView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val spacingBetweenTooltipAndAnchor = props.spacingBetweenTooltipAndAnchor
        val isVisible = props.initialIsVisible
        val isPersistent = props.isPersistent
        val focusable = props.focusable
        val enableUserInput = props.enableUserInput

        val tooltip = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTooltip }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateContent }
        }
        val positionProvider =
            if (tooltip?.node?.tag == richTooltip) {
                TooltipDefaults.rememberRichTooltipPositionProvider(
                    spacingBetweenTooltipAndAnchor = spacingBetweenTooltipAndAnchor
                )
            } else {
                TooltipDefaults.rememberPlainTooltipPositionProvider(
                    spacingBetweenTooltipAndAnchor = spacingBetweenTooltipAndAnchor
                )
            }
        val state = rememberTooltipState(
            initialIsVisible = isVisible,
            isPersistent = isPersistent,
            // TODO mutatorMutex: MutatorMutex = BasicTooltipDefaults.GlobalMutatorMutex
        )
        TooltipBox(
            positionProvider = positionProvider,
            tooltip = {
                // TODO Handle CaretScope.drawCaret Modifier
                tooltip?.let {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            },
            state = state,
            modifier = props.commonProps.modifier,
            focusable = focusable,
            enableUserInput = enableUserInput,
            content = {
                content?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
        )
    }

    @Stable
    internal data class Properties(
        val enableUserInput: Boolean = true,
        val focusable: Boolean = true,
        val isPersistent: Boolean = false,
        val initialIsVisible: Boolean = false,
        val spacingBetweenTooltipAndAnchor: Dp = 4.dp,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<TooltipBoxView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): TooltipBoxView = TooltipBoxView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrEnableUserInput -> enableUserInput(props, attribute.value)
                attrFocusable -> focusable(props, attribute.value)
                attrInitialIsVisible -> initialIsVisible(props, attribute.value)
                attrIsPersistent -> isPersistent(props, attribute.value)
                attrSpacingBetweenTooltipAndAnchor -> spacingBetweenTooltipAndAnchor(
                    props,
                    attribute.value
                )

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

        override fun subTags(): Map<String, ComposableViewFactory<*>> {
            return mapOf(
                plainTooltip to TooltipView.Factory,
                richTooltip to TooltipView.Factory,
            )
        }

        /**
         * Boolean which determines if this TooltipBox will handle long press and mouse hover to
         * trigger the tooltip through the state provided.
         * ```
         * <TooltipBox enableUserInput="true">...</TooltipBox>
         * ```
         * @param value true if this TooltipBox will handle long press and mouse hover, false
         * otherwise.
         */
        private fun enableUserInput(props: Properties, value: String): Properties {
            return props.copy(enableUserInput = value.toBoolean())
        }

        /**
         * Boolean that determines if the tooltip is focusable. When true, the tooltip will consume
         * touch events while it's shown and will have accessibility focus move to the first element
         * of the component. When false, the tooltip won't consume touch events while it's shown but
         * assistive-tech users will need to swipe or drag to get to the first element of the
         * component.
         * ```
         * <TooltipBox focusable="true">...</TooltipBox>
         * ```
         * @param value if true the tooltip will consume touch events while it's shown, false
         * otherwise.
         */
        private fun focusable(props: Properties, value: String): Properties {
            return props.copy(focusable = value.toBoolean())
        }

        /**
         * Boolean that determines if the tooltip associated with this will be persistent or not.
         * If isPersistent is true, then the tooltip will only be dismissed when the user clicks
         * outside the bounds of the tooltip. When isPersistent is false, the tooltip will dismiss
         * after a short duration. Ideally, this should be set to true when there is actionable
         * content being displayed within a tooltip.
         * ```
         * <TooltipBox isPersistent="true">...</TooltipBox>
         * ```
         * @param value true if the tooltip associated with this will be persistent, false
         * otherwise.
         */
        private fun isPersistent(props: Properties, value: String): Properties {
            return props.copy(isPersistent = value.toBoolean())
        }

        /**
         * The initial value for the tooltip's visibility when drawn.
         * ```
         * <TooltipBox initialIsVisible="true">...</TooltipBox>
         * ```
         * @param value true if initially visible, false otherwise.
         */
        private fun initialIsVisible(props: Properties, value: String): Properties {
            return props.copy(initialIsVisible = value.toBoolean())
        }

        /**
         * The spacing between the tooltip and the anchor content.
         * ```
         * <TooltipBox spacingBetweenTooltipAndAnchor="24">...</TooltipBox>
         * ```
         * @param value int value indicating the spacing between the tooltip and the anchor content.
         */
        private fun spacingBetweenTooltipAndAnchor(props: Properties, value: String): Properties {
            return if (value.isNotEmptyAndIsDigitsOnly()) {
                return props.copy(spacingBetweenTooltipAndAnchor = value.toInt().dp)
            } else props
        }
    }
}
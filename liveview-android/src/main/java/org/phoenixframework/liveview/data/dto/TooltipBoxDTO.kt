package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrEnableUserInput
import org.phoenixframework.liveview.data.constants.Attrs.attrFocusable
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialIsVisible
import org.phoenixframework.liveview.data.constants.Attrs.attrIsPersistent
import org.phoenixframework.liveview.data.constants.Attrs.attrSpacingBetweenTooltipAndAnchor
import org.phoenixframework.liveview.data.constants.Templates.templateContent
import org.phoenixframework.liveview.data.constants.Templates.templateTooltip
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes.plainTooltip
import org.phoenixframework.liveview.domain.base.ComposableTypes.richTooltip
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class TooltipBoxDTO private constructor(builder: Builder) :
    ComposableView<TooltipBoxDTO.Builder>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val spacingBetweenTooltipAndAnchor = builder.spacingBetweenTooltipAndAnchor
        val isVisible = builder.initialIsVisible
        val isPersistent = builder.isPersistent
        val focusable = builder.focusable
        val enableUserInput = builder.enableUserInput

        val tooltip = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTooltip }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateContent }
        }
        val positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(
            spacingBetweenTooltipAndAnchor = spacingBetweenTooltipAndAnchor
        )
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
            modifier = modifier,
            focusable = focusable,
            enableUserInput = enableUserInput,
            content = {
                content?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
        )
    }

    internal class Builder : ComposableBuilder() {
        var enableUserInput: Boolean = true
            private set
        var focusable: Boolean = true
            private set
        var isPersistent: Boolean = false
            private set
        var initialIsVisible: Boolean = false
            private set
        var spacingBetweenTooltipAndAnchor: Dp = 4.dp
            private set

        /**
         * Boolean which determines if this TooltipBox will handle long press and mouse hover to
         * trigger the tooltip through the state provided.
         * ```
         * <TooltipBox enableUserInput="true">...</TooltipBox>
         * ```
         * @param value true if this TooltipBox will handle long press and mouse hover, false
         * otherwise.
         */
        fun enableUserInput(value: String) = apply {
            this.enableUserInput = value.toBoolean()
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
        fun focusable(value: String) = apply {
            this.focusable = value.toBoolean()
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
        fun isPersistent(value: String) = apply {
            this.isPersistent = value.toBoolean()
        }

        /**
         * The initial value for the tooltip's visibility when drawn.
         * ```
         * <TooltipBox initialIsVisible="true">...</TooltipBox>
         * ```
         * @param value true if initially visible, false otherwise.
         */
        fun initialIsVisible(value: String) = apply {
            this.initialIsVisible = value.toBoolean()
        }

        /**
         * The spacing between the tooltip and the anchor content.
         * ```
         * <TooltipBox spacingBetweenTooltipAndAnchor="24">...</TooltipBox>
         * ```
         * @param value int value indicating the spacing between the tooltip and the anchor content.
         */
        fun spacingBetweenTooltipAndAnchor(value: String) = apply {
            if (value.isNotEmptyAndIsDigitsOnly()) {
                this.spacingBetweenTooltipAndAnchor = value.toInt().dp
            }
        }

        fun build() = TooltipBoxDTO(this)
    }
}

internal object TooltipBoxDtoFactory : ComposableViewFactory<TooltipBoxDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): TooltipBoxDTO = attributes.fold(TooltipBoxDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrEnableUserInput -> builder.enableUserInput(attribute.value)
            attrFocusable -> builder.focusable(attribute.value)
            attrInitialIsVisible -> builder.initialIsVisible(attribute.value)
            attrIsPersistent -> builder.isPersistent(attribute.value)
            attrSpacingBetweenTooltipAndAnchor -> builder.spacingBetweenTooltipAndAnchor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TooltipBoxDTO.Builder
    }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*>> {
        return mapOf(
            plainTooltip to TooltipDtoFactory,
            richTooltip to TooltipDtoFactory,
        )
    }
}
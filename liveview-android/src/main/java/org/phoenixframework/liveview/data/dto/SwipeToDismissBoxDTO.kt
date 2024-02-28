package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.phoenixframework.liveview.data.constants.Attrs.attrEnableDismissFromEndToStart
import org.phoenixframework.liveview.data.constants.Attrs.attrEnableDismissFromStartToEnd
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialValue
import org.phoenixframework.liveview.data.constants.Attrs.attrOnValueChanged
import org.phoenixframework.liveview.data.constants.SwipeToDismissBoxValues
import org.phoenixframework.liveview.data.constants.Templates.templateBackgroundContent
import org.phoenixframework.liveview.data.constants.Templates.templateContent
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A composable that can be dismissed by swiping left or right.
 *
 * ```
 * <SwipeToDismissBox onValueChanged="onSwipeChanged">
 *   <ListItem template="content">
 *     <Text template="headlineContent">Headline</Text>
 *   </ListItem>
 *   <Box background="system-red" padding="12" template="backgroundContent" width="fill">
 *     <Icon imageVector="filled:Delete" />
 *   </Box>
 * </SwipeToDismissBox>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SwipeToDismissBoxDTO private constructor(builder: Builder) :
    ComposableView<SwipeToDismissBoxDTO.Builder>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val initialValue = builder.initialValue
        val enableDismissFromStartToEnd = builder.enableDismissFromStartToEnd
        val enableDismissFromEndToStart = builder.enableDismissFromEndToStart
        val onValueChange = builder.onValueChange

        val backgroundContent = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateBackgroundContent }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateContent }
        }
        val initialStateValue = initialValue ?: SwipeToDismissBoxValue.Settled
        var lastState = remember(composableNode) {
            initialStateValue
        }
        val state = rememberSwipeToDismissBoxState(
            initialValue = initialStateValue,
            confirmValueChange = {
                if (lastState != it) {
                    lastState = it
                    if (onValueChange.isNotEmpty()) {
                        pushEvent(
                            ComposableBuilder.EVENT_TYPE_CHANGE,
                            onValueChange,
                            mergeValueWithPhxValue(KEY_SWIPE_TO_DISMISS_VALUE, it),
                            null
                        )
                    }
                }
                true
            }
        )
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                backgroundContent?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            },
            modifier = modifier,
            enableDismissFromStartToEnd = enableDismissFromStartToEnd ?: true,
            enableDismissFromEndToStart = enableDismissFromEndToStart ?: true,
            content = {
                content?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    companion object {
        const val KEY_SWIPE_TO_DISMISS_VALUE = "swipeToDismissValue"
    }

    internal class Builder : ComposableBuilder() {
        var initialValue: SwipeToDismissBoxValue? = null
            private set
        var enableDismissFromStartToEnd: Boolean? = null
            private set
        var enableDismissFromEndToStart: Boolean? = null
            private set
        var onValueChange: String = ""
            private set

        /**
         * The initial value of the state. See the supported values at
         * [org.phoenixframework.liveview.data.constants.SwipeToDismissBoxValues].
         * ```
         * <SwipeToDismissBox initialValue="Settle">...</SwipeToDismissBox>
         * ```
         * @param value initial value of the swipe to dismiss.
         */
        fun initialValue(value: String) = apply {
            this.initialValue = when (value) {
                SwipeToDismissBoxValues.startToEnd -> SwipeToDismissBoxValue.StartToEnd
                SwipeToDismissBoxValues.endToStart -> SwipeToDismissBoxValue.EndToStart
                SwipeToDismissBoxValues.settled -> SwipeToDismissBoxValue.Settled
                else -> null
            }
        }

        /**
         * Whether SwipeToDismissBox can be dismissed from start to end.
         * ```
         * <SwipeToDismissBox enableDismissFromStartToEnd="false">...</SwipeToDismissBox>
         * ```
         * @param value true if the SwipeToDismissBox can be dismissed from start to end, false
         * otherwise.
         */
        fun enableDismissFromStartToEnd(value: String) = apply {
            this.enableDismissFromStartToEnd = value.toBooleanStrictOrNull()
        }

        /**
         * Whether SwipeToDismissBox can be dismissed from end to start.
         * ```
         * <SwipeToDismissBox enableDismissFromEndToStart="false">...</SwipeToDismissBox>
         * ```
         * @param value true if the SwipeToDismissBox can be dismissed from end to start, false
         * otherwise.
         */
        fun enableDismissFromEndToStart(value: String) = apply {
            this.enableDismissFromEndToStart = value.toBooleanStrictOrNull()
        }

        /**
         * Callback called when the SwipeToDismissBox is dismissed.
         * ```
         * <SwipeToDismissBox onValueChanged="callback">...</SwipeToDismissBox>
         * ```
         * @param value callback function name called in the server when the swipe is dismissed.
         */
        fun onValueChange(value: String) = apply {
            this.onValueChange = value
        }

        fun build() = SwipeToDismissBoxDTO(this)
    }
}

internal object SwipeToDismissBoxDtoFactory : ComposableViewFactory<SwipeToDismissBoxDTO>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SwipeToDismissBoxDTO =
        attributes.fold(SwipeToDismissBoxDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrInitialValue -> builder.initialValue(attribute.value)
                attrEnableDismissFromStartToEnd -> builder.enableDismissFromStartToEnd(attribute.value)
                attrEnableDismissFromEndToStart -> builder.enableDismissFromEndToStart(attribute.value)
                attrOnValueChanged -> builder.onValueChange(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as SwipeToDismissBoxDTO.Builder
        }.build()
}
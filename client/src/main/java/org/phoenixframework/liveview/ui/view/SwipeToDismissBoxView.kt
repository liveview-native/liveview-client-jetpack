package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrEnableDismissFromEndToStart
import org.phoenixframework.liveview.constants.Attrs.attrEnableDismissFromStartToEnd
import org.phoenixframework.liveview.constants.Attrs.attrInitialValue
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.SwipeToDismissBoxValues
import org.phoenixframework.liveview.constants.Templates.templateBackgroundContent
import org.phoenixframework.liveview.constants.Templates.templateContent
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A composable that can be dismissed by swiping left or right.
 *
 * ```
 * <SwipeToDismissBox onValueChanged="onSwipeChanged">
 *   <ListItem template="content">
 *     <Text template="headlineContent">Headline</Text>
 *   </ListItem>
 *   <Box style="fillMaxWidth();padding(12.dp).background(Color.Red) template="backgroundContent">
 *     <Icon imageVector="filled:Delete" />
 *   </Box>
 * </SwipeToDismissBox>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SwipeToDismissBoxView private constructor(props: Properties) :
    ComposableView<SwipeToDismissBoxView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val initialValue = props.initialValue
        val enableDismissFromStartToEnd = props.enableDismissFromStartToEnd
        val enableDismissFromEndToStart = props.enableDismissFromEndToStart
        val onValueChange = props.onChange

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
                            EVENT_TYPE_CHANGE,
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
            modifier = props.commonProps.modifier,
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

    @Stable
    internal data class Properties(
        val initialValue: SwipeToDismissBoxValue? = null,
        val enableDismissFromStartToEnd: Boolean? = null,
        val enableDismissFromEndToStart: Boolean? = null,
        val onChange: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<SwipeToDismissBoxView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): SwipeToDismissBoxView = SwipeToDismissBoxView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrInitialValue -> initialValue(props, attribute.value)
                    attrEnableDismissFromStartToEnd -> enableDismissFromStartToEnd(
                        props,
                        attribute.value
                    )

                    attrEnableDismissFromEndToStart -> enableDismissFromEndToStart(
                        props,
                        attribute.value
                    )

                    attrPhxChange -> onChange(props, attribute.value)
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
         * The initial value of the state. See the supported values at
         * [org.phoenixframework.liveview.constants.SwipeToDismissBoxValues].
         * ```
         * <SwipeToDismissBox initialValue="Settle">...</SwipeToDismissBox>
         * ```
         * @param value initial value of the swipe to dismiss.
         */
        private fun initialValue(props: Properties, value: String): Properties {
            return props.copy(
                initialValue = when (value) {
                    SwipeToDismissBoxValues.startToEnd -> SwipeToDismissBoxValue.StartToEnd
                    SwipeToDismissBoxValues.endToStart -> SwipeToDismissBoxValue.EndToStart
                    SwipeToDismissBoxValues.settled -> SwipeToDismissBoxValue.Settled
                    else -> null
                }
            )
        }

        /**
         * Whether SwipeToDismissBox can be dismissed from start to end.
         * ```
         * <SwipeToDismissBox enableDismissFromStartToEnd="false">...</SwipeToDismissBox>
         * ```
         * @param value true if the SwipeToDismissBox can be dismissed from start to end, false
         * otherwise.
         */
        private fun enableDismissFromStartToEnd(props: Properties, value: String): Properties {
            return props.copy(enableDismissFromStartToEnd = value.toBooleanStrictOrNull())
        }

        /**
         * Whether SwipeToDismissBox can be dismissed from end to start.
         * ```
         * <SwipeToDismissBox enableDismissFromEndToStart="false">...</SwipeToDismissBox>
         * ```
         * @param value true if the SwipeToDismissBox can be dismissed from end to start, false
         * otherwise.
         */
        private fun enableDismissFromEndToStart(props: Properties, value: String): Properties {
            return props.copy(enableDismissFromEndToStart = value.toBooleanStrictOrNull())
        }

        /**
         * Callback called when the SwipeToDismissBox is dismissed.
         * ```
         * <SwipeToDismissBox onValueChanged="callback">...</SwipeToDismissBox>
         * ```
         * @param value callback function name called in the server when the swipe is dismissed.
         */
        private fun onChange(props: Properties, value: String): Properties {
            return props.copy(onChange = value)
        }
    }
}
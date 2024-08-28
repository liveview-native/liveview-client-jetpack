package org.phoenixframework.liveview.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrEnter
import org.phoenixframework.liveview.constants.Attrs.attrExit
import org.phoenixframework.liveview.constants.Attrs.attrLabel
import org.phoenixframework.liveview.constants.Attrs.attrVisible
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * AnimatedVisibility composable animates the appearance and disappearance of its content, as
 * visible value changes. Different EnterTransitions and ExitTransitions can be defined in enter
 * and exit for the appearance and disappearance animation. There are 4 types of EnterTransition
 * and ExitTransition: Fade, Expand/Shrink, Scale and Slide. The enter transitions can be combined
 * passing a list of transitions. Same for exit transitions. The order of the combination does not
 * matter, as the transition animations will start simultaneously.
 * By default, the enter transition will be a combination of fadeIn and expandIn of the content
 * from the bottom end. And the exit transition will be shrinking the content towards the bottom
 * end while fading out (i.e. fadeOut + shrinkOut). The expanding and shrinking will likely also
 * animate the parent and siblings if they rely on the size of appearing/disappearing content.
 * ```
 * <AnimatedVisibility visible={"#{@isVisible}"}>
 *    ...
 * </AnimatedVisibility>
 * ```
 * You can customize the default animation using the `enter` and `exit` attributes. The supported
 * animations are:
 * - Enter: `expandHorizontally`, `expandIn`, `expandVertically`, `fadeIn`, `scaleIn`, `slideIn`,
 * `slideInHorizontally`, `slideInVertically`.
 * - Exit: `fadeOut`, `scaleOut`, `slideOut`, `slideOutHorizontally`, `slideOutVertically`,
 * `shrinkHorizontally`, `shrinkOut`, `shrinkVertically`.
 */
internal class AnimatedVisibilityView private constructor(props: Properties) :
    ComposableView<AnimatedVisibilityView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        when (val scope = props.scope) {
            is RowScope -> {
                scope.AnimatedVisibility(
                    visible = props.visible,
                    label = props.label,
                    modifier = props.commonProps.modifier,
                    enter = props.enter,
                    exit = props.exit,
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }

            is ColumnScope -> {
                scope.AnimatedVisibility(
                    visible = props.visible,
                    label = props.label,
                    modifier = props.commonProps.modifier,
                    enter = props.enter,
                    exit = props.exit,
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }

            else -> {
                AnimatedVisibility(
                    visible = props.visible,
                    label = props.label,
                    modifier = props.commonProps.modifier,
                    enter = props.enter,
                    exit = props.exit,
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        }

    }

    @Stable
    data class Properties(
        val scope: Any?,
        val enter: EnterTransition,
        val exit: ExitTransition,
        val visible: Boolean = true,
        val label: String = "AnimatedVisibility",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<AnimatedVisibilityView>() {

        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): AnimatedVisibilityView {
            val (enterTrans, exitTrans) = when (scope) {
                is ColumnScope -> {
                    (fadeIn() + expandVertically()) to (fadeOut() + shrinkVertically())
                }

                is RowScope -> {
                    (fadeIn() + expandHorizontally()) to (fadeOut() + shrinkHorizontally())
                }

                else -> {
                    (fadeIn() + expandIn()) to (shrinkOut() + fadeOut())
                }
            }
            return AnimatedVisibilityView(
                attributes.fold(
                    Properties(scope, enterTrans, exitTrans)
                ) { props, attribute ->
                    when (attribute.name) {
                        attrEnter -> enter(props, attribute.value)
                        attrExit -> exit(props, attribute.value)
                        attrLabel -> label(props, attribute.value)
                        attrVisible -> visible(props, attribute.value)
                        else -> props.copy(
                            commonProps = handleCommonAttributes(
                                props.commonProps,
                                attribute,
                                pushEvent,
                                scope
                            )
                        )
                    }
                }
            )
        }

        /**
         * Enter transition in JSON format. The supported animations are:
         * `expandHorizontally`, `expandIn`, `expandVertically`, `fadeIn`, `scaleIn`, `slideIn`,
         * `slideInHorizontally`, `slideInVertically`.
         * ```
         * <AnimatedVisibility visible={"#{@isVisible}"} enter="oneOfTheAnimationsBelow">
         * ...
         * {"expandHorizontally": {}}
         * {"expandHorizontally": {"expandFrom": "CenterHorizontally"}}
         * {"expandHorizontally": {"expandFrom": "Start", "clip": false}}
         * {"expandIn": {}}
         * {"expandIn": {"expandFrom": "TopEnd"}}
         * {"expandIn": {"expandFrom": "BottomStart", "clip": false}}
         * {"expandVertically": {}}
         * {"expandVertically": {"expandFrom": "CenterVertically"}}
         * {"expandVertically": {"expandFrom": "Start", "clip": false}}
         * {"fadeIn": {}}
         * {"fadeIn": {"initialAlpha": 0.5}}
         * {"scaleIn": {}}
         * {"scaleIn": {"initialScale": 0.5}}
         * {"scaleIn": {"initialScale": 0.5, "transformOrigin": {"pivotFractionX": 0.75, "pivotFractionY": 0.25}}}
         * {"slideIn": {"initialOffset": {"x": 10, "y": 20}}}
         * {"slideInHorizontally": {}}
         * {"slideInHorizontally": {"initialOffsetX": 12}}
         * {"slideInVertically": {}}
         * {"slideInVertically": {"initialOffsetY": 24}}
         * ```
         * You can also use an JSON array to combine animations:
         * ```
         * [{"expandVertically": {} }, {"slideInVertically": {} }, {"fadeIn": {} }]
         * ```
         */
        private fun enter(props: Properties, enter: String): Properties {
            return try {
                enterTransitionFromString(enter)?.let {
                    props.copy(enter = it)
                } ?: props
            } catch (_: Exception) {
                props
            }
        }

        /**
         * Exit transition in JSON format. The supported animations are:
         * `fadeOut`, `scaleOut`, `slideOut`, `slideOutHorizontally`, `slideOutVertically`,
         * `shrinkHorizontally`, `shrinkOut`, `shrinkVertically`.
         * ```
         * <AnimatedVisibility visible={"#{@isVisible}"} exit="oneOfTheAnimationsBelow">
         * ...
         * {"shrinkHorizontally": {}}
         * {"shrinkHorizontally": {"shrinkTowards": "CenterHorizontally"}}
         * {"shrinkHorizontally": {"shrinkTowards": "Start", "clip": false}}
         * {"shrinkOut": {}}
         * {"shrinkOut": {"shrinkTowards": "TopEnd"}}
         * {"shrinkOut": {"shrinkTowards": "BottomStart", "clip": false}}
         * {"shrinkVertically": {}}
         * {"shrinkVertically": {"shrinkTowards": "CenterVertically"}}
         * {"shrinkVertically": {"shrinkTowards": "Start", "clip": false}}
         * {"fadeOut": {}}
         * {"fadeOut": {"targetAlpha": 0.5}}
         * {"scaleOut": {}}
         * {"scaleOut": {"targetScale": 0.5}}
         * {"scaleOut": {"targetScale": 0.5, "transformOrigin": {"pivotFractionX": 0.75, "pivotFractionY": 0.25}}}
         * {"slideOut": {"targetOffset": {"x": 10, "y": 20}}}
         * {"slideOutHorizontally": {}}
         * {"slideOutHorizontally": {"targetOffsetX": 12}}
         * {"slideOutVertically": {}}
         * {"slideOutVertically": {"targetOffsetY": 24}}
         * ```
         * You can also use an JSON array to combine animations:
         * ```
         * [{"shrinkHorizontally": {} }, {"slideOutVertically": {} }, {"fadeOut": {} }]
         * ```
         */
        private fun exit(props: Properties, exit: String): Properties {
            return try {
                exitTransitionFromString(exit)?.let {
                    props.copy(exit = it)
                } ?: props
            } catch (_: Exception) {
                props
            }
        }

        /**
         * Optional Label used to differentiate different transitions in Android Studio.
         */
        private fun label(props: Properties, label: String): Properties {
            return props.copy(label = label)
        }

        /**
         * Defines whether the content should be visible.
         * @param visible true if the content should be visible, false otherwise.
         */
        fun visible(props: Properties, visible: String): Properties {
            return props.copy(visible = visible.toBooleanStrictOrNull() ?: true)
        }
    }
}

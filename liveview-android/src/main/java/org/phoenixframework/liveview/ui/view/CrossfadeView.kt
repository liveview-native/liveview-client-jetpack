package org.phoenixframework.liveview.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrAnimationSpec
import org.phoenixframework.liveview.data.constants.Attrs.attrLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrTargetState
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

internal class CrossfadeView private constructor(props: Properties) :
    ComposableView<CrossfadeView.Properties>(props) {
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val targetState = props.targetState
        val animationSpec = props.animationSpec
        val label = props.label
        Crossfade(
            targetState = targetState,
            modifier = props.commonProps.modifier,
            animationSpec = animationSpec ?: tween(),
            label = label ?: "Crossfade",
        ) { targetValue ->
            // TODO Should we pass the target value to the server somehow?
            targetValue
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val targetState: Any = Unit,
        val animationSpec: FiniteAnimationSpec<Float>? = null,
        val label: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<CrossfadeView>() {

        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): CrossfadeView = CrossfadeView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrAnimationSpec -> animationSpec(props, attribute.value)
                attrLabel -> label(props, attribute.value)
                attrTargetState -> targetState(props, attribute.value)
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

        private fun animationSpec(props: Properties, value: String): Properties {
            return props.copy(animationSpec = finiteAnimationSpecFromString(value))
        }

        private fun label(props: Properties, value: String): Properties {
            return props.copy(label = value)
        }

        private fun targetState(props: Properties, value: String): Properties {
            return props.copy(targetState = value)
        }
    }
}

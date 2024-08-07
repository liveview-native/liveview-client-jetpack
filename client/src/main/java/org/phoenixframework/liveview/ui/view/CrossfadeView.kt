package org.phoenixframework.liveview.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrAnimationSpec
import org.phoenixframework.liveview.constants.Attrs.attrLabel
import org.phoenixframework.liveview.constants.Attrs.attrTargetState
import org.phoenixframework.liveview.constants.Attrs.attrViewId
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Crossfade allows to switch between two layouts with a crossfade animation.
 * The current layout must be set in the `targetState` and the new layout value can be get in
 * different ways depending of the usage. In the example below, the value is obtained using
 * `labelValue(<Type>, <ViewId>)` where the `ViewId` must be same specified in the `viewId`
 * attribute, and the `Type` must be: `String`, `Int`, `Float`, `Boolean`, or the class name of a
 * class supported by modifiers or attributes.
 * ```
 * <Crossfade
 *   type="Color" viewId="foo" targetState={"#{@crossfadeColor}"}
 *   animationSpec={%{tween: %{durationMillis: 2000}}} >
 *   <AsyncImage
 *     url="/images/logo.svg"
 *     contentScale="fillWidth"
 *     style={"size(100.dp);background(lambdaValue(Color, \"foo\"))"} />
 * </Crossfade>
 * ```
 * In case of the child of `CrossfadeView` needs the parent value in a property, you can do the
 * following:
 * ```
 * <Crossfade
 *  type="String" viewId="bar" targetState={"#{@imagePath}"}
 *  animationSpec={%{tween: %{durationMillis: 2000}}} >
 *   <AsyncImage
 *     url={%{lambdaValue: "bar"}}}
 *     contentScale="fillWidth"
 *     style={"size(100.dp)"}/>
 *   </AsyncImage>
 * </Crossfade>
 * ```
 */
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
            props.viewId?.let {
                saveViewValue(it, targetValue)
                DisposableEffect(it) {
                    onDispose {
                        removeViewValue(it)
                    }
                }
            }
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
        val viewId: String? = null,
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
                attrViewId -> viewId(props, attribute.value)
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
         * The AnimationSpec to configure the animation.
         * ```
         * <Crossfade animationSpec={%{tween: %{durationMillis: 2000}}} >
         * ```
         */
        private fun animationSpec(props: Properties, value: String): Properties {
            return props.copy(animationSpec = finiteAnimationSpecFromString(value))
        }

        /**
         * An optional label to differentiate from other animations in Android Studio.
         * ```
         * <Crossfade label="Title crossfade" >
         * ```
         */
        private fun label(props: Properties, value: String): Properties {
            return props.copy(label = value)
        }

        /**
         * TargetState is a key representing your target layout state. Every time you change a key
         * the animation will be triggered. The content called with the old key will be faded out
         * while the content called with the new key will be faded in. To use the new target value
         * in the children nodes, use the `lambdaValue` expressions like below.
         * ```
         * <Crossfade
         *   type="Color" viewId="foo" targetState={"#{@crossfadeColor}"}
         *   animationSpec={%{tween: %{durationMillis: 2000}}} >
         *   <AsyncImage
         *     url="/images/logo.svg"
         *     contentScale="fillWidth"
         *     style={"size(100.dp);background(lambdaValue(Color, \"foo\"))"} />
         * </Crossfade>
         * ```
         * In case of the child of `CrossfadeView` needs the parent value in a property, you can do the
         * following:
         * ```
         * <Crossfade
         *  type="String" viewId="bar" targetState={"#{@imagePath}"}
         *  animationSpec={%{tween: %{durationMillis: 2000}}} >
         *   <AsyncImage
         *     url={%{lambdaValue: "bar"}}}
         *     contentScale="fillWidth"
         *     style={"size(100.dp)"}/>
         *   </AsyncImage>
         * </Crossfade>
         * ```
         */
        private fun targetState(props: Properties, value: String): Properties {
            return props.copy(targetState = value)
        }

        /**
         * Assigns an id to the view in order to get the lamdba value from its children.
         * ```
         * <BoxWithConstraintsView viewId="foo" />
         * ```
         */
        private fun viewId(props: Properties, value: String): Properties {
            return props.copy(viewId = value)
        }
    }
}

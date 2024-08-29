package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.constants.Attrs.attrPropagateMinConstraints
import org.phoenixframework.liveview.constants.Attrs.attrViewId
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * `BoxWithConstraints` defines its own content according to the available space, based on the
 * incoming constraints or the current LayoutDirection.
 * ```
 * <BoxWithConstraints style="size(100.dp);background(Color.Red)">
 *   <Icon imageVector="filled:Add" style="align(Alignment.TopStart)"/>
 *   <Text style="align(Alignment.Center)">Text</Text>
 *   <Icon imageVector="filled:Share" style="align(Alignment.BottomEnd)"/>
 * </BoxWithConstraints>
 * ```
 */
internal class BoxWithConstraintsView private constructor(props: Properties) :
    ComposableView<BoxWithConstraintsView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val contentAlignment: Alignment = props.contentAlignment
        val propagateMinConstraints = props.propagateMinConstraints

        BoxWithConstraints(
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
        ) {
            props.viewId?.let {
                saveViewValue("$it.minHeight", minHeight.value)
                saveViewValue("$it.minWidth", minWidth.value)
                saveViewValue("$it.maxHeight", maxHeight.value)
                saveViewValue("$it.maxWidth", maxWidth.value)
                DisposableEffect(it) {
                    onDispose {
                        removeViewValue("$it.minHeight")
                        removeViewValue("$it.minWidth")
                        removeViewValue("$it.maxHeight")
                        removeViewValue("$it.maxWidth")
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
        val contentAlignment: Alignment = Alignment.TopStart,
        val propagateMinConstraints: Boolean = false,
        val viewId: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<BoxWithConstraintsView>() {
        /**
         * Creates a `BoxWithConstraintsView` object based on the attributes of the input
         * `Attributes` object. `BoxWithConstraintsView` co-relates to the `BoxWithConstraints`
         * composable.
         * @param attributes the `Attributes` object to create the `BoxView` object from
         * @return a `BoxWithConstraints` object based on the attributes of the input `Attributes`
         * object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): BoxWithConstraintsView =
            BoxWithConstraintsView(attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrContentAlignment -> contentAlignment(props, attribute.value)
                    attrPropagateMinConstraints -> propagateMinConstraints(props, attribute.value)
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
         * The default alignment inside the BoxWithConstraintsView.
         *
         * ```
         * <BoxWithConstraintsView contentAlignment="Alignment.BbottomEnd">
         *   ...
         * </BoxWithConstraintsView>
         * ```
         * @param contentAlignment children's alignment inside the Box. See the supported at
         * [org.phoenixframework.liveview.constants.AlignmentValues].
         */
        private fun contentAlignment(props: Properties, contentAlignment: String): Properties {
            return props.copy(
                contentAlignment = alignmentFromString(
                    contentAlignment,
                    Alignment.TopStart
                )
            )
        }

        /**
         * Whether the incoming min constraints should be passed to content.
         *
         * ```
         * <BoxWithConstraintsView propagateMinConstraints="true">...</BoxWithConstraintsView>
         * ```
         * @param value true if the incoming min constraints should be passed to content, false
         * otherwise.
         */
        private fun propagateMinConstraints(props: Properties, value: String): Properties {
            return props.copy(propagateMinConstraints = value.toBoolean())
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
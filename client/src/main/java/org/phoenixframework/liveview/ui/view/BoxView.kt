package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.constants.Attrs.attrPropagateMinConstraints
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
 * The Box will organize the components of top of each other in a Z-order.
 * ```
 * <Box style="size(100.dp);background(Color.Red)">
 *   <Icon imageVector="filled:Add" style="align(Alignment.TopStart)"/>
 *   <Text style="align(Alignment.Center)">Text</Text>
 *   <Icon imageVector="filled:Share" style="align(Alignment.BottomEnd)"/>
 * </Box>
 * ```
 */
internal class BoxView private constructor(props: Properties) :
    ComposableView<BoxView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val contentAlignment: Alignment = props.contentAlignment
        val propagateMinConstraints = props.propagateMinConstraints

        Box(
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val contentAlignment: Alignment = Alignment.TopStart,
        val propagateMinConstraints: Boolean = false,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<BoxView>() {
        /**
         * Creates a `BoxView` object based on the attributes of the input `Attributes` object.
         * BoxView co-relates to the Box composable
         * @param attributes the `Attributes` object to create the `BoxView` object from
         * @return a `BoxView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): BoxView = BoxView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrContentAlignment -> contentAlignment(props, attribute.value)
                attrPropagateMinConstraints -> propagateMinConstraints(props, attribute.value)
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
         * The default alignment inside the Box.
         *
         * ```
         * <Box contentAlignment="Alignment.BbottomEnd">...</Box>
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
         * <Box propagateMinConstraints="true">...</Box>
         * ```
         * @param value true if the incoming min constraints should be passed to content, false
         * otherwise.
         */
        private fun propagateMinConstraints(props: Properties, value: String): Properties {
            return props.copy(propagateMinConstraints = value.toBoolean())
        }
    }
}
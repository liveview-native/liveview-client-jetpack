package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

/**
 * Component that represents an empty space layout, whose size can be defined using width, height
 * and size attributes.
 * ```
 * <Spacer style="height(8.dp)" />
 * ```
 */
internal class SpacerView private constructor(props: Properties) :
    ComposableView<SpacerView.Properties>(props) {
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        Spacer(modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues))
    }

    @Stable
    internal data class Properties(
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<SpacerView>() {

        /**
         * Creates a `SpacerView` object based on the attributes of the input `Attributes` object.
         * SpacerView co-relates to the Spacer composable
         * @param attributes the `Attributes` object to create the `SliderView` object from
         * @return a `SpacerView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): SpacerView = SpacerView(attributes.fold(Properties()) { props, attribute ->
            props.copy(
                commonProps = handleCommonAttributes(
                    props.commonProps,
                    attribute,
                    pushEvent,
                    scope
                )
            )
        })
    }
}

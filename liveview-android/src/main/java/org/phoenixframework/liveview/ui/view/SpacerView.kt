package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.data.ComposableTreeNode

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
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        fun build() = SpacerView(Properties(commonProps))
    }
}

internal object SpacerViewFactory : ComposableViewFactory<SpacerView>() {

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
    ): SpacerView = attributes.fold(SpacerView.Builder()) { builder, attribute ->
        builder.handleCommonAttributes(attribute, pushEvent, scope) as SpacerView.Builder
    }.build()
}

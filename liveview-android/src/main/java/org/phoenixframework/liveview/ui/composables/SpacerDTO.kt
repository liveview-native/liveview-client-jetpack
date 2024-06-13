package org.phoenixframework.liveview.ui.composables

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
internal class SpacerDTO private constructor(props: Properties) :
    ComposableView<SpacerDTO.Properties>(props) {
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
        fun build() = SpacerDTO(Properties(commonProps))
    }
}

internal object SpacerDtoFactory : ComposableViewFactory<SpacerDTO>() {

    /**
     * Creates a `SpacerDTO` object based on the attributes of the input `Attributes` object.
     * SpacerDTO co-relates to the Spacer composable
     * @param attributes the `Attributes` object to create the `SliderDTO` object from
     * @return a `SpacerDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): SpacerDTO = attributes.fold(SpacerDTO.Builder()) { builder, attribute ->
        builder.handleCommonAttributes(attribute, pushEvent, scope) as SpacerDTO.Builder
    }.build()
}

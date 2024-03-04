package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrPropagateMinConstraints
import org.phoenixframework.liveview.data.constants.Attrs.attrScroll
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * The Box will organize the components of top of each other in a Z-order.
 * ```
 * <Box size="100" background="#FFFF0000">
 *   <Icon imageVector="filled:Add" align="topStart"/>
 *   <Text align="center">Text</Text>
 *   <Icon imageVector="filled:Share" align="bottomEnd"/>
 * </Box>
 * ```
 */
internal class BoxDTO private constructor(props: Properties) :
    ComposableView<BoxDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val contentAlignment: Alignment = props.contentAlignment
        val propagateMinConstraints = props.propagateMinConstraints
        val hasVerticalScroll = props.commonProps.hasVerticalScrolling
        val hasHorizontalScroll = props.commonProps.hasHorizontalScrolling

        Box(
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues)
                .optional(
                    hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                )
                .optional(
                    hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                ),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val contentAlignment: Alignment,
        val propagateMinConstraints: Boolean,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var contentAlignment: Alignment = Alignment.TopStart
        private var propagateMinConstraints: Boolean = false

        /**
         * The default alignment inside the Box.
         *
         * ```
         * <Box contentAlignment="bottomEnd">...</Box>
         * ```
         * @param contentAlignment children's alignment inside the Box. See the supported at
         * [org.phoenixframework.liveview.data.constants.AlignmentValues].
         */
        fun contentAlignment(contentAlignment: String) = apply {
            this.contentAlignment = alignmentFromString(contentAlignment, Alignment.TopStart)
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
        fun propagateMinConstraints(value: String) = apply {
            this.propagateMinConstraints = value.toBoolean()
        }

        fun build(): BoxDTO = BoxDTO(
            Properties(
                contentAlignment,
                propagateMinConstraints,
                commonProps,
            )
        )
    }
}

internal object BoxDtoFactory : ComposableViewFactory<BoxDTO>() {
    /**
     * Creates a `BoxDTO` object based on the attributes of the input `Attributes` object.
     * BoxDTO co-relates to the Box composable
     * @param attributes the `Attributes` object to create the `BoxDTO` object from
     * @return a `BoxDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): BoxDTO = attributes.fold(BoxDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContentAlignment -> builder.contentAlignment(attribute.value)
            attrPropagateMinConstraints -> builder.propagateMinConstraints(attribute.value)
            attrScroll -> builder.scrolling(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as BoxDTO.Builder
    }.build()
}
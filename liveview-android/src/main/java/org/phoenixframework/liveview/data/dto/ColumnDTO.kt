package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrScroll
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A layout composable that places its children in a vertical sequence.
 * ```
 * <Column height="200" width="200" background="#FFCCCCCC">
 *   // Children
 * </Column>
 * ```
 */
internal class ColumnDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    private val horizontalAlignment: Alignment.Horizontal = builder.horizontalAlignment
    private val hasVerticalScroll = builder.hasVerticalScrolling
    private val hasHorizontalScroll = builder.hasHorizontalScrolling

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        Column(
            modifier = modifier
                .paddingIfNotNull(paddingValues)
                .optional(
                    hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                )
                .optional(
                    hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                ),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
            private set
        var horizontalAlignment: Alignment.Horizontal = Alignment.Start
            private set

        /**
         * The vertical arrangement of the Column's children
         *
         * ```
         * <Column vertical-arrangement="spaceAround" >...</Column>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. The
         * supported values are: `top`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `bottom`,
         * and `center`. An int value is also supported, which will be used to determine the space.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = verticalArrangementFromString(verticalArrangement)
        }

        /**
         * The horizontal alignment of the Column's children
         *
         * ```
         * <Column horizontal-alignment="center" >...</Column>
         * ```
         * @param horizontalAlignment the horizontal alignment of the column's children. The
         * supported values are: `start`, `center`, and `end`.
         */
        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = horizontalAlignmentFromString(horizontalAlignment)
        }

        fun build(): ColumnDTO = ColumnDTO(this)
    }
}

internal object ColumnDtoFactory : ComposableViewFactory<ColumnDTO, ColumnDTO.Builder>() {
    /**
     * Creates a `ColumnDTO` object based on the attributes of the input `Attributes` object.
     * ColumnDTO co-relates to the Column composable
     * @param attributes the `Attributes` object to create the `ColumnDTO` object from
     * @return a `ColumnDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ColumnDTO = attributes.fold(ColumnDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrHorizontalAlignment -> builder.horizontalAlignment(attribute.value)
            attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
            attrScroll -> builder.scrolling(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ColumnDTO.Builder
    }.build()
}
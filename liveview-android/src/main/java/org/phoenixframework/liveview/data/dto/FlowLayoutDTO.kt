package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxItemsInEachColumn
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxItemsInEachRow
import org.phoenixframework.liveview.data.constants.Attrs.attrScroll
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.optional
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * FlowColumn is a layout that fills items from top to bottom, and when it runs out of space on the
 * bottom, moves to the next "column" or "line" on the right or left based on ltr or rtl layouts,
 * and then continues filling items from top to bottom.
 *
 * FlowRow is a layout that fills items from left to right (ltr) in LTR layouts or right to left
 * (rtl) in RTL layouts and when it runs out of space, moves to the next "row" or "line" positioned
 * on the bottom, and then continues filling items until the items run out.
 */
@OptIn(ExperimentalLayoutApi::class)
internal class FlowLayoutDTO private constructor(props: Properties) :
    ComposableView<FlowLayoutDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val horizontalArrangement = props.horizontalArrangement
        val verticalArrangement = props.verticalArrangement
        val maxItems = props.maxItems
        val hasVerticalScroll = props.commonProps.hasVerticalScrolling
        val hasHorizontalScroll = props.commonProps.hasHorizontalScrolling

        when (composableNode?.node?.tag) {
            ComposableTypes.flowColumn -> {
                FlowColumn(
                    modifier = props.commonProps.modifier
                        .paddingIfNotNull(paddingValues)
                        .optional(
                            hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                        )
                        .optional(
                            hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                        ),
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement,
                    maxItemsInEachColumn = maxItems,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }

            ComposableTypes.flowRow -> {
                FlowRow(
                    modifier = props.commonProps.modifier
                        .paddingIfNotNull(paddingValues)
                        .optional(
                            hasVerticalScroll, Modifier.verticalScroll(rememberScrollState())
                        )
                        .optional(
                            hasHorizontalScroll, Modifier.horizontalScroll(rememberScrollState())
                        ),
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement,
                    maxItemsInEachRow = maxItems,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        }
    }

    @Stable
    internal data class Properties(
        val horizontalArrangement: Arrangement.Horizontal,
        val maxItems: Int,
        val verticalArrangement: Arrangement.Vertical,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
        private var maxItems: Int = Int.MAX_VALUE
        private var verticalArrangement: Arrangement.Vertical = Arrangement.Top

        /**
         * The horizontal arrangement of the children
         *
         * ```
         * <FlowColumn horizontalArrangement="spaceAround" >...</FlowColumn>
         * <FlowRow horizontalArrangement="spaceAround" >...</FlowRow>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.HorizontalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun horizontalArrangement(horizontalArrangement: String) = apply {
            this.horizontalArrangement = horizontalArrangementFromString(horizontalArrangement)
        }

        /**
         * The maximum number of items per column (for FlowColumn) or row (for FlowRow).
         *
         * ```
         * <FlowColumn maxItemsInEachColumn="10" >...</FlowColumn>
         * <FlowRow maxItemsInEachRow="10" >...</FlowRow>
         * ```
         * @param max the maximum number of items of each column/row.
         */
        fun maxItems(max: String) = apply {
            this.maxItems = max.toIntOrNull() ?: Int.MAX_VALUE
        }

        /**
         * The vertical arrangement of the children
         *
         * ```
         * <FlowColumn verticalArrangement="spaceAround" >...</FlowColumn>
         * <FlowRow verticalArrangement="spaceAround" >...</FlowRow>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.VerticalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = verticalArrangementFromString(verticalArrangement)
        }

        fun build() = FlowLayoutDTO(
            Properties(
                horizontalArrangement,
                maxItems,
                verticalArrangement,
                commonProps,
            )
        )
    }
}

internal object FlowLayoutDtoFactory : ComposableViewFactory<FlowLayoutDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): FlowLayoutDTO = attributes.fold(FlowLayoutDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrMaxItemsInEachColumn, attrMaxItemsInEachRow -> builder.maxItems(attribute.value)
            attrHorizontalArrangement -> builder.horizontalArrangement(attribute.value)
            attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
            attrScroll -> builder.scrolling(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as FlowLayoutDTO.Builder
    }.build()
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.constants.Attrs.attrMaxItemsInEachColumn
import org.phoenixframework.liveview.constants.Attrs.attrMaxItemsInEachRow
import org.phoenixframework.liveview.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.constants.ComposableTypes
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
 * FlowColumn is a layout that fills items from top to bottom, and when it runs out of space on the
 * bottom, moves to the next "column" or "line" on the right or left based on ltr or rtl layouts,
 * and then continues filling items from top to bottom.
 *
 * FlowRow is a layout that fills items from left to right (ltr) in LTR layouts or right to left
 * (rtl) in RTL layouts and when it runs out of space, moves to the next "row" or "line" positioned
 * on the bottom, and then continues filling items until the items run out.
 */
@OptIn(ExperimentalLayoutApi::class)
internal class FlowLayoutView private constructor(props: Properties) :
    ComposableView<FlowLayoutView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val horizontalArrangement = props.horizontalArrangement
        val verticalArrangement = props.verticalArrangement
        val maxItems = props.maxItems

        when (composableNode?.node?.tag) {
            ComposableTypes.flowColumn -> {
                FlowColumn(
                    modifier = props.commonProps.modifier
                        .paddingIfNotNull(paddingValues),
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
                        .paddingIfNotNull(paddingValues),
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
        val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        val maxItems: Int = Int.MAX_VALUE,
        val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<FlowLayoutView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): FlowLayoutView = FlowLayoutView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrMaxItemsInEachColumn, attrMaxItemsInEachRow -> maxItems(props, attribute.value)
                attrHorizontalArrangement -> horizontalArrangement(props, attribute.value)
                attrVerticalArrangement -> verticalArrangement(props, attribute.value)
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
         * The horizontal arrangement of the children
         *
         * ```
         * <FlowColumn horizontalArrangement="spaceAround" >...</FlowColumn>
         * <FlowRow horizontalArrangement="spaceAround" >...</FlowRow>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.constants.HorizontalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        private fun horizontalArrangement(
            props: Properties,
            horizontalArrangement: String
        ): Properties {
            return props.copy(
                horizontalArrangement = horizontalArrangementFromString(
                    horizontalArrangement
                )
            )
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
        private fun maxItems(props: Properties, max: String): Properties {
            return props.copy(maxItems = max.toIntOrNull() ?: Int.MAX_VALUE)
        }

        /**
         * The vertical arrangement of the children
         *
         * ```
         * <FlowColumn verticalArrangement="spaceAround" >...</FlowColumn>
         * <FlowRow verticalArrangement="spaceAround" >...</FlowRow>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.constants.VerticalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        private fun verticalArrangement(
            props: Properties,
            verticalArrangement: String
        ): Properties {
            return props.copy(
                verticalArrangement = verticalArrangementFromString(
                    verticalArrangement
                )
            )
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.constants.Attrs.attrVerticalAlignment
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
 * A layout composable that places its children in a horizontal sequence.
 * ```
 * <Row style="fillMaxWidth().wrapContentWidth();background(Color.Gray)">
 *   // Children
 * </Row>
 * ```
 */
internal class RowView private constructor(props: Properties) :
    ComposableView<RowView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val horizontalArrangement = props.horizontalArrangement
        val verticalAlignment = props.verticalAlignment

        Row(
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        val verticalAlignment: Alignment.Vertical = Alignment.Top,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<RowView>() {
        /**
         * Creates a `RowView` object based on the attributes of the input `Attributes` object.
         * RowView co-relates to the Row composable
         * @param attributes the `Attributes` object to create the `RowView` object from
         * @return a `RowView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): RowView = RowView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrHorizontalArrangement -> horizontalArrangement(props, attribute.value)
                attrVerticalAlignment -> verticalAlignment(props, attribute.value)
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
         * The horizontal arrangement of the Row's children
         *
         * ```
         * <Row horizontalArrangement="spaceAround" >...</Row>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the row's children. See the
         * supported values at [org.phoenixframework.liveview.constants.HorizontalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        private fun horizontalArrangement(
            props: Properties,
            horizontalArrangement: String
        ): Properties {
            return if (horizontalArrangement.isNotEmpty()) {
                props.copy(
                    horizontalArrangement = horizontalArrangementFromString(
                        horizontalArrangement
                    )
                )
            } else props
        }

        /**
         * The vertical alignment of the Row's children
         *
         * ```
         * <Row verticalAlignment="center" >...</Row>
         * ```
         * @param verticalAlignment the vertical alignment of the row's children. See the
         * supported values at [org.phoenixframework.liveview.constants.VerticalAlignmentValues].
         */
        private fun verticalAlignment(props: Properties, verticalAlignment: String): Properties {
            return if (verticalAlignment.isNotEmpty()) {
                props.copy(verticalAlignment = verticalAlignmentFromString(verticalAlignment))
            } else props
        }
    }
}
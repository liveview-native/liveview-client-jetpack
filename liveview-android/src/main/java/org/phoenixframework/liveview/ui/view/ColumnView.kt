package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A layout composable that places its children in a vertical sequence.
 * ```
 * <Column style="height(200.dp);width(200.dp);background(Color.Gray)">
 *   // Children
 * </Column>
 * ```
 */
internal class ColumnView private constructor(props: Properties) :
    ComposableView<ColumnView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val verticalArrangement = props.verticalArrangement
        val horizontalAlignment = props.horizontalAlignment

        Column(
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val verticalArrangement: Arrangement.Vertical,
        val horizontalAlignment: Alignment.Horizontal,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var verticalArrangement: Arrangement.Vertical = Arrangement.Top
        private var horizontalAlignment: Alignment.Horizontal = Alignment.Start

        /**
         * The vertical arrangement of the Column's children
         *
         * ```
         * <Column verticalArrangement="spaceAround" >...</Column>
         * ```
         * @param verticalArrangement the vertical arrangement of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.VerticalArrangementValues].
         * An int value is also supported, which will be used to determine the space between the
         * items.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = verticalArrangementFromString(verticalArrangement)
        }

        /**
         * The horizontal alignment of the Column's children
         *
         * ```
         * <Column horizontalAlignment="center" >...</Column>
         * ```
         * @param horizontalAlignment the horizontal alignment of the column's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues].
         */
        fun horizontalAlignment(horizontalAlignment: String) = apply {
            this.horizontalAlignment = horizontalAlignmentFromString(horizontalAlignment)
        }

        fun build(): ColumnView = ColumnView(
            Properties(
                verticalArrangement,
                horizontalAlignment,
                commonProps,
            )
        )
    }
}

internal object ColumnViewFactory : ComposableViewFactory<ColumnView>() {
    /**
     * Creates a `ColumnView` object based on the attributes of the input `Attributes` object.
     * ColumnView co-relates to the Column composable
     * @param attributes the `Attributes` object to create the `ColumnView` object from
     * @return a `ColumnView` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ColumnView = attributes.fold(ColumnView.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrHorizontalAlignment -> builder.horizontalAlignment(attribute.value)
            attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ColumnView.Builder
    }.build()
}
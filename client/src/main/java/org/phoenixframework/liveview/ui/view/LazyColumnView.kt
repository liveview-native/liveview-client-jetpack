package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * The vertically scrolling list that only composes and lays out the currently visible items.
 * ```
 * <LazyColumn style="fillMaxWidth()" verticalArrangement="center" horizontalAlignment="center">
 *   // Children
 * </LazyColumn>
 * ```
 */
internal class LazyColumnView private constructor(props: Properties) :
    LazyView<LazyColumnView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val verticalArrangement = props.verticalArrangement
        val horizontalAlignment = props.horizontalAlignment
        val reverseLayout = props.lazyListProps.reverseLayout
        val userScrollEnabled = props.lazyListProps.userScrollEnabled
        val contentPadding = props.lazyListProps.contentPadding

        LazyColumn(
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            contentPadding = PaddingValues(
                (contentPadding[LazyView.Factory.START] ?: 0).dp,
                (contentPadding[LazyView.Factory.TOP] ?: 0).dp,
                (contentPadding[LazyView.Factory.END] ?: 0).dp,
                (contentPadding[LazyView.Factory.BOTTOM] ?: 0).dp
            ),
            // TODO flingBehavior = ,
            userScrollEnabled = userScrollEnabled,
            content = {
                items(
                    composableNode?.children ?: emptyArray(),
                    key = { item -> item.id },
                ) { item ->
                    PhxLiveView(item, pushEvent, composableNode, null, this)
                }
            },
        )
    }

    @Stable
    internal data class Properties(
        val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        val horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
        override val lazyListProps: LazyListProperties = LazyListProperties(),
    ) : ILazyListProperties

    internal object Factory : LazyView.Factory() {
        /**
         * Creates a `LazyColumnView` object based on the attributes of the input `Attributes` object.
         * LazyColumnView co-relates to the LazyColumn composable
         * @param attributes the `Attributes` object to create the `LazyColumnView` object from
         * @return a `LazyColumnView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): LazyColumnView = LazyColumnView(
            attributes.fold(Properties()) { props, attribute ->
                handleLazyAttribute(props.lazyListProps, attribute)?.let {
                    props.copy(lazyListProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrHorizontalAlignment -> horizontalAlignment(props, attribute.value)
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
                }
            })

        /**
         * The vertical arrangement of the Column's children
         *
         * ```
         * <LazyColumn verticalArrangement="spaceAround" >...</LazyColumn>
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

        /**
         * The horizontal alignment of the Column's children
         *
         * ```
         * <LazyColumn horizontalAlignment="center" >...</LazyColumn>
         * ```
         * @param horizontalAlignment the horizontal alignment of the column's children. See the
         * supported values at [org.phoenixframework.liveview.constants.HorizontalAlignmentValues].
         */
        private fun horizontalAlignment(
            props: Properties,
            horizontalAlignment: String
        ): Properties {
            return props.copy(
                horizontalAlignment = horizontalAlignmentFromString(
                    horizontalAlignment
                )
            )
        }
    }
}
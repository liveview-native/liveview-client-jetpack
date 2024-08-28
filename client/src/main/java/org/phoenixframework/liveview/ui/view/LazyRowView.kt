package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * The horizontally scrolling list that only composes and lays out the currently visible items.
 * ```
 * <LazyRow style="fillMaxWidth()" horizontalArrangement="start" verticalAlignment="center" >
 *   // Children
 * </LazyRow>
 * ```
 */
internal class LazyRowView private constructor(props: Properties) :
    LazyView<LazyRowView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val horizontalArrangement = props.horizontalArrangement
        val verticalAlignment = props.verticalAlignment
        val reverseLayout = props.lazyListProps.reverseLayout
        val userScrollEnabled = props.lazyListProps.userScrollEnabled
        val contentPadding = props.lazyListProps.contentPadding

        LazyRow(
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            reverseLayout = reverseLayout,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            contentPadding = PaddingValues(
                (contentPadding[LazyView.Factory.START] ?: 0).dp,
                (contentPadding[LazyView.Factory.TOP] ?: 0).dp,
                (contentPadding[LazyView.Factory.END] ?: 0).dp,
                (contentPadding[LazyView.Factory.BOTTOM] ?: 0).dp
            ),
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
        val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        val verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
        override val lazyListProps: LazyListProperties = LazyListProperties(),
    ) : ILazyListProperties

    internal object Factory : LazyView.Factory() {
        /**
         * Creates a `LazyRowView` object based on the attributes of the input `Attributes` object.
         * LazyRowView co-relates to the LazyRow composable
         * @param attributes the `Attributes` object to create the `LazyRowView` object from
         * @return a `LazyRowView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): LazyRowView = LazyRowView(
            attributes.fold(Properties()) { props, attribute ->
                handleLazyAttribute(props.lazyListProps, attribute)?.let {
                    props.copy(lazyListProps = it)
                } ?: run {
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
                }
            })

        /**
         * The horizontal arrangement of the Row's children
         *
         * ```
         * <LazyRow horizontalArrangement="spaceAround" >...</LazyRow>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the column's children. See the
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
         * <LazyRow verticalAlignment="center" >...</LazyRow>
         * ```
         * @param verticalAlignment the vertical alignment of the row's children. See the supported
         * values at [org.phoenixframework.liveview.constants.VerticalAlignmentValues].
         */
        private fun verticalAlignment(props: Properties, verticalAlignment: String): Properties {
            return if (verticalAlignment.isNotEmpty()) {
                props.copy(verticalAlignment = verticalAlignmentFromString(verticalAlignment))
            } else props
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrColumns
import org.phoenixframework.liveview.constants.Attrs.attrCount
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.constants.Attrs.attrMinSize
import org.phoenixframework.liveview.constants.Attrs.attrRows
import org.phoenixframework.liveview.constants.Attrs.attrSize
import org.phoenixframework.liveview.constants.Attrs.attrType
import org.phoenixframework.liveview.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.LazyGridColumnTypeValues
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * A lazy grid layout. It could be vertical, using `LazyVerticalGrid`, or horizontal using
 * `LazyHorizontalGrid`. It composes only visible elements of the grid.
 * ```
 * <LazyVerticalGrid columns="{'type': 'fixed', 'count': '3'}">
 *   // Children
 * </LazyVerticalGrid>
 *
 * <LazyHorizontalGrid rows="{'type': 'fixed', 'count': '3'}">
 *   // Children
 * </LazyHorizontalGrid>
 * ```
 */
internal class LazyGridView private constructor(props: Properties) :
    LazyView<LazyGridView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val verticalArrangement = props.verticalArrangement
        val horizontalArrangement = props.horizontalArrangement
        val reverseLayout = props.lazyListProps.reverseLayout
        val userScrollEnabled = props.lazyListProps.userScrollEnabled
        val gridCells = props.gridCells
        val contentPadding = props.lazyListProps.contentPadding

        when (composableNode?.node?.tag) {
            ComposableTypes.lazyHorizontalGrid ->
                LazyHorizontalGrid(
                    rows = gridCells,
                    modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
                    contentPadding = PaddingValues(
                        (contentPadding[LazyView.Factory.START] ?: 0).dp,
                        (contentPadding[LazyView.Factory.TOP] ?: 0).dp,
                        (contentPadding[LazyView.Factory.END] ?: 0).dp,
                        (contentPadding[LazyView.Factory.BOTTOM] ?: 0).dp
                    ),
                    reverseLayout = reverseLayout,
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement,
                    // TODO flingBehavior = ,
                    userScrollEnabled = userScrollEnabled,
                    content = {
                        items(
                            composableNode.children,
                            key = { item -> item.id },
                        ) { item ->
                            PhxLiveView(item, pushEvent, composableNode, null, this)
                        }
                    },
                )

            ComposableTypes.lazyVerticalGrid ->
                LazyVerticalGrid(
                    columns = gridCells,
                    modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
                    contentPadding = PaddingValues(
                        (contentPadding[LazyView.Factory.START] ?: 0).dp,
                        (contentPadding[LazyView.Factory.TOP] ?: 0).dp,
                        (contentPadding[LazyView.Factory.END] ?: 0).dp,
                        (contentPadding[LazyView.Factory.BOTTOM] ?: 0).dp
                    ),
                    reverseLayout = reverseLayout,
                    verticalArrangement = verticalArrangement,
                    horizontalArrangement = horizontalArrangement,
                    // TODO flingBehavior = ,
                    userScrollEnabled = userScrollEnabled,
                    content = {
                        items(
                            composableNode.children,
                            key = { item -> item.id },
                        ) { item ->
                            PhxLiveView(item, pushEvent, composableNode, null, this)
                        }
                    },
                )
        }
    }

    @Stable
    internal data class Properties(
        val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
        val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
        val gridCells: GridCells = GridCells.Fixed(1),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
        override val lazyListProps: LazyListProperties = LazyListProperties()
    ) : ILazyListProperties

    internal object Factory : LazyView.Factory() {
        /**
         * Creates a `LazyGridView` object based on the attributes of the input `Attributes` object.
         * LazyGridView co-relates to the LazyHorizontalGrid or LazyVerticalGrid composable depending of
         * the tag used.
         * @param attributes the `Attributes` object to create the `LazyVerticalGridView` object from
         * @return a `LazyGridView` object based on the attributes of the input `Attributes` object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): LazyGridView = LazyGridView(
            attributes.fold(Properties()) { props, attribute ->
                handleLazyAttribute(props.lazyListProps, attribute)?.let {
                    props.copy(lazyListProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrColumns, attrRows -> columns(props, attribute.value)
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
                }
            })

        /**
         * Describes the count and the size of the grid's columns. The supported values are:
         * - `fixed` and the number of columns (`count`);
         * - `adaptive` and the minimum cell size (`minSize`);
         * - `fixedSize` and the size of the cell (`size`).
         * ```
         * <LazyVerticalGrid columns="{'type': 'fixed', 'count': '2'}">
         * <LazyVerticalGrid columns="{'type': 'adaptive', 'minSize': '100'}">
         * <LazyVerticalGrid columns="{'type': 'fixedSize', 'size': '150'}">
         * ```
         */
        private fun columns(props: Properties, columns: String): Properties {
            return try {
                val map = JsonParser.parse<Map<String, String>>(columns)
                val gridCells = when (map?.get(attrType)) {
                    LazyGridColumnTypeValues.fixed ->
                        GridCells.Fixed(map[attrCount]!!.toInt())

                    LazyGridColumnTypeValues.adaptive ->
                        GridCells.Adaptive(map[attrMinSize]!!.toInt().dp)

                    LazyGridColumnTypeValues.fixedSize ->
                        GridCells.FixedSize(map[attrSize]!!.toInt().dp)

                    else -> return props
                }
                props.copy(gridCells = gridCells)
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }

        /**
         * The vertical arrangement of the Grid's children
         *
         * ```
         * <LazyVerticalGrid verticalArrangement="spaceAround" >...</LazyVerticalGrid>
         * ```
         * @param verticalArrangement the vertical arrangement of the grid's children. See the
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
         * The horizontal arrangement of the Grid's children
         *
         * ```
         * <LazyVerticalGrid horizontalArrangement="2" >...</LazyVerticalGrid>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the grid's children. See the
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
    }
}
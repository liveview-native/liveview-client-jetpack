package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.constants.Attrs.attrColumns
import org.phoenixframework.liveview.data.constants.Attrs.attrCount
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrMinSize
import org.phoenixframework.liveview.data.constants.Attrs.attrRows
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrType
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.constants.LazyGridColumnTypeValues
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.paddingIfNotNull
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class LazyGridDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val verticalArrangement: Arrangement.Vertical = builder.verticalArrangement
    private val horizontalArrangement: Arrangement.Horizontal = builder.horizontalArrangement
    private val contentPadding: ImmutableMap<String, Int> = builder.contentPadding.toImmutableMap()
    private val reverseLayout: Boolean = builder.reverseLayout
    private val userScrollEnabled: Boolean = builder.userScrollEnabled
    private val gridCells: GridCells = builder.gridCells

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        when (composableNode?.node?.tag) {
            ComposableTypes.lazyHorizontalGrid ->
                LazyHorizontalGrid(
                    rows = gridCells,
                    modifier = modifier.paddingIfNotNull(paddingValues),
                    contentPadding = PaddingValues(
                        (contentPadding[LazyComposableBuilder.START] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.TOP] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.END] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.BOTTOM] ?: 0).dp
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
                    modifier = modifier.paddingIfNotNull(paddingValues),
                    contentPadding = PaddingValues(
                        (contentPadding[LazyComposableBuilder.START] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.TOP] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.END] ?: 0).dp,
                        (contentPadding[LazyComposableBuilder.BOTTOM] ?: 0).dp
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

    internal class Builder : LazyComposableBuilder() {

        var verticalArrangement: Arrangement.Vertical = Arrangement.Top
            private set
        var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
            private set
        var gridCells: GridCells = GridCells.Fixed(1)
            private set


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
        fun columns(columns: String) = apply {
            try {
                val map = JsonParser.parse<Map<String, String>>(columns)
                when (map?.get(attrType)) {
                    LazyGridColumnTypeValues.fixed ->
                        this.gridCells = GridCells.Fixed(map[attrCount]!!.toInt())

                    LazyGridColumnTypeValues.adaptive ->
                        this.gridCells = GridCells.Adaptive(map[attrMinSize]!!.toInt().dp)

                    LazyGridColumnTypeValues.fixedSize ->
                        this.gridCells = GridCells.FixedSize(map[attrSize]!!.toInt().dp)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * The vertical arrangement of the Grid's children
         *
         * ```
         * <LazyVerticalGrid verticalArrangement="spaceAround" >...</LazyVerticalGrid>
         * ```
         * @param verticalArrangement the vertical arrangement of the grid's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.VerticalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun verticalArrangement(verticalArrangement: String) = apply {
            this.verticalArrangement = verticalArrangementFromString(verticalArrangement)
        }

        /**
         * The horizontal arrangement of the Grid's children
         *
         * ```
         * <LazyVerticalGrid horizontalArrangement="2" >...</LazyVerticalGrid>
         * ```
         * @param horizontalArrangement the horizontal arrangement of the grid's children. See the
         * supported values at [org.phoenixframework.liveview.data.constants.HorizontalArrangementValues].
         * An int value is also supported, which will be used to determine the space.
         */
        fun horizontalArrangement(horizontalArrangement: String) = apply {
            if (horizontalArrangement.isNotEmpty()) {
                this.horizontalArrangement = horizontalArrangementFromString(horizontalArrangement)
            }
        }

        fun build() = LazyGridDTO(this)
    }
}

internal object LazyGridDtoFactory :
    ComposableViewFactory<LazyGridDTO, LazyGridDTO.Builder>() {
    /**
     * Creates a `LazyGridDTO` object based on the attributes of the input `Attributes` object.
     * LazyGridDTO co-relates to the LazyHorizontalGrid or LazyVerticalGrid composable depending of
     * the tag used.
     * @param attributes the `Attributes` object to create the `LazyVerticalGridDTO` object from
     * @return a `LazyGridDTO` object based on the attributes of the input `Attributes` object.
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): LazyGridDTO = LazyGridDTO.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleLazyAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrColumns, attrRows -> builder.columns(attribute.value)
                    attrHorizontalArrangement -> builder.horizontalArrangement(attribute.value)
                    attrVerticalArrangement -> builder.verticalArrangement(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as LazyGridDTO.Builder
            }
        }
    }.build()
}
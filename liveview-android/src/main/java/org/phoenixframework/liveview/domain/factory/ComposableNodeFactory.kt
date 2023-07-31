package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.data.dto.AsyncImageDtoFactory
import org.phoenixframework.liveview.data.dto.ButtonDtoFactory
import org.phoenixframework.liveview.data.dto.CardDtoFactory
import org.phoenixframework.liveview.data.dto.ColumnDTO
import org.phoenixframework.liveview.data.dto.ColumnDtoFactory
import org.phoenixframework.liveview.data.dto.IconButtonDtoFactory
import org.phoenixframework.liveview.data.dto.IconDtoFactory
import org.phoenixframework.liveview.data.dto.LazyColumnDtoFactory
import org.phoenixframework.liveview.data.dto.LazyRowDtoFactory
import org.phoenixframework.liveview.data.dto.RowDtoFactory
import org.phoenixframework.liveview.data.dto.ScaffoldDtoFactory
import org.phoenixframework.liveview.data.dto.SpacerDtoFactory
import org.phoenixframework.liveview.data.dto.TextDtoFactory
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.lib.Node

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
object ComposableNodeFactory {

    val registry = ComposableRegistry()

    init {
        registry.run {
            registerComponent(ComposableTypes.asyncImage, AsyncImageDtoFactory)
            registerComponent(ComposableTypes.button, ButtonDtoFactory)
            registerComponent(ComposableTypes.card, CardDtoFactory)
            registerComponent(ComposableTypes.column, ColumnDtoFactory)
            registerComponent(ComposableTypes.icon, IconDtoFactory)
            registerComponent(ComposableTypes.lazyColumn, LazyColumnDtoFactory)
            registerComponent(ComposableTypes.lazyRow, LazyRowDtoFactory)
            registerComponent(ComposableTypes.row, RowDtoFactory)
            registerComponent(ComposableTypes.scaffold, ScaffoldDtoFactory)
            registerComponent(ComposableTypes.spacer, SpacerDtoFactory)
            registerComponent(ComposableTypes.text, TextDtoFactory)
            registerComponent(ComposableTypes.topAppBar, TopAppBarDtoFactory)

            // Specific for TopAppBar
            registerComponent("Title", RowDtoFactory)
            registerComponent("Action", IconButtonDtoFactory)
            registerComponent("NavIcon", IconButtonDtoFactory)
        }
    }

    /**
     * Creates a `ComposableTreeNode` object based on the input `Element` object.
     *
     * @param element the `Element` object to create the `ComposableTreeNode` object from
     * @return a `ComposableTreeNode` object based on the input `Element` object
     */
    fun buildComposableTreeNode(
        element: Node.Element, children: List<Node>, pushEvent: PushEvent
    ): ComposableTreeNode {
        return ComposableTreeNode(
            element.tag,
            registry.getComponentFactory(element.tag)?.buildComposableView(
                element.attributes,
                children,
                pushEvent
            ) ?: run {
                TextDtoFactory.buildComposableView(
                    "${element.tag} not supported yet",
                    element.attributes,
                )
            }
        )
    }

    fun createEmptyNode(): ComposableView {
        return ColumnDTO.Builder().build()
    }
}
package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.dto.AsyncImageDtoFactory
import org.phoenixframework.liveview.data.dto.BottomDtoFactory
import org.phoenixframework.liveview.data.dto.ButtonDtoFactory
import org.phoenixframework.liveview.data.dto.CardDtoFactory
import org.phoenixframework.liveview.data.dto.ColumnDtoFactory
import org.phoenixframework.liveview.data.dto.FloatingActionButtonDtoFactory
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
import org.phoenixframework.liveview.lib.NodeRef

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
object ComposableNodeFactory {

    init {
        ComposableRegistry.run {
            registerComponent(ComposableTypes.asyncImage, AsyncImageDtoFactory)
            registerComponent(ComposableTypes.box, BottomDtoFactory)
            registerComponent(ComposableTypes.button, ButtonDtoFactory)
            registerComponent(ComposableTypes.card, CardDtoFactory)
            registerComponent(ComposableTypes.column, ColumnDtoFactory)
            registerComponent(ComposableTypes.fab, FloatingActionButtonDtoFactory)
            registerComponent(ComposableTypes.icon, IconDtoFactory)
            registerComponent(ComposableTypes.iconButton, IconButtonDtoFactory)
            registerComponent(ComposableTypes.lazyColumn, LazyColumnDtoFactory)
            registerComponent(ComposableTypes.lazyRow, LazyRowDtoFactory)
            registerComponent(ComposableTypes.row, RowDtoFactory)
            registerComponent(ComposableTypes.scaffold, ScaffoldDtoFactory)
            registerComponent(ComposableTypes.spacer, SpacerDtoFactory)
            registerComponent(ComposableTypes.text, TextDtoFactory)
            registerComponent(ComposableTypes.topAppBar, TopAppBarDtoFactory)

            // Specific for TopAppBar
            registerComponent(TopAppBarDtoFactory.titleTag, RowDtoFactory)
            registerComponent(TopAppBarDtoFactory.actionTag, IconButtonDtoFactory)
            registerComponent(TopAppBarDtoFactory.navigationIconTag, IconButtonDtoFactory)
        }
    }

    /**
     * Creates a `ComposableTreeNode` object based on the input `Element` object.
     *
     * @param element the `Element` object to create the `ComposableTreeNode` object from
     * @return a `ComposableTreeNode` object based on the input `Element` object
     */
    fun buildComposableTreeNode(
        screenId: String,
        nodeRef: NodeRef,
        element: CoreNodeElement,
    ): ComposableTreeNode {
        return ComposableTreeNode(
            screenId = screenId,
            refId = nodeRef.ref,
            node = element,
            id = "${screenId}_${nodeRef.ref}",
        )
    }

    fun buildComposableView(
        element: CoreNodeElement?,
        pushEvent: PushEvent,
        scope: Any?
    ): ComposableView {
        return if (element != null) {
            val tag = element.tag
            val attrs = element.attributes
            ComposableRegistry.getComponentFactory(tag)?.buildComposableView(
                attrs, pushEvent, scope
            ) ?: run {
                TextDtoFactory.buildComposableView(
                    "$tag not supported yet",
                    attrs,
                    scope,
                    pushEvent,
                )
            }
        } else {
            TextDtoFactory.buildComposableView(
                "Invalid element",
                emptyArray(),
                scope,
                pushEvent
            )
        }
    }
}
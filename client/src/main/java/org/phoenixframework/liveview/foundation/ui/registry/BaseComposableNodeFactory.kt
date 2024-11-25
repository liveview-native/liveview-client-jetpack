package org.phoenixframework.liveview.foundation.ui.registry

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter.Companion.TYPE_LAMBDA_VALUE
import org.phoenixframework.liveviewnative.core.NodeRef

abstract class BaseComposableNodeFactory(
    private val composableRegistry: ComposableRegistry
) {
    fun registerComponent(tag: String, factory: ComposableViewFactory<*>) {
        return composableRegistry.registerComponent(
            tag = tag, factory = factory
        )
    }

    fun unregisterComponent(tag: String) {
        composableRegistry.unregisterComponent(tag)
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
            refId = nodeRef.ref(),
            node = element,
            id = "${screenId}_${nodeRef.ref()}",
        )
    }

    open fun buildComposableView(
        element: CoreNodeElement?,
        parentTag: String?,
        pushEvent: PushEvent,
        scope: Any?,
    ): ComposableView<*> {
        return if (element != null) {
            val tag = element.tag
            val attrs = parseAttributeList(element.attributes)
            composableRegistry.getComponentFactory(tag, parentTag)?.buildComposableView(
                attrs, pushEvent, scope
            ) ?: run {
                throw UnsupportedOperationException("$tag not supported yet")
            }
        } else {
            throw IllegalArgumentException("Invalid element")
        }
    }

    protected fun parseAttributeList(
        attributes: ImmutableList<CoreAttribute>
    ): ImmutableList<CoreAttribute> {
        val lambdaValuePrefix = "{\"${TYPE_LAMBDA_VALUE}\":"
        // Special case to parse the lambda value from parent composable view
        return if (attributes.none { it.value.startsWith(lambdaValuePrefix) })
            attributes
        else
            attributes.map {
                if (it.value.startsWith(lambdaValuePrefix)) {
                    val map = JsonParser.parse<Map<String, Any>>(it.value)
                    CoreAttribute(
                        it.name,
                        it.namespace,
                        ComposableView.getViewValue(map?.get(TYPE_LAMBDA_VALUE).toString())
                            ?.toString() ?: it.value
                    )
                } else it
            }.toImmutableList()
    }
}
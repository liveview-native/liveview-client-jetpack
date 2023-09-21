package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.domain.base.ComposableViewFactory

object ComposableRegistry {
    private val componentsRegistry = mutableMapOf<String, ComposableViewFactory<*, *>>()

    fun registerComponent(
        tag: String, factory: ComposableViewFactory<*, *>, replaceIfExists: Boolean = false
    ) {
        if (!componentsRegistry.containsKey(tag) || replaceIfExists) {
            componentsRegistry[tag] = factory
        }
    }

    fun unregisterComponent(tag: String) {
        componentsRegistry.remove(tag)
    }

    fun getComponentFactory(tag: String): ComposableViewFactory<*, *>? {
        return componentsRegistry[tag]
    }
}
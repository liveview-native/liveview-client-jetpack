package org.phoenixframework.liveview.foundation.ui.registry

import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory

class ComposableRegistry {
    private val componentsRegistry = mutableMapOf<String, ComposableViewFactory<*>>()
    private val subComponentsRegistry =
        mutableMapOf<String, Map<String, ComposableViewFactory<*>>>()

    fun registerComponent(
        tag: String, factory: ComposableViewFactory<*>, replaceIfExists: Boolean = false
    ) {
        if (!componentsRegistry.containsKey(tag) || replaceIfExists) {
            componentsRegistry[tag] = factory
            subComponentsRegistry[tag] = factory.subTags()
        }
    }

    fun unregisterComponent(tag: String) {
        componentsRegistry.remove(tag)
        subComponentsRegistry.remove(tag)
    }

    fun getComponentFactory(tag: String, parentTag: String?): ComposableViewFactory<*>? {
        return componentsRegistry[tag] ?: parentTag?.let { subComponentsRegistry[it] }?.get(tag)
    }
}
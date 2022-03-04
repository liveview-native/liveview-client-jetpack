package org.phoenixframework.liveview

sealed interface Static
data class ComponentReference(val componentID: ComponentID) : Static

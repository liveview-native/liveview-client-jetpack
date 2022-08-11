package org.phoenixframework.liveview.external

sealed interface Static
data class ComponentReference(val componentID: ComponentID) : Static

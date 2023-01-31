package org.phoenixframework.liveview.domain.external

sealed interface Static
data class ComponentReference(val componentID: ComponentID) : Static

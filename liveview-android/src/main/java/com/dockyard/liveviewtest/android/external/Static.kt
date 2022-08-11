package com.dockyard.liveviewtest.android.external

sealed interface Static
data class ComponentReference(val componentID: ComponentID) : Static

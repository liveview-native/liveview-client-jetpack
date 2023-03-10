package org.phoenixframework.liveview.lib

data class NodeRef(val ref: Int) {
    init {
        assert(ref >= 0) { "Node should be a positive integer or 0" }
    }
}

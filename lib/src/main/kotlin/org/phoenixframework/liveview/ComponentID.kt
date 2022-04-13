package org.phoenixframework.liveview

@JvmInline
value class ComponentID(private val id: Int) {
    fun isAdded(): Boolean = id > 0
    fun withoutAge(): ComponentID = if (id > 0) {
        this
    } else {
        ComponentID(-id)
    }

    fun oldComponentID(): ComponentID {
        assert(!isAdded())

        return ComponentID(-id)
    }

    override fun toString(): String = "ComponentID($id)"
    fun toHTML(): String = id.toString()
}

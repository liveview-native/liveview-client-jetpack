package org.phoenixframework.liveview.lib

class Attribute(private var nativeObject: Long) {

    private var _name: String? = null

    /** The name of an attribute */
    val name
        get() = _name ?: get_name(nativeObject).also { _name = it }
    private var _namespace: String? = null

    /** The namespace of an attribute */
    val namespace
        get() = _namespace ?: get_namespace(nativeObject).also { _namespace = it }

    private var _val: String? = null
    val value
        get() = _val ?: get_value(nativeObject).also { _val = it }

    private external fun get_name(pointer: Long): String

    private external fun get_value(pointer: Long): String

    private external fun get_namespace(pointer: Long): String

    override fun toString(): String {
        return "Attribute {\nName: ${name.ifEmpty { "None" }}" +
                "\nNamespace: ${namespace.ifEmpty { "None" }}\nValue: ${value.ifEmpty { "None" }}" +
                "\n}"
    }

    @Synchronized
    private fun delete() {
        if (nativeObject != 0L) {
            drop(nativeObject)
            nativeObject = 0
        }
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        try {
            delete()
        } finally {
            // do nothing
        }
    }

    private external fun drop(pointer: Long)
}
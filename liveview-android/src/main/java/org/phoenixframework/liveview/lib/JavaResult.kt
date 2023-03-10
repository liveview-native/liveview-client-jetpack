package org.phoenixframework.liveview.lib

class JavaResult(private var nativeObject: Long) {

    private external fun get_val(jResultPointer: Long): Long

    private external fun get_error(jResultPointer: Long): String

    /** Returns the `Document` if the parsing was successful.
     * Returns null otherwise.
     **/
    val document = get_val(nativeObject).let { if (it == 0L) null else Document(it) }

    /** Returns the error message returned from failure to parse
     * the `Document`. Returns `null` otherwise.
     * */
    val error = get_error(nativeObject).let { it.ifEmpty { null } }

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
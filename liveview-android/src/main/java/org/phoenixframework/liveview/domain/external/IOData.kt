package org.phoenixframework.liveview.domain.external

sealed class IOData {
    abstract fun reverse(): IOData
    internal abstract fun append(appendable: Appendable)

    override fun toString(): String {
        val appendable = StringBuilder()

        append(appendable)

        return appendable.toString()
    }
}

private object IONil : IOData() {
    override fun reverse(): IOData = this
    override fun append(appendable: Appendable) {}
}

private data class IODatum(val string: String) : IOData() {

    init {
        assert(string.isNotEmpty())
    }

    override fun reverse(): IOData = this
    override fun append(appendable: Appendable) {
        appendable.append(string)
    }

    override fun toString(): String = super.toString()
}


private data class IOCons(val head: IOData, val tail: IOData) : IOData() {
    init {
        assert(head !is IONil)
    }

    override fun reverse(): IOData = ioData(tail.reverse(), head)
    override fun append(appendable: Appendable) {
        head.append(appendable)
        tail.append(appendable)
    }

    override fun toString(): String = super.toString()
}

fun ioData(): IOData = IONil

fun ioData(string: String): IOData =
    if (string.isEmpty()) {
        IONil
    } else {
        IODatum(string)
    }

fun ioData(head: IOData, tail: IOData): IOData =
    when (head) {
        is IOCons, is IODatum -> when (tail) {
            is IOCons, is IODatum -> IOCons(head, tail)
            IONil -> head
        }
        IONil -> tail
    }

fun ioData(string: String, tail: IOData): IOData = ioData(ioData(string), tail)
fun ioData(head: IOData, tail: String): IOData = ioData(head, ioData(tail))
fun ioData(first: IOData, second: String, tail: IOData): IOData = ioData(first, ioData(second, tail))

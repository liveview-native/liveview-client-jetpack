package com.dockyard.liveviewtest.android.external

import org.apache.commons.text.StringEscapeUtils

data class Template(val literals: List<String>) : Static {
    constructor(vararg literals: String) : this(literals.asList())

    override fun toString(): String {
        val hasNewLines = literals.any { it.contains("\n") }

        return if (hasNewLines) {
            literals.joinToString(
                prefix = "Template(\n",
                separator = ",\n",
                postfix = "\n)"
            ) { literal ->
                "\"${StringEscapeUtils.escapeJava(literal)}\"".prependIndent("  ")
            }
        } else {
            literals.joinToString(
                prefix = "Template(",
                separator = ",",
                postfix = ")",
                transform = { literal -> "\"${StringEscapeUtils.escapeJava(literal)}\"" })
        }
    }
}

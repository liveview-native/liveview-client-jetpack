package com.dockyard.liveviewtest.liveview.util

abstract class BaseTest {
    internal fun String.templateToTest() =
        this.trimIndent().trimMargin().trimEnd().replace("\"", "\\\"").lines().joinToString("")

    internal fun String.toJsonForTemplate() =
        this.trimIndent().trim().replace("\n", "")

    internal fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }
}
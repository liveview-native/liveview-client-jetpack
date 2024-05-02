package com.dockyard.liveviewtest.liveview.util

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

abstract class BaseTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    internal fun String.templateToTest() =
        this.trimIndent().trimMargin().trimEnd().replace("\"", "\\\"").lines().joinToString("")

    internal fun String.toJsonForTemplate() =
        this.trimIndent().trim().replace("\n", "")

    internal fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }
}
package org.phoenixframework.liveview.test.base

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.robolectric.annotation.Config

@Config(sdk = [34])
abstract class BaseTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    protected val modifiersParser: BaseModifiersParser = LiveViewJetpack.getModifiersParser()

    internal fun String.templateToTest() =
        this.trimIndent().trimMargin().trimEnd().replace("\"", "\\\"").lines().joinToString("")

    internal fun String.toJsonForTemplate() =
        this.trimIndent().trim().replace("\n", "")

    internal fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }
}
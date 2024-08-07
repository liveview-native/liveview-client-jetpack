package org.phoenixframework.liveview.addons.test.base

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.get
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.robolectric.annotation.Config

@Config(sdk = [34])
abstract class BaseTest : KoinTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    protected val modifiersParser: BaseModifiersParser by lazy {
        get<BaseModifiersParser>()
    }

    protected fun String.templateToTest() =
        this.trimIndent().trimMargin().trimEnd().replace("\"", "\\\"").lines().joinToString("")

    protected fun String.toJsonForTemplate() =
        this.trimIndent().trim().replace("\n", "")

    protected fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }
}
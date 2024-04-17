package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyleName
import org.phoenixframework.liveview.domain.base.PushEvent

@RunWith(AndroidJUnit4::class)
abstract class ModifierBaseTest {

    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    protected fun assertModifierFromStyle(
        styleContentString: String,
        targetModifier: Modifier,
        scope: Any? = null,
        pushEvent: PushEvent? = null
    ) {
        ModifiersParser.fromStyleFile(styleContentString, pushEvent)
        val styleName = getStyleName(styleContentString)
        val result = Modifier.fromStyleName(styleName, scope, pushEvent)
        assertEquals(result, targetModifier)
    }

    fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }

    private fun getStyleName(styleContentString: String): String {
        // Simple substring logic to extract the style name
        val stylePrefix = "%{"
        val styleStart = styleContentString.indexOf(stylePrefix)
        val styleEnd = styleContentString.indexOf("=>")

        return styleContentString.substring(
            startIndex = styleStart + stylePrefix.length,
            endIndex = styleEnd - 1
        ).trim().replace("\"", "").replace("'", "")
    }
}
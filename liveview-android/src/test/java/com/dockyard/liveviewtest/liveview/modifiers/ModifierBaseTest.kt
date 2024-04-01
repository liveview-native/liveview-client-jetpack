package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
abstract class ModifierBaseTest {

    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    protected fun assertModifierFromStyle(style: String, targetModifier: Modifier) {
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(targetModifier)
        assertEquals(result, modifier)
    }

    fun String.trimStyle(): String =
        this.lines().joinToString("") { it.trim().replace("\n", "") }
}
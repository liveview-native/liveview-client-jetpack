package com.dockyard.liveviewtest.liveview.ui.modifiers

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser.fromStyleName

@RunWith(AndroidJUnit4::class)
class OtherTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun menuAnchorTest() {
        val style = """
            %{"menuAnchorTest" => [
                {:menuAnchor, [], []},
            ]}
            """
        ModifiersParser.fromStyleFile(style)
        var result: Modifier? = null
        composeRule.setContent {
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                result = Modifier.then(Modifier.fromStyleName("menuAnchorTest", this))
            }
        }
        assert(result != null)
        assertNotEquals(result, Modifier)
    }
}
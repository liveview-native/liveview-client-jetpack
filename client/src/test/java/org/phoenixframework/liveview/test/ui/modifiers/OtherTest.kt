package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.test.base.ModifierBaseTest

@RunWith(AndroidJUnit4::class)
class OtherTest : ModifierBaseTest() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun menuAnchorTest() {
        val style = """
            %{"menuAnchorTest" => [
                {:menuAnchor, [], []},
            ]}
            """
        modifiersParser.fromStyleFile(style)
        var result: Modifier? = null
        composeRule.setContent {
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                result = modifiersParser.run { Modifier.fromStyleName("menuAnchorTest", this) }
            }
        }
        assert(result != null)
        assertNotEquals(result, Modifier)
    }
}
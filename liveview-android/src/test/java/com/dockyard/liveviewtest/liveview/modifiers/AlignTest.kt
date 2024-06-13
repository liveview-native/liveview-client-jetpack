package com.dockyard.liveviewtest.liveview.modifiers

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser.fromStyleName

@RunWith(AndroidJUnit4::class)
class AlignTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun boxAlignTest() {
        val style = """
            %{"boxAlignTest" => [
                {:align, [], [{:., [], [:Alignment, :BottomEnd]}]},
            ]}
            """
        var result: Modifier? = null
        var modifier: Modifier? = null
        ModifiersParser.fromStyleFile(style, null)
        composeRule.setContent {
            Box {
                result = Modifier.fromStyleName("boxAlignTest", this, null)
                modifier = Modifier.align(Alignment.BottomEnd)
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun boxAlignNamedParamTest() {
        val style = """
            %{"boxAlignNamedParamTest" => [
                {:align, [], [[alignment: {:., [], [:Alignment, :BottomEnd]}]]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Box {
                result = Modifier.fromStyleName("boxAlignNamedParamTest", this, null)
                modifier = Modifier.then(Modifier.align(Alignment.BottomEnd))
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun columnAlignTest() {
        val style = """
            %{"columnAlignTest" => [
                {:align, [], [{:., [], [:Alignment, :End]}]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Column {
                result = Modifier.fromStyleName("columnAlignTest", this, null)
                modifier = Modifier.align(Alignment.End)
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun rowAlignTest() {
        val style = """
            %{"rowAlignTest" => [
                {:align, [], [{:., [], [:Alignment, :Bottom]}]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Row {
                result = Modifier.fromStyleName("rowAlignTest", this, null)
                modifier = Modifier.align(Alignment.Bottom)
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun rowAlignByBaselineTest() {
        val style = """
            %{"rowAlignByBaselineTest" => [
                {:alignByBaseline, [], []},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Row {
                result = Modifier.fromStyleName("rowAlignByBaselineTest", this, null)
                modifier = Modifier.alignByBaseline()
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Test
    fun flowRowAlignByBaselineTest() {
        val style = """
            %{"flowRowAlignByBaselineTest" => [
                {:alignByBaseline, [], []},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            FlowRow {
                result = Modifier.fromStyleName("flowRowAlignByBaselineTest", this, null)
                modifier = Modifier.alignByBaseline()
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }
}
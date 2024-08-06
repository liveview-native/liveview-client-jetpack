package org.phoenixframework.liveview.test.ui.modifiers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.phoenixframework.liveview.test.base.BaseComposableModifierTest
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlignTest : BaseComposableModifierTest() {

    @Test
    fun boxAlignTest() {
        val style = """
            %{"boxAlignTest" => [
                {:align, [], [{:., [], [:Alignment, :BottomEnd]}]},
            ]}
            """
        var result: Modifier? = null
        var modifier: Modifier? = null
        modifiersParser.fromStyleFile(style, null)
        composeRule.setContent {
            Box {
                result =
                    modifiersParser.run { Modifier.fromStyleName("boxAlignTest", this@Box, null) }
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
        modifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Box {
                result = modifiersParser.run {
                    Modifier.fromStyleName(
                        "boxAlignNamedParamTest",
                        this@Box,
                        null
                    )
                }
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
        modifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Column {
                result = modifiersParser.run {
                    Modifier.fromStyleName(
                        "columnAlignTest",
                        this@Column,
                        null
                    )
                }
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
        modifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Row {
                result =
                    modifiersParser.run { Modifier.fromStyleName("rowAlignTest", this@Row, null) }
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
        modifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Row {
                result = modifiersParser.run {
                    Modifier.fromStyleName(
                        "rowAlignByBaselineTest",
                        this@Row,
                        null
                    )
                }
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
        modifiersParser.fromStyleFile(style, null)
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            FlowRow {
                result = modifiersParser.run {
                    Modifier.fromStyleName(
                        "flowRowAlignByBaselineTest",
                        this@FlowRow,
                        null
                    )
                }
                modifier = Modifier.alignByBaseline()
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }
}
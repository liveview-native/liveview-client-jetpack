package com.dockyard.liveviewtest.liveview.modifiers

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dockyard.liveviewtest.liveview.util.ModifierBaseTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyleName

@RunWith(AndroidJUnit4::class)
class SizeTest : ModifierBaseTest() {

    @Test
    fun aspectRatioTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioTest" => [
                {:aspectRatio, [], [1.33]},
            ]}
            """,
            Modifier.aspectRatio(1.33f)
        )
    }

    @Test
    fun aspectRatioWithMatchConstraintsTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioWithMatchConstraintsTest" => [
                {:aspectRatio, [], [1.33, true]},
            ]}
            """,
            Modifier.aspectRatio(1.33f, true)
        )
    }

    @Test
    fun aspectRatioWithNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"aspectRatioWithNamedParamsTest" => [
                {:aspectRatio, [], [[matchHeightConstraintsFirst: true, ratio: 1.33]]},
            ]}
            """,
            Modifier.aspectRatio(1.33f, true)
        )
    }

    @Test
    fun defaultMinSizeTest() {
        assertModifierFromStyle(
            """
            %{"defaultMinSizeTest" => [
              {:defaultMinSize, [], [
                {:., [], [50,:dp]}, 
                {:., [], [100,:dp]},
              ]},
            ]}
            """.trimStyle(),
            Modifier.defaultMinSize(50.dp, 100.dp)
        )
    }

    @Test
    fun defaultMinSizeNamedTest() {
        assertModifierFromStyle(
            """
            %{"defaultMinSizeNamedTest" => [
              {:defaultMinSize, [], [[
                minHeight: {:., [], [50,:dp]}, 
                minWidth: {:., [], [150,:dp]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.defaultMinSize(
                minHeight = 50.dp,
                minWidth = 150.dp,
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun exposedDropdownSizeTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"exposedDropdownSizeTest" => [
                {:exposedDropdownSize, [], [false]},
            ]}
            """
        )
        var result: Modifier? = null
        composeRule.setContent {
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                result = Modifier.then(Modifier.fromStyleName("exposedDropdownSizeTest", this))
            }
        }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assert(result != null)
        assertNotEquals(result, Modifier)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun exposedDropdownSizeNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"exposedDropdownSizeNamedTest" => [
                {:exposedDropdownSize, [], [[matchTextFieldWidth: false]]},
            ]}
            """
        )
        var result: Modifier? = null
        composeRule.setContent {
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                result = Modifier.then(Modifier.fromStyleName("exposedDropdownSizeNamedTest", this))
            }
        }
        // This Modifier always creates a different instance, so it cannot be compared.
        // So we're just checking whether it is being processed (being not an empty modifier).
        assert(result != null)
        assertNotEquals(result, Modifier)
    }

    @Test
    fun fillMaxHeightTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightTest" => [
                {:fillMaxHeight, [], []},
            ]}
            """,
            Modifier.fillMaxHeight()
        )
    }

    @Test
    fun fillMaxHeightWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightWithFractionTest" => [
                {:fillMaxHeight, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxHeight(0.5f)
        )
    }

    @Test
    fun fillMaxHeightWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxHeightWithFractionNamedTest" => [
                {:fillMaxHeight, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxHeight(0.5f)
        )
    }

    @Test
    fun fillMaxSizeTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeTest" => [
                {:fillMaxSize, [], []},
            ]}
            """,
            Modifier.fillMaxSize()
        )
    }

    @Test
    fun fillMaxSizeWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeWithFractionTest" => [
                {:fillMaxSize, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxSize(0.5f)
        )
    }

    @Test
    fun fillMaxSizeWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxSizeWithFractionNamedTest" => [
                {:fillMaxSize, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxSize(0.5f)
        )
    }

    @Test
    fun fillMaxWidthTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthTest" => [
                {:fillMaxWidth, [], []},
            ]}
            """,
            Modifier.fillMaxWidth()
        )
    }

    @Test
    fun fillMaxWidthWithFractionTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthWithFractionTest" => [
                {:fillMaxWidth, [], [0.5]},
            ]}
            """,
            Modifier.fillMaxWidth(0.5f)
        )
    }

    @Test
    fun fillMaxWidthWithFractionNamedTest() {
        assertModifierFromStyle(
            """
            %{"fillMaxWidthWithFractionNamedTest" => [
                {:fillMaxWidth, [], [[fraction: 0.5]]},
            ]}
            """,
            Modifier.fillMaxWidth(0.5f)
        )
    }

    @Test
    fun fillParentMaxHeightTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxHeightTest" => [
                {:fillParentMaxHeight, [], [0.5]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result = Modifier.then(Modifier.fromStyleName("fillParentMaxHeightTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxHeight(0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun fillParentMaxHeightNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxHeightNamedTest" => [
                {:fillParentMaxHeight, [], [[faction: 0.5]]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result =
                        Modifier.then(Modifier.fromStyleName("fillParentMaxHeightNamedTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxHeight(fraction = 0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun fillParentMaxSizeTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxSizeTest" => [
                {:fillParentMaxSize, [], [0.5]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result = Modifier.then(Modifier.fromStyleName("fillParentMaxSizeTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxSize(0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun fillParentMaxSizeNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxSizeNamedTest" => [
                {:fillParentMaxSize, [], [[faction: 0.5]]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result =
                        Modifier.then(Modifier.fromStyleName("fillParentMaxSizeNamedTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxSize(fraction = 0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun fillParentMaxWidthTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxWidthTest" => [
                {:fillParentMaxWidth, [], [0.5]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result = Modifier.then(Modifier.fromStyleName("fillParentMaxWidthTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxWidth(0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun fillParentMaxWidthNamedTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"fillParentMaxWidthNamedTest" => [
                {:fillParentMaxWidth, [], [[faction: 0.5]]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            LazyColumn {
                item {
                    result =
                        Modifier.then(Modifier.fromStyleName("fillParentMaxWidthNamedTest", this))
                    modifier = Modifier.then(Modifier.fillParentMaxWidth(fraction = 0.5f))
                }
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun heightDpTest() {
        assertModifierFromStyle(
            """
            %{"heightDpTest" => [
                {:height, [], [{:., [], [100, :dp]}]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightDpWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightDpWithInvalidValueMustFail" => [
                {:height, [], ["100"]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun heightDpNamed() {
        assertModifierFromStyle(
            """
            %{"heightDpNamed" => [
                {:height, [], [[height: {:., [], [100, :dp]}]]},
            ]}
            """,
            Modifier.height(100.dp)
        )
    }

    @Test
    fun heightDpNamedWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"heightDpNamedWithInvalidValueMustFail" => [
                {:height, [], [[height: "100"]]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun heightInEmptyTest() {
        assertModifierFromStyle(
            """
            %{"heightInEmptyTest" => [
                {:heightIn, [], []},
            ]}
            """,
            Modifier.heightIn()
        )
    }

    @Test
    fun heightInTest() {
        assertModifierFromStyle(
            """
            %{"heightInTest" => [
                {:heightIn, [], [{:., [], [100, :dp]}, {:., [], [200, :dp]}]},
            ]}
            """,
            Modifier.heightIn(100.dp, 200.dp)
        )
    }

    @Test
    fun heightInNamedTest() {
        assertModifierFromStyle(
            """
            %{"heightInNamedTest" => [
              {:heightIn, [], [[
                min: {:., [], [100, :dp]}, 
                max: {:., [], [200, :dp]}
              ]]},
            ]}
            """,
            Modifier.heightIn(min = 100.dp, max = 200.dp)
        )
    }

    @Test
    fun heightIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"heightIntrinsicTest" => [
                {:height, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.height(IntrinsicSize.Min)
        )
    }

    @Test
    fun heightIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"heightIntrinsicNamedTest" => [
                {:height, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Min]}]]},
            ]}
            """,
            Modifier.height(IntrinsicSize.Min)
        )
    }

    @Test
    fun matchParentSizeTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"matchParentSizeTest" => [
                {:matchParentSize, [], []},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Box {
                result = Modifier.fromStyleName("matchParentSizeTest", this)
                modifier = Modifier.matchParentSize()
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun requiredHeightInDpTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightInDpTest" => [
                {:requiredHeight, [], [{:., [], [50,:dp]}]},
            ]}
            """,
            Modifier.requiredHeight(50.dp)
        )
    }

    @Test
    fun requiredHeightInDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightInDpNamedTest" => [
                {:requiredHeight, [], [[height: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.requiredHeight(height = 50.dp)
        )
    }

    @Test
    fun requiredHeightIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightIntrinsicTest" => [
                {:requiredHeight, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.requiredHeight(IntrinsicSize.Min)
        )
    }

    @Test
    fun requiredHeightIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightIntrinsicNamedTest" => [
                {:requiredHeight, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Max]}]]},
            ]}
            """,
            Modifier.requiredHeight(intrinsicSize = IntrinsicSize.Max)
        )
    }

    @Test
    fun requiredHeightInTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightInTest" => [
                {:requiredHeightIn, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.requiredHeightIn(50.dp, 100.dp)
        )
    }

    @Test
    fun requiredHeightInNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredHeightInNamedTest" => [
                {:requiredHeightIn, [], [[min: {:., [], [50,:dp]}, max: {:., [], [100,:dp]}]]},
            ]}
            """,
            Modifier.requiredHeightIn(min = 50.dp, max = 100.dp)
        )
    }

    @Test
    fun requiredSizeInDpTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInDpTest" => [
                {:requiredSize, [], [{:., [], [50,:dp]}]},
            ]}
            """,
            Modifier.requiredSize(50.dp)
        )
    }

    @Test
    fun requiredSizeInDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInDpNamedTest" => [
                {:requiredSize, [], [[height: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.requiredSize(size = 50.dp)
        )
    }

    @Test
    fun requiredSizeInWHDpTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInWHDpTest" => [
                {:requiredSize, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.requiredSize(50.dp, 100.dp)
        )
    }

    @Test
    fun requiredSizeInWHDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInWHDpNamedTest" => [
                {:requiredSize, [], [[width: {:., [], [50,:dp]}, height: {:., [], [100,:dp]}]]},
            ]}
            """,
            Modifier.requiredSize(width = 50.dp, height = 100.dp)
        )
    }

    @Test
    fun requiredSizeInDpSizeTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInDpSizeTest" => [
                {:requiredSize, [], [{:DpSize, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]}]},
            ]}
            """,
            Modifier.requiredSize(DpSize(50.dp, 100.dp))
        )
    }

    @Test
    fun requiredSizeInDpSizeNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInDpSizeNamedTest" => [
                {:requiredSize, [], [[
                  size: {:DpSize, [], [[
                    width: {:., [], [50,:dp]}, 
                    height: {:., [], [100,:dp]}
                  ]]}
                ]]},
            ]}
            """.trimStyle(),
            Modifier.requiredSize(size = DpSize(width = 50.dp, height = 100.dp))
        )
    }

    @Test
    fun requiredSizeInTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInTest" => [
              {:requiredSizeIn, [], [
                {:., [], [50,:dp]}, 
                {:., [], [100,:dp]},
                {:., [], [150,:dp]},
                {:., [], [200,:dp]},
              ]},
            ]}
            """.trimStyle(),
            Modifier.requiredSizeIn(50.dp, 100.dp, 150.dp, 200.dp)
        )
    }

    @Test
    fun requiredSizeInNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredSizeInNamedTest" => [
              {:requiredSizeIn, [], [[
                minHeight: {:., [], [50,:dp]}, 
                maxHeight: {:., [], [100,:dp]},
                minWidth: {:., [], [150,:dp]},
                maxWidth: {:., [], [200,:dp]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.requiredSizeIn(
                minHeight = 50.dp,
                maxHeight = 100.dp,
                minWidth = 150.dp,
                maxWidth = 200.dp
            )
        )
    }

    @Test
    fun requiredWidthInDpTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthInDpTest" => [
                {:requiredWidth, [], [{:., [], [50,:dp]}]},
            ]}
            """,
            Modifier.requiredWidth(50.dp)
        )
    }

    @Test
    fun requiredWidthInDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthInDpNamedTest" => [
                {:requiredWidth, [], [[width: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.requiredWidth(width = 50.dp)
        )
    }

    @Test
    fun requiredWidthIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthIntrinsicTest" => [
                {:requiredWidth, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.requiredWidth(IntrinsicSize.Min)
        )
    }

    @Test
    fun requiredWidthIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthIntrinsicNamedTest" => [
                {:requiredWidth, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Max]}]]},
            ]}
            """,
            Modifier.requiredWidth(intrinsicSize = IntrinsicSize.Max)
        )
    }

    @Test
    fun requiredWidthInTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthInTest" => [
                {:requiredWidthIn, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.requiredWidthIn(50.dp, 100.dp)
        )
    }

    @Test
    fun requiredWidthInNamedTest() {
        assertModifierFromStyle(
            """
            %{"requiredWidthInNamedTest" => [
                {:requiredWidthIn, [], [[min: {:., [], [50,:dp]}, max: {:., [], [100,:dp]}]]},
            ]}
            """,
            Modifier.requiredWidthIn(min = 50.dp, max = 100.dp)
        )
    }

    @Test
    fun sizeInDpTest() {
        assertModifierFromStyle(
            """
            %{"sizeInDpTest" => [
                {:size, [], [{:., [], [50,:dp]}]},
            ]}
            """,
            Modifier.size(50.dp)
        )
    }

    @Test
    fun sizeInDpNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeInDpNamedTest" => [
                {:size, [], [[size: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.size(50.dp)
        )
    }

    @Test
    fun sizeInDpSizeTest() {
        assertModifierFromStyle(
            """
            %{"sizeInDpSizeTest" => [
              {:size, [], [{
                :DpSize, [], [
                  {:., [], [50,:dp]}, 
                  {:., [], [100,:dp]}
                ]
              }]}
            ]}
            """.trimStyle(),
            Modifier.size(50.dp, 100.dp)
        )
    }

    @Test
    fun sizeInDpSizeNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeInDpSizeNamedTest" => [
              {:size, [], [[
                size: {:DpSize, [], [[
                  height: {:., [], [100,:dp]}, 
                  width: {:., [], [50,:dp]}
                ]]}
              ]]},
            ]}
            """.trimStyle(),
            Modifier.size(DpSize(height = 100.dp, width = 50.dp))
        )
    }

    @Test
    fun sizeInTest() {
        assertModifierFromStyle(
            """
            %{"sizeInTest" => [
              {:sizeIn, [], [
                {:., [], [50,:dp]}, 
                {:., [], [100,:dp]},
                {:., [], [150,:dp]},
                {:., [], [200,:dp]}
              ]}
            ]}
            """.trimStyle(),
            Modifier.sizeIn(50.dp, 100.dp, 150.dp, 200.dp)
        )
    }

    @Test
    fun sizeInNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeInNamedTest" => [
              {:sizeIn, [], [[
                minHeight: {:., [], [50,:dp]}, 
                minWidth: {:., [], [100,:dp]},
                maxHeight: {:., [], [150,:dp]},
                maxWidth: {:., [], [200,:dp]}
              ]]}
            ]}
            """.trimStyle(),
            Modifier.sizeIn(
                minHeight = 50.dp,
                minWidth = 100.dp,
                maxHeight = 150.dp,
                maxWidth = 200.dp
            )
        )
    }

    @Test
    fun sizeWHTest() {
        assertModifierFromStyle(
            """
            %{"sizeWHTest" => [
                {:size, [], [{:., [], [50,:dp]}, {:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.size(50.dp, 100.dp)
        )
    }

    @Test
    fun sizeWHNamedTest() {
        assertModifierFromStyle(
            """
            %{"sizeWHNamedTest" => [
                {:size, [], [[height: {:., [], [25,:dp]}, width: {:., [], [50,:dp]}]]},
            ]}
            """,
            Modifier.size(height = 25.dp, width = 50.dp)
        )
    }

    @Test
    fun weightRowTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"weightRowTest" => [
                {:weight, [], [0.5, false]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Row {
                result = Modifier.then(Modifier.fromStyleName("weightRowTest", this))
                modifier = Modifier.then(Modifier.weight(0.5f, false))
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun weightColumnTest() {
        ModifiersParser.fromStyleFile(
            """
            %{"weightColumnTest" => [
                {:weight, [], [[weight: 0.5, fill: false]]},
            ]}
            """
        )
        var result: Modifier? = null
        var modifier: Modifier? = null
        composeRule.setContent {
            Column {
                result = Modifier.then(Modifier.fromStyleName("weightColumnTest", this))
                modifier = Modifier.then(Modifier.weight(0.5f, false))
            }
        }
        assert(result != null)
        assert(modifier != null)
        assertEquals(result, modifier)
    }

    @Test
    fun windowInsetsBottomHeightTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsBottomHeightTest" => [
                {:windowInsetsBottomHeight, [], [{:WindowInsets, [], [8, 16, 24, 32]}]},
            ]}
            """,
            Modifier.windowInsetsBottomHeight(WindowInsets(8, 16, 24, 32))
        )
    }

    @Test
    fun windowInsetsBottomHeightNamedTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsBottomHeightNamedTest" => [
              {:windowInsetsBottomHeight, [], [[
                insets: {:WindowInsets, [], [
                  {:., [], [8,:dp]}, 
                  {:., [], [16,:dp]},
                  {:., [], [24,:dp]},
                  {:., [], [32,:dp]}
                ]}
              ]]},
            ]}
            """,
            Modifier.windowInsetsBottomHeight(WindowInsets(8.dp, 16.dp, 24.dp, 32.dp))
        )
    }

    @Test
    fun windowInsetsEndWidthTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsEndWidthTest" => [
              {:windowInsetsEndWidth, [], [
                {:WindowInsets, [], [
                  {:., [], [8,:dp]}, 
                  {:., [], [16,:dp]},
                  {:., [], [24,:dp]},
                  {:., [], [32,:dp]}
                ]}
              ]},
            ]}
            """,
            Modifier.windowInsetsEndWidth(WindowInsets(8.dp, 16.dp, 24.dp, 32.dp))
        )
    }

    @Test
    fun windowInsetsEndWidthNamedTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsEndWidthNamedTest" => [
              {:windowInsetsEndWidth, [], [[
                insets: {:WindowInsets, [], [8, 16, 24, 32]}
              ]]},
            ]}
            """,
            Modifier.windowInsetsEndWidth(WindowInsets(8, 16, 24, 32))
        )
    }

    @Test
    fun windowInsetsStartWidthTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsStartWidthTest" => [
              {:windowInsetsStartWidth, [], [
                {:WindowInsets, [], [[
                  left: {:., [], [8,:dp]}, 
                  top: {:., [], [16,:dp]},
                  right: {:., [], [24,:dp]},
                  bottom: {:., [], [32,:dp]}
                ]]}
              ]},
            ]}
            """,
            Modifier.windowInsetsStartWidth(
                WindowInsets(
                    left = 8.dp,
                    top = 16.dp,
                    right = 24.dp,
                    bottom = 32.dp
                )
            )
        )
    }

    @Test
    fun windowInsetsStartWidthNamedTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsStartWidthNamedTest" => [
              {:windowInsetsStartWidth, [], [[
                insets: {:WindowInsets, [], [[
                  left: 8, top: 16, right: 24, bottom: 32
                ]]}
              ]]},
            ]}
            """.trimStyle(),
            Modifier.windowInsetsStartWidth(WindowInsets(8, 16, 24, 32))
        )
    }

    @Test
    fun windowInsetsTopHeightTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsTopHeightTest" => [
              {:windowInsetsTopHeight, [], [
                {:WindowInsets, [], [[
                  left: {:., [], [8,:dp]}, 
                  top: {:., [], [16,:dp]},
                  right: {:., [], [24,:dp]},
                  bottom: {:., [], [32,:dp]}
                ]]}
              ]},
            ]}
            """,
            Modifier.windowInsetsTopHeight(
                WindowInsets(
                    left = 8.dp,
                    top = 16.dp,
                    right = 24.dp,
                    bottom = 32.dp
                )
            )
        )
    }

    @Test
    fun windowInsetsTopHeightNamedTest() {
        assertModifierFromStyle(
            """
            %{"windowInsetsTopHeightNamedTest" => [
              {:windowInsetsTopHeight, [], [[
                insets: {:WindowInsets, [], [[
                  left: 8, top: 16, right: 24, bottom: 32
                ]]}
              ]]},
            ]}
            """.trimStyle(),
            Modifier.windowInsetsTopHeight(WindowInsets(8, 16, 24, 32))
        )
    }

    @Test
    fun widthInDp() {
        assertModifierFromStyle(
            """
            %{"widthInDp" => [
                {:width, [], [{:., [], [100,:dp]}]},
            ]}
            """,
            Modifier.width(100.dp)
        )
    }

    @Test
    fun widthInDpWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"widthInDpWithInvalidValueMustFail" => [
                {:width, [], ["100"]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun widthInDpNamed() {
        assertModifierFromStyle(
            """
            %{"widthInDpNamed" => [
                {:width, [], [[width: {:., [], [100,:dp]}]]},
            ]}
            """,
            Modifier.width(100.dp)
        )
    }

    @Test
    fun widthInDpNamedWithInvalidValueMustFail() {
        assertModifierFromStyle(
            """
            %{"widthInDpNamedWithInvalidValueMustFail" => [
                {:width, [], [[width: "100"]]},
            ]}
            """,
            Modifier
        )
    }

    @Test
    fun widthIntrinsicTest() {
        assertModifierFromStyle(
            """
            %{"widthIntrinsicTest" => [
                {:width, [], [{:., [], [:IntrinsicSize, :Min]}]},
            ]}
            """,
            Modifier.width(IntrinsicSize.Min)
        )
    }

    @Test
    fun widthIntrinsicNamedTest() {
        assertModifierFromStyle(
            """
            %{"widthIntrinsicNamedTest" => [
                {:width, [], [[intrinsicSize: {:., [], [:IntrinsicSize, :Min]}]]},
            ]}
            """,
            Modifier.width(IntrinsicSize.Min)
        )
    }

    @Test
    fun wrapContentHeightTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentHeightTest" => [
                {:wrapContentHeight, [], []},
            ]}
            """,
            Modifier.wrapContentHeight()
        )
    }

    @Test
    fun wrapContentHeightWithParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentHeightWithParamsTest" => [
                {:wrapContentHeight, [], [{:., [], [:Alignment, :Top]}, false]},
            ]}
            """,
            Modifier.wrapContentHeight(Alignment.Top, false)
        )
    }

    @Test
    fun wrapContentHeightWithNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentHeightWithNamedParamsTest" => [
              {:wrapContentHeight, [], [[
                align: {:., [], [:Alignment, :Top]}, 
                unbounded: false
              ]]},
            ]}
            """,
            Modifier.wrapContentHeight(align = Alignment.Top, unbounded = false)
        )
    }

    @Test
    fun wrapContentSizeTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentSizeTest" => [
                {:wrapContentSize, [], []},
            ]}
            """,
            Modifier.wrapContentSize()
        )
    }

    @Test
    fun wrapContentSizeWithParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentSizeWithParamsTest" => [
                {:wrapContentSize, [], [{:., [], [:Alignment, :BottomEnd]}, false]},
            ]}
            """,
            Modifier.wrapContentSize(Alignment.BottomEnd, false)
        )
    }

    @Test
    fun wrapContentSizeWithNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentSizeWithNamedParamsTest" => [
              {:wrapContentSize, [], [[
                align: {:., [], [:Alignment, :BottomEnd]}, 
                unbounded: false
              ]]},
            ]}
            """,
            Modifier.wrapContentSize(align = Alignment.BottomEnd, unbounded = false)
        )
    }

    @Test
    fun wrapContentWidthTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentWidthTest" => [
                {:wrapContentWidth, [], []},
            ]}
            """,
            Modifier.wrapContentWidth()
        )
    }

    @Test
    fun wrapContentWidthWithParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentWidthWithParamsTest" => [
                {:wrapContentWidth, [], [{:., [], [:Alignment, :End]}, false]},
            ]}
            """,
            Modifier.wrapContentWidth(Alignment.End, false)
        )
    }

    @Test
    fun wrapContentWidthWithNamedParamsTest() {
        assertModifierFromStyle(
            """
            %{"wrapContentWidthWithNamedParamsTest" => [
              {:wrapContentWidth, [], [[
                align: {:., [], [:Alignment, :End]}, 
                unbounded: false
              ]]},
            ]}
            """,
            Modifier.wrapContentWidth(align = Alignment.End, unbounded = false)
        )
    }
}
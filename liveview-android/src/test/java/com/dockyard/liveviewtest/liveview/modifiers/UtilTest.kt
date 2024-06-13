package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.KeyframesWithSplineSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.keyframesWithSpline
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dockyard.liveviewtest.liveview.util.ModifierBaseTest
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.ui.modifiers.ModifierDataAdapter
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.modifiers.alignmentFromArgument
import org.phoenixframework.liveview.ui.modifiers.borderStrokeFromArgument
import org.phoenixframework.liveview.ui.modifiers.brushFromArgument
import org.phoenixframework.liveview.ui.modifiers.colorFromArgument
import org.phoenixframework.liveview.ui.modifiers.dpFromArgument
import org.phoenixframework.liveview.ui.modifiers.dpSizeFromArgument
import org.phoenixframework.liveview.ui.modifiers.enterTransitionFromArgument
import org.phoenixframework.liveview.ui.modifiers.exitTransitionFromArgument
import org.phoenixframework.liveview.ui.modifiers.finiteAnimationSpecFromArg
import org.phoenixframework.liveview.ui.modifiers.floatRangeFromArgument
import org.phoenixframework.liveview.ui.modifiers.horizontalAlignmentFromArgument
import org.phoenixframework.liveview.ui.modifiers.horizontalAlignmentLineFromArgument
import org.phoenixframework.liveview.ui.modifiers.intOffsetFromArgument
import org.phoenixframework.liveview.ui.modifiers.intRangeFromArgument
import org.phoenixframework.liveview.ui.modifiers.intSizeFromArgument
import org.phoenixframework.liveview.ui.modifiers.intrinsicSizeFromArgument
import org.phoenixframework.liveview.ui.modifiers.offsetFromArgument
import org.phoenixframework.liveview.ui.modifiers.repeatModeFromArgument
import org.phoenixframework.liveview.ui.modifiers.roleFromArgument
import org.phoenixframework.liveview.ui.modifiers.shapeFromArgument
import org.phoenixframework.liveview.ui.modifiers.startOffsetFromArgument
import org.phoenixframework.liveview.ui.modifiers.textUnitFromArgument
import org.phoenixframework.liveview.ui.modifiers.toggleableStateFromArgument
import org.phoenixframework.liveview.ui.modifiers.transformOriginFromArgument
import org.phoenixframework.liveview.ui.modifiers.verticalAlignmentFromArgument
import org.phoenixframework.liveview.ui.modifiers.windowInsetsFromArgument
import org.phoenixframework.liveview.stylesheet.ElixirParser.TupleExprContext

@RunWith(AndroidJUnit4::class)
class UtilTest : ModifierBaseTest() {

    @Test
    fun alignmentTest() {
        val aligns = listOf(
            Alignment.TopStart,
            Alignment.TopCenter,
            Alignment.TopEnd,
            Alignment.CenterStart,
            Alignment.Center,
            Alignment.CenterEnd,
            Alignment.BottomStart,
            Alignment.BottomCenter,
            Alignment.BottomEnd
        )
        val alignsText = listOf(
            AlignmentValues.topStart,
            AlignmentValues.topCenter,
            AlignmentValues.topEnd,
            AlignmentValues.centerStart,
            AlignmentValues.center,
            AlignmentValues.centerEnd,
            AlignmentValues.bottomStart,
            AlignmentValues.bottomCenter,
            AlignmentValues.bottomEnd
        )
        aligns.forEachIndexed { index, alignment ->
            val expr = ModifiersParser.parseElixirContent(
                """
                {:., [], [{:., [], [:Alignment, :${alignsText[index]}]}]}
                """.trimStyle()
            )

            val alignmentFromStyle =
                alignmentFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
            assertEquals(alignment, alignmentFromStyle)
        }
    }

    @Test
    fun borderStrokeTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [{:BorderStroke, [], [{:Dp, [], [2]}, {:., [], [:Color, :Red]}]}]}
            """.trimStyle()
        )

        val borderFromStyle =
            borderStrokeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val border = BorderStroke(2.dp, Color.Red)
        assertEquals(border, borderFromStyle)
    }

    @Test
    fun borderStrokeNamedArgsTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [{:BorderStroke, [], [[width: {:Dp, [], [2]}, color: {:., [], [:Color, :Red]}]]}]}
            """.trimStyle()
        )

        val borderFromStyle =
            borderStrokeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val border = BorderStroke(2.dp, Color.Red)
        assertEquals(border, borderFromStyle)
    }

    @Test
    fun brushHorizontalGradientTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:horizontalGradient, [], [
                        [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        0.0,
                        500.0,
                        {:., [], [:TileMode, :Clamp]}
                    ]}
                ]},
            ]}
            """.trimStyle()
        )

        val borderFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())

        val brush = Brush.horizontalGradient(
            colors = listOf(Color.Red, Color.Blue, Color.Green),
            startX = 0f,
            endX = 500f,
            tileMode = TileMode.Clamp,
        )
        assertEquals(brush, borderFromStyle)
    }

    @Test
    fun brushHorizontalGradientNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:horizontalGradient, [], [[
                        tileMode: {:., [], [:TileMode, :Clamp]},
                        colors: [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        startX: 0.0,
                        endX: 500.0
                    ]]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.horizontalGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                startX = 0f,
                endX = 500f,
                tileMode = TileMode.Clamp,
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushVerticalGradientTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:verticalGradient, [], [
                        [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        0.0,
                        500.0,
                        {:., [], [:TileMode, :Decal]}
                    ]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.verticalGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                startY = 0f,
                endY = 500f,
                tileMode = TileMode.Decal,
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushVerticalGradientNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:verticalGradient, [], [[
                        startY: 0.0,
                        endY: 500.0,
                        tileMode: {:., [], [:TileMode, :Decal]},
                        colors: [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ]
                    ]]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.verticalGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                startY = 0f,
                endY = 500f,
                tileMode = TileMode.Decal,
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushLinearGradientTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:linearGradient, [], [
                        [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        {:Offset, [], [1, 2]},
                        {:Offset, [], [3, 4]},
                        {:., [], [:TileMode, :Mirror]}
                    ]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.linearGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                tileMode = TileMode.Mirror,
                start = Offset(1f, 2f),
                end = Offset(3f, 4f),
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushLinearGradientNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:linearGradient, [], [[
                        start: {:Offset, [], [1, 2]},
                        end: {:Offset, [], [3, 4]},
                        colors: [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        tileMode: {:., [], [:TileMode, :Mirror]}
                    ]]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.linearGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                tileMode = TileMode.Mirror,
                start = Offset(1f, 2f),
                end = Offset(3f, 4f),
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushRadialGradientTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:radialGradient, [], [
                        [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        {:Offset, [], [10, 20]},
                        50.0,
                        {:., [], [:TileMode, :Repeated]}
                    ]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.radialGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                center = Offset(10f, 20f),
                radius = 50f,
                tileMode = TileMode.Repeated,
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushRadialGradientNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:radialGradient, [], [[
                        colors: [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        center: {:Offset, [], [10, 20]},
                        radius: 50.0,
                        tileMode: {:., [], [:TileMode, :Repeated]}
                    ]]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.radialGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                center = Offset(10f, 20f),
                radius = 50f,
                tileMode = TileMode.Repeated,
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushSweepGradientTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:sweepGradient, [], [
                        [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        {:Offset, [], [10, 20]}
                    ]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.sweepGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                center = Offset(10f, 20f),
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun brushSweepGradientNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [
                    :Brush,
                    {:sweepGradient, [], [[
                        colors: [
                            {:., [], [:Color, :Red]},
                            {:., [], [:Color, :Blue]},
                            {:., [], [:Color, :Green]}
                        ],
                        center: {:Offset, [], [10, 20]}
                    ]]}
                ]},
            ]}
            """.trimStyle()
        )
        val brushFromStyle =
            brushFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val brush =
            Brush.sweepGradient(
                colors = listOf(Color.Red, Color.Blue, Color.Green),
                center = Offset(10f, 20f),
            )
        assertEquals(brush, brushFromStyle)
    }

    @Test
    fun colorSystemTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:Color, :Red]},
            ]}
            """.trimStyle()
        )
        val colorFromStyle =
            colorFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val color = Color.Red
        assertEquals(color, colorFromStyle)
    }

    @Test
    fun colorRgbaTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Color, [], [10, 20, 30, 40]},
            ]}
            """.trimStyle()
        )
        val colorFromStyle =
            colorFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val color = Color(10, 20, 30, 40)
        assertEquals(color, colorFromStyle)
    }

    @Test
    fun colorRgbaNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Color, [], [[alpha: 10, blue: 20, green: 30, red: 40]]},
            ]}
            """.trimStyle()
        )
        val colorFromStyle =
            colorFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val color = Color(alpha = 10, blue = 20, green = 30, red = 40)
        assertEquals(color, colorFromStyle)
    }

    @Test
    fun colorIntTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Color, [], [3454955]},
            ]}
            """.trimStyle()
        )
        val colorFromStyle =
            colorFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val color = Color(3454955)
        assertEquals(color, colorFromStyle)
    }

    @Test
    fun dpTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Dp, [], [12]},
            ]}
            """.trimStyle()
        )
        val dpFromStyle =
            dpFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val dp = Dp(12f)
        assertEquals(dp, dpFromStyle)
    }

    @Test
    fun dpFloatTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Dp, [], [12.0]},
            ]}
            """.trimStyle()
        )
        val dpFromStyle =
            dpFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val dp = Dp(12f)
        assertEquals(dp, dpFromStyle)
    }

    @Test
    fun dpSizeTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:DpSize, [], [{:Dp, [], [12]}, {:Dp, [], [24]}]},
            ]}
            """.trimStyle()
        )
        val dpSizeFromStyle =
            dpSizeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val dpSize = DpSize(12.dp, 24.dp)
        assertEquals(dpSize, dpSizeFromStyle)
    }

    @Test
    fun dpSizeNamedTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:DpSize, [], [[width: {:Dp, [], [12]}, height: {:Dp, [], [24]}]]},
            ]}
            """.trimStyle()
        )
        val dpSizeFromStyle =
            dpSizeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val dpSize = DpSize(12.dp, 24.dp)
        assertEquals(dpSize, dpSizeFromStyle)
    }

    @Test
    fun enterTransitionList() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeIn, [], []},
                {:expandIn, [], []}
            ]}
            """.trimStyle()
        )
        val transitionFromStyle = enterTransitionFromArgument(
            *(ModifierDataAdapter(expr as TupleExprContext).arguments.toTypedArray())
        )
        val transition = fadeIn() + expandIn()
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionExpandHorizontallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandHorizontally, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = expandHorizontally()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandHorizontally, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandHorizontally(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandHorizontally, [], [[
                    expandFrom: {:., [], [:Alignment, :CenterHorizontally]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandHorizontally(expandFrom = Alignment.CenterHorizontally)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandHorizontally, [], [[
                    expandFrom: {:., [], [:Alignment, :CenterHorizontally]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandHorizontally(expandFrom = Alignment.CenterHorizontally, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionExpandInTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandIn, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = expandIn()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandIn, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandIn(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandIn, [], [[
                    expandFrom: {:., [], [:Alignment, :Center]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandIn(expandFrom = Alignment.Center)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandIn, [], [[
                    expandFrom: {:., [], [:Alignment, :BottomEnd]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandIn(expandFrom = Alignment.BottomEnd, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionExpandVerticallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandVertically, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = expandVertically()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandVertically, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandVertically(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandVertically, [], [[
                    expandFrom: {:., [], [:Alignment, :CenterVertically]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandVertically(expandFrom = Alignment.CenterVertically)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:expandVertically, [], [[
                    expandFrom: {:., [], [:Alignment, :CenterVertically]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = expandVertically(expandFrom = Alignment.CenterVertically, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionFadeInTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeIn, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = fadeIn()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeIn, [], [[
                    initialAlpha: 0.5
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = fadeIn(initialAlpha = 0.5f)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeIn, [], [[
                    initialAlpha: 0.5,
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = fadeIn(initialAlpha = 0.5f, animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionScaleInTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleIn, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = scaleIn()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleIn, [], [[
                    initialScale: 0.5
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = scaleIn(initialScale = 0.5f)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleIn, [], [[
                    animationSpec: {:tween, [], []},
                    initialScale: 0.5,
                    transformOrigin: {:TransformOrigin, [], [0.75, 0.25]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = scaleIn(
            animationSpec = tween(),
            initialScale = 0.5f,
            transformOrigin = TransformOrigin(.75f, .25f)
        )
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionSlideInTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideIn, [], [[
                    initialOffset: {:IntOffset, [], [10, 20]}
                ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideIn { IntOffset(10, 20) }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideIn, [], [[
                    animationSpec: {:tween, [], []},
                    initialOffset: {:IntOffset, [], [10, 20]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideIn(animationSpec = tween()) { IntOffset(10, 20) }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionSlideInHorizontallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideInHorizontally, [], [[ initialOffsetX: 50 ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideInHorizontally { 50 }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideInHorizontally, [], [[
                    animationSpec: {:tween, [], []},
                    initialOffsetX: 60
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideInHorizontally(animationSpec = tween()) { 60 }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun enterTransitionSlideInVerticallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideInVertically, [], [[ initialOffsetY: 50 ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideInVertically { 50 }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideInVertically, [], [[ 
                    initialOffsetY: 60, 
                    animationSpec: {:tween, [], []} 
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            enterTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideInVertically(animationSpec = tween()) { 60 }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionList() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeOut, [], []},
                {:shrinkOut, [], []}
            ]}
            """.trimStyle()
        )
        val transitionFromStyle = exitTransitionFromArgument(
            *(ModifierDataAdapter(expr as TupleExprContext).arguments.toTypedArray())
        )
        val transition = fadeOut() + shrinkOut()
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionFadeOutTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeOut, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = fadeOut()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeOut, [], [[
                    targetAlpha: 0.5
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = fadeOut(targetAlpha = 0.5f)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:fadeOut, [], [[
                    targetAlpha: 0.5,
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = fadeOut(targetAlpha = 0.5f, animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionScaleOutTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleOut, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = scaleOut()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleOut, [], [[
                    targetScale: 0.5
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = scaleOut(targetScale = 0.5f)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:scaleOut, [], [[
                    animationSpec: {:tween, [], []},
                    targetScale: 0.5,
                    transformOrigin: {:TransformOrigin, [], [0.75, 0.25]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = scaleOut(
            animationSpec = tween(),
            targetScale = 0.5f,
            transformOrigin = TransformOrigin(.75f, .25f)
        )
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionSlideOutTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOut, [], [[
                    targetOffset: {:IntOffset, [], [10, 20]}
                ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideOut { IntOffset(10, 20) }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOut, [], [[
                    animationSpec: {:tween, [], []},
                    targetOffset: {:IntOffset, [], [10, 20]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideOut(animationSpec = tween()) { IntOffset(10, 20) }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionSlideOutHorizontallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOutHorizontally, [], [[ targetOffsetX: 50 ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideOutHorizontally { 50 }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOutHorizontally, [], [[
                    animationSpec: {:tween, [], []},
                    targetOffsetX: 60
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideOutHorizontally(animationSpec = tween()) { 60 }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionSlideOutVerticallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOutVertically, [], [[ targetOffsetY: 50 ]]},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = slideOutVertically { 50 }
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:slideOutVertically, [], [[ 
                    targetOffsetY: 60, 
                    animationSpec: {:tween, [], []} 
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = slideOutVertically(animationSpec = tween()) { 60 }
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionShrinkHorizontallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkHorizontally, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = shrinkHorizontally()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkHorizontally, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkHorizontally(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkHorizontally, [], [[
                    shrinkTowards: {:., [], [:Alignment, :CenterHorizontally]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkHorizontally, [], [[
                    shrinkTowards: {:., [], [:Alignment, :CenterHorizontally]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionShrinkOutTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkOut, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = shrinkOut()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkOut, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkOut(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkOut, [], [[
                    shrinkTowards: {:., [], [:Alignment, :Center]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkOut(shrinkTowards = Alignment.Center)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkOut, [], [[
                    shrinkTowards: {:., [], [:Alignment, :BottomEnd]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkOut(shrinkTowards = Alignment.BottomEnd, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun exitTransitionShrinkVerticallyTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkVertically, [], []},
            ]}
            """.trimStyle()
        )
        var transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var transition = shrinkVertically()
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkVertically, [], [[
                    animationSpec: {:tween, [], []}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkVertically(animationSpec = tween())
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkVertically, [], [[
                    shrinkTowards: {:., [], [:Alignment, :CenterVertically]}
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        assertEquals(transition.toString(), transitionFromStyle.toString())

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:shrinkVertically, [], [[
                    shrinkTowards: {:., [], [:Alignment, :CenterVertically]},
                    clip: false
                ]]},
            ]}
            """.trimStyle()
        )
        transitionFromStyle =
            exitTransitionFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        transition = shrinkVertically(shrinkTowards = Alignment.CenterVertically, clip = false)
        assertEquals(transition.toString(), transitionFromStyle.toString())
    }

    @Test
    fun finiteAnimationSpecTweenTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:tween, [], []}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = tween<Int>()
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun finiteAnimationSpecTweenWithArgsTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:tween, [], [2000, 100, {:., [], [:EaseInBounce]}]}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = tween<Int>(2000, 100, EaseInBounce)
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun finiteAnimationSpecSpringTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:spring, [], []}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = spring<Int>()
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun finiteAnimationSpecSpringWithArgsTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:spring, [], [0.75, 400]}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = spring<Int>(0.75f, 400f)
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun finiteAnimationSpecKeyframesTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                 {:keyframes, [], []}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = keyframes<Int> { }
        assert(finiteAnimationSpecFromStyle is KeyframesSpec)
        assertEquals(
            finiteAnimationSpec.config.delayMillis,
            (finiteAnimationSpecFromStyle as KeyframesSpec).config.delayMillis
        )
        assertEquals(
            finiteAnimationSpec.config.durationMillis,
            finiteAnimationSpecFromStyle.config.durationMillis
        )
    }

    @Test
    fun finiteAnimationSpecKeyframesWithArgsTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:keyframes, [], [2000, 500]}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec =
            keyframes<Int> { this.durationMillis = 2000; this.delayMillis = 500 }
        assert(finiteAnimationSpecFromStyle is KeyframesSpec)
        assertEquals(
            finiteAnimationSpec.config.delayMillis,
            (finiteAnimationSpecFromStyle as KeyframesSpec).config.delayMillis
        )
        assertEquals(
            finiteAnimationSpec.config.durationMillis,
            finiteAnimationSpecFromStyle.config.durationMillis
        )
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Test
    fun finiteAnimationSpecKeyframesWithSplineTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                 {:keyframesWithSpline, [], []}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = keyframesWithSpline<Int> { }
        assert(finiteAnimationSpecFromStyle is KeyframesWithSplineSpec)
        assertEquals(
            finiteAnimationSpec.config.delayMillis,
            (finiteAnimationSpecFromStyle as KeyframesWithSplineSpec).config.delayMillis
        )
        assertEquals(
            finiteAnimationSpec.config.durationMillis,
            finiteAnimationSpecFromStyle.config.durationMillis
        )
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Test
    fun finiteAnimationSpecKeyframesWithSplineWithArgsTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:keyframesWithSpline, [], [2000, 500]}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec =
            keyframesWithSpline<Int> { this.durationMillis = 2000; this.delayMillis = 500 }
        assert(finiteAnimationSpecFromStyle is KeyframesWithSplineSpec)
        assertEquals(
            finiteAnimationSpec.config.delayMillis,
            (finiteAnimationSpecFromStyle as KeyframesWithSplineSpec).config.delayMillis
        )
        assertEquals(
            finiteAnimationSpec.config.durationMillis,
            finiteAnimationSpecFromStyle.config.durationMillis
        )
    }


    @Test
    fun finiteAnimationSpecRepeatableTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:repeatable, [], [
                    10, 
                    {:tween, [], []}, 
                    {:., [], [:RepeatMode, :Restart]}, 
                    {:StartOffset, [], [15]}
                ]}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = repeatable<Int>(10, tween(), RepeatMode.Restart, StartOffset(15))
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun finiteAnimationSpecSnapTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:snap, [], []}
            ]}
            """.trimStyle()
        )
        val finiteAnimationSpecFromStyle =
            finiteAnimationSpecFromArg<Int>(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val finiteAnimationSpec = snap<Int>()
        assertEquals(finiteAnimationSpec, finiteAnimationSpecFromStyle)
    }

    @Test
    fun floatRangeTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:.., [], [10.5, 99.3]}
            ]}
            """.trimStyle()
        )
        val floatRangeFromStyle =
            floatRangeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val floatRange = 10.5f..99.3f
        assertEquals(floatRange, floatRangeFromStyle)
    }

    @Test
    fun horizontalAlignmentTest() {
        val aligns = listOf(
            Alignment.Start,
            Alignment.CenterHorizontally,
            Alignment.End
        )
        val alignsText = listOf(
            HorizontalAlignmentValues.start,
            HorizontalAlignmentValues.centerHorizontally,
            HorizontalAlignmentValues.end
        )
        aligns.forEachIndexed { index, horizontalAlign ->
            val expr = ModifiersParser.parseElixirContent(
                """
                {:., [], [
                    {:., [], [:Alignment, :${alignsText[index]}]}
                ]}
                """.trimStyle()
            )
            val horizontalAlignmentFromStyle =
                horizontalAlignmentFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
            assertEquals(horizontalAlign, horizontalAlignmentFromStyle)
        }
    }

    @Test
    fun horizontalAlignmentLineTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:FirstBaseline]}
            ]}
            """.trimStyle()
        )
        var horizontalAlignmentLineFromStyle =
            horizontalAlignmentLineFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var horizontalAlignmentLine = FirstBaseline
        assertEquals(horizontalAlignmentLine, horizontalAlignmentLineFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:LastBaseline]}
            ]}
            """.trimStyle()
        )
        horizontalAlignmentLineFromStyle =
            horizontalAlignmentLineFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        horizontalAlignmentLine = LastBaseline
        assertEquals(horizontalAlignmentLine, horizontalAlignmentLineFromStyle)
    }

    @Test
    fun intOffsetTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:IntOffset, [], [15, 35]}
            ]}
            """.trimStyle()
        )
        val intOffsetFromStyle =
            intOffsetFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val intOffset = IntOffset(15, 35)
        assertEquals(intOffset, intOffsetFromStyle)
    }

    @Test
    fun intRangeTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:.., [], [11, 99]}
            ]}
            """.trimStyle()
        )
        val intRangeFromStyle =
            intRangeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val intRange = 11..99
        assertEquals(intRange, intRangeFromStyle)
    }

    @Test
    fun intrinsicSizeTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:IntrinsicSize, :Min]}
            ]}
            """.trimStyle()
        )
        var intrinsicSizeFromStyle =
            intrinsicSizeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var intrinsicSize = IntrinsicSize.Min
        assertEquals(intrinsicSize, intrinsicSizeFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:IntrinsicSize, :Max]}
            ]}
            """.trimStyle()
        )
        intrinsicSizeFromStyle =
            intrinsicSizeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        intrinsicSize = IntrinsicSize.Max
        assertEquals(intrinsicSize, intrinsicSizeFromStyle)
    }

    @Test
    fun intSizeTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:IntSize, [], [48, 96]}
            ]}
            """.trimStyle()
        )
        val intSizeFromStyle =
            intSizeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val intSize = IntSize(48, 96)
        assertEquals(intSize, intSizeFromStyle)
    }

    @Test
    fun offsetTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Offset, [], [48, 96]}
            ]}
            """.trimStyle()
        )
        var offsetFromStyle =
            offsetFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var offset = Offset(48f, 96f)
        assertEquals(offset, offsetFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:Offset, [], [48.5, 96.2]}
            ]}
            """.trimStyle()
        )
        offsetFromStyle =
            offsetFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        offset = Offset(48.5f, 96.2f)
        assertEquals(offset, offsetFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:Offset, :Infinite]}
            ]}
            """.trimStyle()
        )
        offsetFromStyle =
            offsetFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        offset = Offset.Infinite
        assertEquals(offset, offsetFromStyle)
    }

    @Test
    fun repeatModeTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:RepeatMode, :Restart]}
            ]}
            """.trimStyle()
        )
        var repeatModeFromStyle =
            repeatModeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var repeatMode = RepeatMode.Restart
        assertEquals(repeatMode, repeatModeFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:RepeatMode, :Reverse]}
            ]}
            """.trimStyle()
        )
        repeatModeFromStyle =
            repeatModeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        repeatMode = RepeatMode.Reverse
        assertEquals(repeatMode, repeatModeFromStyle)
    }

    @Test
    fun roleTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:Role, :Button]}
            ]}
            """.trimStyle()
        )
        var roleFromStyle =
            roleFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var role = Role.Button
        assertEquals(role, roleFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:Role, :Switch]}
            ]}
            """.trimStyle()
        )
        roleFromStyle =
            roleFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        role = Role.Switch
        assertEquals(role, roleFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:Role, :DropdownList]}
            ]}
            """.trimStyle()
        )
        roleFromStyle =
            roleFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        role = Role.DropdownList
        assertEquals(role, roleFromStyle)
    }

    @Test
    fun shapeTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:CircleShape]}
            ]}
            """.trimStyle()
        )
        var shapeFromStyle =
            shapeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var shape: Shape = CircleShape
        assertEquals(shape, shapeFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:RectangleShape]}
            ]}
            """.trimStyle()
        )
        shapeFromStyle =
            shapeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        shape = RectangleShape
        assertEquals(shape, shapeFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:RoundedCornerShape, [], [{:Dp, [], [12]}]}
            ]}
            """.trimStyle()
        )
        shapeFromStyle =
            shapeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        shape = RoundedCornerShape(12.dp)
        assertEquals(shape, shapeFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:RoundedCornerShape, [], [[topStart: {:Dp, [], [12]}, bottomEnd: {:Dp, [], [36]}]] }
            ]}
            """.trimStyle()
        )
        shapeFromStyle =
            shapeFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 36.dp)
        assertEquals(shape, shapeFromStyle)
    }

    @Test
    fun startOffsetTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:StartOffset, [], [20]}
            ]}
            """.trimStyle()
        )
        val startOffsetFromStyle =
            startOffsetFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        val startOffset = StartOffset(20)
        assertEquals(startOffset, startOffsetFromStyle)
    }

    @Test
    fun textUnitTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:TextUnit, [], [20, {:., [], [:TextUnitType, :Sp]}]}
            ]}
            """.trimStyle()
        )
        var textUnitFromStyle =
            textUnitFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var textUnit = TextUnit(20f, TextUnitType.Sp)
        assertEquals(textUnit, textUnitFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:TextUnit, [], [16.0, {:., [], [:TextUnitType, :Em]}]}
            ]}
            """.trimStyle()
        )
        textUnitFromStyle =
            textUnitFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        textUnit = TextUnit(16f, TextUnitType.Em)
        assertEquals(textUnit, textUnitFromStyle)
    }

    @Test
    fun toggleableStateTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:ToggleableState, [], [true]}
            ]}
            """.trimStyle()
        )
        var toggleableStateFromStyle =
            toggleableStateFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var toggleableState = ToggleableState(true)
        assertEquals(toggleableState, toggleableStateFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:ToggleableState, :On]}
            ]}
            """.trimStyle()
        )
        toggleableStateFromStyle =
            toggleableStateFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        toggleableState = ToggleableState.On
        assertEquals(toggleableState, toggleableStateFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:., [], [:ToggleableState, :Off]}
            ]}
            """.trimStyle()
        )
        toggleableStateFromStyle =
            toggleableStateFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        toggleableState = ToggleableState.Off
        assertEquals(toggleableState, toggleableStateFromStyle)
    }

    @Test
    fun transformOriginTest() {
        val expr = ModifiersParser.parseElixirContent(
            """
                {:., [], [
                    {:TransformOrigin, [], [10, 20]}
                ]}
                """.trimStyle()
        )
        val transformOrigin = TransformOrigin(10f, 20f)
        val transformOriginFromStyle =
            transformOriginFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        assertEquals(transformOrigin, transformOriginFromStyle)
    }

    @Test
    fun verticalAlignmentTest() {
        val aligns = listOf(
            Alignment.Top,
            Alignment.CenterVertically,
            Alignment.Bottom
        )
        val alignsText = listOf(
            VerticalAlignmentValues.top,
            VerticalAlignmentValues.centerVertically,
            VerticalAlignmentValues.bottom
        )
        aligns.forEachIndexed { index, horizontalAlign ->
            val expr = ModifiersParser.parseElixirContent(
                """
                {:., [], [
                    {:., [], [:Alignment, :${alignsText[index]}]}
                ]}
                """.trimStyle()
            )
            val verticalAlignmentFromStyle =
                verticalAlignmentFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
            assertEquals(horizontalAlign, verticalAlignmentFromStyle)
        }
    }

    @Test
    fun windowInsetsTest() {
        var expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:WindowInsets, [], [10, 20, 30, 40]}
            ]}
            """.trimStyle()
        )
        var windowInsetsFromStyle =
            windowInsetsFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        var windowInsets = WindowInsets(10, 20, 30, 40)
        assertEquals(windowInsets, windowInsetsFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:WindowInsets, [], [[top: 10, right: 30]]}
            ]}
            """.trimStyle()
        )
        windowInsetsFromStyle =
            windowInsetsFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        windowInsets = WindowInsets(top = 10, right = 30)
        assertEquals(windowInsets, windowInsetsFromStyle)

        expr = ModifiersParser.parseElixirContent(
            """
            {:., [], [
                {:WindowInsets, [], [{:Dp, [], [10]}, {:Dp, [], [20]}, {:Dp, [], [30]}, {:Dp, [], [40]}]}
            ]}
            """.trimStyle()
        )
        windowInsetsFromStyle =
            windowInsetsFromArgument(ModifierDataAdapter(expr as TupleExprContext).arguments.first())
        windowInsets = WindowInsets(10.dp, 20.dp, 30.dp, 40.dp)
        assertEquals(windowInsets, windowInsetsFromStyle)
    }
}

package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.keyframesWithSpline
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.inspectable
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dockyard.liveviewtest.liveview.util.ModifierBaseTest
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser.fromStyleName

@RunWith(AndroidJUnit4::class)
class AnimationTest : ModifierBaseTest() {

    @Test
    fun animateContentSizeTweenTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeTweenTest" => [
                {:animateContentSize, [], [
                    {:tween, [], []}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = tween()
            )
        )
    }

    @Test
    fun animateContentSizeTweenWithArgsTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeTweenWithArgsTest" => [
                {:animateContentSize, [], [
                    {:tween, [], [2000, 100, {:., [], [:EaseInBounce]}]}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = tween(2000, 100, EaseInBounce)
            )
        )
    }

    @Test
    fun animateContentSizeSpringTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeSpringTest" => [
                {:animateContentSize, [], [
                    {:spring, [], []}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = spring()
            )
        )
    }

    @Test
    fun animateContentSizeSpringWithArgsTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeSpringTest" => [
                {:animateContentSize, [], [
                    {:spring, [], [0.75, 400]}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.75f,
                    stiffness = 400f,
                )
            )
        )
    }

    @Test
    fun animateContentSizeKeyframesTest() {
        val style = """
            %{"animateContentSizeKeyframesTest" => [
                {:animateContentSize, [], [
                    {:keyframes, [], []}
                ]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        val modifier = Modifier.fromStyleName("animateContentSizeKeyframesTest")
        // Keyframe spec is a lambda, so we can't compare with equals. So we're comparing using
        // toString ignoring the memory address
        val s = "KeyframesSpec@"
        val a = Modifier.animateContentSize(animationSpec = keyframes { }).toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        val b = modifier.toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        assertEquals(a, b)
    }

    @Test
    fun animateContentSizeKeyframesWithParamsTest() {
        val style = """
            %{"animateContentSizeKeyframesWithParamsTest" => [
                {:animateContentSize, [], [
                    {:keyframes, [], [2000, 100]}
                ]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        val modifier = Modifier.fromStyleName("animateContentSizeKeyframesWithParamsTest")
        // Keyframe spec is a lambda, so we can't compare with equals. So we're comparing using
        // toString ignoring the memory address
        val s = "KeyframesSpec@"
        val a = Modifier.animateContentSize(animationSpec = keyframes { }).toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        val b = modifier.toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        assertEquals(a, b)
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Test
    fun animateContentSizeKeyframesWithSplineTest() {
        val style = """
            %{"animateContentSizeKeyframesWithSplineTest" => [
                {:animateContentSize, [], [
                    {:keyframesWithSpline, [], []}
                ]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        val modifier = Modifier.fromStyleName("animateContentSizeKeyframesWithSplineTest")
        // Keyframe spec is a lambda, so we can't compare with equals. So we're comparing using
        // toString ignoring the memory address
        val s = "KeyframesWithSplineSpec@"
        val a =
            Modifier.animateContentSize(animationSpec = keyframesWithSpline { }).toString().let {
                val from = it.indexOf(s) + s.length
                val to = it.indexOf(',', from)
                it.removeRange(from, to)
            }
        val b = modifier.toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        assertEquals(a, b)
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Test
    fun animateContentSizeKeyframesWithSplineWithParamsTest() {
        val style = """
            %{"animateContentSizeKeyframesWithSplineWithParamsTest" => [
                {:animateContentSize, [], [
                    {:keyframesWithSpline, [], [2000, 100]}
                ]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        val modifier = Modifier.fromStyleName("animateContentSizeKeyframesWithSplineWithParamsTest")
        // Keyframe spec is a lambda, so we can't compare with equals. So we're comparing using
        // toString ignoring the memory address
        val s = "KeyframesWithSplineSpec@"
        val a =
            Modifier.animateContentSize(animationSpec = keyframesWithSpline { }).toString().let {
                val from = it.indexOf(s) + s.length
                val to = it.indexOf(',', from)
                it.removeRange(from, to)
            }
        val b = modifier.toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        assertEquals(a, b)
    }

    @Test
    fun animateContentSizeRepeatableTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeRepeatableTest" => [
                {:animateContentSize, [], [
                    {:repeatable, [], [
                      10, 
                      {:tween, [], []}, 
                      {:., [], [:RepeatMode, :Restart]}, 
                      {:StartOffset, [], [15]}
                    ]}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = repeatable(10, tween(), RepeatMode.Restart, StartOffset(15))
            )
        )
    }

    @Test
    fun animateContentSizeRepeatableNamedTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeRepeatableNamedTest" => [
                {:animateContentSize, [], [
                    {:repeatable, [], [[
                      iterations: 10, 
                      animation: {:tween, [], []}, 
                      repeatMode: {:., [], [:RepeatMode, :Restart]}, 
                      initialStartOffset: {:StartOffset, [], [15]}
                    ]]}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = repeatable(10, tween(), RepeatMode.Restart, StartOffset(15))
            )
        )
    }

    @Test
    fun animateContentSizeSnapTest() {
        assertModifierFromStyle(
            """
            %{"animateContentSizeSnapTest" => [
                {:animateContentSize, [], [
                    {:snap, [], []}
                ]},
            ]}
            """,
            Modifier.animateContentSize(
                animationSpec = snap()
            )
        )
    }

    @Test
    fun animateContentSizeSnapWithArgsTest() {
        val style = """
            %{"animateContentSizeSnapWithArgsTest" => [
                {:animateContentSize, [], [
                    {:snap, [], [100]}
                ]},
            ]}
            """
        ModifiersParser.fromStyleFile(style, null)
        val modifier = Modifier.fromStyleName("animateContentSizeSnapWithArgsTest")
        // Snap spec is a lambda, so we can't compare with equals. So we're comparing using
        // toString ignoring the memory address
        val s = "SnapSpec@"
        val a = Modifier.animateContentSize(animationSpec = snap(100)).toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        val b = modifier.toString().let {
            val from = it.indexOf(s) + s.length
            val to = it.indexOf(',', from)
            it.removeRange(from, to)
        }
        assertEquals(a, b)
    }
}
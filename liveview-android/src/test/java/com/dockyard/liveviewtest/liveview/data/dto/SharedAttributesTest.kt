package com.dockyard.liveviewtest.liveview.data.dto

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.AlignmentValues.bottomStart
import org.phoenixframework.liveview.data.constants.AlignmentValues.topEnd
import org.phoenixframework.liveview.data.constants.Attrs.attrBottom
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrLeft
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionX
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionY
import org.phoenixframework.liveview.data.constants.Attrs.attrRight
import org.phoenixframework.liveview.data.constants.Attrs.attrTop
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ContentScaleValues
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argClip
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argExpandFrom
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialAlpha
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffset
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffsetX
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffsetY
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialScale
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argShrinkTowards
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetAlpha
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffset
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffsetX
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffsetY
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetScale
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTransformOrigin
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argX
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argY
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.expandHorizontally
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.expandIn
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.expandVertically
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.fadeIn
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.fadeOut
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.scaleIn
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.scaleOut
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.shrinkHorizontally
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.shrinkOut
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.shrinkVertically
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideIn
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideInHorizontally
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideInVertically
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideOut
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideOutHorizontally
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.slideOutVertically
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues.centerHorizontally
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues.start
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.SecureFlagPolicyValues
import org.phoenixframework.liveview.data.constants.SystemColorValues
import org.phoenixframework.liveview.data.constants.TileModeValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues.centerVertically
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues.top
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.data.dto.alignmentFromString
import org.phoenixframework.liveview.data.dto.borderFromString
import org.phoenixframework.liveview.data.dto.colorsFromString
import org.phoenixframework.liveview.data.dto.contentScaleFromString
import org.phoenixframework.liveview.data.dto.elevationsFromString
import org.phoenixframework.liveview.data.dto.enterTransitionFromString
import org.phoenixframework.liveview.data.dto.exitTransitionFromString
import org.phoenixframework.liveview.data.dto.horizontalAlignmentFromString
import org.phoenixframework.liveview.data.dto.horizontalArrangementFromString
import org.phoenixframework.liveview.data.dto.secureFlagPolicyFromString
import org.phoenixframework.liveview.data.dto.tileModeFromString
import org.phoenixframework.liveview.data.dto.verticalAlignmentFromString
import org.phoenixframework.liveview.data.dto.verticalArrangementFromString
import org.phoenixframework.liveview.data.dto.windowInsetsFromString

@RunWith(AndroidJUnit4::class)
class SharedAttributesTest {

    @Test
    fun alignFromStringTest() {
        assertEquals(
            Alignment.TopStart,
            alignmentFromString(AlignmentValues.topStart, Alignment.TopStart)
        )
        assertEquals(
            Alignment.TopCenter,
            alignmentFromString(AlignmentValues.topCenter, Alignment.TopStart)
        )
        assertEquals(
            Alignment.TopEnd,
            alignmentFromString(AlignmentValues.topEnd, Alignment.TopStart)
        )
        assertEquals(
            Alignment.CenterStart,
            alignmentFromString(AlignmentValues.centerStart, Alignment.TopStart)
        )
        assertEquals(
            Alignment.Center,
            alignmentFromString(AlignmentValues.center, Alignment.TopStart)
        )
        assertEquals(
            Alignment.CenterEnd,
            alignmentFromString(AlignmentValues.centerEnd, Alignment.TopStart)
        )
        assertEquals(
            Alignment.BottomStart,
            alignmentFromString(AlignmentValues.bottomStart, Alignment.TopStart)
        )
        assertEquals(
            Alignment.BottomCenter,
            alignmentFromString(AlignmentValues.bottomCenter, Alignment.TopStart)
        )
        assertEquals(
            Alignment.BottomEnd,
            alignmentFromString(AlignmentValues.bottomEnd, Alignment.TopStart)
        )
    }

    @Test
    fun borderFromStringTest() {
        assertEquals(
            BorderStroke(20.dp, Color.Red),
            borderFromString("{'$attrWidth': 20, '$attrColor': '${SystemColorValues.Red}'}")
        )
    }

    @Test
    fun colorsFromStringTest() {
        val map = colorsFromString("{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}")
        assertNotNull(map)
        assertEquals("#FFFF0000", map?.get("containerColor"))
        assertEquals("#FF00FF00", map?.get("contentColor"))
    }

    @Test
    fun contentScaleFromStringTest() {
        assertEquals(
            ContentScale.Crop,
            contentScaleFromString(ContentScaleValues.crop, ContentScale.None)
        )
        assertEquals(
            ContentScale.FillBounds,
            contentScaleFromString(ContentScaleValues.fillBounds, ContentScale.None)
        )
        assertEquals(
            ContentScale.FillHeight,
            contentScaleFromString(ContentScaleValues.fillHeight, ContentScale.None)
        )
        assertEquals(
            ContentScale.FillWidth,
            contentScaleFromString(ContentScaleValues.fillWidth, ContentScale.None)
        )
        assertEquals(
            ContentScale.Fit,
            contentScaleFromString(ContentScaleValues.fit, ContentScale.None)
        )
        assertEquals(
            ContentScale.Inside,
            contentScaleFromString(ContentScaleValues.inside, ContentScale.None)
        )
        assertEquals(
            ContentScale.None,
            contentScaleFromString(ContentScaleValues.none, ContentScale.None)
        )
    }

    @Test
    fun elevationFromStringTest() {
        val map = elevationsFromString("{'defaultElevation': '20', 'pressedElevation': '10'}")
        assertNotNull(map)
        assertEquals("20", map?.get("defaultElevation"))
        assertEquals("10", map?.get("pressedElevation"))
    }

    @Test
    fun enterTransitionFromStringListTest() {
        val json = """[
            {"$expandVertically": {} },
            {"$slideInVertically": {} },
            {"$fadeIn": {} }
            ]
            """.trimMargin()
        val expected = expandVertically() + slideInVertically() + fadeIn()
        val actual = enterTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringExpandHorizontallyTest() {
        var json = """{"$expandHorizontally": {}}"""
        var expected = expandHorizontally()
        var actual = enterTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandHorizontally": {
              "$argExpandFrom": "$centerHorizontally"
            }}
            """.trimIndent()
        expected = expandHorizontally(expandFrom = Alignment.CenterHorizontally)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandHorizontally": {
              "$argExpandFrom": "$start",
              "$argClip": false
            }}
            """.trimIndent()
        expected = expandHorizontally(expandFrom = Alignment.Start, clip = false)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringExpandInTest() {
        var json = """{"$expandIn": {}}"""
        var expected = expandIn()
        var actual = enterTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandIn": {
              "$argExpandFrom": "$topEnd"
            }}
            """.trimIndent()
        expected = expandIn(expandFrom = Alignment.TopEnd)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandIn": {
              "$argExpandFrom": "$bottomStart",
              "$argClip": false
            }}
            """.trimIndent()
        expected = expandIn(expandFrom = Alignment.BottomStart, clip = false)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringExpandVerticallyTest() {
        var json = """{"$expandVertically": {}}"""
        var expected = expandVertically()
        var actual = enterTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandVertically": {
              "$argExpandFrom": "$top"
            }}
            """.trimIndent()
        expected = expandVertically(expandFrom = Alignment.Top)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandVertically": {
              "$argExpandFrom": "$centerVertically",
              "$argClip": false
            }}
            """.trimIndent()
        expected = expandVertically(expandFrom = Alignment.CenterVertically, clip = false)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringFadeInTest() {
        var json = """{"$fadeIn": {}}"""
        var expected = fadeIn()
        var actual = enterTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$fadeIn": {
              "$argInitialAlpha": 0.5
            }}
            """.trimIndent()
        expected = fadeIn(initialAlpha = 0.5f)
        actual = enterTransitionFromString(json)
        assertEquals(expected, actual)
    }

    @Test
    fun enterTransitionFromStringScaleInTest() {
        var json = """{"$scaleIn": {}}"""
        var expected = scaleIn()
        var actual = enterTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$scaleIn": {
              "$argInitialScale": 0.5
            }}
            """.trimIndent()
        expected = scaleIn(initialScale = 0.5f)
        actual = enterTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$scaleIn": {
              "$argInitialScale": 0.5,
              "$argTransformOrigin": {"$attrPivotFractionX": 0.75, "$attrPivotFractionY": 0.25}
            }}
            """.trimIndent()
        expected = scaleIn(initialScale = 0.5f, transformOrigin = TransformOrigin(.75f, .25f))
        actual = enterTransitionFromString(json)
        assertEquals(expected, actual)
    }

    @Test
    fun enterTransitionFromStringSlideInTest() {
        val json = """
            {"$slideIn": {"$argInitialOffset": {"$argX": 10, "$argY": 20} }}
            """.trimIndent()
        val expected = slideIn { IntOffset(x = 10, y = 20) }
        val actual = enterTransitionFromString(json)
        // slide has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringSlideInHorizontallyTest() {
        var json = """{"$slideInHorizontally": {}}"""
        var expected = slideInHorizontally()
        var actual = enterTransitionFromString(json)
        // slide has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$slideInHorizontally": {
              "$argInitialOffsetX": 50
            }}
            """.trimIndent()
        expected = slideInHorizontally(initialOffsetX = { 50 })
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun enterTransitionFromStringSlideInVerticallyTest() {
        var json = """{"$slideInVertically": {}}"""
        var expected = slideInVertically()
        var actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$slideInVertically": {
              "$argInitialOffsetY": 50
            }}
            """.trimIndent()
        expected = slideInVertically(initialOffsetY = { 50 })
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringListTest() {
        val json = """[
            {"$shrinkHorizontally": {} },
            {"$slideOutHorizontally": {} },
            {"$fadeOut": {} }
            ]
            """.trimMargin()
        val expected = shrinkHorizontally() + slideOutHorizontally() + fadeOut()
        val actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringShrinkHorizontallyTest() {
        var json = """{"$shrinkHorizontally": {}}"""
        var expected = shrinkHorizontally()
        var actual = exitTransitionFromString(json)
        // shrink has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkHorizontally": {
              "$argShrinkTowards": "$centerHorizontally"
            }}
            """.trimIndent()
        expected = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkHorizontally": {
              "$argShrinkTowards": "$start",
              "$argClip": false
            }}
            """.trimIndent()
        expected = shrinkHorizontally(shrinkTowards = Alignment.Start, clip = false)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringShrinkOutTest() {
        var json = """{"$shrinkOut": {}}"""
        var expected = shrinkOut()
        var actual = exitTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkOut": {
              "$argShrinkTowards": "$topEnd"
            }}
            """.trimIndent()
        expected = shrinkOut(shrinkTowards = Alignment.TopEnd)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkOut": {
              "$argShrinkTowards": "$bottomStart",
              "$argClip": false
            }}
            """.trimIndent()
        expected = shrinkOut(shrinkTowards = Alignment.BottomStart, clip = false)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringShrinkVerticallyTest() {
        var json = """{"$shrinkVertically": {}}"""
        var expected = shrinkVertically()
        var actual = exitTransitionFromString(json)
        // expand has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkVertically": {
              "$argShrinkTowards": "$top"
            }}
            """.trimIndent()
        expected = shrinkVertically(shrinkTowards = Alignment.Top)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkVertically": {
              "$argShrinkTowards": "$centerVertically",
              "$argClip": false
            }}
            """.trimIndent()
        expected = shrinkVertically(shrinkTowards = Alignment.CenterVertically, clip = false)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringFadeOutTest() {
        var json = """{"$fadeOut": {}}"""
        var expected = fadeOut()
        var actual = exitTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$fadeOut": {
              "$argTargetAlpha": 0.5
            }}
            """.trimIndent()
        expected = fadeOut(targetAlpha = 0.5f)
        actual = exitTransitionFromString(json)
        assertEquals(expected, actual)
    }

    @Test
    fun exitTransitionFromStringScaleOutTest() {
        var json = """{"$scaleOut": {}}"""
        var expected = scaleOut()
        var actual = exitTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$scaleOut": {
              "$argTargetScale": 0.5
            }}
            """.trimIndent()
        expected = scaleOut(targetScale = 0.5f)
        actual = exitTransitionFromString(json)
        assertEquals(expected, actual)

        json = """
            {"$scaleOut": {
              "$argTargetScale": 0.5,
              "$argTransformOrigin": {"$attrPivotFractionX": 0.75, "$attrPivotFractionY": 0.25}
            }}
            """.trimIndent()
        expected = scaleOut(targetScale = 0.5f, transformOrigin = TransformOrigin(.75f, .25f))
        actual = exitTransitionFromString(json)
        assertEquals(expected, actual)
    }

    @Test
    fun exitTransitionFromStringSlideOutTest() {
        val json = """
            {"$slideOut": {"$argTargetOffset": {"$argX": 10, "$argY": 20} }}
            """.trimIndent()
        val expected = slideOut { IntOffset(x = 10, y = 20) }
        val actual = exitTransitionFromString(json)
        // slide has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringSlideOutHorizontallyTest() {
        var json = """{"$slideOutHorizontally": {}}"""
        var expected = slideOutHorizontally()
        var actual = exitTransitionFromString(json)
        // slide has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$slideOutHorizontally": {
              "$argTargetOffsetX": 50
            }}
            """.trimIndent()
        expected = slideOutHorizontally(targetOffsetX = { 50 })
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun exitTransitionFromStringSlideOutVerticallyTest() {
        var json = """{"$slideOutVertically": {}}"""
        var expected = slideOutVertically()
        var actual = exitTransitionFromString(json)
        // slide has a lambda as parameter, so we need to test using toString
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$slideOutVertically": {
              "$argTargetOffsetY": 50
            }}
            """.trimIndent()
        expected = slideOutVertically(targetOffsetY = { 50 })
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun horizontalAlignmentFromStringTest() {
        assertEquals(
            Alignment.Start,
            horizontalAlignmentFromString(start)
        )
        assertEquals(
            Alignment.CenterHorizontally,
            horizontalAlignmentFromString(centerHorizontally)
        )
        assertEquals(Alignment.End, horizontalAlignmentFromString(HorizontalAlignmentValues.end))
    }

    @Test
    fun horizontalArrangementFromStringTest() {
        assertEquals(
            Arrangement.SpaceEvenly,
            horizontalArrangementFromString(HorizontalArrangementValues.spaceEvenly)
        )
        assertEquals(
            Arrangement.SpaceAround,
            horizontalArrangementFromString(HorizontalArrangementValues.spaceAround)
        )
        assertEquals(
            Arrangement.SpaceBetween,
            horizontalArrangementFromString(HorizontalArrangementValues.spaceBetween)
        )
        assertEquals(
            Arrangement.Start,
            horizontalArrangementFromString(HorizontalArrangementValues.start)
        )
        assertEquals(
            Arrangement.End,
            horizontalArrangementFromString(HorizontalArrangementValues.end)
        )
        assertEquals(
            Arrangement.Center,
            horizontalArrangementFromString(HorizontalArrangementValues.center)
        )
        assertEquals(Arrangement.spacedBy(24.dp), horizontalArrangementFromString("24"))
    }

    @Test
    fun tileModeFromStringTest() {
        assertEquals(TileMode.Clamp, tileModeFromString(TileModeValues.clamp, TileMode.Clamp))
        assertEquals(TileMode.Decal, tileModeFromString(TileModeValues.decal, TileMode.Clamp))
        assertEquals(TileMode.Mirror, tileModeFromString(TileModeValues.mirror, TileMode.Clamp))
        assertEquals(TileMode.Repeated, tileModeFromString(TileModeValues.repeated, TileMode.Clamp))
    }

    @Test
    fun secureFlagPolicyFromStringTest() {
        assertEquals(
            SecureFlagPolicy.SecureOn,
            secureFlagPolicyFromString(SecureFlagPolicyValues.secureOn)
        )
        assertEquals(
            SecureFlagPolicy.SecureOff,
            secureFlagPolicyFromString(SecureFlagPolicyValues.secureOff)
        )
        assertEquals(
            SecureFlagPolicy.Inherit,
            secureFlagPolicyFromString(SecureFlagPolicyValues.inherit)
        )
    }

    @Test
    fun verticalAlignmentFromStringTest() {
        assertEquals(Alignment.Top, verticalAlignmentFromString(VerticalAlignmentValues.top))
        assertEquals(
            Alignment.CenterVertically,
            verticalAlignmentFromString(VerticalAlignmentValues.centerVertically)
        )
        assertEquals(Alignment.Bottom, verticalAlignmentFromString(VerticalAlignmentValues.bottom))
    }

    @Test
    fun verticalArrangementFromStringTest() {
        assertEquals(Arrangement.Top, verticalArrangementFromString(VerticalArrangementValues.top))
        assertEquals(
            Arrangement.SpaceEvenly,
            verticalArrangementFromString(VerticalArrangementValues.spaceEvenly)
        )
        assertEquals(
            Arrangement.SpaceAround,
            verticalArrangementFromString(VerticalArrangementValues.spaceAround)
        )
        assertEquals(
            Arrangement.SpaceBetween,
            verticalArrangementFromString(VerticalArrangementValues.spaceBetween)
        )
        assertEquals(
            Arrangement.Bottom,
            verticalArrangementFromString(VerticalArrangementValues.bottom)
        )
        assertEquals(
            Arrangement.Center,
            verticalArrangementFromString(VerticalArrangementValues.center)
        )
        assertEquals(Arrangement.spacedBy(10.dp), verticalArrangementFromString("10"))
    }

    @Test
    fun windowInsetsFromStringTest() {
        val json = """
            {"$attrLeft": 1, "$attrTop": 2, "$attrRight": 3, "$attrBottom": 4}
            """
        val windowInsets = windowInsetsFromString(json)
        assertEquals(
            WindowInsets(left = 1.dp, top = 2.dp, right = 3.dp, bottom = 4.dp),
            windowInsets
        )
    }
}
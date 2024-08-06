package org.phoenixframework.liveview.test.ui.view

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.KeyframesWithSplineSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrBaselineShift
import org.phoenixframework.liveview.data.constants.Attrs.attrBottom
import org.phoenixframework.liveview.data.constants.Attrs.attrCap
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrFirstLine
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFeatureSettings
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSynthesis
import org.phoenixframework.liveview.data.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrHyphens
import org.phoenixframework.liveview.data.constants.Attrs.attrJoin
import org.phoenixframework.liveview.data.constants.Attrs.attrLeft
import org.phoenixframework.liveview.data.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrLineBreak
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeightStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrMiter
import org.phoenixframework.liveview.data.constants.Attrs.attrOffsetMillis
import org.phoenixframework.liveview.data.constants.Attrs.attrOffsetType
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionX
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionY
import org.phoenixframework.liveview.data.constants.Attrs.attrRestLine
import org.phoenixframework.liveview.data.constants.Attrs.attrRight
import org.phoenixframework.liveview.data.constants.Attrs.attrScaleX
import org.phoenixframework.liveview.data.constants.Attrs.attrSkewX
import org.phoenixframework.liveview.data.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDirection
import org.phoenixframework.liveview.data.constants.Attrs.attrTextGeometricTransform
import org.phoenixframework.liveview.data.constants.Attrs.attrTextIndent
import org.phoenixframework.liveview.data.constants.Attrs.attrTextMotion
import org.phoenixframework.liveview.data.constants.Attrs.attrTop
import org.phoenixframework.liveview.data.constants.Attrs.attrTrim
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.BaselineShiftValues.subscript
import org.phoenixframework.liveview.data.constants.BaselineShiftValues.superscript
import org.phoenixframework.liveview.data.constants.ContentScaleValues
import org.phoenixframework.liveview.data.constants.DrawStyleValues
import org.phoenixframework.liveview.data.constants.EasingValues
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
import org.phoenixframework.liveview.data.constants.FiniteAnimationSpecFunctions
import org.phoenixframework.liveview.data.constants.FontStyleValues.italic
import org.phoenixframework.liveview.data.constants.FontSynthesisValues.style
import org.phoenixframework.liveview.data.constants.FontWeightValues.bold
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues.centerHorizontally
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues.start
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.HyphensValues.auto
import org.phoenixframework.liveview.data.constants.LineBreakValues.heading
import org.phoenixframework.liveview.data.constants.LineHeightStyleAlignmentValues
import org.phoenixframework.liveview.data.constants.LineHeightStyleAlignmentValues.proportional
import org.phoenixframework.liveview.data.constants.LineHeightStyleTrimValues
import org.phoenixframework.liveview.data.constants.LineHeightStyleTrimValues.both
import org.phoenixframework.liveview.data.constants.RepeatModeValues
import org.phoenixframework.liveview.data.constants.SecureFlagPolicyValues
import org.phoenixframework.liveview.data.constants.StartOffsetTypeValues
import org.phoenixframework.liveview.data.constants.StrokeCapValues
import org.phoenixframework.liveview.data.constants.StrokeJoinValues
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.TextAlignValues.justify
import org.phoenixframework.liveview.data.constants.TextDecorationValues.underline
import org.phoenixframework.liveview.data.constants.TextDirectionValues.rtl
import org.phoenixframework.liveview.data.constants.TextMotionValues
import org.phoenixframework.liveview.data.constants.TextMotionValues.animated
import org.phoenixframework.liveview.data.constants.TileModeValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString
import org.phoenixframework.liveview.ui.view.alignmentFromString
import org.phoenixframework.liveview.ui.view.borderFromString
import org.phoenixframework.liveview.ui.view.colorsFromString
import org.phoenixframework.liveview.ui.view.contentScaleFromString
import org.phoenixframework.liveview.ui.view.drawStyleFromString
import org.phoenixframework.liveview.ui.view.easingFromString
import org.phoenixframework.liveview.ui.view.elevationsFromString
import org.phoenixframework.liveview.ui.view.enterTransitionFromString
import org.phoenixframework.liveview.ui.view.exitTransitionFromString
import org.phoenixframework.liveview.ui.view.finiteAnimationSpecFromString
import org.phoenixframework.liveview.ui.view.horizontalAlignmentFromString
import org.phoenixframework.liveview.ui.view.horizontalArrangementFromString
import org.phoenixframework.liveview.ui.view.lineHeightStyleAlignmentFromString
import org.phoenixframework.liveview.ui.view.lineHeightStyleFromMap
import org.phoenixframework.liveview.ui.view.lineHeightStyleTrimFromString
import org.phoenixframework.liveview.ui.view.paragraphStyleFromString
import org.phoenixframework.liveview.ui.view.repeatModeFromString
import org.phoenixframework.liveview.ui.view.secureFlagPolicyFromString
import org.phoenixframework.liveview.ui.view.spanStyleFromString
import org.phoenixframework.liveview.ui.view.startOffsetFromMap
import org.phoenixframework.liveview.ui.view.startOffsetTypeFromString
import org.phoenixframework.liveview.ui.view.strokeCapFromString
import org.phoenixframework.liveview.ui.view.strokeJoinFromString
import org.phoenixframework.liveview.ui.view.textGeometricTransformFromMap
import org.phoenixframework.liveview.ui.view.textIndentFromMap
import org.phoenixframework.liveview.ui.view.textMotionFromString
import org.phoenixframework.liveview.ui.view.tileModeFromString
import org.phoenixframework.liveview.ui.view.transformOriginFromString
import org.phoenixframework.liveview.ui.view.verticalAlignmentFromString
import org.phoenixframework.liveview.ui.view.verticalArrangementFromString
import org.phoenixframework.liveview.ui.view.windowInsetsFromString

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
            borderFromString("{'$attrWidth': 20, '$attrColor': '${Red}'}")
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
    fun drawStyleFromValueTest() {
        assertEquals(drawStyleFromString(DrawStyleValues.fill), Fill)
        assertEquals(drawStyleFromString("{'$attrWidth': 2}"), Stroke(width = 2f))
        assertEquals(
            drawStyleFromString("{'$attrWidth': 4, '$attrMiter': 8}"),
            Stroke(width = 4f, miter = 8f)
        )
        assertEquals(
            drawStyleFromString("{'$attrWidth': 6, '$attrMiter': 4, '$attrCap': '${StrokeCapValues.round}'}"),
            Stroke(width = 6f, miter = 4f, cap = StrokeCap.Round)
        )
        assertEquals(
            drawStyleFromString("{'$attrWidth': 6, '$attrMiter': 4, '$attrCap': '${StrokeCapValues.square}', '$attrJoin': '${StrokeJoinValues.round}'}"),
            Stroke(width = 6f, miter = 4f, cap = StrokeCap.Square, join = StrokeJoin.Round)
        )
    }

    @Test
    fun easingFromStringTest() {
        assertEquals(easingFromString(EasingValues.ease), Ease)
        assertEquals(easingFromString(EasingValues.easeOut), EaseOut)
        assertEquals(easingFromString(EasingValues.easeIn), EaseIn)
        assertEquals(easingFromString(EasingValues.easeInOut), EaseInOut)
        assertEquals(easingFromString(EasingValues.easeInSine), EaseInSine)
        assertEquals(easingFromString(EasingValues.easeOutSine), EaseOutSine)
        assertEquals(easingFromString(EasingValues.easeInOutSine), EaseInOutSine)
        assertEquals(easingFromString(EasingValues.easeInCubic), EaseInCubic)
        assertEquals(easingFromString(EasingValues.easeOutCubic), EaseOutCubic)
        assertEquals(easingFromString(EasingValues.easeInOutCubic), EaseInOutCubic)
        assertEquals(easingFromString(EasingValues.easeInQuint), EaseInQuint)
        assertEquals(easingFromString(EasingValues.easeOutQuint), EaseOutQuint)
        assertEquals(easingFromString(EasingValues.easeInOutQuint), EaseInOutQuint)
        assertEquals(easingFromString(EasingValues.easeInCirc), EaseInCirc)
        assertEquals(easingFromString(EasingValues.easeOutCirc), EaseOutCirc)
        assertEquals(easingFromString(EasingValues.easeInOutCirc), EaseInOutCirc)
        assertEquals(easingFromString(EasingValues.easeInQuad), EaseInQuad)
        assertEquals(easingFromString(EasingValues.easeOutQuad), EaseOutQuad)
        assertEquals(easingFromString(EasingValues.easeInOutQuad), EaseInOutQuad)
        assertEquals(easingFromString(EasingValues.easeInQuart), EaseInQuart)
        assertEquals(easingFromString(EasingValues.easeOutQuart), EaseOutQuart)
        assertEquals(easingFromString(EasingValues.easeInOutQuart), EaseInOutQuart)
        assertEquals(easingFromString(EasingValues.easeInExpo), EaseInExpo)
        assertEquals(easingFromString(EasingValues.easeOutExpo), EaseOutExpo)
        assertEquals(easingFromString(EasingValues.easeInOutExpo), EaseInOutExpo)
        assertEquals(easingFromString(EasingValues.easeInBack), EaseInBack)
        assertEquals(easingFromString(EasingValues.easeOutBack), EaseOutBack)
        assertEquals(easingFromString(EasingValues.easeInOutBack), EaseInOutBack)
        assertEquals(easingFromString(EasingValues.easeInElastic), EaseInElastic)
        assertEquals(easingFromString(EasingValues.easeOutElastic), EaseOutElastic)
        assertEquals(easingFromString(EasingValues.easeInOutElastic), EaseInOutElastic)
        assertEquals(easingFromString(EasingValues.easeOutBounce), EaseOutBounce)
        assertEquals(easingFromString(EasingValues.easeInBounce), EaseInBounce)
        assertEquals(easingFromString(EasingValues.easeInOutBounce), EaseInOutBounce)
    }

    @Test
    fun elevationsFromStringTest() {
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
              "$argExpandFrom": "${AlignmentValues.topEnd}"
            }}
            """.trimIndent()
        expected = expandIn(expandFrom = Alignment.TopEnd)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandIn": {
              "$argExpandFrom": "${AlignmentValues.bottomStart}",
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
              "$argExpandFrom": "${VerticalAlignmentValues.top}"
            }}
            """.trimIndent()
        expected = expandVertically(expandFrom = Alignment.Top)
        actual = enterTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$expandVertically": {
              "$argExpandFrom": "${VerticalAlignmentValues.centerVertically}",
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
              "$argShrinkTowards": "${AlignmentValues.topEnd}"
            }}
            """.trimIndent()
        expected = shrinkOut(shrinkTowards = Alignment.TopEnd)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkOut": {
              "$argShrinkTowards": "${AlignmentValues.bottomStart}",
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
              "$argShrinkTowards": "${VerticalAlignmentValues.top}"
            }}
            """.trimIndent()
        expected = shrinkVertically(shrinkTowards = Alignment.Top)
        actual = exitTransitionFromString(json)
        assertEquals(expected.toString(), actual.toString())

        json = """
            {"$shrinkVertically": {
              "$argShrinkTowards": "${VerticalAlignmentValues.centerVertically}",
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
    fun finiteAnimationSpecFromStringTweenTest() {
        assertEquals(
            tween<Int>(),
            finiteAnimationSpecFromString<Int>(
                """
            {'${FiniteAnimationSpecFunctions.tween}': {}}    
            """.trimIndent()
            )
        )
        assertEquals(
            tween<Int>(1500),
            finiteAnimationSpecFromString<Int>(
                """
            {'${FiniteAnimationSpecFunctions.tween}': {'${FiniteAnimationSpecFunctions.argDurationMillis}': 1500 }}    
            """.trimIndent()
            )
        )
        assertEquals(
            tween<Int>(1500, 2000),
            finiteAnimationSpecFromString<Int>(
                """
            {'${FiniteAnimationSpecFunctions.tween}': 
              {
                '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500, 
                '${FiniteAnimationSpecFunctions.argDelayMillis}': 2000
              }
            }""".trimIndent()
            )
        )
        assertEquals(
            tween<Int>(1500, 2000, EaseInBounce),
            finiteAnimationSpecFromString<Int>(
                """
                {'${FiniteAnimationSpecFunctions.tween}': 
                  {
                    '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500, 
                    '${FiniteAnimationSpecFunctions.argDelayMillis}': 2000, 
                    '${FiniteAnimationSpecFunctions.argEasing}': '${EasingValues.easeInBounce}'
                  }
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun finiteAnimationSpecFromStringSpringTest() {
        assertEquals(
            spring<Int>(),
            finiteAnimationSpecFromString<Int>(
                """
                {'${FiniteAnimationSpecFunctions.spring}': {} }
                """.trimIndent()
            )
        )
        assertEquals(
            spring<Int>(200f),
            finiteAnimationSpecFromString<Int>(
                """
                {'${FiniteAnimationSpecFunctions.spring}': 
                  {
                    '${FiniteAnimationSpecFunctions.argDampingRatio}': 200
                  } 
                }
                """.trimIndent()
            )
        )
        assertEquals(
            spring<Int>(200f, 100f),
            finiteAnimationSpecFromString<Int>(
                """
                {'${FiniteAnimationSpecFunctions.spring}': 
                  {
                    '${FiniteAnimationSpecFunctions.argDampingRatio}': 200,
                    '${FiniteAnimationSpecFunctions.argStiffness}': 100
                  } 
                }
                """.trimIndent()
            )
        )
    }

    @Test
    fun finiteAnimationSpecFromStringKeyframesTest() {
        var expect = keyframes<Int> { }
        var actual = finiteAnimationSpecFromString<Int>(
            """
            {'${FiniteAnimationSpecFunctions.keyframes}': {} }
            """.trimIndent()
        )
        assert(actual is KeyframesSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )

        expect = keyframes {
            this.durationMillis = 1500
        }
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.keyframes}': 
              {
                '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500 
              } 
            }
            """.trimIndent()
        )
        assert(actual is KeyframesSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )

        expect = keyframes {
            this.durationMillis = 1500
            this.delayMillis = 2000
        }
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.keyframes}': 
              {
                '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500, 
                '${FiniteAnimationSpecFunctions.argDelayMillis}': 2000
              } 
            }
            """.trimIndent()
        )
        assert(actual is KeyframesSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )
    }

    @OptIn(ExperimentalAnimationSpecApi::class)
    @Test
    fun finiteAnimationSpecFromStringKeyframesWithSplineTest() {
        var expect = keyframesWithSpline<Int> { }
        var actual = finiteAnimationSpecFromString<Int>(
            """
            {'${FiniteAnimationSpecFunctions.keyframesWithSpline}': {} }
            """.trimIndent()
        )
        assert(actual is KeyframesWithSplineSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesWithSplineSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )

        expect = keyframesWithSpline {
            this.durationMillis = 1500
        }
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.keyframesWithSpline}': 
              {
                '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500 
              } 
            }
            """.trimIndent()
        )
        assert(actual is KeyframesWithSplineSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesWithSplineSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )

        expect = keyframesWithSpline {
            this.durationMillis = 1500
            this.delayMillis = 2000
        }
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.keyframesWithSpline}': 
              {
                '${FiniteAnimationSpecFunctions.argDurationMillis}': 1500, 
                '${FiniteAnimationSpecFunctions.argDelayMillis}': 2000
              } 
            }
            """.trimIndent()
        )
        assert(actual is KeyframesWithSplineSpec)
        assertEquals(
            expect.config.durationMillis,
            (actual as KeyframesWithSplineSpec).config.durationMillis
        )
        assertEquals(
            expect.config.delayMillis,
            actual.config.delayMillis
        )
    }

    @Test
    fun finiteAnimationSpecFromStringRepeatableTest() {
        var expect = repeatable<Int>(10, tween())
        var actual = finiteAnimationSpecFromString<Int>(
            """
            {'${FiniteAnimationSpecFunctions.repeatable}': {
                '${FiniteAnimationSpecFunctions.argIterations}': 10,
                '${FiniteAnimationSpecFunctions.argAnimation}': {
                  '${FiniteAnimationSpecFunctions.tween}': {}
                }
            }}
            """.trimIndent()
        )
        assertEquals(expect, actual)

        expect = repeatable(10, tween(), RepeatMode.Reverse)
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.repeatable}': {
                '${FiniteAnimationSpecFunctions.argIterations}': 10,
                '${FiniteAnimationSpecFunctions.argAnimation}': {
                  '${FiniteAnimationSpecFunctions.tween}': {}
                },
                '${FiniteAnimationSpecFunctions.argRepeatMode}': '${RepeatModeValues.reverse}'
            }}
            """.trimIndent()
        )
        assertEquals(expect, actual)

        expect = repeatable(10, tween(), RepeatMode.Reverse, StartOffset(200))
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.repeatable}': {
                '${FiniteAnimationSpecFunctions.argIterations}': 10,
                '${FiniteAnimationSpecFunctions.argAnimation}': {
                  '${FiniteAnimationSpecFunctions.tween}': {}
                },
                '${FiniteAnimationSpecFunctions.argRepeatMode}': '${RepeatModeValues.reverse}',
                '${FiniteAnimationSpecFunctions.argInitialStartOffset}': {
                  '${attrOffsetMillis}': 200
                }
            }}
            """.trimIndent()
        )
        assertEquals(expect, actual)
    }

    @Test
    fun finiteAnimationSpecFromStringSnapTest() {
        var expect = snap<Int>()
        var actual = finiteAnimationSpecFromString<Int>(
            """
            {'${FiniteAnimationSpecFunctions.snap}': {} }
            """.trimIndent()
        )
        assertEquals(expect, actual)

        expect = snap(250)
        actual = finiteAnimationSpecFromString(
            """
            {'${FiniteAnimationSpecFunctions.snap}': {
              '${FiniteAnimationSpecFunctions.argDelayMillis}': 250
            } }
            """.trimIndent()
        )
        assertEquals(expect, actual)
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
    fun lineHeightStyleAlignmentFromStringTest() {
        assertEquals(
            lineHeightStyleAlignmentFromString(LineHeightStyleAlignmentValues.top),
            LineHeightStyle.Alignment.Top
        )
        assertEquals(
            lineHeightStyleAlignmentFromString(LineHeightStyleAlignmentValues.bottom),
            LineHeightStyle.Alignment.Bottom
        )
        assertEquals(
            lineHeightStyleAlignmentFromString(LineHeightStyleAlignmentValues.proportional),
            LineHeightStyle.Alignment.Proportional
        )
        assertEquals(
            lineHeightStyleAlignmentFromString(LineHeightStyleAlignmentValues.center),
            LineHeightStyle.Alignment.Center
        )
    }

    @Test
    fun lineHeightStyleTrimFromStringTest() {
        assertEquals(
            lineHeightStyleTrimFromString(LineHeightStyleTrimValues.both),
            LineHeightStyle.Trim.Both
        )
        assertEquals(
            lineHeightStyleTrimFromString(LineHeightStyleTrimValues.none),
            LineHeightStyle.Trim.None
        )
        assertEquals(
            lineHeightStyleTrimFromString(LineHeightStyleTrimValues.lastLineBottom),
            LineHeightStyle.Trim.LastLineBottom
        )
        assertEquals(
            lineHeightStyleTrimFromString(LineHeightStyleTrimValues.firstLineTop),
            LineHeightStyle.Trim.FirstLineTop
        )
    }

    @Test
    fun lineHeightStyleFromMapTest() {
        assertEquals(
            lineHeightStyleFromMap(
                mapOf(
                    attrAlignment to LineHeightStyleAlignmentValues.proportional,
                    attrTrim to LineHeightStyleTrimValues.both
                )
            ),
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.Both
            )
        )
    }

    @Test
    fun paragraphStyleFromStringTest() {
        assertEquals(
            paragraphStyleFromString("{'$attrTextAlign': '$justify'}"),
            ParagraphStyle(textAlign = TextAlign.Justify)
        )
        assertEquals(
            paragraphStyleFromString(
                """{
                |'$attrTextAlign': '$justify', 
                |'$attrTextDirection': '$rtl'
                |}""".trimMargin()
            ),
            ParagraphStyle(textAlign = TextAlign.Justify, textDirection = TextDirection.Rtl)
        )
        assertEquals(
            paragraphStyleFromString(
                """{
                |'$attrTextAlign': '$justify', 
                |'$attrTextDirection': '$rtl',
                |'$attrLineHeight': '18'
                |}""".trimMargin()
            ),
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textDirection = TextDirection.Rtl,
                lineHeight = 18.sp
            )
        )
        assertEquals(
            paragraphStyleFromString(
                """{
                |'$attrTextAlign': '$justify', 
                |'$attrTextDirection': '$rtl',
                |'$attrLineHeight': '18',
                |'$attrTextIndent': {'$attrFirstLine': 24, '$attrRestLine': 12},
                |'$attrLineHeightStyle': {
                |   '$attrAlignment': '$proportional', 
                |   '$attrTrim': '$both'
                |},
                |'$attrLineBreak': '$heading',
                |'$attrHyphens': '$auto',
                |'$attrTextMotion': '$animated'
                |}""".trimMargin()
            ),
            ParagraphStyle(
                textAlign = TextAlign.Justify,
                textDirection = TextDirection.Rtl,
                lineHeight = 18.sp,
                textIndent = TextIndent(firstLine = 24.sp, restLine = 12.sp),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Proportional,
                    trim = LineHeightStyle.Trim.Both
                ),
                lineBreak = LineBreak.Heading,
                hyphens = Hyphens.Auto,
                textMotion = TextMotion.Animated
            )
        )
    }

    @Test
    fun repeatModeFromStringTest() {
        assertEquals(
            RepeatMode.Reverse,
            repeatModeFromString(RepeatModeValues.reverse)
        )
        assertEquals(
            RepeatMode.Restart,
            repeatModeFromString(RepeatModeValues.restart)
        )
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
    fun spanStyleFromStringTest() {
        assertEquals(
            spanStyleFromString(""),
            SpanStyle()
        )
        assertEquals(
            spanStyleFromString("{'$attrColor': '$Red'}"),
            SpanStyle(color = Color.Red)
        )
        assertEquals(
            spanStyleFromString("{'$attrColor': '$Red', '$attrFontSize': 18}"),
            SpanStyle(color = Color.Red, fontSize = 18.sp)
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold'
                |}""".trimMargin()
            ),
            SpanStyle(color = Color.Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style
            )
        )
        val fontName = "Lobster Two"
        val fontFamily = fontFamilyFromString(fontName)
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24,
                |'$attrBaselineShift': '$superscript'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp,
                baselineShift = BaselineShift.Superscript
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24,
                |'$attrBaselineShift': '$subscript',
                |'$attrTextGeometricTransform': {'$attrScaleX': 2, '$attrSkewX': 0.5 }
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp,
                baselineShift = BaselineShift.Subscript,
                textGeometricTransform = TextGeometricTransform(scaleX = 2f, skewX = 0.5f)
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24,
                |'$attrBaselineShift': '$subscript',
                |'$attrTextGeometricTransform': {'$attrScaleX': 2, '$attrSkewX': 0.5 },
                |'$attrBackground': '$Yellow'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp,
                baselineShift = BaselineShift.Subscript,
                textGeometricTransform = TextGeometricTransform(scaleX = 2f, skewX = 0.5f),
                background = Color.Yellow
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24,
                |'$attrBaselineShift': '$subscript',
                |'$attrTextGeometricTransform': {'$attrScaleX': 2, '$attrSkewX': 0.5 },
                |'$attrBackground': '$Yellow',
                |'$attrTextDecoration': '$underline'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp,
                baselineShift = BaselineShift.Subscript,
                textGeometricTransform = TextGeometricTransform(scaleX = 2f, skewX = 0.5f),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
            )
        )
        assertEquals(
            spanStyleFromString(
                """{
                |'$attrColor': '$Red', 
                |'$attrFontSize': 18, 
                |'$attrFontWeight': '$bold', 
                |'$attrFontStyle': '$italic', 
                |'$attrFontSynthesis': '$style',
                |'$attrFontFamily': '$fontName',
                |'$attrFontFeatureSettings': 'c2sc',
                |'$attrLetterSpacing': 24,
                |'$attrBaselineShift': '$subscript',
                |'$attrTextGeometricTransform': {'$attrScaleX': 2, '$attrSkewX': 0.5 },
                |'$attrBackground': '$Yellow',
                |'$attrTextDecoration': '$underline',
                |'$attrDrawStyle': '${DrawStyleValues.fill}'
                |}""".trimMargin()
            ),
            SpanStyle(
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSynthesis = FontSynthesis.Style,
                fontFamily = fontFamily,
                fontFeatureSettings = "c2sc",
                letterSpacing = 24.sp,
                baselineShift = BaselineShift.Subscript,
                textGeometricTransform = TextGeometricTransform(scaleX = 2f, skewX = 0.5f),
                background = Color.Yellow,
                textDecoration = TextDecoration.Underline,
                drawStyle = Fill
            )
        )
    }

    @Test
    fun startOffsetFromMapTest() {
        assertEquals(
            StartOffset(1),
            startOffsetFromMap(mapOf(attrOffsetMillis to 1))
        )
        assertEquals(
            StartOffset(2, StartOffsetType.FastForward),
            startOffsetFromMap(
                mapOf(
                    attrOffsetMillis to 2,
                    attrOffsetType to StartOffsetTypeValues.fastForward
                )
            )
        )
        assertEquals(
            StartOffset(3, StartOffsetType.Delay),
            startOffsetFromMap(
                mapOf(
                    attrOffsetMillis to 3,
                    attrOffsetType to StartOffsetTypeValues.delay
                )
            )
        )
    }

    @Test
    fun startOffsetTypeFromStringTest() {
        assertEquals(
            StartOffsetType.Delay,
            startOffsetTypeFromString(StartOffsetTypeValues.delay)
        )
        assertEquals(
            StartOffsetType.FastForward,
            startOffsetTypeFromString(StartOffsetTypeValues.fastForward)
        )
    }

    @Test
    fun strokeCapFromStringTest() {
        assertEquals(strokeCapFromString(StrokeCapValues.round), StrokeCap.Round)
        assertEquals(strokeCapFromString(StrokeCapValues.square), StrokeCap.Square)
        assertEquals(strokeCapFromString(StrokeCapValues.butt), StrokeCap.Butt)
    }

    @Test
    fun strokeJoinFromStringTest() {
        assertEquals(strokeJoinFromString(StrokeJoinValues.miter), StrokeJoin.Miter)
        assertEquals(strokeJoinFromString(StrokeJoinValues.round), StrokeJoin.Round)
        assertEquals(strokeJoinFromString(StrokeJoinValues.bevel), StrokeJoin.Bevel)
    }

    @Test
    fun textGeometricTransformFromMapTest() {
        assertEquals(textGeometricTransformFromMap(emptyMap()), TextGeometricTransform())
        assertEquals(
            textGeometricTransformFromMap(mapOf(attrScaleX to 5f)),
            TextGeometricTransform(scaleX = 5f)
        )
        assertEquals(
            textGeometricTransformFromMap(mapOf(attrScaleX to 5f, attrSkewX to 10f)),
            TextGeometricTransform(scaleX = 5f, skewX = 10f)
        )
    }

    @Test
    fun textIndentFromMapTest() {
        assertEquals(textIndentFromMap(emptyMap()), TextIndent())
        assertEquals(
            textIndentFromMap(mapOf(attrFirstLine to 10)),
            TextIndent(firstLine = 10.sp)
        )
        assertEquals(
            textIndentFromMap(mapOf(attrFirstLine to 10, attrRestLine to 20)),
            TextIndent(firstLine = 10.sp, restLine = 20.sp)
        )
    }

    @Test
    fun textMotionFromStringTest() {
        assertEquals(textMotionFromString(TextMotionValues.animated), TextMotion.Animated)
        assertEquals(textMotionFromString(TextMotionValues.static), TextMotion.Static)
    }

    @Test
    fun tileModeFromStringTest() {
        assertEquals(TileMode.Clamp, tileModeFromString(TileModeValues.clamp, TileMode.Clamp))
        assertEquals(TileMode.Decal, tileModeFromString(TileModeValues.decal, TileMode.Clamp))
        assertEquals(TileMode.Mirror, tileModeFromString(TileModeValues.mirror, TileMode.Clamp))
        assertEquals(TileMode.Repeated, tileModeFromString(TileModeValues.repeated, TileMode.Clamp))
    }

    @Test
    fun transformOriginFromStringTest() {
        assertEquals(transformOriginFromString("Center"), TransformOrigin.Center)
        assertEquals(
            transformOriginFromString("{'$attrPivotFractionX': 10, '$attrPivotFractionY': 20}"),
            TransformOrigin(pivotFractionX = 10f, pivotFractionY = 20f)
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
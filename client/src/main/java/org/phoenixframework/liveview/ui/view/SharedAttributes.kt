package org.phoenixframework.liveview.ui.view

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.DurationBasedAnimationSpec
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
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.VisibilityThreshold
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
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.phoenixframework.liveview.constants.AlignmentValues
import org.phoenixframework.liveview.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.constants.Attrs.attrBackground
import org.phoenixframework.liveview.constants.Attrs.attrBaselineShift
import org.phoenixframework.liveview.constants.Attrs.attrBottom
import org.phoenixframework.liveview.constants.Attrs.attrCap
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrDrawStyle
import org.phoenixframework.liveview.constants.Attrs.attrFirstLine
import org.phoenixframework.liveview.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.constants.Attrs.attrFontFeatureSettings
import org.phoenixframework.liveview.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.constants.Attrs.attrFontSynthesis
import org.phoenixframework.liveview.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.constants.Attrs.attrHeight
import org.phoenixframework.liveview.constants.Attrs.attrHyphens
import org.phoenixframework.liveview.constants.Attrs.attrJoin
import org.phoenixframework.liveview.constants.Attrs.attrLeft
import org.phoenixframework.liveview.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.constants.Attrs.attrLineBreak
import org.phoenixframework.liveview.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.constants.Attrs.attrLineHeightStyle
import org.phoenixframework.liveview.constants.Attrs.attrMiter
import org.phoenixframework.liveview.constants.Attrs.attrOffsetMillis
import org.phoenixframework.liveview.constants.Attrs.attrOffsetType
import org.phoenixframework.liveview.constants.Attrs.attrPivotFractionX
import org.phoenixframework.liveview.constants.Attrs.attrPivotFractionY
import org.phoenixframework.liveview.constants.Attrs.attrRestLine
import org.phoenixframework.liveview.constants.Attrs.attrRight
import org.phoenixframework.liveview.constants.Attrs.attrScaleX
import org.phoenixframework.liveview.constants.Attrs.attrSkewX
import org.phoenixframework.liveview.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.constants.Attrs.attrTextDirection
import org.phoenixframework.liveview.constants.Attrs.attrTextGeometricTransform
import org.phoenixframework.liveview.constants.Attrs.attrTextIndent
import org.phoenixframework.liveview.constants.Attrs.attrTextMotion
import org.phoenixframework.liveview.constants.Attrs.attrTop
import org.phoenixframework.liveview.constants.Attrs.attrTrim
import org.phoenixframework.liveview.constants.Attrs.attrWidth
import org.phoenixframework.liveview.constants.ContentScaleValues
import org.phoenixframework.liveview.constants.DrawStyleValues
import org.phoenixframework.liveview.constants.EasingValues
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argAnimationSpec
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argClip
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argExpandFrom
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argHeight
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialAlpha
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialHeight
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialOffset
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialOffsetX
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialOffsetY
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialScale
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialSize
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argInitialWidth
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argShrinkTowards
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetAlpha
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetHeight
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffset
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffsetX
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetOffsetY
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetScale
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetSize
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTargetWidth
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argTransformOrigin
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argWidth
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argX
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.argY
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.expandHorizontally
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.expandIn
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.expandVertically
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.fadeIn
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.fadeOut
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.scaleIn
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.scaleOut
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.shrinkHorizontally
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.shrinkOut
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.shrinkVertically
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideIn
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideInHorizontally
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideInVertically
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideOut
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideOutHorizontally
import org.phoenixframework.liveview.constants.EnterExitTransitionFunctions.slideOutVertically
import org.phoenixframework.liveview.constants.FiniteAnimationSpecFunctions
import org.phoenixframework.liveview.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.constants.LineHeightStyleAlignmentValues
import org.phoenixframework.liveview.constants.LineHeightStyleTrimValues
import org.phoenixframework.liveview.constants.RepeatModeValues
import org.phoenixframework.liveview.constants.SecureFlagPolicyValues
import org.phoenixframework.liveview.constants.StartOffsetTypeValues
import org.phoenixframework.liveview.constants.StrokeCapValues
import org.phoenixframework.liveview.constants.StrokeJoinValues
import org.phoenixframework.liveview.constants.TextMotionValues
import org.phoenixframework.liveview.constants.TileModeValues
import org.phoenixframework.liveview.constants.TransformOriginValues
import org.phoenixframework.liveview.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.constants.VerticalArrangementValues
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.mappers.JsonParser
import org.phoenixframework.liveview.ui.theme.baselineShiftFromString
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString
import org.phoenixframework.liveview.ui.theme.fontStyleFromString
import org.phoenixframework.liveview.ui.theme.fontSynthesisFromString
import org.phoenixframework.liveview.ui.theme.fontWeightFromString
import org.phoenixframework.liveview.ui.theme.hyphensFromString
import org.phoenixframework.liveview.ui.theme.lineBreakFromString
import org.phoenixframework.liveview.ui.theme.textAlignFromString
import org.phoenixframework.liveview.ui.theme.textDecorationFromString
import org.phoenixframework.liveview.ui.theme.textDirectionFromString
import org.phoenixframework.liveview.ui.theme.textUnitFromString

/**
 * Returns an `Alignment` object from a String.
 * @param alignment string to be converted to an `Alignment`.
 * @param defaultValue default value to be used in case of [alignment] does not match with
 * any supported value.
 */
fun alignmentFromString(alignment: String, defaultValue: Alignment): Alignment =
    when (alignment) {
        AlignmentValues.topStart -> Alignment.TopStart
        AlignmentValues.topCenter -> Alignment.TopCenter
        AlignmentValues.topEnd -> Alignment.TopEnd
        AlignmentValues.centerStart -> Alignment.CenterStart
        AlignmentValues.center -> Alignment.Center
        AlignmentValues.centerEnd -> Alignment.CenterEnd
        AlignmentValues.bottomStart -> Alignment.BottomStart
        AlignmentValues.bottomCenter -> Alignment.BottomCenter
        AlignmentValues.bottomEnd -> Alignment.BottomEnd
        else -> defaultValue
    }

val borderCache = mutableMapOf<Int, BorderStroke>()
fun borderFromString(border: String): BorderStroke? {
    return try {
        val key = border.hashCode()
        if (!borderCache.containsKey(key)) {
            val map = JsonParser.parse<Map<String, Any>>(border)
            if (map != null) {
                borderCache[key] = BorderStroke(
                    (map[attrWidth].toString().toFloatOrNull() ?: 1f).dp,
                    map[attrColor].toString().toColor()
                )
            }
        }
        borderCache[key]
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private val colorsCache = mutableMapOf<Int, Map<String, String>>()
fun colorsFromString(colors: String): Map<String, String>? {
    return try {
        val key = colors.hashCode()
        if (!colorsCache.containsKey(key)) {
            val newMap: Map<String, String>? = JsonParser.parse(colors)
            if (newMap != null) {
                colorsCache[colors.hashCode()] = newMap
            }
        }
        colorsCache[key]

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Returns an `ContentScale` object from a String.
 * @param contentScale string to be converted to an `ContentScale`.
 * @param defaultValue default value to be used in case of [contentScale] does not match with
 * any supported value.
 */
fun contentScaleFromString(
    contentScale: String, defaultValue: ContentScale = ContentScale.None
): ContentScale = when (contentScale) {
    ContentScaleValues.crop -> ContentScale.Crop
    ContentScaleValues.fillBounds -> ContentScale.FillBounds
    ContentScaleValues.fillHeight -> ContentScale.FillHeight
    ContentScaleValues.fillWidth -> ContentScale.FillWidth
    ContentScaleValues.fit -> ContentScale.Fit
    ContentScaleValues.inside -> ContentScale.Inside
    ContentScaleValues.none -> ContentScale.None
    else -> defaultValue
}

fun dpSizeFromString(string: String): DpSize? {
    return try {
        JsonParser.parse<Map<String, String>>(string)?.let { map ->
            DpSize(
                height = map[attrHeight].toString().toInt().dp,
                width = map[attrWidth].toString().toInt().dp,
            )
        }
    } catch (e: Exception) {
        null
    }
}

fun drawStyleFromString(string: String): DrawStyle {
    return if (string == DrawStyleValues.fill) {
        Fill
    } else {
        try {
            val value = JsonParser.parse<Map<String, Any>>(string)
            Stroke(
                width = value?.get(attrWidth)?.toString()?.toFloat() ?: 0.0f,
                miter = value?.get(attrMiter)?.toString()?.toFloat() ?: Stroke.DefaultMiter,
                cap = value?.get(attrCap)?.toString()?.let { strokeCapFromString(it) }
                    ?: Stroke.DefaultCap,
                join = value?.get(attrJoin)?.toString()?.let { strokeJoinFromString(it) }
                    ?: Stroke.DefaultJoin,
                // TODO pathEffect = value?.get("pathEffect")?.toString()?.let { pathEffectFromString(it) }
            )
        } catch (e: Exception) {
            Fill
        }
    }
}

fun easingFromString(string: String): Easing? {
    return when (string) {
        EasingValues.ease -> Ease
        EasingValues.easeOut -> EaseOut
        EasingValues.easeIn -> EaseIn
        EasingValues.easeInOut -> EaseInOut
        EasingValues.easeInSine -> EaseInSine
        EasingValues.easeOutSine -> EaseOutSine
        EasingValues.easeInOutSine -> EaseInOutSine
        EasingValues.easeInCubic -> EaseInCubic
        EasingValues.easeOutCubic -> EaseOutCubic
        EasingValues.easeInOutCubic -> EaseInOutCubic
        EasingValues.easeInQuint -> EaseInQuint
        EasingValues.easeOutQuint -> EaseOutQuint
        EasingValues.easeInOutQuint -> EaseInOutQuint
        EasingValues.easeInCirc -> EaseInCirc
        EasingValues.easeOutCirc -> EaseOutCirc
        EasingValues.easeInOutCirc -> EaseInOutCirc
        EasingValues.easeInQuad -> EaseInQuad
        EasingValues.easeOutQuad -> EaseOutQuad
        EasingValues.easeInOutQuad -> EaseInOutQuad
        EasingValues.easeInQuart -> EaseInQuart
        EasingValues.easeOutQuart -> EaseOutQuart
        EasingValues.easeInOutQuart -> EaseInOutQuart
        EasingValues.easeInExpo -> EaseInExpo
        EasingValues.easeOutExpo -> EaseOutExpo
        EasingValues.easeInOutExpo -> EaseInOutExpo
        EasingValues.easeInBack -> EaseInBack
        EasingValues.easeOutBack -> EaseOutBack
        EasingValues.easeInOutBack -> EaseInOutBack
        EasingValues.easeInElastic -> EaseInElastic
        EasingValues.easeOutElastic -> EaseOutElastic
        EasingValues.easeInOutElastic -> EaseInOutElastic
        EasingValues.easeOutBounce -> EaseOutBounce
        EasingValues.easeInBounce -> EaseInBounce
        EasingValues.easeInOutBounce -> EaseInOutBounce
        else -> null
    }
}

fun elevationsFromString(elevations: String): Map<String, String>? {
    return try {
        JsonParser.parse(elevations)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun enterTransitionFromString(animationJson: String): EnterTransition? {

    val jsonElement = JsonParser.parse<JsonElement>(animationJson)
    val jsonArray = if (jsonElement is JsonObject) {
        JsonArray(1).apply {
            add(jsonElement)
        }
    } else if (jsonElement is JsonArray && jsonElement.size() > 0) {
        jsonElement.asJsonArray
    } else return null

    var result: EnterTransition? = null
    for (i in 0 until jsonArray.size()) {
        val currentJsonElement = jsonArray.get(i)?.asJsonObject
        // each JSON element is supposed to have just one field containing the transition params.
        // e.g: {"expandHorizontally": {"expandFrom": "Center", "clip": true, "initialWidth": 100}}
        val animationType = currentJsonElement?.entrySet()?.firstOrNull()?.key
        val animationParams = currentJsonElement?.entrySet()?.firstOrNull()?.value?.asJsonObject
        val animationSpec = animationParams?.get(argAnimationSpec)?.asJsonObject?.asString
        val transition = when (animationType) {
            expandHorizontally -> {
                val expandFrom = animationParams?.get(argExpandFrom)?.let {
                    horizontalAlignmentFromString(it.asString)
                } ?: Alignment.End
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val initialWidth = animationParams?.get(argInitialWidth)?.asInt ?: 0
                expandHorizontally(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    expandFrom = expandFrom,
                    clip = clip,
                    initialWidth = { initialWidth }
                )
            }

            expandIn -> {
                val expandFrom = animationParams?.get(argExpandFrom)?.let {
                    alignmentFromString(it.asString, Alignment.BottomEnd)
                } ?: Alignment.BottomEnd
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val initialSize = animationParams?.get(argInitialSize)?.asJsonObject?.let {
                    val width = it.get(argWidth)?.asInt ?: 0
                    val height = it.get(argHeight)?.asInt ?: 0
                    IntSize(width, height)
                } ?: IntSize.Zero
                expandIn(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    expandFrom = expandFrom,
                    clip = clip,
                    initialSize = { initialSize }
                )
            }

            expandVertically -> {
                val expandFrom = animationParams?.get(argExpandFrom)?.let {
                    verticalAlignmentFromString(it.asString)
                } ?: Alignment.Bottom
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val initialHeight = animationParams?.get(argInitialHeight)?.asInt ?: 0
                expandVertically(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    expandFrom = expandFrom,
                    clip = clip,
                    initialHeight = { initialHeight }
                )
            }

            fadeIn -> {
                val initialAlpha = animationParams?.get(argInitialAlpha)?.asFloat ?: 0f
                fadeIn(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(stiffness = Spring.StiffnessMediumLow),
                    initialAlpha = initialAlpha
                )
            }

            scaleIn -> {
                val initialScale = animationParams?.get(argInitialScale)?.asFloat ?: 0f
                val transformOrigin = animationParams?.get(argTransformOrigin)?.let {
                    transformOriginFromString(it.toString())
                } ?: TransformOrigin.Center
                scaleIn(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(stiffness = Spring.StiffnessMediumLow),
                    initialScale = initialScale,
                    transformOrigin = transformOrigin
                )
            }

            slideIn -> {
                val initialOffset = animationParams?.get(argInitialOffset)?.asJsonObject?.let {
                    val x = it.get(argX)?.asInt ?: 0
                    val y = it.get(argY)?.asInt ?: 0
                    IntOffset(x, y)
                } ?: IntOffset.Zero
                slideIn(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffset = { initialOffset }
                )
            }

            slideInHorizontally -> {
                val initialOffsetX = animationParams?.get(argInitialOffsetX)?.asInt
                slideInHorizontally(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffsetX = { initialOffsetX ?: (-it / 2) }
                )
            }

            slideInVertically -> {
                val initialOffsetY = animationParams?.get(argInitialOffsetY)?.asInt
                slideInVertically(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    initialOffsetY = { initialOffsetY ?: (-it / 2) }
                )
            }

            else -> null
        }
        if (transition != null) {
            if (result == null) {
                result = transition
            } else {
                result += transition
            }
        }
    }

    return result
}

fun exitTransitionFromString(animationJson: String): ExitTransition? {

    val jsonElement = JsonParser.parse<JsonElement>(animationJson)
    val jsonArray = if (jsonElement is JsonObject) {
        JsonArray(1).apply {
            add(jsonElement)
        }
    } else if (jsonElement is JsonArray && jsonElement.size() > 0) {
        jsonElement.asJsonArray
    } else return null

    var result: ExitTransition? = null
    for (i in 0 until jsonArray.size()) {
        val currentJsonElement = jsonArray.get(i)?.asJsonObject

        // each JSON element is supposed to have just one field containing the transition params.
        // e.g: {"expandHorizontally": {"expandFrom": "Center", "clip": true, "initialWidth": 100}}
        val animationType = currentJsonElement?.entrySet()?.firstOrNull()?.key
        val animationParams = currentJsonElement?.entrySet()?.firstOrNull()?.value?.asJsonObject
        val animationSpec = animationParams?.get(argAnimationSpec)?.asJsonObject?.asString

        val transition = when (animationType) {
            fadeOut -> {
                val targetAlpha = animationParams?.get(argTargetAlpha)?.asFloat ?: 0f
                fadeOut(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(stiffness = Spring.StiffnessMediumLow),
                    targetAlpha = targetAlpha
                )
            }

            scaleOut -> {
                val targetScale = animationParams?.get(argTargetScale)?.asFloat ?: 0f
                val transformOrigin = animationParams?.get(argTransformOrigin)?.let {
                    transformOriginFromString(it.toString())
                } ?: TransformOrigin.Center
                scaleOut(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(stiffness = Spring.StiffnessMediumLow),
                    targetScale = targetScale,
                    transformOrigin = transformOrigin
                )
            }

            slideOut -> {
                val targetOffset = animationParams?.get(argTargetOffset)?.asJsonObject?.let {
                    val x = it.get(argX)?.asInt ?: 0
                    val y = it.get(argY)?.asInt ?: 0
                    IntOffset(x, y)
                } ?: IntOffset.Zero
                slideOut(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    targetOffset = { targetOffset }
                )
            }

            slideOutHorizontally -> {
                val initialOffsetX = animationParams?.get(argTargetOffsetX)?.asInt
                slideOutHorizontally(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    targetOffsetX = { initialOffsetX ?: (-it / 2) }
                )
            }

            slideOutVertically -> {
                val targetOffsetY = animationParams?.get(argTargetOffsetY)?.asInt
                slideOutVertically(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                    targetOffsetY = { targetOffsetY ?: (-it / 2) }
                )
            }

            shrinkHorizontally -> {
                val shrinkTowards = animationParams?.get(argShrinkTowards)?.let {
                    horizontalAlignmentFromString(it.asString)
                } ?: Alignment.End
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val targetWidth = animationParams?.get(argTargetWidth)?.asInt ?: 0
                shrinkHorizontally(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    shrinkTowards = shrinkTowards,
                    clip = clip,
                    targetWidth = { targetWidth }
                )
            }


            shrinkOut -> {
                val shrinkTowards = animationParams?.get(argShrinkTowards)?.let {
                    alignmentFromString(it.asString, Alignment.BottomEnd)
                } ?: Alignment.BottomEnd
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val targetSize = animationParams?.get(argTargetSize)?.asJsonObject?.let {
                    val width = it.get(argWidth)?.asInt ?: 0
                    val height = it.get(argHeight)?.asInt ?: 0
                    IntSize(width, height)
                } ?: IntSize.Zero
                shrinkOut(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    shrinkTowards = shrinkTowards,
                    clip = clip,
                    targetSize = { targetSize }
                )
            }

            shrinkVertically -> {
                val expandFrom = animationParams?.get(argShrinkTowards)?.let {
                    verticalAlignmentFromString(it.asString)
                } ?: Alignment.Bottom
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val targetHeight = animationParams?.get(argTargetHeight)?.asInt ?: 0
                shrinkVertically(
                    animationSpec = animationSpec?.let {
                        finiteAnimationSpecFromString(it)
                    } ?: spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    ),
                    shrinkTowards = expandFrom,
                    clip = clip,
                    targetHeight = { targetHeight }
                )
            }

            else -> null
        }
        if (transition != null) {
            if (result == null) {
                result = transition
            } else {
                result += transition
            }
        }
    }

    return result
}

@OptIn(ExperimentalAnimationSpecApi::class)
private fun <T> finiteAnimationSpecFromMap(jsonMap: Map<String, Any>): FiniteAnimationSpec<T>? {
    val function = jsonMap.keys.firstOrNull() ?: return null
    val map = jsonMap[function] as? Map<String, Any> ?: return null
    val defaultDurationMillis = 300
    return when (function) {
        FiniteAnimationSpecFunctions.tween -> {
            val duration = (map[FiniteAnimationSpecFunctions.argDurationMillis] as? Number)
                ?: defaultDurationMillis
            val delay = (map[FiniteAnimationSpecFunctions.argDelayMillis] as? Number) ?: 0
            val ease =
                (map[FiniteAnimationSpecFunctions.argEasing] as? String)?.let { easingFromString(it) }
                    ?: FastOutSlowInEasing
            tween(
                durationMillis = duration.toInt(), delayMillis = delay.toInt(), easing = ease
            )
        }

        FiniteAnimationSpecFunctions.spring -> {
            val dampingRatio = (map[FiniteAnimationSpecFunctions.argDampingRatio] as? Number)
                ?: Spring.DampingRatioNoBouncy
            val stiffness =
                (map[FiniteAnimationSpecFunctions.argStiffness] as? Number)
                    ?: Spring.StiffnessMedium
            spring(
                dampingRatio = dampingRatio.toFloat(),
                stiffness = stiffness.toFloat(),
                // TODO  visibilityThreshold =
            )
        }

        FiniteAnimationSpecFunctions.keyframes -> {
            val duration = (map[FiniteAnimationSpecFunctions.argDurationMillis] as? Number)
                ?: defaultDurationMillis
            val delay = (map[FiniteAnimationSpecFunctions.argDelayMillis] as? Number) ?: 0
            keyframes {
                // TODO KeyframesSpecConfig
                this.durationMillis = duration.toInt()
                this.delayMillis = delay.toInt()
            }
        }

        FiniteAnimationSpecFunctions.keyframesWithSpline -> {
            val duration = (map[FiniteAnimationSpecFunctions.argDurationMillis] as? Number)
                ?: defaultDurationMillis
            val delay = (map[FiniteAnimationSpecFunctions.argDelayMillis] as? Number) ?: 0
            keyframesWithSpline {
                // TODO KeyframesWithSplineSpecConfig
                this.durationMillis = duration.toInt()
                this.delayMillis = delay.toInt()
            }
        }

        FiniteAnimationSpecFunctions.repeatable -> {
            val iterations =
                (map[FiniteAnimationSpecFunctions.argIterations] as? Number)
                    ?: defaultDurationMillis
            val animation = map[FiniteAnimationSpecFunctions.argAnimation]?.let {
                it as? Map<String, Any>
            }?.let {
                finiteAnimationSpecFromMap<T>(it)
            }
            val repeatMode = (map[FiniteAnimationSpecFunctions.argRepeatMode] as? String)?.let {
                repeatModeFromString(it)
            } ?: RepeatMode.Restart
            val startOffset =
                (map[FiniteAnimationSpecFunctions.argInitialStartOffset] as? Map<String, Any>)?.let {
                    startOffsetFromMap(it)
                } ?: StartOffset(0)
            if (animation is DurationBasedAnimationSpec) {
                repeatable(
                    iterations = iterations.toInt(),
                    animation = animation,
                    repeatMode = repeatMode,
                    initialStartOffset = startOffset,
                )
            } else null
        }

        FiniteAnimationSpecFunctions.snap -> {
            val delay = (map[FiniteAnimationSpecFunctions.argDelayMillis] as? Number) ?: 0
            snap(delayMillis = delay.toInt())
        }

        else -> null
    }
}

/*
FiniteAnimationSpec is the interface that all non-infinite AnimationSpecs implement, including:
TweenSpec, SpringSpec, KeyframesSpec, RepeatableSpec, SnapSpec, etc.
 */
fun <T> finiteAnimationSpecFromString(string: String): FiniteAnimationSpec<T>? {
    return JsonParser.parse<Map<String, Any>>(string)?.let {
        finiteAnimationSpecFromMap(it)
    }
}

/**
 * The horizontal alignment of the Column's children
 *
 * @param horizontalAlignment the horizontal alignment of the column's children. See the
 * supported values at [org.phoenixframework.liveview.constants.HorizontalAlignmentValues].
 */
fun horizontalAlignmentFromString(horizontalAlignment: String) =
    when (horizontalAlignment) {
        HorizontalAlignmentValues.start -> Alignment.Start
        HorizontalAlignmentValues.centerHorizontally -> Alignment.CenterHorizontally
        HorizontalAlignmentValues.end -> Alignment.End
        else -> Alignment.Start
    }

/**
 * The horizontal arrangement of the Row's children
 *
 * @param horizontalArrangement the horizontal arrangement of the column's children. See the
 * supported values at [org.phoenixframework.liveview.constants.HorizontalArrangementValues].
 * An int value is also supported, which will be used to determine the space.
 */
fun horizontalArrangementFromString(horizontalArrangement: String) =
    when (horizontalArrangement) {
        HorizontalArrangementValues.spaceEvenly -> Arrangement.SpaceEvenly
        HorizontalArrangementValues.spaceAround -> Arrangement.SpaceAround
        HorizontalArrangementValues.spaceBetween -> Arrangement.SpaceBetween
        HorizontalArrangementValues.start -> Arrangement.Start
        HorizontalArrangementValues.end -> Arrangement.End
        else -> if (horizontalArrangement.isNotEmptyAndIsDigitsOnly()) {
            Arrangement.spacedBy(horizontalArrangement.toInt().dp)
        } else {
            Arrangement.Center
        }
    }

fun lineHeightStyleAlignmentFromString(string: String): LineHeightStyle.Alignment {
    return when (string) {
        LineHeightStyleAlignmentValues.top -> LineHeightStyle.Alignment.Top
        LineHeightStyleAlignmentValues.bottom -> LineHeightStyle.Alignment.Bottom
        LineHeightStyleAlignmentValues.proportional -> LineHeightStyle.Alignment.Proportional
        LineHeightStyleAlignmentValues.center -> LineHeightStyle.Alignment.Center
        else -> LineHeightStyle.Alignment.Center
    }
}

fun lineHeightStyleTrimFromString(string: String): LineHeightStyle.Trim {
    return when (string) {
        LineHeightStyleTrimValues.both -> LineHeightStyle.Trim.Both
        LineHeightStyleTrimValues.lastLineBottom -> LineHeightStyle.Trim.LastLineBottom
        LineHeightStyleTrimValues.firstLineTop -> LineHeightStyle.Trim.FirstLineTop
        else -> LineHeightStyle.Trim.None
    }
}

fun lineHeightStyleFromMap(map: Map<String, Any>): LineHeightStyle {
    val alignment = map[attrAlignment]?.let {
        lineHeightStyleAlignmentFromString(it.toString())
    } ?: LineHeightStyle.Alignment.Center
    val trim = map[attrTrim]?.let {
        lineHeightStyleTrimFromString(it.toString())
    } ?: LineHeightStyle.Trim.None
    return LineHeightStyle(
        alignment = alignment,
        trim = trim,
    )
}

fun paragraphStyleFromString(string: String): ParagraphStyle {
    val map = try {
        JsonParser.parse<Map<String, Any>>(string)
    } catch (e: Exception) {
        emptyMap()
    }
    val textAlign = map?.get(attrTextAlign)?.let {
        textAlignFromString(it.toString())
    } ?: TextAlign.Unspecified
    val textDirection = map?.get(attrTextDirection)?.let {
        textDirectionFromString(it.toString())
    } ?: TextDirection.Unspecified
    val lineHeight = map?.get(attrLineHeight)?.let {
        textUnitFromString(it.toString())
    } ?: TextUnit.Unspecified
    val textIndent = map?.get(attrTextIndent)?.let { tiMap ->
        (tiMap as? Map<String, Any>)?.let {
            textIndentFromMap(it)
        }
    }
    val lineHeightStyle = map?.get(attrLineHeightStyle)?.let { lhsMap ->
        (lhsMap as? Map<String, Any>)?.let {
            lineHeightStyleFromMap(it)
        }
    }
    val lineBreak = map?.get(attrLineBreak)?.let {
        lineBreakFromString(it.toString())
    } ?: LineBreak.Unspecified
    val hyphens = map?.get(attrHyphens)?.let {
        hyphensFromString(it.toString())
    } ?: Hyphens.Unspecified
    val textMotion = map?.get(attrTextMotion)?.let {
        textMotionFromString(it.toString())
    }
    return ParagraphStyle(
        textAlign = textAlign,
        textDirection = textDirection,
        lineHeight = lineHeight,
        textIndent = textIndent,
        //TODO platformStyle: PlatformSpanStyle? = null,
        lineHeightStyle = lineHeightStyle,
        lineBreak = lineBreak,
        hyphens = hyphens,
        textMotion = textMotion
    )
}

fun repeatModeFromString(string: String): RepeatMode? {
    return when (string) {
        RepeatModeValues.restart -> RepeatMode.Restart
        RepeatModeValues.reverse -> RepeatMode.Reverse
        else -> null
    }
}

fun secureFlagPolicyFromString(securePolicy: String): SecureFlagPolicy {
    return when (securePolicy) {
        SecureFlagPolicyValues.secureOn -> SecureFlagPolicy.SecureOn
        SecureFlagPolicyValues.secureOff -> SecureFlagPolicy.SecureOff
        else -> SecureFlagPolicy.Inherit
    }
}

fun spanStyleFromString(string: String): SpanStyle {
    val map = try {
        JsonParser.parse<Map<String, Any>>(string)
    } catch (e: Exception) {
        emptyMap()
    }
    val color = map?.get(attrColor)?.toString()?.toColor() ?: Color.Unspecified
    val fontSize = map?.get(attrFontSize)?.let {
        textUnitFromString(it.toString())
    } ?: TextUnit.Unspecified
    val fontWeight = map?.get(attrFontWeight)?.let {
        fontWeightFromString(it.toString())
    }
    val fontStyle = map?.get(attrFontStyle)?.let {
        fontStyleFromString(it.toString())
    }
    val fontSynthesis = map?.get(attrFontSynthesis)?.let {
        fontSynthesisFromString(it.toString())
    }
    val fontFamily = map?.get(attrFontFamily)?.let {
        fontFamilyFromString(it.toString())
    }
    val fontFeatureSettings = map?.get(attrFontFeatureSettings)?.toString()
    val letterSpacing = map?.get(attrLetterSpacing)?.let {
        textUnitFromString(it.toString())
    } ?: TextUnit.Unspecified
    val baselineShift = map?.get(attrBaselineShift)?.let {
        baselineShiftFromString(it.toString())
    }
    val textGeometricTransform = map?.get(attrTextGeometricTransform)?.let { tgtMap ->
        (tgtMap as? Map<String, Any>)?.let {
            textGeometricTransformFromMap(it)
        }
    }
    //val localeList = map?.get("localeList")
    val background = map?.get(attrBackground)?.toString()?.toColor() ?: Color.Unspecified
    val textDecoration = map?.get(attrTextDecoration)?.let {
        textDecorationFromString(it.toString())
    }
    //val shadow = map?.get("shadow")
    //val platformStyle = map?.get("platformStyle")
    val drawStyle = map?.get(attrDrawStyle)?.let { dsValue ->
        drawStyleFromString(dsValue.toString())
    }
    return SpanStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSynthesis = fontSynthesis,
        fontFamily = fontFamily,
        fontFeatureSettings = fontFeatureSettings,
        letterSpacing = letterSpacing,
        baselineShift = baselineShift,
        textGeometricTransform = textGeometricTransform,
        //TODO localeList: LocaleList? = null,
        background = background,
        textDecoration = textDecoration,
        //TODO shadow: Shadow? = null,
        //TODO platformStyle: PlatformSpanStyle? = null,
        drawStyle = drawStyle
    )
}

fun startOffsetFromMap(map: Map<String, Any>): StartOffset? {
    return map[attrOffsetMillis]?.let {
        it as? Number
    }?.let { value ->
        val type = map[attrOffsetType]?.let {
            startOffsetTypeFromString(it.toString())
        } ?: StartOffsetType.Delay
        StartOffset(value.toInt(), type)
    }
}

fun startOffsetTypeFromString(string: String): StartOffsetType? {
    return when (string) {
        StartOffsetTypeValues.delay -> StartOffsetType.Delay
        StartOffsetTypeValues.fastForward -> StartOffsetType.FastForward
        else -> null
    }
}

fun strokeCapFromString(string: String): StrokeCap? {
    return when (string) {
        StrokeCapValues.round -> StrokeCap.Round
        StrokeCapValues.square -> StrokeCap.Square
        StrokeCapValues.butt -> StrokeCap.Butt
        else -> null
    }
}

fun strokeJoinFromString(string: String): StrokeJoin? {
    return when (string) {
        StrokeJoinValues.miter -> StrokeJoin.Miter
        StrokeJoinValues.round -> StrokeJoin.Round
        StrokeJoinValues.bevel -> StrokeJoin.Bevel
        else -> null
    }
}

fun textGeometricTransformFromMap(map: Map<String, Any>): TextGeometricTransform {
    return TextGeometricTransform(
        scaleX = map[attrScaleX]?.toString()?.toFloat() ?: 1f,
        skewX = map[attrSkewX]?.toString()?.toFloat() ?: 0f,
    )
}

fun textIndentFromMap(map: Map<String, Any>): TextIndent {
    return TextIndent(
        firstLine = map[attrFirstLine]?.let { textUnitFromString(it.toString()) } ?: 0.sp,
        restLine = map[attrRestLine]?.let { textUnitFromString(it.toString()) } ?: 0.sp,
    )
}

fun textMotionFromString(string: String): TextMotion {
    return when (string) {
        TextMotionValues.animated -> TextMotion.Animated
        TextMotionValues.static -> TextMotion.Static
        else -> TextMotion.Static
    }
}

/**
 * Returns an `TileMode` object from a String.
 * @param tileMode string to be converted to an `TileMode`.
 * @param defaultValue default value to be used in case of [tileMode] does not match with
 * any supported value.
 */
fun tileModeFromString(tileMode: String, defaultValue: TileMode): TileMode =
    when (tileMode) {
        TileModeValues.clamp -> TileMode.Clamp
        TileModeValues.decal -> TileMode.Decal
        TileModeValues.mirror -> TileMode.Mirror
        TileModeValues.repeated -> TileMode.Repeated
        else -> defaultValue
    }

fun transformOriginFromString(string: String): TransformOrigin {
    return if (string == TransformOriginValues.center) {
        TransformOrigin.Center
    } else {
        val map = JsonParser.parse<Map<String, Double>>(string)
        TransformOrigin(
            pivotFractionX = map?.get(attrPivotFractionX)?.toFloat() ?: 0f,
            pivotFractionY = map?.get(attrPivotFractionY)?.toFloat() ?: 0f
        )
    }
}

/**
 * The vertical alignment of the Row's children
 *
 * @param verticalAlignment the vertical alignment of the row's children. See the supported values
 * at [org.phoenixframework.liveview.constants.VerticalAlignmentValues].
 */
fun verticalAlignmentFromString(verticalAlignment: String) = when (verticalAlignment) {
    VerticalAlignmentValues.top -> Alignment.Top
    VerticalAlignmentValues.centerVertically -> Alignment.CenterVertically
    else -> Alignment.Bottom
}

/**
 * The vertical arrangement of the Column's children
 *
 * @param verticalArrangement the vertical arrangement of the column's children. See the
 * supported values at [org.phoenixframework.liveview.constants.VerticalArrangementValues].
 * An int value is also supported, which will be used to determine the space.
 */
fun verticalArrangementFromString(verticalArrangement: String) =
    when (verticalArrangement) {
        VerticalArrangementValues.top -> Arrangement.Top
        VerticalArrangementValues.spaceEvenly -> Arrangement.SpaceEvenly
        VerticalArrangementValues.spaceAround -> Arrangement.SpaceAround
        VerticalArrangementValues.spaceBetween -> Arrangement.SpaceBetween
        VerticalArrangementValues.bottom -> Arrangement.Bottom
        else -> if (verticalArrangement.isNotEmptyAndIsDigitsOnly()) {
            Arrangement.spacedBy(verticalArrangement.toInt().dp)
        } else {
            Arrangement.Center
        }
    }

/**
 * Returns a `WindowInsets` object from a String.
 * ```
 * <ModalBottomSheet windowInsets="{'bottom': '100'}" >
 * ```
 * @param insets the space, in Dp, at the each border of the window that the inset
 * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
 */
fun windowInsetsFromString(insets: String): WindowInsets {
    val map = JsonParser.parse<Map<String, Number>>(insets)
    return WindowInsets(
        left = (map?.get(attrLeft)?.toInt() ?: 0).dp,
        top = (map?.get(attrTop)?.toInt() ?: 0).dp,
        right = (map?.get(attrRight)?.toInt() ?: 0).dp,
        bottom = (map?.get(attrBottom)?.toInt() ?: 0).dp,
    )
}
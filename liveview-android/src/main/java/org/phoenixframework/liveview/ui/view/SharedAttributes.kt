package org.phoenixframework.liveview.ui.view

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrBottom
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrLeft
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionX
import org.phoenixframework.liveview.data.constants.Attrs.attrPivotFractionY
import org.phoenixframework.liveview.data.constants.Attrs.attrRight
import org.phoenixframework.liveview.data.constants.Attrs.attrTop
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ContentScaleValues
import org.phoenixframework.liveview.data.constants.EasingValues
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argClip
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argExpandFrom
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argHeight
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialAlpha
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialHeight
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffset
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffsetX
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialOffsetY
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialScale
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialSize
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argInitialWidth
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argShrinkTowards
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetAlpha
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetHeight
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffset
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffsetX
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetOffsetY
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetScale
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetSize
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTargetWidth
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argTransformOrigin
import org.phoenixframework.liveview.data.constants.EnterExitTransitionFunctions.argWidth
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
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.SecureFlagPolicyValues
import org.phoenixframework.liveview.data.constants.TileModeValues
import org.phoenixframework.liveview.data.constants.TransformOriginValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor

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

/**
 * Returns an `Alignment` object from a String.
 * @param alignment string to be converted to an `Alignment`.
 * @param defaultValue default value to be used in case of [alignment] does not match with
 * any supported value.
 */
internal fun alignmentFromString(alignment: String, defaultValue: Alignment): Alignment =
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

/**
 * Returns an `TileMode` object from a String.
 * @param tileMode string to be converted to an `TileMode`.
 * @param defaultValue default value to be used in case of [tileMode] does not match with
 * any supported value.
 */
internal fun tileModeFromString(tileMode: String, defaultValue: TileMode): TileMode =
    when (tileMode) {
        TileModeValues.clamp -> TileMode.Clamp
        TileModeValues.decal -> TileMode.Decal
        TileModeValues.mirror -> TileMode.Mirror
        TileModeValues.repeated -> TileMode.Repeated
        else -> defaultValue
    }

/**
 * Returns an `ContentScale` object from a String.
 * @param contentScale string to be converted to an `ContentScale`.
 * @param defaultValue default value to be used in case of [contentScale] does not match with
 * any supported value.
 */
internal fun contentScaleFromString(
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

/**
 * The vertical arrangement of the Column's children
 *
 * @param verticalArrangement the vertical arrangement of the column's children. See the
 * supported values at [org.phoenixframework.liveview.data.constants.VerticalArrangementValues].
 * An int value is also supported, which will be used to determine the space.
 */
internal fun verticalArrangementFromString(verticalArrangement: String) =
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
 * The horizontal alignment of the Column's children
 *
 * @param horizontalAlignment the horizontal alignment of the column's children. See the
 * supported values at [org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues].
 */
internal fun horizontalAlignmentFromString(horizontalAlignment: String) =
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
 * supported values at [org.phoenixframework.liveview.data.constants.HorizontalArrangementValues].
 * An int value is also supported, which will be used to determine the space.
 */
internal fun horizontalArrangementFromString(horizontalArrangement: String) =
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

/**
 * The vertical alignment of the Row's children
 *
 * @param verticalAlignment the vertical alignment of the row's children. See the supported values
 * at [org.phoenixframework.liveview.data.constants.VerticalAlignmentValues].
 */
internal fun verticalAlignmentFromString(verticalAlignment: String) = when (verticalAlignment) {
    VerticalAlignmentValues.top -> Alignment.Top
    VerticalAlignmentValues.centerVertically -> Alignment.CenterVertically
    else -> Alignment.Bottom
}

internal fun onClickFromString(
    pushEvent: PushEvent?,
    event: String,
    value: Any?,
    target: Int? = null
): () -> Unit = {
    if (event.isNotEmpty()) {
        pushEvent?.invoke(ComposableBuilder.EVENT_TYPE_CLICK, event, value, target)
    }
}

private val colorsCache = mutableMapOf<Int, Map<String, String>>()
internal fun colorsFromString(colors: String): Map<String, String>? {
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

internal fun elevationsFromString(elevations: String): Map<String, String>? {
    return try {
        JsonParser.parse(elevations)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

val borderCache = mutableMapOf<Int, BorderStroke>()
internal fun borderFromString(border: String): BorderStroke? {
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

internal fun secureFlagPolicyFromString(securePolicy: String): SecureFlagPolicy {
    return when (securePolicy) {
        SecureFlagPolicyValues.secureOn -> SecureFlagPolicy.SecureOn
        SecureFlagPolicyValues.secureOff -> SecureFlagPolicy.SecureOff
        else -> SecureFlagPolicy.Inherit
    }
}

internal fun enterTransitionFromString(animationJson: String): EnterTransition? {

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
        val transition = when (animationType) {
            expandHorizontally -> {
                val expandFrom = animationParams?.get(argExpandFrom)?.let {
                    horizontalAlignmentFromString(it.asString)
                } ?: Alignment.End
                val clip = animationParams?.get(argClip)?.asBoolean ?: true
                val initialWidth = animationParams?.get(argInitialWidth)?.asInt ?: 0
                expandHorizontally(
                    // TODO animationSpec: FiniteAnimationSpec
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
                    // TODO animationSpec: FiniteAnimationSpec
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
                    // TODO animationSpec: FiniteAnimationSpec
                    expandFrom = expandFrom,
                    clip = clip,
                    initialHeight = { initialHeight }
                )
            }

            fadeIn -> {
                val initialAlpha = animationParams?.get(argInitialAlpha)?.asFloat ?: 0f
                fadeIn(
                    // TODO animationSpec: FiniteAnimationSpec,
                    initialAlpha = initialAlpha
                )
            }

            scaleIn -> {
                val initialScale = animationParams?.get(argInitialScale)?.asFloat ?: 0f
                val transformOrigin = animationParams?.get(argTransformOrigin)?.let {
                    transformOriginFromString(it.toString())
                } ?: TransformOrigin.Center
                scaleIn(
                    // TODO animationSpec: FiniteAnimationSpec,
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
                    // TODO animationSpec: FiniteAnimationSpec,
                    initialOffset = { initialOffset }
                )
            }

            slideInHorizontally -> {
                val initialOffsetX = animationParams?.get(argInitialOffsetX)?.asInt
                slideInHorizontally(
                    // TODO animationSpec: FiniteAnimationSpec,
                    initialOffsetX = { initialOffsetX ?: (-it / 2) }
                )
            }

            slideInVertically -> {
                val initialOffsetY = animationParams?.get(argInitialOffsetY)?.asInt
                slideInVertically(
                    // TODO animationSpec: FiniteAnimationSpec,
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

internal fun exitTransitionFromString(animationJson: String): ExitTransition? {

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
        val transition = when (animationType) {
            fadeOut -> {
                val targetAlpha = animationParams?.get(argTargetAlpha)?.asFloat ?: 0f
                fadeOut(
                    // TODO animationSpec: FiniteAnimationSpec,
                    targetAlpha = targetAlpha
                )
            }

            scaleOut -> {
                val targetScale = animationParams?.get(argTargetScale)?.asFloat ?: 0f
                val transformOrigin = animationParams?.get(argTransformOrigin)?.let {
                    transformOriginFromString(it.toString())
                } ?: TransformOrigin.Center
                scaleOut(
                    // TODO animationSpec: FiniteAnimationSpec,
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
                    // TODO animationSpec: FiniteAnimationSpec,
                    targetOffset = { targetOffset }
                )
            }

            slideOutHorizontally -> {
                val initialOffsetX = animationParams?.get(argTargetOffsetX)?.asInt
                slideOutHorizontally(
                    // TODO animationSpec: FiniteAnimationSpec,
                    targetOffsetX = { initialOffsetX ?: (-it / 2) }
                )
            }

            slideOutVertically -> {
                val targetOffsetY = animationParams?.get(argTargetOffsetY)?.asInt
                slideOutVertically(
                    // TODO animationSpec: FiniteAnimationSpec,
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
                    // TODO animationSpec: FiniteAnimationSpec
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
                    // TODO animationSpec: FiniteAnimationSpec
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
                    // TODO animationSpec: FiniteAnimationSpec
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

internal fun transformOriginFromString(string: String): TransformOrigin {
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

internal fun easingFromString(string: String): Easing? {
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
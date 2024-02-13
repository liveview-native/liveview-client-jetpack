package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrBottom
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrLeft
import org.phoenixframework.liveview.data.constants.Attrs.attrRight
import org.phoenixframework.liveview.data.constants.Attrs.attrTop
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ContentScaleValues
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.SecureFlagPolicyValues
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.PushEvent
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
    val map = JsonParser.parse<Map<String, String>>(insets)
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
    VerticalAlignmentValues.center -> Alignment.CenterVertically
    else -> Alignment.Bottom
}

internal fun onClickFromString(
    pushEvent: PushEvent?,
    event: String,
    value: String = "",
    target: Int? = null
): () -> Unit = {
    pushEvent?.invoke(ComposableBuilder.EVENT_TYPE_CLICK, event, value, target)
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

internal fun elevationsFromString(colors: String): Map<String, String>? {
    return try {
        JsonParser.parse(colors)
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
                    (map[attrWidth].toString().toIntOrNull() ?: 1).dp,
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
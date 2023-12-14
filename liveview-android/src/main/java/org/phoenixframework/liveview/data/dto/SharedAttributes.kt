package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly

/**
 * Returns a `WindowInsets` object from a String.
 * ```
 * <ModalBottomSheet window-insets="{'bottom': '100'}" >
 * ```
 * @param insets the space, in Dp, at the each border of the window that the inset
 * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
 */
fun windowInsetsFromString(insets: String): WindowInsets {
    val map = JsonParser.parse<Map<String, String>>(insets)
    return WindowInsets(
        left = (map?.get("left")?.toInt() ?: 0).dp,
        top = (map?.get("top")?.toInt() ?: 0).dp,
        right = (map?.get("right")?.toInt() ?: 0).dp,
        bottom = (map?.get("bottom")?.toInt() ?: 0).dp,
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
        "topStart" -> Alignment.TopStart
        "topCenter" -> Alignment.TopCenter
        "topEnd" -> Alignment.TopEnd
        "centerStart" -> Alignment.CenterStart
        "center" -> Alignment.Center
        "centerEnd" -> Alignment.CenterEnd
        "bottomStart" -> Alignment.BottomStart
        "bottomCenter" -> Alignment.BottomCenter
        "bottomEnd" -> Alignment.BottomEnd
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
    "crop" -> ContentScale.Crop
    "fillBounds" -> ContentScale.FillBounds
    "fillHeight" -> ContentScale.FillHeight
    "fillWidth" -> ContentScale.FillWidth
    "fit" -> ContentScale.Fit
    "inside" -> ContentScale.Inside
    "none" -> ContentScale.None
    else -> defaultValue
}

/**
 * Returns an `Alignment.Vertical` object from a String.
 * @param alignment string to be converted to an `Alignment.Vertical`.
 * @param defaultValue default value to be used in case of [alignment] does not match with
 * any supported value.
 */
internal fun verticalAlignmentFromString(
    alignment: String, defaultValue: Alignment.Vertical = Alignment.Top
): Alignment.Vertical = when (alignment) {
    "top" -> Alignment.Top
    "center" -> Alignment.CenterVertically
    "bottom" -> Alignment.Bottom
    else -> defaultValue
}

/**
 * Returns an `Alignment.Horizontal` object from a String.
 * @param alignment string to be converted to an `Alignment.Horizontal`.
 * @param defaultValue default value to be used in case of [alignment] does not match with
 * any supported value.
 */
internal fun horizontalAlignmentFromString(
    alignment: String, defaultValue: Alignment.Horizontal = Alignment.Start
): Alignment.Horizontal = when (alignment) {
    "start" -> Alignment.Start
    "center" -> Alignment.CenterHorizontally
    "end" -> Alignment.End
    else -> defaultValue
}

/**
 * The vertical arrangement of the Column's children
 *
 * @param verticalArrangement the vertical arrangement of the column's children. The
 * supported values are: `top`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `bottom`,
 * and `center`. An int value is also supported, which will be used to determine the space.
 */
internal fun verticalArrangementFromString(verticalArrangement: String) =
    when (verticalArrangement) {
        "top" -> Arrangement.Top
        "spaceEvenly" -> Arrangement.SpaceEvenly
        "spaceAround" -> Arrangement.SpaceAround
        "spaceBetween" -> Arrangement.SpaceBetween
        "bottom" -> Arrangement.Bottom
        else -> if (verticalArrangement.isNotEmptyAndIsDigitsOnly()) {
            Arrangement.spacedBy(verticalArrangement.toInt().dp)
        } else {
            Arrangement.Center
        }
    }

/**
 * The horizontal alignment of the Column's children
 *
 * @param horizontalAlignment the horizontal alignment of the column's children. The
 * supported values are: `start`, `center`, and `end`.
 */
internal fun horizontalAlignmentFromString(horizontalAlignment: String) =
    when (horizontalAlignment) {
        "start" -> Alignment.Start
        "center" -> Alignment.CenterHorizontally
        "end" -> Alignment.End
        else -> Alignment.Start
    }

/**
 * The horizontal arrangement of the Row's children
 *
 * @param horizontalArrangement the horizontal arrangement of the column's children. The
 * supported values are: `start`, `spacedEvenly`, `spaceAround`, `spaceBetween`, `end`,
 * and `center`. An int value is also supported, which will be used to determine the space.
 */
internal fun horizontalArrangementFromString(horizontalArrangement: String) =
    when (horizontalArrangement) {
        "spaceEvenly" -> Arrangement.SpaceEvenly
        "spaceAround" -> Arrangement.SpaceAround
        "spaceBetween" -> Arrangement.SpaceBetween
        "start" -> Arrangement.Start
        "end" -> Arrangement.End
        else -> if (horizontalArrangement.isNotEmptyAndIsDigitsOnly()) {
            Arrangement.spacedBy(horizontalArrangement.toInt().dp)
        } else {
            Arrangement.Center
        }
    }

/**
 * The vertical alignment of the Row's children
 *
 * @param verticalAlignment the vertical alignment of the row's children. The
 * supported values are: `top`, `center`, and `bottom`.
 */
internal fun verticalAlignmentFromString(verticalAlignment: String) = when (verticalAlignment) {
    "top" -> Alignment.Top
    "center" -> Alignment.CenterVertically
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

internal fun colorsFromString(colors: String): Map<String, String>? {
    return try {
        JsonParser.parse(colors)
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
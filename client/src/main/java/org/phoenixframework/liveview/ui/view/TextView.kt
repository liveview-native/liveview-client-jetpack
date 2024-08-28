package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.constants.Attrs.attrMaxLines
import org.phoenixframework.liveview.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.constants.Attrs.attrOverflow
import org.phoenixframework.liveview.constants.Attrs.attrSoftWrap
import org.phoenixframework.liveview.constants.Attrs.attrText
import org.phoenixframework.liveview.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.constants.Attrs.attrTextStyle
import org.phoenixframework.liveview.constants.TextOverflowValues
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString
import org.phoenixframework.liveview.ui.theme.fontStyleFromString
import org.phoenixframework.liveview.ui.theme.fontWeightFromString
import org.phoenixframework.liveview.ui.theme.textAlignFromString
import org.phoenixframework.liveview.ui.theme.textDecorationFromString
import org.phoenixframework.liveview.ui.theme.textStyleFromString
import org.phoenixframework.liveview.ui.theme.textUnitFromString

/**
 * High level element that displays text.
 * ```
 * <Text>My Text 1</Text>
 * <Text text="My Text 2" />
 * ```
 */
internal class TextView private constructor(props: Properties) :
    ComposableView<TextView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val textValue = props.text
        val color = props.color
        val fontSize = props.fontSize
        val fontStyle = props.fontStyle
        val fontWeight = props.fontWeight
        val fontFamily = props.fontFamily
        val letterSpacing = props.letterSpacing
        val textDecoration = props.textDecoration
        val textAlign = props.textAlign
        val lineHeight = props.lineHeight
        val overflow = props.overflow
        val softWrap = props.softWrap
        val maxLines = props.maxLines
        val minLines = props.minLines
        val style = props.style

        val text = remember(composableNode) {
            getText(composableNode) ?: textValue
        }
        Text(
            text = text,
            color = color,
            modifier = props.commonProps.modifier,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            textDecoration = textDecoration,
            style = textStyleFromString(style),
        )
    }

    private fun getText(composableNode: ComposableTreeNode?): String? {
        // The first (and only) children node of a Text element must be the text itself.
        // It is contained in a attribute called "text"
        val childrenNodes = composableNode?.children
        if (childrenNodes?.isNotEmpty() == true) {
            val firstNode = childrenNodes.first().node
            if (firstNode?.attributes?.isNotEmpty() == true) {
                val firstAttr = firstNode.attributes.first()
                if (firstAttr.name == CoreNodeElement.TEXT_ATTRIBUTE) {
                    return firstAttr.value
                }
            }
        }
        return null
    }

    @Stable
    internal data class Properties(
        val text: String = "",
        val color: Color = Color.Unspecified,
        val fontSize: TextUnit = TextUnit.Unspecified,
        val fontStyle: FontStyle? = null,
        val fontWeight: FontWeight? = null,
        val fontFamily: FontFamily? = null,
        val letterSpacing: TextUnit = TextUnit.Unspecified,
        val textDecoration: TextDecoration? = null,
        val textAlign: TextAlign? = null,
        val lineHeight: TextUnit = TextUnit.Unspecified,
        val overflow: TextOverflow = TextOverflow.Clip,
        val softWrap: Boolean = true,
        val minLines: Int = 1,
        val maxLines: Int = Int.MAX_VALUE,
        val style: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<TextView>() {
        fun buildComposableView(
            text: String,
            attributes: ImmutableList<CoreAttribute>,
            scope: Any?,
            pushEvent: PushEvent?,
        ): TextView = TextView(textBuilder(Properties(text = text), attributes, scope, pushEvent))

        /**
         * Creates a `TextView` object based on the attributes and text of the input `Attributes` object.
         * TextView co-relates to the Text composable
         *
         * @param attributes the `Attributes` object to create the `TextView` object from
         * @return a `TextView` object based on the attributes and text of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): TextView = TextView(textBuilder(Properties(), attributes, scope, pushEvent))

        private fun textBuilder(
            properties: Properties,
            attributes: ImmutableList<CoreAttribute>,
            scope: Any?,
            pushEvent: PushEvent?
        ): Properties = attributes.fold(properties) { props, attribute ->
            when (attribute.name) {
                attrColor -> color(props, attribute.value)
                attrFontFamily -> fontFamily(props, attribute.value)
                attrFontSize -> fontSize(props, attribute.value)
                attrFontStyle -> fontStyle(props, attribute.value)
                attrFontWeight -> fontWeight(props, attribute.value)
                attrLetterSpacing -> letterSpacing(props, attribute.value)
                attrLineHeight -> lineHeight(props, attribute.value)
                attrMaxLines -> maxLines(props, attribute.value)
                attrMinLines -> minLines(props, attribute.value)
                attrOverflow -> overflow(props, attribute.value)
                attrSoftWrap -> softWrap(props, attribute.value)
                attrText -> text(props, attribute.value)
                attrTextAlign -> textAlign(props, attribute.value)
                attrTextDecoration -> textDecoration(props, attribute.value)
                // FIXME style attribute is used for modifiers, so I renamed to textStyle
                attrTextStyle -> style(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        }

        /**
         * Sets the text to be displayed. There are two ways to set the text:
         * ```
         * <Text text="Your text" />
         * // or
         * <Text>Your text</Text>
         * ```
         * @param text text to be displayed.
         */
        private fun text(props: Properties, text: String): Properties {
            return props.copy(text = text)
        }

        /**
         * Sets the text color for a given text.
         *
         * @param color The color to be applied to the text. The color must be specified as a string
         *   in the AARRGGBB format or one of
         *   [org.phoenixframework.liveview.constants.SystemColorValues]. If an empty string or
         *   any other value is provided, the color will not be changed.
         */
        private fun color(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(color = color.toColor())
            } else props
        }

        /**
         * Sets the font family for a given text. This font must be a downloadable font from
         * fonts.google.com
         * ```
         * <Text fontFamily="" />
         * ```
         * @param fontFamily The font family to be applied. The font family is the font name.
         */
        private fun fontFamily(props: Properties, fontFamily: String): Properties {
            return if (fontFamily.isNotEmpty()) {
                props.copy(fontFamily = fontFamilyFromString(fontFamily))
            } else props
        }

        /**
         * Sets the font size for a given text.
         *
         * @param fontSize The font size to be applied, in sp. The font size must be a positive
         *   integer. If an empty string is provided, the font size will not be changed.
         */
        private fun fontSize(props: Properties, fontSize: String): Properties {
            return if (fontSize.isNotEmptyAndIsDigitsOnly()) {
                props.copy(fontSize = textUnitFromString(fontSize))
            } else props
        }

        /**
         * Sets the font style for a given text.
         *
         * @param fontStyle The font style to be applied. Accepted values are "normal" for
         *   NormalText and "italic" for ItalicText. If an empty string or any other value is
         *   provided, the font style will be set to Italic.
         */
        private fun fontStyle(props: Properties, fontStyle: String): Properties {
            return if (fontStyle.isNotEmpty()) {
                props.copy(fontStyle = fontStyleFromString(fontStyle))
            } else props
        }

        /**
         * Sets the font weight for a given text.
         *
         * @param fontWeight The font weight to be applied. Accepted values are "W100" (or "thin"),
         *   "W200" (or "extraLight"), "W300" (or "light"), "W400" (or "normal"), "W500" (or
         *   "medium"), "W600" (or "semiBold"), "W700" (or "bold"), "W800" (or "extraBold"), and
         *   "W900" (or "black"). If an empty string or any other value is provided, the font weight
         *   will be set to Normal.
         */
        private fun fontWeight(props: Properties, fontWeight: String): Properties {
            return if (fontWeight.isNotEmpty()) {
                props.copy(fontWeight = fontWeightFromString(fontWeight))
            } else props
        }

        /**
         * Sets the letter spacing for a given text.
         *
         * @param letterSpacing The letter spacing to be applied, in sp. The letter spacing must be
         *   a positive integer. If an empty string or any other value is provided, the letter
         *   spacing will not be changed.
         */
        private fun letterSpacing(props: Properties, letterSpacing: String): Properties {
            return if (letterSpacing.isNotEmptyAndIsDigitsOnly()) {
                props.copy(letterSpacing = textUnitFromString(letterSpacing))
            } else props
        }

        /**
         * Sets the text decoration of a text element.
         *
         * @param textDecoration the text decoration to set. Valid values are "underline" and
         *   "lineThrough". If an invalid value is provided, the default value is "none".
         */
        private fun textDecoration(props: Properties, textDecoration: String): Properties {
            return if (textDecoration.isNotEmpty()) {
                props.copy(textDecoration = textDecorationFromString(textDecoration))
            } else props
        }

        /**
         * Sets the text alignment of a text element.
         *
         * @param textAlign the alignment to set. Valid values are "left", "right", "center",
         *   "justify", "start", and "end". If an invalid value is provided, the default alignment
         *   is "start".
         */
        private fun textAlign(props: Properties, textAlign: String): Properties {
            return if (textAlign.isNotEmpty()) {
                props.copy(textAlign = textAlignFromString(textAlign))
            } else props
        }

        /**
         * Sets the line height for a given text.
         *
         * @param lineHeight The line height to be applied, in sp. The line height must be a
         *   positive integer. If an empty string or any other value is provided, the line height
         *   will not be changed.
         */
        private fun lineHeight(props: Properties, lineHeight: String): Properties {
            return if (lineHeight.isNotEmptyAndIsDigitsOnly()) {
                props.copy(lineHeight = textUnitFromString(lineHeight))
            } else props
        }

        /**
         * Sets the overflow behavior of a text element.
         *
         * @param overflow the overflow behavior to set. Valid values are "clip" and "ellipsis". If
         *   an invalid value is provided, the default behavior is "visible".
         */
        private fun overflow(props: Properties, overflow: String): Properties {
            return if (overflow.isNotEmpty()) {
                props.copy(
                    overflow = when (overflow) {
                        TextOverflowValues.clip -> TextOverflow.Clip
                        TextOverflowValues.ellipsis -> TextOverflow.Ellipsis
                        else -> TextOverflow.Visible
                    }
                )
            } else props
        }

        /**
         * Sets the soft wrap behavior of a text element.
         *
         * @param softWrap the soft wrap behavior to set. Valid values are "true" and "false". If an
         *   invalid value is provided, the default behavior is used.
         */
        private fun softWrap(props: Properties, softWrap: String): Properties {
            return if (softWrap.isNotEmpty()) {
                props.copy(softWrap = softWrap.toBoolean())
            } else props
        }

        /**
         * Sets the maximum number of lines that a text element should display.
         *
         * @param maxLines the maximum number of lines to set. If the provided value is not a valid
         *   integer, the default value is used.
         */
        private fun maxLines(props: Properties, maxLines: String): Properties {
            return if (maxLines.isNotEmptyAndIsDigitsOnly()) {
                props.copy(maxLines = maxLines.toInt())
            } else props
        }

        /**
         * Sets the minimum number of lines that a text element should display.
         *
         * @param minLines the minimum number of lines to set. If the provided value is not a valid
         *   integer, the default value is used.
         */
        private fun minLines(props: Properties, minLines: String): Properties {
            return if (minLines.isNotEmptyAndIsDigitsOnly()) {
                props.copy(minLines = minLines.toInt())
            } else props
        }

        /**
         * Sets the text style using Material Design naming convention.
         *
         * @param style text style based on Material Design naming convention. See the supported
         * values at [org.phoenixframework.liveview.constants.ThemeTextStyleValues].
         */
        private fun style(props: Properties, style: String): Properties {
            return props.copy(style = style)
        }
    }
}
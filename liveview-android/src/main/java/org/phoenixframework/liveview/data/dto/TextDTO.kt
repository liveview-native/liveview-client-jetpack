package org.phoenixframework.liveview.data.dto

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
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFontFamily
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrFontStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrFontWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrLetterSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrLineHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxLines
import org.phoenixframework.liveview.data.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.data.constants.Attrs.attrOverflow
import org.phoenixframework.liveview.data.constants.Attrs.attrSoftWrap
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrTextDecoration
import org.phoenixframework.liveview.data.constants.TextOverflowValues
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.fontFamilyFromString
import org.phoenixframework.liveview.ui.theme.fontSizeFromString
import org.phoenixframework.liveview.ui.theme.fontStyleFromString
import org.phoenixframework.liveview.ui.theme.fontWeightFromString
import org.phoenixframework.liveview.ui.theme.letterSpacingFromString
import org.phoenixframework.liveview.ui.theme.lineHeightFromString
import org.phoenixframework.liveview.ui.theme.textAlignFromString
import org.phoenixframework.liveview.ui.theme.textDecorationFromString
import org.phoenixframework.liveview.ui.theme.textStyleFromString

/**
 * High level element that displays text.
 * ```
 * <Text>My Text 1</Text>
 * <Text text="My Text 2" />
 * ```
 */
internal class TextDTO private constructor(props: Properties) :
    ComposableView<TextDTO.Properties>(props) {

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
            getText(composableNode, textValue)
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

    private fun getText(composableNode: ComposableTreeNode?, textValue: String): String {
        // The first (and only) children node of a Text element must be the text itself.
        // It is contained in a attribute called "text"
        val childrenNodes = composableNode?.children
        var text = textValue
        if (childrenNodes?.isNotEmpty() == true) {
            val firstNode = childrenNodes.first().node
            if (firstNode?.attributes?.isNotEmpty() == true) {
                val firstAttr = firstNode.attributes.first()
                if (firstAttr.name == CoreNodeElement.TEXT_ATTRIBUTE) {
                    text = firstAttr.value
                }
            }
        }
        return text
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

    internal class Builder : ComposableBuilder() {
        private var text: String = ""
        private var color: Color = Color.Unspecified
        private var fontSize: TextUnit = TextUnit.Unspecified
        private var fontStyle: FontStyle? = null
        private var fontWeight: FontWeight? = null
        private var fontFamily: FontFamily? = null
        private var letterSpacing: TextUnit = TextUnit.Unspecified
        private var textDecoration: TextDecoration? = null
        private var textAlign: TextAlign? = null
        private var lineHeight: TextUnit = TextUnit.Unspecified
        private var overflow: TextOverflow = TextOverflow.Clip
        private var softWrap: Boolean = true
        private var minLines: Int = 1
        private var maxLines: Int = Int.MAX_VALUE
        private var style: String? = null

        /**
         * Sets the text to be displayed. There are two ways to set the text:
         * ```
         * <Text text="Your text" />
         * // or
         * <Text>Your text</Text>
         * ```
         * @param text text to be displayed.
         */
        fun text(text: String) = apply { this.text = text }

        /**
         * Sets the text color for a given text.
         *
         * @param color The color to be applied to the text. The color must be specified as a string
         *   in the AARRGGBB format or one of
         *   [org.phoenixframework.liveview.data.constants.SystemColorValues]. If an empty string or
         *   any other value is provided, the color will not be changed.
         */
        fun color(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = color.toColor()
            }
        }

        /**
         * Sets the font family for a given text. This font must be a downloadable font from
         * fonts.google.com
         * ```
         * <Text fontFamily="" />
         * ```
         * @param fontFamily The font family to be applied. The font family is the font name.
         */
        fun fontFamily(fontFamily: String) = apply {
            if (fontFamily.isNotEmpty()) {
                this.fontFamily = fontFamilyFromString(fontFamily)
            }
        }

        /**
         * Sets the font size for a given text.
         *
         * @param fontSize The font size to be applied, in sp. The font size must be a positive
         *   integer. If an empty string is provided, the font size will not be changed.
         */
        fun fontSize(fontSize: String) = apply {
            if (fontSize.isNotEmptyAndIsDigitsOnly()) {
                this.fontSize = fontSizeFromString(fontSize)
            }
        }

        /**
         * Sets the font style for a given text.
         *
         * @param fontStyle The font style to be applied. Accepted values are "normal" for
         *   NormalText and "italic" for ItalicText. If an empty string or any other value is
         *   provided, the font style will be set to Italic.
         */
        fun fontStyle(fontStyle: String) = apply {
            if (fontStyle.isNotEmpty()) {
                this.fontStyle = fontStyleFromString(fontStyle)
            }
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
        fun fontWeight(fontWeight: String) = apply {
            if (fontWeight.isNotEmpty()) {
                this.fontWeight = fontWeightFromString(fontWeight)
            }
        }

        /**
         * Sets the letter spacing for a given text.
         *
         * @param letterSpacing The letter spacing to be applied, in sp. The letter spacing must be
         *   a positive integer. If an empty string or any other value is provided, the letter
         *   spacing will not be changed.
         */
        fun letterSpacing(letterSpacing: String) = apply {
            if (letterSpacing.isNotEmptyAndIsDigitsOnly()) {
                this.letterSpacing = letterSpacingFromString(letterSpacing)
            }
        }

        /**
         * Sets the text decoration of a text element.
         *
         * @param textDecoration the text decoration to set. Valid values are "underline" and
         *   "lineThrough". If an invalid value is provided, the default value is "none".
         */
        fun textDecoration(textDecoration: String) = apply {
            if (textDecoration.isNotEmpty()) {
                this.textDecoration = textDecorationFromString(textDecoration)
            }
        }

        /**
         * Sets the text alignment of a text element.
         *
         * @param textAlign the alignment to set. Valid values are "left", "right", "center",
         *   "justify", "start", and "end". If an invalid value is provided, the default alignment
         *   is "start".
         */
        fun textAlign(textAlign: String) = apply {
            if (textAlign.isNotEmpty()) {
                this.textAlign = textAlignFromString(textAlign)
            }
        }

        /**
         * Sets the line height for a given text.
         *
         * @param lineHeight The line height to be applied, in sp. The line height must be a
         *   positive integer. If an empty string or any other value is provided, the line height
         *   will not be changed.
         */
        fun lineHeight(lineHeight: String) = apply {
            if (lineHeight.isNotEmptyAndIsDigitsOnly()) {
                this.lineHeight = lineHeightFromString(lineHeight)
            }
        }

        /**
         * Sets the overflow behavior of a text element.
         *
         * @param overflow the overflow behavior to set. Valid values are "clip" and "ellipsis". If
         *   an invalid value is provided, the default behavior is "visible".
         */
        fun overflow(overflow: String) = apply {
            if (overflow.isNotEmpty()) {
                this.overflow = when (overflow) {
                    TextOverflowValues.clip -> TextOverflow.Clip
                    TextOverflowValues.ellipsis -> TextOverflow.Ellipsis
                    else -> TextOverflow.Visible
                }
            }
        }

        /**
         * Sets the soft wrap behavior of a text element.
         *
         * @param softWrap the soft wrap behavior to set. Valid values are "true" and "false". If an
         *   invalid value is provided, the default behavior is used.
         */
        fun softWrap(softWrap: String) = apply {
            if (softWrap.isNotEmpty()) {
                this.softWrap = softWrap.toBoolean()
            }
        }

        /**
         * Sets the maximum number of lines that a text element should display.
         *
         * @param maxLines the maximum number of lines to set. If the provided value is not a valid
         *   integer, the default value is used.
         */
        fun maxLines(maxLines: String) = apply {
            if (maxLines.isNotEmptyAndIsDigitsOnly()) {
                this.maxLines = maxLines.toInt()
            }
        }

        /**
         * Sets the minimum number of lines that a text element should display.
         *
         * @param minLines the minimum number of lines to set. If the provided value is not a valid
         *   integer, the default value is used.
         */
        fun minLines(minLines: String) = apply {
            if (minLines.isNotEmptyAndIsDigitsOnly()) {
                this.minLines = minLines.toInt()
            }
        }

        /**
         * Sets the text style using Material Design naming convention.
         *
         * @param style text style based on Material Design naming convention. See the supported
         * values at [org.phoenixframework.liveview.data.constants.TextStyleValues].
         */
        fun style(style: String) = apply {
            this.style = style
        }

        fun build() = TextDTO(
            Properties(
                text,
                color,
                fontSize,
                fontStyle,
                fontWeight,
                fontFamily,
                letterSpacing,
                textDecoration,
                textAlign,
                lineHeight,
                overflow,
                softWrap,
                minLines,
                maxLines,
                style,
                commonProps,
            )
        )
    }
}

internal object TextDtoFactory : ComposableViewFactory<TextDTO>() {
    fun buildComposableView(
        text: String,
        attributes: ImmutableList<CoreAttribute>,
        scope: Any?,
        pushEvent: PushEvent?,
    ): TextDTO = textBuilder(attributes, scope, pushEvent).text(text).build()

    /**
     * Creates a `TextDTO` object based on the attributes and text of the input `Attributes` object.
     * TextDTO co-relates to the Text composable
     *
     * @param attributes the `Attributes` object to create the `TextDTO` object from
     * @return a `TextDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TextDTO = textBuilder(attributes, scope, pushEvent).build()

    private fun textBuilder(
        attributes: ImmutableList<CoreAttribute>, scope: Any?, pushEvent: PushEvent?
    ): TextDTO.Builder = attributes.fold(TextDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrColor -> builder.color(attribute.value)
            attrFontFamily -> builder.fontFamily(attribute.value)
            attrFontSize -> builder.fontSize(attribute.value)
            attrFontStyle -> builder.fontStyle(attribute.value)
            attrFontWeight -> builder.fontWeight(attribute.value)
            attrLetterSpacing -> builder.letterSpacing(attribute.value)
            attrLineHeight -> builder.lineHeight(attribute.value)
            attrMaxLines -> builder.maxLines(attribute.value)
            attrMinLines -> builder.minLines(attribute.value)
            attrOverflow -> builder.overflow(attribute.value)
            attrSoftWrap -> builder.softWrap(attribute.value)
            attrStyle -> builder.style(attribute.value)
            attrText -> builder.text(attribute.value)
            attrTextAlign -> builder.textAlign(attribute.value)
            attrTextDecoration -> builder.textDecoration(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TextDTO.Builder
    }
}
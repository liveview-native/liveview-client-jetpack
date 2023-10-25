package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
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

class TextDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val text: String = builder.text
    private val color: Color = builder.color
    private val fontSize: TextUnit = builder.fontSize
    private val fontStyle: FontStyle? = builder.fontStyle
    private val fontWeight: FontWeight? = builder.fontWeight
    private val fontFamily: FontFamily? = builder.fontFamily
    private val letterSpacing: TextUnit = builder.letterSpacing
    private val textDecoration: TextDecoration? = builder.textDecoration
    private val textAlign: TextAlign? = builder.textAlign
    private val lineHeight: TextUnit = builder.lineHeight
    private val overflow: TextOverflow = builder.overflow
    private val softWrap: Boolean = builder.softWrap
    private val maxLines: Int = builder.maxLines
    private val minLines: Int = builder.minLines
    private val style: String? = builder.style

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val text = remember(composableNode) {
            getText(composableNode)
        }
        Text(
            text = text,
            color = color,
            modifier = modifier,
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

    private fun getText(composableNode: ComposableTreeNode?): String {
        // The first (and only) children node of a Text element must be the text itself.
        // It is contained in a attribute called "text"
        val childrenNodes = composableNode?.children
        var text = text
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

    class Builder : ComposableBuilder() {
        var text: String = ""
        var color: Color = Color.Unspecified
        var fontSize: TextUnit = TextUnit.Unspecified
        var fontStyle: FontStyle? = null
        var fontWeight: FontWeight? = null
        var fontFamily: FontFamily? = null
        var letterSpacing: TextUnit = TextUnit.Unspecified
        var textDecoration: TextDecoration? = null
        var textAlign: TextAlign? = null
        var lineHeight: TextUnit = TextUnit.Unspecified
        var overflow: TextOverflow = TextOverflow.Clip
        var softWrap: Boolean = true
        var minLines: Int = 1
        var maxLines: Int = Int.MAX_VALUE
        var style: String? = null

        fun text(text: String) = apply { this.text = text }

        /**
         * Sets the text color for a given text.
         *
         * @param color The color to be applied to the text. The color must be specified as a string
         *   in the format "0xFFBB86FC", where FF, BB, 86, and FC are the red, green, blue, and
         *   alpha (transparency) components of the color in hexadecimal format. If an empty string
         *   or any other value is provided, the color will not be changed.
         */
        fun color(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = Color(java.lang.Long.decode(color))
            }
        }

        /**
         * Sets the font family for a given text.
         *
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
         * @param fontWeight The font weight to be applied. Accepted values are "W100" for Thin,
         *   "W200" for ExtraLight, "W300" for Light, "W400" for Normal (regular/plain), "W500" for
         *   Medium, "W600" for SemiBold, "W700" for Bold, "W800" for ExtraBold, and "W900" for
         *   Black. If an empty string or any other value is provided, the font weight will be set
         *   to Normal.
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
                    "clip" -> TextOverflow.Clip
                    "ellipsis" -> TextOverflow.Ellipsis
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
         * @param style text style based on Material Design naming convention (e.g.: displayLarge,
         * headlineMedium, titleSmall, bodyMedium, labelSmall, etc.).
         */
        fun style(style: String) = apply {
            this.style = style
        }

        fun build() = TextDTO(this)
    }
}

object TextDtoFactory : ComposableViewFactory<TextDTO, TextDTO.Builder>() {
    fun buildComposableView(
        text: String,
        attributes: Array<CoreAttribute>,
        scope: Any?,
        pushEvent: PushEvent?,
    ): TextDTO = textBuilder(attributes, scope, pushEvent).text(text).build()

    /**
     * Creates a `TextDTO` object based on the attributes and text of the input `Attributes` object.
     * Text co-relates to the Text composable
     *
     * @param attributes the `Attributes` object to create the `TextDTO` object from
     * @param text the text of the input `Attributes` object
     * @return a `TextDTO` object based on the attributes and text of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TextDTO = textBuilder(attributes, scope, pushEvent).build()

    private fun textBuilder(
        attributes: Array<CoreAttribute>,
        scope: Any?,
        pushEvent: PushEvent?
    ): TextDTO.Builder =
        attributes.fold(TextDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                "text" -> builder.text(attribute.value)
                "color" -> builder.color(attribute.value)
                "fontFamily" -> builder.fontFamily(attribute.value)
                "fontSize" -> builder.fontSize(attribute.value)
                "fontStyle" -> builder.fontStyle(attribute.value)
                "fontWeight" -> builder.fontWeight(attribute.value)
                "letterSpacing" -> builder.letterSpacing(attribute.value)
                "textDecoration" -> builder.textDecoration(attribute.value)
                "textAlign" -> builder.textAlign(attribute.value)
                "lineHeight" -> builder.lineHeight(attribute.value)
                "overflow" -> builder.overflow(attribute.value)
                "style" -> builder.style(attribute.value)
                "softWrap" -> builder.softWrap(attribute.value)
                "maxLines" -> builder.maxLines(attribute.value)
                "minLines" -> builder.minLines(attribute.value)
                else -> builder.processCommonAttributes(scope, attribute, pushEvent)
            } as TextDTO.Builder
        }
}
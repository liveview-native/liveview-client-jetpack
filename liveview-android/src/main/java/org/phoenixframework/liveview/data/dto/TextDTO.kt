package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class TextDTO private constructor(private val builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    var color: Color = builder.color
    var fontSize: TextUnit = builder.fontSize
    var fontStyle: FontStyle? = builder.fontStyle
    var fontWeight: FontWeight? = builder.fontWeight
    var fontFamily: FontFamily? = builder.fontFamily
    var letterSpacing: TextUnit = builder.letterSpacing
    var textDecoration: TextDecoration? = builder.textDecoration
    var textAlign: TextAlign? = builder.textAlign
    var lineHeight: TextUnit = builder.lineHeight
    var overflow: TextOverflow = builder.overflow
    var softWrap: Boolean = builder.softWrap
    var maxLines: Int = builder.maxLines

    @Composable
    fun Compose(paddingValues: PaddingValues?) {
        if (text.isEmpty() && builder.text.isNotEmpty()) {
            text = builder.text
        }
        Text(
            text = text,
            color = color,
            modifier = modifier.paddingIfNotNull(paddingValues),
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
            textDecoration = textDecoration)
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
        var maxLines: Int = Int.MAX_VALUE

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
         * Sets the font size for a given text.
         *
         * @param fontSize The font size to be applied, in sp. The font size must be a positive
         *   integer. If an empty string is provided, the font size will not be changed.
         */
        fun fontSize(fontSize: String) = apply {
            if (fontSize.isNotEmptyAndIsDigitsOnly()) {
                this.fontSize = (fontSize.toInt()).sp
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
                this.fontStyle =
                    when (fontStyle) {
                        "normal" -> FontStyle.Normal
                        "italic" -> FontStyle.Italic
                        else -> FontStyle.Normal
                    }
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
                this.fontWeight =
                    when (fontWeight) {
                        "W100" -> FontWeight(100)
                        "W200" -> FontWeight(200)
                        "W300" -> FontWeight(300)
                        "W400" -> FontWeight(400)
                        "W500" -> FontWeight(500)
                        "W600" -> FontWeight(600)
                        "W700" -> FontWeight(700)
                        "W800" -> FontWeight(800)
                        "W900" -> FontWeight(900)
                        else -> FontWeight.Normal
                    }
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
                this.letterSpacing = (letterSpacing.toInt()).sp
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
                this.textDecoration =
                    when (textDecoration) {
                        // Draws a horizontal line below the text.
                        "underline" -> TextDecoration.Underline
                        // Draws a horizontal line over the text.
                        "line-through" -> TextDecoration.LineThrough
                        else -> TextDecoration.None
                    }
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
                this.textAlign =
                    when (textAlign) {
                        "left" -> TextAlign.Left
                        "right" -> TextAlign.Right
                        "center" -> TextAlign.Center
                        "justify" -> TextAlign.Justify
                        "start" -> TextAlign.Start
                        "end" -> TextAlign.End
                        else -> TextAlign.Start
                    }
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
                this.lineHeight = (lineHeight.toInt()).sp
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
                this.overflow =
                    when (overflow) {
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

        override fun size(size: String): Builder = apply { super.size(size) }

        override fun padding(padding: String): Builder = apply { super.padding(padding) }

        override fun verticalPadding(padding: String): Builder = apply {
            super.verticalPadding(padding)
        }

        override fun horizontalPadding(padding: String): Builder = apply {
            super.horizontalPadding(padding)
        }

        override fun height(height: String): Builder = apply { super.height(height) }

        override fun width(width: String): Builder = apply { super.width(width) }

        fun build() = TextDTO(this)
    }
}

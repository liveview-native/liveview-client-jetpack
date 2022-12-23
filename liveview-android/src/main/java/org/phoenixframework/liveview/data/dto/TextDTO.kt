package org.phoenixframework.liveview.data.dto

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

class TextDTO private constructor(builder: Builder) : ComposableView() {
    // Original class fields
    var text: String
    var modifier: Modifier = Modifier
    var color: Color
    var fontSize: TextUnit
    var fontStyle: FontStyle?
    var fontWeight: FontWeight?
    var fontFamily: FontFamily?
    var letterSpacing: TextUnit
    var textDecoration: TextDecoration?
    var textAlign: TextAlign?
    var lineHeight: TextUnit
    var overflow: TextOverflow
    var softWrap: Boolean
    var maxLines: Int



    // Builder class
    class Builder : ComposableBuilder(){
        // Required parameters
        private var text: String = ""

        // Optional parameters - initialized to default values
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
        private var maxLines: Int = Int.MAX_VALUE

        // Setters for required parameters
        fun setText(text: String) = apply { this.text = text }

        // Setters for optional parameters
        /**
         *Sets the text color for a given text.
         *@param color The color to be applied to the text. The color must be specified as a string in the format "0xFFBB86FC", where
         *FF, BB, 86, and FC are the red, green, blue, and alpha (transparency) components of the color in hexadecimal format. If an
         *empty string or any other value is provided, the color will not be changed.
         */

        fun setColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.color = Color(java.lang.Long.decode(color))
            }
        }

        /**
         *Sets the font size for a given text.
         *@param fontSize The font size to be applied, in sp.
         * The font size must be a positive integer. If an empty string
         *is provided, the font size will not be changed.
         */
        fun setFontSize(fontSize: String) = apply {
            if (fontSize.isNotEmptyAndIsDigitsOnly()) {
                this.fontSize = (fontSize.toInt()).sp
            }
        }

        /**

         *Sets the font style for a given text.
         *@param fontStyle The font style to be applied. Accepted values are "normal" for NormalText and "italic" for ItalicText. If an
         *empty string or any other value is provided, the font style will be set to Italic.
         **/
        fun setFontStyle(fontStyle: String) = apply {
            if (fontStyle.isNotEmpty()) {
                this.fontStyle = when (fontStyle) {

                    "normal" -> {
                        FontStyle.Normal
                    }
                    else -> {
                        FontStyle.Italic
                    }

                }
            }
        }

        /**
         *Sets the font weight for a given text.
         *@param fontWeight The font weight to be applied. Accepted values are "W100" for Thin, "W200" for ExtraLight, "W300" for Light,
         *"W400" for Normal (regular/plain), "W500" for Medium, "W600" for SemiBold, "W700" for Bold, "W800" for ExtraBold, and "W900"
         *for Black. If an empty string or any other value is provided, the font weight will be set to Normal.
         **/

        fun setFontWeight(fontWeight: String) = apply {
            if (fontWeight.isNotEmpty()) {
                this.fontWeight = when (fontWeight) {


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

        fun setFontFamily(fontFamily: FontFamily) = apply { this.fontFamily = fontFamily }

        /**
         *Sets the letter spacing for a given text.
         *@param letterSpacing The letter spacing to be applied, in sp. The letter spacing must be a positive integer. If an empty
         *string or any other value is provided, the letter spacing will not be changed.
         */
        fun setLetterSpacing(letterSpacing: String) = apply {
            if (letterSpacing.isNotEmptyAndIsDigitsOnly()) {
                this.letterSpacing = (letterSpacing.toInt()).sp
            }
        }

        /**
         * Sets the text decoration of a text element.
         * @param textDecoration the text decoration to set. Valid values are "underline" and "lineThrough".
         * If an invalid value is provided, the default value is "none".
         */
        fun setTextDecoration(textDecoration: String) = apply {
            if (textDecoration.isNotEmpty()) {
                this.textDecoration = when (textDecoration) {

                    // Draws a horizontal line below the text.
                    "underline" -> TextDecoration.Underline
                    // Draws a horizontal line over the text.
                    "line-through" -> TextDecoration.LineThrough

                    else -> {
                        TextDecoration.None
                    }

                }
            }
        }

        /**
         * Sets the text alignment of a text element.
         * @param textAlign the alignment to set. Valid values are "left", "right", "center", "justify", "start", and "end".
         *If an invalid value is provided, the default alignment is "start".
         */
        fun setTextAlign(textAlign: String) = apply {
            if (textAlign.isNotEmpty()) {
                this.textAlign = when (textAlign) {
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
         *Sets the line height for a given text.
         *@param lineHeight The line height to be applied, in sp. The line height must be a positive integer. If an empty string or any
         *other value is provided, the line height will not be changed.
         */

        fun setLineHeight(lineHeight: String) = apply {
            if (lineHeight.isNotEmptyAndIsDigitsOnly()) {

                this.lineHeight = (lineHeight.toInt()).sp

            }
        }

        /**
         *
         * Sets the overflow behavior of a text element.
         * @param overflow the overflow behavior to set. Valid values are "clip" and "ellipsis".
         * If an invalid value is provided, the default behavior is "visible".
         */
        fun setOverflow(overflow: String) = apply {
            if (overflow.isNotEmpty()) {
                this.overflow = when (overflow) {
                    "clip" -> TextOverflow.Clip
                    "ellipsis" -> TextOverflow.Ellipsis
                    else -> TextOverflow.Visible

                }
            }
        }

        /**
         *Sets the soft wrap behavior of a text element.
         *@param softWrap the soft wrap behavior to set. Valid values are "true" and "false".
         *If an invalid value is provided, the default behavior is used.
         */

        fun setSoftWrap(softWrap: String) = apply {
            if (softWrap.isNotEmpty()) {
                this.softWrap = softWrap.toBoolean()
            }
        }

        /**
         *Sets the maximum number of lines that a text element should display.
         *@param maxLines the maximum number of lines to set. If the provided value is not a valid integer, the default value is used.
         */
        fun setMaxLines(maxLines: String) = apply {
            if (maxLines.isNotEmptyAndIsDigitsOnly()) {
                this.maxLines = maxLines.toInt()
            }
        }

        // Getters for required parameters
        fun getText() = this.text


        // Getters for optional parameters
        fun getColor() = this.color
        fun getFontSize() = this.fontSize
        fun getFontStyle() = this.fontStyle
        fun getFontWeight() = this.fontWeight
        fun getFontFamily() = this.fontFamily
        fun getLetterSpacing() = this.letterSpacing
        fun getTextDecoration() = this.textDecoration
        fun getTextAlign() = this.textAlign
        fun getLineHeight() = this.lineHeight
        fun getOverflow() = this.overflow
        fun getSoftWrap() = this.softWrap
        fun getMaxLines() = this.maxLines


        // Build method to create the TextDTO object
        fun build() = TextDTO(this)
    }

    init {
        this.modifier = builder.getModifier()
        this.text = builder.getText()
        this.color = builder.getColor()
        this.fontSize = builder.getFontSize()
        this.fontStyle = builder.getFontStyle()
        this.fontWeight = builder.getFontWeight()
        this.fontFamily = builder.getFontFamily()
        this.letterSpacing = builder.getLetterSpacing()
        this.textDecoration = builder.getTextDecoration()
        this.textAlign = builder.getTextAlign()
        this.lineHeight = builder.getLineHeight()
        this.overflow = builder.getOverflow()
        this.softWrap = builder.getSoftWrap()
        this.maxLines = builder.getMaxLines()

    }
}

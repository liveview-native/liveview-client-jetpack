package org.phoenixframework.liveview.domain.factory

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.phoenixframework.liveview.data.dto.TextDTO
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable views based on
 * the type of an input `Element` object.
 * The `ComposableTreeNode` objects are created by calling different functions depending on the tag name of the input
 * `Element` object.
 */
object ComposableNodeFactory {
    /**
     * Creates a `ComposableTreeNode` object based on the input `Element` object.
     * @param element the `Element` object to create the `ComposableTreeNode` object from
     * @return a `ComposableTreeNode` object based on the input `Element` object
     */
    fun buildComposable(element: Element): ComposableTreeNode {
        return when (element.tagName()) {
            ComposableTypes.text -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )
            }


            else -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )

            }
        }
    }




    /**
     * Creates a `TextDTO` object based on the attributes and text of the input `Attributes` object.
     * Text co-relates to the Text composable
     * @param attributes the `Attributes` object to create the `TextDTO` object from
     * @param text the text of the input `Attributes` object
     * @return a `TextDTO` object based on the attributes and text of the input `Attributes` object
     */

    private fun buildTextNode(attributes: Attributes, text: String): ComposableView {

        val textNode = TextDTO.Builder().setText(text)

        attributes.forEach { attribute ->
            when (attribute.key) {
                "color" -> textNode.setColor(attribute.value)
                "font-size" -> textNode.setFontSize(attribute.value)
                "font-style" -> textNode.setFontStyle(attribute.value)
                "font-weight" -> textNode.setFontWeight(attribute.value)
                /*  "font-family"-> textNode.setFontFamily(attribute.value)*/
                "letter-spacing" -> textNode.setLetterSpacing(attribute.value)
                "text-decoration" -> textNode.setTextDecoration(attribute.value)
                "text-align" -> textNode.setTextAlign(attribute.value)
                "line-height" -> textNode.setLineHeight(attribute.value)
                "overflow" -> textNode.setOverflow(attribute.value)
                "soft-wrap" -> textNode.setSoftWrap(attribute.value)
                "max-lines" -> textNode.setMaxLines(attribute.value)
                "size" -> textNode.setSize(attribute.value)
                "height" -> textNode.setHeight(attribute.value)
                "width" -> textNode.setWidth(attribute.value)
                "padding" -> textNode.setPadding(attribute.value)
                "horizontal-padding" -> textNode.setHorizontalPadding(attribute.value)
                "vertical-padding" -> textNode.setVerticalPadding(attribute.value)

            }
        }

        return textNode.build()

    }


}
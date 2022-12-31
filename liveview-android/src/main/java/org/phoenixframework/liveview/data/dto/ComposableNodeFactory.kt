package org.phoenixframework.liveview.data.dto

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element

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
            ComposableTypes.column -> {
                ComposableTreeNode(buildColumnNode(element.attributes()))
            }
            ComposableTypes.row -> {
                ComposableTreeNode(buildRowNode(element.attributes()))

            }
            ComposableTypes.lazyColumn -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )
            }
            ComposableTypes.lazyRow -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )
            }
            ComposableTypes.text -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )
            }
            ComposableTypes.card -> {
                ComposableTreeNode(
                    buildCardNode(attributes = element.attributes())
                )
            }
            ComposableTypes.asyncImage -> {
                ComposableTreeNode(buildAsyncImageNode(element.attributes(), element.text()))
            }
            else -> {
                ComposableTreeNode(
                    buildTextNode(attributes = element.attributes(), text = element.text())
                )

            }
        }
    }

    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * Card co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/

    fun buildCardNode(attributes: Attributes): ComposableView {
        val cardNode = CardDTO.Builder()
        attributes.forEach { attribute ->
            when (attribute.key) {
                "shape" -> cardNode.setShape(attribute.value)
                "background-color" -> cardNode.setBackgroundColor(attribute.value)
                "elevation" -> cardNode.setCardElevation(attribute.value)
                "size" -> cardNode.setSize(attribute.value)
                "height" -> cardNode.setHeight(attribute.value)
                "width" -> cardNode.setWidth(attribute.value)
            }
        }
        return cardNode.build()
    }

    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object.
     * AsyncImage co-relates to the AsyncImage composable from Coil library used to load images from network
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @param url is the url of image that is to be loaded
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object
     */

    private fun buildAsyncImageNode(attributes: Attributes, url: String): ComposableView {
        val asyncImageNode = AsyncImageDTO.Builder().setImageUrl(url)
        attributes.forEach { attribute ->
            when (attribute.key) {
                "content-scale" -> asyncImageNode.setContentScale(attribute.key)
                "content-description" -> asyncImageNode.setContentDescription(attribute.key)
                "cross-fade" -> asyncImageNode.setCrossFade(attribute.key)
                "shape" -> asyncImageNode.setShape(attribute.key)
                "size" -> asyncImageNode.setSize(attribute.value)
                "height" -> asyncImageNode.setHeight(attribute.value)
                "width" -> asyncImageNode.setWidth(attribute.value)
            }
        }
        return asyncImageNode.build()
    }

    /**
     * Creates a `RowDTO` object based on the attributes of the input `Attributes` object.
     * Row co-relates to the Row composable
     * @param attributes the `Attributes` object to create the `RowDTO` object from
     * @return a `RowDTO` object based on the attributes of the input `Attributes` object
     */
    private fun buildRowNode(attributes: Attributes): ComposableView {
        val rowNode = RowDTO.Builder()
        attributes.forEach { attribute ->
            when (attribute.key) {
                "horizontal-arrangement" -> {
                    rowNode.setHorizontalArrangement(horizontalArrangement = attribute.key)
                }
                "vertical-alignment" -> {
                    rowNode.setVerticalAlignment(verticalAlignment = attribute.key)
                }
                "size" -> rowNode.setSize(attribute.value)
                "height" -> rowNode.setHeight(attribute.value)
                "width" -> rowNode.setWidth(attribute.value)
            }

        }

        return rowNode.build()

    }

    /**
     * Creates a `ColumnDTO` object based on the attributes of the input `Attributes` object.
     * Column co-relates to the Column composable
     * @param attributes the `Attributes` object to create the `ColumnDTO` object from
     * @return a `ColumnDTO` object based on the attributes of the input `Attributes` object
     */
    private fun buildColumnNode(attributes: Attributes): ComposableView {
        val columnNode = ColumnDTO.Builder()
        attributes.forEach { attribute ->
            when (attribute.key) {
                "vertical-arrangement" -> {
                    columnNode.setVerticalArrangement(attribute.value)
                }
                "horizontal-alignment" -> {
                    columnNode.setHorizontalAlignment(attribute.value)
                }
                "size" -> columnNode.setSize(attribute.value)
                "height" -> columnNode.setHeight(attribute.value)
                "width" -> columnNode.setWidth(attribute.value)

            }
        }
        return columnNode.build()
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

            }
        }

        return textNode.build()

    }
}
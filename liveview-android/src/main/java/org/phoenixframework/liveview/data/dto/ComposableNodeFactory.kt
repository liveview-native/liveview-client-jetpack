package org.phoenixframework.liveview.data.dto

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
object ComposableNodeFactory {
    /**
     * Creates a `ComposableTreeNode` object based on the input `Element` object.
     *
     * @param element the `Element` object to create the `ComposableTreeNode` object from
     * @return a `ComposableTreeNode` object based on the input `Element` object
     */
    fun buildComposable(element: Element): ComposableTreeNode = when (element.tagName()) {
        ComposableTypes.asyncImage -> ComposableTreeNode(
            buildAsyncImageNode(
                element.attributes(),
                element.text()
            )
        )
        ComposableTypes.card -> ComposableTreeNode(buildCardNode(element.attributes()))
        ComposableTypes.column -> ComposableTreeNode(buildColumnNode(element.attributes()))
        ComposableTypes.lazyColumn -> ComposableTreeNode(
            buildTextNode(attributes = element.attributes(), text = element.text())
        )
        ComposableTypes.lazyRow -> ComposableTreeNode(
            buildTextNode(attributes = element.attributes(), text = element.text())
        )
        ComposableTypes.row -> ComposableTreeNode(buildRowNode(element.attributes()))
        ComposableTypes.text -> ComposableTreeNode(
            buildTextNode(attributes = element.attributes(), text = element.text())
        )
        else -> ComposableTreeNode(
            buildTextNode(attributes = element.attributes(), text = element.text())
        )
    }

    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object.
     * AsyncImage co-relates to the AsyncImage composable from Coil library used to load images from network
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @param url is the url of image that is to be loaded
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    private fun buildAsyncImageNode(attributes: Attributes, url: String): ComposableView =
        attributes
            .fold(AsyncImageDTO.Builder().imageUrl(url)) { builder, attribute ->
                when (attribute.key) {
                    "content-scale" -> builder.contentScale(attribute.key)
                    "content-description" -> builder.contentDescription(attribute.key)
                    "cross-fade" -> builder.crossFade(attribute.key)
                    "shape" -> builder.shape(attribute.key)
                    else -> builder
                }
            }
            .build()

    /**
     * Creates a `CardDTO` object based on the attributes of the input `Attributes` object.
     * Card co-relates to the Card composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `CardDTO` object based on the attributes of the input `Attributes` object
     **/
    private fun buildCardNode(attributes: Attributes): ComposableView =
        attributes
            .fold(CardDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "shape" -> builder.shape(attribute.value)
                    "background-color" -> builder.backgroundColor(attribute.value)
                    "elevation" -> builder.elevation(attribute.value)
                    else -> builder
                }
            }
            .build()

    /**
     * Creates a `ColumnDTO` object based on the attributes of the input `Attributes` object.
     * Column co-relates to the Column composable
     * @param attributes the `Attributes` object to create the `ColumnDTO` object from
     * @return a `ColumnDTO` object based on the attributes of the input `Attributes` object
     */
    private fun buildColumnNode(attributes: Attributes): ComposableView =
        attributes
            .fold(ColumnDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "vertical-arrangement" -> {
                        builder.verticalArrangement(attribute.value)
                    }
                    "horizontal-alignment" -> {
                        builder.horizontalAlignment(attribute.value)
                    }
                    else -> builder
                }
            }
            .build()

    /**
     * Creates a `RowDTO` object based on the attributes of the input `Attributes` object.
     * Row co-relates to the Row composable
     * @param attributes the `Attributes` object to create the `RowDTO` object from
     * @return a `RowDTO` object based on the attributes of the input `Attributes` object
     */
    private fun buildRowNode(attributes: Attributes): ComposableView =
        attributes
            .fold(RowDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "horizontal-arrangement" -> {
                        builder.horizontalArrangement(horizontalArrangement = attribute.key)
                    }
                    "vertical-alignment" -> {
                        builder.verticalAlignment(verticalAlignment = attribute.key)
                    }
                    else -> builder
                }
            }
            .build()

    /**
     * Creates a `TextDTO` object based on the attributes and text of the input `Attributes` object.
     * Text co-relates to the Text composable
     * @param attributes the `Attributes` object to create the `TextDTO` object from
     * @param text the text of the input `Attributes` object
     * @return a `TextDTO` object based on the attributes and text of the input `Attributes` object
     */
    private fun buildTextNode(attributes: Attributes, text: String): ComposableView =
        attributes
            .fold(TextDTO.Builder().text(text)) { builder, attribute ->
                when (attribute.key) {
                    "color" -> builder.color(attribute.value)
                    "font-size" -> builder.fontSize(attribute.value)
                    "font-style" -> builder.fontStyle(attribute.value)
                    "font-weight" -> builder.fontWeight(attribute.value)
                    "letter-spacing" -> builder.letterSpacing(attribute.value)
                    "text-decoration" -> builder.textDecoration(attribute.value)
                    "text-align" -> builder.textAlign(attribute.value)
                    "line-height" -> builder.lineHeight(attribute.value)
                    "overflow" -> builder.overflow(attribute.value)
                    "soft-wrap" -> builder.softWrap(attribute.value)
                    "max-lines" -> builder.maxLines(attribute.value)
                    else -> builder
                }
            }
            .build()
}
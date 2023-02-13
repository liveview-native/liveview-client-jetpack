package org.phoenixframework.liveview.domain.factory

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.phoenixframework.liveview.data.dto.AsyncImageDTO
import org.phoenixframework.liveview.data.dto.TextDTO
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView

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
        ComposableTypes.text -> {
            ComposableTreeNode(
                buildTextNode(attributes = element.attributes(), text = element.text())
            )
        }
        ComposableTypes.asyncImage -> {
            ComposableTreeNode(buildAsyncImageNode(element.attributes()))
        }
        else -> ComposableTreeNode(
            buildTextNode(
                attributes = element.attributes(),
                text = "${element.tagName()} not supported yet"
            )
        )
    }

    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object.
     * AsyncImage co-relates to the AsyncImage composable from Coil library used to load images from network
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @param url is the url of image that is to be loaded
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    private fun buildAsyncImageNode(attributes: Attributes): ComposableView =
        attributes
            .fold(AsyncImageDTO.Builder().imageUrl(attributes.get("url"))) { builder, attribute ->
                when (attribute.key) {
                    "content-scale" -> builder.contentScale(attribute.key)
                    "content-description" -> builder.contentDescription(attribute.key)
                    "cross-fade" -> builder.crossFade(attribute.key)
                    "shape" -> builder.shape(attribute.key)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    else -> builder
                }
            }
            .build()

    /**
     * Creates a `TextDTO` object based on the attributes and text of the input `Attributes` object.
     * Text co-relates to the Text composable
     *
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
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                }
            }
            .build()
}
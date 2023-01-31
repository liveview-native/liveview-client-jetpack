package org.phoenixframework.liveview.domain.factory

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.phoenixframework.liveview.data.dto.*
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
        ComposableTypes.asyncImage -> ComposableTreeNode(buildAsyncImageNode(element.attributes()))
        ComposableTypes.card -> ComposableTreeNode(buildCardNode(element.attributes()))
        ComposableTypes.column -> ComposableTreeNode(buildColumnNode(element.attributes()))
        ComposableTypes.icon -> ComposableTreeNode(buildIconNode(element.attributes()))
        ComposableTypes.lazyColumn -> ComposableTreeNode(buildLazyColumnNode(element.attributes()))
        ComposableTypes.lazyRow -> ComposableTreeNode(buildLazyRowNode(element.attributes()))
        ComposableTypes.row -> ComposableTreeNode(buildRowNode(element.attributes()))
        ComposableTypes.scaffold -> ComposableTreeNode(buildScaffoldNode(element.attributes()))
        ComposableTypes.spacer -> ComposableTreeNode(buildSpacerNode(element.attributes()))
        ComposableTypes.text -> ComposableTreeNode(
            buildTextNode(attributes = element.attributes(), text = element.text())
        )
        ComposableTypes.topAppBar -> ComposableTreeNode(
            buildTopAppBarNode(element = element, attributes = element.attributes())
        )
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
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
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

    private fun buildIconNode(attributes: Attributes): IconDTO =
        attributes
            .fold(IconDTO.Builder().imageVector(attributes.get("name"))) { builder, attribute ->
                when (attribute.key) {
                    "tint" -> builder.tint(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "content-description" -> builder.contentDescription(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                }
            }
            .build()

    private fun buildLazyColumnNode(attributes: Attributes): ComposableView =
        attributes
            .fold(LazyColumnDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "height" -> builder.height(attribute.value)
                    "horizontal-alignment" -> builder.horizontalAlignment(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "item-bottom-padding" -> builder.bottomPadding(attribute.value)
                    "item-horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "item-left-padding" -> builder.leftPadding(attribute.value)
                    "item-padding" -> builder.lazyColumnItemPadding(attribute.value)
                    "item-right-padding" -> builder.rightPadding(attribute.value)
                    "item-top-padding" -> builder.topPadding(attribute.value)
                    "item-vertical-padding" -> builder.verticalPadding(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "reverse-layout" -> builder.reverseLayout(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "vertical-arrangement" -> builder.verticalArrangement(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                    "width" -> builder.width(attribute.value)
                    else -> builder
                }
            }
            .build()

    private fun buildLazyRowNode(attributes: Attributes): ComposableView =
        attributes
            .fold(LazyRowDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "height" -> builder.height(attribute.value)
                    "horizontal-arrangement" -> builder.horizontalArrangement(horizontalArrangement = attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "item-bottom-padding" -> builder.bottomPadding(attribute.value)
                    "item-horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "item-left-padding" -> builder.leftPadding(attribute.value)
                    "item-padding" -> builder.lazyRowItemPadding(attribute.value)
                    "item-right-padding" -> builder.rightPadding(attribute.value)
                    "item-top-padding" -> builder.topPadding(attribute.value)
                    "item-vertical-padding" -> builder.verticalPadding(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "reverse-layout" -> builder.reverseLayout(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "vertical-alignment" -> builder.verticalAlignment(verticalAlignment = attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                    "width" -> builder.width(attribute.value)
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

    private fun buildScaffoldNode(attributes: Attributes): ComposableView =
        attributes
            .fold(ScaffoldDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "background-color" -> builder.backgroundColor(attribute.value)
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

    private fun buildSpacerNode(attributes: Attributes): ComposableView =
        attributes
            .fold(SpacerDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "size" -> builder.size(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
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

    private fun buildTopAppBarNode(element: Element, attributes: Attributes): ComposableView =
        attributes
            .fold(TopAppBarDTO.Builder()) { builder, attribute ->
                when (attribute.key) {
                    "background-color" -> builder.backgroundColor(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                }
            }
            .also { builder ->
                element.select("text").first()?.let { element ->
                    builder.textDTO =
                        buildTextNode(element.attributes(), element.text()) as TextDTO
                }

                element.select("nav-icon").forEach { navIcon ->
                    buildAndAddIconNode(navIcon, builder::addNavIcon)
                }

                element.select("action-icon").forEach { actionIcon ->
                    buildAndAddIconNode(actionIcon, builder::addActionIcon)
                }
            }
            .build()

    private fun buildAndAddIconNode(element: Element, setter: (IconDTO) -> Unit) {
        setter(buildIconNode(element.attributes()))
    }
}
package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.data.dto.*
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.lib.Attribute
import org.phoenixframework.liveview.lib.Node

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
    fun buildComposableTreeNode(element: Node.Element, children: List<Node>): ComposableTreeNode =
        buildComposableView(element, children).let(::ComposableTreeNode)

    private fun buildComposableView(element: Node.Element, children: List<Node>): ComposableView =
        when (element.tag) {
            ComposableTypes.asyncImage -> buildAsyncImageNode(element.attributes)
            ComposableTypes.card -> buildCardNode(element.attributes)
            ComposableTypes.column -> buildColumnNode(element.attributes)
            ComposableTypes.icon -> buildIconNode(element.attributes)
            ComposableTypes.lazyColumn -> buildLazyColumnNode(element.attributes)
            ComposableTypes.lazyRow -> buildLazyRowNode(element.attributes)
            ComposableTypes.row -> buildRowNode(element.attributes)
            ComposableTypes.scaffold -> buildScaffoldNode(element.attributes)
            ComposableTypes.spacer -> buildSpacerNode(element.attributes)
            ComposableTypes.text ->
                buildTextNode(attributes = element.attributes, text = element.tag)
            ComposableTypes.topAppBar ->
                buildTopAppBarNode(attributes = element.attributes, children = children)
            else ->
                buildTextNode(
                    attributes = element.attributes, text = "${element.tag} not supported yet")
        }

    /**
     * Creates an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object.
     * AsyncImage co-relates to the AsyncImage composable from Coil library used to load images from network
     * @param attributes the `Attributes` object to create the `AsyncImageDTO` object from
     * @return an `AsyncImageDTO` object based on the attributes and text of the input `Attributes` object
     */
    private fun buildAsyncImageNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(
                AsyncImageDTO.Builder().imageUrl(attributes.find { it.name == "url" }?.value ?: "")
            ) { builder, attribute ->
                when (attribute.name) {
                    "content-scale" -> builder.contentScale(attribute.value)
                    "content-description" -> builder.contentDescription(attribute.value)
                    "cross-fade" -> builder.crossFade(attribute.value)
                    "shape" -> builder.shape(attribute.value)
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
    private fun buildCardNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(CardDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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
    private fun buildColumnNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(ColumnDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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

    private fun buildIconNode(attributes: Array<Attribute>): IconDTO =
        attributes
            .fold(
                IconDTO.Builder().imageVector(attributes.find { it.name == "name" }?.value ?: "")
            ) { builder, attribute ->
                when (attribute.name) {
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

    private fun buildLazyColumnNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(LazyColumnDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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

    private fun buildLazyRowNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(LazyRowDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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
    private fun buildRowNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(RowDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    "horizontal-arrangement" -> {
                        builder.horizontalArrangement(horizontalArrangement = attribute.value)
                    }
                    "vertical-alignment" -> {
                        builder.verticalAlignment(verticalAlignment = attribute.value)
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

    private fun buildScaffoldNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(ScaffoldDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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

    private fun buildSpacerNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(SpacerDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
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
    private fun buildTextNode(attributes: Array<Attribute>, text: String): ComposableView =
        attributes
            .fold(TextDTO.Builder().text(text)) { builder, attribute ->
                when (attribute.name) {
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

    private fun buildTopAppBarNode(
        attributes: Array<Attribute>,
        children: List<Node>
    ): ComposableView {
        val builder = TopAppBarDTO.Builder()

        attributes
            .forEach { attribute ->
                when (attribute.name) {
                    "background-color" -> builder.backgroundColor(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontal-padding" -> builder.horizontalPadding(attribute.value)
                    "vertical-padding" -> builder.verticalPadding(attribute.value)
                }
            }
            .also {
                children.forEach { childNode ->
                    childNode as Node.Element
                    when (childNode.tag) {
                        "heading" -> {
                            val textDto =
                                buildTextNode(
                                    childNode.attributes,
                                    childNode.attributes.find { it.name == "value" }?.value ?: "")
                                    as TextDTO
                            builder.textDTO = textDto
                        }
                        "nav-icon",
                        "action-icon" -> {
                            val iconDto = buildIconNode(childNode.attributes)

                            if (childNode.tag == "nav-icon") {
                                builder.addNavIcon(iconDto)
                            } else {
                                builder.addActionIcon(iconDto)
                            }
                        }
                    }
                }
            }

        return builder.build()
    }

    fun createEmptyNode(): ComposableView {
        return ColumnDTO.Builder().build()
    }
}
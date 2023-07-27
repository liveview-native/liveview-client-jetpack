package org.phoenixframework.liveview.domain.factory

import org.phoenixframework.liveview.data.dto.AsyncImageDTO
import org.phoenixframework.liveview.data.dto.ButtonDTO
import org.phoenixframework.liveview.data.dto.CardDTO
import org.phoenixframework.liveview.data.dto.ColumnDTO
import org.phoenixframework.liveview.data.dto.IconDTO
import org.phoenixframework.liveview.data.dto.LazyColumnDTO
import org.phoenixframework.liveview.data.dto.LazyRowDTO
import org.phoenixframework.liveview.data.dto.RowDTO
import org.phoenixframework.liveview.data.dto.ScaffoldDTO
import org.phoenixframework.liveview.data.dto.SpacerDTO
import org.phoenixframework.liveview.data.dto.TextDTO
import org.phoenixframework.liveview.data.dto.TopAppBarDTO
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.PushEvent
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
    fun buildComposableTreeNode(
        element: Node.Element,
        children: List<Node>,
        pushEvent: PushEvent
    ): ComposableTreeNode =
        buildComposableView(element, children, pushEvent).let(::ComposableTreeNode)

    private fun buildComposableView(
        element: Node.Element,
        children: List<Node>,
        pushEvent: PushEvent
    ): ComposableView =
        when (element.tag) {
            ComposableTypes.asyncImage -> buildAsyncImageNode(element.attributes)
            ComposableTypes.button -> buildButtonNode(element.attributes, pushEvent)
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
                    attributes = element.attributes,
                    text = "${element.tag} not supported yet"
                )
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
                    "contentScale" -> builder.contentScale(attribute.value)
                    "contentDescription" -> builder.contentDescription(attribute.value)
                    "crossFade" -> builder.crossFade(attribute.value)
                    "shape" -> builder.shape(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as AsyncImageDTO.Builder
            }
            .build()

    /**
     * Creates a `ButtonDTO` object based on the attributes of the input `Attributes` object.
     * Button co-relates to the Button composable
     * @param attributes the `Attributes` object to create the `CardDTO` object from
     * @return a `ButtonDTO` object based on the attributes of the input `Attributes` object
     **/
    private fun buildButtonNode(
        attributes: Array<Attribute>,
        pushEvent: PushEvent
    ): ComposableView =
        attributes
            .fold(ButtonDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    //TODO Swift is using `phx-click`. Should Android use the same?
                    "phx-click" -> builder.onClick {
                        pushEvent("click", attribute.value, "", null)
                    }

                    "padding" -> builder.padding(attribute.value)
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
                    "backgroundColor" -> builder.backgroundColor(attribute.value)
                    "elevation" -> builder.elevation(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as CardDTO.Builder
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
                    "verticalArrangement" -> {
                        builder.verticalArrangement(attribute.value)
                    }

                    "horizontalAlignment" -> {
                        builder.horizontalAlignment(attribute.value)
                    }

                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as ColumnDTO.Builder
            }
            .build()

    private fun buildIconNode(attributes: Array<Attribute>): IconDTO =
        attributes
            .fold(
                IconDTO.Builder()
                    .imageVector(attributes.find { it.name == "imageVector" }?.value ?: "")
            ) { builder, attribute ->
                when (attribute.name) {
                    "tint" -> builder.tint(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "contentDescription" -> builder.contentDescription(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as IconDTO.Builder
            }
            .build()

    private fun buildLazyColumnNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(LazyColumnDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    "height" -> builder.height(attribute.value)
                    "horizontalAlignment" -> builder.horizontalAlignment(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "itemBottomPadding" -> builder.bottomPadding(attribute.value)
                    "itemHorizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "itemLeftPadding" -> builder.leftPadding(attribute.value)
                    "itemPadding" -> builder.lazyColumnItemPadding(attribute.value)
                    "itemRightPadding" -> builder.rightPadding(attribute.value)
                    "itemTopPadding" -> builder.topPadding(attribute.value)
                    "itemVerticalPadding" -> builder.verticalPadding(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "reverseLayout" -> builder.reverseLayout(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "verticalArrangement" -> builder.verticalArrangement(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    "width" -> builder.width(attribute.value)
                    else -> builder
                } as LazyColumnDTO.Builder
            }
            .build()

    private fun buildLazyRowNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(LazyRowDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    "height" -> builder.height(attribute.value)
                    "horizontalArrangement" -> builder.horizontalArrangement(horizontalArrangement = attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "itemBottomPadding" -> builder.bottomPadding(attribute.value)
                    "itemHorizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "itemLeftPadding" -> builder.leftPadding(attribute.value)
                    "itemPadding" -> builder.lazyRowItemPadding(attribute.value)
                    "itemRightPadding" -> builder.rightPadding(attribute.value)
                    "itemTopPadding" -> builder.topPadding(attribute.value)
                    "itemVerticalPadding" -> builder.verticalPadding(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "reverseLayout" -> builder.reverseLayout(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "verticalAlignment" -> builder.verticalAlignment(verticalAlignment = attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    "width" -> builder.width(attribute.value)
                    else -> builder
                } as LazyRowDTO.Builder
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
                    "horizontalArrangement" -> {
                        builder.horizontalArrangement(horizontalArrangement = attribute.value)
                    }

                    "verticalAlignment" -> {
                        builder.verticalAlignment(verticalAlignment = attribute.value)
                    }

                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as RowDTO.Builder
            }
            .build()

    private fun buildScaffoldNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(ScaffoldDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    "backgroundColor" -> builder.backgroundColor(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as ScaffoldDTO.Builder
            }
            .build()

    private fun buildSpacerNode(attributes: Array<Attribute>): ComposableView =
        attributes
            .fold(SpacerDTO.Builder()) { builder, attribute ->
                when (attribute.name) {
                    "size" -> builder.size(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    else -> builder
                } as SpacerDTO.Builder
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
                    "text" -> builder.text(attribute.value)
                    "color" -> builder.color(attribute.value)
                    "fontSize" -> builder.fontSize(attribute.value)
                    "fontStyle" -> builder.fontStyle(attribute.value)
                    "fontWeight" -> builder.fontWeight(attribute.value)
                    "letterSpacing" -> builder.letterSpacing(attribute.value)
                    "textDecoration" -> builder.textDecoration(attribute.value)
                    "textAlign" -> builder.textAlign(attribute.value)
                    "lineHeight" -> builder.lineHeight(attribute.value)
                    "overflow" -> builder.overflow(attribute.value)
                    "softWrap" -> builder.softWrap(attribute.value)
                    "maxLines" -> builder.maxLines(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                    else -> builder
                } as TextDTO.Builder
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
                    "backgroundColor" -> builder.backgroundColor(attribute.value)
                    "size" -> builder.size(attribute.value)
                    "height" -> builder.height(attribute.value)
                    "width" -> builder.width(attribute.value)
                    "padding" -> builder.padding(attribute.value)
                    "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                    "verticalPadding" -> builder.verticalPadding(attribute.value)
                }
            }
            .also {
                children.forEach { childNode ->
                    childNode as Node.Element
                    // TopAppBar can have three sub-items:
                    // Title, NavigationIcon and a list of Actions
                    when (childNode.tag) {
                        "Title" -> {
                            builder.textDTO = buildTextNode(
                                childNode.attributes,
                                childNode.tag
                            ) as TextDTO
                        }

                        "NavigationIcon" -> {
                            val iconDto = buildIconNode(childNode.attributes)
                            builder.addNavIcon(iconDto)
                        }

                        "Action" -> {
                            val iconDto = buildIconNode(childNode.attributes)
                            builder.addActionIcon(iconDto)
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
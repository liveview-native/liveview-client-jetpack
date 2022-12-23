package org.phoenixframework.liveview.data.dto

import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element


object ComposableNodeFactory {

    fun getComposableView(element: Element): ComposableTreeNode {
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

    private fun buildCardNode(attributes: Attributes): ComposableView {
        val cardNode = CardDTO.Builder()
        attributes.forEach { attribute ->
            when (attribute.key) {
                "shape" -> cardNode.setShape(attribute.value)
                "background-color" -> cardNode.setBackgroundColor(attribute.value)
                "elevation" -> cardNode.setCardElevation(attribute.value)
            }
        }
        return cardNode.build()
    }

    private fun buildAsyncImageNode(attributes: Attributes, text: String): ComposableView {
        val asyncImageNode = AsyncImageDTO.Builder().setImageUrl(text)
        attributes.forEach { attribute ->
            when (attribute.key) {
                "content-scale" -> asyncImageNode.setContentScale(attribute.key)
                "content-description" -> asyncImageNode.setContentDescription(attribute.key)
                "cross-fade" -> asyncImageNode.setCrossFade(attribute.key)
                "shape" -> asyncImageNode.setShape(attribute.key)
            }
        }
        return asyncImageNode.build()
    }

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
            }

        }

        return rowNode.build()

    }

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

            }
        }
        return columnNode.build()
    }

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

            }
        }

        return textNode.build()

    }
}
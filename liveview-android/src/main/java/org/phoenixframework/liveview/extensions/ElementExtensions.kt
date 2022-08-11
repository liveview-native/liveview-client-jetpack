package org.phoenixframework.liveview.extensions

import org.jsoup.nodes.Element

fun Element.multiAttr(vararg attributeKeys: String): String {

    var result = ""

    attributeKeys.forEach { theKey ->
        if (this.hasAttr(theKey)) {
            result = this.attr(theKey)
            return@forEach
        }
    }

    return result

}
package org.phoenixframework.liveview.ui.phx_components

import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element
import org.phoenixframework.Payload

sealed class PhxAction(
    val event: String = "event",
    val payload: Payload,
) {

    class PhxButtonClickAction(
        val element: Element
    ) : PhxAction(

        payload = mapOf(
            "type" to "click",
            "event" to element.attr("phx-click"),
            "value" to element.extractMetaData()
        )
    )

    class PhxModifierClickAction(
        val element: Element
    ) : PhxAction (

            payload = mapOf(
                "type" to "click",
                "event" to element.attr("phx-mod-click"),
                "value" to element.extractMetaData()
            )
    )

    class PhxTextValueChangeAction(
        val element: Element,
        val inputText: String
    ) : PhxAction(
        payload = mapOf(
            "type" to "anything",
            "event" to element.attr("phx-change"),
            "value" to mapOf("input_text" to inputText)
        )
    )

    class PhxCheckChangedAction(
        val element: Element,
        val isChecked: Boolean
    ) : PhxAction(
        payload = mapOf(
            "type" to "check",
            "event" to element.attr("phx-check"),
            "value" to isChecked
        )
    )

    class PhxNavAction(
        val element: Element,
        val navDestination: String
    ) : PhxAction(
        payload = mapOf(
            "type" to "navigate",
            "event" to "phx-navigate",
            "value" to navDestination
        )
    )
}

fun Element.extractMetaData() : Payload {
    return this.attributes()
        .filter { it.key.startsWith("phx-", true) }
        .fold(mapOf()) { acc: Map<String, Any>, attribute: Attribute? ->

            val key = attribute?.key?.substringAfter("phx-")

            key?.let {
                acc + mapOf(key to attribute.value)
            } ?: acc

        }
}

package org.phoenixframework.liveview.domain.extensions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

/*

fun xGenerateFromElement(
    element: Element,
    phxActionListener: ((PhxAction) -> Unit)? = null
): Modifier {

    var internalModifier = Modifier.wrapContentWidth().wrapContentHeight()

    element.attributes().forEach { attribute: Attribute ->

        internalModifier = when(attribute.key) {

            "padding" -> {
                internalModifier.then(Modifier.padding(attribute.value.toInt().dp))
            }

            "width" -> {
                when(attribute.value) {
                    "wrap", "wrap_content" -> {
                        internalModifier.then(Modifier.wrapContentWidth())
                    }
                    "fill", "match_parent" -> {
                        internalModifier.then(Modifier.fillMaxWidth())
                    }
                    else -> {
                        internalModifier.then(Modifier.width(attribute.value.toInt().dp))
                    }
                }
            }

            "height" -> {
                when(attribute.value) {
                    "wrap", "wrap_content" -> {
                        internalModifier.then(Modifier.wrapContentHeight())
                    }
                    "fill", "match_parent" -> {
                        internalModifier.then(Modifier.fillMaxHeight())
                    }
                    else -> {
                        internalModifier.then(Modifier.height(attribute.value.toInt().dp))
                    }
                }
            }

            "max-size" -> {
                internalModifier.then(Modifier.fillMaxSize())
            }

            "background" -> {
                val color = Color(attribute.value.toColorInt())
                internalModifier.then(Modifier.background(color))
            }

            "click" -> {
                val eventName = attribute.value
                internalModifier.then(
                    Modifier.clickable {
                        Log.d("CLICK", eventName)
                        phxActionListener?.invoke(
                            PhxAction.PhxModifierClickAction(
                                element = element
                            )
                        )
                    }
                )
            }

            else -> {
                internalModifier
            }
        }

    }

    return internalModifier
}*/

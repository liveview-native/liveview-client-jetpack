package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.paddingFromStyle(arguments: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val params =
        if (arguments.firstOrNull()?.isList == true) arguments.first().listValue else arguments
    val paddingValues = when (params.size) {
        1 -> {
            val paddingValue = params.firstOrNull()
            val dpValue = (paddingValue?.intValue ?: 0).dp
            when (paddingValue?.name) {
                "horizontal" -> PaddingValues(horizontal = dpValue)
                "vertical" -> PaddingValues(vertical = dpValue)
                "start" -> PaddingValues(start = dpValue)
                "_end" -> PaddingValues(end = dpValue) //TODO "end" is a reserved word in Elixir
                "top" -> PaddingValues(top = dpValue)
                "bottom" -> PaddingValues(bottom = dpValue)
                else -> null
            }
        }

        2 -> {
            val paddingValue = params.firstOrNull()
            when (paddingValue?.name) {
                "horizontal", "vertical", null -> {
                    val hp =
                        params.find { it.name == "horizontal" }?.intValue
                            ?: params.getOrNull(0)?.intValue
                    val vp =
                        params.find { it.name == "vertical" }?.intValue
                            ?: params.getOrNull(1)?.intValue

                    if (hp != null && vp != null) {
                        PaddingValues(vertical = vp.dp, horizontal = hp.dp)
                    } else null
                }

                else -> {
                    PaddingValues(
                        start = (params.find { it.name == "start" }?.intValue ?: 0).dp,
                        top = (params.find { it.name == "top" }?.intValue ?: 0).dp,
                        end = (params.find { it.name == "_end" }?.intValue
                            ?: 0).dp, //TODO "end" is a reserved word in Elixir
                        bottom = (params.find { it.name == "bottom" }?.intValue ?: 0).dp,
                    )
                }
            }
        }

        3, 4 -> {
            val paddingValues = if (params.any { it.name == null }) {
                params.map { it.intValue ?: 0 }
            } else {
                listOf(
                    params.find { it.name == "start" }?.intValue ?: 0,
                    params.find { it.name == "top" }?.intValue ?: 0,
                    params.find { it.name == "_end" }?.intValue
                        ?: 0, //TODO "end" is a reserved word in Elixir
                    params.find { it.name == "bottom" }?.intValue ?: 0
                )
            }
            PaddingValues(
                paddingValues[0].dp,
                paddingValues[1].dp,
                paddingValues[2].dp,
                (paddingValues.getOrNull(3) ?: 0).dp
            )
        }

        else -> null
    }
    return paddingValues?.let { this.then(Modifier.padding(it)) } ?: this
}
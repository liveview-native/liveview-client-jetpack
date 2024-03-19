package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.paddingFromStyle(modifierData: List<ModifierDataWrapper.ArgumentData>): Modifier {
    val params = if (modifierData.first().isList) modifierData.first().listValue else modifierData
    val paddingValues = when (params.size) {
        1 -> {
            val paddingValue = params.first()
            val dpValue = (paddingValue.intValue ?: 0).dp
            when (paddingValue.name) {
                "horizontal" -> PaddingValues(horizontal = dpValue)
                "vertical" -> PaddingValues(vertical = dpValue)
                "start" -> PaddingValues(start = dpValue)
                "_end" -> PaddingValues(end = dpValue)
                "top" -> PaddingValues(top = dpValue)
                "bottom" -> PaddingValues(bottom = dpValue)
                else -> PaddingValues(dpValue)
            }
        }

        2 -> {
            val paddingValue = params.first()
            when (paddingValue.name) {
                "horizontal", "vertical", null -> {
                    val hp =
                        params.find { it.name == "horizontal" }?.intValue ?: params[0].intValue ?: 0
                    val vp =
                        params.find { it.name == "vertical" }?.intValue ?: params[1].intValue ?: 0
                    PaddingValues(vertical = vp.dp, horizontal = hp.dp)
                }

                else -> {
                    PaddingValues(
                        start = (params.find { it.name == "start" }?.intValue ?: 0).dp,
                        top = (params.find { it.name == "top" }?.intValue ?: 0).dp,
                        end = (params.find { it.name == "_end" }?.intValue ?: 0).dp,
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
                    params.find { it.name == "_end" }?.intValue ?: 0,
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

        else -> PaddingValues(0.dp)
    }
    return this.then(Modifier.padding(paddingValues))
}
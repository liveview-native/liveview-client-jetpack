package com.dockyard.lightpandarenderer.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import com.dockyard.lightpandarenderer.NodeSnapshot
import kotlin.jvm.optionals.getOrNull

inline fun <reified T> NodeSnapshot.attributeValue(name: String): T? {
    val value = node.attributeValue(name.lowercase()).getOrNull()
    println("ATTRIBUTE VALUE:")
    println(name)
    println(value)
    println(T::class)
    println(Alignment.Horizontal::class)
    return when (T::class) {
        Arrangement.Vertical::class ->
            when (value) {
                "Center" -> Arrangement.Center as? T
                "Top" -> Arrangement.Top as? T
                "Bottom" -> Arrangement.Bottom as? T
                else -> null
            }
        Alignment.Horizontal::class ->
            when (value) {
                "Start" -> Alignment.Start as? T
                "CenterHorizontally" -> Alignment.CenterHorizontally as? T
                "End" -> Alignment.End as? T
                else -> null
            }
        else -> value as? T
    }
}
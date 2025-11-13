package com.dockyard.lightpandarenderer

import androidx.compose.ui.Modifier
import com.dockyard.lightpandaclient.Node
//import com.dockyard.lightpandarenderer.library.ModifierLibrary
import org.swift.swiftkit.core.SwiftArena
import kotlin.jvm.optionals.getOrNull

class NodeSnapshot(val node: Node, val arena: SwiftArena) {
    val type by lazy { node.getType(arena) }
    val name by lazy { node.name }
    val value by lazy { node.value }
    val children by lazy { node.getChildren(arena) }

    fun modifier(): Modifier {
        return Modifier
//        return attributeValue("modifier")
//            ?.let { ModifierLibrary.parseModifier(it) }
//            ?: Modifier
    }
}
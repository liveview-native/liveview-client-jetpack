package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo

internal class PlaceholderModifierNode(
    var modifierName: String,
    var argListContext: List<ModifierDataAdapter.ArgumentData>
) : Modifier.Node()

internal data class PlaceholderModifierNodeElement(
    val modifierName: String,
    val argListContext: List<ModifierDataAdapter.ArgumentData>
) :
    ModifierNodeElement<PlaceholderModifierNode>() {
    override fun create() = PlaceholderModifierNode(modifierName, argListContext)

    override fun update(node: PlaceholderModifierNode) {
        node.modifierName = modifierName
        node.argListContext = argListContext
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "placeholderModifier"
        properties["modifierName"] = modifierName
        properties["argListContext"] = argListContext
    }
}

fun Modifier.placeholderModifier(
    modifierName: String,
    argListContext: List<ModifierDataAdapter.ArgumentData>
) =
    this then PlaceholderModifierNodeElement(modifierName, argListContext)
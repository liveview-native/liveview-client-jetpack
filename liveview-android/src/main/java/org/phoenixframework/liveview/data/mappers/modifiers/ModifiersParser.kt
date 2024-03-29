package org.phoenixframework.liveview.data.mappers.modifiers

import android.util.Log
import androidx.compose.ui.Modifier
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.phoenixframework.liveview.data.constants.Attrs.attrAlign
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignByBaseline
import org.phoenixframework.liveview.data.constants.Attrs.attrAspectRatio
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrClip
import org.phoenixframework.liveview.data.constants.Attrs.attrFillMaxHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrFillMaxWidth
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.stylesheet.ElixirLexer
import org.phoenixframework.liveview.stylesheet.ElixirParser
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.MapExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.TupleExprContext

object ModifiersParser {
    private val modifiersCacheTable = mutableMapOf<String, Modifier>()

    fun clearCacheTable() {
        modifiersCacheTable.clear()
    }

    fun Modifier.fromStyle(string: String, scope: Any?): Modifier {
        // Simple substring logic to extract the style name in order to check if it was parsed already
        val firstQuote = string.indexOf("\"")
        val styleKey = string.substring(
            startIndex = firstQuote + 1,
            endIndex = string.indexOf("\"", firstQuote + 1)
        )
        modifiersCacheTable[styleKey]?.let {
            return this.then(it)
        }

        // Parsing String using Elixir parser
        val charStream: CharStream = CharStreams.fromString(string)
        val elixirLexer = ElixirLexer(charStream)
        val commonTokenStream = CommonTokenStream(elixirLexer)
        val elixirParser = ElixirParser(commonTokenStream)

        // The stylesheet is a map, therefore the root expression must be a map expression
        val rootExpression = elixirParser.parse().block().expression().first()
        val mapExprContext: MapExprContext
        if (rootExpression is MapExprContext) {
            mapExprContext = rootExpression
        } else {
            return this
        }

        // Each style is a map with one child, so we get just the first child
        val mapContext = mapExprContext.map()
        val mapEntryContext = mapContext.map_entries().map_entry(0)

        // The map key is the style name and the map value contain the list of modifiers
        val mapKeyContext = mapEntryContext.expression(0)
        val mapValueContext = mapEntryContext.expression(1)

        // Map value must be a list of tuples
        val mapValueAsListContext: ListContext
        if (mapValueContext is ListExprContext && mapValueContext.list().expressions_().expression()
                .all { it is TupleExprContext }
        ) {
            mapValueAsListContext = mapValueContext.list()
        } else {
            return this
        }

        var parsedModifier: Modifier = Modifier

        // Each tuple of this list is a modifier.
        val modifiersTupleExpressionsList =
            mapValueAsListContext.expressions_().expression().filterIsInstance<TupleExprContext>()

        // Each tuple has 3 expressions:
        // modifier name, meta data, and arguments to create the modifier
        modifiersTupleExpressionsList.forEach { tupleExpr ->
            try {
                val modifierDataAdapter = ModifierDataAdapter(tupleExpr)

                modifierDataAdapter.modifierName?.let { name ->
                    try {
                        handlerModifier(
                            name,
                            modifierDataAdapter.arguments,
                            scope
                        ).let { modifier ->
                            parsedModifier = parsedModifier.then(modifier)
                        }
                    } catch (e: Exception) {
                        Log.e(
                            TAG,
                            "Error parsing modifier: $name -> ${modifierDataAdapter.metaData}",
                            e
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error creating modifier data from tuple", e)
            }
        }
        return if (parsedModifier != Modifier) {
            modifiersCacheTable[styleKey] = parsedModifier
            this.then(parsedModifier)
        } else this
    }

    private fun Modifier.handlerModifier(
        modifierId: String,
        argListContext: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?
    ): Modifier {
        return when (modifierId) {
            attrAlign -> this.then(alignFromStyle(argListContext, scope))
            attrAlignByBaseline -> this.then(alignByBaselineFromStyle(scope))
            attrAspectRatio -> this.then(aspectRatioFromStyle(argListContext))
            attrBackground -> this.then(backgroundFromStyle(argListContext))
            attrBorder -> this.then(borderFromStyle(argListContext))
            attrClip -> this.then(clipFromStyle(argListContext))
            attrFillMaxHeight -> this.then(fillMaxHeightFromStyle(argListContext))
            attrFillMaxWidth -> this.then(fillMaxWidthFromStyle(argListContext))
            attrHeight -> this.then(heightFromStyle(argListContext))
            attrPadding -> this.then(paddingFromStyle(argListContext))
            attrSize -> this.then(sizeFromStyle(argListContext))
            attrWidth -> this.then(widthFromStyle(argListContext))
            else -> this
        }
    }

    const val TAG = "ModifiersParse"
}
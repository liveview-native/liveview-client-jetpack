package org.phoenixframework.liveview.foundation.ui.modifiers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.Modifier
import org.antlr.v4.kotlinruntime.BailErrorStrategy
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import org.antlr.v4.kotlinruntime.atn.PredictionMode
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.stylesheet.ElixirLexer
import org.phoenixframework.liveview.stylesheet.ElixirParser
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.MapExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.TupleExprContext

abstract class BaseModifiersParser {
    private val modifiersCacheTable = mutableMapOf<String, List<Modifier>>()

    abstract var mustLoadModifiersFile: Boolean

    val isEmpty: Boolean
        get() = modifiersCacheTable.isEmpty()

    fun clearCacheTable() {
        modifiersCacheTable.clear()
    }

    fun fromStyleFile(fileContent: String, scope: Any? = null, pushEvent: PushEvent? = null) {
        clearCacheTable()
        val pairs = try {
            parseStyleFileContent(fileContent)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Error paring style file.", e)
            emptyList()
        }
        pairs?.forEach { pair ->
            val (styleName, tupleExpressionList) = pair
            val modifiersList = mutableListOf<Modifier>()
            tupleExpressionList.forEach { tupleExpr ->
                try {
                    fromTupleExpression(tupleExpr, scope, pushEvent)?.let {
                        modifiersList.add(it)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating modifier data from tuple", e)
                }
            }
            if (modifiersList.isNotEmpty()) {
                modifiersCacheTable[styleName] = modifiersList
            }
        }
    }

    private fun fromTupleExpression(
        tupleExpr: TupleExprContext,
        scope: Any?,
        pushEvent: PushEvent?
    ): Modifier? {
        // Each tuple has 3 expressions:
        // modifier name, meta data, and arguments to create the modifier
        return ModifierDataAdapter(tupleExpr).let { modifierDataAdapter ->
            modifierDataAdapter.modifierName?.let { modifierName ->
                try {
                    handleModifier(
                        modifierName,
                        modifierDataAdapter.arguments,
                        scope,
                        pushEvent
                    )

                } catch (e: Exception) {
                    Log.e(
                        TAG,
                        "Error parsing modifier: $modifierName -> ${modifierDataAdapter.metaData}",
                        e
                    )
                    null
                }
            }
        }
    }

    fun Modifier.fromStyleName(
        styleKey: String,
        scope: Any? = null,
        pushEvent: PushEvent? = null
    ): Modifier {
        return modifiersCacheTable[styleKey]?.fold(this) { acc: Modifier, modifier: Modifier ->
            if (modifier is PlaceholderModifierNodeElement) {
                handleModifier(
                    modifier.modifierName,
                    modifier.argListContext,
                    scope,
                    pushEvent
                )?.let {
                    acc.then(it)
                } ?: acc
            } else {
                acc.then(modifier)
            }
        } ?: Modifier
    }

    private fun parseStyleFileContent(fileContent: String): List<Pair<String, List<TupleExprContext>>>? {
        val rootExpression = parseElixirContent(
            // The parser has some problems with line breaks, so we're removing them
            fileContent.split('\n').joinToString("")
        )
        // The stylesheet is a map, therefore the root expression must be a map expression
        val mapExprContext: MapExprContext
        if (rootExpression is MapExprContext) {
            mapExprContext = rootExpression
        } else {
            return null
        }

        val mapContext = mapExprContext.map()
        val mapEntryContext = mapContext.map_entries()?.map_entry()?.map { mapEntryContext ->
            // The map key is the style name and the map value contain the list of modifiers
            val styleName = mapEntryContext.expression(0)?.text
                ?.removePrefix("\"")
                ?.removeSuffix("\"")
                ?.replace("\\\"", "\"") ?: return null // replacing backslash quotes by quotes

            val mapValueContext = mapEntryContext.expression(1)

            // Map value must be a list of tuples
            val mapValueAsListContext: ListContext
            if (mapValueContext is ListExprContext && mapValueContext.list().expressions_()
                    ?.expression()?.all { it is TupleExprContext } == true
            ) {
                mapValueAsListContext = mapValueContext.list()
            } else {
                return null
            }

            // Each tuple of this list is a modifier.
            styleName to (mapValueAsListContext.expressions_()?.expression()
                ?.filterIsInstance<TupleExprContext>() ?: emptyList())
        }
        return mapEntryContext
    }

    fun parseElixirContent(fileContent: String): ElixirParser.ExpressionContext? {
        // Parsing String using Elixir parser
        val elixirLexer = ElixirLexer(CharStreams.fromString(fileContent))
        val commonTokenStream = CommonTokenStream(elixirLexer)
        val elixirParser = ElixirParser(commonTokenStream).apply {
            // https://tomassetti.me/improving-the-performance-of-an-antlr-parser/
            interpreter.predictionMode = PredictionMode.SLL
            removeErrorListeners()
            errorHandler = BailErrorStrategy()
        }

        // The stylesheet is a map, therefore the root expression must be a map expression
        return elixirParser.parse().block().expression().firstOrNull()
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    abstract fun handleModifier(
        modifierId: String,
        arguments: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?,
        pushEvent: PushEvent?
    ): Modifier?

    companion object {
        private const val TAG = "BaseModifiersParser"
    }
}
package org.phoenixframework.liveview.foundation.ui.modifiers

import org.phoenixframework.liveview.stylesheet.ElixirParser

class ModifierDataAdapter(tupleExpression: ElixirParser.TupleExprContext) {

    private val modifierIdExpression = tupleExpression.tuple().expressions_()?.expression(0)
    private val metaDataExpression = tupleExpression.tuple().expressions_()?.expression(1)
    private val argsExpression = tupleExpression.tuple().expressions_()?.expression(2)

    val modifierName: String?
        get() =
            if (modifierIdExpression is ElixirParser.AtomExprContext) {
                modifierIdExpression.ATOM().text.substring(1)
            } else null

    val metaData: MetaData?
        get() =
            if (metaDataExpression is ElixirParser.ListExprContext) {
                val metadataExpressionListCtx = metaDataExpression.list()
                val metadataMapEntries = metadataExpressionListCtx.short_map_entries()
                var file: String? = null
                var line: Int? = null
                var module: String? = null
                var source: String? = null

                for (shortMapEntryContext in metadataMapEntries?.short_map_entry() ?: emptyList()) {
                    when (shortMapEntryContext.variable().text) {
                        "file" -> file = shortMapEntryContext.expression().text
                        "line" -> line = shortMapEntryContext.expression().text.toInt()
                        "module" -> module = shortMapEntryContext.expression().text
                        "source" -> source = shortMapEntryContext.expression().text
                    }
                }
                if (file != null || line != null || module != null || source != null) {
                    MetaData(file, line, module, source)
                } else null
            } else null


    val arguments: List<ArgumentData>
        get() {
            if (argsExpression is ElixirParser.ListExprContext) {
                val argsExpressionListCtx = argsExpression.list()
                val argsExpressionListCtxExpressions = argsExpressionListCtx.expressions_()
                if (argsExpressionListCtxExpressions == null || argsExpressionListCtxExpressions.expression().isEmpty()
                ) {
                    return emptyList()
                }

                val result = mutableListOf<ArgumentData>()
                argsExpressionListCtxExpressions.expression().forEach { argumentExpression ->
                    when (argumentExpression) {
                        is ElixirParser.TupleExprContext -> {
                            result.add(argValueFromContext(null, argumentExpression))
                        }

                        is ElixirParser.ListExprContext -> {
                            result.add(argValueFromContext(null, argumentExpression))
                        }

                        else -> {
                            argValueFromContext(null, argumentExpression)?.let {
                                result.add(it)
                            }
                        }
                    }
                }
                return result
            }
            return emptyList()
        }

    private fun argValueFromContext(
        argumentKey: String?,
        expression: ElixirParser.ExpressionContext
    ): ArgumentData? {
        return when (expression) {
            is ElixirParser.AtomExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.BoolExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.IntegerExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.FloatExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.ListExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.TupleExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.SingleLineStringExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.SingleLineCharlistExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.MultiLineStringExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.MultiLineCharlistExprContext ->
                argValueFromContext(argumentKey, expression)

            is ElixirParser.UnaryExprContext -> {
                argValueFromContext(argumentKey, expression)
            }

            else -> null
        }
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.SingleLineStringExprContext
    ): ArgumentData {
        val stringWithQuotes = argumentValueExpression.SINGLE_LINE_STRING().text
        return ArgumentData(
            argumentKey,
            TYPE_STRING,
            stringWithQuotes.substring(1, stringWithQuotes.lastIndex)
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.SingleLineCharlistExprContext
    ): ArgumentData {
        val stringWithQuotes = argumentValueExpression.SINGLE_LINE_CHARLIST().text
        return ArgumentData(
            argumentKey,
            TYPE_STRING,
            stringWithQuotes.substring(1, stringWithQuotes.lastIndex)
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.MultiLineStringExprContext
    ): ArgumentData {
        val stringWithTripleQuotes = argumentValueExpression.MULTI_LINE_STRING().text
        return ArgumentData(
            argumentKey,
            TYPE_STRING,
            stringWithTripleQuotes.substring(3, stringWithTripleQuotes.lastIndex - 2)
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.MultiLineCharlistExprContext
    ): ArgumentData {
        val stringWithTripleQuotes = argumentValueExpression.MULTI_LINE_CHARLIST().text
        return ArgumentData(
            argumentKey,
            TYPE_STRING,
            stringWithTripleQuotes.substring(3, stringWithTripleQuotes.lastIndex - 2)
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.ListExprContext
    ): ArgumentData {
        val result = mutableListOf<ArgumentData>()
        val argumentEntriesList = argumentValueExpression.list()

        argumentEntriesList.expressions_()?.expression()?.forEach { expression ->
            argValueFromContext(null, expression)
                ?.let {
                    result.add(it)
                }
        }
        argumentEntriesList?.short_map_entries()?.short_map_entry()?.forEach { argumentEntry ->
            val argumentEntryKey = try {
                argumentEntry.variable().text
            } catch (_: Exception) {
                // special case to read Elixir reserved words like "end" and "after"
                argumentEntry.children?.getOrNull(0)?.text
            }
            argValueFromContext(argumentEntryKey, argumentEntry.expression())
                ?.let {
                    result.add(it)
                }

        }
        return ArgumentData(
            argumentKey,
            TYPE_LIST,
            result
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.BoolExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TYPE_BOOLEAN,
            argumentValueExpression.bool_().text.toBoolean()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.FloatExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TYPE_FLOAT,
            argumentValueExpression.FLOAT().text.toFloat()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.IntegerExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TYPE_INT,
            argumentValueExpression.INTEGER().text.toInt()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.AtomExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TYPE_ATOM,
            argumentValueExpression.ATOM().text
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.UnaryExprContext
    ): ArgumentData {
        val unaryOperator = argumentValueExpression.unaryOp().text
        val unaryExpression = argumentValueExpression.expression()

        if (unaryExpression is ElixirParser.IntegerExprContext) {
            var intValue = unaryExpression.INTEGER().text.toInt()
            if (unaryOperator == "-") {
                intValue = -intValue
            }
            return ArgumentData(
                argumentKey,
                TYPE_INT,
                intValue
            )
        }

        if (unaryExpression is ElixirParser.FloatExprContext) {
            var floatValue = unaryExpression.FLOAT().text.toFloat()
            if (unaryOperator == "-") {
                floatValue = -floatValue
            }
            return ArgumentData(
                argumentKey,
                TYPE_FLOAT,
                floatValue
            )
        }

        if (unaryExpression is ElixirParser.BoolExprContext) {
            var boolValue = unaryExpression.bool_().text.toBoolean()
            if (unaryOperator == "!") {
                boolValue = !boolValue
            }
            return ArgumentData(
                argumentKey,
                TYPE_BOOLEAN,
                boolValue
            )
        }
        return ArgumentData(
            argumentKey,
            TYPE_UNARY,
            null
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.TupleExprContext
    ): ArgumentData {
        val tupleCtx = argumentValueExpression.tuple()

        val tupleExpressions = tupleCtx.expressions_()

        val clazz = when (val argTypeExpression = tupleExpressions?.expression(0)) {
            is ElixirParser.AtomExprContext -> argTypeExpression.ATOM().text.replace(":", "")
            is ElixirParser.AliasExprContext -> argTypeExpression.ALIAS().text.replace(":", "")
            else -> TYPE_UNDEFINED
        }

        val argMetaDataExpression = tupleExpressions?.expression(1) as ElixirParser.ListExprContext

        val argsValues = when (val argParamsExpression = tupleExpressions.expression(2)) {
            is ElixirParser.ListExprContext -> {
                argParamsExpression.list().expressions_()?.expression()?.map {
                    argValueFromContext(null, it)
                }
            }

            is ElixirParser.TupleExprContext -> {
                argParamsExpression.tuple().expressions_()?.expression()?.map {
                    argValueFromContext(null, it)
                }
            }

            else -> null
        }

        return ArgumentData(
            argumentKey,
            clazz,
            argsValues
        )
    }

    class ArgumentData(
        val name: String?,
        val type: String,
        val value: Any?
    ) {
        val isAtom: Boolean
            get() = type == TYPE_ATOM

        val isBoolean: Boolean
            get() = type == TYPE_BOOLEAN

        val isDot: Boolean
            get() = type == TYPE_DOT

        val isFloat: Boolean
            get() = type == TYPE_FLOAT

        val isInt: Boolean
            get() = type == TYPE_INT

        val isList: Boolean
            get() = type == TYPE_LIST

        val isNumber: Boolean
            get() = type == TYPE_INT || type == TYPE_FLOAT

        val isString: Boolean
            get() = type == TYPE_STRING

        val booleanValue: Boolean?
            get() = value as? Boolean

        val floatValue: Float?
            get() {
                return if (value is Int) {
                    value.toFloat()
                } else {
                    value as? Float
                }
            }

        val intValue: Int?
            get() = value as? Int

        val listValue: List<ArgumentData>
            get() = (value as? List<ArgumentData>) ?: emptyList()

        val stringValue: String?
            get() = value?.toString()

        val stringValueWithoutColon: String?
            get() = stringValue?.replace(":", "")
    }

    data class MetaData(
        val file: String?,
        val line: Int?,
        val module: String?,
        val source: String?,
    )

    companion object {
        const val TYPE_ATOM = "Atom"
        const val TYPE_BOOLEAN = "Boolean"
        const val TYPE_DOT = "."
        const val TYPE_FLOAT = "Float"
        const val TYPE_INT = "Int"
        const val TYPE_LAMBDA_VALUE = "lambdaValue"
        const val TYPE_LIST = "List"
        const val TYPE_STRING = "String"
        const val TYPE_UNARY = "Unary"
        const val TYPE_UNDEFINED = "<undefined>"

        // TODO We're just supporting primitive types from now
        fun typeFromClass(value: Any?): String {
            return when (value) {
                is Boolean -> TYPE_BOOLEAN
                is Float, is Double -> TYPE_FLOAT
                is Int -> TYPE_INT
                is String -> TYPE_STRING
                else -> TYPE_STRING
            }
        }
    }
}


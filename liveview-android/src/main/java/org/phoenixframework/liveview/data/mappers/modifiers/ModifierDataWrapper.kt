package org.phoenixframework.liveview.data.mappers.modifiers

import android.util.Log
import org.phoenixframework.liveview.stylesheet.ElixirParser

class ModifierDataWrapper(tupleExpression: ElixirParser.TupleExprContext) {

    private val modifierIdExpression = tupleExpression.tuple().expressions_().expression(0)
    private val metaDataExpression = tupleExpression.tuple().expressions_().expression(1)
    private val argsExpression = tupleExpression.tuple().expressions_().expression(2)

    init {
        if (metaDataExpression is ElixirParser.ListExprContext) {
            val metadataExpressionListCtx = metaDataExpression.list()
            val metadataMapEntries = metadataExpressionListCtx?.short_map_entries()
            metadataMapEntries?.short_map_entry()?.forEach { shortMapEntryContext ->
                Log.d(
                    TAG,
                    "** ${shortMapEntryContext.variable().text} = ${shortMapEntryContext.expression().text}"
                )
            }
        }
    }

    val modifierName: String?
        get() =
            if (modifierIdExpression is ElixirParser.AtomExprContext) {
                modifierIdExpression.ATOM().text.substring(1)
            } else null

    val arguments: List<ArgumentData>
        get() {
            if (argsExpression is ElixirParser.ListExprContext) {
                val argsExpressionListCtx = argsExpression.list()

                if (argsExpressionListCtx.expressions_() == null || argsExpressionListCtx.expressions_()
                        .expression().isEmpty()
                ) {
                    return emptyList()
                }
                val result = mutableListOf<ArgumentData>()
                argsExpressionListCtx.expressions_().expression().forEach { argumentExpression ->
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

            is ElixirParser.UnaryExprContext -> {
                argValueFromContext(argumentKey, expression)
            }


            else -> null
        }
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
            val argumentEntryKey = argumentEntry.variable().text
            argValueFromContext(argumentEntryKey, argumentEntry.expression())
                ?.let {
                    result.add(it)
                }

        }
        return ArgumentData(
            argumentKey,
            TypeList,
            result
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.BoolExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TypeBoolean,
            argumentValueExpression.bool_().text.toBoolean()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.FloatExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TypeFloat,
            argumentValueExpression.FLOAT().text.toFloat()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.IntegerExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TypeInt,
            argumentValueExpression.INTEGER().text.toInt()
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.AtomExprContext
    ): ArgumentData {
        return ArgumentData(
            argumentKey,
            TypeAtom,
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
                TypeInt,
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
                TypeFloat,
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
                TypeBoolean,
                boolValue
            )
        }
        return ArgumentData(
            argumentKey,
            TypeUnary,
            null
        )
    }

    private fun argValueFromContext(
        argumentKey: String?,
        argumentValueExpression: ElixirParser.TupleExprContext
    ): ArgumentData {
        val tupleCtx = argumentValueExpression.tuple()

        val tupleExpressions = tupleCtx.expressions_()

        val argTypeExpression = tupleExpressions.expression(0) as ElixirParser.AtomExprContext
        val argMetaDataExpression = tupleExpressions.expression(1) as ElixirParser.ListExprContext
        val argParamsExpression = tupleExpressions.expression(2) as ElixirParser.ListExprContext

        val clazz = argTypeExpression.ATOM().text.replace(":", "")

        val argsValues = argParamsExpression.list().expressions_()?.expression()?.map {
            argValueFromContext(null, it)
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
        val isBoolean: Boolean
            get() = type == TypeBoolean

        val isDot: Boolean
            get() = type == TypeDot

        val isFloat: Boolean
            get() = type == TypeFloat

        val isInt: Boolean
            get() = type == TypeInt

        val isList: Boolean
            get() = type == TypeList

        val isNumber: Boolean
            get() = type == TypeInt || type == TypeFloat

        val booleanValue: Boolean?
            get() = value as? Boolean

        val floatValue: Float?
            get() = value as? Float

        val intValue: Int?
            get() = value as? Int

        val listValue: List<ArgumentData>
            get() = (value as? List<ArgumentData>) ?: emptyList()

        val stringValue: String?
            get() = value?.toString()

        val stringValueWithoutColon: String?
            get() = stringValue?.replace(":", "")
    }

    companion object {
        const val TAG = "ModifierDataWrapper"

        private const val TypeAtom = "Atom"
        private const val TypeBoolean = "Boolean"
        private const val TypeDot = "."
        private const val TypeFloat = "Float"
        private const val TypeInt = "Int"
        private const val TypeList = "List"
        private const val TypeUnary = "Unary"
    }
}


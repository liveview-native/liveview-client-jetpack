package org.phoenixframework.liveview.ui.modifiers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.preferKeepClear
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.toolingGraphicsLayer
import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.atn.PredictionMode
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAbsoluteOffset
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAbsolutePadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlignBy
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlignByBaseline
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlpha
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAnimateContentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAnimateEnterExit
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAspectRatio
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBorder
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierCaptionBarPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClickable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClip
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClipToBounds
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierCombinedClickable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierDefaultMinSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierDisplayCutoutPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierExposedDropdownSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFocusGroup
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFocusTarget
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFocusable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeightIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHorizontalScroll
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierImeNestedScroll
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierImePadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierLayoutId
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMandatorySystemGesturesPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMatchParentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMenuAnchor
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMinimumInteractiveComponentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierNavigationBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierOffset
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierOnFocusChanged
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierOnFocusEvent
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierOnFocusedBoundsChanged
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPaddingFrom
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPaddingFromBaseline
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPreferKeepClear
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierProgressSemantics
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredHeightIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredSizeIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRequiredWidthIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierRotate
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSafeContentPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSafeDrawingPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSafeGesturesPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierScale
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSelectable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSelectableGroup
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierShadow
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSizeIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierStatusBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSystemBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSystemGestureExclusion
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSystemGesturesPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierTestTag
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierToggleable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierToolingGraphicsLayer
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierTriStateToggleable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierVerticalScroll
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWaterfallPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWidthIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWindowInsetsBottomHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWindowInsetsEndWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWindowInsetsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWindowInsetsStartWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWindowInsetsTopHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWrapContentHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWrapContentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWrapContentWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierZIndex
import org.phoenixframework.liveview.stylesheet.ElixirLexer
import org.phoenixframework.liveview.stylesheet.ElixirParser
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.ListExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.MapExprContext
import org.phoenixframework.liveview.stylesheet.ElixirParser.TupleExprContext
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.ui.modifiers.ModifierDataAdapter.Companion.TypeLambdaValue
import org.phoenixframework.liveview.ui.view.ExposedDropdownMenuBoxScopeWrapper


internal object ModifiersParser {
    private val modifiersCacheTable = mutableMapOf<String, List<Modifier>>()

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

    internal fun Modifier.fromStyleName(
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
        val mapEntryContext = mapContext?.map_entries()?.map_entry()?.map { mapEntryContext ->
            // The map key is the style name and the map value contain the list of modifiers
            val styleName = mapEntryContext.expression(0).text
                .removePrefix("\"")
                .removeSuffix("\"")
                .replace("\\\"", "\"") // replacing backslash quotes by quotes
            val mapValueContext = mapEntryContext.expression(1)

            // Map value must be a list of tuples
            val mapValueAsListContext: ListContext
            if (mapValueContext is ListExprContext && mapValueContext.list().expressions_()
                    .expression().all { it is TupleExprContext }
            ) {
                mapValueAsListContext = mapValueContext.list()
            } else {
                return null
            }

            // Each tuple of this list is a modifier.
            styleName to mapValueAsListContext.expressions_().expression()
                .filterIsInstance<TupleExprContext>()
        }
        return mapEntryContext
    }

    internal fun parseElixirContent(fileContent: String): ElixirParser.ExpressionContext? {
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
        return elixirParser.parse()?.block()?.expression()?.firstOrNull()
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    private fun handleModifier(
        modifierId: String,
        arguments: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?,
        pushEvent: PushEvent?
    ): Modifier? {
        if (arguments.any { it.type == TypeLambdaValue } && scope == null)
            return Modifier.placeholderModifier(modifierId, arguments)

        val argListContext = parseArguments(arguments)

        parseScopedModifiers(modifierId, argListContext, scope)?.let {
            return it
        }
        parseActionModifiers(modifierId, argListContext, pushEvent)?.let {
            return it
        }
        parseNonParamsModifiers(modifierId)?.let {
            return it
        }
        parseStaticModifiers(modifierId, argListContext)?.let {
            return it
        }
        return null
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    private fun parseStaticModifiers(
        modifierId: String,
        argListContext: List<ModifierDataAdapter.ArgumentData>,
    ): Modifier? {
        return when (modifierId) {
            modifierAbsoluteOffset -> Modifier.absoluteOffsetFromStyle(argListContext)
            modifierAbsolutePadding -> Modifier.absolutePaddingFromStyle(argListContext)
            modifierAlpha -> Modifier.alphaFromStyle(argListContext)
            modifierAspectRatio -> Modifier.aspectRatioFromStyle(argListContext)
            modifierBackground -> Modifier.backgroundFromStyle(argListContext)
            modifierBorder -> Modifier.borderFromStyle(argListContext)
            modifierClip -> Modifier.clipFromStyle(argListContext)
            modifierDefaultMinSize -> Modifier.defaultMinSizeFromStyle(argListContext)
            modifierFillMaxHeight -> Modifier.fillMaxHeightFromStyle(argListContext)
            modifierFillMaxSize -> Modifier.fillMaxSizeFromStyle(argListContext)
            modifierFillMaxWidth -> Modifier.fillMaxWidthFromStyle(argListContext)
            modifierFocusable -> Modifier.focusableFromStyle(argListContext)
            modifierHeight -> Modifier.heightFromStyle(argListContext)
            modifierHeightIn -> Modifier.heightInFromStyle(argListContext)
            modifierHorizontalScroll -> Modifier.horizontalScrollFromStyle(argListContext)
            modifierLayoutId -> Modifier.layoutIdFromStyle(argListContext)
            modifierOffset -> Modifier.offsetFromStyle(argListContext)
            modifierPadding -> Modifier.paddingFromStyle(argListContext)
            modifierPaddingFrom -> Modifier.paddingFromFromStyle(argListContext)
            modifierPaddingFromBaseline -> Modifier.paddingFromBaselineFromStyle(argListContext)
            modifierProgressSemantics -> Modifier.progressSemanticsFromStyle(argListContext)
            modifierRequiredHeight -> Modifier.requiredHeightFromStyle(argListContext)
            modifierRequiredHeightIn -> Modifier.requiredHeightInFromStyle(argListContext)
            modifierRequiredSize -> Modifier.requiredSizeFromStyle(argListContext)
            modifierRequiredSizeIn -> Modifier.requiredSizeInFromStyle(argListContext)
            modifierRequiredWidth -> Modifier.requiredWidthFromStyle(argListContext)
            modifierRequiredWidthIn -> Modifier.requiredWidthInFromStyle(argListContext)
            modifierRotate -> Modifier.rotateFromStyle(argListContext)
            modifierScale -> Modifier.scaleFromStyle(argListContext)
            modifierShadow -> Modifier.shadowFromStyle(argListContext)
            modifierSize -> Modifier.sizeFromStyle(argListContext)
            modifierSizeIn -> Modifier.sizeInFromStyle(argListContext)
            modifierTestTag -> Modifier.testTagFromStyle(argListContext)
            modifierVerticalScroll -> Modifier.verticalScrollFromStyle(argListContext)
            modifierWindowInsetsBottomHeight -> Modifier.windowInsetsBottomHeightFromStyle(
                argListContext
            )

            modifierWindowInsetsEndWidth -> Modifier.windowInsetsEndWidthFromStyle(argListContext)
            modifierWindowInsetsStartWidth -> Modifier.windowInsetsStartWidthFromStyle(
                argListContext
            )

            modifierWindowInsetsTopHeight -> Modifier.windowInsetsTopHeightFromStyle(argListContext)
            modifierWidth -> Modifier.widthFromStyle(argListContext)
            modifierWidthIn -> Modifier.widthInFromStyle(argListContext)
            modifierWindowInsetsPadding -> Modifier.windowInsetsPaddingFromStyle(argListContext)
            modifierWrapContentHeight -> Modifier.wrapContentHeightFromStyle(argListContext)
            modifierWrapContentSize -> Modifier.wrapContentSizeFromStyle(argListContext)
            modifierWrapContentWidth -> Modifier.wrapContentWidthFromStyle(argListContext)
            modifierZIndex -> Modifier.zIndexFromStyle(argListContext)
            else -> null
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    private fun parseActionModifiers(
        modifierId: String,
        argListContext: List<ModifierDataAdapter.ArgumentData>,
        pushEvent: PushEvent?
    ): Modifier? {
        fun modifierOrPlaceholder(modifier: Modifier) = if (pushEvent != null)
            modifier
        else
            Modifier.placeholderModifier(modifierId, argListContext)

        return when (modifierId) {
            modifierAnimateContentSize ->
                Modifier.animateContentSizeFromStyle(argListContext, pushEvent)

            modifierClickable -> modifierOrPlaceholder(
                Modifier.clickableFromStyle(argListContext, pushEvent)
            )

            modifierCombinedClickable -> modifierOrPlaceholder(
                Modifier.combinedClickableFromStyle(argListContext, pushEvent)
            )

            modifierOnFocusedBoundsChanged -> modifierOrPlaceholder(
                Modifier.onFocusedBoundsChangedFromStyle(argListContext, pushEvent)
            )

            modifierOnFocusChanged -> modifierOrPlaceholder(
                Modifier.onFocusChangedFromStyle(argListContext, pushEvent)
            )

            modifierOnFocusEvent -> modifierOrPlaceholder(
                Modifier.onFocusEventFromStyle(argListContext, pushEvent)
            )

            modifierSelectable -> modifierOrPlaceholder(
                Modifier.selectableFromStyle(argListContext, pushEvent)
            )

            modifierToggleable -> modifierOrPlaceholder(
                Modifier.toggleableFromStyle(argListContext, pushEvent)
            )

            modifierTriStateToggleable -> modifierOrPlaceholder(
                Modifier.triStateToggleableFromStyle(argListContext, pushEvent)
            )

            else -> null
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("ModifierFactoryExtensionFunction")
    private fun parseScopedModifiers(
        modifierId: String,
        argListContext: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?,
    ): Modifier? {
        return when (modifierId) {
            modifierMenuAnchor -> {
                if (scope is ExposedDropdownMenuBoxScopeWrapper) {
                    scope.scope.run {
                        Modifier.menuAnchor()
                    }

                } else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierAlign -> {
                if (scope != null) Modifier.alignFromStyle(argListContext, scope)
                else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierAlignBy -> {
                if (scope != null) Modifier.alignByFromStyle(argListContext, scope)
                else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierAlignByBaseline -> {
                if (scope != null)
                    Modifier.alignByBaselineFromStyle(scope)
                else
                    Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierAnimateEnterExit -> {
                if (scope != null)
                    Modifier.animateEnterExitFromStyle(argListContext, scope)
                else
                    Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierExposedDropdownSize -> {
                if (scope != null)
                    Modifier.exposedDropdownSizeFromStyle(argListContext, scope)
                else
                    Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierFillParentMaxHeight -> {
                if (scope != null) Modifier.fillParentMaxHeightFromStyle(argListContext, scope)
                else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierFillParentMaxSize -> {
                if (scope != null) Modifier.fillParentMaxSizeFromStyle(argListContext, scope)
                else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierFillParentMaxWidth -> {
                if (scope != null) Modifier.fillParentMaxWidthFromStyle(argListContext, scope)
                else Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierMatchParentSize -> {
                if (scope != null)
                    Modifier.matchParentSizeFromStyle(scope)
                else
                    Modifier.placeholderModifier(modifierId, argListContext)
            }

            modifierWeight -> {
                if (scope != null)
                    Modifier.weightFromStyle(argListContext, scope)
                else
                    Modifier.placeholderModifier(modifierId, argListContext)
            }

            else -> null
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @OptIn(ExperimentalLayoutApi::class)
    private fun parseNonParamsModifiers(modifierId: String): Modifier? {
        return when (modifierId) {
            modifierCaptionBarPadding -> Modifier.captionBarPadding()
            modifierClipToBounds -> Modifier.clipToBounds()
            modifierDisplayCutoutPadding -> Modifier.displayCutoutPadding()
            modifierFocusTarget -> Modifier.focusTarget()
            modifierFocusGroup -> Modifier.focusGroup()
            modifierImePadding -> Modifier.imePadding()
            modifierImeNestedScroll -> Modifier.imeNestedScroll()
            modifierMandatorySystemGesturesPadding -> Modifier.mandatorySystemGesturesPadding()
            modifierMinimumInteractiveComponentSize -> Modifier.minimumInteractiveComponentSize()
            modifierNavigationBarsPadding -> Modifier.navigationBarsPadding()
            modifierPreferKeepClear -> Modifier.preferKeepClear()
            modifierSafeContentPadding -> Modifier.safeContentPadding()
            modifierSafeDrawingPadding -> Modifier.safeDrawingPadding()
            modifierSafeGesturesPadding -> Modifier.safeGesturesPadding()
            modifierSelectableGroup -> Modifier.selectableGroup()
            modifierStatusBarsPadding -> Modifier.statusBarsPadding()
            modifierSystemBarsPadding -> Modifier.systemBarsPadding()
            modifierSystemGesturesPadding -> Modifier.systemGesturesPadding()
            modifierSystemGestureExclusion -> Modifier.systemGestureExclusion()
            modifierToolingGraphicsLayer -> Modifier.toolingGraphicsLayer()
            modifierWaterfallPadding -> Modifier.waterfallPadding()
            else -> null
        }
    }

    private fun parseArguments(
        arguments: List<ModifierDataAdapter.ArgumentData>,
    ): List<ModifierDataAdapter.ArgumentData> {
        return arguments.map {
            // Lambda value must have two arguments: Data type and ViewId
            if (it.type == TypeLambdaValue && it.listValue.size == 2) {
                parseLambdaValueArgument(it)
            } else it
        }
    }

    private fun parseLambdaValueArgument(
        argument: ModifierDataAdapter.ArgumentData,
    ): ModifierDataAdapter.ArgumentData {
        val viewValue = ComposableView.getViewValue(argument.listValue[1].value.toString())
        val viewValueType = ModifierDataAdapter.typeFromClass(viewValue)

        return ModifierDataAdapter.ArgumentData(
            argument.name,
            argument.listValue[0].stringValueWithoutColon.toString(),
            listOf(
                ModifierDataAdapter.ArgumentData(
                    null,
                    viewValueType,
                    viewValue
                ),
            )
        )
    }

    private const val TAG = "ModifiersParser"
}
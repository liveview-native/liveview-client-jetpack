package org.phoenixframework.liveview.data.mappers.modifiers

import android.util.Log
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutoutPadding
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAbsoluteOffset
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAbsolutePadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlignByBaseline
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlpha
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAspectRatio
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBorder
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierCaptionBarPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClickable
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClip
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierClipToBounds
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierDefaultMinSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierDisplayCutoutPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierExposedDropdownSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillParentMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeightIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierImePadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierLayoutId
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMandatorySystemGesturesPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMatchParentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMenuAnchor
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMinimumInteractiveComponentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierNavigationBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierOffset
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPaddingFrom
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPaddingFromBaseline
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
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierShadow
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSizeIn
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierStatusBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSystemBarsPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSystemGesturesPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierTestTag
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
import org.phoenixframework.liveview.domain.base.PushEvent
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

    fun Modifier.fromStyle(
        string: String,
        scope: Any? = null,
        pushEvent: PushEvent? = null
    ): Modifier {
        // Simple substring logic to extract the style name in order to check if it was parsed already
        val stylePrefix = "%{"
        val styleStart = string.indexOf(stylePrefix)
        val styleEnd = string.indexOf("=>")
        if (styleStart < 0 || styleEnd <= styleStart) {
            return this
        }
        val styleKey = string.substring(
            startIndex = styleStart + stylePrefix.length,
            endIndex = styleEnd - 1
        ).trim().replace("\"", "").replace("'", "")
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
                            scope,
                            pushEvent,
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

    @OptIn(ExperimentalMaterial3Api::class)
    private fun Modifier.handlerModifier(
        modifierId: String,
        argListContext: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?,
        pushEvent: PushEvent?
    ): Modifier {
        return when (modifierId) {
            // No param modifiers
            modifierCaptionBarPadding -> this.then(Modifier.captionBarPadding())
            modifierClipToBounds -> this.then(Modifier.clipToBounds())
            modifierDisplayCutoutPadding -> this.then(Modifier.displayCutoutPadding())
            modifierImePadding -> this.then(Modifier.imePadding())
            modifierMandatorySystemGesturesPadding -> this.then(Modifier.mandatorySystemGesturesPadding())
            modifierMenuAnchor -> {
                if (scope is ExposedDropdownMenuBoxScope) {
                    scope.run {
                        this@handlerModifier.then(Modifier.menuAnchor())
                    }

                } else this
            }

            modifierMinimumInteractiveComponentSize -> this.then(Modifier.minimumInteractiveComponentSize())
            modifierNavigationBarsPadding -> this.then(Modifier.navigationBarsPadding())
            modifierSafeContentPadding -> this.then(Modifier.safeContentPadding())
            modifierSafeDrawingPadding -> this.then(Modifier.safeDrawingPadding())
            modifierSafeGesturesPadding -> this.then(Modifier.safeGesturesPadding())
            modifierStatusBarsPadding -> this.then(Modifier.statusBarsPadding())
            modifierSystemBarsPadding -> this.then(Modifier.systemBarsPadding())
            modifierSystemGesturesPadding -> this.then(Modifier.systemGesturesPadding())
            modifierWaterfallPadding -> this.then(Modifier.waterfallPadding())
            // Parameterized modifiers
            modifierAbsoluteOffset -> this.then(absoluteOffsetFromStyle(argListContext))
            modifierAbsolutePadding -> this.then(absolutePaddingFromStyle(argListContext))
            modifierAlpha -> this.then(alphaFromStyle(argListContext))
            modifierAlign -> this.then(alignFromStyle(argListContext, scope))
            modifierAlignByBaseline -> this.then(alignByBaselineFromStyle(scope))
            modifierAspectRatio -> this.then(aspectRatioFromStyle(argListContext))
            modifierBackground -> this.then(backgroundFromStyle(argListContext))
            modifierBorder -> this.then(borderFromStyle(argListContext))
            modifierClickable -> this.then(clickableFromStyle(argListContext, pushEvent))
            modifierClip -> this.then(clipFromStyle(argListContext))
            modifierDefaultMinSize -> this.then(defaultMinSizeFromStyle(argListContext))
            modifierExposedDropdownSize -> this.then(
                exposedDropdownSizeFromStyle(
                    argListContext,
                    scope
                )
            )

            modifierFillParentMaxHeight -> this.then(
                fillParentMaxHeightFromStyle(
                    argListContext,
                    scope
                )
            )

            modifierFillParentMaxSize -> this.then(
                fillParentMaxSizeFromStyle(
                    argListContext,
                    scope
                )
            )

            modifierFillParentMaxWidth -> this.then(
                fillParentMaxWidthFromStyle(
                    argListContext,
                    scope
                )
            )

            modifierFillMaxHeight -> this.then(fillMaxHeightFromStyle(argListContext))
            modifierFillMaxSize -> this.then(fillMaxSizeFromStyle(argListContext))
            modifierFillMaxWidth -> this.then(fillMaxWidthFromStyle(argListContext))
            modifierHeight -> this.then(heightFromStyle(argListContext))
            modifierHeightIn -> this.then(heightInFromStyle(argListContext))
            modifierLayoutId -> this.then(layoutIdFromStyle(argListContext))
            modifierMatchParentSize -> this.then(matchParentSizeFromStyle(scope))
            modifierOffset -> this.then(offsetFromStyle(argListContext))
            modifierPadding -> this.then(paddingFromStyle(argListContext))
            modifierPaddingFrom -> this.then(paddingFromFromStyle(argListContext))
            modifierPaddingFromBaseline -> this.then(paddingFromBaselineFromStyle(argListContext))
            modifierProgressSemantics -> this.then(progressSemanticsFromStyle(argListContext))
            modifierRequiredHeight -> this.then(requiredHeightFromStyle(argListContext))
            modifierRequiredHeightIn -> this.then(requiredHeightInFromStyle(argListContext))
            modifierRequiredSize -> this.then(requiredSizeFromStyle(argListContext))
            modifierRequiredSizeIn -> this.then(requiredSizeInFromStyle(argListContext))
            modifierRequiredWidth -> this.then(requiredWidthFromStyle(argListContext))
            modifierRequiredWidthIn -> this.then(requiredWidthInFromStyle(argListContext))
            modifierRotate -> this.then(rotateFromStyle(argListContext))
            modifierScale -> this.then(scaleFromStyle(argListContext))
            modifierShadow -> this.then(shadowFromStyle(argListContext))
            modifierSize -> this.then(sizeFromStyle(argListContext))
            modifierSizeIn -> this.then(sizeInFromStyle(argListContext))
            modifierTestTag -> this.then(testTagFromStyle(argListContext))
            modifierWeight -> this.then(weightFromStyle(argListContext, scope))
            modifierWindowInsetsBottomHeight -> this.then(
                windowInsetsBottomHeightFromStyle(
                    argListContext
                )
            )

            modifierWindowInsetsEndWidth -> this.then(windowInsetsEndWidthFromStyle(argListContext))
            modifierWindowInsetsStartWidth -> this.then(
                windowInsetsStartWidthFromStyle(
                    argListContext
                )
            )

            modifierWindowInsetsTopHeight -> this.then(windowInsetsTopHeightFromStyle(argListContext))
            modifierWidth -> this.then(widthFromStyle(argListContext))
            modifierWidthIn -> this.then(widthInFromStyle(argListContext))
            modifierWindowInsetsPadding -> this.then(windowInsetsPaddingFromStyle(argListContext))
            modifierWrapContentHeight -> this.then(wrapContentHeightFromStyle(argListContext))
            modifierWrapContentSize -> this.then(wrapContentSizeFromStyle(argListContext))
            modifierWrapContentWidth -> this.then(wrapContentWidthFromStyle(argListContext))
            modifierZIndex -> this.then(zIndexFromStyle(argListContext))
            else -> this
        }
    }

    private const val TAG = "ModifiersParser"
}
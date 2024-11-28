package org.phoenixframework.liveview.ui.modifiers

import android.annotation.SuppressLint
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
import org.phoenixframework.liveview.constants.ModifierNames.modifierAbsoluteOffset
import org.phoenixframework.liveview.constants.ModifierNames.modifierAbsolutePadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.constants.ModifierNames.modifierAlignBy
import org.phoenixframework.liveview.constants.ModifierNames.modifierAlignByBaseline
import org.phoenixframework.liveview.constants.ModifierNames.modifierAlpha
import org.phoenixframework.liveview.constants.ModifierNames.modifierAnimateContentSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierAnimateEnterExit
import org.phoenixframework.liveview.constants.ModifierNames.modifierAspectRatio
import org.phoenixframework.liveview.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.constants.ModifierNames.modifierBorder
import org.phoenixframework.liveview.constants.ModifierNames.modifierCaptionBarPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierClickable
import org.phoenixframework.liveview.constants.ModifierNames.modifierClip
import org.phoenixframework.liveview.constants.ModifierNames.modifierClipToBounds
import org.phoenixframework.liveview.constants.ModifierNames.modifierCombinedClickable
import org.phoenixframework.liveview.constants.ModifierNames.modifierDefaultMinSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierDisplayCutoutPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierExposedDropdownSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillMaxHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillParentMaxHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillParentMaxSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillParentMaxWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierFocusGroup
import org.phoenixframework.liveview.constants.ModifierNames.modifierFocusTarget
import org.phoenixframework.liveview.constants.ModifierNames.modifierFocusable
import org.phoenixframework.liveview.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierHeightIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierHorizontalScroll
import org.phoenixframework.liveview.constants.ModifierNames.modifierImeNestedScroll
import org.phoenixframework.liveview.constants.ModifierNames.modifierImePadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierLayoutId
import org.phoenixframework.liveview.constants.ModifierNames.modifierMandatorySystemGesturesPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierMatchParentSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierMenuAnchor
import org.phoenixframework.liveview.constants.ModifierNames.modifierMinimumInteractiveComponentSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierNavigationBarsPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierOffset
import org.phoenixframework.liveview.constants.ModifierNames.modifierOnFocusChanged
import org.phoenixframework.liveview.constants.ModifierNames.modifierOnFocusEvent
import org.phoenixframework.liveview.constants.ModifierNames.modifierOnFocusedBoundsChanged
import org.phoenixframework.liveview.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierPaddingFrom
import org.phoenixframework.liveview.constants.ModifierNames.modifierPaddingFromBaseline
import org.phoenixframework.liveview.constants.ModifierNames.modifierPreferKeepClear
import org.phoenixframework.liveview.constants.ModifierNames.modifierProgressSemantics
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredHeightIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredSizeIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierRequiredWidthIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierRotate
import org.phoenixframework.liveview.constants.ModifierNames.modifierSafeContentPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierSafeDrawingPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierSafeGesturesPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierScale
import org.phoenixframework.liveview.constants.ModifierNames.modifierSelectable
import org.phoenixframework.liveview.constants.ModifierNames.modifierSelectableGroup
import org.phoenixframework.liveview.constants.ModifierNames.modifierShadow
import org.phoenixframework.liveview.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierSizeIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierStatusBarsPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierSystemBarsPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierSystemGestureExclusion
import org.phoenixframework.liveview.constants.ModifierNames.modifierSystemGesturesPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierTestTag
import org.phoenixframework.liveview.constants.ModifierNames.modifierToggleable
import org.phoenixframework.liveview.constants.ModifierNames.modifierToolingGraphicsLayer
import org.phoenixframework.liveview.constants.ModifierNames.modifierTriStateToggleable
import org.phoenixframework.liveview.constants.ModifierNames.modifierVerticalScroll
import org.phoenixframework.liveview.constants.ModifierNames.modifierWaterfallPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierWidthIn
import org.phoenixframework.liveview.constants.ModifierNames.modifierWindowInsetsBottomHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierWindowInsetsEndWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierWindowInsetsPadding
import org.phoenixframework.liveview.constants.ModifierNames.modifierWindowInsetsStartWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierWindowInsetsTopHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierWrapContentHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierWrapContentSize
import org.phoenixframework.liveview.constants.ModifierNames.modifierWrapContentWidth
import org.phoenixframework.liveview.constants.ModifierNames.modifierZIndex
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter.Companion.TYPE_LAMBDA_VALUE
import org.phoenixframework.liveview.foundation.ui.modifiers.placeholderModifier
import org.phoenixframework.liveview.ui.view.ExposedDropdownMenuBoxScopeWrapper


class ModifiersParser : BaseModifiersParser() {

    override var mustLoadModifiersFile: Boolean = true

    @SuppressLint("ModifierFactoryExtensionFunction")
    override fun handleModifier(
        modifierId: String,
        arguments: List<ModifierDataAdapter.ArgumentData>,
        scope: Any?,
        pushEvent: PushEvent?
    ): Modifier? {
        if (arguments.any { it.type == TYPE_LAMBDA_VALUE } && scope == null)
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
            if (it.type == TYPE_LAMBDA_VALUE && it.listValue.size == 2) {
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
}
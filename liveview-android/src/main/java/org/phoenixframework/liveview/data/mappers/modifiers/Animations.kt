package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import org.phoenixframework.liveview.data.constants.ModifierArgs
import org.phoenixframework.liveview.data.constants.ModifierArgs.argAnimationSpec
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEnter
import org.phoenixframework.liveview.data.constants.ModifierArgs.argExit
import org.phoenixframework.liveview.data.dto.onClickFromString
import org.phoenixframework.liveview.domain.base.PushEvent

fun Modifier.animateContentSizeFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    pushEvent: PushEvent?
): Modifier {
    val modifierArgs = argsOrNamedArgs(arguments)
    val animationFunction = argOrNamedArg(modifierArgs, argAnimationSpec, 0)
    val animationSpec = animationFunction?.let {
        finiteAnimationSpecFromArg(it)
    } ?: spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntSize.VisibilityThreshold
    )
    val event = argOrNamedArg(modifierArgs, ModifierArgs.argFinishedListener, 1)?.let {
        eventFromArgument(it)
    }
    return this.then(
        Modifier.animateContentSize(
            animationSpec = animationSpec,
            finishedListener = event?.let {
                { initialValue, targetValue ->
                    val (eventName, _) = it
                    onClickFromString(
                        pushEvent, eventName, mapOf(
                            "initialWidth" to initialValue.width,
                            "initialHeight" to initialValue.height,
                            "targetWidth" to targetValue.width,
                            "targetHeight" to targetValue.height
                        )
                    ).invoke()
                }
            }
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun Modifier.animateEnterExitFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?,
): Modifier {
    val modifierArgs = argsOrNamedArgs(arguments)
    val enterTransition = argOrNamedArg(modifierArgs, argEnter, 0)?.let {
        enterTransitionFromArgument(it)
    } ?: (fadeIn() + expandIn())
    val exitTransition = argOrNamedArg(modifierArgs, argExit, 1)?.let {
        exitTransitionFromArgument(it)
    } ?: (fadeOut() + shrinkOut())
    return if (scope is AnimatedVisibilityScope) {
        scope.run {
            this@animateEnterExitFromStyle.then(
                Modifier.animateEnterExit(
                    enter = enterTransition,
                    exit = exitTransition
                )
            )
        }
    } else this
}
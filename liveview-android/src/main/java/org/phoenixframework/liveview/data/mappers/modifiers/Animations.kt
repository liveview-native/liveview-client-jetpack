package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import org.phoenixframework.liveview.data.constants.ModifierArgs
import org.phoenixframework.liveview.data.constants.ModifierArgs.argAnimationSpec
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
        eventFromStyle(it)
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
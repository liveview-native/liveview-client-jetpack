package org.phoenixframework.liveview.data.mappers.modifiers

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import org.phoenixframework.liveview.data.constants.ModifierArgs.argAnimationSpec

fun Modifier.animateContentSizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val modifierArgs = argsOrNamedArgs(arguments)
    val animationFunction = argOrNamedArg(modifierArgs, argAnimationSpec, 0)
    val animationSpec = animationFunction?.let {
        finiteAnimationSpecFromArg(it)
    } ?: spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntSize.VisibilityThreshold
    )
    return this.then(
        Modifier.animateContentSize(
            animationSpec = animationSpec,
        )
    )
}
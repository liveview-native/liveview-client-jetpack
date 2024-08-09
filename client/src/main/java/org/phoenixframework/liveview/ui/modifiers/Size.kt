package org.phoenixframework.liveview.ui.modifiers

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import org.phoenixframework.liveview.constants.ModifierArgs.argAlign
import org.phoenixframework.liveview.constants.ModifierArgs.argFill
import org.phoenixframework.liveview.constants.ModifierArgs.argFraction
import org.phoenixframework.liveview.constants.ModifierArgs.argHeight
import org.phoenixframework.liveview.constants.ModifierArgs.argInsets
import org.phoenixframework.liveview.constants.ModifierArgs.argIntrinsicSize
import org.phoenixframework.liveview.constants.ModifierArgs.argMatchHeightConstraintFirst
import org.phoenixframework.liveview.constants.ModifierArgs.argMatchTextFieldWidth
import org.phoenixframework.liveview.constants.ModifierArgs.argMax
import org.phoenixframework.liveview.constants.ModifierArgs.argMaxHeight
import org.phoenixframework.liveview.constants.ModifierArgs.argMaxWidth
import org.phoenixframework.liveview.constants.ModifierArgs.argMin
import org.phoenixframework.liveview.constants.ModifierArgs.argMinHeight
import org.phoenixframework.liveview.constants.ModifierArgs.argMinWidth
import org.phoenixframework.liveview.constants.ModifierArgs.argRatio
import org.phoenixframework.liveview.constants.ModifierArgs.argSize
import org.phoenixframework.liveview.constants.ModifierArgs.argUnbounded
import org.phoenixframework.liveview.constants.ModifierArgs.argWeight
import org.phoenixframework.liveview.constants.ModifierArgs.argWidth
import org.phoenixframework.liveview.constants.ModifierTypes.typeDpSize
import org.phoenixframework.liveview.foundation.ui.modifiers.ModifierDataAdapter
import org.phoenixframework.liveview.ui.view.alignmentFromString
import org.phoenixframework.liveview.ui.view.horizontalAlignmentFromString
import org.phoenixframework.liveview.ui.view.verticalAlignmentFromString

fun Modifier.aspectRatioFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)
    val ratio = argOrNamedArg(params, argRatio, 0)?.floatValue
    val matchConstraints = argOrNamedArg(params, argMatchHeightConstraintFirst, 1)?.booleanValue
    return ratio?.let {
        this.then(Modifier.aspectRatio(it, matchConstraints ?: false))
    } ?: this
}

fun Modifier.defaultMinSizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)
    val minWidth = argOrNamedArg(params, argMinWidth, 0)?.let { dpFromArgument(it) }
    val minHeight = argOrNamedArg(params, argMinHeight, 1)?.let { dpFromArgument(it) }
    return this.then(
        Modifier.defaultMinSize(
            minWidth = minWidth ?: Dp.Unspecified,
            minHeight = minHeight ?: Dp.Unspecified,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun Modifier.exposedDropdownSizeFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    return if (scope is ExposedDropdownMenuBoxScope) {
        val params = argsOrNamedArgs(arguments)
        val matchTextFieldWidth = argOrNamedArg(params, argMatchTextFieldWidth, 0)?.booleanValue
        scope.run {
            this@exposedDropdownSizeFromStyle.then(
                Modifier.exposedDropdownSize(matchTextFieldWidth = matchTextFieldWidth ?: true)
            )
        }
    } else this
}

fun Modifier.fillMaxHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    return this.then(
        if (args.isEmpty()) Modifier.fillMaxHeight() else {
            val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
            Modifier.fillMaxHeight(fraction)
        }
    )
}

fun Modifier.fillMaxSizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    return this.then(
        if (args.isEmpty()) Modifier.fillMaxSize() else {
            val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
            Modifier.fillMaxSize(fraction)
        }
    )
}

fun Modifier.fillMaxWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    return this.then(
        if (args.isEmpty()) Modifier.fillMaxWidth() else {
            val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
            Modifier.fillMaxWidth(fraction)
        }
    )
}

fun Modifier.fillParentMaxHeightFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
    return when (scope) {
        is LazyItemScope -> {
            scope.run {
                this@fillParentMaxHeightFromStyle.then(
                    Modifier.fillParentMaxHeight(fraction)
                )
            }
        }

        else -> this
    }
}

fun Modifier.fillParentMaxSizeFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
    return when (scope) {
        is LazyItemScope -> {
            scope.run {
                this@fillParentMaxSizeFromStyle.then(
                    Modifier.fillParentMaxSize(fraction)
                )
            }
        }

        else -> this
    }
}

fun Modifier.fillParentMaxWidthFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val args = argsOrNamedArgs(arguments)
    val fraction = argOrNamedArg(args, argFraction, 0)?.floatValue ?: 1f
    return when (scope) {
        is LazyItemScope -> {
            scope.run {
                this@fillParentMaxWidthFromStyle.then(
                    Modifier.fillParentMaxWidth(fraction)
                )
            }
        }

        else -> this
    }
}

fun Modifier.heightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val height = argOrNamedArg(args, argHeight, 0)?.let { dpFromArgument(it) }
    if (height != null) {
        return this.then(Modifier.height(height))
    }
    val intrinsicSize =
        argOrNamedArg(args, argIntrinsicSize, 0)?.let { intrinsicSizeFromArgument(it) }
    if (intrinsicSize != null) {
        return this.then(Modifier.height(intrinsicSize = intrinsicSize))
    }
    return this
}

fun Modifier.heightInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val min = argOrNamedArg(args, argMin, 0)?.let { dpFromArgument(it) }
    val max = argOrNamedArg(args, argMax, 1)?.let { dpFromArgument(it) }
    return this.then(
        Modifier.heightIn(
            min = min ?: Dp.Unspecified,
            max = max ?: Dp.Unspecified
        )
    )
}

fun Modifier.matchParentSizeFromStyle(scope: Any?): Modifier {
    return when (scope) {
        is BoxScope -> {
            scope.run {
                this@matchParentSizeFromStyle.then(
                    Modifier.matchParentSize()
                )
            }
        }

        else -> this
    }
}

fun Modifier.requiredHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val reqHeightInDp = argOrNamedArg(args, argHeight, 0)?.let { dpFromArgument(it) }
    if (reqHeightInDp != null) {
        return this.then(Modifier.requiredHeight(height = reqHeightInDp))
    }
    val reqIntrinsicSize =
        argOrNamedArg(args, argIntrinsicSize, 0)?.let { intrinsicSizeFromArgument(it) }
    if (reqIntrinsicSize != null) {
        return this.then(Modifier.requiredHeight(intrinsicSize = reqIntrinsicSize))
    }
    return this
}

fun Modifier.requiredHeightInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val min = argOrNamedArg(args, argMin, 0)?.let { dpFromArgument(it) }
    val max = argOrNamedArg(args, argMax, 1)?.let { dpFromArgument(it) }
    if (min != null || max != null) {
        return this.then(
            Modifier.requiredHeightIn(
                min = min ?: Dp.Unspecified,
                max = max ?: Dp.Unspecified
            )
        )
    }
    return this
}

fun Modifier.requiredSizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    return when (args.size) {
        1 -> {
            argOrNamedArg(args, argSize, 0)?.let { reqSize ->
                if (reqSize.type == typeDpSize) {
                    dpSizeFromArgument(reqSize)?.let { reqSizeInDpSize ->
                        this.then(Modifier.requiredSize(reqSizeInDpSize))
                    }
                } else {
                    dpFromArgument(reqSize)?.let { reqSizeInDp ->
                        this.then(Modifier.requiredSize(reqSizeInDp))
                    }
                }
            } ?: this
        }

        2 -> {
            val width = argOrNamedArg(args, argWidth, 0)?.let { dpFromArgument(it) }
            val height = argOrNamedArg(args, argHeight, 1)?.let { dpFromArgument(it) }
            if (width != null && height != null) {
                this.then(Modifier.requiredSize(width = width, height = height))
            } else this
        }

        else -> this
    }
}

fun Modifier.requiredSizeInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val minWidth = argOrNamedArg(args, argMinWidth, 0)?.let { dpFromArgument(it) }
    val minHeight = argOrNamedArg(args, argMinHeight, 1)?.let { dpFromArgument(it) }
    val maxWidth = argOrNamedArg(args, argMaxWidth, 2)?.let { dpFromArgument(it) }
    val maxHeight = argOrNamedArg(args, argMaxHeight, 3)?.let { dpFromArgument(it) }
    return this.then(
        Modifier.requiredSizeIn(
            minWidth = minWidth ?: Dp.Unspecified,
            minHeight = minHeight ?: Dp.Unspecified,
            maxWidth = maxWidth ?: Dp.Unspecified,
            maxHeight = maxHeight ?: Dp.Unspecified,
        )
    )
}

fun Modifier.requiredWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val reqWidthInDp = argOrNamedArg(args, argWidth, 0)?.let { dpFromArgument(it) }
    if (reqWidthInDp != null) {
        return this.then(Modifier.requiredWidth(width = reqWidthInDp))
    }
    val reqIntrinsicSize =
        argOrNamedArg(args, argIntrinsicSize, 0)?.let { intrinsicSizeFromArgument(it) }
    if (reqIntrinsicSize != null) {
        return this.then(Modifier.requiredWidth(intrinsicSize = reqIntrinsicSize))
    }
    return this
}

fun Modifier.requiredWidthInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val min = argOrNamedArg(args, argMin, 0)?.let { dpFromArgument(it) }
    val max = argOrNamedArg(args, argMax, 1)?.let { dpFromArgument(it) }
    if (min != null || max != null) {
        return this.then(
            Modifier.requiredWidthIn(
                min = min ?: Dp.Unspecified,
                max = max ?: Dp.Unspecified
            )
        )
    }
    return this
}

fun Modifier.sizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)

    return when (params.size) {
        1 -> {
            val sizeInDp = argOrNamedArg(params, argSize, 0)?.let { dpFromArgument(it) }
            if (sizeInDp != null) {
                this.then(Modifier.size(sizeInDp))
            } else {
                val sizeInDpSize = argOrNamedArg(params, argSize, 0)?.let { dpSizeFromArgument(it) }
                if (sizeInDpSize != null) {
                    this.then(Modifier.size(sizeInDpSize))
                } else this
            }
        }

        2 -> {
            val width = argOrNamedArg(params, argWidth, 0)?.let { dpFromArgument(it) }
            val height = argOrNamedArg(params, argHeight, 1)?.let { dpFromArgument(it) }
            if (width != null && height != null)
                this.then(Modifier.size(width, height))
            else
                this
        }

        else -> this
    }
}

fun Modifier.sizeInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val params = argsOrNamedArgs(arguments)
    val minWidth = argOrNamedArg(params, argMinWidth, 0)?.let { dpFromArgument(it) }
    val minHeight = argOrNamedArg(params, argMinHeight, 1)?.let { dpFromArgument(it) }
    val maxWidth = argOrNamedArg(params, argMaxWidth, 2)?.let { dpFromArgument(it) }
    val maxHeight = argOrNamedArg(params, argMaxHeight, 3)?.let { dpFromArgument(it) }
    return this.then(
        Modifier.sizeIn(
            minWidth = minWidth ?: Dp.Unspecified,
            minHeight = minHeight ?: Dp.Unspecified,
            maxWidth = maxWidth ?: Dp.Unspecified,
            maxHeight = maxHeight ?: Dp.Unspecified,
        )
    )
}

fun Modifier.weightFromStyle(
    arguments: List<ModifierDataAdapter.ArgumentData>,
    scope: Any?
): Modifier {
    val params = argsOrNamedArgs(arguments)
    val weight = argOrNamedArg(params, argWeight, 0)?.floatValue ?: return this
    val fill = argOrNamedArg(params, argFill, 1)?.booleanValue
    return when (scope) {
        is RowScope ->
            scope.run {
                this@weightFromStyle.then(
                    Modifier.weight(weight, fill ?: true)
                )
            }

        is ColumnScope ->
            scope.run {
                this@weightFromStyle.then(
                    Modifier.weight(weight, fill ?: true)
                )
            }

        else -> this
    }
}

fun Modifier.windowInsetsBottomHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val insets = argOrNamedArg(args, argInsets, 0)?.let { windowInsetsFromArgument(it) }
    return if (insets != null) {
        this.then(Modifier.windowInsetsBottomHeight(insets))
    } else this
}

fun Modifier.windowInsetsEndWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val insets = argOrNamedArg(args, argInsets, 0)?.let { windowInsetsFromArgument(it) }
    return if (insets != null) {
        this.then(Modifier.windowInsetsEndWidth(insets))
    } else this
}

fun Modifier.windowInsetsStartWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val insets = argOrNamedArg(args, argInsets, 0)?.let { windowInsetsFromArgument(it) }
    return if (insets != null) {
        this.then(Modifier.windowInsetsStartWidth(insets))
    } else this
}

fun Modifier.windowInsetsTopHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val insets = argOrNamedArg(args, argInsets, 0)?.let { windowInsetsFromArgument(it) }
    return if (insets != null) {
        this.then(Modifier.windowInsetsTopHeight(insets))
    } else this
}

fun Modifier.widthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val height = argOrNamedArg(args, argWidth, 0)?.let { dpFromArgument(it) }
    if (height != null) {
        return this.then(Modifier.width(height))
    }
    val intrinsicSize =
        argOrNamedArg(args, argIntrinsicSize, 0)?.let { intrinsicSizeFromArgument(it) }
    if (intrinsicSize != null) {
        return this.then(Modifier.width(intrinsicSize = intrinsicSize))
    }
    return this
}

fun Modifier.widthInFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)
    val min = argOrNamedArg(args, argMin, 0)?.let { dpFromArgument(it) }
    val max = argOrNamedArg(args, argMax, 1)?.let { dpFromArgument(it) }
    return this.then(
        Modifier.widthIn(
            min = min ?: Dp.Unspecified,
            max = max ?: Dp.Unspecified
        )
    )
}

fun Modifier.wrapContentHeightFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)

    val alignmentValue = argOrNamedArg(args, argAlign, 0)
        ?.let {
            singleArgumentObjectValue(it)?.second?.toString()
        }
        ?.let {
            verticalAlignmentFromString(it)
        } ?: Alignment.CenterVertically
    val unbounded = argOrNamedArg(args, argUnbounded, 1)?.booleanValue ?: false

    return this.then(
        Modifier.wrapContentHeight(
            align = alignmentValue,
            unbounded = unbounded,
        )
    )
}

fun Modifier.wrapContentSizeFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)

    val alignmentValue = argOrNamedArg(args, argAlign, 0)
        ?.let {
            singleArgumentObjectValue(it)?.second?.toString()
        }
        ?.let {
            alignmentFromString(it, Alignment.Center)
        } ?: Alignment.Center
    val unbounded = argOrNamedArg(args, argUnbounded, 1)?.booleanValue ?: false

    return this.then(
        Modifier.wrapContentSize(
            align = alignmentValue,
            unbounded = unbounded,
        )
    )
}

fun Modifier.wrapContentWidthFromStyle(arguments: List<ModifierDataAdapter.ArgumentData>): Modifier {
    val args = argsOrNamedArgs(arguments)

    val alignmentValue = argOrNamedArg(args, argAlign, 0)
        ?.let {
            singleArgumentObjectValue(it)?.second?.toString()
        }
        ?.let {
            horizontalAlignmentFromString(it)
        } ?: Alignment.CenterHorizontally
    val unbounded = argOrNamedArg(args, argUnbounded, 1)?.booleanValue ?: false

    return this.then(
        Modifier.wrapContentWidth(
            align = alignmentValue,
            unbounded = unbounded,
        )
    )
}
package org.phoenixframework.liveview.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.phoenixframework.liveview.data.constants.Attrs.attrActionColor
import org.phoenixframework.liveview.data.constants.Attrs.attrActionContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrActionEvent
import org.phoenixframework.liveview.data.constants.Attrs.attrActionLabel
import org.phoenixframework.liveview.data.constants.Attrs.attrActionOnNewLine
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissActionContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissEvent
import org.phoenixframework.liveview.data.constants.Attrs.attrDuration
import org.phoenixframework.liveview.data.constants.Attrs.attrMessage
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrWithDismissAction
import org.phoenixframework.liveview.data.constants.SnackbarDurationValues
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design snackbar. A Snackbar must be declared inside of a Scaffold and it is displayed
 * conditionally. The two required params are `message` and `dismissEvent`, where the second one is
 * the event name in the server responsible to update the flag responsible to show/hide the
 * Snackbar.
 * ```
 * <Snackbar
 *   message="message"
 *   dismissEvent="hideDialog"
 * />
 * ```
 */
internal class SnackbarDTO private constructor(props: Properties) :
    ComposableView<SnackbarDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val actionOnNewLine = props.actionOnNewLine
        val shape = props.shape
        val containerColor = props.containerColor
        val contentColor = props.contentColor
        val actionColor = props.actionColor
        val actionContentColor = props.actionContentColor
        val dismissActionContentColor = props.dismissActionContentColor

        val visuals = props.visuals
        val actionEvent = props.actionEvent
        val dismissEvent = props.dismissEvent

        val snackbarData = remember(composableNode) {
            Data(
                visuals,
                pushEvent,
                props.commonProps.phxValue,
                actionEvent,
                dismissEvent,
            )
        }
        Snackbar(
            snackbarData = snackbarData,
            modifier = props.commonProps.modifier,
            actionOnNewLine = actionOnNewLine,
            shape = shape ?: SnackbarDefaults.shape,
            containerColor = containerColor ?: SnackbarDefaults.color,
            contentColor = contentColor ?: SnackbarDefaults.contentColor,
            actionColor = actionColor ?: SnackbarDefaults.actionColor,
            actionContentColor = actionContentColor ?: SnackbarDefaults.actionContentColor,
            dismissActionContentColor = dismissActionContentColor
                ?: SnackbarDefaults.dismissActionContentColor,
        )
        DisposableEffect(composableNode) {
            onDispose {
                if (!snackbarData.dismissWasCalled) {
                    dismissEvent?.let {
                        pushEvent(
                            ComposableBuilder.EVENT_TYPE_BLUR,
                            it,
                            props.commonProps.phxValue,
                            null
                        )
                    }
                }
            }
        }
    }

    @Stable
    internal data class Properties(
        val actionOnNewLine: Boolean,
        val shape: Shape?,
        val containerColor: Color?,
        val contentColor: Color?,
        val actionColor: Color?,
        val actionContentColor: Color?,
        val dismissActionContentColor: Color?,
        val visuals: Visuals,
        val actionEvent: String?,
        val dismissEvent: String?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var actionOnNewLine: Boolean = false
        private var shape: Shape? = null
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var actionColor: Color? = null
        private var actionContentColor: Color? = null
        private var dismissActionContentColor: Color? = null
        private var actionEvent: String? = null
        private var dismissEvent: String? = null
        var visuals: Visuals = Visuals()
            private set

        /**
         * Whether or not action should be put on a separate line. Recommended for action with
         * long action text.
         * ```
         * <Snackbar actionOnNewLine="true" />
         * ```
         * @param actionOnNewLine true if action should be put on a separate line, false otherwise.
         */
        fun actionOnNewLine(actionOnNewLine: String) = apply {
            this.actionOnNewLine = actionOnNewLine.toBoolean()
        }

        /**
         * Defines the shape of this snackbar's container.
         * ```
         * <Snackbar shape="16" />
         * ```
         * @param shape snackbar's shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * The color used for the background of this snackbar.
         * ```
         * <Snackbar containerColor="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside this snackbar.
         * ```
         * <Snackbar contentColor="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar actionColor="#FF000000" />
         * ```
         * @param actionColor the action color in AARRGGBB format or one of
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun actionColor(actionColor: String) = apply {
            this.actionColor = actionColor.toColor()
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar actionContentColor="#FF00FF00" />
         * ```
         * @param actionContentColor the action color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun actionContentColor(actionContentColor: String) = apply {
            this.actionContentColor = actionContentColor.toColor()
        }

        /**
         * The preferred content color for the optional dismiss action inside this snackbar.
         * ```
         * <Snackbar dismissActionContentColor="#FF00FF00" />
         * ```
         * @param dismissActionContentColor the color for the optional dismiss action in AARRGGBB
         * format or one of the [org.phoenixframework.liveview.data.constants.SystemColorValues]
         * colors.
         */
        fun dismissActionContentColor(dismissActionContentColor: String) = apply {
            this.dismissActionContentColor = dismissActionContentColor.toColor()
        }

        /**
         * Optional action label to show as button in the Snackbar.
         * ```
         * <Snackbar actionLabel="Action" />
         * ```
         * @param label action label to show as button in the Snackbar
         */
        fun actionLabel(label: String) = apply {
            this.visuals = visuals.copy(snackbarLabel = label)
        }

        /**
         * Duration of the Snackbar
         * ```
         * <Snackbar duration="short" />
         * ```
         * @param duration duration of the Snackbar. See the supported values at
         * [org.phoenixframework.liveview.data.constants.SnackbarDurationValues].
         */
        fun duration(duration: String) = apply {
            this.visuals = visuals.copy(
                snackbarDuration = when (duration) {
                    SnackbarDurationValues.indefinite -> SnackbarDuration.Indefinite
                    SnackbarDurationValues.long -> SnackbarDuration.Long
                    else -> SnackbarDuration.Short
                }
            )
        }

        /**
         * Text to be shown in the Snackbar.
         * ```
         * <Snackbar message="Message" />
         * ```
         * @param message text to be shown.
         */
        fun message(message: String) = apply {
            this.visuals = visuals.copy(snackbarMessage = message)
        }

        /**
         * A boolean to show a dismiss action in the Snackbar. This is recommended to be set to
         * true better accessibility when a Snackbar duration is set `indefinite`.
         * ```
         * <Snackbar withDismissAction="true" />
         * ```
         * @param withDismissAction true to show dismiss action, false otherwise.
         */
        fun withDismissAction(withDismissAction: String) = apply {
            this.visuals = visuals.copy(snackbarWithDismissAction = withDismissAction.toBoolean())
        }

        /**
         * Event name is the server to be called when the user presses the action in the snackbar.
         * ```
         * <Snackbar actionEvent="doSomething" />
         * ```
         * @param actionEvent event name in the server to be called in the action button.
         */
        fun actionEvent(actionEvent: String) = apply {
            this.actionEvent = actionEvent
        }

        /**
         * Event name is the server to be called when the snackbar is dismissed (both from the user
         * or after a timeout when the duration is `short` or `long`).
         * ```
         * <Snackbar dismissEvent="doSomething" />
         * ```
         * @param dismissEvent event name in the server to be called when the snackbar is dismissed.
         */
        fun dismissEvent(dismissEvent: String) = apply {
            this.dismissEvent = dismissEvent
        }

        fun build() = SnackbarDTO(
            Properties(
                actionOnNewLine,
                shape,
                containerColor,
                contentColor,
                actionColor,
                actionContentColor,
                dismissActionContentColor,
                visuals,
                actionEvent,
                dismissEvent,
                commonProps,
            )
        )
    }

    internal data class Data(
        val snackbarVisuals: Visuals,
        val pushEvent: PushEvent,
        val phxValue: Any?,
        val actionEventName: String?,
        val dismissEventName: String?,
        var dismissWasCalled: Boolean = false
    ) : SnackbarData {

        override val visuals: SnackbarVisuals = snackbarVisuals

        override fun dismiss() {
            dismissEventName?.let {
                onClickFromString(pushEvent, it, phxValue).invoke()
                dismissWasCalled = true
            }
        }

        override fun performAction() {
            actionEventName?.let {
                onClickFromString(pushEvent, it, phxValue).invoke()
            }
        }
    }

    internal data class Visuals(
        val snackbarLabel: String? = null,
        val snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
        val snackbarMessage: String = "",
        val snackbarWithDismissAction: Boolean = false,
    ) : SnackbarVisuals {
        override val actionLabel: String?
            get() = snackbarLabel
        override val duration: SnackbarDuration
            get() = snackbarDuration
        override val message: String
            get() = snackbarMessage
        override val withDismissAction: Boolean
            get() = snackbarWithDismissAction
    }

    companion object {
        fun visualsFromNode(node: CoreNodeElement?): Visuals {
            return SnackbarDtoFactory.handleAttrs(
                node?.attributes ?: persistentListOf(),
                null,
                null
            ).visuals
        }
    }
}

internal object SnackbarDtoFactory : ComposableViewFactory<SnackbarDTO>() {

    /**
     * Creates a `SnackbarDTO` object based on the attributes of the input `Attributes` object.
     * SnackbarDTO co-relates to the Snackbar composable
     * @param attributes the `Attributes` object to create the `SnackbarDTO` object from
     * @return a `SnackbarDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SnackbarDTO = handleAttrs(attributes, pushEvent, scope).build()

    internal fun handleAttrs(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SnackbarDTO.Builder = attributes.fold(SnackbarDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrActionColor -> builder.actionColor(attribute.value)
            attrActionEvent -> builder.actionEvent(attribute.value)
            attrActionLabel -> builder.actionLabel(attribute.value)
            attrActionOnNewLine -> builder.actionOnNewLine(attribute.value)
            attrActionContentColor -> builder.actionContentColor(attribute.value)
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrDismissActionContentColor -> builder.dismissActionContentColor(attribute.value)
            attrDismissEvent -> builder.dismissEvent(attribute.value)
            attrDuration -> builder.duration(attribute.value)
            attrMessage -> builder.message(attribute.value)
            attrShape -> builder.shape(attribute.value)
            attrWithDismissAction -> builder.withDismissAction(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as SnackbarDTO.Builder
    }
}
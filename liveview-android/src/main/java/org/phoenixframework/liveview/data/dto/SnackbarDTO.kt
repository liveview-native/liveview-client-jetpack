package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.dto.Attrs.attrActionColor
import org.phoenixframework.liveview.data.dto.Attrs.attrActionContentColor
import org.phoenixframework.liveview.data.dto.Attrs.attrActionEvent
import org.phoenixframework.liveview.data.dto.Attrs.attrActionOnNewLine
import org.phoenixframework.liveview.data.dto.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.dto.Attrs.attrContentColor
import org.phoenixframework.liveview.data.dto.Attrs.attrDismissActionContentColor
import org.phoenixframework.liveview.data.dto.Attrs.attrDismissEvent
import org.phoenixframework.liveview.data.dto.Attrs.attrDuration
import org.phoenixframework.liveview.data.dto.Attrs.attrLabel
import org.phoenixframework.liveview.data.dto.Attrs.attrMessage
import org.phoenixframework.liveview.data.dto.Attrs.attrShape
import org.phoenixframework.liveview.data.dto.Attrs.attrWithDismissAction
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CLICK
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design snackbar. A Snackbar must be declared inside of a Scaffold and it is displayed
 * conditionally. The two required params are `message` and `dismissEvent`, where the second one is
 * the event name in the server responsible to update the flag responsible to show/hide the
 * Snackbar.
 * ```
 * <Snackbar
 *   message="message"
 *   dismiss-event="hideDialog"
 * />
 * ```
 */
internal class SnackbarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val actionOnNewLine = builder.actionOnNewLine
    private val shape = builder.shape
    private val containerColor = builder.containerColor
    private val contentColor = builder.contentColor
    private val actionColor = builder.actionColor
    private val actionContentColor = builder.actionContentColor
    private val dismissActionContentColor = builder.dismissActionContentColor

    private val visuals = builder.visuals
    private val actionEvent = builder.actionEvent
    private val dismissEvent = builder.dismissEvent

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val snackbarData = remember(composableNode) {
            Data(
                visuals,
                pushEvent,
                actionEvent,
                dismissEvent,
            )
        }
        Snackbar(
            snackbarData = snackbarData,
            modifier = modifier,
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
                        pushEvent(EVENT_TYPE_CLICK, it, "", null)
                    }
                }
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var actionOnNewLine: Boolean = false
            private set
        var shape: Shape? = null
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var actionColor: Color? = null
            private set
        var actionContentColor: Color? = null
            private set
        var dismissActionContentColor: Color? = null
            private set
        var visuals: Visuals = Visuals()
            private set
        var actionEvent: String? = null
            private set
        var dismissEvent: String? = null
            private set

        /**
         * Whether or not action should be put on a separate line. Recommended for action with
         * long action text.
         * ```
         * <Snackbar action-on-new-line="true" />
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
         * @param shape snackbar's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * The color used for the background of this snackbar.
         * ```
         * <Snackbar container-color="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside this snackbar.
         * ```
         * <Snackbar content-color="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar action-color="#FF000000" />
         * ```
         * @param actionColor the action color in AARRGGBB format.
         */
        fun actionColor(actionColor: String) = apply {
            this.actionColor = actionColor.toColor()
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar action-content-color="#FF00FF00" />
         * ```
         * @param actionContentColor the action color in AARRGGBB format.
         */
        fun actionContentColor(actionContentColor: String) = apply {
            this.actionContentColor = actionContentColor.toColor()
        }

        /**
         * The preferred content color for the optional dismiss action inside this snackbar.
         * ```
         * <Snackbar dismiss-action-content-color="#FF00FF00" />
         * ```
         * @param dismissActionContentColor the color for the optional dismiss action in AARRGGBB
         * format.
         */
        fun dismissActionContentColor(dismissActionContentColor: String) = apply {
            this.dismissActionContentColor = dismissActionContentColor.toColor()
        }

        /**
         * Optional action label to show as button in the Snackbar.
         * ```
         * <Snackbar label="Action" />
         * ```
         * @param label action label to show as button in the Snackbar
         */
        fun label(label: String) = apply {
            this.visuals = visuals.copy(snackbarLabel = label)
        }

        /**
         * Duration of the Snackbar
         * ```
         * <Snackbar duration="short" />
         * ```
         * @param duration duration of the Snackbar. The supported values are: `short`, `long`, and
         * `indefinite`.
         */
        fun duration(duration: String) = apply {
            this.visuals = visuals.copy(
                snackbarDuration = when (duration) {
                    "indefinite" -> SnackbarDuration.Indefinite
                    "long" -> SnackbarDuration.Long
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
         * <Snackbar with-dismiss-action="true" />
         * ```
         * @param withDismissAction true to show dismiss action, false otherwise.
         */
        fun withDismissAction(withDismissAction: String) = apply {
            this.visuals = visuals.copy(snackbarWithDismissAction = withDismissAction.toBoolean())
        }

        /**
         * Event name is the server to be called when the user presses the action in the snackbar.
         * ```
         * <Snackbar action-event="doSomething" />
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
         * <Snackbar dismiss-event="doSomething" />
         * ```
         * @param dismissEvent event name in the server to be called when the snackbar is dismissed.
         */
        fun dismissEvent(dismissEvent: String) = apply {
            this.dismissEvent = dismissEvent
        }

        fun build() = SnackbarDTO(this)
    }

    internal data class Data(
        val snackbarVisuals: Visuals,
        val pushEvent: PushEvent,
        val actionEventName: String?,
        val dismissEventName: String?,
        var dismissWasCalled: Boolean = false
    ) : SnackbarData {

        override val visuals: SnackbarVisuals = snackbarVisuals

        override fun dismiss() {
            dismissEventName?.let {
                pushEvent(EVENT_TYPE_CLICK, it, "", null)
                dismissWasCalled = true
            }
        }

        override fun performAction() {
            actionEventName?.let {
                pushEvent(EVENT_TYPE_CLICK, it, "", null)
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
                node?.attributes ?: emptyArray(),
                null,
                null
            ).visuals
        }
    }
}

internal object SnackbarDtoFactory : ComposableViewFactory<SnackbarDTO, SnackbarDTO.Builder>() {

    /**
     * Creates a `SnackbarDTO` object based on the attributes of the input `Attributes` object.
     * SnackbarDTO co-relates to the Snackbar composable
     * @param attributes the `Attributes` object to create the `SnackbarDTO` object from
     * @return a `SnackbarDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SnackbarDTO = handleAttrs(attributes, pushEvent, scope).build()

    internal fun handleAttrs(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SnackbarDTO.Builder = attributes.fold(SnackbarDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrActionColor -> builder.actionColor(attribute.value)
            attrActionEvent -> builder.actionEvent(attribute.value)
            attrActionOnNewLine -> builder.actionOnNewLine(attribute.value)
            attrActionContentColor -> builder.actionContentColor(attribute.value)
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrDismissActionContentColor -> builder.dismissActionContentColor(attribute.value)
            attrDismissEvent -> builder.dismissEvent(attribute.value)
            attrDuration -> builder.duration(attribute.value)
            attrLabel -> builder.label(attribute.value)
            attrMessage -> builder.message(attribute.value)
            attrShape -> builder.shape(attribute.value)
            attrWithDismissAction -> builder.withDismissAction(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as SnackbarDTO.Builder
    }
}
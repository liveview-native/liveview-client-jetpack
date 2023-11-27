package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design basic dialog.
 * There are two ways to create a dialog:
 * - The first one is defining the content of the dialog by hand.
 * ```
 * <AlertDialog phx-click="dismissAction">
 *  // Content
 * </AlertDialog>
 * ```
 * - The second one is determining each part of the dialog using the following templates:
 *   - `confirm` for confirm button (required);
 *   - `dismiss` for dismiss button;
 *   - `icon` for dialog icon;
 *   - `title` for the dialog title;
 *   - and no template form the dialog content.
 * ```
 * <AlertDialog phx-click="dismissAction">
 *  <Button phx-click="confirmEvent" template="confirm">
 *      <Text>Confirm</Text>
 *  </Button>
 *  <TextButton phx-click="dismissEvent" template="dismiss">
 *      <Text>Dismiss</Text>
 *  </TextButton>
 *  <Icon imageVector="filled:Add" template="icon" />
 *  <Text template="title">Alert Title</Title>
 *  <Text>Alert message</Text>
 * </AlertDialog>
 * ```
 * An `AlertDialog` usually is wrapped by a condition in order to show it or not. And a dismiss
 * event should be used to hide it:
 * ```
 * def handle_event("showDialog", _params, socket) do
 *   {:noreply, assign(socket, :showDialog, true)}
 * end
 *
 * def handle_event("hideDialog", _params, socket) do
 *     {:noreply, assign(socket, :showDialog, false)}
 * end
 *
 * // render function...
 * <%= if @showDialog do %>
 *   <AlertDialog phx-click="hideDialog">...</AlertDialog>
 * <% end %>
 * ```
 */
internal class AlertDialogDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val dismissEvent = builder.dismissEvent
    private val dialogProperties = builder.dialogProperties
    private val shape = builder.shape
    private val containerColor = builder.containerColor
    private val iconContentColor = builder.iconContentColor
    private val titleContentColor = builder.titleContentColor
    private val textContentColor = builder.textContentColor
    private val tonalElevation = builder.tonalElevation

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val dismissButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == AlertDialogDtoFactory.dismissButton }
        }
        val confirmButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == AlertDialogDtoFactory.confirmButton }
        }
        val icon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == AlertDialogDtoFactory.icon }
        }
        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == AlertDialogDtoFactory.title }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == null }
        }

        val predefinedDialog =
            dismissButton != null || confirmButton != null || icon != null || title != null || content != null

        if (predefinedDialog) {
            AlertDialog(
                onDismissRequest = dismissEvent,
                confirmButton = {
                    confirmButton?.let {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                },
                modifier = modifier,
                dismissButton = dismissButton?.let {
                    {
                        PhxLiveView(dismissButton, pushEvent, composableNode, null)
                    }
                },
                icon = icon?.let {
                    {
                        PhxLiveView(icon, pushEvent, composableNode, null)
                    }
                },
                title = title?.let {
                    {
                        PhxLiveView(title, pushEvent, composableNode, null)
                    }
                },
                text = content?.let {
                    {
                        PhxLiveView(content, pushEvent, composableNode, null)
                    }
                },
                shape = shape ?: AlertDialogDefaults.shape,
                containerColor = containerColor ?: AlertDialogDefaults.containerColor,
                iconContentColor = iconContentColor ?: AlertDialogDefaults.iconContentColor,
                titleContentColor = titleContentColor ?: AlertDialogDefaults.titleContentColor,
                textContentColor = textContentColor ?: AlertDialogDefaults.textContentColor,
                tonalElevation = tonalElevation ?: AlertDialogDefaults.TonalElevation,
                properties = dialogProperties,
            )
        } else {
            AlertDialog(
                onDismissRequest = dismissEvent,
                modifier = modifier,
                properties = dialogProperties,
            ) {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var dismissEvent: () -> Unit = {}
            private set
        var dialogProperties: DialogProperties = DialogProperties()
            private set
        var shape: Shape? = null
            private set
        var containerColor: Color? = null
            private set
        var iconContentColor: Color? = null
            private set
        var titleContentColor: Color? = null
            private set
        var textContentColor: Color? = null
            private set
        var tonalElevation: Dp? = null
            private set

        private var dismissOnBackPress: Boolean = true
        private var dismissOnClickOutside: Boolean = true
        private var securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit
        private var usePlatformDefaultWidth: Boolean = true
        private var decorFitsSystemWindows: Boolean = true

        /**
         * Whether the dialog can be dismissed by pressing the back button. If true, pressing the
         * back button will call `dismissEvent` event. Default value is true.
         * ```
         * <AlertDialog dismissOnBackPress="true">...</AlertDialog>
         * ```
         * @param dismissOnBackPress true if the dialog can be dismissed by pressing the back
         * button, false otherwise.
         */
        fun dismissOnBackPress(dismissOnBackPress: String) = apply {
            this.dismissOnBackPress = dismissOnBackPress.toBoolean()
        }

        /**
         * Whether the dialog can be dismissed by clicking outside the dialog's bounds. If true,
         * clicking outside the dialog will call `dismissEvent` event. Default value is true.
         * ```
         * <AlertDialog dismissOnClickOutside="true">...</AlertDialog>
         * ```
         * @param dismissOnClickOutside true if the dialog can be dismissed by clicking outside the
         * dialog's bounds, false otherwise.
         */
        fun dismissOnClickOutside(dismissOnClickOutside: String) = apply {
            this.dismissOnClickOutside = dismissOnClickOutside.toBoolean()
        }

        /**
         * Policy for setting for the dialog's window. Indicates if the content of the window should
         * be treated as secure, preventing it from appearing in screenshots or from being viewed
         * on non-secure displays. Default value is `inherit`.
         * ```
         * <AlertDialog securePolicy="inherit">...</AlertDialog>
         * ```
         * @param securePolicy possible values are: `secureOn`, `secureOff`, and `inherit` (default).
         */
        fun securePolicy(securePolicy: String) = apply {
            this.securePolicy = when (securePolicy) {
                "secureOn" -> SecureFlagPolicy.SecureOn
                "secureOff" -> SecureFlagPolicy.SecureOff
                else -> SecureFlagPolicy.Inherit
            }
        }

        /**
         * Whether the width of the dialog's content should be limited to the platform default,
         * which is smaller than the screen width.
         * ```
         * <AlertDialog usePlatformDefaultWidth="true">...</AlertDialog>
         * ```
         * @param usePlatformDefaultWidth true if the width of the dialog's content should be
         * limited to the platform default, false otherwise. Default value is true.
         */
        fun usePlatformDefaultWidth(usePlatformDefaultWidth: String) = apply {
            this.usePlatformDefaultWidth = usePlatformDefaultWidth.toBoolean()
        }

        /**
         *  Sets WindowCompat.setDecorFitsSystemWindows value. Set to false to use WindowInsets.
         *  If false, the soft input mode will be changed to
         *  WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE and android:windowIsFloating is set
         *  to false for Android R and earlier.
         *  ```
         *  <AlertDialog decorFitsSystemWindows="true">...</AlertDialog>
         *  ```
         *  @param decorFitsSystemWindows true to set WindowCompat.setDecorFitsSystemWindows value,
         *  false otherwise. Default value is true.
         */
        fun decorFitsSystemWindows(decorFitsSystemWindows: String) = apply {
            this.decorFitsSystemWindows = decorFitsSystemWindows.toBoolean()
        }

        /**
         * Event to be triggered on the server when the dialog should be dismissed.
         * ```
         * <AlertDialog phx-click="dismissAction">...</AlertDialog>
         * ```
         * @param dismissEventName event name to be called on the server in order to dismiss the
         * alert dialog.
         */
        fun onDismissRequest(dismissEventName: String, pushEvent: PushEvent?) = apply {
            this.dismissEvent = {
                pushEvent?.invoke(EVENT_TYPE_CLICK, dismissEventName, "", null)
            }
        }

        /**
         * Defines the shape of this dialog's container.
         * ```
         * <AlertDialog shape="16" >...</AlertDialog>
         * ```
         * @param shape button's shape. Supported values are: `circle`, `rectangle`, or an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * The color used for the background of this dialog.
         * ```
         * <AlertDialog containerColor="#FFFFFFFF" >...</AlertDialog>
         * ```
         * @param containerColor the background color in AARRGGBB format.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The content color used for the icon.
         * ```
         * <AlertDialog iconContentColor="#FF00FF00" >...</AlertDialog>
         * ```
         * @param iconContentColor the content color used for the icon in AARRGGBB format.
         */
        fun iconContentColor(iconContentColor: String) = apply {
            this.iconContentColor = iconContentColor.toColor()
        }

        /**
         * The content color used for the title.
         * ```
         * <AlertDialog titleContentColor="#FFCCCCCC" >...</AlertDialog>
         * ```
         * @param titleContentColor the content color used for the title in AARRGGBB format.
         */
        fun titleContentColor(titleContentColor: String) = apply {
            this.titleContentColor = titleContentColor.toColor()
        }

        /**
         * The content color used for the text.
         * ```
         * <AlertDialog textContentColor="#FFCCCCCC" >...</AlertDialog>
         * ```
         * @param textContentColor the content color used for the text in AARRGGBB format.
         */
        fun textContentColor(textContentColor: String) = apply {
            this.textContentColor = textContentColor.toColor()
        }

        /**
         * When containerColor is the default one, a translucent primary color overlay is applied
         * on top of the container. A higher tonal elevation value will result in a darker color in
         * light theme and lighter color in dark theme.
         * ```
         * <AlertDialog tonalElevation="12" >...</AlertDialog>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        fun build(): AlertDialogDTO {
            dialogProperties = DialogProperties(
                dismissOnBackPress,
                dismissOnClickOutside,
                securePolicy,
                usePlatformDefaultWidth,
                decorFitsSystemWindows
            )
            return AlertDialogDTO(this)
        }
    }
}

internal object AlertDialogDtoFactory :
    ComposableViewFactory<AlertDialogDTO, AlertDialogDTO.Builder>() {

    /**
     * Creates a `AlertDialogDTO` object based on the attributes of the input `Attributes` object.
     * AlertDialogDTO co-relates to the AlertDialog composable
     * @param attributes the `Attributes` object to create the `AlertDialogDTO` object from
     * @return a `AlertDialogDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): AlertDialogDTO = attributes.fold(AlertDialogDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            "dismissOnBackPress" -> builder.dismissOnBackPress(attribute.value)
            "dismissOnClickOutside" -> builder.dismissOnClickOutside(attribute.value)
            "securePolicy" -> builder.securePolicy(attribute.value)
            "usePlatformDefaultWidth" -> builder.usePlatformDefaultWidth(attribute.value)
            "decorFitsSystemWindows" -> builder.decorFitsSystemWindows(attribute.value)
            "shape" -> builder.shape(attribute.value)
            "containerColor" -> builder.containerColor(attribute.value)
            "iconContentColor" -> builder.iconContentColor(attribute.value)
            "titleContentColor" -> builder.titleContentColor(attribute.value)
            "textContentColor" -> builder.textContentColor(attribute.value)
            "tonalElevation" -> builder.tonalElevation(attribute.value)
            ComposableBuilder.ATTR_CLICK -> builder.onDismissRequest(attribute.value, pushEvent)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as AlertDialogDTO.Builder
    }.build()

    const val confirmButton = "confirm"
    const val dismissButton = "dismiss"
    const val icon = "icon"
    const val title = "title"
}
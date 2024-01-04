package org.phoenixframework.liveview.data.dto

import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import org.phoenixframework.liveview.data.constants.Attrs.attrDecorFitsSystemWindows
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissOnBackPress
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissOnClickOutside
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSecurePolicy
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrUsePlatformDefaultWidth
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Parent class of `AlertDialog` and `DatePickerDialog`.
 */
internal abstract class DialogDTO(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    protected val dismissEvent = builder.dismissEvent
    protected val dialogProperties = builder.dialogProperties
    protected val shape = builder.shape
    protected val tonalElevation = builder.tonalElevation
    protected val value = builder.value

    internal abstract class Builder : ComposableBuilder() {
        var dismissEvent: String? = null
            private set
        var dialogProperties: DialogProperties = DialogProperties()
            private set
        var shape: Shape? = null
            private set
        var tonalElevation: Dp? = null
            private set

        // Attributes to initialize the dialogProperties
        private var decorFitsSystemWindows: Boolean = true
        private var dismissOnBackPress: Boolean = true
        private var dismissOnClickOutside: Boolean = true
        private var securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit
        private var usePlatformDefaultWidth: Boolean = true

        /**
         * Whether the dialog can be dismissed by pressing the back button. If true, pressing the
         * back button will call `dismissEvent` event. Default value is true.
         * ```
         * <AlertDialog dismiss-on-back-press="true">...</AlertDialog>
         * ```
         * @param dismissOnBackPress true if the dialog can be dismissed by pressing the back
         * button, false otherwise.
         */
        private fun dismissOnBackPress(dismissOnBackPress: String) = apply {
            this.dismissOnBackPress = dismissOnBackPress.toBoolean()
        }

        /**
         * Whether the dialog can be dismissed by clicking outside the dialog's bounds. If true,
         * clicking outside the dialog will call `dismissEvent` event. Default value is true.
         * ```
         * <AlertDialog dismiss-on-click-outside="true">...</AlertDialog>
         * ```
         * @param dismissOnClickOutside true if the dialog can be dismissed by clicking outside the
         * dialog's bounds, false otherwise.
         */
        private fun dismissOnClickOutside(dismissOnClickOutside: String) = apply {
            this.dismissOnClickOutside = dismissOnClickOutside.toBoolean()
        }

        /**
         * Policy for setting for the dialog's window. Indicates if the content of the window should
         * be treated as secure, preventing it from appearing in screenshots or from being viewed
         * on non-secure displays. Default value is `inherit`.
         * ```
         * <AlertDialog secure-policy="inherit">...</AlertDialog>
         * ```
         * @param securePolicy possible values are: `secureOn`, `secureOff`, and `inherit` (default).
         */
        private fun securePolicy(securePolicy: String) = apply {
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
         * <AlertDialog use-platform-default-width="true">...</AlertDialog>
         * ```
         * @param usePlatformDefaultWidth true if the width of the dialog's content should be
         * limited to the platform default, false otherwise. Default value is true.
         */
        protected fun usePlatformDefaultWidth(usePlatformDefaultWidth: String) = apply {
            this.usePlatformDefaultWidth = usePlatformDefaultWidth.toBoolean()
        }

        /**
         *  Sets WindowCompat.setDecorFitsSystemWindows value. Set to false to use WindowInsets.
         *  If false, the soft input mode will be changed to
         *  WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE and android:windowIsFloating is set
         *  to false for Android R and earlier.
         *  ```
         *  <AlertDialog decor-fits-system-windows="true">...</AlertDialog>
         *  ```
         *  @param decorFitsSystemWindows true to set WindowCompat.setDecorFitsSystemWindows value,
         *  false otherwise. Default value is true.
         */
        private fun decorFitsSystemWindows(decorFitsSystemWindows: String) = apply {
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
        fun onDismissRequest(dismissEventName: String) = apply {
            this.dismissEvent = dismissEventName
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
         * When containerColor is the default one, a translucent primary color overlay is applied
         * on top of the container. A higher tonal elevation value will result in a darker color in
         * light theme and lighter color in dark theme.
         * ```
         * <AlertDialog tonal-elevation="12" >...</AlertDialog>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        fun handleDialogAttributes(attribute: CoreAttribute): Boolean {
            var result = true
            when (attribute.name) {
                attrDecorFitsSystemWindows -> decorFitsSystemWindows(attribute.value)
                attrDismissOnBackPress -> dismissOnBackPress(attribute.value)
                attrDismissOnClickOutside -> dismissOnClickOutside(attribute.value)
                attrPhxClick -> onDismissRequest(attribute.value)
                attrSecurePolicy -> securePolicy(attribute.value)
                attrTonalElevation -> tonalElevation(attribute.value)
                attrUsePlatformDefaultWidth -> usePlatformDefaultWidth(attribute.value)
                attrShape -> shape(attribute.value)
                else -> result = false
            }
            return result
        }

        fun buildDialogProperties() {
            dialogProperties = DialogProperties(
                dismissOnBackPress,
                dismissOnClickOutside,
                securePolicy,
                usePlatformDefaultWidth,
                decorFitsSystemWindows
            )
        }
    }
}
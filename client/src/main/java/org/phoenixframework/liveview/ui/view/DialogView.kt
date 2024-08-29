package org.phoenixframework.liveview.ui.view

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import org.phoenixframework.liveview.constants.Attrs.attrDecorFitsSystemWindows
import org.phoenixframework.liveview.constants.Attrs.attrDismissOnBackPress
import org.phoenixframework.liveview.constants.Attrs.attrDismissOnClickOutside
import org.phoenixframework.liveview.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.constants.Attrs.attrSecurePolicy
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrUsePlatformDefaultWidth
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Parent class of `AlertDialog` and `DatePickerDialog`.
 */
internal abstract class DialogView<DP : DialogView.IDialogProperties>(props: DP) :
    ComposableView<DP>(props) {

    internal interface IDialogProperties : ComposableProperties {
        val dialogProps: DialogComposableProperties
    }

    @Stable
    internal data class DialogComposableProperties(
        val dismissEvent: String? = null,
        val shape: Shape? = null,
        val tonalElevation: Dp? = null,
        val dismissOnBackPress: Boolean = true,
        val dismissOnClickOutside: Boolean = true,
        val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        val usePlatformDefaultWidth: Boolean = true,
        val decorFitsSystemWindows: Boolean = true,
    ) {
        val dialogProperties: DialogProperties
            get() = DialogProperties(
                dismissOnBackPress,
                dismissOnClickOutside,
                securePolicy,
                usePlatformDefaultWidth,
                decorFitsSystemWindows
            )
    }

    internal abstract class Factory : ComposableViewFactory<DialogView<*>>() {
        /**
         * Whether the dialog can be dismissed by pressing the back button. If true, pressing the
         * back button will call `dismissEvent` event. Default value is true.
         * ```
         * <AlertDialog dismissOnBackPress="true">...</AlertDialog>
         * ```
         * @param dismissOnBackPress true if the dialog can be dismissed by pressing the back
         * button, false otherwise.
         */
        private fun dismissOnBackPress(
            props: DialogComposableProperties,
            dismissOnBackPress: String
        ): DialogComposableProperties {
            return props.copy(dismissOnBackPress = dismissOnBackPress.toBoolean())
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
        private fun dismissOnClickOutside(
            props: DialogComposableProperties,
            dismissOnClickOutside: String
        ): DialogComposableProperties {
            return props.copy(dismissOnClickOutside = dismissOnClickOutside.toBoolean())
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
        private fun securePolicy(
            props: DialogComposableProperties,
            securePolicy: String
        ): DialogComposableProperties {
            return props.copy(securePolicy = secureFlagPolicyFromString(securePolicy))
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
        private fun usePlatformDefaultWidth(
            props: DialogComposableProperties,
            usePlatformDefaultWidth: String
        ): DialogComposableProperties {
            return props.copy(usePlatformDefaultWidth = usePlatformDefaultWidth.toBoolean())
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
        private fun decorFitsSystemWindows(
            props: DialogComposableProperties,
            decorFitsSystemWindows: String
        ): DialogComposableProperties {
            return props.copy(decorFitsSystemWindows = decorFitsSystemWindows.toBoolean())
        }

        /**
         * Event to be triggered on the server when the dialog should be dismissed.
         * ```
         * <AlertDialog onDismissRequest="dismissAction">...</AlertDialog>
         * ```
         * @param dismissEventName event name to be called on the server in order to dismiss the
         * alert dialog.
         */
        fun onDismissRequest(
            props: DialogComposableProperties,
            dismissEventName: String
        ): DialogComposableProperties {
            return props.copy(dismissEvent = dismissEventName)
        }

        /**
         * Defines the shape of this dialog's container.
         * ```
         * <AlertDialog shape="16" >...</AlertDialog>
         * ```
         * @param shape button's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        fun shape(props: DialogComposableProperties, shape: String): DialogComposableProperties {
            return props.copy(shape = shapeFromString(shape))
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
        private fun tonalElevation(
            props: DialogComposableProperties,
            tonalElevation: String
        ): DialogComposableProperties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                return props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        fun handleDialogAttributes(
            props: DialogComposableProperties,
            attribute: CoreAttribute
        ): DialogComposableProperties? {
            return when (attribute.name) {
                attrDecorFitsSystemWindows -> decorFitsSystemWindows(props, attribute.value)
                attrDismissOnBackPress -> dismissOnBackPress(props, attribute.value)
                attrDismissOnClickOutside -> dismissOnClickOutside(props, attribute.value)
                attrOnDismissRequest -> onDismissRequest(props, attribute.value)
                attrSecurePolicy -> securePolicy(props, attribute.value)
                attrTonalElevation -> tonalElevation(props, attribute.value)
                attrUsePlatformDefaultWidth -> usePlatformDefaultWidth(props, attribute.value)
                attrShape -> shape(props, attribute.value)
                else -> null
            }
        }
    }
}
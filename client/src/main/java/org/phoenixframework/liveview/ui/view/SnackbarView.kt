package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrActionColor
import org.phoenixframework.liveview.constants.Attrs.attrActionContentColor
import org.phoenixframework.liveview.constants.Attrs.attrActionEvent
import org.phoenixframework.liveview.constants.Attrs.attrActionLabel
import org.phoenixframework.liveview.constants.Attrs.attrActionOnNewLine
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrDismissActionContentColor
import org.phoenixframework.liveview.constants.Attrs.attrDismissEvent
import org.phoenixframework.liveview.constants.Attrs.attrDuration
import org.phoenixframework.liveview.constants.Attrs.attrMessage
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrWithDismissAction
import org.phoenixframework.liveview.constants.SnackbarDurationValues
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
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
internal class SnackbarView private constructor(props: Properties) :
    ComposableView<SnackbarView.Properties>(props) {

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
                            EVENT_TYPE_BLUR,
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
        val actionOnNewLine: Boolean = false,
        val shape: Shape? = null,
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val actionColor: Color? = null,
        val actionContentColor: Color? = null,
        val dismissActionContentColor: Color? = null,
        val visuals: Visuals = Visuals(),
        val actionEvent: String? = null,
        val dismissEvent: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

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
            return Factory.handleAttrs(
                node?.attributes ?: persistentListOf(),
                null,
                null
            ).visuals
        }
    }

    internal object Factory : ComposableViewFactory<SnackbarView>() {

        /**
         * Creates a `SnackbarView` object based on the attributes of the input `Attributes` object.
         * SnackbarView co-relates to the Snackbar composable
         * @param attributes the `Attributes` object to create the `SnackbarView` object from
         * @return a `SnackbarView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): SnackbarView = SnackbarView(handleAttrs(attributes, pushEvent, scope))

        internal fun handleAttrs(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): Properties = attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrActionColor -> actionColor(props, attribute.value)
                attrActionEvent -> actionEvent(props, attribute.value)
                attrActionLabel -> actionLabel(props, attribute.value)
                attrActionOnNewLine -> actionOnNewLine(props, attribute.value)
                attrActionContentColor -> actionContentColor(props, attribute.value)
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrDismissActionContentColor -> dismissActionContentColor(props, attribute.value)
                attrDismissEvent -> dismissEvent(props, attribute.value)
                attrDuration -> duration(props, attribute.value)
                attrMessage -> message(props, attribute.value)
                attrShape -> shape(props, attribute.value)
                attrWithDismissAction -> withDismissAction(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        }

        /**
         * Whether or not action should be put on a separate line. Recommended for action with
         * long action text.
         * ```
         * <Snackbar actionOnNewLine="true" />
         * ```
         * @param actionOnNewLine true if action should be put on a separate line, false otherwise.
         */
        private fun actionOnNewLine(props: Properties, actionOnNewLine: String): Properties {
            return props.copy(actionOnNewLine = actionOnNewLine.toBoolean())
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
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape))
            } else props
        }

        /**
         * The color used for the background of this snackbar.
         * ```
         * <Snackbar containerColor="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, containerColor: String): Properties {
            return props.copy(containerColor = containerColor.toColor())
        }

        /**
         * The preferred color for content inside this snackbar.
         * ```
         * <Snackbar contentColor="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, contentColor: String): Properties {
            return props.copy(contentColor = contentColor.toColor())
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar actionColor="#FF000000" />
         * ```
         * @param actionColor the action color in AARRGGBB format or one of
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun actionColor(props: Properties, actionColor: String): Properties {
            return props.copy(actionColor = actionColor.toColor())
        }

        /**
         * The color of the snackbar's action.
         * ```
         * <Snackbar actionContentColor="#FF00FF00" />
         * ```
         * @param actionContentColor the action color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        private fun actionContentColor(props: Properties, actionContentColor: String): Properties {
            return props.copy(actionContentColor = actionContentColor.toColor())
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
        private fun dismissActionContentColor(
            props: Properties,
            dismissActionContentColor: String
        ): Properties {
            return props.copy(dismissActionContentColor = dismissActionContentColor.toColor())
        }

        /**
         * Optional action label to show as button in the Snackbar.
         * ```
         * <Snackbar actionLabel="Action" />
         * ```
         * @param label action label to show as button in the Snackbar
         */
        private fun actionLabel(props: Properties, label: String): Properties {
            return props.copy(visuals = props.visuals.copy(snackbarLabel = label))
        }

        /**
         * Duration of the Snackbar
         * ```
         * <Snackbar duration="short" />
         * ```
         * @param duration duration of the Snackbar. See the supported values at
         * [org.phoenixframework.liveview.data.constants.SnackbarDurationValues].
         */
        private fun duration(props: Properties, duration: String): Properties {
            return props.copy(
                visuals = props.visuals.copy(
                    snackbarDuration = when (duration) {
                        SnackbarDurationValues.indefinite -> SnackbarDuration.Indefinite
                        SnackbarDurationValues.long -> SnackbarDuration.Long
                        SnackbarDurationValues.short -> SnackbarDuration.Short
                        else -> SnackbarDuration.Short
                    }
                )
            )
        }

        /**
         * Text to be shown in the Snackbar.
         * ```
         * <Snackbar message="Message" />
         * ```
         * @param message text to be shown.
         */
        private fun message(props: Properties, message: String): Properties {
            return props.copy(visuals = props.visuals.copy(snackbarMessage = message))
        }

        /**
         * A boolean to show a dismiss action in the Snackbar. This is recommended to be set to
         * true better accessibility when a Snackbar duration is set `indefinite`.
         * ```
         * <Snackbar withDismissAction="true" />
         * ```
         * @param withDismissAction true to show dismiss action, false otherwise.
         */
        private fun withDismissAction(props: Properties, withDismissAction: String): Properties {
            return props.copy(visuals = props.visuals.copy(snackbarWithDismissAction = withDismissAction.toBoolean()))
        }

        /**
         * Event name is the server to be called when the user presses the action in the snackbar.
         * ```
         * <Snackbar actionEvent="doSomething" />
         * ```
         * @param actionEvent event name in the server to be called in the action button.
         */
        private fun actionEvent(props: Properties, actionEvent: String): Properties {
            return props.copy(actionEvent = actionEvent)
        }

        /**
         * Event name is the server to be called when the snackbar is dismissed (both from the user
         * or after a timeout when the duration is `short` or `long`).
         * ```
         * <Snackbar dismissEvent="doSomething" />
         * ```
         * @param dismissEvent event name in the server to be called when the snackbar is dismissed.
         */
        private fun dismissEvent(props: Properties, dismissEvent: String): Properties {
            return props.copy(dismissEvent = dismissEvent)
        }
    }
}
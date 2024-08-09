package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrClippingEnabled
import org.phoenixframework.liveview.constants.Attrs.attrDismissOnBackPress
import org.phoenixframework.liveview.constants.Attrs.attrDismissOnClickOutside
import org.phoenixframework.liveview.constants.Attrs.attrExcludeFromSystemGesture
import org.phoenixframework.liveview.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.constants.Attrs.attrFocusable
import org.phoenixframework.liveview.constants.Attrs.attrOffset
import org.phoenixframework.liveview.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.constants.Attrs.attrSecurePolicy
import org.phoenixframework.liveview.constants.Attrs.attrUsePlatformDefaultWidth
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design dropdown menu. Menus display a list of choices on a temporary surface.
 * They appear when users interact with a button, action, or other control.
 * You must use the `expanded` property to show/hide the `DropdownMenu` and define the dismiss
 * event to hide it:
 * ```
 * def handle_event("showPopup", _, socket) do
 *   {:noreply, assign(socket, :showPopup, true)}
 * end
 *
 * def handle_event("hidePopup", _, socket) do
 *   {:noreply, assign(socket, :showPopup, false)}
 * end
 *
 * def handle_event("onMenuOptionClick", value, socket) do
 *   {:noreply, socket |> assign(:menuOption, value) |> assign(:showPopup, false) }
 * end
 *
 * // render function...
 * <IconButton template="action" phx-click="showPopup">
 *   <Icon imageVector="filled:MoreVert" />
 *   <DropdownMenu onDismissRequest="hidePopup" expanded={"#{ @showPopup }"}>
 *     <DropdownMenuItem phx-click="onMenuOptionClick" phx-value="A">
 *       <Text>Option A</Text>
 *     </DropdownMenuItem>
 *     <DropdownMenuItem phx-click="onMenuOptionClick" phx-value="B">
 *       <Text>Option B</Text>
 *     </DropdownMenuItem>
 *   </DropdownMenu>
 * </IconButton>
 */
internal class DropdownMenuView private constructor(props: Properties) :
    ComposableView<DropdownMenuView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val expanded = props.expanded
        val dismissEvent = props.dismissEvent
        val popupProperties = props.popupProperties
        val offset = props.offset

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                dismissEvent?.let {
                    pushEvent(
                        EVENT_TYPE_BLUR,
                        it,
                        props.commonProps.phxValue,
                        null
                    )
                }
            },
            modifier = props.commonProps.modifier,
            offset = offset ?: DpOffset(0.dp, 0.dp),
            scrollState = rememberScrollState(),
            properties = popupProperties,
            content = {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    @Stable
    internal data class Properties(
        val dismissEvent: String? = null,
        val expanded: Boolean = true,
        val offset: DpOffset? = null,
        val focusable: Boolean = true,
        val dismissOnBackPress: Boolean = true,
        val dismissOnClickOutside: Boolean = true,
        val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        val excludeFromSystemGesture: Boolean = true,
        val clippingEnabled: Boolean = true,
        val usePlatformDefaultWidth: Boolean = false,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties {
        val popupProperties: PopupProperties
            get() = PopupProperties(
                focusable,
                dismissOnBackPress,
                dismissOnClickOutside,
                securePolicy,
                excludeFromSystemGesture,
                clippingEnabled
            )
    }

    internal object Factory :
        ComposableViewFactory<DropdownMenuView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): DropdownMenuView = DropdownMenuView(attributes.fold(
            Properties()
        ) { props, attribute ->
            when (attribute.name) {
                attrClippingEnabled -> clippingEnabled(props, attribute.value)
                attrDismissOnBackPress -> dismissOnBackPress(props, attribute.value)
                attrDismissOnClickOutside -> dismissOnClickOutside(props, attribute.value)
                attrExcludeFromSystemGesture -> excludeFromSystemGesture(props, attribute.value)
                attrExpanded -> expanded(props, attribute.value)
                attrFocusable -> focusable(props, attribute.value)
                attrOffset -> offset(props, attribute.value)
                attrOnDismissRequest -> onDismissRequest(props, attribute.value)
                attrSecurePolicy -> securePolicy(props, attribute.value)
                attrUsePlatformDefaultWidth -> usePlatformDefaultWidth(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })


        /**
         * Whether to allow the popup window to extend beyond the bounds of the screen. By default
         * the window is clipped to the screen boundaries. Setting this to false will allow windows
         * to be accurately positioned. The default value is true.
         * ```
         * <DropdownMenu clippingEnabled="true">...</DropdownMenu>
         * ```
         * @param clipping true if the systemGestureExclusionRects is set, false otherwise.
         */
        private fun clippingEnabled(props: Properties, clipping: String): Properties {
            return props.copy(clippingEnabled = clipping.toBoolean())
        }

        /**
         * A flag to check whether to set the systemGestureExclusionRects. The default is true.
         * ```
         * <DropdownMenu excludeFromSystemGesture="true">...</DropdownMenu>
         * ```
         * @param exclude true if the systemGestureExclusionRects is set, false otherwise.
         */
        private fun excludeFromSystemGesture(props: Properties, exclude: String): Properties {
            return props.copy(excludeFromSystemGesture = exclude.toBoolean())
        }

        /**
         * Whether the dropdown menu is expanded or not
         * ```
         * <DropdownMenu expanded="true">...</DropdownMenu>
         * ```
         * @param expanded true if the dropdown menu is expanded, false otherwise.
         */
        private fun expanded(props: Properties, expanded: String): Properties {
            return props.copy(expanded = expanded.toBoolean())
        }

        /**
         * Whether the popup is focusable. When true, the popup will receive IME events and key
         * presses, such as when the back button is pressed.
         * ```
         * <DropdownMenu focusable="true">...</DropdownMenu>
         * ```
         * @param focusable true if the dropdown menu is focusable, false otherwise.
         */
        private fun focusable(props: Properties, focusable: String): Properties {
            return props.copy(focusable = focusable.toBoolean())
        }

        /**
         * Whether the dropdown menu can be dismissed by pressing the back button. If true,
         * pressing the back button will call `dismissEvent` event. Default value is true.
         * ```
         * <DropdownMenu dismissOnBackPress="true">...</DropdownMenu>
         * ```
         * @param dismissOnBackPress true if the dropdown menu can be dismissed by pressing the
         * back button, false otherwise.
         */
        private fun dismissOnBackPress(props: Properties, dismissOnBackPress: String): Properties {
            return props.copy(dismissOnBackPress = dismissOnBackPress.toBoolean())
        }

        /**
         * Whether the dropdown menu can be dismissed by clicking outside the dropdown menu's
         * bounds. If true, clicking outside the dropdown menu will call `dismissEvent` event.
         * Default value is true.
         * ```
         * <DropdownMenu dismissOnClickOutside="true">...</DropdownMenu>
         * ```
         * @param dismissOnClickOutside true if the dropdown menu can be dismissed by clicking
         * outside the dropdown menu's bounds, false otherwise.
         */
        private fun dismissOnClickOutside(
            props: Properties,
            dismissOnClickOutside: String
        ): Properties {
            return props.copy(dismissOnClickOutside = dismissOnClickOutside.toBoolean())
        }

        /**
         * Event to be triggered on the server when the dropdown menu should be dismissed.
         * ```
         * <DropdownMenu phx-click="dismissAction">...</DropdownMenu>
         * ```
         * @param dismissEventName event name to be called on the server in order to dismiss the
         * dropdown menu.
         */
        private fun onDismissRequest(props: Properties, dismissEventName: String): Properties {
            return props.copy(dismissEvent = dismissEventName)
        }

        /**
         * DpOffset from the original position of the menu. The offset respects the LayoutDirection,
         * so the offset's x position will be added in LTR and subtracted in RTL. The value must be
         * passed as an array of two items (start and end).
         * ```
         * <DropdownMenu offset="8,12">...</DropdownMenu>
         * ```
         * @param offsetStr array containing the start and end offset.
         */
        private fun offset(props: Properties, offsetStr: String): Properties {
            return if (offsetStr.contains(',')) {
                val offset = try {
                    offsetStr
                        .split(',')
                        .map { it.toInt() }
                        .let { list ->
                            DpOffset(list[0].dp, list[1].dp)
                        }
                } catch (e: Exception) {
                    DpOffset.Unspecified
                }
                props.copy(offset = offset)
            } else props
        }

        /**
         * Policy for setting for the dropdown menu's window. Indicates if the content of the
         * window should be treated as secure, preventing it from appearing in screenshots or from
         * being viewed on non-secure displays. Default value is `inherit`.
         * ```
         * <DropdownMenu securePolicy="inherit">...</DropdownMenu>
         * ```
         * @param securePolicy possible values are: `secureOn`, `secureOff`, and `inherit` (default).
         */
        private fun securePolicy(props: Properties, securePolicy: String): Properties {
            return props.copy(securePolicy = secureFlagPolicyFromString(securePolicy))
        }

        /**
         * Whether the width of the dropdown menu's content should be limited to the platform
         * default, which is smaller than the screen width.
         * ```
         * <DropdownMenu usePlatformDefaultWidth="true">...</DropdownMenu>
         * ```
         * @param usePlatformDefaultWidth true if the width of the dropdown menu's content should
         * be limited to the platform default, false otherwise. Default value is true.
         */
        private fun usePlatformDefaultWidth(
            props: Properties,
            usePlatformDefaultWidth: String
        ): Properties {
            return props.copy(usePlatformDefaultWidth = usePlatformDefaultWidth.toBoolean())
        }
    }
}
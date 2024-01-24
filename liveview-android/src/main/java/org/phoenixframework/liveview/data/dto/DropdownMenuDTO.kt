package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import org.phoenixframework.liveview.data.constants.Attrs.attrClippingEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissOnBackPress
import org.phoenixframework.liveview.data.constants.Attrs.attrDismissOnClickOutside
import org.phoenixframework.liveview.data.constants.Attrs.attrExcludeFromSystemGesture
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrFocusable
import org.phoenixframework.liveview.data.constants.Attrs.attrOffset
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSecurePolicy
import org.phoenixframework.liveview.data.constants.Attrs.attrUsePlatformDefaultWidth
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design dropdown menu. Menus display a list of choices on a temporary surface.
 * They appear when users interact with a button, action, or other control.
 * You must use the `expanded` property to show/hide the `DropdownMenu` and define the dismiss
 * event to hide it:
 * ```
 * def handle_event("showPopup", _params, socket) do
 *   {:noreply, assign(socket, :showPopup, true)}
 * end
 *
 * def handle_event("hidePopup", _params, socket) do
 *   {:noreply, assign(socket, :showPopup, false)}
 * end
 *
 * def handle_event("onMenuOptionClick", _params, socket) do
 *   {:noreply, socket |> assign(:menuOption, _params) |> assign(:showPopup, false) }
 * end
 *
 * // render function...
 * <IconButton template="action" phx-click="showPopup">
 *   <Icon image-vector="filled:MoreVert" />
 *   <DropdownMenu phx-click="hidePopup" expanded={"#{ @showPopup }"}>
 *     <DropdownMenuItem phx-click="onMenuOptionClick" phx-value="A">
 *       <Text>Option A</Text>
 *     </DropdownMenuItem>
 *     <DropdownMenuItem phx-click="onMenuOptionClick" phx-value="B">
 *       <Text>Option B</Text>
 *     </DropdownMenuItem>
 *   </DropdownMenu>
 * </IconButton>
 */
internal class DropdownMenuDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val expanded = builder.expanded
    private val dismissEvent = builder.dismissEvent
    private val value = builder.value
    private val popupProperties = builder.popupProperties
    private val offset = builder.offset

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = dismissEvent?.let {
                onClickFromString(pushEvent, it, value?.toString() ?: "")
            } ?: {
                // Do nothing
            },
            modifier = modifier,
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

    internal class Builder : ComposableBuilder() {
        var dismissEvent: String? = null
            private set

        var expanded: Boolean = true
            private set

        var offset: DpOffset? = null
            private set

        lateinit var popupProperties: PopupProperties
            private set

        // Attributes to initialize the popup Properties
        private var focusable: Boolean = true
        private var dismissOnBackPress: Boolean = true
        private var dismissOnClickOutside: Boolean = true
        private var securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit
        private var excludeFromSystemGesture: Boolean = true
        private var clippingEnabled: Boolean = true
        private var usePlatformDefaultWidth: Boolean = false

        /**
         * Whether to allow the popup window to extend beyond the bounds of the screen. By default
         * the window is clipped to the screen boundaries. Setting this to false will allow windows
         * to be accurately positioned. The default value is true.
         * ```
         * <DropdownMenu clipping-enabled="true">...</DropdownMenu>
         * ```
         * @param clipping true if the systemGestureExclusionRects is set, false otherwise.
         */
        fun clippingEnabled(clipping: String) = apply {
            this.clippingEnabled = clipping.toBoolean()
        }

        /**
         * A flag to check whether to set the systemGestureExclusionRects. The default is true.
         * ```
         * <DropdownMenu exclude-from-system-gesture="true">...</DropdownMenu>
         * ```
         * @param exclude true if the systemGestureExclusionRects is set, false otherwise.
         */
        fun excludeFromSystemGesture(exclude: String) = apply {
            this.excludeFromSystemGesture = exclude.toBoolean()
        }

        /**
         * Whether the dropdown menu is expanded or not
         * ```
         * <DropdownMenu expanded="true">...</DropdownMenu>
         * ```
         * @param expanded true if the dropdown menu is expanded, false otherwise.
         */
        fun expanded(expanded: String) = apply {
            this.expanded = expanded.toBoolean()
        }

        /**
         * Whether the popup is focusable. When true, the popup will receive IME events and key
         * presses, such as when the back button is pressed.
         * ```
         * <DropdownMenu focusable="true">...</DropdownMenu>
         * ```
         * @param focusable true if the dropdown menu is focusable, false otherwise.
         */
        fun focusable(focusable: String) = apply {
            this.focusable = focusable.toBoolean()
        }

        /**
         * Whether the dropdown menu can be dismissed by pressing the back button. If true,
         * pressing the back button will call `dismissEvent` event. Default value is true.
         * ```
         * <DropdownMenu dismiss-on-back-press="true">...</DropdownMenu>
         * ```
         * @param dismissOnBackPress true if the dropdown menu can be dismissed by pressing the
         * back button, false otherwise.
         */
        fun dismissOnBackPress(dismissOnBackPress: String) = apply {
            this.dismissOnBackPress = dismissOnBackPress.toBoolean()
        }

        /**
         * Whether the dropdown menu can be dismissed by clicking outside the dropdown menu's
         * bounds. If true, clicking outside the dropdown menu will call `dismissEvent` event.
         * Default value is true.
         * ```
         * <DropdownMenu dismiss-on-click-outside="true">...</DropdownMenu>
         * ```
         * @param dismissOnClickOutside true if the dropdown menu can be dismissed by clicking
         * outside the dropdown menu's bounds, false otherwise.
         */
        fun dismissOnClickOutside(dismissOnClickOutside: String) = apply {
            this.dismissOnClickOutside = dismissOnClickOutside.toBoolean()
        }

        /**
         * Event to be triggered on the server when the dropdown menu should be dismissed.
         * ```
         * <DropdownMenu phx-click="dismissAction">...</DropdownMenu>
         * ```
         * @param dismissEventName event name to be called on the server in order to dismiss the
         * dropdown menu.
         */
        fun onDismissRequest(dismissEventName: String) = apply {
            this.dismissEvent = dismissEventName
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
        fun offset(offsetStr: String) = apply {
            if (offsetStr.contains(',')) {
                this.offset = try {
                    offsetStr
                        .split(',')
                        .map { it.toInt() }
                        .let { list ->
                            DpOffset(list[0].dp, list[1].dp)
                        }
                } catch (e: Exception) {
                    DpOffset.Unspecified
                }
            }
        }

        /**
         * Policy for setting for the dropdown menu's window. Indicates if the content of the
         * window should be treated as secure, preventing it from appearing in screenshots or from
         * being viewed on non-secure displays. Default value is `inherit`.
         * ```
         * <DropdownMenu secure-policy="inherit">...</DropdownMenu>
         * ```
         * @param securePolicy possible values are: `secureOn`, `secureOff`, and `inherit` (default).
         */
        fun securePolicy(securePolicy: String) = apply {
            this.securePolicy = secureFlagPolicyFromString(securePolicy)
        }

        /**
         * Whether the width of the dropdown menu's content should be limited to the platform
         * default, which is smaller than the screen width.
         * ```
         * <DropdownMenu use-platform-default-width="true">...</DropdownMenu>
         * ```
         * @param usePlatformDefaultWidth true if the width of the dropdown menu's content should
         * be limited to the platform default, false otherwise. Default value is true.
         */
        fun usePlatformDefaultWidth(usePlatformDefaultWidth: String) = apply {
            this.usePlatformDefaultWidth = usePlatformDefaultWidth.toBoolean()
        }

        fun build() = DropdownMenuDTO(
            this.apply {
                popupProperties = PopupProperties(
                    focusable,
                    dismissOnBackPress,
                    dismissOnClickOutside,
                    securePolicy,
                    excludeFromSystemGesture,
                    clippingEnabled
                )
            }
        )
    }
}

internal object DropdownMenuDtoFactory :
    ComposableViewFactory<DropdownMenuDTO, DropdownMenuDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): DropdownMenuDTO = attributes.fold(
        DropdownMenuDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            attrClippingEnabled -> builder.clippingEnabled(attribute.value)
            attrPhxClick -> builder.onDismissRequest(attribute.value)
            attrDismissOnBackPress -> builder.dismissOnBackPress(attribute.value)
            attrDismissOnClickOutside -> builder.dismissOnClickOutside(attribute.value)
            attrExcludeFromSystemGesture -> builder.excludeFromSystemGesture(attribute.value)
            attrExpanded -> builder.expanded(attribute.value)
            attrFocusable -> builder.focusable(attribute.value)
            attrOffset -> builder.offset(attribute.value)
            attrSecurePolicy -> builder.securePolicy(attribute.value)
            attrUsePlatformDefaultWidth -> builder.usePlatformDefaultWidth(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as DropdownMenuDTO.Builder
    }.build()
}
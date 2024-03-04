package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrIconContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrTextContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrTitleContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateConfirmButton
import org.phoenixframework.liveview.data.constants.Templates.templateDismissButton
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_BLUR
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design basic dialog.
 * There are two ways to create a dialog:
 * - The first one is defining the content of the dialog by hand.
 * ```
 * <BasicAlertDialog onDismissRequest="dismissAction">
 *  // Content
 * </BasicAlertDialog>
 * ```
 * - The second one is determining each part of the dialog using the following templates:
 *   - `confirm` for confirm button (required);
 *   - `dismiss` for dismiss button;
 *   - `icon` for dialog icon;
 *   - `title` for the dialog title;
 *   - and no template form the dialog content.
 * ```
 * <AlertDialog onDismissRequest="dismissAction">
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
 *   <AlertDialog onDismissRequest="hideDialog">...</AlertDialog>
 * <% end %>
 * ```
 */
internal class AlertDialogDTO private constructor(props: Properties) :
    DialogDTO<AlertDialogDTO.Properties>(props) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val dismissEvent = props.dialogProps.dismissEvent
        val dialogProperties = props.dialogProps.dialogProperties
        val shape = props.dialogProps.shape
        val tonalElevation = props.dialogProps.tonalElevation
        val containerColor = props.containerColor
        val iconContentColor = props.iconContentColor
        val titleContentColor = props.titleContentColor
        val textContentColor = props.textContentColor

        when (composableNode?.node?.tag) {
            ComposableTypes.alertDialog -> {
                val dismissButton = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateDismissButton }
                }
                val confirmButton = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateConfirmButton }
                }
                val icon = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateIcon }
                }
                val title = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == templateTitle }
                }
                val content = remember(composableNode.children) {
                    composableNode.children.find { it.node?.template == null }
                }
                AlertDialog(
                    onDismissRequest = {
                        dismissEvent?.let { event ->
                            pushEvent(EVENT_TYPE_BLUR, event, props.commonProps.phxValue, null)
                        }
                    },
                    confirmButton = {
                        confirmButton?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
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
            }

            ComposableTypes.basicAlertDialog -> {
                BasicAlertDialog(
                    onDismissRequest = {
                        dismissEvent?.let { event ->
                            pushEvent(EVENT_TYPE_BLUR, event, props.commonProps.phxValue, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
                    properties = dialogProperties,
                    content = {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )
            }
        }
    }

    @Stable
    internal data class Properties(
        val containerColor: Color?,
        val iconContentColor: Color?,
        val titleContentColor: Color?,
        val textContentColor: Color?,
        override val dialogProps: DialogComposableProperties,
        override val commonProps: CommonComposableProperties,
    ) : IDialogProperties

    internal class Builder : DialogDTO.Builder() {
        private var containerColor: Color? = null
        private var iconContentColor: Color? = null
        private var titleContentColor: Color? = null
        private var textContentColor: Color? = null

        /**
         * The color used for the background of this dialog.
         * ```
         * <AlertDialog containerColor="#FFFFFFFF" >...</AlertDialog>
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the `system-*`
         * colors.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The content color used for the icon.
         * ```
         * <AlertDialog iconContentColor="#FF00FF00" >...</AlertDialog>
         * ```
         * @param iconContentColor the content color used for the icon in AARRGGBB format or one of
         * the [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun iconContentColor(iconContentColor: String) = apply {
            this.iconContentColor = iconContentColor.toColor()
        }

        /**
         * The content color used for the title.
         * ```
         * <AlertDialog titleContentColor="#FFCCCCCC" >...</AlertDialog>
         * ```
         * @param titleContentColor the content color used for the title in AARRGGBB format or one
         * of the [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun titleContentColor(titleContentColor: String) = apply {
            this.titleContentColor = titleContentColor.toColor()
        }

        /**
         * The content color used for the text.
         * ```
         * <AlertDialog textContentColor="#FFCCCCCC" >...</AlertDialog>
         * ```
         * @param textContentColor the content color used for the text in AARRGGBB format or one of
         * the [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun textContentColor(textContentColor: String) = apply {
            this.textContentColor = textContentColor.toColor()
        }

        fun build(): AlertDialogDTO {
            return AlertDialogDTO(
                Properties(
                    containerColor,
                    iconContentColor,
                    titleContentColor,
                    textContentColor,
                    dialogComposableProps,
                    commonProps,
                )
            )
        }
    }
}

internal object AlertDialogDtoFactory : ComposableViewFactory<AlertDialogDTO>() {

    /**
     * Creates a `AlertDialogDTO` object based on the attributes of the input `Attributes` object.
     * AlertDialogDTO co-relates to the AlertDialog composable
     * @param attributes the `Attributes` object to create the `AlertDialogDTO` object from
     * @return a `AlertDialogDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): AlertDialogDTO = attributes.fold(AlertDialogDTO.Builder()) { builder, attribute ->
        if (builder.handleDialogAttributes(attribute)) {
            builder
        } else {
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrIconContentColor -> builder.iconContentColor(attribute.value)
                attrTextContentColor -> builder.textContentColor(attribute.value)
                attrTitleContentColor -> builder.titleContentColor(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as AlertDialogDTO.Builder
        }
    }.build()
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Templates.templateConfirmButton
import org.phoenixframework.liveview.constants.Templates.templateDismissButton
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design date picker dialog.
 * You can specify three children:
 *   - confirm button (required) using the `confirm` template;
 *   - dismiss button (optional) using the `dismiss` template;
 *   - and the content of the dialog (required, i.e. `DatePicker`) require no template.
 * An `DatePickerDialog` usually is wrapped by a condition in order to show it or not.
 * And a dismiss event should be used to hide it:
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
 *   <DatePickerDialog onDismissRequest="hideDialog">...</DatePickerDialog>
 * <% end %>
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class DatePickerDialogView private constructor(props: Properties) :
    DialogView<DatePickerDialogView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors
        val dismissEvent = props.dialogProps.dismissEvent
        val dialogProperties = props.dialogProps.dialogProperties
        val shape = props.dialogProps.shape
        val tonalElevation = props.dialogProps.tonalElevation

        val dismissButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateDismissButton }
        }
        val confirmButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateConfirmButton }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == null }
        }
        DatePickerDialog(
            onDismissRequest = {
                dismissEvent?.let {
                    if (it.isNotEmpty()) {
                        pushEvent.invoke(
                            EVENT_TYPE_BLUR,
                            it,
                            props.commonProps.phxValue,
                            null
                        )
                    }
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
            shape = shape ?: DatePickerDefaults.shape,
            tonalElevation = tonalElevation ?: DatePickerDefaults.TonalElevation,
            colors = getDatePickerColors(colors),
            properties = dialogProperties,
            content = {
                content?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        override val dialogProps: DialogComposableProperties = DialogComposableProperties(
            usePlatformDefaultWidth = false
        ),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IDialogProperties

    internal object Factory : DialogView.Factory() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): DatePickerDialogView = DatePickerDialogView(
            attributes.fold(Properties()) { props, attribute ->
                handleDialogAttributes(props.dialogProps, attribute)?.let {
                    props.copy(dialogProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrColors -> colors(props, attribute.value)
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
            })

        /**
         * Set DatePicker colors.
         * ```
         * <DatePicker
         *   colors="{'containerColor': '#FFFF0000', 'titleContentColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `containerColor`, `titleContentColor`, `headlineContentColor`,
         * `weekdayContentColor`, `subheadContentColor`, `yearContentColor`,
         * `currentYearContentColor`, `selectedYearContentColor`, `selectedYearContainerColor`,
         * `dayContentColor`, `disabledDayContentColor`, `selectedDayContentColor`,
         * `disabledSelectedDayContentColor`, `selectedDayContainerColor`,
         * `disabledSelectedDayContainerColor`, `todayContentColor`, `todayDateBorderColor`,
         * `dayInSelectionRangeContentColor`, and `dayInSelectionRangeContainerColor`.
         */
        fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }
    }
}
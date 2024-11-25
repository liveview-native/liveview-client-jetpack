package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrContentWindowInsets
import org.phoenixframework.liveview.constants.Attrs.attrOnDismissRequest
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrSkipPartiallyExpanded
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Templates.templateDragHandle
import org.phoenixframework.liveview.extensions.SHEET_VALUE_KEY
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.extensions.toValue
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design modal bottom sheet.
 * You can define the `onChanged` event in order to be notified when the `sheetValue` changed.
 * ```
 * <ModalBottomSheet onChanged="updateSheetState">
 *   <Box contentAlignment="center" style="fillMaxWidth();height(200.dp)">
 *     <Text>BottomSheet Content</Text>
 *   </Box>
 * </ModalBottomSheet>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class ModalBottomSheetView private constructor(props: Properties) :
    ComposableView<ModalBottomSheetView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val dismissEvent = props.dismissEvent
        val skipPartiallyExpanded = props.skipPartiallyExpanded
        val onChanged = props.onChanged
        val windowsInsets = props.contentWindowInsets
        val shape = props.shape
        val containerColor = props.containerColor
        val contentColor = props.contentColor
        val tonalElevation = props.tonalElevation
        val scrimColor = props.scrimColor

        val dragHandle = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateDragHandle }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != templateDragHandle }
        }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = { sheetValue ->
                if (onChanged.isNotEmpty()) {
                    pushNewValue(sheetValue, pushEvent, onChanged)
                }
                true
            }
        )
        ModalBottomSheet(
            onDismissRequest = {
                dismissEvent?.let { event ->
                    pushEvent(
                        EVENT_TYPE_BLUR,
                        event,
                        props.commonProps.phxValue,
                        null
                    )
                }
            },
            modifier = props.commonProps.modifier,
            sheetState = sheetState,
            shape = shape ?: BottomSheetDefaults.ExpandedShape,
            containerColor = containerColor ?: BottomSheetDefaults.ContainerColor,
            contentColor = contentColor ?: contentColorFor(
                containerColor ?: BottomSheetDefaults.ContainerColor
            ),
            tonalElevation = tonalElevation ?: BottomSheetDefaults.Elevation,
            scrimColor = scrimColor ?: BottomSheetDefaults.ScrimColor,
            dragHandle = {
                dragHandle?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: BottomSheetDefaults.DragHandle()
            },
            contentWindowInsets = { windowsInsets ?: BottomSheetDefaults.windowInsets },
            content = {
                content?.let {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun pushNewValue(sheetValue: SheetValue, pushEvent: PushEvent, onChangedEvent: String) {
        pushEvent(
            EVENT_TYPE_CHANGE,
            onChangedEvent,
            mergeValueWithPhxValue(SHEET_VALUE_KEY, sheetValue.toValue()),
            null,
        )
    }

    @Stable
    internal data class Properties(
        val dismissEvent: String? = null,
        val skipPartiallyExpanded: Boolean = false,
        val onChanged: String = "",
        val contentWindowInsets: WindowInsets? = null,
        val shape: Shape? = null,
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val tonalElevation: Dp? = null,
        val scrimColor: Color? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ModalBottomSheetView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): ModalBottomSheetView = ModalBottomSheetView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrContainerColor -> containerColor(props, attribute.value)
                    attrContentColor -> contentColor(props, attribute.value)
                    attrContentWindowInsets -> contentWindowInsets(props, attribute.value)
                    attrOnDismissRequest -> onDismissRequest(props, attribute.value)
                    attrPhxChange -> onChanged(props, attribute.value)
                    attrScrimColor -> scrimColor(props, attribute.value)
                    attrShape -> shape(props, attribute.value)
                    attrSkipPartiallyExpanded -> skipPartiallyExpanded(props, attribute.value)
                    attrTonalElevation -> tonalElevation(props, attribute.value)
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
         * Whether the partially expanded state, if the sheet is tall enough, should be skipped.
         * If true, the sheet will always expand to the Expanded state and move to the Hidden state
         * when hiding the sheet, either programmatically or by user interaction.
         * ```
         * <ModalBottomSheet skipPartiallyExpanded="true" >...</ModalBottomSheet>
         * ```
         * @param skipPartiallyExpanded true if the partially expanded state should be skipped,
         * false otherwise.
         */
        private fun skipPartiallyExpanded(
            props: Properties,
            skipPartiallyExpanded: String
        ): Properties {
            return props.copy(skipPartiallyExpanded = skipPartiallyExpanded.toBoolean())
        }

        /**
         * Function in the server to be called when the bottom sheet state changes.
         * ```
         * <ModalBottomSheet phx-change="updateBottomSheet" >...</ModalBottomSheet>
         * ```
         * @param onChanged the name of the function to be called in the server when the bottom
         * sheet is expanded.
         */
        private fun onChanged(props: Properties, onChanged: String): Properties {
            return props.copy(onChanged = onChanged)
        }

        /**
         * Event to be triggered on the server when the bottom sheet should be dismissed.
         * ```
         * <ModalBottomSheet onDismissRequest="dismissAction">...</AlertDialog>
         * ```
         * @param dismissEventName event name to be called on the server in order to dismiss the
         * bottom sheet.
         */
        private fun onDismissRequest(props: Properties, dismissEventName: String): Properties {
            return props.copy(dismissEvent = dismissEventName)
        }

        /**
         * The shape of the bottom sheet.
         * ```
         * <ModalBottomSheet shape="8">...</ModalBottomSheet>
         * ```
         * @param shape drawer's container's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }

        /**
         * The color used for the background of this bottom sheet
         * ```
         * <ModalBottomSheet containerColor="#FFFFFF00">...</ModalBottomSheet>
         * ```
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return props.copy(containerColor = color.toColor())
        }

        /**
         * The preferred color for content inside this bottom sheet.
         * ```
         * <ModalBottomSheet contentColor="#FFCCCCCC">...</ModalBottomSheet>
         * ```
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return props.copy(contentColor = color.toColor())
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <ModalDrawerSheet tonalElevation="24">...</ModalDrawerSheet>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Color of the scrim that obscures content when the drawer is open
         * ```
         * <ModalDrawerSheet scrimColor="#FF000000">...</ModalDrawerSheet>
         * ```
         * @scrimColor the scrim color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun scrimColor(props: Properties, scrimColor: String): Properties {
            return props.copy(scrimColor = scrimColor.toColor())
        }

        /**
         * Window insets to be passed to the bottom sheet window via PaddingValues params.
         * ```
         * <ModalBottomSheet contentWindowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        private fun contentWindowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(contentWindowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }
    }
}
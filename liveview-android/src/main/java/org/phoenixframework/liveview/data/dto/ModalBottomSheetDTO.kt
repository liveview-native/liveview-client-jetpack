package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrOnChanged
import org.phoenixframework.liveview.data.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSkipPartiallyExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.pushNewValue
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.extensions.toSheetValue
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design modal bottom sheet.
 * Use the sheet-value property to determine if the bottom sheet is hidden, partially expanded, or
 * expanded. You must have to define the `on-changed` event in order to keep the `sheet-value` in
 * sync with the server.
 * ```
 * <ModalBottomSheet on-changed="updateSheetState" sheet-value="expanded">
 *   <Box content-alignment="center" width="fill" height="200">
 *     <Text>BottomSheet Content</Text>
 *   </Box>
 * </ModalBottomSheet>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class ModalBottomSheetDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val skipPartiallyExpanded = builder.skipPartiallyExpanded
    private val sheetValue = builder.sheetValue
    private val onChanged = builder.onChanged
    private val windowsInsets = builder.windowInsets
    private val shape = builder.shape
    private val containerColor = builder.containerColor
    private val contentColor = builder.contentColor
    private val tonalElevation = builder.tonalElevation
    private val scrimColor = builder.scrimColor

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val dragHandle = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateDragHandle }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != Templates.templateDragHandle }
        }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = { sheetValue ->
                sheetValue.pushNewValue(pushEvent, onChanged)
                true
            }
        )
        ModalBottomSheet(
            onDismissRequest = {
                // TODO Do nothing
            },
            modifier = modifier,
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
            windowInsets = windowsInsets ?: BottomSheetDefaults.windowInsets,
            content = {
                content?.let {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    internal class Builder : ComposableBuilder() {
        var skipPartiallyExpanded: Boolean = false
            private set
        var sheetValue: SheetValue = SheetValue.Hidden
            private set
        var onChanged: String = ""
            private set
        var windowInsets: WindowInsets? = null
            private set
        var shape: Shape? = null
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var tonalElevation: Dp? = null
            private set
        var scrimColor: Color? = null
            private set

        /**
         * Whether the partially expanded state, if the sheet is tall enough, should be skipped.
         * If true, the sheet will always expand to the Expanded state and move to the Hidden state
         * when hiding the sheet, either programmatically or by user interaction.
         * ```
         * <ModalBottomSheet skip-partially-expanded="true" >...</ModalBottomSheet>
         * ```
         * @param skipPartiallyExpanded true if the partially expanded state should be skipped,
         * false otherwise.
         */
        fun skipPartiallyExpanded(skipPartiallyExpanded: String) = apply {
            this.skipPartiallyExpanded = skipPartiallyExpanded.toBoolean()
        }

        /**
         * Possible values of SheetState.
         * ```
         * <ModalBottomSheet sheet-value="expanded" >...</ModalBottomSheet>
         * ```
         * @param sheetValue the value representing the state of the bottom sheet. The possible
         * values are: `expanded`, `partiallyExpanded`, and `hidden`.
         */
        fun sheetValue(sheetValue: String) = apply {
            this.sheetValue = sheetValue.toSheetValue() ?: SheetValue.Hidden
        }

        /**
         * Function in the server to be called when the bottom sheet state changes.
         * ```
         * <ModalBottomSheet on-changed="updateBottomSheet" >...</ModalBottomSheet>
         * ```
         * @param onChanged the name of the function to be called in the server when the bottom
         * sheet is expanded.
         */
        fun onChanged(onChanged: String) = apply {
            this.onChanged = onChanged
        }

        /**
         * The shape of the bottom sheet.
         * ```
         * <ModalBottomSheet shape="8">...</ModalBottomSheet>
         * ```
         * @param shape drawer's container's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * The color used for the background of this bottom sheet
         * ```
         * <ModalBottomSheet container-color="#FFFFFF00">...</ModalBottomSheet>
         * ```
         * @param color container color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            this.containerColor = color.toColor()
        }

        /**
         * The preferred color for content inside this bottom sheet.
         * ```
         * <ModalBottomSheet content-color="#FFCCCCCC">...</ModalBottomSheet>
         * ```
         * @param color content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            this.contentColor = color.toColor()
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <ModalDrawerSheet tonal-elevation="24">...</ModalDrawerSheet>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Color of the scrim that obscures content when the drawer is open
         * ```
         * <ModalDrawerSheet scrim-color="#FF000000">...</ModalDrawerSheet>
         * ```
         * @scrimColor the scrim color in AARRGGBB format.
         */
        fun scrimColor(scrimColor: String) = apply {
            this.scrimColor = scrimColor.toColor()
        }

        /**
         * Window insets to be passed to the bottom sheet window via PaddingValues params.
         * ```
         * <ModalBottomSheet window-insets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(insets: String) = apply {
            try {
                this.windowInsets = windowInsetsFromString(insets)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun build() = ModalBottomSheetDTO(this)
    }
}

internal object ModalBottomSheetDtoFactory :
    ComposableViewFactory<ModalBottomSheetDTO, ModalBottomSheetDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ModalBottomSheetDTO =
        attributes.fold(ModalBottomSheetDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrContentColor -> builder.contentColor(attribute.value)
                attrOnChanged -> builder.onChanged(attribute.value)
                attrScrimColor -> builder.scrimColor(attribute.value)
                attrShape -> builder.shape(attribute.value)
                attrSkipPartiallyExpanded -> builder.skipPartiallyExpanded(attribute.value)
                attrSheetValue -> builder.sheetValue(attribute.value)
                attrTonalElevation -> builder.tonalElevation(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as ModalBottomSheetDTO.Builder
        }.build()
}
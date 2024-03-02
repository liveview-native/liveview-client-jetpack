package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetOnChanged
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetPeekHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetSkipHiddenState
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetSwipeEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetValue
import org.phoenixframework.liveview.data.constants.Templates.templateBody
import org.phoenixframework.liveview.data.constants.Templates.templateDragHandle
import org.phoenixframework.liveview.data.constants.Templates.templateSheetContent
import org.phoenixframework.liveview.data.constants.Templates.templateTopBar
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.SHEET_VALUE_KEY
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.extensions.toSheetValue
import org.phoenixframework.liveview.domain.extensions.toValue
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design standard bottom sheet scaffold.
 * This component must have at least two children:
 * - A content using the "body" template;
 * - and the bottom sheet content using the "sheetContent" template.
 * ```
 * <BottomSheetScaffold>
 *   <Box size="fill" template="sheetContent">
 *     <Text>Sheet content</Text>
 *   </Box>
 *   <Box size="fill" template="body">
 *     <Text fontSize="24">Screen content</Text>
 *   </Box>
 * </BottomSheetScaffold>
 * ```
 * Optional children are:
 * - A top bar using the "topBar" template;
 * - A custom bottom sheet drag handle using the "dragHandle" template;
 * - and a snackbar, simply using the `SnackBar` tag.
 * ```
 * <BottomSheetScaffold>
 *   <%= if @showSnack do %>
 *     <Snackbar message="Hi there!" dismissEvent="hideSnackbar" />
 *   <% end %>
 *   <Box width="fill" template="dragHandle">
 *     <Icon imageVector="filled:ArrowUpward" align="center" />
 *   </Box>
 *   <TopAppBar template="topBar">
 *     <Text template="title">Title</Text>
 *   </TopAppBar>
 *   <Box size="fill" template="sheetContent">
 *     <Text>Sheet content</Text>
 *   </Box>
 *   <Box size="fill" template="body">
 *     <Text fontSize="24">Screen content</Text>
 *   </Box>
 * </BottomSheetScaffold>
 * ```
 *  Use the `sheetValue` property to determine if the bottom sheet is hidden, partially expanded,
 *  or expanded. You must have to define the `sheetOnChanged` event in order to keep the
 *  `sheetValue` in sync with the server. Notice that the hidden value is only allowed if the
 *  `sheetSkipHiddenState` set to false.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class BottomSheetScaffoldDTO private constructor(props: Properties) :
    ComposableView<BottomSheetScaffoldDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val containerColor = props.containerColor
        val contentColor = props.contentColor
        val onChanged = props.onChanged
        val sheetContainerColor = props.sheetContainerColor
        val sheetContentColor = props.sheetContentColor
        val sheetPickHeight = props.sheetPickHeight
        val sheetShadowElevation = props.sheetShadowElevation
        val sheetShape = props.sheetShape
        val sheetSkipHiddenState = props.sheetSkipHiddenState
        val sheetSwipeEnabled = props.sheetSwipeEnabled
        val sheetTonalElevation = props.sheetTonalElevation
        val sheetValue = props.sheetValue

        val topBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTopBar }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBody }
        }
        val sheetContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateSheetContent }
        }
        val sheetDragHandle = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateDragHandle }
        }
        val snackBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == ComposableTypes.snackbar }
        }
        val snackbarHostState = remember { SnackbarHostState() }
        val state =
            rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = sheetValue,
                    skipHiddenState = sheetSkipHiddenState,
                    confirmValueChange = { newSheetValue ->
                        if (newSheetValue != sheetValue) {
                            pushNewValue(newSheetValue, pushEvent, onChanged)
                        }
                        true
                    },
                ),
                snackbarHostState = snackbarHostState,
            )
        val actualContainerColor = containerColor ?: MaterialTheme.colorScheme.surface
        val actualSheetContainerColor = sheetContainerColor ?: BottomSheetDefaults.ContainerColor
        BottomSheetScaffold(sheetContent = {
            sheetContent?.let { content ->
                PhxLiveView(content, pushEvent, composableNode, null, this)
            }
        },
            modifier = props.commonProps.modifier,
            scaffoldState = state,
            sheetPeekHeight = sheetPickHeight ?: BottomSheetDefaults.SheetPeekHeight,
            sheetShape = sheetShape ?: BottomSheetDefaults.ExpandedShape,
            sheetContainerColor = actualSheetContainerColor,
            sheetContentColor = sheetContentColor ?: MaterialTheme.colorScheme.contentColorFor(
                actualSheetContainerColor
            ).takeOrElse {
                LocalContentColor.current
            },
            sheetTonalElevation = sheetTonalElevation ?: BottomSheetDefaults.Elevation,
            sheetShadowElevation = sheetShadowElevation ?: BottomSheetDefaults.Elevation,
            sheetDragHandle = {
                sheetDragHandle?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: BottomSheetDefaults.DragHandle()
            },
            sheetSwipeEnabled = sheetSwipeEnabled,
            topBar = topBar?.let { appBar ->
                {
                    PhxLiveView(appBar, pushEvent, composableNode, null)
                }
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) {
                    snackBar?.let { sb ->
                        PhxLiveView(sb, pushEvent, composableNode, null)
                    }
                }
            },
            containerColor = actualContainerColor,
            contentColor = contentColor
                ?: MaterialTheme.colorScheme.contentColorFor(actualContainerColor)
                    .takeOrElse {
                        LocalContentColor.current
                    },
            content = { contentPaddingValues ->
                content?.let { content ->
                    PhxLiveView(content, pushEvent, composableNode, contentPaddingValues)
                }
            })
        LaunchedEffect(snackBar?.id) {
            if (snackBar != null) {
                snackbarHostState.showSnackbar(SnackbarDTO.visualsFromNode(snackBar.node))
            }
        }
        LaunchedEffect(composableNode) {
            when (sheetValue) {
                SheetValue.Hidden -> state.bottomSheetState.hide()

                SheetValue.PartiallyExpanded -> state.bottomSheetState.partialExpand()

                SheetValue.Expanded -> state.bottomSheetState.expand()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun pushNewValue(sheetValue: SheetValue, pushEvent: PushEvent, onChangedEvent: String) {
        pushEvent(
            ComposableBuilder.EVENT_TYPE_CHANGE,
            onChangedEvent,
            mergeValueWithPhxValue(SHEET_VALUE_KEY, sheetValue.toValue()),
            null,
        )
    }

    @Stable
    internal data class Properties(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val onChanged: String = "",
        val sheetContainerColor: Color? = null,
        val sheetContentColor: Color? = null,
        val sheetPickHeight: Dp? = null,
        val sheetShadowElevation: Dp? = null,
        val sheetShape: Shape? = null,
        val sheetSwipeEnabled: Boolean = true,
        val sheetTonalElevation: Dp? = null,
        val sheetSkipHiddenState: Boolean = true,
        val sheetValue: SheetValue = SheetValue.PartiallyExpanded,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var onChanged: String = ""
        private var sheetContainerColor: Color? = null
        private var sheetContentColor: Color? = null
        private var sheetPickHeight: Dp? = null
        private var sheetShadowElevation: Dp? = null
        private var sheetShape: Shape? = null
        private var sheetSwipeEnabled: Boolean = true
        private var sheetTonalElevation: Dp? = null
        private var sheetSkipHiddenState: Boolean = true
        private var sheetValue: SheetValue = SheetValue.PartiallyExpanded

        /**
         * The color used for the background of this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold containerColor="#FFFFFFFF" >
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold contentColor="#FF000000" >
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        /**
         * Function in the server to be called when the bottom sheet state changes.
         * ```
         * <BottomSheetScaffold sheetOnChanged="updateBottomSheet" >
         * ```
         * @param onChanged the name of the function to be called in the server when the bottom
         * sheet is expanded.
         */
        fun sheetOnChanged(onChanged: String) = apply {
            this.onChanged = onChanged
        }

        /**
         * The color used for the sheet's background of this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetContainerColor="#FFFFFFFF" >
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun sheetContainerColor(containerColor: String) = apply {
            this.sheetContainerColor = containerColor.toColor()
        }

        /**
         * The preferred color for sheet's content inside this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetContentColor="#FF000000" >
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun sheetContentColor(contentColor: String) = apply {
            this.sheetContentColor = contentColor.toColor()
        }

        /**
         * The shadow elevation of the bottom sheet.
         * ```
         * <BottomSheetScaffold sheetShadowElevation="12" >
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        fun sheetShadowElevation(shadowElevation: String) = apply {
            if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                this.sheetShadowElevation = shadowElevation.toInt().dp
            }
        }

        /**
         * The height of the bottom sheet when it is collapsed.
         * ```
         * <BottomSheetScaffold sheetPeekHeight="50" >
         * ```
         * @param height height of the bottom sheet peek area (whe it is not hidden).
         */
        fun sheetPickHeight(height: String) = apply {
            if (height.isNotEmptyAndIsDigitsOnly()) {
                this.sheetPickHeight = height.toInt().dp
            }
        }

        /**
         * Defines the shape of the BottomSheetScaffold sheet's container.
         * ```
         * <BottomSheetScaffold sheetShape="16" >
         * ```
         * @param shape BottomSheetScaffold's sheet shape. See the supported values at
         * [org.phoenixframework.liveview.data.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        fun sheetShape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.sheetShape = shapeFromString(shape)
            }
        }

        /**
         * Whether the sheet swiping is enabled and should react to the user's input
         * ```
         * <BottomSheetScaffold sheetSwipeEnabled="true" >
         * ```
         * @param enabled true if the sheet swiping is enabled, false otherwise.
         */
        fun sheetSwipeEnabled(enabled: String) = apply {
            if (enabled.isNotEmpty()) {
                this.sheetSwipeEnabled = enabled.toBoolean()
            }
        }

        /**
         * When containerColor is the default one, a translucent primary color overlay is applied
         * on top of the container. A higher tonal elevation value will result in a darker color in
         * light theme and lighter color in dark theme.
         * ```
         * <BottomSheetScaffold sheetTonalElevation="12" >
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun sheetTonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.sheetTonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Whether Hidden state is skipped for BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetSkipHiddenState="true" >
         * ```
         * @param skipHiddenState true if the hidden state must be skipped for BottomSheetScaffold,
         * false otherwise.
         */
        fun sheetSkipHiddenState(skipHiddenState: String) = apply {
            this.sheetSkipHiddenState = skipHiddenState.toBoolean()
        }

        /**
         * Possible values of SheetState. The `hidden` value is only allowed setting the
         * `sheetSkipHiddenState` property to true.
         * ```
         * <BottomSheetScaffold sheetValue="expanded" >
         * ```
         * @param sheetValue the value representing the state of the bottom sheet. See the possible
         * values at [org.phoenixframework.liveview.data.constants.SheetValues].
         */
        fun sheetValue(sheetValue: String) = apply {
            this.sheetValue = sheetValue.toSheetValue() ?: SheetValue.PartiallyExpanded
        }

        fun build() = BottomSheetScaffoldDTO(
            Properties(
                containerColor,
                contentColor,
                onChanged,
                sheetContainerColor,
                sheetContentColor,
                sheetPickHeight,
                sheetShadowElevation,
                sheetShape,
                sheetSwipeEnabled,
                sheetTonalElevation,
                sheetSkipHiddenState,
                sheetValue,
                commonProps,
            )
        )
    }
}

internal object BottomSheetScaffoldDtoFactory :
    ComposableViewFactory<BottomSheetScaffoldDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): BottomSheetScaffoldDTO =
        attributes.fold(BottomSheetScaffoldDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrContainerColor -> builder.containerColor(attribute.value)
                attrContentColor -> builder.contentColor(attribute.value)
                attrSheetContainerColor -> builder.sheetContainerColor(attribute.value)
                attrSheetContentColor -> builder.sheetContentColor(attribute.value)
                attrSheetOnChanged -> builder.sheetOnChanged(attribute.value)
                attrSheetPeekHeight -> builder.sheetPickHeight(attribute.value)
                attrSheetShadowElevation -> builder.sheetShadowElevation(attribute.value)
                attrSheetShape -> builder.sheetShape(attribute.value)
                attrSheetSkipHiddenState -> builder.sheetSkipHiddenState(attribute.value)
                attrSheetSwipeEnabled -> builder.sheetSwipeEnabled(attribute.value)
                attrSheetTonalElevation -> builder.sheetTonalElevation(attribute.value)
                attrSheetValue -> builder.sheetValue(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as BottomSheetScaffoldDTO.Builder
        }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*>> {
        return mapOf(
            ComposableTypes.snackbar to SnackbarDtoFactory
        )
    }
}
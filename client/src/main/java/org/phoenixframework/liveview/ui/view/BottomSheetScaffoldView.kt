package org.phoenixframework.liveview.ui.view

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
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrSheetContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrSheetContentColor
import org.phoenixframework.liveview.constants.Attrs.attrSheetPeekHeight
import org.phoenixframework.liveview.constants.Attrs.attrSheetShadowElevation
import org.phoenixframework.liveview.constants.Attrs.attrSheetShape
import org.phoenixframework.liveview.constants.Attrs.attrSheetSkipHiddenState
import org.phoenixframework.liveview.constants.Attrs.attrSheetSwipeEnabled
import org.phoenixframework.liveview.constants.Attrs.attrSheetTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrSheetValue
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateBody
import org.phoenixframework.liveview.constants.Templates.templateDragHandle
import org.phoenixframework.liveview.constants.Templates.templateSheetContent
import org.phoenixframework.liveview.constants.Templates.templateTopBar
import org.phoenixframework.liveview.extensions.SHEET_VALUE_KEY
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.extensions.toSheetValue
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
 * Material Design standard bottom sheet scaffold.
 * This component must have at least two children:
 * - A content using the "body" template;
 * - and the bottom sheet content using the "sheetContent" template.
 * ```
 * <BottomSheetScaffold>
 *   <Box style="fillMaxSize()" template="sheetContent">
 *     <Text>Sheet content</Text>
 *   </Box>
 *   <Box style="fillMaxSize()" template="body">
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
 *   <Box style="fillMaxWidth()" template="dragHandle">
 *     <Icon imageVector="filled:ArrowUpward" align="center" />
 *   </Box>
 *   <TopAppBar template="topBar">
 *     <Text template="title">Title</Text>
 *   </TopAppBar>
 *   <Box style="fillMaxSize()" template="sheetContent">
 *     <Text>Sheet content</Text>
 *   </Box>
 *   <Box style="fillMaxSize()" template="body">
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
internal class BottomSheetScaffoldView private constructor(props: Properties) :
    ComposableView<BottomSheetScaffoldView.Properties>(props) {

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
                snackbarHostState.showSnackbar(SnackbarView.visualsFromNode(snackBar.node))
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
            EVENT_TYPE_CHANGE,
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

    internal object Factory : ComposableViewFactory<BottomSheetScaffoldView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): BottomSheetScaffoldView = BottomSheetScaffoldView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrContainerColor -> containerColor(props, attribute.value)
                    attrContentColor -> contentColor(props, attribute.value)
                    attrPhxChange -> sheetOnChanged(props, attribute.value)
                    attrSheetContainerColor -> sheetContainerColor(props, attribute.value)
                    attrSheetContentColor -> sheetContentColor(props, attribute.value)
                    attrSheetPeekHeight -> sheetPickHeight(props, attribute.value)
                    attrSheetShadowElevation -> sheetShadowElevation(props, attribute.value)
                    attrSheetShape -> sheetShape(props, attribute.value)
                    attrSheetSkipHiddenState -> sheetSkipHiddenState(props, attribute.value)
                    attrSheetSwipeEnabled -> sheetSwipeEnabled(props, attribute.value)
                    attrSheetTonalElevation -> sheetTonalElevation(props, attribute.value)
                    attrSheetValue -> sheetValue(props, attribute.value)
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

        override fun subTags(): Map<String, ComposableViewFactory<*>> {
            return mapOf(
                ComposableTypes.snackbar to SnackbarView.Factory
            )
        }

        /**
         * The color used for the background of this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold containerColor="#FFFFFFFF" >
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, containerColor: String): Properties {
            return props.copy(containerColor = containerColor.toColor())
        }

        /**
         * The preferred color for content inside this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold contentColor="#FF000000" >
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, contentColor: String): Properties {
            return props.copy(contentColor = contentColor.toColor())
        }

        /**
         * Function in the server to be called when the bottom sheet state changes.
         * ```
         * <BottomSheetScaffold phx-change="updateBottomSheet" >
         * ```
         * @param onChanged the name of the function to be called in the server when the bottom
         * sheet is expanded.
         */
        private fun sheetOnChanged(props: Properties, onChanged: String): Properties {
            return props.copy(onChanged = onChanged)
        }

        /**
         * The color used for the sheet's background of this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetContainerColor="#FFFFFFFF" >
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun sheetContainerColor(props: Properties, containerColor: String): Properties {
            return props.copy(sheetContainerColor = containerColor.toColor())
        }

        /**
         * The preferred color for sheet's content inside this BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetContentColor="#FF000000" >
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun sheetContentColor(props: Properties, contentColor: String): Properties {
            return props.copy(sheetContentColor = contentColor.toColor())
        }

        /**
         * The shadow elevation of the bottom sheet.
         * ```
         * <BottomSheetScaffold sheetShadowElevation="12" >
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        private fun sheetShadowElevation(props: Properties, shadowElevation: String): Properties {
            return if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(sheetShadowElevation = shadowElevation.toInt().dp)
            } else props
        }

        /**
         * The height of the bottom sheet when it is collapsed.
         * ```
         * <BottomSheetScaffold sheetPeekHeight="50" >
         * ```
         * @param height height of the bottom sheet peek area (whe it is not hidden).
         */
        private fun sheetPickHeight(props: Properties, height: String): Properties {
            return if (height.isNotEmptyAndIsDigitsOnly()) {
                props.copy(sheetPickHeight = height.toInt().dp)
            } else props
        }

        /**
         * Defines the shape of the BottomSheetScaffold sheet's container.
         * ```
         * <BottomSheetScaffold sheetShape="16" >
         * ```
         * @param shape BottomSheetScaffold's sheet shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        private fun sheetShape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(sheetShape = shapeFromString(shape))
            } else props
        }

        /**
         * Whether the sheet swiping is enabled and should react to the user's input
         * ```
         * <BottomSheetScaffold sheetSwipeEnabled="true" >
         * ```
         * @param enabled true if the sheet swiping is enabled, false otherwise.
         */
        private fun sheetSwipeEnabled(props: Properties, enabled: String): Properties {
            return if (enabled.isNotEmpty()) {
                props.copy(sheetSwipeEnabled = enabled.toBoolean())
            } else props
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
        private fun sheetTonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                return props.copy(sheetTonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Whether Hidden state is skipped for BottomSheetScaffold.
         * ```
         * <BottomSheetScaffold sheetSkipHiddenState="true" >
         * ```
         * @param skipHiddenState true if the hidden state must be skipped for BottomSheetScaffold,
         * false otherwise.
         */
        private fun sheetSkipHiddenState(props: Properties, skipHiddenState: String): Properties {
            return props.copy(sheetSkipHiddenState = skipHiddenState.toBoolean())
        }

        /**
         * Possible values of SheetState. The `hidden` value is only allowed setting the
         * `sheetSkipHiddenState` property to true.
         * ```
         * <BottomSheetScaffold sheetValue="expanded" >
         * ```
         * @param sheetValue the value representing the state of the bottom sheet. See the possible
         * values at [org.phoenixframework.liveview.constants.SheetValues].
         */
        private fun sheetValue(props: Properties, sheetValue: String): Properties {
            return props.copy(
                sheetValue = sheetValue.toSheetValue() ?: SheetValue.PartiallyExpanded
            )
        }
    }
}
package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrEdgePadding
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedTabIndex
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design fixed tabs and scrollable tabs.
 * In order to create fixed tabs use the `TabRow` tag.
 * ```
 * <TabRow selectedTabIndex="0">
 *   <Tab ...>
 *   <Tab ...>
 * </TabRow>
 * ```
 * For scrollable tabs, use `ScrollableTabRow`.
 * ```
 * <ScrollableTabRow selectedTabIndex="0">
 *   <Tab ...>
 *   <Tab ...>
 * </ScrollableTabRow>
 * ```
 */
internal class TabRowDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val selectedTabIndex = builder.selectedTabIndex
    private val contentColor = builder.contentColor
    private val containerColor = builder.containerColor
    private val edgePadding = builder.edgePadding

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val divider = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateDivider }
        }
        val tabs = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template != Templates.templateDivider }
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.tabRow ->
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = modifier,
                    containerColor = containerColor ?: TabRowDefaults.primaryContainerColor,
                    contentColor = contentColor ?: TabRowDefaults.primaryContentColor,
                    //indicator = { tabPositions ->
                    // TODO How to pass the tab positions?
                    //},
                    divider = {
                        divider?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: HorizontalDivider()
                    },
                    tabs = {
                        tabs?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            ComposableTypes.scrollableTabRow ->
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = modifier,
                    containerColor = containerColor ?: TabRowDefaults.primaryContainerColor,
                    contentColor = contentColor ?: TabRowDefaults.primaryContentColor,
                    edgePadding = edgePadding ?: TabRowDefaults.ScrollableTabRowEdgeStartPadding,
                    //indicator = { tabPositions ->
                    // TODO How to pass the tab positions?
                    //},
                    divider = {
                        divider?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        } ?: HorizontalDivider()
                    },
                    tabs = {
                        tabs?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )
        }
    }

    internal class Builder : ComposableBuilder() {
        var selectedTabIndex: Int = 0
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var edgePadding: Dp? = null
            private set

        /**
         * The index of the currently selected tab.
         * ```
         * <TabRow selectedTabIndex="0" />
         * ```
         * @param index current selected tab index.
         */
        fun selectedTabIndex(index: String) = apply {
            this.selectedTabIndex = index.toIntOrNull() ?: 0
        }

        /**
         * The color used for the background of this tab row.
         * ```
         * <TabRow containerColor="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside this tab row.
         * ```
         * <TabRow contentColor="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
        }

        /**
         * The padding between the starting and ending edge of the scrollable tab row, and the tabs
         * inside the row. This padding helps inform the user that this tab row can be scrolled.
         * ```
         * <TabRow selectedTabIndex="0" />
         * ```
         * @param padding current selected tab index.
         */
        fun edgePadding(padding: String) = apply {
            this.edgePadding = padding.toIntOrNull()?.dp
        }

        fun build() = TabRowDTO(this)
    }
}

internal object TabRowDtoFactory : ComposableViewFactory<TabRowDTO, TabRowDTO.Builder>() {
    /**
     * Creates a `TabRowDTO` object based on the attributes of the input `Attributes` object.
     * TabRowDTO co-relates to the TabRow composable
     * @param attributes the `Attributes` object to create the `TabRowDTO` object from
     * @return a `TabRowDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TabRowDTO = attributes.fold(TabRowDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrEdgePadding -> builder.edgePadding(attribute.value)
            attrSelectedTabIndex -> builder.selectedTabIndex(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TabRowDTO.Builder
    }.build()
}
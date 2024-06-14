package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrEdgePadding
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedTabIndex
import org.phoenixframework.liveview.data.constants.Templates.templateDivider
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
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
internal class TabRowView private constructor(props: Properties) :
    ComposableView<TabRowView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val selectedTabIndex = props.selectedTabIndex
        val contentColor = props.contentColor
        val containerColor = props.containerColor
        val edgePadding = props.edgePadding

        val divider = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateDivider }
        }
        val tabs = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template != templateDivider }
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.tabRow ->
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = props.commonProps.modifier,
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
                    modifier = props.commonProps.modifier,
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

    @Stable
    internal data class Properties(
        val selectedTabIndex: Int,
        val containerColor: Color?,
        val contentColor: Color?,
        val edgePadding: Dp?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var selectedTabIndex: Int = 0
        private var containerColor: Color? = null
        private var contentColor: Color? = null
        private var edgePadding: Dp? = null

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

        fun build() = TabRowView(
            Properties(
                selectedTabIndex,
                containerColor,
                contentColor,
                edgePadding,
                commonProps,
            )
        )
    }
}

internal object TabRowViewFactory : ComposableViewFactory<TabRowView>() {
    /**
     * Creates a `TabRowView` object based on the attributes of the input `Attributes` object.
     * TabRowView co-relates to the TabRow composable
     * @param attributes the `Attributes` object to create the `TabRowView` object from
     * @return a `TabRowView` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TabRowView = attributes.fold(TabRowView.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrEdgePadding -> builder.edgePadding(attribute.value)
            attrSelectedTabIndex -> builder.selectedTabIndex(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TabRowView.Builder
    }.build()
}
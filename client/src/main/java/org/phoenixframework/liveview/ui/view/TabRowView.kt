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
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrEdgePadding
import org.phoenixframework.liveview.constants.Attrs.attrSelectedTabIndex
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateDivider
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
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
        val selectedTabIndex: Int = 0,
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val edgePadding: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<TabRowView>() {
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
        ): TabRowView = TabRowView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrEdgePadding -> edgePadding(props, attribute.value)
                attrSelectedTabIndex -> selectedTabIndex(props, attribute.value)
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
         * The index of the currently selected tab.
         * ```
         * <TabRow selectedTabIndex="0" />
         * ```
         * @param index current selected tab index.
         */
        private fun selectedTabIndex(props: Properties, index: String): Properties {
            return props.copy(selectedTabIndex = index.toIntOrNull() ?: 0)
        }

        /**
         * The color used for the background of this tab row.
         * ```
         * <TabRow containerColor="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, containerColor: String): Properties {
            return props.copy(containerColor = containerColor.toColor())
        }

        /**
         * The preferred color for content inside this tab row.
         * ```
         * <TabRow contentColor="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, contentColor: String): Properties {
            return props.copy(contentColor = contentColor.toColor())
        }

        /**
         * The padding between the starting and ending edge of the scrollable tab row, and the tabs
         * inside the row. This padding helps inform the user that this tab row can be scrolled.
         * ```
         * <TabRow selectedTabIndex="0" />
         * ```
         * @param padding current selected tab index.
         */
        private fun edgePadding(props: Properties, padding: String): Properties {
            return props.copy(edgePadding = padding.toIntOrNull()?.dp)
        }
    }
}
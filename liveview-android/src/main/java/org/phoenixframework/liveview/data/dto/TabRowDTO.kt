package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedTabIndex
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

internal class TabRowDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val selectedTabIndex = builder.selectedTabIndex
    private val contentColor = builder.contentColor
    private val containerColor = builder.containerColor

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
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
            contentColor = contentColor ?: TabRowDefaults.contentColor,
            containerColor = containerColor ?: TabRowDefaults.containerColor,
//            indicator = { tabPositions ->
//                // TODO How to pass the tab positions
//            },
            divider = {
                divider?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                } ?: Divider()
            },
            tabs = {
                tabs?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )
    }

    internal class Builder : ComposableBuilder() {
        var selectedTabIndex: Int = 0
            private set
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set

        /**
         * The index of the currently selected tab.
         * ```
         * <TabRow selected-tab-index="0" />
         * ```
         * @param index current selected tab index.
         */
        fun selectedTabIndex(index: String) = apply {
            this.selectedTabIndex = index.toIntOrNull() ?: 0
        }

        /**
         * The color used for the background of this tab row.
         * ```
         * <TabRow container-color="#FFFFFFFF" />
         * ```
         * @param containerColor the background color in AARRGGBB format.
         */
        fun containerColor(containerColor: String) = apply {
            this.containerColor = containerColor.toColor()
        }

        /**
         * The preferred color for content inside this tab row.
         * ```
         * <TabRow content-color="#FF000000" />
         * ```
         * @param contentColor the content color in AARRGGBB format.
         */
        fun contentColor(contentColor: String) = apply {
            this.contentColor = contentColor.toColor()
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
            attrSelectedTabIndex -> builder.selectedTabIndex(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrContainerColor -> builder.containerColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TabRowDTO.Builder
    }.build()
}
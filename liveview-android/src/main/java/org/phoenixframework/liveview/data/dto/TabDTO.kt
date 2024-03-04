package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrSelectedContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrUnselectedContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design tab.
 * Usually, this component is declared inside of a TabRow.
 * This component supports the following children:
 * - a text can be defined using a child with `text` template;
 * - a icon bar can be defined using a child with `bottomBar` template;
 * ```
 * <Tab selected="true" phx-click="selectTab" phx-value="0">
 *   <Text template="text">Tab Title</Text>
 *   <Icon template="icon" imageVector="filled:Add"/>
 * </Tab>
 * ```
 * The code above shows the icon on top of the title. In order to have the icon on the left of the
 * title, use `LeadingIconTab`.
 *
 * A custom content can be set to a tab defining children without any template.
 * ```
 * <Tab selected="true" phx-click="selectTab" phx-value="0">
 *   <Row>
 *     <Icon template="icon" imageVector="filled:Add"/>
 *     <Text>Tab Title</Text>
 *   </Row>
 * </Tab>
 * ```
 */
internal class TabDTO private constructor(props: Properties) :
    ComposableView<TabDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val onClick = props.onClick
        val selected = props.selected
        val enabled = props.enabled
        val selectedContentColor = props.selectedContentColor
        val unselectedContentColor = props.unselectedContentColor

        val text = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateText }
        }
        val icon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateIcon }
        }
        val selectedColor = selectedContentColor ?: LocalContentColor.current
        when (composableNode?.node?.tag) {
            ComposableTypes.tab ->
                if (text == null && icon == null) {
                    Tab(
                        selected = selected,
                        onClick = onClickFromString(
                            pushEvent,
                            onClick,
                            props.commonProps.phxValue
                        ),
                        modifier = props.commonProps.modifier,
                        enabled = enabled,
                        selectedContentColor = selectedColor,
                        unselectedContentColor = unselectedContentColor ?: selectedColor,
                    ) {
                        composableNode.children.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }

                } else {
                    Tab(
                        selected = selected,
                        onClick = onClickFromString(
                            pushEvent,
                            onClick,
                            props.commonProps.phxValue
                        ),
                        modifier = props.commonProps.modifier,
                        enabled = enabled,
                        text = text?.let {
                            {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        icon = icon?.let {
                            {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        selectedContentColor = selectedColor,
                        unselectedContentColor = unselectedContentColor ?: selectedColor,
                    )
                }

            ComposableTypes.leadingIconTab ->
                LeadingIconTab(
                    selected = selected,
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    text = text?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    } ?: {},
                    icon = icon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    } ?: {},
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    selectedContentColor = selectedColor,
                    unselectedContentColor = unselectedContentColor ?: selectedColor,
                )
        }
    }

    @Stable
    internal data class Properties(
        val onClick: String,
        val selected: Boolean,
        val enabled: Boolean,
        val selectedContentColor: Color?,
        val unselectedContentColor: Color?,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var onClick: String = ""
        private var selected: Boolean = false
        private var enabled: Boolean = true
        private var selectedContentColor: Color? = null
        private var unselectedContentColor: Color? = null

        /**
         * Sets the event name to be triggered on the server when the tab is clicked.
         *
         * ```
         * <Tab phx-click="yourServerEventHandler">...</Tab>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onClick(event: String) = apply {
            this.onClick = event
        }

        /**
         * A boolean value indicating if the component is selected or not.
         *
         * ```
         * <Tab selected="true" />
         * ```
         * @param selected true if the component is selected, false otherwise.
         */
        fun selected(selected: String) = apply {
            this.selected = selected.toBoolean()
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <Tab enabled="true" />
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        /**
         * The color used for the background of this tab row.
         * ```
         * <Tab selectedContentColor="#FFFFFFFF" />
         * ```
         * @param selectedContentColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun selectedContentColor(selectedContentColor: String) = apply {
            this.selectedContentColor = selectedContentColor.toColor()
        }

        /**
         * The preferred color for content inside this tab row.
         * ```
         * <Tab unselectedContentColor="#FF000000" />
         * ```
         * @param unselectedContentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun unselectedContentColor(unselectedContentColor: String) = apply {
            this.unselectedContentColor = unselectedContentColor.toColor()
        }

        fun build() = TabDTO(
            Properties(
                onClick,
                selected,
                enabled,
                selectedContentColor,
                unselectedContentColor,
                commonProps,
            )
        )
    }
}

internal object TabDtoFactory : ComposableViewFactory<TabDTO>() {
    /**
     * Creates a `TabDTO` object based on the attributes of the input `Attributes` object.
     * TabDTO co-relates to the Tab composable.
     * @param attributes the `Attributes` object to create the `TabDTO` object from
     * @return a `TabDTO` object based on the attributes of the input `Attributes` object
     **/
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): TabDTO = attributes.fold(TabDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrEnabled -> builder.enabled(attribute.value)
            attrPhxClick -> builder.onClick(attribute.value)
            attrSelected -> builder.selected(attribute.value)
            attrSelectedContentColor -> builder.selectedContentColor(attribute.value)
            attrUnselectedContentColor -> builder.unselectedContentColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as TabDTO.Builder
    }.build()
}
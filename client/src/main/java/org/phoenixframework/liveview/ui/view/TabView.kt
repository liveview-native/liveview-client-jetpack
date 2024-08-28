package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.Attrs.attrSelectedContentColor
import org.phoenixframework.liveview.constants.Attrs.attrUnselectedContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateIcon
import org.phoenixframework.liveview.constants.Templates.templateText
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
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
internal class TabView private constructor(props: Properties) :
    ComposableView<TabView.Properties>(props) {

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
        val onClick: String = "",
        val selected: Boolean = false,
        val enabled: Boolean = true,
        val selectedContentColor: Color? = null,
        val unselectedContentColor: Color? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<TabView>() {
        /**
         * Creates a `TabView` object based on the attributes of the input `Attributes` object.
         * TabView co-relates to the Tab composable.
         * @param attributes the `Attributes` object to create the `TabView` object from
         * @return a `TabView` object based on the attributes of the input `Attributes` object
         **/
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): TabView = TabView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrEnabled -> enabled(props, attribute.value)
                attrPhxClick -> onClick(props, attribute.value)
                attrSelected -> selected(props, attribute.value)
                attrSelectedContentColor -> selectedContentColor(props, attribute.value)
                attrUnselectedContentColor -> unselectedContentColor(props, attribute.value)
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
         * Sets the event name to be triggered on the server when the tab is clicked.
         *
         * ```
         * <Tab phx-click="yourServerEventHandler">...</Tab>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onClick(props: Properties, event: String): Properties {
            return props.copy(onClick = event)
        }

        /**
         * A boolean value indicating if the component is selected or not.
         *
         * ```
         * <Tab selected="true" />
         * ```
         * @param selected true if the component is selected, false otherwise.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }

        /**
         * A boolean value indicating if the component is enabled or not.
         *
         * ```
         * <Tab enabled="true" />
         * ```
         * @param enabled true if the component is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        /**
         * The color used for the background of this tab row.
         * ```
         * <Tab selectedContentColor="#FFFFFFFF" />
         * ```
         * @param selectedContentColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun selectedContentColor(
            props: Properties,
            selectedContentColor: String
        ): Properties {
            return props.copy(selectedContentColor = selectedContentColor.toColor())
        }

        /**
         * The preferred color for content inside this tab row.
         * ```
         * <Tab unselectedContentColor="#FF000000" />
         * ```
         * @param unselectedContentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun unselectedContentColor(
            props: Properties,
            unselectedContentColor: String
        ): Properties {
            return props.copy(unselectedContentColor = unselectedContentColor.toColor())
        }
    }
}
package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrGesturesEnabled
import org.phoenixframework.liveview.constants.Attrs.attrIsOpen
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates
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
 * Material Design navigation drawer.
 * It can has two children: one with the template `drawerContent` will display the content inside
 * the drawer; and the second one is the screen content.
 * ```
 * <ModalNavigationDrawer
 *   isOpen={"#{@drawerIsOpen}"}
 *   phx-change="onChange">
 *    <ModalDrawerSheet template="drawerContent">...</ModalDrawerSheet>
 *    <Scaffold>...</Scaffold>
 *  </ModalNavigationDrawer>
 * ```
 * Notice that it's essential to declared the `isOpen`, `onClose`, and `onOpen` properties in
 * order to control if the drawer is open or not.
 * You can also use a `PermanentNavigationDrawer` or a `DismissibleNavigationDrawer`.
 */
internal class NavigationDrawerView private constructor(props: Properties) :
    ComposableView<NavigationDrawerView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val gesturesEnabled = props.gesturesEnabled
        val isOpen = props.isOpen
        val onChange = props.onChange
        val scrimColor = props.scrimColor

        val drawerContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateDrawerContent }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != Templates.templateDrawerContent }
        }
        val tag = composableNode?.node?.tag
        if (tag == ComposableTypes.permanentNavigationDrawer) {
            PermanentNavigationDrawer(
                drawerContent = {
                    drawerContent?.let {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                },
                modifier = props.commonProps.modifier,
                content = {
                    content?.let {
                        PhxLiveView(it, pushEvent, composableNode, null)
                    }
                },
            )
        } else {
            val drawerState =
                rememberDrawerState(
                    initialValue = if (isOpen) DrawerValue.Open else DrawerValue.Closed,
                    confirmStateChange = { drawerValue ->
                        val drawerIsOpen = drawerValue == DrawerValue.Open
                        if (onChange.isNotEmpty()) {
                            val pushValue = mergeValueWithPhxValue(KEY_DRAWER_VALUE, drawerIsOpen)
                            pushEvent(EVENT_TYPE_CHANGE, onChange, pushValue, null)
                        }
                        true
                    },
                )
            when (composableNode?.node?.tag) {
                ComposableTypes.modalNavigationDrawer -> {
                    ModalNavigationDrawer(
                        drawerContent = {
                            drawerContent?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        modifier = props.commonProps.modifier,
                        drawerState = drawerState,
                        gesturesEnabled = gesturesEnabled,
                        scrimColor = scrimColor ?: DrawerDefaults.scrimColor,
                        content = {
                            content?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                    )
                }

                ComposableTypes.dismissibleNavigationDrawer ->
                    DismissibleNavigationDrawer(
                        drawerContent = {
                            drawerContent?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                        modifier = props.commonProps.modifier,
                        drawerState = drawerState,
                        gesturesEnabled = gesturesEnabled,
                        content = {
                            content?.let {
                                PhxLiveView(it, pushEvent, composableNode, null)
                            }
                        },
                    )
            }
            LaunchedEffect(composableNode) {
                if (isOpen && drawerState.isClosed) {
                    drawerState.open()
                } else if (!isOpen && drawerState.isOpen) {
                    drawerState.close()
                }
            }
        }
    }

    companion object {
        const val KEY_DRAWER_VALUE = "isOpen"
    }

    @Stable
    internal data class Properties(
        val gesturesEnabled: Boolean = true,
        val scrimColor: Color? = null,
        val isOpen: Boolean = false,
        val onChange: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<NavigationDrawerView>() {
        /**
         * Creates a `NavigationDrawerView` object based on the attributes of the input `Attributes`
         * object. NavigationDrawerView co-relates to the ModalNavigationDrawer,
         * DismissibleNavigationDrawer, and PermanentNavigationDrawer composable.
         * @param attributes the `Attributes` object to create the `NavigationDrawerView` object from.
         * @return a `NavigationDrawerView` object based on the attributes of the input `Attributes`
         * object.
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): NavigationDrawerView = NavigationDrawerView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrGesturesEnabled -> gesturesEnabled(props, attribute.value)
                    attrIsOpen -> isOpen(props, attribute.value)
                    attrPhxChange -> onChange(props, attribute.value)
                    attrScrimColor -> scrimColor(props, attribute.value)
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
         * Indicates if the drawer is opened or closed. This property is only used by
         * `ModalNavigationDrawer` and `DismissibleNavigationDrawer`
         * ```
         * <ModalNavigationDrawer isOpen={"#{@drawerIsOpen}"} >
         * ```
         * @param isOpen true if the drawer is opened, false if it is closed.
         */
        private fun isOpen(props: Properties, isOpen: String): Properties {
            return props.copy(isOpen = isOpen.toBoolean())
        }

        /**
         * Whether or not the drawer can be interacted by gestures. This property is only used by
         * `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer gesturesEnabled="false">
         * ```
         * @param gesturesEnabled true if the user can interact with the drawer using gestures,
         * false otherwise.
         */
        private fun gesturesEnabled(props: Properties, gesturesEnabled: String): Properties {
            return props.copy(gesturesEnabled = gesturesEnabled.toBoolean())
        }

        /**
         * Color of the scrim that obscures content when the drawer is open. This is only used by
         * the `ModalNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer scrimColor="#FF000000">
         * ```
         * @scrimColor the scrim color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun scrimColor(props: Properties, scrimColor: String): Properties {
            return props.copy(scrimColor = scrimColor.toColor())
        }

        /**
         * Function to be called on the server when the drawer is open or closed. This property
         * is only used by `ModalNavigationDrawer` and `DismissibleNavigationDrawer`. The server
         * receives a boolean parameter in order to indicate if the drawer is open or closed.
         * ```
         * <ModalNavigationDrawer phx-change="onChange" >
         * ```
         * @param event name of the function to be called on the server when the drawer is closed.
         */
        private fun onChange(props: Properties, event: String): Properties {
            return props.copy(onChange = event)
        }
    }
}
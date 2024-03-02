package org.phoenixframework.liveview.data.dto

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
import org.phoenixframework.liveview.data.constants.Attrs.attrGesturesEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrIsOpen
import org.phoenixframework.liveview.data.constants.Attrs.attrOnClose
import org.phoenixframework.liveview.data.constants.Attrs.attrOnOpen
import org.phoenixframework.liveview.data.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.data.constants.Templates
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design navigation drawer.
 * It can has two children: one with the template `drawerContent` will display the content inside
 * the drawer; and the second one is the screen content.
 * ```
 * <ModalNavigationDrawer
 *   isOpen={"#{@drawerIsOpen}"}
 *   onClose="closeDrawer"
 *   onOpen="openDrawer">
 *    <ModalDrawerSheet template="drawerContent">...</ModalDrawerSheet>
 *    <Scaffold>...</Scaffold>
 *  </ModalNavigationDrawer>
 * ```
 * Notice that it's essential to declared the `isOpen`, `onClose`, and `onOpen` properties in
 * order to control if the drawer is open or not.
 * You can also use a `PermanentNavigationDrawer` or a `DismissibleNavigationDrawer`.
 */
internal class NavigationDrawerDTO private constructor(props: Properties) :
    ComposableView<NavigationDrawerDTO.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val gesturesEnabled = props.gesturesEnabled
        val isOpen = props.isOpen
        val onClose = props.onClose
        val onOpen = props.onOpen
        val scrimColor = props.scrimColor

        val drawerContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == Templates.templateDrawerContent }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != Templates.templateDrawerContent }
        }
        val tag = composableNode?.node?.tag
        if (tag == ComposableTypes.permanentNavigationDrawer) {
            PermanentNavigationDrawer(drawerContent = {
                drawerContent?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            }, modifier = props.commonProps.modifier, content = {
                content?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            })
        } else {
            val drawerState =
                rememberDrawerState(initialValue = if (isOpen) DrawerValue.Open else DrawerValue.Closed,
                    confirmStateChange = { drawerValue ->
                        val event = if (drawerValue == DrawerValue.Open) onOpen else onClose
                        if (event.isNotEmpty()) {
                            val pushValue = mergeValueWithPhxValue(KEY_DRAWER_VALUE, drawerValue)
                            pushEvent(EVENT_TYPE_CHANGE, event, pushValue, null)
                        }
                        true
                    })
            when (composableNode?.node?.tag) {
                ComposableTypes.modalNavigationDrawer -> {
                    ModalNavigationDrawer(drawerContent = {
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
                        })
                }

                ComposableTypes.dismissibleNavigationDrawer -> DismissibleNavigationDrawer(
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
                    })
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
        const val KEY_DRAWER_VALUE = "drawerValue"
    }

    @Stable
    internal data class Properties(
        val gesturesEnabled: Boolean = true,
        val scrimColor: Color? = null,
        val isOpen: Boolean = false,
        val onClose: String = "",
        val onOpen: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var gesturesEnabled: Boolean = true
        private var scrimColor: Color? = null
        private var isOpen: Boolean = false
        private var onClose: String = ""
        private var onOpen: String = ""

        /**
         * Indicates if the drawer is opened or closed. This property is only used by
         * `ModalNavigationDrawer` and `DismissibleNavigationDrawer`
         * ```
         * <ModalNavigationDrawer isOpen={"#{@drawerIsOpen}"} >
         * ```
         * @param isOpen true if the drawer is opened, false if it is closed.
         */
        fun isOpen(isOpen: String) = apply {
            this.isOpen = isOpen.toBoolean()
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
        fun gesturesEnabled(gesturesEnabled: String) = apply {
            this.gesturesEnabled = gesturesEnabled.toBoolean()
        }

        /**
         * Color of the scrim that obscures content when the drawer is open. This is only used by
         * the `ModalNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer scrimColor="#FF000000">
         * ```
         * @scrimColor the scrim color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.data.constants.SystemColorValues] colors.
         */
        fun scrimColor(scrimColor: String) = apply {
            this.scrimColor = scrimColor.toColor()
        }

        /**
         * Function to be called on the server to be called when the drawer is closed. This property
         * is only used by `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer onClose="closeDrawer" >
         * ```
         * @param event name of the function to be called on the server when the drawer is closed.
         */
        fun onClose(event: String) = apply {
            this.onClose = event
        }

        /**
         * Function to be called on the server to be called when the drawer is opened. This property
         * is only used by `ModalNavigationDrawer` and `DismissibleNavigationDrawer`.
         * ```
         * <ModalNavigationDrawer onOpen="openDrawer" >
         * ```
         * @param event name of the function to be called on the server when the drawer is opened.
         */
        fun onOpen(event: String) = apply {
            this.onOpen = event
        }

        fun build() = NavigationDrawerDTO(
            Properties(
                gesturesEnabled,
                scrimColor,
                isOpen,
                onClose,
                onOpen,
                commonProps,
            )
        )
    }
}

internal object NavigationDrawerDtoFactory : ComposableViewFactory<NavigationDrawerDTO>() {
    /**
     * Creates a `NavigationDrawerDTO` object based on the attributes of the input `Attributes`
     * object. NavigationDrawerDTO co-relates to the ModalNavigationDrawer,
     * DismissibleNavigationDrawer, and PermanentNavigationDrawer composable.
     * @param attributes the `Attributes` object to create the `NavigationDrawerDTO` object from.
     * @return a `NavigationDrawerDTO` object based on the attributes of the input `Attributes`
     * object.
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): NavigationDrawerDTO = attributes.fold(NavigationDrawerDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrGesturesEnabled -> builder.gesturesEnabled(attribute.value)
            attrIsOpen -> builder.isOpen(attribute.value)
            attrOnClose -> builder.onClose(attribute.value)
            attrOnOpen -> builder.onOpen(attribute.value)
            attrScrimColor -> builder.scrimColor(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as NavigationDrawerDTO.Builder
    }.build()
}
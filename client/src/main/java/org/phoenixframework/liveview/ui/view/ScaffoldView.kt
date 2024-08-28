package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrContentWindowInsets
import org.phoenixframework.liveview.constants.Attrs.attrFabPosition
import org.phoenixframework.liveview.constants.Attrs.attrTopBarScrollBehavior
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.FabPositionValues
import org.phoenixframework.liveview.constants.ScrollBehaviorValues
import org.phoenixframework.liveview.constants.Templates.templateBody
import org.phoenixframework.liveview.constants.Templates.templateBottomBar
import org.phoenixframework.liveview.constants.Templates.templateFab
import org.phoenixframework.liveview.constants.Templates.templateTopBar
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
 * Scaffold implements the basic material design visual layout structure.
 * This component supports the following children:
 * - a title bar can be defined using a child with `topBar` template;
 * - a bottom bar can be defined using a child with `bottomBar` template;
 * - a FloatingActionButton to define the screen main action can be defined using `fab` template;
 * - a `<SnackBar>` to show a snackbar.
 * - `body` will be considered the Scaffold's body.
 * ```
 * <Scaffold>
 *   <TopAppBar template="topBar">
 *     <Text>Screen Title</Text>
 *   </TopAppBar>
 *   <BottomAppBar template="bottomBar">
 *     // Bottom App Bar items
 *   </BottomAppBar>
 *   <FloatingActionButton phx-click="navigateToOtherScreen" template="fab">
 *     <Icon imageVector="filled:Add" />
 *   </FloatingActionButton>
 *   <Snackbar message="message" dismissEvent="hideDialog" />
 *   <Box template="body">
 *       <Text>Screen Body</Text>
 *   </Box>
 * </Scaffold>
 * ```
 */
internal class ScaffoldView private constructor(props: Properties) :
    ComposableView<ScaffoldView.Properties>(props) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val containerColorValue = props.containerColor
        val contentColor = props.contentColor
        val fabPosition = props.fabPosition
        val topAppScrollBehavior = props.topAppScrollBehavior
        val contentWindowInsets = props.contentWindowInsets

        val topBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTopBar }
        }
        val bottomBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBottomBar }
        }
        val floatingActionButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateFab }
        }
        val snackBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == ComposableTypes.snackbar }
        }
        val body = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBody }
        }
        val containerColor = containerColorValue ?: MaterialTheme.colorScheme.background

        val topAppBarScrollBehavior = scrollBehaviorFromString(topAppScrollBehavior)

        val snackbarHostState = remember { SnackbarHostState() }
        CompositionLocalProvider(LocalAppScrollBehavior provides topAppBarScrollBehavior) {
            Scaffold(
                modifier = props.commonProps.modifier.then(
                    if (topAppBarScrollBehavior != null)
                        Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                    else Modifier
                ),
                containerColor = containerColor,
                contentColor = contentColor ?: contentColorFor(containerColor),
                topBar = {
                    topBar?.let { appBar ->
                        PhxLiveView(appBar, pushEvent, composableNode, null)
                    }
                },
                bottomBar = {
                    bottomBar?.let { bottomBar ->
                        PhxLiveView(bottomBar, pushEvent, composableNode, null)
                    }
                },
                floatingActionButton = {
                    floatingActionButton?.let { fab ->
                        PhxLiveView(fab, pushEvent, composableNode, null)
                    }
                },
                floatingActionButtonPosition = fabPosition,
                snackbarHost = {
                    SnackbarHost(snackbarHostState) {
                        snackBar?.let { sb ->
                            PhxLiveView(sb, pushEvent, composableNode, null)
                        }
                    }
                },
                contentWindowInsets = contentWindowInsets ?: ScaffoldDefaults.contentWindowInsets,
                content = { contentPaddingValues ->
                    body?.let { content ->
                        PhxLiveView(content, pushEvent, composableNode, contentPaddingValues)
                    }
                }
            )
        }
        LaunchedEffect(snackBar?.id) {
            if (snackBar != null) {
                snackbarHostState.showSnackbar(SnackbarView.visualsFromNode(snackBar.node))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun scrollBehaviorFromString(scrollBehavior: String?): TopAppBarScrollBehavior? {
        return when (scrollBehavior) {
            ScrollBehaviorValues.exitUntilCollapsed -> TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            ScrollBehaviorValues.enterAlways -> TopAppBarDefaults.enterAlwaysScrollBehavior()
            ScrollBehaviorValues.pinnedScroll -> TopAppBarDefaults.pinnedScrollBehavior()
            else -> null
        }
    }

    @Stable
    internal data class Properties(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val fabPosition: FabPosition = FabPosition.End,
        val topAppScrollBehavior: String? = null,
        val contentWindowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ScaffoldView>() {
        /**
         * Creates a `ScaffoldView` object based on the attributes of the input `Attributes` object.
         * ScaffoldView co-relates to the Scaffold composable
         * @param attributes the `Attributes` object to create the `ScaffoldView` object from
         * @return a `ScaffoldView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): ScaffoldView = ScaffoldView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrContentWindowInsets -> contentWindowInsets(props, attribute.value)
                attrFabPosition -> fabPosition(props, attribute.value)
                attrTopBarScrollBehavior -> topBarScrollBehavior(props, attribute.value)
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
         * Color used for the background of this scaffold.
         *
         * @param color container color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(containerColor = color.toColor())
            } else props
        }

        /**
         * Preferred color for content inside this scaffold.
         *
         * @param color content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return if (color.isNotEmpty()) {
                props.copy(contentColor = color.toColor())
            } else props
        }

        /**
         * Window insets to be passed to content slot via PaddingValues params. Scaffold will take
         * the insets into account from the top/bottom only if the topBar/ bottomBar are not
         * present, as the scaffold expect topBar/bottomBar to handle insets instead.
         * ```
         * <Scaffold contentWindowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the scaffold's content that the
         * inset represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        private fun contentWindowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(contentWindowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }

        /**
         * Position of the Floating Action Button on the screen
         *
         * @param position FAB position on the screen. See the supported values at
         * [org.phoenixframework.liveview.constants.FabPositionValues].
         */
        private fun fabPosition(props: Properties, position: String): Properties {
            return if (position.isNotEmpty()) {
                props.copy(
                    fabPosition = when (position) {
                        FabPositionValues.center -> FabPosition.Center
                        FabPositionValues.start -> FabPosition.Start
                        FabPositionValues.end -> FabPosition.End
                        FabPositionValues.endOverlay -> FabPosition.EndOverlay
                        else -> FabPosition.End
                    }
                )
            } else props
        }

        /**
         * A TopAppBar scroll behavior which holds various offset values that will be applied by
         * this top app bar to set up its height and colors. A scroll behavior is designed to work
         * in conjunction with a scrolled content to change the top app bar appearance as the
         * content scrolls.
         * Once this scroll behavior is set, a NestedScrollConnection will be attached to a
         * Modifier.nestedScroll in order to keep track of the scroll events.
         * @param scrollBehavior see the supported values at
         * [org.phoenixframework.liveview.constants.ScrollBehaviorValues].
         * ```
         * <Scaffold topBarScrollBehavior="exitUntilCollapsed">
         * ```
         */
        private fun topBarScrollBehavior(props: Properties, scrollBehavior: String): Properties {
            return if (scrollBehavior.isNotEmpty()) {
                props.copy(topAppScrollBehavior = scrollBehavior)
            } else props
        }
    }
}

/**
 * The scroll behavior requires a cooperation between Scaffold and TopAppBar. The `TopAbbBar` must
 * set the `scrollBehavior` parameter and the `Scaffold` must add a `Modifier.nestedScroll`. Both
 * must use the same object instance. Therefore, this instance is being passed via compositionLocal.
 */
@OptIn(ExperimentalMaterial3Api::class)
val LocalAppScrollBehavior = compositionLocalOf<TopAppBarScrollBehavior?> { null }
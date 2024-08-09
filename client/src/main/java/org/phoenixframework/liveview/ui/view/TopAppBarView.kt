package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrActionIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrNavigationIconContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrScrolledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateAction
import org.phoenixframework.liveview.constants.Templates.templateNavigationIcon
import org.phoenixframework.liveview.constants.Templates.templateTitle
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
 * TopAppBar is usually used as child of a `Scaffold` and represents the title of the screen.
 * This component can contains the following templates for its children:
 * - `title`: the title to be displayed in the top app bar.
 * - `action`: the actions displayed at the end of the top app bar. This should typically be
 * `IconButton`s. The default layout here is a Row, so icons inside will be placed horizontally.
 * - `navigation-icon`:  the navigation icon displayed at the start of the top app bar. This should
 * typically be an `IconButton`.
 * ```
 * <TopAppBar>
 *   <Text template="title">App title</Text>
 *   <IconButton template="action" phx-click="decrement-count">
 *     <Icon imageVector="filled:Add" />
 *   </IconButton>
 *   <IconButton template="navigation-icon" phx-click="reset-count">
 *     <Icon imageVector="filled:Menu" />
 *   </IconButton>
 * </TopAppBar>
 * ```
 */
internal class TopAppBarView private constructor(props: Properties) :
    ComposableView<TopAppBarView.Properties>(props) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val colors = props.colors
        val windowsInsets = props.windowInsets

        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTitle }
        }
        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == templateAction }
        }
        val navIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateNavigationIcon }
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.topAppBar ->
                TopAppBar(
                    title = {
                        title?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
                    navigationIcon = {
                        navIcon?.let {
                            Box {
                                PhxLiveView(it, pushEvent, composableNode, null, this)
                            }
                        }
                    },
                    actions = {
                        actions?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    },
                    windowInsets = windowsInsets ?: TopAppBarDefaults.windowInsets,
                    colors = getTopAppBarColors(colors = colors),
                    scrollBehavior = LocalAppScrollBehavior.current,
                )

            ComposableTypes.centerAlignedTopAppBar ->
                CenterAlignedTopAppBar(
                    title = {
                        title?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
                    navigationIcon = {
                        navIcon?.let {
                            Box {
                                PhxLiveView(it, pushEvent, composableNode, null, this)
                            }
                        }
                    },
                    actions = {
                        actions?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    },
                    windowInsets = windowsInsets ?: TopAppBarDefaults.windowInsets,
                    colors = getCenterTopAppBarColors(colors = colors),
                    scrollBehavior = LocalAppScrollBehavior.current,
                )

            ComposableTypes.mediumTopAppBar ->
                MediumTopAppBar(
                    title = {
                        title?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
                    navigationIcon = {
                        navIcon?.let {
                            Box {
                                PhxLiveView(it, pushEvent, composableNode, null, this)
                            }
                        }
                    },
                    actions = {
                        actions?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    },
                    windowInsets = windowsInsets ?: TopAppBarDefaults.windowInsets,
                    colors = getMediumTopAppBarColors(colors = colors),
                    scrollBehavior = LocalAppScrollBehavior.current,
                )

            ComposableTypes.largeTopAppBar ->
                LargeTopAppBar(
                    title = {
                        title?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    modifier = props.commonProps.modifier,
                    navigationIcon = {
                        navIcon?.let {
                            Box {
                                PhxLiveView(it, pushEvent, composableNode, null, this)
                            }
                        }
                    },
                    actions = {
                        actions?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    },
                    windowInsets = windowsInsets ?: TopAppBarDefaults.windowInsets,
                    colors = getLargeTopAppBarColors(colors = colors),
                    scrollBehavior = LocalAppScrollBehavior.current,
                )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getTopAppBarColors(colors: ImmutableMap<String, String>?): TopAppBarColors {
        val defaultColors = TopAppBarDefaults.mediumTopAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            TopAppBarDefaults.topAppBarColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                scrolledContainerColor = colors[colorAttrScrolledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                navigationIconContentColor = colors[colorAttrNavigationIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                titleContentColor = colors[colorAttrTitleContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = colors[colorAttrActionIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getCenterTopAppBarColors(colors: ImmutableMap<String, String>?): TopAppBarColors {
        val defaultColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                scrolledContainerColor = colors[colorAttrScrolledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                navigationIconContentColor = colors[colorAttrNavigationIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                titleContentColor = colors[colorAttrTitleContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = colors[colorAttrActionIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getMediumTopAppBarColors(colors: ImmutableMap<String, String>?): TopAppBarColors {
        val defaultColors = TopAppBarDefaults.mediumTopAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                scrolledContainerColor = colors[colorAttrScrolledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                navigationIconContentColor = colors[colorAttrNavigationIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                titleContentColor = colors[colorAttrTitleContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = colors[colorAttrActionIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getLargeTopAppBarColors(colors: ImmutableMap<String, String>?): TopAppBarColors {
        val defaultColors = TopAppBarDefaults.largeTopAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            TopAppBarDefaults.largeTopAppBarColors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                scrolledContainerColor = colors[colorAttrScrolledContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                navigationIconContentColor = colors[colorAttrNavigationIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                titleContentColor = colors[colorAttrTitleContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = colors[colorAttrActionIconContentColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        val windowInsets: WindowInsets? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<TopAppBarView>() {

        /**
         * Creates a `TopAppBarView` object based on the attributes and text of the input `Attributes`
         * object. TopAppBarView co-relates to the TopAppBar composable
         *
         * @param attributes the `Attributes` object to create the `TopAppBarView` object from
         * @return a `TopAppBarView` object based on the attributes and text of the input `Attributes`
         * object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): TopAppBarView = TopAppBarView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrColors -> colors(props, attribute.value)
                    attrWindowInsets -> windowInsets(props, attribute.value)
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
         * Set TopAppBar colors.
         * ```
         * <TopAppBar
         *   colors="{'containerColor': '#FFFF0000', 'titleContentColor': '#FF00FF00'}">
         *   ...
         * </TopAppBar>
         * ```
         * @param colors an JSON formatted string, containing the app bar colors. The color keys
         * supported are: `containerColor`, `scrolledContainerColor`, `navigationIconContentColor,
         * `titleContentColor`, and `actionIconContentColor`.
         */
        fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Window insets to be passed to the top bar via PaddingValues params.
         * ```
         * <TopAppBar windowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(windowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }
    }
}
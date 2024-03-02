package org.phoenixframework.liveview.data.dto

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
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActionIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrNavigationIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrScrolledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateNavigationIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
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
internal class TopAppBarDTO private constructor(props: Properties) :
    ComposableView<TopAppBarDTO.Properties>(props) {

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

    class Builder : ComposableBuilder() {
        private var colors: ImmutableMap<String, String>? = null
        private var windowInsets: WindowInsets? = null

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
        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)?.toImmutableMap()
            }
        }

        /**
         * Window insets to be passed to the top bar via PaddingValues params.
         * ```
         * <TopAppBar windowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(insets: String) = apply {
            try {
                this.windowInsets = windowInsetsFromString(insets)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun build() = TopAppBarDTO(
            Properties(
                colors,
                windowInsets,
                commonProps,
            )
        )
    }
}

internal object TopAppBarDtoFactory : ComposableViewFactory<TopAppBarDTO>() {

    /**
     * Creates a `TopAppBarDTO` object based on the attributes and text of the input `Attributes`
     * object. TopAppBarDTO co-relates to the TopAppBar composable
     *
     * @param attributes the `Attributes` object to create the `TopAppBarDTO` object from
     * @return a `TopAppBarDTO` object based on the attributes and text of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TopAppBarDTO {
        val builder = TopAppBarDTO.Builder()

        attributes.forEach { attribute ->
            when (attribute.name) {
                attrColors -> builder.colors(attribute.value)
                attrWindowInsets -> builder.windowInsets(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            }
        }
        return builder.build()
    }
}
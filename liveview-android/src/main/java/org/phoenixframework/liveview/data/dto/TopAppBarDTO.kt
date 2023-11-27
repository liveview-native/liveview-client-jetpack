package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.dto.Attrs.attrColors
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.actionTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.navigationIconTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.titleTag
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * TopAppBar is usually used as child of a `Scaffold` and represents the title of the screen.
 * This component can contains the following templates for its children:
 * - `title`: the title to be displayed in the top app bar.
 * - `action`: the actions displayed at the end of the top app bar. This should typically be
 * `IconButton`s. The default layout here is a Row, so icons inside will be placed horizontally.
 * - `navIcon`:  the navigation icon displayed at the start of the top app bar. This should
 * typically be an `IconButton`.
 * ```
 * <TopAppBar>
 *   <Text template="title">App title</Text>
 *   <IconButton template="action" phx-click="decrement-count">
 *     <Icon image-vector="filled:Add" />
 *   </IconButton>
 *   <IconButton template="navIcon" phx-click="reset-count">
 *     <Icon image-vector="filled:Menu" />
 *   </IconButton>
 * </TopAppBar>
 * ```
 */
internal class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val colors: ImmutableMap<String, String>? = builder.colors?.toImmutableMap()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == titleTag }
        }
        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.template == actionTag }
        }
        val navIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == navigationIconTag }
        }
        TopAppBar(
            colors = getTopAppBarColors(colors = colors),
            title = {
                title?.let {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
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
            modifier = modifier,
        )
    }

    class Builder : ComposableBuilder() {
        var colors: Map<String, String>? = null
            private set

        /**
         * Set TopAppBar colors.
         * ```
         * <TopAppBar
         *   colors="{'containerColor': '#FFFF0000', 'contentColor': '#FF00FF00'}">
         *   ...
         * </Button>
         * ```
         * @param colors an JSON formatted string, containing the app bar colors. The color keys
         * supported are: `containerColor`, `scrolledContainerColor`, `navigationIconContentColor,
         * `titleContentColor`, and `actionIconContentColor`.
         */
        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun build() = TopAppBarDTO(this)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getTopAppBarColors(colors: ImmutableMap<String, String>?): TopAppBarColors {
        val defaultColors = TopAppBarDefaults.topAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            fun value(key: String) =
                colors[key]?.toColor()
                    ?: Color(defaultColors.privateField(key))

            TopAppBarDefaults.topAppBarColors(
                containerColor = value("containerColor"),
                scrolledContainerColor = value("scrolledContainerColor"),
                navigationIconContentColor = value("navigationIconContentColor"),
                titleContentColor = value("titleContentColor"),
                actionIconContentColor = value("actionIconContentColor"),
            )
        }
    }
}

internal object TopAppBarDtoFactory : ComposableViewFactory<TopAppBarDTO, TopAppBarDTO.Builder>() {

    /**
     * Creates a `TopAppBarDTO` object based on the attributes and text of the input `Attributes`
     * object. TopAppBarDTO co-relates to the TopAppBar composable
     *
     * @param attributes the `Attributes` object to create the `TopAppBarDTO` object from
     * @return a `TopAppBarDTO` object based on the attributes and text of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): TopAppBarDTO {
        val builder = TopAppBarDTO.Builder()

        attributes.forEach { attribute ->
            when (attribute.name) {
                attrColors -> builder.colors(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            }
        }
        return builder.build()
    }

    const val titleTag = "title"
    const val actionTag = "action"
    const val navigationIconTag = "navIcon"
}
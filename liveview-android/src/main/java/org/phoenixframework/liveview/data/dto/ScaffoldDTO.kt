package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.dto.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.dto.Attrs.attrContentColor
import org.phoenixframework.liveview.data.dto.Attrs.attrFabPosition
import org.phoenixframework.liveview.data.dto.Templates.templateBody
import org.phoenixframework.liveview.data.dto.Templates.templateFab
import org.phoenixframework.liveview.data.dto.Templates.templateSnackbar
import org.phoenixframework.liveview.data.dto.Templates.templateTopBar
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Scaffold implements the basic material design visual layout structure.
 * This component supports the following children:
 * - a title bar can be defined using a child with `topBar` template;
 * - a FloatingActionButton to define the screen main action can be defined using `fab` template;
 * - a `<SnackBar>` to show a snackbar.
 * - `body` will be considered the Scaffold's body.
 * ```
 * <Scaffold>
 *   <TopAppBar template="topBar">
 *     <Text>Screen Title</Text>
 *   </TopAppBar>
 *   <FloatingActionButton phx-click="navigateToOtherScreen" template="fab">
 *     <Icon imageVector="filled:Add" />
 *   </FloatingActionButton>
 *   <Snackbar message="message" dismiss-event="hideDialog" />
 *   <Box template="body">
 *       <Text>Screen Body</Text>
 *   </Box>
 * </Scaffold>
 * ```
 */
internal class ScaffoldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val containerColor: Color? = builder.containerColor
    private val contentColor: Color? = builder.contentColor
    private val fabPosition: FabPosition = builder.fabPosition

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val topBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTopBar }
        }
        val floatingActionButton = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateFab }
        }
        val snackBar = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == templateSnackbar }
        }
        val body = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBody }
        }
        val containerColor = containerColor ?: MaterialTheme.colorScheme.background

        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor ?: contentColorFor(containerColor),
            topBar = {
                topBar?.let { appBar ->
                    PhxLiveView(appBar, pushEvent, composableNode, null)
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
            content = { contentPaddingValues ->
                body?.let { content ->
                    PhxLiveView(content, pushEvent, composableNode, contentPaddingValues)
                }
            }
        )
        LaunchedEffect(snackBar?.id) {
            if (snackBar != null) {
                snackbarHostState.showSnackbar(SnackbarDTO.visualsFromNode(snackBar.node))
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var containerColor: Color? = null
            private set
        var contentColor: Color? = null
            private set
        var fabPosition: FabPosition = FabPosition.End
            private set

        /**
         * Color used for the background of this scaffold.
         *
         * @param color container color in AARRGGBB format.
         */
        fun containerColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.containerColor = color.toColor()
            }
        }

        /**
         * Preferred color for content inside this scaffold.
         *
         * @param color content color in AARRGGBB format.
         */
        fun contentColor(color: String) = apply {
            if (color.isNotEmpty()) {
                this.contentColor = color.toColor()
            }
        }

        /**
         * Position of the Floating Action Button on the screen
         *
         * @param position FAB position on the screen. Supported values are `center` and `end`.
         */
        fun fabPosition(position: String) = apply {
            if (position.isNotEmpty()) {
                this.fabPosition = when (position) {
                    "center" -> FabPosition.Center
                    else -> FabPosition.End
                }
            }
        }

        fun build() = ScaffoldDTO(this)
    }
}

internal object ScaffoldDtoFactory : ComposableViewFactory<ScaffoldDTO, ScaffoldDTO.Builder>() {
    /**
     * Creates a `ScaffoldDTO` object based on the attributes of the input `Attributes` object.
     * ScaffoldDTO co-relates to the Scaffold composable
     * @param attributes the `Attributes` object to create the `ScaffoldDTO` object from
     * @return a `ScaffoldDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): ScaffoldDTO = attributes.fold(ScaffoldDTO.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrContainerColor -> builder.containerColor(attribute.value)
            attrContentColor -> builder.contentColor(attribute.value)
            attrFabPosition -> builder.fabPosition(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as ScaffoldDTO.Builder
    }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*, *>> {
        return mapOf(
            templateSnackbar to SnackbarDtoFactory
        )
    }
}

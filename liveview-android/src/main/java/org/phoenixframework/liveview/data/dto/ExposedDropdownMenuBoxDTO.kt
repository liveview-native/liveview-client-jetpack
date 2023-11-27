package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design Exposed Dropdown Menu.
 * Menus display a list of choices on a temporary surface. They appear when users interact with a
 * button, action, or other control.
 * Exposed dropdown menus display the currently selected item (most often) in a text field to which
 * the menu is anchored. If the text field input is used to filter results in the menu, the
 * component is also known as "autocomplete" or a "combobox".
 * The first child must be the "anchor", which means the clickable component that will show the
 * menu. This component must add the `menuAnchor` property. The second component and the following
 * ones, will be considered menu items. The menu items are commonly `DropdownMenuItem`, but can be
 * any component.
 * ```
 * <ExposedDropdownMenuBox horizontalPadding="16">
 *   <TextField text={"#{@ddOption}"} readOnly="true" menuAnchor/>
 *   <DropdownMenuItem phx-click="setDDOption" value="A">
 *     <Text>Option A</Text>
 *   </DropdownMenuItem>
 *   <DropdownMenuItem phx-click="setDDOption" value="B" enabled="false">
 *     <Text>Option B</Text>
 *   </DropdownMenuItem>
 *   <DropdownMenuItem phx-click="setDDOption" value="C">
 *     <Text>Option C</Text>
 *   </DropdownMenuItem>
 * </ExposedDropdownMenuBox>
 * ```
 */
internal class ExposedDropdownMenuBoxDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val items = remember(composableNode?.children) {
            composableNode?.children?.let {
                if (it.size > 1) it.copyOfRange(1, it.size) else null
            } ?: emptyArray()
        }
        val body = remember(composableNode?.children) {
            composableNode?.children?.first()
        }
        var isExpanded by remember {
            mutableStateOf(false)
        }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier = modifier,
        ) {
            body?.let {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                items.forEach {
                    PhxLiveView(it, { type, event, value, target ->
                        isExpanded = false
                        pushEvent(type, event, value, target)
                    }, composableNode, null, this)
                }
            }
        }
    }

    internal class Builder : ComposableBuilder() {

        fun build() = ExposedDropdownMenuBoxDTO(this)
    }
}

internal object ExposedDropdownMenuBoxDtoFactory :
    ComposableViewFactory<ExposedDropdownMenuBoxDTO, ExposedDropdownMenuBoxDTO.Builder>() {

    /**
     * Creates a `ExposedDropdownMenuBoxDTO` object based on the attributes of the input
     * `Attributes` object. ExposedDropdownMenuBoxDTO co-relates to the ExposedDropdownMenuBox
     * composable.
     * @param attributes the `Attributes` object to create the `ExposedDropdownMenuBoxDTO` object
     * from
     * @return a `ExposedDropdownMenuBoxDTO` object based on the attributes of the input
     * `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ExposedDropdownMenuBoxDTO =
        attributes.fold(ExposedDropdownMenuBoxDTO.Builder()) { builder, attribute ->
            builder.handleCommonAttributes(
                attribute,
                pushEvent,
                scope
            ) as ExposedDropdownMenuBoxDTO.Builder
        }.build()
}
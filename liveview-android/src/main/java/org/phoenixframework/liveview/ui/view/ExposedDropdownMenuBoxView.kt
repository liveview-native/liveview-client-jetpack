package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.data.constants.ComposableTypes.exposedDropdownMenu
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design Exposed Dropdown Menu.
 * Menus display a list of choices on a temporary surface. They appear when users interact with a
 * button, action, or other control.
 * Exposed dropdown menus display the currently selected item (most often) in a text field to which
 * the menu is anchored. If the text field input is used to filter results in the menu, the
 * component is also known as "autocomplete" or a "combobox".
 * The first child must be the "anchor", which means the clickable component that will show the
 * menu. This component must add the `menuAnchor` property. The second component is an
 * ExposedDropdownMenu which will contain the menu items. The menu items are commonly
 * `DropdownMenuItem`, but can be any component.
 * ```
 * <ExposedDropdownMenuBox horizontalPadding="16">
 *   <TextField text={"#{@ddOption}"} readOnly="true" menuAnchor/>
 *   <ExposedDropdownMenu>
 *     <DropdownMenuItem phx-click="setDDOption" phx-value="A">
 *       <Text>Option A</Text>
 *     </DropdownMenuItem>
 *     <DropdownMenuItem phx-click="setDDOption" phx-value="B" enabled="false">
 *       <Text>Option B</Text>
 *     </DropdownMenuItem>
 *     <DropdownMenuItem phx-click="setDDOption" phx-value="C">
 *       <Text>Option C</Text>
 *     </DropdownMenuItem>
 *   </ExposedDropdownMenu>
 * </ExposedDropdownMenuBox>
 * ```
 */
internal class ExposedDropdownMenuBoxView private constructor(props: Properties) :
    ComposableView<ExposedDropdownMenuBoxView.Properties>(props) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        var isExpanded by remember(props) {
            mutableStateOf(props.expanded)
        }
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = it
            },
            modifier = props.commonProps.modifier,
        ) {
            val scopeWrapper = remember(this, isExpanded) {
                ExposedDropdownMenuBoxScopeWrapper(
                    scope = this,
                    isExpanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                    }
                )
            }
            composableNode?.children?.forEach {
                PhxLiveView(
                    composableNode = it,
                    pushEvent = { type, event, value, target ->
                        isExpanded = false
                        pushEvent(type, event, value, target)
                    },
                    parentNode = composableNode,
                    paddingValues = null,
                    scope = scopeWrapper
                )
            }
        }
    }

    @Stable
    internal data class Properties(
        val expanded: Boolean,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties

    internal class Builder : ComposableBuilder() {
        private var expanded: Boolean = false

        fun expanded(expanded: String) = apply {
            this.expanded = expanded.toBoolean()
        }

        fun build() = ExposedDropdownMenuBoxView(
            Properties(expanded, commonProps)
        )
    }
}

// Wrapper class in order to communicate with ExposedDropdownMenu class.
@OptIn(ExperimentalMaterial3Api::class)
@Stable
internal data class ExposedDropdownMenuBoxScopeWrapper(
    val scope: ExposedDropdownMenuBoxScope,
    val isExpanded: Boolean,
    val onDismissRequest: () -> Unit
)

internal object ExposedDropdownMenuBoxViewFactory :
    ComposableViewFactory<ExposedDropdownMenuBoxView>() {

    /**
     * Creates a `ExposedDropdownMenuBoxView` object based on the attributes of the input
     * `Attributes` object. ExposedDropdownMenuBoxView co-relates to the ExposedDropdownMenuBox
     * composable.
     * @param attributes the `Attributes` object to create the `ExposedDropdownMenuBoxView` object
     * from
     * @return a `ExposedDropdownMenuBoxView` object based on the attributes of the input
     * `Attributes` object
     */
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): ExposedDropdownMenuBoxView =
        attributes.fold(ExposedDropdownMenuBoxView.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrExpanded -> builder.expanded(attribute.value)
                else -> builder.handleCommonAttributes(
                    attribute,
                    pushEvent,
                    scope
                ) as ExposedDropdownMenuBoxView.Builder
            }
        }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*>> {
        return mapOf(exposedDropdownMenu to ExposedDropdownMenuViewFactory)
    }
}
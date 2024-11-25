package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.constants.Attrs.attrValue
import org.phoenixframework.liveview.constants.ComposableTypes.exposedDropdownMenu
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.LocalParentDataHolder
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
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
        val parentDataHolder = LocalParentDataHolder.current
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
            val onItemSelected = { itemValue: Any? ->
                val newValue = mergeValueWithPhxValue(KEY_PHX_VALUE, itemValue)
                parentDataHolder?.setValue(composableNode, newValue)
                isExpanded = false
            }
            CompositionLocalProvider(
                LocalDropdownMenuBoxOnItemSelectedAction provides onItemSelected
            ) {
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

        LaunchedEffect(composableNode?.id) {
            val value = mergeValueWithPhxValue(KEY_PHX_VALUE, props.commonProps.phxValue)
            parentDataHolder?.setValue(composableNode, value)
        }
    }

    @Stable
    internal data class Properties(
        val expanded: Boolean = false,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<ExposedDropdownMenuBoxView>() {

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
        ): ExposedDropdownMenuBoxView = ExposedDropdownMenuBoxView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrExpanded -> expanded(props, attribute.value)
                    attrValue -> props.copy(
                        commonProps = super.setPhxValueFromAttr(
                            props.commonProps,
                            attrPhxValue,
                            attribute.value
                        )
                    )

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
            return mapOf(exposedDropdownMenu to ExposedDropdownMenuView.Factory)
        }

        private fun expanded(props: Properties, expanded: String): Properties {
            return props.copy(expanded = expanded.toBoolean())
        }
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

/**
 * This composition local allows the DropdownMenuItems notify when the value is selected.
 */
val LocalDropdownMenuBoxOnItemSelectedAction = compositionLocalOf<(value: Any?) -> Unit> {
    { _ -> }
}
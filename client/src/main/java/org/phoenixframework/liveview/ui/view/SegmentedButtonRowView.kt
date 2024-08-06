package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrSpace
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.data.constants.ComposableTypes.segmentedButton
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Segmented Button Rows. It could be single or multi choice.
 * ```
 * def mount(_params, _session, socket) do
 *   {:ok, socket  |> assign(:selectedChoice, "0") |> assign(:selectedChoices, %{"0" => "false", "1" => "false", "2" => "true"})}
 * end
 *
 * def handle_event("selectChoice", choice, socket) do
 *   {:noreply, assign(socket, :selectedChoice, choice)}
 * end
 *
 * def handle_event("selectMultiChoice", change, socket) do
 *   {:noreply, assign(socket, :selectedChoices, Map.put(socket.assigns.selectedChoices, Enum.at(change, 0), Enum.at(change, 1)))}
 * end
 *
 * <SingleChoiceSegmentedButtonRow>
 *   <SegmentedButton selected={"#{@selectedChoice == "0"}"} phx-click="selectChoice" phx-value="0">
 *     <Text template="label">Option 1</Text>
 *   </SegmentedButton>
 *   <SegmentedButton selected={"#{@selectedChoice == "1"}"} phx-click="selectChoice" phx-value="1">
 *     <Text template="label">Option 2</Text>
 *   </SegmentedButton>
 *   <SegmentedButton selected={"#{@selectedChoice == "2"}"} phx-click="selectChoice" phx-value="2">
 *     <Text template="label">Option 3</Text>
 *   </SegmentedButton>
 * </SingleChoiceSegmentedButtonRow>
 *
 * <MultiChoiceSegmentedButtonRow>
 *   <SegmentedButton checked={"#{Map.get(@selectedChoices, "0")}"}
 *      phx-change="selectMultiChoice" phx-value="0">
 *     <Text template="label">Option 1</Text>
 *   </SegmentedButton>
 *   <SegmentedButton checked={"#{Map.get(@selectedChoices, "1")}"}
 *      phx-change="selectMultiChoice" phx-value="1">
 *     <Text template="label">Option 2</Text>
 *   </SegmentedButton>
 *   <SegmentedButton checked={"#{Map.get(@selectedChoices, "2")}"}
 *      phx-change="selectMultiChoice" phx-value="2">
 *     <Text template="label">Option 3</Text>
 *   </SegmentedButton>
 * </MultiChoiceSegmentedButtonRow>
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SegmentedButtonRowView private constructor(props: Properties) :
    ComposableView<SegmentedButtonRowView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val space = props.space

        when (composableNode?.node?.tag) {
            ComposableTypes.singleChoiceSegmentedButtonRow -> {
                SingleChoiceSegmentedButtonRow(
                    modifier = props.commonProps.modifier,
                    space = space ?: SegmentedButtonDefaults.BorderWidth,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }

            ComposableTypes.multiChoiceSegmentedButtonRow -> {
                MultiChoiceSegmentedButtonRow(
                    modifier = props.commonProps.modifier,
                    space = space ?: SegmentedButtonDefaults.BorderWidth,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        }
    }

    @Stable
    internal data class Properties(
        val space: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<SegmentedButtonRowView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): SegmentedButtonRowView = SegmentedButtonRowView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrSpace -> space(props, attribute.value)
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
                segmentedButton to SegmentedButtonView.Factory
            )
        }

        /**
         * the dimension of the overlap between buttons. Should be equal to the stroke width used
         * on the items.
         * @param space dimension in Dp of the overlap between buttons.
         */
        fun space(props: Properties, space: String): Properties {
            return if (space.isNotEmptyAndIsDigitsOnly()) {
                props.copy(space = space.toInt().dp)
            } else props
        }
    }
}
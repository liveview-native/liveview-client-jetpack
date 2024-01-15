package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.data.constants.Attrs.attrSpace
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
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
internal class SegmentedButtonRowDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val space = builder.space

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        when (composableNode?.node?.tag) {
            ComposableTypes.singleChoiceSegmentedButtonRow -> {
                SingleChoiceSegmentedButtonRow(
                    modifier = modifier,
                    space = space ?: SegmentedButtonDefaults.BorderWidth,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }

            ComposableTypes.multiChoiceSegmentedButtonRow -> {
                MultiChoiceSegmentedButtonRow(
                    modifier = modifier,
                    space = space ?: SegmentedButtonDefaults.BorderWidth,
                ) {
                    composableNode.children.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        }
    }

    internal class Builder : ComposableBuilder() {
        var space: Dp? = null
            private set

        /**
         * the dimension of the overlap between buttons. Should be equal to the stroke width used
         * on the items.
         * @param space dimension in Dp of the overlap between buttons.
         */
        fun space(space: String) = apply {
            if (space.isNotEmptyAndIsDigitsOnly()) {
                this.space = space.toInt().dp
            }
        }

        fun build() = SegmentedButtonRowDTO(this)
    }
}

internal object SegmentedButtonRowDtoFactory :
    ComposableViewFactory<SegmentedButtonRowDTO, SegmentedButtonRowDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SegmentedButtonRowDTO =
        attributes.fold(SegmentedButtonRowDTO.Builder()) { builder, attribute ->
            when (attribute.name) {
                attrSpace -> builder.space(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as SegmentedButtonRowDTO.Builder
        }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*, *>> {
        return mapOf(
            "SegmentedButton" to SegmentedButtonDtoFactory
        )
    }
}
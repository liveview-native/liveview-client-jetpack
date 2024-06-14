package org.phoenixframework.liveview.ui.registry

import kotlinx.collections.immutable.persistentListOf
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.ui.view.AlertDialogViewFactory
import org.phoenixframework.liveview.ui.view.AnimatedVisibilityViewFactory
import org.phoenixframework.liveview.ui.view.AsyncImageViewFactory
import org.phoenixframework.liveview.ui.view.BackHandlerViewFactory
import org.phoenixframework.liveview.ui.view.BadgedBoxViewFactory
import org.phoenixframework.liveview.ui.view.BottomAppBarViewFactory
import org.phoenixframework.liveview.ui.view.BottomSheetScaffoldViewFactory
import org.phoenixframework.liveview.ui.view.BoxViewFactory
import org.phoenixframework.liveview.ui.view.ButtonViewFactory
import org.phoenixframework.liveview.ui.view.CardViewFactory
import org.phoenixframework.liveview.ui.view.CheckBoxViewFactory
import org.phoenixframework.liveview.ui.view.ChipViewFactory
import org.phoenixframework.liveview.ui.view.ColumnViewFactory
import org.phoenixframework.liveview.ui.view.DatePickerDialogViewFactory
import org.phoenixframework.liveview.ui.view.DatePickerViewFactory
import org.phoenixframework.liveview.ui.view.DividerViewFactory
import org.phoenixframework.liveview.ui.view.DrawerSheetViewFactory
import org.phoenixframework.liveview.ui.view.DropdownMenuViewFactory
import org.phoenixframework.liveview.ui.view.DropdownMenuItemViewFactory
import org.phoenixframework.liveview.ui.view.ExposedDropdownMenuBoxViewFactory
import org.phoenixframework.liveview.ui.view.FloatingActionButtonViewFactory
import org.phoenixframework.liveview.ui.view.FlowLayoutViewFactory
import org.phoenixframework.liveview.ui.view.IconButtonViewFactory
import org.phoenixframework.liveview.ui.view.IconViewFactory
import org.phoenixframework.liveview.ui.view.IconToggleButtonViewFactory
import org.phoenixframework.liveview.ui.view.ImageViewFactory
import org.phoenixframework.liveview.ui.view.LazyColumnViewFactory
import org.phoenixframework.liveview.ui.view.LazyGridViewFactory
import org.phoenixframework.liveview.ui.view.LazyRowViewFactory
import org.phoenixframework.liveview.ui.view.LinkViewFactory
import org.phoenixframework.liveview.ui.view.ListItemViewFactory
import org.phoenixframework.liveview.ui.view.ModalBottomSheetViewFactory
import org.phoenixframework.liveview.ui.view.NavigationBarViewFactory
import org.phoenixframework.liveview.ui.view.NavigationDrawerViewFactory
import org.phoenixframework.liveview.ui.view.NavigationDrawerItemViewFactory
import org.phoenixframework.liveview.ui.view.NavigationRailViewFactory
import org.phoenixframework.liveview.ui.view.NavigationRailItemViewFactory
import org.phoenixframework.liveview.ui.view.PagerViewFactory
import org.phoenixframework.liveview.ui.view.ProgressIndicatorViewFactory
import org.phoenixframework.liveview.ui.view.RadioButtonViewFactory
import org.phoenixframework.liveview.ui.view.RowViewFactory
import org.phoenixframework.liveview.ui.view.ScaffoldViewFactory
import org.phoenixframework.liveview.ui.view.SearchBarViewFactory
import org.phoenixframework.liveview.ui.view.SegmentedButtonRowViewFactory
import org.phoenixframework.liveview.ui.view.SliderViewFactory
import org.phoenixframework.liveview.ui.view.SpacerViewFactory
import org.phoenixframework.liveview.ui.view.SurfaceViewFactory
import org.phoenixframework.liveview.ui.view.SwipeToDismissBoxViewFactory
import org.phoenixframework.liveview.ui.view.SwitchViewFactory
import org.phoenixframework.liveview.ui.view.TabViewFactory
import org.phoenixframework.liveview.ui.view.TabRowViewFactory
import org.phoenixframework.liveview.ui.view.TextViewFactory
import org.phoenixframework.liveview.ui.view.TextFieldViewFactory
import org.phoenixframework.liveview.ui.view.TimePickerViewFactory
import org.phoenixframework.liveview.ui.view.TooltipBoxViewFactory
import org.phoenixframework.liveview.ui.view.TooltipViewFactory
import org.phoenixframework.liveview.ui.view.TopAppBarViewFactory
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.data.constants.ComposableTypes
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.lib.NodeRef

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
object ComposableNodeFactory {

    init {
        ComposableRegistry.run {
            registerComponent(ComposableTypes.alertDialog, AlertDialogViewFactory)
            registerComponent(ComposableTypes.animatedVisibility, AnimatedVisibilityViewFactory)
            registerComponent(ComposableTypes.assistChip, ChipViewFactory)
            registerComponent(ComposableTypes.asyncImage, AsyncImageViewFactory)
            registerComponent(ComposableTypes.backHandler, BackHandlerViewFactory)
            registerComponent(ComposableTypes.badgedBox, BadgedBoxViewFactory)
            registerComponent(ComposableTypes.basicAlertDialog, AlertDialogViewFactory)
            registerComponent(ComposableTypes.box, BoxViewFactory)
            registerComponent(ComposableTypes.bottomAppBar, BottomAppBarViewFactory)
            registerComponent(ComposableTypes.bottomSheetScaffold, BottomSheetScaffoldViewFactory)
            registerComponent(ComposableTypes.button, ButtonViewFactory)
            registerComponent(ComposableTypes.card, CardViewFactory)
            registerComponent(ComposableTypes.centerAlignedTopAppBar, TopAppBarViewFactory)
            registerComponent(ComposableTypes.checkbox, CheckBoxViewFactory)
            registerComponent(
                ComposableTypes.circularProgressIndicator,
                ProgressIndicatorViewFactory
            )
            registerComponent(ComposableTypes.column, ColumnViewFactory)
            registerComponent(ComposableTypes.datePicker, DatePickerViewFactory)
            registerComponent(ComposableTypes.datePickerDialog, DatePickerDialogViewFactory)
            registerComponent(ComposableTypes.dateRangePicker, DatePickerViewFactory)
            registerComponent(
                ComposableTypes.dismissibleNavigationDrawer,
                NavigationDrawerViewFactory
            )
            registerComponent(ComposableTypes.dismissibleDrawerSheet, DrawerSheetViewFactory)
            registerComponent(ComposableTypes.dockedSearchBar, SearchBarViewFactory)
            registerComponent(ComposableTypes.dropdownMenu, DropdownMenuViewFactory)
            registerComponent(ComposableTypes.dropdownMenuItem, DropdownMenuItemViewFactory)
            registerComponent(ComposableTypes.elevatedAssistChip, ChipViewFactory)
            registerComponent(ComposableTypes.elevatedButton, ButtonViewFactory)
            registerComponent(ComposableTypes.elevatedCard, CardViewFactory)
            registerComponent(ComposableTypes.elevatedFilterChip, ChipViewFactory)
            registerComponent(ComposableTypes.elevatedSuggestionChip, ChipViewFactory)
            registerComponent(
                ComposableTypes.extendedFloatingActionButton,
                FloatingActionButtonViewFactory
            )
            registerComponent(
                ComposableTypes.exposedDropdownMenuBox,
                ExposedDropdownMenuBoxViewFactory
            )
            registerComponent(ComposableTypes.filledIconButton, IconButtonViewFactory)
            registerComponent(ComposableTypes.filledIconToggleButton, IconToggleButtonViewFactory)
            registerComponent(ComposableTypes.filledTonalButton, ButtonViewFactory)
            registerComponent(ComposableTypes.filledTonalIconButton, IconButtonViewFactory)
            registerComponent(
                ComposableTypes.filledTonalIconToggleButton,
                IconToggleButtonViewFactory
            )
            registerComponent(ComposableTypes.filterChip, ChipViewFactory)
            registerComponent(ComposableTypes.floatingActionButton, FloatingActionButtonViewFactory)
            registerComponent(ComposableTypes.flowColumn, FlowLayoutViewFactory)
            registerComponent(ComposableTypes.flowRow, FlowLayoutViewFactory)
            registerComponent(ComposableTypes.horizontalDivider, DividerViewFactory)
            registerComponent(ComposableTypes.horizontalPager, PagerViewFactory)
            registerComponent(ComposableTypes.icon, IconViewFactory)
            registerComponent(ComposableTypes.iconButton, IconButtonViewFactory)
            registerComponent(ComposableTypes.iconToggleButton, IconToggleButtonViewFactory)
            registerComponent(ComposableTypes.image, ImageViewFactory)
            registerComponent(ComposableTypes.inputChip, ChipViewFactory)
            registerComponent(
                ComposableTypes.largeFloatingActionButton,
                FloatingActionButtonViewFactory
            )
            registerComponent(ComposableTypes.largeTopAppBar, TopAppBarViewFactory)
            registerComponent(ComposableTypes.lazyColumn, LazyColumnViewFactory)
            registerComponent(ComposableTypes.lazyHorizontalGrid, LazyGridViewFactory)
            registerComponent(ComposableTypes.lazyRow, LazyRowViewFactory)
            registerComponent(ComposableTypes.lazyVerticalGrid, LazyGridViewFactory)
            registerComponent(ComposableTypes.leadingIconTab, TabViewFactory)
            registerComponent(
                ComposableTypes.linearProgressIndicator,
                ProgressIndicatorViewFactory
            )
            registerComponent(ComposableTypes.link, LinkViewFactory)
            registerComponent(ComposableTypes.listItem, ListItemViewFactory)
            registerComponent(ComposableTypes.mediumTopAppBar, TopAppBarViewFactory)
            registerComponent(ComposableTypes.modalBottomSheet, ModalBottomSheetViewFactory)
            registerComponent(ComposableTypes.modalDrawerSheet, DrawerSheetViewFactory)
            registerComponent(
                ComposableTypes.modalNavigationDrawer,
                NavigationDrawerViewFactory
            )
            registerComponent(
                ComposableTypes.multiChoiceSegmentedButtonRow,
                SegmentedButtonRowViewFactory
            )
            registerComponent(ComposableTypes.navigationBar, NavigationBarViewFactory)
            registerComponent(ComposableTypes.navigationDrawerItem, NavigationDrawerItemViewFactory)
            registerComponent(ComposableTypes.navigationRail, NavigationRailViewFactory)
            registerComponent(ComposableTypes.navigationRailItem, NavigationRailItemViewFactory)
            registerComponent(ComposableTypes.outlinedButton, ButtonViewFactory)
            registerComponent(ComposableTypes.outlinedCard, CardViewFactory)
            registerComponent(ComposableTypes.outlinedIconButton, IconButtonViewFactory)
            registerComponent(ComposableTypes.outlinedIconToggleButton, IconToggleButtonViewFactory)
            registerComponent(ComposableTypes.outlinedTextField, TextFieldViewFactory)
            registerComponent(ComposableTypes.permanentDrawerSheet, DrawerSheetViewFactory)
            registerComponent(ComposableTypes.permanentNavigationDrawer, NavigationDrawerViewFactory)
            registerComponent(ComposableTypes.radioButton, RadioButtonViewFactory)
            registerComponent(ComposableTypes.rangeSlider, SliderViewFactory)
            registerComponent(ComposableTypes.richTooltip, TooltipViewFactory)
            registerComponent(ComposableTypes.row, RowViewFactory)
            registerComponent(ComposableTypes.scaffold, ScaffoldViewFactory)
            registerComponent(ComposableTypes.scrollableTabRow, TabRowViewFactory)
            registerComponent(ComposableTypes.searchBar, SearchBarViewFactory)
            registerComponent(
                ComposableTypes.singleChoiceSegmentedButtonRow,
                SegmentedButtonRowViewFactory
            )
            registerComponent(ComposableTypes.slider, SliderViewFactory)
            registerComponent(
                ComposableTypes.smallFloatingActionButton,
                FloatingActionButtonViewFactory
            )
            registerComponent(ComposableTypes.spacer, SpacerViewFactory)
            registerComponent(ComposableTypes.suggestionChip, ChipViewFactory)
            registerComponent(ComposableTypes.surface, SurfaceViewFactory)
            registerComponent(ComposableTypes.swipeToDismissBox, SwipeToDismissBoxViewFactory)
            registerComponent(ComposableTypes.switch, SwitchViewFactory)
            registerComponent(ComposableTypes.tab, TabViewFactory)
            registerComponent(ComposableTypes.tabRow, TabRowViewFactory)
            registerComponent(ComposableTypes.text, TextViewFactory)
            registerComponent(ComposableTypes.textButton, ButtonViewFactory)
            registerComponent(ComposableTypes.textField, TextFieldViewFactory)
            registerComponent(ComposableTypes.timeInput, TimePickerViewFactory)
            registerComponent(ComposableTypes.timePicker, TimePickerViewFactory)
            registerComponent(ComposableTypes.tooltipBox, TooltipBoxViewFactory)
            registerComponent(ComposableTypes.topAppBar, TopAppBarViewFactory)
            registerComponent(ComposableTypes.verticalDivider, DividerViewFactory)
            registerComponent(ComposableTypes.verticalPager, PagerViewFactory)
        }
    }

    /**
     * Creates a `ComposableTreeNode` object based on the input `Element` object.
     *
     * @param element the `Element` object to create the `ComposableTreeNode` object from
     * @return a `ComposableTreeNode` object based on the input `Element` object
     */
    fun buildComposableTreeNode(
        screenId: String,
        nodeRef: NodeRef,
        element: CoreNodeElement,
    ): ComposableTreeNode {
        return ComposableTreeNode(
            screenId = screenId,
            refId = nodeRef.ref,
            node = element,
            id = "${screenId}_${nodeRef.ref}",
        )
    }

    fun buildComposableView(
        element: CoreNodeElement?,
        parentTag: String?,
        pushEvent: PushEvent,
        scope: Any?
    ): ComposableView<*> {
        return if (element != null) {
            val tag = element.tag
            val attrs = element.attributes
            ComposableRegistry.getComponentFactory(tag, parentTag)?.buildComposableView(
                attrs, pushEvent, scope
            ) ?: run {
                TextViewFactory.buildComposableView(
                    "$tag not supported yet",
                    attrs,
                    scope,
                    pushEvent,
                )
            }
        } else {
            TextViewFactory.buildComposableView(
                "Invalid element",
                persistentListOf(),
                scope,
                pushEvent
            )
        }
    }
}
package org.phoenixframework.liveview.domain.factory

import kotlinx.collections.immutable.persistentListOf
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.dto.AlertDialogDtoFactory
import org.phoenixframework.liveview.data.dto.AnimatedVisibilityDtoFactory
import org.phoenixframework.liveview.data.dto.AsyncImageDtoFactory
import org.phoenixframework.liveview.data.dto.BackHandlerDtoFactory
import org.phoenixframework.liveview.data.dto.BadgedBoxDtoFactory
import org.phoenixframework.liveview.data.dto.BottomAppBarDtoFactory
import org.phoenixframework.liveview.data.dto.BottomSheetScaffoldDtoFactory
import org.phoenixframework.liveview.data.dto.BoxDtoFactory
import org.phoenixframework.liveview.data.dto.ButtonDtoFactory
import org.phoenixframework.liveview.data.dto.CardDtoFactory
import org.phoenixframework.liveview.data.dto.CheckBoxDtoFactory
import org.phoenixframework.liveview.data.dto.ChipDtoFactory
import org.phoenixframework.liveview.data.dto.ColumnDtoFactory
import org.phoenixframework.liveview.data.dto.DatePickerDialogDtoFactory
import org.phoenixframework.liveview.data.dto.DatePickerDtoFactory
import org.phoenixframework.liveview.data.dto.DividerDtoFactory
import org.phoenixframework.liveview.data.dto.DrawerSheetDtoFactory
import org.phoenixframework.liveview.data.dto.DropdownMenuDtoFactory
import org.phoenixframework.liveview.data.dto.DropdownMenuItemDtoFactory
import org.phoenixframework.liveview.data.dto.ExposedDropdownMenuBoxDtoFactory
import org.phoenixframework.liveview.data.dto.FloatingActionButtonDtoFactory
import org.phoenixframework.liveview.data.dto.FlowLayoutDtoFactory
import org.phoenixframework.liveview.data.dto.IconButtonDtoFactory
import org.phoenixframework.liveview.data.dto.IconDtoFactory
import org.phoenixframework.liveview.data.dto.IconToggleButtonDtoFactory
import org.phoenixframework.liveview.data.dto.ImageDtoFactory
import org.phoenixframework.liveview.data.dto.LazyColumnDtoFactory
import org.phoenixframework.liveview.data.dto.LazyGridDtoFactory
import org.phoenixframework.liveview.data.dto.LazyRowDtoFactory
import org.phoenixframework.liveview.data.dto.LinkDtoFactory
import org.phoenixframework.liveview.data.dto.ListItemDtoFactory
import org.phoenixframework.liveview.data.dto.ModalBottomSheetDtoFactory
import org.phoenixframework.liveview.data.dto.NavigationBarDtoFactory
import org.phoenixframework.liveview.data.dto.NavigationDrawerDtoFactory
import org.phoenixframework.liveview.data.dto.NavigationDrawerItemDtoFactory
import org.phoenixframework.liveview.data.dto.NavigationRailDtoFactory
import org.phoenixframework.liveview.data.dto.NavigationRailItemDtoFactory
import org.phoenixframework.liveview.data.dto.PagerDtoFactory
import org.phoenixframework.liveview.data.dto.ProgressIndicatorDtoFactory
import org.phoenixframework.liveview.data.dto.RadioButtonDtoFactory
import org.phoenixframework.liveview.data.dto.RowDtoFactory
import org.phoenixframework.liveview.data.dto.ScaffoldDtoFactory
import org.phoenixframework.liveview.data.dto.SearchBarDtoFactory
import org.phoenixframework.liveview.data.dto.SegmentedButtonRowDtoFactory
import org.phoenixframework.liveview.data.dto.SliderDtoFactory
import org.phoenixframework.liveview.data.dto.SpacerDtoFactory
import org.phoenixframework.liveview.data.dto.SurfaceDtoFactory
import org.phoenixframework.liveview.data.dto.SwipeToDismissBoxDtoFactory
import org.phoenixframework.liveview.data.dto.SwitchDtoFactory
import org.phoenixframework.liveview.data.dto.TabDtoFactory
import org.phoenixframework.liveview.data.dto.TabRowDtoFactory
import org.phoenixframework.liveview.data.dto.TextDtoFactory
import org.phoenixframework.liveview.data.dto.TextFieldDtoFactory
import org.phoenixframework.liveview.data.dto.TimePickerDtoFactory
import org.phoenixframework.liveview.data.dto.TooltipBoxDtoFactory
import org.phoenixframework.liveview.data.dto.TooltipDtoFactory
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.lib.NodeRef

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
object ComposableNodeFactory {

    init {
        ComposableRegistry.run {
            registerComponent(ComposableTypes.alertDialog, AlertDialogDtoFactory)
            registerComponent(ComposableTypes.animatedVisibility, AnimatedVisibilityDtoFactory)
            registerComponent(ComposableTypes.assistChip, ChipDtoFactory)
            registerComponent(ComposableTypes.asyncImage, AsyncImageDtoFactory)
            registerComponent(ComposableTypes.backHandler, BackHandlerDtoFactory)
            registerComponent(ComposableTypes.badgedBox, BadgedBoxDtoFactory)
            registerComponent(ComposableTypes.basicAlertDialog, AlertDialogDtoFactory)
            registerComponent(ComposableTypes.box, BoxDtoFactory)
            registerComponent(ComposableTypes.bottomAppBar, BottomAppBarDtoFactory)
            registerComponent(ComposableTypes.bottomSheetScaffold, BottomSheetScaffoldDtoFactory)
            registerComponent(ComposableTypes.button, ButtonDtoFactory)
            registerComponent(ComposableTypes.card, CardDtoFactory)
            registerComponent(ComposableTypes.centerAlignedTopAppBar, TopAppBarDtoFactory)
            registerComponent(ComposableTypes.checkbox, CheckBoxDtoFactory)
            registerComponent(
                ComposableTypes.circularProgressIndicator,
                ProgressIndicatorDtoFactory
            )
            registerComponent(ComposableTypes.column, ColumnDtoFactory)
            registerComponent(ComposableTypes.datePicker, DatePickerDtoFactory)
            registerComponent(ComposableTypes.datePickerDialog, DatePickerDialogDtoFactory)
            registerComponent(ComposableTypes.dateRangePicker, DatePickerDtoFactory)
            registerComponent(
                ComposableTypes.dismissibleNavigationDrawer,
                NavigationDrawerDtoFactory
            )
            registerComponent(ComposableTypes.dismissibleDrawerSheet, DrawerSheetDtoFactory)
            registerComponent(ComposableTypes.dockedSearchBar, SearchBarDtoFactory)
            registerComponent(ComposableTypes.dropdownMenu, DropdownMenuDtoFactory)
            registerComponent(ComposableTypes.dropdownMenuItem, DropdownMenuItemDtoFactory)
            registerComponent(ComposableTypes.elevatedAssistChip, ChipDtoFactory)
            registerComponent(ComposableTypes.elevatedButton, ButtonDtoFactory)
            registerComponent(ComposableTypes.elevatedCard, CardDtoFactory)
            registerComponent(ComposableTypes.elevatedFilterChip, ChipDtoFactory)
            registerComponent(ComposableTypes.elevatedSuggestionChip, ChipDtoFactory)
            registerComponent(
                ComposableTypes.extendedFloatingActionButton,
                FloatingActionButtonDtoFactory
            )
            registerComponent(
                ComposableTypes.exposedDropdownMenuBox,
                ExposedDropdownMenuBoxDtoFactory
            )
            registerComponent(ComposableTypes.filledIconButton, IconButtonDtoFactory)
            registerComponent(ComposableTypes.filledIconToggleButton, IconToggleButtonDtoFactory)
            registerComponent(ComposableTypes.filledTonalButton, ButtonDtoFactory)
            registerComponent(ComposableTypes.filledTonalIconButton, IconButtonDtoFactory)
            registerComponent(
                ComposableTypes.filledTonalIconToggleButton,
                IconToggleButtonDtoFactory
            )
            registerComponent(ComposableTypes.filterChip, ChipDtoFactory)
            registerComponent(ComposableTypes.floatingActionButton, FloatingActionButtonDtoFactory)
            registerComponent(ComposableTypes.flowColumn, FlowLayoutDtoFactory)
            registerComponent(ComposableTypes.flowRow, FlowLayoutDtoFactory)
            registerComponent(ComposableTypes.horizontalDivider, DividerDtoFactory)
            registerComponent(ComposableTypes.horizontalPager, PagerDtoFactory)
            registerComponent(ComposableTypes.icon, IconDtoFactory)
            registerComponent(ComposableTypes.iconButton, IconButtonDtoFactory)
            registerComponent(ComposableTypes.iconToggleButton, IconToggleButtonDtoFactory)
            registerComponent(ComposableTypes.image, ImageDtoFactory)
            registerComponent(ComposableTypes.inputChip, ChipDtoFactory)
            registerComponent(
                ComposableTypes.largeFloatingActionButton,
                FloatingActionButtonDtoFactory
            )
            registerComponent(ComposableTypes.largeTopAppBar, TopAppBarDtoFactory)
            registerComponent(ComposableTypes.lazyColumn, LazyColumnDtoFactory)
            registerComponent(ComposableTypes.lazyHorizontalGrid, LazyGridDtoFactory)
            registerComponent(ComposableTypes.lazyRow, LazyRowDtoFactory)
            registerComponent(ComposableTypes.lazyVerticalGrid, LazyGridDtoFactory)
            registerComponent(ComposableTypes.leadingIconTab, TabDtoFactory)
            registerComponent(
                ComposableTypes.linearProgressIndicator,
                ProgressIndicatorDtoFactory
            )
            registerComponent(ComposableTypes.link, LinkDtoFactory)
            registerComponent(ComposableTypes.listItem, ListItemDtoFactory)
            registerComponent(ComposableTypes.mediumTopAppBar, TopAppBarDtoFactory)
            registerComponent(ComposableTypes.modalBottomSheet, ModalBottomSheetDtoFactory)
            registerComponent(ComposableTypes.modalDrawerSheet, DrawerSheetDtoFactory)
            registerComponent(
                ComposableTypes.modalNavigationDrawer,
                NavigationDrawerDtoFactory
            )
            registerComponent(
                ComposableTypes.multiChoiceSegmentedButtonRow,
                SegmentedButtonRowDtoFactory
            )
            registerComponent(ComposableTypes.navigationBar, NavigationBarDtoFactory)
            registerComponent(ComposableTypes.navigationDrawerItem, NavigationDrawerItemDtoFactory)
            registerComponent(ComposableTypes.navigationRail, NavigationRailDtoFactory)
            registerComponent(ComposableTypes.navigationRailItem, NavigationRailItemDtoFactory)
            registerComponent(ComposableTypes.outlinedButton, ButtonDtoFactory)
            registerComponent(ComposableTypes.outlinedCard, CardDtoFactory)
            registerComponent(ComposableTypes.outlinedIconButton, IconButtonDtoFactory)
            registerComponent(ComposableTypes.outlinedIconToggleButton, IconToggleButtonDtoFactory)
            registerComponent(ComposableTypes.outlinedTextField, TextFieldDtoFactory)
            registerComponent(ComposableTypes.permanentDrawerSheet, DrawerSheetDtoFactory)
            registerComponent(ComposableTypes.permanentNavigationDrawer, NavigationDrawerDtoFactory)
            registerComponent(ComposableTypes.radioButton, RadioButtonDtoFactory)
            registerComponent(ComposableTypes.rangeSlider, SliderDtoFactory)
            registerComponent(ComposableTypes.richTooltip, TooltipDtoFactory)
            registerComponent(ComposableTypes.row, RowDtoFactory)
            registerComponent(ComposableTypes.scaffold, ScaffoldDtoFactory)
            registerComponent(ComposableTypes.scrollableTabRow, TabRowDtoFactory)
            registerComponent(ComposableTypes.searchBar, SearchBarDtoFactory)
            registerComponent(
                ComposableTypes.singleChoiceSegmentedButtonRow,
                SegmentedButtonRowDtoFactory
            )
            registerComponent(ComposableTypes.slider, SliderDtoFactory)
            registerComponent(
                ComposableTypes.smallFloatingActionButton,
                FloatingActionButtonDtoFactory
            )
            registerComponent(ComposableTypes.spacer, SpacerDtoFactory)
            registerComponent(ComposableTypes.suggestionChip, ChipDtoFactory)
            registerComponent(ComposableTypes.surface, SurfaceDtoFactory)
            registerComponent(ComposableTypes.swipeToDismissBox, SwipeToDismissBoxDtoFactory)
            registerComponent(ComposableTypes.switch, SwitchDtoFactory)
            registerComponent(ComposableTypes.tab, TabDtoFactory)
            registerComponent(ComposableTypes.tabRow, TabRowDtoFactory)
            registerComponent(ComposableTypes.text, TextDtoFactory)
            registerComponent(ComposableTypes.textButton, ButtonDtoFactory)
            registerComponent(ComposableTypes.textField, TextFieldDtoFactory)
            registerComponent(ComposableTypes.timeInput, TimePickerDtoFactory)
            registerComponent(ComposableTypes.timePicker, TimePickerDtoFactory)
            registerComponent(ComposableTypes.tooltipBox, TooltipBoxDtoFactory)
            registerComponent(ComposableTypes.topAppBar, TopAppBarDtoFactory)
            registerComponent(ComposableTypes.verticalDivider, DividerDtoFactory)
            registerComponent(ComposableTypes.verticalPager, PagerDtoFactory)
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
                TextDtoFactory.buildComposableView(
                    "$tag not supported yet",
                    attrs,
                    scope,
                    pushEvent,
                )
            }
        } else {
            TextDtoFactory.buildComposableView(
                "Invalid element",
                persistentListOf(),
                scope,
                pushEvent
            )
        }
    }
}
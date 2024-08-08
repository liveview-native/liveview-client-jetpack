package org.phoenixframework.liveview.ui.registry

import kotlinx.collections.immutable.persistentListOf
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.foundation.data.core.CoreNodeElement
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveview.foundation.ui.registry.ComposableRegistry
import org.phoenixframework.liveview.ui.view.AlertDialogView
import org.phoenixframework.liveview.ui.view.AnimatedVisibilityView
import org.phoenixframework.liveview.ui.view.BackHandlerView
import org.phoenixframework.liveview.ui.view.BadgedBoxView
import org.phoenixframework.liveview.ui.view.BottomAppBarView
import org.phoenixframework.liveview.ui.view.BottomSheetScaffoldView
import org.phoenixframework.liveview.ui.view.BoxView
import org.phoenixframework.liveview.ui.view.BoxWithConstraintsView
import org.phoenixframework.liveview.ui.view.ButtonView
import org.phoenixframework.liveview.ui.view.CardView
import org.phoenixframework.liveview.ui.view.CheckBoxView
import org.phoenixframework.liveview.ui.view.ChipView
import org.phoenixframework.liveview.ui.view.ColumnView
import org.phoenixframework.liveview.ui.view.CrossfadeView
import org.phoenixframework.liveview.ui.view.DatePickerDialogView
import org.phoenixframework.liveview.ui.view.DatePickerView
import org.phoenixframework.liveview.ui.view.DividerView
import org.phoenixframework.liveview.ui.view.DrawerSheetView
import org.phoenixframework.liveview.ui.view.DropdownMenuItemView
import org.phoenixframework.liveview.ui.view.DropdownMenuView
import org.phoenixframework.liveview.ui.view.ExposedDropdownMenuBoxView
import org.phoenixframework.liveview.ui.view.FloatingActionButtonView
import org.phoenixframework.liveview.ui.view.FlowLayoutView
import org.phoenixframework.liveview.ui.view.IconButtonView
import org.phoenixframework.liveview.ui.view.IconToggleButtonView
import org.phoenixframework.liveview.ui.view.IconView
import org.phoenixframework.liveview.ui.view.ImageView
import org.phoenixframework.liveview.ui.view.LazyColumnView
import org.phoenixframework.liveview.ui.view.LazyGridView
import org.phoenixframework.liveview.ui.view.LazyRowView
import org.phoenixframework.liveview.ui.view.LinkView
import org.phoenixframework.liveview.ui.view.ListItemView
import org.phoenixframework.liveview.ui.view.ModalBottomSheetView
import org.phoenixframework.liveview.ui.view.NavigationBarView
import org.phoenixframework.liveview.ui.view.NavigationDrawerItemView
import org.phoenixframework.liveview.ui.view.NavigationDrawerView
import org.phoenixframework.liveview.ui.view.NavigationRailItemView
import org.phoenixframework.liveview.ui.view.NavigationRailView
import org.phoenixframework.liveview.ui.view.PagerView
import org.phoenixframework.liveview.ui.view.ProgressIndicatorView
import org.phoenixframework.liveview.ui.view.RadioButtonView
import org.phoenixframework.liveview.ui.view.RowView
import org.phoenixframework.liveview.ui.view.ScaffoldView
import org.phoenixframework.liveview.ui.view.SearchBarView
import org.phoenixframework.liveview.ui.view.SegmentedButtonRowView
import org.phoenixframework.liveview.ui.view.SliderView
import org.phoenixframework.liveview.ui.view.SpacerView
import org.phoenixframework.liveview.ui.view.SurfaceView
import org.phoenixframework.liveview.ui.view.SwipeToDismissBoxView
import org.phoenixframework.liveview.ui.view.SwitchView
import org.phoenixframework.liveview.ui.view.TabRowView
import org.phoenixframework.liveview.ui.view.TabView
import org.phoenixframework.liveview.ui.view.TextFieldView
import org.phoenixframework.liveview.ui.view.TextView
import org.phoenixframework.liveview.ui.view.TimePickerView
import org.phoenixframework.liveview.ui.view.TooltipBoxView
import org.phoenixframework.liveview.ui.view.TooltipView
import org.phoenixframework.liveview.ui.view.TopAppBarView

/**
 * A factory class that is used to create `ComposableTreeNode` objects that co-relate to composable
 * views based on the type of an input `Element` object. The `ComposableTreeNode` objects are
 * created by calling different functions depending on the tag name of the input `Element` object.
 */
class ComposableNodeFactory(
    composableRegistry: ComposableRegistry
) : BaseComposableNodeFactory(composableRegistry) {

    init {
        registerComponent(ComposableTypes.alertDialog, AlertDialogView.Factory)
        registerComponent(ComposableTypes.animatedVisibility, AnimatedVisibilityView.Factory)
        registerComponent(ComposableTypes.assistChip, ChipView.Factory)
        registerComponent(ComposableTypes.backHandler, BackHandlerView.Factory)
        registerComponent(ComposableTypes.badgedBox, BadgedBoxView.Factory)
        registerComponent(ComposableTypes.basicAlertDialog, AlertDialogView.Factory)
        registerComponent(ComposableTypes.box, BoxView.Factory)
        registerComponent(ComposableTypes.boxWithConstraints, BoxWithConstraintsView.Factory)
        registerComponent(ComposableTypes.bottomAppBar, BottomAppBarView.Factory)
        registerComponent(ComposableTypes.bottomSheetScaffold, BottomSheetScaffoldView.Factory)
        registerComponent(ComposableTypes.button, ButtonView.Factory)
        registerComponent(ComposableTypes.card, CardView.Factory)
        registerComponent(ComposableTypes.centerAlignedTopAppBar, TopAppBarView.Factory)
        registerComponent(ComposableTypes.checkbox, CheckBoxView.Factory)
        registerComponent(
            ComposableTypes.circularProgressIndicator,
            ProgressIndicatorView.Factory
        )
        registerComponent(ComposableTypes.column, ColumnView.Factory)
        registerComponent(ComposableTypes.crossfade, CrossfadeView.Factory)
        registerComponent(ComposableTypes.datePicker, DatePickerView.Factory)
        registerComponent(ComposableTypes.datePickerDialog, DatePickerDialogView.Factory)
        registerComponent(ComposableTypes.dateRangePicker, DatePickerView.Factory)
        registerComponent(
            ComposableTypes.dismissibleNavigationDrawer,
            NavigationDrawerView.Factory
        )
        registerComponent(ComposableTypes.dismissibleDrawerSheet, DrawerSheetView.Factory)
        registerComponent(ComposableTypes.dockedSearchBar, SearchBarView.Factory)
        registerComponent(ComposableTypes.dropdownMenu, DropdownMenuView.Factory)
        registerComponent(ComposableTypes.dropdownMenuItem, DropdownMenuItemView.Factory)
        registerComponent(ComposableTypes.elevatedAssistChip, ChipView.Factory)
        registerComponent(ComposableTypes.elevatedButton, ButtonView.Factory)
        registerComponent(ComposableTypes.elevatedCard, CardView.Factory)
        registerComponent(ComposableTypes.elevatedFilterChip, ChipView.Factory)
        registerComponent(ComposableTypes.elevatedSuggestionChip, ChipView.Factory)
        registerComponent(
            ComposableTypes.extendedFloatingActionButton,
            FloatingActionButtonView.Factory
        )
        registerComponent(
            ComposableTypes.exposedDropdownMenuBox,
            ExposedDropdownMenuBoxView.Factory
        )
        registerComponent(ComposableTypes.filledIconButton, IconButtonView.Factory)
        registerComponent(ComposableTypes.filledIconToggleButton, IconToggleButtonView.Factory)
        registerComponent(ComposableTypes.filledTonalButton, ButtonView.Factory)
        registerComponent(ComposableTypes.filledTonalIconButton, IconButtonView.Factory)
        registerComponent(
            ComposableTypes.filledTonalIconToggleButton,
            IconToggleButtonView.Factory
        )
        registerComponent(ComposableTypes.filterChip, ChipView.Factory)
        registerComponent(
            ComposableTypes.floatingActionButton,
            FloatingActionButtonView.Factory
        )
        registerComponent(ComposableTypes.flowColumn, FlowLayoutView.Factory)
        registerComponent(ComposableTypes.flowRow, FlowLayoutView.Factory)
        registerComponent(ComposableTypes.horizontalDivider, DividerView.Factory)
        registerComponent(ComposableTypes.horizontalPager, PagerView.Factory)
        registerComponent(ComposableTypes.icon, IconView.Factory)
        registerComponent(ComposableTypes.iconButton, IconButtonView.Factory)
        registerComponent(ComposableTypes.iconToggleButton, IconToggleButtonView.Factory)
        registerComponent(ComposableTypes.image, ImageView.Factory)
        registerComponent(ComposableTypes.inputChip, ChipView.Factory)
        registerComponent(
            ComposableTypes.largeFloatingActionButton,
            FloatingActionButtonView.Factory
        )
        registerComponent(ComposableTypes.largeTopAppBar, TopAppBarView.Factory)
        registerComponent(ComposableTypes.lazyColumn, LazyColumnView.Factory)
        registerComponent(ComposableTypes.lazyHorizontalGrid, LazyGridView.Factory)
        registerComponent(ComposableTypes.lazyRow, LazyRowView.Factory)
        registerComponent(ComposableTypes.lazyVerticalGrid, LazyGridView.Factory)
        registerComponent(ComposableTypes.leadingIconTab, TabView.Factory)
        registerComponent(
            ComposableTypes.linearProgressIndicator,
            ProgressIndicatorView.Factory
        )
        registerComponent(ComposableTypes.link, LinkView.Factory)
        registerComponent(ComposableTypes.listItem, ListItemView.Factory)
        registerComponent(ComposableTypes.mediumTopAppBar, TopAppBarView.Factory)
        registerComponent(ComposableTypes.modalBottomSheet, ModalBottomSheetView.Factory)
        registerComponent(ComposableTypes.modalDrawerSheet, DrawerSheetView.Factory)
        registerComponent(
            ComposableTypes.modalNavigationDrawer,
            NavigationDrawerView.Factory
        )
        registerComponent(
            ComposableTypes.multiChoiceSegmentedButtonRow,
            SegmentedButtonRowView.Factory
        )
        registerComponent(ComposableTypes.navigationBar, NavigationBarView.Factory)
        registerComponent(
            ComposableTypes.navigationDrawerItem,
            NavigationDrawerItemView.Factory
        )
        registerComponent(ComposableTypes.navigationRail, NavigationRailView.Factory)
        registerComponent(ComposableTypes.navigationRailItem, NavigationRailItemView.Factory)
        registerComponent(ComposableTypes.outlinedButton, ButtonView.Factory)
        registerComponent(ComposableTypes.outlinedCard, CardView.Factory)
        registerComponent(ComposableTypes.outlinedIconButton, IconButtonView.Factory)
        registerComponent(
            ComposableTypes.outlinedIconToggleButton,
            IconToggleButtonView.Factory
        )
        registerComponent(ComposableTypes.outlinedTextField, TextFieldView.Factory)
        registerComponent(ComposableTypes.permanentDrawerSheet, DrawerSheetView.Factory)
        registerComponent(
            ComposableTypes.permanentNavigationDrawer,
            NavigationDrawerView.Factory
        )
        registerComponent(ComposableTypes.radioButton, RadioButtonView.Factory)
        registerComponent(ComposableTypes.rangeSlider, SliderView.Factory)
        registerComponent(ComposableTypes.richTooltip, TooltipView.Factory)
        registerComponent(ComposableTypes.row, RowView.Factory)
        registerComponent(ComposableTypes.scaffold, ScaffoldView.Factory)
        registerComponent(ComposableTypes.scrollableTabRow, TabRowView.Factory)
        registerComponent(ComposableTypes.searchBar, SearchBarView.Factory)
        registerComponent(
            ComposableTypes.singleChoiceSegmentedButtonRow,
            SegmentedButtonRowView.Factory
        )
        registerComponent(ComposableTypes.slider, SliderView.Factory)
        registerComponent(
            ComposableTypes.smallFloatingActionButton,
            FloatingActionButtonView.Factory
        )
        registerComponent(ComposableTypes.spacer, SpacerView.Factory)
        registerComponent(ComposableTypes.suggestionChip, ChipView.Factory)
        registerComponent(ComposableTypes.surface, SurfaceView.Factory)
        registerComponent(ComposableTypes.swipeToDismissBox, SwipeToDismissBoxView.Factory)
        registerComponent(ComposableTypes.switch, SwitchView.Factory)
        registerComponent(ComposableTypes.tab, TabView.Factory)
        registerComponent(ComposableTypes.tabRow, TabRowView.Factory)
        registerComponent(ComposableTypes.text, TextView.Factory)
        registerComponent(ComposableTypes.textButton, ButtonView.Factory)
        registerComponent(ComposableTypes.textField, TextFieldView.Factory)
        registerComponent(ComposableTypes.timeInput, TimePickerView.Factory)
        registerComponent(ComposableTypes.timePicker, TimePickerView.Factory)
        registerComponent(ComposableTypes.tooltipBox, TooltipBoxView.Factory)
        registerComponent(ComposableTypes.topAppBar, TopAppBarView.Factory)
        registerComponent(ComposableTypes.verticalDivider, DividerView.Factory)
        registerComponent(ComposableTypes.verticalPager, PagerView.Factory)
    }

    override fun buildComposableView(
        element: CoreNodeElement?,
        parentTag: String?,
        pushEvent: PushEvent,
        scope: Any?
    ): ComposableView<*> {
        return try {
            super.buildComposableView(element, parentTag, pushEvent, scope)
        } catch (uoe: UnsupportedOperationException) {
            val attrs = parseAttributeList(element?.attributes ?: persistentListOf())
            TextView.Factory.buildComposableView(
                "${element?.tag} not supported yet",
                attrs,
                scope,
                pushEvent,
            )
        } catch (iae: IllegalArgumentException) {
            TextView.Factory.buildComposableView(
                "Invalid element",
                persistentListOf(),
                scope,
                pushEvent
            )
        }
    }
}
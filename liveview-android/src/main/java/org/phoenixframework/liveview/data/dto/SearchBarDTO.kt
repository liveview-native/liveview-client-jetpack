package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.data.constants.Attrs.attrActive
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrOnActiveChanged
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxSubmit
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDividerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInputFieldColor
import org.phoenixframework.liveview.data.constants.Templates.templateContent
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_SUBMIT
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design search. A search bar represents a floating search field that allows users to
 * enter a keyword or phrase and get relevant information. It can be used as a way to navigate
 * through an app via search queries. An active search bar expands into a search "view" and can be
 * used to display dynamic suggestions.
 *
 * ```
 * <SearchBar query={"#{@queryText}"} phx-change="onQueryChange" active="false"
 *   phx-value="Initial value" phx-submit="onSearch">
 *   <Icon image-vector="filled:Search"  template="leadingIcon"/>
 *   <IconButton phx-click="" template="trailingIcon">
 *     <Icon image-vector="filled:Clear" />
 *   </IconButton>
 *   <Text template="placeholder">Placeholder</Text>
 *   <Text template="content">Searching by: <%= @queryText %></Text>
 * </SearchBar>
 * ```
 * You can also use a  DockedSearchBar using the same parameters.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SearchBarDTO private constructor(builder: Builder) :
    ChangeableDTO<String>(builder) {

    private val active = builder.active
    private val colors = builder.colors?.toImmutableMap()
    private val onActiveChanged = builder.onActiveChanged
    private val onSubmit = builder.onSubmit
    private val shadowElevation = builder.shadowElevation
    private val shape = builder.shape
    private val tonalElevation = builder.tonalElevation
    private val windowsInsets = builder.windowInsets

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val placeholder = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templatePlaceholder }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }
        val content = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateContent }
        }
        var queryStateValue by remember {
            mutableStateOf(value)
        }
        var activeState by remember {
            mutableStateOf(active)
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.dockedSearchBar ->
                DockedSearchBar(
                    query = queryStateValue,
                    onQueryChange = { q ->
                        queryStateValue = q
                    },
                    onSearch = { query ->
                        onSubmit.let { onSubmitEvent ->
                            if (onSubmitEvent.isNotBlank())
                                pushEvent.invoke(EVENT_TYPE_SUBMIT, onSubmitEvent, query, null)
                        }
                    },
                    active = activeState,
                    onActiveChange = { actv ->
                        activeState = actv
                        onActiveChanged.let { onActiveChangedEvent ->
                            if (onActiveChangedEvent.isNotBlank())
                                pushEvent.invoke(
                                    EVENT_TYPE_CHANGE,
                                    onActiveChangedEvent,
                                    actv,
                                    null
                                )
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    placeholder = placeholder?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: SearchBarDefaults.dockedShape,
                    colors = getSearchBarColors(colors),
                    tonalElevation = tonalElevation ?: SearchBarDefaults.TonalElevation,
                    shadowElevation = shadowElevation ?: SearchBarDefaults.ShadowElevation,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        content?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    }
                )

            ComposableTypes.searchBar ->
                SearchBar(
                    query = queryStateValue,
                    onQueryChange = { q ->
                        queryStateValue = q
                    },
                    onSearch = { query ->
                        onSubmit.let { onSubmitEvent ->
                            if (onSubmitEvent.isNotBlank())
                                pushEvent.invoke(EVENT_TYPE_SUBMIT, onSubmitEvent, query, null)
                        }
                    },
                    active = activeState,
                    onActiveChange = { actv ->
                        activeState = actv
                        onActiveChanged.let { onActiveChangedEvent ->
                            if (onActiveChangedEvent.isNotBlank())
                                pushEvent.invoke(
                                    EVENT_TYPE_CHANGE,
                                    onActiveChangedEvent,
                                    actv,
                                    null
                                )
                        }
                    },
                    modifier = modifier,
                    enabled = enabled,
                    placeholder = placeholder?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    shape = shape ?: SearchBarDefaults.inputFieldShape,
                    colors = getSearchBarColors(colors),
                    tonalElevation = tonalElevation ?: SearchBarDefaults.TonalElevation,
                    shadowElevation = shadowElevation ?: SearchBarDefaults.ShadowElevation,
                    windowInsets = windowsInsets ?: SearchBarDefaults.windowInsets,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        content?.let {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    }
                )
        }

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { queryStateValue }.map { it }.onChangeable().collect { value ->
                    pushOnChangeEvent(pushEvent, event, value)
                }
            }
        }
    }

    @Composable
    private fun getSearchBarColors(colors: ImmutableMap<String, String>?): SearchBarColors {
        val defaultColors = SearchBarDefaults.colors()

        return if (colors == null) {
            defaultColors
        } else {
            SearchBarDefaults.colors(
                containerColor = colors[colorAttrContainerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface,
                dividerColor = colors[colorAttrDividerColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                inputFieldColors = if (colors.containsKey(colorAttrInputFieldColor)) {
                    TextFieldDTO.getTextFieldColors(
                        textFieldColors = (
                                (colors[colorAttrInputFieldColor] as? Map<String, String>)
                                    ?: emptyMap()
                                ).toImmutableMap()
                    )
                } else {
                    SearchBarDefaults.inputFieldColors()
                }
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<String>("") {
        var active: Boolean = false
            private set
        var colors: Map<String, String>? = null
            private set
        var onActiveChanged: String = ""
            private set
        var onSubmit: String = ""
            private set
        var shadowElevation: Dp? = null
            private set
        var shape: Shape? = null
            private set
        var tonalElevation: Dp? = null
            private set
        var windowInsets: WindowInsets? = null
            private set

        /**
         * Whether this search bar is active.
         * ```
         * <SearchBar active="true">...</SearchBar>
         * ```
         * @param active true if the search bar is active, false otherwise.
         */
        fun active(active: String) = apply {
            this.active = active.toBoolean()
        }

        /**
         * Set SearchBar colors.
         * ```
         * <SearchBar
         *   colors="{'containerColor': '#FFFF0000', 'labelColor': '#FF00FF00'}">
         *   ...
         * </SearchBar>
         * ```
         * @param colors an JSON formatted string, containing the chip colors. The color keys
         * supported are: `containerColor`, `dividerColor`, and `inputFieldColors`.
         */
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                this.colors = colorsFromString(colors)
            }
        }

        /**
         * Sets the event name to be triggered on the server when the active state changes.
         *
         * ```
         * <SearchBar on-active-changed="yourServerEventHandler">...</SearchBar>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onActiveChanged(event: String) = apply {
            this.onActiveChanged = event
        }

        /**
         * Sets the event name to be triggered on the server when the search button is clicked.
         *
         * ```
         * <SearchBar phx-submit="yourServerEventHandler">...</SearchBar>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        fun onSubmit(event: String) = apply {
            this.onSubmit = event
        }

        /**
         * The shadow elevation of the search bar.
         * ```
         * <SearchBar shadow-elevation="12" >
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        fun shadowElevation(shadowElevation: String) = apply {
            if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                this.shadowElevation = shadowElevation.toInt().dp
            }
        }

        /**
         * Defines the shape of the search bar's container, border, and shadow (when using elevation).
         * ```
         * <SearchBar shape="circle" >...</SearchBar>
         * ```
         * @param shape search bar's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied for all four corners.
         */
        fun shape(shape: String) = apply {
            if (shape.isNotEmpty()) {
                this.shape = shapeFromString(shape)
            }
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <SearchBar tonal-elevation="24">...</SearchBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        fun tonalElevation(tonalElevation: String) = apply {
            if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                this.tonalElevation = tonalElevation.toInt().dp
            }
        }

        /**
         * Window insets to be passed to the search bar via PaddingValues params.
         * ```
         * <SearchBar window-insets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        fun windowInsets(insets: String) = apply {
            try {
                this.windowInsets = windowInsetsFromString(insets)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun build() = SearchBarDTO(this)
    }
}

internal object SearchBarDtoFactory : ComposableViewFactory<SearchBarDTO, SearchBarDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SearchBarDTO = SearchBarDTO.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrActive -> builder.active(attribute.value)
                    attrOnActiveChanged -> builder.onActiveChanged(attribute.value)
                    attrColors -> builder.colors(attribute.value)
                    attrPhxSubmit -> builder.onSubmit(attribute.value)
                    attrPhxValue -> builder.value(attribute.value)
                    attrShadowElevation -> builder.shadowElevation(attribute.value)
                    attrShape -> builder.shape(attribute.value)
                    attrTonalElevation -> builder.tonalElevation(attribute.value)
                    attrWindowInsets -> builder.windowInsets(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as SearchBarDTO.Builder
            }
        }
    }.build()
}
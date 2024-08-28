package org.phoenixframework.liveview.ui.view

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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.constants.Attrs.attrActive
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrOnActiveChanged
import org.phoenixframework.liveview.constants.Attrs.attrPhxSubmit
import org.phoenixframework.liveview.constants.Attrs.attrQuery
import org.phoenixframework.liveview.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.Attrs.attrWindowInsets
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDividerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrInputFieldColors
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.Templates.templateContent
import org.phoenixframework.liveview.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material Design search. A search bar represents a floating search field that allows users to
 * enter a keyword or phrase and get relevant information. It can be used as a way to navigate
 * through an app via search queries. An active search bar expands into a search "view" and can be
 * used to display dynamic suggestions.
 *
 * ```
 * <SearchBar query={"#{@queryText}"} active="false"
 *   phx-change="onQueryChange" phx-submit="onSearch">
 *   <Icon imageVector="filled:Search"  template="leadingIcon"/>
 *   <IconButton phx-click="" template="trailingIcon">
 *     <Icon imageVector="filled:Clear" />
 *   </IconButton>
 *   <Text template="placeholder">Placeholder</Text>
 *   <Text template="content">Searching by: <%= @queryText %></Text>
 * </SearchBar>
 * ```
 * You can also use a  DockedSearchBar using the same parameters.
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class SearchBarView private constructor(props: Properties) :
    ChangeableView<String, SearchBarView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val changeValueEventName = props.changeableProps.onChange
        val enabled = props.changeableProps.enabled

        val active = props.active
        val colors = props.colors
        val onActiveChanged = props.onActiveChanged
        val onSubmit = props.onSubmit
        val query = props.query
        val shadowElevation = props.shadowElevation
        val shape = props.shape
        val tonalElevation = props.tonalElevation
        val windowsInsets = props.windowInsets

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
            mutableStateOf(query)
        }
        var activeState by remember {
            mutableStateOf(active)
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.dockedSearchBar ->
                DockedSearchBar(query = queryStateValue,
                    onQueryChange = { q ->
                        queryStateValue = q
                    },
                    onSearch = { queryText ->
                        onSubmit.let { onSubmitEvent ->
                            if (onSubmitEvent.isNotBlank()) pushEvent.invoke(
                                EVENT_TYPE_SUBMIT,
                                onSubmitEvent,
                                mergeValueWithPhxValue(KEY_QUERY, queryText),
                                null
                            )
                        }
                    },
                    active = activeState,
                    onActiveChange = { actv ->
                        onActiveChanged.let { onActiveChangedEvent ->
                            if (onActiveChangedEvent.isNotBlank()) pushEvent.invoke(
                                EVENT_TYPE_CHANGE,
                                onActiveChangedEvent,
                                mergeValueWithPhxValue(KEY_ACTIVE, actv),
                                null
                            )
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    })

            ComposableTypes.searchBar ->
                SearchBar(query = queryStateValue,
                    onQueryChange = { q ->
                        queryStateValue = q
                    },
                    onSearch = { queryText ->
                        onSubmit.let { onSubmitEvent ->
                            if (onSubmitEvent.isNotBlank()) pushEvent.invoke(
                                EVENT_TYPE_SUBMIT,
                                onSubmitEvent,
                                mergeValueWithPhxValue(KEY_QUERY, queryText),
                                null
                            )
                        }
                    },
                    active = activeState,
                    onActiveChange = { actv ->
                        activeState = actv
                        onActiveChanged.let { onActiveChangedEvent ->
                            if (onActiveChangedEvent.isNotBlank()) pushEvent.invoke(
                                EVENT_TYPE_CHANGE,
                                onActiveChangedEvent,
                                mergeValueWithPhxValue(KEY_ACTIVE, actv),
                                null
                            )
                        }
                    },
                    modifier = props.commonProps.modifier,
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
                    })
        }

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow {
                    queryStateValue
                }.map {
                    it
                }.onChangeable().map {
                    mergeValueWithPhxValue(KEY_QUERY, it)
                }.collect { value ->
                    pushOnChangeEvent(pushEvent, event, value)
                }
            }
        }
    }

    companion object {
        const val KEY_QUERY = "query"
        const val KEY_ACTIVE = "active"
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
                inputFieldColors = if (colors.containsKey(colorAttrInputFieldColors)) {
                    TextFieldView.getTextFieldColors(
                        textFieldColors = ((colors[colorAttrInputFieldColors] as? Map<String, String>)
                            ?: emptyMap()).toImmutableMap()
                    )
                } else {
                    SearchBarDefaults.inputFieldColors()
                }
            )
        }
    }

    @Stable
    internal data class Properties(
        val active: Boolean = false,
        val colors: ImmutableMap<String, String>? = null,
        val onActiveChanged: String = "",
        val onSubmit: String = "",
        val query: String = "",
        val shadowElevation: Dp? = null,
        val shape: Shape? = null,
        val tonalElevation: Dp? = null,
        val windowInsets: WindowInsets? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ChangeableView.Factory() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): SearchBarView = SearchBarView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrActive -> active(props, attribute.value)
                        attrOnActiveChanged -> onActiveChanged(props, attribute.value)
                        attrColors -> colors(props, attribute.value)
                        attrPhxSubmit -> onSubmit(props, attribute.value)
                        attrQuery -> query(props, attribute.value)
                        attrShadowElevation -> shadowElevation(props, attribute.value)
                        attrShape -> shape(props, attribute.value)
                        attrTonalElevation -> tonalElevation(props, attribute.value)
                        attrWindowInsets -> windowInsets(props, attribute.value)
                        else -> props.copy(
                            commonProps = handleCommonAttributes(
                                props.commonProps,
                                attribute,
                                pushEvent,
                                scope
                            )
                        )
                    }
                }
            })

        /**
         * Whether this search bar is active.
         * ```
         * <SearchBar active="true">...</SearchBar>
         * ```
         * @param active true if the search bar is active, false otherwise.
         */
        private fun active(props: Properties, active: String): Properties {
            return props.copy(active = active.toBoolean())
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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Sets the event name to be triggered on the server when the active state changes.
         *
         * ```
         * <SearchBar onActiveChanged="yourServerEventHandler">...</SearchBar>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onActiveChanged(props: Properties, event: String): Properties {
            return props.copy(onActiveChanged = event)
        }

        /**
         * Sets the event name to be triggered on the server when the search button is clicked.
         *
         * ```
         * <SearchBar phx-submit="yourServerEventHandler">...</SearchBar>
         * ```
         * @param event event name defined on the server to handle the button's click.
         */
        private fun onSubmit(props: Properties, event: String): Properties {
            return props.copy(onSubmit = event)
        }

        private fun query(props: Properties, query: String): Properties {
            return props.copy(query = query)
        }

        /**
         * The shadow elevation of the search bar.
         * ```
         * <SearchBar shadowElevation="12" >
         * ```
         * @param shadowElevation int value indicating the shadow elevation.
         */
        private fun shadowElevation(props: Properties, shadowElevation: String): Properties {
            return if (shadowElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(shadowElevation = shadowElevation.toInt().dp)
            } else props
        }

        /**
         * Defines the shape of the search bar's container, border, and shadow (when using elevation).
         * ```
         * <SearchBar shape="circle" >...</SearchBar>
         * ```
         * @param shape search bar's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or an integer representing
         * the curve size applied for all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape))
            } else props
        }

        /**
         * A higher tonal elevation value will result in a darker color in light theme and lighter
         * color in dark theme.
         * ```
         * <SearchBar tonalElevation="24">...</SearchBar>
         * ```
         * @param tonalElevation int value indicating the tonal elevation.
         */
        private fun tonalElevation(props: Properties, tonalElevation: String): Properties {
            return if (tonalElevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = tonalElevation.toInt().dp)
            } else props
        }

        /**
         * Window insets to be passed to the search bar via PaddingValues params.
         * ```
         * <SearchBar windowInsets="{'bottom': '100'}" >
         * ```
         * @param insets the space, in Dp, at the each border of the window that the inset
         * represents. The supported values are: `left`, `top`, `bottom`, and `right`.
         */
        private fun windowInsets(props: Properties, insets: String): Properties {
            return try {
                props.copy(windowInsets = windowInsetsFromString(insets))
            } catch (e: Exception) {
                e.printStackTrace()
                props
            }
        }
    }
}
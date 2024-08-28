package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.constants.Attrs.attrBeyondViewportPageCount
import org.phoenixframework.liveview.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.constants.Attrs.attrCurrentPage
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.constants.Attrs.attrInitialPageOffsetFraction
import org.phoenixframework.liveview.constants.Attrs.attrPageCount
import org.phoenixframework.liveview.constants.Attrs.attrPageSize
import org.phoenixframework.liveview.constants.Attrs.attrPageSpacing
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.constants.Attrs.attrUserScrollEnabled
import org.phoenixframework.liveview.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Pager that scrolls horizontally or vertically. Pages are lazily placed in accordance to the
 * available viewport size. By definition, pages in a Pager have the same size, defined by pageSize.
 * You can use beyondBoundsPageCount to place more pages before and after the visible pages.
 * ```
 * <Column style="fillMaxSize()">
 *   <TabRow selectedTabIndex={"#{@selectedTab}"}>
 *     <Tab selected={"#{@selectedTab == "0"}"} phx-click="selectTab" phx-value="0">
 *       <Text template="text">Tab 0</Text>
 *     </Tab>
 *     <Tab selected={"#{@selectedTab == "1"}"} phx-click="selectTab" phx-value="1">
 *       <Text template="text">Tab 1</Text>
 *     </Tab>
 *     <Tab selected={"#{@selectedTab == "2"}"} phx-click="selectTab" phx-value="2">
 *       <Text template="text">Tab 2</Text>
 *     </Tab>
 *   </TabRow>
 *   <HorizontalPager currentPage={"#{@selectedTab}"} pageCount="3" phx-change="selectTab">
 *     <Box contentAlignment="center" background="Red" style="fillMaxSize()">
 *       <Text fontSize="24">Red</Text>
 *     </Box>
 *     <Box contentAlignment="center" background="Green" style="fillMaxSize()">
 *       <Text fontSize="24">Green</Text>
 *     </Box>
 *     <Box contentAlignment="center" background="Blue" style="fillMaxSize()">
 *       <Text fontSize="24">Blue</Text>
 *     </Box>
 *   </HorizontalPager>
 * </Column>
 * ```
 */
@OptIn(FlowPreview::class)
internal class PagerView private constructor(props: Properties) :
    ComposableView<PagerView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val currentPage = props.currentPage
        val initialPageOffsetFraction = props.initialPageOffsetFraction
        val pageCount = props.pageCount
        val contentPadding = props.contentPadding
        val onChanged = props.onChanged
        val pageSize = props.pageSize
        val beyondBoundsPageCount = props.beyondViewportPageCount
        val pageSpacing = props.pageSpacing
        val verticalAlignment = props.verticalAlignment
        val horizontalAlignment = props.horizontalAlignment
        val userScrollEnabled = props.userScrollEnabled
        val reverseLayout = props.reverseLayout

        val childrenNodes = composableNode?.children ?: emptyArray()
        val state = rememberPagerState(
            initialPage = currentPage,
            initialPageOffsetFraction = initialPageOffsetFraction ?: 0f,
            pageCount = { pageCount },
        )
        when (composableNode?.node?.tag) {
            ComposableTypes.horizontalPager -> {
                HorizontalPager(
                    state = state,
                    modifier = props.commonProps.modifier,
                    contentPadding = contentPadding ?: PaddingValues(0.dp),
                    pageSize = pageSize ?: PageSize.Fill,
                    beyondViewportPageCount = beyondBoundsPageCount
                        ?: PagerDefaults.BeyondViewportPageCount,
                    pageSpacing = pageSpacing,
                    verticalAlignment = verticalAlignment ?: Alignment.CenterVertically,
                    // TODO flingBehavior: SnapFlingBehavior,
                    userScrollEnabled = userScrollEnabled,
                    reverseLayout = reverseLayout,
                    key = { index ->
                        childrenNodes[index].id
                    },
                    // TODO pageNestedScrollConnection: NestedScrollConnection,
                    pageContent = { index ->
                        PhxLiveView(childrenNodes[index], pushEvent, composableNode, null, this)
                    }
                )
            }

            ComposableTypes.verticalPager -> {
                VerticalPager(
                    state = state,
                    modifier = props.commonProps.modifier,
                    contentPadding = contentPadding ?: PaddingValues(0.dp),
                    pageSize = pageSize ?: PageSize.Fill,
                    beyondViewportPageCount = beyondBoundsPageCount
                        ?: PagerDefaults.BeyondViewportPageCount,
                    pageSpacing = pageSpacing,
                    horizontalAlignment = horizontalAlignment ?: Alignment.CenterHorizontally,
                    // TODO flingBehavior: SnapFlingBehavior,
                    userScrollEnabled = userScrollEnabled,
                    reverseLayout = reverseLayout,
                    key = { index ->
                        childrenNodes[index].id
                    },
                    // TODO pageNestedScrollConnection: NestedScrollConnection,
                    pageContent = { index ->
                        PhxLiveView(childrenNodes[index], pushEvent, composableNode, null, this)
                    }
                )
            }
        }
        LaunchedEffect(state) {
            snapshotFlow {
                state.currentPage
            }
                .distinctUntilChanged()
                // debouncing to avoid send intermediate values between the scrolling animation
                .debounce(300)
                .map {
                    mergeValueWithPhxValue(KEY_PAGE, it)
                }
                .collect { value ->
                    onChanged?.let { event ->
                        pushEvent.invoke(EVENT_TYPE_CHANGE, event, value, null)
                    }
                }
        }
        LaunchedEffect(currentPage) {
            if (currentPage != state.currentPage) {
                state.animateScrollToPage(currentPage)
            }
        }
    }

    companion object {
        const val KEY_PAGE = "page"
    }

    @Stable
    internal data class Properties(
        val beyondViewportPageCount: Int? = null,
        val contentPadding: PaddingValues? = null,
        val currentPage: Int = 0,
        val horizontalAlignment: Alignment.Horizontal? = null,
        val initialPageOffsetFraction: Float? = null,
        val onChanged: String? = null,
        val pageCount: Int = 0,
        val pageSize: PageSize? = null,
        val pageSpacing: Dp = 0.dp,
        val reverseLayout: Boolean = false,
        val userScrollEnabled: Boolean = true,
        val verticalAlignment: Alignment.Vertical? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<PagerView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): PagerView =
            PagerView(attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrBeyondViewportPageCount -> beyondViewportPageCount(props, attribute.value)
                    attrContentPadding -> contentPadding(props, attribute.value)
                    attrCurrentPage -> currentPage(props, attribute.value)
                    attrHorizontalAlignment -> horizontalAlignment(props, attribute.value)
                    attrInitialPageOffsetFraction -> initialPageOffsetFraction(
                        props,
                        attribute.value
                    )

                    attrPageCount -> pageCount(props, attribute.value)
                    attrPageSize -> pageSize(props, attribute.value)
                    attrPageSpacing -> pageSpacing(props, attribute.value)
                    attrPhxChange -> onChanged(props, attribute.value)
                    attrReverseLayout -> reverseLayout(props, attribute.value)
                    attrUserScrollEnabled -> userScrollEnabled(props, attribute.value)
                    attrVerticalAlignment -> verticalAlignment(props, attribute.value)
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

        /**
         * Pages to compose and layout before and after the list of visible pages.
         * Note: Be aware that using a large value for beyondBoundsPageCount will cause a lot of
         * pages to be composed, measured and placed which will defeat the purpose of using lazy
         * loading. This should be used as an optimization to pre-load a couple of pages before and
         * after the visible ones. This does not include the pages automatically composed and laid
         * out by the pre-fetcher in the direction of the scroll during scroll events.
         * ```
         * <HorizontalPager beyondBoundsPageCount="2" >...</HorizontalPager>
         * ```
         * @param count int value representing the number of pages to compose and layout before and
         * after the list of visible pages.
         */
        private fun beyondViewportPageCount(props: Properties, count: String): Properties {
            return props.copy(beyondViewportPageCount = count.toIntOrNull())
        }

        /**
         * A padding around the whole content. This will add padding for the content after it has
         * been clipped, which is not possible via modifier param. You can use it to add a padding
         * before the first page or after the last one. Use pageSpacing to add spacing between the
         * pages.
         * ```
         * <HorizontalPager contentPadding="8" >...</HorizontalPager>
         * ```
         * @param padding int value representing a padding around the whole content.
         */
        private fun contentPadding(props: Properties, padding: String): Properties {
            return padding.toIntOrNull()?.let {
                props.copy(contentPadding = PaddingValues(it.dp))
            } ?: props
        }

        /**
         * The current selected page index. Notice this value must be between 0 and pageCount
         * (exclusive). This value is also used as initial selected page.
         * ```
         * <HorizontalPager currentPage="0" >...</HorizontalPager>
         * ```
         * @param currentPage int value representing the current selected page index.
         */
        private fun currentPage(props: Properties, currentPage: String): Properties {
            return props.copy(currentPage = currentPage.toIntOrNull() ?: 0)
        }

        /**
         * How pages are aligned horizontally in this in a VerticalPager.
         * ```
         * <VerticalPager horizontalAlignment="center" >...</VerticalPager>
         * ```
         * @param alignment see supported values at
         * [org.phoenixframework.liveview.constants.HorizontalAlignmentValues].
         */
        private fun horizontalAlignment(props: Properties, alignment: String): Properties {
            return props.copy(horizontalAlignment = horizontalAlignmentFromString(alignment))
        }

        /**
         * The offset of the initial page as a fraction of the page size. This should vary between
         * -0.5 and 0.5 and indicates how to offset the initial page from the snapped position.
         * @param offset float value indicating the offset of the initial page as a fraction of the
         * page size.
         */
        private fun initialPageOffsetFraction(props: Properties, offset: String): Properties {
            return props.copy(initialPageOffsetFraction = offset.toFloatOrNull())
        }

        /**
         * Event called in the server when the current page changes. This event receives the page
         * index as parameter.
         * ```
         * <HorizontalPager phx-change="selectTab" >...</HorizontalPager>
         * ```
         * @param onChanged event to be triggered on the server when the selected tab changes.
         */
        private fun onChanged(props: Properties, onChanged: String): Properties {
            return props.copy(onChanged = onChanged)
        }

        /**
         * The amount of pages this Pager will have.
         * ```
         * <HorizontalPager pageCount="3" >...</HorizontalPager>
         * ```
         * @param count int value representing the amount of pages this Pager will have.
         */
        private fun pageCount(props: Properties, count: String): Properties {
            return props.copy(pageCount = count.toIntOrNull() ?: 0)
        }

        /**
         * Use this to change how the pages will look like inside this pager.
         * ```
         * <HorizontalPager pageSize="fill" >...</HorizontalPager>
         * ```
         * @param size the supported values are 'fill' (default) or an int value to set the page
         * size.
         */
        private fun pageSize(props: Properties, size: String): Properties {
            return props.copy(pageSize = when (size) {
                "fill" -> PageSize.Fill
                else -> size.toIntOrNull()?.let { PageSize.Fixed(it.dp) }
            })
        }

        /**
         * The amount of space to be used to separate the pages in this Pager.
         * ```
         * <HorizontalPager pageSpacing="16" >...</HorizontalPager>
         * ```
         * @param spacing int value representing the space to separate the pages
         */
        private fun pageSpacing(props: Properties, spacing: String): Properties {
            return spacing.toIntOrNull()?.let {
                props.copy(pageSpacing = it.dp)
            } ?: props
        }

        /**
         * Reverse the direction of scrolling and layout.
         * ```
         * <HorizontalPager reverseLayout="true" >...</HorizontalPager>
         * ```
         * @param reverse true if the scrolling direction and layout must be reversed, false
         * otherwise
         */
        private fun reverseLayout(props: Properties, reverse: String): Properties {
            return props.copy(reverseLayout = reverse.toBoolean())
        }

        /**
         * Whether the scrolling via the user gestures or accessibility actions is allowed.
         * ```
         * <HorizontalPager userScrollEnabled="false" >...</HorizontalPager>
         * ```
         * @param enabled true if the user gestures are enabled, false otherwise.
         */
        private fun userScrollEnabled(props: Properties, enabled: String): Properties {
            return props.copy(userScrollEnabled = enabled.toBoolean())
        }

        /**
         * How pages are aligned vertically in this in a HorizontalPager.
         * ```
         * <HorizontalPager verticalAlignment="center" >...</HorizontalPager>
         * ```
         * @param alignment see the supported values at
         * [org.phoenixframework.liveview.constants.VerticalAlignmentValues].
         */
        private fun verticalAlignment(props: Properties, alignment: String): Properties {
            return props.copy(verticalAlignment = verticalAlignmentFromString(alignment))
        }
    }
}
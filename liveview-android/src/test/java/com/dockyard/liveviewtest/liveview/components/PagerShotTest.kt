package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalFoundationApi::class)
class PagerShotTest : LiveViewComposableTest() {
    @Test
    fun simpleHorizontalPagerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 2 }
                )
                HorizontalPager(state = state) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="2" phx-change="">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerWithInitialOffsetTest() {
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 500,
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 3 },
                    initialPageOffsetFraction = 0.5f,
                )
                HorizontalPager(state = state) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Green, text = "Green")
                        2 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="3" phx-change="" initial-page-offset-fraction="0.5">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-green", "Green")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerWithContentPaddingTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 2 }
                )
                HorizontalPager(
                    state = state,
                    contentPadding = PaddingValues(16.dp),
                ) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="2" content-padding="16" phx-change="">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerWithPageSizeTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 2 }
                )
                HorizontalPager(
                    state = state,
                    pageSize = PageSize.Fixed(200.dp),
                ) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="2" page-size="200" phx-change="">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerWithPageSpacingTest() {
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 500,
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 3 },
                    initialPageOffsetFraction = 0.5f,
                )
                HorizontalPager(state = state, pageSpacing = 24.dp) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Green, text = "Green")
                        2 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="3" phx-change="" 
                    initial-page-offset-fraction="0.5" page-spacing="24">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-green", "Green")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerSwipeTest() {
        compareNativeComposableWithTemplate(
            onBeforeScreenShot = { composeTestRule ->
                composeTestRule.onRoot().performTouchInput { swipeLeft() }
            },
            delayBeforeScreenshot = 300, // swipe takes 200ms
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 3 },
                )
                HorizontalPager(state = state, pageSpacing = 24.dp) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Green, text = "Green")
                        2 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="3" phx-change="" page-spacing="24">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-green", "Green")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun horizontalPagerReverseLayoutTest() {
        compareNativeComposableWithTemplate(
            onBeforeScreenShot = { composeTestRule ->
                composeTestRule.onRoot().performTouchInput { swipeRight() }
            },
            delayBeforeScreenshot = 300, // swipe takes 200ms
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 3 },
                )
                HorizontalPager(state = state, reverseLayout = true) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Blue, text = "Blue")
                        2 -> pagerTab(color = Color.Green, text = "Green")
                    }
                }
            },
            template = """
                <HorizontalPager current-page="0" page-count="3" phx-change="" reverse-layout="true">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                  ${pagerTemplateTab("system-green", "Green")}
                </HorizontalPager>            
                """
        )
    }

    @Test
    fun verticalPagerSwipeTest() {
        compareNativeComposableWithTemplate(
            onBeforeScreenShot = { composeTestRule ->
                composeTestRule.onRoot().performTouchInput { swipeUp() }
            },
            delayBeforeScreenshot = 300, // swipe takes 200ms
            nativeComposable = {
                val state = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 3 },
                )
                VerticalPager(state = state) { page ->
                    when (page) {
                        0 -> pagerTab(Color.Red, "Red")
                        1 -> pagerTab(color = Color.Green, text = "Green")
                        2 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <VerticalPager current-page="0" page-count="3" phx-change="">
                  ${pagerTemplateTab("system-red", "Red")}
                  ${pagerTemplateTab("system-green", "Green")}
                  ${pagerTemplateTab("system-blue", "Blue")}
                </VerticalPager>            
                """
        )
    }

    private fun pagerTemplateTab(color: String, text: String): String {
        return """
            <Box content-alignment="center" background="$color" size="fill">
                <Text font-size="24">$text</Text>
            </Box>
            """
    }

    @Composable
    private fun pagerTab(color: Color, text: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color)
                .fillMaxSize()
        ) {
            Text(text = text, fontSize = 24.sp)
        }
    }
}
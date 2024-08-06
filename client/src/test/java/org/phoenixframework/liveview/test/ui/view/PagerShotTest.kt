package org.phoenixframework.liveview.test.ui.view

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
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Ignore
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues.center
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrContentPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrCurrentPage
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialPageOffsetFraction
import org.phoenixframework.liveview.data.constants.Attrs.attrPageCount
import org.phoenixframework.liveview.data.constants.Attrs.attrPageSize
import org.phoenixframework.liveview.data.constants.Attrs.attrPageSpacing
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.horizontalPager
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.verticalPager
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red

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
                <$horizontalPager $attrCurrentPage="0" $attrPageCount="2">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
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
                <$horizontalPager $attrCurrentPage="0" $attrPageCount="3"
                  $attrInitialPageOffsetFraction="0.5">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Green, "Green")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
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
                <$horizontalPager
                  $attrCurrentPage="0"
                  $attrPageCount="2"
                  $attrContentPadding="16">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
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
                <$horizontalPager $attrCurrentPage="0" $attrPageCount="2" $attrPageSize="200">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
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
                <$horizontalPager $attrCurrentPage="0" $attrPageCount="3"
                  $attrInitialPageOffsetFraction="0.5" $attrPageSpacing="24">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Green, "Green")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
                """
        )
    }

    @Test
    @Ignore("After swipe gesture, the image is not captured")
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
                HorizontalPager(state = state, pageSpacing = 0.dp) { page ->
                    when (page) {
                        0 -> pagerTab(color = Color.Red, text = "Red")
                        1 -> pagerTab(color = Color.Green, text = "Green")
                        2 -> pagerTab(color = Color.Blue, text = "Blue")
                    }
                }
            },
            template = """
                <$horizontalPager $attrCurrentPage="0" $attrPageCount="3" $attrPageSpacing="24">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Green, "Green")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$horizontalPager>
                """
        )
    }

    @Test
    @Ignore("After swipe gesture, the image is not captured")
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
                <$horizontalPager
                  $attrCurrentPage="0"
                  $attrPageCount="3"
                  $attrReverseLayout="true">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Blue, "Blue")}
                  ${pagerTemplateTab(Green, "Green")}
                </$horizontalPager>
                """
        )
    }

    @Test
    @Ignore("After swipe gesture, the image is not captured")
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
                <$verticalPager $attrCurrentPage="0" $attrPageCount="3">
                  ${pagerTemplateTab(Red, "Red")}
                  ${pagerTemplateTab(Green, "Green")}
                  ${pagerTemplateTab(Blue, "Blue")}
                </$verticalPager>
                """
        )
    }

    private fun pagerTemplateTab(color: String, textContent: String): String {
        return """
            <$box $attrContentAlignment="$center" $attrStyle="$modifierFillMaxSize();$modifierBackground($typeColor.$color)">
                <$text $attrFontSize="24">$textContent</$text>
            </$box>
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